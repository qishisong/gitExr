package com.blomni.o2o.order.entity;

import java.util.Date;

public class OrderReceiptAddress {
	private String id;

    private String receiptName;

    private String receiptPhone;

    private String receiptRegion;

    private String receiptProvinceId;

    private String receiptProvinceName;

    private String receiptCityId;

    private String receiptCityName;

    private String receiptAreaId;

    private String receiptAreaName;

    private String receiptDetailAddress;

    private String receiptType;

    private String identityId;

    private String isDefault;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String delFlag;

    private String postCode;

    private String spare;

    private String spare1;

    private String spare2;

    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName == null ? null : receiptName.trim();
    }

    public String getReceiptPhone() {
        return receiptPhone;
    }

    public void setReceiptPhone(String receiptPhone) {
        this.receiptPhone = receiptPhone == null ? null : receiptPhone.trim();
    }

    public String getReceiptRegion() {
        return receiptRegion;
    }

    public void setReceiptRegion(String receiptRegion) {
        this.receiptRegion = receiptRegion == null ? null : receiptRegion.trim();
    }

    public String getReceiptProvinceId() {
        return receiptProvinceId;
    }

    public void setReceiptProvinceId(String receiptProvinceId) {
        this.receiptProvinceId = receiptProvinceId == null ? null : receiptProvinceId.trim();
    }

    public String getReceiptProvinceName() {
        return receiptProvinceName;
    }

    public void setReceiptProvinceName(String receiptProvinceName) {
        this.receiptProvinceName = receiptProvinceName == null ? null : receiptProvinceName.trim();
    }

    public String getReceiptCityId() {
        return receiptCityId;
    }

    public void setReceiptCityId(String receiptCityId) {
        this.receiptCityId = receiptCityId == null ? null : receiptCityId.trim();
    }

    public String getReceiptCityName() {
        return receiptCityName;
    }

    public void setReceiptCityName(String receiptCityName) {
        this.receiptCityName = receiptCityName == null ? null : receiptCityName.trim();
    }

    public String getReceiptAreaId() {
        return receiptAreaId;
    }

    public void setReceiptAreaId(String receiptAreaId) {
        this.receiptAreaId = receiptAreaId == null ? null : receiptAreaId.trim();
    }

    public String getReceiptAreaName() {
        return receiptAreaName;
    }

    public void setReceiptAreaName(String receiptAreaName) {
        this.receiptAreaName = receiptAreaName == null ? null : receiptAreaName.trim();
    }

    public String getReceiptDetailAddress() {
        return receiptDetailAddress;
    }

    public void setReceiptDetailAddress(String receiptDetailAddress) {
        this.receiptDetailAddress = receiptDetailAddress == null ? null : receiptDetailAddress.trim();
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType == null ? null : receiptType.trim();
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId == null ? null : identityId.trim();
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare == null ? null : spare.trim();
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
}