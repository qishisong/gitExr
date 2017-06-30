package com.blomni.o2o.order.mapper;

import com.blomni.o2o.order.dto.AddressDto;
import com.blomni.o2o.order.dto.MemberAddressDto;
import com.blomni.o2o.order.entity.OrderReceiptAddress;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderReceiptAddressMapper {
    int countByExample(OrderReceiptAddress record);

    int deleteByPrimaryKey(String receiptId);

    int insert(OrderReceiptAddress record);

    int insertSelective(OrderReceiptAddress record);

    List<OrderReceiptAddress> selectByOrderReceiptAddress(OrderReceiptAddress record);

    OrderReceiptAddress selectByPrimaryKey(String receiptId);

    int updateByPrimaryKeySelective(OrderReceiptAddress record);

    int delectAddress(OrderReceiptAddress record);
    
    int updateByIsDefaultAddress(OrderReceiptAddress record);
    
    List<MemberAddressDto>  selectCloudMemberAddress(MemberAddressDto dto);
    
    int updateMerchantAddress(OrderReceiptAddress record);
    
}