package com.blomni.o2o.order.serviceImpl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.blomni.o2o.order.entity.OrderReceiptAddress;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.mapper.OrderReceiptAddressMapper;
import com.blomni.o2o.order.service.OrderReceiptAddressService;
import com.blomni.o2o.order.util.R;

/**
 * 
* @ClassName: OrderReceiptAddressServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zy 
* @date 2017年5月5日 下午2:25:27 
*
 */
@Service
public class OrderReceiptAddressServiceImpl  implements OrderReceiptAddressService{
	
	@Autowired
	private OrderReceiptAddressMapper orderReceiptAddressMapper;

	private static Logger logs = LoggerFactory.getLogger(OrderReceiptAddressServiceImpl.class);
	
	private int result=0;
	/**
	 * title:查询地址数量
	 * 2017年5月4日20:44:41
	 * zy
	 */
	public int countByExample(OrderReceiptAddress record) throws OrderServiceException {
		// TODO Auto-generated method stub
		result=orderReceiptAddressMapper.countByExample(record);
		return result;
	}


	/**
	 * title:添加地址
	 * 2017年5月4日20:44:41
	 * zy
	 */
	public int insertSelective(OrderReceiptAddress record) throws OrderServiceException {
		logs.info("OrderReceiptAddressServiceImpl. insertSelective ========START===={}"+JSONObject.toJSONString(record));
		
		// TODO Auto-generated method stub
		try {
			result= orderReceiptAddressMapper.insertSelective(record);
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("用户添加地址    {}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_insertAddress_errer.getValue(), R.ReturnCodeEnum.code_insertAddress_errer.getLabel());
			
		}
		logs.info("OrderReceiptAddressServiceImpl. insertSelective ========END===={}"+result);
		return result;
	}

	/**
	 * title:查询地址
	 * 2017年5月4日20:44:41
	 * zy
	 */
	public List<OrderReceiptAddress> selectByOrderReceiptAddress(OrderReceiptAddress record)
			throws OrderServiceException {
		logs.info("OrderReceiptAddressServiceImpl. selectByOrderReceiptAddress ========START===={}"+JSONObject.toJSONString(record));
		
		logs.info("OrderReceiptAddressServiceImpl. selectByOrderReceiptAddress ========END===={}"+JSONObject.toJSONString(record));
		
		// TODO Auto-generated method stub
		return orderReceiptAddressMapper.selectByOrderReceiptAddress(record);
	}

	/**
	 * title:查询单个地址
	 * 2017年5月4日20:44:41
	 * zy
	 */
	public OrderReceiptAddress selectByPrimaryKey(String receiptId) throws OrderServiceException {
		
		// TODO Auto-generated method stub
		logs.info("OrderReceiptAddressServiceImpl. selectByPrimaryKey ========START===={}"+JSONObject.toJSONString(receiptId));
		OrderReceiptAddress address=null;
		try {
			address=orderReceiptAddressMapper.selectByPrimaryKey(receiptId);
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("查询单个地址 {}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_selectAddress_errer.getValue(), R.ReturnCodeEnum.code_selectAddress_errer.getLabel());
			
		}
		
		logs.info("OrderReceiptAddressServiceImpl. selectByPrimaryKey ========END===={}"+JSONObject.toJSONString(receiptId));
		
		return address;
	}
	
	/**
	 * title:修改地址
	 * 2017年5月5日10:11:30
	 * zy
	 * 
	 */
	
	public int updateByPrimaryKeySelective(OrderReceiptAddress record) throws OrderServiceException {
		logs.info("OrderReceiptAddressServiceImpl. updateByPrimaryKeySelective ========START===={}"+JSONObject.toJSONString(record));
		
		logs.info("OrderReceiptAddressServiceImpl. updateByPrimaryKeySelective ========END===={}"+JSONObject.toJSONString(record));
		
		// TODO Auto-generated method stub
		return orderReceiptAddressMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * title:删除地址
	 * zy
	 * 2017年5月4日19:58:12
	 */
	@Override
	public int delectAddress(OrderReceiptAddress record) throws OrderServiceException {
		logs.info("OrderReceiptAddressServiceImpl. delectAddress ========START===={}"+JSONObject.toJSONString(record));
		
		
			// TODO Auto-generated method stub
			try {
				record.setDelFlag("1");
				record.setUpdateDate(new Date());
				result=orderReceiptAddressMapper.delectAddress(record);
			} catch (Exception e) {
				// TODO: handle exception
				logs.error("删除地址    {}",e);
				throw new OrderServiceException(R.ReturnCodeEnum.code_delectAddress_errer.getValue(), R.ReturnCodeEnum.code_delectAddress_errer.getLabel());
				
			}
		logs.info("OrderReceiptAddressServiceImpl. delectAddress ========START===={}"+JSONObject.toJSONString(record));
		
		return result;
	}


	@Override
	public int updateMerchantAddress(OrderReceiptAddress record) throws OrderServiceException {
		return orderReceiptAddressMapper.updateMerchantAddress(record);
	}
}
