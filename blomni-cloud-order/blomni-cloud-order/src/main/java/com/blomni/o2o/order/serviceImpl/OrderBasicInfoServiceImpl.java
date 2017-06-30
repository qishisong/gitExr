package com.blomni.o2o.order.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bailian.core.utils.DateUtil;
import com.blomni.o2o.order.dto.CancelOrderDto;
import com.blomni.o2o.order.dto.GetCountForOrderStateDto;
import com.blomni.o2o.order.dto.QueryMerchantOrderDetailsDto;
import com.blomni.o2o.order.dto.QueryOrderListDto;
import com.blomni.o2o.order.dto.ReceiveOrderDto;
import com.blomni.o2o.order.dto.UpdateOrderInfoDto;
import com.blomni.o2o.order.dto.UpdateOrderStateDto;
import com.blomni.o2o.order.entity.OrderBasicInfo;
import com.blomni.o2o.order.entity.OrderDeliveryCode;
import com.blomni.o2o.order.entity.OrderDetails;
import com.blomni.o2o.order.entity.OrderFlowInfo;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.mapper.OrderBasicInfoMapper;
import com.blomni.o2o.order.mapper.OrderDeliveryCodeMapper;
import com.blomni.o2o.order.mapper.OrderDetailsMapper;
import com.blomni.o2o.order.mapper.OrderFlowInfoMapper;
import com.blomni.o2o.order.service.OrderBasicInfoService;
import com.blomni.o2o.order.service.OrderMentionGoodsCodeService;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.util.SnowflakeIdWorker;
import com.blomni.o2o.order.vo.GetCountForOrderStateVo;
import com.blomni.o2o.order.vo.GoodVo;
import com.blomni.o2o.order.vo.MerchantOrderDetailsFlowVo;
import com.blomni.o2o.order.vo.MerchantOrderDetailsGoodsVo;
import com.blomni.o2o.order.vo.MerchantOrderDetailsVo;
import com.blomni.o2o.order.vo.QueryOrderListVo;
import com.github.pagehelper.PageInfo;

@Service
public class OrderBasicInfoServiceImpl implements OrderBasicInfoService {
	
	private static Logger logs = LoggerFactory.getLogger(OrderBasicInfoServiceImpl.class);
	
	@Autowired
	private OrderBasicInfoMapper orderBasicInfoMapper;

	@Autowired
	private OrderFlowInfoMapper orderFlowInfoMapper;

	@Autowired
	private OrderDetailsMapper orderDetailsMapper;

	@Autowired
	private OrderDeliveryCodeMapper OrderDeliveryCodeMapper;
	
