  	var flag = false;
    //��Ǵ���
    var a,b,c,d;
    a=b=c=d=0;
    
    /**
    *�û�����֤
    */
    function name_check()
    {
  		var name_span = document.getElementById("name_span");
  		var name = document.getElementsByName("name")[0];
  		if(name.value.length<=0)
  		{
  			name_span.innerHTML = "�������ջ���";
  			name_span.style.color = "red";
  			a = 0;
  		}
  		else
  		{
  			name_span = "";
  			a = 1;
  		}
    }
    /**
    *�̶��绰��֤
    */
    function phone_check()
    {
  		var phone_span = document.getElementById("phone_span");
  		//�̶�
  		var phone = document.getElementsByName("phone")[0];
  	
  		//������ʽ
  		var str = /^\d{4}[-]\d{8}$/;//�̶��绰
  		b = 0;
  		//�����룬����ʽ����
  		if(phone.value.length > 0 && !str.test(phone.value))
  		{
  			phone_span.style.color = "red";
  			phone_span.innerHTML = "�̶��绰�����ָ�ʽ���롾XXXX-XXXXXXXX��<br/>,X��ʾ����,'-'ǰ����4λ��������8λ";
  		}
  		//û����
  		else if(phone.value.length <= 0)
  		{
  			phone_span.style.color = "#000";
  			phone_span.innerHTML = "�̶��绰���ƶ��绰������дһ�";
  		}
  		//�����룬����ȷ
  		else
  		{
  			phone_span.style.color = "#000";
  			phone_span.innerHTML = "�����ʽ��ȷ";
  			b = 1;
  		}
    }
    
    /**
    *�ƶ��绰��֤
    */
    function mtphone_check()
    {
  		var mtphone_span = document.getElementById("mtphone_span");
  		//�ƶ��绰
  		var mtphone = document.getElementsByName("mtphone")[0];
  		
  		var str = /^\d{11}$/;//�绰
  		
  		c = 0;
  		//�����룬����ʽ����
  		if(mtphone.value.length > 0 && !str.test(mtphone.value))
  		{
  			mtphone_span.style.color = "red";
  			mtphone_span.innerHTML = "�ƶ��绰��������11λ����";
  		}
  		//û����
  		else if(mtphone.value.length <= 0)
  		{
  			mtphone_span.style.color = "#000";
  			mtphone_span.innerHTML = "�̶��绰���ƶ��绰������дһ�";
  		}
  		//�����룬����ȷ
  		else
  		{
  			mtphone_span.style.color = "#000";
  			mtphone_span.innerHTML = "�����ʽ��ȷ";
  			c = 1;
  		}
    }
    
    /**
    *��ַ��֤
    */
    function address_check()
    {
  		var address_span = document.getElementById("address_span");
  		var address = document.getElementsByName("address")[0];
  		
  		if(address.value.length<=0)
  		{
  			address_span.innerHTML = "����ϸ��д��ʵ��ַ��";
  			address_span.style.color = "red";
  			d = 0;
  		}
  		else
  		{
  			address_span = " ";
  			d = 1;
  		}
    }
    
    function ifOk()
    {
    	name_check();
    	phone_check();
    	mtphone_check();
    	address_check();
    	flag = false;
    	if(a+b+d == 3 && c == 0 && document.getElementsByName("mtphone")[0].value.length == 0)
    	{
    		//��д����
    		flag = true;
    	}
    	else if(a+c+d == 3  && b == 0 && document.getElementsByName("phone")[0].value.length == 0)
    	{
    		//��д����
    		flag = true;
    	}
    	else if(a+b+c+d ==4)
    	{
    		//��д����
    		flag = true;
    	}
    	else if(b == 0 && c == 0)
    	{
    		alert("�̶��绰���ƶ��绰������дһ����д��ȷ��");
    	}
    	else
    	{
    		alert("������Ϣ�Ƿ���д����");
    	}
    	return flag;
    }
    
    function clearText(obj)
    {
    	document.getElementById(obj).innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    }
    