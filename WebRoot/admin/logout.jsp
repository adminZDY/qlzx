<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	 System.out.print("执行注销");
	 System.out.print(session != null?"session为不空":"session为空");
	 //HttpSession session = request.getSession(); 
	 if(session != null)
	 {
	 	session.setMaxInactiveInterval(0);
	 	//session.invalidate(); 
	 }
%>

