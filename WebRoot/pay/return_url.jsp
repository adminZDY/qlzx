<%@page import="com.sec.dao.OrderInfoDao"%>
<%@page import="com.sec.model.OrderInfo"%>
<%@page import="com.sec.model.Cart"%>
<%@page import="com.sec.model.CustomerInfo"%>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>电脑网站支付return_url</title>
</head>
<body>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Map"%>
<%@page import="com.alipay.config.AlipayConfig"%>
<%@ page import="com.alipay.api.*"%>
<%@ page import="com.alipay.api.internal.util.*"%>
<%
/* *
 * 功能：支付宝服务器同步通知页面
 * 日期：2017-03-30
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


 *************************页面功能说明*************************
 * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
 */
 
	//获取支付宝GET过来反馈信息
	Map<String,String> params = new HashMap<String,String>();
	Map<String,String[]> requestParams = request.getParameterMap();
	for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
		}
		//乱码解决，这段代码在出现乱码时使用
		valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		params.put(name, valueStr);
	}
	
	boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

	String returnURL = "";
	//——请在这里编写您的程序（以下代码仅作参考）——
	if(signVerified) {
		System.out.print("执行页面显示");
		//修改状态
		OrderInfo order = (OrderInfo)session.getAttribute("orderInfo");
		if(order != null)
		{
			OrderInfoDao orderDao = new OrderInfoDao();
			orderDao.update(1, order.getOrderId());
			returnURL = "/qlzx/PaySuccessPage.jsp?customerid="+order.getCustomer().getId()+"&orderid="+order.getOrderId();
		}
		else {
			int orderId = Integer.parseInt((String)session.getAttribute("orderId"));
			CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
			OrderInfoDao orderDao = new OrderInfoDao();
			orderDao.update(1, orderId);
			returnURL = "/qlzx/PaySuccessPage.jsp?customerid="+customer.getId()+"&orderid="+orderId;
		}
	}else {
		//out.println("验签失败");
		request.setAttribute("msg", "对不起，支付异常，请您及时联系客服");
		request.setAttribute("jsp","/index.jsp");
		//return "../fainled.jsp";
		returnURL = "/failed.jsp";
	}
	response.sendRedirect(returnURL);
	//——请在这里编写您的程序（以上代码仅作参考）——
%>
</body>
</html>