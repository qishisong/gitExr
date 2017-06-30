package com.blomni.o2o.order.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.service.QueryGoodsPriceKeyService;
import com.blomni.o2o.order.vo.QueryGoodsPriceKey;
import com.blomni.o2o.order.vo.QueryGoodsPriceKeyVo;
/**
 * 
* @ClassName: QueryGoodsPriceKeyServiceImpl 
* @Description: TODO("查询商品价格") 
* @author zy 
* @date 2017年5月11日 上午11:26:17 
*
 */
@Service
public class QueryGoodsPriceKeyServiceImpl implements QueryGoodsPriceKeyService{
	
	private static Logger logs=LoggerFactory.getLogger(QueryGoodsPriceKeyServiceImpl.class);
	@Autowired
	RestTemplate restTemplate ;
	
	/**
	 * title:查询商品价格
	 * 2017-5-11 11:29:14
	 * zy
	 */
	@Override
	public QueryGoodsPriceKeyVo queryGoodsPriceKey(String goodsCommId) {
		logs.info("QueryGoodsPriceKeyServiceImpl.queryGoodsPriceKey=====START={}"+goodsCommId);
		// TODO Auto-generated method stub
		QueryGoodsPriceKeyVo vo=null;
		String url="http://10.201.129.158:7410/goods/queryGoods/queryGoodsPriceKey.htm";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("goodsCommId", goodsCommId);
			String result =restTemplate.postForObject(url,map,String.class);
			QueryGoodsPriceKey query=JSONObject.parseObject(result,QueryGoodsPriceKey.class);
			//originalPrice
			if(null!=query.getObj()){
				vo=new QueryGoodsPriceKeyVo();
				vo.setOriginalPrice(query.getObj().getQueryGoods().getOriginalPrice());
				vo.setSellingPrice(query.getObj().getQueryGoods().getSellingPrice());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("queryGoodsPriceKey{}",e);
		}
		logs.info("QueryGoodsPriceKeyServiceImpl.queryGoodsPriceKey=====END={}"+JSONObject.toJSONString(vo));
		
		return vo;
	}
	
	

}
