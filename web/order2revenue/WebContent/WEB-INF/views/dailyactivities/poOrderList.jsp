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
    		targeturl="poOrderDetails.html?value="+id;
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
							<div class="ibox-title" style='min-height: 90px;'>
								<div>
									<h5>PO Orders(${poOrders.size()})</h5>
									<div class="table-menu-links">
										<a href="orderList.html" id="Orders">MP Orders</a> <a href="#"
											id="returnOrders">Return</a> <a href="gatepasslist.html"
											id="gatepass">GatePass</a> <a href="#" id="paymentOrders">Payment</a>
										<a href="#" id="actionableOrders">Actionable</a>
									</div>
								</div>
								<div class="col-sm-12">
									<hr style='margin-top: 0.4em; margin-bottom: 0.7em;' />
								</div>
								<div class="col-sm-12">
									<div class="col-sm-5">
										<label>Period:</label> &nbsp;&nbsp;&nbsp;<label>
											${period} </label>
										<button class="btn btn-xs btn-grey"
											onclick="location.href = 'poOrderList.html?value=All';">View
											All</button>
										<!-- <input name="startDate" id="monthYearPicker" /> -->
										<%-- <input value="${period}" id="ChromeMonthPicker" type="month" 
											class="form-control input-sm" style="width:70%;"/>  --%>
									</div>
									<div class="col-sm-3">
										<label> Change Period: </label>
										<button class="btn btn-xs btn-grey active">Monthly</button>
										<button class="btn btn-xs btn-grey"
											onclick="onclickNavigateOrder('viewPOOrderDetails','0')">Annually</button>
									</div>
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
									</div>
								</div>
							</div>
							<div class="bs-example">

								<div class="ibox-content overflow-h cus-table-filters">
									<div class="scroll-y">
										<c:if test="${!empty savedPOOrder}">
											<h2 style="font-weight: bold">Your order ${savedPOOrder}
												is saved successfully.</h2>
										</c:if>
										<table
											class="table table-striped table-bordered table-hover dataTables-example">
											<thead>
												<tr>
													<th></th>
													<th>#</th>
													<th>Voucher ID</th>
													<th>Channel</th>
													<!-- <th>SKU</th> -->
													<th>Voucher Date</th>
													<th>Voucher REF</th>
													<th>Estimated Payment Date</th>
													<th>Voucher Amount</th>
													<th>A/R</th>
													<th>EOSS</th>
													<th>Payment DIFF</th>
													<th>Voucher QTY</th>
													<th>Net N/R</th>
													<th>Final Status</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty poOrders}">
													<c:forEach items="${poOrders}" var="poOrder"
														varStatus="loop">
														<tr>
															<td><input type="checkbox"></td>
															<td>${loop.index+1}</td>
															<td><a href="#"
																onclick="onclickNavigateOrder('viewPOOrder',${poOrder.orderId})">${poOrder.channelOrderID}</a></td>
															<td>${poOrder.pcName}</td>
															<%-- <td>${poOrder.productSkuCode}</td> --%>
															<td><fmt:formatDate value="${poOrder.orderDate}"
																	pattern="MMM dd ,YY" /></td>
															<td>${poOrder.invoiceID}</td>
															<td><fmt:formatDate
																	value="${poOrder.paymentDueDate}" pattern="MMM dd ,YY" /></td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2" value="${poOrder.poPrice}" /></td>
															<%-- <td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${poOrder.orderTax.tax}" /></td> --%>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2" value="${poOrder.pr}" /></td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2" value="${poOrder.eossValue}" /></td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2"
																	value="${poOrder.orderPayment.paymentDifference}" /></td>
															<td>${poOrder.quantity}</td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2" value="${poOrder.netRate}" /></td>
															<td>${poOrder.status}</td>
															<td></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
									<div class="col-sm-12">
										<div class="hr-line-dashed"></div>
										<a href="#" onclick="onclickNavigateOrder('uploadPO',0)"
											class="btn btn-success btn-xs">Bulk Upload PO</a>&nbsp;&nbsp;
										<a href="#" onclick="onclickNavigateOrder('downloadPO',0)"
											class="btn btn-success btn-xs">Download PO Format</a>
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
    	    	
    	$('#monthYearPicker').datepicker({
    			changeMonth: true,
    			changeYear: true,
    			showButtonPanel: true,
    			dateFormat: 'MM yy'
    		}).focus(function() {
    			var thisCalendar = $(this);
    			$('.ui-datepicker-calendar').detach();
    			$('.ui-datepicker-close').click(function() {
				   	var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
				   	var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
				   	thisCalendar.datepicker('setDate', new Date(year, month, 1));
    			});
    		});
    	
        $('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
        });
        
        $('#searchOrders').change(function () {     
   	     
	        var thisValue = $(this).children(":selected").attr("id");
	       	
	         if(thisValue == 1 || thisValue == 2 || thisValue == 3 || thisValue == 4 ||thisValue == 5 ){
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
            window.location="orderList.html?page="+pageno;
            	
        });
        $('#LoadFirst500').click(function (e) {
           window.location="orderList.html?page="+0;
            	
        });
        $('#returnOrders').click(function (e) {
            window.location="orderList.html?status=return";
             	
         });
        $('#paymentOrders').click(function (e) {
            window.location="orderList.html?status=payment";
             	
         });
        $('#actionableOrders').click(function (e) {
            window.location="orderList.html?status=actionable";
             	
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

.ui-datepicker-calendar {
	display: none;
}
</style>
</body>
</html>
