<%@page import="com.sec.model.GoodsInfo"%>
<%@page import="com.sec.util.PageModel"%>
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
	
   	if("null".equals(text) || text == null){
   		text = "";
   	}
   	
	PageModel<GoodsInfo> pm = (PageModel<GoodsInfo>) request.getAttribute("allgoods");
	ArrayList<GoodsInfo> list = pm.getData(); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>商品管理</title>
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
		background:url(../images/titlebar_div_bg.jpg) repeat-x bottom;
		height: 33px;
	 }
	 .input_txt{
	 	background: url('../images/search_ico.gif') no-repeat;
	 }
	 .input_sub{
	 	background: url('../images/btn.png') no-repeat; width:61px; border: 0px; height: 22px;
	 }
	 /*字体图标引用样式*/
	 .fontStyle
	 {
	 	font-family: 'icomoon';
	 	font-style: normal;
	 }
</style>

<script type="text/javascript">
	var pageSize = <%=pm.getPageSize()%>
	
	var basePath = "GoodsManage?op=viewGoods&pageSize="+pageSize+"&pageNo=";
	var baseop="/qlzx/servlet/GoodsManage?op=queryAll";
		/**
	*页面跳转（上下首尾页）
	*/
	function toPage(pageNo)
	{
		document.getElementsByName("pageNo")[0].value = pageNo;
		var selectText = "<%=text%>";
		//请求要查询所有
		if(selectText == null || selectText == "" || selectText.replace(/\s/g,"").length==0) 
		{
			document.getElementsByName("op")[0].value = "viewGoods";
		}
		else{
			document.getElementsByName("op")[0].value = "viewGoods";
		}
		document.getElementsByName("text")[0].value = selectText;
		document.form1.submit();
	}	
	
	//查询、添加、删除公告的点击事件
	function toManage(obj){
		
		if(obj.id == "removeMore"){
			var r = confirm("确定是否删除");
			if(r)
			{
				document.getElementsByName("op")[0].value = "removeMore";
			}
		}else{
			if(obj.id == "viewGoods")
			{
				document.getElementsByName("op")[0].value = "viewGoods";
			 	var text = document.getElementsByName("text")[0];
			 	text.value = text.value.replace(/\s/g,"");
			}else{
				document.getElementsByName("op")[0].value = obj.id;
			}
			document.form1.submit();
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
	
	//选择表格中的某条公告
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
			window.location.href = "GoodsManage?op=remove&id="+num;
		}
	}
	  
</script>
  </head>
  
  <body>
    <form name="form1" method="post" action = "/qlzx/servlet/GoodsManage">
  <div id="titlebar_div">
  	<img src="../images/doc_red.gif" align="bottom"/>商品信息管理
  <div style="float:right;">
  <input type="text" name="text" style="font-family: 'icomoon'; background-color: white;" value="<%=text%>" placeholder="" size="10"/>
  <input type="submit" onclick="toManage(this)" class="input_sub" id="viewGoods" value="查询"/>
  <input type="submit" onclick="toManage(this)" class="input_sub"  id = "toAdd" value="添加商品"/>
  <input type="submit" onclick="toManage(this)" class="input_sub"  id = "removeMore"value="删除商品"/>
  </div>
  </div>
    <table id="dataTable" border="1px" cellspacing="0px" cellpadding="5px" width="100%" >
    	<tr style="background-image: url('../images/menubar.png');">
    		<th><input type="checkbox" id="chkAll" onclick="chkAll_click();"/> </th>
    		<th>商品编号</th>
    		<th>商品类别</th>
    		<th>商品名称</th>
    		<th>商品价格</th>
    		<th>商品折扣</th>
    		<th>商品图片</th>
    		<th>是否图片</th>
    		<th>是否推荐</th>
    		<th>商品状态</th>
    		<th>操作</th>
    	</tr>
    	<%for(GoodsInfo goodsinfo : list){%>
    	<tr>
    		<th><input type='checkbox' onclick="chkItems_click(this)" name ='chkItems' value=<%=goodsinfo.getGoodsId()%>></th>
    		<td><%=goodsinfo.getGoodsId()%></td>
    		<td><%=goodsinfo.getGoodstype().getTypeName()%></td>
    		<td><%=goodsinfo.getGoodsName()%></td>
    		<td><%=goodsinfo.getPrice() %></td>
    		<td><%=goodsinfo.getDiscount() == 10.0f ? "-":goodsinfo.getDiscount() %></td>
    		<td><%=goodsinfo.getPhoto() %></td>
    		<td><%if(goodsinfo.getIsNew() == 0 ){
    		%>
    			<span style=" font-weight:900;" >
    			<i style=" color: red; " class="fontStyle"></i>
    			新品
    			</span>
    		<%}else{%> 
    			<i style=" font-weight:900; color: gray;"class="fontStyle"></i>
    			<span style=" font-weight:900; ">
    			非新品
    			</span> 
    		<%}%></td>
    		<td><%if(goodsinfo.getIsRecommend() == 0 ){
    		%>
    			<span style=" font-weight:900;" >
    			<i style="color: red; " class="fontStyle"></i>
    			推荐
    			</span>
    		<%}else{%> 
    			<i style=" font-weight:900; color: gray;" class="fontStyle"></i>
    			<span style=" font-weight:900; ">
    			非推荐
    			</span> 
    		<%}%></td>
    		<td><%if(goodsinfo.getStatus() == 0){
    		%>
    			<span style=" font-weight:900;" ><i style="color: green;" class="fontStyle"></i>
    			上架
    			</span>
    		<%}else{%> 
    			<i style=" font-weight:900;color: red;"class="fontStyle"></i>
    			<span style=" font-weight:900; text-decoration:line-through;">
    			下架
    			</span> 
    		<%}%></td>
    		<th ><a href='GoodsManage?op=query&toup=1&goodsid=<%=goodsinfo.getGoodsId()%>'>修改</a>&nbsp;
    		<a href='javascript:fun(<%=goodsinfo.getGoodsId()%>)'><i class="fontStyle" style="color: gray;"></i>
    		删除</a>
    		</th>
    	</tr>
    	<%}%>
    			
    	<tr>
    		<th colspan='11'>
    		共找到<%=pm.getTotalRecords()%>条记录第<%=pm.getPageNo() %>/<%=pm.getTotalpages()%>
    		页&nbsp;<a href='javascript:toPage(<%=pm.getIndexPageNo()%>)'>首页</a>&nbsp;
    		<a href='javascript:toPage(<%=pm.getPreviousPageNo()%>)'>上一页</a>&nbsp;
    		<a href='javascript:toPage(<%=pm.getNextPageNo()%>)'>下一页</a>&nbsp;
    		<a href='javascript:toPage(<%=pm.getLastPageNo()%>)'>尾页</a>
    		<input type="hidden" name = "op" value="viewGoods"/>
    		<input type="hidden" name = "pageNo" value="1">
			<input type="hidden" name = "pageSize" value="7">
			<input type="hidden" name = "id" value="7">
    		</th>
    	</tr>
    </table>
     </form>
  </body>
</html>
