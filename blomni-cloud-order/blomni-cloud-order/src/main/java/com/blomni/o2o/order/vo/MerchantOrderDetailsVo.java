package com.blomni.o2o.order.vo;

import java.util.List;

public class MerchantOrderDetailsVo {
	private String carriageMoney;
	private String consigneeAddress;
	private String consigneeName;
	private String consigneePhone;
	private String favorableMoney;
	private List<MerchantOrderDetailsGoodsVo> goodsList;

	private String orderAmt;
	private List<MerchantOrderDetailsFlowVo> orderFlowList;

	private String orderNo;
	private String orderState;
	private String outTime;
	private String receiptUrl;
	private String salesNumber;
	private String shouldMoney;
	private String distributionMode;
	
	
	public String getDistributionMode() {
		return distributionMode;
	}
	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}
	public String getCarriageMoney() {
		return carriageMoney;
	}
	public void setCarriageMoney(String carriageMoney) {
		this.carriageMoney = carriageMoney;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public String getFavorableMoney() {
		return favorableMoney;
	}
	public void setFavorableMoney(String favorableMoney) {
		this.favorableMoney = favorableMoney;
	}
	public List<MerchantOrderDetailsGoodsVo> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<MerchantOrderDetailsGoodsVo> goodsList) {
		this.goodsList = goodsList;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public List<MerchantOrderDetailsFlowVo> getOrderFlowList() {
		return orderFlowList;
	}
	public void setOrderFlowList(List<MerchantOrderDetailsFlowVo> orderFlowList) {
		this.orderFlowList = orderFlowList;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getReceiptUrl() {
		return receiptUrl;
	}
	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}
	public String getSalesNumber() {
		return salesNumber;
	}
	public void setSalesNumber(String salesNumber) {
		this.salesNumber = salesNumber;
	}
	public String getShouldMoney() {
		return shouldMoney;
	}
	public void setShouldMoney(String shouldMoney) {
		this.shouldMoney = shouldMoney;
	}
	
	
	
}
