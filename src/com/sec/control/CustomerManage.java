package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sec.dao.CustomerInfoDao;
import com.sec.model.CustomerDatailInfo;
import com.sec.model.CustomerInfo;
import com.sec.util.PageModel;
import com.sec.util.StringUtil;

/**
 * 
 * @author 郑
 *
 */
public class CustomerManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CustomerManage() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

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
		response.setContentType("text/html;charset=utf-8");
		
	    String op =	request.getParameter("op");
	    //查询所有客户信息
	    if("".equals(op) || op == null || "queryAll".equals(op))
	    {
	    	System.out.println("查询所有");
	    	queryAll(request, response);
	    }
	    //登录功能(前台功能)
	    else if("login".equals(op))
	    {
	    	login(request, response);
	    }
	    //注册功能(前台功能)
	    else if("register".equals(op))
	    {
	    	register(request, response);
	    }
	    //添加详细地址(前台功能)
	    else if("addDetail".equals(op))
	    {
	    	addDetail(request, response);
	    }
	    //注销功能(前台功能)
	    else if("loginout".equals(op))
	    {
	    	loginout(request, response);
	    }
	    //修改详细地址(前台功能、后台)
	    else if("updateDetail".equals(op))
	    {
	    	updateDetail(request, response);
	    }
	    else if("updateDetailInfo".equals(op)){
	    	updateDetailInfo(request, response);
	    }
	    //修改客户密码(前台功能)
	    else if("updatePwd".equals(op))
	    {
	    	updatePwd(request, response);
	    }
	    //查询指定客户（以客户邮箱查询）
	    else if("query".equals(op))
	    {
	    	query(request, response);
	    }
	    /**
	     * 移除多个客户信息
	     */
	    else if("removeMore".equals(op))
	    {
	    	removeMore(request, response);
	    }
	    else if("remove".equals(op))
	    {
	    	remove(request, response);
	    }
	    //跳转到更新页面
	    else if("toupdate".equals(op))
	    {
	    	toupdate(request, response);
	    }
	}
	
	/**
	 * 添加详细信息(单独添加)
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		//指定添加的编号
	 	int id = request.getParameter("id") != null?Integer.parseInt(request.getParameter("id")):0;
	System.out.println(id);
	 	if(id == 0)
	 	{
	 		//添加失败，为指定客户编号
	 		request.setAttribute("failed", "添加客户配送信息失败!");
			request.getRequestDispatcher("../failed.jsp").forward(request, response);
	 	}
	 	else {
	 		HttpSession session = request.getSession();
	 		
	 		CustomerDatailInfo customerDatail = new CustomerDatailInfo();
			customerDatail.setName(request.getParameter("name").trim());
			customerDatail.setMobileTelphone(request.getParameter("mtphone").trim() == ""?null:request.getParameter("mtphone").trim());
			customerDatail.setTelphone(request.getParameter("phone").trim() == ""?null:request.getParameter("phone").trim());
			customerDatail.setAddress(request.getParameter("address").trim());	
			
			
			CustomerInfoDao customerInfoDao = new CustomerInfoDao();
			
	 		CustomerInfo cus = (CustomerInfo) session.getAttribute("customer");
	 		
	 		if("".equals(cus.getCustomerDatail().getAddress())||cus.getCustomerDatail().getAddress() == null){
	 			
				if(customerInfoDao.insertDetail(id, customerDatail))
				{
					//添加成功，操作成功
					request.setAttribute("ok", "添加客户信息成功!");
					
					String customr = (String)session.getAttribute("peisong");
					
					if(customr != null && !"".equals(customr)){
						session.removeAttribute("peisong");
						cus.setCustomerDatail(customerDatail);
						System.out.println(customr);
						request.setAttribute("jsp", customr);
						request.getRequestDispatcher("../ok.jsp").forward(request, response);
						return;
					}
					
					request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=myManage");
					request.getRequestDispatcher("../ok.jsp").forward(request, response);
				}
				else {
					//添加失败，操作失败
					request.setAttribute("failed", "添加客户信息失败!");
					request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=myManage");
					request.getRequestDispatcher("../failed.jsp").forward(request, response);
				}
	 		}
//	 		else{
//	 			
//					//添加失败，操作失败
//					request.setAttribute("msg", "访问填写配送页面失败,您已填写配送信息是否修改配送信息！");
//					request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=updateDetail&id="+id);
//					request.getRequestDispatcher("../error.jsp").forward(request, response);
//				
//	 		}
			
	 	}
	}

	/**
	 * 删除客户
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = 0;
		try {
		   id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {} 
		
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		//查询在商品信息中是否有我选择删除例其中一个编号
		//如果有查询到则说明，该类型有商品信息，不允许删除，跳转到错误页面
		if(customerInfoDao.delteFilter(id))
		{
			//查询到——>错误页面
			request.setAttribute("msg", "删除客户错误!，你所选要删除客户已经下过订单，不允许删除");
			request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
			request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
		}
		else {
			if(customerInfoDao.deletecustomer(id))
			{
				//添加成功，操作成功
				request.setAttribute("ok", "删除客户信息成功!");
				request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
				request.getRequestDispatcher("/admin/ok.jsp").forward(request, response);
			}
			//删除失败
			else {
				//添加失败，操作失败
				request.setAttribute("failed", "删除客户信息失败!");
				request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
				request.getRequestDispatcher("/admin/failed.jsp").forward(request, response);
			}
		}
	}
	
	/**
	 * 删除多个客户信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void removeMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String []idString = request.getParameterValues("chkItems");
		int [] id = new int[idString.length];  
		for (int i = 0; i < idString.length; i++) {
			id[i] = Integer.parseInt(idString[i]);
		}
		
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		//查询在商品信息中是否有我选择删除例其中一个编号
		//如果有查询到则说明，该类型有商品信息，不允许删除，跳转到错误页面
		if(customerInfoDao.delteFilter(id))
		{
			//查询到——>错误页面
			request.setAttribute("msg", "删除客户错误!，你所选要删除客户已经下过订单，不允许删除");
			request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
			request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
		}
		else {
			 //删除成功
		    if(customerInfoDao.deletecustomers(id))
			{
				//添加成功，操作成功
				request.setAttribute("ok", "删除客户信息成功!");
				request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
				request.getRequestDispatcher("/admin/ok.jsp").forward(request, response);
			}
			//删除失败
			else {
				//添加失败，操作失败
				request.setAttribute("failed", "删除客户信息失败!");
				request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
				request.getRequestDispatcher("/admin/failed.jsp").forward(request, response);
			}
		} 
	}
	
	/**
	 * 修改用户地址信息（数据修改。前台）
	 * @param request
	 * @param response
	 */
	public void updateDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		/**
		 * 封装数据获取
		 */
		CustomerDatailInfo customerDatail = new CustomerDatailInfo();
		customerDatail.setName(request.getParameter("name").trim());
		customerDatail.setMobileTelphone(request.getParameter("mtphone").trim() == ""?null:request.getParameter("mtphone").trim());
		customerDatail.setTelphone(request.getParameter("phone").trim() == ""?null:request.getParameter("phone").trim());
		customerDatail.setAddress(request.getParameter("address").trim());	
		
		CustomerInfo customer = (CustomerInfo)request.getSession().getAttribute("customer");
	
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		//执行修改语句
		if(customerInfoDao.updateData(customer,customerDatail))
		{
			customer.setCustomerDatail(customerDatail);
			//将客户配送消息存入客户中
			request.getSession().setAttribute("customer",customer);
			//修改成功
			request.setAttribute("ok", "修改客户信息成功!");
			
			if(request.getSession().getAttribute("peisong") != null ){
				
				request.setAttribute("jsp", request.getSession().getAttribute("peisong"));
				request.getSession().removeAttribute("peisong");
			}else{
				request.setAttribute("jsp", "/qlzx/index.jsp");
			}
			request.getRequestDispatcher("../ok.jsp").forward(request, response);
		}
		else {
		
			//修改失败
			request.setAttribute("failed", "修改客户信息失败!");
			request.setAttribute("jsp", request.getSession().getAttribute("peisong"));
			request.getSession().removeAttribute("peisong");
			request.getRequestDispatcher("../failed.jsp").forward(request, response);
		}
	}
	
	/**
	 * 修改用户地址信息（数据修改。后台）
	 * @param request
	 * @param response
	 */
	public void updateDetailInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		System.out.println("真正修改");
		
