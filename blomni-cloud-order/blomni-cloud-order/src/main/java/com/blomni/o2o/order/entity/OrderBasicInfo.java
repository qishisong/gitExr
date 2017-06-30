package com.blomni.o2o.order.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderBasicInfo {
    private String id;

    private String orderCode;

    private BigDecimal orderAmt;

    private String orderState;

    private String memberName;

    private String memberId;

    private String orderSource;

    private String orderExternalNo;

    private String orderType;

    private String orderExceptionFlag;

    private Date outTime;

    private String buyerRemark;

    private BigDecimal shouldMoney;

    private String deliveryTime;

    private BigDecimal favorableMoney;

    private String consigneeAddress;

    private String consigneePhone;

    private String consigneeName;

    private BigDecimal carriageMoney;

    private String salesNumber;

    private String receiptUrl;

    private String sellerRemark;

    private String distributionMode;

    private String closingReason;

    private String updateBy;

    private Date updateDate;

    private String createBy;

    private Date createDate;

    private String delFlag;

    private String spare1;

    private String spare2;

    private String spare3;
    
    private String invoiceType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState == null ? null : orderState.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource == null ? null : orderSource.trim();
    }

    public String getOrderExternalNo() {
        return orderExternalNo;
    }

    public void setOrderExternalNo(String orderExternalNo) {
        this.orderExternalNo = orderExternalNo == null ? null : orderExternalNo.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getOrderExceptionFlag() {
        return orderExceptionFlag;
    }

    public void setOrderExceptionFlag(String orderExceptionFlag) {
        this.orderExceptionFlag = orderExceptionFlag == null ? null : orderExceptionFlag.trim();
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark == null ? null : buyerRemark.trim();
    }

    public BigDecimal getShouldMoney() {
        return shouldMoney;
    }

    public void setShouldMoney(BigDecimal shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime == null ? null : deliveryTime.trim();
    }

    public BigDecimal getFavorableMoney() {
        return favorableMoney;
    }

    public void setFavorableMoney(BigDecimal favorableMoney) {
        this.favorableMoney = favorableMoney;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress == null ? null : consigneeAddress.trim();
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone == null ? null : consigneePhone.trim();
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName == null ? null : consigneeName.trim();
    }

    public BigDecimal getCarriageMoney() {
        return carriageMoney;
    }

    public void setCarriageMoney(BigDecimal carriageMoney) {
        this.carriageMoney = carriageMoney;
    }

    public String getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(String salesNumber) {
        this.salesNumber = salesNumber == null ? null : salesNumber.trim();
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl == null ? null : receiptUrl.trim();
    }

    public String getSellerRemark() {
        return sellerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark == null ? null : sellerRemark.trim();
    }

    public String getDistributionMode() {
        return distributionMode;
    }

    public void setDistributionMode(String distributionMode) {
        this.distributionMode = distributionMode == null ? null : distributionMode.trim();
    }

    public String getClosingReason() {
        return closingReason;
    }

    public void setClosingReason(String closingReason) {
        this.closingReason = closingReason == null ? null : closingReason.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2 == null ? null : spare2.trim();
    }

    public String getSpare3() {
        return spare3;
    }

    public void setSpare3(String spare3) {
        this.spare3 = spare3 == null ? null : spare3.trim();
    }

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType== null ? null : invoiceType.trim();
	}
    
    
}