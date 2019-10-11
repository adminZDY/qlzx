<%@page import="com.sec.model.GoodsType"%>
<%@page import="com.sec.model.GoodsInfo"%>
<%@page import="com.sec.dao.GoodsInfoDao"%>
<%@page import="com.sec.model.SalesGoods"%>
<%@page import="com.sec.util.PageModel"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	int j = 0;
	GoodsInfoDao dao = new GoodsInfoDao();
	ArrayList<SalesGoods> salesList = (ArrayList<SalesGoods>) request.getAttribute("allsales");
	PageModel<GoodsInfo> pm = (PageModel<GoodsInfo>) request.getAttribute("allgoods");
	ArrayList<GoodsInfo> list = pm.getData();
	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <title>显示类型的商品</title>

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
  	 margin-right:0px;
  	 float:right;
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
		width:900px; 
		margin-top:10px;
	}
	.div_2{
		margin-top: 10px;
		border-bottom:1px dashed #000; 
		float:left; 
		height: 180px ; 
		width:100%;
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
	<%//商品销售排行图片
		for(int i = 1;i<10;i++)
		{%>
			/*  在.paihang_div里面包含的.top_num有一个标签<h5>设置背景图片位置    */
			.paihang_div .top_num:nth-child(<%=(i+1)%>) h5{
  			background-position: 0 <%=-(i*26)%>px;
  			}
		<%}%>
  </style>
  <script type="text/javascript">
  		var typeid = <%=request.getAttribute("typeid")%>
  		var pageSize = <%=pm.getPageSize()%>
		var goods = "GoodsManage?typeid="+typeid+"&pageSize="+pageSize+"&pageNo=";
		
	//前往首页
	function toTopPage(){
		if(<%=pm.getPageNo()%> == 1){
			alert("当前已在首页");
			return;
		}
		var str =goods+"<%=pm.getIndexPageNo()%>";
		
		window.location = str;
	}
	//上一页
	function toPriviousPage(){
		if(<%=pm.getPageNo()%> == 1){
			alert("当前已在首页");
			return;
		}
		var str =goods+"<%=pm.getPreviousPageNo()%>";
		
		window.location = str;
		
	}
	//下一页
	function toNextPage(){
		if(<%=pm.getPageNo()%> == <%=pm.getTotalpages()%>){
			alert("当前已在尾页");
			return;
		}
		var str = goods+"<%=pm.getNextPageNo()%>";
		
		window.location = str;
	}
	//尾页
	function toLastPage(){
		if(<%=pm.getPageNo()%> == <%=pm.getTotalpages()%>){
			alert("当前已在尾页");
			return;
		}
		var str = goods+"<%=pm.getLastPageNo()%>";
	
		window.location = str;
	} 
  </script>
  <body>  
   <!-- 首页 -->
    <%@include file="top_index.jsp" %> 
    	<!--  -->   
    	<div style="width: 1300px;float: left;">
  		<div style="margin-left:50px;">
    	<img src="/qlzx/images/site_ico.gif">您现在所在的位置，<a href="/qlzx/index.jsp">网站首页</a>&gt;分类商品
    	</div>
    	<!-- 商品销售排行 -->
    	<div class= "left_div">
    		<div class="best_div"><img  src="/qlzx/images/best.gif">销售排行</div>
    		<div class="paihang_div">
    		<%
    			for(SalesGoods sales : salesList){
    		%>
    		<div class="top_num" >
    			<h5 >
    			欧</h5>
    			<!-- 商品名称 -->
    			<span id="sp_left"><%=sales.getGoodsName()%></span>
    			<!-- 商品销售数量 -->
    			<span id="sp_right"><%=sales.getQuantity()%>件</span><br/>
    		</div>
    			<%}%>
    		</div>
    	</div>
    	<!-- 商品显示 -->
    	<div class="div_1" >
    		<%
    		for(GoodsInfo goodsinfo : list){
    			j++;
    			if(j % 2 != 0){		
    		%>
    		<div class="div_2" >
    			<%}%>
    
    		<a class="a" title="<%=goodsinfo.getGoodsName()%>" href="GoodsManage?op=query&goodsid=<%=goodsinfo.getGoodsId()%>">
    		<div class="div_3" >
    			<!-- 商品图片 -->
    			<div class="recommend_nav">
    				<img src='/qlzx/product/<%=goodsinfo.getPhoto()%>' align="top" width="140" height="140"/>
    			</div>
    			<%
    			//判断商品是否有折扣
    			if(goodsinfo.getDiscount() != 10.0f){ 
    			%>
    			<div style="float:left;">
    			<!-- 商品折扣 -->
    			<div class="discount"><%=goodsinfo.getDiscount()%>折</div><br/><br/>
    			
    				<!-- 商品名称 -->
    				<div class="product_title"><%=goodsinfo.getGoodsName()%></div>
    			
    			<!-- 原价 -->
    			<div class="old_price">¥<%=goodsinfo.getPrice()%></div>
    			<!-- 折扣价  -->
    			<div class="new_price">¥<%= goodsinfo.getDiscountPrice()%></div>
    			</div>
    			<%}else{%>
    			<!-- 无折扣 -->
    			<div style="float:left;">
    				<!-- 商品名称 -->
    				<div class="product_title"><%=goodsinfo.getGoodsName()%></div>
    			
    			<!-- 原价 -->
    			<div class="new_price">¥<%=goodsinfo.getPrice()%></div>
    			</div>
    			<%}%> 
    		</div></a>
    			<%
    			if(j % 2 == 0){
    			%>
    				</div>
    			<%}%>
    		<%} %> 
    	</div> 
    	<div style="float:right; width:900px; text-align: center;">
    	共有<%=pm.getTotalRecords() %>件商品 &nbsp;第<%=pm.getPageNo()%>/<%=pm.getTotalpages() %>页&nbsp;
    	<a href="javascript:toTopPage()">首页</a>&nbsp;
    	<a href="javascript:toPriviousPage()">上一页</a>&nbsp;
    	<a href="javascript:toNextPage()">下一页</a>&nbsp;
    	<a href="javascript:toLastPage()">尾页</a>
    	</div>
    	</div>
    <!-- 尾页 -->
   	 <%@include file="bottom_index.jsp" %>
  </body>
  
</html>
