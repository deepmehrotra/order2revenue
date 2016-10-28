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
<script type="text/javascript">
    function onclickNavigateOrder(value,id) {
    	var targeturl="";
    	switch(value)
    	{
    	
    	case "viewOrder" :
    		targeturl="viewOrderDA.html?orderId="+id;
    	break;
    	case "viewPOOrder" :
    		targeturl="viewPOOrderDA.html?orderId="+id;
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
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Payment Upload Details</h5>
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
        
        var selectedValue = "${searchStatus}";
        if(selectedValue != ""){
        	$('#orderCriteria').val(selectedValue);
        }
        
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
             if(status != ""){
            	 window.location="orderList.html?page="+pageno+"&status="+status; 
             } else {
            	window.location="orderList.html?page="+pageno;
             }
        });               
        $('#LoadFirst500').click(function (e) {
        	
        	var status = "${searchStatus}";            
            if(status != ""){
           	 	window.location="orderList.html?page="+0+"&status="+status; 
            } else {
            	window.location="orderList.html?page="+0; 
            }
                      	
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
