package com.sec.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间公共类
 * @author 郑
 *
 */
public class DateTimeUtil {
	
	/**
	 * 字符串数据类型转为Date
	 * @param Time
	 * @return
	 */
	public static Date convertDate(String Time){
		
		Date date = null;

		try{
			date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Time);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Date类型转为 字符串数据
	 * @param time
	 * @return
	 */
	public static String ConvertDate(Date time){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(time);
	}
	
	/**
	 * Date类型转为 字符串数据(年月日)
	 * @param time
	 * @return
	 */
	public static String ConvertDateYMD(Date time){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(time);
	}
	
}
