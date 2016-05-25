<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="bs-example">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#sectionA">Monthly
				</a></li>
				<li><a data-toggle="tab" href="#sectionB">Annually</a></li>
			</ul>
			<div class="tab-content">
				<div id="sectionA" class="tab-pane fade in active">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<table
								class="table table-striped table-bordered table-hover dataTables-example">
								<thead>
									<tr>
										<th rowspan="2">PERIOD</th>
										<th rowspan="2">DEBITS</th>
										<th colspan="3" style="text-align: center;">CREDITS</th>
										<th rowspan="2">PAYMENT DIFF</th>
										<th rowspan="2">CLOSING BALANCE</th>
									</tr>
									<tr>
										<th>PAYMENTS</th>
										<th>MANUAL CHARGES</th>
										<th>EOSS</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty poPaymentListMonthly}">
										<c:forEach items="${poPaymentListMonthly}" var="poPayment"
											varStatus="loop">
											<tr>
												<td><a href="#"
													onclick="onclickNavigateOrder('poOrderList','${poPayment.paymentDetail}')">${poPayment.paymentDetail}</a></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${poPayment.debits}" /></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${poPayment.payments}" /></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${poPayment.manualCharges}" /></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${poPayment.eoss}" /></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${poPayment.paymentDiff}" /></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${poPayment.closingBal}" /></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div id="sectionB" class="tab-pane fade"></div>
				<div class="col-lg-12">
					<div class="ibox float-e-margins">
						<table
							class="table table-striped table-bordered table-hover dataTables-example">
							<thead>
								<tr>
									<th rowspan="2">PERIOD</th>
									<th rowspan="2">DEBITS</th>
									<th colspan="3" style="text-align: center;">CREDITS</th>
									<th rowspan="2">PAYMENT DIFF</th>
									<th rowspan="2">CLOSING BALANCE</th>
								</tr>
								<tr>
									<th>PAYMENTS</th>
									<th>MANUAL CHARGES</th>
									<th>EOSS</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${!empty poPaymentListYearly}">
									<c:forEach items="${poPaymentListYearly}" var="poPayment"
										varStatus="loop">
										<tr>
											<td><a href="#"
												onclick="onclickNavigateOrder('poOrderList','${poPayment.paymentDetail}')">${poPayment.paymentDetail}</a></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.debits}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.payments}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.manualCharges}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.eoss}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.paymentDiff}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.closingBal}" /></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>