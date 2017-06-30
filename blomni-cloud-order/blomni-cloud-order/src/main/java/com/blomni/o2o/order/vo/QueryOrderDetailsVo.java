package com.blomni.o2o.order.vo;

import java.util.List;

import com.blomni.o2o.order.dto.BLSCloudOrderGoods;
import com.blomni.o2o.order.dto.BLSCloudShop;

/**
 * 
* @ClassName: QueryOrderDetailsVo 
* @Description: TODO(查询订单详情) 
* @author zy 
* @date 2017年5月9日 下午5:00:09 
*
 */
public class QueryOrderDetailsVo {
	private String orderId;//	string	Y		订单Id
	private String saleNo	;//string	Y		销售单号
	private String id	;//string	Y		
	private String receiverAddress ;//	string	Y		收货／自提地址
	private String receiverName;//	string	Y		收货人
	private String receiverPhone;//	string	Y		收货人电话
	private BLSCloudShop shop	;//[BLSCloudShop]	Y		商户信息
	private int sendType	;//int	Y		配送方式（0-物流 1-自提）
	private int sendTime	;//int	N		配送时间（0-双休日工作日均可 1-双休日 2-工作日）
	private int invoiceType	;//int	Y		发票类型（0-不开发票 1-个人明细）
	private String invoiceImgUrl	;//string	N		小票图片
	private int payType;//	int	N		支付方式（0-在线，待付款状态下不传）
	private String payTime	;//string	N		付款时间（时间戳格式 1493361628011）
	private int orderType	;//int	Y		订单类型（0-实物 1-虚拟）
	private double orderAmount	;//double	Y		商品总额
	private double discountAmount	;//double	Y		优惠金额（如优惠86元，则为86，不是-86，如果为空传0）
	private double payAmount	;//double	Y		订单应（实）付金额
	private double deliveryAmount	;//double	Y		运费
	private String submitOrderDate	;//string	Y		下单时间（时间戳格式 1493361628011）
	private int timeout	;//int	Y		下单超时秒数
	private String orderStatus	;//string	Y		订单状态（01-待支付 02-待接单 03-待发货 04-待收货 05-待自提 06-交易完成 07-交易关闭 不传则返回全部）
	private String orderRemark	;//string	N		备注
	private List<BLSCloudOrderGoods> goodsList	;//list[BLSCloudOrderGoods]	Y		商品列表
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
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
	public String getInvoiceImgUrl() {
		return invoiceImgUrl;
	}
	public void setInvoiceImgUrl(String invoiceImgUrl) {
		this.invoiceImgUrl = invoiceImgUrl;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
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
	public String getSubmitOrderDate() {
		return submitOrderDate;
	}
	public void setSubmitOrderDate(String submitOrderDate) {
		this.submitOrderDate = submitOrderDate;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
