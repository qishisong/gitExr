package com.blomni.o2o.order.serviceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.BLSCloudOrderGoods;
import com.blomni.o2o.order.dto.BLSDynamicAttributes;
import com.blomni.o2o.order.dto.OrderDto;
import com.blomni.o2o.order.entity.OrderBasicInfo;
import com.blomni.o2o.order.entity.OrderDetails;
import com.blomni.o2o.order.entity.OrderFlowInfo;
import com.blomni.o2o.order.entity.OrderPayInfo;
import com.blomni.o2o.order.entity.OrderReceiptAddress;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.mapper.OrderBasicInfoMapper;
import com.blomni.o2o.order.mapper.OrderDetailsMapper;
import com.blomni.o2o.order.mapper.OrderFlowInfoMapper;
import com.blomni.o2o.order.mapper.OrderPayInfoMapper;
import com.blomni.o2o.order.service.OrderCloudMemeberNameService;
import com.blomni.o2o.order.service.OrderGenerateService;
import com.blomni.o2o.order.service.OrderReceiptAddressService;
import com.blomni.o2o.order.service.QueryGoodsPriceKeyService;
import com.blomni.o2o.order.util.MakeOrderNum;
import com.blomni.o2o.order.util.OrderDateUtils;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.util.SnowflakeIdWorker;
import com.blomni.o2o.order.vo.QueryGoodsPriceKeyVo;

/**
 * 
* @ClassName: OrderGenerateServiceImpl 
* @Description: TODO(生成订单) 
* @author zy 
* @date 2017年5月5日 下午8:19:00 
*
 */
@Service
public class OrderGenerateServiceImpl implements OrderGenerateService {
	private static Logger logs = LoggerFactory.getLogger(OrderGenerateServiceImpl.class);
	@Autowired
	private OrderBasicInfoMapper orderBasicInfoMapper;
	@Autowired
	private OrderDetailsMapper orderDetailsMapper;
	@Autowired
	private OrderReceiptAddressService orderReceiptAddressService;
	
	@Autowired
	private OrderFlowInfoMapper orderFlowInfoMapper;
	
	@Autowired
	private OrderPayInfoMapper orderPayInfoMapper;
	@Autowired
	private  OrderCloudMemeberNameService orderCloudMemeberNameService;
	
	@Autowired
	private  QueryGoodsPriceKeyService queryGoodsPriceKeyService;
	
