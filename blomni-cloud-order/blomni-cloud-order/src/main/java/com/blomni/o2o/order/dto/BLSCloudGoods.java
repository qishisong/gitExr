package com.blomni.o2o.order.dto;
/**
 * 
* @ClassName: BLSCloudGoods 
* @Description: TODO(商品信息) 
* @author zy 
* @date 2017年5月5日 下午8:45:05 
*
 */
public class BLSCloudGoods {
	
	private String productId;//	string	Y		商品信息
	private String goodsSalesName;//	string	Y		商品缩略名
	private String goodsStandaName	;//string	Y		商品标准名
	private double goodsPrice;//	double	Y		销售价
	private double marketPrice	;//double	Y		市场价
	private String imageUrl;//	string	Y		图片
	private double weight	;//double	Y		重量（kg）weight
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getGoodsSalesName() {
		return goodsSalesName;
	}
	public void setGoodsSalesName(String goodsSalesName) {
		this.goodsSalesName = goodsSalesName;
	}
	public String getGoodsStandaName() {
		return goodsStandaName;
	}
	public void setGoodsStandaName(String goodsStandaName) {
		this.goodsStandaName = goodsStandaName;
	}
	public double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "BLSCloudGoods [productId=" + productId + ", goodsSalesName=" + goodsSalesName + ", goodsStandaName="
				+ goodsStandaName + ", goodsPrice=" + goodsPrice + ", marketPrice=" + marketPrice + ", imageUrl="
				+ imageUrl + ", weight=" + weight + "]";
	}
	
	
}
