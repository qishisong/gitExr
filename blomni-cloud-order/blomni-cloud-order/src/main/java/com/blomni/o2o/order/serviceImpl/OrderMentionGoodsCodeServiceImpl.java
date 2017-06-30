package com.blomni.o2o.order.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blomni.o2o.order.dto.MentionGoodsCodeDto;
import com.blomni.o2o.order.entity.OrderBasicInfo;
import com.blomni.o2o.order.entity.OrderDeliveryCode;
import com.blomni.o2o.order.entity.OrderFlowInfo;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.mapper.OrderBasicInfoMapper;
import com.blomni.o2o.order.mapper.OrderDeliveryCodeMapper;
import com.blomni.o2o.order.mapper.OrderFlowInfoMapper;
import com.blomni.o2o.order.service.OrderMentionGoodsCodeService;
import com.blomni.o2o.order.util.R;
import com.blomni.o2o.order.util.SnowflakeIdWorker;

@Service
public class OrderMentionGoodsCodeServiceImpl implements OrderMentionGoodsCodeService {

	private static Logger logs = LoggerFactory.getLogger(OrderMentionGoodsCodeServiceImpl.class);
	
	@Autowired
	private OrderDeliveryCodeMapper orderDeliveryCodeMapper;
	
	@Autowired
	private OrderBasicInfoMapper orderBasicInfoMapper;
	
	@Autowired
	private OrderFlowInfoMapper orderFlowInfoMapper;
	
	@Override
	public String queryOrderIdByMentionGoodsCode(MentionGoodsCodeDto mentionGoodsCode) {
		logs.info("OrderMentionGoodsCodeServiceImpl --> queryOrderIdByMentionGoodsCode start");
		String resultValStr = null;
		String resultVal = this.queryMentionGoodsCodestatus(mentionGoodsCode);
		if(StringUtils.isNotBlank(resultVal) && R.MentionGoodsCodeEnum.mentionGoodsCode_original.getLabel().equals(resultVal)){
			resultValStr = orderDeliveryCodeMapper.queryOrderIdByMentionGoodsCode(mentionGoodsCode);
			if(StringUtils.isNotBlank(resultValStr)){
				if(StringUtils.isBlank(queryOrderInfoStatus(R.OrderStateEnum.orderState_prepare_selfTake.getValue(),resultValStr))){
					resultValStr = R.MentionGoodsCodeEnum.orderStatus_error.getLabel();
				}
			}
		}else{
			resultValStr = resultVal;
		}
		logs.info("OrderMentionGoodsCodeServiceImpl --> queryOrderIdByMentionGoodsCode end resultValStr :",resultValStr);
		return resultValStr;
	}

	@Override
	public String cancellationMentionGoodsCode(MentionGoodsCodeDto mentionGoodsCode) {
		logs.info("OrderMentionGoodsCodeServiceImpl --> cancellationMentionGoodsCode start");
		String resultValStr = null;
		String orderId = queryOrderInfoStatus(R.OrderStateEnum.orderState_prepare_selfTake.getValue(),mentionGoodsCode.getOrderNo());
		if(StringUtils.isNotBlank(orderId)){
			mentionGoodsCode.setOrderId(orderId);
			resultValStr = String.valueOf(orderDeliveryCodeMapper.updateMentionGoodsCodeStatus(mentionGoodsCode));
			logs.info("OrderMentionGoodsCodeServiceImpl --> cancellationMentionGoodsCode 修改订单状态 start  resultValStr:",resultValStr);
			//添加修改订单状态
			if(StringUtils.isNoneBlank(resultValStr) && "1".equals(resultValStr)){
				resultValStr = updateOrderInfoStatus(R.OrderStateEnum.orderState_prepare_selfTake.getValue(),mentionGoodsCode);
			}
			logs.info("OrderMentionGoodsCodeServiceImpl --> cancellationMentionGoodsCode 修改订单状态 end  resultValStr:",resultValStr);
		}
		logs.info("OrderMentionGoodsCodeServiceImpl --> cancellationMentionGoodsCode end resultValStr:",resultValStr);
		return resultValStr;
	}

