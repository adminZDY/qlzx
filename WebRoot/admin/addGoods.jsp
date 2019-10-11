<%@page import="com.sec.model.GoodsType"%>
<%@page import="com.sec.dao.GoodsTypeDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	
	ArrayList<GoodsType> goodstype = (ArrayList<GoodsType>) request.getAttribute("goodstype");
	
	if(goodstype == null || "".equals(goodstype)){
		response.sendRedirect("../servlet/GoodsManage?op=viewGoods");
		return;
	}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加商品</title>
        <link rel="stylesheet" href="/qlzx/css/bootstrap.min.css">  
		<script src="/qlzx/js/jquery.min.js"></script>
		<script src="/qlzx/js/bootstrap.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
    	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
    	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    	<script type="text/javascript" charset="utf-8" src="/qlzx/ueditor/lang/zh-cn/zh-cn.js"></script>
	<style type="text/css">
		
		#table_1{
			width:100%;
			height: 350px;
			
		}
		#table_1 td{
			border-bottom: 1px dashed gray;
			padding: 10px;
		}
		button{
			style="width:63px; height: 32px;margin-left: 94%;margin-top : 10px; font-weight: 900; "
		}
		#scpg{
			width:1000px;
		}
	</style>
	 <script type="text/javascript" src="/qlzx/common/js/ajax.js"></script>
	 
<script type="text/javascript"> 

function fun(obj){
	var num = document.getElementById("txt");
	num.value=obj.value;
}
function returnURL(){
	window.location.href="GoodsManage?op=viewGoods";
}
</script>

<script type="text/javascript">
	function filter()
    {
    	var obj = document.getElementsByTagName("input");
    	for ( var i = 0; i < i.length; i++) {
			obj[i].value = obj[i].value.replace(/(^\s*)|(\s*$)/g, "");
		}
	}
	
	/*商品名称验证*/
	function checkName(checkObj)
	{
		var goodsName = document.getElementsByName("goodsname")[0];
		if(checkObj == "span_name")
		{	
			if(goodsName.value.replace(/(^\s*)|(\s*$)/g, "").length <=1)
			{
				document.getElementById(checkObj).innerHTML = "商品名称至少填写两个字"; 
				goodsName.value = "";
				return;
			}
			else{
				count++;
			}			
		}
	}
	
	function checkPrice(checkObj){
		var goodsPrice = document.getElementsByName("goodsprice")[0];
		
		//验证输入是否为非数字
		if(isNaN(goodsPrice.value))
		{
			document.getElementById(checkObj).innerHTML = "商品商品价格不允许输入非数字数值";
			goodsPrice.value = "1";
			return;
		}else if(goodsPrice.value <= 0)
		{
			document.getElementById(checkObj).innerHTML = "商品价格不能低于0元";
			goodsPrice.focus();
			return;
		}
		else{
			count++;
		}
	}
	
	/**
	*数据验证
	*/
	function checkDiscount(checkObj){
		
		var discount = document.getElementsByName("discount")[0];
		
		//判断折扣
		if(isNaN(discount.value))
		{
			document.getElementById(checkObj).innerHTML = "商品折扣不允许输入非数字数值";
			
			return;
		}
		else if(discount.value <=0 || discount.value > 10)
		{
			document.getElementById(checkObj).innerHTML = "商品折扣只能输入1-10数字";
			discount.value = "10.00";
			return;
		}
		else
		{
			count++;
		}
	}
	
	function clear_span(obj){
		/*onfocus="clear('span_price')"*/
		document.getElementById(obj).innerHTML = " ";
	}
	
	function checkData(){
		count = 0;
		checkName("span_name");
		checkPrice("span_price");
		checkDiscount("span_price");
		if(count==3){
		return true;
		}else{
		alert("请填写信息完整");
		return false;
		}
	}
	
</script> 
  </head>
  
  <body style="margin-left: 10px; ">
    <div style="background:url(/qlzx/images/titlebar_div_bg.jpg) repeat-x bottom; margin-top:5px; height: 25;">
   	<img src="/qlzx/images/doc_red.gif">添加商品信息
  </div>
  <form name="upform" action="/qlzx/servlet/GoodsManage?op=Add" method="POST"  onsubmit="return checkData()">
    <table id="table_1" width="100%" height= "400" >
    	<tr>
    		<td >商品名称:</td>
    		<td><input type="text" name="goodsname" value="" placeholder = "请输入商品名称" 
    		onkeydown="clear_span('span_name')" onblur="checkName('span_name')">
    			<span id="span_name" style="color: red"></span>
    		</td>
    	</tr>
    	<tr>
    		<td>商品分类:</td>
    		<td>
    		<select name ="goodstype">
    			<%
    			for(GoodsType type:goodstype)
    			{%>	
    				<option value="<%=type.getTypeId()%>"><%=type.getTypeName()%></option>
    			<%}%>
    	    </select>
    		</td>
    	</tr>
    	<tr>
    		<td>商品价格/折扣:</td>
    		<td>价格：<input type="text" name="goodsprice" value="" size="5" onkeydown="clear_span('span_price')"
    		 onblur="checkPrice('span_price')" placeholder = "价格"/>
    		折扣：<input type="text" name="discount" value="" size="5" onkeydown="clear_span('span_price')" 
    		onblur="checkDiscount('span_price')" placeholder = "折扣">
    			<span id="span_price" style="color: red"></span>
    		</td>
    		
    	</tr>
    	<tr>
    		<td>上架/推荐/新品:</td>
    		<td>
    		<input type='checkbox' name='stastus' value='0'>
    		上架
    		<input type='checkbox' name='isrecommend' value='0'>
    		推荐
    		<input type='checkbox' name='isnew' value='0'>	
    		新品
    		</td>
    	</tr>
    	<tr>
    		<td>商品图片:</td>
    		<td>
    			<input type="text" style="float:left;"readonly unselectable = "on" size="40" id="txt" name="photo" value="" placeholder = "请选择图片上传"/>
    			<input type="file" name="uploadFile"  style="width:70px; float: left; margin-left:10px;" onchange="fun(this)"/>
    		</td>
    	</tr>
    	<tr >
    		<td >商品描述:</td>
    		<td >
    			<textarea cols="55" rows="6" name="remark" placeholder = "请输入商品描述"></textarea>
    		</td>
    	</tr>
    	<tr>
    		<td>商品详细</td>
    		<td>
    			<textarea cols="60" rows="8" name="content" id="scpg"></textarea>
    		</td>
    	</tr>
    	<tr>
    		<td></td>
    		<td>
    			<button type="submit" class="btn btn-success" >提交表单</button>&nbsp;
    			<button type="reset" class="btn btn-success">重置表单</button>
    			<button type="button" onclick="returnURL()" class="btn btn-success">返回</button>
    		</td>
    	</tr>
    </table>
   </form>
  </body>
   <script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('scpg');
	
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
    </script>
</html>
