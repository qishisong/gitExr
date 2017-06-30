package com.blomni.o2o.order.mapper;

import java.util.List;

import com.blomni.o2o.order.dto.BLSCloudOrderGoods;
import com.blomni.o2o.order.entity.OrderDetails;
import com.blomni.o2o.order.vo.GoodVo;

public interface OrderDetailsMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderDetails record);

    int insertSelective(OrderDetails record);

    OrderDetails selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderDetails record);

    int updateByPrimaryKey(OrderDetails record);
    
    List<GoodVo> getGoodListByOrderId(String orderId);
    
    List<OrderDetails> selectByOrderId(String orderId);
    
   
   
}