<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Plan Upgrade <%=session.getAttribute("sellerId") %></h1>

<c:if test="${!empty upgrade}">
 <table align="left" border="1" width="800px" align="center" cellpadding="8">
  <tr>
   <th>#</th>
   <th>Plan ID</th>
   <th>Plan Name</th>
   <th>description</th>
   <th>orderCount</th>
   <th>planPrice</th>
    <th>planId</th>
   
   
  </tr>

  <c:forEach items="${upgrade}" var="up">
   <tr>
    <td><c:out value="${up.pid}"/></td>
    <td><c:out value="${up.planName}"/></td>
    <td><c:out value="${up.description}"/></td>
    <td><c:out value="${up.orderCount}"/></td>
    <td><c:out value="${up.planPrice}"/></td>
    <td><c:out value="${up.planId}"/></td>
   <td> <a href="upgrade2.html?pid=${up.pid}">Upgrade</a></td>
    
   </tr>
  </c:forEach>
 </table>
</c:if>