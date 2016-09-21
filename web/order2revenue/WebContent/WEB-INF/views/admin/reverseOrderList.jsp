<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Data picker -->
<link href="/O2R/seller/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet" type="text/css">
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script>

<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {

		//Radio toggle
		$("[name=toggler]").click(function() {
			$('.radio1').hide();
			$("#blk-" + $(this).val()).slideDown();
		});

		$('#data_1 .input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : true,
			autoclose : true
		});
		$('#data_2 .input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : true,
			autoclose : true
		});

		$('#LoadOnSubmit').click(function(e) {
			e.preventDefault();
			$.ajax({
				url : $("#findOrderForm").attr("action"),
				context : document.body,
				type : 'post',
				data : $("#findOrderForm").serialize(),
				success : function(res) {
					$("#RTOTableContainer").html(res);
				}
			});

		});
	});
</script>
<script type="text/javascript">
	function onclickReturn(value, id) {
		var targeturl = "";
		switch (value) {
		case "uploadReturn":
			targeturl = "uploadOrderDA.html?value=ordersummary";
			break;
		case "downloadReturn":
			targeturl = "downloadOrderDA.html?value=ordersummary";
			break;
		}
		$.ajax({
			url : targeturl,
			success : function(data) {
				if ($(data).find('#j_username').length > 0) {
					window.location.href = "orderindex.html";
				} else {
					$('#centerpane').html(data);
				}
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


			<div class="wrapper wrapper-content  animated fadeInRight">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>Reverse Order</h5>
							</div>
							<div class="ibox-content overflow-h">
								<form role="search" class="navbar-form-new" method="post"
									action="findGlobalOrders.html" id="findGlobalOrderForm"
									class="form-horizontal">
									<div class="col-sm-12" id="search1">
										<div class="col-sm-5">
											<select class="form-control" name="searchCriteria" required
												autocomplete="off" / id="searchCriteriaGlobal">
												<option value="">Select Criteria</option>
												<option id="1" value="channelOrderID">Channel
													OrderID/PO ID</option>
												<option id="2" value="awbNum">AWB</option>
												<option id="3" value="invoiceID">Invoice ID</option>
												<option id="4" value="subOrderID">Sub Order ID</option>
												<option id="5" value="PIreferenceNo">PI Reference
													No</option>
												<option id="7" value="returnOrRTOId">Sale
													ReturnID/Debit Note No</option>
												<option id="8" value="pcName">Partner</option>
												<option id="9" value="productSkuCode">Product SKU</option>
												<option id="10" value="customerName">Customer Name</option>
												<option id="11" value="customerCity">Customer City</option>
												<option id="12" value="customerEmail">Customer mail</option>
												<option id="13" value="customerPhnNo">Customer
													Phone No</option>
												<option id="14" value="status">Order Status</option>
												<option id="15" value="sellerNote">Seller Notes</option>
												<option id="17" value="orderDate">Order Received
													Date</option>
												<option id="18" value="shippedDate">Order Shipped
													Date</option>
												<option id="19" value="deliveryDate">Order Delivery
													Expected Date</option>
												<option id="20" value="paymentDueDate">Payment Due
													Date</option>
												<option id="21" value="dateofPayment">Actual Date
													of Payment</option>
											</select>
										</div>
										<div class="col-sm-5" id="serchstring1">
											<input type="text" name="searchString" class="form-control"
												placeholder="Search String">
										</div>
										<div class="col-sm-5" id="searcstring2"
											style="display: none">
											<div class="input-group f-left" style="width: 150px;">
												<div class="input-group date">
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span> <input type="text"
														name="startDate" id="startDate" class="form-control">
												</div>
											</div>
											<div class="input-group f-left" style="width: 150px;">
												<div class="input-group date">
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span> <input type="text"
														name="endDate" id="endDate" class="form-control">
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<button class="btn btn-success " type="submit">
												<i class="fa fa-search"></i>&nbsp;&nbsp;<span class="bold">Search</span>
											</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>

			<jsp:include page="../globaljslinks.jsp"></jsp:include>
		</div>
	</div>


</body>
</html>