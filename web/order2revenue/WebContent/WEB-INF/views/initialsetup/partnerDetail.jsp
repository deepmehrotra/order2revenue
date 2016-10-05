<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
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

<script src="/O2R/seller/js/jquery-2.1.1.js"></script>
<link href="/O2R/seller/css/jquery-ui.css" rel="stylesheet">
<script src="/O2R/seller/js/jquery-ui-1.10.4.min.js"
	type="text/javascript"></script>
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script>
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
								<h5>Total Channels (${partnerDetailsList.size()})</h5>
								<div class="ibox-tools">
									<button class="btn btn-white table-menu-search search-dd">
										<i class="fa fa-search"></i>
									</button>
									<span>Last</span>
									<button type="button" id="LoadFirst500"
										class="btn btn-xs btn-white active">500</button>
									<button type="button" class="btn btn-xs btn-white">1000</button>
									<button type="button" id="LoadMoreOrder"
										class="btn btn-xs btn-white">More</button>
								</div>
							</div>
							<div class="bs-example">
								<div class="ibox-content overflow-h cus-table-filters">
									<div class="scroll-y">
										<table
											class="table table-striped table-bordered table-hover dataTables-example">
											<thead>
												<tr>
													<th>#</th>
													<th>Channel Name</th>
													<th>Total Orders</th>
													<th>First Order On</th>
													<th>Last Order On</th>													
													<th>Total Payments</th>
													<th>First Payment On</th>
													<th>Last Payment On</th>
													<th>Total Returns</th>
													<th>First Return On</th>
													<th>Last Return On</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty partnerDetailsList}">
													<c:forEach items="${partnerDetailsList}" var="PD"
														varStatus="loop">
														<tr>
															<td>${loop.index+1}</td>
															<td>${PD.pcName}</td>
															<td>${PD.totalNoOfOrders}</td>															
															<td><fmt:formatDate
																	value="${PD.firstOrderDate}"
																	pattern="MMM dd ,YY" /></td>
															<td><fmt:formatDate
																	value="${PD.lastOrderDate}"
																	pattern="MMM dd ,YY" /></td>
															<td>${PD.totalNoOfPayments}</td>															
															<td><fmt:formatDate
																	value="${PD.firstPaymentDate}"
																	pattern="MMM dd ,YY" /></td>
															<td><fmt:formatDate
																	value="${PD.lastPaymentDate}"
																	pattern="MMM dd ,YY" /></td>
															<td>${PD.totalNoOfReturns}</td>															
															<td><fmt:formatDate
																	value="${PD.firstReturnDate}"
																	pattern="MMM dd ,YY" /></td>
															<td><fmt:formatDate
																	value="${PD.lastReturnDate}"
																	pattern="MMM dd ,YY" /></td>
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
    $(document).ready(function(){
        $('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
        });
    });
	</script>
</body>
</html>