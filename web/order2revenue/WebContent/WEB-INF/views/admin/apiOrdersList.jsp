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
								<form action="listAPIOrders_Admin.html" method="get">
									<div class="col-sm-3">
										<fmt:formatDate value="${startDate}" var="startDate" type="date" pattern="MM/dd/yyyy" />
										<input type="text" id="startDateList" name="startDateList" value="${startDate}" placeholder="From" class="btn btn-primary btn-xs"
											style="background: dimgray; width: 48%; float: left; margin-right: 5px;" />
										<fmt:formatDate value="${endDate}" var="endDate" type="date" pattern="MM/dd/yyyy" />
										<input type="text" id="endDateList" name="endDateList" value="${endDate}" placeholder="To" class="btn btn-primary btn-xs"
											style="background: dimgray; width: 48%; float: left;" />
									</div>
									<div class="col-sm-3" style="text-align: right;">
										<select class="btn btn-primary btn-xs" id="selectPartner" name="selectPartner" style="width: 70%; color: #676a6c !important;">
											<option value="Shopclues" id="Shopclues">Shopclues</option>
											<option value="Amazon" id="Amazon">Amazon</option>											
										</select>
										<button class="btn btn-primary btn-xs" type="submit" style="background: dimgray;">GET</button>
									</div>
								</form>		
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
													<th>SKU Code</th>
													<th>Order Received Date</th>
													<th>Selling Price</th>										
													<th>Quantity</th>													
													<th>Status</th>
													<th>O2R Status</th>													
												</tr>
											</thead>
											<tbody>
												<c:if test="${partner eq 'Shopclues'}">
													<c:forEach items="${Orders}" var="order" varStatus="loop">
														<tr>
															<td><input type="checkbox" class="checkOrder" value="${order.key}"></td>
															<td>${loop.index+1}</td>
															<td>${order.value.order_id}</td>
															<td>${order.value.product_id}</td>
															<td><fmt:formatDate value="${order.value.timestamp}"
																	pattern="MMM dd ,YY" /></td>
															<td>${order.value.selling_price}</td>															
															<td>${order.value.quantity}</td>
															<td>${order.value.status}</td>
															<td>${order.value.orderStatus}</td>														
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>										
									</div>
									<div class="col-lg-12">
										<div class="col-sm-6">
											<input type="checkbox" class="checkAll pull-left" value=""> Select All
										</div>
										<div class="col-sm-6">
											<button class="btn btn-primary pull-right btn-xs"
												type="button" id="orderShip" onclick="getIDs();"><strong>Move To Ship</strong></button>
										</div>
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
    	
    	/* $('#orderShip').attr('disabled',true); */
        $('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
        });
        
        $('.checkAll').click(function(event) {
			if (this.checked) {
				/* $('#orderShip').attr('disabled', false); */
				$('.checkOrder').each(function() {
					this.checked = true;             
				});
			} else {
				$('.checkOrder').each(function() {
					this.checked = false;                     
				});
			}
		});
        
        /* $('.checkOrder').click(function(event) {
			if (this.checked) {
				$('#orderShip').attr('disabled', false);
			} else {
				$('#orderShip').attr('disabled',true);
			}
		}); */
        
        $("#endDateList").change(
				function() {
					var startDate = document
							.getElementById("startDateList").value;
					var endDate = document
							.getElementById("endDateList").value;

					if ((Date.parse(startDate) > Date
							.parse(endDate))) {
						alert("End Date Should Be Greater Than Start Date !");
						document.getElementById("endDateList").value = "";
					}
				});
        $("#startDateList").change(
				function() {
					var startDate = document
							.getElementById("startDateList").value;
					var endDate = document
							.getElementById("endDateList").value;

					if ((Date.parse(startDate) > Date
							.parse(endDate))) {
						alert("Start Date Should Be Smaller Than End Date !");
						document.getElementById("startDateList").value = "";
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
        
        $(function() {
            $("#startDateList").datepicker({
              dateFormat: 'mm-dd-yy'
            });
            $("#endDateList").datepicker({
              dateFormat: 'dd-mm-yy'
            });
          });
        /* $('.input-group.date5').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        }); */
        
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
    
    function getIDs() {    	
    	
    	var checkedValues = $('input:checkbox.checkOrder:checked').map(function () {
            return this.value;
        }).get();    	
    	alert(checkedValues);
    	var partner = $('#selectPartner').val();
    	if(partner = 'Shopclues'){
    		//window.location = "saveShopcluesOrderAPIToO2r.html?orders="+checkedValues;
    	} else if(partner = 'Amazon'){
    		//window.location = "saveShopcluesOrderAPIToO2r.html?orders="+checkedValues;
    	}
    	
  	}
    
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
