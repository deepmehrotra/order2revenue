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
																	<th>Month</th>
																	<th>Opening Stock</th>
																	<th>Closing Stock</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty stockList}">
																	<c:forEach items="${stockList}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.monthStr}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.openStock}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.closeStock}" /></td>
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
																	<th>Month</th>
																	<th>Opening Stock Valuation</th>
																	<th>Closing Stock Valuation</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty stockList}">
																	<c:forEach items="${stockList}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.monthStr}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.openStockValuation}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.closeStockValuation}" /></td>
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
																	<th>Net Expenses</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty expensesList}">
																	<c:forEach items="${expensesList}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.key}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.amount}" /></td>
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
																	<th>Month</th>
																	<th>Net NPR</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty nprList}">
																	<c:forEach items="${nprList}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.key}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.netPaymentResult}" /></td>
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
														<div id="stacked-chart-4"></div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div id="tab-2" class="tab-pane col-sm-12">	
										<div class="row">
											<div class="col-lg-12">
											</div>
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
	var dataArr = [];
	var yAxisText = 'Opening Stock vs Closing Stock';
	var divId = "#stacked-chart-1";
	var xAxisCategories = ['Opening Stock', 'Closing Stock'];
	<c:forEach items="${stockList}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.monthStr}';
		data.data = [parseFloat(parseFloat('${partnerDto.openStock}').toFixed(2)), parseFloat(parseFloat('${partnerDto.closeStock}').toFixed(2))];
		dataArr.push(data);
	</c:forEach>
	stackChart(divId, yAxisText, dataArr);		  

	var dataArr = [];
	var yAxisText = 'Opening Stock Valuation vs Closing Stock Valuation';
	var divId = "#stacked-chart-2";
	var xAxisCategories = ['Opening Stock Valuation', 'Closing Stock Valuation'];
	<c:forEach items="${stockList}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.monthStr}';
		data.data = [parseFloat(parseFloat('${partnerDto.openStockValuation}').toFixed(2)), parseFloat(parseFloat('${partnerDto.closeStockValuation}').toFixed(2))];
		dataArr.push(data);
	</c:forEach>
	stackChart(divId, yAxisText, dataArr);		  
	
	var dataArr = [];
	var yAxisText = 'Net Expenses';
	var divId = "#stacked-chart-3";
	var xAxisCategories = ['Net Expenses'];
	<c:forEach items="${expensesList}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.key}';
		data.data = [parseFloat(parseFloat('${partnerDto.amount}').toFixed(2))];
		dataArr.push(data);
	</c:forEach>
	stackChart(divId, yAxisText, dataArr);
	
	var dataArr = [];
	var yAxisText = 'Net NPR';
	var divId = "#stacked-chart-4";
	var xAxisCategories = ['Net NPR'];
	<c:forEach items="${nprList}" var="partnerDto" varStatus="loop">
		var data = {};
		data.name = '${partnerDto.key}';
		data.data = [parseFloat(parseFloat('${partnerDto.netPaymentResult}').toFixed(2))];
		dataArr.push(data);
	</c:forEach>
	stackChart(divId, yAxisText, dataArr);		  

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