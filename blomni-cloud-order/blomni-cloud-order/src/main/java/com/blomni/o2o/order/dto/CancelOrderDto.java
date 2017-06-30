package com.blomni.o2o.order.dto;

public class CancelOrderDto {
	
	private String memberId;
	private String memberToken;
	private String orderId;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberToken() {
		return memberToken;
	}
	public void setMemberToken(String memberToken) {
		this.memberToken = memberToken;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	
}
