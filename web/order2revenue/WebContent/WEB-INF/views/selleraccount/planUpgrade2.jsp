<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.10.1.min.js"></script>


<style type="text/css">
.column {
	background-color: #f2f2f2;
	height: 400px;
	width: 100%;
	margin-top: 20px;
}

table td {
	border: 1px solid #fff;
	height: 40px;
	font-weight: 800;
}
</style>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight"
				id="centerpane" style="background: #fff;">
				<div class="row">
					<div class="col-lg-12">
						<div class="col-lg-6 text-center" style="margin-top: 50px;">
							<c:choose>
								<c:when test='${status =="true"}'>
									<img src="/O2R/seller/img/success_transaction.jpg" alt="check"
										style="float: left;">
									<h1>Payment Successful</h1>
								</c:when>
								<c:otherwise>
									<img src="/O2R/seller/img/fail_transaction.jpg" alt="check"
										style="float: left;">
									<h1>Sorry! Your payment failed.</h1>
								</c:otherwise>
							</c:choose>

							<div>
								<table cellpadding="0" cellspacing="0" width="100%"
									style="position: relative; top: 23px; background-color: #f2f2f2;">
									<tbody>
										<tr>
											<td>Invoice ID:</td>
											<td>${accountTransaction.invoiceId}</td>
										</tr>
										<tr>
											<td>Status:</td>
											<td><c:out value="${accountTransaction.status}" /></td>
										</tr>
										<tr>
											<td>Transaction Details:</td>
											<td><c:out value="${accountTransaction.transactionId}" />
											</td>
										</tr>
										<tr>
											<td>Transaction Date:</td>
											<td><fmt:formatDate type="date"
													value="${accountTransaction.transactionDate}" /></td>
										</tr>
										<tr>
											<td>Total Orders :</td>
											<td><c:out
													value="${accountTransaction.currentOrderCount}" /></td>
										</tr>
										<tr>
											<td>Transaction Amount :</td>
											<td>&#8377; <c:out
													value="${accountTransaction.transactionAmount}" /> /-
											</td>
										</tr>
									</tbody>

								</table>
							</div>
							<br> <br>
							<button class="btn btn-primary"
								style="background-color: #1ab394; color: #fff; margin-left: 16%; box-shadow: -2px 4px 0px #877070;">Print
								Record</button>
						</div>
						<div class="col-lg-6" style="margin-top: 91px;">
							<div class="column text-center m-t">

								<div class="ui button aligned center teal" id="create_pdf"
									style="z-index: 9999999; position: absolute; right: 43%; top: -77px;">
									<img src="img/pdf.png" alt="pdf" width="100%">
								</div>

								<div class="ui page grid"
									style="opacity: 0; margin-left: -200px; height: 130px; margin-top: -193px; margin-right: 60px;">
									<div class="wide column">
										<div class="ui segment">
											<form class="ui form">
												<div class="col-sm-6" style="padding-top: 18px;">
													<h5>From:</h5>
													<address>
														<strong>Bullfrog Business Solutions</strong><br>
														RB-1,2/Flr,Inderpuri,<br> New Delhi-110012<br> <abbr
															title="Phone">P:</abbr>(+91) 9818833133<br> <span><strong>Tin:</strong>AAQFB2105BSD001
														
													</address>
												</div>
												<div class="col-sm-6" style="padding-top: 18px;">
													<h4>Invoice No.</h4>
													<h4 class="text-navy">${accountTransaction.invoiceId}</h4>
													<span>To:</span>
													<address>
														<strong>${myAccount.name}</strong><br>
															${myAccount.address}<br> <abbr
															title="Phone">P:</abbr> ${myAccount.contactNo} <br> <span><strong>Invoice
																Date:</strong> ${accountTransaction.transactionDate}</span>
													</address>
												</div>
												<div class="table m-t" style="padding-right: 75px;">
													<table class="table invoice-table">
														<thead>
															<tr>
																<th>Plan Name</th>
																<th>Order Count</th>
																<th>Plan Price</th>
																<th>Total Price</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td>${myPlan.planName}</td>
																<td>${myPlan.orderCount}</td>
																<td>Rs. ${myPlan.planPrice}</td>
																<td>Rs. ${myPlan.planPrice}</td>
															</tr>
														</tbody>
													</table>
													<table class="table invoice-total">
														<tbody>
															<tr>
																<td><strong>Sub Total :</strong></td>
																<td>Rs. </td>
															</tr>
															<tr>
																<td><strong>Service Tax :</strong></td>
																<td>Rs. </td>
															</tr>
															<tr>
																<td><strong>Krishi Kalyan Cess:</strong></td>
																<td>Rs. </td>
															</tr>
															<tr>
																<td><strong>Swatch Bharth Cess :</strong></td>
																<td>Rs. </td>
															</tr>

															<tr>
																<td><strong>TOTAL :</strong></td>
																<td>Rs. </td>
															</tr>
														</tbody>
													</table>
												</div>
											</form>
										</div>
									</div>
								</div>
								<br>
								<br>

								<script type="text/javascript"
									src="http://cdn.rawgit.com/MrRio/jsPDF/master/dist/jspdf.min.js"></script>
								<script type="text/javascript"
									src="http://cdn.rawgit.com/niklasvh/html2canvas/0.5.0-alpha2/dist/html2canvas.min.js"></script>
								<script type="text/javascript" src="js/app.js"></script>


								<c:choose>
									<c:when test='${status =="true"}'>
										<h1 class="thank">Thank You</h1>
										<h3>For Your Purchase</h3>
										<p>
											O2R has received your payment.In case of any issues,you will
											be required to present <br> record of this transaction
											which can be downloaded by clicking on <br> "Print
											Record" Button.<br> For any other query feel free to
											contact us @ Customer Care.
										</p>
									</c:when>
									<c:otherwise>
										<h1>Sorry for the inconvenience!</h1>
										<h3>Contact admin and know status about your payment!</h3>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>

		</div>
	</div>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>
</html>