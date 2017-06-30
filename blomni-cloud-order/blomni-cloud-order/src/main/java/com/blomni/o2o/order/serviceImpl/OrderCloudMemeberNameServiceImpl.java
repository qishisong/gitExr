package com.blomni.o2o.order.serviceImpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.service.OrderCloudMemeberNameService;
/**
 * 
* @ClassName: OrderCloudMemeberNameServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zy 
* @date 2017年5月9日 下午9:25:31 
*
 */
@Service
public class OrderCloudMemeberNameServiceImpl  implements OrderCloudMemeberNameService{
	
	@Autowired
	RestTemplate restTemplate ;
	
	/**
	 * title:查询会员昵称
	 * zy
	 * 二〇一七年五月九日 21:26:09
	 */
	@Override
	public String getmemberNickById(String memberId) {
		// TODO Auto-generated method stub
		String url="http://10.201.129.158:7310/cloud/cloudMember/getmemberNickById.htm";
		String memberName=null;
		HashMap<String, Object>map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		String respon  =restTemplate.postForObject(url, map,String.class);
		JSONObject resultJson = JSONObject.parseObject(respon);
		
		if(resultJson.containsKey("memberName")){
			memberName=resultJson.getJSONObject("obj").get("memberName").toString();
		}
		return memberName;
	}

}
