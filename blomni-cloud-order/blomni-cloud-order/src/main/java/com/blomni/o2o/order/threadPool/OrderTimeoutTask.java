package com.blomni.o2o.order.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.blomni.o2o.order.controller.OrderBasicInfoController;
import com.blomni.o2o.order.service.OrderBasicInfoService;

@Component
public class OrderTimeoutTask {
	
	private static Logger logs = LoggerFactory.getLogger(OrderBasicInfoController.class);
	
	@Autowired
	private OrderBasicInfoService orderBasicInfoService;
	
	@Async("taskAsyncPool")
	public void executeBeforePaymentTimeoutTask() {
		logs.info("处理待支付超过时效的订单 begin");
		logs.info("当前执行的线程：={}" , Thread.currentThread().getName());
		
		orderBasicInfoService.HandlePaymentTimeoutListByOrderStatus();
		
		//TODO 关闭商户超时未接单的订单
		
		logs.info("处理待支付超过时效的订单 End");
	}
	
	@Async("taskAsyncPool")
	public void executeTimeOutWaitOrderTask() {
		logs.info("处理商户超时未接单的订单 begin");
		logs.info("当前执行的线程：={}" , Thread.currentThread().getName());
		
		orderBasicInfoService.timeOutWaitOrderJob();
		
		
		logs.info("处理商户超时未接单的订单 End");
	}
	
	

}