	/**
	 * title:创建订单
	 * 2017年5月8日17:36:54
	 * zy
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = {
			OrderServiceException.class })
	@Override
	public Map<String, Object> orderGenerate(OrderDto dto) throws OrderServiceException {
		// TODO Auto-generated method stub
		logs.info("OrderGenerateServiceImpl.orderGenerate=====START=={}"+JSONObject.toJSONString(dto));
		String id=null;
		String orderNo=null;
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			id=new SnowflakeIdWorker(R.OrderConstant.Zero, R.OrderConstant.One).nextId()+"";//生成id
			orderNo=new MakeOrderNum().getOrderIdByUUId("1","1","1");//订单编号
			//获取会员昵称
			String memberName=orderCloudMemeberNameService.getmemberNickById(dto.getMemberId());
			dto.setMemberName(memberName);;
			int infoResult=insertOrderInfo(dto,id ,orderNo);//添加订单主表
			
			int infoDetails=insertOrderDetails(dto,id);//添加订单详情表
			//添加订单流水表
			addFlowInfo(dto,id);
			//添加支付信息表
			addPayInfo(dto,id);
			
			if(infoResult>R.OrderConstant.Zero&& infoDetails>R.OrderConstant.Zero){
				map.put("orderId", orderNo);
			}else{
				map.put("orderId", null);
			}
			
		}catch (OrderServiceException e1) {
			// TODO: handle exception
			logs.error("OrderGenerateServiceImpl.orderGenerate ======{}",e1);
			throw new OrderServiceException(e1.getMsg(), e1.getCode());
			
		}  catch (Exception e) {
			// TODO: handle exception
			logs.error("OrderGenerateServiceImpl.orderGenerate ======{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_order_fail.getValue(), R.ReturnCodeEnum.code_order_fail.getLabel());
			
		}
		logs.info("OrderGenerateServiceImpl.orderGenerate=====START=={}"+JSONObject.toJSONString(dto));
		return map;
	}
	
	/**
	 * title:添加订单主表  
	 * 2017年5月8日11:17:55
	 * zy
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = {
			OrderServiceException.class })
	public int insertOrderInfo(OrderDto dto,String id,String orderNo) throws OrderServiceException {
		logs.info("添加订单主表  OrderGenerateServiceImpl.insertOrderInfo=====START=={}"+JSONObject.toJSONString(dto));
		OrderBasicInfo info=null;
		int result=0;
		try {
			//订单主表赋值
			info=transformationInfo(dto);
			info.setId(id);//id
			info.setMemberId(dto.getMemberId());
			info.setOrderCode(orderNo);//订单编码
			
			result=orderBasicInfoMapper.insertSelective(info);
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("添加订单主表  OrderGenerateServiceImpl.insertOrderInfo ======{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_order_fail.getValue(), R.ReturnCodeEnum.code_order_fail.getLabel());
			
		}
		logs.info("添加订单主表  OrderGenerateServiceImpl.insertOrderInfo=====END=={}"+JSONObject.toJSONString(dto));
		
		// TODO Auto-generated method stub
		return result;
	}
	
	
	/**
	 * title:订单明细表信息 OrderDetails
	 * 2017年5月8日11:17:55
	 * zy
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = {
			OrderServiceException.class })
	public int insertOrderDetails(OrderDto dto,String id) throws OrderServiceException {
		logs.info("订单明细表信息OrderGenerateServiceImpl.insertOrderDetails=====START=={}"+JSONObject.toJSONString(dto));
		OrderDetails details=null;
		int result=0;
		try {
			details=new OrderDetails();
			//订单详情实体赋值
			details=transformationDetails(dto);
			
			details.setId(id);//
			details.setOrderId(id);//订单id
			result=orderDetailsMapper.insertSelective(details);
		}catch (OrderServiceException e1) {
			// TODO: handle exception
			logs.error("订单明细表信息OrderGenerateServiceImpl.insertOrderDetails ======{}",e1);
			throw new OrderServiceException(e1.getMsg(), e1.getCode());
			
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("订单明细表信息OrderGenerateServiceImpl.insertOrderDetails ======{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_order_fail.getValue(), R.ReturnCodeEnum.code_order_fail.getLabel());
			
		}
		logs.info("订单明细表信息OrderGenerateServiceImpl.insertOrderDetails=====END=={}"+JSONObject.toJSONString(dto));
		
		// TODO Auto-generated method stub
		return result;
	}
	/**
	 * 
	* @Title: transformationInfo 
	* @Description: TODO(订单主表赋值) 
	* @param @return    设定文件 
	* @return OrderBasicInfo    返回类型 
	* @date 2017年5月8日 下午3:53:24 
	* @author zy 
	* @throws
	 */
	public OrderBasicInfo transformationInfo(OrderDto dto)throws OrderServiceException {
		OrderBasicInfo info=null;
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			
		
			info=new OrderBasicInfo();
			info.setDelFlag("0");//
			
			map=memberAddress(dto.getOrder().getAddressId());//获取收货人信息
			info.setBuyerRemark(dto.getOrder().getOrderRemark()==null ? null:dto.getOrder().getOrderRemark());//买家备注  SELLER_REMARK 	string	N		备注
			//info.setClosingReason(closingReason);//关闭理由
			info.setConsigneeAddress(map.get("address").toString());//收货人地址/提货地址        调用接口
			info.setConsigneeName(map.get("name").toString());//收货人电话
			info.setConsigneePhone(map.get("phone").toString());//收货人姓名
			info.setCreateDate(new Date());///
			info.setInvoiceType(dto.getOrder().getInvoiceType()+"");///发票类型（0-不开发票 1-个人明细）
			info.setDeliveryTime(dto.getOrder().getSendTime()+""== "" ? null : dto.getOrder().getSendTime()+"");//配送时间方式sendTime	int	N		配送时间（0-双休日工作日均可 1-双休日 2-工作日）
			info.setDistributionMode(dto.getOrder().getSendType()+"");//配送方式  sendType	int	Y		配送方式（0-物流 1-自提）
			info.setMemberName(dto.getMemberName()==null?null:dto.getMemberName());//昵称
			info.setOrderExceptionFlag("N");//是否异常订单
			info.setOrderState("01");//订单状态
			info.setOutTime(new OrderDateUtils().CalendarDate(1));//超时时间当前时间加一小时
			info.setOrderSource("01");//订单来源   01 云店
			info.setOrderType("0");//订单类型          0实物
			//计算金额
			info=queryGoodsPriceKey(info,dto);
			
			}catch (OrderServiceException e1) {
				// TODO: handle exception
				logs.error("订单主表赋值OrderGenerateServiceImpl.transformationInfo ======{}",e1);
				throw new OrderServiceException(e1.getMsg(), e1.getCode());
				
			}  catch (Exception e) {
				// TODO: handle exception
				logs.info("订单主表赋值==transformationInfo{}",e);
				throw new OrderServiceException(R.ReturnCodeEnum.code_param.getValue(), R.ReturnCodeEnum.code_param.getLabel());
				
			}
		
