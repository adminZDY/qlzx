package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.Card;

import com.sec.dao.BulletinDao;
import com.sec.dao.CustomerInfoDao;
import com.sec.dao.OrderInfoDao;
import com.sec.model.BulletinInfo;
import com.sec.model.CustomerInfo;
import com.sec.model.OrderGoodsInfo;
import com.sec.model.OrderInfo;
import com.sec.util.PageModel;

public class OrderManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public OrderManage() {
		super();
	}

	/**
	 * 处理业务的services
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String op = request.getParameter("op");
		String returnURL = "";
		System.out.println(op);
		if(op == null || "".equals(op) || "queryAll".equals(op)){
			//分页查询
			returnURL = queryALL(request,response);
		}else{
			//根据公告编号id获取查询公告信息，便跳转到修改界面
			if("query".equals(op)){
				returnURL =query(request,response);
			//公告信息查询
			}else if("update".equals(op)){
				returnURL = update(request,response);
			//删除单条公告信息
			}else if("remove".equals(op)){
				returnURL = remove(request,response);
			//删除多条公告信息
			}else if("removeMore".equals(op)){
				returnURL = removeMore(request,response);			
			}else if("myManage".equals(op)){
				returnURL = queryAll(request,response);
			}else if("statusChange".equals(op)){
				//前台修改状态
				returnURL = statusChange(request,response);
			}
			else{
				request.setAttribute("msg", "访问资源出错！访问该资源位携带必要的参数！");
				returnURL = "../abmin/error.jsp";
			}
		}
		
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

	/**
	 * 分页查询所有
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryALL(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
			int pageSize = 7;
			int pageNo = 1;
			String sign = null;
				
			sign = request.getParameter("sign");
			String text =request.getParameter("text");
				
			//判断提交方法，如果是get提交时转码，否则将为乱码
			if(text != null && "GET".equals(request.getMethod())){
				//这是get提交的转码方法
				text = new String(request.getParameter("text").getBytes("iso-8859-1"),"utf-8");
			}
			if("null".equals(text)||"".equals(text)){
				text = "";
			}
			
			try{
				pageSize =Integer.parseInt(request.getParameter("pageSize"));
				pageNo =Integer.parseInt(request.getParameter("pageNo"));
					
			}catch(NumberFormatException e){}
			
			OrderInfoDao dao = new OrderInfoDao();
			PageModel<OrderGoodsInfo> pm = dao.getAllBulletionInfo(pageSize, pageNo , text );
			
			if(pm == null || "".equals(pm)){
				text = "";
				pm=dao.getAllBulletionInfo(pageSize, 1 , text);
			}
			if(pm != null && pm.getData().isEmpty() && pageNo != 1){
				pm=dao.getAllBulletionInfo(pageSize, 1 , text);
			}
			
			request.setAttribute("allBullein", pm);
		
			if(sign != null){
				return "../showBulletinList.jsp";
			}
			return "../admin/orderManage.jsp?text="+text;
	}	
	
	/**
	 * 订单详细信息（单个）
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String customerid = request.getParameter("customerid");
		String orderid = request.getParameter("orderid");
		// 判断是否获取id是否不为空
		if (customerid != null && orderid != null) {

			CustomerInfoDao customrDao = new CustomerInfoDao();
			//订单客户信息
			OrderInfo orderinfo = customrDao.Order_Customer(
					Integer.parseInt(customerid), Integer.parseInt(orderid));

			OrderInfoDao dao = new OrderInfoDao();
			ArrayList<OrderGoodsInfo> orderList = new ArrayList<OrderGoodsInfo>();
			// 查询单个订单商品的详细信息
			orderList = dao.add(Integer.parseInt(customerid),
					Integer.parseInt(orderid));

			// 将查询到的公告信息存储到request对象
			request.setAttribute("orderinfo", orderinfo);
			request.setAttribute("orderList", orderList);

			return "../admin/showOrderDetail.jsp";
		}

		return queryALL(request, response);
	}
	
	/**
	 * 数据库修改订单状态
	 */
	public String update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("id"));//编号
		String status = request.getParameter("Status");//标题

		OrderInfoDao dao = new OrderInfoDao();
		if(dao.update(Integer.parseInt(status), id)){
			
			return queryALL(request,response);
		}
		request.setAttribute("failed","修改订单信息失败!");
		return "../admin/failed.jsp";		
		
	}

	/**
	 * 前台！！！
	 * 修改订单状态[0表示未付款][1用户付款成功][2商家已发货][3用户确认收货][4用户评价——》交易成功]
	 */
	public String statusChange(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		int id =Integer.parseInt(request.getParameter("orderId"));//编号
		String status = request.getParameter("status");//状态

		OrderInfoDao dao = new OrderInfoDao();
		if(dao.update(Integer.parseInt(status), id)){
			
		
			return queryAll(request,response);
		}
		request.setAttribute("failed","修改订单信息失败!");
		return "../admin/failed.jsp";		
		
	}
	
	
	/**
	 * 删除订单信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));// 编号

		OrderInfoDao orderInfoDao = new OrderInfoDao();
		System.out.println("删除");
		// 查询在商品信息中是否有我选择删除例其中一个编号
		// 如果有查询到则说明，该类型有商品信息，不允许删除，跳转到错误页面
		if (orderInfoDao.delteFilter(id)) {
			// 查询到——>错误页面
			request.setAttribute("msg", "删除订单错误!，你所选要删除订单未确认");
			request.setAttribute("jsp",
					"/qlzx/servlet/OrderManage?op=queryAll");
			return "/admin/error.jsp";
		} else {
			// 可以执行删除操作
			// 删除成功
			if (orderInfoDao.delete(id)) {
				// 删除成功，操作成功
				request.setAttribute("ok", "删除订单信息成功!");
				request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
				return "/admin/ok.jsp";
			}
			// 删除失败
			else {
				// 删除失败，操作失败
				request.setAttribute("failed", "删除订单信息失败!");
				request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
				return "/admin/failed.jsp";
			}
		}
	}

	/**
	 *  删除多条订单信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String removeMore(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] chk = request.getParameterValues("chkItems");
		int[] orderId = new int[chk.length];
		OrderInfoDao orderInfoDao = new OrderInfoDao();

		if (chk != null) {
			for (int i = 0; i < chk.length; i++) {
				orderId[i] = Integer.parseInt(chk[i]);
			}
		}
//		request.setAttribute("failed", "删除商品类型失败!");
//		return "../admin/failed.jsp";
		// 查询在商品信息中是否有我选择删除例其中一个编号
		// 如果有查询到则说明，该类型有商品信息，不允许删除，跳转到错误页面
		if (orderInfoDao.delteFilter()) {
			// 查询到——>错误页面
			request.setAttribute("msg", "删除订单错误!，你所选要删除订单未确认");
			request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
			return "/admin/error.jsp";
		} else {
			// 可以执行删除操作
			// 删除成功
			if (orderInfoDao.delteFilter(orderId)) {
				// 删除成功，操作成功
				request.setAttribute("ok", "删除订单信息成功!");
				request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
				return "/admin/ok.jsp";
			}
			// 删除失败
			else {
				// 删除失败，操作失败
				request.setAttribute("failed", "删除订单信息失败!");
				request.setAttribute("jsp","/qlzx/servlet/OrderManage?op=queryAll");
				return "/admin/failed.jsp";
			}
		}
	}
	
	/**
	 * 我的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String queryAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String customerid =request.getParameter("customerid");
	
		//判断是否获取id是否不为空
		if(customerid != null){
			
			OrderInfoDao dao = new OrderInfoDao();
			ArrayList<OrderGoodsInfo> orderList = new ArrayList<OrderGoodsInfo>();
			orderList = dao.addGoods(Integer.parseInt(customerid));
			
			//将查询到的信息存储到request对象
			request.setAttribute("orderList", orderList);
			
			return "../myMessage.jsp";
		}
		
		return "../login_register.jsp";
	}
}