	@Override
	public String queryMentionGoodsCodestatus(MentionGoodsCodeDto mentionGoodsCode) {
		logs.info("OrderMentionGoodsCodeServiceImpl --> cancellationMentionGoodsCode start");
		String resultVal = null;
		List<OrderDeliveryCode> list = orderDeliveryCodeMapper.queryMentionGoodsCodeStatus(mentionGoodsCode);
		if(null != list && list.size() > 0){
			Map<String,OrderDeliveryCode> codeMap = new HashMap<String,OrderDeliveryCode>();
			for (OrderDeliveryCode orderDeliveryCode : list) {
				codeMap.put(orderDeliveryCode.getDeliveryStatus(), orderDeliveryCode);
			}
			if(codeMap.containsKey("0")){
				OrderDeliveryCode orderDeliveryCode = codeMap.get("0");
				if(new Date().getTime() < orderDeliveryCode.getInvalidDate().getTime()){
					resultVal = R.MentionGoodsCodeEnum.mentionGoodsCode_original.getLabel();
				}else{
					resultVal =  R.MentionGoodsCodeEnum.mentionGoodsCode_time_out.getLabel();
				}
			}else if(codeMap.containsKey("2")){
				resultVal = R.MentionGoodsCodeEnum.mentionGoodsCode_success.getLabel();
			}
		}else{
			resultVal =  R.MentionGoodsCodeEnum.mentionGoodsCode_failure.getLabel();
		}
		logs.info("OrderMentionGoodsCodeServiceImpl --> cancellationMentionGoodsCode end resultVal:",resultVal);
		return resultVal;
	}
	
	@Override
	public String queryMentionGoodsCodeByOrderId(MentionGoodsCodeDto mentionGoodsCode) {
		String resultVal = null;
		int num = 0;
		OrderBasicInfo orderInfo = orderBasicInfoMapper.selectByOrderCode(mentionGoodsCode.getOrderId());
		if(null != orderInfo){
			mentionGoodsCode.setOrderNo(orderInfo.getId());
			List<OrderDeliveryCode> list = orderDeliveryCodeMapper.queryMentionGoodsCodeByOrderNo(mentionGoodsCode);
			if(null != list && list.size() > 0){
				Map<String,OrderDeliveryCode> codeMap = new HashMap<String,OrderDeliveryCode>();
				for (OrderDeliveryCode orderDeliveryCode : list) {
					codeMap.put(orderDeliveryCode.getDeliveryStatus(), orderDeliveryCode);
				}
				if(codeMap.containsKey("0")){
					OrderDeliveryCode orderDeliveryCode = codeMap.get("0");
					if(new Date().getTime() < orderDeliveryCode.getInvalidDate().getTime()){
						resultVal = orderDeliveryCode.getDeliveryCode();
					}else{
						mentionGoodsCode.setDeliveryCode(orderDeliveryCode.getDeliveryCode());
						//更新 + 新增
						if(1 == updateMentionGoodsCodeFlag(mentionGoodsCode) ){
							num = insertMentionGoodsCodeinfo(mentionGoodsCode);
						}
						
					}
				}else if(!codeMap.containsKey("1")){
					//新增
					num = insertMentionGoodsCodeinfo(mentionGoodsCode);
				}
			}else if(StringUtils.isNotBlank(queryOrderInfoStatus(R.OrderStateEnum.orderState_prepare_selfTake.getValue(),orderInfo.getOrderCode()))	){
				num = insertMentionGoodsCodeinfo(mentionGoodsCode);
			}
		}
		if(StringUtils.isBlank(resultVal) && 1 == num){
			resultVal = queryMentionGoodsCode(mentionGoodsCode);
		}
		return resultVal;
	}
	
