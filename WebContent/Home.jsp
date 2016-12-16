<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
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
	<h1>Hello <c:out value="${sessionScope.username}" /></h1>
	<a href='Logout'>Logout</a>	
	<h2>These are the books available:</h2>
	<table>
		<c:forEach items="${list}" var="item">
			<c:set var="bookParts" value="${fn:split(item, ' ')}" />
			<tr>
				<td><c:out value="${item}" /></td>
				<td><a href='Add?idbook=${bookParts[1]}'>Add to cart</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>