package com.blomni.o2o.order.dto;
/**
 * 
* @ClassName: OrderDto 
* @Description: TODO(1607 生成订单（确认订单）) 
* @author zy 
* @date 2017年5月5日 下午8:36:39 
*
 */
public class OrderDto {
	private String memberId	;//string	Y	
	private String memberName	;//string	Y
	private String memberToken	;//string	Y		
	private  BLSCloudOrder order;//	Y		订单
	
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
	public BLSCloudOrder getOrder() {
		return order;
	}
	public void setOrder(BLSCloudOrder order) {
		this.order = order;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	

}
