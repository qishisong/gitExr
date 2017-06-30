package com.blomni.o2o.order.mapper;

import java.util.List;

import com.blomni.o2o.order.dto.MentionGoodsCodeDto;
import com.blomni.o2o.order.entity.OrderDeliveryCode;

public interface OrderDeliveryCodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderDeliveryCode record);

    int insertSelective(OrderDeliveryCode record);

    OrderDeliveryCode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderDeliveryCode record);

    int updateByPrimaryKey(OrderDeliveryCode record);
    
    List<OrderDeliveryCode> queryMentionGoodsCodeStatus(MentionGoodsCodeDto mentionGoodsCode);
    
    String queryOrderIdByMentionGoodsCode(MentionGoodsCodeDto mentionGoodsCode);
    
    int updateMentionGoodsCodeStatus(MentionGoodsCodeDto mentionGoodsCode);
    
    List<OrderDeliveryCode> queryMentionGoodsCodeByOrderNo(MentionGoodsCodeDto mentionGoodsCode);
    

	List<String> queryDeliverCode();
}