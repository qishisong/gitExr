package com.blomni.o2o.order.serviceImpl;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import com.blomni.o2o.order.entity.CloudDrawback;
import com.blomni.o2o.order.exception.OrderServiceException;
import com.blomni.o2o.order.service.OrderAutographService;
import com.blomni.o2o.order.util.BLSign;
import com.blomni.o2o.order.util.HttpUtilsV3;
import com.blomni.o2o.order.util.OrderDateUtils;
import com.blomni.o2o.order.util.R;



public class OrderAutographServiceImpl implements OrderAutographService{
	
	private static Logger logs = LoggerFactory.getLogger(OrderAutographServiceImpl.class);
	/**
	 * title:回调签名
	 * zy
	 * 2017年5月2日10:42:25
	 */
	@Override
	public String callbackAutograph(String orderNo, String tranDate)throws OrderServiceException {
		Map<String, String> map = new HashMap<String, String>();
		List<String> list = new ArrayList<String>();
		String results =null;

		// 版本号 Version 固定值：20140728
		map.put("Version", R.OrderConstant.VERSION);
		list.add("Version=" + R.OrderConstant.VERSION);
		// 商户号 MerId
		map.put("MerId", R.OrderConstant.MERID);
		list.add("MerId=" + R.OrderConstant.MERID);
		// 商户订单号 MerOrderNo
		map.put("MerOrderNo", orderNo);
		list.add("MerOrderNo=" + orderNo);
		// 商户交易日期 TranDate
		map.put("TranDate", tranDate);
		list.add("TranDate=" + tranDate);
		// 交易类型 TranType
		map.put("TranType", R.OrderConstant.TRAN_TYPE_SEARCH);
		list.add("TranType=" + R.OrderConstant.TRAN_TYPE_SEARCH);
		// 业务类型 BusiType
		map.put("BusiType", R.OrderConstant.BUSI_TYPE);
		list.add("BusiType=" + R.OrderConstant.BUSI_TYPE);

		// 签名
		map.put("Signature", BLSign.getSignature(list, R.OrderConstant.SIGN_KEY));
		
		;
		
		/** 调接口 查支付结果 **/
		results=paymentResults(JSONObject.toJSONString(map));
		
		return results;
		
	}
	/**
	 * title:退款/撤销 签名
	 * zy
	 * 2017年5月2日10:42:25
	 */
	@Override
	public String refundAutograph(CloudDrawback back)throws OrderServiceException {
		logs.info("refundAutograph===========START========"+JSONObject.toJSONString(back));
		Date date = new Date();
		String TranDate = OrderDateUtils.formateDateToDateStr(date);
		String TranTime = OrderDateUtils.formateDateToTimeStr(date);
		String respon =null;
		
		Map<String, String> map = new HashMap<String, String>();
		List<String> list = new ArrayList<String>();
		map.put("BusiType", R.OrderConstant.BUSI_TYPE);// 业务类型
		list.add("BusiType=" + R.OrderConstant.BUSI_TYPE);// 业务类型 BusiType
		map.put("CurryNo", R.OrderConstant.PAY_CNY);// 默认为人民币：CNY
		list.add("CurryNo=" + R.OrderConstant.PAY_CNY);// 默认为人民币：CNY
		
		if (StringUtils.isNotBlank(back.getUrl())) {
			map.put("MarAfterUrl", back.getUrl());// 商户后台通知地址
			list.add("MarAfterUrl=" + back.getUrl());// 商户后台通知地址
		}
		
		map.put("MerId", R.OrderConstant.MERID);// 商户号15位不定长数字，用于确认商户身份
		list.add("MerId=" + R.OrderConstant.MERID);// 商户号 MerId
		map.put("MerOrderNo", "T" + back.getOrderNo());// 商户订单号必填，变长32位，同一商户同一交易日期内不可重复
		list.add("MerOrderNo=" + "T" + back.getOrderNo());// 商户订单号 MerOrderNo
		// map.put("MerResv", 0001);//保留域
		map.put("OriOrderNo", back.getOrderNo());// 原支付订单号
		list.add("OriOrderNo=" + back.getOrderNo());// 原支付订单号
		map.put("OriTranDate",  back.getTranDate());// 原支付交易日期 string @mock=20150918
		list.add("OriTranDate=" + back.getTranDate());// 原支付交易日期 string @mock=20150918
		
		if ( R.OrderConstant.YES.equals(back.getRemarks())) {
			map.put("RefundAmt", Integer.valueOf(back.getRefundAmt()).toString());// 单位：分,退款必传，撤销不需传
			list.add("RefundAmt=" + Integer.valueOf(back.getRefundAmt()));// 单位：分,退款必传，撤销不需传
		}
		
		map.put("TranDate", back.getTranDate());// 商户交易日期yyyyMMdd例如：20150102为订单生成时间（不是提交订单时间）
		list.add("TranDate=" + back.getTranDate());// 商户交易日期 TranDate
		map.put("TranTime", TranTime);// 商户交易时间hhmmss 例如：101122
		list.add("TranTime=" + TranTime);// 商户交易日期 TranDate
		map.put("TranType", back.getTranType());// 交易类型此接口TranType填值范围：0401退款，0403撤销
		list.add("TranType=" + back.getTranType());// 交易类型  TranType
		map.put("Version", R.OrderConstant.VERSION);// Version 版本号固定值：20140728
		list.add("Version=" + R.OrderConstant.VERSION);// 版本号 Version 固定值：20140728
		map.put("Signature", BLSign.getSignature(list, R.OrderConstant.SIGN_KEY));// 签名商户报文签名信息
		//退款或撤销接口
		respon=refund(JSONObject.toJSONString(map));
		    
		   logs.info("refundAutograph===========END========"+JSONObject.toJSONString(back));   
	return respon;
	}
	
