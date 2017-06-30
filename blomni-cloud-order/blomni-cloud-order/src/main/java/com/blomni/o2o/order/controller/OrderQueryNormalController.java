package com.blomni.o2o.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.GetCountForOrderStateDto;
import com.blomni.o2o.order.dto.QueryOrderListDto;
import com.blomni.o2o.order.exception.ParamRuntimeException;
import com.blomni.o2o.order.service.OrderBasicInfoService;
import com.blomni.o2o.order.util.AbstractRestResponse;
import com.blomni.o2o.order.util.DefaultRestApiResponse;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.vo.GetCountForOrderStateVo;
import com.blomni.o2o.order.vo.QueryOrderListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/queryNormal")
public class OrderQueryNormalController {

	private static Logger logs = LoggerFactory.getLogger(OrderQueryNormalController.class);

	@Autowired
	private OrderBasicInfoService orderBasicInfoService;

	/**
	 * 2001-查询订单不同状态数量
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/getCountForOrderState", method = RequestMethod.POST)
	public ResponseEntity<?> getCountForOrderState(@RequestBody String param, HttpServletRequest request) {
		logs.info("OrderQueryNormalController.getCountForOrderState.start.....");
		logs.info("OrderQueryNormalController.getCountForOrderState.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		GetCountForOrderStateDto dto = null;
		try {
			try {
				dto = JSONObject.parseObject(param, GetCountForOrderStateDto.class);
			} catch (Exception e) {
				throw new JSONException();
			}
			if (dto == null || StringUtils.isEmpty(dto.getMemberId())) {
				throw new ParamRuntimeException();
			}
			// 获得订单不同状态数量
			List<GetCountForOrderStateVo> orderTypeList = new ArrayList<GetCountForOrderStateVo>();
			orderTypeList = orderBasicInfoService.getCountForOrderState(dto);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderTypeList", orderTypeList);
			restResponse.setRestObject(map);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_getCountForOrderState_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_getCountForOrderState_errer.getValue());
		}
		logs.info("OrderQueryNormalController.getCountForOrderState.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}

	/**
	 * 2002-查询订单列表
	 * 
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/queryOrderList", method = RequestMethod.POST)
	public ResponseEntity<?> queryOrderList(@RequestBody String param, HttpServletRequest request) {
		logs.info("OrderQueryNormalController.queryOrderList.start.....");
		logs.info("OrderQueryNormalController.queryOrderList.in.params{}", JSONObject.toJSONString(param));
		ResponseEntity<?> result = null;
		AbstractRestResponse restResponse = new DefaultRestApiResponse();
		QueryOrderListDto dto = null;
		try {
			try {
				dto = JSONObject.parseObject(param, QueryOrderListDto.class);
			} catch (Exception e) {
				throw new JSONException();
			}
			if (dto == null || StringUtils.isEmpty(dto.getMemberId())) {
				throw new ParamRuntimeException();
			}
			if (dto.getPageNo() != null && dto.getPageSize() != null) {
				PageHelper.startPage(Integer.parseInt(dto.getPageNo()), Integer.parseInt(dto.getPageSize()));
			} else {
				PageHelper.startPage(1, 10);
			}
			// 获得订单
			List<QueryOrderListVo> listOrder = new ArrayList<QueryOrderListVo>();
			if("0".equals(dto.getOrderState())){
				dto.setOrderState(null);
			}
			PageInfo info = orderBasicInfoService.getOrderList(dto);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("count", info.getTotal());
			map.put("pageNo", info.getPageNum());
			map.put("pageSize", info.getPageSize());
			map.put("totalPage", info.getPages());
			map.put("listOrder", info.getList());
			restResponse.setRestObject(map);
			restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
		} catch (JSONException e1) {
			restResponse.setMessage(R.ReturnCodeEnum.code_jsonfail.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_jsonfail.getValue());
		} catch (ParamRuntimeException e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_praramnotnull.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_praramnotnull.getValue());
		} catch (Exception e) {
			restResponse.setMessage(R.ReturnCodeEnum.code_queryOrderList_errer.getLabel());
			restResponse.setResCode(R.ReturnCodeEnum.code_queryOrderList_errer.getValue());
		}
		logs.info("OrderQueryNormalController.queryOrderList.end.....");
		result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
		return result;
	}
}
