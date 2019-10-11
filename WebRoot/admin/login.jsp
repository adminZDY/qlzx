<%@page import="com.sec.model.UserInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	
    <title>千里之行-管理员登录</title>
    <link rel="shortcut icon" href ="/qlzx/favicon.ico"/>
 	<link rel="bookmark" href = "/qlzx/favicon.ico"/>
   
	<%
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		if(user != null)
		{
			//登录
			response.sendRedirect("/qlzx/admin/index.jsp");
		}
	%>
  <style type="text/css">
  	body{
    		overflow: hidden;
    	}
  	div {
  		background: url('../images/login_div_bg.gif') no-repeat; 
  		text-align:center; 
  		border:1px solid white; 
  		width:600px; height:400px; margin: auto; 
  		margin-top: 100px;
  	}
  	
  	span{
  		font-size: 10px;
  		color: blue;
  	}
  	
  </style>
  <script type="text/javascript" src="/qlzx/common/js/ajax.js"></script>
  <script type="text/javascript">

	
	 /*登录回滚ajax*/
  		function callback(xmlHttp)
        {
	         //使用返回的文本替换div中的内容
	         var msg = xmlHttp.responseText;
	         if(msg == "Login")
	         {
	         	
	         	window.location.href = "/qlzx/admin/index.jsp";
	         }
	         else
	         {
	         	//按钮不禁用
	       		document.getElementById("login_btn").src = "../images/login.gif";
	        	document.getElementById("login_btn").disabled=false;
	         	document.getElementById("s1").innerHTML = msg;
	         	document.getElementById("s1").style.color = "red";
	         }
        }
          
	 
	 	 /*访问Servlet*/	
         function getMessage(userName,userPwd)
         {
	          var url = "/qlzx/servlet/SystemManage";
	          
	          str_params = "op=login&userName="+userName+"&userPwd="+userPwd;
	          //调用sendRequest()方法发送Ajax异步请求
	          sendRequest("post",url,str_params,callback);
         }
         
	//去空格
	function trim(str)
	{ 
		//删除左右两端的空格
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
  	function sub(obj){
  		var userName = document.getElementsByName("userName")[0].value;
  		var userPass = document.getElementsByName("userPass")[0].value;
  		
  		if(userName != null && userPass != null && userPass.length >= 6){
  			//登陆时，禁止按按钮
  			
	        document.getElementById("login_btn").src = "../images/btn_login.gif";
 	        document.getElementById("login_btn").disabled=true;
	        
  			getMessage(trim(userName),trim(userPass));
  		}else{
  			alert("请根据提示填写登录信息！");	
  		}
  		return false;
  	}
  	
  	function foc(sp,text){
  		var s = document.getElementById(sp);
  		s.innerText = text;
  		s.style.color = "blue";
  	}
  	
  	function blu(obj ,sp, text, num){
  	var s = document.getElementById(sp);
  		if(obj.value == ""){
  			 s.innerText = text;
  			 s.style.color = "red";
  			
  		}else{
  			if(obj.name == "userPass" && obj.value.length < 6){
  				s.innerText = text;
  				s.style.color = "red";
  			
  			}else{
  				s.innerText = num;
  			}	 
  		}	
  	}
  	
  	function fun()
  	{
  		alert(window.location.href);
  		window.location.href="./logout.jsp";
  		alert(window.location.href);
  	}
  	</script>
  </head>
  
  <body unload="fun()">
    <div>
    <form  method="post" onsubmit="return sub(this)">
   		<table align="center" style="margin-top: 170px;margin-left: 220px;">
   			<tr>
   				<td>用户名：</td>
   				<td><input type="text" name="userName" onfocus="foc('s1','请输入用户名')" onblur="blu(this,'s1','用户名不能为空！','用户输入正确')"></td>
   				<th rowspan="3" width="100px">
   					<input type="image" src="../images/login.gif" id = "login_btn" style="cursor: pointer;">
   				</th>
   			</tr>
   			<tr>
   				<th colspan="3"><span id = "s1">&nbsp;</span></th>
   			</tr>
   			<tr>
   				<td align="right">密码：</td>
   				<td><input type="password" name = "userPass" onfocus="foc('s2','请输入密码')" onblur="blu(this,'s2','密码不能为空并且长度必须大于6！','密码输入正确')"></td>
   			</tr>
   			<tr>
   				<th colspan="3"><span id = "s2"></span>&nbsp;</th>
   			</tr>
   		</table>
   	</form>
    </div>
  </body>
</html>
