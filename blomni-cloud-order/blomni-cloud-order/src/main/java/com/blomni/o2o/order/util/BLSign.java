package com.blomni.o2o.order.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

public class BLSign {
	private static final Logger loger = LoggerFactory.getLogger(BLSign.class);

	// 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	// 转换list对象
	public String convertSignMsg(List list) {
		loger.info("格式List开始");
		// list排序
		Collections.sort(list);
		// 组key1=value1&key2=value2签名串
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			sb.append("&");
		}
		String signMsg = sb.substring(0, sb.length() - 1);
		loger.info("格式List完成后的signMsg:[" + signMsg + "]");
		return signMsg;
	}

	// 拼接byte数组
	public static byte[] mergeArray(byte[] a1, byte[] a2) {
		loger.info("开始拼接byte数组");
		byte[] a3 = new byte[a1.length + a2.length];
		System.arraycopy(a1, 0, a3, 0, a1.length);
		System.arraycopy(a2, 0, a3, a1.length, a2.length);
		loger.info("拼接byte数组结束");
		return a3;
	}

	// 加密算法
	public static byte[] doDigest(byte[] dataBytes, String algName)throws Exception {
		loger.info("开始"+algName+"算法加密");
		MessageDigest md = MessageDigest.getInstance(algName);
		md.update(dataBytes);
		loger.info("开始"+algName+"算法加密");
		return md.digest();
	}

	// 转换成16进制
	public static String parseByte2HexStr(byte[] buf) {
		loger.info("开始转换成16进制");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		loger.info("转换成16进制结束,转换后的密文为:["+sb.toString()+"]");
		return sb.toString();
	}

	// 签名算法
	public String sign(String signMsg, String key) throws Exception {
	    loger.info("签名开始  MD5转换密钥"+key);
//		MD5 md5 = new MD5();
		String signKey = GetMD5Code(key);
		loger.info("MD5转换后的"+signKey);
		byte[] msgBytes = null;
		msgBytes = mergeArray(signMsg.getBytes("UTF-8"),signKey.getBytes("UTF-8"));
		byte[] signBytes = doDigest(msgBytes, "SHA-256");
		String signature = parseByte2HexStr(signBytes);
		return signature;
	}

	public static String getSignature(List<String> list,String signKey) {
		String ret = null;
		BLSign blign = new BLSign();
//		List<String> list = new ArrayList<String>();
//		list.add("key2=value2");
//		list.add("key3=value3");
//		list.add("key1=value1");
		String signMsg = blign.convertSignMsg(list);
		//String key = "C7EF27E20F144A28A1C3F7BF3FB4E272";
		try {
			ret = blign.sign(signMsg, signKey);
		} catch (Exception e) {
			loger.error("BLSign's sign error:", e.getMessage());
		}
		return ret;
	}

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
	
    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
//		return genByJdk(strObj);
		return genByApache(strObj);
	}

	/**
	 * 使用apache进行MD5加密
	 *
	 * @param strObj 待加密的字符串
	 * @return 加密后的字符串
     */
	private static String genByApache(String strObj) {
		// 转换为大写，适配系统中已存储的MD5字符串
		return DigestUtils.md5Hex(strObj).toUpperCase();
	}

	/*private static String genByJdk(String strObj) {
		String resultString = null;
		try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            loger.error("BLSign's genByJdk error:", ex.getMessage());
        }
		return resultString;
	}*/

}
