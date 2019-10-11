<%@page import="com.sec.dao.GoodsTypeDao"%>
<%@page import="com.sec.model.GoodsType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sec.util.PageModel"%>
<%@page import="com.sec.control.*"%>
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="userLogin.jsp" %>

<%
	PageModel<GoodsType> pm = (PageModel<GoodsType>)request.getAttribute("alltype");
	ArrayList<GoodsType> goodsType = pm.getData();//当前数据
	//获取查询文本
	String selectText = (String)request.getParameter("text");
    selectText = selectText == null?"":selectText;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>显示商品类型</title>
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
	 #titlebar_div{
		background:url('/qlzx/images/titlebar_div_bg.jpg') repeat-x bottom;
		height: 33px;
	 }
	 .input_txt{
	 	background: url('/qlzx/images/search_ico.gif') no-repeat;
	 }
	 .input_noTxt
	 {
	 	background: none;
	 }
	 .input_sub{
	 	background: url('/qlzx/images/btn.png') no-repeat; width:61px; border: 0px; height: 22px;
	 }
	 .linear{
	 
  		  background: -webkit-linear-gradient(left, #FF66CC , #6699FF); /* Safari 5.1 - 6.0 */
  		  background: -o-linear-gradient(right, #FF66CC, #6699FF); /* Opera 11.1 - 12.0 */
   		  background: -moz-linear-gradient(right, #FF66CC, #6699FF); /* Firefox 3.6 - 15 */
    	  background: linear-gradient(to right, #FF66CC , #6699FF); /* 标准的语法（必须放在最后） */
		}
		/*字体图标引用样式*/
	 .fontStyle
	 {
	 	font-family: 'icomoon';
	 	font-style: normal;
	 }
	</style>

<script type="text/javascript">
	//全选和反选
	function chkAll_click() {
		//第一个复选框
		var chkAll = document.getElementById("chkAll");
		//表格id对象
		var dataTable = document.getElementById("dataTable");

		if (chkAll != null) {

			var items = dataTable.getElementsByTagName("input");
			if (items != null) {
				for ( var i = 0; i <= items.length; i++) {
					if ("chkItems" == items[i].name) {
						items[i].checked = chkAll.checked;
					}
				}
			}
		}

	}

		
	//obj代表当前选中的复选框对象
	function chkltems_click(obj) {

		var chkAll = document.getElementById("chkAll");
		var dataTable = document.getElementById("dataTable");

		if (obj != null && chkAll != null) {
			if (obj.checked) {
				chkAll.checked = true;
				return;
			}
			var items = dataTable.getElementsByTagName("input");
			if (items != null) {
				for ( var i = 0; i < items.length; i++) {
					if ("chkItems" == items[i].name && items[i].checked) {
						chkAll.check = true;
						return;
					}
				}
				chkAll.checked = false;
			}
		}
	}

	/**
	*页面跳转（上下首尾页）
	*/
	function toPage(pageNo)
	{
		document.getElementsByName("pageNo")[0].value = pageNo;
		var selectText = "<%=selectText%>";
		//请求要查询所有
		if(selectText == null || selectText == "" || selectText.replace(/\s/g,"").length==0) 
		{
			document.getElementsByName("op")[0].value = "queryAll";
		}
		else{
			document.getElementsByName("op")[0].value = "query";
		}
		document.getElementsByName("text")[0].value = selectText;
		document.form1.submit();
	}
	
	//多删除、查询、添加
	function opck(click_op)
	{
		var op = document.getElementsByName("op")[0];
		op.value = click_op.id;
		
		if(op.value == "query")
		{
			//查询（获取文本框值去空格）
			var text = document.getElementsByName("text")[0];
			text.value = text.value.replace(/\s/g,"");
			//搜索栏为空或没有填写
			if(text.value.length == 0)
			{
				op.value = "queryAll";
			}
			else{
				op.value = click_op.id;
			}
		}
		
		//多删除
		if(op.value == "removeMore")
		{
			if(document.getElementById("chkAll").checked)
			{
				if(confirm("是否删除商品类型"))
				{
					
				}
				else
				{
					return;
				}
			}
			else{
				alert("请选择你想要删除的编号");
				return;
			}
		}
		document.form1.submit();
	}
	
	function toupdate(id)
	{
		document.getElementsByName("id")[0].value = id;
		document.getElementsByName("op")[0].value = "toEdit";
		document.form1.submit();
	}
	function todelete(id)
	{
		if(confirm("是否删除"))
		{
			document.getElementsByName("id")[0].value = id;
			document.getElementsByName("op")[0].value = "remove";
			document.form1.submit();
		}
	}
	
</script>
</head>
<body>
	<form name="form1" method="post" action="GoodsTypeManage">
	
	<div id="titlebar_div">
	  	<img src="/qlzx/images/doc_red.gif" align="bottom"/>商品类型管理
	  <div style="float:right;">
		  <input type="text"  style="font-family: 'icomoon'; background-color: white;"
		  value="<%=selectText%>" placeholder="" name="text" />
		  <input type="button" value="查询" class="input_sub" id="query" onclick="opck(this)" /> 
		  <input type="button" value="添加商品" class="input_sub" id="toAdd" onclick="opck(this)" />
		  <input type="button" value="删除商品" class="input_sub" id="removeMore" onclick="opck(this)" />
	  </div>
	</div>
	<table id="dataTable" border="1px" cellspacing="0px" cellpadding="5px" width="100%" >
		<tr style="background-image: url('/qlzx/images/menubar.png');">
			<th><input type="checkbox" id="chkAll" onclick="chkAll_click();"/></th>
			<th>类型编号</th>
			<th>类型名称</th>
			<th>操作</th>
		</tr>

			<%
			if (goodsType != null) 
			{
				for (int i = 0; i < goodsType.size(); i++) 
				{
			%>
				<tr>
					<th>
						<input type="checkbox" name="chkItems" value="<%=goodsType.get(i).getTypeId()%>" onclick="chkltems_click(this)"/>
					</th>
					<%--类型编号 --%>
					<td><%=goodsType.get(i).getTypeId()%></td>
					<%--类型名称 --%>
					<td><%=goodsType.get(i).getTypeName()%></td>
					<!-- href="goodsTypeManage" -->
					<th>
						<a href="javascript:toupdate(<%=goodsType.get(i).getTypeId()%>)">修改</a> 
						<a href="javascript:todelete(<%=goodsType.get(i).getTypeId()%>)">
						<i class="fontStyle" style="color: gray;"></i>删除</a>
					</th>
				</tr>
				<%}%>
			<%}%>
			<tr>
				<td colspan="4" align="center">共有 <%=pm.getTotalRecords()%>条信息&nbsp;&nbsp;
					第 <%=pm.getPageNo()%>/ <%=pm.getTotalpages()%>页&nbsp;&nbsp;
					<a href="javascript:toPage('<%=pm.getIndexPageNo()%>')">首页</a>
					<a href="javascript:toPage('<%=pm.getPreviousPageNo()%>');">上一页</a>
					<a href="javascript:toPage('<%=pm.getNextPageNo()%>');">下一页</a>
					<a href="javascript:toPage('<%=pm.getLastPageNo()%>');">尾页</a>
					<input type="hidden" name = "op" value="queryAll"/>
					<input type="hidden" name = "pageNo" value="1">
					<input type="hidden" name = "pageSize" value="7">
					<input type="hidden" name = "id" value="7">
				</td>
			</tr>
		</table>
	</form>
	<%--
		
<script type="text/javascript">
      var pageNo = 
      <%=pm.getPageNo()%>;
      var pageSize = 
      <%=pm.getPageSize()%>;
      var text = "<%=request.getParameter("text")%>";
      alert(text);
      if(text == null || text == ""){
      		
      	var pagePath = "GoodsTypeManage?pageSize="+pageSize+"&pageNo=";
      
      }else{
      	
      	var pagePath = "GoodsTypeManage?pageSize="+pageSize+"&text="+text+"&pageNo=";
      	
      }
      
      var Pathop="GoodsTypeManage?op=";//获取op值
      //前往首页
      function toTopPage(){
      window.location.href = pagePath+"<%=pm.getIndexPageNo()%>";
      }
      //上一页
      function toPriviousPage(){
      alert(pagePath+"<%=pm.getPreviousPageNo()%>");
     
     /*  GoodsTypeManage?pageSize=5&text=sfd&pageNo=1;
      	 GoodsTypeManage?op=viewBulletin
       */
      window.location.href = pagePath+"<%=pm.getPreviousPageNo()%>";
      }
      //下一页
      function toNextPage(){
     	alert(pagePath+"<%=pm.getPreviousPageNo()%>");
      window.location.href = pagePath+"<%=pm.getNextPageNo()%>";
      }
      //尾页
      function toLastPage(){
      
      	window.location.href = pagePath+"<%=pm.getLastPageNo()%>";
		
	}
</script>
	 --%>
</body>
</html>
	