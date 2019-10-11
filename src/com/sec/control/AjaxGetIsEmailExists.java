package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sec.dao.CustomerInfoDao;
import com.sec.model.CustomerInfo;

public class AjaxGetIsEmailExists extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxGetIsEmailExists() {
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
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8;");
		//��ȡ��֤����
		String email = request.getParameter("email");//��ȡ�������
		CustomerInfoDao Dao = new CustomerInfoDao();
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setEmail(email);
		//�ж������Ƿ�ʹ��
		boolean flag = Dao.IfExistsEmail(customerInfo);
		if(flag)
		{
			//�Ѿ���ʹ�ã��޷�ע��
			out.print("�������ѱ�ע�ᣬ��������������");
		}
		else
		{
			//���Ա�ע��
			out.print("�������ע��");
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
