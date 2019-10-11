/**
 * 创建XMLHttpRequest 对象(根据不同浏览器创建，尽量兼容主流浏览器)
 */
function createXMLHttpRequest() {
	var xmlHttp = null;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();//firefox、Chrome、Safari等
	} else {
		// IE浏览器
		try {
			xmlHttp = new ActiveXObject("Msxm12.XMLHTTP");
		} catch (e) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return xmlHttp;
}

/**
 * 发送Ajax异步请求
 * 
 * @param method
 *            请求方式post或get
 * @param url
 *            请求的URL地址
 * @param str_params
 *            参数字符串，比如“name=a&&id=1&&size=2”
 * @param calback
 *            服务器返回状态正常时，调用的函数（可选参数）
 * @param error
 *            服务器返回状态不正常时，调用的函数（可选参数）
 */
function sendRequest(method, url, str_params, callback, error) {
	// 若XMLHTTPRequest对象为空，则调用方法创建
	var xmlHttp = createXMLHttpRequest();

	// 若为get方式
	if (method.toLowerCase() == "get" && str_params != null) {
		url += "?" + str_params;// 将参数追加到URL后面进行传递
	}

	// 创建一个新的HTTP请求，指定请求属性
	xmlHttp.open(method, url, true);
	// 设置回调函数
	xmlHttp.onreadystatechange = function() {
		// 若请求完成
		if (xmlHttp.readyState == 4) {
			// 若状态正常
			if (xmlHttp.status == 200) {
				// 如果传入的callback参数是一个函数
				if (typeof (callback) == "function") {
					// 调用函数
					callback(xmlHttp);
				} else {
					// 状态不正常，发送了错误
					// 如果传入的errror1参数是一个参数
					if (typeof (error) == "function") {
						// 调用error函数
						error(xmlHttp);
					}
				}
			}
		}
	}
	

	// 若为POST请求方式
	if (method.toLowerCase() == "post") {
		// 设置Content-Type请求头
		xmlHttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// 发送请求
		xmlHttp.send(str_params);
	} else {
		// 发送请求
		xmlHttp.send(null);
	}
}
