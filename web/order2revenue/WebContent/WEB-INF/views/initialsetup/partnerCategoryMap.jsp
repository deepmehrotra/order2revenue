<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script src="/O2R/seller/js/jquery-2.1.1.js"></script>

<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>

<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
<script
	src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
<!-- Data Tables -->
<link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css"
	rel="stylesheet">
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
								<h5>Channel Category Mappings(${partnerCategoryMap.size()})</h5>
								<div class="ibox-tools">
									<button class="btn btn-white table-menu-search search-dd">
										<i class="fa fa-search"></i>
									</button>
									<div class="search-more-wrp">
										<form role="search" class="form-inline" method="post"
											action="searchPartnerCategoryMap.html">
											<div class="form-group">
												<select class="form-control" name="searchPartnerCat"
													id="searchPartnerCat" required>
													<option value="partnerName">Channel Name</option>
													<option value="partnerCategoryRef">Channel Category
														Ref.</option>
												</select>
											</div>
											<div class="form-group PartnerCatSearch-box" id="PartnerCat">
												<input type="text" class="form-control" name="value"
													style="width: 100%" required>
											</div>
											<div class="form-group">
												<button class="btn btn-primary btn-block" type="submit">Search</button>
											</div>
										</form>
									</div>
									<span>Last</span>
									<button type="button" id="LoadFirst500"
										class="btn btn-xs btn-white active">500</button>
									<button type="button" class="btn btn-xs btn-white">1000</button>
									<button type="button" id="LoadMoreProduct"
										class="btn btn-xs btn-white">More</button>
									<!-- <a href="addPartnerCategoryMap.html"
										class="btn btn-primary btn-xs">Add Channel Category
										Mapping</a> -->
								</div>
							</div>
							<div class="bs-example">
								<div class="ibox-content cus-table-filters scroll-y">
									<div class="col-lg-12">
										<table
											class="table table-striped table-bordered table-hover dataTables-example">
											<thead>
												<tr>
													<th>SL No.</th>
													<th>Channel Name</th>
													<th>Channel Category Ref</th>
													<th>Commission</th>
													<th>Action</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty partnerCategoryMap}">
													<c:forEach items="${partnerCategoryMap}"
														var="partnerCatObj" varStatus="loop">
														<tr>
															<td>${loop.index+1}</td>
															<td>${partnerCatObj.partnerName}</td>
															<td>${partnerCatObj.partnerCategoryRef}</td>
															<td><fmt:formatNumber type="number"
																	maxFractionDigits="2"
																	value="${partnerCatObj.commission}" /></td>
															<td class="tooltip-demo"><a
																onclick="editCategory(
																'${partnerCatObj.id}', '${partnerCatObj.partnerName}',
																'${partnerCatObj.partnerCategoryRef}',
																'${partnerCatObj.commission}')"
																href="#"><i class="fa fa-edit text-navy"
																	data-toggle="tooltip" data-placement="top"
																	data-original-title="Edit"></i></a></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
									<div class="col-lg-12">
										<div class="hr-line-dashed"></div>
										<h4>Add/Edit Channel Category Mapping:</h4>
										<br> <br> <label id="addParterCategory-error"
											style="visible: none;"> </label>
										<form:form method="POST" action="saveParterCategory.html"
											id="addParterCategoryForm" role="form"
											class="form-horizontal">
											<div class="col-sm-3">
												<div class="form-group">
													<form:input type="hidden" name="catId" id="catId" path="id"
														value="${partnerCat.id}" />
													<label class="col-sm-6 control-label">Channel Name</label>
													<div class="col-sm-6">
														<form:select id="partnerSelect" class="form-control"
															path="partnerName">
															<c:if test="${!empty partnerList}">
																<c:forEach items="${partnerList}" var="partner"
																	varStatus="loop">
																	<c:choose>
																		<c:when test="${partner == partnerCat.partnerName}">
																			<option value="${partner}" selected>${partner}</option>
																		</c:when>
																		<c:otherwise>
																			<option value="${partner}">${partner}</option>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
															</c:if>
														</form:select>
													</div>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="form-group">
													<label class="col-sm-6 control-label">Channel
														Category Ref</label>

													<div class="col-sm-6">
														<form:input path="partnerCategoryRef"
															value="${partnerCat.partnerCategoryRef}"
															class="form-control" id="partnerCategoryRef" />
														<span id="catNameMessageR" style="color: red"></span> <span
															id="catNameMessageG" style="color: green"></span>
													</div>
												</div>
											</div>
											<div class="col-sm-3">
												<div class="form-group">
													<label class="col-sm-6 control-label">Commission</label>

													<div class="col-sm-6">
														<form:input path="commission" id="commission"
															value="${partnerCat.commission}" class="form-control" />
													</div>
												</div>
											</div>
											<div class="col-sm-12">
												<div class="hr-line-dashed"></div>
												<div class="pull-right">
													<button class="btn btn-primary" type="button"
														onclick="clearFields()">Clear</button>
													&nbsp;&nbsp;
													<button class="btn btn-primary" type="button"
														onclick="validateCat()">Save</button>
												</div>
											</div>
										</form:form>
									</div>
									<div class="col-sm-12">
										<div class="hr-line-dashed"></div>
										<a href="#" id="upload1" class="btn btn-success btn-xs">Bulk
											Upload Channel Category Mapping</a>&nbsp;&nbsp; <a href="#"
											id="download1" class="btn btn-success btn-xs">Download
											Channel Category Mapping Summary</a>
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
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							$('.dataTables-example')
									.dataTable(
											{
												responsive : true,
												"dom" : 'T<"clear">lfrtip',
												"tableTools" : {
													"sSwfPath" : "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
												}
											});

							$('#searchParterCat').change(function() {
								$('#ParternCat'.fadeIn());
							});
							$('.search-dd').on('click', function(e) {
								e.stopPropagation();
								$('.search-more-wrp').slideToggle();
							});
							$('.input-group.date').datepicker({
								todayBtn : "linked",
								keyboardNavigation : false,
								forceParse : false,
								calendarWeeks : true,
								autoclose : true
							});

							$('#LoadMoreProduct')
									.click(
											function(e) {
												e.preventDefault();
												pageno = parseInt(getQueryVariable("page"));
												pageno = pageno + 1;
												window.location = "partnerCategoryMap.html?page="
														+ pageno;

											});
							$('#LoadFirst500')
									.click(
											function(e) {
												window.location = "partnerCategoryMap.html?page=" + 0;

											});
						});

		function getQueryVariable(variable) {
			var query = window.location.search.substring(1);
			var vars = query.split("&");
			for (var i = 0; i < vars.length; i++) {
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
					if ($(data).find('#j_username').length > 0) {
						window.location.href = "orderindex.html";
					} else {
						$('#centerpane').html(data);
					}
				}
			});
		}

		function onclickEditProduct(id) {
			$.ajax({
				url : 'editProduct.html?id=' + id,
				success : function(data) {
					if ($(data).find('#j_username').length > 0) {
						window.location.href = "orderindex.html";
					} else {
						$('#centerpane').html(data);
					}
				}
			});
		}

		function show(vale, valf) {
			var valued = document.getElementById('productName');
			var valuee = document.getElementById('productSkuCode');

			valued.value = vale;
			valuee.value = valf;
			//editProduct.html?id=${product.productId}
			var xxx = $('editProduct.html?id' + vale).submit();
		}

		function editCategory(id, partnerName, catName, commission) {
			var elId = document.getElementById('catId');
			elId.value = id;
			var elPartnerName = document.getElementById('partnerSelect');
			elPartnerName.value = partnerName;
			elPartnerName.disabled = true;
			var elCat = document.getElementById('partnerCategoryRef');
			elCat.value = catName;
			elCat.disabled = true;
			var elComm = document.getElementById('commission');
			elComm.value = commission;
			elComm.focus();
		}

		function clearFields() {
			var elId = document.getElementById('catId');
			elId.value = 0;
			var elPartnerName = document.getElementById('partnerSelect');
			//elPartnerName.value = "";
			$("#partnerSelect").val($("#partnerSelect option:first").val());
			elPartnerName.disabled = false;
			var elCat = document.getElementById('partnerCategoryRef');
			elCat.value = "";
			elCat.disabled = false;
			var elComm = document.getElementById('commission');
			elComm.value = 0.0;
		}

		function validateCat() {
			var elCat = document.getElementById('partnerCategoryRef');
			alert(elCat.value);
			if (elCat.value == null || elCat.value == '') {
				$('#addParterCategory-error').show();
				document.getElementById("addParterCategory-error").innerHTML = "Please enter Partner Category Reference";

				$('#addParterCategory-error').attr('style', 'color:#8a1f11;');
			} else {
				$('form#addParterCategoryForm').submit();
			}
		}

		$('#upload1').click(function() {
			$.ajax({
				url : 'uploadOrderDA.html?value=savePartnerCatCommMapping',
				success : function(data) {
					if ($(data).find('#j_username').length > 0) {
						window.location.href = "orderindex.html";
					} else {
						$('#centerpane').html(data);
					}
				}
			});
		});

		$('#download1').click(function() {
			$.ajax({
				url : 'downloadOrderDA.html?value=Partner_Cat_Comm_Mapping',
				success : function(data) {
					if ($(data).find('#j_username').length > 0) {
						window.location.href = "orderindex.html";
					} else {
						$('#centerpane').html(data);
					}
				}
			});
		});

		$('#LoadFirst500').click(function(e) {
			window.location = "partnerCategoryMap.html?page=" + 0;

		});
	</script>
</body>
</html>