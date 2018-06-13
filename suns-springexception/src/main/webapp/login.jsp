<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form action="${ctx}/j_spring_security_check" method="post">
		username: <input type="text" name="j_username"><br>
		password: <input type="password" name="j_password"><br>
		<input type="submit" value="登录">
	</form>
</body>
</html>