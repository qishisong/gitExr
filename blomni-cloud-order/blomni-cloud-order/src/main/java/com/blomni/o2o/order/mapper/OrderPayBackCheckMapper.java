package com.blomni.o2o.order.mapper;

import com.blomni.o2o.order.entity.OrderPayBackCheck;
import com.blomni.o2o.order.entity.OrderPayBackCheckKey;

public interface OrderPayBackCheckMapper {
    int deleteByPrimaryKey(OrderPayBackCheckKey key);

    int insert(OrderPayBackCheck record);

    int insertSelective(OrderPayBackCheck record);

    OrderPayBackCheck selectByPrimaryKey(OrderPayBackCheckKey key);

    int updateByPrimaryKeySelective(OrderPayBackCheck record);

    int updateByPrimaryKey(OrderPayBackCheck record);
}