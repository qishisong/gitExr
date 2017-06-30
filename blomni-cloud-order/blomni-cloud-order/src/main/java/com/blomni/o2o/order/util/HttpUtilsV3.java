package com.blomni.o2o.order.util;



/**
 * @Probject Name: common-core
 * @Path: com.bailian.core.utils.HttpUtilsV3.java
 * @Create By IBM Consultant Team
 * @Create In 2016-01-11 涓婂崍9:27:46
 * 
 */

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.bailian.core.constants.ComErrorCodeConstants;
import com.bailian.core.framework.exception.BleException;
import com.bailian.core.utils.IdleConnectionTimeoutThread;
import com.bailian.core.utils.MySSLProtocolSocketFactory;

import net.sf.json.JSONObject;

/**
 * http宸ュ叿绫�
 * @Class Name HttpUtil
 * @Author IBM Consultant Team
 * @Create In 2014骞�10鏈�28鏃�
 */
public class HttpUtilsV3 {
	
	protected final static Logger LOGGER = LoggerFactory.getLogger(HttpUtilsV3.class);
	protected final static int SOCKET_TIMEOUT = 60 * 1000;
	protected final static String DEFAULT_ENCODING = "UTF-8";
	protected final static String JSON_CONTENT_TYPE = "application/json";
	
	private static final int HTTP_CONNNECT_TIMEOUT = 30 * 1000;
	
	/**
	 * @see IdleConnectionTimeoutThread#setTimeoutInterval(long)
	 */
	private static final long CONNECTION_TIMEOUT_INTERVAL = 30 * 1000;
	
	protected static RequestConfig defaultRequestConfig = null;
	
	private static IdleConnectionTimeoutThread idleConnectionTimeoutThread;
	
	private final static ThreadLocal<CloseableHttpClient> THREAD_HTTP_CLENTS = new ThreadLocal<CloseableHttpClient>() {};
	
	private static PoolingHttpClientConnectionManager clientConnectionManager = null;
	
	private final static int DEFALUT_CLIENT_CONNECTION_MAX_TTL = 1000;
	
	private final static int DEFALUT_CLIENT_CONNECTION_MAX_PER_ROUTE_TTL = 300;
	
	static {
		
		Integer clientConnectionMaxTtl= null, clientConnectionMaxPerRouteTtl = null;
		
		try{
			
			clientConnectionMaxTtl = Integer.parseInt(System.getProperty("httpclient.conn.max.total"));
			clientConnectionMaxPerRouteTtl = Integer.parseInt(System.getProperty("hhttpclient.conn.max.per.route"));
		}
		catch (Exception ex){
			
		}
		
		if (clientConnectionMaxTtl== null) {
			
			clientConnectionMaxTtl = DEFALUT_CLIENT_CONNECTION_MAX_TTL;
		}
		
		if (clientConnectionMaxPerRouteTtl== null) {
			
			clientConnectionMaxPerRouteTtl = DEFALUT_CLIENT_CONNECTION_MAX_PER_ROUTE_TTL;
		}
		
		clientConnectionManager = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
		clientConnectionManager.setMaxTotal(clientConnectionMaxTtl);
		clientConnectionManager.setDefaultMaxPerRoute(clientConnectionMaxPerRouteTtl);
		
		addConnectionManager(clientConnectionManager);
	}
	
	public synchronized static void addConnectionManager(HttpClientConnectionManager connectionManager) {
		
		if (idleConnectionTimeoutThread == null) {
			idleConnectionTimeoutThread = new IdleConnectionTimeoutThread();
			idleConnectionTimeoutThread.setTimeoutInterval(CONNECTION_TIMEOUT_INTERVAL);
			idleConnectionTimeoutThread.setConnectionTimeout(HTTP_CONNNECT_TIMEOUT);
			idleConnectionTimeoutThread.start();
		}
		idleConnectionTimeoutThread.addConnectionManager(connectionManager);
	}
	
