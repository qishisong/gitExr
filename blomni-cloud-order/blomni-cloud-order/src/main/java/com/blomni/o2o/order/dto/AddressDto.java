package com.blomni.o2o.order.dto;
/**
 * 
* @ClassName: AddressDto 
* @Description: TODO(地址) 
* @author zy 
* @date 2017年5月4日 下午8:01:50 
*
 */
public class AddressDto {
	private String memberId;//	string	Y		
	private String memberToken;//	string	Y		
	private BLSCloudAddress address;//	[BLSCloudAddress]	Y		地址信息
	
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
	public BLSCloudAddress getAddress() {
		return address;
	}
	public void setAddress(BLSCloudAddress address) {
		this.address = address;
	}
	
	
}
