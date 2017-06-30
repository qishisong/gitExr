package com.blomni.o2o.order.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.service.SingleMemberService;
import com.blomni.o2o.order.util.HttpUtilsV3;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.vo.SingleMemberResVo;

@Service
public class SingleMemberServiceImpl implements SingleMemberService{
	
	private static Logger logs = LoggerFactory.getLogger(SingleMemberServiceImpl.class);
	
	//@Value("${single.memberId.url}")
	//private String SINGLE_MEMBERURL;
	
	@Override
	public SingleMemberResVo getSingleMemberIdByMemberToken(String memberToken)throws Exception {
		logs.debug("用户监权接口begin:memberToken={}",memberToken);
		String result;
		Map<String,String> singleMap=new HashMap<String, String>();
		singleMap.put("member_token", memberToken);
		try {
			result=HttpUtilsV3.doPost("http://10.201.128.125:7080/member/baseinfo/jn/getSingleMemberIdByMemberToken.htm", JSONObject.toJSONString(singleMap));
		} catch (Exception e) {
			logs.error(R.OrderErrorEnum.ERROR_TIMEOUT.getLabel(),e);
			throw new Exception(R.OrderErrorEnum.ERROR_TIMEOUT.getLabel());
		}
		//解析json得到对象
		SingleMemberResVo vo= JSONObject.parseObject(result,SingleMemberResVo.class);
		logs.debug("用户监权接口end:result={}",result);
		return vo;
	}
	
	/**
	 * title:用户鉴权
	 */
	@Override
	public void getSingleMemberIdByMemberToken(String memberToken ,String memberId)throws OrderServiceException {
		
		try {
			SingleMemberResVo singleMmemberId = getSingleMemberIdByMemberToken(memberToken);
			if (singleMmemberId == null || singleMmemberId.getObj() == null 
					|| singleMmemberId.getObj().getMemberId() == null 
					|| !singleMmemberId.getObj().getMemberId().equals(memberId)) {
				throw new OrderServiceException(R.ReturnCodeEnum.code_userSingleFail.getValue(),
												R.ReturnCodeEnum.code_userSingleFail.getLabel());

			}
		} catch (Exception e) {
			logs.error(e.getMessage(), e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_userSingleFail.getValue(),
											R.ReturnCodeEnum.code_userSingleFail.getLabel());
			
		}
	}
	
}
