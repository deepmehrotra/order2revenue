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
					url : "checkCategory.html?name=" + name,
					success : function(res) {
						if (res == "false") {
							nameAvailability = false;
							$("#catNameMessageR")
									.html("Category already exist").show();
							$("#catNameMessageG").html("Category available")
									.hide();
						} else {
							nameAvailability = true;
							$("#catNameMessageG").html("Category available")
									.show();
							$("#catNameMessageR")
									.html("Category already exist").hide();
						}
					}
				});
	}
	function submitForm() {
		var validator = $("#addCategoryForm").validate({
			rules : {
				catName : {
					required : true,
				}
			},
			messages : {
				taxCatName : "Category Name Required"
			}
		});

		if (validator.form() && nameAvailability) { // validation perform
			$('form#addCategoryForm').submit();
		}

	}

	function submitCategory() {
		var validator = $("#addCategoryForm").validate({
			rules : {
				catName : {
					required : true,
				}
			},
			messages : {
				catName : "Product Category  name required"
			}
		});
		if (validator.form() && nameAvailability) {
			$.ajax({
				url : $("#addCategoryForm").attr("action"),
				context : document.body,
				type : 'post',
				data : $("#addCategoryForm").serialize(),
				success : function(data) {
					if ($(data).find('#j_username').length > 0) {
						window.location.href = "orderindex.html";
					} else {
						$('#centerpane').html(data);
					}
				}
			});
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
					<h5>Product Category</h5>
				</div>
				<div class="ibox-content add-company">
					<form:form method="POST" action="saveCatInventory.html"
						id="addCategoryForm" role="form" class="form-horizontal">
						<c:if test="${!empty category.id}">
							<input type="hidden" name="catId" id="catId"
								value="${category.id}" />
						</c:if>
						<div class="col-lg-12">
							<div class="col-sm-6">
								<div class="form-group">
									<label class="col-sm-6 control-label">Category Name</label>

									<div class="col-sm-6">
										<form:input path="catName" value="${category.catName}"
											class="form-control" id="categoryName" onblur="checkOnBlur()" />
										<span id="catNameMessageR" style="color: red"></span> <span
											id="catNameMessageG" style="color: green"></span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-6 control-label">Alias Name</label>

									<div class="col-sm-6">
										<form:input path="catDescription"
											value="${category.catDescription}" class="form-control" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label class="col-sm-4 control-label">Parent Category</label> <input
										type="hidden" name="parentcatid" id="parentcatid"
										value="${parentcatid}" />
									<div class="col-sm-8">
										<form:select id="categorySelect" class="form-control"
											path="parentCatName">
											<c:if test="${!empty categories}">
												<c:forEach items="${categories}" var="category"
													varStatus="loop">
													<c:choose>
														<c:when test="${category.id == parentcatid}">
															<option value="${category.catName}" selected>${category.catName}</option>
														</c:when>
														<c:otherwise>
															<option value="${category.catName}">${category.catName}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:if>
										</form:select>
									</div>
								</div>
							</div>
						</div>
						<c:if test="${!empty partnerCatRefList}">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label">Mapped Channel
									Category</label>
								<div class="col-sm-7">
									<select data-placeholder="Click To Select" name="multiSku"
										id="multiSku" class="chosen-select" multiple="multiple"
										style="width: 750px;" tabindex="4" required>
										<c:forEach items="${partnerCatRefList}" var="category"
											varStatus="loop">
											<option value="${category}" selected>${category}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</c:if>
						<div class="col-sm-12">
							<div class="hr-line-dashed"></div>
							<h4>Add Channel Mapping</h4>
							<br><br>
						</div>
						<div class="col-sm-12">
							<c:if test="${!empty partnerCategories}">
								<label class="col-sm-3 control-label">Predefined Channel
									Category</label>
								<div class="col-sm-7">
									<select data-placeholder="Click To Select" name="multiSku1"
										id="multiSku1" class="chosen-select" multiple="multiple"
										style="width: 750px;" tabindex="4" required>
										<c:forEach items="${partnerCategories}" var="category"
											varStatus="loop">
											<option value="${category}">${category}</option>
										</c:forEach>
									</select>
								</div>
							</c:if>
						</div>
						<div id="room_fileds">
							<div id="content">
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="form-group">
											<br><br>
											<label class="col-sm-4 control-label">Partner</label>

											<div class="col-sm-8">
												<select id="partnerSelect" class="form-control"
													name="partnerName-0">
													<c:if test="${!empty partnerList}">
														<c:forEach items="${partnerList}" var="partner"
															varStatus="loop">
															<option value="${partner}">${partner}</option>
														</c:forEach>
													</c:if>
												</select>
											</div>
										</div>
									</div>

									<div class="col-sm-8">
										<div class="form-group">
											<br><br>
											<label class="col-sm-5 control-label">Channel
												Category Ref</label>

											<div class="col-sm-7">
												<input name="partnerCatRef-0" value="" class="form-control" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<button class="btn btn-primary pull-right" type="button"
								onclick="addField()">Add More</button>
						</div>
						<div class="col-sm-12">
							<div class="hr-line-dashed"></div>
							<button class="btn btn-primary pull-right" type="button"
								onclick="submitCategory()">Save</button>
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
	<script type="text/javascript">
		$('#select_all').click(
				function() {
					var list = "${categoryList}";
					/*alert(list);
					$('#multiSku').val(list); */
					$.each(list.split(","), function(i, e) {
						alert(e);
						$("#multiSku option[value='" + e + "']").attr(
								"selected", true);
					});
				});
	</script>
	<script type="text/javascript">
		var partnerStr = "${partnerList}";
		partnerStr = partnerStr.replace("[", "");
		partnerStr = partnerStr.replace("]", "");
		var partnerList = partnerStr.split(',');

		//alert(partnerList);

		var v = 1;
		var index = 0;
		function addField() {
			v++;
			index = index + 1;
			var objTo = document.getElementById('room_fileds');
			var divtest = document.createElement("div");
			var output = "<div class='col-sm-12'>"
					+ "<div class='col-sm-4'>"
					+ "<div class='form-group'>"
					+ "<label class='col-sm-4 control-label'>Partner</label>"
					+ "<div class='col-sm-8'>"
					+ "<select id='partnerSelect" + index + "' class='form-control' name='partnerName-" + index + "'>"

			for (var i = 0; i < partnerList.length; i++) {
				output += "<option value='" + partnerList[i] + "'>"
						+ partnerList[i] + "</option>"
			}

			output += "</select>"
					+ "</div>"
					+ "</div>"
					+ "</div>"
					+ "<div class='col-sm-8'>"
					+ "<div class='form-group'>"
					+ "<label class='col-sm-5 control-label'>Channel Category Ref</label>"
					+ "<div class='col-sm-7'>"
					+ "<input id='partnerCatRef" + index + "' name='partnerCatRef-" + index + "' class='form-control' type='text' value=''>"
					+ "</div>" + "</div>" + "</div>" + "</div>"

			divtest.innerHTML = output;
			objTo.appendChild(divtest);
		}
	</script>

</body>
</html>
