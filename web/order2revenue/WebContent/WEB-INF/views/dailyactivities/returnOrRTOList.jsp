<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>

<script type="text/javascript">
    function onclickReturn(value,id) {
    	var targeturl="";
    	switch(value)
    	{
    	case "uploadReturn" :
    		targeturl="uploadOrderDA.html?value=ordersummary";
    	break;
    	case "downloadReturn" :
    		targeturl="downloadOrderDA.html?value=ordersummary";
    	break;    		
    	}
        $.ajax({
            url : targeturl,
            success : function(data) {
            	if($(data).find('#j_username').length > 0){
            		window.location.href = "orderindex.html";
            	}else{
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
		<div class="wrapper wrapper-content animated" id="centerpane">
  			<div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Returns</h5>
                        </div>
                        <div class="ibox-content overflow-h">
                        <form method="POST" action="searchReturnOrder.html?action=list" id="findOrderForm" class="form-horizontal">
                        <div class="col-sm-12">
                            <div class="col-sm-4">
                                <div class="radio"><label>
                                 <input type="radio" value="searchByProperty" id="searchType" name="toggler">Search by Attribute</label>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="radio"><label>
                                 <input type="radio" value="searchByDate" id="searchType" name="toggler"> Search by Date </label>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 radio1" id="blk-searchByProperty">
                             <div class="row">
                                <div class="col-md-4">
                                    <select class="form-control" id="searchCriteria" name="searchCriteria">
                                   	<option value="channelOrderID">Order ID/PO No</option>
							    	<option value="awbNum">AWB</option>
							    	<option value="invoiceID">Invoice ID</option>
							    	<option value="subOrderID">Sub Order ID</option>
							    	<option value="PIreferenceNo">PI Reference No</option>
							    	<option value="uniqueItemId">Unique Item ID</option>
							    	<option value="returnOrRTOId">Sale Return ID/ Debit Note No</option>
							    	<option value="pcName">Partner</option> 
							    	<option value="productSkuCode">Product SKU</option>
							    	<option value="customerName">Customer Name</option> 
							    	<option value="customerCity">Customer City</option>
							    	<option value="customerEmail">Customer mail</option>
							    	<option value="customerPhnNo">Customer Phone No</option>    	
							    	<option value="status"> Order Status</option>
							    	<option value="sellerNote">Seller Notes</option> 
                                    </select>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" id="searchString" name="searchString" class="form-control" placeholder="Search String">
                                </div>
                               
                            </div>
                        </div>
						<div class="col-sm-12 radio1" id="blk-searchByDate">

							<div class="col-md-4">
								<select class="form-control" id="searchDateCriteria"
									name="searchDateCriteria">
									<option value="orderDate">Order Received Date</option>
									<option value="shippedDate">Order Shipped Date</option>
									<option value="deliveryDate">Order Delivery Expected Date</option>
									<option value="paymentDueDate">Payment Due Date</option>
									<option value="dateofPayment">Actual Date of Payment</option>
								</select>
							</div>
							<div class="col-md-3" id="data_1">
								<div class="input-group date">
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span><input type="text" name="startDate"
										id="startDate_1" class="form-control" >
								</div>
							</div>
							<div class="col-md-3" id="data_2">
								<div class="input-group date">
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span><input type="text" name="endDate"
										id="endDate_1" class="form-control" >
								</div>
							</div>
						</div>
						<div class="col-md-2">
	                         	<br><button class="btn btn-success " type="submit"><i class="fa fa-search"></i>&nbsp;&nbsp;<span class="bold">Search</span></button>
	                   	</div>
	                </form>
	                   	<c:if test="${!empty searchOrderList}">
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
											<th>Return Date</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
		
										<c:if test="${!empty searchOrderList}">
											<c:forEach items="${searchOrderList}" var="searchOrder"	varStatus="loop">
												<tr>
													<td>${loop.index+1}</td>
													<td>${searchOrder.pcName}</td>
													<td>${searchOrder.channelOrderID}</td>
													<td>${searchOrder.status}</td>
													<td><fmt:formatDate value="${searchOrder.returnLimitCrossed}"
															pattern="MMM dd ,YY" /></td>
													<td>${searchOrder.orderPayment.netPaymentResult}</td>
													<td><fmt:formatDate
															value="${searchOrder.orderReturnOrRTO.returnDate}"
															pattern="MMM dd ,YY" /></td>
													<c:if test="${empty searchOrder.orderReturnOrRTO.returnDate}">
														<td><label data-toggle="modal" data-target="#myModal23"
															onclick="setOrderID1(${searchOrder.orderId},'${searchOrder.channelOrderID}','return')"
															style='cursor: pointer;'>Mark Return</label></td>
													</c:if>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>					
							<!--  Code for Payment and Return Popup boxes -->
							<div class="col-sm-3">										
							<div class="modal inmodal fade" id="myModal23" tabindex="-1" role="dialog" aria-hidden="true">
								<form:form  method="POST" action="saveReturnorRTO.html" id="returnOrderListForm1" role="form" class="form-horizontal">
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
																<form:input path="orderId"
																			id="returnorderId" class="form-control" type="hidden"/>
																<form:input path="channelOrderID"
																			id="returnchannelOrderID" class="form-control" type="hidden"/>
																<label class="col-sm-4 control-label">Return Id</label>
																<div class="col-sm-8">
																	<form:input path="orderReturnOrRTO.returnOrRTOId" id="returnOrRTOId1" class="form-control" />
																</div>
															</div>
														</div>
														<br><br>
														<div class="col-sm-12">
															<div class="mar-btm-20-oh">
																<label class="col-sm-4 control-label">Return Date</label>
																	<div class="col-sm-8" id="data_3">
																		<div class="input-group date"><span class="input-group-addon"><i class="fa fa-calendar"></i></span>
																			<form:input path="orderReturnOrRTO.returnDate"  type="text" class="form-control" id="returnDate1" ></form:input>
																		</div>
																	</div>
															</div>
														</div>
														<br><br>
														<div class="col-sm-12">
															<div class="mar-btm-20-oh">
																<label class="col-sm-4 control-label">Return Quantity</label>
																<div class="col-sm-8">
																	<form:input path="orderReturnOrRTO.returnorrtoQty" id="quantity1"
																		class="form-control" />
																</div>
															</div>
														</div>														
														<br><br>
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
														<div class="col-sm-12" >
															<div class="mar-btm-20-oh">
																<label class="col-sm-4 control-label">Return Reason</label>
																<div class="col-sm-8">
																	<form:input path="orderReturnOrRTO.returnOrRTOreason" id="reason1"
																		class="form-control" />
																</div>
															</div>
														</div>
														<br> <br>
														<div class="col-sm-12">
															<div class="form-group">
																<label class="col-sm-4 control-label">Return Type</label>
																<div class="col-sm-8">
																	<form:select path="orderReturnOrRTO.type" class="form-control"	id="category01">
																		<form:option value="" disabled="true" selected="true">Return Type</form:option>
																		<form:option value="returnCharges">Return Charges</form:option>
																		<form:option value="RTOCharges">RTO Charges</form:option>
																		<form:option value="replacementCharges">Replacement
																			Charges</form:option>
																		<form:option value="partialDeliveryCharges">Partial
																			Delivery Charges</form:option>
																		<form:option value="cancellationCharges" id="">
																			Cancellation Charges</form:option>
																	</form:select>
																</div>
															</div>
														</div>														
														<br><br>
														<div class="col-sm-12">
															<div class="form-group">
																<label class="col-sm-4 control-label">Fault Type</label>
																<div class="col-sm-8">
																	<form:select path="orderReturnOrRTO.returnCategory"
																		class="form-control" onchange="show1()" id="category02">
																		<form:option value="" disabled="true" selected="true">Fault Type</form:option>
																		<form:option value="buyerReturn">Buyer Return</form:option>
																		<form:option value="sellerFault">Seller Fault</form:option>
																	</form:select>
																</div>
															</div>
														</div>
														<br> <br> <br>
														<div class="col-sm-12">
															<div class="form-group" id="showit" style="visibility: visible; display: none;">
																<label class="col-sm-4 control-label">Stages</label>
																<div class="col-sm-8">
																	<form:select path="orderReturnOrRTO.cancelType"
																		class="form-control" style="visibility: visible;" id="showit">
																		<form:option value="beforeRTD">Before RTD</form:option>
																		<form:option value="afterRTD">After RTD</form:option>
																	</form:select>
																</div>
							
															</div>
														</div>
														<br> <br>
														<div class="col-lg-12">
															<div class="modal-footer" style="border: none;">
																<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
																<button type="submit" class="btn btn-primary" >Save Return</button>
															</div>
														</div>
							
													</div>
												</div>
											</div>
										</div>						
									</form:form>
								</div>
			                </div>               
			          	</c:if>       	
	                   	
	                   <div class="col-sm-12">
										<div class="hr-line-dashed"></div>
										<a href="#" onclick="onclickReturn('uploadReturn',0)"
											class="btn btn-success btn-xs">Bulk Upload RTO/Return</a>&nbsp;&nbsp;
										<a href="#"
											onclick="onclickReturn('downloadReturn',0)"
											class="btn btn-success btn-xs">Download RTO/Return Format</a>
							</div>
                       
                        <div class="col-lg-12">
                                <div class="hr-line-dashed"></div>	
                                <div id="RTOTableContainer"></div>					
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
<script type="text/javascript">

$(document).ready(function () {
	
	$("#endDate_1")
	.change(
			function() {
				var startDate = document
						.getElementById("startDate_1").value;
				var endDate = document
						.getElementById("endDate_1").value;

				if ((Date.parse(startDate) > Date
						.parse(endDate))) {
					alert("End Date Should Be Greater Than Start Date !");
					document
							.getElementById("endDate_1").value = "";
				}
			});
	
	
	
	$('#badQuantityDiv').hide();
	$('#inventoryType').click(function(){		
	    if(($('#inventoryType :selected').val())=='badInventory'){
	    	$('#badQuantityDiv').show();
	    }else{
	    	$('#badQuantityDiv').hide();
	    }
	});	
	
	/* var badReturnType=document.getElementById("inventoryType").value;
	if(badReturnType== 'badInventory'){
		$('#badQuantityDiv').show();
	}else{
		$('#badQuantityDiv').hide();
	} */
    //Radio toggle
    $("[name=toggler]").click(function(){
    	 $('.radio1').hide();
        $("#blk-"+$(this).val()).slideDown();
    });

    $('#data_1 .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
    $('#data_2 .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
     });
    $('#data_3 .input-group.date').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    });
    
    
    $("#findOrderForm").validate();
    $("#searchString").rules("add", {
        required:true,
        messages: {
               required: "Please Enter ID!"
        }
     });
    $("#startDate_1").rules("add", {
        required:true,
        messages: {
               required: "Please Enter Start Date !"
        }
     });
    $("#endDate_1").rules("add", {
        required:true,
        messages: {
               required: "Please Enter End Date !"
        }
     });
    
    $("#returnOrderListForm1").validate();
    $("#returnOrRTOId1").rules("add", {
        required:true,
        messages: {
               required: "Please Enter  Return Id !"
        }
     });
    $("#returnDate1").rules("add", {
        required:true,
        messages: {
               required: "Return Date is mandatory !"
        }
     });
    $("#quantity1").rules("add", {
    	min : 1,
        required:true,
        messages: {           		
               
        }
     });
    
    $("#reason1").rules("add", {
        required:true,
        messages: {
               required: "Enter Return Cause !"
        }
     });
    $("#category01").rules("add", {
        required:true,
        messages: {
               required: "Select one Return Type !"
        }
     });
    $("#category02").rules("add", {
        required:true,
        messages: {
               required: "Select one Fault Type !"
        }
     });  
   
	
	
	$('#LoadOnSubmit').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: $("#findOrderForm").attr("action"),
        context: document.body,
        type: 'post',
        data:$("#findOrderForm").serialize(),
        success : function(res) {
             $("#RTOTableContainer").html(res);
     }
 });

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


function setOrderID1(orderid,channelorderid,value)
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

	var option = document.getElementById("category01").value;
    var option1 = document.getElementById("category02").value;
    
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
</body>
</html>