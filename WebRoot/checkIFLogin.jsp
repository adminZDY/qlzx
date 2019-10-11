<%@page import="com.sec.model.CustomerInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%--该jsp使用与下订单前的判断,首页 --%>
<%
	//下订单那会
	CustomerInfo customer = (CustomerInfo)request.getAttribute("customer");
	if(customer == null)
	{
		//跳转到登录界面
		response.sendRedirect("/qlzx/login_register.jsp");
	}
	//用户存在登录，但未填写地址
	else if(customer.getCustomerDatail().getName() == null)
	{
		response.sendRedirect("/qlzx/peisong.jsp");
	}
%>