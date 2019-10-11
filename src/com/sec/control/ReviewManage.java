package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sec.dao.GoodsInfoDao;
import com.sec.dao.OrderInfoDao;
import com.sec.model.GoodsInfo;
import com.sec.model.OrderGoodsInfo;
import com.sec.model.OrderInfo;
import com.sec.model.Review;
import com.sec.model.SalesGoods;
import com.sec.util.PageModel;

public class ReviewManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ReviewManage() {
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

		response.setContentType("text/html");
		
		String op = request.getParameter("op");
		String returnURL = "";
	System.out.println(op);
		if("".equals(op) || op == null){
			returnURL = queryAll(request,response);
		}else if("add".equals(op)){
			returnURL = add(request,response);
		}
	
		request.getRequestDispatcher(returnURL).forward(request, response);
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

		doGet(request, response);
	}

	
	public String queryAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int orderid =Integer.parseInt( request.getParameter("orderid"));
		int customerid = Integer.parseInt( request.getParameter("customerid"));
		
		OrderInfoDao dao = new OrderInfoDao();
		ArrayList<OrderGoodsInfo> orderList = dao.add
				(customerid, orderid);
		request.setAttribute("orderList", orderList);
		return "../Review.jsp";
	}
	
	public String add(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int orderid =Integer.parseInt( request.getParameter("orderid"));
		int customerid = Integer.parseInt( request.getParameter("customerid"));
		String[] goodsid = request.getParameterValues("goodsid");
		String content = request.getParameter("content");
		int reviewStatus = Integer.parseInt(request.getParameter("reviewStatus"));
	
		//System.out.println(orderid+""+customerid+""+reviewStatus);
		OrderInfoDao dao = new OrderInfoDao();
		if(dao.toAdd(orderid,customerid  , goodsid , content , reviewStatus)){
			
			dao.update(4,orderid );
			return "OrderManage?op=myManage&customerid"+customerid;
		}
		
		return queryAll(request,response);
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
