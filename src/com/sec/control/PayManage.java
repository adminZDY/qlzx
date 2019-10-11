package com.sec.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.config.AlipayConfig;
import com.sec.dao.OrderInfoDao;
import com.sec.model.Cart;
import com.sec.model.CustomerDatailInfo;
import com.sec.model.CustomerInfo;
import com.sec.model.OrderGoodsInfo;
import com.sec.model.OrderInfo;
import com.sec.util.DateTimeUtil;

public class PayManage extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PayManage() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
//		HttpSession session = request.getSession();
//		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
//		CustomerInfo customer = new CustomerInfo();
//		customer.setId(1);
//		customer.setEmail("a@sina.com");
//		customer.setPwd("123456");
//		customer.setRegisterTime(DateTimeUtil.convertDate("2009-06-06 12:30:45.000"));
//		
//		CustomerDatailInfo customerDatail = new CustomerDatailInfo();
//		customerDatail.setName("张三");
//		customerDatail.setTelphone("0001-32456754");
//		customerDatail.setMobileTelphone("13534563234");
//		customerDatail.setAddress("重庆市万州区国本路1号");
//		
//		customer.setCustomerDetail(customerDatail);
		
		//session.setAttribute("customer", customer);
		
		String op = request.getParameter("op");
		String returnURL = "";
		//确认订单
		if("add".equals(op)){
			addOrder(request, response);
		}
		else if("updatePay".equals(op)){
			updatePay(request, response);
		}
		else{
			if("confirm".equals(op)){
				returnURL = confirm(request,response);
			}
			else if("toPay".equals(op)){
				/**
				 * 跳转支付页面
				 */
				toPay(request, response);
			}
			else{
				request.setAttribute("msg", "进入结算中心出错！缺少必要的参数！");
				returnURL="../failed.jsp";
			}
			request.getRequestDispatcher(returnURL).forward(request, response);
		}
	}
	
	/**
	 * 进入确认订单页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		//购物车为空，跳回购物车
		if(cart == null || cart.getCart().isEmpty()){
			return "../cart.jsp";
		}
		//设置一个标识，表示客户当前的动作欲前往确定订单页面
		session.setAttribute("toGo", "confirmOrder");
		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
		  //用户未登录跳转登录页面
		  if(customer == null){
		  	return "../login_register.jsp";
		  //用户详细地址为空，跳转填写配送详细页面
		  }else if(customer.getCustomerDatail().getName() == null){
		  	//客户未填写配送消息
		  	 return "../peisong.jsp";
		  }else{
			  //用户跳转到支付页面
		  	return "../confirm.jsp";
		  }
	}

	/**
	 * 跳转支付页面
	 * @return
	 */
	public void toPay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
      //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        HttpSession session = request.getSession();
        
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = request.getParameter("WIDout_trade_no");
        //付款金额，必填
        String total_amount = request.getParameter("WIDtotal_amount");
        //订单名称，必填
        String subject = "商品名称";//request.getParameter("WIDsubject");
        //商品描述，可空
        String body = "商品描述";
        
        System.out.println(out_trade_no);
        System.out.println(total_amount);
        System.out.println(subject);
        System.out.println(body);
        
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
                + "\"total_amount\":\""+ total_amount +"\"," 
                + "\"subject\":\""+ subject +"\"," 
                + "\"body\":\""+ body +"\"," 
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.charset);
            response.getWriter().write(result);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            response.getWriter().write("捕获异常出错");
            response.getWriter().flush();
            response.getWriter().close();
        }
	}
	
	/**
	 * 跳转支付页面
	 * @return
	 */
	public void updatePay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
      //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        HttpSession session = request.getSession();
        
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = request.getParameter("WIDout_trade_no");
        //付款金额，必填
        String total_amount = request.getParameter("WIDtotal_amount");
        //订单名称，必填
        String subject = "商品名称";//request.getParameter("WIDsubject");
        //商品描述，可空
        String body = "商品描述";
        request.getSession().setAttribute("orderId",request.getParameter("orderId"));
        
        System.out.println(out_trade_no);
        System.out.println(total_amount);
        System.out.println(subject);
        System.out.println(body);
        
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
                + "\"total_amount\":\""+ total_amount +"\"," 
                + "\"subject\":\""+ subject +"\"," 
                + "\"body\":\""+ body +"\"," 
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.charset);
            response.getWriter().write(result);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            response.getWriter().write("捕获异常出错");
            response.getWriter().flush();
            response.getWriter().close();
        }
	}
	
	/**
	 * 下订单功能（操作数据库）
	 * 添加订单信息，跳转支付页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		//从session中移除标识
		session.removeAttribute("toGo");
		//取出会话中的登录客户信息
		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
		//取出购物车中的商品放入goodsList中
		Cart cart = (Cart)session.getAttribute("cart");
		ArrayList<OrderGoodsInfo> goodsList = new ArrayList<OrderGoodsInfo>();
		Iterator<OrderGoodsInfo> values = cart.getCart().values().iterator();
		while(values.hasNext()){
			goodsList.add(values.next());
		}
		//封装订单信息
		OrderInfo order = new OrderInfo();
		order.setStatus(0);//订单状态为未确认
		order.setCustomer(customer);//设置用户信息
		order.setOrderDatails(goodsList);//设置订单信息
		//调用数据访问类添加订单方法下订单
		OrderInfoDao dao = new OrderInfoDao();
		boolean flag = dao.addOrder(order);
		
//		//订单成功
		if(flag){
			//清空购物车
			cart.clearCart();
			//存储订单信息
			session.setAttribute("orderInfo", order);
			//跳转到支付页面
			toPay(request, response);
		}else{
			request.setAttribute("msg", "对不起，订单异常！");
			request.setAttribute("jsp","/qlzx/cart.jsp" );
			request.getRequestDispatcher("../fainled.jsp").forward(request, response);
		}
	}
}
