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
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.grossPartnerCommissionPaid}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.netReturnCommission}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.additionalReturnCharges}" /></td>
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
																	<th>Net Channel Commission</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByNetChann}">
																	<c:forEach items="${partnerByNetChann}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partnerDto.netChannelCommissionToBePaid}" /></td>
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
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Partner</th>
																	<th>Selling Fee</th>
																	<th>PCC</th>
																	<th>Fixed Fee</th>
																	<th>Shipping Charges</th>
																	<th>Service Tax</th>
																	<th>Tax SP</th>
																	<th>Additional Return Charges</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty commTablePartner}">
																	<c:forEach items="${commTablePartner}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.key}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.sellingFee}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.pcc}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.fixedFee}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.shippingCharges}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.serviceTax}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.taxSP}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.additionalCharges}" /></td>
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
										<div class="row">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Month</th>
																	<th>Gross Commission</th>
																	<th>Return Commission</th>
																	<th>Net Commission</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty monthlyGraph}">
																	<c:forEach items="${monthlyGraph}"
																		var="categoryDto" varStatus="loop">
																		<tr>
																			<td>${categoryDto.key}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.grossCommission}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.returnCommission}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.netCommission}" /></td>
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
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.grossPartnerCommissionPaid}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.netReturnCommission}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.additionalReturnCharges}" /></td>
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
																	<th>Net Channel Commission</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty categoryByNetChann}">
																	<c:forEach items="${categoryByNetChann}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.categoryName}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.netChannelCommissionToBePaid}" /></td>
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
										<div class="row">
											<div class="col-lg-12">
												<div class="float-e-margins graph-brd">
													<div class="ibox-content">
														<table class="table table-bordered custom-table">
															<thead>
																<tr>
																	<th>Category</th>
																	<th>Selling Fee</th>
																	<th>PCC</th>
																	<th>Fixed Fee</th>
																	<th>Shipping Charges</th>
																	<th>Service Tax</th>
																	<th>Tax SP</th>
																	<th>Additional Return Charges</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty commTableCategory}">
																	<c:forEach items="${commTableCategory}" var="categoryDto"
																		varStatus="loop">
																		<tr>
																			<td>${categoryDto.key}</td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.sellingFee}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.pcc}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.fixedFee}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.shippingCharges}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.serviceTax}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.taxSP}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${categoryDto.additionalCharges}" /></td>
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
														<div id="stacked-chart-5"></div>
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
																			<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${partner.netSaleQty}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.grossPartnerCommissionPaid}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.netSrCommisison}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.netPartnerCommissionPaid}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${partner.netTDSToBeDeposited}" /></td>
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
																			<td><fmt:formatNumber type="number" maxFractionDigits="0" value="${category.netSaleQty}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.grossPartnerCommissionPaid}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.netSrCommisison}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.netPartnerCommissionPaid}" /></td>
																			<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${category.netTDSToBeDeposited}" /></td>
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
		var yAxisText = 'Gross Partner Commission Paid vs Net Return Commission vs Additional Return Charges';
		var divId = "#stacked-chart-1";
		var xAxisCategories = ['Gross Partner Commission Paid', 'Net Return Commission', 'Additional Return Charges'];
		<c:forEach items="${partnerByGrossComm}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = "${partnerDto.partner}";
			data.data = [parseFloat(parseFloat('${partnerDto.grossPartnerCommissionPaid}').toFixed(2)), parseFloat(parseFloat('${partnerDto.netReturnCommission}').toFixed(2)), parseFloat(parseFloat('${partnerDto.additionalReturnCharges}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var temp2 = [];
		var partnerByNetChannArr = [];
		var i = 1;
		<c:forEach items="${partnerByNetChann}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netChannelCommissionToBePaid}' ];
		var arr2 = [ i++, "${partnerDto.partner}" ];
		temp2.push(arr1);
		partnerByNetChannArr.push(arr2);
		</c:forEach>

		var dataArr = [];
		var yAxisText = 'Gross Partner Commission Paid vs Net Return Commission vs Additional Return Charges';
		var divId = "#stacked-chart-2";
		var xAxisCategories = ['Gross Partner Commission Paid', 'Net Return Commission', 'Additional Return Charges'];
		<c:forEach items="${categoryByGrossComm}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = "${partnerDto.partner}";
			data.data = [parseFloat(parseFloat('${partnerDto.grossPartnerCommissionPaid}').toFixed(2)), parseFloat(parseFloat('${partnerDto.netReturnCommission}').toFixed(2)), parseFloat(parseFloat('${partnerDto.additionalReturnCharges}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var temp4 = [];
		var categoryByNetChannArr = [];
		var i = 1;
		<c:forEach items="${categoryByNetChann}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netChannelCommissionToBePaid}' ];
		var arr2 = [ i++, "${categoryDto.categoryName}" ];
		temp4.push(arr1);
		categoryByNetChannArr.push(arr2);
		</c:forEach>
		
		var monthStrArr = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']; 
		var monthlyArr = [];
		<c:forEach items="${monthlyGraph}" var="partnerDto" varStatus="loop">
		var monthStr = "${partnerDto.key}";
		var month = monthStr.split(" ");
		var monthInt = parseInt(monthStrArr.indexOf(month[0])) + 1;
		var finalStr = [month[1], "-", monthInt].join("");
		console.log(finalStr);
		var data = {
				key1 : finalStr,
				key2 : parseFloat(parseFloat('${partnerDto.grossCommission}').toFixed(2)),
				key3 : parseFloat(parseFloat('${partnerDto.returnCommission}').toFixed(2)),
				key4 : parseFloat(parseFloat('${partnerDto.netCommission}').toFixed(2))
		};
		monthlyArr.push(data);
		</c:forEach>
		Morris.Line({
			element : 'morris-line-chart',
			data : monthlyArr,
			xkey : 'key1',
			ykeys : [ 'key2', 'key3', 'key4'],
			labels : [ 'Gross Commission', 'Return Commission', 'Net Commission' ],
			hideHover : 'auto',
			resize : true,
			lineColors : [ '#3a539b', '#2b4b5c', '#00bbb3' ],
		});
		
		var dataArr = [];
		var yAxisText = 'Partner Commission Graph';
		var divId = "#stacked-chart-4";
		var xAxisCategories = ['Selling Fee', 'PCC', 'Fixed Fee', 'Shipping Charges', 'Service Tax', 'Tax SP', 'Additional Charges'];
		<c:forEach items="${commTablePartner}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = "${partnerDto.key}";
			data.data = [parseFloat(parseFloat('${partnerDto.sellingFee}').toFixed(2)), 
			             parseFloat(parseFloat('${partnerDto.pcc}').toFixed(2)), 
			             parseFloat(parseFloat('${partnerDto.fixedFee}').toFixed(2)),
			             parseFloat(parseFloat('${partnerDto.shippingCharges}').toFixed(2)),
			             parseFloat(parseFloat('${partnerDto.serviceTax}').toFixed(2)),
			             parseFloat(parseFloat('${partnerDto.taxSP}').toFixed(2)),
			             parseFloat(parseFloat('${partnerDto.additionalCharges}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		var dataArr = [];
		var yAxisText = 'Category Commission Graph';
		var divId = "#stacked-chart-5";
		var xAxisCategories = ['Selling Fee', 'PCC', 'Fixed Fee', 'Shipping Charges', 'Service Tax', 'Tax SP', 'Additional Charges'];
		<c:forEach items="${commTableCategory}" var="partnerDto" varStatus="loop">
			var data = {};
			data.name = "${partnerDto.key}";
			data.data = [parseFloat(parseFloat('${partnerDto.sellingFee}').toFixed(2)), 
			             parseFloat(parseFloat('${partnerDto.pcc}').toFixed(2)), 
			             parseFloat(parseFloat('${partnerDto.fixedFee}').toFixed(2)),
			             parseFloat(parseFloat('${partnerDto.shippingCharges}').toFixed(2)),
			             parseFloat(parseFloat('${partnerDto.serviceTax}').toFixed(2)),
			             parseFloat(parseFloat('${partnerDto.taxSP}').toFixed(2)),
			             parseFloat(parseFloat('${partnerDto.additionalCharges}').toFixed(2))];
			dataArr.push(data);
		</c:forEach>
		stackChart(divId, yAxisText, dataArr);

		$(window)
				.load(
						function() {

							flotbar(temp2, partnerByNetChannArr,
									"#bar-chart-partner-net-chann");
							flotbar(temp4, categoryByNetChannArr,
									"#bar-chart-category-net-chann");

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
