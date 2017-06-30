package com.blomni.o2o.order.entity;

import java.util.Date;

public class OrderHistoryConsultGoods {
    private String id;

    private String memberId;

    private String merchantId;

    private String goodsId;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private String delFlag;

    private String spare1;

    private String spare2;

    private String spare3;

    private Integer spare4;

    private Double spare5;

    private Date spare6;

    private Date spare7;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
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

    public Integer getSpare4() {
        return spare4;
    }

    public void setSpare4(Integer spare4) {
        this.spare4 = spare4;
    }

    public Double getSpare5() {
        return spare5;
    }

    public void setSpare5(Double spare5) {
        this.spare5 = spare5;
    }

    public Date getSpare6() {
        return spare6;
    }

    public void setSpare6(Date spare6) {
        this.spare6 = spare6;
    }

    public Date getSpare7() {
        return spare7;
    }

    public void setSpare7(Date spare7) {
        this.spare7 = spare7;
    }
}