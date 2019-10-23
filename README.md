# qlzx
# 千里之行电商项目
 使用myeclipse（IDE）开发!
 使用myeclipse（IDE）开发!
 使用myeclipse（IDE）开发!
 
 
 1.环境搭建
 jdk7+,tomcat7+,sqlservice
 支付功能使用的是支付宝沙箱环境，后台中出现的富文本使用的是百度编辑器，百度编辑器引入细节请参考http://fex.baidu.com/ueditor/
 
 2.导入项目
 先在ide创建一个工作空间，然后找到工作空间，直接将项目复制到那个工作空间目录下
 将sql/qlzx.sql脚本在sqlservice管理工具中执行
 
 3.运行
 打开tomcat服务，访问
 http:localhost:8080/qlzx/index.jsp（前台页面）
 
 http:localhost:8080/qlzx/admin/index.jsp(后台页面)
 
 总结：
 这个项目用到了支付宝的支付接口，相关的配置需要去蚂蚁金服开发平台https://www.ant-open.com/platform/home.htm
 注册登录搭建沙箱环境（测试环境，还不是真正的环境。测试环境里模拟了一个客户和商家，客户和商家可以充钱，可以支付），当然你可以不用搭建，但这样的话，你使用的是我的环境。
 关于沙箱环境搭建可以参考这个网站进行配置https://www.jianshu.com/p/a16a6d4f7121
 相关配置修改要去项目中 qlzx/src/com/alipay/config/AlipayConfig.java 中修改
