<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.model.CustomerInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改客户信息</title>
    <%
    	CustomerInfo customer = (CustomerInfo)request.getAttribute("customer");
    	String telphone = "";String mtphone = "";
    	System.out.println(customer.getCustomerDatail() == null);
    	System.out.println(customer.getCustomerDatail().getTelphone() == null);
    	System.out.println(customer.getCustomerDatail().getMobileTelphone() == null);
    	System.out.println(customer.getCustomerDatail().getName());
     	if(customer == null)
     	{
     		response.sendRedirect("/admin/servlet/CutomerManage?op=queryAll");
     	}
     	else{
     		if(customer.getCustomerDatail().getTelphone() != null)
     		{
     			telphone = customer.getCustomerDatail().getTelphone();
     		}
     		if(customer.getCustomerDatail().getMobileTelphone() != null){
     			mtphone = customer.getCustomerDatail().getMobileTelphone();
     		}
	    }
     %>
     <script type="text/javascript" src="/qlzx/js/peisong.js" charset="gbk"></script>
  </head>
  
  <body>
     <div style="background:url('/qlzx/admin/images/titlebar_div_bg.jpg') repeat-x bottom; height: 25;"><img src="/qlzx/images/doc_red.gif">修改客户信息</div>
	  <form action="/qlzx/servlet/CustomerManage" method="post" onsubmit="return ifOk()">
	    <table style="border:1px solid #000; width:100%">
	    	<tr>
	    		<td>客户编号</td>
	    		<td><span style="border: 1px solid #000;">&nbsp;<%=customer.getId()%>&nbsp;</span></td>
	    	</tr>
	    	<tr>
	    		<td>客户账户/邮箱：</td>
	    		<td><input type="text" readonly unselectable = "on" value="<%=customer.getEmail()%>"/></td>
	    	</tr>
	    	<tr>
	    		<td>注册时间：</td>
	    		<td><%=DateTimeUtil.ConvertDate(customer.getRegisterTime())%></td>
	    	</tr>
			<tr>
				<td>收货人姓名：</td>
				<td align="left"><input type="text" name="name"
					onblur="name_check()" onfocus="clearText('name_span')" 
					 value="<%=customer.getCustomerDatail().getName()%>"/> <span
					id="name_span"> 请填写真实的姓名</span></td>
			</tr>
			<tr>
				<td>固定电话：</td>
				<td align="left"><input type="text" name="phone"
					onblur="phone_check()" onfocus="clearText('phone_span')" 
					value="<%=telphone%>"/> <span
					id="phone_span"> 固定电话与移动电话至少填写一项。</span></td>
			</tr>
			<tr>
				<td>移动电话：</td>
				<td align="left"><input type="text" name="mtphone"
					onblur="mtphone_check()" onfocus="clearText('mtphone_span')"
					value="<%=mtphone%>"/> <span
					id="mtphone_span"> 固定电话与移动电话至少填写一项。</span></td>
			</tr>
			<tr>
				<td>收货地址：</td>
				<td align="left"><input type="text" name="address" size="40"
					onblur="address_check()" onfocus="clearText('address_span')"
					value="<%=customer.getCustomerDatail().getAddress()%>" /> <span
					id="address_span">请详细填写真实地址。</span></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="提交表单" class="btn" /> <input
					type="reset" value="重置表单" class="btn" /> 
					<input type="hidden" value="<%=customer.getId()%>" name="id"/>
					<input type="hidden" value="updateDetailInfo" name="op"/>
					</td>
			</tr>
		</table>
	   </form>
  </body>
</html>
