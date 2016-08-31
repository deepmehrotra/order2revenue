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
                            <h5>SKUs(${productCount})</h5>
                            <c:if test="${productCount != 0 }">
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
                             <button class="btn btn-white table-menu-search search-dd">
                                <i class="fa fa-search"></i>
                           </button>
                                <div class="search-more-wrp">
                                    <form role="search" class="form-inline" method="post" action="searchProduct.html">
                                        <div class="form-group">
                                            <select class="form-control" name="searchProduct"  id="searchProduct">
                                                <option value="SKU">SKU</option>
                                                <option value="createdDate">Created Date</option>
                                            </select>
                                        </div>
                                        <div class="form-group ProductSearch-box" id="SKU">
                                           <input type="text" placeholder="SKU code" class="form-control" name="skuCode">
                                        </div>
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
                                <a href="addProduct.html"  class="btn btn-primary btn-xs"  >Add SKU</a>
                            </div>
                        </div>
                        <div class="ibox-content overflow-h cus-table-filters">
                        <div class="scroll-y">
                        	<input type="hidden" id="delStatus" value="${deleteStatus}">
                             <table class="table table-striped table-bordered table-hover dataTables-example" >
                                <thead>
                                <tr>
                                    <th>SL No.</th>
                                    <th>Product Title</th>
                                    <th>SKU</th>
                                    <th>Created On</th>
                                    <th>SKU Price</th>
                                    <th>Category</th>
                                    <th>Volume(L*B*H)(in cm)</th>
                                    <th>Vol-Weight(in cm)</th>
                                    <th>Dead Weight(in cm)</th>
                                    <th>Stock Available</th>
                                    <th>Threshold Limit</th>                                    
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${!empty productList}">
                                <c:forEach items="${productList}" var="product" varStatus="loop">
                                <tr>
                                    <td>${loop.index+1}</td>
                                    <td id="oneone">${product.productName}</td>
                                     <td>${product.productSkuCode}</td>
                                    <td><fmt:formatDate value="${product.productDate}" pattern="MMM dd ,YY"/></td>
                                    <td>${product.productPrice}</td>
                                    <td>${product.categoryName}</td>
                                    <td>${product.length}*${product.breadth}*${product.height}</td>
                                    <td>${product.volWeight}</td>
                                    <td>${product.deadWeight}</td>
                                    <td>${product.quantity}</td>
                                    <td>${product.threholdLimit}</td>                                    
                                    <td class="tooltip-demo">
			                           	<a href="editProduct.html?id=${product.productId}"  ><i class="fa fa-edit text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Edit"></i></a>
			   							<a href="javascript:show('${product.productName}','${product.productSkuCode}')"  ><i class="fa fa-recycle text-navy" data-toggle="modal" data-target="#myModal" data-placement="top" data-original-title="Update"></i></a>
			   							<a href="deleteProduct.html?id=${product.productId}"  ><i class="fa fa-trash text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Delete"></i></a>			   										   							
 									</td>

                                </tr>
                                </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                            </div>
                            <div class="col-sm-12">
                            <div class="hr-line-dashed"></div>
                                  <a href="#" id="upload" class="btn btn-success btn-xs">Bulk Upload Product</a>&nbsp;&nbsp; 
                                  <a href="#" id="download" class="btn btn-success btn-xs">Download Product Summary</a>  
                                </div>
                           
                        </div>
                    </div>
                </div>
            </div>
             </div>
 <jsp:include page="../globalfooter.jsp"></jsp:include>

    </div>
</div>
<div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content animated bounceInRight">
    <form:form method="POST" action="saveUpdateInventory.html" id="addProductForm" role="form" class="form-horizontal"> 
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
        <h4 class="modal-title">Update Inventory </h4>
    </div>
    <div class="modal-body">
        <div class="row">
   

							 <c:if test="${!empty product.productId}">
                        <input  name="productId" id="productId" value="${product.productId}"/>
                         </c:if>

    
          <div class="col-sm-12">
            <div class="col-sm-6">
                <label> 
                    Product Name
                </label>
                <form:input path="productName" id="productName" value="mahesh" class="form-control"/>
            </div>
            <div class="col-sm-6">
                <label>SKU 
                </label>
                              <c:choose>
                                    	 <c:when test="${globalValue}">
                                    	 <form:input path="productSkuCode"  value="kumar" id="productSkuCode"
                                     class="form-control"/><span id="skucodeMsg" style="color:red"></span></c:when>
                                     <c:otherwise> 
                                     <form:input path="productSkuCode" value="kollipara"  id="productSkuCode"
                                     class="form-control" readonly="true"/></c:otherwise>
                                     </c:choose>
            </div>
        </div>
        <div class="col-sm-12" style="margin-top:10px;">
            <div class="col-sm-6">
                <label>Add to Inventory</label>
                <input type="text" value="${variable1}" name="quantityToAdd" class="form-control">
            </div>
            <div class="col-sm-6">
              <label>
                Substract from Inventory </label>
                <input value="${variable2 }"  name="quantityToSubtract" class="form-control">
            </div>
        </div>

    </div>
</div>
			<div class="modal-footer">
			   <button type="button" class="btn btn-white" data-dismiss="modal">Cancel</button>
			<button class="btn btn-primary" type="button" onclick="updateInventory()">Update</button>
			   
			</div>
  </form:form>
</div>
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
    	            url : 'uploadOrderDA.html?value=productSummary',
    	            success : function(data) {
    	            	if($(data).find('#j_username').length > 0){
    	            		window.location.href = "orderindex.html";
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
              		window.location.href = "orderindex.html";
              	}else{
                  	$('#centerpane').html(data);
              	}
              }
          });
      }
      function onclickEditProduct(id) {
          $.ajax({
               url : 'editProduct.html?id='+id,
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
