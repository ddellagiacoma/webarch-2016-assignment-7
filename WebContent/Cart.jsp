<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="bean.ShoppingCartRemote"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cart</title>
</head>
<body>
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
	<h1>Cart</h1>

	<%
		ShoppingCartRemote sc = (ShoppingCartRemote) session.getAttribute("sc");
		List<String> listCart;
		listCart = sc.getContents();
	%>

	<table>
		<c:forEach var="item" items="<%=listCart%>">
			<tr>
				<td><c:out value="${item}" /></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<table cellspacing="10">
		<tr>
			<td>
				<form action="Buy">
					<input type="submit" value="Buy" />
				</form>
			</td>
			<td><form action="Empty">
					<input type="submit" value="Empty" />
				</form></td>
		</tr>
	</table>
</body>
</html>