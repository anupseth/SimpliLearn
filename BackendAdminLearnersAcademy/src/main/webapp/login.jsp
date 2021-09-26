<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1 style="color: blue;" align="center">Welcome To Learners
		Academy</h1>
	<hr>

<c:if test="${ErrMsg != null}">
	<h4 style="color: red;"> <c:out value='${ErrMsg}' /> </h4>
</c:if>

	<form action="login" method="post">
		Name:<input type="text" name="userName" value="servlet"/><br /> Password:<input
			type="password" name="userPass" value="servlet"/><br /> <input type="submit"
			value="login" />
	</form>


</body>
</html>