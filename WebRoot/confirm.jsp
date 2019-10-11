<%@page import="com.sec.model.GoodsInfo"%>
<%@page import="com.sec.model.OrderGoodsInfo"%>
<%@page import="com.sec.model.Cart"%>
<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.model.CustomerInfo"%>
<%@page import="com.sec.model.OrderInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	int i = 0;
	//购物车
	Cart cart = (Cart)session.getAttribute("cart");
	
	//CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>订单确定</title>
	
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
  		background: url('../images/btn.png') no-repeat;
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
  		margin: 0 10 10 10; 
  		width:98%;
  	}
  	
  </style>
  
  <script type="text/javascript" language="javascript">
  	function sendPage(url){
  		window.location.href = url;
  	}
  	function peisong(url){
  	alert(url);
  		<%session.setAttribute("peisong", "/qlzx/servlet/PayManage?op=confirm");%>
  		window.location.href = url;
  	}
  	
  
  </script>
  <body>
  <!-- 首页 -->
<%@ include file="top_index.jsp"%>
  <div style="margin-left:50px; ">
   <img src="../images/site_ico.gif">您现在所在的位置，
   <a href="../index.jsp">网站首页</a>&gt;
   <a href="../cart.jsp">购物车</a>&gt;订单确定
   <center style="font-weight: 600;"><span class="sp">千里之行</span>购物订单确定表</center>
   	<form name="payFrom" action = "PayManage" method="post">
  	<div style="border:1px solid #000;">
  		<div class="message">
  			<div style="margin-top: 5px; font-size: 13px; width:100px; float:left;"><img src="../images/item.gif">
  				客户信息
  			</div>
  			<div style="float: right; margin-top: 0px;">
  				<input type="button" onclick="peisong('../updateCustomer.jsp')"  style="height: 25px;" value="修改配送信息"/>
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
    		<td><%=customer.getCustomerDatail().getName()%></td>
    	</tr>
    	<tr>
    		<td class="td">固定电话</td>
    		<td><%=customer.getCustomerDatail().getTelphone()%></td>
    		<td class="td">移动电话</td>
    		<td><%=customer.getCustomerDatail().getMobileTelphone() %></td>
    	</tr>
    	<tr>
    		<td class="td">收货地址</td>
    		<td colspan="3"><%=customer.getCustomerDatail().getAddress() %></td>
    	</tr>
    </table>
   </div>
   
   
   <div style="border:1px solid #000; margin-top:10px;">
  		<div class="message">
  			<div style="margin-top: 5px; font-size: 13px;"><img src="../images/item.gif">订单信息</div>
  		</div>
    <table class="from" border="1" cellspacing="0" cellpadding="5">
    	<tr style="background-color: #0CF;">
    		<th>商品编号</th>
    		<th>商品名称</th>
   			<th>商品类型</th>
    		<th>价格</th>
    		<th>折扣</th>
    		<th>数量</th>
    		<th>小计</th>
    	</tr>
    	<%if(cart == null ){ %>
    		<tr><th colspan="7">购物车中没有任何商品</th></tr>
    	<%}else{%>
    	<%
       	HashMap<Integer,OrderGoodsInfo> items = cart.getCart();
		Iterator<Integer> it = items.keySet().iterator();
		OrderGoodsInfo ordergoods = null;
		
		if(!it.hasNext()){
			out.println("<tr><th colspan=7>购物车中没有任何商品</th></tr>");
		}
    	while(it.hasNext()){
  			ordergoods = items.get(it.next());
  			GoodsInfo goods = ordergoods.getGoodsInfo();
  			i++;
    	%>
    	<tr>
    		<td><%=goods.getGoodsId() %></td>
    		<td><img src="../product/<%=goods.getPhoto()%>" align="middle" width="50" height="50"/><a href="#"><%=goods.getGoodsName()%></a></td>
    		<td><a href="#"><%=goods.getGoodstype().getTypeName()%></a></td>
    		<td><%=goods.getPrice()%></td>
    		<td><%if(goods.getDiscount() == 10.0f){%>
    				-
    			<%}else{%>
    				<%=goods.getDiscount()%>
    			<%}%></td>
    		<td><%=ordergoods.getQuantity()%></td>
    		<td><%=String.format("%.2f", ordergoods.getSumPrice())%>元</td>
    	</tr>
    	<%}%>
    	<%} %>
    	<tr>
    		<td colspan="7">此订单中共有<span class="sp"><%=i%></span>件商品，总计金额
    		<span class="sp"><%=cart.getTotalPrice()%></span>元!</td>
    	</tr>
    </table>
   </div>
   
   		<center style="margin-top:10px;">
   		<!--  <input type="button" onclick="sendPage('PayServlet')" class="bun" value="确认订单">&nbsp;&nbsp; -->
   		<input type="submit"  class="bun" value="确认订单">&nbsp;&nbsp;
   		 <input type="button" onclick="sendPage('../cart.jsp')" class="bun" value="返回">
   		 <input type="hidden" name = "op" value="add">
   		 <%-- 支付订单编号 --%>
   		 <input type="hidden" id="WIDout_trade_no" name="WIDout_trade_no" />
   		 <%-- 订单名称 --%>
		 <input type="hidden" id="WIDsubject" name="WIDsubject" value="商品支付" />
		 <%-- 订单金额 --%>
		 <input type="hidden" id="WIDtotal_amount" name="WIDtotal_amount" value="<%=cart.getTotalPrice()%>" />
   		</center>
   		</form>
   </div>
   
   	<!-- 尾页 -->
    <%@include file="bottom_index.jsp" %>
  </body>
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
		GetDateNow();
  </script>
</html>
