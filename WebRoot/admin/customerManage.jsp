<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.util.PageModel"%>
<%@page import="com.sec.model.CustomerInfo"%>
<%@page import="com.sec.dao.CustomerInfoDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户管理</title>
    
   <style type="text/css">
   	  @font-face {/*声明字体*/
	  font-family: 'icomoon';
	  src:  url('/qlzx/admin/fonts/icomoon.eot?1x1j55');
	  src:  url('/qlzx/admin/fonts/icomoon.eot?1x1j55#iefix') format('embedded-opentype'),
	    url('/qlzx/admin/fonts/icomoon.ttf?1x1j55') format('truetype'),
	    url('/qlzx/admin/fonts/icomoon.woff?1x1j55') format('woff'),
	    url('/qlzx/admin/fonts/icomoon.svg?1x1j55#icomoon') format('svg');
	  font-weight: normal;
	  font-style: normal;
	}
	 #titlebar_div{
		background:url('/qlzx/images/titlebar_div_bg.jpg') repeat-x bottom;
		height: 33px;
	 }
	 .input_txt{
	 	background: url('/qlzx/images/search_ico.gif') no-repeat;
	 }
	 .input_noTxt
	 {
	 	background: none;
	 }
	 .input_sub{
	 	background: url('/qlzx/images/btn.png') no-repeat; width:61px; border: 0px; height: 22px;
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
	 	font-family: 'icomoon';
	 	font-style: normal;
	 }
	</style>
    <script type="text/javascript" src="/qlzx/common/js/peisong.js" charset="gbk"></script>
    <%
      	//接受一个分页模型对象
      	PageModel<CustomerInfo> pm = (PageModel<CustomerInfo>)request.getAttribute("customers");
      	pm = pm == null?new PageModel<CustomerInfo>():pm;
      	ArrayList<CustomerInfo> customers = pm.getData();
      	String selectText = (String)request.getAttribute("selectText");
      	selectText = selectText == null?"":selectText;
     %>
     <script type="text/javascript">
		function filter()
	    {
	    	var obj = document.getElementsByTagName("input");
	    	for ( var i = 0; i < i.length; i++) {
				obj[i].value = obj[i].value.replace(/(^\s*)|(\s*$)/g, "");
			}
		}
		//上一页、下一页、首页、尾页、删除、修改(路径提交)
		function Sumbit_bath(op,pageNo,id)
		{
		 	if(pageNo == <%=pm.getPageNo()%>){
		 		return;
		 	}
			if(pageNo != null)
			{
				document.getElementsByName("pageNo")[0].value = pageNo;
			}
			if(id != null)
			{
				document.getElementsByName("id")[0].value = id;
			}
			document.getElementsByName("op")[0].value = op;
			document.getElementsByName("text")[0].value = "<%=selectText%>";	
			
			document.form1.submit();
		}
		
		//前往首页
		function toTopPage(){
			var pageNo = <%=pm.getIndexPageNo()%>;
			var op = null;
			var text = "<%=selectText%>";
			if(text.length<=0 || text.replace(/\s/g,"").length==0)
			{
				op = "queryAll";
			}
			else
			{
				op = "query";
			}
			Sumbit_bath(op,pageNo,null);
		}
		//上一页
		function toPriviousPage(){
			var pageNo = <%=pm.getPreviousPageNo()%>;
			var op = null;
			var text = "<%=selectText%>";
			if(text.length<=0 ||text.replace(/\s/g,"").length==0)
			{
				op = "queryAll";
			}
			else
			{
				op = "query";
			}
			Sumbit_bath(op,pageNo,null);
		}
		//下一页
		function toNextPage(){
			<%-- request.setAttribute("text", text); --%>
			var pageNo = <%=pm.getNextPageNo()%>;
			var op = null;
			var text = "<%=selectText%>";
			
			if(text.length<=0 || text.replace(/\s/g,"").length==0)
			{
				op = "queryAll";
			}
			else
			{
				op = "query";
			}
			Sumbit_bath(op,pageNo,null);
		}
		//尾页
		function toLastPage(){
			var pageNo = <%=pm.getLastPageNo()%>;
			var op = null;
			var text = "<%=selectText%>";
			
			if(text.length<=0 || text.replace(/\s/g,"").length==0)
			{
				op = "queryAll";
			}
			else
			{
				op = "query";
			}
			Sumbit_bath(op,pageNo,null);
		}
		
		//查询、删除多个(有提交功能)的点击事件
		function toManage(obj){
			var flag1 = false;//标记是否可以进行删除（用户是否有选择编号删除）
			//删除多个
			if(obj.id == "removeMore"){
				var chkAll = document.getElementById("chkAll");
				var flag = chkAll.checked;
				
				if(!flag)
				{
					alert("请选择编号进行删除");
				}
				else
				{
					var r = confirm("确定是否删除,本次删除可以会删除到订单信息");
					if(r)
					{
						flag1 = true;//可以进行提交
						Sumbit_bath("removeMore",null,null);
					}
				}
				document.form1.onsubmit = function(){return flag1;};	
			}else{
				//判断查询是否为空（为空）
				var SelectText = document.getElementsByName("text")[0].value;
				if(SelectText.length <= 0 || SelectText.replace(/\s/g,"").length==0)
				{
					//查询所有
					document.form1.action = "/qlzx/servlet/CustomerManage?op=queryAll";
				}
				else
				{
					//去除空格
					filter();
					//查询部分
					document.form1.action = "/qlzx/servlet/CustomerManage?op=query";
				}		
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
		
		//obj代表当前选中的复选框对象
		function chkItems_click(obj)
		{
			//全选不全选复选框
			var chkAll = document.getElementById("chkAll");
			//表格对象
			var dateTable = document.getElementById("dataTable");
			
			if(obj != null && chkAll != null)
			{
				if(obj.checked)
				{
					chkAll.checked = true;
					return;
				}
				var items = dateTable.getElementsByTagName("input");
				if(items != null)
				{
					for(var i = 0 ; i < items.length ; i++)
					{
						if("chkItems"== items[i].name && items[i].checked)
						{
							chkAll.checked = true;
							return;
						}
					}
					chkAll.checked = false;
				}
			}
		}
		/**
		*跳转到修改页面
		*/
		function updateInfo(num)
		{
			Sumbit_bath("toupdate",null,num);
		}
		
	 	function tips()
		{
			alert("无法修改客户信息，需要客户自己填写信息！！!");
		}
		
		/**
		*删除单个
		*/
		function deleteInfo(num)
		{
			var r = confirm("确定是否删除");
			if(r)
			{
				Sumbit_bath("remove",null,num);
			}
		}
		
	</script>
  </head>
  
  <body>
  	<form name="form1" method="post" action="/qlzx/servlet/CustomerManage">
  	<div id="titlebar_div">
	  	<img src="/qlzx/images/doc_red.gif" align="bottom"/>客户信息管理
	  <div style="float:right;">
	  <input type="text" name="text" style="font-family: 'icomoon'; background-color: white;" size="10" value="<%=selectText%>" placeholder=""/>
	  <input type="submit" onclick="toManage(this)" class="input_sub" id = "query" value="查询"/>
	  <input type="submit" onclick="toManage(this)" class="input_sub"  id = "removeMore"value="删除"/>
	  </div>
	</div>
    <table id="dataTable" border="1px" cellspacing="0px" cellpadding="5px" width="100%" >	
    	<tr style="background-image: url('/qlzx/images/menubar.png');">
    		<th><input type="checkbox" id="chkAll" onclick="chkAll_click();"/> </th>
    		<th>客户编号</th>
    		<th>客户账户/邮箱</th>
    		<th>注册时间</th>
    		<th>收货人姓名</th>
            <th>固定电话</th>
            <th>移动电话</th>
            <th>收货地址</th>
    		<th>操作</th>
    	</tr>
    		 <%
    			for(CustomerInfo customer : customers){%>
    				<tr>
    				<th><input type='checkbox' onclick="chkItems_click(this)" name ='chkItems' value=<%=customer.getId()%>></th>
    				<td><%--客户编号--%><%=customer.getId()%></td>
    				<td><%--客户账户/邮箱--%><%=customer.getEmail()%></td>
    				<td><%--注册时间--%><%=DateTimeUtil.ConvertDate(customer.getRegisterTime())%></td>
    				<%
    					/* System.out.print(customer.getCustomerDatail().getTelphone()+"  ");
    					System.out.print(customer.getCustomerDatail().getMobileTelphone()+"  ");
    					System.out.print(customer.getCustomerDatail().getCustomerId()+"  ");
    					System.out.print(customer.getCustomerDatail().getName() == null);
    					System.out.print(" "+customer.getCustomerDatail().getCustomerId());
    					System.out.print(customer.getCustomerDatail().getName() == null && customer.getCustomerDatail().getCustomerId() == 0);
    					System.out.println();
    					 */
    					String phone = customer.getCustomerDatail().getTelphone();
    					try{
    						phone = phone.equals("null")?"——":phone;
    					}catch(Exception e){
    						phone = "——";
    					}
    					
    					String mtphone = customer.getCustomerDatail().getMobileTelphone();
    					try{
    						mtphone = mtphone.equals("null")?"——":mtphone;
    					}catch(Exception e){
    						mtphone = "——";
    					}
    					if(customer.getCustomerDatail().getName() == null && customer.getCustomerDatail().getCustomerId() == 0){ %>
    					<th><%--收货人姓名--%>——</th>
	                    <th><%--固定电话--%>——</th>
	                    <th><%--移动电话--%>——</th>
	                    <th><%--收货地址--%>——</th>
	                	<th>
    						<a href='javascript:tips()'>修改</a>&nbsp;
    						<a href='javascript:deleteInfo(<%=customer.getId()%>)'><i class="fontStyle" style="color: gray;"></i>删除</a></th>
    				<%}else{%>
	    					<th><%--收货人姓名--%><%=customer.getCustomerDatail().getName() %></th>
	                    	<th><%--固定电话--%><%=phone %></th>
	                    	<th><%--移动电话--%><%=mtphone %></th>
	                   		<th><%--收货地址--%><%=customer.getCustomerDatail().getAddress() %></th>
	                	<th>
    						<a href='javascript:updateInfo(<%=customer.getId()%>)'>修改</a>&nbsp;
    						<a href='javascript:deleteInfo(<%=customer.getId()%>)'>
    						<i class="fontStyle" style="color: gray;"></i>删除</a>
    					</th>
                    	<%}%>
    					
    				</tr>
    			<%}%>
    		<tr>
    			<th colspan='9'>
    			共找到<%=pm.getTotalRecords()%>条记录第<%=pm.getPageNo()%>/<%=pm.getTotalpages()%>
    			页&nbsp;<a href='javascript:toTopPage()'>首页</a>&nbsp;
    			<a href='javascript:toPriviousPage()'>上一页</a>&nbsp;
    			<a href='javascript:toNextPage()'>下一页</a>&nbsp;
    			<a href='javascript:toLastPage()'>尾页</a>
    			<input type="hidden" name="id" value="0"/>
    			<input type="hidden" name="op" value="queryAll"/>
    			<input type="hidden" name="pageNo" value = "1"/>
    			<input type="hidden" name="pageSize" value = "4"/>
    			</th>
    		</tr>
    </table>
    </form>
  </body>
</html>
