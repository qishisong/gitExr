package com.blomni.o2o.order.entity;


/**
 * title:退款撤销实体
 * @author zy
 * 2017年5月3日14:50:25
 *
 */
public class CloudDrawback {
	private String orderNo;//原支付订单号
	private String mamberId;//会员id
	private String tranDate;//原支付交易日期  20150918
	private String remarks;//"Y"退款  否则不传
	private Integer refundAmt;//单位：分,退款必传，撤销不需传
	private String tranType;//交易类型此接口TranType填值范围：0401退款，0403撤销
	private String url;//商户后台通知地址
	private String state;
	
	private String   merId;
	private String  merOrderNo;
	   
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMamberId() {
		return mamberId;
	}
	public void setMamberId(String mamberId) {
		this.mamberId = mamberId;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(Integer refundAmt) {
		this.refundAmt = refundAmt;
	}
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getMerOrderNo() {
		return merOrderNo;
	}
	public void setMerOrderNo(String merOrderNo) {
		this.merOrderNo = merOrderNo;
	}
	
	
	
}