	/**
	 * 
	* @Title: refund 
	* @Description: TODO(退款或撤销) 
	* @param @param vo
	* @param @return    设定文件 
	* @return String    返回类型 
	* @author zy 
	* @date 2017年5月3日15:24:04
	* @throws
	 */
	public String refund(String results)throws OrderServiceException{
		logs.info("refund==========START==== {}"+results);
		String respon =null;
		  try {
			   //application.for.drawback=http://mpayment.bl.com/CTITS/service/rest/forward/syn/000000000009/0/0/0/0/0
			   respon = HttpUtilsV3.doPost("http://mpayment.bl.com/CTITS/service/rest/forward/syn/000000000009/0/0/0/0/0", results);
		} catch (Exception e) {
			// TODO: handle exception
			logs.error("调用退款撤销接口    {}",e);
			throw new OrderServiceException(R.ReturnCodeEnum.code_refund_errer.getValue(), R.ReturnCodeEnum.code_refund_errer.getLabel());
			
		}
		  logs.info("refund==========END==== {}"+respon);
		return respon;
	}
	
	
	/**
	 * 
	* @Title: paymentResults 
	* @Description: TODO(查询支付结果) 
	* @param @param results
	* @param @return    设定文件 
	* @return String    返回类型 
	* @date 2017年5月3日 下午3:27:15 
	* @author zy 
	* @throws
	 */
	public String paymentResults(String results)throws OrderServiceException{
		logs.info("paymentResults=========START===="+results);
		String result = null;
		if(results!=null){
			try {
				//http://10.201.128.198:8080/CTITS/
				String url = "http://10.201.128.198:8080/CTITS/service/rest/forward/syn/000000000094/0/0/0/0/0";
				// http://10.201.128.198:8080/CTITS/service/rest/forward/syn/000000000094/0/0/0/0/0
				String respon = HttpUtilsV3.doPost(url, results );
				JSONObject resultJson = JSONObject.parseObject(respon);
				
				result = respon.replace("\\", "");
				result = result.replace(":\"[{", ":[{");
				result = result.replace("\"}]\"}", "\"}]}");
				logs.debug("支付中台查询结果 : respon 转换后的 {} ", result);
			} catch (Exception e) {
				// TODO: handle exception
				logs.error("调接口 查支付结果"+R.OrderErrorEnum.ERROR_TIMEOUT.getLabel()+" {}",e);
				throw new OrderServiceException(R.ReturnCodeEnum.code_paymentResults_errer.getValue(), R.ReturnCodeEnum.code_paymentResults_errer.getLabel());
				
			}
		}
		logs.info("paymentResults=========END===="+result);
		return result;
	}
	
}
