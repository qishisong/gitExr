package com.blomni.o2o.order.dto;

import java.util.List;

/**
 * 
* @ClassName: BLSCloudOrder 
* @Description: TODO(创建订单实体) 
* @author zy 
* @date 2017年5月5日 下午8:38:25 
*
 */
public class BLSCloudOrder {
	private String addressId;//	string	Y		地址ID
	private BLSCloudShop shop;//	[BLSCloudShop]	Y		商铺信息
	private  int sendType;//	int	Y		配送方式（0-物流 1-自提）
	private  int sendTime;//	int	N		配送时间（0-双休日工作日均可 1-双休日 2-工作日）
	private  int invoiceType	;//int	Y		发票类型（0-不开发票 1-个人明细）
	private  int payType	;//int	Y		支付方式（0-在线，待付款状态下不传）
	private  double orderAmount;//	double	Y		商品总额
	private  double discountAmount;//	double	Y		优惠金额（如优惠86元，则为86，不是-86，如果为空传0）
	private  double payAmount	;//double	Y		订单应（实）付金额
	private  double deliveryAmount;//	double	Y		运费
	private  String orderRemark	;//string	N		备注
	private  List<BLSCloudOrderGoods> goodsList;//	list[BLSCloudOrderGoods]	Y		商品列表
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public BLSCloudShop getShop() {
		return shop;
	}
	public void setShop(BLSCloudShop shop) {
		this.shop = shop;
	}
	public int getSendType() {
		return sendType;
	}
	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
	public int getSendTime() {
		return sendTime;
	}
	public void setSendTime(int sendTime) {
		this.sendTime = sendTime;
	}
	public int getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	public double getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
	public List<BLSCloudOrderGoods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<BLSCloudOrderGoods> goodsList) {
		this.goodsList = goodsList;
	}
	@Override
	public String toString() {
		return "BLSCloudOrder [addressId=" + addressId + ", shop=" + shop + ", sendType=" + sendType + ", sendTime="
				+ sendTime + ", invoiceType=" + invoiceType + ", payType=" + payType + ", orderAmount=" + orderAmount
				+ ", discountAmount=" + discountAmount + ", payAmount=" + payAmount + ", deliveryAmount="
				+ deliveryAmount + ", orderRemark=" + orderRemark + ", goodsList=" + goodsList + "]";
	}
	
	
}
