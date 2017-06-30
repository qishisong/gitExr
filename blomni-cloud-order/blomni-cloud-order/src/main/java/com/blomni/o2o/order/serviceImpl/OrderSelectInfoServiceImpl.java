package com.blomni.o2o.order.serviceImpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.dto.BLSCloudGoods;
import com.blomni.o2o.order.dto.BLSCloudOrderGoods;
import com.blomni.o2o.order.dto.BLSCloudShop;
import com.blomni.o2o.order.dto.BLSDynamicAttributes;
import com.blomni.o2o.order.dto.SelectQueryOrderDto;
import com.blomni.o2o.order.entity.OrderDetails;
import com.blomni.o2o.order.entity.OrderPayInfo;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.mapper.OrderBasicInfoMapper;
import com.blomni.o2o.order.mapper.OrderDetailsMapper;
import com.blomni.o2o.order.mapper.OrderPayInfoMapper;
import com.blomni.o2o.order.service.OrderSelectInfoService;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.vo.BLSCloudOrderVo;
import com.blomni.o2o.order.vo.QueryOrderDetailsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
* @ClassName: OrderSelectInfoServiceImpl 
* @Description: TODO(查询订单) 
* @author zy 
* @date 2017年5月9日 上午10:27:27 
*
 */
@Service
public class OrderSelectInfoServiceImpl implements OrderSelectInfoService{
	private static Logger logs = LoggerFactory.getLogger(OrderSelectInfoServiceImpl.class);
	
	@Autowired
	private OrderBasicInfoMapper orderBasicInfoMapper;
	@Autowired
	private OrderDetailsMapper orderDetailsMapper;
	
