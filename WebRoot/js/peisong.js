  	var flag = false;
    //标记错误
    var a,b,c,d;
    a=b=c=d=0;
    
    /**
    *用户名验证
    */
    function name_check()
    {
  		var name_span = document.getElementById("name_span");
  		var name = document.getElementsByName("name")[0];
  		if(name.value.length<=0)
  		{
  			name_span.innerHTML = "请输入收货人";
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
    *固定电话验证
    */
    function phone_check()
    {
  		var phone_span = document.getElementById("phone_span");
  		//固定
  		var phone = document.getElementsByName("phone")[0];
  	
  		//正则表达式
  		var str = /^\d{4}[-]\d{8}$/;//固定电话
  		b = 0;
  		//有输入，但格式错误
  		if(phone.value.length > 0 && !str.test(phone.value))
  		{
  			phone_span.style.color = "red";
  			phone_span.innerHTML = "固定电话以这种格式输入【XXXX-XXXXXXXX】<br/>,X表示数字,'-'前输入4位，后输入8位";
  		}
  		//没输入
  		else if(phone.value.length <= 0)
  		{
  			phone_span.style.color = "#000";
  			phone_span.innerHTML = "固定电话与移动电话至少填写一项。";
  		}
  		//有输入，并正确
  		else
  		{
  			phone_span.style.color = "#000";
  			phone_span.innerHTML = "输入格式正确";
  			b = 1;
  		}
    }
    
    /**
    *移动电话验证
    */
    function mtphone_check()
    {
  		var mtphone_span = document.getElementById("mtphone_span");
  		//移动电话
  		var mtphone = document.getElementsByName("mtphone")[0];
  		
  		var str = /^\d{11}$/;//电话
  		
  		c = 0;
  		//有输入，但格式错误
  		if(mtphone.value.length > 0 && !str.test(mtphone.value))
  		{
  			mtphone_span.style.color = "red";
  			mtphone_span.innerHTML = "移动电话必须输入11位数字";
  		}
  		//没输入
  		else if(mtphone.value.length <= 0)
  		{
  			mtphone_span.style.color = "#000";
  			mtphone_span.innerHTML = "固定电话与移动电话至少填写一项。";
  		}
  		//有输入，并正确
  		else
  		{
  			mtphone_span.style.color = "#000";
  			mtphone_span.innerHTML = "输入格式正确";
  			c = 1;
  		}
    }
    
    /**
    *地址验证
    */
    function address_check()
    {
  		var address_span = document.getElementById("address_span");
  		var address = document.getElementsByName("address")[0];
  		
  		if(address.value.length<=0)
  		{
  			address_span.innerHTML = "请详细填写真实地址。";
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
    		//填写完整
    		flag = true;
    	}
    	else if(a+c+d == 3  && b == 0 && document.getElementsByName("phone")[0].value.length == 0)
    	{
    		//填写完整
    		flag = true;
    	}
    	else if(a+b+c+d ==4)
    	{
    		//填写完整
    		flag = true;
    	}
    	else if(b == 0 && c == 0)
    	{
    		alert("固定电话与移动电话至少填写一项填写正确。");
    	}
    	else
    	{
    		alert("请检查信息是否填写完整");
    	}
    	return flag;
    }
    
    function clearText(obj)
    {
    	document.getElementById(obj).innerHTML = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    }
    