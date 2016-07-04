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
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Prepaid NPR</th>
																	<th>COD NPR</th>
																	<th>B2B NPR</th>
																	<th>Total Net NPR</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByNPR}">
																	<c:forEach items="${partnerByNPR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.prepaidNPR}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.codNPR}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.b2bNPR}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.netNPR}" /></td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>	
										<div class="row">	
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div id="stacked-chart-1"></div>
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
																	<th>Partner</th>																	
																	<c:if test="${!empty categories}">
																		<c:forEach items="${categories}" var="category" varStatus="loop">
																			<th>${category} NPR</th>
																		</c:forEach>
																	</c:if>
																</tr>
															</thead>
															<tbody>
																<c:set var="currPartner" value="" />
																<c:if test="${!empty channelCatNPR}">
																	<c:forEach items="${channelCatNPR}" var="partnerDto" varStatus="loop">
																		<tr>
																		<td>${partnerDto.partner}</td>
																		<c:if test="${!empty partnerDto.netNPR}">
																			<c:forEach items="${partnerDto.netNPR}" var="npr" varStatus="loop">
																				<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${npr}" /></td>
																			</c:forEach>
																		</c:if>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>	
										<div class="row">	
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div id="stacked-chart-3"></div>
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
																	<th>Category</th>
																	<th>Prepaid NPR</th>
																	<th>COD NPR</th>
																	<th>B2B NPR</th>
																	<th>Total Net NPR</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByNPR}">
																	<c:forEach items="${categoryByNPR}"
																		var="categoryDto" varStatus="loop">
																		<tr>
																			<td>${categoryDto.category}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.prepaidNPR}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.codNPR}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.b2bNPR}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.netNPR}" /></td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>	
										<div class="row">	
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div id="stacked-chart-2"></div>
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
																	<th>Total NPR</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty nprList}">
																	<c:forEach items="${nprList}"
																		var="categoryDto" varStatus="loop">
																		<tr>
																			<td>${categoryDto.key}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.netPaymentResult}" /></td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>	
										<div class="row">	
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<div id="morris-line-chart"></div>
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
																	<th>Manual Charges</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty channelMC}">
																	<c:forEach items="${channelMC}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.manualCharges}" /></td>
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
																id="bar-chart-partner-mc"></div>
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
																	<th>Payment Type</th>
																	<th>Total Positive Amount</th>
																	<th>Total Negative Amount</th>
																	<th>Total Net Payment Received</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTable}">
																	<c:forEach items="${shortTable}" var="partner"
																		varStatus="loop">
																		<tr>
																			<td>${partner.partner}</td>
																			<td>${partner.paymentType}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.positiveAmount}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.negativeAmount}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.baseNPR}" /></td>
																		</tr>
																	</c:forEach>
																</c:if>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>
										<div class="row" style="margin-top: 40px;">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div style="overflow-y: hidden;overflow-x: scroll;">
														<table class="table table-bordered custom-table" style="margin-bottom: auto;">
															<thead>
																<tr>
																	<th>Payment Date</th>
																	<th>Payment ID</th>
																	<th>Partner</th>
																	<th>Manual Charge particulars/Type</th>
																	<th>Amount</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTableMC}">
																	<c:forEach items="${shortTableMC}" var="mc"
																		varStatus="loop">
																		<tr>
																			<td>${mc.dateOfPayment}</td>
																			<td>${mc.chargesDesc}</td>
																			<td>${mc.partner}</td>
																			<td>${mc.particular}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${mc.paidAmount}" /></td>
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
		
		var dataArr = [];
		var yAxisText = 'Prepaid NPR vs COD NPR vs B2B NPR';
		var divId = "#stacked-chart-1";
		var xAxisCategories = ['Prepaid NPR', 'COD NPR', 'B2B NPR'];
		<c:forEach items="${partnerByNPR}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = '${partnerDto.category}';
			data.data = [parseFloat(parseFloat('${partnerDto.prepaidNPR}').toFixed(2))
			             , parseFloat(parseFloat('${partnerDto.codNPR}').toFixed(2))
			             , parseFloat(parseFloat('${partnerDto.b2bNPR}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);
		
		var dataArr = [];
		var yAxisText = 'Prepaid NPR vs COD NPR vs B2B NPR';
		var divId = "#stacked-chart-2";
		var xAxisCategories = ['Prepaid NPR', 'COD NPR', 'B2B NPR'];
		<c:forEach items="${categoryByNPR}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = '${partnerDto.category}';
			data.data = [parseFloat(parseFloat('${partnerDto.prepaidNPR}').toFixed(2))
			             , parseFloat(parseFloat('${partnerDto.codNPR}').toFixed(2))
			             , parseFloat(parseFloat('${partnerDto.b2bNPR}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);
		
		var dataArr = [];
		var yAxisText = 'Category NPR Comparison';
		var divId = "#stacked-chart-3";
		var xAxisCategories = [];
		<c:forEach items="${categories}" var="category" varStatus="loop">
			xAxisCategories.push('${category}');
		</c:forEach>
		
		<c:forEach items="${channelCatNPR}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = '${partnerDto.partner}';
			data.data = [];
			<c:forEach items="${partnerDto.netNPR}" var="npr" varStatus="loop">
				data.data.push(parseFloat(parseFloat('${npr}').toFixed(2)));
			</c:forEach>
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);
		
		var temp3 = [];
		var channelMC = [];
		var i = 1;
		<c:forEach items="${channelMC}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.manualCharges}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp3.push(arr1);
		channelMC.push(arr2);
		</c:forEach>
		
		var monthStrArr = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']; 
		var monthlyArr = [];
		<c:forEach items="${nprList}" var="partnerDto" varStatus="loop">
		var monthStr = '${partnerDto.key}';
		var month = monthStr.split(" ");
		var monthInt = parseInt(monthStrArr.indexOf(month[0])) + 1;
		var finalStr = [month[1], "-", monthInt].join("");
		console.log(finalStr);
		var data = {
				key1 : finalStr,
				key2 : parseFloat(parseFloat('${partnerDto.netPaymentResult}').toFixed(2))
		};
		monthlyArr.push(data);
		</c:forEach>
		Morris.Line({
			element : 'morris-line-chart',
			data : monthlyArr,
			xkey : 'key1',
			ykeys : [ 'key2'],
			labels : [ 'Total NPR'],
			hideHover : 'auto',
			resize : true,
			lineColors : [ '#3a539b' ],
		});

		$(window)
				.load(
						function() {
							flotbar(temp3, channelMC,
								"#bar-chart-partner-mc");

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
