<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>配送信息</title>
	<script type="text/javascript" src="/qlzx/js/peisong.js" charset="gbk"></script>
    <style>
	    #main_bottom
	    {
	    	margin-left: 0px;
	    }
		.register_table table td
		{
			border-bottom:1px dashed #000;
		}
		.register_table table
		{
			border:0px;
		}
		.btn
		{
			background: url("/qlzx/images/btn.png") no-repeat;
		}
		table td {
			padding: 10px;
		}
		
	</style>
  </head>
  <body>
  	<div class="main">
  		<%@include file="top_index.jsp" %>
  		<%
  		
			if(customer.getEmail() == null)
			{
				//跳转到登录界面
				response.sendRedirect("/qlzx/login_register.jsp");
				return;
			}
			
			
			if(customer.getCustomerDatail().getAddress() != null ){
				response.sendRedirect("/qlzx/updateCustomer.jsp");
				return;
			}
		%>
  		<div style="margin-left: 30px;">
	    	<div class="products_nav">
	       		<img src="/qlzx/images/site_ico.gif"/><span style="font-size: 16px">你所在的位置：<a href="/qlzx/index.jsp">网站首页</a> &gt; 配送填写
	        </span>
	        </div>
	        <div id = "main_bottom">
	        	<!-- 注册 -->
	        	<div class="register" id = "register">
	        		
	                <div class = "register_table" >
	                	<form  method="post" onsubmit="return ifOk()" name="form_login" action="/qlzx/servlet/CustomerManage?op=addDetail">
					      <table  width="1000" height="170" cellpadding="0">
					      	  <tr>
					      	  	<td colspan="3" style="border-bottom: 1px solid #000;">
					      	  		<img src="/qlzx/images/peisong_logo.gif"/>填写配送消息
					      	  	</td>
					      	  </tr>
					          <tr>
					            <td>收货人姓名：</td>
					            <td align="left" colspan="2">
					            	<input type="text" name="name" onblur="name_check()" onfocus="clearText('name_span')"/>
					            	<span id="name_span"> 请填写真实的姓名</span>
					            </td>
					          </tr>
					          <tr>
					            <td>固定电话：</td>
					            <td align="left" colspan="2">
					            	<input type="text" name="phone" onblur="phone_check()"  onfocus="clearText('phone_span')"/>
					            	<span id="phone_span"> 固定电话与移动电话至少填写一项。</span>
					            </td>
					          </tr>
					          <tr>
					            <td>移动电话：</td>
					            <td align="left" colspan="2">
					            	<input type="text" name="mtphone" onblur="mtphone_check()"  onfocus="clearText('mtphone_span')"/>
					            	<span id="mtphone_span"> 固定电话与移动电话至少填写一项。</span>
					            </td>
					          </tr>
					          <tr>
					            <td>收货地址：</td>
					            <td align="left" colspan="2">
					            	<input type="text" name="address" size="40" onblur="address_check()"  onfocus="clearText('address_span')"/>
					            	<span id="address_span">请详细填写真实地址。</span>
					            </td>
					          </tr>
					          <tr>
					          	<td style="border:0px;"></td>
					          	<td colspan="2" style="border:0px;">
					          		<input type="submit" value="提交" class="btn"/>
					          		<input type="reset" value="重置" class="btn"/>
					          		<input type="hidden" value="<%=customer.getId() %>" name="id">
					          	</td>
					          </tr>
					    </table>
	                    </form>
	                </div>
	        	</div>
	        </div>
	    </div>
        <!-- 尾页 -->
    	<%@include file="bottom_index.jsp" %>
   	</div>
  </body>
</html>