	/**
	 * @since 3.1
	 */
	public synchronized static void removeConnectionManager(HttpClientConnectionManager connectionManager) {
		if (idleConnectionTimeoutThread == null) {
			return;
		}
		idleConnectionTimeoutThread.removeConnectionManager(connectionManager);
	}
	
	/***
	 * Get instance of {@link CloseableHttpClient}
	 * @return
	 */
	protected static final CloseableHttpClient getInstance () {
		
		// Create retry handler with 3 times
    	HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(
                    IOException exception,
                    int executionCount,
                    HttpContext context) {
                if (executionCount >= 3) {
                    // 濡傛灉宸茬粡閲嶈瘯浜�5娆★紝灏辨斁寮�
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // 瓒呮椂
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // 鐩爣鏈嶅姟鍣ㄤ笉鍙揪
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // 杩炴帴琚嫆缁�
                    return false;
                }
                if (exception instanceof SSLException) {
                    // ssl鎻℃墜寮傚父
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // 濡傛灉璇锋眰鏄箓绛夌殑锛屽氨鍐嶆灏濊瘯
                    return true;
                }
                return false;
            }

        }; 
        
        CloseableHttpClient httpClient = THREAD_HTTP_CLENTS.get();
        if ( httpClient == null) {
        	
        	httpClient = HttpClients.custom()
        			.setConnectionManager(clientConnectionManager).setRetryHandler(retryHandler).build();
        	THREAD_HTTP_CLENTS.set(httpClient);
        }
        