	/**
	 * 查询当前订单的状态值 (是否有效，是否状态异常)
	 * @param status  当前状态值
	 * @param OrderId 订单ID
	 * @return 订单是否异常
	 */
	public String queryOrderInfoStatus(String status,String OrderCode){
		logs.info("OrderMentionGoodsCodeServiceImpl --> queryOrderInfoStatus start ");
		String resultStr = null;
		OrderBasicInfo orderInfo = orderBasicInfoMapper.selectByOrderCode(OrderCode);
		if(null != orderInfo){
			if(StringUtils.isNotBlank(orderInfo.getOrderExceptionFlag()) && "N".equals(orderInfo.getOrderExceptionFlag())){
			   if(StringUtils.isNotBlank(status) && StringUtils.isNotBlank(orderInfo.getOrderState())
						&& status.equals(orderInfo.getOrderState())){
				   resultStr = orderInfo.getId();
			    }
			}
		}
		logs.info("OrderMentionGoodsCodeServiceImpl --> queryOrderInfoStatus end resultFalg:",resultStr);
		return resultStr;
	}
	
	/**
	 * 
	 * @param status 当前状态值
	 * @param mentionGoodsCode 数据对象
	 * @return 更新状态
	 */
	public String updateOrderInfoStatus(String status,MentionGoodsCodeDto mentionGoodsCode){
		logs.info("OrderMentionGoodsCodeServiceImpl --> updateOrderInfoStatus start ");
		String resultStr= null;
		if(StringUtils.isNotBlank(queryOrderInfoStatus(status,mentionGoodsCode.getOrderNo()))){
			OrderBasicInfo info=new OrderBasicInfo();
			info.setOrderCode(mentionGoodsCode.getOrderNo());
			info.setOrderState(R.OrderStateEnum.orderState_complete.getValue());
			info.setUpdateBy(mentionGoodsCode.getMemberId());
			info.setUpdateDate(new Date());
			int num = orderBasicInfoMapper.updateByOrderCode(info);
			if(num > 0){
				if(updateOrderInfoWater(R.OrderStateEnum.orderState_complete.getValue(),mentionGoodsCode)){
					resultStr = String.valueOf(num);
				}else{
					resultStr = null;
				}
			}
		}
		logs.info("OrderMentionGoodsCodeServiceImpl --> updateOrderInfoStatus start resultStr:",resultStr);
		return resultStr;
	}
	
