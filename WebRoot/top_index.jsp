<%@page import="com.sec.model.CustomerInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script type="text/javascript">
    	/*关闭当前浏览窗口*/
    	function closeForm()
    	{
    		/* var flag = confirm("是否退出当前窗口");
    		if(flag)
    		{ */
    			this.close();
    		/* } */
    	}
    </script>
    <link rel="shortcut icon" href ="/qlzx/favicon.ico"/>
	<link rel="bookmark" href = "/qlzx/favicon.ico"/>
    <style type="text/css">
    	header a
    	{
    		text-decoration: none;
    		color:#000;
    	}
    	header a:HOVER
    	{
    		color: gray;
    	}
    	header
    	{
    		background:url("/qlzx/images/bg.jpg");
    		width:1366px;
    		height: 129px;
    	}
		header div
		{
			float:left;
		}
    	/*logo区整体大小*/
    	.logo 
    	{
    		width: 1366px;
    		height: 90px;
    	}
    	/*logo图,显示图片,以图片大小为准*/
    	.logo_left
    	{
			float:left;
    		width:260px;
    		height: 90px;
    	}
    	/*首页导航*/
    	.logo_right
    	{
    		float:right; 
    		width:1101px;
    		height: 90px;
    	}
		/*首页导航（顶部）*/
		.logo_right_top
		{
			width:480px;
			height:30px;
			float:right;
			margin-right:50px;
			font-size:14px;
			
		}
    	.logo_right_top li
    	{
    		list-style-type: none;
    	}
    	.logo_right_top a
    	{
    		text-decoration: none;
    	}
		.logo_right_top div
		{
			float:left;
			height:21px;
			
		}
		
		/*首页导航（底部）*/
		.logo_right_bottom
		{
			float:left;
			margin-top:35px;
			width:1101px;
			height:10px;
			text-align:left;
		}
		/*搜索栏*/
		.logo_nav
    	{
    		width: 1366px;
    		height: 30px;
    	}
    	.logo_nav div
    	{
    		float: right;
    	}
    	.shortcut_nav
    	{
    		margin-right: 60px;
    	}
    	.search
    	{
    	 	background: url("/qlzx/images/btn_search.gif");
    	}
		.logoInfo{
			margin-left: 200px;
		}
		@font-face {/*声明字体*/
		  font-family: 'icomoon';
		  src:  url('/qlzx/admin/fonts/icomoon.eot?1x1j55');
		  src:  url('/qlzx/admin/fonts/icomoon.eot?1x1j55#iefix') format('embedded-opentype'),
		    url('/qlzx/admin/fonts/icomoon.ttf?1x1j55') format('truetype'),
		    url('/qlzx/admin/fonts/icomoon.woff?1x1j55') format('woff'),
		    url('/qlzx/admin/fonts/icomoon.svg?1x1j55#icomoon') format('svg');
		  font-weight: normal;
		  font-style: normal;
		}
		 /*字体图标引用样式*/
		 .fontStyle
		 {
		 	font-family: 'icomoon';
		 	font-style: normal;
		 }
    </style>
  	<%-- 用户登录验证 --%>
	<%
		CustomerInfo customer = (CustomerInfo)session.getAttribute("customer");
		customer = customer == null?new CustomerInfo():customer;
	%>
	<script type="text/javascript" src="/qlzx/js/hobby.js"></script>
	<script type="text/javascript">
		function fun(){
		
			var text = document.getElementsByName("txt")[0];
			var str = text.value.replace(/(^\s*)|(\s*$)/g, "");
			text.value = str;
		}
	</script>
  </head>
  <body>
  	<!-- 页眉 -->
    <header>
    	<!-- logo整体 -->
    	<div class  = "logo">
    		<!-- logo左边 -->
    		<div class="logo_left">
    			<a href="/qlzx/index.jsp" style="color:transparent;"><img src="/qlzx/images/logo1.gif"  style="margin-left: 20px;margin-top: 10px; width: 243px; height: 90px; " /></a>
    		</div>
    		<!-- logo右边 -->
    		<div class ="logo_right">
   			  	<div class ="logo_right_top">
     	  			<div style="width:80px;margin-left: 5px; margin-top:5px;">
            			<i class="fontStyle" style="color: orange;"></i>
            			<a onclick="SetHome(this,window.location)" style="cursor:pointer">设为首页</a>
       			    </div>
                    <div style="width:64px;margin-left: 5px; margin-top:5px;">
                    	<i class="fontStyle" style="color: red"></i>
                        <a href="/qlzx/cart.jsp">购物车</a></div>
                    <div style="width:74px;margin-left: 10px; margin-top:5px;">
                        <i class="fontStyle" style="color: green;"></i><a href="/qlzx/servlet/OrderManage?op=myManage&customerid=<%=customer.getId() %>">我的信息</a>
                    </div>
                    <div style="width:64px;margin-left: 15px; margin-top:5px;">
                        <i class="fontStyle" ></i><a href="javascript:closeForm();">退出</a>
                    </div>
                    <div style="width:80px;margin-left: 5px; margin-top:5px;">
                        <i class="fontStyle"style="color: orange;"></i>
                        <a onclick="AddFavorite(window.location,document.title)" style="cursor:pointer">收藏网站</a>
                    </div>
                    <div style="width:64px;margin-left: 10px; margin-top:5px;">
                        <i class="fontStyle" style="color: green;"></i><a href="javascript:alert('帮助')">帮助</a>
                    </div>
                </div>
              <div class ="logo_right_bottom">
              		<span class  = "logoInfo"> 你好
              		<% 
              		if(customer.getEmail() == null){%>
              			,欢迎光临千里之行购买旅游用品！
              			 <a href="/qlzx/login_register.jsp">[请登录]</a>
                        <a href="/qlzx/login_register.jsp">[免费注册]</a>
              		<%}else{%>
                   		<%= customer.getEmail().split("@")[0]%>,欢迎光临千里之行购买旅游用品！
                   		<a href="/qlzx/servlet/CustomerManage?op=loginout">[退出]</a>
                    <%}%> 
                   </span>
                </div>
   		 	</div>
       </div>
       
       <div class ="logo_nav">
    		<form action="/qlzx/servlet/GoodsManage?op=addvague" onsubmit="fun()" method="post">
    			<div class ="shortcut_nav">
    				商品关键词：<input type="text" name="txt" class="fontStyle" style="background-color: white;" size="10" placeholder=""/>
    				<input type="submit" value="搜 索" class ="search"/> 
    				热门商品: 登山攀岩器材、户外服装、户外桌椅、睡袋垫子、野营用品、野营帐篷、运动手表...
    			</div>
    		</form>
    	</div>
    </header>
  </body>