	@Autowired
	private OrderPayInfoMapper orderPayInfoMapper;
	/**
	 * title:1605 查询订单列表
	 * 2017年5月9日10:31:10
	 * zy
	 *
	 */
	@Override
	public Map<String, Object> selectQueryOrderList(SelectQueryOrderDto dto) throws OrderServiceException {
		logs.info("OrderSelectInfoServiceImpl.selectQueryOrderList=========START==={}"+JSONObject.toJSONString(dto));
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		 List<BLSCloudOrderVo> list=null;
		 BLSCloudOrderGoods goods=null;
		 OrderDetails details=null;
		 List<BLSCloudOrderVo> orderList=null;
		 List<BLSCloudOrderGoods> goodsList=null;
		 int pageNo=dto.getPageNo();
		 int pageSize=dto.getPageSize();
		 PageHelper.startPage(pageNo, pageSize);
		try {
			
			list=new ArrayList<BLSCloudOrderVo>();
			orderList=orderBasicInfoMapper.selectQueryOrderList(dto);
			logs.info("orderBasicInfoMapper.selectQueryOrderList======={}"+JSONObject.toJSONString(orderList));	
			 if(null!=orderList&&orderList.size()>0){
				
				
				
				for (BLSCloudOrderVo vo : orderList) {
					goods=new BLSCloudOrderGoods();
					goodsList=new ArrayList<BLSCloudOrderGoods>();
					details=selectByPrimaryKey(vo.getId());//查询订单详情 商户信息 和子属性
					
					if(null!=details){
						//添加商品信息
						BLSCloudGoods blGoods=assignmentGoods(details);
						goods.setGoods(blGoods);
						//商品数量
						goods.setCount(details.getGoodsNum());
						//添加商户信息
						BLSCloudShop shop=assignmentShop(details);
						vo.setShop(shop);
						
						//添加子属性
						List<BLSDynamicAttributes>  dynamicAttributes=divisionAttribute(details.getSkuId(),details.getSkuName());//分割单个订单子属性
						goods.setDynamicAttributes(dynamicAttributes);
						
						goodsList.add(goods);//添加订单详情到 
						vo.setGoodsList(goodsList);//商品列表
						list.add(vo);
						goods=null;
						goodsList=null;
					}
				}
				
				PageInfo info = new PageInfo(list);
				map.put("orderList", info.getList());
				map.put("pageNo", info.getPageNum());
				map.put("pageSize", info.getPageSize());
				map.put("totalPage", info.getPages());
				map.put("count", info.getTotal());
				
			 }else{
				 	map.put("orderList", null);
					map.put("pageNo", pageNo);
					map.put("pageSize",pageSize);
					map.put("totalPage", 0);
					map.put("count", 0);
					
				 logs.info("orderBasicInfoMapper.selectQueryOrderList======={}"+JSONObject.toJSONString(orderList));	
			 }
		}catch (OrderServiceException e1) {
			// TODO: handle exception
			logs.error("OrderGenerateServiceImpl.orderGenerate ======{}",e1);
			throw new OrderServiceException(e1.getMsg(), e1.getCode());
			
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("selectQueryOrderList{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_queryOrderList_errer.getValue(), R.ReturnCodeEnum.code_queryOrderList_errer.getLabel());
			
		}
		logs.info("OrderSelectInfoServiceImpl.selectQueryOrderList=========END==={}"+JSONObject.toJSONString(list));
		return map;
	}
	
	/**
	 * title:1606 查询订单详情
	 * 2017年5月9日10:31:10
	 * zy
	 */
	@Override
	public QueryOrderDetailsVo selectQueryOrderDetails(SelectQueryOrderDto dto) throws OrderServiceException {
		logs.info("OrderSelectInfoServiceImpl.selectQueryOrderDetails=========START==={}"+JSONObject.toJSONString(dto));
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		QueryOrderDetailsVo QueryDetails=null;
		OrderDetails  details=null;
		List<BLSCloudOrderGoods> goodsList=	null;
		BLSCloudOrderGoods goods=null;
		try {
			QueryDetails=new QueryOrderDetailsVo();
			goodsList=new ArrayList<BLSCloudOrderGoods>();
			goods=new BLSCloudOrderGoods();
			
			QueryDetails=orderBasicInfoMapper.selectQueryOrderDetails(dto);
			if(StringUtils.isNotBlank(QueryDetails.getId())){
				details=selectByPrimaryKey(QueryDetails.getId());//查询订单详情 商户信息 和子属性
				if(null!=details){
					//添加商品信息
					BLSCloudGoods blGoods=assignmentGoods(details);
					goods.setGoods(blGoods);
					
					//商品数量
					goods.setCount(details.getGoodsNum());
					
					//添加支付方式 和  支付时间
					QueryOrderDetailsVo result =selectPayInfoOrPayDate(QueryDetails.getId());
					QueryDetails.setPayType(result.getPayType()==0?0:result.getPayType());
					QueryDetails.setPayTime(result.getPayTime()==null?null:result.getPayTime());
					
					//添加商户信息
					BLSCloudShop shop=assignmentShop(details);
					QueryDetails.setShop(shop);
					//过期时间
					QueryDetails.setTimeout(Integer.parseInt(R.OrderConstant.PAY_OUT_TIME_SECOND));
					
					//添加子属性
					List<BLSDynamicAttributes>  dynamicAttributes=divisionAttribute(details.getSkuId(),details.getSkuName());//分割单个订单子属性
					goods.setDynamicAttributes(dynamicAttributes);
					
					//商品列表
					goodsList.add(goods);
					
					QueryDetails.setGoodsList(goodsList);
				}
			}
			//map.put("orderDetails", QueryDetails);
			
		}catch (OrderServiceException e1) {
			// TODO: handle exception
			logs.error("OrderSelectInfoServiceImpl.selectQueryOrderDetails ======{}",e1);
			throw new OrderServiceException(e1.getMsg(), e1.getCode());
			
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("selectQueryOrderDetails",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_queryOrderList_errer.getValue(), R.ReturnCodeEnum.code_queryOrderList_errer.getLabel());
			
		}
		
		logs.info("OrderSelectInfoServiceImpl.selectQueryOrderDetails=========END==={}"+JSONObject.toJSONString(map));
		
		return QueryDetails;
	}
	/**
	 * 
	* @Title: divisionAttribute 
	* @Description: TODO(查询单个订单详情) 
	* @param @param skuId
	* @param @param skuName
	* @param @return    设定文件 
	* @return List<BLSDynamicAttributes>    返回类型 
	* @date 2017年5月9日 下午2:47:29 
	* @author zy 
	* @throws
	 */
	public OrderDetails selectByPrimaryKey(String id )throws OrderServiceException{
		 OrderDetails details=null;
		
		if(!"".equals(id)||null!=id){
			try {
				details=new OrderDetails();
				details= orderDetailsMapper.selectByPrimaryKey(id);//查询订单详情 商户信息 和子属性
			} catch (Exception e) {
				// TODO: handle exception
				logs.error("查询订单详情 selectByPrimaryKey   {}",e);
				throw new OrderServiceException(R.ReturnCodeEnum.code_queryOrderList_errer.getValue(), R.ReturnCodeEnum.code_queryOrderList_errer.getLabel());
				
			}
		}
		return details;
	}
	/**
	 * 
	* @Title: AssignmentShop 
	* @Description: TODO(添加 商品信息) 
	* @param @return    设定文件 
	* @return BLSCloudShop    返回类型 
	* @date 2017年5月9日 下午3:04:59 
	* @author zy 
	* @throws
	 */
	public BLSCloudGoods  assignmentGoods( OrderDetails details)throws OrderServiceException{
		BLSCloudGoods blGoods=null;
		logs.info("添加 商品信息AssignmentGoods====START==========={}"+JSONObject.toJSONString(details));
		try {
			blGoods=new BLSCloudGoods();
			double goodsPrice= details.getMarketPrice().doubleValue();
			blGoods.setGoodsPrice(goodsPrice==0?0:goodsPrice);//	double	Y		销售价
			blGoods.setGoodsSalesName(details.getBreviaryQualityName());//	string	Y		商品缩略名
			blGoods.setGoodsStandaName(details.getNormQualityName());//string	Y		商品标准名
			blGoods.setImageUrl(details.getGoodsImg());//	string	Y		图片
			double marketPrice=details.getOriginalPrice().doubleValue();
			blGoods.setMarketPrice(marketPrice==0?0:marketPrice);//double	Y		市场价
			blGoods.setProductId(details.getGoodsId());//	string	Y		商品信息
			blGoods.setWeight(details.getWeight());//double	Y		重量（kg）weight
			} catch (Exception e) {
				// TODO: handle exception
				logs.info("添加 商品信息 assignmentGoods{}",e);
				throw new OrderServiceException(R.ReturnCodeEnum.code_queryOrderList_errer.getValue(), R.ReturnCodeEnum.code_queryOrderList_errer.getLabel());
				
			}
		logs.info("添加 商品信息AssignmentGoods=====END==========={}"+JSONObject.toJSONString(blGoods));
		
		return blGoods;
	}
	
	/**
	 * 
	* @Title: AssignmentShop 
	* @Description: TODO(添加商户信息) 
	* @param @return    设定文件 
	* @return BLSCloudShop    返回类型 
	* @date 2017年5月9日 下午3:04:59 
	* @author zy 
	* @throws
	 */
	public BLSCloudShop assignmentShop( OrderDetails details)throws OrderServiceException{
		logs.info("添加商户信息AssignmentShop====START==========={}"+JSONObject.toJSONString(details));
		BLSCloudShop shop=null;
		
		try {
			shop=new BLSCloudShop();
			shop.setLogoImgUrl(details.getMerchantLogo());
			shop.setShopCode(details.getMerchantId());
			shop.setShopName(details.getMerchantName());
			shop.setStoreCode(details.getStoreCode());
			shop.setStoreName(details.getStoreName());
			shop.setStoreType(details.getCommercialType());
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("添加商户信息 assignmentShop{}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_queryOrderList_errer.getValue(), R.ReturnCodeEnum.code_queryOrderList_errer.getLabel());
			
		}
		logs.info("添加商户信息AssignmentShop====END==========={}"+JSONObject.toJSONString(shop));
		return shop;
		
	}
	
	
	
	/**
	 * title:分割商品属性
	 * zy
	 * 2017年5月9日14:47:06
	 */
	public List<BLSDynamicAttributes>divisionAttribute(String skuId ,String skuName){
		logs.info("分割商品属性======START===skuId{}"+skuId+"=={}"+skuName);
		List<BLSDynamicAttributes>list =null;
		BLSDynamicAttributes bl=null;
		
		list=new ArrayList<BLSDynamicAttributes>();
		 String[] strId=convertStrToArray(skuId);
		 String[] strName=convertStrToArray(skuName);
		 for (int i = 0; i < strId.length; i++) {
			 bl=new BLSDynamicAttributes();
			 bl.setAttributeId(strId[i]);
			 bl.setAttributeName(strName[i]);
			 list.add(bl);
		}
		 logs.info("分割商品属性======START===list{}"+JSONObject.toJSONString(list));
		return list;  
	}
	
	//使用String的split 方法   
    public static String[] convertStrToArray(String str){   
        String[] strArray = null;   
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
        return strArray;
    }   
    /**
     * 
    * @Title: selectPayInfoOrPayDate 
    * @Description: TODO(查询付款方式和查询付款时间) 
    * @param @return    设定文件 
    * @return String    返回类型 
    * @date 2017年5月10日 下午1:47:30 
    * @author zy 
    * @throws
     */
    public QueryOrderDetailsVo selectPayInfoOrPayDate(String id ){
    	logs.info("selectPayInfoOrPayDate======START===={}"+id);
    	String result=null;
    	Map<String, Object> map = new HashMap<String, Object>();
    	QueryOrderDetailsVo info=null;
    	
    	try {
    		info=new QueryOrderDetailsVo();
    		info=orderPayInfoMapper.selectPayInfoOrPayDate(id);
        	
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("selectPayInfoOrPayDate{}",e);
		}
    	
    	logs.info("selectPayInfoOrPayDate======END===={}"+JSONObject.toJSONString(info));
		return info;
    	
    }

}
