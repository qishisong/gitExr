package com.blomni.o2o.order.service;

import java.util.List;

import com.blomni.o2o.order.dto.CancelOrderDto;
import com.blomni.o2o.order.dto.GetCountForOrderStateDto;
import com.blomni.o2o.order.dto.QueryOrderListDto;
import com.blomni.o2o.order.dto.QueryMerchantOrderDetailsDto;
import com.blomni.o2o.order.dto.ReceiveOrderDto;
import com.blomni.o2o.order.dto.UpdateOrderInfoDto;
import com.blomni.o2o.order.dto.UpdateOrderStateDto;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.vo.GetCountForOrderStateVo;
import com.blomni.o2o.order.vo.QueryOrderListVo;
import com.github.pagehelper.PageInfo;
import com.blomni.o2o.order.vo.MerchantOrderDetailsVo;

public interface OrderBasicInfoService {

	int receiveOrder(ReceiveOrderDto receiveOrderDto)throws OrderServiceException;

	int reqNewCancelOrderState(CancelOrderDto dto)throws OrderServiceException;
	
	List<GetCountForOrderStateVo> getCountForOrderState(GetCountForOrderStateDto dto);
	
	PageInfo getOrderList(QueryOrderListDto dto);
	
	MerchantOrderDetailsVo  queryMerchantOrderDetailsByOrderNo(QueryMerchantOrderDetailsDto dto);

	int updateOrderState(UpdateOrderStateDto dto)throws OrderServiceException;

	int updateOrderInfo(UpdateOrderInfoDto dto)throws OrderServiceException;
	
	String HandlePaymentTimeoutListByOrderStatus();

	void timeOutWaitOrderJob();

}
