<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inventory Group</title>

<script type="text/javascript">
var nameAvailability=true;
$(document).ready(function() {
	
	var inventorygroup='${category.id}';
	if(inventorygroup!=null&&inventorygroup.length!=0)
		$("#inventorygroupdwopdown").val(inventorygroup);
	
	 $('#inventorygroupdwopdown').change(function () {
         var val=$(this).val();
         $.ajax({
             url : 'changeInventorygroup.html?catId='+val,
             success : function(data) {
                 $('#centerpane').html(data);
             }
         });
     });
	
});


function checkOnBlur()
{
	var name=document.getElementById("categoryName").value;
	
	$.ajax({
        url: "checkInventoryGroup.html?name="+name,
       success : function(res) {
        	   if(res=="false")
                	{
                	nameAvailability=false;
                	 $("#catNameMessage").html("Product Category  already exist");
                	}
                else
                	{
                	nameAvailability=true;
                	$("#catNameMessage").html("Product Category  name available");
                	}
      	   }
	 });
	}
    function onclickDeleteCategory(parentId,catId) {
    	alert("calling delete cat : "+parentId+" category : "+catId );
        $.ajax({
            url : 'deleteProductCategory.html?id='+catId+'&parentId='+parentId,
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
  
    function submitCategory(){
    		 var validator = $("#addCategoryForm").validate({
 	    		  rules: {
 	    			  catName: {
 	    			        required: true,
 	    			            }
 	    			  },
 	    	     messages: {
 	    	    	catName: "Product Category  name required"
 	    	     }
 	    	 });
 	   	 if(validator.form()&&nameAvailability){
 	   		   $.ajax({
               url: $("#addCategoryForm").attr("action"),
               context: document.body,
               type: 'post',
               data:$("#addCategoryForm").serialize(),
               success : function(res) {
                             
                   $("#centerpane").html(res);
              
           }
        });
 	   	 }
 
}
 
</script>
</head>
<body>
<div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Inventory Group</h5>
                        </div>
                        <div class="ibox-content add-company">
                         <form role="form" class="form-horizontal">
                                <div class="col-sm-12" >
                                    <div class="form-group"><label class="col-sm-5 control-label">Inventory Group</label>

                                    <div class="col-sm-4">
                                            <select class="form-control" name="account" id="inventorygroupdwopdown">
                                             <c:forEach items="${categorymap}" var="cat">
										   
										         <option value="${cat.key}">${cat.value}</option>
										    </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                <br><div class="hr-line-dashed"></div>
                                </div>
                                 </form>
                                <div class="col-sm-12">
                                 ${error}
                                    <table class="table table-bordered custom-table">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Product Category Name</th>
                                            <th>Created on</th>
		                                    <th>Total SKU count</th>
		                                     <th>Current Stock</th>
		                                      <th>Monthly Opening Stock</th>
		                                       <th>Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                         <c:forEach items="${subcategory}" var="subcategory" varStatus="loop">
                                        <tr>
                                            <td>${loop.index+1}</td>
                                            <td>${subcategory.catName}</td>
                                            <td>${subcategory.createdOn}</td>
                                             <td>${subcategory.skuCount}</td>
		                                      <td>${subcategory.productCount}</td>
		                                       <td>${subcategory.openingStock}</td>
                                            <td class="tooltip-demo">
                                            <a href="#"  onclick="onclickDeleteCategory(${category.id},${subcategory.id})"><i class="fa fa-edit text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Delete"></i></a></td>
                                        </tr>
                                       </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="hr-line-dashed"></div>
                                </div>
					<form:form method="POST" action="saveCatInventory.html"	id="addCategoryForm" role="form" class="form-horizontal">
						
						<input type="hidden" name="parentid" id="parentid"
							value="${category.id}" />
						<input type="hidden" name="parentCatName" id="parentCatName"
							value="${category.catName}" />
						<div class="col-sm-12">
							<div class="col-md-5">
								<form:input path="catName" placeholder="Product Category"
									class="form-control" id="categoryName" onblur="checkOnBlur()" style="margin-bottom:5px;" />
								<span id="catNameMessage" style="color: red; font-weight: bold"></span>
							</div>
							<div class="col-md-5">
								<form:input path="catDescription" placeholder="Alias Name"
									class="form-control" style="margin-bottom:5px;" />
							</div>
						</div>
						<%-- <div class="col-sm-12">
							<div class="col-md-5">
								<form:input path="product.productConfig.productSKuCode" placeholder="Product SKU Code"
									class="form-control" id="categoryName" onblur="checkOnBlur()" style="margin-bottom:5px;"/>
								<span id="catNameMessage" style="color: red; font-weight: bold"></span>
							</div>
							<div class="col-md-5">
								<form:input path="product.productConfig.channelSKuRef" placeholder="Channel SKU Reference"
									class="form-control" style="margin-bottom:5px;"/>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="col-md-5">
								<form:input path="product.productConfig.commision" placeholder="Commision"
									class="form-control" id="categoryName" onblur="checkOnBlur()" style="margin-bottom:5px;" />
								<span id="catNameMessage" style="color: red; font-weight: bold;"></span>
							</div>
							<div class="col-md-5">
								<form:input path="productConfig.taxSp" placeholder="Tax(SP)"
									class="form-control" style="margin-bottom:5px;" />
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="col-md-5">
								<form:input path="productConfig.taxPo" placeholder="Tax(PO)"
									class="form-control" id="categoryName" onblur="checkOnBlur()" style="margin-bottom:5px;" />
								<span id="catNameMessage" style="color: red; font-weight: bold"></span>
							</div>
							<div class="col-md-5">
								<form:input path="productConfig.discount" placeholder="Discount"
									class="form-control" style="margin-bottom:5px;" />
							</div>
						</div>
							
						<div class="col-sm-12">
							<div class="col-md-5">
								<form:input path="productConfig.mrp" placeholder="MRP"
									class="form-control" id="categoryName" onblur="checkOnBlur()" style="margin-bottom:5px;" />
								<span id="catNameMessage" style="color: red; font-weight: bold"></span>
							</div>
							<div class="col-md-5">
								<form:input path="productConfig.sp" placeholder="Selling Price"
									class="form-control" style="margin-bottom:5px;" />
							</div>
						</div>
						<div class="col-sm-12">
							<div class="col-md-5">
								<form:input path="productConfig.eossDiscount" placeholder="EOSS Discount"
									class="form-control" id="categoryName" onblur="checkOnBlur()" style="margin-bottom:5px;" />
								<span id="catNameMessage" style="color: red; font-weight: bold"></span>
							</div>
							<div class="col-md-5">
								<form:input path="productConfig.productPrice" placeholder="Product Price"
									class="form-control" style="margin-bottom:5px;" />
							</div>
						</div>
						<div class="col-sm-12">
							<div class="col-md-5">
								<form:input path="productConfig.suggestedPOPrice" placeholder="Suggested PO Price"
									class="form-control" id="categoryName" onblur="checkOnBlur()" style="margin-bottom:5px;" />
								<span id="catNameMessage" style="color: red; font-weight: bold"></span>
							</div>
							<div class="col-md-5">
								<form:input path="productConfig.eossDiscountValue" placeholder="EOSS Discount Value"
									class="form-control" style="margin-bottom:5px;" />
							</div>
						</div>
						<div class="col-sm-12">
							<div class="col-md-5">
								<form:input path="productConfig.grossNR" placeholder="Gross NR"
									class="form-control" id="categoryName" onblur="checkOnBlur()" style="margin-bottom:5px;" />
								<span id="catNameMessage" style="color: red; font-weight: bold"></span>
							</div>
						</div> --%>
						<div class="col-sm-12">
							<div class="hr-line-dashed"></div>
							<button class="btn btn-primary pull-right" type="button"
								onclick="submitCategory()">Add Product Category</button>
						</div>
					</form:form>

				</div>
                    </div>
                </div>
            </div>
            <script>
    $(document).ready(function(){
        $('.panel').each(function() {
            animationHover(this, 'flipInY');
        });
    });
</script>
</html>