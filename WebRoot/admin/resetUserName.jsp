<%@ page language="java" import="java.util.*,com.sec.model.UserInfo" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改名称</title>
    <% 
    	String oldPwd = ((UserInfo) request.getSession().getAttribute("user")).getUserPwd();
    %>
    <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "welcome.jsp";
    	}	
    	
    	function VailLen(obj)
    	{
    		var msg = obj.value+"";
    		if(msg.length >= 0 || msg.indexOf("", 0))
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
   			alert(userName.value);
    		alert(VailLen(userName));
    		//输入长度大于等于6
    		if(!VailLen(userName))
    		{
    			userName.innerHTML = "名称不能为空";
    		}
    		else
    		{
    			var flag = confirm("确定是否修改？");
    			if(flag)
    			{
    				return true;
    			}
    		}
    		return false;
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
    <form action="../servlet/SystemManage" method="post" onsubmit="return Submt()">
    	<table width="100%">
    		<tr>
    			<th colspan="2" style="font-size: 18px; text-align: left">修改名称</th>
    			<td class="right"><input type="button" value="返回" onclick="returnMenu()"></input></td>
    		</tr>
    		<tr>
    			<td width="100px">用户名称：</td>
    			<td><input type="text" name = "userName"/></td>
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

  </body>
</html>
