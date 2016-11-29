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

									<c:if test="${!empty partnerSellerAuthInfo.id}">
										<input type="hidden" name="taxPurchaseId" id="id"
											value="${partnerSellerAuthInfo.id}" />
									</c:if>
									<div class="col-sm-12">
										<div class="col-sm-6">
											
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">accesskey</label>
												<div class="col-sm-7">
													<form:input path="accesskey"
														value="${partnerSellerAuthInfo.accesskey}"
														class="form-control" readonly="true" />							
														
														
												</div>
											</div>
																						
											
											<div class="mar-btm-20-oh">
												<label class="col-sm-5 control-label">secretkey</label>
												<div class="col-sm-7">
													<form:input path="secretkey"
														value="${partnerSellerAuthInfo.secretkey}"
														class="form-control" />							
														
														
												</div>
											</div>
										</div>
										<div class="col-sm-6">

											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">serviceurl</label>
												<div class="col-sm-8">
													<form:input path="serviceurl"
														value="${partnerSellerAuthInfo.serviceurl}" class="form-control" />
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">mwsauthtoken</label>
												<div class="col-sm-8">
													<form:input path="serviceurl"
														value="${partnerSellerAuthInfo.mwsauthtoken}"
														class="form-control" />
												</div>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="hr-line-dashed"></div>
											<button class="btn btn-primary pull-right" type="submit"
												onclick="submittaxpurcharse()"></button>
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
					purchaseDate : "Purchase Date required"
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