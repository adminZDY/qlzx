<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.model.OrderInfo"%>
<%@page import="com.sec.model.OrderGoodsInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	OrderInfo orderinfo = (OrderInfo) request.getAttribute("orderinfo");
	ArrayList<OrderGoodsInfo> orderList = (ArrayList<OrderGoodsInfo>) request.getAttribute("orderList");
	double sum = 0.0f;
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>显示客户订单详细信息</title>
    <link rel="stylesheet" href="/qlzx/css/bootstrap.min.css">  
	<script src="/qlzx/js/jquery.min.js"></script>
	<script src="/qlzx/js/bootstrap.min.js"></script>
    <style type="text/css">
    
		#table_1 td
		{
			border-bottom: 1px dashed gray;
			padding: 10px;
		}
		#table_2{
			width: 100%;
		}
		#table_2 td
		{
			border: 1px solid gray;
			padding: 5px;
		}
		#table_2 th
		{
			text-align: center;
			padding: 5px;
		}
		.tit{
			font-weight: 700;
		}
	</style>
	
	<script type="text/javascript">
		function fun(){
			window.location.href="OrderManage";
		}
		
	</script>
  </head>
  
  <body>
     <div style="background:url(../images/titlebar_div_bg.jpg) repeat-x bottom; margin-top:5px; height: 25;">
     	<img src="../images/doc_red.gif">查看/修改公告信息
     </div>
     <div style="width:100%;">
    <table cellspacing="0" width="100%" id="table_1">
    	<tr>
    		<td class="tit">订单编号:</td>
    		<td><%=orderinfo.getOrderId()%></td>
    		<td class="tit">订单状态:</td>
    		<td><%if(orderinfo.getStatus() == 0){%>
    			未确认
    		<%}else{%>
    			确认
    		<%} %></td>
    		<td class="tit">下单时间:</td>
    		<td><%=DateTimeUtil.ConvertDate(orderinfo.getOrderTime()) %></td>
    	</tr>
    	<tr>
    		<td class="tit">客户编号:</td>
    		<td><%=orderinfo.getCustomer().getId()%></td>
    		<td class="tit">客户账号/邮箱:</td>
    		<td><%=orderinfo.getCustomer().getEmail()%></td>
    		<td class="tit">注册时间:</td>
    		<td><%=DateTimeUtil.ConvertDate(orderinfo.getCustomer().getRegisterTime())%></td>
    	</tr>
    	<tr>
    		<td class="tit">收货人:</td>
    		<td><%=orderinfo.getCustomer().getCustomerDatail().getName()%></td>
    		<td class="tit">固定电话:</td>
    		<td><%=orderinfo.getCustomer().getCustomerDatail().getTelphone() == null ? "—" :  orderinfo.getCustomer().getCustomerDatail().getTelphone()%></td>
    		<td class="tit">移动电话:</td>
    		<td><%=orderinfo.getCustomer().getCustomerDatail().getMobileTelphone() == null ? "—" :  orderinfo.getCustomer().getCustomerDatail().getMobileTelphone()%></td>
    	</tr>
    	<tr>
    		<td class="tit">收货地址:</td>
    		<td colspan="5"><%=orderinfo.getCustomer().getCustomerDatail().getAddress()%></td>
    	</tr>
    	<tr>
    		<td colspan="6" height="200" style="padding: 0px;">
    		<table border="1" cellspacing="0" id = "table_2">
    			<tr style="background-image: url('/qlzx/images/menubar.png');">
    				<th >商品编号</th>
    				<th>商品类别</th>
    				<th>商品名称</th>
    				<th>商品价格</th>
    				<th>商品折扣</th>
    				<th>订购数量</th>
    				<th>小计</th>
    			</tr>
    			<%for(OrderGoodsInfo order : orderList){ 
    				sum += order.getSumPrice();
    			%>
    			<tr>
    				<td><%=order.getGoodsInfo().getGoodsId()%></td>
    				<td><a href="#"><%=order.getGoodsInfo().getGoodstype().getTypeName() %></a></td>
    				<td><%=order.getGoodsInfo().getGoodsName() %></td>
    				<td><%=order.getGoodsInfo().getPrice()%></td>
    				<td><%=order.getGoodsInfo().getDiscount() == 10.0f ? "-" :order.getGoodsInfo().getDiscount()%></td>
    				<td><%=order.getQuantity() %></td>
    				<td><%=order.getGoodsInfo().getDiscountPrice() %></td>
    			</tr>
    			<%} %>
    			<tr>
    				<td>总金额:</td>
    				<td colspan="6">¥<%=String.format("%.2f", sum) %></td>
    			</tr>
    		</table>
    		</td>
    	</tr>
    </table>
    <button type="button" onclick="fun()" class="btn btn-success" style="width:63px; height: 32px;margin-left: 94%;margin-top : 10px; font-weight: 900; " >返回</button>
    </div>
  </body>
</html>
	