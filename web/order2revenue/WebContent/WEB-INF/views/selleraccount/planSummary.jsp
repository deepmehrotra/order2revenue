<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
	
	function updateFields(obj) {
		if ($(obj).val() && "notSelected" != $(obj).val()) {
			var minAmount = parseInt($(obj).find(':selected').data('minamount'));
			var planPrice = $(obj).find(':selected').data('planprice');
			var orderCount = minAmount / planPrice;
			var taxAmount = minAmount * 14.5 / 100;
			var totalAmount = minAmount + taxAmount;
			$(".currPlanPriceTxt").html(planPrice);
			$(".currAmount").val(minAmount);
			$(".currTotalAmount").val(totalAmount);
			$(".currOrderCount").val(orderCount);
			$(".currAmountText").html(minAmount);
			$(".currTaxAmountText").html(taxAmount);
			$(".currTotalAmountText").html(totalAmount);
			$(".checkoutButton").removeAttr('disabled');
		} else {
			$(".currPlanPriceTxt").html(0);
			$(".currAmount").val(0);
			$(".currTotalAmount").val(0);
			$(".currOrderCount").val(0);
			$(".currAmountText").html(0);
			$(".currTaxAmountText").html(0);
			$(".currTotalAmountText").html(0);
			$(".checkoutButton").prop('disabled', true);
		}
	}
	function updatePrices(obj) {
		var currObjVal = $(obj).val();
		if (currObjVal && "" != currObjVal) {
			var minAmount = parseInt($(".selectPlanField").find(':selected')
					.data('minamount'));
			var currAmount = parseInt(currObjVal);
			if (currAmount >= minAmount) {
				$(".checkoutButton").removeAttr('disabled');
				var planPrice = $(".selectPlanField").find(':selected').data(
						'planprice');
				var orderCount = currAmount / planPrice;
				var taxAmount = currAmount * 14.5 / 100;
				var totalAmount = currAmount + taxAmount;
				$(".currTotalAmount").val(totalAmount);
				$(".currOrderCount").val(orderCount);
				$(".currAmountText").html(currAmount);
				$(".currTaxAmountText").html(taxAmount);
				$(".currTotalAmountText").html(totalAmount);
			} else {
				$(".currTotalAmount").val(0);
				$(".currOrderCount").val(0);
				$(".currAmountText").html(0);
				$(".currTaxAmountText").html(0);
				$(".currTotalAmountText").html(0);
				$(".checkoutButton").prop('disabled', true);
			}
		} else {
			$(".currTotalAmount").val(0);
			$(".currOrderCount").val(0);
			$(".currAmountText").html(0);
			$(".currTaxAmountText").html(0);
			$(".currTotalAmountText").html(0);
			$(".checkoutButton").prop('disabled', true);
		}
	}
</script>
<style type="text/css">
.column {
	background-color: #f2f2f2;
	height: 400px;
	width: 100%;
	margin-top: 20px;
}
.heading {
	color: #00a1f1;
	font-weight: 500;
}
.table th {
	border: 1px solid #ccc;
}
.para {
	position: relative;
	top: -87px;
	left: -2px;
	font-size: 14px;
	font-weight: 800;
}

