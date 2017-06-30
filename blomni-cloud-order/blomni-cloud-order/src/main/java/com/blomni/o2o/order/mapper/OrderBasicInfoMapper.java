package com.blomni.o2o.order.mapper;

import java.util.List;

import com.blomni.o2o.order.dto.SelectQueryOrderDto;
import com.blomni.o2o.order.dto.GetCountForOrderStateDto;
import com.blomni.o2o.order.dto.QueryOrderListDto;
import com.blomni.o2o.order.entity.OrderBasicInfo;
import com.blomni.o2o.order.vo.BLSCloudOrderVo;
import com.blomni.o2o.order.vo.GetCountForOrderStateVo;
import com.blomni.o2o.order.vo.QueryOrderDetailsVo;
import com.blomni.o2o.order.vo.QueryOrderListVo;


public interface OrderBasicInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderBasicInfo record);

    int insertSelective(OrderBasicInfo record);

    OrderBasicInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderBasicInfo record);

    int updateByPrimaryKey(OrderBasicInfo record);
    
    
    List<OrderBasicInfo>selectByOrderBasicInfo(OrderBasicInfo record);
    
    int updateByOrderCode(OrderBasicInfo record);
    
    OrderBasicInfo selectByOrderCode(String orderCode);

    
    List<BLSCloudOrderVo>selectQueryOrderList(SelectQueryOrderDto dto);

    
    List<GetCountForOrderStateVo> getCountForOrderState(GetCountForOrderStateDto dto);
    
    List<QueryOrderListVo> getOrderList(QueryOrderListDto dto);

    
    QueryOrderDetailsVo selectQueryOrderDetails (SelectQueryOrderDto dto);

    
    List<String> selectByOrderStatus(String orderState,String currentDate);
    
    int updateBatchByOrderId(List<String> list);

}