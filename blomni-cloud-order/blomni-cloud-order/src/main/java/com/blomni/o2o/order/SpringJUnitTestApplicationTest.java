package com.blomni.o2o.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.blomni.o2o.order.dto.CancelOrderDto;
import com.blomni.o2o.order.dto.UpdateOrderInfoDto;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.service.OrderBasicInfoService;
import com.blomni.o2o.order.threadPool.TestAsyncTask;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = OrderApplication.class)  
@WebAppConfiguration  
public class SpringJUnitTestApplicationTest {

	@Autowired
	private OrderBasicInfoService orderBasicInfoService;
	
	@Autowired
	private TestAsyncTask testAsyncTask;
	
	@Test
	public void updateStatus(){
		testAsyncTask.doTask1(1);
		testAsyncTask.doTask2(2);
	}
	
}
