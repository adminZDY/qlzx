package com.user;

import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sec.model.UserInfo;

/**
 * ���û�ͨ������������һ����վ��ʱ��������״η��ʣ���ô�������վ�ķ������˶��ᴴ��һ��session������һЩ��������û�����Ϣ��
 * 
 * �ڴ���session��ʱ����ʵ�ǻᴥ��һ��sessionCreated�¼��ģ�ͬ���ģ����û������˳��������û���½�˲��˳�����session�������ڽ�����ʱ��
 * 
 * �ͻᴥ��һ��sessionDestroyed�¼����������¼����ǿ���ͨ��HttpSessionListener�������������������԰�����׽��
 * 
 * @author Administrator
 *
 */
public class listener implements HttpSessionListener {

	public int count=0;//��¼session������
	/**
	 * ����session�Ĵ���
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("����");
	}

	
	/**
	 * ����session�ĳ���
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		 HttpSession session = event.getSession();
		 ServletContext application = session.getServletContext(); 
         UserInfo user = (UserInfo)session.getAttribute("user");  
        //System.out.println(session.getAttribute("user") == null?"2��":"2����");
         try{
        	 System.out.println(user.getUserName());
         }catch(Exception e){
        	System.out.println("�쳣"); 
         }
         if(user != null)
         {
	         @SuppressWarnings("unchecked")
			TreeMap<String, UserInfo> AllUser = (TreeMap<String, UserInfo>) application.getAttribute("AllUser");
	         
	         //ѭ��AllUser���û�ɾ��
	         for(String keyString:AllUser.keySet()){ 
	             //������û���ServletContext�������Ƴ�
	        	 //System.out.println(keyString.equals(user.getUserName()));
	        	 System.out.println(AllUser.get(keyString).getUserName().equals(user.getUserName()) == true?"�Ƴ�":"�����Ƴ�");
	        	 if(AllUser.get(keyString).getUserName().equals(user.getUserName()))
	        	 {
//	        		 for (String usere : AllUser.keySet()) {
//	        				System.out.println("2���"+AllUser.get(usere).getUserId());
//	        				System.out.println("2����"+AllUser.get(usere).getUserName());
//	        			}
	        		 AllUser.remove(user.getUserName());
	        		 application.setAttribute("AllUser",AllUser);
//	        		 for (String usere : AllUser.keySet()) {
//	        				System.out.println("3���"+AllUser.get(usere).getUserId());
//	        				System.out.println("4����"+AllUser.get(usere).getUserName());
//	        			}
	        		 break;
	        	 }
	         }
         }
         //��session���ó���Ч  
         session.setMaxInactiveInterval(0);
      //   session.invalidate();   
         System.out.println("һ��Session��������!");   
	}
}
