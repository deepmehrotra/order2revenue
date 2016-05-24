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
																	<th>Gross Commission to be paid</th>
																	<th>Return Commission</th>
																	<th>Additional Return Charges</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByGrossComm}">
																	<c:forEach items="${partnerByGrossComm}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.grossPartnerCommissionPaid}</td>
																			<td>${partnerDto.netReturnCommission}</td>
																			<td>${partnerDto.additionalReturnCharges}</td>
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
																id="line-chart-partner-gross-comm"></div>
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
																	<th>Net Channel Commission</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByNetChann}">
																	<c:forEach items="${partnerByNetChann}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.netChannelCommissionToBePaid}</td>
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
																id="bar-chart-partner-net-chann"></div>
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
																	<th>Gross Commission to be paid</th>
																	<th>Return Commission</th>
																	<th>Additional Return Charges</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByGrossComm}">
																	<c:forEach items="${categoryByGrossComm}"
																		var="categoryDto" varStatus="loop">
																		<tr>
																			<td>${categoryDto.categoryName}</td>
																			<td>${categoryDto.grossPartnerCommissionPaid}</td>
																			<td>${categoryDto.netReturnCommission}</td>
																			<td>${categoryDto.additionalReturnCharges}</td>
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
																id="line-chart-category-gross-comm"></div>
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
																	<th>Net Channel Commission</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByNetChann}">
																	<c:forEach items="${categoryByNetChann}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.categoryName}</td>
																			<td>${categoryDto.netChannelCommissionToBePaid}</td>
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
																id="bar-chart-category-net-chann"></div>
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
													<div>
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Net Sale Qty</th>
																	<th>Gross Partner Commission</th>
																	<th>SR Commission</th>
																	<th>Net Partner Commission</th>
																	<th>Net TDS to be deposited</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTablePartner}">
																	<c:forEach items="${shortTablePartner}" var="partner"
																		varStatus="loop">
																		<tr>
																			<td>${partner.partner}</td>
																			<td>${partner.netSaleQty}</td>
																			<td>${partner.grossPartnerCommissionPaid}</td>
																			<td>${partner.netSrCommisison}</td>
																			<td>${partner.netPartnerCommissionPaid}</td>
																			<td>${partner.netTDSToBeDeposited}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>

													<div class="ibox-content">
														<div id="morris-line-chart"></div>
													</div>
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div>
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Net Sale Qty</th>
																	<th>Gross Partner Commission</th>
																	<th>SR Commission</th>
																	<th>Net Partner Commission</th>
																	<th>Net TDS to be deposited</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTableCategory}">
																	<c:forEach items="${shortTableCategory}" var="category"
																		varStatus="loop">
																		<tr>
																			<td>${category.categoryName}</td>
																			<td>${category.netSaleQty}</td>
																			<td>${category.grossPartnerCommissionPaid}</td>
																			<td>${category.netSrCommisison}</td>
																			<td>${category.netPartnerCommissionPaid}</td>
																			<td>${category.netTDSToBeDeposited}</td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>

													<div class="ibox-content">
														<div id="morris-line-chart"></div>
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
		var partnerByGrossCommArr = [];
		var i = 1;
		<c:forEach items="${partnerByGrossComm}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.grossPartnerCommissionPaid}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp1.push(arr1);
		partnerByGrossCommArr.push(arr2);
		</c:forEach>

		var temp2 = [];
		var partnerByNetChannArr = [];
		var i = 1;
		<c:forEach items="${partnerByNetChann}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netChannelCommissionToBePaid}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp2.push(arr1);
		partnerByNetChannArr.push(arr2);
		</c:forEach>

		var temp3 = [];
		var categoryByGrossCommArr = [];
		var i = 1;
		<c:forEach items="${categoryByGrossComm}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.grossPartnerCommissionPaid}' ];
		var arr2 = [ i++, '${categoryDto.categoryName}' ];
		temp3.push(arr1);
		categoryByGrossCommArr.push(arr2);
		</c:forEach>

		var temp4 = [];
		var categoryByNetChannArr = [];
		var i = 1;
		<c:forEach items="${categoryByNetChann}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netChannelCommissionToBePaid}' ];
		var arr2 = [ i++, '${categoryDto.categoryName}' ];
		temp4.push(arr1);
		categoryByNetChannArr.push(arr2);
		</c:forEach>

		$(window)
				.load(
						function() {

							flotbar(temp2, partnerByNetChannArr,
									"#bar-chart-partner-net-chann");
							flotbar(temp4, categoryByNetChannArr,
									"#bar-chart-category-net-chann");
							flotline(temp1, partnerByGrossCommArr,
									"#line-chart-partner-gross-comm");
							flotline(temp3, categoryByGrossCommArr,
									"#line-chart-category-gross-comm");

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