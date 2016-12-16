<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:set var="list" value="${list}" />
	<table cellspacing="10">
		<tr>
			<td><a href='Home'>Home</a></td>
			<td><a href='Cart.jsp'>Cart</a></td>
			<c:if test="${sessionScope.username=='admin'}">
				<td><a href='InsertBook.jsp'>Insert new books</a></td>
				<td><a href='Operations'>List operations</a></td>
			</c:if>
		</tr>
	</table>
<h1>These are all the transactions carried out:</h1>
	<table>
		<c:forEach items="${list}" var="item">
			<tr>
				<td><c:out value="${item}" /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>