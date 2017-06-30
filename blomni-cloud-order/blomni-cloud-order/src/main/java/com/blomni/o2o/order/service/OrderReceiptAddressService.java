package com.blomni.o2o.order.service;

import java.util.List;

import com.blomni.o2o.order.entity.OrderReceiptAddress;
import com.blomni.o2o.order.exception.OrderServiceException;

/**
 * 
* @ClassName: OrderReceiptAddressService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zy 
* @date 2017年5月5日 下午2:24:21 
*
 */
public interface OrderReceiptAddressService {
	int countByExample(OrderReceiptAddress record)throws OrderServiceException;

    int insertSelective(OrderReceiptAddress record)throws OrderServiceException;

    List<OrderReceiptAddress> selectByOrderReceiptAddress(OrderReceiptAddress record)throws OrderServiceException;

    OrderReceiptAddress selectByPrimaryKey(String receiptId)throws OrderServiceException;

    int updateByPrimaryKeySelective(OrderReceiptAddress record)throws OrderServiceException;

    int delectAddress(OrderReceiptAddress record)throws OrderServiceException;
    
    int updateMerchantAddress(OrderReceiptAddress record) throws OrderServiceException;
}