	/**
	 * 
	 * @param status  当前状态值
	 * @param mentionGoodsCode 数据对象
	 * @return 添加状态
	 */
	public boolean updateOrderInfoWater(String status,MentionGoodsCodeDto mentionGoodsCode){
		logs.info("OrderMentionGoodsCodeServiceImpl --> updateOrderInfoWater start ");
		boolean resultFalg = false;
		OrderBasicInfo orderInfo = orderBasicInfoMapper.selectByOrderCode(mentionGoodsCode.getOrderNo());
		if(null != orderInfo){
			//check订单状态
			if(StringUtils.isNotBlank(queryOrderInfoStatus(status,mentionGoodsCode.getOrderNo()))){
				SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 2);
				OrderFlowInfo ofi=new OrderFlowInfo();
				ofi.setId(idWorker.nextId()+"");
				ofi.setCreateBy(orderInfo.getMemberId());
				ofi.setCreateDate(new Date());
				ofi.setOrderId(mentionGoodsCode.getOrderId());
				ofi.setOrderState(status);
				ofi.setDelFlag(R.OrderConstant.Zero+"");
				ofi.setMemberId(orderInfo.getMemberId());
				ofi.setMemberName(orderInfo.getMemberName());
				ofi.setOrderType(orderInfo.getOrderType());
				ofi.setIsAbnormalFlag(orderInfo.getOrderExceptionFlag());
				int num = orderFlowInfoMapper.insertSelective(ofi);
				resultFalg = num > 0 ? true:false;
			}
		}
		logs.info("OrderMentionGoodsCodeServiceImpl --> updateOrderInfoWater end resultFalg:",resultFalg);
		return resultFalg;
	}
	
	/**
	 * 查询有效提货码
	 * @param status
	 * @param mentionGoodsCode
	 * @return
	 */
	public String queryMentionGoodsCode(MentionGoodsCodeDto mentionGoodsCode){
		logs.info("OrderMentionGoodsCodeServiceImpl --> updateOrderInfoStatus start ");
		String resultVal= null;
		List<OrderDeliveryCode> list = orderDeliveryCodeMapper.queryMentionGoodsCodeByOrderNo(mentionGoodsCode);
		if(null != list && list.size() > 0){
			Map<String,OrderDeliveryCode> codeMap = new HashMap<String,OrderDeliveryCode>();
			for (OrderDeliveryCode orderDeliveryCode : list) {
				codeMap.put(orderDeliveryCode.getDeliveryStatus(), orderDeliveryCode);
			}
			if(codeMap.containsKey("0")){
				OrderDeliveryCode orderDeliveryCode = codeMap.get("0");
				if(new Date().getTime() < orderDeliveryCode.getInvalidDate().getTime()){
					resultVal = orderDeliveryCode.getDeliveryCode();
				}
			}
		}
		logs.info("OrderMentionGoodsCodeServiceImpl --> updateOrderInfoStatus start resultStr:",resultVal);
		return resultVal;
	}
	
	
	public int updateMentionGoodsCodeFlag(MentionGoodsCodeDto mentionGoodsCode){
		int num = 0;
		logs.info("OrderMentionGoodsCodeServiceImpl --> updateMentionGoodsCodeFlag start ");
		OrderDeliveryCode orderDeliveryCode = new OrderDeliveryCode();
		orderDeliveryCode.setOrderId(mentionGoodsCode.getOrderNo());
		orderDeliveryCode.setDeliveryCode(mentionGoodsCode.getDeliveryCode());
		orderDeliveryCode.setDeliveryStatus(R.MentionGoodsCodeEnum.mentionGoodsCode_failure.getValue());
		orderDeliveryCode.setUpdateBy(mentionGoodsCode.getMemberId());
		orderDeliveryCode.setUpdateDate(new Date());
		num = orderDeliveryCodeMapper.updateByPrimaryKeySelective(orderDeliveryCode);
		logs.info("OrderMentionGoodsCodeServiceImpl --> updateMentionGoodsCodeFlag start resultStr:",num);
		return num;
	}
	
	public int insertMentionGoodsCodeinfo(MentionGoodsCodeDto mentionGoodsCode) {
		int num=0;
		logs.info("OrderMentionGoodsCodeServiceImpl --> insertMentionGoodsCodeinfo start ");
		try {
			SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 2);
			OrderDeliveryCode record=new OrderDeliveryCode();
			record.setId(idWorker.nextId()+"");
			record.setDeliveryCode(getRandomNumber());
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MINUTE, 5);
			record.setInvalidDate(cal.getTime());
			record.setCreateBy(mentionGoodsCode.getMemberId());
			record.setOrderId(mentionGoodsCode.getOrderNo());
			record.setCreateDate(new Date());
			record.setDeliveryStatus(R.MentionGoodsCodeEnum.mentionGoodsCode_original.getValue());
			record.setDelFlag(R.OrderConstant.Zero+"");
			num=orderDeliveryCodeMapper.insert(record);
		} catch (Exception e) {
			logs.error("OrderMentionGoodsCodeServiceImpl -- > insertMentionGoodsCodeinfo",e.getMessage());
		}
		logs.info("OrderMentionGoodsCodeServiceImpl --> insertMentionGoodsCodeinfo start resultStr:",num);
		return num;
	}
	
	@Override
	public String getRandomNumber() throws OrderServiceException{
		List<String> codeList=orderDeliveryCodeMapper.queryDeliverCode();
		String str="";
		for(int i=0;i<6;i++){
			Random random = new Random();
			int num=random.nextInt(9);
			str+=String.valueOf(num);
		}
		if(codeList.size()>=950000){
			throw new OrderServiceException(R.ReturnCodeEnum.code_selfTakeCode_errer.getValue(),R.ReturnCodeEnum.code_selfTakeCode_errer.getLabel());
		}else{
			if(!codeList.contains(str)){
				return str;
			}else{
				
				return getRandomNumber();
			}
		}
	}

	

}
