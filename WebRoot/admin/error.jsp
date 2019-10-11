<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>错误页面</title>
    <%
		response.setCharacterEncoding("utf-8");
		String msg;
		if(request.getAttribute("msg") == null)
		{
			msg = new String(request.getParameter("msg").getBytes("iso-8859-1"),"utf-8");
		}else
		{
			msg = (String)request.getAttribute("msg");
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
    	<h1 align="center"><img src="../images/error.gif" align="middle">发生错误了!!!</h1>
    	<hr>
    	<h6><%=msg%>&nbsp;&nbsp;<a href="<%=jsp%>">返回</a></h6>
    </div>
  </body>
</html>
