package com.blomni.o2o.order.serviceImpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;

@Service 
public class TestOrderServiceImpl {
	
	@Autowired
	RestTemplate restTemplate ;
	
	final String SERVICE_NAME = "blomni-cloud-order";

//    @HystrixCommand(fallbackMethod = "fallbackSearchAll")
	/**
	 * 调用其他中台GET服务
	 */
    public void invokeTrdGet() {
    	ResponseEntity<String> entity = restTemplate.getForEntity("http://10.201.128.126:19080/blgroup-osp-site-api/site/store/all", String.class);
//    	ResponseEntity entity = restTemplate.getForEntity("http://localhost:8081/blomni-o2o/oms/orders", JSONObject.class);
    	System.out.println(entity);
    }
    
    /**
     * 调用服务注册中心服务
     */
    public void invokeRegistCenter(){
    	//List<Order> entity = restTemplate.getForObject("http://" + SERVICE_NAME + "/oms/orders", List.class);
    	//System.out.println(entity);
    }
    
    /**
     * 调用其他中台POST服务
     */
    public void invokeThrdPost(){
    	HashMap<String, String> param =  Maps.newHashMap();
    	param.put("rangeCode", "15");
    	param.put("specialService","1");
    	param.put("mapType","gcj-02");
    	String entity = restTemplate.postForObject("http://10.201.128.126:19080/blgroup-osp-site-api/site/nsupplyer/range", param, String.class);
    	System.out.println(entity);
    }

}
