package com.blomni.o2o.order.dto;
/**
 * 
* @ClassName: BLSDynamicAttributes 
* @Description: TODO(最子级的动态属性列表) 
* @author zy 
* @date 2017年5月5日 下午8:46:32 
*
 */
public class BLSDynamicAttributes {
	private String attributeId;//	string	Y		属性ID
	private String  attributeName;//	string	Y		属性名称
	public String getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	@Override
	public String toString() {
		return "BLSDynamicAttributes [attributeId=" + attributeId + ", attributeName=" + attributeName + "]";
	}
	
	
}
