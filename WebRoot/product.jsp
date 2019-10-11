
<%@page import="com.sec.model.Review"%>
<%@page import="com.sec.dao.OrderInfoDao"%>
<%@page import="com.sec.model.GoodsInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	GoodsInfo goodsinfo =(GoodsInfo) request.getAttribute("goodsinfo");	
	
	OrderInfoDao dao = new OrderInfoDao();
	
	ArrayList<Review> reviewList =  dao.review(goodsinfo.getGoodsId());
	
 %>
<!doctype html>
<html lang="zh">
<head>
<%@ include file="top_index.jsp" %>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>商品详细信息</title>
	 <link rel="stylesheet" href="/qlzx/photo/css/bootstrap.min.css">  
	<script src="/qlzx/photo/js/jquery.min.js"></script>
	<script src="/qlzx/photo/js/bootstrap.min.js"></script> 
	<script src="/qlzx/js/ajax.js"></script>
  	
	<style type="text/css">
	.btn {
	display: inline-block;
	padding: 6px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
	}
  .old_price
	{
		text-decoration:line-through;
		font-weight:700;
		color:gray;
	}
  .discount
	{
		color:#F30;
		font-size:28px;
	}
	.zk
	{
		color:gray;
		font-family:Georgia;
		font-size:40px;
		font-weight: 700;
		
	}
	#right_bottom_div{
		margin-top:20px;
		font-weight:700; 
		background-color:#C0C0C0; 
		height: 90px; 
		width:370px; 
		float: left;
	}
	#buy_div{
		font-size: 15px;  
		margin-top:10px; 
		float: left;
	}
	#sub_but{
		margin-left:100px;background: url('../images/addtocart.gif') center no-repeat; 
		width: 130px
	}
	#cart_div{
		margin-top: 20px; 
		float:right; 
		margin-right: 50px;
	}
  </style>
	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link rel="stylesheet" href="/qlzx/photo/css/smoothproducts.css">
	<style type="text/css">
		.page {
		padding: 5px 30px 30px 30px;
		max-width: 800px;
		margin: 0 auto;
		font-family: "Segoe UI", Frutiger, "Frutiger Linotype", "Dejavu Sans", "Helvetica Neue", Arial, sans-serif;
		background: #fff;
		color: #555;
	}
	.cart_div a:link,
	.cart_div a:visited {
		color: #F0353A;
	}
	.cart_div a:hover {
		color: #8C0B0E;
	}
	ul {
		overflow: hidden;
	}
	pre {
		background: #333;
		padding: 10px;
		overflow: auto;
		color: #BBB7A9;
	}
	.demo {
		text-align: center;
		padding: 30px 0
	}
	.clear {
		clear: both;
	}
	
	img
	{
		border:0px;
		width:658px;
		height:658px;
	}
	#showDetail img{
		width:790px;
		height: auto;
		
	}
	#Review img{
		width:790px;
		height: auto;
		
	}
	#Review{
		display: none;
	}
	</style>
	<script type="text/javascript">
		window.onload = function(){
			var add = "<%=session.getAttribute("addok")%>";
		
			if(add != "null"){
				alert("加入购物车成功！");
				<% session.removeAttribute("addok");%>
			}
		};
		
		 function fun(){
		
			var txt = document.getElementsByName("product_num")[0];
			var str = txt.value.replace(/(^\s*)|(\s*$)/g, "");
			
			if(txt.value.length == 0 || txt.value <= 0){
				alert("购买件数不能为空并且数字不能小于0！");
				txt.value=1;
				txt.focus();
				return false;
			}
			var re = new RegExp("\\d{"+txt.value.length+"}");
			
			if(re.test(str)){
				return true;
			}
			
			alert("去填写正确的数字");
			txt.value=1;
			txt.focus();
			return false;
		} 
		
		function sedRequest(obj){
		
			 if(!fun()){
				return;
			} 
			var url = "/qlzx/servlet/AjaxCartManage";
			var str = "id="+obj+"&txt="+document.getElementsByName("product_num")[0].value;
			
			sendRequest("post",url,str,callback,null);
		}
		
		function callback(xmlHttp){
		
			var text = xmlHttp.responseText;
			//alert(text);
			
			if(text == "addok"){
				alert("加入购物车成功！");
			}else{
				window.location.href=text;
			}
		}
		
		function detailed(obj,str){
			document.getElementById(obj).style.display="block";
			document.getElementById(str).style.display="none";
		}
		function Review(obj,str){
			document.getElementById(str).style.display="block";
			
			document.getElementById(obj).style.display="none";
			
		}
	</script>
