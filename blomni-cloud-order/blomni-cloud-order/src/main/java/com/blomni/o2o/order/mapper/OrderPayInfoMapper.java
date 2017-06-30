package com.blomni.o2o.order.mapper;

import com.blomni.o2o.order.entity.OrderPayInfo;
import com.blomni.o2o.order.vo.QueryOrderDetailsVo;

public interface OrderPayInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderPayInfo record);

    int insertSelective(OrderPayInfo record);

    OrderPayInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderPayInfo record);

    int updateByPrimaryKey(OrderPayInfo record);
    
    QueryOrderDetailsVo selectPayInfoOrPayDate(String id);
}