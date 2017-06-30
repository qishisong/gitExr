package com.blomni.o2o.order.vo;

import java.util.List;

import com.blomni.o2o.order.dto.BLSCloudOrderGoods;
import com.blomni.o2o.order.dto.BLSCloudShop;
/**
 * 
* @ClassName: BLSCloudOrderVo 
* @Description: TODO(查询订单列表) 
* @author zy 
* @date 2017年5月9日 上午11:04:59 
*
 */
public class BLSCloudOrderVo {
	private String id;//	string	Y		订单详情id
	private String orderId;//	string	Y		订单Id
	private BLSCloudShop shop;//	[BLSCloudShop]	Y		商铺信息
	private int orderType;//	int	Y		订单类型（0-实物 1-虚拟）
	private double orderAmount;//	double	Y		商品总额
	private double discountAmount;//	double	Y		优惠金额（如优惠86元，则为86，不是-86，如果为空传0）
	private double payAmount;//	double	Y		订单应（实）付金额
	private double deliveryAmount;//	double	Y		运费
	private String orderStatus;//	string	Y		订单状态（01-待支付 02-待接单 03-待发货 04-待收货 05-待自提 06-交易完成 07-交易关闭）
	private  List<BLSCloudOrderGoods> goodsList;//	list[BLSCloudOrderGoods]	Y		商品列表
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BLSCloudShop getShop() {
		return shop;
	}
	public void setShop(BLSCloudShop shop) {
		this.shop = shop;
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
