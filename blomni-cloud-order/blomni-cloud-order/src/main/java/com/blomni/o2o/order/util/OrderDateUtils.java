package com.blomni.o2o.order.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDateUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderDateUtils.class);

	/**
	 * 格式化日期date->String
	 * @Methods Name formatDateToStr
	 * @Create In 2015-9-8 By jiangzhen
	 * @param date
	 * @return String
	 */
	public static String formatDateToStr(Date date) {
		SimpleDateFormat formatTmeplate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatTmeplate.format(date);
	}
	
	/**
	 * 格式化日期 String->date
	 * @Methods Name formatStrToDate
	 * @Create In 2015-9-8 By jiangzhen
	 * @param dateStr
	 * @return Date
	 */
	public static Date formatStrToDate(String dateStr) {
		SimpleDateFormat formatTmeplate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatTmeplate.parse(dateStr);
		} catch (ParseException e) {
			logger.error("日期转换发生错误,元格式"+dateStr);
		}
		return null;
	}
	/**
	 * 
	* @Title: formateDateToDateStr 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param date
	* @param @return    设定文件 
	* @return String    返回类型 yyyyMMdd
	* @author zy 
	* @throws
	 */
	public static String formateDateToDateStr(Date date) {
		SimpleDateFormat formatTmeplate = new SimpleDateFormat("yyyyMMdd");
		return formatTmeplate.format(date);
	}
	/**
	 * 
	* @Title: formateDateToDateStrs 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param time
	* @param @return    设定文件 
	* @return String    返回类型 yyyy-MM-dd hh:mm:ss
	* @author zy 
	* @throws
	 */
	public static String formateDateToDateStrs(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date date = format.parse(time);
			time =formateDateToDateStr(date);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return time;
	}
	
	
	/**
	 * 
	* @Title: formateDateToTimeStr 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param date
	* @param @return    设定文件 
	* @return String    返回类型 HHmmss
	* @author zy 
	* @throws
	 */
	public static String formateDateToTimeStr(Date date) {
		SimpleDateFormat formatTmeplate = new SimpleDateFormat("HHmmss");
		return formatTmeplate.format(date);
	}
	
	public static String createOrderNoFormate(Date date){
		String orderNo="P";
		orderNo+=formateDateToDateStr(date).toString()+
		formateDateToTimeStr(date).toString()+
		new Random().nextInt(99999);
		return orderNo;
	}
	public static String createOuterNoFormate(Date date){
		String outerNo="W";
		outerNo+=formateDateToDateStr(date).toString()+
				formateDateToTimeStr(date).toString()+
				new Random().nextInt(99999);
		return outerNo;
	}
	
	//美式时间转中式时间
		public static String ustimeToChina(Date date) throws ParseException{
			String result="";
			if (date!=null) {
				String daString=date.toString();
				SimpleDateFormat format=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
				Date dat = (Date) format.parse(daString);
				result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dat);
			}
			return result;
		}
		
		public static String formateDateToStr(Date date) {
			SimpleDateFormat formatTmeplate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatTmeplate.format(date);
		}
		
	
		/**
		 * title:时间比较
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static boolean booleanUstime(Date date) throws ParseException{
			boolean bl=false;
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date1 = formateDateToStr(date);  
			String date2 = formateDateToStr(new Date());
			try {
			       Date date_01 = sdf.parse(date1);
			       Date date_02 = sdf.parse(date2);
//			       System.out.println(date_01.before(date_02)); //true，当 date_01 小于 date_02 时，为 true，否则为 false
//			       System.out.println(date_02.after(date_01)); //true，当 date_02 大于 date_01 时，为 true，否则为 false
//			       System.out.println(date_01.compareTo(date_02)); //-1，当 date_01 小于 date_02 时，为 -1
//			       System.out.println(date_02.compareTo(date_01)); //1，当 date_02 大于 date_01 时，为 1
//			       System.out.println(date_02.compareTo(date_02)); //0，当两个日期相等时，为 0
			       
			       int i= date_02.compareTo(date_01);
			       if(i==1 || i==0){
			    	   bl=true;
			       }
			       
			} catch (ParseException e) {
			        e.printStackTrace();
			}
			
			return bl;
			
			
		}
		
		/**
		 * title:时间比较
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static int IntUstime(Date date) throws ParseException{
			int i=0;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date1 = formateDateToStr(date);  
			String date2 = formateDateToStr(new Date());
			try {
			       Date date_01 = sdf.parse(date1);
			       Date date_02 = sdf.parse(date2);
//			       System.out.println(date_01.before(date_02)); //true，当 date_01 小于 date_02 时，为 true，否则为 false
//			       System.out.println(date_02.after(date_01)); //true，当 date_02 大于 date_01 时，为 true，否则为 false
//			       System.out.println(date_01.compareTo(date_02)); //-1，当 date_01 小于 date_02 时，为 -1
//			       System.out.println(date_02.compareTo(date_01)); //1，当 date_02 大于 date_01 时，为 1
//			       System.out.println(date_02.compareTo(date_02)); //0，当两个日期相等时，为 0
			       
			        i= date_02.compareTo(date_01);
			       
			       
			} catch (ParseException e) {
			        e.printStackTrace();
			}
			
			return i;
			
			
		}
		
		//判断是否超过24小时  
	    public static boolean GreaterThan(Date start , Date end) throws Exception { 
	        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-M-d HH:mm:ss"); 
	       
	        long cha = end.getTime() - start.getTime(); 
	        double result = cha * 1.0 / (1000 * 60 * 60); 
	        if(result>=24){ 
	             //System.out.println("可用");   
	             return true; 
	        }else{ 
	             //System.out.println("已过期");  
	             return false; 
	        } 
	    } 
	    	/**判断是否大于等于5分钟*/
	    	public static Long compareDate(Date da ){
	    		
	    		Date date = new Date();
	    		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmm");
	    		String formate = dateFormater.format(date);
	    		Long endata = Long.decode(formate);

	    		SimpleDateFormat Formater = new SimpleDateFormat("yyyyMMddHHmm");
	    		formate = dateFormater.format(da);
	    		Long formater = Long.decode(formate);
	    		return formater - endata;
	    		
	    	}
	    	/**
	    	 * 
	    	* @Title: CalendarDate 
	    	* @Description: TODO(当前时间加i小时) i为小时数  1 为一小时  2 为两小时
	    	* @param @return    设定文件 
	    	* @return Date    返回类型 
	    	* @date 2017年5月9日 上午10:49:58 
	    	* @author zy 
	    	* @throws
	    	 */
	    	public Date CalendarDate(int i){
		    	Calendar c = Calendar.getInstance();  
		        c.setTime(new Date());   //设置当前日期  
		        c.add(Calendar.HOUR, i==0?0:i); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)  
		        Date date = c.getTime(); //结果  
		        System.out.println(date); 
		        
		        return date;
	    	}
	    	
	    	
	    	public static void main(String[] args) {
	    		OrderDateUtils utils=new OrderDateUtils();
	    		utils.CalendarDate(3);
	    		
			}
		
}
