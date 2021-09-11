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



		<form action="insertStudent" method="post">

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
					<th>Student</th>
					<td><select name="studentsList">
							<c:forEach var="stu" items="${students}">
								<option value="<c:out value='${stu.id}' />">
									<c:out value='${stu.name}' />
								</option>
							</c:forEach></td>
				</tr>
				
				<tr>
					<td colspan="2" align="center"><input type="submit" value="Add Student"></td>
				</tr>
			</table>

		</form>




	</div>

</body>
</html>