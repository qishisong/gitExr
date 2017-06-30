package com.blomni.o2o.order.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.AddressDto;
import com.blomni.o2o.order.dto.BLSCloudAddress;
import com.blomni.o2o.order.dto.MemberAddressDto;
import com.blomni.o2o.order.entity.OrderReceiptAddress;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.service.OrderCloudReceiptAddressService;
import com.blomni.o2o.order.service.SingleMemberService;
import com.blomni.o2o.order.serviceImpl.OrderAutographServiceImpl;
import com.blomni.o2o.order.util.AbstractRestResponse;
import com.blomni.o2o.order.util.DefaultRestApiResponse;
import com.blomni.o2o.order.util.R;

/**
 * 
* @ClassName: OrderReceiptAddressController 
* @Description: TODO（有逛地址接口) 
* @author zy 
* @date 2017年5月4日 下午7:20:29 
*
 */
@RestController
@RequestMapping("order/address")
public class OrderCloudReceiptAddressController {
	private static Logger logs = LoggerFactory.getLogger(OrderCloudReceiptAddressController.class);
	
	@Autowired
	private OrderCloudReceiptAddressService orderReceiptAddressService;
	
	@Autowired
	private SingleMemberService singleMemberService;
	
	/**
	 * 
	* @Title: insert 
	* @Description: TODO(1602 新增收货地址) 
	* @param @param dto
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return ResponseEntity<?>    返回类型 
	* @date 2017年5月4日 下午7:38:32 
	* @author zy 
	* @throws
	 */
	@RequestMapping("insert")
	public  ResponseEntity<?> insert(@RequestBody AddressDto dto, HttpServletRequest request,HttpServletResponse response){
		logs.info("OrderReceiptAddressController.insert========START======{}"+JSONObject.toJSONString(dto));
		 ResponseEntity<?>  result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		
		if(StringUtils.isBlank(dto.getMemberId())||StringUtils.isBlank(dto.getMemberToken())||
				StringUtils.isBlank(dto.getAddress().getAddress().toString())||
				StringUtils.isBlank(dto.getAddress().getProvinceName())||
				StringUtils.isBlank(dto.getAddress().getDistrictName())||
				StringUtils.isBlank(dto.getAddress().getReceiverName())||
				StringUtils.isBlank(dto.getAddress().getReceiverPhone())
				){
			restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			
			return result;
		}
		/********************** 用户鉴权 ************************/

		try {
			singleMemberService.getSingleMemberIdByMemberToken(dto.getMemberToken(),dto.getMemberId());
		} catch (OrderServiceException e) {
			restResponse.setMessage(e.getMsg());
			restResponse.setResCode(e.getCode());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}

		/********************** 用户鉴权 ************************/
		
		try {
			String addressId=orderReceiptAddressService.insertAddress(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
			map.put("addressId", addressId);
			restResponse.setRestObject(map);
		}catch (OrderServiceException e1) {
			restResponse.setMessage(e1.getMsg());
			restResponse.setResCode(e1.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			logs.error(e.getMessage(),e);
			restResponse.setMessage(R.ReturnCodeEnum.code_insertAddress_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_insertAddress_errer.getValue());
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		logs.info("OrderReceiptAddressController.insert=====END=={}"+JSONObject.toJSONString(result));
		return result;
		
	}
	
	/**
	 * 
	* @Title: selectByOrderReceiptAddress 
	* @Description: TODO(1601 查询用户收货地址列表) 
	* @param @param dto
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return ResponseEntity<?>    返回类型 
	* @date 2017年5月4日 下午7:39:09 
	* @author zy 
	* @throws
	 */
	@RequestMapping("selectByOrderReceiptAddress")
	public ResponseEntity<?>selectByOrderReceiptAddress(@RequestBody MemberAddressDto dto, HttpServletRequest request,HttpServletResponse response){
		ResponseEntity<?> result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		logs.info("selectByOrderReceiptAddress======START===={}"+JSONObject.toJSONString(dto));
		if(StringUtils.isBlank(dto.getMemberId())||StringUtils.isBlank(dto.getMemberToken())){
			restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		/********************** 用户鉴权 ************************/

		try {
			singleMemberService.getSingleMemberIdByMemberToken(dto.getMemberToken(),dto.getMemberId());
		} catch (OrderServiceException e) {
			restResponse.setMessage(e.getMsg());
			restResponse.setResCode(e.getCode());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}

		/********************** 用户鉴权 ************************/
		
		try {
			map=orderReceiptAddressService.selectCloudMemberAddress(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
			restResponse.setRestObject(map);
		}catch (OrderServiceException e1) {
			restResponse.setMessage(e1.getMsg());
			restResponse.setResCode(e1.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("1601 查询用户收货地址列表 selectByOrderReceiptAddress{}",e);
			restResponse.setMessage(R.ReturnCodeEnum.code_insertAddress_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_insertAddress_errer.getValue());
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		logs.info("selectByOrderReceiptAddress======END===={}"+JSONObject.toJSONString(result));
		return result;
		
	}
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective 
	* @Description: TODO(1603 修改收货地址) 
	* @param @param dto
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return ResponseEntity<?>    返回类型 
	* @date 2017年5月4日 下午7:39:09 
	* @author zy 
	* @throws
	 */
	@RequestMapping("updateByPrimaryKeySelective")
	public ResponseEntity<?>updateByPrimaryKeySelective( @RequestBody AddressDto dto, HttpServletRequest request,HttpServletResponse response){
		ResponseEntity<?> result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		
		if(StringUtils.isBlank(dto.getMemberId())||StringUtils.isBlank(dto.getMemberToken())){
			restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		/********************** 用户鉴权 ************************/

		try {
			singleMemberService.getSingleMemberIdByMemberToken(dto.getMemberToken(),dto.getMemberId());
		} catch (OrderServiceException e) {
			restResponse.setMessage(e.getMsg());
			restResponse.setResCode(e.getCode());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}

		/********************** 用户鉴权 ************************/
		try {
			orderReceiptAddressService.updateAddress(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
			
		}catch (OrderServiceException e1) {
			restResponse.setMessage(e1.getMsg());
			restResponse.setResCode(e1.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("1603 修改收货地址 updateByPrimaryKeySelective{}",e);
			restResponse.setMessage(R.ReturnCodeEnum.code_updateAddress_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_updateAddress_errer.getValue());
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		
		return result;
		
	}
	
	/**
	 * 
	* @Title: delectOrderReceiptAddress 
	* @Description: TODO(1604 删除收货地址 ) 
	* @param @param dto
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return ResponseEntity<?>    返回类型 
	* @date 2017年5月4日 下午7:39:09 
	* @author zy 
	* @throws
	 */
	@RequestMapping("delectOrderReceiptAddress")
	public ResponseEntity<?>delectOrderReceiptAddress(@RequestBody MemberAddressDto dto, HttpServletRequest request,HttpServletResponse response){
		ResponseEntity<?> result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		
		if(StringUtils.isBlank(dto.getMemberId())||StringUtils.isBlank(dto.getMemberToken())){
			restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		/********************** 用户鉴权 ************************/

		try {
			singleMemberService.getSingleMemberIdByMemberToken(dto.getMemberToken(),dto.getMemberId());
		} catch (OrderServiceException e) {
			restResponse.setMessage(e.getMsg());
			restResponse.setResCode(e.getCode());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}

		/********************** 用户鉴权 ************************/
		
		try {
			
			orderReceiptAddressService.delectMemberAddress(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
			
		}catch (OrderServiceException e1) {
			restResponse.setMessage(e1.getMsg());
			restResponse.setResCode(e1.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("1603 修改收货地址 updateByPrimaryKeySelective{}",e);
			restResponse.setMessage(R.ReturnCodeEnum.code_delectAddress_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_delectAddress_errer.getValue());
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		
		return result;
		
	}
	
	public static void main(String[] args) {
		BLSCloudAddress res=new BLSCloudAddress();
		res.setAddress("四川南路26号");
		res.setAddressId("000001");
		res.setCityId("0000002");
		res.setCityName("上海");
		res.setDistrictId("000003");
		res.setProvinceId("0000004");
		res.setProvinceName( "省名");
		res.setReceiverName("史诗之巨魔");
		res.setReceiverPhone("138888888888");
		res.setZipcode("200000");
		AddressDto dto = new AddressDto();
		dto.setMemberId("100000002712036");
		dto.setAddress(res);
		
		
		System.out.println(JSONObject.toJSONString(dto));
	}
	
}
