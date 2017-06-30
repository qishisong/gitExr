package com.blomni.o2o.order.util;



public final class R {
	/**
	 * 
	 * Function : cloud常量定义<br/>
	 * Date : 2016年6月23日 下午1:33:42 <br/>
	 * 
	 * @author TANLQ
	 * @version 
	 * @since JDK 1.7
	 */
	public interface OrderConstant {
		/** UTF-8 */
		public static final String UTF8 = "UTF-8";
		/** MD5 */
		public static final String MD5 = "MD5";
		/** 返回标识 */
		public static final String RET_FLAG = "ret_flag";
		/** 返回代码 */
		public static final String RET_CODE = "ret_code";
		/** 返回消息 */
		public static final String RET_MSG = "ret_msg";
		/** 返回转接流水号*/
		public static final String REQ_MSG_ID = "reqMsgId";		
		/** 成功 */
		public static final String SUCCESS = "Y";
		/** 失败 */
		public static final String FAILURE = "W";
		/** 异常 */
		public static final String EXCEPTION = "W";
		/** 超时 */
		public static final String TIMEOUT = "T";
		/** 是 */
		public static final String YES = "Y";
		/** 否 */
		public static final String NO = "N";
		
		public static final String MD5_SIGN_KEY = "C7EF27E20F144A28A1C3F7BF3FB4E272";
		/** 停车场商户号*/
		public static final String MERID = "000090150504231";
		/** 版本*/
		public static final String VERSION = "20140728";
		/** 交易类型 0403消费撤销*/
		public static final String TRAN_TYPE_CANCEL = "0403";
		/** 交易类型 0401消费退款*/
		public static final String TRAN_TYPE_REFUND = "0401";
		/** 交易类型 0502查询*/
		public static final String TRAN_TYPE_SEARCH = "0502";
		/** 交易类型 0001线上*/
		public static final String BUSI_TYPE = "0001";
		/** 支付密钥*/
		public static final String SIGN_KEY = MD5_SIGN_KEY;
		/** 支付成功*/
		public static final String PAY_STATUS_SUCCESS = "0000";
		
		/** 人民币：CNY*/
		public static final String PAY_CNY = "CNY";
		
		/** 0*/
		public static final  int Zero=0;
		
		/** 1*/
		public static final  int One=1;
		
		/** 支付订单超时时间 (1个小时)*/
		public static final  String PAY_OUT_TIME_SECOND="3600";
		
		
		/** 接单超时时间 (2个小时)*/
		public static final  String MEETORDER_OUT_TIME_SECOND="7200";
		
		
		/** 日期格式 yyyy-MM-dd */
		public static final String DATE_FULL = "yyyy-MM-dd";
		/** 时间格式 yyyy-MM-dd HH:mm:ss */
		public static final String DATETIME_FULL = "yyyy-MM-dd HH:mm:ss";
		/** 日期格式（无连接符）yyyyMM */
		public static final String DATE_SHORT_MONTH = "yyyyMM";
		/** 日期格式 yyyy-MM */
		public static final String DATE_MONTH = "yyyy-MM";
		/** 日期格式（无连接符）yyyyMMdd */
		public static final String DATE_SHORT = "yyyyMMdd";
		/** 时间格式（无连接符） yyyyMMddHHmmss */
		public static final String DATETIME_SHORT = "yyyyMMddHHmmss";
	    /** 时间格式（无连接符） yyyyMMddHH */
		public static final String DATETIME_HOUR = "yyyyMMddHH";
		/** 时间格式（无连接符）HHmmss*/
		public static final String TIME_SHORT = "HHmmss";
		/** 时间格式 HH:mm:ss*/
		public static final String TIME_FULL = "HH:mm:ss";
		/** 时间格式 yyyy-MM-dd HH:mm:ss.SSS */
		public static final String DATETIME_FULL_S = "yyyy-MM-dd HH:mm:ss.SSS";
		/** 时间格式YYYYMMdd HH:mm:ss */
		public static final String DATETIME_FULL_QUICK_PAY = "yyyyMMdd HH:mm:ss";
		
		/** 时间格式MMDDhhmmss */
		public static final String DATETIME_FULL_MMDDHHMMSS = "MMddHHmmss";
		
		/** 时间格式hhmmss */
		public static final String DATETIME_FULL_HHMMSS = "hhmmss";
		
		/** 时间格式MMDD */
		public static final String DATETIME_FULL_MMDD = "MMdd";
		
