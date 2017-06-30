package com.blomni.o2o.order.util;



public class BaseResponse {
	private String resCode;
	private String msg;

	
	
	
	public BaseResponse() {
		
	}


	public BaseResponse(String resCode,String msg){
		this.resCode=resCode;
		this.msg=msg;
		
	}

	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
