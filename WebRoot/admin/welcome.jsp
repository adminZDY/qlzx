<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>欢迎页面</title>
    <style type="text/css">
    	body{
    		overflow: hidden;
    	}
    	#img_left
  		{
  			float: left;
  			background:url("images/welcome.png") no-repeat;
  			margin-left: 20px;
  			margin-top:40px;
  			width:250px;
  			height: 100px;
  		}
  		#img_right
  		{
  			float:right;
  			background:url("images/welcome_2.gif") no-repeat;
  			margin-top:170px;
  			width:700px;
  			height: 100px;
  		}
  		#img
  		{
  			margin-top: 100px;
  		}
    </style>
  </head>
  
  <body>
  	<div id="img">
   		<div id="img_left">
   		</div>
   		<div id="img_right">
   		</div>
   	</div>
  </body>
</html>
