
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title>千里之行-后台管理</title>
	<link rel="stylesheet" type="text/css" href="index/css/htmleaf-demo.css">
	<link rel="stylesheet" type="text/css" href="index/css/nav.css">
    <link rel="stylesheet" type="text/css" href="index/fonts/iconfont.css">
    
    <link rel="shortcut icon" href ="/qlzx/favicon.ico"/>
	<link rel="bookmark" href = "/qlzx/favicon.ico"/>
       <style type="text/css">
		@font-face {
	  font-family: 'icomoon';
		  src:  url('/qlzx/admin/index/fonts/icomoon1.eot?1btxsa');
		  src:  url('/qlzx/admin/index/fonts/icomoon1.eot?1btxsa#iefix') format('embedded-opentype'),
		    url('/qlzx/admin/index/fonts/icomoon1.ttf?1btxsa') format('truetype'),
		    url('/qlzx/admin/index/fonts/icomoon1.woff?1btxsa') format('woff'),
		    url('/qlzx/admin/index/fonts/icomoon1.svg?1btxsa#icomoon') format('svg');
		  font-weight: normal;
		  font-style: normal;
		}
		 .fontStyle
	 {
	 font-size: 20px;position: absolute;margin-left:-1px;
	 	font-family: 'icomoon';
	 	font-style: normal;
	 }
       	#mainFrame
		{
			border:0;
		    marginwidth:0;
		    marginheight:0;
		    width:1150px;
			position:absolute;
		    height:650px;
		    background-color: white;
		    
		}
       </style>
       <script type="text/javascript">
		/*关闭之前的事件*/
		window.unload = function() 
  		{
  			event.returnValue="确定离开当前页面吗？";   
		}; 
		function closeThis()
		{
			window.location.href = "../servlet/SystemManage?op=loginOut";
		}
    	//window.onunload = closeThis();
	/* 	window.onbeforeunload = function (e) {
        e = e || window.event;
        var y = e.clientY
        if (y <= 0
        || y >= Math.max(document.body.clientHeight, document.documentElement.clientHeight)
        )
            e.returnValue = "确认关闭浏览器窗口？！！";
    } */
    $(window).unload(function (evt) {
		if (typeof evt == 'undefined') {
			evt = window.event;
		}
		alert("执行");
		if (evt) {
			var n = window.event.screenX - window.screenLeft; 
			var b = n > document.documentElement.scrollWidth-20;
			
			if(b && window.event.clientY < 0 || window.event.altKey){
			alert("正在关闭");
				// 这个可以排除刷新 关闭的时候触发
			} 
		}
	});
	
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
		
		function removeline(){
		if(event.clientX<0&&event.clientY<0)
			 {
			document.write('<iframe width="100" height="100" src="/qlzx/admin/logout.jsp"></iframe><OBJECT classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0  id=WebBrowser width=0></OBJECT>');
			document.all.WebBrowser.ExecWB(45,1);
			}
		}
	</script>
</head>
<body style="height:650px;" onunload = "">
	<div class="nav">
	       <div >
	            <div><img src="index/images/mini.png" >
               </div>
	            
	        </div>
	        <ul>
	            <li class="nav-item">
	                <a href="javascript:;"><i class="fontStyle"></i><span>公告管理</span><i class="my-icon nav-more"></i></a>
	                <ul>
	                   <li><a href="../servlet/BulletinManage?op=toAdd" target="mainFrame">发布公告</a></li>
	                   <li><a href="../servlet/BulletinManage" target="mainFrame">公告管理</a></li>
	                </ul>
	            </li>
	            <li class="nav-item">
	                <a href="javascript:;"><i class="fontStyle"></i><span>商品管理</span><i class="my-icon nav-more"></i></a>
	                <ul>
			  			<li> <a href="../servlet/GoodsManage?op=toAdd" target="mainFrame">添加商品</a></li>
			  			
			  			<li> <a href="../servlet/GoodsManage?op=viewGoods" target="mainFrame">商品信息管理</a></li>
			  			<li> <a href="../servlet/GoodsTypeManage?op=toAdd"  target="mainFrame">添加商品类型</a></li>
			  			<li> <a href="../servlet/GoodsTypeManage?op=queryAll"  target="mainFrame">商品类型管理</a></li>
		  			</ul>
	            </li>
	            <li class="nav-item">
	                <a href="javascript:;"><i class="my-icon nav-icon icon_3"></i><span>订单管理</span><i class="my-icon nav-more"></i></a>
	                <ul>
		  				<li><a href="../servlet/OrderManage?op=queryAll" target="mainFrame">订单信息管理</a></li>
		  			</ul>
	            </li>
	            <li class="nav-item">
	                <a href="javascript:;"><i class="fontStyle"></i><span >客户管理</span><i class="my-icon nav-more"></i></a>
	                <ul>
		  				<li><a href="../servlet/CustomerManage" target="mainFrame">客户信息管理</a></li>
	  				</ul>
	            </li>
	            <li class="nav-item">
	                <a href="javascript:;"><i class="my-icon nav-icon icon_1"></i><span>系统管理</span><i class="my-icon nav-more"></i></a>
	                <ul>
		  			<li><a href="updateUserPwd.jsp" target="mainFrame">修改密码</a></li>
		  			<li> <a href="updateUserName.jsp" target="mainFrame">修改用户名</a></li>
		  			<li> <a href="javascript:closeThis()">退出系统</a></li>
		  			</ul>
	            </li>
	        </ul>
	         <div style="position: absolute; top:600px;">
		         <li>登录用户：<span id="span_name"><%=userName %></span><br/></li>
		    	 <li>登录时间：<%=time %></li>
	         </div>
	    </div>
	<div style="position:relative; width: 1150px;height:650px; background-color: #000;margin-left:220px">
		 <iframe name = "mainFrame" src="welcome.jsp"  id="mainFrame"></iframe> 
	</div>
	<script src="index/js/jquery-1.11.0.min.js" type="text/javascript"></script>
	<script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>');</script>
	<script type="text/javascript" src="index/js/nav.js"></script>
</body>
</html>