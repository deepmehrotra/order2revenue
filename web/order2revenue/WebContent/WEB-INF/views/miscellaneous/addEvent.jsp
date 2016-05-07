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
			<div class="wrapper wrapper-content animated fadeInRight" id="centerpane">
				<div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Events</h5>
                            <div class="ibox-tools">
                                <a href="" class="btn btn-primary btn-xs" >Create New Events</a>
                            </div>
                        </div>
                        <div class="ibox-content add-company"> 
                     <form:form method="POST" action="saveEvent.html" role="form" class="form-horizontal" id="addEvent">
                        <div class="col-sm-6">
                          <div class="form-group">
                            <label class="col-sm-4 control-label">Event Name</label>
                            <div class="col-sm-8">
                              <form:input path="eventName" value="${eventsBean.eventName}"	class="form-control" />
                            </div>
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="form-group">
                            <label class="col-sm-4 control-label">Select Channel</label>
                            <div class="col-sm-8">
                              <form:select path="channelName" items="${partnerMap}" class="form-control"></form:select>
                            </div>
                          </div>
                        </div>
                        <div class="col-lg-12">
                            <h4>Event Period</h4>
                        </div>
                        
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">Start Date</label>
                                <div class="col-sm-8" id="data_1">
                                    <div class="input-group date"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <form:input path="startDate" value="${eventsBean.startDate}"	class="form-control" />
                                    </div>
                                </div>
                            </div> 
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">End Date</label>
                                <div class="col-sm-8" id="data_1">
                                    <div class="input-group date"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <form:input path="endDate" value="${eventsBean.endDate}"	class="form-control" />
                                    </div>
                                </div>
                            </div>  
                        </div>
                        <div class="col-sm-12">
                          <h4>NR Calculator</h4>
                        </div>
                        <div class="col-sm-12">
							<div class="col-sm-4">
                            <div class="radio">
                              <label>
                                <form:radiobutton  value="original" id="94" path="nrnReturnConfig.nrCalculatorEvent" onChange="handleRadioEvent(this);" />
                                Original </label>
                            </div>
                          </div>
                          <div class="col-sm-4">
                            <div class="radio">
                              <label>
                                <form:radiobutton  value="variable" id="40" path="nrnReturnConfig.nrCalculatorEvent"  onChange="handleRadioEvent(this);"  />
                                Variable NR</label>
                            </div>
                          </div>
                          <div class="col-sm-4">
                            <div class="radio">
                              <label>
                                <form:radiobutton  value="fixed" id="41" path="nrnReturnConfig.nrCalculatorEvent" onChange="handleRadioEvent(this);"  />
                                Fixed TP </label>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-12 radio1 m-b" id="blk-40">
                            <div class="col-sm-12">
								<div class="col-sm-12 radio5" id="nr-switch-sec" style="display: block;">
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
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.commissionType" value="fixed"
																					id="1" name="commissionType"
																					class="commissionType"  /> <!--       <input type="radio" value="4" id="optionsRadios1" > -->
																				Fixed
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-6">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.commissionType"
																					value="categoryWise"
																					id="2"
																					name="commissionType" class="commissionType"  /> <!--  <input type="radio" value="5" id="optionsRadios1" > -->
																				Category Wise
																			</label>
																		</div>
																	</div>
																	
																</div>
																<div class="col-sm-12 radio1"
																	id="blk-1">
																	<div class="col-sm-12">
																		<div class="form-group">
																			<div class="input-group m-b col-md-4">
																				<input type="text" class="form-control"
																					name="nr-fixedCommissionPercent" value="${chargeMap.fixedCommissionPercent}"> <span
																					class="input-group-addon">%</span>
																			</div>
																		</div>
																	</div>
																</div>
																<div class="col-sm-12 radio1"
																	id="blk-2">
																	<c:if test="${!empty categoryList}">
																		<c:forEach items="${categoryList}" var="category"
																			varStatus="loop">
																			<div class="form-group col-md-12">
																				<label class="col-md-4 control-label">${category}</label>
																				<div class="input-group m-b col-md-4">
																					<input type="text" class="form-control"
																						name='nr-${category}' value="" >
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
																				name="nr-fixedfeelt250" value="${chargeMap.fixedfeelt250}"/>
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;250&&&lt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt250lt500" value="${chargeMap.fixedfeegt250lt500}"/>
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500" value="${chargeMap.fixedfeegt500}">
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
																				name="nr-fixedfeelt500Big" value="${chargeMap.fixedfeelt500Big}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;500&&&lt;1000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500lt1000" value="${chargeMap.fixedfeegt500lt1000}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;1000&&&lt;10000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt1000lt10000" value="${chargeMap.fixedfeegt1000lt10000}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label">&gt;10000</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt10000" value="${chargeMap.fixedfeegt10000}">
																		</div>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="form-group col-md-12">
																		<label class="col-md-4 control-label content-rgt">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeelt500" value="${chargeMap.fixedfeelt500}">
																		</div>
																	</div>
																	<div class="form-group col-md-12">
																		<label class="col-md-12 control-label">&gt;500</label>
																		<div class="col-md-12 content-rgt">
																			<input type="text" placeholder=""
																				class="form-control" style="width: 50%;"
																				name="nr-fixedfeegt500Big" value="${chargeMap.fixedfeegt500Big}">
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
																						name="nr-percentSPPCC" value="${chargeMap.percentSPPCC}"> <span
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
																						name="nr-fixedAmtPCC" value="${chargeMap.fixedAmtPCC}"> <span
																						class="input-group-addon" >%</span>
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
																				<label> <!-- <input type="radio" value="6" id="optionsRadios1" > -->
																					<form:radiobutton  onChange="handleRadioEvent(this);"
																						path="nrnReturnConfig.shippingFeeType"
																						value="variable" id="46"
																						 class="shippingFeeType" />
																					Variable Shipping Charges
																				</label>
																			</div>
																		</div>
																		<div class="col-sm-6">
																			<div class="radio">
																				<label>
																					<form:radiobutton  onChange="handleRadioEvent(this);"
																						path="nrnReturnConfig.shippingFeeType"
																						value="fixed" id="47"
																						class="shippingFeeType"  /> Fixed Shipping Charges
																				</label>
																			</div>
																		</div>
							
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-46">
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
																										class="form-control" name="nr-localvwlt500" value="${chargeMap.localvwlt500}">
																								</div>
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonalvwlt500" value="${chargeMap.zonalvwlt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationalvwlt500" value="${chargeMap.nationalvwlt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrovwlt500" value="${chargeMap.metrovwlt500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>500 &gt; 1000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt500lt1000" value="${chargeMap.localvwgt500lt1000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt500lt1000" value="${chargeMap.zonalvwgt500lt1000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt500lt1000" value="${chargeMap.nationalvwgt500lt1000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt500lt1000" value="${chargeMap.metrovwgt500lt1000}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1000 &gt; 1500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt1000lt1500" value="${chargeMap.localvwgt1000lt1500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt1000lt1500" value="${chargeMap.zonalvwgt1000lt1500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt1000lt1500" value="${chargeMap.nationalvwgt1000lt1500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt1000lt1500" value="${chargeMap.metrovwgt1000lt1500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1500 &gt; 5000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-localvwgt1500lt5000" value="${chargeMap.localvwgt1500lt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-zonalvwgt1500lt5000" value="${chargeMap.zonalvwgt1500lt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-nationalvwgt1500lt5000" value="${chargeMap.nationalvwgt1500lt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-metrovwgt1500lt5000" value="${chargeMap.metrovwgt1500lt5000}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>add 1 kg</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-localvwgt5000" value="${chargeMap.localvwgt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonalvwgt5000" value="${chargeMap.zonalvwgt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationalvwgt5000" value="${chargeMap.nationalvwgt5000}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrovwgt5000" value="${chargeMap.metrovwgt5000}">
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
																										class="form-control" name="nr-localdwlt500" value="${chargeMap.localdwlt500}">
																								</div>
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonaldwlt500" value="${chargeMap.zonaldwlt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationaldwlt500" value="${chargeMap.nationaldwlt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrodwlt500" value="${chargeMap.metrodwlt500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>additional &gt; 500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-localdwgt500" value="${chargeMap.localdwgt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-zonaldwgt500" value="${chargeMap.zonaldwgt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-nationaldwgt500" value="${chargeMap.nationaldwgt500}">
																							</div></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-metrodwgt500" value="${chargeMap.metrodwgt500}">
																							</div></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-47">
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
																										class="form-control" name="nr-fixeddwlt500" value="${chargeMap.fixeddwlt500}">
																								</div>
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>additional &gt; 500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-fixeddwgt500" value="${chargeMap.fixeddwgt500}">
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
																										class="form-control" name="nr-fixedvwlt500" value="${chargeMap.fixedvwlt500}">
																								</div>
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>500 &gt; 1000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt500lt1000" value="${chargeMap.fixedvwgt500lt1000}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1000 &gt; 1500</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt1000lt1500" value="${chargeMap.fixedvwgt1000lt1500}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>1500 &gt; 5000</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control"
																									name="nr-fixedvwgt1500lt5000" value="${chargeMap.fixedvwgt1500lt5000}">
																							</div></td>
																					</tr>
																					<tr>
																						<td><label>add 1 kg</label></td>
																						<td><div class=" content-rgt">
																								<input type="text" placeholder=""
																									class="form-control" name="nr-fixedvwgt5000" value="${chargeMap.fixedvwgt5000}">
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
																				class="form-control" name="nr-serviceTax" value="${chargeMap.serviceTax}">
																		</div>
																	</div>
																</div>
															</div>
														</div>
													
												</div>
											</div>
		</div><!-- -----------------------------id-40----------------------------- -->
                            </div>
                          </div>
                          <div class="col-sm-12 radio1" id="blk-41">
                            <div class="form-group col-md-12">
                              <label class="col-sm-2 control-label">label</label>
                              <div class="col-sm-3">
                                <input type="text" placeholder="" class="form-control">
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="col-sm-12">
                          <h4>Return Calculator/Charges</h4>
                        </div>
                        <div class="col-sm-12">
                          <div class="col-sm-6">
                            <div class="radio">
                              <label>
                                <form:radiobutton  onChange="handleRadioEvent(this);" value="original" id="42" path="nrnReturnConfig.returnCalculatorEvent" />
                                Original Terms</label>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="radio">
                              <label>
                                <form:radiobutton  onChange="handleRadioEvent(this);" value="newTerms" id="43" path="nrnReturnConfig.returnCalculatorEvent" />
                                New Terms </label>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-12 radio1 m-b" id="blk-42">
                            <div class="col-sm-12">
                                
                            </div>
                          </div>
                          <div class="col-sm-12 radio1" id="blk-43">
                                <div class="ibox-content add-company">
							<div class="panel-body">
												<div class="panel-group" id="accordion1">
													<div class="panel panel-default">
														<div class="panel-heading">
															<h5 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapsesix1">Return Charges</a>
															</h5>
														</div>
														<div id="collapsesix1" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.retCharSFType" value="fixed"
																					id="48" 
																					class="retCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.retCharSFType"
																					value="variable" id="49"
																					 class="retCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.retCharSFType"
																					value="noCharges" id="95"
																					 class="retCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-48">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-retCharSFFixedAmt" value="${chargeMap.retCharSFFixedAmt}">
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
																		id="blk-49">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFVarFixedAmt" value="${chargeMap.retCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFPercentSP" value="${chargeMap.retCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharSFPercentPCC" value="${chargeMap.retCharSFPercentPCC}">
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
																	
																	<div class="col-sm-12 radio1 m-b" id="blk-95">
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
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.retCharBRType" value="fixed"
																					id="60" 
																					class="retCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.retCharBRType"
																					value="variable" id="61"
																					 class="retCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.retCharBRType"
																					value="noCharges" id="96"
																					 class="retCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-60">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-retCharBRFixedAmt" value="${chargeMap.retCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1"
																		id="blk-61">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRVarFixedAmt" value="${chargeMap.retCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRPercentSP" value="${chargeMap.retCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-retCharBRPercentPCC" value="${chargeMap.retCharBRPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-96">
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapseseven1">RTO Charges</a>
															</h4>
														</div>
														<div id="collapseseven1" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.RTOCharSFType" value="fixed"
																					id="56" 
																					class="RTOCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.RTOCharSFType"
																					value="variable" id="57"
																					 class="RTOCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.RTOCharSFType"
																					value="noCharges" id="97"
																					 class="RTOCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-56">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-RTOCharSFFixedAmt" value="${chargeMap.RTOCharSFFixedAmt}">
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
																	<div class="col-sm-12 radio1" id="blk-57">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFVarFixedAmt" value="${chargeMap.RTOCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFPercentSP" value="${chargeMap.RTOCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharSFPercentPCC" value="${chargeMap.RTOCharSFPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-97">
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
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.RTOCharBRType" value="fixed"
																					id="66" 
																					class="RTOCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.RTOCharBRType"
																					value="variable" id="67"
																					 class="RTOCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.RTOCharBRType"
																					value="noCharges" id="98"
																					 class="RTOCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-66">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-RTOCharBRFixedAmt" value="${chargeMap.RTOCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-67">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRVarFixedAmt" value="${chargeMap.RTOCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRPercentSP" value="${chargeMap.RTOCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-RTOCharBRPercentPCC" value="${chargeMap.RTOCharBRPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-98">
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapseeight1">Replacement Charges</a>
															</h4>
														</div>
														<div id="collapseeight1" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.repCharSFType" value="fixed"
																					id="64" 
																					class="repCharSFType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.repCharSFType"
																					value="variable" id="65"
																					 class="repCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.repCharSFType"
																					value="noCharges" id="99"
																					 class="repCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-64">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-repCharSFFixedAmt" value="${chargeMap.repCharSFFixedAmt}">
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
																	<div class="col-sm-12 radio1" id="blk-65">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFVarFixedAmt" value="${chargeMap.repCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFPercentSP" value="${chargeMap.repCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharSFPercentPCC" value="${chargeMap.repCharSFPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-99">
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
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.repCharBRType" value="fixed"
																					id="68" 
																					class="repCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.repCharBRType"
																					value="variable" id="69"
																					 class="repCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.repCharBRType"
																					value="noCharges" id="100"
																					 class="repCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-68">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-repCharBRFixedAmt" value="${chargeMap.repCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-69">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRVarFixedAmt" value="${chargeMap.repCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRPercentSP" value="${chargeMap.repCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-repCharBRPercentPCC" value="${chargeMap.repCharBRPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-100">
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapsenine1">Partial Delivery Charges</a>
															</h4>
														</div>
														<div id="collapsenine1" class="panel-collapse collapse">
															<div class="panel-body">
																<h4>Seller Fault</h4>
																<div class="col-sm-12">
																	<div class="hr-line-dashed"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.PDCharSFType" value="fixed"
																					id="72"  class="PDCharSFType" />
																				Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.PDCharSFType"
																					value="variable" id="73" 
																					class="PDCharSFType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.PDCharSFType"
																					value="noCharges" id="101"
																					 class="PDCharSFType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-72">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-PDCharSFFixedAmt" value="${chargeMap.PDCharSFFixedAmt}">
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
																	<div class="col-sm-12 radio1" id="blk-73">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFVarFixedAmt" value="${chargeMap.PDCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFPercentSP" value="${chargeMap.PDCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharSFPercentPCC" value="${chargeMap.PDCharSFPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-101">
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
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.PDCharBRType" value="fixed"
																					id="76"  class="PDCharBRType" />
																				Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.PDCharBRType"
																					value="variable" id="77" 
																					class="PDCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.PDCharBRType"
																					value="noCharges" id="102"
																					 class="PDCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-76">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-PDCharBRFixedAmt" value="${chargeMap.PDCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-77">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRVarFixedAmt" value="${chargeMap.PDCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRPercentSP" value="${chargeMap.PDCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-PDCharBRPercentPCC" value="${chargeMap.PDCharBRPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-102">
																	</div>
																</div>
															</div>
														</div>
													</div>
													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapseten1">Cancellation Charges</a>
															</h4>
														</div>
														<div id="collapseten1" class="panel-collapse collapse">
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
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="fixed" id="80" 
																					class="canCharSFBFRTDType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="variable" id="81"
																					 class="canCharSFBFRTDType" />
																				Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharSFBFRTDType"
																					value="noCharges" id="82"
																					 class="canCharSFBFRTDType"
																					checked="checked" /> No	Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-80">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-canCharSFBFRTDFixedAmt" value="${chargeMap.canCharSFBFRTDFixedAmt}">
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
																		id="blk-81">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDVarFixedAmt" value="${chargeMap.canCharSFBFRTDVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDPercentSP" value="${chargeMap.canCharSFBFRTDPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control"
																					name="nr-canCharSFBFRTDPercentPCC" value="${chargeMap.canCharSFBFRTDPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-82">
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
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharSFARTDType"
																					value="fixed" id="83" 
																					class="canCharSFARTDType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharSFARTDType"
																					value="variable" id="84"
																					 class="canCharSFARTDType" />
																				Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharSFARTDType"
																					value="noCharges" id="103"
																					 class="canCharSFARTDType"
																					checked="checked" /> No
																				Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b"
																		id="blk-83">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-canCharSFFixedAmt" value="${chargeMap.canCharSFFixedAmt}">
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
																		id="blk-84">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFVarFixedAmt" value="${chargeMap.canCharSFVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFPercentSP" value="${chargeMap.canCharSFPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharSFPercentPCC" value="${chargeMap.canCharSFPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-103">
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
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharBRType" value="fixed"
																					id="88" 
																					class="canCharBRType" /> Fixed Amount
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharBRType"
																					value="variable" id="89"
																					 class="canCharBRType" /> Variable
																			</label>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="radio">
																			<label> <form:radiobutton  onChange="handleRadioEvent(this);"
																					path="nrnReturnConfig.canCharBRType"
																					value="noCharges" id="104"
																					 class="canCharBRType"
																					checked="checked" /> No Charges
																			</label>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-12 radio1 m-b" id="blk-88">
																		<div class="col-sm-12">
																			<div class="form-group">
																				<div class="col-md-2 control-label">
																					<label>Enter Fix Charges</label>
																				</div>
																				<div class="col-md-3 content-rgt">
																					<input type="text" class="form-control"
																						name="nr-canCharBRFixedAmt" value="${chargeMap.canCharBRFixedAmt}">
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-12 radio1" id="blk-89">
																		<div class="form-group col-md-12">
																			<label class="col-sm-2 control-label">Fix
																				Amount</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRVarFixedAmt" value="${chargeMap.canCharBRVarFixedAmt}">
																			</div>
																			<label class="col-sm-2 control-label">% of SP</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRPercentSP" value="${chargeMap.canCharBRPercentSP}">
																			</div>
																			<label class="col-sm-2 control-label"> % of
																				Commision</label>
																			<div class="col-sm-2">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-canCharBRPercentPCC" value="${chargeMap.canCharBRPercentPCC}">
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
																	<div class="col-sm-12 radio1 m-b" id="blk-104">
																	</div>
																</div>
															</div>
														</div>
													</div>

													<div class="panel panel-default">
														<div class="panel-heading">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion1"
																	href="#collapseeleven1">Reverse Shipping Fee</a>
															</h4>
														</div>
														<div id="collapseeleven1" class="panel-collapse collapse">
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
																				class="form-control" name="nr-revShipFeePCC" value="${chargeMap.revShipFeePCC}">
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
																					class="form-control" name="nr-revShipFeeFlatAmt" value="${chargeMap.revShipFeeFlatAmt}">
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
																					class="form-control" name="nr-revShipFeePCCMF" value="${chargeMap.revShipFeePCCMF}">
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
																				class="form-control" name="nr-revShipFeeFF" value="${chargeMap.revShipFeeFF}">
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
																					class="form-control" name="nr-revShipFeeDWAmt" value="${chargeMap.revShipFeeDWAmt}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Amount</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWAmt" value="${chargeMap.revShipFeeVWAmt}">
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
																					class="form-control" name="nr-revShipFeeDWPW" value="${chargeMap.revShipFeeDWPW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Per Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWPW" value="${chargeMap.revShipFeeVWPW}">
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
																					class="form-control" name="nr-revShipFeeDWMW" value="${chargeMap.revShipFeeDWMW}">
																			</div>
																			<div class="col-md-2">
																				<label> </label>
																			</div>
																			<div class="col-md-2 control-label">
																				<label>Min Weight</label>
																			</div>
																			<div class="col-md-2 content-rgt">
																				<input type="text" placeholder=""
																					class="form-control" name="nr-revShipFeeVWMW" value="${chargeMap.revShipFeeVWMW}">
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
								onclick="submitOrder()">Save</button>
				</div>
				</form:form>
                </div>
			</div>	
            </div>
			<div class="col-lg-12">
				
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
<script src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
        });
    });
</script>
<style>
    body.DTTT_Print {
        background: #fff;

    }
    .DTTT_Print #page-wrapper {
        margin: 0;
        background:#fff;
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



<script src="/O2R/seller/js/jquery-2.1.1.js"></script> 
<script src="/O2R/seller/js/bootstrap.min.js"></script> 
<script src="/O2R/seller/js/plugins/metisMenu/jquery.metisMenu.js"></script> 
<script src="/O2R/seller/js/plugins/slimscroll/jquery.slimscroll.min.js"></script> 
<!-- Data picker --> 
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<!-- Custom and plugin javascript --> 
<script src="/O2R/seller/js/inspinia.js"></script> 
<script src="/O2R/seller/js/plugins/pace/pace.min.js"></script> 
<!-- iCheck --> 
<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script> 
<!-- Switchery --> 
<script type="text/javascript">
            $(document).ready(function () {
                $('.i-checks').iCheck({
                    checkboxClass: 'icheckbox_square-green',
                    radioClass: 'iradio_square-green',
                });
				var elem = document.querySelector('.js-switch');
            var switchery = new Switchery(elem, { color: '#1AB394' });

            var elem_2 = document.querySelector('.js-switch_2');
            var switchery_2 = new Switchery(elem_2, { color: '#ED5565' });

            var elem_3 = document.querySelector('.js-switch_3');
            var switchery_3 = new Switchery(elem_3, { color: '#1AB394' });


            });
        </script> 	
	
	
	
	
	
	
	
	
<script>

        function handleRadioEvent(thiss){
        	   var x=thiss.id;

        	   if(!(x>=1 &&  x<=105))
        	            $('.radio1').hide();

        	            $("#blk-"+ x).slideDown();

        	    for(var a=0;a<105;a++){
        	        if(a!=x && a!=41 && a!=40 && a!=42 && a!=43)
        	    $("#blk-"+a).slideUp();
        	    if(x==41)
        	    $("#blk-40").slideUp();
        	    if(x==40)
        	    $("#blk-41").slideUp();
        	    if(x==42)
        	    $("#blk-43").slideUp();
        	    if(x==43)
        	    $("#blk-42").slideUp();
        	    }
        	    
        	     if(x==94){
        	    $("#blk-40").slideUp();
        	    $("#blk-41").slideUp();
        	    $("#blk-42").slideUp();
        	    $("#blk-43").slideUp();
        	    }
        	     if(x==40){
             	$("#blk-94").slideUp();
             	$("#blk-41").slideUp();
             	$("#blk-42").slideUp();
             	$("#blk-43").slideUp();
             	}
        	     if(x==42){
                $("#blk-94").slideUp();
                $("#blk-41").slideUp();
                $("#blk-40").slideUp();
                $("#blk-43").slideUp();
                }
        	     if(x==43){
                $("#blk-94").slideUp();
                $("#blk-41").slideUp();
                $("#blk-42").slideUp();
                $("#blk-40").slideUp();
                }
        	     if(x==41){
                $("#blk-94").slideUp();
                $("#blk-40").slideUp();
                $("#blk-42").slideUp();
                $("#blk-43").slideUp();
                }
        	     
        	    $("#blk-"+x).slideDown();
        }

		function submitOrder() {
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
					
				},
				messages : {
					eventName : "Event Name Should Be Entered !",
					channelName : "Channel Name Should be Selected !",
					startDate : "Select a Starting Date !",
					endDate : "select an Ending Date !"
				}
			});
		}
	</script>
	
	
<script type="text/javascript">
    $(document).ready(function(){
    	//alert("ready");
        $("[name=toggler]").click(function(){

   var x=this.id;

   if(!(x>=1 &&  x<=95))
            $('.radio1').hide();

            $("#blk-"+ x).slideDown();

    for(var a=0;a<95;a++){
        if(a!=x && a!=41 && a!=40 && a!=42 && a!=43)
    $("#blk-"+a).slideUp();
    if(x==41)
    $("#blk-40").slideUp();
    if(x==40)
    $("#blk-41").slideUp();
    if(x==42)
    $("#blk-43").slideUp();
    if(x==43)
    $("#blk-42").slideUp();
    }
    alert(x);
     if(x==94){
    $("#blk-40").slideUp();
    $("#blk-41").slideUp();
    $("#blk-42").slideUp();
    $("#blk-43").slideUp();
    }
    $("#blk-"+x).slideDown();
        });

  
  
$("#nr-switch").change(function() {
    if(this.checked) {
            $('.radio5').hide();
            $("#nr-switch-sec").slideDown();
    }
 else
 {
  
            $("#nr-switch-sec").slideUp();
   
  
 }
 });
 
  
        $('#paymentField').change(function () {
            $('.payment-box').hide();
            $('#'+$(this).val()).fadeIn();
        });
        $('#data_1 .input-group.date').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
            });
    });
</script>
<script type="text/javascript">
	div = {
		show: function(elem) {
			document.getElementById(elem).style.visibility = 'visible';
		},
		hide: function(elem) {
			document.getElementById(elem).style.visibility = 'hidden';
		}
	}	
</script>



	
</body>
</html>