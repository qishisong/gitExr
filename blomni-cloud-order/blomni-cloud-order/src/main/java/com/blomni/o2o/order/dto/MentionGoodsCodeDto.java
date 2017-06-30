package com.blomni.o2o.order.dto;

/**
 * @ClassName: MentionGoodsCodeDto 
 * @Description: TODO(提货码信息DTO) 
 * @author xhj
 * @date 2017年5月8日 上午10:29:19
 *
 */
public class MentionGoodsCodeDto {
	
	/** 提货码  */
	private String deliveryCode;
	
	/** 用户ID  */
	private String memberId;
	
	/** 商户ID  */
	private String merchantId;
	
	/** 订单CODE  */
	private String orderNo;
	
	/** 订单Id  */
	private String orderId;
	
	private String memberToken;

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMemberToken() {
		return memberToken;
	}

	public void setMemberToken(String memberToken) {
		this.memberToken = memberToken;
	}
	
}
