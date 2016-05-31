<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<jsp:include page="../globalcsslinks.jsp"></jsp:include>

<!-- orris -->
<link href="/O2R/seller/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">

<!-- Data Tables -->
<link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css"
	rel="stylesheet">

</head>

<body>

	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight"
				id="centerpane">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>Reports</h5>
							</div>
							<div class="ibox-content overflow-h">

								<div class="panel-options">

									<ul class="nav nav-tabs">
										<li class="active"><a data-toggle="tab" href="#tab-1">View
												Graph</a></li>
										<li class=""><a data-toggle="tab" href="#tab-2">View
												Report</a></li>
									</ul>
								</div>
								<div class="tab-content">
									<div id="tab-1" class="tab-pane active col-sm-12 chart-even">										
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Net NPR</th>
																	<th>Net Payment Difference</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByNPR}">
																	<c:forEach items="${partnerByNPR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.netPaymentResult}</td>
																			<td>${partnerDto.paymentDifference}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="line-chart-partner-npr"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Net Payment Difference Order Qty</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByNPDQY}">
																	<c:forEach items="${partnerByNPDQY}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.netPayDiffOrderQty}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="bar-chart-partner-npdqy"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Upcoming Payments N/R</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByUPNR}">
																	<c:forEach items="${partnerByUPNR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.upcomingPaymentNR}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="bar-chart-partner-upnr"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Net NPR</th>
																	<th>Net Payment Difference</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByNPR}">
																	<c:forEach items="${categoryByNPR}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td>${categoryDto.netPaymentResult}</td>
																			<td>${categoryDto.paymentDifference}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="line-chart-category-npr"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Net Payment Difference Order Qty</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByNPDQY}">
																	<c:forEach items="${categoryByNPDQY}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td>${categoryDto.netPayDiffOrderQty}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="bar-chart-category-npdqy"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Upcoming Payments N/R</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByUPNR}">
																	<c:forEach items="${categoryByUPNR}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td>${categoryDto.upcomingPaymentNR}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div class="flot-chart">
															<div class="flot-chart-content"
																id="bar-chart-category-upnr"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-2" class="tab-pane col-sm-12">	
										<div class="row">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div style="overflow-y: hidden;overflow-x: scroll;">
														<table class="table table-bordered custom-table" style="margin-bottom: auto;">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Upcoming Payments N/R</th>
																	<th>Net NPR</th>
																	<th>Net Payment Difference</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTablePartner}">
																	<c:forEach items="${shortTablePartner}" var="partner"
																		varStatus="loop">
																		<tr>
																			<td>${partner.partner}</td>
																			<td>${partner.upcomingPaymentNR}</td>
																			<td>${partner.netPaymentResult}</td>
																			<td>${partner.paymentDifference}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="margin-top: 20px;">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div style="overflow-y: hidden;overflow-x: scroll;">
														<table class="table table-bordered custom-table" style="margin-bottom: auto;">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Upcoming Payments N/R</th>
																	<th>Net NPR</th>
																	<th>Net Payment Difference</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTableCategory}">
																	<c:forEach items="${shortTableCategory}" var="category"
																		varStatus="loop">
																		<tr>
																			<td>${category.category}</td>
																			<td>${category.upcomingPaymentNR}</td>
																			<td>${category.netPaymentResult}</td>
																			<td>${category.paymentDifference}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>

										<div class="col-sm-12">
											<div class="hr-line-dashed"></div>
											<button class="btn btn-primary pull-right" type="submit">Print</button>
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
	</div>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>

	<!-- Flot -->
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.resize.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.pie.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.time.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.axislabels.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.symbol.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.spline.js"></script>
	<script src="/O2R/seller/js/plugins/flot/jquery.flot.resize.js"></script>

	<!-- Morris -->
	<script src="/O2R/seller/js/plugins/morris/raphael-2.1.0.min.js"></script>
	<script src="/O2R/seller/js/plugins/morris/morris.js"></script>

	<!-- Morris demo data-->
	<script src="/O2R/seller/js/demo/morris-demo.js"></script>

	<!-- ChartJS-->
	<script src="/O2R/seller/js/plugins/chartJs/Chart.min.js"></script>
	<script src="/O2R/seller/js/demo/flot-demo-1.js"></script>


	<!-- Data Tables -->
	<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
	<script>
		//Script for Bar Chart

		var temp1 = [];
		var partnerByNPR = [];
		var i = 1;
		<c:forEach items="${partnerByNPR}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netPaymentResult}' ];
		var arr2 = [ i, '${partnerDto.partner}' ];
		i++;
		temp1.push(arr1);
		partnerByNPR.push(arr2);
		</c:forEach>

		var temp2 = [];
		var categoryByNPR = [];
		var i = 1;
		<c:forEach items="${categoryByNPR}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netPaymentResult}' ];
		var arr2 = [ i, '${categoryDto.category}' ];
		i++;
		temp2.push(arr1);
		categoryByNPR.push(arr2);
		</c:forEach>
		
		var temp3 = [];
		var partnerByNPDQY = [];
		var i = 1;
		<c:forEach items="${partnerByNPDQY}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netPayDiffOrderQty}' ];
		var arr2 = [ i, '${partnerDto.partner}' ];
		i++;
		temp3.push(arr1);
		partnerByNPDQY.push(arr2);
		</c:forEach>

		var temp4 = [];
		var categoryByNPDQY = [];
		var i = 1;
		<c:forEach items="${categoryByNPDQY}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netPayDiffOrderQty}' ];
		var arr2 = [ i, '${categoryDto.category}' ];
		i++;
		temp4.push(arr1);
		categoryByNPDQY.push(arr2);
		</c:forEach>
		
		var temp5 = [];
		var partnerByUPNR = [];
		var i = 1;
		<c:forEach items="${partnerByUPNR}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.upcomingPaymentNR}' ];
		var arr2 = [ i, '${partnerDto.partner}' ];
		i++;
		temp5.push(arr1);
		partnerByUPNR.push(arr2);
		</c:forEach>

		var temp6 = [];
		var categoryByUPNR = [];
		var i = 1;
		<c:forEach items="${categoryByUPNR}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.upcomingPaymentNR}' ];
		var arr2 = [ i, '${categoryDto.category}' ];
		i++;
		temp6.push(arr1);
		categoryByUPNR.push(arr2);
		</c:forEach>

		$(window)
				.load(
						function() {
							flotline(temp1, partnerByNPR,
									"#line-chart-partner-npr");
							flotline(temp2, categoryByNPR,
									"#line-chart-category-npr");
							flotbar(temp3, partnerByNPDQY,
									"#bar-chart-partner-npdqy");
							flotbar(temp4, categoryByNPDQY,
									"#bar-chart-category-npdqy");
							flotbar(temp5, partnerByUPNR,
									"#bar-chart-partner-upnr");
							flotbar(temp6, categoryByUPNR,
									"#bar-chart-category-upnr");

							$('.dataTables-example')
									.dataTable(
											{
												responsive : true,
												"dom" : 'T<"clear">lfrtip',
												"tableTools" : {
													"sSwfPath" : "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
												}
											});
						});
	</script>
	<style>
body.DTTT_Print {
	background: #fff;
}

.DTTT_Print #page-wrapper {
	margin: 0;
	background: #fff;
}

button.DTTT_button,div.DTTT_button,a.DTTT_button {
	border: 1px solid #e7eaec;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

button.DTTT_button:hover,div.DTTT_button:hover,a.DTTT_button:hover {
	border: 1px solid #d2d2d2;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

.dataTables_filter label {
	margin-right: 5px;
}

div.dataTables_length select {
	padding: 0 10px;
}
</style>
</body>

</html>
