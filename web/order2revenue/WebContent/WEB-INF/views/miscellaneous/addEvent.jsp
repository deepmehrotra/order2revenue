<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<link href="/O2R/seller/css/chosen.css" rel="stylesheet">
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
	var nameAvailability = false;

	function checkOnBlur() {
		var name = document.getElementById("eventName").value;
		$
				.ajax({
					url : "checkEvent.html?name=" + name,
					success : function(res) {
						if (res == "false") {
							nameAvailability = false;
							$("#eventNameMessageR")
									.html("Invalid Event Name !").show();
							$("#eventNameMessageG").html("Valid Event Name !")
									.hide();
						} else {
							nameAvailability = true;
							$("#eventNameMessageG").html("Valid Event Name !")
									.show();
							$("#eventNameMessageR")
									.html("Invalid Event Name !").hide();

						}

					}
				});
	}
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
								<h5>Events</h5>
								<!-- <div class="ibox-tools">
                                <a href="" class="btn btn-primary btn-xs" >Create New Events</a>
                            </div> -->
							</div>

							<form:form method="POST" action="saveEvent.html" role="form"
								class="form-horizontal" id="addEvent">
								<div class="ibox-content add-company">

									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label">Event Name</label>
											<form:input type="hidden" path="eventId" id="eventId"
												value="${eventsBean.eventId}" />
											<div class="col-sm-8">
												<c:choose>
													<c:when test="${eventsBean.eventId > 0}">
														<form:input path="eventName"
															value="${eventsBean.eventName}" class="form-control"
															id="eventName" readonly="true" />
													</c:when>
													<c:otherwise>
														<form:input path="eventName"
															value="${eventsBean.eventName}" class="form-control"
															id="eventName" onblur="checkOnBlur()" />
														<span id="eventNameMessageR"
															style="color: red; font-weight: bold"></span>
														<span id="eventNameMessageG"
															style="color: green; font-weight: bold"></span>
													</c:otherwise>
												</c:choose>


											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label">Select Channel</label>
											<div class="col-sm-8">
												<form:select path="channelName" items="${partnerMap}"
													id="channelName" class="form-control"></form:select>
											</div>
										</div>
									</div>
									<div class="col-lg-12">
										<h4>Event Period</h4>
									</div>
									<div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">Start Date</label>
												<div class="col-sm-8" id="data_1">
													<div class="input-group date">
														<span class="input-group-addon"><i
															class="fa fa-calendar"></i></span>
														<fmt:formatDate value="${eventsBean.startDate}"
															var="startDate" type="date" pattern="MM/dd/yyyy" />
														<form:input path="startDate" value="${startDate}"
															type="text" class="StartDate  form-control"
															id="StartDate"></form:input>
														<span id="startDateMessageG"
															style="color: green; font-weight: bold"></span> <span
															id="startDateMessageR"
															style="color: red; font-weight: bold"></span>
													</div>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<label class="col-sm-4 control-label">End Date</label>
												<div class="col-sm-8" id="data_1">
													<div class="input-group date">
														<span class="input-group-addon"><i
															class="fa fa-calendar"></i></span>
														<fmt:formatDate value="${eventsBean.endDate}"
															var="endDate" type="date" pattern="MM/dd/yyyy" />
														<form:input path="endDate" value="${endDate}" type="text"
															class="EndDate  form-control" id="EndDate"></form:input>
														<span id="endDateMessageR"
															style="color: red; font-weight: bold"></span> <span
															id="endDateMessageG"
															style="color: green; font-weight: bold"></span>
													</div>
												</div>
											</div>
										</div>

										<div class="col-sm-12">
											<h4>Product SKU</h4>
										</div>
											<div class="col-sm-6">												
												<label class="col-sm-4 control-label text-center" style="padding: 0px;margin-right: -6px;position: relative;right: 22px;"><b>Select All</b></label>
												<div class="col-sm-8">
													<div style="padding: 0px;">														
														<form:checkbox path="selectAll" id="selectAllSku" value="true" />
													</div>												
												</div>
											</div>										
											<div class="col-sm-6" id="multiSelect" >												
												<label class="col-sm-4 control-label" >Select SKU </label>
												<div class="col-sm-8">	                                       
			                                        <select data-placeholder="Click To Select" name="multiSku" id="multiSku" class="chosen-select" multiple="multiple" style="width:350px;" tabindex="4" required>
			                                            <c:choose>
															<c:when test="${!empty skuList}">
																<c:forEach items="${skuList}" var="sk"
																	varStatus="loop">
																	<option value="${sk}" selected>${sk}</option>
																</c:forEach>
																<c:if test="${!empty skus}">			                                            		
					                                            	<c:forEach items="${skus}" var="sku" varStatus="loop">
					                                            		<option value="${sku}">${sku}</option>
					                                            	</c:forEach>
					                                            </c:if>
															</c:when>
															<c:otherwise>
																<c:if test="${!empty skus}">			                                            		
					                                            	<c:forEach items="${skus}" var="sku" varStatus="loop">
					                                            		<option value="${sku}">${sku}</option>
					                                            	</c:forEach>
					                                            </c:if>
															</c:otherwise>
														</c:choose>                                        
			                                        </select>
		                                        </div>
		                                    </div>                                  		
                                   		<br>
                                   		
                                   		<div class="col-sm-12">
											<h4>NR Calculator</h4>
										</div>
										<div class="col-sm-12">
											<div class="col-sm-12">
												<div class="col-sm-4">
													<div class="radio">
														<label> <form:radiobutton name="nr"
																value="original" id="nrCalculatorEvent_original"
																path="nrnReturnConfig.nrCalculatorEvent"
																class="nrCalculatorEvent" /> Original
														</label>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="radio">
														<label> <form:radiobutton name="nr"
																value="variable" id="nrCalculatorEvent_variable"
																path="nrnReturnConfig.nrCalculatorEvent"
																class="nrCalculatorEvent" /> Variable NR
														</label>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="radio">
														<label> <form:radiobutton name="nr" value="fixed"
																id="nrCalculatorEvent_fixed"
																path="nrnReturnConfig.nrCalculatorEvent"
																class="nrCalculatorEvent" /> Fixed TP
														</label>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-12 radio2 m-b"
													id="blk-nrCalculatorEvent_variable" style="display: none;">

													<div class="panel-body">
														<div class="panel-group" id="accordion">
															<div class="panel panel-default">
																<div class="panel-heading">
																	<h5 class="panel-title">
																		<a data-toggle="collapse" data-parent="#accordion"
																			href="#collapseOne1">Commission</a>
																	</h5>
																</div>
																<div id="collapseOne1" class="panel-collapse collapse">
																	<div class="panel-body">
																		<div class="col-sm-12">
																			<div class="col-sm-6">
																				<div class="radio">
																					<label> <form:radiobutton
																							path="nrnReturnConfig.commissionType"
																							value="fixed" id="commisionType-fixed"
																							name="commissionType" class="commissionType" />
																						<!--       <input type="radio" value="4" id="optionsRadios1" name="toggler"> -->
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
																							name="commissionType" class="commissionType" />
																						<!--  <input type="radio" value="5" id="optionsRadios1" name="toggler"> -->
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
																							name="nr-fixedCommissionPercent"
																							value="${chargeMap.fixedCommissionPercent}"
																							id="fixedCommissionPercent"> <span
																							class="input-group-addon">%</span>
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
																								<input type="text" class="form-control"
																									name='nr-${cat.key}' value='${cat.value}'>
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
																						<c:choose>
																							<c:when test="${!empty partner.fixedfeeList}">
																								<c:forEach items="${partner.fixedfeeList}"
																									var="fixedfee" varStatus="loop">
																									<div class="col-md-3 content-rgt">
																										<select class="form-control"
																											name="nr-fixedfee${loop.index}-criteria">
																											<c:choose>
																												<c:when
																													test="${fixedfee.criteria eq 'Upto'}">
																													<option selected>Upto</option>
																													<option>Greater Than</option>
																												</c:when>
																												<c:otherwise>
																													<option>Upto</option>
																													<option selected>Greater Than</option>
																												</c:otherwise>
																											</c:choose>
																										</select>
																									</div>
																									<div class="col-md-3 content-rgt">
																										<input type="text"
																											class="form-control validateNumber"
																											name="nr-fixedfee${loop.index}-range"
																											id="nr-fixedfee${loop.index}-range"
																											value="${fixedfee.range}" />
																									</div>
																									<div class="col-md-3 content-rgt">
																										<input type="text"
																											class="form-control validateNumber"
																											name="nr-fixedfee${loop.index}-value"
																											id="nr-fixedfee${loop.index}-value"
																											value="${fixedfee.value}" />
																									</div>
																									<div class="col-md-3 content-rgt">
																										<c:choose>
																											<c:when test="${fixedfee.criteria eq 'Upto'}">
																												<input type="button" onclick="myFunction();"
																													value="+" class="btn btn-primary"
																													id="myBtn" />
																											</c:when>
																											<c:otherwise>
																												<input type="button" onclick="myFunction();"
																													value="+" class="btn btn-primary"
																													id="myBtn" disabled="disabled" />
																											</c:otherwise>
																										</c:choose>
																									</div>
																								</c:forEach>
																							</c:when>
																							<c:otherwise>

																								<div class="col-md-3 content-rgt">
																									<select class="form-control"
																										name="nr-fixedfee0-criteria">
																										<option>Upto</option>
																										<option>Greater Than</option>
																									</select>
																								</div>
																								<div class="col-md-3 content-rgt">
																									<input type="text"
																										class="form-control validateNumber"
																										name="nr-fixedfee0-range"
																										id="nr-fixedfee0-range" />
																								</div>
																								<div class="col-md-3 content-rgt">
																									<input type="text"
																										class="form-control validateNumber"
																										name="nr-fixedfee0-value"
																										id="nr-fixedfee0-value" />
																								</div>
																								<div class="col-md-3 content-rgt">
																									<input type="button" onclick="myFunction();"
																										value="+" class="btn btn-primary" id="myBtn" />
																								</div>
																							</c:otherwise>
																						</c:choose>
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
																						<input type="text" name="nr-pccrange"
																							class="form-control validateNumber"																							
																							value="${chargeMap.pccrange}" id="text01">
																					</div>
																					<div class="col-md-5">
																						<input type="text" name="nr-pccvalue"
																							class="form-control validateNumber"
																							value="${chargeMap.pccvalue}">
																					</div>
																					<br> <br> <br>
																					<div class="col-md-2">
																						<label>Greater than</label>
																					</div>
																					<div class="col-md-5" style="padding: 0px;">
																						<label id="label01"> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ${chargeMap.pccrange}</label>
																					</div>

																					<div class="col-md-5">
																						<div class="col-md-6" style="padding: 0px;">
																							<input type="text" name="nr-pccpercentSPValue"
																								class="form-control validateNumber"
																								value="${chargeMap.pccpercentSPValue}">
																						</div>
																						<div class="col-md-6"
																							style="margin-top: 3px; padding: 0px 0px 0px 5px;">
																							<span class="input-group-addon"
																								style="border: 1px solid #ebdfdf;">% Of
																								SP</span>
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
																							<input type="text" name="nr-pccpercentSPHigher"
																								class="form-control validateNumber"
																								value="${chargeMap.pccpercentSPHigher}">
																							<span class="input-group-addon">%</span>
																						</div>
																					</div>
																				</div>
																				<div class="col-sm-12">
																					<div class="col-sm-6">
																						<label> Fixed Amount </label>
																					</div>
																					<div class="col-sm-6">
																						<div class="input-group m-b">
																							<input type="text" name="nr-pccfixedAmt"
																								class="form-control validateNumber"
																								value="${chargeMap.pccfixedAmt}">
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
																						<c:choose>
																							<c:when
																								test="${!empty partner.shippingfeeVolumeVariableList}">
																								<c:forEach
																									items="${partner.shippingfeeVolumeVariableList}"
																									var="shippingfee" varStatus="loop">
																									<tr>
																										<td><select class="form-control selected"
																											name="nr-shippingfeeVolumeVariable${loop.index}-criteria">
																												<c:choose>
																													<c:when
																														test="${shippingfee.criteria eq 'Upto'}">
																														<option value="Upto" selected>Upto</option>
																														<option value="Additional">Additional</option>
																													</c:when>
																													<c:otherwise>
																														<option value="Upto">Upto</option>
																														<option value="Additional" selected>Additional</option>
																													</c:otherwise>
																												</c:choose>
																										</select></td>
																										<td><div class="form-group ">
																												<div class=" content-rgt">
																													<input type="text"
																														name="nr-shippingfeeVolumeVariable${loop.index}-range"
																														class="form-control validateNumber"
																														value="${shippingfee.range}">
																												</div>
																											</div></td>
																										<td><div class="form-group ">
																												<div class=" content-rgt">
																													<input type="text"
																														name="nr-shippingfeeVolumeVariable${loop.index}-localValue"
																														class="form-control validateNumber"
																														value="${shippingfee.localValue}">
																												</div>
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeVolumeVariable${loop.index}-zonalValue"
																													class="form-control validateNumber"
																													value="${shippingfee.zonalValue}">
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeVolumeVariable${loop.index}-nationalValue"
																													class="form-control validateNumber"
																													value="${shippingfee.nationalValue}">
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeVolumeVariable${loop.index}-metroValue"
																													class="form-control validateNumber"
																													value="${shippingfee.metroValue}">
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="button"
																													class="button btn btn-primary" value="+"
																													onclick="addField()">
																											</div></td>
																									</tr>
																								</c:forEach>
																							</c:when>
																							<c:otherwise>
																								<tr>
																									<td><select class="form-control selected"
																										name="nr-shippingfeeVolumeVariable0-criteria">
																											<option value="Upto">Upto</option>
																											<option value="Additional">Additional</option>
																									</select></td>
																									<td><div class="form-group ">
																											<div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeVolumeVariable0-range"
																													class="form-control validateNumber">
																											</div>
																										</div></td>
																									<td><div class="form-group ">
																											<div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeVolumeVariable0-localValue"
																													class="form-control validateNumber">
																											</div>
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="text"
																												name="nr-shippingfeeVolumeVariable0-zonalValue"
																												class="form-control validateNumber">
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="text"
																												name="nr-shippingfeeVolumeVariable0-nationalValue"
																												class="form-control validateNumber">
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="text"
																												name="nr-shippingfeeVolumeVariable0-metroValue"
																												class="form-control validateNumber">
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="button"
																												class="button btn btn-primary" value="+"
																												onclick="addField()">
																										</div></td>
																								</tr>
																							</c:otherwise>
																						</c:choose>
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
																						<c:choose>
																							<c:when
																								test="${!empty partner.shippingfeeWeightVariableList}">
																								<c:forEach
																									items="${partner.shippingfeeWeightVariableList}"
																									var="shippingfee" varStatus="loop">
																									<tr>
																										<td><select class="form-control selected"
																											name="nr-shippingfeeWeightVariable${loop.index}-criteria">
																												<c:choose>
																													<c:when
																														test="${shippingfee.criteria eq 'Upto'}">
																														<option value="Upto" selected>Upto</option>
																														<option value="Additional">Additional</option>
																													</c:when>
																													<c:otherwise>
																														<option value="Upto">Upto</option>
																														<option value="Additional" selected>Additional</option>
																													</c:otherwise>
																												</c:choose>
																										</select></td>
																										<td><div class="form-group ">
																												<div class=" content-rgt">
																													<input type="text"
																														name="nr-shippingfeeWeightVariable${loop.index}-range"
																														class="form-control validateNumber"
																														value="${shippingfee.range}">
																												</div>
																											</div></td>
																										<td><div class="form-group ">
																												<div class=" content-rgt">
																													<input type="text"
																														name="nr-shippingfeeWeightVariable${loop.index}-localValue"
																														class="form-control validateNumber"
																														value="${shippingfee.localValue}">
																												</div>
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeWeightVariable${loop.index}-zonalValue"
																													class="form-control validateNumber"
																													value="${shippingfee.zonalValue}">
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeWeightVariable${loop.index}-nationalValue"
																													class="form-control"
																													value="${shippingfee.nationalValue}">
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeWeightVariable${loop.index}-metroValue"
																													class="form-control validateNumber"
																													value="${shippingfee.metroValue}">
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="button"
																													class="button btn btn-primary" value="+"
																													onclick="addField2()">
																											</div></td>
																									</tr>
																								</c:forEach>
																							</c:when>
																							<c:otherwise>
																								<tr>
																									<td><select class="form-control selected"
																										name="nr-shippingfeeWeightVariable0-criteria">
																											<option value="Upto">Upto</option>
																											<option value="Additional">Additional</option>
																									</select></td>
																									<td><div class="form-group ">
																											<div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeWeightVariable0-range"
																													class="form-control validateNumber">
																											</div>
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="text"
																												name="nr-shippingfeeWeightVariable0-localValue"
																												class="form-control validateNumber">
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="text"
																												name="nr-shippingfeeWeightVariable0-zonalValue"
																												class="form-control validateNumber">
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="text"
																												name="nr-shippingfeeWeightVariable0-nationalValue"
																												class="form-control validateNumber">
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="text"
																												name="nr-shippingfeeWeightVariable0-metroValue"
																												class="form-control validateNumber">
																										</div></td>
																									<td><div class=" content-rgt">
																											<input type="button"
																												class="button btn btn-primary" value="+"
																												onclick="addField2()">
																										</div></td>
																								</tr>
																							</c:otherwise>
																						</c:choose>
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
																						<c:choose>
																							<c:when
																								test="${!empty partner.shippingfeeWeightFixedList}">
																								<c:forEach
																									items="${partner.shippingfeeWeightFixedList}"
																									var="shippingfee" varStatus="loop">
																									<tr>
																										<td><select class="form-control selected"
																											name="nr-shippingfeeWeightFixed${loop.index}-criteria">
																												<c:choose>
																													<c:when
																														test="${shippingfee.criteria eq 'Upto'}">
																														<option value="Upto" selected>Upto</option>
																														<option value="Additional">Additional</option>
																													</c:when>
																													<c:otherwise>
																														<option value="Upto">Upto</option>
																														<option value="Additional" selected>Additional</option>
																													</c:otherwise>
																												</c:choose>
																										</select></td>
																										<td><div class="form-group ">
																												<div class=" content-rgt">
																													<input type="text"
																														name="nr-shippingfeeWeightFixed${loop.index}-range"
																														class="form-control validateNumber"
																														value="${shippingfee.range}">
																												</div>
																											</div></td>
																										<td><div class="form-group ">
																												<div class=" content-rgt">
																													<input type="text"
																														name="nr-shippingfeeWeightFixed${loop.index}-value"
																														class="form-control validateNumber"
																														value="${shippingfee.value}">
																												</div>
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="button"
																													class="button btn btn-primary" value="+"
																													onclick="addField3()">
																											</div></td>
																									</tr>
																								</c:forEach>
																							</c:when>
																							<c:otherwise>
																								<tr>
																									<td><select class="form-control selected"
																										name="nr-shippingfeeWeightFixed0-criteria">
																											<option value="Upto">Upto</option>
																											<option value="Additional">Additional</option>
																									</select></td>
																									<td>
																										<div class="form-group ">
																											<div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeWeightFixed0-range"
																													class="form-control validateNumber">
																											</div>
																										</div>
																									</td>
																									<td>
																										<div class="form-group ">
																											<div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeWeightFixed0-value"
																													class="form-control validateNumber">
																											</div>
																										</div>
																									</td>
																									<td><input type="button" name=""
																										class="button btn btn-primary"
																										onclick="addField3()" value="+"></td>
																								</tr>
																							</c:otherwise>
																						</c:choose>
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
																						<c:choose>
																							<c:when
																								test="${!empty partner.shippingfeeVolumeFixedList}">
																								<c:forEach
																									items="${partner.shippingfeeVolumeFixedList}"
																									var="shippingfee" varStatus="loop">
																									<tr>
																										<td><select class="form-control selected"
																											name="nr-shippingfeeVolumeFixed${loop.index}-criteria">
																												<c:choose>
																													<c:when
																														test="${shippingfee.criteria eq 'Upto'}">
																														<option value="Upto" selected>Upto</option>
																														<option value="Additional">Additional</option>
																													</c:when>
																													<c:otherwise>
																														<option value="Upto">Upto</option>
																														<option value="Additional" selected>Additional</option>
																													</c:otherwise>
																												</c:choose>
																										</select></td>
																										<td><div class="form-group ">
																												<div class=" content-rgt">
																													<input type="text"
																														name="nr-shippingfeeVolumeFixed${loop.index}-range"
																														class="form-control validateNumber"
																														value="${shippingfee.range}">
																												</div>
																											</div></td>
																										<td><div class="form-group ">
																												<div class=" content-rgt">
																													<input type="text"
																														name="nr-shippingfeeVolumeFixed${loop.index}-value"
																														class="form-control validateNumber"
																														value="${shippingfee.value}">
																												</div>
																											</div></td>
																										<td><div class=" content-rgt">
																												<input type="button"
																													class="button btn btn-primary" value="+"
																													onclick="addField4()">
																											</div></td>
																									</tr>
																								</c:forEach>
																							</c:when>
																							<c:otherwise>
																								<tr>
																									<td><select class="form-control selected"
																										name="nr-shippingfeeVolumeFixed0-criteria">
																											<option value="Upto">Upto</option>
																											<option value="Additional">Additional</option>
																									</select></td>
																									<td><input type="text"
																										name="nr-shippingfeeVolumeFixed0-range"
																										class="form-control validateNumber"></td>
																									<td>
																										<div class="form-group ">
																											<div class=" content-rgt">
																												<input type="text"
																													name="nr-shippingfeeVolumeFixed0-value"
																													class="form-control validateNumber">
																											</div>
																										</div>
																									</td>
																									<td><input type="button" name=""
																										class="button btn btn-primary" value="+"
																										onclick="addField4()"></td>
																								</tr>
																							</c:otherwise>
																						</c:choose>
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
																					name="nr-serviceTax"
																					value="${chargeMap.serviceTax}" id="serviceTax" />
																				<span class="input-group-addon">%</span>
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

									<div class="col-sm-12 " style="margin-top: 10px;">
										<h4>Return Calculator/Charges</h4>
									</div>
									<div class="col-sm-12" style="margin-bottom: 14px;">
										<div class="col-sm-12">
											<div class="col-sm-6">
												<div class="radio">
													<label> <form:radiobutton value="original"
															id="returnCalculatorEvent_original"
															path="nrnReturnConfig.returnCalculatorEvent"
															class="returnCalculatorEvent" /> Original Terms
													</label>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="radio">
													<label> <form:radiobutton value="newTerms"
															id="returnCalculatorEvent_newTerms"
															path="nrnReturnConfig.returnCalculatorEvent"
															class="returnCalculatorEvent" /> New Terms
													</label>
												</div>
											</div>

										</div>
										<div class="row">

											<div class="col-sm-12 radio3"
												id="blk-returnCalculatorEvent_newTerms"
												style="display: none;">

												<div class="panel-body">
													<div class="panel-group" id="accordion1">
														<div class="panel panel-default">
															<div class="panel-heading">
																<h5 class="panel-title">
																	<a data-toggle="collapse" data-parent="#accordion1"
																		href="#collapseOne">Return Charges</a>
																</h5>
															</div>
															<div id="collapseOne" class="panel-collapse collapse">
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
																							value="${chargeMap.retCharSFPercentSP}">
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
																						<input type="text" class="form-control"
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
																							value="${chargeMap.RTOCharSFPercentSP}">
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
																							value="${chargeMap.RTOCharBRPercentSP}">
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
																						<input type="text" class="form-control"
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
																							value="${chargeMap.repCharSFPercentSP}">
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
																						value="variable" id="PD-sf-variable"
																						name="toggler" class="PDCharSFType" /> Variable
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
																							value="${chargeMap.PDCharSFPercentPCC}">
																						<span class="input-group-addon">%</span>
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
																						value="variable" id="PD-br-variable"
																						name="toggler" class="PDCharBRType" /> Variable
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
																							value="${chargeMap.PDCharBRPercentPCC}">
																						<span class="input-group-addon">%</span>
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
																						<input type="text" class="form-control"
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
																							id="canCharSFBRTDShipFee" /> <i></i> Shipping
																						Fee
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
																						<input type="text" class="form-control"
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
																							class="form-control"
																							name="nr-canCharSFVarFixedAmt"
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
																							value="${chargeMap.canCharSFPercentSP}">
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
																							name="nr-canCharSFPercentPCC"
																							value="${chargeMap.canCharSFPercentPCC}">
																						<span class="input-group-addon">%</span>
																					</div>
																				</div>
																			</div>
																			<div class="col-md-12">
																				<div class="checkbox i-checks">
																					<label> <form:checkbox
																							path="nrnReturnConfig.canCharSFFF"
																							id="canCharSFFF" /> <i></i> Fixed Fee
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
																							value="${chargeMap.canCharBRPercentSP}">
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
																							name="nr-canCharBRPercentPCC"
																							value="${chargeMap.canCharBRPercentPCC}">
																						<span class="input-group-addon">%</span>
																					</div>
																				</div>
																			</div>
																			<div class="col-md-12">
																				<div class="checkbox i-checks">
																					<label> <form:checkbox
																							path="nrnReturnConfig.canCharBRFF"
																							id="canCharBRFF" /> <i></i> Fixed Fee
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
																					class="form-control" name="nr-revShipFeePCC"
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
																					<div id="sub">
																						<img src="/O2R/seller/img/about.png" alt="about"
																							style="float: right; position: relative; top: -31px; left: 18px;">
																					</div>
																					<div id="welcome" style="display: none;">
																						<p>Lorem ipsum dolor sit amet, consectetur
																							quis</p>
																					</div>
																				</div>
																			</div>
																			<div class="col-sm-6">
																				<div class="input-group m-b">
																					<input type="text" placeholder=""
																						class="form-control" name="nr-revShipFeeFlatAmt"
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
																						class="form-control" name="nr-revShipFeePCCMF"
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
																					class="form-control" name="nr-revShipFeeFF"
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
																						class="form-control" name="nr-revShipFeeDWAmt"
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
																						class="form-control" name="nr-revShipFeeVWAmt"
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
																						class="form-control" name="nr-revShipFeeDWPW"
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
																						class="form-control" name="nr-revShipFeeVWPW"
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
																						class="form-control" name="nr-revShipFeeDWMW"
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
																						class="form-control" name="nr-revShipFeeVWMW"
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
												</div>

											</div>

										</div>
									</div>
									<div class="ibox-content add-company">
										<button class="btn btn-primary pull-right" type="submit"
											id="EveSubmit" onclick="submitOrder()">Save</button>
									</div>



								</div>
							</form:form>
						</div>
					</div>

				</div>

			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>

	<!-- Switchery -->
	<script src="/O2R/seller/js/plugins/switchery/switchery.js"></script>
	<script src="/O2R/seller/js/pickList.js"></script>

	<!-- Mainly scripts -->


	<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>




	<!-- <script src="/O2R/seller/js/jquery-2.1.1.js"></script> 
