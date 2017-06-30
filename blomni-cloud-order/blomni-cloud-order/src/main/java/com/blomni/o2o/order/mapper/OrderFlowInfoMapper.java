package com.blomni.o2o.order.mapper;

import java.util.List;

import com.blomni.o2o.order.entity.OrderFlowInfo;

public interface OrderFlowInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderFlowInfo record);

    int insertSelective(OrderFlowInfo record);

    OrderFlowInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderFlowInfo record);

    int updateByPrimaryKey(OrderFlowInfo record);
    
    List<OrderFlowInfo> selectByOrderId(String orderId);
}