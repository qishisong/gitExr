package com.blomni.o2o.order.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.CancelOrderDto;
import com.blomni.o2o.order.dto.QueryMerchantOrderDetailsDto;
import com.blomni.o2o.order.dto.ReceiveOrderDto;
import com.blomni.o2o.order.dto.UpdateOrderInfoDto;
import com.blomni.o2o.order.dto.UpdateOrderStateDto;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.exception.ParamRuntimeException;
import com.blomni.o2o.order.service.OrderBasicInfoService;
import com.blomni.o2o.order.service.SingleMemberService;
import com.blomni.o2o.order.threadPool.OrderTimeoutTask;
import com.blomni.o2o.order.util.AbstractRestResponse;
import com.blomni.o2o.order.util.DefaultRestApiResponse;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.vo.MerchantOrderDetailsVo;


@RestController
@RequestMapping("/orderInfo")
public class OrderBasicInfoController {
	
	private static Logger logs = LoggerFactory.getLogger(OrderBasicInfoController.class);
	
	@Autowired
	private OrderBasicInfoService orderBasicInfoService;
	
	@Autowired
	private SingleMemberService singleMemberService;
	
	@Autowired
	private OrderTimeoutTask task;
	
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/receiveOrder", method = RequestMethod.POST)
	public ResponseEntity<?> receiveOrder(@RequestBody String param, HttpServletRequest request){
		logs.info("OrderBasicInfoController.receiveOrder.start.....");
		logs.info("OrderBasicInfoController.receiveOrder.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		ReceiveOrderDto receiveOrderDto = null;
		try {
			receiveOrderDto=JSONObject.parseObject(param,ReceiveOrderDto.class);
			if (receiveOrderDto == null || StringUtils.isEmpty(receiveOrderDto.getImgUrl())
					||StringUtils.isEmpty(receiveOrderDto.getMemberId())||StringUtils.isEmpty(receiveOrderDto.getMerchantId())
					||StringUtils.isEmpty(receiveOrderDto.getOrderNo())||StringUtils.isEmpty(receiveOrderDto.getSalesSlipNo())) {
				throw new ParamRuntimeException();
			}
			int count=orderBasicInfoService.receiveOrder(receiveOrderDto);
			if(count>0){
				restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
			}else{
				restResponse.setMessage(R.ReturnCodeEnum.code_receiveOrder_errer.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_receiveOrder_errer.getValue());
			}
			
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_receiveOrder_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_receiveOrder_errer.getValue());
		}
		logs.info("OrderBasicInfoController.receiveOrder.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	public ResponseEntity<?> cancelOrder(@RequestBody String param, HttpServletRequest request){
		logs.info("取消订单 begin .params={}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		CancelOrderDto dto = null;
		try {
			dto=JSONObject.parseObject(param,CancelOrderDto.class);
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		if (dto == null || StringUtils.isEmpty(dto.getOrderId())
				||StringUtils.isEmpty(dto.getMemberId())||StringUtils.isEmpty(dto.getMemberToken())
		) {
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
			orderBasicInfoService.reqNewCancelOrderState(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_cancelOrder_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_cancelOrder_errer.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		logs.info("取消订单  end  .result={}",result);
		return result;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/queryMerchantOrderDetails", method = RequestMethod.POST)
	public ResponseEntity<?> queryMerchantOrderDetails(@RequestBody String param, HttpServletRequest request){
		logs.info("查询商户订单详情 begin .params={}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		QueryMerchantOrderDetailsDto dto = null;
		try {
			dto=JSONObject.parseObject(param,QueryMerchantOrderDetailsDto.class);
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		if (dto == null || StringUtils.isEmpty(dto.getMerchantId())
				||StringUtils.isEmpty(dto.getMemberId())||StringUtils.isEmpty(dto.getOrderNo())
		) {
			restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
	
		try {
			
			MerchantOrderDetailsVo vo=this.orderBasicInfoService.queryMerchantOrderDetailsByOrderNo(dto);
			restResponse.setRestObject(vo);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		} catch (Exception e) {
			logs.error(R.ReturnCodeEnum.code_queryOrderDetails_errer.getLabel()+"={}",e);
			restResponse.setMessage(R.ReturnCodeEnum.code_queryOrderDetails_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_queryOrderDetails_errer.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		logs.info("查询商户订单详情end  result={}",result);
		return result;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/updateOrderState", method = RequestMethod.POST)
	public ResponseEntity<?> updateOrderState(@RequestBody String param, HttpServletRequest request){
		logs.info("updateOrderState  begin .params={}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		UpdateOrderStateDto dto = null;
		try {
			dto=JSONObject.parseObject(param,UpdateOrderStateDto.class);
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		if (dto == null || StringUtils.isEmpty(dto.getOrderNo())
				||StringUtils.isEmpty(dto.getMemberId())||StringUtils.isEmpty(dto.getOrderState())
				||(dto.getOrderState().equals(R.OrderStateEnum.orderState_closed.getValue())&&StringUtils.isEmpty(dto.getReason()))){
			restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		
		try {
			orderBasicInfoService.updateOrderState(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_updateOrderState_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_updateOrderState_errer.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		logs.info("updateOrderState end  .result={}",result);
		return result;
	}
	
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/updateOrderInfo", method = RequestMethod.POST)
	public ResponseEntity<?> updateOrderInfo(@RequestBody String param, HttpServletRequest request){
		logs.info("updateOrderInfo  begin .params={}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		UpdateOrderInfoDto dto = null;
		try {
			dto=JSONObject.parseObject(param,UpdateOrderInfoDto.class);
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		if (dto == null || StringUtils.isEmpty(dto.getOrderNo())
				||StringUtils.isEmpty(dto.getMemberId())||StringUtils.isEmpty(dto.getRemark())){
			restResponse.setMessage(R.ReturnCodeEnum.code_req_msg.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_req_msg.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		
		try {
			orderBasicInfoService.updateOrderInfo(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_updateOrderState_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_updateOrderState_errer.getValue());
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			return result;
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		logs.info("updateOrderState end  .result={}",result);
		return result;
	}
	
	/**
	 * 待接单任务
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/timeOutWaitOrderJob", method = RequestMethod.POST)
	public ResponseEntity<?> timeOutWaitOrderJob(){
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		task.executeTimeOutWaitOrderTask();
		restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
		restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
	
	/**
	 * 待支付任务
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/timeOutPaymentOrderJob", method = RequestMethod.POST)
	public ResponseEntity<?> timeOutPaymentOrderJob(){
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		task.executeBeforePaymentTimeoutTask();
		restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
		restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
	
	

}
