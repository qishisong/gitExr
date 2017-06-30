package com.blomni.o2o.order.threadPool;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TestAsyncTask {
	
	
	@Async("taskAsyncPool")
	public void doTask1(int i) {
		System.out.println("当前的线程名"+i+"--->"+Thread.currentThread().getName());
	}
	
	@Async("taskAsyncPool")
	public void doTask2(int i) {
		System.out.println("当前的线程名"+i+"--->"+Thread.currentThread().getName());
	}
}
