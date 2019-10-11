<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sec.model.UserInfo"%>
<%@page contentType="text/html; charset=utf-8" %>
<%
	UserInfo user = (UserInfo)request.getSession().getAttribute("user");
	String userName = null;
	 String time = null;
	if(user == null)
	{
		//没登录
		//response.sendRedirect("/qlzx/admin/login.jsp");
	}
	else{
		 userName = user.getUserName();
		 
		 SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24 "yyyy-MM-dd HH:mm:ss" 12 "yyyy-MM-dd hh:mm:ss"
		 time = sd.format(new Date(request.getSession().getCreationTime()));
		 //response.sendRedirect("/qlzx/admin/index.jsp");
	}
%>
<html>
	<head>
		<script type="text/javascript" language="javaScript">
			function checkParent()
			{
				var user = "<%=user%>";
				if(user == null || user == "null")
				{
					if(window != top)
					{
						if(window.parent.length > 0)
						{
							window.parent.location.href = "/qlzx/admin/login.jsp";
						}
					}
					else
					{
						window.location.href = "/qlzx/admin/login.jsp";
					}
				}
			}
		</script>
		<link rel="shortcut icon" href ="/qlzx/favicon.ico"/>
		<link rel="bookmark" href = "/qlzx/favicon.ico"/>
		<meta>
	</head>
	<body onload="checkParent()">
	</body>
</html>