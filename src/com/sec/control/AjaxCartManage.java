package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sec.dao.OrderInfoDao;
import com.sec.model.Cart;
import com.sec.model.CustomerInfo;
import com.sec.model.GoodsInfo;
import com.sec.model.OrderGoodsInfo;
import com.sec.util.StringUtil;

public class AjaxCartManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AjaxCartManage() {
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
	 *加入购物车
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
	
		//判断用户是否登录
		if(customer == null ){
			String id = request.getParameter("id");
			session.setAttribute("togo", "/qlzx/servlet/GoodsManage?op=query&goodsid="+id);
			out.println("/qlzx/login_register.jsp");
			return;
		}
		String quantity = request.getParameter("txt");
		Cart cart = (Cart) session.getAttribute("cart");
		
		if(cart == null){
			cart = new Cart();
		}
		
		String id = request.getParameter("id");
		
		OrderGoodsInfo ordergoods = new OrderInfoDao().select(Integer.parseInt(id));
		
		GoodsInfo goods = ordergoods.getGoodsInfo();
		
		if(goods != null){
			
			cart.addGoods(goods,Integer.parseInt(quantity));
			session.setAttribute("cart", cart);
			out.print("addok");
			
		}
		out.flush();
		out.close();
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
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
	
		//判断用户是否登录
		if(customer == null ){
			String id = request.getParameter("id");
			session.setAttribute("togo", "/qlzx/servlet/GoodsManage?op=query&goodsid="+id);
			out.println("/qlzx/login_register.jsp");
			return;
		}
		String quantity = request.getParameter("txt");
		Cart cart = (Cart) session.getAttribute("cart");
		
		if(cart == null){
			cart = new Cart();
		}
		
		String id = request.getParameter("id");
		
		OrderGoodsInfo ordergoods = new OrderInfoDao().select(Integer.parseInt(id));
		
		GoodsInfo goods = ordergoods.getGoodsInfo();
		
		if(goods != null){
			
			cart.addGoods(goods,Integer.parseInt(quantity));
			session.setAttribute("cart", cart);
			out.print("addok");
			
		}
		out.flush();
		out.close();
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
