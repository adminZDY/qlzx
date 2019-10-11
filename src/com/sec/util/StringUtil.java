package com.sec.util;


/**  
* 字符串工具类  
*/   
public class StringUtil {   

 /**  
  * 验证字符串是否为空（null或空字符串）  
  * @param str 要验证的字符串  
  * @return 验证结果（字符串为空返回true，否则返回false）  
  */   
 public static boolean isNullOrEmpty(String str){   
     if(str == null || "".equals(str.trim())){   
         return true;   
     }   
     return false;   
 }   
}  