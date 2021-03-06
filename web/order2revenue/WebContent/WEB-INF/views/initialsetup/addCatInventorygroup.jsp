<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>Category to Inventory Group</title>
  <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function submitCategory(){
    	     $.ajax({
                    url: $("#addCategoryForm").attr("action"),
                    context: document.body,
                    type: 'post',
                    data:$("#addCategoryForm").serialize(),
                    success : function(res) {
                                  
                        $("#centerpane").html(res);
                   
                }
             });
      
    };
</script>
 </head>
 <body>
  <h2>Add Category Data</h2>
  <form:form method="POST" action="saveCatInventory.html" id="addCategoryForm">
      <table>
       <tr>
           <td><form:label path="id">Category ID:</form:label></td>
           <td><form:input path="id" value="${category.id}" readonly="true"/></td>
       </tr>
       <tr>
           <td><form:label path="catName">Category Name:</form:label></td>
           <td><form:input path="catName" value="${category.catName}"/></td>
       </tr>
       <tr>
           <td><form:label path="catDescription">Category Desc:</form:label></td>
           <td><form:input path="catDescription" value="${category.catDescription}"/></td>
       </tr>
        <tr>
           <td><form:label path="parentCatName">Chose Parent Category:</form:label></td>
            <td><ul>  
            <form:select path="parentCatName" items="${categorymap}">  
        </form:select></ul></td>  
       </tr>
      
          <tr>
         <td colspan="2"><input type="button" value="Submit" onclick="submitCategory()"></td>
        </tr>
   </table> 
  </form:form>
  
  <c:if test="${!empty categories}">
  <h2>List Categories</h2>
 <table align="left" border="1">
  <tr>
   <th>Category ID</th>
   <th>Category Name</th>
   <th>Category Description</th>
    <th>Parent Category</th>
   
           <th>Actions on Row</th>
  </tr>

  <c:forEach items="${categories}" var="category">
   <tr>
    <td><c:out value="${category.id}"/></td>
    <td><c:out value="${category.catName}"/></td>
    <td><c:out value="${category.catDescription}"/></td>
    <td><c:out value="${category.parentCatName}"/></td>
    <td align="center"><a href="editCategory.html?id=${category.id}">Edit</a> | <a href="deleteCategory.html?id=${category.id}">Delete</a></td>
   </tr>
  </c:forEach>
 </table>
</c:if>
 </body>
</html>