<%@page import="com.sec.model.CustomerDatailInfo"%>
<%@page import="com.sec.model.CustomerInfo"%>
<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.model.OrderGoodsInfo"%>
<%@page import="com.sec.util.PageModel"%>
<%@page import="com.sec.model.OrderInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	
	response.setContentType("text/html;charset=utf-8");
	request.setCharacterEncoding("utf-8");
	
	String text = request.getParameter("text");
	
   	if("null".equals(text)){
   		text = "";
   	}
   	
    PageModel<OrderGoodsInfo> pamod = (PageModel<OrderGoodsInfo>) request.getAttribute("allBullein");
    			
    ArrayList<OrderGoodsInfo> Order = pamod.getData();
    
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>客户订单信息管理</title>
    
	<%!
		public String returnUPURL(int id)
		{
			String bath = "OrderManage?op=update&id=";
			return bath+id;
		}
	 %>
<style type="text/css">
	 #titlebar_div{
		background:url(/qlzx/images/titlebar_div_bg.jpg) repeat-x bottom;
		height: 33px;
	 }
	 .input_txt{
	 	background: url('/qlzx/images/search_ico.gif') no-repeat;
	 }
	 .input_sub{
	 	background: url('/qlzx/images/btn.png') no-repeat; width:61px; border: 0px; height: 22px;
	 }
		@font-face {
  			font-family: 'icomoon';
 			src:  url('/qlzx/admin/fonts/icomoon.eot?k7wgzl');
  			src:  url('/qlzx/admin/fonts/icomoon.eot?k7wgzl#iefix') format('embedded-opentype'),
    		url('/qlzx/admin/fonts/icomoon.ttf?k7wgzl') format('truetype'),
   			url('/qlzx/admin/fonts/icomoon.woff?k7wgzl') format('woff'),
    		url('/qlzx/admin/fonts/icomoon.svg?k7wgzl#icomoon') format('svg');
  			font-weight: normal;
  			font-style: normal;
		}
	 .linear{
  		  background: -webkit-linear-gradient(left, #FF66CC , #6699FF); /* Safari 5.1 - 6.0 */
  		  background: -o-linear-gradient(right, #FF66CC, #6699FF); /* Opera 11.1 - 12.0 */
   		  background: -moz-linear-gradient(right, #FF66CC, #6699FF); /* Firefox 3.6 - 15 */
    	  background: linear-gradient(to right, #FF66CC , #6699FF); /* 标准的语法（必须放在最后） */
		}
		/*字体图标引用样式*/
	 .fontStyle
	 {
	 	font-family:'icomoon';
	 	font-style: normal;
	 	font-size:25px;
	 }
</style>
	 
<script type="text/javascript">
	
	var pageSize = <%=pamod.getPageSize()%>
	
	var basePath = "OrderManage?pageSize="+pageSize+"&pageNo=";
	var baseop="OrderManage?op=";
	//前往首页
	function toTopPage(){
	//var text =document.getElementsByName("text")[0].value;
		var text =  "<%=text == "" ? null : text%>";
		var str =basePath+"<%=pamod.getIndexPageNo()%>";
		
		window.location.href = str+"&text="+text;
	}
	//上一页
	function toPriviousPage(){
	//var text =document.getElementsByName("text")[0].value;
		var text =  "<%=text == "" ? null : text%>";
		var str =basePath+"<%=pamod.getPreviousPageNo()%>";
		
		window.location.href = str+"&text="+text;
		
	}
	//下一页
	function toNextPage(){
	//var text =document.getElementsByName("text")[0].value;
		var text = "<%=text == "" ? null : text%>";
		var str = basePath+"<%=pamod.getNextPageNo()%>";
		
		window.location.href = str+"&text="+text;
	}
	//尾页
	function toLastPage(){
		//var text =document.getElementsByName("text")[0].value;
		var text ="<%=text == "" ? null : text%>";
		var str = basePath+"<%=pamod.getLastPageNo()%>";
	
		window.location.href = str+"&text="+text;
	}
	
	//查询、删除公告的点击事件
	function toManage(obj){
		
		if(obj.id == "removeMore"){
			var r = confirm("确定是否删除");
			if(r)
			{
				document.form1.action =baseop+obj.id;
			}
		}else{
			document.form1.action =baseop;
		}
	}
	
	//全选、全不选
	function chkAll_click(){
		var chkAll = document.getElementById("chkAll");
		var dateTable = document.getElementById("dataTable");
	
		if(chkAll != null ){
			var items = dateTable.getElementsByTagName("input");
			if(items != null && chkAll.checked){
				for(var i = 0 ; i < items.length ; i++){
					if("chkItems" == items[i].name){
						items[i].checked = true;
					}
				}
			}else{
				for(var i = 0 ; i < items.length ; i++){
					if("chkItems" == items[i].name){
						items[i].checked = false;
					}
				}
			}
		}
	}
	
	//选择表格中的某条订单
	//obj代表当前选中的复选框对象
	function chkItems_click(obj){
		var chkAll = document.getElementById("chkAll");
		var dateTable = document.getElementById("dataTable");
		
		if(obj != null && chkAll != null){
			if(obj.checked){
				chkAll.checked = true;
				return;
			}
		var items = dateTable.getElementsByTagName("input");
		if(items != null){
			for(var i = 0 ; i < items.length ; i++){
				if("chkItems"== items[i].name && items[i].checked){
					chkAll.checked = true;
					return;
				}
			}
				chkAll.checked=false;
			}
		}
	}
	
	function fun(num)
	{
		var r = confirm("确定是否删除");
		if(r)
		{
			window.location.href = "OrderManage?op=remove&id="+num;
		}
	}
	
	/**
	*文本框获取焦点触发该函数
	*/
	function fcText(obj)
	{
		obj.style="background: none;";
	}
	
	/**
	*文本框失去焦点触发该函数
	*/
	function brText(obj)
	{
		
		if(obj.value.length <= 0){
		
			obj.style="background: url('images/search_ico.gif') no-repeat;";
		}else{
		
			obj.style="background:none;";
		}
	}
	//修改订单状态
	function state(obj,id){
		var url = "OrderManage?op=update&id="+id;
		obj.href=url+"&Status="+obj.name;
	}
	
	function particular(obj,orderid , customerid){
		var url = "OrderManage?op=query&orderid="+orderid;
		obj.href=url+"&customerid="+customerid;
	}
</script>
  </head>
  
  <body>
  <form name="form1" method="post">
  <div id="titlebar_div">
  	<img src="/qlzx/images/doc_red.gif" align="bottom"/>订单信息管理
  <div style="float:right;">
  <input type="text" name="text" style="font-family: 'icomoon'; background-color: white;" value="<%=text%>" placeholder="" size="10"/>
  <input type="submit" onclick="toManage(this)" class="input_sub" value="查询"/>
  <input type="submit" onclick="toManage(this)" class="input_sub"  id = "removeMore"value="删除订单"/>
  </div>
  </div>
    <table id="dataTable" border="1px" cellspacing="0px" cellpadding="5px" width="100%" >
    	<tr style="background-image: url('/qlzx/images/menubar.png');">
    		<th><input type="checkbox" id="chkAll" onclick="chkAll_click();" /> </th>
    		<th>订单编号</th>
    		<th>订单状态</th>
    		<th>下单时间</th>
    		<th>客户账号/邮箱</th>
    		<th>收货人</th>
    		<th>固定电话</th>
    		<th>移动电话</th>
    		<th>操作</th>
    	</tr>
    		 <%
    			for(OrderGoodsInfo order : Order){
    			
    			OrderInfo orinfo = order.getOrderInfo();
    			CustomerInfo customer = orinfo.getCustomer();
    			CustomerDatailInfo cusdata = customer.getCustomerDatail();
    			%>
    				<tr >
    				<th><input type='checkbox' onclick="chkItems_click(this)" name ='chkItems' value=<%=orinfo.getOrderId()%>></th>
    				<td><%=orinfo.getOrderId()%></td>
    				<td>
    				<%-- <%if(orinfo.getStatus() == 1){ %>
    					[0表示未付款][1用户付款成功][2商家已发货][3用户确认收货][4用户评价]
    					<span style="font-weight: 700; text-decoration: underline;">
    					已付款
    					</span><i class="fontStyle" style="color: green;"></i>
    				<%} else{%> --%>
    					<span style='font-weight: 700; '>
    					<%if(orinfo.getStatus() == 0){
    						out.print("<i class='fontStyle' ></i>待支付");
    					 }else if(orinfo.getStatus() == 1){
       					 	out.print("<i class='fontStyle' style='color: red;'></i>待发货");
       					 }else if(orinfo.getStatus() == 2){
       					 	out.print("<i class='fontStyle' style='color: yellow;'></i>待收货");
       					 }else if(orinfo.getStatus() == 3){
       					 	out.print("<i class='fontStyle' style='color: green;'>等评论</i>");
       					 }else if(orinfo.getStatus() == 4){
       					 	out.print("<i class='fontStyle; style='color: green;'></i>交易完成");
       					 }%>
       					 </span> 
    				<%-- <%}%>--%>
    				</td>
    				<td><%=DateTimeUtil.ConvertDate(orinfo.getOrderTime())%></td>
    				<td><%=customer.getEmail()%></td>
    				<td><%=cusdata.getName() %></td>
    				<td><%=cusdata.getTelphone() == null ? "——":cusdata.getTelphone() %></td>
    				<td><%=cusdata.getMobileTelphone()  == null ? "——":cusdata.getMobileTelphone()%></td>
    				<th>
    				&nbsp;
    				<a href='#' onclick="particular(this,'<%=orinfo.getOrderId()%>','<%=orinfo.getCustomer().getId()%>')">详细</a>&nbsp;
    				<%-- <a href='javascript:fun(<%=orinfo.getOrderId()%>)'><i class="fontStyle" style="color: gray;"></i>删除</a> --%>
    				<%if(orinfo.getStatus() == 1){
						//客户付款成功    [0表示未付款][1用户付款成功][2商家已发货][3用户确认收货][4用户评价]
					%>
    					<a href='#' onclick="state(this,'<%=orinfo.getOrderId()%>')" name="<%=2%>">确定发货</a>
    				<%}%>
    				</th>
    				</tr>
    			<%}%>
    			
    		<tr>
    			<th colspan='9'>
    			共找到<%=pamod.getTotalRecords()%>条记录第<%=pamod.getPageNo() %>/<%=pamod.getTotalpages()%>
    			页&nbsp;<a href='javascript:toTopPage()'>首页</a>&nbsp;
    			<a href='javascript:toPriviousPage()'>上一页</a>&nbsp;
    			<a href='javascript:toNextPage()'>下一页</a>&nbsp;
    			<a href='javascript:toLastPage()'>尾页</a>
    			</th>
    		</tr>
    </table>
    </form>
  </body>
</html>
