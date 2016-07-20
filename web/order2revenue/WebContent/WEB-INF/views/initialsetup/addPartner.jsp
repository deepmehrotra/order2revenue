<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*"%>
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
<link href="/O2R/seller/css/bootstrap.min.css" rel="stylesheet">
<link href="/O2R/seller/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="/O2R/seller/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="/O2R/seller/css/animate.css" rel="stylesheet">
<link href="/O2R/seller/css/style.css" rel="stylesheet">
<link href="/O2R/seller/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="/O2R/seller/css/plugins/switchery/switchery.css"
	rel="stylesheet">
<link href="/O2R/seller/css/tooltip.css" rel="stylesheet">


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
				<form:form method="POST" action="savePartner.html"
					id="addpartnerform" name="addpartnerform" role="form"
					class="form-horizontal" enctype="multipart/form-data">
					<div class="row">
						<div class="col-lg-12">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>Partner</h5>
								</div>
								<div class="ibox-content add-company">

									<c:if test="${!empty partner.pcId}">
										<%--  <form:hidden path="pcId" value="${partner.pcId}"/> --%>
										<input type="hidden" name="pcId" id="pcId"
											value="${partner.pcId}" />
										<input type="hidden" name="nrnReturnConfig.configId"
											id="nrnReturnConfig.configId"
											value="${partner.nrnReturnConfig.configId}" />
									</c:if>

									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label">Partner Name</label>

											<div class="col-sm-8">
												<c:choose>
													<c:when test="${partner.pcId != 0}">
														<form:input path="pcName" value="${partner.pcName}"
															class="form-control" id="partnerName" readonly="true" />
													</c:when>
													<c:otherwise>
														<form:input path="pcName" value="${partner.pcName}"
															class="form-control" id="partnerName"
															onblur="checkOnBlur()" />
														<span id="partnerNameMessage"
															style="font-weight: bold;color=red"></span>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label">Alias Name</label>

											<div class="col-sm-8">
												<form:input path="pcDesc" value="${partner.pcDesc}"
													class="form-control" />
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label class="col-sm-2 control-label">Upload Brand
												Logo</label>

											<div class="col-md-4">
												<label title="Upload image file" for="image"
													class="btn btn-white btn-block"> <i
													class="fa fa-upload"></i> <input type="file"
													accept="image/*" name="image" id="image" class="hide">
													Upload Logo
												</label>
												<c:if test="${partner.pcLogoUrl != null}">
													<input type="hidden" name="pcLogoUrl" id="pcLogoUrl"
														value="${partner.pcLogoUrl}" />

												</c:if>
											</div>

										</div>


										<div class="hr-line-dashed"></div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label><form:radiobutton path="paymentType"
													value="paymentcycle" id="paymentcycle" name="toggler"
													class="paymentcycleClass" />Subdivided Monthly</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label><form:radiobutton path="paymentType"
													value="datewisepay" id="datewisepay" name="toggler"
													class="paymentcycleClass" />Payment from Delivery/Ship
												Date</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label><form:radiobutton path="paymentType"
													value="monthly" id="monthly" name="toggler" />Monthly
												Payment</label>
										</div>
									</div>

									<div class="col-sm-12 radio1" id="blk-paymentcycle">
										<div class="col-sm-6">
											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">Cycle Start
													date</label>
												<div class="col-sm-8">
													<form:input path="startcycleday"
														value="${partner.startcycleday}"
														placeholder="Duration of Payment from Start Date"
														class="form-control" />
												</div>
											</div>
											<div class="mar-btm-20-oh">
												<label class="col-sm-4 control-label">Cycle End date</label>
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
												<label class="col-sm-4 control-label">Payment date</label>
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
													<form:select path="paycyclefromshipordel"
														items="${datemap}" class="form-control" name="account"
														id="paymentField">
													</form:select>
												</div>
											</div>
										</div>
										<small class="help-block">(For ex: If your first
											payment cycle of month is staring from 5th May to 10th May
											and Payment date for that cycel is 15th May , then you Start
											Date will have 5 End day will have 10 and Payment day will
											have 15)</small>
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
											<div class="col-md-6 payment-box" id="false"
												style="display: block;">
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
										<div class="form-group">
											<label class="col-sm-3 control-label">Max RTO
												Acceptance Period</label>
											<div class="col-md-4">
												<form:input path="maxRTOAcceptance"
													value="${partner.maxRTOAcceptance}"
													placeholder="Enter Value" class="form-control" />
											</div>
											<div class="col-md-4">
												<select class="form-control" name="account">
													<option>Days</option>
												</select>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<label class="col-sm-3 control-label">Max Return
												Acceptance</label>
											<div class="col-md-4">
												<form:input path="maxReturnAcceptance"
													value="${partner.maxReturnAcceptance}"
													placeholder="Enter Value" class="form-control" />
											</div>
											<div class="col-md-4">
												<select class="form-control" name="account">
													<option>Days</option>
												</select>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="form-group">
											<%-- <div class="col-md-3">
												<form:input path="taxcategory"
													value="${partner.taxcategory}"
													placeholder="Tax Category" class="form-control" />
											</div>
											<div class="col-md-4 content-rgt">
												<form:input path="taxrate"
													value="${partner.taxrate}"
													placeholder="Tax Rate" class="form-control" />
												<span>%</span>
											</div> --%>
											<div class="col-md-4">
												<form:checkbox path="tdsApplicable" id="tdsApplicable" />
												TDS Applicable
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="col-lg-12">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>State Selection</h5>
								</div>
								<div class="ibox-content add-company">
									<div class="row">
										<div class="col-sm-2">Select State</div>
										<div class="col-sm-2">Local</div>
										<div class="col-sm-2">Zonal</div>
										<div class="col-sm-2">National</div>
										<div class="col-sm-2">Metro</div>
									</div>
									<div class="row">
										<div class="col-lg-12 m-l-n">
											<div class="panel panel-default">
												<div class="panel-body">
													<div id="pickList"></div>
													<br> <br>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-lg-12">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>Dropship</h5>
								</div>
								<div class="ibox-content add-company">
									<div class="col-lg-12">
										<!--   <input type="checkbox" class="js-switch_2" id="nr-switch" /> -->
										<form:checkbox path="nrnReturnConfig.nrCalculator"
											value="${partner.nrnReturnConfig.nrCalculator}"
											class="js-switch_2" id="nr-switch" />
										<label>NR Switch</label>
										<div class="col-sm-12 radio5" id="nr-switch-sec">
											<!--<div class="col-sm-12 radio1" id="blk-200">-->
											<h3>NR Calculator</h3>
											<div class="panel-body">
												<div class="panel-group" id="accordion">
													<div class="panel panel-default">
														<div class="panel-heading">
															<div class="help-tip">
																<p>This is the inline help tip! You can explain to
																	your users what this section of your web app is about.</p>
															</div>
															<h5 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseOne1">Commission</a>
															</h5>
														</div>
														<div id="collapseOne1" class="panel-collapse collapse in">
															<div class="panel-body">
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.commissionType" value="fixed"
																					id="commisionType-fixed" name="commissionType"
																					class="commissionType" /> <!--       <input type="radio" value="4" id="optionsRadios1" name="toggler"> -->
																				Fixed
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.commissionType"
																					value="categoryWise"
																					id="commisionType-categoryWise"
																					name="commissionType" class="commissionType" /> <!--  <input type="radio" value="5" id="optionsRadios1" name="toggler"> -->
																				Category Wise
																			</label>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12 radio1"
																	id="blk-commisionType-fixed">
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="input-group m-b col-md-4">
																				<input type="text"
																					class="form-control validateNumber"
																					name="nr-fixedCommissionPercent"
																					id="fixedCommissionPercent"
																					value="${chargeMap.fixedCommissionPercent}">
																				<span class="input-group-addon"></span>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12 radio1"
																	id="blk-commisionType-categoryWise">
																	<c:choose>
																		<c:when test="${!empty categoryMap}">
																			<c:forEach items="${categoryMap}" var="cat"
																				varStatus="loop">
																				<div class="form-group col-md-12">
																					<label class="col-md-4 control-label">${cat.key}</label>
																					<div class="input-group m-b col-md-4">
																						<input type="text"
																							class="form-control validateNumber "
																							name='nr-${cat.key}' value='${cat.value}'
																							id='categoryWiseCommission'> <span
																							class="input-group-addon">%</span>
																					</div>
																				</div>
																			</c:forEach>
																		</c:when>
																		<c:when test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category"
																				varStatus="loop">
																				<div class="form-group col-md-12">
																					<label class="col-md-4 control-label">${category}</label>
																					<div class="input-group m-b col-md-4">
																						<input type="text"
																							class="form-control validateNumber"
																							name='nr-${category}'>
																						<!--   <span class="input-group-addon">%</span> -->
																					</div>
																				</div>
																			</c:forEach>
																		</c:when>
																	</c:choose>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseTwo1">Fixed Fee</a>
															</h4>
														</div>
														<div id="collapseTwo1" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Please select .....</h4>
																<div id="room_fileds">
																	<div id="content">
																		<div class="col-sm-12">
																			<div class="form-group col-md-12">
																				<div class="col-md-3 content-rgt">
																					<form:select class="form-control" path="fixedfeeList[0].criteria"
																						name="nr-fixedfee-criteria">
																						<option>Upto</option>
																						<option>Greater Than</option>
																					</form:select>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<form:input type="text" path="fixedfeeList[0].range"
																						class="form-control validateNumber"
																						name="nr-fixedfee-range" id="nr-fixedfee-range" />
																				</div>
																				<div class="col-md-3 content-rgt">
																					<form:input type="text" path="fixedfeeList[0].value"
																						class="form-control validateNumber"
																						name="nr-fixedfee-value" id="nr-fixedfee-value" />
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="button" onclick="myFunction();"
																						value="+" class="btn btn-primary" id="myBtn" />
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>

													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapseThree1">Payment Collection Charges</a>
															</h4>
														</div>
														<div id="collapseThree1" class="panel-collapse collapse">
															<div class="panel-body">
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.whicheverGreaterPCC"
																					value="false" id="pccValue"
																					name="whicheverGreaterPCC" class="pccValue" />
																				Value Based
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.whicheverGreaterPCC"
																					value="true" id="pccHigher"
																					name="whicheverGreaterPCC" class="pccHigher" />
																				Which Ever is Higher
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1 collpase in"
																		id="blk-pccValue">
																		<div class="col-sm-6" ng-app="myApp"
																			ng-controller="myCtrl">
																			<div class="col-md-2">
																				<label>Upto</label>
																			</div>
																			<div class="col-md-5" style="padding: 0px;">
																				<input type="text" name="nr-pcc-range" class="form-control"
																					ng-model="firstname">

																			</div>
																			<div class="col-md-5">
																				<input type="text" name="nr-pcc-value" class="form-control">
																			</div>
																			<br> <br> <br>
																			<div class="col-md-2">
																				<label>Greater than</label>
																			</div>
																			<div class="col-md-5" style="padding: 0px;">																
																				<label> &nbsp; &nbsp;{{firstname}}</label>
																			</div>

																			<div class="col-md-5">
																				<div class="col-md-4" style="padding: 0px;">
																					<input type="text" name="nr-pcc-percentSP" class="form-control">

																				</div>
																				<div class="col-md-8"
																					style="margin-top: 3px; padding: 0px 0px 0px 5px;">
																					<span class="input-group-addon"
																						style="border: 1px solid #ebdfdf;">% Of SP</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-6"></div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-pccHigher">
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<label> Percentage of SP </label>
																			</div>
																			<div class="col-sm-6">
																				<div class="input-group m-b">
																					<input type="text" name="nr-pcc-percentSP" class="form-control"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<label> Fixed Amount </label>
																			</div>
																			<div class="col-sm-6">
																				<div class="input-group m-b">
																					<input type="text" name="nr-pcc-fixedAmt" class="form-control"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapsefour1">Shipping Fee</a>
															</h4>
														</div>
														<div id="collapsefour1" class="panel-collapse collapse">
															<div class="panel-body">
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.shippingFeeType"
																					value="variable" id="shippingfee-variable"
																					name="shippingFeeType" class="shippingFeeType" />
																			 	Variable Shipping Charges
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.shippingFeeType"
																					value="fixed" id="shippingfee-fixed"
																					name="shippingFeeType" class="shippingFeeType" /> 																
																				Fixed Shipping Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12 radio1"
																	id="blk-shippingfee-variable">
																	<div
																		class="col-sm-12 center-align text-center font-bold">
																		<h4 class="text-info">Which Ever is Higher</h4>
																	</div>
																	<div class="col-sm-12">
																		<h4>Volume calculation= (lxbxh)(cm)/5000</h4>
																		<table class="table table-bordered" id="myTable">
																			<thead>
																				<tr>
																					<th>Volume Weight Slab(gms)</th>
																					<th>Value</th>
																					<th>Local</th>
																					<th>Zonal</th>
																					<th>National</th>
																					<th>Metro</th>
																					<th>Add More</th>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<td><select class="form-control selected"
																						name="nr-shippingFeeVolume-criteria" name="nr-shippingFeeVolume-criteria">
																							<option value="upto">Upto</option>
																							<option value="additional">Additional</option>
																					</select></td>
																					<td><div class="form-group ">
																							<div class=" content-rgt">
																								<input type="text" name="nr-shippingFeeVolume-range"
																									class="form-control">
																							</div>
																						</div></td>
																					<td><div class="form-group ">
																							<div class=" content-rgt">
																								<input type="text" name="nr-shippingFeeVolume-localValue"
																									class="form-control">
																							</div>
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="text" name="nr-shippingFeeVolume-zonalValue"
																								class="form-control">
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="text" name="nr-shippingFeeVolume-nationalValue"
																								class="form-control">
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="text" name="nr-shippingFeeVolume-metroValue"
																								class="form-control">
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="button"
																								class="button btn btn-primary" value="+"
																								onclick="addField()">
																						</div></td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																	<div class="col-sm-12">
																		<h4>Weight calculation</h4>
																		<table class="table table-bordered" id="myTable2">
																			<thead>
																				<tr>
																					<th>Volume Weight Slab(gms)</th>
																					<th>Value</th>
																					<th>Local</th>
																					<th>Zonal</th>
																					<th>National</th>
																					<th>Metro</th>
																					<th>Add More</th>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<td><select class="form-control selected"
																						name="nr-shippingFeeWeight-criteria">
																							<option value="upto">Upto</option>
																							<option value="additional">Additional</option>
																					</select></td>
																					<td><div class="form-group ">
																							<div class=" content-rgt">
																								<input type="text" name="nr-shippingFeeWeight-range"
																									class="form-control">
																							</div>
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="text" name="nr-shippingFeeWeight-localValue"
																								class="form-control">
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="text" name="nr-shippingFeeWeight-zonalValue"
																								class="form-control">
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="text" name="nr-shippingFeeWeight-nationalValue"
																								class="form-control">
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="text" name="nr-shippingFeeWeight-metroValue"
																								class="form-control">
																						</div></td>
																					<td><div class=" content-rgt">
																							<input type="button"
																								class="button btn btn-primary" value="+"
																								onclick="addField2()">
																						</div></td>
																				</tr>

																			</tbody>
																		</table>
																	</div>
																</div>
																<div class="col-sm-12 radio1" id="blk-shippingfee-fixed">
																	<div
																		class="col-sm-12 center-align text-center font-bold">
																		<h4 class="text-info">Which Ever is Higher</h4>
																	</div>
																	<div class="col-sm-6">
																		<h4>Weight calculation</h4>
																		<table class="table table-bordered" id="myTable3">
																			<thead>
																				<tr>
																					<th>Weight Weight Slab(gms)</th>
																					<th>Value</th>
																					<th>Price</th>
																					<th>Add More</th>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<td><select class="form-control selected"
																						name="nr-shippingFeeWeight-criteria">
																							<option value="upto">Upto</option>
																							<option value="additional">Additional</option>
																					</select></td>
																					<td>
																						<div class="form-group ">
																							<div class=" content-rgt">
																								<input type="text" name="nr-shippingFeeWeight-range"
																									class="form-control">
																							</div>
																						</div>
																					</td>
																					<td>
																						<div class="form-group ">
																							<div class=" content-rgt">
																								<input type="text" name="nr-shippingFeeWeight-value"
																									class="form-control">
																							</div>
																						</div>
																					</td>
																					<td><input type="button" name=""
																						class="button btn btn-primary"
																						onclick="addField3()" value="+"></td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																	<div class="col-sm-6">
																		<h4>Volume calculation= (lxbxh)(cm)/5000</h4>
																		<table class="table table-bordered" id="myTable4">
																			<thead>
																				<tr>
																					<th>Volume Weight Slab(gms)</th>
																					<th>Value</th>
																					<th>Price</th>
																					<th>Add More</th>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<td><select class="form-control selected"
																						name="nr-shippingFeeVolume-criteria">
																							<option value="upto">Upto</option>
																							<option value="additional">Additional</option>
																					</select></td>
																					<td><input type="text" name="nr-shippingFeeVolume-range"
																						class="form-control"></td>
																					<td>
																						<div class="form-group ">
																							<div class=" content-rgt">
																								<input type="text" name="nr-shippingFeeVolume-value"
																									class="form-control">
																							</div>
																						</div>
																					</td>
																					<td><input type="button" name=""
																						class="button btn btn-primary" value="+"
																						onclick="addField4()"></td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
																	href="#collapsefive1">Service Tax</a>
															</h4>
														</div>
														<div id="collapsefive1" class="panel-collapse collapse">
															<div class="panel-body">
																<div class="form-group col-md-12">
																	<div class="col-md-4 input-group m-b">
																		<input type="text" placeholder=""
																			class="form-control validateNumber"
																			name="nr-serviceTax" value="${chargeMap.serviceTax}"
																			id="serviceTax" /> <span class="input-group-addon">%</span>
																	</div>
																</div>
															</div>
														</div>
													</div>

												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="ibox float-e-margins">
									<div class="ibox-title">
										<h5>Return Calculator</h5>
									</div>
									<div class="ibox-content add-company">
										<form class="form-horizontal">
											<div class="panel-body">
												<div class="panel-group" id="accordion1">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h5 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapseOne">Return Charges</a>
															</h5>
														</div>
														<div id="collapseOne" class="panel-collapse collapse in">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharSFType" value="fixed"
																					id="retrun-sf-fix" name="toggler"
																					class="retCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharSFType"
																					value="variable" id="retrun-sf-variable"
																					name="toggler" class="retCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharSFType"
																					value="noCharges" id="retrun-sf-nocharges"
																					name="toggler" class="retCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-retrun-sf-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text"
																						class="form-control validateNumber"
																						name="nr-retCharSFFixedAmt"
																						value="${chargeMap.retCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-retrun-sf-variable">
																		<div class="form-group col-lg-12">
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">Fix Amount</label>
																				</div>
																				<div class="col-md-6">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-retCharSFVarFixedAmt"
																						value="${chargeMap.retCharSFVarFixedAmt}">
																				</div>
																			</div>

																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">% of SP</label>
																				</div>
																				<div class="col-md-6 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-retCharSFPercentSP"
																						value="${chargeMap.retCharSFPercentSP}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-7">
																					<label class="control-label"> % of
																						Commision</label>
																				</div>
																				<div class="col-md-5 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-retCharSFPercentPCC"
																						value="${chargeMap.retCharSFPercentPCC}">
																					<span class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharSFFF"
																						id="retCharSFFF" /> <i></i> Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label><form:checkbox
																						path="nrnReturnConfig.retCharSFShipFee"
																						id="retCharSFShipFee" /> <i></i> Shipping Fee </label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharSFRevShipFee"
																						id="retCharSFRevShipFee" /> <i></i> Reverse
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharSFPCC"
																						id="retCharSFPCC" /> <i> </i> Payment Collection
																					Charges
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<h4>Buyer Return</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharBRType" value="fixed"
																					id="retrun-br-fix" name="toggler"
																					class="retCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharBRType"
																					value="variable" id="retrun-br-variable"
																					name="toggler" class="retCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.retCharBRType"
																					value="noCharges" id="retrun-br-nocharges"
																					name="toggler" class="retCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-retrun-br-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text"
																						class="form-control validateNumber"
																						name="nr-retCharBRFixedAmt"
																						value="${chargeMap.retCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-retrun-br-variable">
																		<div class="form-group col-lg-12">
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">Fix Amount</label>
																				</div>
																				<div class="col-md-6">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-retCharBRVarFixedAmt"
																						value="${chargeMap.retCharBRVarFixedAmt}">
																				</div>
																			</div>
																			<div class="col-sm-4 ">
																				<div class="col-md-6">
																					<label class="control-label">% of SP</label>
																				</div>
																				<div class="col-md-6 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-retCharBRPercentSP"
																						value="${chargeMap.retCharBRPercentSP}"><span
																						class="input-group-addon">%</span>
																				</div>
																			</div>

																			<div class="col-sm-4">
																				<div class="col-md-7">
																					<label class="control-label"> % of
																						Commision</label>
																				</div>
																				<div class="col-md-5 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-retCharBRPercentPCC"
																						value="${chargeMap.retCharBRPercentPCC}"><span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharBRFF"
																						id="retCharBRFF" /> <i></i> Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label><form:checkbox
																						path="nrnReturnConfig.retCharBRShipFee"
																						id="retCharBRShipFee" /> <i></i> Shipping Fee </label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapseTwo">RTO Charges</a>
															</h4>
														</div>
														<div id="collapseTwo" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharSFType" value="fixed"
																					id="RTO-sf-fix" name="toggler"
																					class="RTOCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharSFType"
																					value="variable" id="RTO-sf-variable"
																					name="toggler" class="RTOCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharSFType"
																					value="noCharges" id="RTO-sf-nocharges"
																					name="toggler" class="RTOCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-RTO-sf-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text"
																						class="form-control validateNumber"
																						name="nr-RTOCharSFFixedAmt"
																						value="${chargeMap.RTOCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1" id="blk-RTO-sf-variable">
																		<div class="form-group col-lg-12">
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">Fix Amount</label>
																				</div>
																				<div class="col-md-6">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-RTOCharSFVarFixedAmt"
																						value="${chargeMap.RTOCharSFFixedAmt}">
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">% of SP</label>
																				</div>
																				<div class="col-md-6 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-RTOCharSFPercentSP"
																						value="${chargeMap.RTOCharSFPercentSP}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-7">
																					<label class="control-label"> % of
																						Commision</label>
																				</div>
																				<div class="col-md-5 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-RTOCharSFPercentPCC"
																						value="${chargeMap.RTOCharSFPercentPCC}">
																					<span class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFFF"
																						id="RTOCharSFFF" /> <i></i> Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFShipFee"
																						id="RTOCharSFShipFee" /> <i></i> Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFRevShipFee"
																						id="RTOCharSFRevShipFee" /> <i></i> Reverse
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFPCC"
																						id="RTOCharSFPCC" /> <i></i> Payment Collection
																					Charges
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<h4>Buyer Return</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharBRType" value="fixed"
																					id="RTO-br-fix" name="toggler"
																					class="RTOCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharBRType"
																					value="variable" id="RTO-br-variable"
																					name="toggler" class="RTOCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.RTOCharBRType"
																					value="noCharges" id="RTO-br-nocharges"
																					name="toggler" class="RTOCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-RTO-br-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text"
																						class="form-control validateNumber"
																						name="nr-RTOCharBRFixedAmt"
																						value="${chargeMap.RTOCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-RTO-br-variable">

																		<div class="form-group col-lg-12">
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">Fix Amount</label>
																				</div>
																				<div class="col-md-6">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-RTOCharBRVarFixedAmt"
																						value="${chargeMap.RTOCharBRVarFixedAmt}">
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">% of SP</label>
																				</div>
																				<div class="col-md-6 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-RTOCharBRPercentSP"
																						value="${chargeMap.RTOCharBRPercentSP}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-7">
																					<label class="control-label"> % of
																						Commision</label>
																				</div>
																				<div class="col-md-5 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-RTOCharBRPercentPCC"
																						value="${chargeMap.RTOCharBRPercentPCC}">
																					<span class="input-group-addon">%</span>
																				</div>

																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharBRFF"
																						id="RTOCharBRFF" /> <i></i> Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharBRShipFee"
																						id='RTOCharBRShipFee' /> <i></i> Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>

													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapseThree">Replacement Charges</a>
															</h4>
														</div>
														<div id="collapseThree" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharSFType" value="fixed"
																					id="rep-sf-fix" name="toggler"
																					class="repCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharSFType"
																					value="variable" id="rep-sf-variable"
																					name="toggler" class="repCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.repCharSFType"
																					value="noCharges" id="rep-sf-nocharges"
																					name="toggler" class="repCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-rep-sf-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text"
																						class="form-control validateNumber"
																						name="nr-repCharSFFixedAmt"
																						value="${chargeMap.repCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1" id="blk-rep-sf-variable">
																		<div class="form-group col-lg-12">
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">Fix Amount</label>
																				</div>
																				<div class="col-md-6">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-repCharSFVarFixedAmt"
																						value="${chargeMap.repCharSFVarFixedAmt}">
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">% of SP</label>
																				</div>
																				<div class="col-md-6 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-repCharSFPercentSP"
																						value="${chargeMap.repCharSFPercentSP}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-7">
																					<label class="control-label"> % of
																						Commision</label>
																				</div>
																				<div class="col-md-5 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-repCharSFPercentPCC"
																						value="${chargeMap.repCharSFPercentPCC}">
																					<span class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFFF"
																						id="repCharSFFF" /> <i></i> Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFShipFee"
																						id="repCharSFShipFee" /> <i></i> Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFRevShipFee"
																						id="repCharSFRevShipFee" /> <i></i> Reverse
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFPCC"
																						id="repCharSFPCC" /> <i></i> Payment Collection
																					Charges
																				</label>
																			</div>
																		</div>
																	</div>

																	<div class="col-sm-12">
																		<div class="hr-line-dashed"></div>
																	</div>
																	<h4>Buyer Return</h4>
																	<div class="col-sm-12">
																		<div class="hr-line-dashed"></div>
																	</div>
																	<div class="col-sm-12">
																		<div class="col-sm-4">
																			<div class="radio">
																				<label> <form:radiobutton
																						path="nrnReturnConfig.repCharBRType" value="fixed"
																						id="rep-br-fix" name="toggler"
																						class="repCharBRType" /> Fixed Amount
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-4">
																			<div class="radio">
																				<label> <form:radiobutton
																						path="nrnReturnConfig.repCharBRType"
																						value="variable" id="rep-br-variable"
																						name="toggler" class="repCharBRType" /> Variable
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-4">
																			<div class="radio">
																				<label> <form:radiobutton
																						path="nrnReturnConfig.repCharBRType"
																						value="noCharges" id="rep-br-nocharges"
																						name="toggler" class="repCharBRType"
																						checked="checked" /> No Charges
																				</label>
																			</div>
																		</div>
																	</div>
																	<div class="row">
																		<div class="col-sm-12 radio1 m-b" id="blk-rep-br-fix">
																			<div class="col-sm-12">
																				<div class="form-group">
																					<div class="col-md-2 control-label">
																						<label>Enter Fix Charges</label>
																					</div>
																					<div class="col-md-3 content-rgt">
																						<input type="text"
																							class="form-control validateNumber"
																							name="nr-repCharBRFixedAmt"
																							value="${chargeMap.repCharBRFixedAmt}">
																					</div>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12 radio1" id="blk-rep-br-variable">
																			<div class="form-group col-lg-12">
																				<div class="col-sm-4">
																					<div class="col-md-6">
																						<label class="control-label">Fix Amount</label>
																					</div>
																					<div class="col-md-6">
																						<input type="text" placeholder=""
																							class="form-control validateNumber"
																							name="nr-repCharBRVarFixedAmt"
																							value="${chargeMap.repCharBRFixedAmt}">
																					</div>
																				</div>
																				<div class="col-sm-4">
																					<div class="col-md-6">
																						<label class="control-label">% of SP</label>
																					</div>
																					<div class="col-md-6 input-group m-b">
																						<input type="text" placeholder=""
																							class="form-control validateNumber"
																							name="nr-repCharBRPercentSP"
																							value="${chargeMap.repCharBRPercentSP}">
																						<span class="input-group-addon">%</span>
																					</div>
																				</div>
																				<div class="col-sm-4">
																					<div class="col-md-7">
																						<label class="control-label"> % of
																							Commision</label>
																					</div>
																					<div class="col-md-5 input-group m-b">
																						<input type="text" placeholder=""
																							class="form-control validateNumber"
																							name="nr-repCharBRPercentPCC"
																							value="${chargeMap.repCharBRPercentPCC}">
																						<span class="input-group-addon">%</span>
																					</div>
																				</div>
																			</div>
																			<div class="col-md-12">
																				<div class="checkbox i-checks">
																					<label> <form:checkbox
																							path="nrnReturnConfig.repCharBRFF"
																							id="repCharBRFF" /> <i></i> Fixed Fee
																					</label>
																				</div>
																			</div>
																			<div class="col-md-12">
																				<div class="checkbox i-checks">
																					<label> <form:checkbox
																							path="nrnReturnConfig.repCharBRShipFee"
																							id="repCharBRShipFee" /> <i></i> Shipping Fee
																					</label>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapsefive">Partial Delivery Charges</a>
															</h4>
														</div>
														<div id="collapsefive" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharSFType" value="fixed"
																					id="PD-sf-fix" name="toggler" class="PDCharSFType" />
																				Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharSFType"
																					value="variable" id="PD-sf-variable" name="toggler"
																					class="PDCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharSFType"
																					value="noCharges" id="PD-sf-nocharges"
																					name="toggler" class="PDCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-PD-sf-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text"
																						class="form-control validateNumber"
																						name="nr-PDCharSFFixedAmt"
																						value="${chargeMap.PDCharSFFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1" id="blk-PD-sf-variable">
																		<div class="form-group col-lg-12">
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">Fix Amount</label>
																				</div>
																				<div class="col-md-6">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-PDCharSFVarFixedAmt"
																						value="${chargeMap.PDCharSFVarFixedAmt}">
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">% of SP</label>
																				</div>
																				<div class="col-md-6 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-PDCharSFPercentSP"
																						value="${chargeMap.PDCharSFPercentSP}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-7">
																					<label class="control-label"> % of
																						Commision</label>
																				</div>
																				<div class="col-md-5 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-PDCharSFPercentPCC"
																						value="${chargeMap.PDCharSFPercentPCC}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFFF" id="PDCharSFFF" />
																					<i></i> Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFShipFee"
																						id="PDCharSFShipFee" /> <i></i> Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFRevShipFee"
																						id="PDCharSFRevShipFee" /> <i></i> Reverse
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFPCC"
																						id="PDCharSFPCC" /> <i></i> Payment Collection
																					Charges
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<h4>Buyer Return</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharBRType" value="fixed"
																					id="PD-br-fix" name="toggler" class="PDCharBRType" />
																				Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharBRType"
																					value="variable" id="PD-br-variable" name="toggler"
																					class="PDCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.PDCharBRType"
																					value="noCharges" id="PD-br-nocharges"
																					name="toggler" class="PDCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-PD-br-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text"
																						class="form-control validateNumber"
																						name="nr-PDCharBRFixedAmt"
																						value="${chargeMap.PDCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-PD-br-variable">
																		<div class="form-group col-lg-12">
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">Fix Amount</label>
																				</div>
																				<div class="col-md-6">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-PDCharBRVarFixedAmt"
																						value="${chargeMap.PDCharBRVarFixedAmt}">
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">% of SP</label>
																				</div>
																				<div class="col-md-6 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-PDCharBRPercentSP"
																						value="${chargeMap.PDCharBRPercentSP}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-7">
																					<label class="control-label"> % of
																						Commision</label>
																				</div>
																				<div class="col-md-5 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-PDCharBRPercentPCC"
																						value="${chargeMap.PDCharBRPercentPCC}"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharBRFF" id="PDCharBRFF" />
																					<i></i> Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharBRShipFee"
																						id="PDCharBRShipFee" /> <i></i> Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapsesix">Cancellation Charges</a>
															</h4>
														</div>
														<div id="collapsesix" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<h5>Before RTD</h5>
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="fixed" id="can-sfbfrtd-fix" name="toggler"
																					class="canCharSFBFRTDType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="variable" id="can-sfbfrtd-variable"
																					name="toggler" class="canCharSFBFRTDType" />
																				Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="noCharges" id="can-sfbfrtd-nocharges"
																					name="toggler" class="canCharSFBFRTDType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-can-sfbfrtd-fix">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text"
																						class="form-control validateNumber"
																						name="nr-canCharSFBFRTDFixedAmt"
																						value="${chargeMap.canCharSFBFRTDFixedAmt}">
																				</div>
																			</div>
																		</div>

																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-can-sfbfrtd-variable">
																		<div class="form-group col-lg-12">
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">Fix Amount</label>
																				</div>
																				<div class="col-md-6">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-canCharSFBFRTDVarFixedAmt"
																						value="${chargeMap.canCharSFBFRTDVarFixedAmt}">
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-6">
																					<label class="control-label">% of SP</label>
																				</div>
																				<div class="col-md-6 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-canCharSFBFRTDPercentSP"
																						value="${chargeMap.canCharSFBFRTDPercentSP}">
																					<span class="input-group-addon">%</span>
																				</div>
																			</div>
																			<div class="col-sm-4">
																				<div class="col-md-7">
																					<label class="control-label"> % of
																						Commision</label>
																				</div>
																				<div class="col-md-5 input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control validateNumber"
																						name="nr-canCharSFBFRTDPercentPCC"
																						value="${chargeMap.canCharSFBFRTDPercentPCC}">
																					<span class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDFF"
																						id="canCharSFBRTDFF" /> <i></i> Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDShipFee"
																						id="canCharSFBRTDShipFee" /> <i></i> Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDRevShipFee"
																						id="canCharSFBRTDRevShipFee" /> <i></i> Reverse
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFPCC"
																						id="canCharSFPCC" /> <i></i> Payment Collection
																					Charges
																				</label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="col-sm-12">
																<div class="hr-line-dashed"></div>
															</div>
															<div class="col-sm-12">
																<h5>After RTD</h5>
																<div class="hr-line-dashed"></div>
															</div>
															<div class="col-sm-12">
																<div class="col-sm-4">
																	<div class="radio">
																		<label> <form:radiobutton
																				path="nrnReturnConfig.canCharSFARTDType"
																				value="fixed" id="can-sfartd-fix" name="toggler"
																				class="canCharSFARTDType" /> Fixed Amount
																		</label>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="radio">
																		<label> <form:radiobutton
																				path="nrnReturnConfig.canCharSFARTDType"
																				value="variable" id="can-sfartd-variable"
																				name="toggler" class="canCharSFARTDType" />
																			Variable
																		</label>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="radio">
																		<label> <form:radiobutton
																				path="nrnReturnConfig.canCharSFARTDType"
																				value="noCharges" id="can-sfartd-nocharges"
																				name="toggler" class="canCharSFARTDType"
																				checked="checked" /> No Charges
																		</label>
																	</div>
																</div>
															</div>
															<div class="row">
																<div class="col-sm-12 radio1 m-b"
																	id="blk-can-sfartd-fix">
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="col-md-2 control-label">
																				<label>Enter Fix Charges</label>
																			</div>
																			<div class="col-md-3 content-rgt">
																				<input type="text"
																					class="form-control validateNumber"
																					name="nr-canCharSFFixedAmt"
																					value="${chargeMap.canCharSFFixedAmt}">
																			</div>
																		</div>
																	</div>

																</div>
																<div class="col-sm-12 radio1"
																	id="blk-can-sfartd-variable">
																	<div class="form-group col-lg-12">
																		<div class="col-sm-4">
																			<div class="col-md-6">
																				<label class="control-label">Fix Amount</label>
																			</div>
																			<div class="col-md-6">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFVarFixedAmt"
																					value="${chargeMap.canCharSFVarFixedAmt}">
																			</div>
																		</div>
																		<div class="col-sm-4">
																			<div class="col-md-6">
																				<label class="control-label">% of SP</label>
																			</div>
																			<div class="col-md-6 input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-canCharSFPercentSP"
																					value="${chargeMap.canCharSFPercentSP}"> <span
																					class="input-group-addon">%</span>
																			</div>
																		</div>
																		<div class="col-sm-4">
																			<div class="col-md-7">
																				<label class="control-label"> % of Commision</label>
																			</div>
																			<div class="col-md-5 input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-canCharSFPercentPCC"
																					value="${chargeMap.canCharSFPercentPCC}"> <span
																					class="input-group-addon">%</span>
																			</div>
																		</div>
																	</div>
																	<div class="col-md-12">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.canCharSFFF" id="canCharSFFF" />
																				<i></i> Fixed Fee
																			</label>
																		</div>
																	</div>
																	<div class="col-md-12">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.canCharSFShipFee"
																					id="canCharSFShipFee" /> <i></i> Shipping Fee
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.canCharSFARTDRevShipFee"
																					id="canCharSFARTDRevShipFee" /> <i></i> Reverse
																				Shipping Fee
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.canCharSFBRTDPCC"
																					id="canCharSFBRTDPCC" /> <i></i> Payment
																				Collection Charges
																			</label>
																		</div>
																	</div>
																</div>
															</div>
															<div class="col-sm-12">
																<div class="hr-line-dashed"></div>
															</div>
															<h4>Buyer Cancellation</h4>
															<div class="col-sm-12">
																<div class="hr-line-dashed"></div>
															</div>
															<div class="col-sm-12">
																<div class="col-sm-4">
																	<div class="radio">
																		<label> <form:radiobutton
																				path="nrnReturnConfig.canCharBRType" value="fixed"
																				id="can-br-fix" name="toggler" class="canCharBRType" />
																			Fixed Amount
																		</label>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="radio">
																		<label> <form:radiobutton
																				path="nrnReturnConfig.canCharBRType"
																				value="variable" id="can-br-variable" name="toggler"
																				class="canCharBRType" /> Variable
																		</label>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="radio">
																		<label> <form:radiobutton
																				path="nrnReturnConfig.canCharBRType"
																				value="noCharges" id="can-br-nocharges"
																				name="toggler" class="canCharBRType"
																				checked="checked" /> No Charges
																		</label>
																	</div>
																</div>
															</div>
															<div class="row">
																<div class="col-sm-12 radio1 m-b" id="blk-can-br-fix">
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="col-md-2 control-label">
																				<label>Enter Fix Charges</label>
																			</div>
																			<div class="col-md-3 content-rgt">
																				<input type="text"
																					class="form-control validateNumber"
																					name="nr-canCharBRFixedAmt"
																					value="${chargeMap.canCharSFPercentPCC}">
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12 radio1" id="blk-can-br-variable">
																	<div class="form-group col-lg-12">
																		<div class="col-sm-4">
																			<div class="col-md-6">
																				<label class="control-label">Fix Amount</label>
																			</div>
																			<div class="col-md-6">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-canCharBRVarFixedAmt"
																					value="${chargeMap.canCharBRVarFixedAmt}">
																			</div>
																		</div>
																		<div class="col-sm-4">
																			<div class="col-md-6">
																				<label class="control-label">% of SP</label>
																			</div>
																			<div class="col-md-6 input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-canCharBRPercentSP"
																					value="${chargeMap.canCharBRPercentSP}"> <span
																					class="input-group-addon">%</span>
																			</div>
																		</div>
																		<div class="col-sm-4">
																			<div class="col-md-7">
																				<label class="control-label"> % of Commision</label>
																			</div>
																			<div class="col-md-5 input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-canCharBRPercentPCC"
																					value="${chargeMap.canCharBRPercentPCC}"> <span
																					class="input-group-addon">%</span>
																			</div>
																		</div>
																	</div>
																	<div class="col-md-12">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.canCharBRFF" id="canCharBRFF" />
																				<i></i> Fixed Fee
																			</label>
																		</div>
																	</div>
																	<div class="col-md-12">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.canCharBRShipFee"
																					id="canCharBRShipFee" /> <i></i> Shipping Fee
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.canCharBRRevShipFee"
																					id="canCharBRRevShipFee" /> <i></i> Reverse
																				Shipping Fee
																			</label>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapseseven">Reverse Shipping Fee</a>
															</h4>
														</div>
														<div id="collapseseven" class="panel-collapse collapse">
															<div class="panel-body">
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType"
																					id="revShippingFeeType_revShipFeePCC"
																					value="revShipFeePCC" /> <i></i>( % of Shipping
																				Fee )
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="input-group m-b">
																			<input type="text" placeholder=""
																				class="form-control validateNumber"
																				name="nr-revShipFeePCC"
																				value="${chargeMap.revShipFeePCC}"> <span
																				class="input-group-addon">%</span>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType"
																					id="revShippingFeeType_revShipFeeGRT"
																					value="revShipFeeGRT" /> <i></i> Which Ever Is
																				Greater
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="col-sm-6">
																			<div class="checkbox i-checks">
																				<label> Flat Amount </label>
																			</div>
																		</div>
																		<div class="col-sm-6">
																			<div class="input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-revShipFeeFlatAmt"
																					value="${chargeMap.revShipFeeFlatAmt}">
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="col-sm-6">
																			<div class="checkbox i-checks">
																				<label> <i></i> % of Market Fee(Payment
																					Collection Charges)
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-6">
																			<div class="input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-revShipFeePCCMF"
																					value="${chargeMap.revShipFeePCCMF}"> <span
																					class="input-group-addon">%</span>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType"
																					id="revShippingFeeType_revShipFeeFF"
																					value="revShipFeeFF" /> <i></i>Fix Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="input-group m-b" style="width: 96%;">
																			<input type="text" placeholder=""
																				class="form-control validateNumber"
																				name="nr-revShipFeeFF"
																				value="${chargeMap.revShipFeeFF}">
																		</div>
																	</div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType"
																					id="revShippingFeeType_revShipFeeShipFee"
																					value="revShipFeeShipFee" /> <i></i>Same as
																				Shipping Fee
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6"></div>
																</div>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-6">
																		<div class="checkbox i-checks">
																			<label> <form:checkbox
																					path="nrnReturnConfig.revShippingFeeType"
																					id="revShippingFeeType_revShipFeeVar"
																					value="revShipFeeVar" /> <i></i>Variable Shipping
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6"></div>
																</div>
																<br> <br>
																<div class="col-sm-12">
																	<div class="col-sm-3 control-label">
																		<label> Dead Weight </label>
																	</div>
																	<div class="col-sm-3">
																		<label> </label>
																	</div>
																	<div class="col-sm-3 control-label">
																		<label> Volume Weight </label>
																	</div>
																	<div class="col-sm-3">
																		<label> </label>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="col-md-2 control-label">
																				<label>Amount</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-revShipFeeDWAmt"
																					value="${chargeMap.revShipFeeDWAmt}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Amount</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-revShipFeeVWAmt"
																					value="${chargeMap.revShipFeeVWAmt}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="col-md-2 control-label">
																				<label>Per Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-revShipFeeDWPW"
																					value="${chargeMap.revShipFeeDWPW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Per Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-revShipFeeVWPW"
																					value="${chargeMap.revShipFeeVWPW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="col-md-2 control-label">
																				<label>Min Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-revShipFeeDWMW"
																					value="${chargeMap.revShipFeeDWMW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Min Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control validateNumber"
																					name="nr-revShipFeeVWMW"
																					value="${chargeMap.revShipFeeVWMW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
												<input type="hidden" id="hidlocalList"
													value='${partner.nrnReturnConfig.localList}' /> <input
													type="hidden" id="hidzonalList"
													value='${partner.nrnReturnConfig.zonalList}' /> <input
													type="hidden" id="hidmetroList"
													value='${partner.nrnReturnConfig.metroList}' /> <input
													type="hidden" id="hidnationalList"
													value='${partner.nrnReturnConfig.nationalList}' />
											</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</form:form>
				<div class="ibox float-e-margins">
					<div class="ibox-content add-company">
						<input class="btn btn-primary pull-right" id="submitButton"
							type="submit" value="Save">
					</div>
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
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

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
		var v = 1;
		var index = 0;
		function myFunction() {
			v++;
			index = index + 1;
			var objTo = document.getElementById('room_fileds');
			var divtest = document.createElement("div");
			divtest.innerHTML = "<div class='col-sm-12'>"
					+ "<div class='form-group col-md-12'>"
					+ "<div class='col-md-3 content-rgt'>"
					+ "<select name='nr-fixedfee-criteria"+index+"' id=='nr-fixedfee-criteria"+index+"' onchange='(this.selectedIndex == 0) ? upto() : additional("
					+ (index - 1)
					+ ")' class='form-control'>"
					+ "<option>Upto</option>"
					+ "<option>Greater Than</option>"
					+ "</select>"
					+

					"</div>"
					+ "<div class='col-md-3 content-rgt'>"
					+ "<input type='text' name='nr-fixedfee-range"+index+"' placeholder='' class='form-control' id='txt_name"+index+"' multiple>"
					+ "</div>"
					+ "<div class='col-md-3 content-rgt'>"
					+ "<input type='text' placeholder='' name='nr-fixedfee-value"+index+"' id='nr-fixedfee-value"+index+"' class='form-control'>"
					+ "</div>" + "<div class='col-md-3 content-rgt'>"
					+ "</div>" + "</div>" + "</div>";
			objTo.appendChild(divtest)
		}
	</script>
	<script type="text/javascript">
		function addField(argument) {
			var myTable = document.getElementById("myTable");
			var currentIndex = myTable.rows.length;
			var currentRow = myTable.insertRow(-1);

			var linksBox = document.createElement("select");
			linksBox.setAttribute("name", "nr-shippingFeeVolume-criteria" + currentIndex);
			linksBox.setAttribute("option", "");
			linksBox.setAttribute("value", "");

			linksBox.setAttribute("type", "button");
			linksBox.setAttribute("class", "form-control");

			theOption = document.createElement("OPTION");
			theText = document.createTextNode("Upto");
			theOption.setAttribute("value", "Upto");
			theOption.appendChild(theText);
			linksBox.appendChild(theOption);

			theOption = document.createElement("OPTION");
			theText = document.createTextNode("Additional");
			theOption.setAttribute("value", "Additional");
			theOption.appendChild(theText);
			linksBox.appendChild(theOption);

			var linksBox1 = document.createElement("input");
			linksBox1.setAttribute("name", "nr-shippingFeeVolume-range" + currentIndex);
			linksBox1.setAttribute("class", "form-control");

			var linksBox2 = document.createElement("input");
			linksBox2.setAttribute("name", "nr-shippingFeeVolume-localValue" + currentIndex);
			linksBox2.setAttribute("class", "form-control");

			var linksBox3 = document.createElement("input");
			linksBox3.setAttribute("name", "nr-shippingFeeVolume-zonalValue" + currentIndex);
			linksBox3.setAttribute("class", "form-control");

			var keywordsBox = document.createElement("input");
			keywordsBox.setAttribute("name", "nr-shippingFeeVolume-nationalValue" + currentIndex);
			keywordsBox.setAttribute("class", "form-control");

			var violationsBox = document.createElement("input");
			violationsBox.setAttribute("name", "nr-shippingFeeVolume-metroValue" + currentIndex);
			violationsBox.setAttribute("class", "form-control");

			var addRowBox = document.createElement("input");
			addRowBox.setAttribute("type", "button");
			addRowBox.setAttribute("value", "+");
			addRowBox.setAttribute("onclick", "addField();");
			addRowBox.setAttribute("class", "button btn btn-primary");

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox1);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox2);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox3);

			currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(keywordsBox);

			currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(violationsBox);

			currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(addRowBox);
		}
		function addField2(argument) {
			var myTable = document.getElementById("myTable2");
			var currentIndex = myTable.rows.length;
			var currentRow = myTable.insertRow(-1);

			var linksBox = document.createElement("select");
			linksBox.setAttribute("name", "nr-shippingFeeWeight-criteria" + currentIndex);
			linksBox.setAttribute("option", "");
			linksBox.setAttribute("value", "");

			linksBox.setAttribute("type", "button");
			linksBox.setAttribute("class", "form-control");

			theOption = document.createElement("OPTION");
			theText = document.createTextNode("Upto");
			theOption.setAttribute("value", "Upto");
			theOption.appendChild(theText);
			linksBox.appendChild(theOption);

			theOption = document.createElement("OPTION");
			theText = document.createTextNode("Additional");
			theOption.setAttribute("value", "Additional");
			theOption.appendChild(theText);
			linksBox.appendChild(theOption);

			var linksBox1 = document.createElement("input");
			linksBox1.setAttribute("name", "nr-shippingFeeWeight-range" + currentIndex);
			linksBox1.setAttribute("class", "form-control");

			var linksBox2 = document.createElement("input");
			linksBox2.setAttribute("name", "nr-shippingFeeWeight-localValue" + currentIndex);
			linksBox2.setAttribute("class", "form-control");

			var linksBox3 = document.createElement("input");
			linksBox3.setAttribute("name", "nr-shippingFeeWeight-zonalValue" + currentIndex);
			linksBox3.setAttribute("class", "form-control");

			var keywordsBox = document.createElement("input");
			keywordsBox.setAttribute("name", "nr-shippingFeeWeight-nationalValue" + currentIndex);
			keywordsBox.setAttribute("class", "form-control");

			var violationsBox = document.createElement("input");
			violationsBox.setAttribute("name", "nr-shippingFeeWeight-metroValue" + currentIndex);
			violationsBox.setAttribute("class", "form-control");

			var addRowBox = document.createElement("input");
			addRowBox.setAttribute("type", "button");
			addRowBox.setAttribute("value", "+");
			addRowBox.setAttribute("onclick", "addField2();");
			addRowBox.setAttribute("class", "button btn btn-primary");

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox1);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox2);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox3);

			currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(keywordsBox);

			currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(violationsBox);

			currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(addRowBox);
		}
		function addField3(argument) {
			var myTable = document.getElementById("myTable3");
			var currentIndex = myTable.rows.length;
			var currentRow = myTable.insertRow(-1);

			var linksBox = document.createElement("select");
			linksBox.setAttribute("name", "nr-shippingFeeWeight-criteria" + currentIndex);
			linksBox.setAttribute("option", "");
			linksBox.setAttribute("value", "");

			linksBox.setAttribute("type", "button");
			linksBox.setAttribute("class", "form-control");

			theOption = document.createElement("OPTION");
			theText = document.createTextNode("Upto");
			theOption.setAttribute("value", "Upto");
			theOption.appendChild(theText);
			linksBox.appendChild(theOption);

			theOption = document.createElement("OPTION");
			theText = document.createTextNode("Additional");
			theOption.setAttribute("value", "Additional");
			theOption.appendChild(theText);
			linksBox.appendChild(theOption);

			var linksBox1 = document.createElement("input");
			linksBox1.setAttribute("name", "nr-shippingFeeWeight-range" + currentIndex);
			linksBox1.setAttribute("class", "form-control");

			var linksBox2 = document.createElement("input");
			linksBox2.setAttribute("name", "nr-shippingFeeWeight-value" + currentIndex);
			linksBox2.setAttribute("class", "form-control");

			var addRowBox = document.createElement("input");
			addRowBox.setAttribute("type", "button");
			addRowBox.setAttribute("value", "+");
			addRowBox.setAttribute("onclick", "addField3();");
			addRowBox.setAttribute("class", "button btn btn-primary");

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox1);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox2);

			currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(addRowBox);
		}
		function addField4(argument) {
			var myTable = document.getElementById("myTable4");
			var currentIndex = myTable.rows.length;
			var currentRow = myTable.insertRow(-1);

			var linksBox = document.createElement("select");
			linksBox.setAttribute("name", "nr-shippingFeeVolume-criteria" + currentIndex);
			linksBox.setAttribute("option", "");
			linksBox.setAttribute("value", "");
			linksBox.setAttribute("type", "button");
			linksBox.setAttribute("class", "form-control");

			theOption = document.createElement("OPTION");
			theText = document.createTextNode("Upto");
			theOption.setAttribute("value", "Upto");
			theOption.appendChild(theText);
			linksBox.appendChild(theOption);

			theOption = document.createElement("OPTION");
			theText = document.createTextNode("Additional");
			theOption.setAttribute("value", "Additional");
			theOption.appendChild(theText);
			linksBox.appendChild(theOption);

			var linksBox1 = document.createElement("input");
			linksBox1.setAttribute("name", "nr-shippingFeeVolume-range" + currentIndex);
			linksBox1.setAttribute("class", "form-control");

			var linksBox2 = document.createElement("input");
			linksBox2.setAttribute("name", "nr-shippingFeeVolume-value" + currentIndex);
			linksBox2.setAttribute("class", "form-control");

			var addRowBox = document.createElement("input");
			addRowBox.setAttribute("type", "button");
			addRowBox.setAttribute("value", "+");
			addRowBox.setAttribute("onclick", "addField4();");
			addRowBox.setAttribute("class", "button btn btn-primary");

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox1);

			var currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(linksBox2);

			currentCell = currentRow.insertCell(-1);
			currentCell.appendChild(addRowBox);
		}
	</script>


	<script>
		var app = angular.module('myApp', []);
		app.controller('myCtrl', function($scope) {
			$scope.firstname = "";
		});
	</script>

	<script language=JavaScript>
		function additional(x) {
			var v = $("#txt_name" + x + "").val();
			$("#txt_name" + (x + 1) + "").val(v);
			document.getElementById("myBtn").disabled = true;

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

							$("#nr-switch").change(function() {
								if (this.checked) {

									$('.radio5').hide();
									$("#nr-switch-sec").slideDown();
								} else {
									$("#nr-switch-sec").slideUp();

								}
							});

							$(".commissionType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".shippingFeeType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".retCharSFType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".retCharBRType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".RTOCharSFType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".RTOCharBRType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".repCharSFType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".repCharBRType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".PDCharSFType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".PDCharBRType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".canCharSFBFRTDType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".canCharSFARTDType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".canCharBRType").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".weight").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});

							$(".pccValue").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".pccHigher").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});

							$("[name=paymentType]").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).val()).slideDown();
							});
							/* $('#paymentField').change(function() {
								$('.payment-box').hide();
								$('#' + $(this).val()).fadeIn();
							}); */
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

							if ('${partner.tdsApplicable}' == 'true')
								$("#tdsApplicable").prop("checked", true);

							$("#submitButton").click(function() {
								submitForm();
							});
							if ('${partner.nrnReturnConfig.nrCalculator}' == 'true')
								$('input.js-switch_2').click();

							if ('${partner.nrnReturnConfig.commissionType}' == 'fixed')
								$("#commisionType-fixed").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.commissionType}' == 'categoryWise')
								$("#commisionType-categoryWise").prop(
										"checked", true).trigger("click");

							if ('${partner.nrnReturnConfig.shippingFeeType}' == 'fixed')
								$("#shippingfee-fixed").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.shippingFeeType}' == 'variable')
								$("#shippingfee-variable")
										.prop("checked", true).trigger("click");
							if ('${partner.nrnReturnConfig.retCharSFType}' == 'fixed') {
								$("#retrun-sf-fix").prop("checked", true)
										.trigger("click");
							} else if ('${partner.nrnReturnConfig.retCharSFType}' == 'variable') {
								$("#retrun-sf-variable").prop("checked", true)
										.trigger("click");
							} else if ('${partner.nrnReturnConfig.retCharSFType}' == 'noCharges')
								$("#retrun-sf-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.retCharBRType}' == 'fixed')
								$("#retrun-br-fix").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.retCharBRType}' == 'variable')
								$("#retrun-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.retCharBRType}' == 'noCharges')
								$("#retrun-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.RTOCharSFType}' == 'fixed')
								$("#RTO-sf-fix").prop("checked", true).trigger(
										"click");
							else if ('${partner.nrnReturnConfig.RTOCharSFType}' == 'variable')
								$("#RTO-sf-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.RTOCharSFType}' == 'noCharges')
								$("#RTO-sf-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.RTOCharBRType}' == 'fixed')
								$("#RTO-br-fix").prop("checked", true).trigger(
										"click");
							else if ('${partner.nrnReturnConfig.RTOCharBRType}' == 'variable')
								$("#RTO-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.RTOCharBRType}' == 'noCharges')
								$("#RTO-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.repCharSFType}' == 'fixed')
								$("#rep-sf-fix").prop("checked", true).trigger(
										"click");
							else if ('${partner.nrnReturnConfig.repCharSFType}' == 'variable')
								$("#rep-sf-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.repCharSFType}' == 'noCharges')
								$("#rep-sf-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.repCharBRType}' == 'fixed')
								$("#rep-br-fix").prop("checked", true).trigger(
										"click");
							else if ('${partner.nrnReturnConfig.repCharBRType}' == 'variable')
								$("#rep-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.repCharBRType}' == 'noCharges')
								$("#rep-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.PDCharSFType}' == 'fixed')
								$("#PD-sf-fix").prop("checked", true).trigger(
										"click");
							else if ('${partner.nrnReturnConfig.PDCharSFType}' == 'variable')
								$("#PD-sf-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.PDCharSFType}' == 'noCharges')
								$("#PD-sf-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.PDCharBRType}' == 'fixed')
								$("#PD-br-fix").prop("checked", true).trigger(
										"click");
							else if ('${partner.nrnReturnConfig.PDCharBRType}' == 'variable')
								$("#PD-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.PDCharBRType}' == 'noCharges')
								$("#PD-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.canCharSFBFRTDType}' == 'fixed')
								$("#can-sfbfrtd-fix").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.canCharSFBFRTDType}' == 'variable')
								$("#can-sfbfrtd-variable")
										.prop("checked", true).trigger("click");
							else if ('${partner.nrnReturnConfig.canCharSFBFRTDType}' == 'noCharges')
								$("#can-sfbfrtd-nocharges").prop("checked",
										true).trigger("click");

							if ('${partner.nrnReturnConfig.canCharSFARTDType}' == 'fixed')
								$("#can-sfartd-fix").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.canCharSFARTDType}' == 'variable')
								$("#can-sfartd-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.canCharSFARTDType}' == 'noCharges')
								$("#can-sfartd-nocharges")
										.prop("checked", true).trigger("click");

							if ('${partner.nrnReturnConfig.canCharBRType}' == 'fixed')
								$("#can-br-fix").prop("checked", true).trigger(
										"click");
							else if ('${partner.nrnReturnConfig.canCharBRType}' == 'variable')
								$("#can-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${partner.nrnReturnConfig.canCharBRType}' == 'noCharges')
								$("#can-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${partner.nrnReturnConfig.whicheverGreaterPCC}' == 'true') {
								$('#whicheverGreaterPCC').iCheck('check');
							}
							if ('${partner.nrnReturnConfig.retCharSFRevShipFee}' == 'true')
								$('#retCharSFRevShipFee').iCheck('check');
							if ('${partner.nrnReturnConfig.retCharSFShipFee}' == 'true')
								$('#retCharSFShipFee').iCheck('check');
							if ('${partner.nrnReturnConfig.retCharBRFF}' == 'true')
								$('#retCharBRFF').iCheck('check');

							if ('${partner.nrnReturnConfig.retCharBRShipFee}' == 'true')
								$('#retCharBRShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.RTOCharSFRevShipFee}' == 'true')
								$('#RTOCharSFRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.RTOCharSFFF}' == 'true')
								$('#RTOCharSFFF').iCheck('check');

							if ('${partner.nrnReturnConfig.RTOCharSFShipFee}' == 'true')
								$('#RTOCharSFShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.RTOCharSFRevShipFee}' == 'true')
								$('#RTOCharSFRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.RTOCharBRFF}' == 'true')
								$('#RTOCharBRFF').iCheck('check');

							if ('${partner.nrnReturnConfig.RTOCharBRShipFee}' == 'true')
								$('#RTOCharBRShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.repCharSFRevShipFee}' == 'true')
								$('#repCharSFRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.repCharSFFF}' == 'true')
								$('#repCharSFFF').iCheck('check');

							if ('${partner.nrnReturnConfig.repCharSFShipFee}' == 'true')
								$('#repCharSFShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.repCharSFRevShipFee}' == 'true')
								$('#repCharSFRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.repCharBRFF}' == 'true')
								$('#repCharBRFF').iCheck('check');

							if ('${partner.nrnReturnConfig.repCharBRShipFee}' == 'true')
								$('#repCharBRShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.PDCharSFRevShipFee}' == 'true')
								$('#PDCharSFRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.PDCharSFFF}' == 'true')
								$('#PDCharSFFF').iCheck('check');

							if ('${partner.nrnReturnConfig.PDCharSFShipFee}' == 'true')
								$('#PDCharSFShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.PDCharSFRevShipFee}' == 'true')
								$('#PDCharSFRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.PDCharBRFF}' == 'true')
								$('#PDCharBRFF').iCheck('check');

							if ('${partner.nrnReturnConfig.PDCharBRShipFee}' == 'true')
								$('#PDCharBRShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharSFBRTDRevShipFee}' == 'true')
								$('#canCharSFBRTDRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharSFBRTDFF}' == 'true')
								$('#canCharSFBRTDFF').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharSFBRTDShipFee}' == 'true')
								$('#canCharSFBRTDShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharSFBRTDRevShipFee}' == 'true')
								$('#canCharSFBRTDRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharSFARTDRevShipFee}' == 'true')
								$('#canCharSFARTDRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharSFFF}' == 'true')
								$('#canCharSFFF').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharSFShipFee}' == 'true')
								$('#canCharSFShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharSFARTDRevShipFee}' == 'true')
								$('#canCharSFARTDRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharBRFF}' == 'true')
								$('#canCharBRFF').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharBRFF}' == 'true')
								$('#canCharBRFF').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharBRShipFee}' == 'true')
								$('#canCharBRShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.canCharBRRevShipFee}' == 'true')
								$('#canCharBRRevShipFee').iCheck('check');

							if ('${partner.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeVar')
								$('#revShippingFeeType_revShipFeeVar').iCheck(
										'check');
							if ('${partner.nrnReturnConfig.retCharSFFF}' == 'true')
								$('#retCharSFFF').iCheck('check');

							else if ('${partner.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeShipFee')
								$('#revShippingFeeType_revShipFeeShipFee')
										.iCheck('check');
							else if ('${partner.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeFF')
								$('#revShippingFeeType_revShipFeeFF').iCheck(
										'check');
							else if ('${partner.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeGRT')
								$('#revShippingFeeType_revShipFeeGRT').iCheck(
										'check');
							else if ('${partner.nrnReturnConfig.revShippingFeeType}' == 'revShipFeePCC')
								$('#revShippingFeeType_revShipFeePCC').iCheck(
										'check');

							$('#whicheverGreaterPCC').on(
									"ifChecked",
									function() {
										if ($(this).is(":checked")) {
											$('#ispercentSPPCC')
													.iCheck('check');
											$('#isfixedAmountPCC').iCheck(
													'check');
										}
									});
							/*  $("#shippingfee-variable").click(function() {

								    alert($('#pickListResult :selected').text());

								   }); */

							/*    $(".validateNumber").rules("add", { 
										number : true
									}); */

							var val = {
								01 : {
									id : 01,
									text : 'Andhra Pradesh'
								},
								02 : {
									id : 02,
									text : 'Andaman and Nicobar Islands'
								},
								03 : {
									id : 03,
									text : 'Arunachal Pradesh'
								},
								04 : {
									id : 04,
									text : 'Assam'
								},
								05 : {
									id : 05,
									text : 'Chhattisgarh'
								},
								06 : {
									id : 06,
									text : 'Chandigarh'
								},
								07 : {
									id : 07,
									text : 'Dadra and Nagar Haveli'
								},
								08 : {
									id : 08,
									text : 'Daman and Diu'
								},
								09 : {
									id : 09,
									text : 'Delhi'
								},
								10 : {
									id : 10,
									text : 'Goa'
								},
								11 : {
									id : 11,
									text : 'Gujarat'
								},
								12 : {
									id : 12,
									text : 'Haryana'
								},
								13 : {
									id : 13,
									text : 'Himachal Pradesh'
								},
								14 : {
									id : 14,
									text : 'Jammu and Kashmir'
								},
								15 : {
									id : 15,
									text : 'Jharkhand'
								},
								16 : {
									id : 16,
									text : 'Karnataka'
								},
								17 : {
									id : 17,
									text : 'Kerala'
								},
								18 : {
									id : 18,
									text : 'Lakshadweep'
								},
								19 : {
									id : 19,
									text : 'Madhya Pradesh'
								},
								20 : {
									id : 20,
									text : 'Maharashtra'
								},
								21 : {
									id : 21,
									text : 'Manipur'
								},
								22 : {
									id : 22,
									text : 'Meghalaya'
								},
								23 : {
									id : 23,
									text : 'Mizoram'
								},
								24 : {
									id : 24,
									text : 'Nagaland'
								},
								25 : {
									id : 25,
									text : 'Odisha'
								},
								26 : {
									id : 26,
									text : 'Punjab'
								},
								27 : {
									id : 27,
									text : 'Pondicherry'
								},
								28 : {
									id : 28,
									text : 'Rajasthan'
								},
								29 : {
									id : 29,
									text : 'Sikkim'
								},
								30 : {
									id : 30,
									text : 'Tamil Nadu'
								},
								31 : {
									id : 31,
									text : 'Telangana'
								},
								32 : {
									id : 32,
									text : 'Tripura'
								},
								33 : {
									id : 33,
									text : 'Uttar Pradesh'
								},
								34 : {
									id : 34,
									text : 'Uttarakhand'
								},
								35 : {
									id : 35,
									text : 'West Bengal'
								}
							};

							var pick = $("#pickList").pickList({
								data : val
							});

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