        return httpClient;
	}
	
	/***
	 * Fire Http Restful Request
	 * @param uriRequest
	 * @return instance of {@link CloseableHttpResponse}
	 * @throws BleException
	 */
	protected static CloseableHttpResponse fireHttpRestRequest(HttpUriRequest uriRequest) throws BleException {
		
		return HttpUtilsV3.fireHttpRestRequest(uriRequest, HTTP_CONNNECT_TIMEOUT);
	}

	/***
	 * Fire Http Restful Request
	 * @param uriRequest
	 * @param connectionTimeout
	 * @return instance of {@link CloseableHttpResponse}
	 * @throws BleException
	 */
	protected static CloseableHttpResponse fireHttpRestRequest(HttpUriRequest uriRequest, int connectionTimeout) throws BleException {
		
		CloseableHttpResponse httpResponse = null;
		
		//SET RequestConfig for instance of HttpRequestBase
		if (uriRequest != null && uriRequest instanceof HttpRequestBase) {
			
			synchronized (uriRequest){
				
				RequestConfig requestConfig = HttpUtilsV3.buildRequestConfig(connectionTimeout);
				
				((HttpRequestBase)uriRequest).setConfig(requestConfig);
				
				// 璁剧疆Http Post鏁版嵁 
		        try {
		        	httpResponse = HttpUtilsV3.getInstance().execute(uriRequest);
		        } catch (Exception e) {
		        	
		        	LOGGER.error("fireHttpRestRequest", e);
		        	throw new BleException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
		        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e).getMessage() );
		        } finally {
		        	
		        	//TODO: To be refactored with HttpResponse Copier
/*		        	try {
						if (httpResponse != null && httpResponse.getEntity() != null) {
							httpResponse.getEntity().getContent().close();
						}
						if (HttpUtilsV3.getInstance() != null) {
							HttpUtilsV3.getInstance().close();
						}
					} catch (IOException e) {
					}*/
		        }
		        
			}
		}
				
		return httpResponse;
	}
	
	/***
	 * build RequestConfig
	 * @param connectionTimeout
	 * @return instance of {@link RequestConfig}
	 */
	protected static final RequestConfig buildRequestConfig(int connectionTimeout){
		
		if ( HTTP_CONNNECT_TIMEOUT==connectionTimeout ) {
			
			if ( defaultRequestConfig == null) {
				
				defaultRequestConfig = RequestConfig.custom()
		                .setSocketTimeout(HttpUtilsV3.SOCKET_TIMEOUT)
		                .setConnectTimeout(HttpUtilsV3.HTTP_CONNNECT_TIMEOUT)
		                .setConnectionRequestTimeout(HttpUtilsV3.HTTP_CONNNECT_TIMEOUT)
		                .setStaleConnectionCheckEnabled(true)
		                .build();
			}
			return defaultRequestConfig;
		}
		else{
			return RequestConfig.custom()
	                .setSocketTimeout(connectionTimeout)
	                .setConnectTimeout(connectionTimeout)
	                .setConnectionRequestTimeout(connectionTimeout)
	                .setStaleConnectionCheckEnabled(true)
	                .build();
		}
	}
	
	/***
	 * Build HttpRequest URi
	 * @param url
	 * @param method
	 * @param paramMap
	 * @return
	 */
	protected static java.net.URI buildUri(String url, String method, Map<String, Object> paramMap) {
		
		java.net.URI uri = null;
		URIBuilder uriBuilder = null;
		try {
			
			if ( !StringUtils.isEmpty(method) ) {
				
				url += "/" + method;
			}
			uriBuilder = new URIBuilder(url);
			
			if ( !CollectionUtils.isEmpty(paramMap) ) {
				
				Set<String> keySet = paramMap.keySet();
				for (String key : keySet) {
					Object value = paramMap.get(key);
					if (value != null) {
						uriBuilder.addParameter(key, value.toString());
					}
				}
			}
			uri = uriBuilder.build();
			
		} catch (URISyntaxException e1) {
		}
		
		return uri;
	}
	
	/**
     * 鍙戦�丟et璇锋眰宸ュ叿鏂规硶
     * @Methods Name HttpGet
     * @Create In Dec 30, 2014 By lihongfei
     * @param url
     * @param paramMap
     * @return String
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String HttpGet(String url, Map paramMap) {
    	
    	return HttpUtilsV3.HttpGet(url, null, paramMap);
    }
	
	/**
     * 鍙戦�丟et璇锋眰宸ュ叿鏂规硶
     * @Methods Name HttpGet
     * @Create In Dec 30, 2014 By lihongfei
     * @param url
     * @param method
     * @param paramMap
     * @return String
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String HttpGet(String url, String method, Map paramMap) {
    	
    	return HttpUtilsV3.HttpGet(url, method, paramMap, DEFAULT_ENCODING);
    }
    
    /**
     * 鍙戦�丟et璇锋眰宸ュ叿鏂规硶
     * @Methods Name HttpGet
     * @Create In Dec 30, 2014 By lihongfei
     * @param url
     * @param method
     * @param paramMap
     * @param encoding
     * @return String
     */
    public static String HttpGet(String url, String method, Map paramMap, String encoding) {
    	
    	LOGGER.debug("HttpGet url is {},method is {}, paramMap is {}, encoding is {encoding} ",new Object[]{ url, method, paramMap, encoding});
    	
    	String response = null;
    	
    	CloseableHttpResponse httpResponse = null;
		
        java.net.URI uri = HttpUtilsV3.buildUri(url, method, paramMap);
        
        if ( StringUtils.isEmpty(encoding) ) {
        	
        	encoding = DEFAULT_ENCODING;
        }
		
		if ( uri != null) {
			
			HttpGet getMethod = new HttpGet(uri);
			/*getMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE);*/
			getMethod.addHeader(HttpHeaders.CONTENT_ENCODING, encoding);
			
			// 璁剧疆Http Post鏁版嵁       
	        try {
	        	httpResponse = HttpUtilsV3.fireHttpRestRequest(getMethod);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, encoding);
	        		}
	        	}
	        } catch (BleException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e1});
	        	throw new BleException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(),
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage() );
	        } finally {
	        	
	        	closeConnection(httpResponse);
	        } 
		}
		
        LOGGER.debug("URL is {},method is {},paramMap is {},response is {}",new Object[]{url, method, paramMap, response});
        return response;
    }
    
    
    /**
     * 鍙戦�乸ost璇锋眰宸ュ叿鏂规硶
     * @Methods Name HttpPost
     * @Create In 2014骞�10鏈�28鏃� By wangfei
     * @param url
     * @param method
     * @param paramMap
     * @return String
     */
    public static String HttpPost(String url, String method, Map paramMap) {
    	
    	return HttpUtilsV3.HttpPost(url, method, paramMap, HttpUtilsV3.HTTP_CONNNECT_TIMEOUT);
    }
    
	 public static String HttpPost(String url, String method, Map paramMap,String json) {
	    	
	    	return HttpUtilsV3.HttpPost(url, method, paramMap,json, HttpUtilsV3.HTTP_CONNNECT_TIMEOUT);
	 }
    
    
    /**
     * 鍙戦�乸ost璇锋眰宸ュ叿鏂规硶
     * @Methods Name HttpPost
     * @Create In 2014骞�10鏈�28鏃� By wangfei
     * @param url
     * @param method
     * @param paramMap
     * @param connectionTimeout
     * @return String
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String HttpPost(String url, String method, Map paramMap, int connectionTimeout) {
    	
    	LOGGER.debug("HttpPost url is {},method is {},paramMap is {}",
    			new Object[]{url, method, paramMap});
    	
    	String response = null;
    	
    	CloseableHttpResponse httpResponse = null;
        
        java.net.URI uri = HttpUtilsV3.buildUri(url, method, paramMap);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE);
			
			// 璁剧疆Http Post鏁版嵁       
	        try {
	        	httpResponse = HttpUtilsV3.fireHttpRestRequest(postMethod);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, DEFAULT_ENCODING);
	        		}
	        	}
	        } catch (BleException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e1});
	        	throw new BleException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage() );
	        } finally {
	        	
	        	closeConnection(httpResponse);
	        }
		}
		
        LOGGER.debug("URL is {},method is {},paramMap is {},response is {}",new Object[]{url, method, paramMap, response});
        return response;
		
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String HttpPost(String url, String method, Map paramMap, String json,int connectionTimeout) {
    	
    	LOGGER.debug("HttpPost url is {},method is {},paramMap is {}",
    			new Object[]{url, method, paramMap});
    	
    	String response = null;
    	
    	CloseableHttpResponse httpResponse = null;
        
        java.net.URI uri = HttpUtilsV3.buildUri(url, method, paramMap);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE);
			postMethod.addHeader(HttpHeaders.CONTENT_ENCODING, HttpUtilsV3.DEFAULT_ENCODING);
			StringEntity requestEntity = new StringEntity(json, ContentType.create("plain/text", HttpUtilsV3.DEFAULT_ENCODING));
			requestEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE)); 
			requestEntity.setChunked(true);
			
			
			postMethod.setEntity(requestEntity);
			
			// 璁剧疆Http Post鏁版嵁       
	        try {
	        	httpResponse = HttpUtilsV3.fireHttpRestRequest(postMethod);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, HttpUtilsV3.DEFAULT_ENCODING);
	        		}
	        	}
	        } catch (BleException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, json, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, json, e1});
	        	throw new BleException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage());
	        } finally {
	        	
	        	closeConnection(httpResponse);
	        }
		}
		
        LOGGER.debug("doPost url is {},parajsonjson is {},response is {}",new Object[]{url,json,response});
        return response;
		
    }
    
    /***
     * 鍙戦�乸ost璇锋眰宸ュ叿鏂规硶 , paramMap as requestBody, JSON 
     * @param url
     * @param paramMap
     * @return
     */
    public static String HttpPostByJson(String url, Map<String,String> paramMap) {
    	
    	String response = null;
        
    	CloseableHttpResponse httpResponse = null;
		
        java.net.URI uri = HttpUtilsV3.buildUri(url, null, null);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE);
			postMethod.addHeader(HttpHeaders.CONTENT_ENCODING, HttpUtilsV3.DEFAULT_ENCODING);
			
			if (!CollectionUtils.isEmpty(paramMap)) {

				JSONObject jsonObj2 = null;

				jsonObj2 = JSONObject.fromObject(paramMap);

				if (jsonObj2 != null) {

					StringEntity requestEntity = new StringEntity(jsonObj2.toString(), ContentType.create("plain/text", HttpUtilsV3.DEFAULT_ENCODING));
					requestEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE));
					requestEntity.setChunked(true);
					postMethod.setEntity(requestEntity);
				}
			}
			
			// 璁剧疆Http Post鏁版嵁       
	        try {
	        	httpResponse = HttpUtilsV3.fireHttpRestRequest(postMethod);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, HttpUtilsV3.DEFAULT_ENCODING);
	        		}
	        	}
        		
	        } catch (BleException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, paramMap, e1});
	        	throw new BleException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage() );
	        } finally {
	        	
	        	closeConnection(httpResponse);
	        }
		}
		
        LOGGER.debug("doPost url is {},parajsonjson is {},response is {}",new Object[]{url, paramMap, response});
        return response;
    }
    
    
    /**
     * POST HTTP Request
     * @param url
     * @param json
     * @param connectionTimeout 搴旂敤鑷畾涔塇TTP_CONNECTION_TIMEOUT, 濡傛灉涓�-1, 鍒欏彇榛樿鍊�
     * @return HTTP Response
     */
    public static String doPost(String url, String json, int connectionTimeout) {
    	
    	String response = null;
        
    	CloseableHttpResponse httpResponse = null;
		
        java.net.URI uri = HttpUtilsV3.buildUri(url, null, null);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE);
			//postMethod.addHeader(HttpHeaders.CONTENT_ENCODING, HttpUtilsV3.DEFAULT_ENCODING);
			
			StringEntity requestEntity = new StringEntity(json, ContentType.create(HttpUtilsV3.JSON_CONTENT_TYPE, HttpUtilsV3.DEFAULT_ENCODING));
		//	requestEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE)); 
			requestEntity.setChunked(true);
			
			postMethod.setEntity(requestEntity);
			
			// 璁剧疆Http Post鏁版嵁       
	        try {
	        	httpResponse = HttpUtilsV3.fireHttpRestRequest(postMethod);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, HttpUtilsV3.DEFAULT_ENCODING);
	        		}
	        	}
	        } catch (BleException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, json, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, json, e1});
	        	throw new BleException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage());
	        } finally {
	        	
	        	closeConnection(httpResponse);
	        }
		}
		
        LOGGER.debug("doPost url is {},parajsonjson is {},response is {}",new Object[]{url,json,response});
        return response;
    }
    
    
    
    /**
     * 鎵ц涓�涓狧TTP POST璇锋眰锛岃繑鍥炶姹傚搷搴旂殑HTML
     * @param url  璇锋眰鐨刄RL鍦板潃
     * @param params  璇锋眰鐨勬煡璇㈠弬鏁�,鍙互涓簄ull
     * @return 杩斿洖璇锋眰鍝嶅簲鐨凥TML
     */
    public static String doPost(String url, String json) {
        
    	return doPost(url, json, HttpUtilsV3.HTTP_CONNNECT_TIMEOUT);
    }

    
    /**
     * Check Object 鏄惁涓篘ULL
     * @param target
     * @return
     */
    public static boolean checkNull(Object target) {
    	
        if (target == null || "".equals(target.toString().trim()) || "null".equalsIgnoreCase(target.toString().trim())) {
            return true;
        }
        return false;
    }

    
    /***
     * Do Post HTTPS Request with Cert ignored
     * @param url
     * @param json
     * @return
     */
    public static String doPostHttpsIgnoreCert(String url, String json) {
    	
    	String response = null;
        
    	CloseableHttpResponse httpResponse = null;
		
        java.net.URI uri = HttpUtilsV3.buildUri(url, null, null);
		
		if ( uri != null) {
			
			HttpPost postMethod = new HttpPost(uri);
			postMethod.addHeader(HttpHeaders.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE);
			postMethod.addHeader(HttpHeaders.CONTENT_ENCODING, HttpUtilsV3.DEFAULT_ENCODING);
			
			StringEntity requestEntity = new StringEntity(json, ContentType.create("plain/text", HttpUtilsV3.DEFAULT_ENCODING));
			requestEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, HttpUtilsV3.JSON_CONTENT_TYPE)); 
			requestEntity.setChunked(true);
			
			postMethod.setEntity(requestEntity);
			
			Protocol ptlHttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);   
			String protocolId= "https" + System.currentTimeMillis();
			LOGGER.debug("protocolId", protocolId);
			Protocol.registerProtocol("https", ptlHttps);
			
			// 璁剧疆Http Post鏁版嵁       
	        try {
	        	httpResponse = HttpUtilsV3.fireHttpRestRequest(postMethod);
	        	
	        	StatusLine statusLine = httpResponse.getStatusLine();
	        	if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	        		
	        		HttpEntity entity = httpResponse.getEntity(); 
	        		if ( entity != null) {
	        			response = EntityUtils.toString(entity, HttpUtilsV3.DEFAULT_ENCODING);
	        		}
	        	}
	        } catch (BleException  e) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, json, e});
	        	throw e;
	        } catch (Exception  e1) {
	        	LOGGER.error("HttpGet url is {},paras is {},exception is {}",new Object[]{url, json, e1});
	        	throw new BleException(ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode(), 
	        			ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo() + getRootCause(e1).getMessage() );
	        } finally {
        	
				Protocol.unregisterProtocol("https");
	        	closeConnection(httpResponse);
	        }
		}
		
        LOGGER.debug("doPost url is {},parajsonjson is {},response is {}",new Object[]{url,json,response});
        return response;
    }
    
    
    /** 鎶婃暟缁勬墍鏈夊厓绱犳帓搴忥紝骞舵寜鐓р�滃弬鏁�=鍙傛暟鍊尖�濈殑妯″紡鐢ㄢ��&鈥濆瓧绗︽嫾鎺ユ垚瀛楃涓�
     * @param params 闇�瑕佹帓搴忓苟鍙備笌瀛楃鎷兼帴鐨勫弬鏁扮粍
     * @return 鎷兼帴鍚庡瓧绗︿覆
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//鎷兼帴鏃讹紝涓嶅寘鎷渶鍚庝竴涓�&瀛楃
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /***
     * Force Close HttpClient Connection
     * @param httpResponse
     */
    public static void closeConnection( CloseableHttpResponse httpResponse ){
    	
    	if ( httpResponse != null ) {
    		
    		try {
        		//Force consume HttpResponse Entity
    			EntityUtils.consume(httpResponse.getEntity());
    		} catch (IOException e) {
    			
    		}
    		
    		try {
				httpResponse.close();
    		} catch (IOException e) {
    			
    		}
		}
    	
		THREAD_HTTP_CLENTS.remove();
    	
    }
    
    public static Throwable getRootCause(Throwable t) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(t);
		
		if ( rootCause!= null){
			
			return rootCause;
		}
		else return t;
	}

    public static void main(String[] args){
    	
    	System.out.println(doPost("http://localhost:8080/blgroup-osp/api/sysproxy/mmenus/all",""));
    	
    	System.out.println(doPostHttpsIgnoreCert("https://adt-10010011.adtvpn.ticp.net:8090/app/app_auth","{\"appId\":\"dfdsf\"}"));
    }
}