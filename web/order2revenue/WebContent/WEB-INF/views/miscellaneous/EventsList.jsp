<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All Products</title>
</head>
<body>
<h1>List Events</h1>
<h3><a href="addProduct.html">Add More Events</a></h3>

<c:if test="${!empty events}">
 <table align="left" border="1">
  	<tr>
		<th>Event Name</th>
		<th>Event Created Date</th>
		<th>Event Channel</th>
		<th>Event Start Date</th>
		<th>Event End Date</th>
		<th>Event Status</th>
		<th>Net Sales Quantity</th>
		<th>Net Sales Amount</th>
	</tr>

  <c:forEach items="${events}" var="event">
   <tr>
   	<td><c:out value="${event.eventName}"/></td>
    <td><c:out value="${event.eventCreatedDate}"/></td>
    <td><c:out value="${event.eventChannel}"/></td>
    <td><c:out value="${event.eventStartDate}"/></td>
    <td><c:out value="${event.eventEndDate}"/></td>
    <td><c:out value="${event.eventStatus}"/></td>
    <td><c:out value="${event.netSalesQuantity}"/></td>
    <td><c:out value="${event.netSalesAmount}"/></td>
   </tr>
  </c:forEach>
 </table>
</c:if>
</body>
</html>