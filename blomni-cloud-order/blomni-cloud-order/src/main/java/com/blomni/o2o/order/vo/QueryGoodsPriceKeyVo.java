package com.blomni.o2o.order.vo;


/**
 * 
* @ClassName: QueryGoodsPriceKeyVo 
* @Description: TODO(查询商品价格) 
* @author zy 
* @date 2017年5月11日 上午10:30:29 
*
 */
public class QueryGoodsPriceKeyVo {
	private double originalPrice;//原价
	
	private double sellingPrice;//销售价

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	
	
}
