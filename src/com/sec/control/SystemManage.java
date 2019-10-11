package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sec.dao.UserInfoDao;
import com.sec.model.UserInfo;
import com.sec.util.DateTimeUtil;

/**
 * ��̨ϵͳ������
 * @author ֣
 *
 */
@SuppressWarnings("serial")
public class SystemManage extends HttpServlet {
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//��ȡ������ֵ
		String op = request.getParameter("op");
		if(op.equals("login"))
		{
			//��¼
			login(request,response);
		}else if(op.equals("loginOut"))
		{
			//ע��
			loginOut(request,response);
		}else if(op.equals("updatePwd"))
		{
			//�޸�����
			updatePwd(request, response);
		}else if(op.equals("updateName"))
		{
			//�޸�����ҵ��
			selectName(request, response);
		}
		else
		{
			response.sendRedirect("../admin.login.jsp");
		}
	}
	
	/**
	 * ��¼����֤(ajax��֤)
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�����
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		UserInfo userinfo = (UserInfo)request.getSession().getAttribute("user");
		
		//��ȡ�û�������û���������
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		//�û���Ϣ��
		UserInfo user = new UserInfo(userName,userPwd);
		//���ݿ��ѯ�Ƿ���ڸ��û�������������ʼ��UserInfo
		UserInfoDao userDao = new UserInfoDao();
		
//		//���ڣ��ж�application���Ƿ��д��ڸ��û�
//		//�洢�����������û�
		ServletContext application = this.getServletContext();
		TreeMap<String, UserInfo> AllUser = (TreeMap<String, UserInfo>) application.getAttribute("AllUser");
		AllUser = AllUser == null? new TreeMap<String, UserInfo>():AllUser;
		
		boolean exist = userDao.selectUserInfo(user);
		if(exist)
		{	
			//�����û�δ����¼
			if(!AllUser.containsKey(userName) && userinfo == null)
			{
				out.print("Login");
				//��ӵ�application�У������û���Ϣ��
				AllUser.put(userName, user);
				application.setAttribute("AllUser", AllUser);
				//�洢��ǰ�û�
				request.getSession().setAttribute("user", user);
			}
			else
			{
				out.println(AllUser.get(userName).getUserName()+"�Ѿ�����¼�������µ�¼");
				//out.println("�����Ѿ���¼"+AllUser.get(userName).getUserName()+"�������ظ���¼");
			}
		}
		//������,��ת����¼���沢��ʾ
		else
		{
			out.println("��¼ʧ�ܣ��û������������");
		}
		
//		if(AllUser == null){
//		//��һ���û�
//		AllUser = new TreeMap<String, UserInfo>();
//		AllUser.put(userName, user);
//		application.setAttribute("AllUser", AllUser);
//	}else{
//		//�����û�δ����¼
//		//�Ҳ�������Ϣ�����û�û�����ù���session���û���Ϣ
//		if(!AllUser.containsKey(userName) && userinfo == null)
//		{
//			boolean exist = userDao.selectUserInfo(user);
//			//��¼�ɹ�
//			if(exist)
//			{
//				out.print("Login");
//				//��ӵ�application�У������û���Ϣ��
//				AllUser.put(userName, user);
//				application.setAttribute("AllUser", AllUser);
//				//�洢��ǰ�û�
//				request.getSession().setAttribute("user", user);
//			}else{
//				out.println("��¼ʧ�ܣ��û������������");
//			}
//		}
//		else{
//			//session�������Ϣ��app�д����������
//			out.println(userName+"�Ѿ�����¼�������µ�¼");
//		}
//	}
	}
	
	/**
	 * ע���û�
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loginOut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("����ע��");
		//��ŵ�ǰ�û�
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		user = user == null?new UserInfo():user;
		
		//�洢�����û�
		ServletContext application = super.getServletContext();
		TreeMap<String, UserInfo> AllUser = (TreeMap<String, UserInfo>) application.getAttribute("AllUser");
		AllUser = AllUser == null ? new TreeMap<String, UserInfo>(): AllUser;
			
		for (String usere : AllUser.keySet()) {
			System.out.println("1���"+AllUser.get(usere).getUserId());
			System.out.println("1����"+AllUser.get(usere).getUserName());
		}
//		//�û�ע���ɹ�
//		if(AllUser.containsKey(user.getUserName()))
//		{
//			//�Ƴ���application�У���ǰ�û���
//			AllUser.remove(user.getUserName());
//		}
		//System.out.println(request.getSession().getAttribute("user") == null?"1��":"1����");
		//�Ƴ���ǰ�û�
		request.getSession().invalidate();
		//System.out.println(request.getSession().getAttribute("user") == null?"3��":"3����");
		//��ת�ص�¼����
		response.sendRedirect("../admin/login.jsp");
	}
	
	/**
	 * �޸��û�����
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updatePwd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("�޸�����");
		//��ȡ�û���������
		String userPwd = (String) request.getParameter("newPwd");
		//��ŵ�ǰ�û�
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		user.setUserPwd(userPwd);
		UserInfoDao userDao = new UserInfoDao();
		boolean ifOk = userDao.updatePwd(user);
		System.out.println(ifOk);
		if(ifOk)
		{
			
			request.getSession().invalidate();
			//
			request.setAttribute("ok", "�޸�����ɹ�,����������µ�¼");
			//���ص�·��
			//request.setAttribute("jsp", "/qlzx/admin/welcome.jsp");
			request.setAttribute("jsp", "/qlzx/admin/userLogin.jsp");
			//�����ɹ�
			request.getRequestDispatcher("../admin/ok.jsp").forward(request, response);
		}
		else
		{
			//
			request.setAttribute("failed", "�޸�����ʧ��");
			//���ص�·��
			request.setAttribute("jsp", "/qlzx/admin/welcome.jsp");
			//����ʧ��
			response.sendRedirect("../admin/failed.jsp");
		}
	}
	
	/**
	 * �޸�����
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateName(HttpServletRequest request, HttpServletResponse response,UserInfo user,UserInfoDao userDao)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		boolean ifOk = userDao.updateName(user);
		if(ifOk)
		{
			//��ȡͨѶ��Ϣ
			ServletContext application = super.getServletContext();
			UserInfo oldUserInfo = (UserInfo)request.getSession().getAttribute("user");
			
			
			TreeMap<String, UserInfo> AllUser = (TreeMap<String, UserInfo>) application.getAttribute("AllUser");
			AllUser = AllUser == null ? new TreeMap<String, UserInfo>(): AllUser;
			AllUser.remove(oldUserInfo.getUserName());
			
			//���ÿͻ�
			request.getSession().setAttribute("user", user);
			application.setAttribute("AllUser", AllUser);
			//�����ɹ�
			out.print(user.getUserName());
		}
		else
		{
			//����ʧ��
			out.print("failed");
		}
	}

	/**
	 * ��ѯ�û����Ƿ�ʹ��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void selectName(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		//��ȡ�û���������
		String userName = (String) request.getParameter("userName");
		//��ŵ�ǰ�û�
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		UserInfoDao userDao = new UserInfoDao();
		boolean ifexists = userDao.UserNameExists(user, userName);
		//�û���δ��ʹ��
		if(!ifexists)
		{
			user.setUserName(userName);
			
			/**
			 * ִ���޸� 
			 */
			updateName(request, response,user,userDao);
		}
		else
		{
			//�û����Ѿ���ʹ��
			out.print("true");
		}
	}
}
