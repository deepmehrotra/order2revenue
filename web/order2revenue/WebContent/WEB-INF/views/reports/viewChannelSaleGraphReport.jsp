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
																	<th>Net Sale SP</th>
																	<th>Net Sale N/R</th>
																	<th>Net Sale A/R</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByNetSaleSP}">
																	<c:forEach items="${partnerByNetSaleSP}"
																		var="partnerDto" varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.netSpAmount}</td>
																			<td>${partnerDto.netNrAmount}</td>
																			<td>${partnerDto.netAr}</td>
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
																id="line-chart-partner-saleSP"></div>
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
																	<th>Net A/R</th>
																	<th>Net Due to be Received</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByNetAR}">
																	<c:forEach items="${partnerByNetAR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.netAr}</td>
																			<td>${partnerDto.netToBeReceived}</td>
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
																id="line-chart-partner-ar"></div>
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
																	<th>Gross Sale Qty</th>
																	<th>Return Qty</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByGSvSR}">
																	<c:forEach items="${partnerByGSvSR}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.grossQty}</td>
																			<td>${partnerDto.saleRetQty}</td>
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
																id="bar-chart-partner-gross-qty"></div>
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
																	<th>Gross Sale Amount</th>
																	<th>Return Amount</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty partnerByGSAvRA}">
																	<c:forEach items="${partnerByGSAvRA}" var="partnerDto"
																		varStatus="loop">
																		<tr>
																			<td>${partnerDto.partner}</td>
																			<td>${partnerDto.grossSpAmount}</td>
																			<td>${partnerDto.saleRetSpAmount}</td>
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
																id="bar-chart-partner-gross-amount"></div>
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
																	<th rowspan="2">Partner</th>
																	<th colspan="3" style="text-align: center;">Gross</th>
																	<th colspan="3" style="text-align: center;">Sale Return</th>
																	<th rowspan="2">Sale Return vs Gross Sale</th>
																	<th colspan="3" style="text-align: center;">Net Sale</th>
																	<th rowspan="2">Tax Category</th>
																	<th rowspan="2">Net Tax Liability</th>
																	<th colspan="3" style="text-align: center;">Net Pure Sale</th>
																	<th rowspan="2">Net A/R</th>
																	<th rowspan="2">Net Due to be Received</th>
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
																	<th>N/R Amount</th>
																	<th>SP Amount</th>
																	<th>Qty</th>
																</tr>
															</thead>
															<tbody>
																<c:if test="${!empty shortTablePartner}">
																	<c:forEach items="${shortTablePartner}" var="partner"
																		varStatus="loop">
																		<tr>
																			<td>${partner.partner}</td>
																			<td>${partner.grossNrAmount}</td>
																			<td>${partner.grossSpAmount}</td>
																			<td>${partner.grossQty}</td>
																			<td>${partner.saleRetNrAmount}</td>
																			<td>${partner.saleRetSpAmount}</td>
																			<td>${partner.saleRetQty}</td>
																			<td>${partner.saleRetVsGrossSale}</td>
																			<td>${partner.netNrAmount}</td>
																			<td>${partner.netSpAmount}</td>
																			<td>${partner.netQty}</td>
																			<td>${partner.taxCategory}</td>
																			<td>${partner.netTaxLiability}</td>
																			<td>${partner.netPureSaleNrAmount}</td>
																			<td>${partner.netPureSaleSpAmount}</td>
																			<td>${partner.netPureSaleQty}</td>
																			<td>${partner.netAr}</td>
																			<td>${partner.netToBeReceived}</td>
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
		var partnerByNetSaleSP = [];
		var i = 1;
		<c:forEach items="${partnerByNetSaleSP}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netSpAmount}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp1.push(arr1);
		partnerByNetSaleSP.push(arr2);
		</c:forEach>

		var temp2 = [];
		var partnerByNetAR = [];
		var i = 1;
		<c:forEach items="${partnerByNetAR}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netAr}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp2.push(arr1);
		partnerByNetAR.push(arr2);
		</c:forEach>

		var temp3 = [];
		var partnerByGSvSR = [];
		var i = 1;
		<c:forEach items="${partnerByGSvSR}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.grossQty}' ];
		var arr2 = [ i++, '${partnerDto.category}' ];
		temp3.push(arr1);
		partnerByGSvSR.push(arr2);
		</c:forEach>

		var temp4 = [];
		var partnerByGSAvRA = [];
		var i = 1;
		<c:forEach items="${partnerByGSAvRA}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.grossSpAmount}' ];
		var arr2 = [ i++, '${partnerDto.category}' ];
		temp4.push(arr1);
		partnerByGSAvRA.push(arr2);
		</c:forEach>
		
		$(window)
				.load(
						function() {

							flotline(temp1, partnerByNetSaleSP,
								"#line-chart-partner-saleSP");
							flotbar(temp2, partnerByNetAR,
								"#line-chart-partner-ar");
							flotbar(temp3, partnerByGSvSR,
								"#bar-chart-partner-gross-qty");
							flotbar(temp4, partnerByGSAvRA,
								"#bar-chart-partner-gross-amount");
							
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
