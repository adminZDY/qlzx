package com.sec.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ʱ�乫����
 * @author ֣
 *
 */
public class DateTimeUtil {
	
	/**
	 * �ַ�����������תΪDate
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
	 * Date����תΪ �ַ�������
	 * @param time
	 * @return
	 */
	public static String ConvertDate(Date time){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(time);
	}
	
	/**
	 * Date����תΪ �ַ�������(������)
	 * @param time
	 * @return
	 */
	public static String ConvertDateYMD(Date time){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(time);
	}
	
}
