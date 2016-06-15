<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<script type="text/javascript">
	function onclickNavigateMC(value, id) {
		var targeturl = "";
		switch (value) {
		case "createMC":
			targeturl = "addManualCharge.html";
			break;

		case "editMC":
			targeturl = "editmanualChargeDA.html?manualChargeId=" + id;
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
								<h5>Manual Charges(${manualCharges.size()})</h5>
								<div class="ibox-tools">
									<a href="#" onclick="onclickNavigateMC('createMC','0')"
										class="btn btn-primary btn-xs">Create New Manual Charge</a>
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
													<th>Partner</th>
													<th>Particular</th>
													<th>Paid Amount</th>
													<th>Payment Date</th>
													<th>Created On</th>
													<th>Payment ID</th>
													<th>Payment Cycle</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty manualCharges}">
													<c:forEach items="${manualCharges}" var="manualCharge"
														varStatus="loop">
														<tr>
															<td><input type="checkbox"></td>
															<td>${loop.index+1}</td>
															<td>${manualCharge.pcName}</td>
															<td>${manualCharge.productSkuCode}</td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2" value="${manualCharge.netRate}" /></td>
															<td><fmt:formatDate
																	value="${manualCharge.manualChargeDate}"
																	pattern="MMM dd ,YY" /></td>
															<td><fmt:formatDate
																	value="${manualCharge.shippedDate}"
																	pattern="MMM dd ,YY" /></td>
															<td>${manualCharge.quantity}</td>
															<td>${manualCharge.status}</td>
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
