package com.blomni.o2o.order.dto;

import java.util.List;

/**
 * 
* @ClassName: BLSCloudOrderGoods 
* @Description: TODO(商品列表) 
* @author zy 
* @date 2017年5月5日 下午8:43:11 
*
 */
public class BLSCloudOrderGoods {
	private BLSCloudGoods goods	;//[BLSCloudGoods]	Y		商品信息
	private int count	;//int	Y		数量
	private List<BLSDynamicAttributes> dynamicAttributes	;//list [BLSDynamicAttributes]	Y		最子级的动态属性列表
	
	public BLSCloudGoods getGoods() {
		return goods;
	}
	public void setGoods(BLSCloudGoods goods) {
		this.goods = goods;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public List<BLSDynamicAttributes> getDynamicAttributes() {
		return dynamicAttributes;
	}
	public void setDynamicAttributes(List<BLSDynamicAttributes> dynamicAttributes) {
		this.dynamicAttributes = dynamicAttributes;
	}
	@Override
	public String toString() {
		return "BLSCloudOrderGoods [goods=" + goods + ", count=" + count + ", dynamicAttributes=" + dynamicAttributes
				+ "]";
	}

	
	
}
