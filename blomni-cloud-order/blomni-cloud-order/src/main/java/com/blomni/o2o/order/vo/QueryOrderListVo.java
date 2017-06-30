package com.blomni.o2o.order.vo;

import java.util.List;

public class QueryOrderListVo {

	private String memberId;

	private String memberName;

	private String orderNo;

	private String sellerRemark;

	private String shouldMoney;

	private String orderState;

	private String timeOut;

	private String totalGoodsNum;

	private String lowerOrderTime;

	private String buyerRemark;

	private String orderId;

	private List<GoodVo> goodslist;

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSellerRemark() {
		return sellerRemark;
	}

	public void setSellerRemark(String sellerRemark) {
		this.sellerRemark = sellerRemark;
	}

	public String getShouldMoney() {
		return shouldMoney;
	}

	public void setShouldMoney(String shouldMoney) {
		this.shouldMoney = shouldMoney;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getTotalGoodsNum() {
		return totalGoodsNum;
	}

	public void setTotalGoodsNum(String totalGoodsNum) {
		this.totalGoodsNum = totalGoodsNum;
	}

	public String getLowerOrderTime() {
		return lowerOrderTime;
	}

	public void setLowerOrderTime(String lowerOrderTime) {
		this.lowerOrderTime = lowerOrderTime;
	}

	public String getBuyerRemark() {
		return buyerRemark;
	}

	public void setBuyerRemark(String buyerRemark) {
		this.buyerRemark = buyerRemark;
	}

	public List<GoodVo> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(List<GoodVo> goodslist) {
		this.goodslist = goodslist;
	}

}