</head>
<body >
	<div style="margin-left:50px;">
    	<img src="../images/site_ico.gif" style="width:20px; height: 20px;">您现在所在的位置，<a href="../index.jsp">网站首页</a>&gt;商品信息
    </div>
	<article class="htmleaf-container">
		<div class="page" >
			
			<div class="sp-loading"  ><img  src="/qlzx/product/sp-loading.gif" alt=""></div>
			<div class="sp-wrap" style="cursor:zoom-in;">
				<a href="/qlzx/product/<%=goodsinfo.getPhoto() %>"><img src="/qlzx/product/<%=goodsinfo.getPhoto() %>"  alt=""></a>
				<a href="/qlzx/product/2.jpg"><img src="/qlzx/product/2_tb.jpg" alt=""></a>
				<a href="/qlzx/product/3.jpg"><img src="/qlzx/product/3_tb.jpg" alt=""></a>
				<a href="/qlzx/product/4.jpg"><img src="/qlzx/product/4_tb.jpg" alt=""></a>
				<a href="/qlzx/product/5.jpg"><img src="/qlzx/product/5_tb.jpg" alt=""></a>
				<a href="/qlzx/product/6.jpg"><img src="/qlzx/product/6_tb.jpg" alt=""></a>
				
			</div>
<div style=" position: absolute; top: 171px; left: 270px;">
	<%if(goodsinfo.getDiscount() != 10.0f){
		out.println("<span class='zk'>"+goodsinfo.getDiscount());
		out.println("</span>折");
	}%>
</div>
			<div >
				 
				 <form action="CartManage?op=addGoods&id=<%--=goodsinfo.getGoodsId()--%>" onsubmit="return fun()" method="post">
				  
			    	<div  style="float: left; margin-left:50px; width:400px; height:300px;">
			    		<!-- 商品名称 -->
			    		<p style="font-size: 18px; font-weight: bold;"><%=goodsinfo.getGoodsName()%> </p>
			    		<p><%//商品商品是否打折
			    		if(goodsinfo.getDiscount() == 10.0f){%>
			    		<!-- 无折扣 -->
			    		<!-- 现价 -->
			    		现价：<span class="discount">¥<%=goodsinfo.getPrice()%></span>
			    		<%}else{%>
			    			<!-- 有折扣 -->
			    			<!-- 现价 -->
			    			原价：<span class="old_price">¥<%=goodsinfo.getPrice()%></span>&nbsp;
			    			<!-- 折扣价 -->
			    			现价：<span class="discount">¥<%=goodsinfo.getDiscountPrice() %></span>
			    		<%}%></p>
			    		<!-- 商品类型名称 -->
			    		<p>商品类型：<a><%=goodsinfo.getGoodstype().getTypeName()%></a></p>
			    		<!-- 商品描述 -->
			    		描述：<span><%=goodsinfo.getRemark().replaceAll("\r\n","<p/>").replaceAll(" ", "nbsp;")%></span><br/>
			    		
			    		<div id="right_bottom_div">
			    			<div id="buy_div">我&nbsp;要&nbsp;买：</div>
			    			<!-- 商品购买数量 -->
			    			 <input type="text" size="3" value="1" name="product_num" style="margin-top:5px;"  />件 
			    			<!-- <input type="number" min="1"  name="product_num" style="margin-top:5px;"/> -->
							
			    			<!-- 加入购物车 -->
			    			<div style="margin-top:10px;">
			    			<button type="button" id="sub_but"  onclick="sedRequest(<%=goodsinfo.getGoodsId()%>)" class="btn" >&nbsp;</button>
			    			<!-- 查看购物车 -->
			    			<div id="cart_div">
			    			<a href="../cart.jsp"  >查看购物车</a>
			    			</div>
			    			</div>
			    		</div>
			    	</div>
			      </form> 
			</div>
			<!-- <ul>
				<li>Easy to use</li>
				<li>Easy to style</li>
				<li>Responsive</li>
				<li>Handles different image sizes</li>
				<li>'Quick zoom' on hover with mouse</li>
				<li>Handles multiple instances on one page</li>
			</ul> -->
		</div>
	</article>
	<div style="float:left; width:100%;">
	<div style="padding:10px;">
	 <span style=" border:1px solid #000;padding:10px; margin-left:250px;" onmousemove="detailed('showDetail','Review')" >
		商品详细信息
	 </span>
	 <span style=" border:1px solid #000;padding:10px;" onmousemove="Review('showDetail','Review')">
	 	商品评论
	 </span> 
	</div>
		<div align="center" id="showDetail"><%=goodsinfo.getDetailed()%></div>
		<div align="center" id="Review" >
			<table width="900">
			<%for(Review review :reviewList){
			%>
				<tr>
					<td style="text-align: left;"><%=review.getCustoomer().getEmail() %></td>
					<td style="text-align: right;"><%if(review.getReviewStatus() == 1){ 
						out.println("差评");
					}else if(review.getReviewStatus() == 2){
						out.println("一般");
					}else {
						out.println("好评");
					} %></td>
				</tr>
				<tr >
					<td colspan="2"><span><%="".equals(review.getReviewContent()) ? "用户没评论默认好评" : review.getReviewContent()%></span></td>
					
				</tr>
			<% } %>
				
			</table>
		</div>
	</div>
	<script src="/qlzx/photo/js/jquery.min.js"></script>
	<script type="text/javascript" src="/qlzx/photo/js/smoothproducts.min.js"></script>
	<script type="text/javascript">
	/* wait for images to load */
	$(window).load(function() {
		$('.sp-wrap').smoothproducts();
	});
	</script>
	<%@include file="bottom_index.jsp"%>
</body>
</html>