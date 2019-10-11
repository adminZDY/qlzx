<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE style PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<style type="text/css">
/*尾部*/
footer {
	margin-top:10px;
	float: left;
	text-align: center;
	background: url("/qlzx/images/bg.jpg");
	width: 1366px;
	height: 129px;
}

footer div {
	float: left;
}

#footer_nav {
	width: 1366px;
	text-align: left;
}

#footer_nav_content {
	width: 341px;
	float: left;
}

#footer_nav_content div {
	float: none;
	padding-left: 60px;
}

#footer_top {
	background: url("/qlzx/images/bg.gif");
	height: 28px;
}

#footer_middle {
	background: url("/qlzx/images/bg.jpg");
	height: 80px; /*129*/
}

#footer_bottom {
	background: url("/qlzx/images/bg.jpg") bottom;
	text-align: center;
	width: 1366px;
	height: 49px;
}

#footer_bottom p {
	padding-top: 0px;
	margin-top: 10px;
	font-size: 15px;
}
#footer_middle li{
	margin-left: -20px;
}
#footer_middle a{
	text-decoration: none;/*超链接下划线去除*/
}
</style>
</head>
<body>
<footer>
	<!-- 底部导航栏 -->
	<div id="footer_nav">
		<!-- 我的订单 -->
		<div id="footer_nav_content">
			<div id="footer_top">我的订单</div>
			<div id="footer_middle">
				<ul>
					<li><a href="#">如何下订单</a>
					</li>
					<li><a href="#">跟踪最近的订单</a>
					</li>
					<li><a href="#">查看或修改订单</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- 如何付款 -->
		<div id="footer_nav_content">
			<div id="footer_top">如何付款</div>
			<div id="footer_middle">
				<ul>
					<li><a href="#">付款方式</a>
					</li>
					<li><a href="#">查看我的礼品卡余额</a>
					</li>
					<li><a href="#">查看我的电子账户</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- 配送信息 -->
		<div id="footer_nav_content">
			<div id="footer_top">配送信息</div>
			<div id="footer_middle">
				<ul>
					<li><a href="#">配送费收取标准</a>
					</li>
					<li><a href="#">国内订单配送时间和配送范围</a>
					</li>
					<li><a href="#">海外订单配送时间和配送范围</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- 需要帮助 -->
		<div id="footer_nav_content">
			<div id="footer_top">需要帮助</div>
			<div id="footer_middle">
				<ul>
					<li><a href="#">汇款单招聘</a>
					</li>
					<li><a href="#">忘记了密码</a>
					</li>
					<li><a href="#">参考帮助中心</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- 底部版权 -->
	<div id="footer_bottom">
		<p>千里之行&copy;(2009-2011)版权所有</p>
	</div>
</footer>
</body>
</html>
