package com.blomni.o2o.order.service;

import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.vo.SingleMemberResVo;

public interface SingleMemberService {
	public SingleMemberResVo getSingleMemberIdByMemberToken(String memberToken)throws Exception;
	
	public void getSingleMemberIdByMemberToken(String memberToken ,String memberId)throws OrderServiceException;
}