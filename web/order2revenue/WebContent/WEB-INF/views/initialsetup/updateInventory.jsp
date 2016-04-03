<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <jsp:include page="../globalcsslinks.jsp"></jsp:include>

 </head>
 <body>
 <div>

    <div>
       
      <div class="wrapper wrapper-content animated fadeInRight" id="centerpane"> 
  <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Update Inventory</h5>
                        </div>
                        <div class="ibox-content overflow-h">
<form:form method="POST" action="saveUpdateInventory.html" id="addProductForm" role="form" class="form-horizontal">
 							
 							 <c:if test="${!empty product.productId}">
                        <input type="hidden" name="productId" id="productId" value="${product.productId}"/>
                         </c:if>
                            <div class="col-sm-12">
                                <div class="col-sm-6">
                                 <div class="mar-btm-20-oh"><label class="col-sm-5 control-label">Product Name</label>
                                    <div class="col-sm-7"><form:input path="productName" value="${product.productName}"
                                     class="form-control"/>
                                     </div>
                                    </div>
                               
                                    <div class="mar-btm-20-oh"><label class="col-sm-5 control-label">SKU</label>
                                    <div class="col-sm-7">
                                    <c:choose>
                                    	 <c:when test="${empty product.productSkuCode}">
                                    	 <form:input path="productSkuCode"  id="addproductSkuCode"
                                     class="form-control"  onchange="checkOnBlur()"/><span id="skucodeMsg" style="color:red"></span></c:when>
                                     <c:otherwise> 
                                     <form:input path="productSkuCode" value="${product.productSkuCode}"  id="productSkuCode"
                                     class="form-control" readonly="true"/></c:otherwise>
                                     </c:choose></div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                
                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Add To Inventory </label>
                                    <div class="col-sm-8">

									<input name="quantityToAdd" value="${variable1 }"  />
  
       								 </div>
                                    </div>
                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Subtract From Inventory</label>
                                    <div class="col-sm-8">                                       
                                    <input name="quantityToSubtract" value="${variable2 }" />
                                     
                                    </div>
                                    </div>

                                 
                                  
                                </div>
                            </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                    <button class="btn btn-primary pull-right" type="button" onclick="updateInventory()">Update</button>
                                </div>
                            </form:form>
                        </div>
                </div>
            </div>
        </div>
        
        </div>
 <jsp:include page="../globalfooter.jsp"></jsp:include>

    </div>
</div>

<jsp:include page="../globaljslinks.jsp"></jsp:include>
<script>
var nameAvailability=true;

function checkOnBlur()
{
	var sku=document.getElementById("addproductSkuCode").value;
	$.ajax({
        url: "checkSKUAvailability.html?sku="+sku,
       success : function(res) {
    	 	   if(res=="false")
                	{
        	nameAvailability=false;
                	 $("#skucodeMsg").html("SKU code  already exist");
                	}
                else
                	{
                	nameAvailability=true;
                	$("#skucodeMsg").html("SKU code  available");
                	}
      }
	 });
	}
	
	
function updateInventory(){
	//alert('delete producrt goes here');
	var xxx = $('form#addProductForm').submit();
	//alert('delete producrt goes here');
	//$('form#addProductForm').submit();
	//opener.location.reload();
	//window.close();
}	
	
function submitProduct(){
 var validator = $("#addProductForm").validate({
	  rules: {
		  productName: {
		        required: true,
		            },
productSkuCode : {
		       required : true
		            },
         categoryName : {
       required : true
            },
	quantity: {
				required: true,
		        min: 1,
				number:true,
				    } ,
	productPrice: {
		required: true,
        min: 1,
		number:true,
		    }   
		  },
     messages: {
    	 productName: "Product Title Required",
    	 productSkuCode: "SKU ismandatory",
    	 categoryName: "Product Title Required",
    	 productPrice: "Product Price  is mandatory",
    	 quantity: "Quantity  is required"
     }
 });
 alert('GOINT TO UPDATE');
 if(validator.form()&&nameAvailability){ // validation perform
  $('form#addProductForm').submit();
 }
}



</script>
        <script>
    $(document).ready(function(){
        $('#data_1 .input-group.date').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
            });
    });
</script>
 </body>
</html>