package com.blomni.o2o.order.service;

import java.util.Map;

import com.blomni.o2o.order.dto.SelectQueryOrderDto;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.vo.QueryOrderDetailsVo;

/**
 * 
* @ClassName: OrderSelectInfoService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zy 
* @date 2017年5月9日 上午10:27:02 
*
 */
public interface OrderSelectInfoService {
	Map<String, Object>	selectQueryOrderList (SelectQueryOrderDto dto)throws OrderServiceException;
	
	QueryOrderDetailsVo	selectQueryOrderDetails (SelectQueryOrderDto dto)throws OrderServiceException;
}
