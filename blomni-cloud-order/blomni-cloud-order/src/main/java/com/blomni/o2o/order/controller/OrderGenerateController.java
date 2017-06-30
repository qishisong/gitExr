package com.blomni.o2o.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.BLSCloudGoods;
import com.blomni.o2o.order.dto.BLSCloudOrder;
import com.blomni.o2o.order.dto.BLSCloudOrderGoods;
import com.blomni.o2o.order.dto.BLSCloudShop;
import com.blomni.o2o.order.dto.BLSDynamicAttributes;
import com.blomni.o2o.order.dto.OrderDto;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.service.OrderGenerateService;
import com.blomni.o2o.order.service.SingleMemberService;
import com.blomni.o2o.order.util.AbstractRestResponse;
import com.blomni.o2o.order.util.DefaultRestApiResponse;
import com.blomni.o2o.order.util.R;

/**
 * 
* @ClassName: OrderController 
* @Description: TODO(创建订单) 
* @author zy 
* @date 2017年5月5日 下午8:16:36 
*
 */
@RestController
@RequestMapping("order/generate")
public class OrderGenerateController {
	private static Logger logs = LoggerFactory.getLogger(OrderGenerateController.class);
	
	@Autowired
	private OrderGenerateService orderGenerateService;
	
	@Autowired
	private SingleMemberService singleMemberService;
	
	/**
	 * 
	* @Title: orderGenerate 
	* @Description: TODO(订单生成) 
	* @param @return    设定文件 
	* @return ResponseEntity<?>    返回类型 
	* @date 2017年5月8日 上午10:22:27 
	* @author zy 
	* @throws
	 */
	
	@RequestMapping("orderGenerate")
	public  ResponseEntity<?> orderGenerate(@RequestBody OrderDto  dto, HttpServletRequest request,HttpServletResponse response){
			logs.info("OrderGenerateController.orderGenerate=====END=={}"+JSONObject.toJSONString(dto));
			ResponseEntity<?>  result = null;
			Map<String, Object> map = new HashMap<String, Object>();
			AbstractRestResponse restResponse = new DefaultRestApiResponse();
			if(StringUtils.isBlank(dto.getMemberId())||StringUtils.isBlank(dto.getMemberToken())
					||null==dto.getOrder()){
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
				map=orderGenerateService.orderGenerate(dto);
				restResponse.setMessage(R.ReturnCodeEnum.code_success.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_success.getValue());
				restResponse.setRestObject(map);
			}catch (OrderServiceException e1) {
				// TODO: handle exception
				restResponse.setMessage(e1.getMsg());
				restResponse.setResCode(e1.getCode());
			} catch (Exception e) {
				// TODO: handle exception
				logs.error(e.getMessage(),e);
				restResponse.setMessage(R.ReturnCodeEnum.code_order_fail.getLabel());
				restResponse.setResCode(R.ReturnCodeEnum.code_order_fail.getValue());
			}
			
			result = new ResponseEntity<AbstractRestResponse>(restResponse, HttpStatus.OK);
			logs.info("OrderReceiptAddressController.insert=====END=={}"+JSONObject.toJSONString(result));
			return result;
			
	}
	
	public static void main(String[] args) {
		
		List<BLSDynamicAttributes>bl=new ArrayList<BLSDynamicAttributes>();
		BLSDynamicAttributes bLSDynamicAttributes=new BLSDynamicAttributes();
		bLSDynamicAttributes.setAttributeId("10000101");
		bLSDynamicAttributes.setAttributeName("乳白色");
		
		BLSDynamicAttributes bLSDynamicAttributes1=new BLSDynamicAttributes();
		bLSDynamicAttributes1.setAttributeId("10000102");
		bLSDynamicAttributes1.setAttributeName("米白色");
		bl.add(bLSDynamicAttributes);
		bl.add(bLSDynamicAttributes1);
		
		BLSCloudGoods goods=new BLSCloudGoods();
		goods.setProductId("293745688464850953");
		goods.setGoodsSalesName("安踏1号测试");
		goods.setGoodsStandaName("安踏缩略品名");
		goods.setGoodsPrice(199);
		goods.setMarketPrice(299);
		goods.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/TB1HtPmOVXXXXagapXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
		goods.setWeight(12);
		
		BLSCloudGoods goods1=new BLSCloudGoods();
		goods1.setProductId("293745688464850953");
		goods1.setGoodsSalesName("安踏1号测试");
		goods1.setGoodsStandaName("安踏缩略品名");
		goods1.setGoodsPrice(199);
		goods1.setMarketPrice(299);
		goods1.setImageUrl("https://img.alicdn.com/bao/uploaded/i3/TB17h9RQpXXXXbKXXXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
		goods1.setWeight(12);
		
		
		List<BLSCloudOrderGoods>blGoods=new ArrayList<BLSCloudOrderGoods>();
		BLSCloudOrderGoods bLSCloudOrderGoods=new BLSCloudOrderGoods();
		bLSCloudOrderGoods.setCount(2);
		bLSCloudOrderGoods.setGoods(goods);
		bLSCloudOrderGoods.setDynamicAttributes(bl);
		blGoods.add(bLSCloudOrderGoods);
		
		BLSCloudOrderGoods bLSCloudOrderGoods1=new BLSCloudOrderGoods();
		bLSCloudOrderGoods1.setCount(1);
		bLSCloudOrderGoods1.setGoods(goods1);
		bLSCloudOrderGoods1.setDynamicAttributes(bl);
		blGoods.add(bLSCloudOrderGoods1);
		
		BLSCloudShop shop =new BLSCloudShop ();
		shop.setShopName("百联滨江");
		shop.setStoreName("又一城");
		shop.setLogoImgUrl("http://res13.iblimg.com/respc-1/resources/v4.0/css/i/header-logo.png");
		shop.setShopCode("4a7693c804cc46dca757d4e22bdf783b");
		shop.setStoreCode("000002");
		shop.setStoreName("门店名称");
		shop.setStoreType("业态");
		
		
		BLSCloudOrder bLSCloudOrder=new BLSCloudOrder();
		bLSCloudOrder.setAddressId("310096953012781056");
		bLSCloudOrder.setDeliveryAmount(10);
		bLSCloudOrder.setOrderAmount(123);
		bLSCloudOrder.setPayType(123);
		bLSCloudOrder.setShop(shop);
		bLSCloudOrder.setSendType(0);
		bLSCloudOrder.setInvoiceType(0);
		bLSCloudOrder.setPayType(0);
		bLSCloudOrder.setDiscountAmount(0);
		bLSCloudOrder.setPayAmount(123);
		bLSCloudOrder.setGoodsList(blGoods);
		
		OrderDto dto=new OrderDto();
		dto.setMemberId("100000002715245");
		dto.setMemberToken("d6e240efa675882e7cc2ac77890403ee0105cca43601e666ab2977efdbe28e13");
		dto.setOrder(bLSCloudOrder);
		System.out.println(JSONObject.toJSONString(dto));
	}
}
