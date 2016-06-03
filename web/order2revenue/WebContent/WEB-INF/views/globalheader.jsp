<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<style type="text/css">
.progress {
	display: block;
	text-align: center;
	width: 0;
	height: 3px;
	background: red;
	transition: width .3s;
}
</style>

<link rel="stylesheet" type="text/css" href="/O2R/landing/css/sticky.css">

</head>

<body>
	<script type="text/javascript">
		function onclickDownload(value) {
			window.location = 'download/uploadLog.html?id=' + value;
		}
	</script>
	<div class="row border-bottom">
		<nav class="navbar navbar-static-top white-bg" role="navigation"
			style="margin-bottom: 0">
		<div class="navbar-header">
			<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
				href="#"><i class="fa fa-bars"></i> </a>
			<form role="search" class="navbar-form-new" method="post"
				action="findGlobalOrders.html" id="findGlobalOrderForm"
				class="form-horizontal">
				<div class="form-group  top-search-180" id="search1">
					<div class="top-search-30 f-left">
						<select class="form-control" name="searchCriteria"
							id="searchCriteria">
							<option value="">Select Criteria</option>
							<option id="1" value="channelOrderID">Channel Order
								ID/PO ID</option>
							<option id="2" value="awbNum">AWB</option>
							<option id="3" value="invoiceID">Invoice ID</option>
							<option id="4" value="subOrderID">Sub Order ID</option>
							<option id="5" value="PIreferenceNo">PI Reference No</option>
							<option id="7" value="returnOrRTOId">Sale Return
								ID/Debit Note No</option>
							<option id="8" value="pcName">Partner</option>
							<option id="9" value="productSkuCode">Product SKU</option>
							<option id="10" value="customerName">Customer Name</option>
							<option id="11" value="customerCity">Customer City</option>
							<option id="12" value="customerEmail">Customer mail</option>
							<option id="13" value="customerPhnNo">Customer Phone No</option>
							<option id="14" value="status">Order Status</option>
							<option id="15" value="sellerNote">Seller Notes</option>
							<option id="17" value="orderDate">Order Received Date</option>
							<option id="18" value="shippedDate">Order Shipped Date</option>
							<option id="19" value="deliveryDate">Order Delivery
								Expected Date</option>
							<option id="20" value="paymentDueDate">Payment Due Date</option>
							<option id="21" value="dateofPayment">Actual Date of
								Payment</option>
						</select>
					</div>
					<div class="top-search-60 f-left TopSearch-box1" id="newdiv">
						<input type="text" name="searchString" class="form-control">
					</div>
					<div class="top-search-60 f-left TopSearch-box2" id="option2"
						style="display: none">
						<div class="input-group f-left" style="width: 150px;">
							<div class="input-group date">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" name="startDate" id="startDate"
									class="form-control">
							</div>
						</div>
						<div class="input-group f-left" style="width: 150px;">
							<div class="input-group date">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" name="endDate" id="endDate"
									class="form-control">
							</div>
						</div>
					</div>
					<button class="btn btn-white" type="submit">
						<i class="fa fa-search"></i>
					</button>
				</div>
			</form>
		</div>
		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown"><a class="dropdown-toggle count-info"
				id="dropdown" data-toggle="dropdown" href="#"> <i
					class="fa fa-exchange"></i>
			</a>
				<ul class="dropdown-menu dropdown-messages animated fadeInRight">
					<c:if test="${!empty uploadReportList}">
						<c:forEach items="${uploadReportList}" var="uploadReport"
							varStatus="loop">
							<li>
								<div class="dropdown-messages-box">
									<a href="javascript:onclickDownload('${uploadReport.id}');"
										class="pull-left">
									<c:if test="${fn:containsIgnoreCase(uploadReport.description, 'Exported')}">
										<c:set value="fa-level-up" var="cssClass"></c:set>
									</c:if>
									<c:if test="${fn:containsIgnoreCase(uploadReport.description, 'Imported')}">
										<c:set value="fa-level-down" var="cssClass"></c:set>
									</c:if>
										<i class="fa ${cssClass}"></i>
									</a>
									<div class="media-body">
										<small class="pull-right">${uploadReport.uploadedAt}</small> <strong>${uploadReport.description}</strong>
										by ${uploadReport.sellerName} <br> <small
											class="text-muted">${uploadReport.fileType} - ${uploadReport.uploadDate}</small>
									</div>
								</div>
							</li>
							<li class="divider"></li>
						</c:forEach>
					</c:if>
					<li>
						<div class="text-center link-block">
							<a href="mailbox.html"> <i class="fa fa-envelope"></i> <strong>Read
									All Updates</strong>
							</a>
						</div>
					</li>
					<li class="divider"></li>
					<li>
						<div class="progress"></div>
						<div class="bar" role="bar" id="bar1" style="display:none;">
							<div class="peg">Uploading...</div>
						</div>
				</ul></li>
			<li class="dropdown"><a class="dropdown-toggle count-info"
				data-toggle="dropdown"> <i class="fa fa-info"></i>
			</a>
				<ul class="dropdown-menu animated fadeInRight m-t-xs">
					<li><a href="support.html">Support</a></li>
					<li><a href="support.html?set=section1">Common Terms</a></li>
					<li><a href="support.html?set=section2">FAQ'S</a></li>
					<li><a href="support.html?set=section3">Guidelines</a></li>
					<li><a href="support.html?set=section41">Initial Setup</a></li>
					<li><a href="support.html?set=section42">Daily Activities</a></li>
					<li><a href="support.html?set=section43">Reports</a></li>
					<li><a href="support.html?set=section44">Miscellaneous</a></li>					
				</ul></li>
			<li class="dropdown"><a class="dropdown-toggle count-info"
				data-toggle="dropdown"> <i class="fa fa-gear"></i>
			</a>
				<ul class="dropdown-menu animated fadeInRight m-t-xs">
					<li><a href="addSeller.html">Seller Info</a></li>
					<li><a href="#">Goeasy Account</a></li>
					<li><a href="upgrade.html">Plan Upgrade</a></li>
					<li><a href="summary.html">Summary</a></li>
				</ul></li>
			<li><a href="/j_spring_security_logout"> <i
					class="fa fa-sign-out"></i> Log out
			</a></li>
		</ul>
		</nav>
	</div>
	<aside id="sticky-social">
		<ul>
			<li><a href="#" onclick="onclickNavigateOrder('upload',0)" class="entypo-links" ><span><img src="/O2R/landing/img/export.png"></span></a></li>
			<li><a href="#" onclick="onclickNavigateOrder('upload',0)" class="entypo-links2" ><span><img src="/O2R/landing/img/import.png"></span></a></li>
			<li><a href="orderList.html" class="entypo-links3" ><span><img src="/O2R/landing/img/orderlist.png"></span></a></li>
			<li><a href="#" onclick="onclickSideNavigation('RTO/Return')"  class="entypo-links4" ><span><img src="/O2R/landing/img/return1.png"></span></a></li>
			<li><a href="paymentUploadList.html" class="entypo-links5" ><span><img src="/O2R/landing/img/payment.png"></span></a></li>
		</ul>
	</aside>	
	
	<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
	<script type="text/javascript">
	
	function onclickNavigateOrder(value,id) {
    	var targeturl="";
    	switch(value)
    	{
    	case "upload" :
    		targeturl="uploadOrderDA.html?value=ordersummary";
    	break;
    	case "download" :
    		targeturl="downloadOrderDA.html?value=ordersummary";
    	break;
    	}
    	$.ajax({
            url : targeturl,
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    	}
	
		$(document).ready(
				function() {
					$('#searchCriteria').change(
							function() {

								var thisValue = $(this).children(":selected")
										.attr("id");

								if (thisValue == 1 || thisValue == 2
										|| thisValue == 3 || thisValue == 4
										|| thisValue == 5 || thisValue == 6
										|| thisValue == 7 || thisValue == 8
										|| thisValue == 9 || thisValue == 10
										|| thisValue == 11 || thisValue == 12
										|| thisValue == 13 || thisValue == 14
										|| thisValue == 15) {

									$('.TopSearch-box1').show();
									$('.TopSearch-box2').hide();
								} else {
									$('.TopSearch-box1').hide();
									$('.TopSearch-box2').show();
								}
							});

				});
	</script>
</body>

</html>
