<%@page import="com.sec.model.GoodsType"%>
<%@page import="com.sec.dao.GoodsTypeDao"%>
<%@page import="com.sec.model.GoodsInfo"%>
<%@page import="com.sec.dao.GoodsInfoDao"%>
<%@page import="com.sec.util.DateTimeUtil"%>
<%@page import="com.sec.util.PageModel"%>
<%@page import="com.sec.dao.BulletinDao"%>
<%@page import="com.sec.model.BulletinInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>千里之行前台首页</title>
 	<link rel="shortcut icon" href ="/qlzx/favicon.ico"/>
	<link rel="bookmark" href = "/qlzx/favicon.ico"/>
	<script type="text/javascript">
	/*得到*/
	function upContent(obj)
	{
		obj.getElementsByTagName("th")[0].className = "content_title2";
		obj.className = "content_product_type2";
	}
	/*失去*/
	function downContent(obj)
	{
		obj.getElementsByTagName("th")[0].className = "content_title1";
		obj.className = "content_product_type1";
	}
	</script>

	<style type="text/css">
		/*页面版心*/
		#main{
			width: 1366px;
			height: 700px;
			margin-top:10px;
			margin-bottom: 20px;
		}
		/*版心div*/
		#main div
		{
			float: left;
		}
		/*版心左边*/
		#main_left
		{
			margin-left:60px;
			width:300px;
		}
		#main_left a
    	{
    		text-decoration: none;
    		color:#000;
    	}
    	#main_left a:HOVER
    	{
    		color: gray;
    	}
		/*版心右边*/
		#main_right
		{	
			margin-left:15px;
			width:900px;
		}
    	#main_right a 
    	{
    		text-decoration: none;
    		color: white;
    	}
    	#main_right a:HOVER
    	{
    		color: gray;
    	}
		/*版心左边  公告区*/
		#main_left_Bulletinfo
		{
			border:1px solid #E6E6E6;
			width: 300px;
		}
		/*公告标题*/
		#main_left_Bulletinfo #title
		{
			width:300px;
			font-weight: 700px;
		}
		/*网站公告和产品导航  【更多>>】样式 */
		#main_left_Bulletinfo #title a
		{
			float: right;
			margin-top:0px;
		}
		/*网站公告 标题（字体）样式 */
		#main_left_Bulletinfo #title span
		{
			font-size:18px;
			float: left;
		}
		/*网站公告 列表项样式 */
		#main_left_Bulletinfo #content li
		{
			list-style-position:inside;/*无序图标包含到列表项中*/
			list-style-image: url("/qlzx/images/dot.jpg");
			
			width: 250px;
			font-size:12px;
			margin-left: -40px;
		}
		#main_left_Bulletinfo #content a
		{
			list-style-position:inside;/*无序图标包含到列表项中*/
			list-style-image: url("/qlzx/images/dot.jpg");
		}
		#main_left_Bulletinfo #content a:HOVER/*悬浮*/
		{
			list-style-position:inside;/*无序图标包含到列表项中*/
			list-style-image: url("/qlzx/images/dot.png");
		}
		/*
		*公告样式
		*/
		.dateTime
		{
			color: gray;
			font-weight: bold;
			margin-right: 30px;
			float:right;
		}
		
		
		/*版心右边  产品区*/
		#main_left_type
		{
			margin-top:5px;
			border:1px solid #E6E6E6;
			width: 300px;
		}
		
		#main_left_type #title
		{
			width:300px;
		}
		/*产品类型标题*/
		#main_left_type #content_title
		{
			float:left;
			width:35px;
			font-size:18px;
			height:100%;
			background: url("images/daohang.png") center;
		}
		/*产品类别标题*/
		#main_left_type #content_product1,#main_left_type #content_product2,#main_left_type #content_product3,
		#main_left_type #content_product4,#main_left_type #content_product5,#main_left_type #content_product6,
		#main_left_type #content_product7
		{
			border: 1px solid;
			margin-top: 3px;
			height: 120px;
		}
		/*产品类别项*/
		#main_left_type #content_text
		{
			float:left;
			margin-left: 20px;
			margin-top: 2px;
			background-color: blue;
		}
		#main_left_type #content_text a
		{
			float: right;
		}
		#all
		{
			float: right;
			width: 300px;
		}
	</style>
	
	<!-- 左边样式 -->
	<style type="text/css">
	/*产品导航*/
	.content_product
	{
		width:300px;
		height:1100px;
	}
	.content_product_type1
	{
		width:290px;
		border:2px solid gray;	
		
	}
	.content_product_type2
	{
		width:290px;
		border: 2px solid #000;	
	}
	/*表格项*/
	.datailItem td
    {
		text-indent:20px;
		width: 200px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		text-align: left;
    }
  
	/*标题失去焦点的*/
	.content_title1
    {
		background:url("images/left_box_bg.png") center;
		width:24px;
		
    }
      /*标题得到焦点的*/
	.content_title2
    {
		background:none;
		width:24px;
		background-color:white;
    }
	</style>
	
	<!-- 右边样式 -->
	<style type = "text/css">
	/*整个模块*/
	.recommend
	{
		width:950px;
		float:none;
		margin-bottom: 40px;
	}
	/*模块图*/
	.recommend_nav
	{
		width:950px;
		height:61px;
		float:left;
	}
	/*商品项*/
	.recommend_Info
	{
		border-top:1px solid;
		width:950px;
		float:left;
	}
	.recommend_Info div
	{
		float:left;
	}
	/*商品整个信息（图片、价格、标题）*/
	.recommend_show
	{
		margin-top:20px;
		width:300px;
	}
	/*商品图片*/
	.recommend_show_img
	{
		margin-left:10px;
		border: 0px;
	}
	/*商品价格信息*/
	.recommend_show_Info
	{
		margin-left:25px;
		width:150px;
		height:100px;	
	}
	.recommend_show_Info div
	{
		height:34px;
		float:none;
	}
	/*价格区*/
	.recommend_show .price div
	{
		height:30px;
		width:68px;
		float:left;
		margin-left:5px;
		padding-top:8px;
		text-align:center;
	}
	/*（销售价）无删除线*/
	.new_price
	{
		text-decoration:none;
		color:#F00;
		font-weight:700;
	}
	/*(原价)有删除线*/
	 .old_price
	{
		text-decoration:line-through;
		font-weight:700;
		color:gray;
	}
	.product_title
	{
		font-size:14px;
		width:148px;
		height:10px;
		color:#000; 
		font-weight: 900;
		/*文本溢出*/
		
		/*字母溢出换行*/
		word-break: break-all;
		word-wrap:break-word;
		/*文本溢出:隐藏文本*/
		overflow:hidden;
		background:.0F3;
	}
	/*折扣样式*/
	.discount
	{
		font-family:Georgia;
		font-size:24px;
		font-weight:700;
		color:red;
		width:150px;
	}
	</style>

	
	<%-- 公告信息加载 --%>
	<% 
		//公告信息
		BulletinDao bulletinDao = new BulletinDao();
		//一个页面显示5条公告
		ArrayList<BulletinInfo> Bulletins = bulletinDao.getAllBulletionInfo(5, 1, null,0).getData();
		
		//商品类型
		GoodsTypeDao typedao = new GoodsTypeDao();
		ArrayList<GoodsType> typeList = typedao.GoodsTypeList();
		
		//商品信息
		GoodsInfoDao goodsdao = new GoodsInfoDao();
		
		ArrayList<GoodsInfo> isrecommend = goodsdao.isrecommend();

		ArrayList<GoodsInfo> isnew = goodsdao.isnew();
		
		ArrayList<GoodsInfo> discount = goodsdao.discount();
	%>
  </head>
  
  <body>
  
    <!-- 首页 -->
    <%@include file="top_index.jsp" %>    
    <!-- 版心 -->
    <div id="main">
    	<!-- 左div -->
    	<div id = "main_left">
    		<!-- 网站公告 （5条公告）-->
    		<div id="main_left_Bulletinfo">
    			<!-- 模块一 -->
    			<div id="title" style="margin-bottom:5px;">
    					<span><img src="/qlzx/images/gonggao.gif"/>网站公告</span>
    					<!-- 公告更多 -->
    					<a href="servlet/BulletinManage?pageSize=6&pageNo=1&sign=ok" style="text-align: center;">更多&raquo;</a>
    			</div>
    			<div id="content">
    				<%//PageModel<PageModel<CustomerInfo>> pm = new PageModel<PageModel<CustomerInfo>>();
    				for(int i = 0;i<Bulletins.size();i++)
    					{%>
    						<div style="border-bottom:1px dashed #000; width:100%; margin-bottom:5px; font-size: 12px; padding-bottom: 5px;">
    						<img src="images/dot.gif" >&nbsp;<a href="/qlzx/servlet/BulletinManage?op=viewBulletin&id=<%=Bulletins.get(i).getId()%>" ><%=Bulletins.get(i).getTitle() %>
    							<span class="dateTime"><%=DateTimeUtil.ConvertDateYMD(Bulletins.get(i).getCreateTime()) %></span>
    						</a>
    						</div>		
    				<%} %>
    			</div>
    		</div>
    		<!-- 产品导航 （7种产品）-->
    		<div id="main_left_type">
    			<!-- 模块一 -->
				<table class="content_product" cellspacing="0">
			      <tr>
			        <td><img src="/qlzx/images/daohang.png" width="24" height="26"  alt=""/>产品导航</td>
			      </tr>
				
			      <%for(int j = 0 ;  j < typeList.size() ; j++){ %>
			  	  <tr>
			        <td class="datailItem" id="datailItem_1"  align="center">
			            <table class = "content_product_type1" onmouseover="upContent(this)" onmouseout="downContent(this)">
			            <tr>
			            	<th class="content_title1" rowspan="6" scope="row"><%=typeList.get(j).getTypeName() %></th>
			            	
			            <%ArrayList<GoodsInfo> goodsList = goodsdao.getAllGoodsInfo(5, 1, typeList.get(j).getTypeId()).getData();
			             for(int i = 0;i<goodsList.size();i++)
			            	{
			            		if(i == 0){%>
			            			<td><%=goodsList.get(i).getGoodsName()%></td>
			            </tr>
			            		<%}else{%>
    						<tr >
    							<td><%=goodsList.get(i).getGoodsName()%></td>
    						</tr>
    						<%}%>
    					<%} %>
			              <tr>
			                <td align="right" style="text-align:right;"><a href="servlet/GoodsManage?typeid=<%=typeList.get(j).getTypeId() %>">更多&raquo;</a></td>
			              </tr>
			         </table>
			     	</td>
			    </tr>
			    <%}%>
			</table>
    		</div>
    	</div>
    	
    	
    	<!-- 右div(商品信息：) -->
    	<div id = "main_right">
    		<!-- 推荐 -->
    		<div class="recommend">
			    <div class="recommend_nav">
			    	<img src="/qlzx/images/recommend2.gif"   alt=""/>
			    </div>
    <div class="recommend_Info">
    	<%
    	int i = 0;
    	for(GoodsInfo recommend : isrecommend){  
    	i++;
    	if(i > 9){break;}%>
     	 <!-- 折扣 -->
      	<div class="recommend_show">
	      	<a href="servlet/GoodsManage?op=query&goodsid=<%=recommend.getGoodsId()%>" style="text-decoration: none;" title="<%=recommend.getGoodsName()%>">
	      		<!-- 商品图片 -->
	             <div class="recommend_show_img">
	            	<img src="/qlzx/product/<%=recommend.getPhoto()%>" width="100" height="100"  alt=""/>
	            </div>
	             <div class="recommend_show_Info">
	            <%if(recommend.getDiscount() != 10.0f){ %>
	             	<!-- 商品折扣 -->
	                <div class="discount"><%=recommend.getDiscount() %>折</div>
	                <%}%>
	                <div>
	                	<!-- 商品标题 -->
	               	  <div class="product_title" style="color:#000; font-weight: 900;"><%=recommend.getGoodsName()%> </div>
	               </div>
	                <div class="price">
	                	<!-- 有折扣 -->
	                	<!-- 原价 -->
	                	
	                	<%if(recommend.getDiscount() != 10.0f){ %>
	                	<div class="old_price">&yen;<%=recommend.getPrice()%></div>
	                    <!-- 销售价 -->
	                    <div class="new_price">&yen;<%=recommend.getDiscountPrice() %></div>
	                    <%}else{%>
    						<!-- 无折扣 -->
    						<!-- 原价 -->
    						<div class="new_price">¥<%=recommend.getPrice()%></div>
    					<%}%>
	                </div>
	             </div>
             </a>
   		</div>
   		<%} %>
 	</div>
