<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
body.DTTT_Print {
	background: #fff;
}

.DTTT_Print #page-wrapper {
	margin: 0;
	background: #fff;
}

button.DTTT_button, div.DTTT_button, a.DTTT_button {
	border: 1px solid #e7eaec;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

button.DTTT_button:hover, div.DTTT_button:hover, a.DTTT_button:hover {
	border: 1px solid #d2d2d2;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

.dataTables_filter label {
	margin-right: 5px;
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Order : ${order.channelOrderID}</h5>
				</div>
				<div class="ibox-content view-order">

					<div class="time-line-wrp">
						<ul>
							<c:if test="${!empty order.orderTimeline}">
								<c:forEach items="${order.orderTimeline}" var="timeline"
									varStatus="loop">
									<li class="active"><i class="fa fa-check"></i> <span><fmt:formatDate
												value="${timeline.eventDate}" pattern="MMM-dd-YYYY" /></span>
										<p>${timeline.event}</p></li>

								</c:forEach>
							</c:if>
						</ul>
					</div>

				</div>
			</div>
		</div>
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title ">
					<h5>Order Info</h5>
				</div>

				<div class="ibox-content add-company view-order">
					<table class="table table-bordered custom-table">
						<thead>
							<tr>
								<th>Partner</th>
								<th>Invoice Id</th>
								<th>PI Reference No</th>
								<th>Sub Order Id</th>
								<th>Order Date</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${order.pcName}</td>
								<td>${order.invoiceID}</td>
								<td>${order.PIreferenceNo}</td>
								<td>${order.subOrderID}</td>
								<td><fmt:formatDate value="${order.orderDate}"
										pattern="MMM-dd-YYYY" /></td>
								<td>${order.status}</td>
								<td>${order.finalStatus}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-lg-12 order-info-block">
			<div class="float-e-margins col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title ibox-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapsetrans">TRANSACTION SUMMARY</a>
						</h4>
					</div>
					<div id="collapsetrans" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="ibox-content add-company view-order">
								<div class="table-format-wrapper three-data-width">
									<table
										class="table table-striped table-bordered table-hover dataTables-example">
										<thead>
											<tr>
												<th>SKU</th>
												<th>MRP</th>
												<th>SP</th>
												<th>CUSTOMER DISC</th>
												<th>CHANNEL COMMISSION</th>
												<th>EOSS DISC</th>
												<th>N/R</th>
												<th>TAX</th>
												<th>P/R</th>
												<th>SUGGESTED <br>P/O PRICE
												</th>
												<th>P/O PRICE</th>
												<th>QTY</th>
												<th>SUBBTOTAL</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty orderlist}">
												<c:forEach items="${orderlist}" var="poOrder"
													varStatus="loop">
													<tr>
														<td>${poOrder.productSkuCode}</td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2" value="${poOrder.orderMRP / poOrder.quantity}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2" value="${((poOrder.orderMRP / poOrder.quantity) * poOrder.productConfig.discount) / 100}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2"
																value="${(poOrder.orderMRP / poOrder.quantity) - (((poOrder.orderMRP / poOrder.quantity) * poOrder.productConfig.discount) / 100)}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2"
																value="${poOrder.productConfig.commisionAmt}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2" value="${poOrder.eossValue}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2" value="${poOrder.grossNetRate}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2" value="${poOrder.orderTax.tax}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2" value="${(poOrder.pr  / poOrder.quantity)}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2"
																value="${poOrder.productConfig.suggestedPOPrice}" /></td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2" value="${poOrder.poPrice}" /></td>
														<td>${poOrder.quantity}</td>
														<td><fmt:formatNumber type="number"
																maxFractionDigits="2"
																value="${poOrder.totalAmountRecieved}" /></td>
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
		</div>
		<div class="col-lg-12 order-info-block">
			<div class="float-e-margins col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title ibox-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapsecust">CUSTOMER INFO</a>
						</h4>
					</div>
					<div id="collapsemer" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="ibox-content add-company view-order">

								<div class="table-format-wrapper three-data-width">
									<div>
										<span>Name</span> <span></span> <span>${order.customer.customerName}</span>
									</div>
									<div>
										<span>Contact</span> <span></span> <span>${order.customer.customerPhnNo}</span>
									</div>
									<div>
										<span>Email</span> <span></span> <span>${order.customer.customerEmail}</span>
									</div>
									<div>
										<span>City</span> <span></span> <span>${order.customer.customerCity}</span>
									</div>
									<div>
										<span>Address</span> <span></span> <span>${order.customer.customerAddress}</span>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="float-e-margins col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title ibox-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapsevents">EVENTS INFO</a>
						</h4>
					</div>
					<div id="collapsevents" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="ibox-content add-company view-order">
								<div class="table-format-wrapper three-data-width"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="float-e-margins col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title ibox-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapsenotes">SELLER NOTES</a>
						</h4>
					</div>
					<div class="ibox-content add-company view-order">

						<blockquote>
							<p>${order.sellerNote}</p>
						</blockquote>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>

	<!-- Scripts ro Table -->



	<!-- Data Tables -->
	<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>

	<script>
		$(document)
				.ready(
						function() {
							$('.dataTables-example')
									.dataTable(
											{
												responsive : true,
												"dom" : 'T<"clear">lfrtip',
												"tableTools" : {
													"sSwfPath" : "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
												}
											});

							$('#searchOrder').change(function() {
								$('.OrderSearch-box').hide();
								$('#' + $(this).val()).fadeIn();
							});
							$('.search-dd').on('click', function(e) {
								e.stopPropagation();
								$('.search-more-wrp').slideToggle();
							});
							$('.input-group.date5').datepicker({
								todayBtn : "linked",
								keyboardNavigation : false,
								forceParse : false,
								calendarWeeks : true,
								autoclose : true
							});

							$('#LoadMoreOrder')
									.click(
											function(e) {
												e.preventDefault();
												pageno = parseInt(getQueryVariable("page"));
												pageno = pageno + 1;
												window.location = "orderList.html?page="
														+ pageno;

											});
							$('#LoadFirst500').click(function(e) {
								window.location = "orderList.html?page=" + 0;

							});
							$('#returnOrders')
									.click(
											function(e) {
												window.location = "orderList.html?status=return";

											});
							$('#paymentOrders')
									.click(
											function(e) {
												window.location = "orderList.html?status=payment";

											});
							$('#actionableOrders')
									.click(
											function(e) {
												window.location = "orderList.html?status=actionable";

											});

							$("#form").validate({
								rules : {
									number : {
										required : true,
										number : true
									}
								}
							});
						});

		function getQueryVariable(variable) {
			var query = window.location.search.substring(1);
			var vars = query.split("&");
			for (var i = 0; i < vars.length; i++) {
				var pair = vars[i].split("=");
				if (pair[0] == variable) {
					return pair[1];
				}
			}
			return 0;
		}
	</script>
</body>
</html>