<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.model.OrderGoodsInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	int i =0;
	ArrayList<OrderGoodsInfo> orderList = (ArrayList<OrderGoodsInfo>) request.getAttribute("orderList");
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
  		int customerid = customer.getId();
  		session.setAttribute("peisong", "/qlzx/servlet/OrderManage?op=myManage&customerid="+customerid);%>
  		window.location.href = url;
  	}
  	
  	/**跳转支付页面*/
  	function payPath(orderId){
  		GetDateNow();
  		document.getElementsByName("orderId")[0].value = orderId;
  		
  		document.getElementById("WIDtotal_amount").value = document.getElementById(orderId+"").innerHTML;
  		
  		document.orderForm.submit();
  	}
  	/**修改订单状态*/
  	function statusChange(orderId,status){
  		document.getElementsByName("orderId")[0].value = orderId;
  		document.getElementsByName("status")[0].value = status;
  		document.orderForm.action = "/qlzx/servlet/OrderManage?op=statusChange&customerid="+<%=customerid%>;
  		document.orderForm.submit();
  	}
  	
  	function Review(orderid){
  		
  		document.orderForm.action = "/qlzx/servlet/ReviewManage?orderid="+orderid+"&customerid="+<%=customer.getId()%>;
  		document.orderForm.submit();
  	}
  </script>
     <script language="javascript">
  		function GetDateNow() {
  		//时间生成订单号
		var vNow = new Date();
		var sNow = "";
		sNow += String(vNow.getFullYear());
		sNow += String(vNow.getMonth() + 1);
		sNow += String(vNow.getDate());
		sNow += String(vNow.getHours());
		sNow += String(vNow.getMinutes());
		sNow += String(vNow.getSeconds());
		sNow += String(vNow.getMilliseconds());
		//订单号
		document.getElementById("WIDout_trade_no").value =  sNow;
		//收款方
		//document.getElementById("WIDsubject").value = "测试";
		//金额
		//document.getElementById("WIDtotal_amount").value = "0.01";
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
  			<div style="float: right; margin-top: 0px;">
  				<button onclick="peisong('/qlzx/updateCustomer.jsp')"  style="height: 25px;" >添加/修改配送信息</button>
  				<button onclick="peisong('/qlzx/updateUserPwd.jsp')" style="height: 25px;">修改密码</button>
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
       				out.println("<tr><td colspan='6' style='text-align: right;'>订单总金额："
       				+"<span id='"+id+"'>"+String.format("%.2f",sum)+"</span>¥</td></tr>");
       				out.println("</table>");
       				sum=0;
       			}
       			
       			id = order.getOrderInfo().getOrderId();
       			%>
       			<center>订单编号：<%=order.getOrderInfo().getOrderId() %>&nbsp;&nbsp;&nbsp;&nbsp;订单时间:<%=DateTimeUtil.ConvertDate(order.getOrderInfo().getOrderTime()) %>
       			<span style=" margin-left:600px;">订单状态:
       			<%--
       				if(order.getOrderInfo().getStatus()==0){
       				out.print("<a href='javascript:payPath("+order.getOrderInfo().getOrderId()+")')>未付款</a>");
	       			}else{
	       				out.print("已付款"); 
	       			}--%>
    					<%if(order.getOrderInfo().getStatus() == 0){
    						out.print("<a href='javascript:payPath("+order.getOrderInfo().getOrderId()+")')>未付款</a>");
    					 }else if(order.getOrderInfo().getStatus() == 1){
       					 	out.print("待发货");
       					 }else if(order.getOrderInfo().getStatus() == 2){
       					 	//statusChage
       					 	out.print("<a href='javascript:statusChange("+order.getOrderInfo().getOrderId()+",3)'>确认收货</a>");
       					 }else if(order.getOrderInfo().getStatus() == 3){
       					 	out.print("<a href='javascript:Review("+order.getOrderInfo().getOrderId()+")'>待评论</a>");
       					 }else if(order.getOrderInfo().getStatus() == 4){
       					 	out.print("交易完成");
       					 }%>
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
       		<%}sum+=order.getSumPrice();
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
    	<tr>
    		<td colspan='6' style="text-align: right;">
    			订单总金额：<span id=<%=id%>><%=String.format("%.2f",sum)%></span>¥
    			<form name = "orderForm" method="post" action = "/qlzx/servlet/PayManage?op=updatePay">
    			 <input type = "hidden" name = "orderId" value = "0">
    			 <input type = "hidden" name = "status" value = "0">
    			 <input type="hidden" id="WIDout_trade_no" name="WIDout_trade_no" />
		   		 <%-- 订单名称 --%>
				 <input type="hidden" id="WIDsubject" name="WIDsubject" value="商品支付" />
				 <%-- 订单金额 --%>
				 <input type="hidden" id="WIDtotal_amount" name="WIDtotal_amount" value="<%=String.format("%.2f",sum)%>"/>
				</form>
    		</td>
    	</tr>
    	</table>
   </div>
   		<div style="margin-top:10px; width:1250px; text-align: right;">
   		 <input type="button" onclick="fun('/qlzx/index.jsp')" class="bun" value="返回">
   		 
   		</div>
   </div>
  </body>
</html>