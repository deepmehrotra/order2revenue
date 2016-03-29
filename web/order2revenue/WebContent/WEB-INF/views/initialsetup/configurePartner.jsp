<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Spring MVC Form Handling</title>
 </head>
 <body>
  <h2>Configure Partner</h2>
  <form:form method="POST" action="configurePartner.html">
      <table>
       <tr>
           <td><form:label path="pcId">Partner ID:</form:label></td>
           <td><form:input path="pcId" value="${partner.pcId}" readonly="true"/></td>
       </tr>
       <tr>
           <td><form:label path="pcName">Partner Name:</form:label></td>
           <td><form:input path="pcName" value="${partner.pcName}"/></td>
       </tr>
       <tr>
       <td>
           <input type="radio" name="model" value="dropship" checked> DropShip<br>
  <input type="radio" name="model" value="PO"> PO<br>
  <input type="radio" name="model" value="SOR"> SOR
  
  	<input type="checkbox" name="nrswitch" value="claculateNR"> NR Calculator<br>  
  	</td>
  	
       </tr>
       <tr>
       <td>
       Partner Commission 
        <input type="radio" name="commisiontype" value="fixed"> Fixed
  <input type="radio" name="commisiontype" value="categorywise"> Category Wise
  
  Fixed : <input type="text"  name="fixexpcpercent"/>
  Product Categories : 
  
  	</td>
       </tr>
     
          <tr>
         <td colspan="2"><input type="submit" value="Submit"/></td>
        </tr>
   </table> 
  </form:form>
  
  
 </body>
</html>