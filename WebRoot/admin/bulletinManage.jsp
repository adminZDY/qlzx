<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.model.BulletinInfo"%>
<%@page import="com.sec.util.PageModel"%>
<%@page import="com.sec.control.BulletinManage"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	
	response.setContentType("text/html;charset=utf-8");
	request.setCharacterEncoding("utf-8");
	
	String text = request.getParameter("text");
   	if("null".equals(text)){
   		text = "";
   	}
   	
    PageModel<BulletinInfo> pamod = (PageModel<BulletinInfo>) request.getAttribute("allBullein");
    			
    ArrayList<BulletinInfo> BuList = pamod.getData();
   
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公告信息管理</title>
    <meta charset="utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%!
		public String returnUPURL(int id)
		{
			String bath = "/qlzx/servlet/BulletinManage?op=query&id=";
			return bath+id;
		}
	 %>
<style type="text/css">
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
	 #titlebar_div{
		background:url(/qlzx/images/titlebar_div_bg.jpg) repeat-x bottom;
		height: 33px;
	 }
	 .input_txt{
	 	background: url('/qlzx/images/search_ico.gif') no-repeat;
	 }
	 .input_sub{
	 	background: url('/qlzx/images/btn.png') no-repeat ; width:62px; border: 0px; height: 22px;
	 }
</style>
	 
<script type="text/javascript">
	
	var pageSize = <%=pamod.getPageSize()%>
	var pageNo = <%=pamod.getPageNo()%>
	
	var basePath = "/qlzx/servlet/BulletinManage?pageSize="+pageSize+"&pageNo=";
	var baseop="/qlzx/servlet/BulletinManage?op=";
	//前往首页
	function toTopPage(){
	//var text =document.getElementsByName("text")[0].value;
		var text =  "<%=text == "" ? null : text%>";
		var str =basePath+"<%=pamod.getIndexPageNo()%>";
		
		window.location.href = str+"&text="+text;
	}
	//上一页
	function toPriviousPage(){
	//var text =document.getElementsByName("text")[0].value;
		var text =  "<%=text == "" ? null : text%>";
		var str =basePath+"<%=pamod.getPreviousPageNo()%>";
		
		window.location.href = str+"&text="+text;
		
	}
	//下一页
	function toNextPage(){
	//var text =document.getElementsByName("text")[0].value;
		var text = "<%=text == "" ? null : text%>";
		var str = basePath+"<%=pamod.getNextPageNo()%>";
		
		window.location.href = str+"&text="+text;
	}
	//尾页
	function toLastPage(){
		//var text =document.getElementsByName("text")[0].value;
		var text ="<%=text == "" ? null : text%>";
		var str = basePath+"<%=pamod.getLastPageNo()%>";
	
		window.location.href = str+"&text="+text;
	}
	
	//查询、添加、删除公告的点击事件
	function toManage(obj){
		//删除多个
		if(obj.id == "removeMore"){
			var chk = document.getElementsByName("chkItems");
			var flag = false;
			var flag1 = false;
			for (var i = 0;i<chk.length;i++) {
				//所有复选框
				if(chk[i].checked)
				{
					flag = true;
					break;
				}
			}
			
			if(!flag)
			{
				alert("请选择编号进行删除");
			}
			else
			{
				var r = confirm("确定是否删除");
				if(r)
				{
					document.form1.action =baseop+obj.id;
					flag1 = true;
				}
			}
			document.form1.onsubmit = function(){return flag1;};	
		}else{
			document.form1.action =baseop+obj.id;
		}
	}
	
	//全选、全不选
	function chkAll_click(){
		var chkAll = document.getElementById("chkAll");
		var dateTable = document.getElementById("dataTable");
	
		if(chkAll != null ){
			var items = dateTable.getElementsByTagName("input");
			if(items != null && chkAll.checked){
				for(var i = 0 ; i < items.length ; i++){
					if("chkItems" == items[i].name){
						items[i].checked = true;
					}
				}
			}else{
				for(var i = 0 ; i < items.length ; i++){
					if("chkItems" == items[i].name){
						items[i].checked = false;
					}
				}
			}
		}
	}
	
	//选择表格中的某条公告
	//obj代表当前选中的复选框对象
	function chkItems_click(obj){
		var chkAll = document.getElementById("chkAll");
		var dateTable = document.getElementById("dataTable");
		
		if(obj != null && chkAll != null){
			if(obj.checked){
				chkAll.checked = true;
				return;
			}
		var items = dateTable.getElementsByTagName("input");
		if(items != null){
			for(var i = 0 ; i < items.length ; i++){
				if("chkItems"== items[i].name && items[i].checked){
					chkAll.checked = true;
					return;
				}
			}
				chkAll.checked=false;
			}
		}
	}
	
	function fun(num)
	{
		var r = confirm("确定是否删除");
		if(r)
		{
			window.location = "/qlzx/servlet/BulletinManage?op=remove&id="+num;
		}
	}
