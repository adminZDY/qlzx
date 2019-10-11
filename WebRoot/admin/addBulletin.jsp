<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发布公告信息</title>
   
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/qlzx/ueditor/lang/zh-cn/zh-cn.js"></script>
		
    <script type="text/javascript">
    	function filter()
    	{
    		var obj = document.getElementsByTagName("input");
  			var flag = false;
    		for ( var i = 0; i < i.length; i++) {
				obj[i].value = obj[i].value.replace(/(^\s*)|(\s*$)/g, "");
			}
			
    		var title = document.getElementsByName("title")[0];
    		var content = document.getElementsByName("content")[0];
    		var text =  content.value.replace(/(^\s*)|(\s*$)/g, "");
    		
    		if(title.value == "" || title.value.length == 0)
    		{
    			alert("标题不能为空");
    		}
    		else if(content.value.length <= 10 || text.length < 1){
    			alert("内容必须填写10个字以上");
    		}
    		else{
    			flag = true;
    		}
    		return flag;
    	}
    </script>
  </head>
  
  <body >
    <div style="background:url('/qlzx/admin/images/titlebar_div_bg.jpg') repeat-x bottom; height: 25;">
    <img src="/qlzx/images/doc_red.gif">发布公告信息</div>
  <form action="/qlzx/servlet/BulletinManage?op=add" method="post" onsubmit="return filter()">
    <table style="border:1px solid #000; width:100%">
    	<tr>
    		<td style="width:30px;">标题</td>
    		<td><input type="text" name="title" size="30"></td>
    	</tr>
    	<tr>
    		<td>内容</td>
    		<td>
    			<textarea cols="60" rows="8" name="content" id="scpg"></textarea>
    		</td>
    	</tr>
    	<tr>
    		<td><input type="submit" value="提交表单"></td>
    		<td><input type="reset" value="重置表单"></td>
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
