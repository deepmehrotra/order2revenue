<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>

  <!-- Data Tables -->
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet">
<script>
 var globalValue="Mahesh kOLLIPAR";
 </script>
</head>
<body>
<div id="wrapper">
<jsp:include page="../sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="../globalheader.jsp"></jsp:include>  
      <div class="wrapper wrapper-content animated fadeInRight" id="centerpane"> 
<div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Taxable Purchases</h5>
                            <c:if test="${productCount > 0 }">
									<c:choose>
										<c:when test="${productCount >= listSize}">
											<label class="m-l-lg">${listSize-499} - ${listSize} of ${productCount}</label>
										</c:when>
										<c:when test="${productCount < listSize}">
											<label class="m-l-lg">${listSize-499} - ${productCount} of ${productCount}</label>
										</c:when>																			
									</c:choose>									
								</c:if>
								<c:if test="${productCount == 0 }">
									<label class="m-l-lg">0 - 0 of 0</label>
								</c:if>
                            <div class="ibox-tools">
                            <!--   <button class="btn btn-white table-menu-search search-dd">
                                <i class="fa fa-search"></i>
                           </button> -->
                                <div class="search-more-wrp">
                                    <form role="search" class="form-inline" method="post" action="searchProduct.html">
                                        <!--  <div class="form-group">
                                            <select class="form-control" name="searchProduct"  id="searchProduct">
                                                <option value="SKU">SKU</option>
                                                <option value="createdDate">Created Date</option>
                                            </select>
                                        </div>
                                        <div class="form-group ProductSearch-box" id="SKU">
                                           <input type="text" placeholder="SKU code" class="form-control" name="skuCode">
                                        </div>-->
                                        <div class="form-group ProductSearch-box" id="createdDate" style="display:none">
                                            <div class="input-group date">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" class="form-control"  placeholder="Start Date"
                                                name="startDate">
                                            </div>
                                            <div class="input-group date">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" class="form-control" placeholder="End Date"
                                                 name="endDate">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button class="btn btn-primary btn-block" type="submit">Search</button>
                                        </div>
                                    </form>
                                </div>
                                <span>Last</span>
                               <button type="button"   id="LoadFirst500"  class="btn btn-xs btn-white active">500</button>
                                <button type="button" class="btn btn-xs btn-white">1000</button>
                                <button type="button"  id="LoadMoreProduct" class="btn btn-xs btn-white">More</button>
                                <a href="addTaxablePurchases.html"  class="btn btn-primary btn-xs"  >Add Purchases</a>
                            </div>
                        </div>
                        <div class="ibox-content overflow-h cus-table-filters">
                        <div class="scroll-y">
                        	<input type="hidden" id="delStatus" value="${deleteStatus}">
                             <table class="table table-striped table-bordered table-hover dataTables-example" >
                                <thead>
                                <tr>
                                    <th>SL No.</th>                                 
                                    <th>TaxCategory</th>
                                    <th>TaxRate</th>
                                    <th>PurchaseDate</th>
                                    <th>Particular</th>
                                    <th>BasicPrice</th>
                                    <th>TaxAmount</th>
                                    <th>TotalAmount</th>                                                                      
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${!empty productList}">
                                <c:forEach items="${productList}" var="product" varStatus="loop">
                                <tr>                               
                                    <td>${loop.index+1}</td>                             
                                    <td>${product.taxCategory}</td>                                     
                                    <td><fmt:formatNumber type="number"
																maxFractionDigits="2" minFractionDigits="2" value="${product.taxRate}"/></td>									
									
																							                                   
                                    <td> <fmt:formatDate value="${product.purchaseDate}"
																	pattern="MMM dd ,YY" /></td>                                   
                                   
																	
                                     <td>${product.particular}</td> 
                                     
                                     <td><fmt:formatNumber type="number"
																maxFractionDigits="2" minFractionDigits="2"  value="${product.basicPrice}"/></td>
																                                  
                                   
                                   <td><fmt:formatNumber type="number"
																maxFractionDigits="2" minFractionDigits="2" value="${product.basicPrice/product.taxRate}"/></td>
																
                                   
                                    <td><fmt:formatNumber type="number"
																maxFractionDigits="2" minFractionDigits="2" value="${product.basicPrice/product.taxRate+product.basicPrice}"/></td>
													
													
                                  
                                    <td class="tooltip-demo">
			                           	<a href="edittaxablepurchase.html?id=${product.taxPurchaseId}"  ><i class="fa fa-edit text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Edit"></i></a>
			   							<a></a> 
			   							<a href="deletePurchaseTaxables.html?id=${product.taxPurchaseId}"  ><i class="fa fa-trash text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Delete"></i></a>			   										   							
 									</td>

                                </tr>
                                </c:forEach>
                                </c:if>
                                </tbody>
                            </table>                      
                            </div>                 
                            
                            <div class="col-sm-12">
                            <div class="hr-line-dashed"></div>
                                  <a href="#" id="upload" class="btn btn-success btn-xs" onclick="onclickTaxablePurchases('uploadGatePass',0)" >Bulk Upload Product</a>&nbsp;&nbsp; 
                                  <a href="#" id="download" class="btn btn-success btn-xs" onclick="onclickTaxablePurchases('downloadGatePass',0)">Download Product Summary</a>  
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
<script src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>

