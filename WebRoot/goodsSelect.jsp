<%@page import="com.sec.util.PageModel"%>
<%@page import="com.sec.model.GoodsInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	int j = 0;
	String text = request.getParameter("txt");
	
   	if("null".equals(text) || text == null || "".equals(text)){
   		text = "";
   	}
  
	PageModel<GoodsInfo> pm = (PageModel<GoodsInfo>) request.getAttribute("allgoods");
	ArrayList<GoodsInfo> list = pm.getData();
	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <title>搜索商品信息</title>

  </head>
  <style type="text/css">
  	.left_div{
  		float:left;
  		margin-top:10px;
  		margin-left:60px;
		width:300px;
		position: relative;
		border:1px solid #000;
	}

  	#sp_left{
 	margin-left: 30px;
  	position:absolute; 
  	margin-top:5px;
  	}
  	#sp_right{
  	 margin-left: 270px;
  	 position:absolute;
  	 margin-top:5px;
  	}
  	
	.best_div{
		border-bottom: 1px dashed #000; 
		font-size: 30px;
	}
	.recommend
	{
		width:950px;
		height:400px;
		float:none;
	}
	
	.recommend_nav
	{
		float:left;
	}
	
	.recommend_Info
	{
		border-top:1px solid;
		width:950px;
		height:338px;
		float:left;
	}
	.recommend_Info div
	{
		float:left;
	}
	/*商品整个信息（图片、价格、标题）*/
	.recommend_show
	{
		width:300px;
		
	}
	/*商品图片*/
	.recommend_show_img
	{
		margin-left:10px;
	}
	/*商品价格信息*/
	.recommend_show_Info
	{
		float:left;
	}
	/*价格区*/
	.price
	{
		height:30px;
		width:70px;
		float:left;
		margin-left:5px;
		padding-top:8px;
		text-align:center;
	}
	.old_price
	{
		float: left;
		text-decoration:line-through;
		font-weight:700;
		color:gray;
	}
	.new_price
	{
		float: left;
		text-decoration:none;
		color:#F00;
		font-weight:700;
		margin-left:20px;
	}
	.product_title
	{
		color:#000;
		font-weight:bold;
		font-size:14px;
		width:148px;
		height:35px;
		word-break: break-all;
		word-wrap:break-word;
		overflow:hidden;
	}
	/*折扣样式*/
	.discount
	{
		float:left;
		color:#F30;
		font-family:"Georgia";
		font-size:20px;
	}
	.a{
		text-decoration: none;
		color:white;
	}
	.a:HOVER
	{
		
	}
	.div_1{
		
		float: right;  
		width:1200px; 
		margin-top:10px;
	}
	.div_2{
		border-bottom:1px dashed #000; 
		float:left; 
		height: 160px ; 
		margin-top: 10px;
	}
	
	.div_3{
		float: left; 
		margin-right: 125px;
	}
	.paihang_div .top_num h5{
  		left:0px;
  		float:left;
		position:absolute; 
		background: url('../images/paihang.png') no-repeat; 
		width:29px;
		height:26px; 
		text-indent:-999em; 
		margin-top:0px;
	}
	.paihang_div .top_num
	{
		border-bottom: 1px dashed #000;
		
	}
	.paihang_div .top_num
	{
		float:left;
		width:300px;
		height:30px;
		margin-left:0px;
		margin-top:10px;
	}
	
  </style>
  <script type="text/javascript">
  	function load_text()	
	{
	
		var text = document.getElementsByName("txt")[0];
		text.value = "<%=text%>";
		
		var content = document.getElementsByName("content");//.getElementsByTagName("span").length;
		
		for(var i = 0 ; i < content.length ; i++){
			//原文本
			var contents = content[i].innerHTML;
			
			//搜索值
			var value = text.value;
			//搜索值去前后空格
         	var str = value.replace(/(^\s*)|(\s*$)/g, "");
         	//搜索值为空
         	if(str == ""){
         		//终断
         		break;
         	}
         	//搜索不区分大小写
         	var reg = new RegExp(str,"gi");
         	//原文本匹配到的值
         	var s = contents.match(reg);
         	//替换高亮
         	var replceStr = '<span style="color:red;font-weight:700;">' +s + '</span>';
			content[i].innerHTML = contents.replace(reg,replceStr);
		}
	}
	/**
	*页面跳转（上下首尾页）
	*/
	function toPage(pageNo)
	{	
		if(<%=pm.getPageNo()%> == 1 && pageNo == 1 ){
			alert("当前已在首页");
			return;
		}
		else if(<%=pm.getPageNo()%> == <%=pm.getTotalpages()%> && pageNo == 3 ){
			alert("当前已在尾页");
			return;
		}
		var str = "/qlzx/servlet/GoodsManage?op=addvague&pageNo="+pageNo;
		var selectText = "<%=text%>";
		var text = selectText.replace(/(^\s*)|(\s*$)/g, "");
		document.getElementsByName("txt")[0].value = text;
		
		window.location.href = str+"&txt="+text;
	}
  </script>
  
  <body onload="load_text()">  
   <!-- 首页 -->
    <%@include file="top_index.jsp" %> 
    	<!--  -->   
    	<div style="width: 1300px;float: left;">
  		<div style="margin-left:50px;">
    	<img src="../images/site_ico.gif">您现在所在的位置，<a href="../index.jsp">网站首页</a>&gt;分类商品
    	</div>
    	
    	<!-- 商品显示 -->
    	<div class="div_1" >
    		<%
    		for(GoodsInfo goodsinfo : list){
    			j++;
    			if(j == 1){		
    		%>
    		<div class="div_2" >
    			<%}%>
    
    		<a class="a" title="<%=goodsinfo.getGoodsName()%>" href="GoodsManage?op=query&goodsid=<%=goodsinfo.getGoodsId()%>">
    		<div class="div_3" >
    			<!-- 商品图片 -->
    			<div class="recommend_nav">
    				<img src='/qlzx/product/<%=goodsinfo.getPhoto()%>' align="top" width="120" height="120"/>
    			</div>
    			<div style="float: left;">
    			<%
    			//判断商品是否有折扣
    			if(goodsinfo.getDiscount() != 10.0f){ 
    			%>
    			
    			<!-- 商品折扣 -->
    			<div class="discount"><%=goodsinfo.getDiscount()%>折</div><br/><br/>
    			<% }%>
  
    			<!-- 商品名称 -->
    			<div class="product_title" name="content"><%=goodsinfo.getGoodsName()%></div>
    			
    			<!-- 原价 -->
    			<div class="old_price">¥<%=goodsinfo.getPrice()%></div>
    			<!-- 折扣价  -->
    			<div class="new_price">¥<%=goodsinfo.getDiscountPrice()%></div>
    			</div >
    		</div></a>
    			<%
    			if(j  == 3){j=0;
    			%>
    				</div>
    			<%}}%> 
    	</div> 
    	<div style="float:right; width:900px; text-align: center;">
    	共有<%=pm.getTotalRecords() %>件商品 &nbsp;第<%=pm.getPageNo()%>/<%=pm.getTotalpages() %>页&nbsp;
    	<a href="javascript:toPage(<%=pm.getIndexPageNo()%>)">首页</a>&nbsp;
    	<a href="javascript:toPage(<%=pm.getPreviousPageNo()%>)">上一页</a>&nbsp;
    	<a href="javascript:toPage(<%=pm.getNextPageNo()%>)">下一页</a>&nbsp;
    	<a href="javascript:toPage(<%=pm.getLastPageNo()%>)">尾页</a>
    	</div>
    	</div>
    <!-- 尾页 -->
   	 <%@include file="bottom_index.jsp" %>
  </body>
  
</html>