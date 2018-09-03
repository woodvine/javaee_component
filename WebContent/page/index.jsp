<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>Welcome To SequoiaDB Spring MVC Demo.</p>
	<form action="<%=contextPath %>/controller/user/login" method="post">
		账号：<input type="text" name="userId"/><br/>
		密码：<input type="password" name="password"/><br/>
		<input type="submit" value="登陆" />
		<input type="hidden" name = "opertionType" value="登陆"/>
		<input type="hidden" name = "opertionModule" value="登陆模块"/>
		<input type="hidden" name = "opertionObj" value="登陆帐号"/>
	</form>
</body>
</html>