</div> 
    		<!-- 新品上架 -->
    		<div class="recommend">
			    <div class="recommend_nav">
			    	<img src="/qlzx/images/newproduct5.gif"   alt=""/>
			    </div>
			    <div class="recommend_Info">
			    <% i = 0;
    			   for(GoodsInfo recommend : isnew){
    			   i++;
    			    if(i > 9){break;}%>
     				<!-- 折扣 -->
     				<div class="recommend_show">
	      		<a href="servlet/GoodsManage?op=query&goodsid=<%=recommend.getGoodsId()%>" style="text-decoration: none;" title="<%=recommend.getGoodsName()%>">
	      		<!-- 商品图片 -->
	             <div class="recommend_show_img">
	            	<img src="/qlzx/product/<%=recommend.getPhoto()%>" width="100" height="100"  alt=""/>
	            </div>
	             <div class="recommend_show_Info">
	            <%if(recommend.getDiscount() != 10.0f){ %>
	             	<!-- 商品折扣 -->
	                <div class="discount"><%=recommend.getDiscount() %>折</div>
	                <%}%>
	                <div>
	                	<!-- 商品标题 -->
	               	  <div class="product_title" style="color:#000; font-weight: 900;"><%=recommend.getGoodsName()%> </div>
	               </div>
	                <div class="price">
	                	<!-- 原价 -->
	                	
	                	<%if(recommend.getDiscount() != 10.0f){ %>
	                	<div class="old_price">&yen;<%=recommend.getPrice()%></div>
	                	<!-- 有折扣 -->
	                    <!-- 销售价 -->
	                    <div class="new_price">&yen;<%=recommend.getDiscountPrice() %></div>
	                    <%}else{%>
    						<!-- 无折扣 -->
    						<!-- 原价 -->
    						<div class="new_price">¥<%=recommend.getPrice()%></div>
    					<%}%>
	                </div>
	             </div>
             </a>
   		</div>
   		<%} %>
			    </div>
			</div>
    		<!-- 特价商品 -->
    		<div class="recommend">
			    <div class="recommend_nav">
			    	<img src="/qlzx/images/specilproduct2.gif"   alt=""/>
			    </div>
			    <div class="recommend_Info">
			    <%
			    i=0;
    			for(GoodsInfo recommend : discount){ 
    			i++;
    			if(i > 9){break;}%>
      			<!-- 折扣 -->
      			<div class="recommend_show">
	      		<a href="servlet/GoodsManage?op=query&goodsid=<%=recommend.getGoodsId()%>" style="text-decoration: none;" title="<%=recommend.getGoodsName()%>">
	      		<!-- 商品图片 -->
	             <div class="recommend_show_img">
	            	<img src="/qlzx/product/<%=recommend.getPhoto()%>" width="100" height="100"  alt=""/>
	            </div>
	             <div class="recommend_show_Info">
	            <%if(recommend.getDiscount() != 10.0f){ %>
	             	<!-- 商品折扣 -->
	                <div class="discount"><%=recommend.getDiscount() %>折</div>
	                <%}%>
	                <div>
	                	<!-- 商品标题 -->
	               	  <div class="product_title" style="color:#000; font-weight: 900;"><%=recommend.getGoodsName()%> </div>
	               </div>
	                <div class="price">
					<%if(recommend.getDiscount() != 10.0f){ %>
					<div class="old_price">&yen;<%=recommend.getPrice()%></div>
	                    <!-- 销售价 -->
	                    <div class="new_price">&yen;<%=recommend.getDiscountPrice() %></div>
	                    <%}else{%>
    						<!-- 无折扣 -->
    						<!-- 原价 -->
    						<div class="new_price">¥<%=recommend.getPrice()%></div>
    					<%}%>
	                </div>
	             </div>
             </a>
   		</div>
   		<%} %>
	 </div>
</div>
    </div>
    	
    </div>
    <!-- 尾页 -->
    <%@include file="bottom_index.jsp" %>
  </body>
</html>
