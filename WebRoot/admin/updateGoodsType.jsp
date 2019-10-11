<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.sec.control.*"%>
<%@page import="com.sec.dao.*"%>
<%@page import="com.sec.model.*"%>
<%@page import="com.sec.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%-- <base href="<%=basePath%>"> --%>

<title>商品类型</title>
<%
	GoodsType goodsType = (GoodsType)request.getAttribute("goodsType");
	System.out.print(goodsType == null);
	System.out.print(goodsType.getTypeId()+"  "+goodsType.getTypeName());
%>

<script type="text/javascript" src="/qlzx/common/js/ajax.js" charset="gbk"></script>
<script type="text/javascript">
	/**
	*字符空格提交
	*/
	function filter()
    {
    	var obj = document.getElementsByTagName("input");
    	for ( var i = 0; i < i.length; i++) {
			obj[i].value = obj[i].value.replace(/(^\s*)|(\s*$)/g, "");
		}
	}
	
	/**
	*登录回滚ajax
	*/
  	function callback(xmlHttp)
    {
	    //使用返回的文本替换div中的内容
	    var msg = xmlHttp.responseText;
	    //查询到数据
	    if(msg == "no")
	    {
	    	var typeName = document.getElementsByName("typeName")[0];
			typeName.value = "";
	    	typeName.placeholder = "该类型已经存在，请重新输入";
	    	typeName.style = "font-weight: 700px;color: red;";
	    }
	    else{
	    	//ok
	    	/*清除空格*/
	    	filter();
	    	document.form1.action = "/qlzx/servlet/GoodsTypeManage";
	    	document.getElementsByName("op")[0];
	    	document.form1.submit();
	    }
    }
	  /*访问Servlet*/	
     function getMessage()
     {
     	var typeName = document.getElementsByName("typeName")[0].value.replace(/(^\s*)|(\s*$)/g, "");
     	if(typeName.length <= 2)
     	{
     		alert("类型名称至少要输入2个字，并且不能输入空格");
     	}
     	else if(typeName == "<%=goodsType.getTypeName()%>")
     	{
     		alert("不能修改成原先的名称");
     		document.getElementsByName("typeName")[0].value = "";
     		document.getElementsByName("typeName")[0].focus();
     	}
     	else
     	{
     		var url = "/qlzx/servlet/GoodsTypeManage";
	       	var str_params = "op=checkType&checkType="+typeName;
	       	//调用sendRequest()方法发送Ajax异步请求
	       	sendRequest("post",url,str_params,callback);
     	}
     }
         
    //返回管理主页
	function skip() {
		window.location.href = "/qlzx/servlet/GoodsTypeManage";
	}
	
	/**
	*文本框获得焦点
	*/
	function focusText(text)
	{
		text.style = "color:#000;";
		text.placeholder = "请输入你要添加的类型";
	}
</script>
</head>

<body>
	<div style="background:url('/qlzx/admin/images/titlebar_div_bg.jpg') repeat-x bottom; height: 25;">
		<img src="/qlzx/images/doc_red.gif">修改商品类型
	</div>
	
	<form name="form1" action="servlet/GoodsTypeManage"
		method="post">
		<table style="border:1px solid #000; width:100%">
			<tr>
				<td>类型编号</td>
				<td>
					<input type="text" readonly unselectable = "on" name="typeId" value="<%=goodsType.getTypeId()%>"/>
				</td>
			</tr>
			<tr>
				<td>类型名称:</td>
				<td><input type="text" name="typeName" onfocus="focusText(this)" value="<%=goodsType.getTypeName()%>" placeholder = "请输入你要添加的类型"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="button" value="提交表单" onclick="getMessage()"> 
					<input type="reset" value="重置表单"> 
					<input type="button" onclick="skip();" value="返回">
					<input type="hidden" name = "op" value = "update"/>
				</td>
			</tr>
		</table>
	</form>
</body>
<%--
	<div
		style="background:url(images/titlebar_div_bg.jpg) repeat-x bottom; height: 25;">
		<img src="images/doc_red.gif">修改商品类型
	</div>
	<form name="form1" action="GoodsTypeManage?op=update" method="post">
		<table style="border:1px solid #000; width:100%">
			<tr>
				<td>类型编号</td>
				<td>
					<input type="text" readonly="readonly" name="typeId" value="<%=goodsType.getTypeId()%>"/>
				</td>
			</tr>
			<tr>
				<td>类型名称:</td>
				<td>
					<input type="text" name="typeName" 
					value="<%=goodsType.getTypeName()%>" placeholder = "请输入类型名称" onfocus="focusText(this)"/>
				</td>
			</tr>
			<tr>
				<td><input type="button" value="提交表单" /> <input
					type="reset" value="重置表单" /> <input type="button" onclick="skip();"
					value="返回" /></td>
			</tr>
		</table>
	</form>
 --%>
</html>
