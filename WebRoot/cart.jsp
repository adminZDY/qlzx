<%@page import="com.sec.model.OrderGoodsInfo"%>
<%@page import="com.sec.model.Cart"%>
<%@page import="com.sec.model.GoodsInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	CustomerInfo customer1 = (CustomerInfo)session.getAttribute("customer");
	if(customer1 == null ){
	  	response.sendRedirect("/qlzx/login_register.jsp");	
	}
	
	double sum = 0.0;
	Cart cart = (Cart)session.getAttribute("cart");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>购物车页面</title>
    <style type="text/css">
    	.a{
    		color:white;
    	}
    	.sp{
    		color:#F30;
    		font-family:Georgia;
    		font-size:24px;
    		font-weight: 700;
    	}
    	#money_div{
    		height: 50px; 
    		font-size: 12; 
    		font-weight: 700;
    	}
    	#empty_sp{
    		margin-left:940px; 
    		font-weight: 700;
    	}
    </style>
    <script type="text/javascript">
    	function update(obj,quantity){
    		
    		var quantity1 = document.getElementById(quantity);
    		
			if(quantity1.value.length == 0 || quantity1.value <= 0){
				
				alert("购买件数不能为空并且数字不能小于0！");
				quantity1.value = 1;
				quantity1.focus();
				return;
			}
			//alert(!isNaN(quantity1));//字符false
			//var re = new RegExp("\\d{"+quantity1.length+"}");
			
			if(quantity1.value.indexOf(".")!=-1)
			{
				
				alert("只能输入整数，不能输入小数哦");
				quantity1.value = 1;
				quantity1.focus();
				return;
			}
			if(!isNaN(quantity1.value) && quantity1.value > 0){
				
				obj.href="/qlzx/servlet/PayManage?op=confirm";
				window.location.href="/qlzx/servlet/CartManage?op=update&quantity="+quantity1.value+"&id="+obj;
				return;
			}
			
			alert("去填写正确的数字");
			quantity1.value=1;
			quantity1.focus();
			return ;
    	}
    
    </script>
  </head>
  
  <body>
<!-- 首页 -->
    <%@include file="top_index.jsp" %>
    <div  style="margin-left:50px;">
    <img src="images/site_ico.gif">您现在所在的位置，<a href="index.jsp">网站首页</a>&gt;购物车
 	<div>
    <img src="images/buycart_logo.gif">
    </div>
    <table border="1" cellspacing="0" style=" width:1200px; ">
    	<tr style="background-color:#0CF;">
    		<th>商品名称</th>
    		<th>商品类型</th>
    		<th>价格</th>
    		<th>折扣</th>
    		<th>数量</th>
    		<th>小计</th>
    		<th>操作</th>
    	</tr>
    	<%if(cart == null ){ 
    	    cart = new Cart();%>
    		<tr><th colspan="7">购物车中没有任何商品</th></tr>
    	<%}else{%>
    	<%
       	HashMap<Integer,OrderGoodsInfo> items = cart.getCart();
		Iterator<Integer> it = items.keySet().iterator();
		OrderGoodsInfo ordergoods = null;
		int i = 0;
		if(!it.hasNext()){
			out.println("<tr><th colspan=7>购物车中没有任何商品</th></tr>");
		}
    	while(it.hasNext()){
    		i++;
  			ordergoods = items.get(it.next());
  			GoodsInfo goods = ordergoods.getGoodsInfo();
    	%>
    	<tr>
    		<td><img src="product/<%=goods.getPhoto()%>" align="middle" width="50" height="50"/><a href="#"><%=goods.getGoodsName()%></a></td>
    		<td><a href="#"><%=goods.getGoodstype().getTypeName()%></a></td>
    		<td><%=goods.getPrice()%>元</td>
    		<td><%if(goods.getDiscount() == 10.0f){%>
    				-
    			<%}else{%>
    				<%=goods.getDiscount()%>
    			<%}%></td>
    		<td><input type="text" id="quantity_<%=i%>" name = "quantity_num" value="<%=ordergoods.getQuantity()%>" size="3"></td>
    		<td><%=String.format("%.2f", ordergoods.getSumPrice())%>元</td>
    		<th><a href="javascript:void(0)" onclick="update(<%=goods.getGoodsId()%>,'quantity_<%=i%>')">更改数量</a>&nbsp;
    		<a href="servlet/CartManage?op=removeGoods&id=<%=goods.getGoodsId()%>">删除商品</a></th>
    	</tr>
    	<%}%>
    	<% } %>
    </table>
    <div id="money_div">
    	商品金额总计：
    	<span class="sp">¥<%=cart.getTotalPrice()%></span>
    	<span id="empty_sp"><a href="servlet/CartManage?op=clearCart" style="font-size: 15;">清空购物车</a></span>
    </div>
    
    <div style="font-weight: 600;">
   		<center>你可以<a class="a" href="servlet/PayManage?op=confirm">
   		<img src="images/jrjs.gif" align="middle">
   		</a>,也可以
   		<a class="a" href="index.jsp">
   		<img src="images/jxgm.gif" align="middle">
   		</a></center>
    </div>
 	</div>
 	<!-- 尾页 -->
    <%@include file="bottom_index.jsp" %>
  </body>
</html>