//		String name =  new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
//		String address = new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8"); 
//		String telphone = request.getParameter("phone");
//		String mobileTelphone = request.getParameter("mtphone");
		
//		CustomerDatailInfo customerDatail = new CustomerDatailInfo();
//		customerDatail.setName(name);
//		customerDatail.setCustomerId(customerId);
//		customerDatail.setTelphone(telphone);
//		customerDatail.setMobileTelphone(mobileTelphone);
//		customerDatail.setAddress(address);
		
		/**
		 * 封装数据获取
		 */
		CustomerDatailInfo customerDatail = new CustomerDatailInfo();
		customerDatail.setName(request.getParameter("name").trim());
		customerDatail.setMobileTelphone(request.getParameter("mtphone").trim() == ""?null:request.getParameter("mtphone").trim());
		customerDatail.setTelphone(request.getParameter("phone").trim() == ""?null:request.getParameter("phone").trim());
		customerDatail.setAddress(request.getParameter("address").trim());	
		int id = Integer.parseInt(request.getParameter("id"));
		
		CustomerInfo customer = new CustomerInfo();
		customer.setId(id);
	
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		//执行修改语句
		if(customerInfoDao.updateData(customer, customerDatail))
		{
			//修改成功
			request.setAttribute("ok", "修改客户信息成功!");
			request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
			request.getRequestDispatcher("../ok.jsp").forward(request, response);
		}
		else {
		
			//修改失败
			request.setAttribute("failed", "修改客户信息失败!");
			request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
			request.getRequestDispatcher("../failed.jsp").forward(request, response);
		}
	}
	
	
	/**
	 * 查询所有客户，分页
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void queryAll(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException{
		int pageSize = 4;
		int pageNo = 1;
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} catch (NumberFormatException e) {
		}
		
		PageModel<CustomerInfo> pm = new PageModel<CustomerInfo>();
		pm.setPageSize(pageSize);
		pm.setPageNo(pageNo);
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		customerInfoDao.selectPage(pm);
		

		request.setAttribute("customers", pm);
		request.getRequestDispatcher("/admin/customerManage.jsp").forward(request, response);
	}
	
	/**
	 * 修改用户地址信息（显示信息）
	 * @param request
	 * @param response
	 */
	public void toupdate(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {}
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		CustomerInfo customer = new CustomerInfo();
		customer.setId(id);
		customerInfoDao.selectCustomer(customer);
//		System.out.println(customer.getId());
//		System.out.println(customer.getCustomerDatail().getName());
//		System.out.println(customer.getCustomerDatail().getAddress());
//		System.out.println(customer.getCustomerDatail().getMobileTelphone());
//		System.out.println(customer.getCustomerDatail().getTelphone());
		request.setAttribute("customer", customer);
		request.getRequestDispatcher("/admin/updateCustomer.jsp").forward(request, response);
	}
	
	
	/**
	 * 查询指定客户(根据客户名称)，分页
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void query(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		//获取请求查询的文本
		String selectText = request.getParameter("text");
		selectText = new String(request.getParameter("text").getBytes("iso-8859-1"),"utf-8");
		int pageSize = 4;
		int pageNo = 1;
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		
		PageModel<CustomerInfo> pm = new PageModel<CustomerInfo>();
		pm.setPageNo(pageNo);
		pm.setPageSize(pageSize);
		
		customerInfoDao.selectCasePage(pm, selectText);
		System.out.println(pm.getData().isEmpty());
		if(pm.getData().isEmpty()){
			selectText = "";
			customerInfoDao.selectCasePage(pm, selectText);
		}
		request.setAttribute("customers", pm);
		request.setAttribute("selectText", selectText);
		request.getRequestDispatcher("/admin/customerManage.jsp").forward(request, response);
	}
	
	//前台功能
	/**
	 * 正常登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		//邮箱，登录密码,查询时有编号
		//根据编号查询客户详细信息
		//如果没有信息，在下订单页面跳转到新增地址
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		CustomerInfo customer = new CustomerInfo();
		customer.setEmail(email);
		customer.setPwd(pwd);
		boolean flag = customerInfoDao.CustomerSelect(customer);
		
		//登录成功
		if(flag)
		{
			request.getSession().setAttribute("customer", customer);
			out.print("true");
		}
		else 
		{
			//登录失败标记提示，用户名或密码错误
			out.print("用户名或密码错误，请重新输入");
		}
	}
	
	/**
	 * 注册
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入注册");
		//封装数据
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		//封装客户信息
		CustomerInfo customer = new CustomerInfo();
		customer.setEmail(email);
		customer.setPwd(pwd);
		
		String name = request.getParameter("name");
		System.out.println(name);
		if (name != null)
		{
			//封装客户地址信息
			customer.getCustomerDatail().setName(request.getParameter("name"));
			customer.getCustomerDatail().setMobileTelphone(request.getParameter("mtphone").trim() == ""?null:request.getParameter("mtphone").trim());
			customer.getCustomerDatail().setTelphone(request.getParameter("phone").trim() == ""?null:request.getParameter("phone").trim());
			customer.getCustomerDatail().setAddress(request.getParameter("address"));
		}
		System.out.println(customer.getCustomerDatail().getName());
		System.out.println(customer.getCustomerDatail().getMobileTelphone());
		System.out.println(customer.getCustomerDatail().getTelphone());
		System.out.println(customer.getCustomerDatail().getAddress());
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		//注册
		customerInfoDao.addUserInfo(customer);
		//跳转回上一个请求路径
		//注册成功调转到成功界面，然后用户登录
		request.setAttribute("ok", "注册成功！");
		request.setAttribute("jsp", "/qlzx/login_register.jsp");
		request.getRequestDispatcher("../ok.jsp").forward(request, response);
	}
	
	/**
	 * 注销
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void loginout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		CustomerInfo customer = (CustomerInfo) request.getSession().getAttribute("customer");
		if(customer != null)
		{
			if (customer.getEmail() != null)
			{
				//移除当前用户
				request.getSession().removeAttribute("customer");
				response.sendRedirect("/qlzx/index.jsp");
			}
		}else{
			response.sendRedirect("/qlzx/index.jsp");
		}
	}
	
	/**
	 * 修改用户密码
	 * @param request
	 * @param response
	 */
	public void updatePwd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String[] UserPwd = request.getParameterValues("newPwd");
		
		if(request.getSession().getAttribute("customer") != null ){
			int id = ((CustomerInfo)request.getSession().getAttribute("customer")).getId();
		
			CustomerInfoDao dao = new CustomerInfoDao();
		
			if(dao.updateUserPwd(UserPwd[0],id)){
				request.setAttribute("ok", "用户密码修改成功！");
				if(request.getSession().getAttribute("peisong") != null ){
					
					request.setAttribute("jsp", request.getSession().getAttribute("peisong"));
					request.getSession().removeAttribute("peisong");
					
				}else{
					request.setAttribute("jsp", "/qlzx/index.jsp");
				}
				((CustomerInfo)request.getSession().getAttribute("customer")).setPwd(UserPwd[0]);
				request.getRequestDispatcher("../ok.jsp").forward(request, response);
			}else{
				request.setAttribute("failed", "用户密码修改失败！");
				request.setAttribute("jsp", "/qlzx/index.jsp");
				request.getRequestDispatcher("../failed.jsp").forward(request, response);
			}
		}else{
			response.sendRedirect("/qlzx/login_register.jsp");
		}
	}
}