		/** 时间格式YYMM */
		public static final String DATETIME_FULL_YYMM = "yyMM";
		
		/** 时间格式DD */
		public static final String DATETIME_FULL_DD = "dd";
	
	}
	
	
	/**
	 * 
	 * Function : cloud错误定义<br/>
	 * Date : 2016年6月23日 下午1:34:59 <br/>
	 * 
	 * @author TANLQk
	 * @version DictConst
	 * @since JDK 1.7
	 */
	public enum OrderErrorEnum {
		/** 其他系统错误*/
		ERROR_SYSTEM("10110910", "其他系统错误"), 
		/**操作数据库发生异常*/
		ERROR_DATABASE("10110418", "操作数据库发生异常"),
		/**主建列不能为空*/
		ERROR_PRIMERKEYNULL("10110419","主建列不能为空"),
		/**对象不能为空*/
		ERROR_OBJECTNULL("10110420","对象不能为空"),
		/**网络连接超时*/
		ERROR_TIMEOUT("10110418", "网络连接超时"),
		;
		private String value;

		private String label;

		private OrderErrorEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	
	
	/**
	 * 
	 * Function : 系统标识定义<br/>
	 * Date : 2016年6月23日 下午1:34:59 <br/>
	 * 
	 * @author TANLQ
	 * @version 
	 * @since JDK 1.7
	 */
	public enum SystemEnum {
		/** 订单标识码*/
		sys_order("2107", "订单标识码"), 
		;
		private String value;

		private String label;

		private SystemEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	
	/**
	 * 
	 * Function : 云店返回码定义<br/>
	 * Date : 2016年6月23日 下午1:34:59 <br/>
	 *    APP 用的是编码的后四位
	 * @author TANLQ
	 * @version 
	 * @since JDK 1.7
	 */
	public enum ReturnCodeEnum {	
		/** 成功*/
		code_success("00100000", "操作成功"), 
		/**登录或注册失败*/
		code_loginfail("03000000", "登录或注册失败"), 
		/**获取验证码失败*/
		code_CodeMessagefail("03000002", "获取验证码失败"), 
		/**json解析失败*/
		code_jsonfail("03000003", "json解析失败"), 
		
		/**查询会员信息失败*/
		code_querymbfail("03000004", "查询会员信息失败"),
		
		/**参数不能为空*/
		code_praramnotnull("03000005", "参数不能为空"),
		
		/**根据坐标查询门店失败**/
		code_getStoreByCoordinate("03000006","根据坐标查询门店失败"),

		
		/**入参异常**/
		code_param("03000007","入参不完整"),
		
		/**缺少请求信息*/
		code_req_msg("03000008", "缺少请求参数"),
		
		/**订单创建失败*/
		code_order_fail("03000009", "订单创建失败"),
		
		/**结果集查询为空*/
		code_result("03000010", "结果集查询为空"),
		/**支付失败*/
		code_pay_fail("03000011","支付失败"),
		
		/**支付失败或部分支付*/
		code_paypart("03000012","支付失败或部分支付"),
		
		/**未查询到有效支付记录*/
		code_notquerypay_fail("03000013","未查询到有效支付记录"),
		
		/**退款撤销失败*/
		code_refund_errer("03000014","退款撤销失败"),
		
		/**支付回调失败*/
		code_paymentResults_errer("03000015","支付回调失败"),
		
		/**商户开单失败*/
		code_receiveOrder_errer("03000001","商户开单失败"),
		
		/**添加地址失败*/
		code_insertAddress_errer("03000016","添加地址失败"),
		
		/**修改地址失败*/
		code_updateAddress_errer("03000017","修改地址失败"),
		
		/**删除地址失败*/
		code_delectAddress_errer("03000018","删除地址失败"),
		
		/**用户监权失败**/
		code_userSingleFail("03000019","用户监权失败"),
		
		/**查询用户地址失败**/
		code_selectAddress_errer("03000020","查询用户地址失败"),
		
		/**添加历史咨询商品失败*/
		code_addConsultGoods_errer("03000021","添加历史咨询商品失败"),
		
		/**取消订单失败*/
		code_cancelOrder_errer("03000022","取消订单失败"),
		
		/**没有找到订单信息*/
		code_notFindOrderInfo_errer("03000023","没有找到订单信息"),
		
		/**查询提货码信息失败*/
		code_notMentionGoodsCode_errer("03000024","查询提货码信息失败"),
		
