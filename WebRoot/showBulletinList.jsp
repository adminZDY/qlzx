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
	response.setContentType("text/html;charset=utf-8");
	request.setCharacterEncoding("utf-8");
   	
    PageModel<BulletinInfo> pamod = (PageModel<BulletinInfo>) request.getAttribute("allBullein");
    			
    ArrayList<BulletinInfo> BuList = pamod.getData();
   

 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>公告列表</title>
  </head>
  <style type="text/css">
  	#list_div{
  		border-bottom: 1px dashed #000;
  		padding: 5px;
  	}
  	#notice_div{
  		width: 1200px;
  		margin-left: 60px; 
  		height: 300px;
  	}
  </style>
  <script type="text/javascript">
	var pageSize = <%=pamod.getPageSize()%>
  	var basePath = "BulletinManage?sign=ok&pageSize="+pageSize+"&pageNo=";
	//前往首页
	function toTopPage(){
		if(<%=pamod.getPageNo()%> == 1){
			alert("当前已在首页");
			return;
		}
		window.location.href = basePath+"<%=pamod.getIndexPageNo()%>";
	}
	//上一页
	function toPriviousPage(){	
		if(<%=pamod.getPageNo()%> == 1){
			alert("当前已在首页");
			return;
		}
		window.location.href = basePath+"<%=pamod.getPreviousPageNo()%>";
	}
	//下一页
	function toNextPage(){
		if(<%=pamod.getPageNo()%> == <%=pamod.getTotalpages()%>){
			alert("当前已在尾页");
			return;
		}
		window.location.href =  basePath+"<%=pamod.getNextPageNo()%>";
	}
	//尾页
	function toLastPage(){
		if(<%=pamod.getPageNo()%> == <%=pamod.getTotalpages()%>){
			alert("当前已在尾页");
			return;
		}
		window.location.href =basePath+"<%=pamod.getLastPageNo()%>";
	}
	
	function topage(){
		var num = <%=pamod.getTotalpages()%>
		var text = document.getElementById("text");
		var str = text.value.replace(/(^\s*)|(\s*$)/g, "");
		var rs = new RegExp("\\d{"+str.length+"}");
		var s = rs.test(str);
		
		if(s ){
			if(str > num || str < 1 ){
				alert("只能输入1-"+num+"之间的数字");
				return;
			}
			window.location.href =basePath+str;
		}else{
				alert("请输入正确的数字");
		}
	}
  </script>
  <body>
   	<%@include file="top_index.jsp" %>
   	
  <div id="notice_div" >
  	<img src="../images/site_ico.gif">您现在所在的位置：<a href="/qlzx/index.jsp">网站首页</a>&gt;网站公告
  	<p></p>
  	<div style="height: 250px;">
  	<%
  	for(BulletinInfo bull : BuList ){
  	%>
  	<div id="list_div"> <a href='BulletinManage?op=viewBulletin&id=<%=bull.getId()%>'> <%=bull.getTitle()%></a>
  	 &nbsp;&nbsp;&nbsp;<span style="float: right;"><%=DateTimeUtil.ConvertDate(bull.getCreateTime()) %></span></div>
  <%}%>
  	</div>
  <span style="float: right;">共有<%=pamod.getTotalRecords()%>条公告&nbsp;第<%=pamod.getPageNo()%>/<%=pamod.getTotalpages()%>页
  &nbsp;<a href="javascript:toTopPage()">首页</a>
  &nbsp;<a href="javascript:toPriviousPage()">上一页</a>
  &nbsp;<a href="javascript:toNextPage()">下一页</a>
  &nbsp;<a href="javascript:toLastPage()">尾页</a>
  &nbsp;<input type="text" id="text" size="3" >
  &nbsp;<button type="button" onclick="topage()" >GO</button></span>
  
  </div>
  
  <%@include file="bottom_index.jsp" %>
  </body>
</html>
