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
	<form action="<%=contextPath %>/controller/user/addUser" method="post">
		账号：<input type="text" name="userId"/><br/>
		密码：<input type="password" name="password"/><br/>
		<input type="submit" value="添加" />
		<input type="hidden" name = "opertionType" value="添加"/>
		<input type="hidden" name = "opertionModule" value="帐号模块"/>
		<input type="hidden" name = "opertionObj" value="添加帐号"/>
	</form>
	
</body>
</html>