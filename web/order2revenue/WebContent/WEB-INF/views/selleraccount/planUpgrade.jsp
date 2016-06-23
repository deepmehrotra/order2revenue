<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>
  <script type="text/javascript"
    src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function onclickviewExpCat(id) {
       $.ajax({
            url : 'viewExpenseGroup.html?expcategoryId='+id,
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
    function onclickAddTaxCategory() {
    	  $.ajax({
            url : 'addTaxCategory.html',
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
	function updateFields(obj){
		if($(obj).val() && "notSelected" != $(obj).val()){
			var minAmount = parseInt($(obj).find(':selected').data('minamount'));
			var planPrice = $(obj).find(':selected').data('planprice');
			var orderCount = minAmount/planPrice;
			var taxAmount = minAmount*14.5/100;
			var totalAmount = minAmount + taxAmount;
			$(".currPlanPriceTxt").html(planPrice);
			$(".currAmount").val(minAmount);
			$(".currTotalAmount").val(totalAmount);
			$(".currOrderCount").val(orderCount);
			$(".currAmountText").html(minAmount);
			$(".currTaxAmountText").html(taxAmount);
			$(".currTotalAmountText").html(totalAmount);
			$(".checkoutButton").removeAttr('disabled');
		}else{
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
	function updatePrices(obj){
		var currObjVal = $(obj).val();
		if(currObjVal && "" != currObjVal){
			var minAmount = parseInt($(".selectPlanField").find(':selected').data('minamount'));
			var currAmount = parseInt(currObjVal);
			if(currAmount >= minAmount){
				$(".checkoutButton").removeAttr('disabled');
				var planPrice = $(".selectPlanField").find(':selected').data('planprice');
				var orderCount = currAmount/planPrice;
				var taxAmount = currAmount*14.5/100;
				var totalAmount = currAmount + taxAmount;
				$(".currTotalAmount").val(totalAmount);
				$(".currOrderCount").val(orderCount);
				$(".currAmountText").html(currAmount);
				$(".currTaxAmountText").html(taxAmount);
				$(".currTotalAmountText").html(totalAmount);
			} else{
				$(".currTotalAmount").val(0);
				$(".currOrderCount").val(0);
				$(".currAmountText").html(0);
				$(".currTaxAmountText").html(0);
				$(".currTotalAmountText").html(0);
				$(".checkoutButton").prop('disabled', true);
			}
		} else{
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
    .box{
        
        display: none;
		}
	.table ,th,td{
		font-weight: 800;
		border:none !important;
	}
	.tabltd
	{
		border-top: 2px solid #ccc !important;
	}
	
	table td + td{
	font-weight:800;

	}
	.column
	{
		background-color: #ededed;
		height: 570px;
		width: 100%;
	}
	.column p
	{
		font-size: 14px;
	}
	.column h3
	{
		padding-top: 32px;
		color: #55c2ac;
	}
	.column button
	{
		margin:61px 0px 3px 26px;
    	width: 80%;
    	background-color: #1ab394;
    	color:#fff;
	}
	.column1
	{
		background-color: #ededed;
		height: 200px;
		width: 100%;
		margin-top: 20px;
	}
	.column1 p
	{
		font-size: 14px;
	}
	.column1 h3
	{
		padding-top: 32px;
		color: #55c2ac;
	}
	.column1 button
	{
		margin:0px 0px 3px 26px;
    	width: 80%;
    	background-color: #1ab394;
    	color:#fff;
	}
	.span
	{
		color: #1ab394;
	}


       
    </style>
 </head>
 <body>
  <div id="wrapper">
<jsp:include page="../sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="../globalheader.jsp"></jsp:include>  
		<div class="wrapper wrapper-content animated fadeInRight" id="centerpane" style="background: #fff;"> 
			<div class="row">
                <div class="col-lg-12 text-center">
					<div class ="navy-line" style="border:1px solid #1ab395;width: 6%;margin-left: 47%;">
					</div>
					<h1>Upgrade Plan </h1>
				</div>
            </div>
			<div class="row">
                <div class="col-lg-12">
					<div class="col-lg-9">
						<div class="col-md-4">
							<div class="column text-center">
								<h3>Current Plan</h3>
								<button class="btn btn-block"><c:out value="${myAccount.plan.planName}"/></button>
								<br>
								<div align="center">
									<p>								
										<img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${myAccount.plan.planPrice}"/> per Order
									</p>
									<p>
										MIN AMOUNT = <span><img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${myAccount.plan.orderCount}"/>/-</span>
									</p>
									<p>
										ORDER COUNT = <c:out value="${myAccount.plan.orderCount/myAccount.plan.planPrice}"/> 
									</p>
								</div>
							</div>
						</div>
						<c:forEach items="${upgrade}" var="up">
						<div class="col-md-4">
							<div class="column1 text-center">
								<button class="btn btn-block"><c:out value="${up.planName}"/></button>
								<br>
								<div align="center">
									<p>								
										<img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${up.planPrice}"/> per Order
									</p>
									<p>
										MIN AMOUNT = <span><img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${up.orderCount}"/>/-</span>
									</p>
									<p>
										ORDER COUNT = <c:out value="${up.orderCount/up.planPrice}"/> 
									</p>
								</div>
							</div>
						</div>
						</c:forEach>
					</div>
					<div class="col-lg-3">
						<div class="column text-center">
							<form id="selectPlan" action="thankyou.html" method="post" >
							<table class="table"cellspacing="0"cellpadding="0" border="0">
								<tbody>
									<tr>
										<td>
											Desired Plan
										</td>
										<td>
											<select class="form-control selectPlanField" name="pid" onchange="updateFields(this)">
												<option value="notSelected">
													Select Plan
												</option>
												<c:forEach items="${upgrade}" var="up">
													<option value='<c:out value="${up.pid}"/>' 
														data-planprice='<c:out value="${up.planPrice}"/>'
														data-minamount='<c:out value="${up.orderCount}"/>'>
														<c:out value="${up.planName}"/>
													</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td>
											Desired Amount	
										</td>
										<td>
											<input type="number" class="form-control currAmount" name="amount" oninput="updatePrices(this)">
											<small>Minimum Recharge Amount</small>
										</td>

									</tr>
									<tr>
										<td>
											Price/Order<br>
											<small>As Per Current Plan</small>
										</td>
										<td>
											<img src="/O2R/seller/img/rupee.png" alt="rupee"> <span class="currPlanPriceTxt"></span> per Order
										</td>
									</tr>
									<tr>
										<td>
											Order Count<br>
											<small>that will be added through <br>this transaction</small>
										</td>
										<td>
											<input type="text" class="form-control currOrderCount" name="orderCount" style="width: 60%; float: left;" readonly="true">Orders
										</td>
									</tr>
									<tr style="border-top: 2px solid #ccc;">
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td>
											<p>Amount : &#8377; <span class="span currAmountText">0</span>/-</p>
										</td>

									</tr>
									<tr>
										<td></td>
										<td>
											<p>Service Tax : &#8377; <span class="span currTaxAmountText">0</span>/-</p>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="tabltd">
											
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<input type="hidden" class="currTotalAmount" name="totalAmount" >
											<p>Total: &#8377; <span class="span currTotalAmountText">0</span>/-</p>
										</td>
									</tr>
								</tbody>
							</table>
							<button type="submit" disabled="true" class="btn btn-gray pull-right checkoutButton" style="width: 50%;margin-top: -14px;margin-right: 8px;box-shadow: 2px solid;box-shadow: 4px 11px 10px #ccc;">Checkout</button>
							</form>
						</div>
					</div>	
                </div>
				<div class="col-lg-12 text-center">
					<br>
					<p style="font-weight:bolder;">
						<a href="#">Plz note</a> the above mentioned charges are subjected to additional service tax @ 14.5% over and above the stated charges. No additional or hidden charges<br>
						<a href="#">Order Count</a> is the number of orders purchased by the seller. The seller can choose to recharge his account with any amount equal to greater than the stated minimum amounts.<br>
						<a href="#">Purchased Orders</a> have unlimited time validity. They can used as per the consumption levels without any fixed monthly.liability.<br>
						The seller will receive reminders to recharge his account when only 10% of purchased order count.are left in the order bucket.
					</p>
				</div>
            </div>
		</div>
 <jsp:include page="../globalfooter.jsp"></jsp:include>

    </div>
</div>

<jsp:include page="../globaljslinks.jsp"></jsp:include>
</html>