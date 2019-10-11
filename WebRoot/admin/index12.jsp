<%@page import="com.sun.org.apache.xalan.internal.xsltc.compiler.sym"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!doctype html>
<html>
  <head>    
    <title>千里之行-后台管理</title>
    <link rel="shortcut icon" href="/qlzx/favicon.ico" />
    <%@ include file="userLogin.jsp"%>
	<style type="text/css">
		body
		{
			width:1300px;
		}
		li{
			margin-left:30px;
			list-style-image: url("images/doc.gif");
			background-image: url("images/menu_item.png");
		}
		p{
			padding-top:0px;
    		margin-top:5px;
    		font-size: 15px;
		}
		.qq:ACTIVE{
			color: white;
			
		}
		a
		{
			text-decoration: none;
		}
		a:HOVER{
			background-image: url("images/menu_item_hover.png");
			text-decoration: none;
		}
		
		a:ACTIVE
		{
			list-style-image: url("images/doc_red.gif");
			text-decoration: none;
		}
		
		/*头部*/
		header
		{
			margin-top:0px;
			background:height:100px;
			width:100%;
			border:1px solid white;
			background-color:#9fc0ec;
		}
		#logo_bottom
		{
			background: url("images/middle.jpg") repeat-x;
			width:1300px;
			height: 30px;
		}
		/*版心*/
		#middle
		{
			float: left; 
			width:100%; 
			margin-top:10px;
		}
		/*版心左边    菜单*/
		#middle_left_div
		{
			float: left;
			width: 20%; 
			border:1px solid #000;
		}
		#menu_item
		{
			padding-top: 5px;
		}
		/*用户信息栏*/
		#loginInfo
		{
			background-image: url("images/macback.png");
		}
		/*用户信息栏*/
		#loginInfo li
		{
			margin-left: 0px;
			list-style: none;
			font-size: 10px;
		}
		/*菜单栏*/
		#title{
			background:url("images/menubar.png") repeat-x ;
			color:white;
			height:25px;
			margin-bottom: 5px;
			text-indent: 20px;
		}
		/*版心中间   折叠 */
		#middle_center_div
		{
			 float:left;
			 width:5px;
			 margin-left:15px;
			 height:100%;
			 margin-top: 300px;
		}
		/*版心右边  iframe框架 */
		#middle_right_div
		{
			 float: right;
			 width: 78%;
			 height: 100%; 
		}
		/*iframe框架样式*/
		#mainFrame
		{
			frameborder:0;
		    marginwidth:0;
		    marginheight:0;
		    width:100%;
		    height:700px;
		    background-color: white;
		    
		}
		#login_bottom
		{
			float:left;
			text-align:center;
			background:url("images/middle.png") repeat-x;
			width:1300px;
		}
		/*尾部*/
		footer
		{
			float:left;
			text-align:center;
			background:url("images/middle.jpg") repeat-x;
			width:1300px;
		}
	</style>
	
	<script type="text/javascript">
		/*关闭之前的事件*/
		window.unload = function() 
  		{
  			event.returnValue="确定离开当前页面吗？";   
		}; 
    	//window.onunload = closeThis();
		
		function close_open_leftmenu(imgBtn){
		
			var left_div = document.getElementById("middle_left_div");
			var right_div = document.getElementById("middle_right_div");
			if(left_div != null && right_div != null){
				if(left_div.style.display == 'block' || left_div.style.display == ''){
					left_div.style.display = "none";
					right_div.style.width="98%";
					imgBtn.src="images/open_left.gif";
					imgBtn.title = "展开左边";
				}else{
					left_div.style.display="block";
					right_div.style.width="78%";
					imgBtn.src = "images/close_left.gif";
					imgBtn.title = "折叠左边";
				}
			}
		}
		
		function closeThis()
		{
			window.location.href = "../servlet/SystemManage?op=loginOut";
		}
		
		function updatePwd()
		{
			/*访问修改密码页面*/
			return "updateUserPwd.jsp";
		}
		
		function updateName()
		{
			/*访问修改名称页面*/
			return "updateUserName.jsp";
		}
	</script>
	
  </head>
  
  <body>
  	<header>
  		<img src="images/logo.gif"/>
  	</header>
  	<div id="logo_bottom"></div>"C:/Users/郑/Desktop/菜单导航/index.html"
  	<!--  style="background:url('images/middle.jpg') -->
  	<div id ="middle">
  	
	  	<div id ="middle_left_div">
	  		<div id="loginInfo">
	  			<ul>
	  			<li>登录用户：<span id="span_name"><%=userName %></span><br/></li>
	  			<li>登录时间：<%=time %></li>
	  			</ul>
	  		</div>
	  		<div id="meun_item">
	  			<div id="title">公告管理</div>
	  			<div id="content">
	  				<ul>
		  			<li><a href="../servlet/BulletinManage?op=toAdd" target="mainFrame">发布公告</a><hr/></li>
		  			<li> <a href="../servlet/BulletinManage" target="mainFrame">公告管理</a></li>
	  				</ul>
	  			</div>
	  		</div>
	  		<div id="meun_item">
	  			<div id="title">商品管理</div>
	  			<div id="content">
	  				<ul>
		  			<li> <a href="../servlet/GoodsManage?op=toAdd" target="mainFrame">添加商品</a><hr/></li>
		  			
		  			<li> <a href="../servlet/GoodsManage?op=viewGoods" target="mainFrame">商品信息管理</a><hr/></li>
		  			<li> <a href="../servlet/GoodsTypeManage?op=toAdd"  target="mainFrame">添加商品类型</a><hr/></li>
		  			<li> <a href="../servlet/GoodsTypeManage?op=queryAll"  target="mainFrame">商品类型管理</a><hr/></li>
		  			</ul>
	  			</div>
	  		</div>
	  		<div id="meun_item">
	  			<div id="title">订单管理</div>
	  			<ul>
	  			<li><a href="../servlet/OrderManage?op=queryAll" target="mainFrame">订单信息管理</a></li>
	  			</ul>
	  		</div>
	  		<div id="meun_item">
	  			<div id="title">客户管理</div>
	  			<div id="content">
	  				<ul>
	  				<li><a href="../servlet/CustomerManage?op=queryAll" target="mainFrame">客户信息管理</a></li>
	  				</ul>
	  			</div>
	  		</div>
	  		<div id="meun_item">
	  			<div id="title">系统管理</div>
	  			<div id="content">
	  				<ul>
		  			<li><a href="updateUserPwd.jsp" target="mainFrame">修改密码</a><hr/></li>
		  			<li> <a href="updateUserName.jsp" target="mainFrame">修改用户名</a><hr/></li>
		  			<li> <a href="javascript:closeThis()">退出系统</a></li>
		  			</ul>
	  			</div>
	  		</div>
	  	</div>
	  	
	  	<!-- 版心中间折叠功能 -->
	  	<div id="middle_center_div">
	  	<input type="image" title="折叠左边" src="images/close_left.gif" onclick="close_open_leftmenu(this)"/>
	  	</div>
	  	
	  	<!-- 版心iframe框架 -->
	  	<div id ="middle_right_div">
	     <iframe name = "mainFrame" src="welcome.jsp" id="mainFrame"></iframe> 
	  	</div>
 	</div>
 	<!--尾眉-->
  	<footer>
    	<p>千里之行&copy;(2009-2011)All Right Reserved</p>
    </footer>
  </body>
</html>
