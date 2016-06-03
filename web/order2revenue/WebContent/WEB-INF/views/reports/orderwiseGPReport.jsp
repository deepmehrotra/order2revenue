<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
																	<th>Gross Profit/Loss Amount</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByGrossProfit}">
																	<c:forEach items="${partnerByGrossProfit}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.grossProfit}" /></td>
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
																id="bar-chart-partner-gross-profit"></div>
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
																	<th>% GP Vs Cost of Profit</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByGPCP}">
																	<c:forEach items="${partnerByGPCP}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.gpVsProductCost}" /></td>
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
																id="bar-chart-partner-gpcp"></div>
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
																	<th>P/R</th>
																	<th>Cost of Product</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByPR}">
																	<c:forEach items="${partnerByPR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.netPr}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.netProductCost}" /></td>
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
														<div id="stacked-chart-1"></div>
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
																	<th>N/R</th>
																	<th>Gross Profit</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByGNR}">
																	<c:forEach items="${partnerByGNR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.grossNrAmount}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.grossProfit}" /></td>
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
														<div id="stacked-chart-2"></div>
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
																	<th>Gross Profit/Loss Amount</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByGrossProfit}">
																	<c:forEach items="${categoryByGrossProfit}"
																		var="categoryDto" varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.grossProfit}" /></td>
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
																id="bar-chart-category-gross-profit"></div>
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
																	<th>% GP Vs Cost of Profit</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByGPCP}">
																	<c:forEach items="${categoryByGPCP}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.gpVsProductCost}" /></td>
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
																id="bar-chart-category-gpcp"></div>
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
																	<th>P/R</th>
																	<th>Cost of Product</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByGPCP}">
																	<c:forEach items="${categoryByGPCP}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.netPr}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.netProductCost}" /></td>
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
														<div id="stacked-chart-3"></div>
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
																	<th>N/R</th>
																	<th>Gross Profit</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByGNR}">
																	<c:forEach items="${categoryByGNR}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.grossNrAmount}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.grossProfit}" /></td>
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
														<div id="stacked-chart-4"></div>
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
																	<th>Total Net N/R</th>
																	<th>Total Return Charges to be reversed</th>
																	<th>Net Cost of Product</th>
																	<th>Net P/R</th>
																	<th>Total Additional Charges</th>
																	<th>Gross Profit/Loss</th>
																	<th>% GP Vs Cost of Profit</th>
																	<th>Net Payment Result</th>
																	<th>Net Payment Difference</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTablePartner}">
																	<c:forEach items="${shortTablePartner}" var="partner"
																		varStatus="loop">
																		<tr>
																			<td>${partner.partner}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${partner.netQty}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.grossNrAmount}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.saleRetNrAmount - partner.netReturnCharges}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.netProductCost}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.netPr}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.netReturnCharges}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.grossProfit}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.gpVsProductCost}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.netPaymentResult}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.paymentDifference}" /></td>
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
																	<th>Total Net N/R</th>
																	<th>Total Return Charges to be reversed</th>
																	<th>Net Cost of Product</th>
																	<th>Net P/R</th>
																	<th>Total Addtional Charges</th>
																	<th>Gross Profit/Loss</th>
																	<th>% GP Vs Cost of Profit</th>
																	<th>Net Payment Result</th>
																	<th>Net Payment Difference</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTableCategory}">
																	<c:forEach items="${shortTableCategory}" var="category"
																		varStatus="loop">
																		<tr>
																			<td>${category.category}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${category.netQty}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.netNrAmount}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.saleRetNrAmount - partner.netReturnCharges}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.netProductCost}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.netPr}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.netReturnCharges}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.grossProfit}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.gpVsProductCost}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.netPaymentResult}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.paymentDifference}" /></td>
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
	<script src="/O2R/seller/js/plugins/highchart/highcharts.js"></script>

	<!-- Morris -->
	<script src="/O2R/seller/js/plugins/morris/raphael-2.1.0.min.js"></script>
	<script src="/O2R/seller/js/plugins/morris/morris.js"></script>

	<!-- Highchart Custom -->
	<script src="/O2R/seller/js/demo/highchart-demo.js"></script>

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
		var partnerByGrossProfit = [];
		var i = 1;
		<c:forEach items="${partnerByGrossProfit}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.grossProfit}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp1.push(arr1);
		partnerByGrossProfit.push(arr2);
		</c:forEach>

		var temp2 = [];
		var partnerByGPCP = [];
		var i = 1;
		<c:forEach items="${partnerByGPCP}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.gpVsProductCost}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp2.push(arr1);
		partnerByGPCP.push(arr2);
		</c:forEach>
		
		var dataArr = [];
		var yAxisText = 'Net P/R vs Net Product Cost';
		var divId = "#stacked-chart-1";
		var xAxisCategories = ['Net P/R', 'Net Product Cost'];
		<c:forEach items="${partnerByPR}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = '${partnerDto.partner}';
			data.data = [parseFloat(parseFloat('${partnerDto.netPr}').toFixed(2)), parseFloat(parseFloat('${partnerDto.netProductCost}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);	
		
		var dataArr = [];
		var yAxisText = 'Net N/R vs Gross Profit';
		var divId = "#stacked-chart-2";
		var xAxisCategories = ['Net N/R', 'Gross Profit'];
		<c:forEach items="${partnerByGNR}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = '${partnerDto.partner}';
			data.data = [parseFloat(parseFloat('${partnerDto.grossNrAmount}').toFixed(2)), parseFloat(parseFloat('${partnerDto.grossProfit}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var temp3 = [];
		var categoryByGrossProfit = [];
		var i = 1;
		<c:forEach items="${categoryByGrossProfit}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.grossProfit}' ];
		var arr2 = [ i++, '${categoryDto.category}' ];
		temp3.push(arr1);
		categoryByGrossProfit.push(arr2);
		</c:forEach>

		var temp4 = [];
		var categoryByGPCP = [];
		var i = 1;
		<c:forEach items="${categoryByGPCP}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.gpVsProductCost}' ];
		var arr2 = [ i++, '${categoryDto.category}' ];
		temp4.push(arr1);
		categoryByGPCP.push(arr2);
		</c:forEach>
		
		var dataArr = [];
		var yAxisText = 'Net P/R vs Net Product Cost';
		var divId = "#stacked-chart-3";
		var xAxisCategories = ['Net P/R', 'Net Product Cost'];
		<c:forEach items="${categoryByPR}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = '${partnerDto.partner}';
			data.data = [parseFloat(parseFloat('${partnerDto.netPr}').toFixed(2)), parseFloat(parseFloat('${partnerDto.netProductCost}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);	
		
		var dataArr = [];
		var yAxisText = 'Net N/R vs Gross Profit';
		var divId = "#stacked-chart-4";
		var xAxisCategories = ['Net N/R', 'Gross Profit'];
		<c:forEach items="${categoryByGNR}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = '${partnerDto.partner}';
			data.data = [parseFloat(parseFloat('${partnerDto.grossNrAmount}').toFixed(2)), parseFloat(parseFloat('${partnerDto.grossProfit}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		$(window)
				.load(
						function() {

							flotbar(temp1, partnerByGrossProfit,
									"#bar-chart-partner-gross-profit");
							flotbar(temp2, partnerByGPCP,
									"#bar-chart-partner-gpcp");
							flotbar(temp3, categoryByGrossProfit,
									"#bar-chart-category-gross-profit");
							flotbar(temp4, categoryByGPCP,
									"#bar-chart-category-gpcp");

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