</script>
  </head>
  
  <body>
  <form name="form1" method="post" >
  <div id="titlebar_div">
  	<img src="/qlzx/images/doc_red.gif" align="bottom"/>公告信息管理
  <div style="float:right;">
  <input type="text" name="text" size="10" style="font-family: 'icomoon'; background-color: white;" value="<%=text%>" placeholder=""/>
  <input type="submit" onclick="toManage(this)" class="input_sub" value="查询"/>
  <input type="submit" onclick="toManage(this)" class="input_sub"  id = "toAdd" value="添加公告"/>
  <input type="submit" onclick="toManage(this)" class="input_sub"  id = "removeMore"value="删除公告"/>
  </div>
  </div>
    <table id="dataTable" border="1px" cellspacing="0px" cellpadding="5px" width="100%" >
    	<tr style="background-image: url('/qlzx/images/menubar.png');">
    		<th><input type="checkbox" id="chkAll" onclick="chkAll_click();"/> </th>
    		<th>公告编号</th>
    		<th>标题</th>
    		<th>发布者</th>
    		<th>发布时间</th>
    		<th>操作</th>
    	</tr>
    		
    		<%--
    			for(BulletinInfo buin : BuList){
    				out.println("<tr>");
    				out.println("<th><input type='checkbox' onclick=\"chkItems_click(this)\" name ='chkItems' value="+buin.getId()+"></th>");
    				out.println("<td>"+buin.getId()+"</td>");
    				out.println("<td>"+buin.getTitle()+"</td>");
    				out.println("<td>"+buin.getUser().getUserName()+"</td>");
    				out.println("<td>"+DateTimeUtil.ConvertDate((buin.getCreateTime()))+"</td>");
    				out.println("<th ><a href='"+returnUPURL(buin.getId())+"'>修改</a>&nbsp;<a href='javascript:fun('"+buin.getId()+"')'>删除</a></th>");
    				//out.println("<th ><a href='"+returnUPURL(1)+"'>修改</a>");
    				out.println("</tr>");
    			}
    			
    			out.println("<tr>");
    			out.println("<th colspan='6'>共找到"
    			+pamod.getTotalRecords()+"条记录第"+pamod.getPageNo()+"/"+pamod.getTotalpages()+
    			"页&nbsp;<a href='javascript:toTopPage()'>首页</a>&nbsp;<a href='javascript:toPriviousPage()'>上一页</a>&nbsp;<a href='javascript:toNextPage()'>下一页</a>&nbsp;<a href='javascript:toLastPage()'>尾页</a></th>");
    			out.println("</tr>");
    		 --%>
    		 <%
    			for(BulletinInfo buin : BuList){%>
    				<tr>
    				<th><input type='checkbox' onclick="chkItems_click(this)" name ='chkItems' value=<%=buin.getId()%>></th>
    				<td><%=buin.getId()%></td>
    				<td><%=buin.getTitle()%></td>
    				<td><%=buin.getUser().getUserName()%></td>
    				<td><%=DateTimeUtil.ConvertDate((buin.getCreateTime())) %></td>
    				<th ><a href='<%=returnUPURL(buin.getId())%>'>修改</a>&nbsp;
    				<a href='javascript:fun(<%=buin.getId()%>)'><i class="fontStyle" style="color: gray;"></i>删除</a></th>
    				</tr>
    			<%}%>
    			
    		<tr>
    			<th colspan='6'>
    			共找到<%=pamod.getTotalRecords()%>条记录第<%=pamod.getPageNo() %>/<%=pamod.getTotalpages()%>
    			页&nbsp;<a href='javascript:toTopPage()'>首页</a>&nbsp;
    			<a href='javascript:toPriviousPage()'>上一页</a>&nbsp;
    			<a href='javascript:toNextPage()'>下一页</a>&nbsp;
    			<a href='javascript:toLastPage()'>尾页</a>
    			</th>
    		</tr>
    </table>
    </form>
  </body>
</html>
