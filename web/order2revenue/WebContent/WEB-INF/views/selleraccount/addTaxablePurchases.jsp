<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function() {
		var catvalue = '${taxablePurchases.taxPurchaseId}';

		var dd = document.getElementById('taxcat');
		dd.options[2].selected = true;

		alert(catvalue);
		if (catvalue != null && uploadValue.length != 0)
			$("#taxcat").val(catvalue);
	});
</script>
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
								<h5>Create Taxable Purchase</h5>
							</div>
							<div class="ibox-content overflow-h">
								<form:form method="GET" action="saveTaxablePurchases.html" id="addTaxablePurchasesForm" role="form"
									class="form-horizontal">

									<c:if test="${!empty taxablePurchases.taxPurchaseId}">
										<input type="hidden" name="taxPurchaseId" id="taxPurchaseId"
											value="${taxablePurchases.taxPurchaseId}" />
									</c:if>
									<div class="col-sm-12">
										<div class="col-sm-6">
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Tax Type</label>
												<div class="col-md-7" >
													<div class="input-group" style="width: 100%;">
														<c:choose>
																<c:when test="${!empty taxablePurchases.taxCategory}">
																	<form:select path="taxCategory" id="taxCategory" name="taxCategory"
																		value="${taxablePurchases.taxCategory}"
																		class="form-control" onchange="listProductCat()" >
																		<c:choose>
																			<c:when
																				test="${taxablePurchases.taxCategory eq 'LST'}">
																				<option selected>LST</option>
																				<option>CST</option>
																				<option>Excise</option>
																				<option>CustomDuty</option>
																				<option>AntiDumpityDuty</option>
																			</c:when>
																			<c:when
																				test="${taxablePurchases.taxCategory eq 'CST'}">
																				<option selected>CST</option>
																				<option>LST</option>
																				<option>Excise</option>
																				<option>CustomDuty</option>
																				<option>AntiDumpityDuty</option>
																			</c:when>
																			<c:when
																				test="${taxablePurchases.taxCategory eq 'Excise'}">
																				<option selected>Excise</option>
																				<option>LST</option>
																				<option>CST</option>
																				<option>CustomDuty</option>
																				<option>AntiDumpityDuty</option>
																			</c:when>
																			<c:when
																				test="${taxablePurchases.taxCategory eq 'CustomDuty'}">
																				<option selected>CustomDuty</option>
																				<option>LST</option>
																				<option>CST</option>
																				<option>Excise</option>
																				<option>AntiDumpityDuty</option>
																			</c:when>
																			<c:otherwise>
																				<option>LST</option>
																				<option selected>AntiDumpityDuty</option>
																				<option>CST</option>
																				<option>CustomDuty</option>
																				<option>AntiDumpityDuty</option>
																			</c:otherwise>
																		</c:choose>
																	</form:select>
																</c:when>
																<c:otherwise>
																	<form:select path="taxCategory" id="taxCategory2"
																		value="${taxablePurchases.taxCategory}"
																		class="form-control">
																		<option>LST</option>
																		<option>CST</option>
																		<option>Excise</option>
																		<option>CustomDuty</option>
																		<option>AntiDumpityDuty</option>
																	</form:select>
																</c:otherwise>
															</c:choose>	
													</div>
												</div>
											</div>											
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Date of
													Purchase</label>
												<div class="col-md-7" id="data_1">
													<div class="input-group date">
														<span class="input-group-addon"><i
															class="fa fa-calendar"></i></span>
															 <fmt:formatDate value="${taxablePurchases.purchaseDate}"
															var="endDate" type="date" pattern="MM/dd/yyyy" />
															<form:input type="text"  value="${endDate}"
															 class="form-control" path="purchaseDate" />																							
															
													</div>
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">Particulars</label>
												<div class="col-sm-7">
													<form:input path="particular"
														value="${taxablePurchases.particular}"
														class="form-control" />							
														
														
												</div>
											</div>
										</div>
										<div class="col-sm-6">

											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">tax Rate</label>
												<div class="col-sm-8">
													<form:input path="taxRate"
														value="${taxablePurchases.taxRate}" class="form-control" />
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">Basic Price</label>
												<div class="col-sm-8">
													<form:input path="basicPrice"
														value="${taxablePurchases.basicPrice}"
														class="form-control" />
												</div>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="hr-line-dashed"></div>
											<button class="btn btn-primary pull-right" type="submit"
												onclick="submittaxpurcharse()">Save</button>
										</div>
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
		function submittaxpurcharse() {
			var validator = $("#addTaxablePurchasesForm").validate({
				rules : {
					particular : {
						required : true,						
					},
					basicPrice : {
						required : true,
						min : 1,
						number : true,
					},
					taxRate : {
						required : true,
						min : 1,
						number : true,
					},					
					purchaseDate : {
						required : true,						
					}
				},
				messages : {
					expenseName : "particular Name Required",
					basicPrice : "basicPrice required",
					taxRate : "taxRate required",
					purchaseDate : "purchaseDate required"
				}
			});
			if (validator.form()) { // validation perform
				$('form#addTaxablePurchasesForm').submit();
			}
		}

		$(document).ready(function() {
			$('#data_1 .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				calendarWeeks : false,
				autoclose : true
			});
		});
	</script>
</body>
</html>