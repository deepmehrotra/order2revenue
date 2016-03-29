<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
#register {
		position: absolute;
		left: 350px;
		top: 100px;
		width: 280px;
		padding: 10px;
		display: none;
       }
      
</style>
<label onclick="setVisibility('register', 'inline');" style='cursor: pointer;'>Add Plan</label> |


<hr  style='border:2px solid orange;'/>

<script language="JavaScript">
      function setVisibility(id, visibility) {
      document.getElementById(id).style.display = visibility;
      }

</script>
  <c:if test="${!empty plans}">
  <h2>List Categories</h2>
 <table align="left" border="1">
  <tr>
   <th>PID</th>
   <th>Plan Name</th>
   <th>Plan ID</th> 
   <th>Order Count</th>
   <th>Description</th>
   <th>Plan Price</th>
  </tr>

  <c:forEach items="${plans}" var="plan">
   <tr>
    <td><c:out value="${plan.pid}"/></td>
     <td><c:out value="${plan.planName}"/></td>
   
    <td><c:out value="${plan.planId}"/></td>
    <td><c:out value="${plan.orderCount}"/></td>
    <td><c:out value="${plan.description}"/></td>
    <td><c:out value="${plan.planPrice}"/></td>
    <td align="center"><a href="drop.html?id=${plan.pid}">Delete</a></td>
   </tr>
  </c:forEach>
 </table>
</c:if>


<div id="register">
<table border='2' bordercolor='#424242' width='700' class='boxs' height="150">
	<tr>
		<td height='20'bgcolor='#424242' align='right'>
			<table width='697' border='0'>
				<tr>
					<td width='200' align='left' style='color:white;font-family:Segoe UI Light;'>Add Student</td>
					<td align='right'><input type='image' class='hk' src="img/cl.png" height='20'width='20'onclick="setVisibility('register', 'none');"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
	<td bgcolor='white'>  
		<form name="myform" action="savePlan.html" onsubmit="return validate()" method='post'>
			<table cellpadding='10' border='0' align='center' width='600'>
			<tr><td height='15'></td></tr>
			<tr>
			    <td class='label' align='right'>planId</td>
			    <td><input type="text" title="Enter First Name" required name="planId"></td>
			
			    <td class='label' align='right'>orderCount:</td>
			    <td><input type="mobile" title="Enter Last Name" required name="orderCount"></td>
			</tr>
			<tr>
			    <td class='label' align='right'>planName</td>
			    <td><input type="mobile" title="Enter Mobile Number" required name="planName"></td>
			    <td class='label' align='right'>description</td>
			    <td><input type="text"  name="description"></td>
			
			</tr>
			<tr>
			    <td class='label' align='right'>isActive</td>
			    <td>
			    	<select name="isActive">
			    		<option value="true">True</option>
			    		<option value="False">false</option>
			    	</select>
				</td>
			
			    <td class='label' align='right'>Plan Price</td>
			    <td><input type="text"  name="planPrice"></td>
			</tr>			
			 <tr>
			    <td></td>
			    <td colspan='2'><input type="submit" value="Add" name='action'class='buttun' style='background-color:#424242' >
			    &nbsp;<input type="reset" value="Reset"class='buttun'style='background-color:#424242' >
			    &nbsp;<input type="button" value="Cancel"class='buttun'style='background-color:#424242' onclick="setVisibility('register', 'none');" >
			    </td>
			</tr> 
			</table>  
		</form>
	</td>
	</tr>
</table>
</div>