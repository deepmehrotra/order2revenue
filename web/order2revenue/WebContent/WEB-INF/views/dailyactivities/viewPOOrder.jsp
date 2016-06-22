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
								<th>PO ID</th>
								<th>Seal No.</th>
								<th>Order Shipped Date</th>
								<th>Expected Payment Date</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${order.pcName}</td>
								<td>${order.invoiceID}</td>
								<td>${order.channelOrderID}</td>
								<td>${order.sealNo}</td>
								<td><fmt:formatDate value="${order.orderDate}"
										pattern="MMM-dd-YYYY" /></td>
								<td><fmt:formatDate value="${order.paymentDueDate}"
										pattern="MMM-dd-YYYY" /></td>
								<td>${order.status}</td>
								<td>${order.finalStatus}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="panel-group accordion" id="accordion1">
			<div class="col-lg-12 order-info-block">
				<div class="float-e-margins col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title ibox-title">
								<a data-toggle="collapse" data-parent="#accordion1"
									href="#collapsetrans">TRANSACTION SUMMARY</a>
							</h4>
						</div>
						<div id="collapsetrans" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="bs-example">
									<div class="ibox-content overflow-h cus-table-filters">
										<div class="scroll-y">
											<table
												class="table table-striped table-bordered table-hover dataTables-example">
												<thead>
													<tr>
														<th>#</th>
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
												<tfoot>
													<tr>
														<th colspan="2" style="text-align: right">Total:</th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
													</tr>
												</tfoot>
												<tbody>
													<c:if test="${!empty orderlist}">
														<c:forEach items="${orderlist}" var="poOrder"
															varStatus="loop">
															<tr>
																<td>${loop.index+1}</td>
																<td>${poOrder.productSkuCode}</td>
																<td><fmt:formatNumber type="number"
																		maxFractionDigits="2"
																		value="${poOrder.orderMRP / poOrder.quantity}" /></td>
																<td><fmt:formatNumber type="number"
																		maxFractionDigits="2"
																		value="${((poOrder.orderMRP / poOrder.quantity) * poOrder.productConfig.discount) / 100}" /></td>
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
																		maxFractionDigits="2"
																		value="${(poOrder.pr  / poOrder.quantity)}" /></td>
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
													"sSwfPath" : "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
												},
												"columns" : [ {
													"width" : "1%"
												}, {
													"width" : "10%"
												}, {
													"width" : "5%"
												}, {
													"width" : "5%"
												}, {
													"width" : "8%"
												}, {
													"width" : "10%"
												}, {
													"width" : "5%"
												}, {
													"width" : "8%"
												}, {
													"width" : "5%"
												}, {
													"width" : "8%"
												}, {
													"width" : "10%"
												}, {
													"width" : "10%"
												}, {
													"width" : "5%"
												}, {
													"width" : "10%"
												} ],
												"footerCallback" : function(
														row, data, start, end,
														display) {
													var api = this.api(), data;

													// Remove the formatting to get integer data for summation
													var intVal = function(i) {
														return typeof i === 'string' ? i
																.replace(
																		/[\$,]/g,
																		'') * 1
																: typeof i === 'number' ? i
																		: 0;
													};

													// Total over all pages
													total2 = api
															.column(2)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total3 = api
															.column(3)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total4 = api
															.column(4)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total5 = api
															.column(5)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total6 = api
															.column(6)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total7 = api
															.column(7)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total8 = api
															.column(8)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total9 = api
															.column(9)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total10 = api
															.column(10)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total11 = api
															.column(11)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total12 = api
															.column(12)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													total13 = api
															.column(13)
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);

													// Total over this page
													pageTotal2 = api
															.column(
																	2,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal3 = api
															.column(
																	3,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal4 = api
															.column(
																	4,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal5 = api
															.column(
																	5,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal6 = api
															.column(
																	6,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal7 = api
															.column(
																	7,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal8 = api
															.column(
																	8,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal9 = api
															.column(
																	9,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal10 = api
															.column(
																	10,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal11 = api
															.column(
																	11,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal12 = api
															.column(
																	12,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);
													pageTotal13 = api
															.column(
																	13,
																	{
																		page : 'current'
																	})
															.data()
															.reduce(
																	function(a,
																			b) {
																		return intVal(a)
																				+ intVal(b);
																	}, 0);

													// Update footer
													$(api.column(2).footer())
															.html(
																	pageTotal2
																			.toFixed(2)
																			+ "<br>("
																			+ total2
																					.toFixed(2)
																			+ ")");
													$(api.column(3).footer())
															.html(
																	pageTotal3
																			.toFixed(2)
																			+ "<br>("
																			+ total3
																					.toFixed(2)
																			+ ")");
													$(api.column(4).footer())
															.html(
																	pageTotal4
																			.toFixed(2)
																			+ "<br>("
																			+ total4
																					.toFixed(2)
																			+ ")");
													$(api.column(5).footer())
															.html(
																	pageTotal5
																			.toFixed(2)
																			+ "<br>("
																			+ total5
																					.toFixed(2)
																			+ ")");
													$(api.column(6).footer())
															.html(
																	pageTotal6
																			.toFixed(2)
																			+ "<br>("
																			+ total6
																					.toFixed(2)
																			+ ")");
													$(api.column(7).footer())
															.html(
																	pageTotal7
																			.toFixed(2)
																			+ "<br>("
																			+ total7
																					.toFixed(2)
																			+ ")");
													$(api.column(8).footer())
															.html(
																	pageTotal8
																			.toFixed(2)
																			+ "<br>("
																			+ total8
																					.toFixed(2)
																			+ ")");
													$(api.column(9).footer())
															.html(
																	pageTotal9
																			.toFixed(2)
																			+ "<br>("
																			+ total9
																					.toFixed(2)
																			+ ")");
													$(api.column(10).footer())
															.html(
																	pageTotal10
																			.toFixed(2)
																			+ "<br>("
																			+ total10
																					.toFixed(2)
																			+ ")");
													$(api.column(11).footer())
															.html(
																	pageTotal11
																			.toFixed(2)
																			+ "<br>("
																			+ total11
																					.toFixed(2)
																			+ ")");
													$(api.column(12).footer())
															.html(
																	pageTotal12
																			.toFixed(2)
																			+ "<br>("
																			+ total12
																					.toFixed(2)
																			+ ")");
													$(api.column(13).footer())
															.html(
																	pageTotal13
																			.toFixed(2)
																			+ "<br>("
																			+ total13
																					.toFixed(2)
																			+ ")");
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