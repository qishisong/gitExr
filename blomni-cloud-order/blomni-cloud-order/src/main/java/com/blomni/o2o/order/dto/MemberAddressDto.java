package com.blomni.o2o.order.dto;
/**
 * 
* @ClassName: MemberAddressDto 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zy 
* @date 2017年5月5日 下午1:47:03 
*
 */
public class MemberAddressDto {
	private String memberId;//	string	Y		
	private String memberToken;//	string	Y		
	private String addressId;//	string	Y
	private String receiptType = "0";
	
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
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
	
}
