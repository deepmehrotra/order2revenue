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
    	
    	case "uploadGatePass" :
    		targeturl="uploadOrderDA.html?value=gatepassSummary";
    	break;
    	case "downloadGatePass" :
    		targeturl="downloadOrderDA.html?value=gatepassSummary";
    	break;
    	
    	case "viewPOOrderList" :
    		targeturl="poorderlist.html";
    	break;
    	case "viewGatePassList" :
    		targeturl="gatepasslist.html";
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
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>GatePasses(${gatepasses.size()})</h5>
					<div class="table-menu-links">
						<a href="#" id="POOrders"
							onclick="onclickNavigateOrder('viewPOOrderList','0')">PO
							Orders</a> <a href="#" id="returnOrders">Return</a> <a href="#"
							id="gatepass"
							onclick="onclickNavigateOrder('viewGatePassList','0')">GatePass</a>
						<a href="#" id="paymentOrders">Payment</a> <a href="#"
							id="actionableOrders">Actionable</a>
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
										id="searchOrder">
										<option value="searchchannelOrderID">Channel OrderId</option>
										<option value="SearchorderDate">Order Date</option>
									</select>
								</div>
								<div class="form-group OrderSearch-box"
									id="searchchannelOrderID">
									<input type="text" placeholder="Enter Channel OrderID"
										class="form-control" name="channelOrderID">
								</div>
								<div class="form-group OrderSearch-box" id="SearchorderDate"
									style="display: none">
									<div class="input-group date5">
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span><input type="text"
											class="form-control" placeholder="Start Date"
											name="startDate">
									</div>
									<div class="input-group date5">
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span><input type="text"
											class="form-control" placeholder="End Date" name="endDate">
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
				<div class="bs-example">
					<div class="ibox-content overflow-h cus-table-filters">
						<div class="scroll-y">
							<table
								class="table table-striped table-bordered table-hover dataTables-example">
								<thead>
									<tr>
										<th></th>
										<th>#</th>
										<th>PO ID</th>
										<th>Channel</th>
										<th>SKU</th>
										<th>Invoice ID</th>
										<th>Order Date</th>
										<th>Shipped Date</th>
										<th>Gross Sale Qty</th>
										<th>Rate(Gross PO Price)</th>
										<th>PO MRP/unit</th>
										<th>Amount(Net PO Price)</th>
										<th>N/R</th>
										<th>Payment Difference</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty gatepasses}">
										<c:forEach items="${gatepasses}" var="gatepass"
											varStatus="loop">
											<tr>
												<td><input type="checkbox"></td>
												<td>${loop.index+1}</td>
												<td><a href="#"
													onclick="onclickNavigateOrder('viewPOOrder',${gatepass.orderId})">${gatepass.subOrderID}</a></td>
												<td>${gatepass.pcName}</td>
												<td>${gatepass.productSkuCode}</td>
												<td>${gatepass.invoiceID}</td>
												<td><fmt:formatDate value="${gatepass.orderDate}"
														pattern="MMM dd ,YY" /></td>
												<td><fmt:formatDate value="${gatepass.shippedDate}"
														pattern="MMM dd ,YY" /></td>
												<td>${gatepass.quantity}</td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${gatepass.poPrice}" /></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${gatepass.orderMRP}" /></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${gatepass.orderSP}" /></td>
												<td><fmt:formatNumber type="number"
														maxFractionDigits="2" value="${gatepass.netRate}" /></td>
												<td>${gatepass.orderPayment.paymentDifference}</td>
												<td>${gatepass.status}</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
						<div class="col-sm-12">
							<div class="hr-line-dashed"></div>
							<a href="#" onclick="onclickNavigateOrder('uploadGatePass',0)"
								class="btn btn-success btn-xs">Bulk Upload GatePass</a>&nbsp;&nbsp;
							<a href="#" onclick="onclickNavigateOrder('downloadGatePass',0)"
								class="btn btn-success btn-xs">Download GatePass Format</a>
						</div>

					</div>
				</div>
			</div>
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
                    "sSwfPath": "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
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
</style>
</body>
</html>