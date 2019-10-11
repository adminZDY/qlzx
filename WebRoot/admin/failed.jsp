<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>操作失败</title>
    <%
		String failed;
		if(request.getAttribute("failed") == null)
		{
			failed = new String(request.getParameter("failed").getBytes("iso-8859-1"),"utf-8");
		}else
		{
			failed = (String)request.getAttribute("failed");
		}
		
		String jsp;
		if(request.getAttribute("jsp") == null)
		{
			jsp = request.getParameter("jsp");
		}else
		{
			jsp = (String)request.getAttribute("jsp");
		}
	 %>
  </head>
  
  <body>
    <div>
    	<div style="background: url('../images/titlebar_div_bg.jpg') repeat-x bottom; height: 25;"><img src="../images/doc_red.gif">操作失败</div>
    	<h1 align="center"><img src="../images/error.gif" align="middle">操作失败!</h1>
    	<hr>
    	<h6>对不起，<%=failed%>&nbsp;&nbsp;<a href="<%=jsp%>">返回</a></h6>
    </div>
  </body>
</html>