<script src="/O2R/seller/js/bootstrap.min.js"></script> 
<script src="/O2R/seller/js/plugins/metisMenu/jquery.metisMenu.js"></script> 
<script src="/O2R/seller/js/plugins/slimscroll/jquery.slimscroll.min.js"></script> 
Data picker 
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
Custom and plugin javascript 
<script src="/O2R/seller/js/inspinia.js"></script> 
<script src="/O2R/seller/js/plugins/pace/pace.min.js"></script>  -->
	<!-- iCheck -->
	<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script>
	<!-- Switchery -->

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
					+ "<select name='nr-fixedfee"
					+ index
					+ "-criteria' id=='nr-fixedfee"
					+ index
					+ "-criteria' onchange='(this.selectedIndex == 0) ? upto() : additional("
					+ (index - 1)
					+ ")' class='form-control'>"
					+ "<option>Upto</option>"
					+ "<option>Greater Than</option>"
					+ "</select>"
					+

					"</div>"
					+ "<div class='col-md-3 content-rgt'>"
					+ "<input type='text' name='nr-fixedfee"+index+"-range' placeholder='' class='form-control' id='txt_name"+index+"' multiple>"
					+ "</div>"
					+ "<div class='col-md-3 content-rgt'>"
					+ "<input type='text' placeholder='' name='nr-fixedfee"+index+"-value' id='nr-fixedfee"+index+"-value' class='form-control'>"
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
			linksBox.setAttribute("name", "nr-shippingfeeVolumeVariable"
					+ currentIndex + "-criteria");
			linksBox.setAttribute("option", "");
			linksBox.setAttribute("value", "");
			linksBox.setAttribute("path", "");

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
			linksBox1.setAttribute("name", "nr-shippingfeeVolumeVariable"
					+ currentIndex + "-range");
			linksBox1.setAttribute("class", "form-control validateNumber");

			var linksBox2 = document.createElement("input");
			linksBox2.setAttribute("name", "nr-shippingfeeVolumeVariable"
					+ currentIndex + "-localValue");
			linksBox2.setAttribute("class", "form-control validateNumber");

			var linksBox3 = document.createElement("input");
			linksBox3.setAttribute("name", "nr-shippingfeeVolumeVariable"
					+ currentIndex + "-zonalValue");
			linksBox3.setAttribute("class", "form-control validateNumber");

			var keywordsBox = document.createElement("input");
			keywordsBox.setAttribute("name", "nr-shippingfeeVolumeVariable"
					+ currentIndex + "-nationalValue");
			keywordsBox.setAttribute("class", "form-control validateNumber");

			var violationsBox = document.createElement("input");
			violationsBox.setAttribute("name", "nr-shippingfeeVolumeVariable"
					+ currentIndex + "-metroValue");
			violationsBox.setAttribute("class", "form-control validateNumber");

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
			linksBox.setAttribute("name", "nr-shippingfeeWeightVariable"
					+ currentIndex + "-criteria");
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
			linksBox1.setAttribute("name", "nr-shippingfeeWeightVariable"
					+ currentIndex + "-range");
			linksBox1.setAttribute("class", "form-control validateNumber");

			var linksBox2 = document.createElement("input");
			linksBox2.setAttribute("name", "nr-shippingfeeWeightVariable"
					+ currentIndex + "-localValue");
			linksBox2.setAttribute("class", "form-control validateNumber");

			var linksBox3 = document.createElement("input");
			linksBox3.setAttribute("name", "nr-shippingfeeWeightVariable"
					+ currentIndex + "-zonalValue");
			linksBox3.setAttribute("class", "form-control");

			var keywordsBox = document.createElement("input");
			keywordsBox.setAttribute("name", "nr-shippingfeeWeightVariable"
					+ currentIndex + "-nationalValue");
			keywordsBox.setAttribute("class", "form-control validateNumber");

			var violationsBox = document.createElement("input");
			violationsBox.setAttribute("name", "nr-shippingfeeWeightVariable"
					+ currentIndex + "-metroValue");
			violationsBox.setAttribute("class", "form-control validateNumber");

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
			linksBox.setAttribute("name", "nr-shippingfeeWeightFixed"
					+ currentIndex + "-criteria");
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
			linksBox1.setAttribute("name", "nr-shippingfeeWeightFixed"
					+ currentIndex + "-range");
			linksBox1.setAttribute("class", "form-control validateNumber");

			var linksBox2 = document.createElement("input");
			linksBox2.setAttribute("name", "nr-shippingfeeWeightFixed"
					+ currentIndex + "-value");
			linksBox2.setAttribute("class", "form-control validateNumber");

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
			linksBox.setAttribute("name", "nr-shippingfeeVolumeFixed"
					+ currentIndex + "-criteria");
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
			linksBox1.setAttribute("name", "nr-shippingfeeVolumeFixed"
					+ currentIndex + "-range");
			linksBox1.setAttribute("class", "form-control validateNumber");

			var linksBox2 = document.createElement("input");
			linksBox2.setAttribute("name", "nr-shippingfeeVolumeFixed"
					+ currentIndex + "-value");
			linksBox2.setAttribute("class", "form-control validateNumber");

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
		function upto() {
			document.getElementById("myBtn").disabled = false;
		}
	</script>	

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$("#EndDate")
									.change(
											function() {
												var startDate = document
														.getElementById("StartDate").value;
												var endDate = document
														.getElementById("EndDate").value;

												if ((Date.parse(startDate) > Date
														.parse(endDate))) {
													alert("End Date Should Be Greater Than Start Date !");
													document
															.getElementById("EndDate").value = "";
												}
											});

							/* Retrive Radio Buttons Starts */
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
							$(".pccValue").click(function() {
								$('.radio1').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".pccHigher").click(function() {
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
							$(".returnCalculatorEvent").click(function() {
								$('.radio3').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});
							$(".nrCalculatorEvent").click(function() {
								$('.radio2').hide();
								$("#blk-" + $(this).attr('id')).slideDown();
							});

							$('#data_1 .input-group.date').datepicker({
								todayBtn : "linked",
								keyboardNavigation : false,
								forceParse : false,
								calendarWeeks : true,
								autoclose : true
							});

							if ('${eventsBean.nrnReturnConfig.nrCalculatorEvent}' == 'original')
								$("#nrCalculatorEvent_original").prop(
										"checked", true).trigger("click");
							else if ('${eventsBean.nrnReturnConfig.nrCalculatorEvent}' == 'variable')
								$("#nrCalculatorEvent_variable").prop(
										"checked", true).trigger("click");
							else if ('${eventsBean.nrnReturnConfig.nrCalculatorEvent}' == 'fixed')
								$("#nrCalculatorEvent_fixed").prop("checked",
										true).trigger("click");

							if ('${eventsBean.nrnReturnConfig.returnCalculatorEvent}' == 'original')
								$("#returnCalculatorEvent_original").prop(
										"checked", true).trigger("click");
							else if ('${eventsBean.nrnReturnConfig.returnCalculatorEvent}' == 'newTerms')
								$("#returnCalculatorEvent_newTerms").prop(
										"checked", true).trigger("click");

							if ('${eventsBean.nrnReturnConfig.commissionType}' == 'fixed')
								$("#commisionType-fixed").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.commissionType}' == 'categoryWise')
								$("#commisionType-categoryWise").prop(
										"checked", true).trigger("click");

							if ('${eventsBean.nrnReturnConfig.shippingFeeType}' == 'fixed')
								$("#shippingfee-fixed").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.shippingFeeType}' == 'variable')
								$("#shippingfee-variable")
										.prop("checked", true).trigger("click");
							if ('${eventsBean.nrnReturnConfig.retCharSFType}' == 'fixed') {
								$("#retrun-sf-fix").prop("checked", true)
										.trigger("click");
							} else if ('${eventsBean.nrnReturnConfig.retCharSFType}' == 'variable') {
								$("#retrun-sf-variable").prop("checked", true)
										.trigger("click");
							} else if ('${eventsBean.nrnReturnConfig.retCharSFType}' == 'noCharges')
								$("#retrun-sf-nocharges").prop("checked", true)
										.trigger("click");

							if ('${eventsBean.nrnReturnConfig.retCharBRType}' == 'fixed')
								$("#retrun-br-fix").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.retCharBRType}' == 'variable')
								$("#retrun-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.retCharBRType}' == 'noCharges')
								$("#retrun-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${eventsBean.nrnReturnConfig.RTOCharSFType}' == 'fixed')
								$("#RTO-sf-fix").prop("checked", true).trigger(
										"click");
							else if ('${eventsBean.nrnReturnConfig.RTOCharSFType}' == 'variable')
								$("#RTO-sf-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.RTOCharSFType}' == 'noCharges')
								$("#RTO-sf-nocharges").prop("checked", true)
										.trigger("click");

							if ('${eventsBean.nrnReturnConfig.RTOCharBRType}' == 'fixed')
								$("#RTO-br-fix").prop("checked", true).trigger(
										"click");
							else if ('${eventsBean.nrnReturnConfig.RTOCharBRType}' == 'variable')
								$("#RTO-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.RTOCharBRType}' == 'noCharges')
								$("#RTO-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${eventsBean.nrnReturnConfig.repCharSFType}' == 'fixed')
								$("#rep-sf-fix").prop("checked", true).trigger(
										"click");
							else if ('${eventsBean.nrnReturnConfig.repCharSFType}' == 'variable')
								$("#rep-sf-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.repCharSFType}' == 'noCharges')
								$("#rep-sf-nocharges").prop("checked", true)
										.trigger("click");

							if ('${eventsBean.nrnReturnConfig.repCharBRType}' == 'fixed')
								$("#rep-br-fix").prop("checked", true).trigger(
										"click");
							else if ('${eventsBean.nrnReturnConfig.repCharBRType}' == 'variable')
								$("#rep-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.repCharBRType}' == 'noCharges')
								$("#rep-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${eventsBean.nrnReturnConfig.PDCharSFType}' == 'fixed')
								$("#PD-sf-fix").prop("checked", true).trigger(
										"click");
							else if ('${eventsBean.nrnReturnConfig.PDCharSFType}' == 'variable')
								$("#PD-sf-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.PDCharSFType}' == 'noCharges')
								$("#PD-sf-nocharges").prop("checked", true)
										.trigger("click");

							if ('${eventsBean.nrnReturnConfig.PDCharBRType}' == 'fixed')
								$("#PD-br-fix").prop("checked", true).trigger(
										"click");
							else if ('${eventsBean.nrnReturnConfig.PDCharBRType}' == 'variable')
								$("#PD-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.PDCharBRType}' == 'noCharges')
								$("#PD-br-nocharges").prop("checked", true)
										.trigger("click");

							if ('${eventsBean.nrnReturnConfig.canCharSFBFRTDType}' == 'fixed')
								$("#can-sfbfrtd-fix").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.canCharSFBFRTDType}' == 'variable')
								$("#can-sfbfrtd-variable")
										.prop("checked", true).trigger("click");
							else if ('${eventsBean.nrnReturnConfig.canCharSFBFRTDType}' == 'noCharges')
								$("#can-sfbfrtd-nocharges").prop("checked",
										true).trigger("click");

							if ('${eventsBean.nrnReturnConfig.canCharSFARTDType}' == 'fixed')
								$("#can-sfartd-fix").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.canCharSFARTDType}' == 'variable')
								$("#can-sfartd-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.canCharSFARTDType}' == 'noCharges')
								$("#can-sfartd-nocharges")
										.prop("checked", true).trigger("click");

							if ('${eventsBean.nrnReturnConfig.canCharBRType}' == 'fixed')
								$("#can-br-fix").prop("checked", true).trigger(
										"click");
							else if ('${eventsBean.nrnReturnConfig.canCharBRType}' == 'variable')
								$("#can-br-variable").prop("checked", true)
										.trigger("click");
							else if ('${eventsBean.nrnReturnConfig.canCharBRType}' == 'noCharges')
								$("#can-br-nocharges").prop("checked", true)
										.trigger("click");

							/* Retrive Radio Buttons Ends */

							/* Retrive CheckBoxes Starts */

							if ('${eventsBean.nrnReturnConfig.whicheverGreaterPCC}' == 'true') {
								$('#whicheverGreaterPCC').iCheck('check');
							}
							if ('${eventsBean.nrnReturnConfig.retCharSFRevShipFee}' == 'true')
								$('#retCharSFRevShipFee').iCheck('check');
							if ('${eventsBean.nrnReturnConfig.retCharSFShipFee}' == 'true')
								$('#retCharSFShipFee').iCheck('check');
							if ('${eventsBean.nrnReturnConfig.retCharBRFF}' == 'true')
								$('#retCharBRFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.retCharBRShipFee}' == 'true')
								$('#retCharBRShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.RTOCharSFRevShipFee}' == 'true')
								$('#RTOCharSFRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.RTOCharSFFF}' == 'true')
								$('#RTOCharSFFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.RTOCharSFShipFee}' == 'true')
								$('#RTOCharSFShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.RTOCharSFRevShipFee}' == 'true')
								$('#RTOCharSFRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.RTOCharBRFF}' == 'true')
								$('#RTOCharBRFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.RTOCharBRShipFee}' == 'true')
								$('#RTOCharBRShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.repCharSFRevShipFee}' == 'true')
								$('#repCharSFRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.repCharSFFF}' == 'true')
								$('#repCharSFFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.repCharSFShipFee}' == 'true')
								$('#repCharSFShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.repCharSFRevShipFee}' == 'true')
								$('#repCharSFRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.repCharBRFF}' == 'true')
								$('#repCharBRFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.repCharBRShipFee}' == 'true')
								$('#repCharBRShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.PDCharSFRevShipFee}' == 'true')
								$('#PDCharSFRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.PDCharSFFF}' == 'true')
								$('#PDCharSFFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.PDCharSFShipFee}' == 'true')
								$('#PDCharSFShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.PDCharSFRevShipFee}' == 'true')
								$('#PDCharSFRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.PDCharBRFF}' == 'true')
								$('#PDCharBRFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.PDCharBRShipFee}' == 'true')
								$('#PDCharBRShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharSFBRTDRevShipFee}' == 'true')
								$('#canCharSFBRTDRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharSFBRTDFF}' == 'true')
								$('#canCharSFBRTDFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharSFBRTDShipFee}' == 'true')
								$('#canCharSFBRTDShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharSFBRTDRevShipFee}' == 'true')
								$('#canCharSFBRTDRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharSFARTDRevShipFee}' == 'true')
								$('#canCharSFARTDRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharSFFF}' == 'true')
								$('#canCharSFFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharSFShipFee}' == 'true')
								$('#canCharSFShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharSFARTDRevShipFee}' == 'true')
								$('#canCharSFARTDRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharBRFF}' == 'true')
								$('#canCharBRFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharBRFF}' == 'true')
								$('#canCharBRFF').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharBRShipFee}' == 'true')
								$('#canCharBRShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.canCharBRRevShipFee}' == 'true')
								$('#canCharBRRevShipFee').iCheck('check');

							if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeVar')
								$('#revShippingFeeType_revShipFeeVar').iCheck(
										'check');
							if ('${eventsBean.nrnReturnConfig.retCharSFFF}' == 'true')
								$('#retCharSFFF').iCheck('check');
							else if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeShipFee')
								$('#revShippingFeeType_revShipFeeShipFee')
										.iCheck('check');
							else if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeFF')
								$('#revShippingFeeType_revShipFeeFF').iCheck(
										'check');
							else if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeeGRT')
								$('#revShippingFeeType_revShipFeeGRT').iCheck(
										'check');
							else if ('${eventsBean.nrnReturnConfig.revShippingFeeType}' == 'revShipFeePCC')
								$('#revShippingFeeType_revShipFeePCC').iCheck(
										'check');

							$('#selectAll').click(function() {
								alert("Lets Work")
								$('#multiSku option').prop('selected', true);
							});

							/* Retrive CheckBoxes Ends */
							
							$("#text01").keyup(function() {
								$("#label01").text($(this).val()); //OR $("#label1").html($(this).val());
							});
							
							$('.i-checks').iCheck({
								checkboxClass : 'icheckbox_square-green',
								radioClass : 'iradio_square-green',
							});

							$('#data_1 .input-group.date').datepicker({
								todayBtn : "linked",
								keyboardNavigation : false,
								forceParse : false,
								calendarWeeks : true,
								autoclose : true
							});

						});

		function submitOrder() {
			/* var result = $('#errordiv'); */
			var validator = $("#addEvent").validate({
				rules : {
					eventName : {
						required : true,
					},
					channelName : {
						required : true,
					},

					startDate : {
						required : true,
					},
					endDate : {
						required : true,
					},
				/* errorPlacement: function(error, element) {
					result.empty();
					result.append(error);
				} */

				},
				messages : {
					eventName : "Event Name Required !",
					channelName : "Channel Name Should Be Selected !",
					startDate : "Select a Starting Date !",
					endDate : "Select an Ending Date !"

				}
			});
			$(".nrCalculatorEvent").rules("add", {
				required : function(element) {
					if ($(".nrCalculatorEvent:checked").length == 0) {
						return true;
					}
				},
				messages : {
					required : "Select One NR Type."
				}
			});
			$(".commissionType").rules("add", {
				required : function(element) {
					if ($(".nrCalculatorEvent:checked").val() == "variable") {
						if ($(".commissionType:checked").length == 0) {
							return true;
						}
					}
				},
				messages : {
					required : "Select One Commission Type."
				}

			});
			$("#fixedCommissionPercent").rules("add", {
				required : function(element) {
					if ($(".commissionType:checked").val() == "fixed") {
						number: true
					}
				}
			});
			$("#serviceTax").rules("add", {
				required : function(element) {
					if ($(".nrCalculatorEvent:checked").val() == "variable") {
						number: true
					}
				}
			});
			$(".returnCalculatorEvent").rules("add", {
				required : function(element) {
					if ($(".returnCalculatorEvent").length == 0) {
						return true;
					}
				},
				messages : {
					required : "Select One Return Type."
				}
			});

			var startDate = document.getElementById("StartDate").value;
			var endDate = document.getElementById("EndDate").value;
			var channelName = document.getElementById("channelName").value;
			/* alert(startDate+" "+endDate); */
			$
					.ajax({
						url : "checkDates.html?start=" + startDate + "&end="
								+ endDate + "&channel=" + channelName,
						success : function(res) {
							if (res == "false") {
								$("#startDateMessageR").html(
										"Invalid Start Date !").show();
								$("#endDateMessageR")
										.html("Invalid End Date !").show();
								$("#startDateMessageG").html(
										"Valid Start Date !").hide();
								$("#endDateMessageG").html("Valid End Date !")
										.hide();
								nameAvailability=false;
							} else {
								nameAvailability=true;
								$("#startDateMessageG").html(
										"Valid Start Date !").show();
								$("#endDateMessageG").html("Valid End Date !")
										.show();
								$("#startDateMessageR").html(
										"Invalid Start Date !").hide();
								$("#endDateMessageR")
										.html("Invalid End Date !").hide();

							}

						}
					});

			if (validator.form() && nameAvailability) {
				$('form#addEvent').submit();
			}

		}

		div = {
			show : function(elem) {
				document.getElementById(elem).style.visibility = 'visible';
			},
			hide : function(elem) {
				document.getElementById(elem).style.visibility = 'hidden';
			}
		}
	</script>
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
	
	
	</script>


</body>
</html>
