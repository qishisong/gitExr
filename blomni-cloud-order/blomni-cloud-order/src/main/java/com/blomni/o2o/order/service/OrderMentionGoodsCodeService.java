package com.blomni.o2o.order.service;


import com.blomni.o2o.order.dto.MentionGoodsCodeDto;
import com.blomni.o2o.order.exception.OrderServiceException;

public interface OrderMentionGoodsCodeService {
	
	/**
	 * 根据提货码信息查询订单ID
	 * @param mentionGoodsCode
	 * @return 订单ID
	 */
	public String queryOrderIdByMentionGoodsCode(MentionGoodsCodeDto mentionGoodsCode);
	
	/**
	 * 核销提货码
	 * @param mentionGoodsCode
	 * @return 核销状态
	 */
	public String cancellationMentionGoodsCode(MentionGoodsCodeDto mentionGoodsCode);
	
	/**
	 * 查询提货码状态
	 * @param mentionGoodsCode
	 * @return 提货码状态
	 */
	public String queryMentionGoodsCodestatus(MentionGoodsCodeDto mentionGoodsCode);
	
	/**
	 * 根据订单ID查询提货码信息
	 * @param mentionGoodsCode
	 * @return 提货码
	 */
	public String queryMentionGoodsCodeByOrderId(MentionGoodsCodeDto mentionGoodsCode);
	
	
	/**
	 * 生成提货码
	 * @param mentionGoodsCode
	 * @return 提货码
	 */
	public String getRandomNumber() throws OrderServiceException;
	
	
	
}
