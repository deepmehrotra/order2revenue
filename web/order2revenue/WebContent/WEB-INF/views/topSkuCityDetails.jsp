<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

</head>
<body>

	<h2>
		<small>Select Headers</small>
	</h2>


	<div>
		<form id=quarter method="POST" action="quaterlyindex.html">
			<label id="hello">Choose your Quarter</label> <select id="colour1"
				name="colour1">
				<option value="dropdown">Pls select one
				<option value="Jan">Monthly
				<option value="Feb">Quarterly
				<option value="March">HalfYearly
				<option value="April">Annually
			</select> <input type="submit" class="btn btn-primary block full-width m-b"
				value="Submit" onclick="onclickQSubmitButton()" />
		</form>
	</div>

	<h2>
		<small></small>
	</h2>
	<div>
		<table border="1" cellpading="5"
			class="table table-bordered custom-table">
			<thead>
				<tr>
					<th>State</th>
					<th>Sale Qty</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${productList}" var="viewDetailsmp1">
					<tr>
						<td><c:out value="${viewDetailsmp1.skucode}" /></td>
						<td><c:out value="${viewDetailsmp1.netSaleQty}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
