package com.blomni.o2o.order.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.blomni.o2o.order.dto.MentionGoodsCodeDto;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.exception.ParamRuntimeException;
import com.blomni.o2o.order.service.OrderMentionGoodsCodeService;
import com.blomni.o2o.order.service.SingleMemberService;
import com.blomni.o2o.order.util.AbstractRestResponse;
import com.blomni.o2o.order.util.DefaultRestApiResponse;
import com.blomni.o2o.order.util.R;

/**
 * 
* @ClassName: OrderMentionGoodsCodeController 
* @Description: TODO(提货码接口) 
* @author xhj 
* @date 2017年5月8日 上午10:32:56 
 */
@RestController
@RequestMapping("deliveryCode")
public class OrderMentionGoodsCodeController {
	
	private static Logger logs = LoggerFactory.getLogger(OrderMentionGoodsCodeController.class);
	
	@Autowired
	private OrderMentionGoodsCodeService orderMentionGoodsCodeService;
	
	@Autowired
	private SingleMemberService singleMemberService;
	
	/**
	 * 根据提货码信息查询订单ID
	 * @param param
	 * @return
	 */
	@RequestMapping("queryOrderIdByMentionGoodsCode")
	public ResponseEntity<?> queryOrderIdByMentionGoodsCode(@RequestBody String param){
		logs.info("OrderMentionGoodsCodeController.queryOrderIdByMentionGoodsCode.start.....");
		logs.info("OrderMentionGoodsCodeController.queryOrderIdByMentionGoodsCode.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		MentionGoodsCodeDto mentionGoodsCode = null;
		Map<String,String> resultMap = null;
		try {
			mentionGoodsCode=JSONObject.parseObject(param,MentionGoodsCodeDto.class);
			if(StringUtils.isNotBlank(mentionGoodsCode.getDeliveryCode()) && StringUtils.isNotBlank(mentionGoodsCode.getMerchantId()) 
					&& StringUtils.isNotBlank(mentionGoodsCode.getMemberId())){
				String resultVal = orderMentionGoodsCodeService.queryOrderIdByMentionGoodsCode(mentionGoodsCode);
				if(StringUtils.isNotBlank(resultVal)){
					if(R.MentionGoodsCodeEnum.mentionGoodsCode_failure.getLabel().equals(resultVal)){
						restResponse.setMessage(R.ReturnCodeEnum.code_mentionGoodsCode_time_out.getLabel());
						restResponse.setResCode(R.ReturnCodeEnum.code_mentionGoodsCode_time_out.getValue());
						result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
						return result;
					}else if(R.MentionGoodsCodeEnum.mentionGoodsCode_time_out.getLabel().equals(resultVal)){
						restResponse.setMessage(R.ReturnCodeEnum.code_mentionGoodsCode_time_out.getLabel());
						restResponse.setResCode(R.ReturnCodeEnum.code_mentionGoodsCode_time_out.getValue());
						result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
						return result;
					}else if(R.MentionGoodsCodeEnum.mentionGoodsCode_success.getLabel().equals(resultVal)){
						restResponse.setMessage(R.ReturnCodeEnum.code_mentionGoodsCode_repeat.getLabel());
						restResponse.setResCode(R.ReturnCodeEnum.code_mentionGoodsCode_repeat.getValue());
						result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
						return result;
					}else if(R.MentionGoodsCodeEnum.orderStatus_error.getLabel().equals(resultVal)){
						restResponse.setMessage(R.ReturnCodeEnum.code_queryOrderStatus_errer.getLabel());
						restResponse.setResCode(R.ReturnCodeEnum.code_queryOrderStatus_errer.getValue());
						result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
						return result;
					}else{
						resultMap = new HashMap<String,String>();
						resultMap.put("orderNo", resultVal);
						restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
						restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
						restResponse.setRestObject(resultMap);
						result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
						return result;
					}
				}else{
					restResponse.setMessage(R.ReturnCodeEnum.code_mentionGoodsCode_null.getLabel());
					restResponse.setResCode(R.ReturnCodeEnum.code_mentionGoodsCode_null.getValue());
					result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
					return result;
				}
			}else{
				restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
				result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
				return result;
			}
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_notMentionGoodsCode_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_notMentionGoodsCode_errer.getValue());
		}
		logs.info("OrderMentionGoodsCodeController.queryOrderIdByMentionGoodsCode.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
	
	
	/**
	 * 核销提货码
	 * @param param
	 * @return
	 */
	@RequestMapping("cancellationMentionGoodsCode")
	public ResponseEntity<?> cancellationMentionGoodsCode(@RequestBody String param){
		logs.info("OrderMentionGoodsCodeController.cancellationMentionGoodsCode.start.....");
		logs.info("OrderMentionGoodsCodeController.cancellationMentionGoodsCode.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		MentionGoodsCodeDto mentionGoodsCode = null;
		try {
			mentionGoodsCode=JSONObject.parseObject(param,MentionGoodsCodeDto.class);
			if(StringUtils.isNotBlank(mentionGoodsCode.getDeliveryCode()) && StringUtils.isNotBlank(mentionGoodsCode.getMerchantId()) 
					&& StringUtils.isNotBlank(mentionGoodsCode.getMemberId()) && StringUtils.isNotBlank(mentionGoodsCode.getOrderNo()) ){
				String resultVal = orderMentionGoodsCodeService.cancellationMentionGoodsCode(mentionGoodsCode);
				if(StringUtils.isNotBlank(resultVal)){
					if("1".equals(resultVal)){
						restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
						restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
						result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
						return result;
					}else{
						restResponse.setMessage(R.ReturnCodeEnum.code_mentionGoodsCode_errer.getLabel());
						restResponse.setResCode(R.ReturnCodeEnum.code_mentionGoodsCode_errer.getValue());
						result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
						return result;
					}
				}else{
					restResponse.setMessage(R.ReturnCodeEnum.code_mentionGoodsCode_errer.getLabel());
					restResponse.setResCode(R.ReturnCodeEnum.code_mentionGoodsCode_errer.getValue());
					result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
					return result;
				}
			}else{
				restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
				result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
				return result;
			}
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_mentionGoodsCode_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_mentionGoodsCode_errer.getValue());
		}
		logs.info("OrderMentionGoodsCodeController.cancellationMentionGoodsCode.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
	
	
	/**
	 * 根据订单ID查询提货码信息
	 * @param param
	 * @return 提货码
	 */
	@RequestMapping("queryMentionGoodsCodeByOrderId")
	public ResponseEntity<?> queryMentionGoodsCodeByOrderId(@RequestBody String param){
		logs.info("OrderMentionGoodsCodeController.queryOrderIdByMentionGoodsCode.start.....");
		logs.info("OrderMentionGoodsCodeController.queryOrderIdByMentionGoodsCode.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		MentionGoodsCodeDto mentionGoodsCode = null;
		Map<String,String> resultMap = null;
		try {
			mentionGoodsCode=JSONObject.parseObject(param,MentionGoodsCodeDto.class);
			if(StringUtils.isNotBlank(mentionGoodsCode.getOrderId()) && StringUtils.isNotBlank(mentionGoodsCode.getMemberId())
					&& StringUtils.isNotBlank(mentionGoodsCode.getMemberToken()) ){
				
				/********************** 用户鉴权 ************************/
				try {
					singleMemberService.getSingleMemberIdByMemberToken(mentionGoodsCode.getMemberToken(),mentionGoodsCode.getMemberId());
				} catch (OrderServiceException e) {
					restResponse.setMessage(e.getMsg());
					restResponse.setResCode(e.getCode());
					result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
					return result;
				}
				/********************** 用户鉴权 ************************/
				
				//调用service接口
				String resultStr = orderMentionGoodsCodeService.queryMentionGoodsCodeByOrderId(mentionGoodsCode);
				if (StringUtils.isNotBlank(resultStr)) {
					Map<String,String> map = new HashMap<String,String>();
					restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
					restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
					map.put("pickCode", resultStr);
					restResponse.setRestObject(map);
					result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
					return result;
				}else{
					restResponse.setMessage(R.ReturnCodeEnum.code_mentionGoodsCode_time_out.getLabel());
					restResponse.setResCode(R.ReturnCodeEnum.code_mentionGoodsCode_time_out.getValue());
					result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
					return result;
				}
			}else{
				restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
				result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
				return result;
			}
			
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_notMentionGoodsCode_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_notMentionGoodsCode_errer.getValue());
		}
		logs.info("OrderMentionGoodsCodeController.queryOrderIdByMentionGoodsCode.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
	
	
	

}
