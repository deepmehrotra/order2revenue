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
                $('#centerpane').html(data);
            }
        });
    }
  
</script>
</head>
<body>
	<div id="wrapper">

		<div class="ibox-content overflow-h cus-table-filters">
			<div class="scroll-y">
				<c:if test="${!empty savedPOOrder}">
					<h2 style="font-weight: bold">Your order ${savedPOOrder} is
						saved successfully.</h2>
				</c:if>
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
							<th>Shipped Date</th>
							<th>Gross Sale Qty</th>
							<th>Amount(Net PO Price)</th>
							<th>EOSS Disc Value</th>
							<th>N/R</th>
							<th>Tax(PO) Amt</th>
							<th>Gross PR</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${!empty poOrders}">
							<c:forEach items="${poOrders}" var="poOrder" varStatus="loop">
								<tr>
									<td><input type="checkbox"></td>
									<td>${loop.index+1}</td>
									<td><a href="#"
										onclick="onclickNavigateOrder('viewPOOrder',${poOrder.orderId})">${poOrder.subOrderID}</a></td>
									<td>${poOrder.pcName}</td>
									<td>${poOrder.productSkuCode}</td>
									<td>${poOrder.invoiceID}</td>
									<td><fmt:formatDate value="${poOrder.shippedDate}"
											pattern="MMM dd ,YY" /></td>
									<td>${poOrder.quantity}</td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${poOrder.poPrice}" /></td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${poOrder.eossValue}" /></td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${poOrder.netRate}" /></td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${poOrder.orderTax.tax}" /></td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${poOrder.pr}" /></td>
									<td>${poOrder.status}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
			<div class="col-sm-12">
				<div class="hr-line-dashed"></div>
				<a href="#" onclick="onclickNavigateOrder('uploadPO',0)"
					class="btn btn-success btn-xs">Bulk Upload PO</a>&nbsp;&nbsp; <a
					href="#" onclick="onclickNavigateOrder('downloadPO',0)"
					class="btn btn-success btn-xs">Download PO Format</a>
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
</style>
</body>
</html>
