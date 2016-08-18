<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	var nameAvailability = true;

	function checkOnBlur() {
		var name = document.getElementById("categoryName").value;

		$
				.ajax({
					url : "checkTaxCategory.html?name=" + name,
					success : function(res) {
						if (res == "false") {
							nameAvailability = false;
							$("#catNameMessageR").html(
									"Tax category already exist").show();
							$("#catNameMessageG")
									.html("Tax category available").hide();
						} else {
							nameAvailability = true;
							$("#catNameMessageG")
									.html("Tax category available").show();
							$("#catNameMessageR").html(
									"Tax category already exist").hide();
						}
					}
				});
	}
	function submitForm() {
		var validator = $("#addTaxCatForm").validate({
			rules : {
				taxCatName : {
					required : true,
				},
				taxPercent : {
					required : true,
					min : 1,
					max : 100,
					number : true,
				}
			},
			messages : {
				taxCatName : "Tax Category Name Required",
				taxPercent : "Tax percentage is required from 1% to 100%"
			}
		});
		
		categoryStr = "${categoryLSTList}";
		categoryStr = categoryStr.replace("[", "");
		categoryStr = categoryStr.replace("]", "");
		var categoryLSTList = categoryStr.split(',');
		//alert("LST : " + categoryLSTList);
		
		categoryStr = "${categoryCSTList}";
		categoryStr = categoryStr.replace("[", "");
		categoryStr = categoryStr.replace("]", "");
		var categoryCSTList = categoryStr.split(',');
		//alert("CST : " + categoryCSTList);
		
		categoryStr = "${prodCategoryList}";
		categoryStr = categoryStr.replace("[", "");
		categoryStr = categoryStr.replace("]", "");
		var catList = categoryStr.split(',');
		//alert("Cat : " + catList);
		
		//categoryStr = document.getElementById("multiSku").value;
		categoryStr = String($("#multiSku").chosen().val());
		var selectedList = categoryStr.split(',');
		//alert("selected : " + selectedList);
		if (selectedList == "null"  || selectedList.length < 0) {
			selectedList = null;
			//alert(!selectedList);
		}
		
		taxType = String($("#taxCatType2").val());
		//alert("Type : " + taxType);
		if (taxType == 'undefined') {
			taxType = String($("#taxCatType1").val());
			//alert("Type : " + taxType);
		}
		
		if (selectedList) {
			if(catList.length > 0 && catList[0] != "") { 
				
				if (taxType == "LST") {
					//alert("IN LST");
					categoryLSTList.push.apply(categoryLSTList, catList);
				} else {
					//alert("IN CST");
					categoryCSTList.push.apply(categoryCSTList, catList);
				}
			}
			//alert("LST : " + categoryLSTList);
			//alert("CST : " + categoryCSTList);
			
			
			var isProdCatEmpty = false;
			var count = 0;
			for (var i = 0; i < selectedList.length; i++) {
				var prodCat = String(selectedList[i]).trim();
			    if (taxType == "LST") {
			    	//alert("IN LST");
			    	for (var j = 0; j < categoryLSTList.length; j++) {
			    		var cat = String(categoryLSTList[j]).trim();
			    		//alert(prodCat);
			    		//alert(cat);
			    		if (prodCat == cat) {
			    			count++;
			    		}
			    		//alert(count);
			    	}
			    } else {
			    	for (var j = 0; j < categoryCSTList.length; j++) {
			    		var cat = String(categoryCSTList[j]).trim();
			    		if (prodCat == cat) {
			    			count++;
			    		}
			    	}
			    }
			}
			if (count >= selectedList.length) {
				isProdCatEmpty = true;
			}
		} else {
			isProdCatEmpty = true;
		}
		
		if (!isProdCatEmpty) {
			alert("Some of the Product Category already has the " + taxType + " Tax Category");
		}
		else {
			if (validator.form() && nameAvailability) { // validation perform
				$('form#addTaxCatForm').submit();
			}
		}
	}
</script>
<style type="text/css">
.chosen-container .chosen-results {
	max-height: 80px !important;
}

