<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<!-- Data Tables -->
<link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css"
	rel="stylesheet">

<script src="/O2R/seller/js/jquery-2.1.1.js"></script>
<link href="/O2R/seller/css/jquery-ui.css" rel="stylesheet">
<script src="/O2R/seller/js/jquery-ui-1.10.4.min.js"
	type="text/javascript"></script>
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script>

<script type="text/javascript">
	function onclickGatePass(value, id) {
		var targeturl = "";
		switch (value) {
		case "editOrder":
			targeturl = "editOrderDA.html?orderId=" + id;
			break;
		case "deleteOrder":
			targeturl = "deleteOrderDA.html?orderId=" + id;
			break;
		case "viewOrder":
			targeturl = "viewOrderDA.html?orderId=" + id;
			break;
		case "addOrder":
			targeturl = "addOrderDA.html";
			break;
		case "uploadGatePass":
			targeturl = "uploadOrderDA.html?value=gatepassSummary";
			break;
		case "downloadGatePass":
			targeturl = "downloadOrderDA.html?value=gatepassSummary";
			break;

		case "viewPOOrder":
			targeturl = "viewPOOrderDA.html?orderId=" + id;
			break;
		case "uploadPO":
			targeturl = "uploadOrderDA.html?value=orderPoSummary";
			break;
		case "downloadPO":
			targeturl = "downloadOrderDA.html?value=orderPoSummary";
			break;

		case "viewPOOrderDetails":
			targeturl = "poOrderDetails.html";
			break;
		case "viewGatePassList":
			targeturl = "gatepasslist.html";
			break;
		case "poOrderList":
			targeturl = "poOrderList.html?value=" + id;
			break;
		}
		$.ajax({
			url : targeturl,
			success : function(data) {
				$('#centerpane').html(data);
			}
		});
	}
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated" id="centerpane">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>Disputed Gate-passes(${gatepasses.size()})</h5>
								<div class="table-menu-links">
									<a href="poOrderList.html?value=" id="POOrders">PO Orders</a> <a
										href="#" id="returnOrders">Return</a> <a href="#"
										id="gatepass"
										onclick="onclickGatePass('viewPOOrderDetails','0')">GatePass</a>
									<a href="#" id="paymentOrders">Payment</a> <a href="#"
										id="actionableOrders">Actionable</a>
								</div>
								<div class="ibox-tools">
									<button class="btn btn-white table-menu-search search-dd">
										<i class="fa fa-search"></i>
									</button>
									<div class="search-more-wrp">
										<form role="search" class="form-inline" method="post"
											action="searchOrder.html">
											<div class="form-group">
												<select class="form-control" name="searchOrder"
													id="searchOrders">
													<option id="1" value="channelOrderID">Channel
														OrderId/PO ID</option>
													<option id="2" value="invoiceID">Invoice ID</option>
													<option id="3" value="subOrderID">Sub Order ID</option>
													<option id="4" value="pcName">Partner</option>
													<option id="5" value="customerName">Customer Name</option>
													<option id="6" value="status">Order Status</option>
													<option id="7" value="orderDate">Order Date</option>
													<option id="8" value="shippedDate">Order Shipped
														Date</option>
												</select>
											</div>
											<div class="form-group TopSearch-box001 OrderSearch-box "
												id="searchchannelOrderID">
												<input type="text" placeholder="Enter Channel OrderID"
													class="form-control" name="channelOrderID">
											</div>
											<div class="form-group TopSearch-box002 OrderSearch-box"
												id="SearchorderDate" style="display: none">
												<div class="input-group date">
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span><input type="text"
														class="form-control" placeholder="" name="startDate">
												</div>
												<div class="input-group date">
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span><input type="text"
														class="form-control" placeholder="" name="endDate">
												</div>
											</div>
											<div class="form-group">
												<button class="btn btn-primary btn-block" type="submit">Search</button>
											</div>
										</form>
									</div>
									<span>Last</span>
									<button type="button" id="LoadFirst500"
										class="btn btn-xs btn-white active">500</button>
									<button type="button" class="btn btn-xs btn-white">1000</button>
									<button type="button" id="LoadMoreOrder"
										class="btn btn-xs btn-white">More</button>
								</div>
							</div>
							<div class="bs-example">
								<div class="ibox-content overflow-h cus-table-filters">
									<div class="scroll-y">
										<table
											class="table table-striped table-bordered table-hover dataTables-example">
											<thead>
												<tr>
													<th></th>
													<th>#</th>
													<th>GP ID</th>
													<th>Channel</th>
													<th>Date of Payment</th>
													<th>Negative Amount</th>
													<th>Note</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty gatepasses}">
													<c:forEach items="${gatepasses}" var="gatepass"
														varStatus="loop">
														<tr>
															<td><input type="checkbox"></td>
															<td>${loop.index+1}</td>
															<td>${gatepass.channelOrderID}</td>
															<td>${gatepass.pcName}</td>
															<td><fmt:formatDate
																	value="${gatepass.orderPayment.dateofPayment}"
																	pattern="MMM dd ,YY" /></td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2"
																	value="${gatepass.orderPayment.negativeAmount}" /></td>
															<td>${gatepass.sellerNote}</td>
															<td><a href="#" onclick="onclickGatePass('uploadGatePass',0)">Reconcile</a>&nbsp;&nbsp;
																<a href="#" onclick="onclickSideNavigation('ManualCharges')">Add Manual Charge</a></td>
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
			<jsp:include page="../globalfooter.jsp"></jsp:include>
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
</body>
</html>