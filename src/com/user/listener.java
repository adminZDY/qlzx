package com.user;

import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sec.model.UserInfo;

/**
 * 当用户通过网络来访问一个网站的时候，如果是首次访问，那么在这个网站的服务器端都会创建一个session来保存一些属于这个用户的信息。
 * 
 * 在创建session的时候其实是会触发一个sessionCreated事件的，同样的，当用户正常退出或者是用户登陆了不退出并当session生命周期结束的时候，
 * 
 * 就会触发一个sessionDestroyed事件。这两个事件我们可以通过HttpSessionListener监听器来监听到并可以把它捕捉。
 * 
 * @author Administrator
 *
 */
public class listener implements HttpSessionListener {

	public int count=0;//记录session的数量
	/**
	 * 监听session的创建
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("创建");
	}

	
	/**
	 * 监听session的撤销
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		 HttpSession session = event.getSession();
		 ServletContext application = session.getServletContext(); 
         UserInfo user = (UserInfo)session.getAttribute("user");  
        //System.out.println(session.getAttribute("user") == null?"2空":"2不空");
         try{
        	 System.out.println(user.getUserName());
         }catch(Exception e){
        	System.out.println("异常"); 
         }
         if(user != null)
         {
	         @SuppressWarnings("unchecked")
			TreeMap<String, UserInfo> AllUser = (TreeMap<String, UserInfo>) application.getAttribute("AllUser");
	         
	         //循环AllUser将用户删除
	         for(String keyString:AllUser.keySet()){ 
	             //将这个用户从ServletContext对象中移除
	        	 //System.out.println(keyString.equals(user.getUserName()));
	        	 System.out.println(AllUser.get(keyString).getUserName().equals(user.getUserName()) == true?"移除":"不能移除");
	        	 if(AllUser.get(keyString).getUserName().equals(user.getUserName()))
	        	 {
//	        		 for (String usere : AllUser.keySet()) {
//	        				System.out.println("2编号"+AllUser.get(usere).getUserId());
//	        				System.out.println("2名称"+AllUser.get(usere).getUserName());
//	        			}
	        		 AllUser.remove(user.getUserName());
	        		 application.setAttribute("AllUser",AllUser);
//	        		 for (String usere : AllUser.keySet()) {
//	        				System.out.println("3编号"+AllUser.get(usere).getUserId());
//	        				System.out.println("4名称"+AllUser.get(usere).getUserName());
//	        			}
	        		 break;
	        	 }
	         }
         }
         //将session设置成无效  
         session.setMaxInactiveInterval(0);
      //   session.invalidate();   
         System.out.println("一个Session被销毁了!");   
	}
}