</html>

<!--

<div class ="logo_right_top">
     	  			<div class ="logo_right_top1">
            			<a><img src="/qlzx/images/home.gif"/>首页</a><span>|</span>
       			    </div>
                    <div class ="logo_right_top2">
                        <a><img src="/qlzx/images/cart.gif"/>购物车</a><span>|</span>
                    </div>
                    <div class ="logo_right_top3">
                        <a><img src="/qlzx/images/user_info.gif"/>我的信息</a><span>|</span>
                    </div>
                    <div class ="logo_right_top4">
                        <a><img src="/qlzx/images/loginOut.gif"/>退出</a><span>|</span>
                    </div>
                    <div class ="logo_right_top5">
                        <a><img src="/qlzx/images/shoucang.gif"/>收藏网站</a><span>|</span>
                    </div>
                    <div class ="logo_right_top6">
                        <a ><img src="/qlzx/images/help.gif"/>帮助</a>
                    </div>
                </div>
              <div class ="logo_right_bottom">
                    <span class  = "logoInfo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你好,欢迎光临千里之行购买旅游用品！
                        <a>[请登录]</a>
                        <a>[免费注册]</a>
                   </span>
                </div>

-->

<!-- logo下面导航栏 -->
<!--    	<div class ="logo_nav">
    		<form>
    			<div class ="shortcut_nav">
    				商品关键词：<input type="text"/>
    				<input type="button" value="搜 索" class ="search"/> 
    				热门商品: 登山攀岩器材、户外服装、户外桌椅、睡袋垫子、野营用品、野营帐篷、运动手表...
    			</div>
    		</form>
    		</div>
  -->
