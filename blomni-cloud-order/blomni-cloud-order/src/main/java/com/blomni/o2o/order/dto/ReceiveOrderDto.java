package com.blomni.o2o.order.dto;

public class ReceiveOrderDto {
	private String imgUrl;//小票图片
	private String memberId;//会员id
	private String merchantId;//商户id
	private String orderNo;//订单号
	private String salesSlipNo;//销售单号
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public String getSalesSlipNo() {
		return salesSlipNo;
	}
	public void setSalesSlipNo(String salesSlipNo) {
		this.salesSlipNo = salesSlipNo;
	}
	
}
