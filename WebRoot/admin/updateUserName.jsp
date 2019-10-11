<%@ page language="java" import="java.util.*,com.sec.model.UserInfo" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改名称</title>
    <script type="text/javascript" src="../common/js/ajax.js"></script>
    <script type="text/javascript">
    	//回调函数/*获取Servler返回的信息*/
    	function callback(xmlHttp)
    	{
    		//提示文本
    		var tips = document.getElementById("tips");
    		//用户输入的名称
    		var userName = document.getElementsByName("userName")[0].value;
    		//使用返回的文本替换div中的内容
    		var msg = xmlHttp.responseText;
    		//用户名存在
    		if(msg == "true")
    		{
    			tips.innerHTML = "该用户名已经被使用,请重新输入一个用户名";
    			//window.location.href = "/qlzx/admin/index.jsp";
    			//return;
    		}
    		//用户名不存在
    		else
    		{
    			if(xmlHttp.responseText == userName)
    			{
    				//修改成功
    				//局部刷新并跳转
    				parent.document.getElementById("span_name").innerHTML =  xmlHttp.responseText;
    				document.forms[0].action = "ok.jsp?jsp=/qlzx/admin/welcome.jsp&ok=修改用户名成功";
    				
    				//jsp//ok
					//window.location.href = "ok.jsp?jsp=/qlzx/admin/welcome.jsp&ok=修改用户名成功";
					document.forms[0].submit();
    				
    			}
    			else if(xmlHttp.responseText == "failed")
    			{
    				//修改失败
    				//window.location.href = "failed.jsp?jsp=/qlzx/admin/welcome.jsp&failed=修改用户名失败";
    				document.forms[0].action = "failed.jsp?jsp=/qlzx/admin/welcome.jsp&failed=修改用户名失败";
    				document.forms[0].submit();
    			}
    		}
    	}
    	/*访问Servlet*/
    	function getMessage()
    	{
    		var url = "../servlet/SystemManage";
    		var userName = document.getElementsByName("userName")[0].value;
    		var str_params = "op=updateName&userName="+userName;
    		//调用sendRequest()方法发送Ajax异步请求
    		sendRequest("post",url,str_params,callback);
    	}
    </script>
    
    <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "/qlzx/admin/welcome.jsp";
    	}	
    	
    	function VailLen(obj)
    	{
    		var msg = obj.value+"";
    		if(msg.length > 0)
    		{
    			obj.focus();
    			return true;
    		}
    		return false;
    	}
    	
    	/**
    	*提交表单时的验证
    	*/
    	function Submt()
    	{
    		var userName = document.getElementsByName("userName")[0];
    		//输入长度大于等于6
    		if(!VailLen(userName))
    		{
    			document.getElementById("tips").innerHTML = "名称不能为空";
    		}
    		else if(userName.value.indexOf(" ")!=-1)
    		{
    			document.getElementById("tips").innerHTML = "名称不能输入空字符!!";
    		}
    		else
    		{
    			var flag = confirm("确定是否修改？");
    			if(flag)
    			{
    				getMessage();
    			}
    		}
    		return false;
    	}
    	
    	/**
    	*清空span提示
    	*/
    	function clear_span()
    	{
    		document.getElementById("tips").innerHTML = "&nbsp;";	
    	}
    	
    </script>
    <style type="text/css">
    	.right{
    		text-align: right;
    		margin-right: 5px;
    	}
    </style>
  </head>
  
  <body>
  <div  style="width:600px ; margin: auto; margin-top: 250px;">
    <form action="../servlet/SystemManage" method="post" onsubmit="return Submt()">
    	<table width="100%">
    		<tr>
    			<th colspan="2" style="font-size: 18px; text-align: left" style="background:url('/qlzx/admin/images/titlebar_div_bg.jpg') repeat-x bottom; height: 25;"><img src="/qlzx/images/doc_red.gif">修改名称</th>
    			<td class="right"><input type="button" value="返回" onclick="returnMenu()"></input></td>
    		</tr>
    		<tr>
    			<td width="100px" colspan="2">用户名称：<input type="text" name = "userName" onkeyup="clear_span()"/></td>
				
    		</tr>
    		<tr>
    			<th colspan="2">
    				<span id="tips">&nbsp;</span>
    			</th>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="submit" value="提交"/>&nbsp;&nbsp;
    				<input type="reset" value="重置"/>
    				<input type="hidden" value="updateName" name="op">
    			</td>
    		</tr>
    	</table>
    </form>
</div>
  </body>
</html>
