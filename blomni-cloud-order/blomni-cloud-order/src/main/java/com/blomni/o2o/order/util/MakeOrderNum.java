package com.blomni.o2o.order.util;



import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * title:生成订单号
 * @author user
 *
 */
public class MakeOrderNum {
	 /** 
     * 锁对象，可以为任意对象 
     */  
    private static Object lockObj = "lockerOrder";  
    /** 
     * 订单号生成计数器 
     */  
    private static long orderNumCount = 0L;  
    /** 
     * 每毫秒生成订单号数量最大值 
     */  
    private int maxPerMSECSize=1000;  
    /** 
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展 
     * @param 
     */  
    public String  makeOrderNum() {  
    	String OrderNo="";
        try {  
            // 最终生成的订单号  
            String finOrderNum = "";  
            
            synchronized (lockObj) {  
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒  
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));  
            	//long nowLong =  System.nanoTime(); 
            	// 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万  
                if (orderNumCount > maxPerMSECSize) {  
                    orderNumCount = 0L;  
                }  
                String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",  
                        "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
                        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
                        "W", "X", "Y", "Z" };  
                List list = Arrays.asList(beforeShuffle);  
                Collections.shuffle(list);  
                StringBuilder sb = new StringBuilder();  
                for (int i = 0; i < list.size(); i++) {  
                    sb.append(list.get(i));  
                }  
               // String afterShuffle = ;  
                String result = sb.toString().substring(4, 9);  
                //组装订单号  
                String countStr=maxPerMSECSize +orderNumCount+"";  
                finOrderNum=nowLong+countStr.substring(1);  
                orderNumCount++;  
                OrderNo= "YD"+result+finOrderNum;
                // Thread.sleep(1000); 
                
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return OrderNo;  
        
      
    }  
    
    public static String getOrderIdByUUId(String merchantType,String orderType,String businessType) { 
    	String OrderNo="";
    	     //int machineId = 1;//最大支持1-9个集群机器部署  
             int hashCodeV = UUID.randomUUID().toString().hashCode();  
              if(hashCodeV < 0) {//有可能是负数  
                 hashCodeV = - hashCodeV;  
             }  
    	         // 0 代表前面补充0       
    	         // 4 代表长度为4       
    	       // d 代表参数为正数型  
              OrderNo=merchantType+orderType+businessType+String.format("%013d", hashCodeV);
             return  OrderNo; 
    	    }  
    
   public static void main(String[] args) {
	for (int i = 0; i < 100000; i++) {
		System.out.println(getOrderIdByUUId("1","1","1"));
	}
}
   
}
