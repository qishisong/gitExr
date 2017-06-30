package com.blomni.o2o.order.dto;
/**
 * 
* @ClassName: BLSCloudAddress 
* @Description: TODO(地址) 
* @author zy 
* @date 2017年5月4日 下午8:04:27 
*
 */
public class BLSCloudAddress {
	
	private int defaultFlag	;//int	Y	0-不是默认 1-是默认	默认地址
	private String provinceId;//	string	Y		省id
	private String provinceName	;//string	Y		省名
	private String cityId;//	string	Y		城市id
	private String cityName	;//string	Y		城市名
	private String districtId	;//string	Y		区id
	private String districtName	;//string	Y		区名
	private String address;//	string	Y	友谊大厦	地址
	private String zipcode	;//string	N		邮政编码
	private String receiverName;//	string	Y		收货人
	private String receiverPhone;//	string	Y		收货人手机
	
	private String addressId;//id
	
	private int receiptType=0;
	
	public int getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictId() {
		return districtId;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public int getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(int receiptType) {
		this.receiptType = receiptType;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	
	
	
}
