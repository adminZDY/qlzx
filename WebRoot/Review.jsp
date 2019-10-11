<%@page import="com.sec.model.OrderGoodsInfo"%>
<%@page import="com.sec.model.GoodsInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	ArrayList<OrderGoodsInfo> orderList = (ArrayList<OrderGoodsInfo>) request.getAttribute("orderList");

 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>购物车页面</title>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>
	 <link rel="stylesheet" href="/qlzx/css/bootstrap.min.css">  
		<script src="/qlzx/js/jquery.min.js"></script>
		<script src="/qlzx/js/bootstrap.min.js"></script>
    <style type="text/css">
    	.a{
    		color:white;
    	}
    	.sp{
    		color:#F30;
    		font-family:Georgia;
    		font-size:24px;
    		font-weight: 700;
    	}
    	#money_div{
    		height: 50px; 
    		font-size: 12; 
    		font-weight: 700;
    	}
    	#empty_sp{
    		margin-left:940px; 
    		font-weight: 700;
    	}
   
    </style>
  </head>
  
  <body>
<!-- 首页 -->
    <%@include file="top_index.jsp" %>
    <div  style="margin-left:50px;">
    <img src="/qlzx/images/site_ico.gif">您现在所在的位置，<a href="/qlzx/index.jsp">网站首页</a>&gt;购物车
 	<div>
    <img src="/qlzx/images/buycart_logo.gif">
    </div>
    <form action="/qlzx/servlet/ReviewManage" method="post">
    <input type="radio"  name="reviewStatus" value="1">差评&nbsp;
    <input type="radio" name="reviewStatus" value="2">一般&nbsp;
    <input type="radio" checked="checked" name="reviewStatus" value="3">好评
    <table border="1" cellspacing="0" style=" width:1200px; ">
    	<tr style="background-color:#0CF;">
    		<th>商品名称</th>
    		<th>商品类型</th>
    		<th>价格</th>
    		<th>折扣</th>
    		<th>数量</th>
    		<th>小计</th>
    	</tr>
    
    	<%
    	double sum = 0f;
    	int orderid = 0;
    	for(OrderGoodsInfo order : orderList){
    		orderid = order.getOrderInfo().getOrderId();
    		//System.out.println(customer.getId());
  			GoodsInfo goods = order.getGoodsInfo();
  			
  			sum += order.getSumPrice();
    	%>
    	<tr>
    		<td><img src="/qlzx/product/<%=goods.getPhoto()%>" align="middle" width="50" height="50"/><a href="#"><%=goods.getGoodsName()%></a></td>
    		<td><a href="#"><%=goods.getGoodstype().getTypeName()%></a></td>
    		<td><%=goods.getPrice()%>元</td>
    		<td><%if(goods.getDiscount() == 10.0f){%>
    				-
    			<%}else{%>
    				<%=goods.getDiscount()%>
    			<%}%></td>
    		<td><%=order.getQuantity()%></td>
    		<td><%=String.format("%.2f",order.getSumPrice() )%>元</td>
    		</tr>
    		
    	<input type="hidden" name = "goodsid" value="<%=goods.getGoodsId()%>">
    	<%}%>
    	<tr>
    		<td colspan="6">
    		评论：
    			<textarea cols="60" rows="8" name="content" id="scpg"></textarea>
    		</td>
    	</tr>
    </table>
    <div style="margin-left: 1150px;width:300px; margin-top:10px;">
    	<input type="submit"  class="btn btn-success" value="确定">
    	<input type="hidden" name = "customerid"  value="<%=customer.getId()%>">
    	<input type="hidden" name = "orderid" value="<%=orderid%>">
    	<input type="hidden" name = "op" value="add">
    </div>
    </form>
 	</div>
 	<!-- 尾页 -->
    <%@include file="bottom_index.jsp" %>
  </body>
  <script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    //var ue = UE.getEditor('scpg');
	//UE.getEditor('scpg', {toolbar: [['simpleupload',  'emotion']]});
	var ue = UE.getEditor('scpg', {
        toolbars: [
            [
        //自定义的工具栏，自定义增减 
                'undo', //撤销
                'redo', //重做
                'simpleupload',//上次单个图片
                'insertimage',
                'emotion', //表情
                'edittip ', //编辑提示
                
            ]
        ],
        autoFloatEnabled:false,
        //关闭字数统计  
        wordCount:false, 
        autoHeightEnabled: true,
        //关闭elementPath  
        elementPathEnabled:false
    });
	 
     function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    } 
    
     /* $(function() {
	//本来是这样的:UE.getEditor('editor'); 传入参数后就是下面那样子了，toolbars里的就是工具的图标
	UE.getEditor('scpg', {toolbar: [['simpleupload',  'emotion']]});
	}) */

</script>
</html>