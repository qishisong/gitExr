package com.blomni.o2o.order.dto;
/**
 * 
* @ClassName: BLSCloudShop 
* @Description: TODO(商铺信息) 
* @author zy 
* @date 2017年5月5日 下午8:41:09 
*
 */
public class BLSCloudShop {
	private String storeCode;//	string	Y		门店编码
	private String storeType;//	string	Y		业态
	private String storeName;//	string	Y		门店名称
	private String shopCode	;//string	Y		商户编码
	private String shopName	;//string	Y		商户名称
	private String logoImgUrl;//	string	Y		商户logo
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getLogoImgUrl() {
		return logoImgUrl;
	}
	public void setLogoImgUrl(String logoImgUrl) {
		this.logoImgUrl = logoImgUrl;
	}
	@Override
	public String toString() {
		return "BLSCloudShop [storeCode=" + storeCode + ", storeType=" + storeType + ", storeName=" + storeName
				+ ", shopCode=" + shopCode + ", shopName=" + shopName + ", logoImgUrl=" + logoImgUrl + "]";
	}
	
	
}
