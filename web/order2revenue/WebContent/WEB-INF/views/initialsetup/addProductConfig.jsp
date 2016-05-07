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
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight"
				id="centerpane">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>Create Product</h5>
							</div>
							<div class="ibox-content overflow-h">
								<form:form method="POST" action="saveProductConfig.html" id="addProductForm" role="form" class="form-horizontal">
																		
									<div class="col-sm-12">
										<div class="hr-line-dashed"></div>
										<div class="col-sm-6">
										
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Product SKU Code</label>
												<div class="col-sm-7">
													<form:select path="productSKuCode" items="${productSKuMap}"
																class="form-control" id="productName">
											</form:select>
												</div>
											</div>										

											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Product SKU Reference</label>
												<div class="col-sm-7">
													<form:input path="channelSKuRef"
														value="${productConfigBean.channelSKuRef}" class="form-control" />
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Commision</label>
												<div class="col-sm-7">
													<form:input path="commision"
														value="${productConfigBean.commision}" class="form-control" />
												</div>
											</div>

											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Tax(SP)</label>
												<div class="col-sm-7">
													<form:input path="taxSp"
														value="${productConfigBean.taxSp}" class="form-control" />
												</div>
											</div>
											
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Tax(PO)</label>
												<div class="col-sm-7">
													<form:input path="taxPo"
														value="${productConfigBean.taxPo}" class="form-control" />
												</div>
											</div>																				
										</div>
										<div class="col-sm-6">

											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">EOSS Discount</label>
												<div class="col-sm-7">
													<form:input path="eossDiscount"
														value="${productConfigBean.eossDiscount}" class="form-control" />
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Discount</label>
												<div class="col-sm-7">
													<form:input path="discount"
														value="${productConfigBean.discount}" class="form-control" />
												</div>
											</div>	
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">MRP</label>
												<div class="col-sm-7">
													<form:input path="mrp"
														value="${productConfigBean.mrp}" class="form-control" />
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Selling Price</label>
												<div class="col-sm-7">
													<form:input path="sp"
														value="${productConfigBean.sp}" class="form-control" />
												</div>
											</div>

											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Product Price</label>
												<div class="col-sm-7">
													<form:input path="productPrice"
														value="${productConfigBean.productPrice}" class="form-control" />
												</div>
											</div>
										</div>								
									</div>
									
									<div class="col-sm-12">
										<div class="hr-line-dashed"></div>
										<button class="btn btn-primary pull-right" type="button"
											onclick="submitProduct()">Save</button>
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
		var nameAvailability = true;

		function checkOnBlur() {
			var sku = document.getElementById("addproductSkuCode").value;
			$.ajax({
				url : "checkSKUAvailability.html?sku=" + sku,
				success : function(res) {
					if (res == "false") {
						nameAvailability = false;
						$("#skucodeMsg").html("SKU code  already exist");
					} else {
						nameAvailability = true;
						$("#skucodeMsg").html("SKU code  available");
					}
				}
			});
		}
		function submitProduct() {
			var validator = $("#addProductForm").validate({
				rules : {
					productName : {
						required : true,
					},
					productSkuCode : {
						required : true
					},
					categoryName : {
						required : true
					},
					quantity : {
						required : true,
						min : 1,
						number : true,
					},
					productPrice : {
						required : true,
						min : 1,
						number : true,
					}
				},
				messages : {
					productName : "Product Title Required",
					productSkuCode : "SKU ismandatory",
					categoryName : "Product Title Required",
					productPrice : "Product Price  is mandatory",
					quantity : "Quantity  is required"
				}
			});
			if (validator.form() && nameAvailability) { // validation perform
				$('form#addProductForm').submit();
			}
		}
	</script>
	<script>
		$(document).ready(function() {
			$('#data_1 .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				calendarWeeks : true,
				autoclose : true
			});
		});
	</script>
</body>
</html>