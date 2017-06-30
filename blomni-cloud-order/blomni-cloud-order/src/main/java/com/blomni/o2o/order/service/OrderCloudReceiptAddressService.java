package com.blomni.o2o.order.service;

import java.util.List;
import java.util.Map;

import com.blomni.o2o.order.dto.AddressDto;
import com.blomni.o2o.order.dto.MemberAddressDto;
import com.blomni.o2o.order.entity.OrderFlowInfo;
import com.blomni.o2o.order.entity.OrderReceiptAddress;
import com.blomni.o2o.order.exception.OrderServiceException;
/**
 * 
* @ClassName: OrderReceiptAddressService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zy 
* @date 2017年5月4日 下午7:18:27 
*
 */
public interface OrderCloudReceiptAddressService{
	 	
	    
	    int updateByIsDefaultAddress(OrderReceiptAddress record)throws OrderServiceException;
	    
	    String insertAddress(AddressDto vo)throws OrderServiceException;
	    
	    Map<String, Object>  selectCloudMemberAddress(MemberAddressDto dto)throws OrderServiceException;
	    
	    int  updateAddress(AddressDto vo)throws OrderServiceException;
	    
	    int delectMemberAddress(MemberAddressDto dto) throws OrderServiceException;

		int updateByIsDefaultAddressMemberId(String id) throws OrderServiceException;
	    
}
