/**
 * ����XMLHttpRequest ����(���ݲ�ͬ����������������������������)
 */
function createXMLHttpRequest() {
	var xmlHttp = null;
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();//firefox��Chrome��Safari��
	} else {
		// IE�����
		try {
			xmlHttp = new ActiveXObject("Msxm12.XMLHTTP");
		} catch (e) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	return xmlHttp;
}

/**
 * ����Ajax�첽����
 * 
 * @param method
 *            ����ʽpost��get
 * @param url
 *            �����URL��ַ
 * @param str_params
 *            �����ַ��������硰name=a&&id=1&&size=2��
 * @param calback
 *            ����������״̬����ʱ�����õĺ�������ѡ������
 * @param error
 *            ����������״̬������ʱ�����õĺ�������ѡ������
 */
function sendRequest(method, url, str_params, callback, error) {
	// ��XMLHTTPRequest����Ϊ�գ�����÷�������
	var xmlHttp = createXMLHttpRequest();

	// ��Ϊget��ʽ
	if (method.toLowerCase() == "get" && str_params != null) {
		url += "?" + str_params;// ������׷�ӵ�URL������д���
	}

	// ����һ���µ�HTTP����ָ����������
	xmlHttp.open(method, url, true);
	// ���ûص�����
	xmlHttp.onreadystatechange = function() {
		// ���������
		if (xmlHttp.readyState == 4) {
			// ��״̬����
			if (xmlHttp.status == 200) {
				// ��������callback������һ������
				if (typeof (callback) == "function") {
					// ���ú���
					callback(xmlHttp);
				} else {
					// ״̬�������������˴���
					// ��������errror1������һ������
					if (typeof (error) == "function") {
						// ����error����
						error(xmlHttp);
					}
				}
			}
		}
	}
	

	// ��ΪPOST����ʽ
	if (method.toLowerCase() == "post") {
		// ����Content-Type����ͷ
		xmlHttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// ��������
		xmlHttp.send(str_params);
	} else {
		// ��������
		xmlHttp.send(null);
	}
}
