<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="globalcsslinks.jsp"></jsp:include>
<style>
#register {
	position: fixed;
	left: 15%;
	top: 20%;
	width: 400px;
	padding: 10px;
	display: none;
}
</style>
<style>
#payment {
	position: fixed;
	left: 15%;
	top: 20%;
	width: 400px;
	padding: 10px;
	display: none;
}
</style>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated" id="centerpane">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>Search Results</h5>
							</div>
							<div class="ibox-content">
								<table class="table table-bordered custom-table">
									<thead>
										<tr>
											<th>#</th>
											<th>Partner</th>
											<th>Channel Order Id</th>
											<th>Status</th>
											<th>Return Limit</th>
											<th>Payment Result</th>
											<th>Status</th>
											<th colspan="2">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty searchOrderList}">
											<c:forEach items="${searchOrderList}" var="searchOrder"
												varStatus="loop">
												<tr>
													<td>${loop.index+1}</td>
													<td>${searchOrder.pcName}</td>
													<td><a href="#"
														onclick="onclickNavigateOrder(${searchOrder.orderId})">${searchOrder.channelOrderID}</a></td>
													<td>${searchOrder.status}</td>
													<td><fmt:formatDate
															value="${searchOrder.returnLimitCrossed}"
															pattern="MMM dd ,YY" /></td>
													<td><fmt:formatNumber type="number"
                                                                maxFractionDigits="2" 
                                                                value="${searchOrder.orderPayment.netPaymentResult}" />
                                                                </td>
													<td>${searchOrder.status}</td>
													<c:if
														test="${empty searchOrder.orderReturnOrRTO.returnDate}">
														<td><label data-toggle="modal"
															data-target="#myModal22"
															onclick="setOrderID(${searchOrder.orderId},'${searchOrder.channelOrderID}','return')"
															style='cursor: pointer;'>Mark Return</label></td>
													</c:if>
													<td><label data-toggle="modal"
															data-target="#myModal21"
														onclick="setOrderID(${searchOrder.orderId},'${searchOrder.channelOrderID}','payment')"
														style='cursor: pointer;'>Mark Paid</label></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

				<!--  Code for Payment and Return Popup boxes -->

				<div class="col-sm-3">					
						<div class="modal inmodal fade" id="myModal22" tabindex="-1" role="dialog" aria-hidden="true">
							<form:form method="POST" action="saveReturnorRTO.html" id="saveReturnorRTOForm" role="form" class="form-horizontal">
							<div class="modal-dialog modal-lg">
								<div class="modal-content animated bounceInRight"
									style="left: 22%; width: 50%;">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">Return Details</h4>
									</div>
									<div class="modal-body" style="padding: 20px 30px 50px 30px;">
										<div class="row">
											<div class="col-sm-12">
												<div class="form-group">
													<form:input path="orderId" id="returnorderId"
														class="form-control" type="hidden" />
													<form:input path="channelOrderID" id="returnchannelOrderID"
														class="form-control" type="hidden" />
													<label class="col-sm-4 control-label">Return Id</label>
													<div class="col-sm-8">
														<form:input path="orderReturnOrRTO.returnOrRTOId" class="form-control"	id="returnOrRTOId" />
													</div>
												</div>
											</div>
											<br> <br> <br>
											<div class="col-sm-12">
												<div class="mar-btm-20-oh">
													<label class="col-sm-4 control-label">Return Date</label>
													<div class="col-sm-8">
														<div class="input-group date"><span class="input-group-addon"><i class="fa fa-calendar"></i></span>
															<form:input path="orderReturnOrRTO.returnDate"  type="text" class="form-control" id="returnDate" ></form:input>
															
														</div>
													</div>
												</div>
											</div>
											<br> <br> <br>
											<div class="col-sm-12">
												<div class="mar-btm-20-oh">
													<label class="col-sm-4 control-label">Return
														Quantity</label>
													<div class="col-sm-8">
														<form:input path="orderReturnOrRTO.returnorrtoQty" class="form-control" id="quantity"/>
													</div>
												</div>
											</div>
											<br> <br> <br>
											<div class="col-sm-12">
												<div class="form-group">
													<label class="col-sm-4 control-label">Inventory Type</label>
													<div class="col-sm-8">
														<form:select path="orderReturnOrRTO.inventoryType" class="form-control"	id="inventoryType">
															<form:option value="goodInventory" selected="true">Good Inventory</form:option>
															<form:option value="badInventory">Bad Inventory</form:option>																		
														</form:select>
													</div>
												</div>
											</div>
											<br><br><br>														
											<div class="col-sm-12" id="badQuantityDiv">															
												<div class="mar-btm-20-oh">
													<label class="col-sm-4 control-label">Return Quantity(Bad)</label>
													<div class="col-sm-8">
														<form:input path="orderReturnOrRTO.badReturnQty" id="badQuantity" class="form-control" onchange="badCheck();"/>
													</div>
												</div>
											</div>
											<br>
											<div class="col-sm-12">
												<div class="mar-btm-20-oh">
													<label class="col-sm-4 control-label">Return Reason</label>
													<div class="col-sm-8">
														<form:input path="orderReturnOrRTO.returnOrRTOreason" class="form-control" id="reason"/>
													</div>
												</div>
											</div>
											<br> <br>
											<div class="col-sm-12">
												<div class="form-group">
													<label class="col-sm-4 control-label">Return Type</label>
													<div class="col-sm-8">
														<form:select path="orderReturnOrRTO.type" class="form-control" id="category">
															<form:option value="" disabled="true" selected="true">Return Type</form:option>
															<form:option value="returnCharges">Return Charges</form:option>
															<form:option value="RTOCharges">RTO Charges</form:option>
															<form:option value="replacementCharges">Replacement Charges</form:option>
															<form:option value="partialDeliveryCharges">Partial	Delivery Charges</form:option>
															<form:option value="cancellationCharges" id="">Cancellation Charges</form:option>
														</form:select>
													</div>
												</div>
											</div>
											<br> <br> <br> <br>
											<div class="col-sm-12">
												<div class="form-group">
													<label class="col-sm-4 control-label">Fault Type</label>
													<div class="col-sm-8">
														<form:select path="orderReturnOrRTO.returnCategory"	class="form-control" onchange="show1()" id="category1">
															<form:option value="" disabled="true" selected="true">Fault Type</form:option>
															<form:option value="buyerReturn">Buyer Return</form:option>
															<form:option value="sellerFault">Seller Fault</form:option>
														</form:select>
													</div>
												</div>
											</div>
											<br> <br> <br>
											<div class="col-sm-12">
												<div class="form-group" id="showit"
													style="visibility: visible; display: none;">
													<label class="col-sm-4 control-label">Stages</label>
													<div class="col-sm-8">
														<form:select path="orderReturnOrRTO.cancelType"
															class="form-control" style="visibility: visible;"
															id="showit">
															<form:option value="beforeRTD">Before RTD</form:option>
															<form:option value="afterRTD">After RTD</form:option>
														</form:select>
													</div>

												</div>
											</div>
											<br> <br>
											<div class="col-lg-12">
												<div class="modal-footer" style="border: none;">													
													<button type="submit" class="btn btn-primary">Save</button>
													<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>	
							</form:form>					
						</div>					
				</div>

				<div class="col-sm-3">					
					<div class="modal inmodal fade" id="myModal21" tabindex="-1"
						role="dialog" aria-hidden="true">
						<form:form method="POST" action="saveManualPayment.html"
										id="saveOrderPaymentForm" role="form" class="form-horizontal">

							<div class="modal-dialog modal-lg">
								<div class="modal-content animated bounceInLeft"
									style="left: 22%; width: 50%;">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">Mark Paid</h4>
									</div>
									<div class="modal-body" style="padding: 20px 30px 50px 30px;">
										<div class="row">
											<div class="col-sm-12">
											<div class="form-group">
											<form:input type="hidden" path="orderId" id="paymentOrderId" value="" />
											<form:input type="hidden" path="channelOrderID" id="paymentChannelOrderID" value="" />
												<label class="col-sm-5 control-label">Positive Payment</label>

												<div class="col-sm-7">
													<form:input path="orderPayment.positiveAmount" class="form-control" id="positiveAmount" width='200' />													
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-5 control-label">Negative Payment</label>
												<div class="col-sm-7">
													<form:input path="orderPayment.negativeAmount"	class="form-control" id="negativeAmount" width='200' />
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-5 control-label">Payment Date</label>
												
												<div class="col-md-7" >
													<div class="input-group date"><span class="input-group-addon"><i class="fa fa-calendar"></i></span>
														<form:input class="form-control" type="text" id="dateofPayment" path="orderPayment.dateofPayment" width='200' />
													</div>
												</div>
											</div>
										</div>
										<div class="col-lg-12">
												<div class="modal-footer" style="border: none;">													
													<button type="submit" class="btn btn-primary">Save</button>
													<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
												</div>
										</div>
										

										</div>
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>

			</div>
			<jsp:include page="globalfooter.jsp"></jsp:include>

		</div>
	</div>

	<jsp:include page="globaljslinks.jsp"></jsp:include>
	<script type="text/javascript">
	
