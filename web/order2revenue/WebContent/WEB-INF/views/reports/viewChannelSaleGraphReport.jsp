<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
								<h5>${reportNameStr}</h5>
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
														<table id="partnerByNR_table"
															class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Net Sale N/R</th>
																	<th>Net Sale SP</th>
																	<th>Net Sale P/R</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByNR}">
																	<c:forEach items="${partnerByNR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netNrAmount}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netSpAmount}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2" value="${partnerDto.netPr}" /></td>
																		</tr>
																	</c:forEach>
																	<tr class="totalColumn" bgcolor="#DCDCDC">
																		<td class="totalCol"><b>Total :</b></td>
																		<td class="totalCol"></td>
																		<td class="totalCol"></td>
																		<td class="totalCol"></td>
																	</tr>
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
														<table id="partnerByGSvSR_table"
															class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Gross Sale Qty</th>
																	<th>Sale Return Qty</th>
																	<th>Net Sale Qty</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByGSvSR}">
																	<c:forEach items="${partnerByGSvSR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="0"
																					value="${partnerDto.grossQty}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="0"
																					value="${partnerDto.saleRetQty}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="0" value="${partnerDto.netQty}" /></td>
																		</tr>
																	</c:forEach>
																	<tr class="totalColumn">
																		<td class="totalCol"><b>Total :</b></td>
																		<td class="totalCol"></td>
																		<td class="totalCol"></td>
																		<td class="totalCol"></td>
																	</tr>
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
																	<th>Partner</th>
																	<th>Approx Net EOSS</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByEOSS}">
																	<c:forEach items="${partnerByEOSS}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netEOSSValue}" /></td>
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
																	<th>Partner</th>
																	<th>Gross Taxable Sale</th>
																	<th>Return Taxable Sale</th>
																	<th>Net Taxable Sale</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByTaxableSale}">
																	<c:forEach items="${partnerByTaxableSale}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.grossTaxableSale}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.returnTaxableSale}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netTaxableSale}" /></td>
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
										<div class="row">
											<div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Gross Actual Sale</th>
																	<th>Return Actual Sale</th>
																	<th>Net Actual Sale</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByActualSale}">
																	<c:forEach items="${partnerByActualSale}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.grossActualSale}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.returnActualSale}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netActualSale}" /></td>
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
														<div id="stacked-chart-5"></div>
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
																	<th>Gross Taxfree Sale</th>
																	<th>Return Taxfree Sale</th>
																	<th>Net Taxfree Sale</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByTaxfreeSale}">
																	<c:forEach items="${partnerByTaxfreeSale}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.grossTaxfreeSale}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.returnTaxfreeSale}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netTaxfreeSale}" /></td>
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
														<div id="stacked-chart-6"></div>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Month</th>
																	<th>Net Taxable Sale</th>
																	<th>Net Actual Sale</th>
																	<th>Net Taxfree Sale</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty monthlySale}">
																	<c:forEach items="${monthlySale}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.month}</td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netTaxableSale}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netActualSale}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netTaxfreeSale}" /></td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<!-- <div class="col-lg-6">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div id="stacked-chart-7"></div>
													</div>
												</div>
											</div> -->
										</div>
										<div class="ibox-content">
											<div id="morris-line-chart"></div>
										</div>
									</div>
									<div id="tab-2" class="tab-pane col-sm-12">
										<div class="row">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div style="overflow-y: hidden; overflow-x: scroll;">
														<table class="table table-bordered custom-table"
															style="margin-bottom: auto;">
															<thead>
																<tr>
																	<th rowspan="2">Partner</th>
																	<th colspan="3" style="text-align: center;">Gross</th>
																	<th colspan="3" style="text-align: center;">Sale
																		Return</th>
																	<th rowspan="2">Return Actual %</th>
																	<th colspan="3" style="text-align: center;">Net
																		Sale</th>
																	<th rowspan="2">Net Tax Liability</th>
																	<th rowspan="2">Sum of Net P/R</th>
																	<!-- <th rowspan="2">Sum of NPR</th>
																	<th rowspan="2">Sum of Net Payment Difference</th> -->
																</tr>
																<tr>
																	<th>N/R Amount</th>
																	<th>SP Amount</th>
																	<th>Qty</th>
																	<th>N/R Amount</th>
																	<th>SP Amount</th>
																	<th>Qty</th>
																	<th>N/R Amount</th>
																	<th>SP Amount</th>
																	<th>Qty</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTablePartner}">
																	<c:set var="initialTC" value="" />
																	<c:forEach items="${shortTablePartner}"
																		var="partnerDto" varStatus="loop">
																		<c:set var="currTC" value="${partnerDto.taxCategory}" />
																		<c:if test="${!(currTC eq initialTC)}">
																			<c:set var="initialTC" value="${currTC}" />
																			<tr>
																				<td colspan="16" style="background-color: #F5F5CF;"><c:out
																						value="${partnerDto.taxCategory}" /></td>
																			</tr>
																		</c:if>
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.grossNrAmount}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.grossSpAmount}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.grossQty}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.saleRetNrAmount}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.saleRetSpAmount}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.saleRetQty}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.retActualPercent}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netNrAmount}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netSpAmount}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="0" value="${partnerDto.netQty}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2"
																					value="${partnerDto.netTaxLiability}" /></td>
																			<td><fmt:formatNumber type="number"
																					maxFractionDigits="2" value="${partnerDto.netPr}" /></td>
																			<%-- <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.netPaymentResult}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.netToBeReceived}" /></td> --%>
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
	<script src="/O2R/seller/js/plugins/highchart/highcharts.js"></script>

	<!-- Morris -->
	<script src="/O2R/seller/js/plugins/morris/raphael-2.1.0.min.js"></script>
	<script src="/O2R/seller/js/plugins/morris/morris.js"></script>

	<!-- <!-- Highchart Custom -->
	<script src="/O2R/seller/js/demo/highchart-demo.js"></script> -->

	<!-- ChartJS-->
	<script src="/O2R/seller/js/plugins/chartJs/Chart.min.js"></script>

	<!-- Morris -->
	<script src="js/plugins/morris/raphael-2.1.0.min.js"></script>
	<script src="js/plugins/morris/morris.js"></script>

	<!-- Data Tables -->
	<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
	<script>
		//Script for Bar Chart	
		function stackChart(divId, yAxisText, dataArr) {
			$(divId)
					.highcharts(
							{
								chart : {
									type : 'column'
								},
								title : {
									text : ''
								},
								xAxis : {
									categories : xAxisCategories
								},
								yAxis : {
									min : 0,
									title : {
										text : yAxisText
									},
									stackLabels : {
										enabled : true,
										style : {
											fontWeight : 'bold',
											color : (Highcharts.theme && Highcharts.theme.textColor)
													|| 'gray'
										}
									}
								},
								legend: {
					                backgroundColor: '#FFFFFF',
					                reversed: true,
					                align: 'right',
					                verticalAlign: 'top',
					                x: 0,
					                y: 0
					            },
								tooltip : {
									headerFormat : '<b>{point.x}</b><br/>',
									pointFormat : '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
								},
								plotOptions : {
									column : {
										stacking : 'normal',
										dataLabels : {
											enabled : true,
											color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
													|| 'white',
											style : {
												textShadow : '0 0 3px black'
											}
										}
									}
								},
								series : dataArr
							});
		}

		var dataArr = [];
		var yAxisText = 'Net Sale SP vs N/R vs P/R Graph';
		var divId = "#stacked-chart-1";
		var xAxisCategories = [ 'Net Nr Amount', 'Net SP Amount', 'Net Pr' ];
		<c:forEach items="${partnerByNR}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.partner}';
		data.data = [
				parseFloat(parseFloat('${partnerDto.netNrAmount}').toFixed(2)),
				parseFloat(parseFloat('${partnerDto.netSpAmount}').toFixed(2)),
				parseFloat(parseFloat('${partnerDto.netPr}').toFixed(2)) ];
		dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var dataArr = [];
		var yAxisText = 'Gross Sale vs Return Qty Graph';
		var divId = "#stacked-chart-2";
		var xAxisCategories = [ 'Gross Sale Qty', 'Return Sale Qty',
				'Net Sale Qty' ];
		<c:forEach items="${partnerByGSvSR}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.partner}';
		data.data = [ parseInt('${partnerDto.grossQty}'),
				parseInt('${partnerDto.saleRetQty}'),
				parseInt('${partnerDto.netQty}') ];
		dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var dataArr = [];
		var yAxisText = 'Approx Net EOSS Graph';
		var divId = "#stacked-chart-3";
		var xAxisCategories = [ 'Approx Net EOSS' ];
		<c:forEach items="${partnerByEOSS}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.partner}';
		data.data = [ parseFloat(parseFloat('${partnerDto.netEOSSValue}')
				.toFixed(2)) ];
		dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var dataArr = [];
		var yAxisText = 'Taxable Sale Graph';
		var divId = "#stacked-chart-4";
		var xAxisCategories = [ 'Gross Taxable Sale', 'Return Taxable Sale',
				'Net Taxable Sale' ];
		<c:forEach items="${partnerByTaxableSale}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.partner}';
		data.data = [
				parseFloat(parseFloat('${partnerDto.grossTaxableSale}')
						.toFixed(2)),
				parseFloat(parseFloat('${partnerDto.returnTaxableSale}')
						.toFixed(2)),
				parseFloat(parseFloat('${partnerDto.netTaxableSale}')
						.toFixed(2)) ];
		dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var dataArr = [];
		var yAxisText = 'Actual Sale Graph';
		var divId = "#stacked-chart-5";
		var xAxisCategories = [ 'Gross Actual Sale', 'Return Actual Sale',
				'Net Actual Sale' ];
		<c:forEach items="${partnerByActualSale}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.partner}';
		data.data = [
				parseFloat(parseFloat('${partnerDto.grossActualSale}').toFixed(
						2)),
				parseFloat(parseFloat('${partnerDto.returnActualSale}')
						.toFixed(2)),
				parseFloat(parseFloat('${partnerDto.netActualSale}').toFixed(2)) ];
		dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var dataArr = [];
		var yAxisText = 'Taxfree Sale Graph';
		var divId = "#stacked-chart-6";
		var xAxisCategories = [ 'Gross Taxfree Sale', 'Return Taxfree Sale',
				'Net Taxfree Sale' ];
		<c:forEach items="${partnerByTaxfreeSale}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.partner}';
		data.data = [
				parseFloat(parseFloat('${partnerDto.grossTaxfreeSale}')
						.toFixed(2)),
				parseFloat(parseFloat('${partnerDto.returnTaxfreeSale}')
						.toFixed(2)),
				parseFloat(parseFloat('${partnerDto.netTaxfreeSale}')
						.toFixed(2)) ];
		dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var dataArr = [];
		var yAxisText = 'Monthly Sale Graph';
		var divId = "#stacked-chart-7";
		var xAxisCategories = [ 'Net Taxable Sale', 'Net Actual Sale',
				'Net Taxfree Sale' ];
		<c:forEach items="${monthlySale}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.month}';
		data.data = [
				parseFloat(parseFloat('${partnerDto.netTaxableSale}')
						.toFixed(2)),
				parseFloat(parseFloat('${partnerDto.netActualSale}').toFixed(2)),
				parseFloat(parseFloat('${partnerDto.netTaxfreeSale}')
						.toFixed(2)) ];
		dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		$(window)
				.load(
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
						});

		$(function() {

			var monthStrArr = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
					'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ];
			var dataArr = [];
			<c:forEach items="${monthlySale}" var="partnerDto" varStatus="loop">
			var monthStr = '${partnerDto.month}';
			var month = monthStr.split(" ");
			var monthInt = parseInt(monthStrArr.indexOf(month[0])) + 1;
			var finalMonth = "";
			if (monthInt < 10)
				finalMonth = "0" + monthInt;
			else
				finalMonth = monthInt;
			var finalStr = [ month[1], "-", finalMonth ].join("");
			var data = {
				key1 : finalStr,
				key2 : parseFloat(parseFloat('${partnerDto.netTaxableSale}')
						.toFixed(2)),
				key3 : parseFloat(parseFloat('${partnerDto.netActualSale}')
						.toFixed(2)),
				key4 : parseFloat(parseFloat('${partnerDto.netTaxfreeSale}')
						.toFixed(2))
			};
			dataArr.push(data);
			</c:forEach>
			Morris.Line({
				element : 'morris-line-chart',
				data : dataArr,
				xkey : 'key1',
				ykeys : [ 'key2', 'key3', 'key4' ],
				labels : [ 'Net Taxable Sale', 'Net Actual Sale',
						'Net Taxfree Sale' ],
				hideHover : 'auto',
				xLabelFormat : function(x) {
					var month = monthStrArr[x.getMonth()] + " "
							+ x.getFullYear();
					return month;
				},
				dateFormat : function(x) {
					var month = monthStrArr[new Date(x).getMonth()];
					var year = new Date(x).getFullYear();
					return month + ' ' + year;
				},
				resize : true,
				xLabelAngle : 45,
				lineColors : [ '#3a539b', '#2b4b5c', '#00bbb3' ],
			});

		});

		$("#partnerByNR_table tr:last td:not(:first)").text(
				function(i) {
					var t = 0;
					$(this).parent().prevAll().find(
							"td:nth-child(" + (i + 2) + ")").each(function() {
						var val = $(this).text().replace(',', '');
						t += parseFloat(val) || 0;
					});
					return t.toFixed(2);
					;
				});

		$("#partnerByGSvSR_table tr:last td:not(:first)").text(
				function(i) {
					var t = 0;
					$(this).parent().prevAll().find(
							"td:nth-child(" + (i + 2) + ")").each(function() {
						var val = $(this).text().replace(',', '');
						t += parseFloat(val) || 0;
					});
					return t.toFixed(2);
					;
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

div.dataTables_length select {
	padding: 0 10px;
}
</style>
</body>

</html>