		/**更新订单失败*/
		code_updateOrderState_errer("03000025","更新订单失败"),
		
		
		/**添加卖家备注失败*/
		code_updateOrderRemark_errer("03000026","添加卖家备注失败"),
		
		/**订单状态不正确*/
		code_orderState_errer("03000027","订单状态不正确"),
		
		/**订单关闭失败*/
		code_updateCancelOrder_errer("03000028","订单关闭失败"),
		
		/**没有该提货码的订单*/
		code_mentionGoodsCode_null("03000029","没有该提货码的订单"),
		
		/**核销提货码失败*/
		code_mentionGoodsCode_errer("03000030","核销提货码失败"),
		
		/**此码已作废，请刷新券码后重试*/
		code_mentionGoodsCode_time_out("03000031","此码已作废，请刷新券码后重试"),
		
		/**此码已核销*/
		code_mentionGoodsCode_repeat("03000032","此码已核销"),
		
		/**查询订单不同状态数量失败*/
		code_getCountForOrderState_errer("03000033","查询订单不同状态数量失败"),
		
		/**查询订单失败*/
		code_queryOrderList_errer("03000034","查询订单失败"),
		/**添加订单流水失败*/
		code_AddOrderinFo_errer("03000035","添加订单流水失败"),
		
		/**添加支付信息失败*/
		code_AddPayinFo_errer("03000036","添加支付信息失败"),
		
		/**生成提货码失败*/
		code_selfTakeCode_errer("03000037","生成提货码失败"),
		
		/**查询订单详情 失败*/
		code_queryOrderDetails_errer("03000035","查询订单详情失败"),
		
		/**查询订单状态异常*/
		code_queryOrderStatus_errer("03000038","订单状态异常"),
		
		/**查询订单商品价格异常*/
		code_queryGoodsPriceKey_errer("03000039","查询订单商品价格异常"),
		;

		
		
		
	
		private String value;

		private String label;

		private ReturnCodeEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	
	
	
	public enum OrderStateEnum {
		/**待支付 */
		orderState_unpaid("01", "待支付"), 
		
		/**待接单 */
		orderState_waiting_billing("02", "待接单"), 
		
		/** 待发货 */
		orderState_prepare_shipments("03", "待发货"), 
		
		/**待收货*/
		orderState_prepare_receipt("04", "待收货"),
		
		/**待收货*/
		orderState_prepare_selfTake("05", "待自提"),
		
		/**交易完成*/
		orderState_complete("06", "交易完成"),
		
		/**交易关闭*/
		orderState_closed("07", "交易关闭"),
		 
		;
		private String value;

		private String label;

		private OrderStateEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	public enum DistributionModeEnum {
		/**物流 */
		distributionMode_logistics("0", "物流"), 
		
		/**自提 */
		distributionMode_selfTake("1", "自提"), 
		
		
		 
		;
		private String value;

		private String label;

		private DistributionModeEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	public enum MentionGoodsCodeEnum {
		/**待提货*/
		mentionGoodsCode_original("0", "待提货"), 
		
		/**已失效 */
		mentionGoodsCode_failure("1", "已失效"), 
		
		/**已超时 */
		mentionGoodsCode_time_out("200", "券码超时"), 
		
		/** 提货完成 */
		mentionGoodsCode_success("2", "提货完成"), 
		
		/** 提货完成 */
		orderStatus_error("500", "订单状态异常"),
		;
		private String value;

		private String label;

		private MentionGoodsCodeEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	/** 商户消息组 */
	public enum MerMessageGroupEnum {
		/**系统消息*/
		system_message("0", "系统消息"), 

		/**商品消息*/
		goods_message("1", "商品消息"), 
		
		/**交易消息*/
		trade_message("2", "交易消息"), 
		
		;
		private String value;

		private String label;

		private MerMessageGroupEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	
	/** 消息类型枚举 */
	public enum MessageTypeEnum {
		
		/**系统消息*/
		a_type("0", "发布上新提醒"), 
		
		/**开单提醒*/
		b_type("1", "开单提醒"), 

		/**发货提醒*/
		c_type("2", "发货提醒"), 
		
		/**商品审核不通过*/
		d_type("3", "商品审核不通过"), 
		
		/**商品待审核*/
		e_type("4", "商品待审核"), 
		
		;
		private String value;

		private String label;

		private MessageTypeEnum(String value, String label) {
			this.value = value;
			this.label = label;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
	
}
