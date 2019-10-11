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
 * 后台系统管理类
 * @author 郑
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
		//获取操作的值
		String op = request.getParameter("op");
		if(op.equals("login"))
		{
			//登录
			login(request,response);
		}else if(op.equals("loginOut"))
		{
			//注销
			loginOut(request,response);
		}else if(op.equals("updatePwd"))
		{
			//修改密码
			updatePwd(request, response);
		}else if(op.equals("updateName"))
		{
			//修改名称业务
			selectName(request, response);
		}
		else
		{
			response.sendRedirect("../admin.login.jsp");
		}
	}
	
	/**
	 * 登录的验证(ajax验证)
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//输出流
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		UserInfo userinfo = (UserInfo)request.getSession().getAttribute("user");
		
		//获取用户输入的用户名和密码
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		//用户信息类
		UserInfo user = new UserInfo(userName,userPwd);
		//数据库查询是否存在该用户，如果存在则初始化UserInfo
		UserInfoDao userDao = new UserInfoDao();
		
//		//存在，判断application中是否有存在该用户
//		//存储服务中所有用户
		ServletContext application = this.getServletContext();
		TreeMap<String, UserInfo> AllUser = (TreeMap<String, UserInfo>) application.getAttribute("AllUser");
		AllUser = AllUser == null? new TreeMap<String, UserInfo>():AllUser;
		
		boolean exist = userDao.selectUserInfo(user);
		if(exist)
		{	
			//本机用户未被登录
			if(!AllUser.containsKey(userName) && userinfo == null)
			{
				out.print("Login");
				//添加到application中（所有用户信息）
				AllUser.put(userName, user);
				application.setAttribute("AllUser", AllUser);
				//存储当前用户
				request.getSession().setAttribute("user", user);
			}
			else
			{
				out.println(AllUser.get(userName).getUserName()+"已经被登录，请重新登录");
				//out.println("本机已经登录"+AllUser.get(userName).getUserName()+"，不能重复登录");
			}
		}
		//不存在,跳转到登录界面并提示
		else
		{
			out.println("登录失败，用户名或密码错误");
		}
		
//		if(AllUser == null){
//		//第一个用户
//		AllUser = new TreeMap<String, UserInfo>();
//		AllUser.put(userName, user);
//		application.setAttribute("AllUser", AllUser);
//	}else{
//		//本机用户未被登录
//		//找不到该信息并且用户没有设置过（session）用户信息
//		if(!AllUser.containsKey(userName) && userinfo == null)
//		{
//			boolean exist = userDao.selectUserInfo(user);
//			//登录成功
//			if(exist)
//			{
//				out.print("Login");
//				//添加到application中（所有用户信息）
//				AllUser.put(userName, user);
//				application.setAttribute("AllUser", AllUser);
//				//存储当前用户
//				request.getSession().setAttribute("user", user);
//			}else{
//				out.println("登录失败，用户名或密码错误");
//			}
//		}
//		else{
//			//session有这个信息或app中存在这个名称
//			out.println(userName+"已经被登录，请重新登录");
//		}
//	}
	}
	
	/**
	 * 注销用户
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loginOut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("正在注销");
		//存放当前用户
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		user = user == null?new UserInfo():user;
		
		//存储所有用户
		ServletContext application = super.getServletContext();
		TreeMap<String, UserInfo> AllUser = (TreeMap<String, UserInfo>) application.getAttribute("AllUser");
		AllUser = AllUser == null ? new TreeMap<String, UserInfo>(): AllUser;
			
		for (String usere : AllUser.keySet()) {
			System.out.println("1编号"+AllUser.get(usere).getUserId());
			System.out.println("1名称"+AllUser.get(usere).getUserName());
		}
//		//用户注销成功
//		if(AllUser.containsKey(user.getUserName()))
//		{
//			//移除在application中（当前用户）
//			AllUser.remove(user.getUserName());
//		}
		//System.out.println(request.getSession().getAttribute("user") == null?"1空":"1不空");
		//移除当前用户
		request.getSession().invalidate();
		//System.out.println(request.getSession().getAttribute("user") == null?"3空":"3不空");
		//跳转回登录界面
		response.sendRedirect("../admin/login.jsp");
	}
	
	/**
	 * 修改用户密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updatePwd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("修改密码");
		//获取用户的新密码
		String userPwd = (String) request.getParameter("newPwd");
		//存放当前用户
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		user.setUserPwd(userPwd);
		UserInfoDao userDao = new UserInfoDao();
		boolean ifOk = userDao.updatePwd(user);
		System.out.println(ifOk);
		if(ifOk)
		{
			
			request.getSession().invalidate();
			//
			request.setAttribute("ok", "修改密码成功,点击返回重新登录");
			//返回的路径
			//request.setAttribute("jsp", "/qlzx/admin/welcome.jsp");
			request.setAttribute("jsp", "/qlzx/admin/userLogin.jsp");
			//操作成功
			request.getRequestDispatcher("../admin/ok.jsp").forward(request, response);
		}
		else
		{
			//
			request.setAttribute("failed", "修改密码失败");
			//返回的路径
			request.setAttribute("jsp", "/qlzx/admin/welcome.jsp");
			//操作失败
			response.sendRedirect("../admin/failed.jsp");
		}
	}
	
	/**
	 * 修改名称
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
			//获取通讯信息
			ServletContext application = super.getServletContext();
			UserInfo oldUserInfo = (UserInfo)request.getSession().getAttribute("user");
			
			
			TreeMap<String, UserInfo> AllUser = (TreeMap<String, UserInfo>) application.getAttribute("AllUser");
			AllUser = AllUser == null ? new TreeMap<String, UserInfo>(): AllUser;
			AllUser.remove(oldUserInfo.getUserName());
			
			//重置客户
			request.getSession().setAttribute("user", user);
			application.setAttribute("AllUser", AllUser);
			//操作成功
			out.print(user.getUserName());
		}
		else
		{
			//操作失败
			out.print("failed");
		}
	}

	/**
	 * 查询用户名是否被使用
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void selectName(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		//获取用户的新密码
		String userName = (String) request.getParameter("userName");
		//存放当前用户
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		UserInfoDao userDao = new UserInfoDao();
		boolean ifexists = userDao.UserNameExists(user, userName);
		//用户名未被使用
		if(!ifexists)
		{
			user.setUserName(userName);
			
			/**
			 * 执行修改 
			 */
			updateName(request, response,user,userDao);
		}
		else
		{
			//用户名已经被使用
			out.print("true");
		}
	}
}
