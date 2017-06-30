package com.blomni.o2o.order.service;

import com.blomni.o2o.order.entity.CloudDrawback;
import com.blomni.o2o.order.exception.OrderServiceException;

/**
 * 
* @ClassName: OrderAutographService 
* @Description: TODO(生成签名) 
* @author zy 
* @date 2017年5月2日 上午10:24:12 
*
 */
public interface OrderAutographService {
	/**
	 * title:回调生成签名
	 * 2017年5月2日10:33:16
	 * 
	 */
	String callbackAutograph(String orderNo,String tranDate)throws OrderServiceException;
	
	/**
	 * title:退款生成签名
	 * 2017年5月2日10:33:16
	 * 
	 */
	String refundAutograph(CloudDrawback back)throws OrderServiceException;
}
