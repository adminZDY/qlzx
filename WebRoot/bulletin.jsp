<%@page import="com.sec.control.*"%>
<%@page import="com.sec.dao.*"%>
<%@page import="com.sec.model.*"%>
<%@page import="com.sec.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	BulletinInfo buinfo = (BulletinInfo)request.getAttribute("buinfo");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>公告信息</title>
<style type="text/css">
	#content_div{
		border:1px solid #000;
		width:1200px;
		
		margin-right: 30px;
	}
</style>


 </head>

  <body>
  <%@include file="top_index.jsp" %>
   	<div id = "" style="margin-left: 60px;"><img src="../images/site_ico.gif">您现在所在的位置：<a href="/qlzx/index.jsp">网站首页</a>&gt;<a href="BulletinManage?sign=ok">网站公告</a>&gt;公告信息
   		<div id="content_div">
			<h2 align="center"><%=buinfo.getTitle()%></h2> 
			<center><%=DateTimeUtil.ConvertDate(buinfo.getCreateTime())%>&nbsp;发布人：<%=buinfo.getUser().getUserName()%></center> <p></p>
			<center id = "full"><%=buinfo.getContent() %></center> 	
   		</div>
   </div>
   <%@include file="bottom_index.jsp" %>
  </body>
</html>