<script>
 var globalValue="Mahesh kOLLIPAR";
    $(document).ready(function(){
    	
    	var status=document.getElementById("delStatus").value;
    	if(status == 'Deleted'){
    		alert("Product Deleted Successfully !!!");
    	}else if(status != 'Deleted' && status != ""){
    		alert("Product Can't Be Deleted ! "+status);
    	}
    	
        $('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
        });
        
        $('#searchProduct').change(function () {
            $('.ProductSearch-box').hide();
            $('#'+$(this).val()).fadeIn();
        });
        $('.search-dd').on('click', function(e){
            e.stopPropagation();
            $('.search-more-wrp').slideToggle();
        });
        $('.input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
       
        $('#download').click(function(){
    		 $.ajax({
    	            url : 'downloadOrderDA.html?value=productSummary',
    	            success : function(data) {    	            	
    	            	if($(data).find('#j_username').length > 0){
    	            		window.location.href = "orderindex.html";
    	            	}else{
    	                	$('#centerpane').html(data);
    	            	}
    	            }
    	        });
    		});
        $('#upload').click(function(){
    		 $.ajax({
    	           // url : 'uploadOrderDA.html?value=productSummary',
    	        	url : 'uploadTaxablePurchasesDA.html?value=TaxablePurchases_Mapping', 
    	            success : function(data) {
    	            	if($(data).find('#j_username').length > 0){
    	            		window.location.href = "uploadTaxablePurchasesDA.html";
    	            	}else{
    	                	$('#centerpane').html(data);
    	            	}
    	            },
    	            error : function(data) {
   	            	 alert(" Failing to get the data");
   	            }
    	        });
    		});
        
        $('#LoadMoreProduct').click(function (e) {
            e.preventDefault();
             pageno=parseInt(getQueryVariable("page"));
             alert(" Page number in loadmore : "+pageno);
             pageno=pageno+1;
            window.location="Product.html?page="+pageno;
            	
        });
        $('#LoadFirst500').click(function (e) {
           window.location="Product.html?page="+0;
            	
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
      function onclickAddProduct() {
         $.ajax({
              url : 'addProduct.html',
              success : function(data) {
            	  if($(data).find('#j_username').length > 0){
              		window.location.href = "addTaxablePurchases.html";
              	}else{
                  	$('#centerpane').html(data);
              	}
              }
          });
      }
      function onclickEditProduct(id) {
    	  alert('ddfdfd');
          $.ajax({
               url : 'edittaxablepurchase.html?id='+id,
               success : function(data) {
            	   if($(data).find('#j_username').length > 0){
               		window.location.href = "orderindex.html";
               	}else{
                   	$('#centerpane').html(data);
               	}
               }
           });
       }
 
      function show(vale,valf){
    		var valued=document.getElementById('productName');
    		var valuee=document.getElementById('productSkuCode');
    		
    		valued.value=vale;
    		valuee.value=valf;
    		//editProduct.html?id=${product.productId}
    		var xxx = $('editProduct.html?id'+vale).submit();
    	}
    	function updateInventory(){
    		var sx=document.getElementById("oneone").value;
    		var xxx = $('form#addProductForm').submit();
    	 
    	}
    	
    	
    	function onclickTaxablePurchases(value,id) {
    		
    		var targeturl="";
        	switch(value)
        	{       	        	
        	case "uploadTaxablePurchases" :
        		targeturl="uploadTaxablePurchasesDA.html?value=TaxablePurchases_Mapping";
        	break;
        	case "downloadTaxablePurchases" :
        		targeturl="downloadTaxablePurchasesDA.html?value=TaxablePurchases_Mapping";
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
<style>
    body.DTTT_Print {
        background: #fff;

    }
    .DTTT_Print #page-wrapper {
        margin: 0;
        background:#fff;
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
