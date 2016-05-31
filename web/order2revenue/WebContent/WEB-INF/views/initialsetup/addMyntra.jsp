<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
<style type="text/css">
span .#error {
	color: red;
	font-weight: bold;
}

.pickListButtons {
	padding: 10px;
	text-align: center;
}

.pickListSelect {
	height: 200px !important;
}

.pickList1Select {
	height: 200px !important;
}
</style>
<link href="/O2R/seller/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="/O2R/seller/css/plugins/switchery/switchery.css"
	rel="stylesheet">
<link href="/O2R/seller/css/plugins/iCheck/custom.css" rel="stylesheet">
<script type="text/javascript">
	
</script>
</head>

<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight"
				id="centerpane">
				<div class="ibox-title">
					<img src="/O2R/partnerimages/Myntra.jpg" alt="logo">
				</div>
				<div class="ibox-content add-company">
					<form:form method="POST" action="saveMyntra.html"
						id="addpartnerform" name="addpartnerform" role="form"
						class="form-horizontal" enctype="multipart/form-data">
						<div class="col-sm-4">
							<div class="radio">
								<label> <form:radiobutton path="paymentType"
										id="paymentcycle" value="paymentcycle" name="toggler"
										onChange="handleRadioEvent(this);" />Payment Cycle
								</label>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="radio">
								<label> <form:radiobutton path="paymentType"
										value="datewisepay" id="datewisepay" name="toggler"
										onChange="handleRadioEvent(this);" />Payment From Delivery
								</label>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="radio">
								<label> <form:radiobutton path="paymentType"
										value="monthly" id="monthly" name="toggler"
										onChange="handleRadioEvent(this);" /> Monthly
								</label>
							</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-paymentcycle">
							<div class="col-sm-6">
								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">Start Date</label>
									<div class="col-sm-8">
										<form:input path="startcycleday"
											value="${partner.startcycleday}"
											placeholder="Duration of Payment from Start Date"
											class="form-control" />
									</div>
								</div>
								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">End Date</label>
									<div class="col-sm-8">
										<form:input path="paycycleduration"
											value="${partner.paycycleduration}"
											placeholder="Duration of Payment from Start Date"
											class="form-control" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">Payment Date</label>
									<div class="col-sm-8">
										<form:input path="paydaysfromstartday"
											value="${partner.paydaysfromstartday}"
											placeholder="Duration of Payment from Start Date"
											class="form-control" />
									</div>
								</div>
								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">Payment From</label>
									<div class="col-sm-8">
										<form:select path="paycyclefromshipordel" items="${datemap}"
											class="form-control" name="account" id="paymentField">
										</form:select>
									</div>
								</div>
							</div>
							<div>
								<small class="help-block">(For ex: If your Payment cycle
									is staring from 5th May to 10th May and Payment date for that
									cycel is 15th May , then you Start Date will have 5 Duration
									will have 5 and Payment from SD will have 10)</small>
							</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-datewisepay">
							<div class="row">
								<div class="col-md-6">
									<form:select path="isshippeddatecalc" items="${datemap}"
										class="form-control" id="paymentField1">
									</form:select>
								</div>
								<div class="col-md-6 payment-box" id="true">
									<form:input path="noofdaysfromshippeddate"
										id="noofdaysfromshippeddate"
										value="${partner.noofdaysfromshippeddate}"
										placeholder="Payment Days From Shipped Date"
										class="form-control" />
								</div>
								<div class="col-md-6 payment-box" id="false">
									<form:input path="noofdaysfromdeliverydate"
										id="noofdaysfromdeliverydate"
										value="${partner.noofdaysfromdeliverydate}"
										placeholder="Payment Days From Delivery Date"
										class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-monthly">
							<div class="row">
								<div class="col-md-4">
									<form:input path="monthlypaydate"
										value="${partner.monthlypaydate}" placeholder="Enter Day"
										class="form-control" />
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="hr-line-dashed"></div>
							<label> ALIAS NAME &nbsp; &nbsp; &nbsp; <form:input
									path="pcDesc" value="${partner.pcDesc}" class="form-control" />
							</label>
						</div>
						<div class="col-sm-12">
							<div class="hr-line-dashed"></div>
							<div class="col-sm-6" style="border-right: 1px solid #ccc;">
								<h5 style="text-align: center;">TOI SETTINGS</h5>
								<table
									style="width: 80%; margin-left: 20px; border-spacing: 0 5px;">
									<tbody>
										<tr>
											<td style="width: 25%;">0.5 CR -1 CR</td>
											<td>
												<div class="input-group">
													<input type="text" class="form-control"> <span
														class="input-group-addon">%</span>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width: 25%;">1 CR -1.99 CR</td>
											<td>
												<div class="input-group">
													<input type="text" class="form-control"> <span
														class="input-group-addon">%</span>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width: 25%;">> 2 CR</td>
											<td>
												<div class="input-group">
													<input type="text" class="form-control"> <span
														class="input-group-addon">%</span>
												</div>
											</td>
										</tr>
										<tr>
											<td>BASIS OF TOI</td>
											<td>
												<div class="input-group" style="width: 100%;">
													<select class="form-control">
														<option value="MRP">MRP</option>
														<option value="SP">SP</option>
													</select>
												</div>
											</td>
										</tr>
										<tr>
											<td>TOI FREQUENCY</td>
											<td>
												<div class="input-group" style="width: 100%;">
													<select class="form-control">
														<option value="ANNUAL">ANNUAL</option>
														<option value="QUARTERLY">QUARTERLY</option>
														<option value="MONTHLY">MONTHLY</option>
													</select>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="col-sm-6">
								<div class="col-sm-12">
									<div class="col-sm-4">
										<div class="radio">
											<label>NET COMMISSION</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label> <form:radiobutton
													path="nrnReturnConfig.commissionType" value="fixed"
													id="commissionType-fixed" name="toggler"
													onChange="handleRadioEvent(this);" class="commissionType" />FIXED
											</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label> <form:radiobutton
													path="nrnReturnConfig.commissionType" value="categoryWise"
													id="commissionType-categoryWise" class="commissionType"
													name="toggler" onChange="handleRadioEvent(this);" />
												CATEGORY WISE
											</label>
										</div>
									</div>
									<div class="col-sm-12 radio1" id="blk-commissionType-fixed">
										<div class="row">
											<div class="col-md-12">
												<div class="input-group m-b col-md-4">
													<input type="text" class="form-control"
														name="nr-fixedCommissionPercent"
														value="${chargeMap.fixedCommissionPercent}"> <span
														class="input-group-addon">%</span>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 radio1"
										id="blk-commissionType-categoryWise">
										<div class="row">
											<div class="col-md-12">
												<c:choose>
													<c:when test="${!empty commissionMap}">
														<c:forEach items="${commissionMap}" var="cat"
															varStatus="loop">
															<div class="form-group col-md-12">
																<label class="col-md-4 control-label">${cat.key}</label>
																<div class="input-group m-b col-md-4">
																	<input type="text" class="form-control validateNumber "
																		name='nr-comm-${cat.key}' value='${cat.value}'
																		id='categoryWiseCommission'> <span
																		class="input-group-addon">%</span>
																</div>
															</div>
														</c:forEach>
													</c:when>
													<c:when test="${!empty commissionMap}">
														<c:forEach items="${commissionMap}" var="category"
															varStatus="loop">
															<div class="form-group col-md-12">
																<label class="col-md-4 control-label">${category}</label>
																<div class="input-group m-b col-md-4">
																	<input type="text" class="form-control validateNumber"
																		name='nr-comm-${category}'>
																	<!--   <span class="input-group-addon">%</span> -->
																</div>
															</div>
														</c:forEach>
													</c:when>
												</c:choose>
												<%-- <c:if test="${!empty categoryList}">
													<c:forEach items="${categoryList}" var="category"
														varStatus="loop">
														<div class="form-group col-md-12">
															<label class="col-md-4 control-label">${category}</label>
															<div class="input-group m-b col-md-4">
																<input type="text" class="form-control"
																	name='nr-comm-${category}' value="${commissionMap.${category}}">
															</div>
														</div>
													</c:forEach>
												</c:if> --%>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="hr-line-dashed"></div>
									<div class="col-sm-4">
										<div class="radio">
											<label>TAX (ON SP)</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label> <form:radiobutton
													path="nrnReturnConfig.taxSpType" value="fixed"
													id="taxSpType-fixed" name="toggler"
													onChange="handleRadioEvent(this);" class="taxSpType" />FIXED
											</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label> <form:radiobutton
													path="nrnReturnConfig.taxSpType" value="categoryWise"
													id="taxSpType-categoryWise" class="taxSpType"
													name="toggler" onChange="handleRadioEvent(this);" />CATEGORY
												WISE
											</label>
										</div>
									</div>
									<div class="col-sm-12 radio1" id="blk-taxSpType-fixed">
										<div class="row">
											<div class="col-md-12">
												<div class="input-group m-b col-md-4">
													<select name="nr-fixedTaxSpPercent">
														<option></option>
														<c:forEach items="${taxCategoryList}" var="taxCategory">
															<c:set var="taxSpMapCategory"
																value="taxSpMap.${taxCategory}" />
															<option value="${taxCategory}"
																${taxCategory == map[taxSpMapCategory] ? 'selected="selected"' : ''}>${taxCategory}</option>
															<%-- <option>${taxCategory}</option> --%>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 radio1" id="blk-taxSpType-categoryWise">
										<div class="row">
											<div class="col-md-12">
												<c:if test="${!empty categoryList}">
													<c:forEach items="${categoryList}" var="category"
														varStatus="loop">
														<div class="form-group col-md-12">
															<label class="col-md-4 control-label">${category}</label>
															<div class="input-group m-b col-md-4">
																<select name='nr-taxSp-${category}'>
																	<option></option>
																	<c:forEach items="${taxCategoryList}" var="taxCategory">
																		<c:set var="taxSpMapCategory"
																			value="taxSpMap.${taxCategory}" />
																		<option value="${taxCategory}"
																			${taxCategory == map[taxSpMapCategory] ? 'selected="selected"' : ''}>${taxCategory}</option>
																		<%-- <option>${taxCategory}</option> --%>
																	</c:forEach>
																</select>
															</div>
														</div>
													</c:forEach>
												</c:if>
											</div>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="hr-line-dashed"></div>
									<div class="col-sm-4">
										<div class="radio">
											<label>TAX (ON PO PRICE)</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label> <form:radiobutton
													path="nrnReturnConfig.taxPoType" value="fixed"
													id="taxPoType-fixed" name="toggler"
													onChange="handleRadioEvent(this);" class="taxPoType" />FIXED
											</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label> <form:radiobutton
													path="nrnReturnConfig.taxPoType" value="categoryWise"
													id="taxPoType-categoryWise" class="taxPoType"
													name="toggler" onChange="handleRadioEvent(this);" />
												CATEGORY WISE
											</label>
										</div>
									</div>
									<div class="col-sm-12 radio1" id="blk-taxPoType-fixed">
										<div class="row">
											<div class="col-md-12">
												<div class="input-group m-b col-md-4">
													<select name="nr-fixedTaxPoPercent">
														<option></option>
														<c:forEach items="${taxCategoryList}" var="taxCategory">
															<c:set var="taxPoMapCategory"
																value="taxPoMap.${taxCategory}" />
															<option value="${taxCategory}"
																${taxCategory == map[taxPoMapCategory] ? 'selected="selected"' : ''}>${taxCategory}</option>
															<%-- <option>${taxCategory}</option> --%>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 radio1" id="blk-taxPoType-categoryWise">
										<div class="row">
											<div class="col-md-12">
												<c:if test="${!empty categoryList}">
													<c:forEach items="${categoryList}" var="category"
														varStatus="loop">
														<div class="form-group col-md-12">
															<label class="col-md-4 control-label">${category}</label>
															<div class="input-group m-b col-md-4">
																<select name='nr-taxPo-${category}'>
																	<option></option>
																	<c:forEach items="${taxCategoryList}" var="taxCategory">
																		<c:set var="taxPoMapCategory"
																			value="taxPoMap.${taxCategory}" />
																		<option value="${taxCategory}"
																			${taxCategory == map[taxPoMapCategory] ? 'selected="selected"' : ''}>${taxCategory}</option>
																		<%-- <option>${taxCategory}</option> --%>
																	</c:forEach>
																</select>
															</div>
														</div>
													</c:forEach>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="ibox-content add-company">
								<input class="btn btn-primary pull-right" id="submitButton"
									type="submit" value="Save">
							</div>
						</div>
					</form:form>
				</div>
			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="../globaljslinks.jsp"></jsp:include>
	<!-- iCheck -->
	<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script>
	<!-- Switchery -->
	<script src="/O2R/seller/js/plugins/switchery/switchery.js"></script>
	<script src="/O2R/seller/js/pickList.js"></script>
	<script type="text/javascript">
		function handleRadioEvent(thiss) {
			var x = thiss.id;

			if (!(x >= 1 && x <= 10))
				$('.radio1').hide();

			$("#blk-" + x).slideDown();

			for (var a = 0; a < 10; a++) {
				if (a != x) {
					$("#blk-" + a).slideUp();
				} else {
					$("#blk-" + a).slideDown();
				}
			}
		}
	</script>
	<script type="text/javascript">
		div = {
			show : function(elem) {
				document.getElementById(elem).style.visibility = 'visible';
			},
			hide : function(elem) {
				document.getElementById(elem).style.visibility = 'hidden';
			}
		}
	</script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('.i-checks').iCheck({
								checkboxClass : 'icheckbox_square-green',
								radioClass : 'iradio_square-green',
							});
							var elem = document.querySelector('.js-switch');
							var switchery = new Switchery(elem, {
								color : '#1AB394'
							});

							var elem_2 = document.querySelector('.js-switch_2');
							var switchery_2 = new Switchery(elem_2, {
								color : '#ED5565'
							});

							var elem_3 = document.querySelector('.js-switch_3');
							var switchery_3 = new Switchery(elem_3, {
								color : '#1AB394'
							});

							$(".commissionType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".taxSpType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".taxPoType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});

							$("[name=paymentType]").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).val()).slideDown();
							});
							$('#paymentField').change(function() {
								$('.payment-box').hide();
								$('#' + $(this).val()).fadeIn();
							});
							$('#paymentField1').change(function() {
								$('.payment-box').hide();
								$('#' + $(this).val()).fadeIn();
							});
							$('#data_1 .input-group.date').datepicker({
								todayBtn : "linked",
								keyboardNavigation : false,
								forceParse : false,
								calendarWeeks : true,
								autoclose : true
							});

							if ('${partner.paymentType}' == 'paymentcycle')
								$("#paymentcycle").prop("checked", true)
										.trigger("click");
							else if ('${partner.paymentType}' == 'datewisepay') {
								$("#datewisepay").prop("checked", true)
										.trigger("click");
								$('#paymentField1').trigger('change');
								if ('${partner.isshippeddatecalc}' != true) {
									$("#noofdaysfromdeliverydate")
											.val(
													'${partner.noofdaysfromshippeddate}');
								}
							} else if ('${partner.paymentType}' == 'monthly')
								$('#monthly').prop("checked", true).trigger(
										"click");

							$("#submitButton").click(function() {
								submitForm();
							});

							if ('${partner.nrnReturnConfig.commissionType}' == 'fixed')
								$("#commissionType-fixed")
										.prop("checked", true).trigger("click");
							else if ('${partner.nrnReturnConfig.commissionType}' == 'categoryWise')
								$("#commissionType-categoryWise").prop(
										"checked", true).trigger("click");

							if ('${partner.nrnReturnConfig.taxSpType}' == 'fixed')
								$("#taxSpType-fixed").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.taxSpType}' == 'categoryWise')
								$("#taxSpType-categoryWise").prop("checked",
										true).trigger("click");

							if ('${partner.nrnReturnConfig.taxPoType}' == 'fixed')
								$("#taxPoType-fixed").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.taxPoType}' == 'categoryWise')
								$("#taxPoType-categoryWise").prop("checked",
										true).trigger("click");

							<c:forEach items="${taxPoMap}" var="cat">

							$('select[name="nr-taxPo-${cat.key}"]').val(
									'${cat.value}');
							</c:forEach>

							<c:forEach items="${taxSpMap}" var="cat">

							$('select[name="nr-taxSp-${cat.key}"]').val(
									'${cat.value}');
							</c:forEach>

						});

		var nameAvailability = true;

		function checkOnBlur() {
			var partner = document.getElementById("partnerName").value;
			$.ajax({
				url : "ajaxPartnerCheck.html?partner=" + partner,
				success : function(res) {
					if (res == "false") {
						if ('${partner.pcId}' != '0') {
							nameAvailability = true;
							$("#partnerNameMessage").html(
									"Partner Name available");
						} else {
							nameAvailability = false;
							$("#partnerNameMessage").html(
									"Partner Name  already exist");
						}
					} else {
						nameAvailability = true;
						$("#partnerNameMessage").html(
								"Partner Name   available");
					}
				}
			});
		}

		function submitForm() {

			var validator = $("#addpartnerform")
					.validate(
							{
								rules : {
									pcName : {
										required : true,
										number : false,
									},
									maxReturnAcceptance : {
										required : true,
										min : 1,
										max : 100,
										number : true,
									},
									maxRTOAcceptance : {
										required : true,
										min : 1,
										max : 100,
										number : true,
									},
									paymentType : {
										required : true
									},
									startcycleday : {
										required : function(element) {
											return (getRole('paymentcycleClass') == 'paymentcycle');
										},
										number : true,
										min : 1,
										max : 31,

									},
									paycycleduration : {
										required : function(element) {
											return (getRole('paymentcycleClass') == 'paymentcycle');
										},
										number : true,
										min : 1,
										max : 31,

									},
									paydaysfromstartday : {
										required : function(element) {
											return (getRole('paymentcycleClass') == 'paymentcycle');
										},
										number : true,
										min : 1,
										max : 31,

									},
									monthlypaydate : {
										required : function(element) {
											return (getRole('paymentcycleClass') == 'monthly');
										},
										number : true,
										min : 1,
										max : 31,

									}
								},
								errorElement : "span",
								messages : {
									pcName : " Enter partner name",
									maxReturnAcceptance : "Max return required between 1 and 100",
									maxRTOAcceptance : "RTO acceptance required between 1 and 100",
									toggler : "Please select any Payment Cycle"
								}

							});

			$(".commissionType").rules("add", {
				required : function(element) {
					var clickCheckbox = document.querySelector('.js-switch_2');
					return clickCheckbox.checked;
				}
			});

			$("#fixedCommissionPercent").rules("add", {
				required : function(element) {
					if (getRole('commissionType') == 'fixed')
						return true;
					else
						return false;
				},
				number : true
			});
			/*  $("#categoryWiseCommission").rules("add", { 
			
			  required:function(element) {
				  a=getRole('commissionType')
					alert("hi there "+a);
				if(getRole('commissionType')=='categoryWise')
					
					return true;
				else 
					return false;
				},
				number : true
			}); */
			$("#serviceTax").rules("add", {
				required : function(element) {
					var clickCheckbox = document.querySelector('.js-switch_2');
					return clickCheckbox.checked;
				},
				number : true
			});

			if (validator.form() && nameAvailability) {
				$('form#addpartnerform').submit();
			}
		}

		function getRole(radioclassname) {
			return $("#addpartnerform").find(
					"input." + radioclassname + ":checked").val();
		}
	</script>
</body>
</html>
