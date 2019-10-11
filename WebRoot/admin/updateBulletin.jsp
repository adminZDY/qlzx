<%@page import="com.sec.control.*"%>
<%@page import="com.sec.dao.*"%>
<%@page import="com.sec.model.*"%>
<%@page import="com.sec.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="userLogin.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	BulletinInfo buin = (BulletinInfo) request.getAttribute("buinfo");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'updateBulletin.jsp' starting page</title>
    
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/qlzx/ueditor/lang/zh-cn/zh-cn.js"></script>
		
<script type="text/javascript">

	function skip(){
		document.form1.action = "/qlzx/servlet/BulletinManage";
		document.form1.submit();
	}
	
</script>

<style type="text/css">
	#scpg{
	
		width:1030px;
	}
</style>
  </head>
  
  <body>
  <div style="background:url(/qlzx/images/titlebar_div_bg.jpg) repeat-x bottom; height: 25;"><img src="/qlzx/images/doc_red.gif">查看/修改公告信息</div>
  <form name="form1" action="/qlzx/servlet/BulletinManage?op=update" method="post">
    <table style="border:1px solid #000; width:100%">
    	<tr>
    		<td style="width:30px;">编号</td>
    		<td><input type="text" name="id" readonly="readonly" size="2" value="<%=buin.getId()%>"></td>
    	</tr>
    	<tr>
    		<td>标题</td>
    		<td><input type="text" name="title" value="<%=buin.getTitle()%>" /></td>
    	</tr>
    	<tr>
    		<td>内容</td>
    		<td><textarea cols="70" rows="8" name="content" id="scpg"><%=buin.getContent() %></textarea>
    		</td>
    	</tr> 
    	<tr>
    		<td>发布者</td>
    		<td><%=buin.getUser().getUserName() %></td>
    	</tr>
    	<tr>
    		<td >发布时间</td>
    		<td><%=DateTimeUtil.ConvertDate(buin.getCreateTime()) %></td>
    	</tr>
    	<tr>
    		<td></td>
    		<td>
    			<input type="submit" value = "提交表单">
    			<input type="reset" value = "重置表单">
    			<input type="button" onclick="skip();" value = "返回"></td>
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