.chosen-container-multi .chosen-choices {
	width: 79% !important;
	right: 3px !important;
}

.chosen-container .chosen-drop {
	width: 79% !important;
}
</style>
<link href="/O2R/seller/css/chosen.css" rel="stylesheet">
</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Tax Category</h5>
				</div>
				<div class="ibox-content add-company">
					<form:form method="POST" action="saveTaxCategory.html"
						id="addTaxCatForm" role="form" class="form-horizontal">
						<c:if test="${!empty taxCategory.taxCatId}">
							<input type="hidden" name="taxCatId" id="taxCatId"
								value="${taxCategory.taxCatId}" />
						</c:if>
						<div class="col-sm-6">
							<div class="form-group">
								<label class="col-sm-5 control-label">Tax Category Name</label>

								<div class="col-sm-7">
									<form:input path="taxCatName" value="${taxCategory.taxCatName}"
										class="form-control" id="categoryName" onblur="checkOnBlur()" />
									<span id="catNameMessageR" style="color: red"></span> <span
										id="catNameMessageG" style="color: green"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-5 control-label">Description</label>

								<div class="col-sm-7">
									<form:input path="taxCatDescription"
										value="${taxCategory.taxCatDescription}" class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label class="col-sm-4 control-label">Tax Percent</label>

								<div class="col-sm-8">
									<form:input path="taxPercent" value="${taxCategory.taxPercent}"
										class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label class="col-sm-4 control-label">Tax Type</label>

								<div class="col-sm-8">
									<c:choose>
										<c:when test="${!empty taxCategory.taxCatType}">
											<form:select path="taxCatType" id="taxCatType1"
												value="${taxCategory.taxCatType}" class="form-control"
												onchange="listProductCat()">
												<c:choose>
													<c:when test="${taxCategory.taxCatType eq 'LST'}">
														<option selected>LST</option>
														<option>CST</option>
													</c:when>
													<c:otherwise>
														<option>LST</option>
														<option selected>CST</option>
													</c:otherwise>
												</c:choose>
											</form:select>
										</c:when>
										<c:otherwise>
											<form:select path="taxCatType" id="taxCatType2"
												value="${taxCategory.taxCatType}" class="form-control">
												<option>LST</option>
												<option>CST</option>
											</form:select>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
						<div class="col-sm-6" id="multiSelect">
							<label class="col-sm-5 control-label">Map Product
								Category</label>
							<div class="col-sm-7">
								<select data-placeholder="Click To Select" name="multiSku"
									id="multiSku" class="chosen-select" multiple="multiple"
									style="width: 350px;" tabindex="4" required>

									<c:choose>
										<c:when test="${!empty prodCategoryList}">
											<c:forEach items="${prodCategoryList}" var="category"
												varStatus="loop">
												<option value="${category}" selected>${category}</option>
											</c:forEach>
											<c:if test="${!empty categoryList}">
												<c:forEach items="${categoryList}" var="category"
													varStatus="loop">
													<option value="${category}">${category}</option>
												</c:forEach>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${!empty categoryList}">
												<c:forEach items="${categoryList}" var="category"
													varStatus="loop">
													<option value="${category}">${category}</option>
												</c:forEach>
											</c:if>
										</c:otherwise>
									</c:choose>
								</select>
							</div>
						</div>

						<div class="col-sm-12">
							<div class="hr-line-dashed"></div>
							<button class="btn btn-primary pull-right" type="button"
								onclick="submitForm()">Save</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

	<script src="/O2R/seller/js/chosen.jquery.js"></script>
	<script>
		var config = {
			'.chosen-select' : {},
			'.chosen-select-deselect' : {
				allow_single_deselect : true
			},
			'.chosen-select-no-single' : {
				disable_search_threshold : 10
			},
			'.chosen-select-no-results' : {
				no_results_text : 'Oops, nothing found!'
			},
			'.chosen-select-width' : {
				width : "95%"
			}
		}
		for ( var selector in config) {
			$(selector).chosen(config[selector]);
		};
	</script>
</body>
</html>