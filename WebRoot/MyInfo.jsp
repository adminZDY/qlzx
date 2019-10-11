<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'CustomerInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/qlzx/css/bootstrap.min.css">  
	<script src="/qlzx/js/jquery.min.js"></script>
	<script src="/qlzx/js/bootstrap.min.js"></script>
	
  </head>
  
  <body>
    <div class="container">
	<div class="row">
		<div class="span6">
			<div class="accordion" id="accordion-666088">
				<div class="accordion-group">
					<div class="accordion-heading">
						 <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-666088" href="#accordion-element-444788">个人信息</a>
					</div>
					<div id="accordion-element-444788" class="accordion-body collapse">
						<div class="accordion-inner">
							功能块...
						</div>
					</div>
				</div>
				<div class="accordion-group">
					<div class="accordion-heading">
						 <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-666088" href="#accordion-element-923446">选项卡 #2</a>
					</div>
					<div id="accordion-element-923446" class="accordion-body in collapse">
						<div class="accordion-inner">
							功能块...
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
  </body>
</html>
