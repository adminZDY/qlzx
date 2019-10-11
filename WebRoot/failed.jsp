<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>操作失败</title>

  </head>
  
  <body>
    <div style="background: url('../images/titlebar_div_bg.jpg') repeat-x bottom; height: 25;"><img src="../images/doc_red.gif">操作失败</div>
    	<h1 align="center"><img src="../images/error.gif" align="middle">操作失败!</h1>
    	<hr>
    	<h6><%=request.getAttribute("msg")%>&nbsp;&nbsp;<a href="<%=request.getAttribute("jsp") %>">返回</a></h6>
  </body>
</html>
