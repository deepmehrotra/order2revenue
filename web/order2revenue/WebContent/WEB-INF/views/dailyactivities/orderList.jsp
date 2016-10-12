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
<link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
<link href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet">
<link href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet">
<script type="text/javascript">
    function onclickNavigateOrder(value,id) {
    	var targeturl="";
    	switch(value)
    	{
    	case "editOrder" :
    		targeturl="editOrderDA.html?orderId="+id;
    	break;
    	case "deleteOrder" :
    		targeturl="deleteOrderDA.html?orderId="+id;
    	break;
    	case "viewOrder" :
    		targeturl="viewOrderDA.html?orderId="+id;
    	break;
    	case "addOrder" :
    		targeturl="addOrderDA.html";
    	break;
    	case "upload" :
    		targeturl="uploadOrderDA.html?value=ordersummary";
    	break;
    	case "download" :
    		targeturl="downloadOrderDA.html?value=ordersummary";
    	break;
    	
    	case "viewPOOrder" :
    		targeturl="viewPOOrderDA.html?orderId="+id;
    	break;
    	case "uploadPO" :
    		targeturl="uploadOrderDA.html?value=orderPoSummary";
    	break;
    	case "downloadPO" :
    		targeturl="downloadOrderDA.html?value=orderPoSummary";
    	break;
    	
    	case "viewPOOrderDetails" :
    		targeturl="poOrderDetails.html";
    	break;
    	case "viewGatePassList" :
    		targeturl="gatepasslist.html";
    	break;
    	case "poOrderList" :
    		targeturl="poOrderList.html?value="+id;
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
										<h5><a href="orderList.html">Orders</a></h5>
										<div class="table-menu-links">
											<div class="pull-left" style="width: 74%;">
												<a href="poOrderList.html?value=" id="POOrders">PO Orders</a> 
												<a href="gatepasslist.html" id="gatepass">GatePass</a>
												<c:choose>
													<c:when test="${searchStatus eq 'return'}">	
														<a href="orderList.html?status=return"><strong>Return</strong></a>												 
														<a href="orderList.html?status=payment">Payment</a>												
													</c:when>
													<c:when test="${searchStatus eq 'payment'}">
														<a href="orderList.html?status=return">Return</a>												 
														<a href="orderList.html?status=payment"><strong>Payment</strong></a>
													</c:when>
													<c:otherwise>
														<a href="orderList.html?status=return">Return</a>												 
														<a href="orderList.html?status=payment">Payment</a>
													</c:otherwise>
												</c:choose>
												<form role="search" class="form-inline" method="GET"
													action="" style="width: 16%;float: right;display: flex;">
													<select class="btn btn-primary btn-xs" id="orderCriteria" name="orderCriteria" required="required" style="margin-right: 3px;color: #676a6c !important;">
														<option value="">Select To Search</option>		
														<option value="Payment Recieved" id="Payment Recieved">Payment Recieved</option>									
														<option value="Payment Deducted" id="Payment Deducted">Payment Deducted</option>
														<option value="Payment Disputed" id="Payment Disputed">Payment Disputed</option>											
														<option value="Return Limit Crossed" id="Return Limit Crossed">Return Limit Crossed</option>
														<option value="Return Not Recieved" id="Return Not Recieved">Return Not Recieved</option>
														<option value="Return Recieved" id="Return Recieved">Return Recieved</option>
														<option value="Shipped" id="Shipped">Shipped</option>
														<option value="Actionable" id="Actionable">Actionable</option>
														<option value="Settled" id="Settled">Settled</option>
														<option value="In Process" id="In Process">In Process</option>											
													</select>
													<button class="btn btn-primary btn-xs" type="submit">GET</button>
												</form>	
											</div>																					
										</div>										
										<c:if test="${listCount > 0 }">
											<c:choose>
												<c:when test="${listCount >= listSize}">
													<label class="m-l-lg">${listSize-499} - ${listSize} of ${listCount}</label>
												</c:when>
												<c:when test="${listCount < listSize}">
													<label class="m-l-lg">${listSize-499} - ${listCount} of ${listCount}</label>
												</c:when>																			
											</c:choose>									
										</c:if>
										<c:if test="${listCount == 0 }">
											<label class="m-l-lg">0 - 0 of 0</label>
										</c:if>
										<div class="ibox-tools">
											<button class="btn btn-white table-menu-search search-dd">
												<i class="fa fa-search"></i>
											</button>
											<div class="search-more-wrp">
												<form role="search" class="form-inline" method="post"
													action="searchOrder.html">
													<div class="form-group">
														<select class="form-control" name="searchOrder"
															id="searchOrders">
															<option id="1" value="channelOrderID">Channel
																OrderId/PO ID</option>
															<option id="2" value="invoiceID">Invoice ID</option>
															<option id="3" value="subOrderID">Sub Order ID</option>
															<option id="4" value="pcName">Partner</option>
															<option id="5" value="customerName">Customer Name</option>
															<option id="6" value="status">Order Status</option>
															<option id="7" value="orderDate">Order Date</option>
															<option id="8" value="shippedDate">Order Shipped
																Date</option>
														</select>
													</div>
													<div class="form-group TopSearch-box001 OrderSearch-box "
														id="searchchannelOrderID">
														<input type="text" placeholder="Enter Channel OrderID"
															class="form-control" name="channelOrderID">
													</div>
													<div class="form-group TopSearch-box002 OrderSearch-box"
														id="SearchorderDate" style="display: none">
														<div class="input-group date">
															<span class="input-group-addon"><i
																class="fa fa-calendar"></i></span><input type="text"
																class="form-control" placeholder="" name="startDate">
														</div>
														<div class="input-group date">
															<span class="input-group-addon"><i
																class="fa fa-calendar"></i></span><input type="text"
																class="form-control" placeholder="" name="endDate">
														</div>
													</div>											
													<div class="form-group">
														<button class="btn btn-primary btn-block" type="submit">Search</button>
													</div>
												</form>
											</div>
											<span>Last</span>
											<button type="button" id="LoadFirst500"
												class="btn btn-xs btn-white active">500</button>
											<button type="button" class="btn btn-xs btn-white">1000</button>
											<button type="button" id="LoadMoreOrder"
												class="btn btn-xs btn-white">More</button>
											<a href="#" class="btn btn-primary btn-xs">Create New Order</a>
										</div>
									</div>
							<!-- onclick="onclickNavigateOrder('addOrder','0')" -->
							<div class="bs-example">
								<div class="ibox-content overflow-h cus-table-filters">
									<div class="scroll-y">
										<c:if test="${!empty savedOrder}">
											<h2 style="font-weight: bold">Your order ${savedOrder}
												is saved successfully.</h2>
										</c:if>
										<table
											class="table table-striped table-bordered table-hover dataTables-example">
											<thead>
												<tr>
													<th></th>
													<th>#</th>
													<th>Order ID</th>
													<th>Partner</th>
													<th>SKU</th>
													<th>Invoice ID</th>
													<th>Order Recieved Date</th>
													<th>Shipped Date</th>
													<th>Estimated Delivery Date</th>
													<th>Estimated Payment Date</th>
													<th>Quantity</th>
													<th>N/R</th>
													<th>Payment Difference</th>
													<th>Status</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty orders}">
													<c:forEach items="${orders}" var="order" varStatus="loop">
														<tr>
															<td><input type="checkbox"></td>
															<td>${loop.index+1}</td>
															<td><a href="#"
																onclick="onclickNavigateOrder('viewOrder',${order.orderId})">${order.channelOrderID}</a></td>
															<td>${order.pcName}</td>
															<td>${order.productSkuCode}</td>
															<td>${order.invoiceID}</td>
															<td><fmt:formatDate value="${order.orderDate}"
																	pattern="MMM dd ,YY" /></td>
															<td><fmt:formatDate value="${order.shippedDate}"
																	pattern="MMM dd ,YY" /></td>
															<td><fmt:formatDate value="${order.deliveryDate}"
																	pattern="MMM dd ,YY" /></td>
															<td><fmt:formatDate value="${order.paymentDueDate}"
																	pattern="MMM dd ,YY" /></td>
															<td>${order.quantity}</td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2" value="${order.netRate}" /></td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2"
																	value="${order.orderPayment.paymentDifference}" /></td>
															<td>${order.status}</td>														
															<td class="tooltip-demo"><a href="#"><i
																	class="fa fa-edit text-navy" data-toggle="tooltip"
																	data-placement="top" data-original-title="Edit"></i></a></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
									<%-- onclick="onclickNavigateOrder('editOrder',${order.orderId})" --%>
									<div class="col-sm-12">
										<div class="hr-line-dashed"></div>
										<a href="#" onclick="onclickNavigateOrder('upload',0)"
											class="btn btn-success btn-xs">Bulk Upload Order</a>&nbsp;&nbsp;
										<a href="#" onclick="onclickNavigateOrder('download',0)"
											class="btn btn-success btn-xs">Download Order Format</a>
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
	<script	src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script	src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>

	<script>
    $(document).ready(function(){
        $('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
        });
        
        $('#searchOrders').change(function () {     
   	     
	        var thisValue = $(this).children(":selected").attr("id");
	       	
	         if(thisValue == 1 || thisValue == 2 || thisValue == 3 || thisValue == 4 ||thisValue == 5 ||thisValue == 6 ){
	        	$('.TopSearch-box001').show();
	        	$('.TopSearch-box002').hide();
	        }
	        else{
	        	 $('.TopSearch-box001').hide();
	        	 $('.TopSearch-box002').show();
	        } 
	    });
        
        
        
        $('#searchOrder').change(function () {
            $('.OrderSearch-box').hide();
            $('#'+$(this).val()).fadeIn();
        });
        $('.search-dd').on('click', function(e){
            e.stopPropagation();
            $('.search-more-wrp').slideToggle();
        });
        $('.input-group.date5').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        
        $('#LoadMoreOrder').click(function (e) {
            e.preventDefault();
             pageno=parseInt(getQueryVariable("page"));
             pageno=pageno+1;
             var status = "${searchStatus}";
             alert(status);
             if(status != ""){
            	 window.location="orderList.html?page="+pageno+"&status="+status; 
             } else {
            	window.location="orderList.html?page="+pageno;
             }
        });               
        $('#LoadFirst500').click(function (e) {
           window.location="orderList.html?page="+0;            	
        });      
        
    });
    
    function getQueryVariable(variable) {
    	  var query = window.location.search.substring(1);
    	  var vars = query.split("&");
    	  for (var i=0;i<vars.length;i++) {
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
