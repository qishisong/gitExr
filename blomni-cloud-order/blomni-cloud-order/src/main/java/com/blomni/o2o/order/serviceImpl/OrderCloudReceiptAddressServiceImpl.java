package com.blomni.o2o.order.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.AddressDto;
import com.blomni.o2o.order.dto.BLSCloudAddress;
import com.blomni.o2o.order.dto.MemberAddressDto;
import com.blomni.o2o.order.entity.OrderReceiptAddress;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.mapper.OrderReceiptAddressMapper;
import com.blomni.o2o.order.service.OrderCloudReceiptAddressService;
import com.blomni.o2o.order.service.OrderReceiptAddressService;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.util.SnowflakeIdWorker;

/**
 * 
* @ClassName: OrderReceiptAddressServiceImpl 
* @Description: TODO(地址) 
* @author zy 
* @date 2017年5月4日 下午7:18:51 
*
 */
@Service
public class OrderCloudReceiptAddressServiceImpl implements OrderCloudReceiptAddressService{
	
	@Autowired
	private OrderReceiptAddressMapper orderReceiptAddressMapper;
	
	@Autowired
	private OrderReceiptAddressService orderReceiptAddressService;

	private static Logger logs = LoggerFactory.getLogger(OrderCloudReceiptAddressServiceImpl.class);
	
	private int result=R.OrderConstant.Zero;
	
	
	
	
	/**
	 * title:有逛用户添加地址
	 * zy
	 * 2017年5月4日20:11:40
	 */
	public String  insertAddress(AddressDto vo)throws OrderServiceException{
		OrderReceiptAddress address=null;
		OrderReceiptAddress record=null;
		logs.info("1602-OrderReceiptAddressServiceImpl. insertAddress ========START===={}"+JSONObject.toJSONString(vo));
		String id=null;
		 SnowflakeIdWorker idWorker = new SnowflakeIdWorker(R.OrderConstant.Zero, R.OrderConstant.One);
		if(null!=vo.getAddress()){
			id=idWorker.nextId()+"";
			address=transformation( vo);
			address.setId(id);
			address.setCreateDate(new Date());
			
			try {
				//判断是否设为默认
				if("1".equals(address.getIsDefault())&& orderReceiptAddressService.countByExample(address)>0){
					record=new OrderReceiptAddress();
					//修改原有默认地址
					record.setIdentityId(vo.getMemberId());
					updateByIsDefaultAddress(record);
				}else if(orderReceiptAddressService.countByExample(address)==R.OrderConstant.Zero){//默认将用户第一个地址设置为默认地址
					address.setIsDefault("1");
				}
				logs.info("orderReceiptAddressMapper.insertSelective===>{}"+JSONObject.toJSONString(address));
				result=orderReceiptAddressMapper.insertSelective(address);
			} catch (Exception e) {
				logs.error("用户添加地址    {}",e);
				throw new OrderServiceException(R.ReturnCodeEnum.code_insertAddress_errer.getValue(), R.ReturnCodeEnum.code_insertAddress_errer.getLabel());
				
			}
			
		}
		logs.info("1602-OrderReceiptAddressServiceImpl. insertAddress ========END===={}"+id);
		
		return id;
	}
	
	
	/**
	 * title:有逛用户修改地址1603
	 * zy
	 * 2017年5月4日20:11:40
	 */
	public int  updateAddress(AddressDto vo)throws OrderServiceException{
		logs.info("1603-OrderReceiptAddressServiceImpl. updateAddress ========START===={}"+JSONObject.toJSONString(vo));
		OrderReceiptAddress address=null;
		OrderReceiptAddress record=null;
		if(null!=vo.getAddress()){
			address=transformation( vo);
			
				try {
					address.setUpdateDate(new Date());
					if("1".equals(address.getIsDefault())&& orderReceiptAddressService.countByExample(address)>0){
						record=new OrderReceiptAddress();
						//修改原有默认地址
						record.setIdentityId(vo.getMemberId());
						updateByIsDefaultAddress(record);
					}
					result=orderReceiptAddressMapper.updateByPrimaryKeySelective(address);
				} catch (Exception e) {
					logs.error("有逛用户修改地址   {}",e);
					throw new OrderServiceException(R.ReturnCodeEnum.code_insertAddress_errer.getValue(), R.ReturnCodeEnum.code_insertAddress_errer.getLabel());
					
				}
			
		}
		logs.info("1603-OrderReceiptAddressServiceImpl. updateAddress ========END===={}"+result);
		return result;
	}
	
