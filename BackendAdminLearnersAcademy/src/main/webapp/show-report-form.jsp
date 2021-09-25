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
			<a href="addStudent">Add Students to class</a> &nbsp;&nbsp;&nbsp; <a
				href="addSubject">Add Subjects And Teacher to class</a>
			&nbsp;&nbsp;&nbsp; <a href="showReportForm">Show Class report</a>

		</h2>
	</center>
	
	

	<div align="center">



		<form action="showReport" method="post">

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
					<td colspan="2" align="center"><input type="submit"
						value="Submit"></td>
				</tr>
			</table>

		</form>

		<br />
		
		<!-- Depending on condition we will display the report -->
		<c:if test="${showReport}">
			
			
				<h3>Showing Report for 
		
		<c:if test="${selClass != null}">
			<c:out value='${selClass.name}' /> 
		</c:if> 
		
		
		!!!</h3>

		<table border="1" cellpadding="5">

			<tr>
				<th>Students</th>
				<td>
				<c:if test="${empty students}">
				There are no students assigned to this class yet
        		</c:if> 
        		
        		<c:if test="${students != null}">
						<ul>
							<c:forEach var="stu" items="${students}">

								<li><c:out value='${stu.name}' /></li>
							</c:forEach>

						</ul>
					</c:if>
					
					</td>
			</tr>

			<tr>
				<th>Subjects And Teachers</th>
				<td>
				
				<c:if test="${empty subjects}">
				There are no Subjects and Teachers assigned to this class yet
        		</c:if> 
        		
        		
				<c:if test="${subjects != null}">
					<ul>
						<c:forEach var="stu" items="${subjects}">

							<li><c:out value='${stu.teacher.name}' /> Teaches <c:out
									value='${stu.name}' /></li>
						</c:forEach>

					</ul>
					
					</c:if> 
				</td>

			</tr>

		</table>
			
			
		</c:if>
		

	




	</div>

</body>
</html>