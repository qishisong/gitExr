package com.blomni.o2o.order.service;

import java.util.Map;

import com.blomni.o2o.order.dto.OrderDto;
import com.blomni.o2o.order.exception.OrderServiceException;

/**
 * 
* @ClassName: OrderGenerateService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zy 
* @date 2017年5月5日 下午8:18:39 
*
 */
public interface OrderGenerateService {
	 Map<String, Object>  orderGenerate(OrderDto dto) throws OrderServiceException;
}