$(document).ready(function(){
	
	$('#badQuantityDiv').hide();
	$('#inventoryType').click(function(){		
	    if(($('#inventoryType :selected').val())=='badInventory'){
	    	$('#badQuantityDiv').show();
	    }else{
	    	$('#badQuantityDiv').hide();
	    }
	});
	
	
	
    $('#data_1 .input-group.date').datepicker({
    	    todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
    
    $("#saveReturnorRTOForm").validate();
    $("#returnOrRTOId").rules("add", {
        required:true,
        messages: {
               required: "Please Enter  Return Id !"
        }
     });
    $("#returnDate").rules("add", {
        required:true,
        messages: {
               required: "Return Date is mandatory !"
        }
     });
    $("#quantity").rules("add", {
    	min : 1,
        required:true,
        messages: {           		
               
        }
     });
    $("#reason").rules("add", {
        required:true,
        messages: {
               required: "Enter Return Cause !"
        }
     });
    $("#category").rules("add", {
        required:true,
        messages: {
               required: "Select one Return Type !"
        }
     });
    $("#category1").rules("add", {
        required:true,
        messages: {
               required: "Select one Fault Type !"
        }
     });
    
    
    
    $("#saveOrderPaymentForm").validate();
    $("#negativeAmount").rules("add", {    	
    	required:true,
        messages: {
        	required: "Enter a Amount !"
        }
     });
    $("#positiveAmount").rules("add", {
    	required:true,
        messages: {        		
        	required: "Enter a Amount !"
        }
     });
    $("#dateofPayment").rules("add", {
        required:true,
        messages: {   
        		required: "Select a Return Date ! "
        }
     });
    
    
});


function badCheck(){
	var goodQ=document.getElementById("quantity1").value;
	var badQ=document.getElementById("badQuantity").value;
	
	if(goodQ < badQ){		
		alert("Invalid Bad Return Quantity !");
		document.getElementById("badQuantity").value="";
	}
}

function setOrderID(orderid,channelorderid,value)
{	
	if(value=='return')
		{
	/* setVisibility('payment', 'none'); */
	document.getElementById("returnorderId").value=orderid;
	document.getElementById("returnchannelOrderID").value=channelorderid;
}
	else
		{
		/* setVisibility('payment', 'inline'); */		
		document.getElementById("paymentOrderId").value=orderid;		
		document.getElementById("paymentChannelOrderID").value=channelorderid;
		}

}

function setVisibility(id, visibility) {
	document.getElementById(id).style.display = visibility;
	}
	
function onclickNavigateOrder(id)
{
	   $.ajax({
           url :"viewOrderDA.html?orderId="+id,
           success : function(data) {
        	   if($(data).find('#j_username').length > 0){
           		window.location.href = "orderindex.html";
           	}else{
               	$('#centerpane').html(data);
           	}
           }
       });
	}

function show1(){

	var option = document.getElementById("category").value;
    var option1 = document.getElementById("category1").value;
    
    if(option == "cancellationCharges" && option1 == "sellerFault")
    {
      document.getElementById("showit").style.display="block";
    }   
  }
  function mydate() {
	  d = new Date(document.getElementById("datefield").value);
	  dt = d.getDate();
	  mn = d.getMonth();
	  mn++;
	  yy = d.getFullYear();
	  date = mn + "/" + dt + "/" + yy;
	  document.getElementById("datevalue").value = date;
	}
  
</script>
</html>