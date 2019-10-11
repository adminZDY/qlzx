<%@page import="com.sec.util.StringUtil"%>
<%@page import="com.sec.model.UserInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登录/注册</title>
    <script type="text/javascript">
    	/*控制是否填写地址*/
		function addInfo(obj)
		{
			if(obj.checked)
			{
				document.getElementById("tableDetail").style.display="block";
			}
			else
			{
				document.getElementById("tableDetail").style.display="none";
			}
		}
	</script>
	
	
   
    <style type="text/css">
    	.main{
	    	width: 1150px;
			float:left;
			margin-top:10px;
			margin-left:60px;
			margin-bottom: 20px;
    	}
    	#main_bottom
    	{
			width:1150px;
			height:400px;
    	}
		#main_bottom div
    	{
			float:left;
    	}
		.register
		{
			width:575px;
		}
		.login
		{
			width:500px;
			height:100px;
		}
		.register img
		{
			float:left;
			margin-top:20px;
		}
		.login img
		{
			float:left;
			margin-top:20px;
		}
		.login_table
		{
			width:500px;
		}
    </style>
    <style type="text/css">>
    	.register
    	{
    		margin-left: 50px;
    	}
    	.register_table
    	{
    		margin-top:20px;
    	}
		.register_table table td
		{
			border-bottom:1px dashed #000;
		}
		.register_table table
		{
			border:0px;
		}
	</style>
    <script type="text/javascript" src="/qlzx/js/ajax.js"></script>
    <script type="text/javascript" src="/qlzx/js/peisong.js" charset="gbk"></script>
  	<script type="text/javascript">
  		var ok = false;
  		/*注册回滚ajax*/
  		function register_callback(xmlHttp)
        {
	         //使用返回的文本替换div中的内容
	         var msg = xmlHttp.responseText;
	         alert(msg);
	         if(msg == "该邮箱可注册")
	         {
	         	ok = true;
	         }
        }
          
        /*登录回滚ajax*/
        function login_callback(xmlHttp)
        {
           	var msg = xmlHttp.responseText;
          	var URL = "<%=session.getAttribute("togo")%>";
          	<%session.removeAttribute("togo");%>
        	if(msg == "true")
	       	{
	       		if(URL != "null" ){
		        	window.location.href = URL;
		         	return;
		        }  
		         window.location.href = "/qlzx/index.jsp";
		         return;
	        }
	        else 
	        {
	       		document.getElementById("email_span").innerHTML = msg;
	        	document.getElementsByName("email")[0].focus();
	        }
        }
          
          /*访问Servlet*/
          function getMessage(op,email,pwd)
          {
	          var url = "/qlzx/servlet/CustomerManage";
	          
	          //检查email使用被注册
	          if(pwd == null)
	          {
	          	 url="/qlzx/servlet/AjaxGetIsEmailExists";
	          	 str_params = "email="+email;
	          	 //调用sendRequest()方法发送Ajax异步请求
	          	 sendRequest("post",url,str_params,register_callback);
	          }
	          
	          //登录时验证email和密码是否正确
	          else
	          {
	             str_params = "op=login&email="+email+"&pwd="+pwd;
	          	 //调用sendRequest()方法发送Ajax异步请求
	          	 sendRequest("post",url,str_params,login_callback);
	          }
          }

        
  		function login()
  		{
  			var flag = false;
  			var loginEmail = document.getElementsByName("email")[0].value;
  			var pwd = document.getElementsByName("pwd")[0].value;
  			if(pwd.length < 6)
  			{
  				//不合法长度
  				document.getElementById("pwd_span").innerHTML = "密码长度要大于6位！！！";
  			} 
  			else
  			{
  				//跳转到其他Servlet
  				getMessage("login", loginEmail, pwd);
  			}
  			return flag;
  		}
  
  
  		/**
  		*注册方法（进行数据验证）
  		*/
  		function register()
  		{
  			var flag = false;
  			var registerEmail = document.getElementsByName("email")[1];
  			//密码
  			var pwd1 = document.getElementsByName("pwd")[1];
  			//确认密码
  			var pwd2 = document.getElementsByName("pwd")[2];
  		
  			//邮箱不可用
  			if(!ok)
  			{
  				alert("请点击检查邮箱按钮\n检查你的邮箱是否合法，或已被注册");
  				return flag;
  			}
  			//密码合法	
  			if(pwd1.value.length< 6)
	  		{
	  			//密码
	  			pwd1.focus();
	  			document.getElementsByName("password_span")[0].innerHTML = "密码长度要大于等于6位";
	  			return flag;
	  		}
	  		else if(pwd2.value.length< 6 || pwd2.value == "")
	  		{
	  			//确认密码
	  			pwd2.focus();
	  			document.getElementsByName("password_span")[1].innerHTML = "密码长度要大于等于6位";
	  			return flag;
	  		}
	  		if(pwd1.value != pwd2.value)
	  		{
	  			//两个密码要一致
	  			document.getElementsByName("password_span")[1].innerHTML = "两个密码要一致！！！";
	  			return flag;
	  		}
	  		//验证高级选项中的数据
	  		//填写高级选项
	  		if(document.getElementsByName("checkbox")[0].checked)
	  		{
	  			mtphone_check();
	  			phone_check();
	  			//地址信息验证无误
	  			if(ifOk())
	  			{
	  				flag = true;
	  			}
	  		}
	  		else
	  		{
	  			flag = true;
	  		}
	  		return flag;
  		}

  		
  		function checkEmail()
  		{
  			var email = document.getElementsByName("email")[1].value;
  			if(email.length <=0)
  			{
  				alert("请输入邮箱");
  			}
  			else
  			{	
	  			var reg = /^\w+((-\w)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
				
				if(!(reg.test(email)))
				{
					alert("请输入正确的电子邮件");
				}
				else
				{
					//ajax验证邮箱是否可用
  					getMessage(null, email, null);
				}
  			}
  		}
  		
  		function clearPwd(obj)
  		{
  			document.getElementById(obj).innerHTML = "&nbsp;";
  			document.getElementById("pwd_span").innerHTML = "&nbsp;";
  			document.getElementsByName("pwd")[0].value="";
  		}
  		
  		function changeOk()
  		{
  			ok = false;
  		}
  		
  		/*function SubmtForm()
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
    		else if( != oldPwd.value)
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
    	}*/
  	</script>
  </head>
  
  <body>
  	<!-- 首页 -->
    <%@include file="top_index.jsp" %>
    <!--地址导航栏-->
   	<div class="main">
    	<div class="products_nav">
       		<img src="/qlzx/images/site_ico.gif"/><span style="font-size: 16px">你所在的位置：<a href="index.jsp">网站首页</a> &gt; 注册登录
        </span></div>
        <div id = "main_bottom">
        	<!-- 登录 -->
        	<div class="login" id = "login">
        		<img src="/qlzx/images/login_logo.gif"/>
                <div class = "login_table" align="center" style="margin-top:50px;">
                	<form action="#"  method="post" onsubmit="return login()" name="form_login">
                        <table align="center">
                            <tr>
                                <td>用户账号/邮箱：</td>
                                <td><input type="email" name="email" value="a@sina.com" onchange="clearPwd('email_span')"></td>
                                <th rowspan="3" width="100px"><input type="image" src="/qlzx/images/btn_login.gif"></th>
                            </tr>
                            <tr>
                                <th colspan="3"><span id = "email_span">请输入账户/邮箱</span></th>
                            </tr>
                            <tr>
                                <td align="right">密码：</td>
                                <td><input type="password" value="123456" name = "pwd"></td>
                            </tr>
                            <tr>
                                <th colspan="3"><span id = "pwd_span">&nbsp;</span> 
                                <input type="hidden" name = "op" value="login"/>
                                </th>
                            </tr>
                        </table>
                    </form>
                </div>
        	</div>
        	<!-- 中间隔开线 -->
        	<div style="width: 10px;height:300px;background:url('images/register_div_line.gif');"> </div>
        	<!-- 注册 -->
        	<div class="register" id = "register">
        		<img src="/qlzx/images/register_logo.gif"/>
                <div class = "register_table">
             		<form action="/qlzx/servlet/CustomerManage?" method="post" onsubmit="return register()" name="form_register">
					    <table width="600" height="170" cellpadding="0">
					      <tr>
					        <td width="104">用户账户/邮箱</td>
					        <td width="175"><input type="email" name="email" id="email" onchange="changeOk()"></td>
					        <td width="297"><input type="button" name="button" id="button" value="检查邮箱" onclick="checkEmail(this)"></td>
					      </tr>
					      <tr>
					        <td>密码：</td>
					        <td><input type="password" name="pwd" id="password"></td>
					        <td><span name = "password_span"> 登录密码。</span> </td>
					      </tr>
					      <tr>
					        <td>确认密码：</td>
					        <td><input type="password" name="pwd" id="password2"></td>
					        <td><span name = "password_span"> 请再次输入密码。</span> </td>
					      </tr>
					      <tr>
					        <td height="45" colspan="3">
					        <input type="checkbox" name="checkbox" onChange="addInfo(this)">
					        <span style="font-size:18px;font-weight:bold;"> 高级选项</span> <span style="font-size:12px;color:gray;"> 高级选项填写关于配送的信息，若勾选此选项，则以下必填。</span>
					        </td>
					      </tr>
					      </table>
					      <div id="tableDetail" style="display:none;">
					      <table width="600" height="170" cellpadding="0">
					          <tr>
					            <td>收货人姓名：</td>
					            <td align="left">
					            	<input type="text" name="name" onblur="name_check()" onfocus="clearText('name_span')"/>
					            	<span id="name_span"> 请填写真实的姓名</span>
					            </td>
					          </tr>
					          <tr>
					            <td>固定电话：</td>
					            <td align="left">
					            	<input type="text" name="phone" onblur="phone_check()"  onfocus="clearText('phone_span')"/>
					            	<span id="phone_span"> 固定电话与移动电话至少填写一项。</span>
					            </td>
					          </tr>
					          <tr>
					            <td>移动电话：</td>
					            <td align="left">
					            	<input type="text" name="mtphone" onblur="mtphone_check()"  onfocus="clearText('mtphone_span')"/>
					            	<span id="mtphone_span"> 固定电话与移动电话至少填写一项。</span>
					            </td>
					          </tr>
					          <tr>
					            <td>收货地址：</td>
					            <td align="left">
					            	<input type="text" name="address" size="40" onblur="address_check()"  onfocus="clearText('address_span')"/>
					            	<span id="address_span">请详细填写真实地址。</span>
					            </td>
					          </tr>
					    </table>
					    </div>
					      <div align="right" style="width:560px; margin-top:20px;">
					        <input type="submit" alt="提交注册" style="background:url('/qlzx/images/btn_register.gif') center no-repeat; width:100px; height:20px;" value=""/>
					      	<input type="hidden" name = "op" value="register"/>
					      </div>
					</form>
                </div>
        	</div>
        </div>
   	</div>
   	<!-- 尾页 -->
    <%@include file="bottom_index.jsp" %>
  </body>
</html>
