
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>操作成功</title>
	<%
		String ok;
		if(request.getAttribute("ok") == null)
		{
			ok = new String(request.getParameter("ok").getBytes("iso-8859-1"),"utf-8");
		}else
		{
			ok = (String)request.getAttribute("ok");
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
    	<div style="background: url('/images/titlebar_div_bg.jpg')repeat-x bottom; height: 25;"><img src="../images/doc_red.gif">操作成功</div>
    	<h1 align="center"><img src="/qlzx/admin/images/ok.png" align="middle">操作成功!</h1>
    	<hr>
    	<h6>恭喜您，<%=ok%>&nbsp;&nbsp;<a href="<%=jsp%>" >返回</a></h6>
    </div>
  </body>
</html>
