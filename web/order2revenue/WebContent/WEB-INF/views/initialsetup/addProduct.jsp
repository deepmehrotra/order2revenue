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
								<form:form method="POST" action="saveProduct.html" id="addProductForm" role="form" class="form-horizontal">

									<c:if test="${!empty product.productId}">
										<input type="hidden" name="productId" id="productId"
											value="${product.productId}" />
									</c:if>
									<div class="col-sm-12">
										<div class="col-sm-6">
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">SKU Name *</label>
												<div class="col-sm-7">
													<form:input path="productName"
														value="${product.productName}" class="form-control" />
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Parent SKU *</label>
												<div class="col-sm-7">
													<c:choose>
														<c:when test="${empty product.productSkuCode}">
															<form:input path="productSkuCode" id="addproductSkuCode"
																class="form-control" onchange="checkOnBlur()" />
															<span id="skucodeMsg" style="color: red"></span>
														</c:when>
														<c:otherwise>
															<form:input path="productSkuCode"
																value="${product.productSkuCode}" id="productSkuCode"
																class="form-control" readonly="true" />
														</c:otherwise>
													</c:choose>
												</div>
											</div>
											<div  class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Price</label>
												<div class="col-sm-7">
													<form:input path="productPrice"
														value="${product.productPrice}" class="form-control" />
												</div>
											</div>
											
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Length (cm) *</label>
												<div class="col-sm-7">
													<form:input path="length"
														value="${product.length}" class="form-control" />
												</div>
											</div>
											
											<div class="mar-btm-20-oh">
											
												<label class="col-sm-5 control-label">Breadth (cm) *</label>
												<div class="col-sm-7">
													<form:input path="breadth"
														value="${product.breadth}" class="form-control" />
												</div>
											</div>
											
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Height (cm) *</label>
												<div class="col-sm-7">
													<form:input path="height"
														value="${product.height}" class="form-control" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">

											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">Product Category *</label>
												<div class="col-sm-8">
													<c:choose>
														<c:when test="${empty product.categoryName}">
															<form:select path="categoryName" items="${categoryMap}"
																class="form-control" id="categoryName">
															</form:select>
														</c:when>
														<c:otherwise>
															<form:input path="categoryName"
																value="${product.categoryName}" class="form-control"
																readonly="true" />
														</c:otherwise>
													</c:choose>
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">Quantity *</label>
												<div class="col-sm-8">
													<c:choose>
														<c:when test="${empty product.productSkuCode}">
															<form:input path="quantity" id="quantity"
																class="form-control" />
														</c:when>
														<c:otherwise>
															<form:input path="quantity" value="${product.quantity}"
																class="form-control" readonly="true" />
														</c:otherwise>
													</c:choose>
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">Threshold
													Limit</label>
												<div class="col-sm-8">
													<form:input path="threholdLimit"
														value="${product.threholdLimit}" class="form-control" />
												</div>
											</div>											
											<div>
												<label class="col-sm-4 control-label">Deadweight (gm) *</label>
												<div class="col-sm-8">
													<form:input path="deadWeight"
														value="${product.deadWeight}" class="form-control" />
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
					}
				},
				messages : {
					productName : "Product Title Required",
					productSkuCode : "SKU ismandatory",
					categoryName : "Product Title Required",					
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