	@Autowired
	private OrderMentionGoodsCodeService orderMentionGoodsCodeService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = {
			OrderServiceException.class })
	public int receiveOrder(ReceiveOrderDto receiveOrderDto) throws OrderServiceException {
		int count = 0;
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 2);
		OrderBasicInfo orderBasicInfo = orderBasicInfoMapper.selectByOrderCode(receiveOrderDto.getOrderNo());
		if (orderBasicInfo != null) {
			if (orderBasicInfo.getOrderState().equals(R.OrderStateEnum.orderState_waiting_billing.getValue())
					&& orderBasicInfo.getOutTime().after(new Date())) {
				
				orderBasicInfo.setSalesNumber(receiveOrderDto.getSalesSlipNo());
				orderBasicInfo.setReceiptUrl(receiveOrderDto.getImgUrl());
				orderBasicInfo.setUpdateBy(receiveOrderDto.getMemberId());
				orderBasicInfo.setUpdateDate(new Date());
				if (orderBasicInfo.getDistributionMode()
						.equals(R.DistributionModeEnum.distributionMode_logistics.getValue())) {
					orderBasicInfo.setOrderState(R.OrderStateEnum.orderState_prepare_shipments.getValue());
				} else {
					orderBasicInfo.setOrderState(R.OrderStateEnum.orderState_prepare_selfTake.getValue());
				}
				count = orderBasicInfoMapper.updateByPrimaryKeySelective(orderBasicInfo);

				if (orderBasicInfo.getDistributionMode()
						.equals(R.DistributionModeEnum.distributionMode_selfTake.getValue())) {
					// TODO 生成提货码
					this.gengerateDeliveryCode(orderBasicInfo.getId(),receiveOrderDto.getMemberId());
				}
				// 添加订单流水记录
				OrderFlowInfo ofi = new OrderFlowInfo();
				ofi.setId(idWorker.nextId() + "");
				ofi.setCreateBy(receiveOrderDto.getMemberId());
				ofi.setCreateDate(new Date());
				ofi.setOrderId(orderBasicInfo.getId());
				ofi.setOrderState(orderBasicInfo.getOrderState());
				ofi.setDelFlag(R.OrderConstant.Zero + "");
				ofi.setMemberId(receiveOrderDto.getMemberId());
				ofi.setMemberName(orderBasicInfo.getMemberName());
				ofi.setOrderType(orderBasicInfo.getOrderType());
				ofi.setIsAbnormalFlag(orderBasicInfo.getOrderExceptionFlag());
				orderFlowInfoMapper.insertSelective(ofi);
			}
		}
		return count;
	}

	private int gengerateDeliveryCode(String id, String memberId) throws OrderServiceException {
		int count=0;
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 2);
		OrderDeliveryCode record=new OrderDeliveryCode();
		record.setId(idWorker.nextId()+"");
		record.setDeliveryCode(orderMentionGoodsCodeService.getRandomNumber());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 5);
		record.setInvalidDate(cal.getTime());
		record.setCreateBy(memberId);
		record.setOrderId(id);
		record.setCreateDate(new Date());
		record.setDeliveryStatus(R.OrderConstant.Zero+"");
		record.setDelFlag(R.OrderConstant.Zero+"");
		count=OrderDeliveryCodeMapper.insert(record);
		return count;
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = {OrderServiceException.class })
	public int reqNewCancelOrderState(CancelOrderDto dto) throws OrderServiceException {
		int num = 0;
		OrderBasicInfo obi = orderBasicInfoMapper.selectByOrderCode(dto.getOrderId());
		if (obi == null) {
			throw new OrderServiceException(R.ReturnCodeEnum.code_notFindOrderInfo_errer.getValue(),
					R.ReturnCodeEnum.code_notFindOrderInfo_errer.getLabel());
		}
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 2);
		try {
			// 1、更新订单表状态
			OrderBasicInfo info = new OrderBasicInfo();
			info.setOrderCode(dto.getOrderId());
			info.setOrderState(R.OrderStateEnum.orderState_closed.getValue());
			info.setUpdateBy(dto.getMemberId());
			info.setUpdateDate(new Date());
			num = orderBasicInfoMapper.updateByOrderCode(info);
			
			// 2、插入到流水表
			OrderFlowInfo ofi = new OrderFlowInfo();
			ofi.setId(idWorker.nextId() + "");
			ofi.setCreateBy(dto.getMemberId());
			ofi.setCreateDate(new Date());
			ofi.setOrderId(dto.getOrderId());
			ofi.setOrderState(R.OrderStateEnum.orderState_closed.getValue());
			ofi.setDelFlag(R.OrderConstant.Zero + "");
			ofi.setMemberId(dto.getMemberId());
			ofi.setMemberName(obi.getMemberName());
			ofi.setOrderType(obi.getOrderType());
			ofi.setIsAbnormalFlag(obi.getOrderExceptionFlag());
			orderFlowInfoMapper.insertSelective(ofi);
		} catch (Exception e) {
			logs.error(R.OrderErrorEnum.ERROR_DATABASE.getLabel()+"={}",e);
			throw new OrderServiceException(R.OrderErrorEnum.ERROR_DATABASE.getValue(),R.OrderErrorEnum.ERROR_DATABASE.getLabel());
		}

		return num;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
	public MerchantOrderDetailsVo queryMerchantOrderDetailsByOrderNo(QueryMerchantOrderDetailsDto dto) {
		MerchantOrderDetailsVo vo = new MerchantOrderDetailsVo();
		// 1、先查主表的信息
		OrderBasicInfo info = this.orderBasicInfoMapper.selectByOrderCode(dto.getOrderNo());
		List<MerchantOrderDetailsGoodsVo> goodsList = new ArrayList<MerchantOrderDetailsGoodsVo>();
		List<MerchantOrderDetailsFlowVo> orderFlowList = new ArrayList<MerchantOrderDetailsFlowVo>();
		if(info!=null){
			vo.setCarriageMoney(info.getCarriageMoney() + "");
			vo.setConsigneeAddress(info.getConsigneeAddress());
			vo.setConsigneeName(info.getConsigneeName());
			vo.setConsigneePhone(info.getConsigneePhone());
			vo.setFavorableMoney(info.getFavorableMoney() + "");
			vo.setOrderAmt(info.getOrderAmt() + "");
			vo.setOrderNo(info.getOrderCode());
			vo.setOrderState(info.getOrderState());
			vo.setOutTime(DateUtil.formatToStr(info.getOutTime(), R.OrderConstant.DATETIME_FULL));
			vo.setReceiptUrl(info.getReceiptUrl());
			vo.setSalesNumber(info.getSalesNumber());
			vo.setShouldMoney(info.getShouldMoney() + "");
			vo.setDistributionMode(info.getDistributionMode());
			// 2、查流水表
			List<OrderFlowInfo> flowList = this.orderFlowInfoMapper.selectByOrderId(info.getId());
			for (OrderFlowInfo orderFlowInfo : flowList) {
				MerchantOrderDetailsFlowVo e = new MerchantOrderDetailsFlowVo();
				e.setBuyerRemark(orderFlowInfo.getBuyerRemark());
				e.setNiceName(orderFlowInfo.getMemberName());
				e.setOperatingTime(DateUtil.formatToStr(orderFlowInfo.getCreateDate(), R.OrderConstant.DATETIME_FULL));
				e.setOrderFlowState(orderFlowInfo.getOrderState());
				e.setSellerRemark(orderFlowInfo.getSellerRemark());
				orderFlowList.add(e);
			}

			// 3、再查详情表
			List<OrderDetails> detailsList = this.orderDetailsMapper.selectByOrderId(info.getId());
			for (OrderDetails orderDetails : detailsList) {
				MerchantOrderDetailsGoodsVo e = new MerchantOrderDetailsGoodsVo();
				e.setGoodsName(orderDetails.getGoodsName());
				e.setGoodsNum(orderDetails.getGoodsNum() + "");
				e.setGoodsPrice(orderDetails.getOriginalPrice() + "");
				e.setImgUrl(orderDetails.getGoodsImg());
				e.setSkuId(orderDetails.getSkuId());
				e.setSkuName(orderDetails.getSkuName());
				goodsList.add(e);
			}
			vo.setGoodsList(goodsList);
			vo.setOrderFlowList(orderFlowList);
		}
		return vo;
	}

	private int cancelOrder(UpdateOrderStateDto dto) throws OrderServiceException {
		int count = 0;
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 2);
		OrderBasicInfo orderBasicInfo = orderBasicInfoMapper.selectByOrderCode(dto.getOrderNo());
		if (orderBasicInfo == null) {
			throw new OrderServiceException(R.ReturnCodeEnum.code_notFindOrderInfo_errer.getValue(),
					R.ReturnCodeEnum.code_notFindOrderInfo_errer.getLabel());
		}
		if (orderBasicInfo.getOrderState().equals(R.OrderStateEnum.orderState_unpaid.getValue())
				|| orderBasicInfo.getOrderState().equals(R.OrderStateEnum.orderState_waiting_billing.getValue())) {
			OrderBasicInfo obi = new OrderBasicInfo();
			obi.setOrderCode(dto.getOrderNo());
			obi.setUpdateBy(dto.getMemberId());
			obi.setUpdateDate(new Date());
			obi.setOrderState(dto.getOrderState());
			obi.setClosingReason(dto.getReason());
			count = orderBasicInfoMapper.updateByOrderCode(obi);

			if (orderBasicInfo.getOrderState().equals(R.OrderStateEnum.orderState_waiting_billing.getValue())) {
				// TODO 退款
			}

			// 插入流水记录
			OrderFlowInfo ofi = new OrderFlowInfo();
			ofi.setId(idWorker.nextId() + "");
			ofi.setCreateBy(dto.getMemberId());
			ofi.setCreateDate(new Date());
			ofi.setOrderId(orderBasicInfo.getId());
			ofi.setOrderState(obi.getOrderState());
			ofi.setDelFlag(R.OrderConstant.Zero + "");
			ofi.setMemberId(dto.getMemberId());
			ofi.setMemberName(orderBasicInfo.getMemberName());
			ofi.setOrderType(orderBasicInfo.getOrderType());
			ofi.setIsAbnormalFlag(orderBasicInfo.getOrderExceptionFlag());
			orderFlowInfoMapper.insertSelective(ofi);

		} else {
			throw new OrderServiceException(R.ReturnCodeEnum.code_updateCancelOrder_errer.getValue(),
					R.ReturnCodeEnum.code_updateCancelOrder_errer.getLabel());
		}

		return count;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false,rollbackFor={OrderServiceException.class})
	public int updateOrderInfo(UpdateOrderInfoDto dto) throws OrderServiceException {
		int count = 0;
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 2);
		OrderBasicInfo orderBasicInfo = orderBasicInfoMapper.selectByOrderCode(dto.getOrderNo());
		if (orderBasicInfo == null) {
			throw new OrderServiceException(R.ReturnCodeEnum.code_notFindOrderInfo_errer.getValue(),
					R.ReturnCodeEnum.code_notFindOrderInfo_errer.getLabel());
		}
