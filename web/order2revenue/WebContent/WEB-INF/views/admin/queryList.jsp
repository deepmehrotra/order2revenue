<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All Queries</title>
</head>
<body>
<h1>List Queries</h1>



 <table align="left" border="1">
  <tr>
   <th>Query ID</th>
   <th> Name</th>
   <th>Email</th>
   <th>Date</th>
    <th>Query</th>
  </tr>
<c:if test="${!empty queries}">
  <c:forEach items="${queries}" var="query">
   <tr>
   <td><c:out value="${query.queryId}"/></td>
    <td><c:out value="${query.name}"/></td>
    <td><c:out value="${query.email}"/></td>
     <td><c:out value="${query.queryTime}"/></td>
    <td><c:out value="${query.queryText}"/></td>
   </tr>
  </c:forEach>
  </c:if>
 </table>

</body>
</html>