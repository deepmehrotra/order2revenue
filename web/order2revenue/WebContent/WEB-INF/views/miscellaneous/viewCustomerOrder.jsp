<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			
			<div class="ibox float-e-margins">
				<div class="ibox-title ">
					<h5>Customer Info</h5>
				</div>

				<div class="ibox-content add-company ">
					<table class="table table-bordered custom-table">
						<thead>
							<tr>
								<th>Customer Name</th>
								<th>Products Purchased</th>
								<th>Product Returned</th>
								<th>Net Products Purchased</th>
								<th>Net Revenue Earned</th>
								<th>Contact</th>								
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${name}</td>
								<td>${purchased}</td>
								<td>${returned}</td>
								<td>${netPurchased}</td>
								<td><fmt:formatNumber type="number"	maxFractionDigits="2"
												value="${revenue}"/></td>
								<td>${email}</td>								
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="ibox-content add-company ">
				<table class="table table-bordered custom-table">
					<thead>
						<tr>
							<th>SKU'S Order</th>
							<th>Order Receive Date</th>
							<th>Order Return Date</th>
							<th>Pincode</th>
							<th>City</th>
							<th>Channel</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${!empty orderList}">
							<c:forEach items="${orderList}" var="order" varStatus="loop">
								<tr>
									<td>${order.productSkuCode}</td>
									<td><fmt:formatDate	value="${order.orderDate}" pattern="MMM dd,YY" /></td>
									<td><fmt:formatDate	value="${order.orderReturnOrRTO.returnDate}" pattern="MMM dd,YY" /></td>
									<td>${order.customer.zipcode }</td>
									<td>${order.customer.customerCity }</td>
									<td>${order.pcName }</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>
		</div>		
	</div>
	<jsp:include page="../globaljslinks.jsp"></jsp:include>
</body>
</html>