		return info;
		
	}
	/**
	 * 
	* @Title: queryGoodsPriceKey 
	* @Description: TODO(计算订单金额) 
	* @param @param info
	* @param @param dto
	* @param @return
	* @param @throws OrderServiceException    设定文件 
	* @return OrderBasicInfo    返回类型 
	* @date 2017年5月11日 下午12:53:57 
	* @author zy 
	* @throws
	 */
	//QueryGoodsPriceKeyService
	public OrderBasicInfo queryGoodsPriceKey(OrderBasicInfo info,OrderDto dto)throws OrderServiceException{

		logs.info("queryGoodsPriceKey======START=={}"+JSONObject.toJSONString(info));
		double carriageMoney=0;//运费
		double orderAmt=0;//订单金额
		double shouldMoney=0;//优惠金额 
		String goodsCommId=null;
		
		QueryGoodsPriceKeyVo  vo=null;
		if(null==info){
			 info=new OrderBasicInfo();
		}
		
		try {
			goodsCommId= dto.getOrder().getGoodsList().get(0).getGoods().getProductId();//商品id
			vo=queryGoodsPriceKeyService.queryGoodsPriceKey(goodsCommId);
			//运费
			carriageMoney=dto.getOrder().getDeliveryAmount()==0?0:dto.getOrder().getDeliveryAmount();
			info.setCarriageMoney(BigDecimal.valueOf(carriageMoney));//运费   deliveryAmount	double	Y
			
			//优惠金额
			shouldMoney=dto.getOrder().getDiscountAmount()==0?0:dto.getOrder().getDiscountAmount();
			
			if(null!=vo){
				//数量
				int count=dto.getOrder().getGoodsList().get(0).
						  getCount()==0?0:dto.getOrder().getGoodsList().get(0).getCount();
				
				orderAmt=(count*vo.getSellingPrice());
				info.setOrderAmt(BigDecimal.valueOf(orderAmt));//订单金额   orderAmount	double	Y		商品总额
				info.setShouldMoney(BigDecimal.valueOf((orderAmt-shouldMoney+carriageMoney)));//应付金额    payAmount	double	Y		订单应（实）付金额
				info.setFavorableMoney(BigDecimal.valueOf(shouldMoney));//优惠金额    discountAmount	double	Y		优惠金额（如优惠86元，则为86，不是-86，如果为空传0）
			}
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("queryGoodsPriceKey{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_queryGoodsPriceKey_errer.getValue(), R.ReturnCodeEnum.code_queryGoodsPriceKey_errer.getLabel());
			
		}
		logs.info("queryGoodsPriceKey======START=={}"+JSONObject.toJSONString(info));
		
		return info;
	}
	
	/**
	 * 
	* @Title: Transformation 
	* @Description: TODO(订单详情实体赋值) 
	* @param @param dto
	* @param @return    设定文件 
	* @return OrderDetails    返回类型 
	* @date 2017年5月8日 下午3:46:48 
	* @author zy 
	* @throws
	 */
	
	public OrderDetails  transformationDetails (OrderDto dto)throws OrderServiceException{
		logs.info("订单明细表信息OrderGenerateServiceImpl.transformationDetails=====START=={}"+JSONObject.toJSONString(dto));
		
		OrderDetails details=null;
		List<BLSDynamicAttributes> list=null;
		List<BLSCloudOrderGoods> goodsList=null;
		try {
			details=new OrderDetails();
			details.setCreateDate(new Date());
			details.setDelFlag("0");//
			/*details.setBrandName(dto.);//品牌名称
			details.setDiscountPrice(discountPrice);//'商品折扣价
			details.setDropPrice(dropPrice);//商品吊牌价
			details.setGoodsName(dto.getOrder().get);//商品名称
			details.setGoodsStandard(goodsStandard);//商品规格
			 */			
			//商铺信息
			details.setCommercialType(dto.getOrder().getShop().getStoreType());//业态类型 storeType	string	Y		业态
			details.setMerchantId(dto.getOrder().getShop().getShopCode());//商户id shopCode	string	Y		商户编码
			details.setMerchantLogo(dto.getOrder().getShop().getLogoImgUrl());//商户Logo logoImgUrl	string	Y		商户logo
			details.setMerchantName(dto.getOrder().getShop().getShopName());//商户名称 shopName	string	Y		商户名称
			details.setStoreCode(dto.getOrder().getShop().getStoreCode());//门店编码   storeCode	string	Y		门店编码
			details.setStoreName(dto.getOrder().getShop().getStoreName());//门店名称 storeName	string	Y		门店名称
			
			//商品信息
			goodsList=dto.getOrder().getGoodsList();
			
			if(goodsList!=null&&goodsList.size()>0){
				int goodsListSize=goodsList.size();
				for (int i = 0; i < goodsListSize; i++) {
					
					details.setGoodsId(goodsList.get(i).getGoods().getProductId());//商品id'    productId	string	Y		商品信息
					details.setNormQualityName(goodsList.get(i).getGoods().getGoodsStandaName());//标准品名  goodsStandaName
					details.setBreviaryQualityName(goodsList.get(i).getGoods().getGoodsSalesName());//缩略品名  goodsSalesName	string	Y		商品缩略名
					details.setMarketPrice(BigDecimal.valueOf(goodsList.get(i).getGoods().getGoodsPrice()));//销售价格   goodsPrice
					//details.setDiscountPrice(goodsList.get(i).getGoods().get);//'商品折扣价
					details.setOriginalPrice((BigDecimal.valueOf(goodsList.get(i).getGoods().getMarketPrice())));//商品原价格  marketPrice
					details.setGoodsNum(goodsList.get(i).getCount());//商品数量   count	int	Y		数量
					details.setGoodsImg(goodsList.get(i).getGoods().getImageUrl());//商品主图   imageUrl
					details.setWeight(goodsList.get(i).getGoods().getWeight());//商品重量   weight
					
					
					 }
				//sku属性   dynamicAttributes	list [BLSDynamicAttributes]	Y		最子级的动态属性列表
				 list=dto.getOrder().getGoodsList().get(0).getDynamicAttributes();
				 String attributeId = null;
				 String attributeName = null;
				 StringBuilder str = new StringBuilder();
				 StringBuilder str1 = new StringBuilder();
				 logs.info("sku属性 ---->"+JSONObject.toJSONString(list));
				 if(list!=null&&list.size()>0){
					 int listSize=list.size();
					for (int j = 0; j < listSize; j++) {
						if(j<listSize-1){
							 str.append(list.get(j).getAttributeId()+",");
						     str1.append(list.get(j).getAttributeName()+",");
						}else{
							str.append(list.get(j).getAttributeId().toString());
						    str1.append(list.get(j).getAttributeName().toString());
						}
				       
				        // 将StringBuilder对象转换为String对象并输出
				        attributeId=str.toString();
				        attributeName=str1.toString();
				       
					}
					 str=null;
					 str1=null;
					details.setSkuId(attributeId);//skuid 逗号隔开
					details.setSkuName(attributeName);//sku 名称'
					
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("订单详情实体赋值OrderGenerateServiceImpl.transformationDetails ======{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_param.getValue(), R.ReturnCodeEnum.code_param.getLabel());
			
		}
		logs.info("订单明细表信息OrderGenerateServiceImpl.transformationDetails=====END=={}"+JSONObject.toJSONString(dto));
		
		return details;
		
	}
	/**
	 * @throws OrderServiceException 
	 * 
	* @Title: memberAddress 
	* @Description: TODO(查询用户收货地址) addressId
	* @param @return    设定文件 
	* @return String    返回类型 
	* @date 2017年5月8日 下午4:22:35 
	* @author zy 
	* @throws
	 */
	public Map<String, Object> memberAddress(String addressId) throws OrderServiceException{
		OrderReceiptAddress details=null;
		String address=null;
		logs.info("OrderGenerateServiceImpl.memberAddress =========START=={}"+addressId);
		Map<String, Object> map=null;
		if(StringUtils.isNotBlank(addressId)){
			map=new HashMap<String, Object>();
			//调用地址表
			details=orderReceiptAddressService.selectByPrimaryKey(addressId);
			StringBuilder str = new StringBuilder();
			str.append(details.getReceiptProvinceName()+"-");//RECEIPT_PROVINCE_NAME 收件人省名称',
			str.append(details.getReceiptCityName()+"-");//RECEIPT_CITY_NAME 收件人市名称,
			str.append(details.getReceiptAreaName()+"-");//RECEIPT_AREA_NAME 收件人区名称',
			str.append(details.getReceiptDetailAddress());//RECEIPT_DETAIL_ADDRESS 收件人详细地址
			address=str.toString();
			map.put("address", address);
			map.put("phone", details.getReceiptPhone());
			map.put("name", details.getReceiptName());
		logs.info("OrderGenerateServiceImpl.memberAddress =========START=={}"+addressId);
		}else{
			throw new OrderServiceException("addressId "+R.ReturnCodeEnum.code_req_msg.getValue(), R.ReturnCodeEnum.code_req_msg.getLabel());
			
		}
		return map;
	}
	
	/**
	 * 
	* @Title: addFlowInfo 
	* @Description: TODO(添加订单流水表) 
	* @param @return    设定文件 
	* @return int    返回类型 
	* @date 2017年5月9日 下午8:08:39 
	* @author zy 
	* @throws
	 */
	public int addFlowInfo (OrderDto dto,String id)throws OrderServiceException{
		logs.info("添加订单流水表  addFlowInfo======START ===={}"+JSONObject.toJSONString(dto));
		OrderFlowInfo info =null;
		int result=0;
		try {
			String infoId=new SnowflakeIdWorker(R.OrderConstant.Zero, R.OrderConstant.One).nextId()+"";
			info =new OrderFlowInfo();
			info.setCreateDate(new Date());
			info.setOrderId(id);
			info.setId(infoId);
			info.setMemberId(dto.getMemberId());
			info.setDelFlag("0");
			info.setMemberName(dto.getMemberName());
			info.setOrderAmt(BigDecimal.valueOf(dto.getOrder().getPayAmount()));
			info.setOrderSource("01");
			info.setOrderType("01");
			info.setIsAbnormalFlag("N");
			info.setSellerRemark(dto.getOrder().getOrderRemark());
			result=orderFlowInfoMapper.insertSelective(info);
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("添加订单流水表 addFlowInfo{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_AddOrderinFo_errer.getValue(), R.ReturnCodeEnum.code_AddOrderinFo_errer.getLabel());
			
		}
		logs.info("添加订单流水表  addFlowInfo======END ====={}"+result);
		return result;
	}
	
	/**
	 * 
	* @Title: addFlowInfo 
	* @Description: TODO(添加支付信息表) 
	* @param @return    设定文件 
	* @return int    返回类型 
	* @date 2017年5月9日 下午8:08:39 
	* @author zy 
	* @throws
	 */
	public int addPayInfo (OrderDto dto,String id)throws OrderServiceException{
		logs.info("添加支付信息表 addPayInfo======START ===={}"+JSONObject.toJSONString(dto));
		OrderPayInfo info =null;
		int result=0;
		try {
			String infoId=new SnowflakeIdWorker(R.OrderConstant.Zero, R.OrderConstant.One).nextId()+"";
			info =new OrderPayInfo();
			info.setCreateDate(new Date());
			info.setId(infoId);
			info.setOrderId(id);
			info.setDelFlag("0");
			info.setPayStatus("0");//
			info.setTransactionMode(dto.getOrder().getPayType()+"");
			result=orderPayInfoMapper.insertSelective(info);
			
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("添加支付信息表 addPayInfo{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_AddPayinFo_errer.getValue(), R.ReturnCodeEnum.code_AddPayinFo_errer.getLabel());
			
		}
		logs.info("添加支付信息表 addPayInfo======END ====={}"+result);
		return result;
	}
}
