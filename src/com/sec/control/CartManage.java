package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sec.dao.GoodsInfoDao;
import com.sec.dao.OrderInfoDao;
import com.sec.model.Cart;
import com.sec.model.CustomerInfo;
import com.sec.model.GoodsInfo;
import com.sec.model.OrderGoodsInfo;
import com.sec.model.SalesGoods;
import com.sec.util.StringUtil;

public class CartManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CartManage() {
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

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
		//判断用户是否登录
		if(customer == null ){
			String id = request.getParameter("id");
			session.setAttribute("togo", "/qlzx/servlet/GoodsManage?op=query&goodsid="+id);
		  	response.sendRedirect("../login_register.jsp");	
		  	return;
		}
		
		String op = request.getParameter("op");
		if("addGoods".equals(op)){
			addGoods(request,response);
		}else if("removeGoods".equals(op)){
			removeGoods(request,response);
		}else if("update".equals(op)){
			update(request,response);
		}else if("clearCart".equals(op)){
			clearCart(request,response);
		}
	}

	//加入购物车
	public void addGoods(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String quantity = request.getParameter("txt");
		Cart cart = (Cart) session.getAttribute("cart");
		
		if(cart == null){
			cart = new Cart();
		}
		
		String id = request.getParameter("id");
		
		if(StringUtil.isNullOrEmpty(id)){
			
			response.sendRedirect("error.jsp");
		}
		
		OrderGoodsInfo ordergoods = new OrderInfoDao().select(Integer.parseInt(id));
		
		GoodsInfo goods = ordergoods.getGoodsInfo();
		
		if(goods != null){
			
			cart.addGoods(goods,Integer.parseInt(quantity));
			session.setAttribute("cart", cart);
			session.setAttribute("addok", "addok");
			response.sendRedirect("GoodsManage?op=query&goodsid="+id);
		}else{
			response.sendRedirect("error.jsp");
		}
	}
	
	//删除购物车
	public void removeGoods(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart == null){
			return;
		}
		
		String id = request.getParameter("id");
		
		if(StringUtil.isNullOrEmpty(id)){
			return;
		}
		
		if(cart.removeGoods(Integer.parseInt(id))){
			response.sendRedirect("../cart.jsp");
			return;
		}
		request.setAttribute("msg", "购物车删除失败！");
		request.getRequestDispatcher("../failed.jsp").forward(request, response);
		//session.setAttribute("cart", cart);
		
		
	}
	
	//修改购物车数量
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String id = request.getParameter("id");
			String quantity = request.getParameter("quantity");
			
			HttpSession session = request.getSession();
			if(StringUtil.isNullOrEmpty(id) && StringUtil.isNullOrEmpty(quantity)){
				response.sendRedirect("../error.jsp");
				return;
			}else{
				Cart cart = (Cart)session.getAttribute("cart");
				if(cart != null){
					try{
						cart.updateQuantity(Integer.parseInt(id), Integer.parseInt(quantity));
					}catch(Exception e){
						
					}
				}
				response.sendRedirect("../cart.jsp");
			}
	}
	
	//清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Cart cart =(Cart) session.getAttribute("cart");
		
		if(cart != null){
			cart.clearCart();
			session.setAttribute("cart", cart);
		}
		response.sendRedirect("../cart.jsp");
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
