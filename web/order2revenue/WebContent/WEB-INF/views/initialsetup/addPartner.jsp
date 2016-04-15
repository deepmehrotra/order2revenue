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
									</c:if>

									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label">Partner Name</label>

											<div class="col-sm-8">
												<form:input path="pcName" value="${partner.pcName}"
													class="form-control" id="partnerName"
													onblur="checkOnBlur()" />
												<span id="partnerNameMessage"
													style="font-weight: bold;color=red"></span>
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
											</div>

										</div>


										<div class="hr-line-dashed"></div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label><form:radiobutton path="paymentType"
													value="paymentcycle" id="paymentcycle" name="toggler" />Payment
												Cycle</label>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="radio">
											<label><form:radiobutton path="paymentType"
													value="datewisepay" id="datewisepay" name="toggler" />Payment
												From Delivery</label>
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
											class="js-switch_2" id="nr-switch" />
										<label>NR Switch</label>
										<div class="col-sm-12 radio5" id="nr-switch-sec">
											<!--<div class="col-sm-12 radio1" id="blk-200">-->
											<h3>NR Calculator</h3>
											<div class="panel-body">
												<div class="panel-group" id="accordion">
													<div class="panel panel-default">
														<div class="panel-heading">
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
																				<input type="text" class="form-control"
																					name="nr-fixedCommissionPercent"> <span
																					class="input-group-addon">%</span>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12 radio1"
																	id="blk-commisionType-categoryWise">
																	<c:if test="${!empty categoryList}">
																		<c:forEach items="${categoryList}" var="category"
																			varStatus="loop">
																			<div class="form-group col-md-12">
																				<label class="col-md-4 control-label">${category}</label>
																				<div class="input-group m-b col-md-4">
																					<input type="text" class="form-control"
																						name='nr-${category}'>
																					<!--   <span class="input-group-addon">%</span> -->
																				</div>
																			</div>
																		</c:forEach>
																	</c:if>
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
																<div class="col-sm-4"
																	style="border-right: 1px dotted #ccc;">
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label content-rgt">&lt;250</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeelt250">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;250&&&lt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt250lt500">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500">
																		</div>
																	</div>
																</div>
																<div class="col-sm-4"
																	style="border-right: 1px dotted #ccc;">
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label content-rgt">&lt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeelt500Big">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;500&&&lt;1000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500lt1000">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;1000&&&lt;10000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt1000lt10000">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;10000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt10000">
																		</div>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label content-rgt">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeelt500">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500">
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
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<div class="checkbox i-checks">
																					<label> <form:checkbox
																							path="nrnReturnConfig.whicheverGreaterPCC"
																							id="whicheverGreaterPCC" /> <i></i> Which Ever
																						Is Greater
																					</label>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<div class="checkbox i-checks">
																					<label> <input type="checkbox" value=""
																						name="nr-ispercentSPPCC"> <i></i>
																						Percentage of SP
																					</label>
																				</div>
																			</div>
																			<div class="col-sm-6">
																				<div class="input-group m-b">
																					<input type="text" class="form-control"
																						name="nr-percentSPPCC"> <span
																						class="input-group-addon">%</span>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<div class="checkbox i-checks">
																					<label> <input type="checkbox" value=""
																						name="nr-isfixedAmountPCC"> <i></i> Fixed
																						Amount
																					</label>
																				</div>
																			</div>
																			<div class="col-sm-6">
																				<div class="input-group m-b">
																					<input type="text" class="form-control"
																						name="nr-fixedAmtPCC"> <span
																						class="input-group-addon">%</span>
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
																				<label> <!-- <input type="radio" value="6" id="optionsRadios1" name="toggler"> -->
																					<form:radiobutton
																						path="nrnReturnConfig.shippingFeeType"
																						value="variable" id="shippingfee-variable"
																						name="toggler" class="shippingFeeType" />
																					Variable Shipping Charges
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-6">
																			<div class="radio">
																				<label> <!--    <input type="radio" value="7" id="optionsRadios1" name="toggler"> -->
																					<form:radiobutton
																						path="nrnReturnConfig.shippingFeeType"
																						value="fixed" id="shippingfee-fixed"
																						class="shippingFeeType" /> Fixed Shipping Charges
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
																		<div class="col-sm-6">
																			<h4>Volume calculation= (lxbxh)(cm)/5000</h4>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Volume Weight Slab(gms)</th>
																						<th>Local</th>
																						<th>Zonal</th>
																						<th>National</th>
																						<th>Metro</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td><label>&lt; 500</label></td>
																						<td><div class="form-group ">
																								<div class=" content-rgt">
																									<input type="text" placeholder=""
																										class="form-control" name="nr-localvwlt500">
																								</div>
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonalvwlt500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationalvwlt500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrovwlt500">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>500 &gt; 1000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt500lt1000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt500lt1000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt500lt1000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt500lt1000">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1000 &gt; 1500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt1000lt1500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt1000lt1500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt1000lt1500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt1000lt1500">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1500 &gt; 5000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt1500lt5000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt1500lt5000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt1500lt5000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt1500lt5000">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>add 1 kg</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-localvwgt5000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonalvwgt5000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationalvwgt5000">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrovwgt5000">
																							</div></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																		<div class="col-sm-6">
																			<h4>Weight calculation</h4>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Dead Weight Slab(gms)</th>
																						<th>Local</th>
																						<th>Zonal</th>
																						<th>National</th>
																						<th>Metro</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td><label>&lt; 500</label></td>
																						<td><div class="form-group ">
																								<div class=" content-rgt">
																									<input type="text" placeholder=""
																										class="form-control" name="nr-localdwlt500">
																								</div>
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonaldwlt500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationaldwlt500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrodwlt500">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>additional &gt; 500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-localdwgt500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonaldwgt500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationaldwgt500">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrodwgt500">
																							</div></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-shippingfee-fixed">
																		<div
																			class="col-sm-12 center-align text-center font-bold">
																			<h4 class="text-info">Which Ever is Higher</h4>
																		</div>
																		<div class="col-sm-6">
																			<h4>Weight calculation</h4>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Dead Weight Slab(gms)</th>
																						<th>Price</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td><label>&lt; 500</label></td>
																						<td><div class="form-group ">
																								<div class=" content-rgt">
																									<input type="text" placeholder=""
																										class="form-control" name="nr-fixeddwlt500">
																								</div>
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>additional &gt; 500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-fixeddwgt500">
																							</div></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																		<div class="col-sm-6">
																			<h4>Volume calculation= (lxbxh)(cm)/5000</h4>
																			<table class="table table-bordered">
																				<thead>
																					<tr>
																						<th>Volume Weight Slab(gms)</th>
																						<th>Price</th>
																					</tr>
																				</thead>
																				<tbody>
																					<tr>
																						<td><label>&lt; 500</label></td>
																						<td><div class="form-group ">
																								<div class=" content-rgt">
																									<input type="text" placeholder=""
																										class="form-control" name="nr-fixedvwlt500">
																								</div>
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>500 &gt; 1000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt500lt1000">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1000 &gt; 1500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt1000lt1500">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1500 &gt; 5000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt1500lt5000">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>add 1 kg</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-fixedvwgt5000">
																							</div></td>
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
																		<div class="col-md-4 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" name="nr-serviceTax">
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
								</div>
								<div class="ibox float-e-margins">
									<div class="ibox-title">
										<h5>Return Calculator</h5>
									</div>
									<div class="ibox-content add-company">
										<form class="form-horizontal">
											<div class="panel-body">
												<div class="panel-group" id="accordion">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h5 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion"
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
																					<input type="text" class="form-control"
																						name="nr-retCharSFFixedAmt">
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharSFRevShipFee" /> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-retrun-sf-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharSFFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label><form:checkbox
																						path="nrnReturnConfig.retCharSFShipFee" /> <i></i>
																					Shipping Fee </label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharSFRevShipFee" /> <i></i>
																					Reverse Shipping Fee
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
																					<input type="text" class="form-control"
																						name="nr-retCharBRFixedAmt">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-retrun-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.retCharBRFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label><form:checkbox
																						path="nrnReturnConfig.retCharBRShipFee" /> <i></i>
																					Shipping Fee </label>
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
																					<input type="text" class="form-control"
																						name="nr-RTOCharSFFixedAmt">
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFRevShipFee" /> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-RTO-sf-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFShipFee" /> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharSFRevShipFee" /> <i></i>
																					Reverse Shipping Fee
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
																					<input type="text" class="form-control"
																						name="nr-RTOCharBRFixedAmt">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-RTO-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharBRFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.RTOCharBRShipFee" /> <i></i>
																					Shipping Fee
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
																<a data-toggle="collapse" data-parent="#accordion"
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
																					<input type="text" class="form-control"
																						name="nr-repCharSFFixedAmt">
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFRevShipFee" /> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-rep-sf-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFShipFee" /> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharSFRevShipFee" /> <i></i>
																					Reverse Shipping Fee
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
																					<input type="text" class="form-control"
																						name="nr-repCharBRFixedAmt">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-rep-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharBRFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.repCharBRShipFee" /> <i></i>
																					Shipping Fee
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
																<a data-toggle="collapse" data-parent="#accordion"
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
																					<input type="text" class="form-control"
																						name="nr-PDCharSFFixedAmt">
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFRevShipFee" /> <i></i>
																					Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-PD-sf-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFShipFee" /> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharSFRevShipFee" /> <i></i>
																					Reverse Shipping Fee
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
																					<input type="text" class="form-control"
																						name="nr-PDCharBRFixedAmt">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-PD-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharBRFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.PDCharBRShipFee" /> <i></i>
																					Shipping Fee
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
																<a data-toggle="collapse" data-parent="#accordion"
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
																					checked="checked" /> No
																				Charges
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
																					<input type="text" class="form-control"
																						name="nr-canCharSFBFRTDFixedAmt">
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDRevShipFee" />
																					<i></i> Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-can-sfbfrtd-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDShipFee" /> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFBRTDRevShipFee" />
																					<i></i> Reverse Shipping Fee
																				</label>
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
																					checked="checked" /> No
																				Charges
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
																					<input type="text" class="form-control"
																						name="nr-canCharSFFixedAmt">
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFARTDRevShipFee" />
																					<i></i> Reverse Shipping Fee
																				</label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-can-sfartd-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFShipFee" /> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharSFARTDRevShipFee" />
																					<i></i> Reverse Shipping Fee
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
																					id="can-br-fix" name="toggler"
																					class="canCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton
																					path="nrnReturnConfig.canCharBRType"
																					value="variable" id="can-br-variable"
																					name="toggler" class="canCharBRType" /> Variable
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
																					<input type="text" class="form-control"
																						name="nr-canCharBRFixedAmt">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-can-br-variable">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRVarFixedAmt">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRPercentSP">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRPercentPCC">
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharBRFF" /> <i></i>
																					Fixed Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-md-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharBRShipFee" /> <i></i>
																					Shipping Fee
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-12">
																			<div class="checkbox i-checks">
																				<label> <form:checkbox
																						path="nrnReturnConfig.canCharBRRevShipFee" /> <i></i>
																					Reverse Shipping Fee
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
																<a data-toggle="collapse" data-parent="#accordion"
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
																					value="revShipFeePCC" /> <i></i>( % of Shipping
																				Fee )
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="input-group m-b">
																			<input type="text" placeholder=""
																				class="form-control" name="nr-revShipFeePCC">
																			<span class="input-group-addon">%</span>
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
																					value="revShipFeeGRT" /> <i></i> Which Ever Is
																				Greater
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-12">
																		<div class="col-sm-6">
																			<div class="checkbox i-checks">
																				<label> Flat Amount </label>
																				<div id="sub">
																					<img src="/O2R/seller/img/about.png" alt="about"
																						style="float: right; position: relative; top: -31px; left: 18px;">
																				</div>
																				<div id="welcome" style="display: none;">
																					<p>Lorem ipsum dolor sit amet, consectetur quis</p>
																				</div>
																			</div>
																		</div>
																		<div class="col-sm-6">
																			<div class="input-group m-b">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeFlatAmt">
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
																					class="form-control" name="nr-revShipFeePCCMF">
																				<span class="input-group-addon">%</span>
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
																					value="revShipFeeFF" /> <i></i>Fix Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="input-group m-b" style="width: 96%;">
																			<input type="text" placeholder=""
																				class="form-control" name="nr-revShipFeeFF">
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
																					class="form-control" name="nr-revShipFeeDWAmt">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Amount</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWAmt">
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
																					class="form-control" name="nr-revShipFeeDWPW">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Per Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWPW">
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
																					class="form-control" name="nr-revShipFeeDWMW">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Min Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWMW">
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
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
						<div></div>
					</div>

					<div class="ibox float-e-margins">
						<div class="ibox-content add-company">
							<input class="btn btn-primary pull-right" id="submitButton"
								type="submit" value="Save">
						</div>
					</div>
				</form:form>
				<jsp:include page="../globalfooter.jsp"></jsp:include>
			</div>

		</div>
	</div>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>
	<!-- iCheck -->
	<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script>
	<!-- Switchery -->
	<script src="/O2R/seller/js/plugins/switchery/switchery.js"></script>
	<script src="/O2R/seller/js/pickList.js"></script>

	<script type="text/javascript">
		$(document).ready(
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
						$("#paymentcycle").prop("checked", true).trigger(
								"click");
					else if ('${partner.paymentType}' == 'datewisepay') {
						$("#datewisepay").prop("checked", true)
								.trigger("click");
						$('#paymentField1').trigger('change');
						if ('${partner.isshippeddatecalc}' != true) {
							$("#noofdaysfromdeliverydate").val(
									'${partner.noofdaysfromshippeddate}');
						}
					} else if ('${partner.paymentType}' == 'monthly')
						$('#monthly').prop("checked", true).trigger("click");

					if ('${partner.tdsApplicable}' == 'true')
						$("#tdsApplicable").prop("checked", true);

					$("#submitButton").click(function() {
						submitForm();
					});

					/* $("#getSelected").click(function () {
					 console.log(pick.getValues());
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
						nameAvailability = false;
						$("#partnerNameMessage").html(
								"Partner Name  already exist");
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
									pcName : "required",
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
									toggler : {
										required : true
									},
									startcycleday : {
										required : function(element) {
											return (getRole() == 'paymentcycle');
										},
										number : true,
										min : 1,
										max : 31,

									},
									paycycleduration : {
										required : function(element) {
											return (getRole() == 'paymentcycle');
										},
										number : true,
										min : 1,
										max : 31,

									},
									paydaysfromstartday : {
										required : function(element) {
											return (getRole() == 'paymentcycle');
										},
										number : true,
										min : 1,
										max : 31,

									},
									monthlypaydate : {
										required : function(element) {
											return (getRole() == 'monthly');
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
			if (validator.form() && nameAvailability) {
				alert("Calling form submit");// validation perform
				$('form#addpartnerform').submit();
			}
		}
		function getRole() {
			return $("#addpartnerform").find("input[type=radio]:checked").val();
		}

		//Code for state selection module
	</script>
</body>
</html>