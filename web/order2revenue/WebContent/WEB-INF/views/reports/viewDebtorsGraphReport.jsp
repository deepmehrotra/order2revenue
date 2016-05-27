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
											<div class="col-lg-12">
												Debtors Table
											</div>
										</div>
									</div>
									<div id="tab-2" class="tab-pane col-sm-12">	
										<div class="row">
											<div class="col-lg-12">
												Debtors Graph
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
		var partnerByNetCommissionArr = [];
		var i = 1;
		<c:forEach items="${partnerByNetCommission}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netPartnerCommissionPaid}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp1.push(arr1);
		partnerByNetCommissionArr.push(arr2);
		</c:forEach>

		var temp2 = [];
		var partnerByNetTDSArr = [];
		var i = 1;
		<c:forEach items="${partnerByNetTDS}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netTDSToBeDeposited}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp2.push(arr1);
		partnerByNetTDSArr.push(arr2);
		</c:forEach>

		var temp3 = [];
		var partnerByNetTaxArr = [];
		var i = 1;
		<c:forEach items="${partnerByNetTax}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netTaxToBePaid}' ];
		var arr2 = [ i++, '${partnerDto.partner}' ];
		temp3.push(arr1);
		partnerByNetTaxArr.push(arr2);
		</c:forEach>

		var temp7 = [];
		var partnerByNetNPR = [];
		var i = 1;
		<c:forEach items="${partnerByNetNPR}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netPaymentResult}' ];
		var arr2 = [ i, '${partnerDto.partner}' ];
		i++;
		temp7.push(arr1);
		partnerByNetNPR.push(arr2);
		</c:forEach>

		var temp8 = [];
		var partnerByNetTaxable = [];
		var i = 1;
		<c:forEach items="${partnerByNetTaxable}" var="partnerDto" varStatus="loop">
		var arr1 = [ i, '${partnerDto.netTaxableSale}' ];
		var arr2 = [ i, '${partnerDto.partner}' ];
		i++;
		temp8.push(arr1);
		partnerByNetTaxable.push(arr2);
		</c:forEach>

		var temp4 = [];
		var categoryByNetCommissionArr = [];
		var i = 1;
		<c:forEach items="${categoryByNetCommission}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netPartnerCommissionPaid}' ];
		var arr2 = [ i++, '${categoryDto.categoryName}' ];
		temp4.push(arr1);
		categoryByNetCommissionArr.push(arr2);
		</c:forEach>

		var temp5 = [];
		var categoryByNetTDSArr = [];
		var i = 1;
		<c:forEach items="${categoryByNetTDS}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netTDSToBeDeposited}' ];
		var arr2 = [ i++, '${categoryDto.categoryName}' ];
		temp5.push(arr1);
		categoryByNetTDSArr.push(arr2);
		</c:forEach>

		var temp6 = [];
		var categoryByNetTaxArr = [];
		var i = 1;
		<c:forEach items="${categoryByNetTax}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netTaxToBePaid}' ];
		var arr2 = [ i++, '${categoryDto.categoryName}' ];
		temp6.push(arr1);
		categoryByNetTaxArr.push(arr2);
		</c:forEach>

		var temp9 = [];
		var categoryByNetNPR = [];
		var i = 1;
		<c:forEach items="${categoryByNetNPR}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netPaymentResult}' ];
		var arr2 = [ i, '${categoryDto.categoryName}' ];
		i++;
		temp9.push(arr1);
		categoryByNetNPR.push(arr2);
		</c:forEach>

		var temp10 = [];
		var categoryByNetTaxable = [];
		var i = 1;
		<c:forEach items="${categoryByNetTaxable}" var="categoryDto" varStatus="loop">
		var arr1 = [ i, '${categoryDto.netTaxableSale}' ];
		var arr2 = [ i, '${categoryDto.categoryName}' ];
		i++;
		temp10.push(arr1);
		categoryByNetTaxable.push(arr2);
		</c:forEach>

		$(window)
				.load(
						function() {

							flotbar(temp1, partnerByNetCommissionArr,
									"#bar-chart-partner-net-commission");
							flotbar(temp2, partnerByNetTDSArr,
									"#bar-chart-partner-net-tds");
							flotbar(temp3, partnerByNetTaxArr,
									"#bar-chart-partner-net-tax");
							flotbar(temp4, categoryByNetCommissionArr,
									"#bar-chart-category-net-commission");
							flotbar(temp5, categoryByNetTDSArr,
									"#bar-chart-category-net-tds");
							flotbar(temp6, categoryByNetTaxArr,
									"#bar-chart-category-net-tax");
							flotline(temp7, partnerByNetNPR,
									"#line-chart-partner-net-npr");
							flotline(temp8, partnerByNetTaxable,
									"#line-chart-partner-net-taxable");
							flotline(temp9, categoryByNetNPR,
									"#line-chart-category-net-npr");
							flotline(temp10, categoryByNetTaxable,
									"#line-chart-category-net-taxable");

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
