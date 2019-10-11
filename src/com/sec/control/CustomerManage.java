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
 * @author ֣
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
	    //��ѯ���пͻ���Ϣ
	    if("".equals(op) || op == null || "queryAll".equals(op))
	    {
	    	System.out.println("��ѯ����");
	    	queryAll(request, response);
	    }
	    //��¼����(ǰ̨����)
	    else if("login".equals(op))
	    {
	    	login(request, response);
	    }
	    //ע�Ṧ��(ǰ̨����)
	    else if("register".equals(op))
	    {
	    	register(request, response);
	    }
	    //�����ϸ��ַ(ǰ̨����)
	    else if("addDetail".equals(op))
	    {
	    	addDetail(request, response);
	    }
	    //ע������(ǰ̨����)
	    else if("loginout".equals(op))
	    {
	    	loginout(request, response);
	    }
	    //�޸���ϸ��ַ(ǰ̨���ܡ���̨)
	    else if("updateDetail".equals(op))
	    {
	    	updateDetail(request, response);
	    }
	    else if("updateDetailInfo".equals(op)){
	    	updateDetailInfo(request, response);
	    }
	    //�޸Ŀͻ�����(ǰ̨����)
	    else if("updatePwd".equals(op))
	    {
	    	updatePwd(request, response);
	    }
	    //��ѯָ���ͻ����Կͻ������ѯ��
	    else if("query".equals(op))
	    {
	    	query(request, response);
	    }
	    /**
	     * �Ƴ�����ͻ���Ϣ
	     */
	    else if("removeMore".equals(op))
	    {
	    	removeMore(request, response);
	    }
	    else if("remove".equals(op))
	    {
	    	remove(request, response);
	    }
	    //��ת������ҳ��
	    else if("toupdate".equals(op))
	    {
	    	toupdate(request, response);
	    }
	}
	
	/**
	 * �����ϸ��Ϣ(�������)
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		//ָ����ӵı��
	 	int id = request.getParameter("id") != null?Integer.parseInt(request.getParameter("id")):0;
	System.out.println(id);
	 	if(id == 0)
	 	{
	 		//���ʧ�ܣ�Ϊָ���ͻ����
	 		request.setAttribute("failed", "��ӿͻ�������Ϣʧ��!");
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
					//��ӳɹ��������ɹ�
					request.setAttribute("ok", "��ӿͻ���Ϣ�ɹ�!");
					
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
					//���ʧ�ܣ�����ʧ��
					request.setAttribute("failed", "��ӿͻ���Ϣʧ��!");
					request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=myManage");
					request.getRequestDispatcher("../failed.jsp").forward(request, response);
				}
	 		}
//	 		else{
//	 			
//					//���ʧ�ܣ�����ʧ��
//					request.setAttribute("msg", "������д����ҳ��ʧ��,������д������Ϣ�Ƿ��޸�������Ϣ��");
//					request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=updateDetail&id="+id);
//					request.getRequestDispatcher("../error.jsp").forward(request, response);
//				
//	 		}
			
	 	}
	}

	/**
	 * ɾ���ͻ�
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
		//��ѯ����Ʒ��Ϣ���Ƿ�����ѡ��ɾ��������һ�����
		//����в�ѯ����˵��������������Ʒ��Ϣ��������ɾ������ת������ҳ��
		if(customerInfoDao.delteFilter(id))
		{
			//��ѯ������>����ҳ��
			request.setAttribute("msg", "ɾ���ͻ�����!������ѡҪɾ���ͻ��Ѿ��¹�������������ɾ��");
			request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
			request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
		}
		else {
			if(customerInfoDao.deletecustomer(id))
			{
				//��ӳɹ��������ɹ�
				request.setAttribute("ok", "ɾ���ͻ���Ϣ�ɹ�!");
				request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
				request.getRequestDispatcher("/admin/ok.jsp").forward(request, response);
			}
			//ɾ��ʧ��
			else {
				//���ʧ�ܣ�����ʧ��
				request.setAttribute("failed", "ɾ���ͻ���Ϣʧ��!");
				request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
				request.getRequestDispatcher("/admin/failed.jsp").forward(request, response);
			}
		}
	}
	
	/**
	 * ɾ������ͻ���Ϣ
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
		//��ѯ����Ʒ��Ϣ���Ƿ�����ѡ��ɾ��������һ�����
		//����в�ѯ����˵��������������Ʒ��Ϣ��������ɾ������ת������ҳ��
		if(customerInfoDao.delteFilter(id))
		{
			//��ѯ������>����ҳ��
			request.setAttribute("msg", "ɾ���ͻ�����!������ѡҪɾ���ͻ��Ѿ��¹�������������ɾ��");
			request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
			request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
		}
		else {
			 //ɾ���ɹ�
		    if(customerInfoDao.deletecustomers(id))
			{
				//��ӳɹ��������ɹ�
				request.setAttribute("ok", "ɾ���ͻ���Ϣ�ɹ�!");
				request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
				request.getRequestDispatcher("/admin/ok.jsp").forward(request, response);
			}
			//ɾ��ʧ��
			else {
				//���ʧ�ܣ�����ʧ��
				request.setAttribute("failed", "ɾ���ͻ���Ϣʧ��!");
				request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
				request.getRequestDispatcher("/admin/failed.jsp").forward(request, response);
			}
		} 
	}
	
	/**
	 * �޸��û���ַ��Ϣ�������޸ġ�ǰ̨��
	 * @param request
	 * @param response
	 */
	public void updateDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		/**
		 * ��װ���ݻ�ȡ
		 */
		CustomerDatailInfo customerDatail = new CustomerDatailInfo();
		customerDatail.setName(request.getParameter("name").trim());
		customerDatail.setMobileTelphone(request.getParameter("mtphone").trim() == ""?null:request.getParameter("mtphone").trim());
		customerDatail.setTelphone(request.getParameter("phone").trim() == ""?null:request.getParameter("phone").trim());
		customerDatail.setAddress(request.getParameter("address").trim());	
		
		CustomerInfo customer = (CustomerInfo)request.getSession().getAttribute("customer");
	
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		//ִ���޸����
		if(customerInfoDao.updateData(customer,customerDatail))
		{
			customer.setCustomerDatail(customerDatail);
			//���ͻ�������Ϣ����ͻ���
			request.getSession().setAttribute("customer",customer);
			//�޸ĳɹ�
			request.setAttribute("ok", "�޸Ŀͻ���Ϣ�ɹ�!");
			
			if(request.getSession().getAttribute("peisong") != null ){
				
				request.setAttribute("jsp", request.getSession().getAttribute("peisong"));
				request.getSession().removeAttribute("peisong");
			}else{
				request.setAttribute("jsp", "/qlzx/index.jsp");
			}
			request.getRequestDispatcher("../ok.jsp").forward(request, response);
		}
		else {
		
			//�޸�ʧ��
			request.setAttribute("failed", "�޸Ŀͻ���Ϣʧ��!");
			request.setAttribute("jsp", request.getSession().getAttribute("peisong"));
			request.getSession().removeAttribute("peisong");
			request.getRequestDispatcher("../failed.jsp").forward(request, response);
		}
	}
	
	/**
	 * �޸��û���ַ��Ϣ�������޸ġ���̨��
	 * @param request
	 * @param response
	 */
	public void updateDetailInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		System.out.println("�����޸�");
		
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
		 * ��װ���ݻ�ȡ
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
		//ִ���޸����
		if(customerInfoDao.updateData(customer, customerDatail))
		{
			//�޸ĳɹ�
			request.setAttribute("ok", "�޸Ŀͻ���Ϣ�ɹ�!");
			request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
			request.getRequestDispatcher("../ok.jsp").forward(request, response);
		}
		else {
		
			//�޸�ʧ��
			request.setAttribute("failed", "�޸Ŀͻ���Ϣʧ��!");
			request.setAttribute("jsp", "/qlzx/servlet/CustomerManage?op=queryAll");
			request.getRequestDispatcher("../failed.jsp").forward(request, response);
		}
	}
	
	
	/**
	 * ��ѯ���пͻ�����ҳ
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
	 * �޸��û���ַ��Ϣ����ʾ��Ϣ��
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
	 * ��ѯָ���ͻ�(���ݿͻ�����)����ҳ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void query(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		//��ȡ�����ѯ���ı�
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
	
	//ǰ̨����
	/**
	 * ������¼
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
		//���䣬��¼����,��ѯʱ�б��
		//���ݱ�Ų�ѯ�ͻ���ϸ��Ϣ
		//���û����Ϣ�����¶���ҳ����ת��������ַ
		CustomerInfoDao customerInfoDao = new CustomerInfoDao();
		CustomerInfo customer = new CustomerInfo();
		customer.setEmail(email);
		customer.setPwd(pwd);
		boolean flag = customerInfoDao.CustomerSelect(customer);
		
		//��¼�ɹ�
		if(flag)
		{
			request.getSession().setAttribute("customer", customer);
			out.print("true");
		}
		else 
		{
			//��¼ʧ�ܱ����ʾ���û������������
			out.print("�û����������������������");
		}
	}
	
	/**
	 * ע��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("����ע��");
		//��װ����
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		//��װ�ͻ���Ϣ
		CustomerInfo customer = new CustomerInfo();
		customer.setEmail(email);
		customer.setPwd(pwd);
		
		String name = request.getParameter("name");
		System.out.println(name);
		if (name != null)
		{
			//��װ�ͻ���ַ��Ϣ
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
		//ע��
		customerInfoDao.addUserInfo(customer);
		//��ת����һ������·��
		//ע��ɹ���ת���ɹ����棬Ȼ���û���¼
		request.setAttribute("ok", "ע��ɹ���");
		request.setAttribute("jsp", "/qlzx/login_register.jsp");
		request.getRequestDispatcher("../ok.jsp").forward(request, response);
	}
	
	/**
	 * ע��
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
				//�Ƴ���ǰ�û�
				request.getSession().removeAttribute("customer");
				response.sendRedirect("/qlzx/index.jsp");
			}
		}else{
			response.sendRedirect("/qlzx/index.jsp");
		}
	}
	
	/**
	 * �޸��û�����
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
				request.setAttribute("ok", "�û������޸ĳɹ���");
				if(request.getSession().getAttribute("peisong") != null ){
					
					request.setAttribute("jsp", request.getSession().getAttribute("peisong"));
					request.getSession().removeAttribute("peisong");
					
				}else{
					request.setAttribute("jsp", "/qlzx/index.jsp");
				}
				((CustomerInfo)request.getSession().getAttribute("customer")).setPwd(UserPwd[0]);
				request.getRequestDispatcher("../ok.jsp").forward(request, response);
			}else{
				request.setAttribute("failed", "�û������޸�ʧ�ܣ�");
				request.setAttribute("jsp", "/qlzx/index.jsp");
				request.getRequestDispatcher("../failed.jsp").forward(request, response);
			}
		}else{
			response.sendRedirect("/qlzx/login_register.jsp");
		}
	}
}