//		orderBasicInfo.setUpdateBy(dto.getMemberId());
//		orderBasicInfo.setUpdateDate(new Date());
//		orderBasicInfo.setSellerRemark(dto.getRemark());
//		count = orderBasicInfoMapper.updateByOrderCode(orderBasicInfo);
		
		// 插入流水记录
		OrderFlowInfo ofi = new OrderFlowInfo();
		ofi.setId(idWorker.nextId() + "");
		ofi.setCreateBy(dto.getMemberId());
		ofi.setCreateDate(new Date());
		ofi.setOrderId(orderBasicInfo.getId());
		ofi.setOrderState(orderBasicInfo.getOrderState());
		ofi.setDelFlag(R.OrderConstant.Zero + "");
		ofi.setMemberId(dto.getMemberId());
		ofi.setMemberName(orderBasicInfo.getMemberName());
		ofi.setOrderType(orderBasicInfo.getOrderType());
		ofi.setSellerRemark(dto.getRemark());
		ofi.setIsAbnormalFlag(orderBasicInfo.getOrderExceptionFlag());
		count=orderFlowInfoMapper.insertSelective(ofi);
		return count;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = {
			OrderServiceException.class })
	public int updateOrderState(UpdateOrderStateDto dto) throws OrderServiceException {
		int count = 0;
		if (dto.getOrderState().equals(R.OrderStateEnum.orderState_closed.getValue())) {
			count = this.cancelOrder(dto);
		} else {
			throw new OrderServiceException(R.ReturnCodeEnum.code_orderState_errer.getValue(),
					R.ReturnCodeEnum.code_orderState_errer.getLabel());
		}
		return count;
	}

	@Override
	public List<GetCountForOrderStateVo> getCountForOrderState(GetCountForOrderStateDto dto) {
		return orderBasicInfoMapper.getCountForOrderState(dto);
	}

	@Override
	public PageInfo getOrderList(QueryOrderListDto dto) {
		List<QueryOrderListVo> list = orderBasicInfoMapper.getOrderList(dto);
		Integer totalNum = 0;
		for (QueryOrderListVo v : list) {
			List<GoodVo> detailList = orderDetailsMapper.getGoodListByOrderId(v.getOrderId());
			totalNum = 0;
			for (GoodVo g : detailList) {
				totalNum += g.getGoodsNum();
			}
			v.setGoodslist(detailList);
			v.setTotalGoodsNum(totalNum.toString());
		}
		return new PageInfo(list);
	}

	@Override
	public String HandlePaymentTimeoutListByOrderStatus() {
		List<String> list=this.orderBasicInfoMapper.selectByOrderStatus(R.OrderStateEnum.orderState_unpaid.getValue(),DateUtil.formatToStr(new Date(), R.OrderConstant.DATETIME_FULL));
		if(list.size()>0){
			//调用批量修改操作
			int resultNum=this.orderBasicInfoMapper.updateBatchByOrderId(list);
			logs.info("成功关闭交易数量为={}",resultNum);
		}
	
		return null;
	}

	@Override
	public void timeOutWaitOrderJob() {
		List<String> list=this.orderBasicInfoMapper.selectByOrderStatus(R.OrderStateEnum.orderState_waiting_billing.getValue(),DateUtil.formatToStr(new Date(), R.OrderConstant.DATETIME_FULL));
		if(list.size()>0){
			for (String orderNo : list) {
				try {
					this.cancelTimeOutOrder(orderNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	private void cancelTimeOutOrder(String orderNo) {
		OrderBasicInfo record=new OrderBasicInfo();
		record.setOrderCode(orderNo);
		this.orderBasicInfoMapper.updateByOrderCode(record);
		// TODO　退款流程
		
	}

}