@import
	url(https://fonts.googleapis.com/css?family=Roboto:500,100,300,700,400);

div.title {
	font-size: 2em;
}

h1 span {
	font-weight: 300;
	color: #Fd4;
}

div.stars {
	width: 213px;
	display: inline-block;
}

input.star {
	display: none;
}

label.star {
	float: right;
    padding: 1px;
    font-size: 20px;
    color: #444;
    transition: all .2s;
    position: relative;
    right: 58px;
}

input.star:checked   ~ label.star:before {
	content: '\f005';
	color: #FD4;
	transition: all .25s;
}

input.star-5:checked   ~ label.star:before {
	color: #FE7;
	text-shadow: 0 0 20px #952;
}

input.star-1:checked   ~ label.star:before {
	color: #F62;
}

label.star:hover {
	transform: rotate(-15deg) scale(1.3);
}

label.star:before {
	content: '\f006';
	font-family: FontAwesome;
}

.black-color {
	color: #000 !important;
}

.progress {
	width: auto !important;
	height: auto !important;
}
thead th 
    { 
    height: 30px;
    line-height: 30px;
    text-align: center;
    }
table,tr,td,th
{
    border:1px solid #ccc;
    padding:2px;
}
table td
{
    min-width: 88px;
}
.name
{
    white-space: nowrap;
}
thead,tbody { 
       display: block; 
   }
tbody {
   height: 100px; 
   overflow-y: auto; 
   overflow-x: scroll;
   width:448px;
}
</style>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight"
				id="centerpane" style="background: #fff;">
				<div class="row">
					<div class="col-lg-12 text-center">
						<div class="navy-line"
							style="border: 1px solid #1ab395; width: 6%; margin-left: 47%;">
						</div>
						<h1>My Account</h1>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="col-lg-6 text-center" style="background: #f5f5f5;">
							<h1 class="heading">Account Summary</h1>
							<div class="col-md-6 tooltip-demo">
								<img src="/O2R/seller/img/bucket.png" alt="bucket" width="50%" data-toggle="tooltip" data-placement="top" title="All balance credited into O2R account is non refundable & can only be used towards processing of orders & purchase of products/services from O2R.">
								<p>
									<b>Orders</b>
								</p>
								<p class="para black-color" style="padding: 9px 0px 4px 2px;font-size: 11px;">
									<c:out value="${myAccount.sellerAccount.orderBucket}" />
								</p>
							</div>
							<div class="col-md-6 tooltip-demo">
								<div>
									<p style="position: relative; left: -34%;">
										<b>Complete life</b>
									</p>
								</div>
								<c:set var="orderBucket" value="${myAccount.sellerAccount.orderBucket}"/>
								<c:choose>
									<c:when test="${orderBucket != null && orderBucket > 0}">
										<c:set var="remainingOrders" value="${myAccount.sellerAccount.orderBucket - myAccount.sellerAccount.totalOrderProcessed}" />
										<c:set var="ordersProcessed" value="${myAccount.sellerAccount.totalOrderProcessed}" />
										<c:set var="remainingOrdersPer" value="${remainingOrders/orderBucket*100}" />
										<c:set var="ordersProcessedPer" value="${ordersProcessed/orderBucket*100}" />
										<div class="progress" data-toggle="tooltip" data-placement="top" title="These bar represent the number of order purchased,consumed,remaining during the current cycle and the whole life period">
											<div class="progress-bar progress-bar-success"
												role="progressbar"
												style="width: <c:out value="${ordersProcessedPer}" />%; background-color: #b4d733;">
												<c:out value="${ordersProcessed}" />
											</div>
											<div class="progress-bar progress-bar-warning"
												role="progressbar" style="width: <c:out value="${remainingOrdersPer}" />%; background-color: #000;">
												<c:out value="${remainingOrders}" /></div>
										</div>
										<div class="progress" data-toggle="tooltip" data-placement="left" title="These bar represent the number of order purchased,consumed,remaining during the current cycle and the whole life period">
											<div class="progress-bar progress-bar-success"
												role="progressbar" style="width: 60%;">746</div>
											<div class="progress-bar progress-bar-warning"
												role="progressbar" style="width: 40%; background-color: #000;">
												254</div>
										</div>
										<div>
											<p style="position: relative; left: -31%; top: -5px;">
												<b>Complete Cycle</b>
											</p>
										</div>
										<p id="box1" style="visibility: hidden;">These bar represent
											the number of order purchased,consumed,remaining during the
											current cycle and the whole life period</p>
									</c:when>
									<c:otherwise>
										<div>Nothing in Order Bucket yet. Please purchase/upgrade your plan.</div>
									</c:otherwise>
								</c:choose>						
							</div>
							<div class="col-md-12">
								<div class="col-md-6 text-center">
									<p>
										<b>Current Plan</b>
									</p>
									<h1>
										<c:out value="${myAccount.plan.planName}" />
									</h1>
									<div class="stars">
										<form action="">
											<input class="star star-5" id="star-5" type="radio"
												name="star" /> <label class="star star-5" for="star-5"></label>
											<input class="star star-4" id="star-4" type="radio"
												name="star" /> <label class="star star-4" for="star-4"></label>
											<input class="star star-3" id="star-3" type="radio"
												name="star" /> <label class="star star-3" for="star-3"></label>
											<input class="star star-2" id="star-2" type="radio"
												name="star" /> <label class="star star-2" for="star-2"></label>
											<input class="star star-1" id="star-1" type="radio"
												name="star" /> <label class="star star-1" for="star-1"></label>
										</form>
									</div>
									<br>
									<h3>Activation Date</h3>
									<p>
										<b>
											<fmt:formatDate type="date"
												value="${myAccount.sellerAccount.ativationDate}" />
										</b>
									</p>
								</div>
								<div class="col-md-6 text-center">
									<p><b>Price per order</b></p>
									<h2>
										<b>&#8377; <c:out value="${myAccount.plan.planPrice}" /></b>
									</h2>
									<button class="btn" style="background-color:#00a1f1;color:#fff;margin-left: 16%;box-shadow: -2px 4px 0px #877070;font-weight: 800;">UPGRADE</button>
									<br> <br>
									<h3 style="margin-top: 16px;">Last Plan Upgrade Date</h3>
									<p>
										<b><fmt:formatDate type="date"
												value="${myAccount.sellerAccount.lastTransaction}" />
										</b>
									</p>
								</div>
							</div>
						</div>

						<div class="col-lg-6 text-center">
							<div class="col-lg-12" style="background: #f5f5f5;">
								<h1 class="heading">Payment History</h1>
								<table class="table table-striped" style="width: 100%;background:#2f4050;color: #fff;margin: 0px;border: none;">
                                    <thead>
                                    <tr>
                                        <th>
                                            Invoice ID
                                        </th>
                                        <th>
                                            Date of Transaction
                                        </th>
                                        <th>
                                            Transaction ID
                                        </th>
                                        <th>
                                            Status
                                        </th>
                                        <th>
                                            Amount
                                        </th>
                                        <th>
                                            <img src="/O2R/seller/img/download.png" alt="download">
                                        </th>
                                    </tr>
                                </thead>
                            </table>
                            <table class="table table-striped">
                                <tbody>
                                	<c:forEach items="${accountTransactions}" var="at">
										<tr>
											<td><c:out value="${at.invoiceId}"/></td>
											<td><fmt:formatDate type="date" value="${at.transactionDate}"/></td>
											<td><c:out value="${at.transactionId}"/></td>
											<td><c:out value="${at.status}"/></td>
											<td><c:out value="${at.transactionAmount}"/></td>
											<td></td>
										</tr>
									</c:forEach>
                                </tbody>
                            </table>	   
							</div>
							<div class="col-lg-12"
								style="background: #f5f5f5; margin-top: 20px;">
								<h1 class="heading">Usage History</h1>
								<table class="table table-striped" style="width: 100%;background:#2f4050;color: #fff;margin: 0px;border: none;">
	                                <thead>
	                                    <tr>
	                                        <th>Date</th>
	                                        <th>Amount Spent</th>
	                                        <th>Price / Order</th>
	                                        <th>Orders Bought</th>
	                                        <th>Orders Consumed</th>
	                                        <th>Orders Remaining</th>
	                                    </tr>
	                                </thead>
                            	</table>
	                            <table class="table table-striped">
	                                <tbody>
	                                    <tr>
	                                        <td>20 aug 2016</td>
	                                        <td>200</td>
	                                        <td>20</td>
	                                        <td>2000</td>
	                                        <td>2000</td>
	                                        <td>898989898988</td>
	                                    </tr>
	                                </tbody>
	                            </table> 
							</div>
						</div>
					</div>
				</div>
			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="../globaljslinks.jsp"></jsp:include>
	<script type="text/javascript">
	var $table = $('table.scroll'),
    $bodyCells = $table.find('tbody tr:first').children(),
    colWidth;
$(window).resize(function() {
    colWidth = $bodyCells.map(function() {
        return $(this).width();
    }).get();
    $table.find('thead tr').children().each(function(i, v) {
        $(v).width(colWidth[i]);
    });    
}).resize();
</script>

	<script type="text/javascript">
	$(document).ready(function() {		
		var starId=${myAccount.plan.planId};		
		document.getElementById("star-"+starId).checked="checked";
	});
	</script>
</body>
</html>