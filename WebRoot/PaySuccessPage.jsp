<%@page import="com.sec.dao.OrderInfoDao"%>
<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.model.OrderGoodsInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	int customerid= 0;
	try{
		customerid = Integer.parseInt(request.getParameter("customerid"));
	}catch(Exception e){
	}
	
	int orderid= 0;
	try{
		orderid = Integer.parseInt(request.getParameter("orderid"));
	}catch(Exception e){
	}
	
	OrderInfoDao dao = new OrderInfoDao();
	
	int i =0;
	ArrayList<OrderGoodsInfo> orderList = dao.add(customerid,orderid);
	if(orderList == null || "".equals(orderList) ){
		orderList = new  ArrayList<OrderGoodsInfo>(); 
	}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>我的信息</title>
<!-- 首页 -->
    <%@ include file="top_index.jsp"%>
    
  </head>
 <style type="text/css">
  	td{
  		padding-left: 10px;
  	}
  	a{
  		text-decoration: none;
  		
  	}
  	.bun{
  		font-weight:600;
  		background: url('/qlzx/images/btn.png') no-repeat;
  		background-size:100%; 
  		width:70px;height:25px;
  		border:0px;
  	}
  	.td{
  	 	background-color:#0CF;
  	}
  	.sp{
  		font-size: 25px; 
  		font-weight:900; 
  		font-family:Georgia;
  		color:#B00000;
  	}
  	.message{
  		border:1px solid #000; 
  		margin-bottom: 10px; 
  		background-color:#0CF; 
  		font-weight: 800;
  		height: 25px;
  	}
  	.from{
  		margin: 10px 10px 10px 10px; 
  		width:1225px;
  	}
  	
  </style>
  <%
   
    if(customer.getEmail() == null ){
		response.sendRedirect("/qlzx/login_register.jsp");
		return;
	}
	 %>
  <script type="text/javascript">
  	function fun(url){
  		window.location.href = url;
  	}
  	function peisong(url){
  	
  		<%
  		session.setAttribute("peisong", "/qlzx/servlet/OrderManage?op=myManage&customerid="+customerid);%>
  		window.location.href = url;
  	}
  </script>
  <body>
  
  <div style="margin-left:50px; ">
   <img src="/qlzx/images/site_ico.gif">您现在所在的位置，
   <a href="/qlzx/index.jsp">网站首页</a>&gt;我的信息
   <center style="font-weight: 600;"><span class="sp">千里之行</span>我的信息</center>
  	<div style="border:1px solid #000; width:1250px;">
  		<div class="message">
  			<div style="margin-top: 5px; font-size: 13px; width:100px; float:left;"><img src="/qlzx/images/item.gif">
  				客户信息
  			</div>
  			
  		</div>
    <table class="from" border="1" cellspacing="0" cellpadding="5">
    	<tr>
    		<td width="120" class="td">客户编号</td>
    		<td width="200"><%=customer.getId()%></td>
    		<td width="120" class="td">注册时间</td>
    		<td width="300" ><%=DateTimeUtil.ConvertDateYMD(customer.getRegisterTime()) %></td>
    	</tr>
    	<tr>
    		<td class="td">客户账号/邮箱</td>
    		<td><%=customer.getEmail()%></td>
    		<td class="td">收货人姓名</td>
    		<td><%=customer.getCustomerDatail().getName() == null ? "无 ":customer.getCustomerDatail().getName()%></td>
    	</tr>
    	<tr>
    		<td class="td">固定电话</td>
    		<td><%=customer.getCustomerDatail().getTelphone() == null ? "无 ":customer.getCustomerDatail().getTelphone()%></td>
    		<td class="td">移动电话</td>
    		<td><%=customer.getCustomerDatail().getMobileTelphone() == null ? "无 ":customer.getCustomerDatail().getMobileTelphone()%></td>
    	</tr>
    	<tr>
    		<td class="td">收货地址</td>
    		<td colspan="3"><%=customer.getCustomerDatail().getAddress()== null ? "无 ":customer.getCustomerDatail().getAddress() %></td>
    	</tr>
    </table>
   </div>
   
   
   <div style="border:1px solid #000; margin-top:10px; width:1250px;">
  		<div class="message">
  			<div style="margin-top: 5px; font-size: 13px;"><img src="/qlzx/images/item.gif">订单信息</div>
  		</div> 
  		   	
    	<%
    		int id = 0;
    		float sum = 0;
    		if(orderList.isEmpty()){
    			out.println("<center style='font-weight:700;'>无订单信息</center>");
    		}
    		
       		for(OrderGoodsInfo order : orderList){
       		
       		i++;
       		//判断订单编号是否相同
       		if(order.getOrderInfo().getOrderId() != id){
       			if(i != 1){
       				out.println("<tr><td colspan='6' style='text-align: right;'>订单总金额："+String.format("%.2f",sum)+"¥</td></tr>");
       				out.println("</table>");
       				sum=0;
       			}
       			
       			id = order.getOrderInfo().getOrderId();
       			%>
       			<center>订单编号：<%=order.getOrderInfo().getOrderId() %>&nbsp;&nbsp;&nbsp;&nbsp;订单时间:<%=DateTimeUtil.ConvertDate(order.getOrderInfo().getOrderTime()) %>
       			<span style="margin-left:600px;">
       				订单状态:<%--=order.getOrderInfo().getStatus()==0? "未确定" : "确定" --%>
       				<span style='font-weight: 700;'>
    					<%if(order.getOrderInfo().getStatus() == 0){
    						out.print("待支付");
    					 }else if(order.getOrderInfo().getStatus() == 1){
       					 	out.print("待发货");
       					 }else if(order.getOrderInfo().getStatus() == 2){
       					 	out.print("确认收货");
       					 }else if(order.getOrderInfo().getStatus() == 3){
       					 	out.print("待评论");
       					 }else if(order.getOrderInfo().getStatus() == 4){
       					 	out.print("交易完成");
       					 }%>
       					</span>
       			</span></center>
       			<table class="from" border="1" cellspacing="0" cellpadding="5">
    				<tr style="background-color: #0CF;">

    				<th>商品名称</th>
   					<th>商品类型</th>
    				<th>价格</th>
    				<th>折扣</th>
    				<th>数量</th>
    				<th>小计</th>
    			</tr>
       		<% }sum+=order.getSumPrice();
    	%>
    	<tr>
    		
    		<td width="400"><a href="/qlzx/servlet/GoodsManage?op=query&goodsid=<%=order.getGoodsInfo().getGoodsId() %>">
    		<img src="/qlzx/product/<%=order.getGoodsInfo().getPhoto()%>" align="middle" width="50" height="50"/><%=order.getGoodsInfo().getGoodsName()%></a></td>
    		<td><a href="/qlzx/servlet/GoodsManage?typeid=<%=order.getGoodsInfo().getGoodstype().getTypeId() %>"><%=order.getGoodsInfo().getGoodstype().getTypeName()%></a></td>
    		<td><%=order.getGoodsInfo().getPrice()%></td>
    		<td><%if(order.getGoodsInfo().getDiscount() == 10.0f){%>
    				-
    			<%}else{%>
    				<%=order.getGoodsInfo().getDiscount()%>
    			<%}%></td>
    		<td><%=order.getQuantity()%></td>
    		<td><%=String.format("%.2f", order.getSumPrice())%>元</td>
    	</tr>
    	<%}%>
    	<tr><td colspan='6' style="text-align: right;">订单总金额：<%=String.format("%.2f",sum) %>¥</td></tr>
    	</table>
   </div>
   		<div style="margin-top:10px; width:1250px; text-align: right;">
   		 <input type="button" onclick="fun('/qlzx/index.jsp')" class="bun" value="返回">
   		</div>
   </div>
  </body>
</html>
