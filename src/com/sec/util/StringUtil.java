package com.sec.util;


/**  
* �ַ���������  
*/   
public class StringUtil {   

 /**  
  * ��֤�ַ����Ƿ�Ϊ�գ�null����ַ�����  
  * @param str Ҫ��֤���ַ���  
  * @return ��֤������ַ���Ϊ�շ���true�����򷵻�false��  
  */   
 public static boolean isNullOrEmpty(String str){   
     if(str == null || "".equals(str.trim())){   
         return true;   
     }   
     return false;   
 }   
}  