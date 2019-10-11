<%@ page language="java" import="java.util.*,com.sec.model.UserInfo" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改用户密码</title>
    <% 
    	//原密码
    	String oldPwd = ((UserInfo) request.getSession().getAttribute("user")).getUserPwd();
    %>
    <script type="text/javascript">
    	//返回主页
    	function returnMenu()
    	{
    		window.location.href = "welcome.jsp";
    	}	
    	/*验证字符串*/
    	function VailLen(obj)
    	{
    		if(obj.value.length >= 6)
    		{
    			obj.focus();
    			return true;
    		}
    		return false;
    	}
    	
    	/**
    	*提交表单时的验证
    	*/
    	function SubmtForm()
    	{
    		var tips = document.getElementById("tips");
    		var oldPwd = document.getElementsByName("oldPwd")[0];
    		var newPwd = document.getElementsByName("newPwd");
    		
    		if(oldPwd.value.indexOf(" ")!=-1 || newPwd[1].value.indexOf(" ")!=-1)
    		{
    			tips.innerHTML = "密码不能输入空格";
    		}
    		else if(!VailLen(oldPwd)||!VailLen(newPwd[0])||!VailLen(newPwd[1]))
    		{
    			tips.innerHTML = "密码长度要大于六位";
    		}
    		//新密码和旧密码相同
    		else if(oldPwd.value == newPwd[0].value || oldPwd.value == newPwd[1].value)
    		{
    			tips.innerHTML = "原始密码和新密码不能相同";
    		}
    		else if(<%=oldPwd%> != oldPwd.value)
    		{
    			tips.innerHTML = "原始密码不正确，请重新输入";
    			oldPwd.focus();
    		}
    		else if(newPwd[0].value != newPwd[1].value)
    		{
    			tips.innerHTML = "新密码和重置密码要一致";
    		}
    		else
    		{
    			return true;
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
  <div  style="width:600px ; margin: auto; margin-top: 250px;">
    <form action="../servlet/SystemManage" method="post" onsubmit="return SubmtForm()">
    	<table width="100%">
    		<tr>
    			<th colspan="2" style="font-size: 18px; text-align: left" style="background:url('/qlzx/admin/images/titlebar_div_bg.jpg') repeat-x bottom; height: 25;"><img src="/qlzx/images/doc_red.gif">修改用户密码</th>
    			<td class="right"><input type="button" value="返回" onclick="returnMenu()"></input></td>
    		</tr>
    		<tr>
    			<td width="100px">原始密码：</td>
    			<td><input type="password" name = "oldPwd"/></td>
    		</tr>
    		<tr>
    			<td>新密码：</td>
    			<td><input type="password" name = "newPwd"/></td>
    		</tr>
    		<tr>
    			<td>重置密码：</td>
    			<td><input type="password" name = "newPwd"/></td>
    		</tr>
    		<tr>
    			<td colspan="2" id = "tips">修改成功后，返回首页</td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="submit" value="提交"/>&nbsp;&nbsp;
    				<input type="reset" value="重置"/>
    				<input type="hidden" value="updatePwd" name="op">
    			</td>
    		</tr>
    	</table>
    </form>
	</div>
  </body>
</html>
