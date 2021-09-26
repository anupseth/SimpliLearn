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

	<center>
		<h1 style="color: blue;">Welcome To Learners Academy</h1>
<hr>
		<h1>User Management</h1>
        <h2>
        	<a href="addStudent">Add Students to class</a>
        	&nbsp;&nbsp;&nbsp;
        	<a href="addSubject">Add Subjects And Teacher to class</a>
        	&nbsp;&nbsp;&nbsp;
        	<a href="showReportForm">Show Class report</a>
        	
        </h2>
	</center>

	<div align="center">

<c:if test="${ErrMsg != null}">
	<h4 style="color: red;"> <c:out value='${ErrMsg}' /> </h4>
</c:if>

		<form action="insertSubject" method="post">

			<table border="1" cellpadding="5">

				<tr>
					<th>Class</th>
					<td><select name="classesList">
							<c:forEach var="cls" items="${classes}">
								<option value="<c:out value='${cls.id}' />">
									<c:out value='${cls.name}' />
								</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<th>Subject</th>
					<td><select name="subjectsList">
							<c:forEach var="stu" items="${subjects}">
								<option value="<c:out value='${stu.id}' />">
									<c:out value='${stu.name}' />
								</option>
							</c:forEach></td>
				</tr>
				
				<tr>
					<th>Teacher</th>
					<td><select name="teachersList">
							<c:forEach var="tea" items="${teachers}">
								<option value="<c:out value='${tea.id}' />">
									<c:out value='${tea.name}' />
								</option>
							</c:forEach>
					</select></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Add"></td>
				</tr>
			</table>

		</form>


<c:if test="${requestScope.SucMsg != null }">
	<c:out value="${requestScope.SucMsg }"> </c:out>
</c:if>


	</div>

</body>
</html>