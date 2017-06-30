package com.blomni.o2o.order.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.SelectQueryOrderDto;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.service.OrderSelectInfoService;
import com.blomni.o2o.order.service.SingleMemberService;
import com.blomni.o2o.order.util.AbstractRestResponse;
import com.blomni.o2o.order.util.DefaultRestApiResponse;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.vo.QueryOrderDetailsVo;

/**
 * 
* @ClassName: OrderSelectInfoController 
* @Description: TODO(查询订单) 
* @author zy 
* @date 2017年5月9日 上午10:16:58 
*
 */
@RestController
@RequestMapping("order/info")
public class OrderSelectInfoController {
	private static Logger logs = LoggerFactory.getLogger(OrderSelectInfoController.class);
	
	@Autowired
	private SingleMemberService singleMemberService;
	
	@Autowired
	private OrderSelectInfoService orderSelectInfoService;
	
	/**
	 * 
	* @Title: selectQueryOrderList 
	* @Description: TODO(1605 查询订单列表) 
	* @param @return    设定文件 
	* @return ResponseEntity<?>    返回类型 
	* @date 2017年5月9日 上午10:19:09 
	* @author zy 
	* @throws
	 */
	@RequestMapping("selectQueryOrderList")
	public  ResponseEntity<?> selectQueryOrderList(@RequestBody SelectQueryOrderDto dto, HttpServletRequest request,HttpServletResponse response){
		logs.info("OrderSelectInfoController.selectQueryOrderList========START======{}"+JSONObject.toJSONString(dto));
		 ResponseEntity<?>  result = null;
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
			map=orderSelectInfoService.selectQueryOrderList(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
			restResponse.setRestObject(map);
		}catch (OrderServiceException e1) {
			// TODO: handle exception
			restResponse.setMessage(e1.getMsg());
			restResponse.setResCode(e1.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			logs.info("OrderSelectInfoController.selectQueryOrderList{}",e);
			restResponse.setMessage(R.ReturnCodeEnum.code_queryOrderList_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_queryOrderList_errer.getValue());
		}
		
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		logs.info("OrderSelectInfoController.selectQueryOrderList=====END=={}"+JSONObject.toJSONString(result));
		return result;
	}
	
	/**
	 * 
	* @Title: selectQueryOrderDetails 
	* @Description: TODO(1606 查询订单详情) 
	* @param @return    设定文件 
	* @return ResponseEntity<?>    返回类型 
	* @date 2017年5月9日 上午10:19:09 
	* @author zy 
	* @throws
	 */
	@RequestMapping("selectQueryOrderDetails")
	public  ResponseEntity<?> selectQueryOrderDetails(@RequestBody SelectQueryOrderDto dto, HttpServletRequest request,HttpServletResponse response){
		logs.info("OrderSelectInfoController.selectQueryOrderList========START======{}"+JSONObject.toJSONString(dto));
		 ResponseEntity<?>  result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		
		if(StringUtils.isBlank(dto.getMemberId())||StringUtils.isBlank(dto.getMemberToken())
				||StringUtils.isBlank(dto.getOrderId())){
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
			QueryOrderDetailsVo vo=orderSelectInfoService.selectQueryOrderDetails(dto);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
			restResponse.setRestObject(vo);
		} catch (OrderServiceException e) {
			// TODO Auto-generated catch block
			logs.info("1605 查询订单列表  selectQueryOrderDetails {}",e);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		}
		
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		logs.info("OrderSelectInfoController.selectQueryOrderList=====END=={}"+JSONObject.toJSONString(result));
		return result;
	}
	
}
