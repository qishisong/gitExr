package com.blomni.o2o.order.dto;
/**
 * 
* @ClassName: selectQueryOrderDto 
* @Description: TODO(查询订单列表) 
* @author zy 
* @date 2017年5月9日 上午10:21:43 
*
 */

public class SelectQueryOrderDto {
	private String memberId	;//string	Y		
	private String memberToken;	//string	Y		
	private String orderStatus	;//string	N		订单状态（01-待支付 02-待接单 03-待发货 04-待收货 05-待自提 06-交易完成 07-交易关闭 默认返回全部）
	private int pageNo=1	;//int	Y	1	页码
	private int pageSize=10;//	int	Y	30	每页数量
	private String  orderId	;//string	Y		订单编号
	
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
