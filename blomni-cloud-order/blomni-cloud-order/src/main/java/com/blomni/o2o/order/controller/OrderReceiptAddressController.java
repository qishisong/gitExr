package com.blomni.o2o.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.BLSCloudAddress;
import com.blomni.o2o.order.dto.MemberAddressDto;
import com.blomni.o2o.order.entity.OrderReceiptAddress;
import com.blomni.o2o.order.exception.ParamRuntimeException;
import com.blomni.o2o.order.service.OrderCloudReceiptAddressService;
import com.blomni.o2o.order.service.OrderReceiptAddressService;
import com.blomni.o2o.order.util.AbstractRestResponse;
import com.blomni.o2o.order.util.DefaultRestApiResponse;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.util.SnowflakeIdWorker;

/**
 * 
* @ClassName: OrderReceiptAddressController 
* @Description: TODO(商户地址接口) 
* @author zy 
* @date 2017年5月5日 下午2:32:56 
*
 */

@RestController
@RequestMapping("order/receiptController")
public class OrderReceiptAddressController {
	
	private static Logger logs = LoggerFactory.getLogger(OrderReceiptAddressController.class);
	
	@Autowired
	private OrderCloudReceiptAddressService orderCloudReceiptAddressService;
	
	@Autowired
	private OrderReceiptAddressService orderReceiptAddressService;
	
	/**
	 * 商户查询提货地址
	 * @param param
	 * @return
	 */
	@RequestMapping("queryMerchantAddress")
	public ResponseEntity<?> queryMerchantAddress(@RequestBody String param){
		logs.info("OrderReceiptAddressController.queryMerchantAddress.start.....");
		logs.info("OrderReceiptAddressController.queryMerchantAddress.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		MemberAddressDto memberAddress = null;
		try {
			memberAddress=JSONObject.parseObject(param,MemberAddressDto.class);
			Map<String, Object> map = orderCloudReceiptAddressService.selectCloudMemberAddress(memberAddress) ;
			if(null != map){
				List<BLSCloudAddress> list = null;
				if(null !=map.get("addressList")){
					list= (List<BLSCloudAddress>)map.get("addressList");
				}
				if(null != list && list.size() > 0){
					Map<String,Object> resultMap = new HashMap<String,Object>();
					restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
					restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
					restResponse.setRestObject(list.get(0).getAddress());
					result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
					return result;
				}else{
					restResponse.setMessage(R.ReturnCodeEnum.code_result.getLabel());
					restResponse.setResCode(R.ReturnCodeEnum.code_result.getValue());
				}
			}else{
				restResponse.setMessage(R.ReturnCodeEnum.code_result.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_result.getValue());
			}
			
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_selectAddress_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_selectAddress_errer.getValue());
		}
		logs.info("OrderReceiptAddressController.queryMerchantAddress.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
	
	/**
	 * 新增商户提货地址
	 * @param param
	 * @return
	 */
	@RequestMapping("insertMerchantAddress")
	public ResponseEntity<?> insertMerchantAddress(@RequestBody String param){
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(R.OrderConstant.Zero, R.OrderConstant.One);
		logs.info("OrderReceiptAddressController.queryMerchantAddress.start.....");
		logs.info("OrderReceiptAddressController.queryMerchantAddress.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		OrderReceiptAddress memberAddress = null;
		try {
			memberAddress=JSONObject.parseObject(param,OrderReceiptAddress.class);
			memberAddress.setId(idWorker.nextId()+"");
			memberAddress.setReceiptType("1");
			int temp = orderReceiptAddressService.insertSelective(memberAddress) ;
			if(temp > 0){
				restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
				restResponse.setRestObject(temp);
				result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			}else{
				restResponse.setMessage(R.ReturnCodeEnum.code_insertAddress_errer.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_insertAddress_errer.getValue());
			}
			
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_insertAddress_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_insertAddress_errer.getValue());
		}
		logs.info("OrderReceiptAddressController.queryMerchantAddress.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
	
	/**
	 * 修改商户提货地址
	 * @param param
	 * @return
	 */
	@RequestMapping("updateMerchantAddress")
	public ResponseEntity<?> updateMerchantAddress(@RequestBody String param){
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(R.OrderConstant.Zero, R.OrderConstant.One);
		logs.info("OrderReceiptAddressController.queryMerchantAddress.start.....");
		logs.info("OrderReceiptAddressController.queryMerchantAddress.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		OrderReceiptAddress memberAddress = null;
		try {
			memberAddress=JSONObject.parseObject(param,OrderReceiptAddress.class);
			memberAddress.setReceiptType("1");
			int temp = orderReceiptAddressService.updateMerchantAddress(memberAddress) ;
			if(temp > 0){
				restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
				restResponse.setRestObject(temp);
				result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			}else{
				restResponse.setMessage(R.ReturnCodeEnum.code_updateAddress_errer.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_updateAddress_errer.getValue());
			}
			
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_updateAddress_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_updateAddress_errer.getValue());
		}
		logs.info("OrderReceiptAddressController.queryMerchantAddress.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
}
