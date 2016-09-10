<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>

<!-- Data Tables CSS -->
<link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
<link href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet">
<link href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet">

<style type="text/css">

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
</head>
<body>

	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>

				<div class="wrapper wrapper-content animated">
            <div class="row">
                <div class="col-lg-12">
                    <div class="col-sm-6" style="padding: 0px;">
                    	<h2><b>CUSTOMER DATABASE</b></h2>
					</div>
					<div class="col-sm-6" style="text-align: right;margin-top: 20px;padding: 0px;">
						<span>Last</span>
						<button type="button" id="LoadFirst500"
							class="btn btn-xs btn-white active">500</button>
						<button type="button" class="btn btn-xs btn-white">1000</button>
						<button type="button" id="LoadMoreOrder"
							class="btn btn-xs btn-white">More</button>
					</div>
				</div>
                 
                <div class="col-lg-12">
                    <table class="table table-striped table-bordered table-hover dataTables-example">
                        <thead>
                            <tr>
                                <th>
                                    Rank
                                </th>
                                <th>
                                    Customer Name
                                </th>
                                <th>
                                    Products Purchased
                                </th>
                                <th>                                    
                                    Product Returned
                                </th>
                                <th>
                                    Net Products Purchased
                                </th>
                                <th>
                                    Net Revenue Earned
                                </th>
                                <th>
                                    State 
                                </th>                                
                                <th>
                                    Contact 
                                </th>
                                <th>
                                    Blacklist Customer
                                </th>
                            </tr>
                        </thead>
                        
                        <tbody>
                        <c:if test="${!empty customers}">
                        	<c:forEach items="${customers}" var="customer" varStatus="loop">
                        		<c:if test="${customer.customerPhnNo != null}">
		                            <tr>
		                                <td>${loop.index+1}</td>		                                
		                                <td style="color: blue;">
		                                <form action="viewCustomerOrders.html" method="POST"> 
		                                	<input type="hidden" name="email" value="${customer.customerEmail}">
		                                	<input type="hidden" name="name" value="${customer.customerName}">
		                                	<input type="hidden" name="purchased" value="${customer.productPurchased}">
		                                	<input type="hidden" name="returned" value="${customer.productReturned}">
		                                	<input type="hidden" name="netPurchased" value="${customer.productPurchased-customer.productReturned}">
		                                	<input type="hidden" name="revenue" value="${customer.netRevenue}">		                                	                       
		                                	<button type="submit" name="your_name" value="your_value" class="btn-link"><u>${customer.customerName}</u></button>
		                                </form></td>
		                                <td>${customer.productPurchased}</td>
		                                <td>${customer.productReturned}</td>
		                                <td>${customer.productPurchased-customer.productReturned}</td>
		                                <td><fmt:formatNumber type="number"	maxFractionDigits="2"
												value="${customer.netRevenue}"/></td>
		                                <td>${customer.customerCity}</td>                                
		                                <td>${customer.customerEmail}<br>
		                                    ${customer.customerPhnNo}
		                                </td>
		                                <td><a href="#"><img src="/O2R/seller/img/edit.png" alt="edit"></a></td>		
		                            </tr>
		                        </c:if>		                            		                            
		                     </c:forEach>       
                        </c:if>
                        </tbody>                        
                    </table>

                </div>
            </div>
            </div>
            
            <jsp:include page="../globalfooter.jsp"></jsp:include>
			<jsp:include page="../globaljslinks.jsp"></jsp:include>
		</div>
	</div>

<!-- Data Tables -->
	<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script	src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script	src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
	
<script type="text/javascript">
	$(document).ready(function(){
	        $('.dataTables-example').dataTable({
	                responsive: true,
	                "dom": 'T<"clear">lfrtip',
	                "tableTools": {
	                    "sSwfPath": "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
	                }
	        });
	        $('#LoadMoreOrder').click(function (e) {
	            e.preventDefault();
	             pageno=parseInt(getQueryVariable("page"));
	             pageno=pageno+1;
	            window.location="customerList.html?page="+pageno;
	            	
	        });
	        $('#LoadFirst500').click(function (e) {
	           window.location="customerList.html?page="+0;
	            	
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
</body>
</html>