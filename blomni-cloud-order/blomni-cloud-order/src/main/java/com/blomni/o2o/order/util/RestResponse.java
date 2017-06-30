package com.blomni.o2o.order.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestResponse<T> {
private String resCode;
	
	@JsonProperty("msg")
	private String message;

	@JsonProperty("obj")
	private T restObject;

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getRestObject() {
		return restObject;
	}

	public void setRestObject(T restObject) {
		this.restObject = restObject;
	}
	
	
}