	/**
	 * title:修改默认地址
	 * 2017年5月5日10:37:52
	 * zy
	 */
	@Override
	public int updateByIsDefaultAddress(OrderReceiptAddress record) throws OrderServiceException {
		logs.info("修改默认地址 OrderReceiptAddressServiceImpl.updateByIsDefaultAddress ========START====="+JSONObject.toJSONString(record));
		// TODO Auto-generated method stub
		try {
			
			record.setUpdateDate(new Date());
			result=orderReceiptAddressMapper.updateByIsDefaultAddress(record);
		} catch (Exception e) {
			logs.error("修改默认地址    {}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_updateAddress_errer.getValue(), R.ReturnCodeEnum.code_updateAddress_errer.getLabel());
			
		}
		logs.info("修改默认地址 OrderReceiptAddressServiceImpl.updateByIsDefaultAddress ========END====="+result);
		return result;
	}
	
	
	/**
	 * title:查询用户地址
	 * zy
	 * 2017年5月5日13:02:49
	 */
	@Override
	public Map<String, Object> selectCloudMemberAddress(MemberAddressDto dto) throws OrderServiceException {
		List<MemberAddressDto> list=null;
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		try {
			
			list=orderReceiptAddressMapper.selectCloudMemberAddress(dto);
			map.put("addressList", list);
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("查询用户地址    {}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_selectAddress_errer.getValue(), R.ReturnCodeEnum.code_selectAddress_errer.getLabel());
			
		}
		
		return map;
	}

	
	/**
	 * title:1604有逛 删除收货地址
	 * zy
	 * 2017年5月4日19:58:12
	 */
	@Override
	public int delectMemberAddress(MemberAddressDto dto) throws OrderServiceException {
		logs.info("OrderReceiptAddressServiceImpl. delectAddress ========START===={}"+JSONObject.toJSONString(dto));
		
		
			// TODO Auto-generated method stub
			try {
				OrderReceiptAddress record=new OrderReceiptAddress();
				record.setDelFlag("1");
				record.setUpdateDate(new Date());
				record.setId(dto.getAddressId());;
				result=orderReceiptAddressMapper.delectAddress(record);
			} catch (Exception e) {
				// TODO: handle exception
				logs.error("删除地址    {}",e);
				throw new OrderServiceException(R.ReturnCodeEnum.code_delectAddress_errer.getValue(), R.ReturnCodeEnum.code_delectAddress_errer.getLabel());
				
			}
		logs.info("OrderReceiptAddressServiceImpl. delectAddress ========START===={}"+JSONObject.toJSONString(dto));
		
		return result;
	}
	
	/**
	 * 
	* @Title: transformation 
	* @Description: TODO(有逛转换实体类) 
	* @param @param vo
	* @param @return    设定文件 
	* @return OrderReceiptAddress    返回类型 
	* @date 2017年5月5日 上午9:50:07 
	* @author zy 
	* @throws
	 */
	public OrderReceiptAddress transformation(AddressDto vo){
		logs.info("OrderReceiptAddressServiceImpl.transformation ========START===={}"+JSONObject.toJSONString(vo));
		OrderReceiptAddress address=null;
		
			if(null!=vo.getAddress()){
				BLSCloudAddress bl=vo.getAddress();
				 address=new OrderReceiptAddress();
				 
				 if(StringUtils.isNotBlank(bl.getAddressId())){
					 address.setId(bl.getAddressId());
				 }
				address.setCreateDate(new Date());
				address.setDelFlag("0");
				address.setIdentityId(vo.getMemberId());//会员id
				address.setIsDefault(bl.getDefaultFlag()+"");//是否默认收货地址(1：表示默认 0：表示非默认) 默认为0',
				address.setPostCode(bl.getZipcode());//邮编
				address.setReceiptAreaId(bl.getDistrictId());//收件人区ID districtId
				address.setReceiptAreaName(bl.getDistrictName());//收件人区名称
				address.setReceiptCityId(bl.getCityId());//收件人市id
				address.setReceiptCityName(bl.getCityName());//收件人市名称
				address.setReceiptDetailAddress(bl.getAddress());//收件人详细地址
				address.setReceiptName(bl.getReceiverName());//收件人姓名 receiverName
				address.setReceiptPhone(bl.getReceiverPhone());//收件人电话
				address.setReceiptProvinceId(bl.getProvinceId());//收件人省ID provinceId
				address.setReceiptProvinceName(bl.getProvinceName());//收件人省名称
				//address.setReceiptRegion();//收件人地区
				address.setReceiptType("0");//收件人类型(0:表示用户  1,:表示商户) 默认为0
		}
			logs.info("transformation ========END===={}"+JSONObject.toJSONString(address));
		return address;
		
	}
	
	

	/**
	 * title:修改默认地址
	 * 2017年5月5日10:37:52
	 * zy
	 */
	@Override
	public int updateByIsDefaultAddressMemberId(String  memberId)throws OrderServiceException{
		logs.info("修改默认地址 OrderReceiptAddressServiceImpl.updateByIsDefaultAddressId ========START====={}"+memberId);
		// TODO Auto-generated method stub
		int result=0;
		try {
			OrderReceiptAddress address=new OrderReceiptAddress();
			address.setUpdateDate(new Date());
			address.setIdentityId(memberId);
			result=orderReceiptAddressMapper.updateByIsDefaultAddress(address);
		} catch (Exception e) {
			logs.error("修改默认地址    {}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_updateAddress_errer.getValue(), R.ReturnCodeEnum.code_updateAddress_errer.getLabel());
			
		}
		logs.info("修改默认地址 OrderReceiptAddressServiceImpl.updateByIsDefaultAddressId ========END====="+result);
		return result;
	}
	

	
	
}
