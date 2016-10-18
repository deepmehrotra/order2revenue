<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import = "org.springframework.core.io.*" %>
<%@ page import = "org.springframework.core.io.support.PropertiesLoaderUtils" %>
<%@ page language="java" import="java.util.*" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<style type="text/css">
.lable1
       {       	
       	margin-top: -35px;
	    font-size: 16px;
	    text-align: center;
	    z-index: 99999999;
	    color: #080e08;
	    font-weight: 800;
	    font-style: normal;
	    border-radius: 10px;
	    position: absolute;
	    margin-left: 37%;
	   }
       .partnerImg
       {
       		height: 91px;        	
        	object-fit: contain;
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

    .labelfix
    {
        font-size: 11px;
        font-weight: 600;
    }
    
.partnerImg
       {
       		height: 100px;        	
        	object-fit: contain;
       }
 .nav-pills{
 	min-width: 20%;
 }
 .shippedtab td{
 text-align:center;
 }
 .shippedtab th
 {
 	text-align:center;
 	background: aquamarine !important;
    color: #000;
    padding: 4px;
    font-size: 10px;
 	font-weight: 900;
 }

</style>

	<link href="/O2R/seller/css/bootstrap.min.css" rel="stylesheet">
    <link href="/O2R/seller/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="/O2R/seller/css/animate.css" rel="stylesheet">
    <link href="/O2R/seller/css/style.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/O2R/seller/css/jcarousel.responsive.css">


	<link href="/O2R/seller/css/plugins/datapicker/datepicker3.css"	rel="stylesheet">
	<link href="/O2R/seller/css/plugins/switchery/switchery.css" rel="stylesheet">
	<link href="/O2R/seller/css/plugins/iCheck/custom.css" rel="stylesheet">

	<!-- <script src="/O2R/seller/js/inspinia.js"></script> -->
 <script type="text/javascript">
 	function relodPage(id) { 		
 		window.location.href = 'viewPartner.html?pcId='+id; 			
 }
 
 </script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInUp">
				<div class="ibox">
					<div class="ibox-content">
						<div class="row" style="background: aquamarine;">
							<div class="col-lg-12">
								<div class="m-b-md m-l">
									<h2>
										<b>Amazon</b>
									</h2>
								</div>
								<div class="col-lg-2"></div>
								<div class="col-lg-6">


									<dl class="dl-horizontal">
										<dt>Channel :</dt>
										<dd class="project-people">
											<a href=""><img alt="image" class="img-circle"
												src="img/Amazon.jpg" style="margin: -14px 0px 0px -12px;"></a>
										</dd>
									</dl>
								</div>
								<div class="col-lg-2"></div>
							</div>
						</div>
						<div class="row" style="background: aquamarine;">
							<div class="col-lg-2"></div>
							<div class="col-lg-4">
								<dl class="dl-horizontal">
									<dt>Created date :</dt>
									<dd><fmt:formatDate	value="${event.createdDate}" pattern="MMM dd,YY" /></dd>
									<dt>Channel :</dt>
									<dd>
										<a class="text-navy">${event.channelName}</a>
									</dd>
									<dt>Net Sales Quantity :</dt>
									<dd>${event.netSalesQuantity}</dd>
									<dt>Net Sales Amount :</dt>
									<dd>${event.netSalesAmount}</dd>

								</dl>
							</div>
							<div class="col-lg-4" id="cluster_info">
								<dl class="dl-horizontal">
									<dt>Start Date :</dt>
									<dd><fmt:formatDate	value="${event.startDate}" pattern="MMM dd,YY" /></dd>
									<dt>End Date :</dt>
									<dd><fmt:formatDate	value="${event.endDate}" pattern="MMM dd,YY" /></dd>
									<dt>Event Is :</dt>
									<dd>
										<a class="text-navy">Active</a>
									</dd>
									<dt>Event Status :</dt>
									<dd>
										<a class="text-navy">${event.status}</a>
									</dd>
								</dl>
							</div>
							<div class="col-lg-2"></div>
							<div class="row">
								<div class="col-lg-12">
									<div class="col-lg-2"></div>
									<div class="col-lg-6">
										<dl class="dl-horizontal">
											<dt>Completed:</dt>
											<dd>
												<div class="progress progress-striped active m-b-sm">
													<div style="width: 60%;" class="progress-bar"></div>
												</div>
												<small>Event completed in <strong>60%</strong>.
													Remaining close the Event, sign a contract and invoice.
												</small>
											</dd>
										</dl>
									</div>
									<div class="col-lg-2"></div>
								</div>
							</div>

						</div>
						<div class="row m-t-sm">
							<div class="col-lg-12">
								<div class="panel blank-panel">
									<div class="panel-heading">
										<div class="panel-options">
											<ul class="nav nav-tabs">
												<li class="active"><a href="#tab-1" data-toggle="tab">N/R
														Config</a></li>
												<li class=""><a href="#tab-2" data-toggle="tab">
														Return Config</a></li>
											</ul>
										</div>
									</div>

									<div class="panel-body">

										<div class="tab-content">
											<div class="tab-pane active" id="tab-1">
												<div class="feed-element">
													<table class="table table hover"
														style="border-spacing: 0 5px;">
														<tbody>
															<tr data-toggle="collapse" data-target="#accordion1" class="clickable">
                                                            <td style="width: 50%;"><label class="labelfix">COMMISSION</label></td>
																		<td>
																			<div>
																				<c:if
																					test="${event.nrnReturnConfig.commissionType == 'fixed' }">
																					<button class="btn btn-grey" id="button1" style="width:40%;"><font size="2" color="green" >Fixed</font><span class="caret"></span></button>																					
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<button class="btn btn-grey" id="button1" style="width:40%;"><font size="2" color="green">Category Wise</font><span class="caret"></span></button>																					
																				</c:if>
																			</div>
																		</td>
																	</tr>

																	<tr>
																		<td style="border: none;"></td>
																		<td colspan="10" style="border: none;">
																			<div id="accordion1" class="collapse">
																				<c:if
																					test="${event.nrnReturnConfig.commissionType == 'fixed' }">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.fixedCommissionPercent} %</label>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<div class="form-group col-md-12" style="overflow-x:hidden;overflow-y:scroll;height:200px;">
																					<c:choose>
																						<c:when test="${!empty categoryMap}">																						
																							<c:forEach items="${categoryMap}" var="cat"
																								varStatus="loop">																								
																									<label class="labelfix">${cat.key} :
																										${cat.value}</label><br>																								
																							</c:forEach>																						
																						</c:when>
																					</c:choose>
																					</div>
																				</c:if>
																			</div>
																		</td>
																	</tr>
															<tr>
																<td style="width: 50%;"><label class="labelfix">FIXED
																		FEE</label></td>
																<td><c:if test="${!empty event.fixedfeeList}">
																	<c:forEach items="${event.fixedfeeList}" var="fixedfee" varStatus="loop">
																		<div>
																			<label class="labelfix" style="text-align: left;">${fixedfee.criteria} ${fixedfee.range} : ${fixedfee.value}</label>
																		</div>
																	</c:forEach>
																</c:if></td>
															</tr>
															<tr>
																<td style="width: 50%;"><label class="labelfix">PCC</label>
																</td>
																<td>
																	<c:if test="${event.nrnReturnConfig.whicheverGreaterPCC == 'true'}">
																		<div>
																			<label class="labelfix" style="text-align: left;">Which Ever Is Greater</label><br>
																			<label class="labelfix" style="text-align: left;">% of SP : ${chargeMap.pccpercentSPHigher}</label><br>
																			<label class="labelfix" style="text-align: left;">Fixed AMT : ${chargeMap.pccfixedAmt}</label>
																		</div>
																	</c:if> 
																	<c:if test="${event.nrnReturnConfig.whicheverGreaterPCC == 'false'}">
																		<div>
																			<label class="labelfix" style="text-align: left;">Value Based</label><br>
																			<label class="labelfix" style="text-align: left;">Upto ${chargeMap.pccrange} : ${chargeMap.pccvalue}</label><br>
																			<label class="labelfix" style="text-align: left;">Greater than ${chargeMap.pccrange} : ${chargeMap.pccpercentSPValue} % of SP</label>
																		</div>
																	</c:if>
																</td>
															</tr>
															<tr data-toggle="collapse" data-target="#accordion2"
																class="clickable">
																<td style="width: 50%;"><label class="labelfix">SHIPPING
																		FEE</label></td>
																<td>
																	<c:if test="${event.nrnReturnConfig.shippingFeeType == 'fixed'}">
																		<button class="btn btn-grey" id="button1" style="width:40%;"><font size="2" color="green">Fixed</font><span class="caret"></span></button>
																	</c:if> 
																	<c:if test="${event.nrnReturnConfig.shippingFeeType == 'variable'}">
																		<button class="btn btn-grey" id="button1" style="width:40%;"><font size="2" color="green">Variable</font><span class="caret"></span></button>
																	</c:if>
																</td>
															</tr>
															<tr>
																<td style="border: none;"></td>
																<td colspan="10" style="border: none;">
																	<div id="accordion2" class="collapse">
																		<c:if
																			test="${event.nrnReturnConfig.shippingFeeType == 'variable'}">
																			<label style="text-align: left; font-size: 13px;" class="labelfix">Volumetric
																				calculation= (lxbxh)(cm)/5</label>
																			<div class="slim-scroll-bar">
																			<table class="table-bordered shippedtab"
																				style="border-spacing: 0 5px; width: 100%;">
																				<thead>
																					<tr>
																						<th>Slab(gms)</th>
																						<th>Value</th>
																						<th>Local</th>
																						<th>Zonal</th>
																						<th>National</th>
																						<th>Metro</th>
																					</tr>
																				</thead>
																				<tbody>
																					<c:if
																						test="${!empty event.shippingfeeVolumeVariableList}">
																						<c:forEach
																							items="${event.shippingfeeVolumeVariableList}"
																							var="shippingfee" varStatus="loop">
																							<tr>
																								<td>${shippingfee.criteria}</td>
																								<td><label class="labelfix">${shippingfee.range}</label></td>
																								<td><label class="labelfix">${shippingfee.localValue}</label></td>
																								<td><label class="labelfix">${shippingfee.zonalValue}</label></td>
																								<td><label class="labelfix">${shippingfee.nationalValue}</label></td>
																								<td><label class="labelfix">${shippingfee.metroValue}</label></td>
																							</tr>
																						</c:forEach>
																					</c:if>
																				</tbody>
																			</table>
																			</div>
																			<br>
																			<label style="text-align: left;font-size: 13px;" class="labelfix">Deadweight
																				calculation</label>
																			<div class="slim-scroll-bar">
																			<table class="table-bordered shippedtab"
																				style="border-spacing: 0 5px;width: 100%;">
																				<thead>
																					<tr>
																						<th>Slab(gms)</th>
																						<th>Value</th>
																						<th>Local</th>
																						<th>Zonal</th>
																						<th>National</th>
																						<th>Metro</th>
																					</tr>
																				</thead>
																				<tbody>
																					<c:if
																						test="${!empty event.shippingfeeWeightVariableList}">
																						<c:forEach
																							items="${event.shippingfeeWeightVariableList}"
																							var="shippingfee" varStatus="loop">
																							<tr>
																								<td>${shippingfee.criteria}</td>
																								<td><label class="labelfix">${shippingfee.range}</label></td>
																								<td><label class="labelfix">${shippingfee.localValue}</label></td>
																								<td><label class="labelfix">${shippingfee.zonalValue}</label></td>
																								<td><label class="labelfix">${shippingfee.nationalValue}</label></td>
																								<td><label class="labelfix">${shippingfee.metroValue}</label></td>
																							</tr>
																						</c:forEach>
																					</c:if>
																				</tbody>
																			</table>
																			</div>
																		</c:if>
																		<c:if
																			test="${event.nrnReturnConfig.shippingFeeType == 'fixed'}">
																			<label style="text-align: left; font-size: 13px;" class="labelfix">Deadweight
																				calculation</label>
																			<div class="slim-scroll-bar">
																			<table class="table-bordered shippedtab"
																				style="border-spacing: 0 5px;width: 100%;">
																				<thead>
																					<tr>
																						<th>Slab(gms)</th>
																						<th>Value</th>
																						<th>Price</th>
																					</tr>
																				</thead>
																				<tbody>
																					<c:if
																						test="${!empty event.shippingfeeWeightFixedList}">
																						<c:forEach
																							items="${event.shippingfeeWeightFixedList}"
																							var="shippingfee" varStatus="loop">
																							<tr>
																								<td>${shippingfee.criteria}</td>
																								<td><label class="labelfix">${shippingfee.range}</label></td>
																								<td><label class="labelfix">${shippingfee.value}</label></td>
																							</tr>
																						</c:forEach>
																					</c:if>
																				</tbody>
																			</table>
																			</div>


																			<label style="text-align: left; font-size: 13px;" class="labelfix">Volumetric
																				calculation= (lxbxh)(cm)/5000</label>
																			<div class="slim-scroll-bar">
																			<table class="table-bordered shippedtab"
																				style="border-spacing: 0 5px;width: 100%;">
																				<thead>
																					<tr>
																						<th>Slab(gms)</th>
																						<th>Value</th>
																						<th>Price</th>
																					</tr>
																				</thead>
																				<tbody>
																					<c:if
																						test="${!empty event.shippingfeeVolumeFixedList}">
																						<c:forEach
																							items="${event.shippingfeeVolumeFixedList}"
																							var="shippingfee" varStatus="loop">
																							<tr>
																								<td>${shippingfee.criteria}</td>
																								<td><label class="labelfix">${shippingfee.range}</label></td>
																								<td><label class="labelfix">${shippingfee.value}</label></td>
																							</tr>
																						</c:forEach>
																					</c:if>
																				</tbody>
																			</table>
																			</div>
																		</c:if>
																	</div>
																</td>
															</tr>

															<tr>
																<td style="width: 50%;"><label class="labelfix">SERVICE
																		TAX</label></td>
																<td>
																	<div>
																		<label class="labelfix">${chargeMap.serviceTax} %</label>
																	</div>
																</td>
															</tr>
														</tbody>
													</table>



												</div>
											</div>
											<div class="tab-pane" id="tab-2">
												<div class="panel-body" style="margin-top: -18px;">
													<div class="add-company">
														<hr>
														<div class="col-sm-12">
															<div class="col-sm-4"></div>
															<div class="col-sm-4" style="text-align: center;">
																<label
																	style="font-size: 12px; font-weight: 700; text-align: center;">SELLER
																	FAULT</label>
															</div>
															<div class="col-sm-4" style="text-align: center;">
																<label
																	style="font-size: 12px; font-weight: 700; text-align: center;">BUYER
																	RETURN</label>
															</div>
														</div>
														<div class="col-lg-12">
															<div class="col-sm-12 m-l">
																<div class="radio col-sm-4">
																	<label
																		style="position: relative; right: 30px; font-size: 12px; font-weight: 600;">RETURN</label>
																</div>
																<div class="col-sm-4" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.retCharSFType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="1" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.retCharSFType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="2" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="3" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
																<div class="col-sm-4" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.retCharBRType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="5" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.retCharBRType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="6" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="4" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
															</div>	
															<div class="col-sm-12">
																<div class="col-sm-4"></div>
																<div class="col-sm-4 radio1" id="blk-1">
																	<div class="row">
																		<div class="col-md-12">
																			<label class="labelfix">Fixed Amount :
																				${chargeMap.retCharSFFixedAmt} %</label>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4 radio1" id="blk-2">
																	<div class="row">
																		<div class="col-md-12">
																			<c:if test="${chargeMap.retCharSFVarFixedAmt != null}">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.retCharSFVarFixedAmt}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.retCharSFPercentSP != null}">
																				<label class="labelfix">% of SP :
																					${chargeMap.retCharSFPercentSP}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.retCharSFPercentPCC != null}">
																				<label class="labelfix">% of Commision :
																					${chargeMap.retCharSFPercentPCC}</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.retCharSFFF == 'true'}">
																				<label class="labelfix">Fixed Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.retCharSFShipFee == 'true'}">
																				<label class="labelfix">Shipping Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.retCharSFRevShipFee == 'true'}">
																				<label class="labelfix">Reverse Shipping Fee</label>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.retCharSFPCC == 'true'}">
																				<label class="labelfix">Payment Collection
																					Charges</label>
																			</c:if>
																		</div>
																	</div>
																</div>															
															</div>
															<div class="col-sm-12">
																<div class="col-sm-4"></div>
																<div class="col-sm-4"></div>
																<div class="col-sm-4 radio1" id="blk-5">
																	<div class="row">
																		<div class="col-md-12">
																			<label class="labelfix">Fixed Amount :
																				${chargeMap.retCharBRFixedAmt} %</label>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4 radio1" id="blk-6">
																	<div class="row">
																		<div class="col-md-12">
																			<c:if
																				test="${chargeMap.retCharBRVarFixedAmt != null}">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.retCharBRVarFixedAmt}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.retCharBRPercentSP != null}">
																				<label class="labelfix">% of SP :
																					${chargeMap.retCharBRPercentSP}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.retCharBRPercentPCC != null}">
																				<label class="labelfix">% of Commision :
																					${chargeMap.retCharBRPercentPCC}</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.retCharBRFF == 'true'}">
																				<label class="labelfix">Fixed Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.retCharBRShipFee == 'true'}">
																				<label class="labelfix">Shipping Fee</label>
																				<br>
																			</c:if>
																		</div>
																	</div>
																</div>
															</div>
														</div>															
															
														<div class="col-lg-12">
															<div class="col-sm-12 m-l">
																<div class="radio col-sm-4">

																	<label
																		style="position: relative; right: 30px; font-size: 12px; font-weight: 600;">RTO</label>

																</div>
																<div class="col-sm-4" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.RTOCharSFType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="7" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.RTOCharSFType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="8" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="9" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
																<div class="col-sm-4" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.RTOCharBRType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="11" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.RTOCharBRType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="12" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="10" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
															</div>
															<div class="col-sm-12">
																<div class="col-sm-4"></div>
																<div class="col-sm-4 radio1" id="blk-7">
																	<div class="row">
																		<div class="col-md-12">
																			<label class="labelfix">Fixed Amount :
																				${chargeMap.RTOCharSFFixedAmt} %</label>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4 radio1" id="blk-8">
																	<div class="row">
																		<div class="col-md-12">
																			<c:if test="${chargeMap.RTOCharSFVarFixedAmt != null}">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.RTOCharSFVarFixedAmt}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.RTOCharSFPercentSP != null}">
																				<label class="labelfix">% of SP :
																					${chargeMap.RTOCharSFPercentSP}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.RTOCharSFPercentPCC != null}">
																				<label class="labelfix">% of Commision :
																					${chargeMap.RTOCharSFPercentPCC}</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.RTOCharSFFF == 'true'}">
																				<label class="labelfix">Fixed Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.RTOCharSFShipFee == 'true'}">
																				<label class="labelfix">Shipping Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.RTOCharSFRevShipFee == 'true'}">
																				<label class="labelfix">Reverse Shipping Fee</label>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.RTOCharSFPCC == 'true'}">
																				<label class="labelfix">Payment Collection
																					Charges</label>
																			</c:if>
																		</div>
																	</div>
																</div>
															</div>
															<div class="col-sm-12">
																<div class="col-sm-4"></div>
																<div class="col-sm-4"></div>
																<div class="col-sm-4 radio1" id="blk-11">
																	<div class="row">
																		<div class="col-md-12">
																			<label class="labelfix">Fixed Amount :
																				${chargeMap.RTOCharBRFixedAmt} %</label>
	
																		</div>
																	</div>
																</div>
																<div class="col-sm-4 radio1" id="blk-12">
																	<div class="row">
																		<div class="col-md-12">
																			<c:if test="${chargeMap.RTOCharBRVarFixedAmt != null}">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.RTOCharBRVarFixedAmt}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.RTOCharBRPercentSP != null}">
																				<label class="labelfix">% of SP :
																					${chargeMap.RTOCharBRPercentSP}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.RTOCharBRPercentPCC != null}">
																				<label class="labelfix">% of Commision :
																					${chargeMap.RTOCharBRPercentPCC}</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.RTOCharBRFF == 'true'}">
																				<label class="labelfix">Fixed Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.RTOCharBRShipFee == 'true'}">
																				<label class="labelfix">Shipping Fee</label>
																				<br>
																			</c:if>
																		</div>
																	</div>
																</div>
															</div>								
														</div>
														<div class="col-lg-12">
															<div class="col-sm-12 m-l">
																<div class="radio col-sm-4">
																	<label
																		style="position: relative; right: 31px; font-size: 12px; font-weight: 600;">REPLACEMENT</label>
																</div>
																<div class="col-sm-4" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.repCharSFType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="15" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.repCharSFType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="14" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="13" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
																<div class="col-sm-4" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.repCharBRType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="18" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.repCharBRType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="17" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="16" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
															</div>
															<div class="col-sm-12">
																<div class="col-sm-4"></div>
																<div class="col-sm-4 radio1" id="blk-14">
																	<div class="row">
																		<div class="col-md-12">
																			<c:if
																				test="${chargeMap.repCharSFVarFixedAmt != null}">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.repCharSFVarFixedAmt}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.repCharSFPercentSP != null}">
																				<label class="labelfix">% of SP :
																					${chargeMap.repCharSFPercentSP}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.repCharSFPercentPCC != null}">
																				<label class="labelfix">% of Commision :
																					${chargeMap.repCharSFPercentPCC}</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.repCharSFFF == 'true'}">
																				<label class="labelfix">Fixed Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.repCharSFShipFee == 'true'}">
																				<label class="labelfix">Shipping Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.repCharSFRevShipFee == 'true'}">
																				<label class="labelfix">Reverse Shipping Fee</label>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.repCharSFPCC == 'true'}">
																				<label class="labelfix">Payment Collection
																					Charges</label>
																			</c:if>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4 radio1" id="blk-15">
																	<div class="row">
																		<div class="col-md-12">
																			<label class="labelfix">Fixed Amount :
																				${chargeMap.repCharSFFixedAmt} %</label>
																		</div>
																	</div>
																</div>
															</div>
															<div class="col-sm-12">
																<div class="col-sm-4"></div>
																<div class="col-sm-4"></div>
																<div class="col-sm-4 radio1" id="blk-17">
																	<div class="row">
																		<div class="col-md-12">
																			<c:if test="${chargeMap.repCharBRFixedAmt != null}">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.repCharBRFixedAmt}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMaprepCharBRPercentSP != null}">
																				<label class="labelfix">% of SP :
																					${chargeMap.repCharBRPercentSP}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.repCharBRPercentPCC != null}">
																				<label class="labelfix">% of Commision :
																					${chargeMap.repCharBRPercentPCC}</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.repCharBRFF == 'true'}">
																				<label class="labelfix">Fixed Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.repCharBRShipFee == 'true'}">
																				<label class="labelfix">Shipping Fee</label>
																				<br>
																			</c:if>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4 radio1" id="blk-18">
																	<div class="row">
																		<div class="col-md-12">
																			<input type="text"
																				value="${chargeMap.repCharBRFixedAmt} %"
																				class="form-control" disabled>
																		</div>
																	</div>
																</div>
															</div>
														</div>
														<div class="col-lg-12 m-t">
															<div class="col-sm-12 m-l">
																<div class="col-sm-4">
																	<label
																		style="position: relative; right: 32px; font-size: 12px; font-weight: 600;">PARTIAL
																		DELIVERY</label>
																</div>
																<div class="col-sm-4" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.PDCharSFType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="20" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.PDCharSFType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="19" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="21" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
																<div class="col-sm-4" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.PDCharBRType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="23" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.PDCharBRType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="22" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="24" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
															</div>
															<div class="col-sm-12">
																<div class="col-sm-4"></div>
																<div class="col-sm-4 radio1" id="blk-19">
																	<div class="row">
																		<div class="col-md-12">
																			<c:if test="${chargeMap.PDCharSFVarFixedAmt != null}">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.PDCharSFVarFixedAmt}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.PDCharSFPercentSP != null}">
																				<label class="labelfix">% of SP :
																					${chargeMap.PDCharSFPercentSP}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.PDCharSFPercentPCC != null}">
																				<label class="labelfix">% of Commision :
																					${chargeMap.PDCharSFPercentPCC}</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.PDCharSFFF == 'true'}">
																				<label class="labelfix">Fixed Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.PDCharSFShipFee == 'true'}">
																				<label class="labelfix">Shipping Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.PDCharSFRevShipFee == 'true'}">
																				<label class="labelfix">Reverse Shipping Fee</label>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.PDCharSFPCC == 'true'}">
																				<label class="labelfix">Payment Collection
																					Charges</label>
																			</c:if>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4 radio1" id="blk-20">
																	<div class="row">
																		<div class="col-md-12">
																			<label class="labelfix">Fixed Amount :
																				${chargeMap.PDCharSFFixedAmt} %</label>

																		</div>
																	</div>
																</div>

															</div>
															<div class="col-sm-12">
																<div class="col-sm-4"></div>
																<div class="col-sm-4"></div>
																<div class="col-sm-4 radio1" id="blk-22">
																	<div class="row">
																		<div class="col-md-12">
																			<c:if test="${chargeMap.PDCharBRVarFixedAmt != null}">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.PDCharBRVarFixedAmt}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.PDCharBRPercentSP != null}">
																				<label class="labelfix">% of SP :
																					${chargeMap.PDCharBRPercentSP}</label>
																				<br>
																			</c:if>
																			<c:if test="${chargeMap.PDCharBRPercentPCC != null}">
																				<label class="labelfix">% of Commision :
																					${chargeMap.PDCharBRPercentPCC}</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.PDCharBRFF == 'true'}">
																				<label class="labelfix">Fixed Fee</label>
																				<br>
																			</c:if>
																			<c:if
																				test="${event.nrnReturnConfig.PDCharBRShipFee == 'true'}">
																				<label class="labelfix">Shipping Fee</label>
																				<br>
																			</c:if>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4 radio1" id="blk-23">
																	<div class="row">
																		<div class="col-md-12">
																			<label class="labelfix">Fixed Amount :
																				${chargeMap.PDCharBRFixedAmt} %</label>

																		</div>
																	</div>
																</div>

															</div>
														</div>
														<div class="col-lg-12">
															<hr>
															<div class="col-sm-12">
																<div class="col-sm-3"></div>
																<div class="col-sm-3" style="text-align: center;">
																	<label
																		style="font-size: 12px; font-weight: 700; text-align: center;">BEFORE
																		RTD</label>
																</div>
																<div class="col-sm-3" style="text-align: center;">
																	<label
																		style="font-size: 12px; font-weight: 700; text-align: center;">AFTER
																		RTD</label>
																</div>
																<div class="col-sm-3" style="text-align: center;">
																	<label
																		style="font-size: 12px; font-weight: 700; text-align: center;">BUYER
																		FAULT</label>
																</div>
															</div>
															<div class="col-sm-12 m-l">
																<div class="col-sm-3">
																	<label
																		style="position: relative; right: 31px; font-size: 12px; font-weight: 600;">CANCELLATION
																	</label>
																</div>
																<div class="col-sm-3" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.canCharSFBFRTDType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="45" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.canCharSFBFRTDType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="46" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="47" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
																<div class="col-sm-3" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.canCharSFARTDType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="48" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.canCharSFARTDType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="49" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="50" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
																<div class="col-sm-3" style="padding: 2px;">
																	<c:choose>
																		<c:when
																			test="${event.nrnReturnConfig.canCharBRType == 'fixed'}">
																			<div class="radio">
																				<button type="button" id="51" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Fixed</font><span class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:when
																			test="${event.nrnReturnConfig.canCharBRType == 'variable'}">
																			<div class="radio">
																				<button type="button" id="52" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">Variable</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:when>
																		<c:otherwise>
																			<div class="radio">
																				<button type="button" id="53" name="toggler"
																					class="form-control" value=""
																					style="background: #eee; font-size: 12px;">
																					<font color="green">No-Charges</font><span
																						class="caret"></span>
																				</button>
																			</div>
																		</c:otherwise>
																	</c:choose>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-3"></div>
																	<div class="col-sm-3 radio1" id="blk-46">
																		<div class="row">
																			<div class="col-md-12">
																				<c:if
																					test="${chargeMap.canCharSFBFRTDVarFixedAmt != null}">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.canCharSFBFRTDVarFixedAmt}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${chargeMap.canCharSFBFRTDPercentSP != null}">
																					<label class="labelfix">% of SP :
																						${chargeMap.canCharSFBFRTDPercentSP}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${chargeMap.canCharSFBFRTDPercentPCC != null}">
																					<label class="labelfix">% of Commision :
																						${chargeMap.canCharSFBFRTDPercentPCC}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharSFBRTDFF == 'true'}">
																					<label class="labelfix">Fixed Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharSFBRTDShipFee == 'true'}">
																					<label class="labelfix">Shipping Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharSFBRTDRevShipFee == 'true'}">
																					<label class="labelfix">Reverse Shipping
																						Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharSFPCC == 'true'}">
																					<label class="labelfix">Payment Collection
																						Charges</label>
																				</c:if>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-3 radio1" id="blk-45">
																		<div class="row">
																			<div class="row">
																				<div class="col-md-12">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.canCharSFBFRTDFixedAmt} %</label>
																				</div>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-3"></div>
																	<div class="col-sm-3"></div>

																</div>
																<div class="col-sm-12">
																	<div class="col-sm-3"></div>
																	<div class="col-sm-3"></div>
																	<div class="col-sm-3 radio1" id="blk-49">
																		<div class="row">
																			<div class="col-md-12">
																				<c:if
																					test="${chargeMap.canCharSFVarFixedAmt != null}">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.canCharSFVarFixedAmt}</label>
																					<br>
																				</c:if>
																				<c:if test="${chargeMap.canCharSFPercentSP != null}">
																					<label class="labelfix">% of SP :
																						${chargeMap.canCharSFPercentSP}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${chargeMap.canCharSFPercentPCC != null}">
																					<label class="labelfix">% of Commision :
																						${chargeMap.canCharSFPercentPCC}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharSFFF == 'true'}">
																					<label class="labelfix">Fixed Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharSFShipFee == 'true'}">
																					<label class="labelfix">Shipping Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharSFARTDRevShipFee == 'true'}">
																					<label class="labelfix">Reverse Shipping
																						Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharSFBRTDPCC == 'true'}">
																					<label class="labelfix">Payment Collection
																						Charges</label>
																				</c:if>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-3 radio1" id="blk-48">
																		<div class="row">
																			<div class="col-md-12">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.canCharSFFixedAmt} %</label>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-3"></div>
																</div>
																<div class="col-sm-12">
																	<div class="col-sm-3"></div>
																	<div class="col-sm-3"></div>
																	<div class="col-sm-3"></div>
																	<div class="col-sm-3 radio1" id="blk-52">
																		<div class="row">
																			<div class="col-md-12">
																				<c:if
																					test="${chargeMap.canCharBRVarFixedAmt != null}">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.canCharBRVarFixedAmt}</label>
																					<br>
																				</c:if>
																				<c:if test="${chargeMap.canCharBRPercentSP != null}">
																					<label class="labelfix">% of SP :
																						${chargeMap.canCharBRPercentSP}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${chargeMap.canCharBRPercentPCC != null}">
																					<label class="labelfix">% of Commision :
																						${chargeMap.canCharBRPercentPCC}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharBRFF == 'true'}">
																					<label class="labelfix">Fixed Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharBRShipFee == 'true'}">
																					<label class="labelfix">Shipping Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.canCharBRRevShipFee == 'true'}">
																					<label class="labelfix">Reverse Shipping
																						Fee</label>
																				</c:if>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-3 radio1" id="blk-51">
																		<div class="row">
																			<div class="col-md-12">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.canCharSFPercentPCC} %</label>
																			</div>
																		</div>
																	</div>

																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4"></div>
																	<div class="col-sm-4"></div>
																	<div class="col-sm-4 radio1" id="blk-22">
																		<div class="row">
																			<div class="col-md-12">
																				<c:if
																					test="${chargeMap.PDCharBRVarFixedAmt != null}">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.PDCharBRVarFixedAmt}</label>
																					<br>
																				</c:if>
																				<c:if test="${chargeMap.PDCharBRPercentSP != null}">
																					<label class="labelfix">% of SP :
																						${chargeMap.PDCharBRPercentSP}</label>
																					<br>
																				</c:if>
																				<c:if test="${chargeMap.PDCharBRPercentPCC != null}">
																					<label class="labelfix">% of Commision :
																						${chargeMap.PDCharBRPercentPCC}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.PDCharBRFF == 'true'}">
																					<label class="labelfix">Fixed Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.PDCharBRShipFee == 'true'}">
																					<label class="labelfix">Shipping Fee</label>
																					<br>
																				</c:if>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-4 radio1" id="blk-23">
																		<div class="row">
																			<div class="col-md-12">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.PDCharBRFixedAmt} %</label>

																			</div>
																		</div>
																	</div>

																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4"></div>
																	<div class="col-sm-4 radio1" id="blk-19">
																		<div class="row">
																			<div class="col-md-12">
																				<c:if
																					test="${chargeMap.PDCharSFVarFixedAmt != null}">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.PDCharSFVarFixedAmt}</label>
																					<br>
																				</c:if>
																				<c:if test="${chargeMap.PDCharSFPercentSP != null}">
																					<label class="labelfix">% of SP :
																						${chargeMap.PDCharSFPercentSP}</label>
																					<br>
																				</c:if>
																				<c:if test="${chargeMap.PDCharSFPercentPCC != null}">
																					<label class="labelfix">% of Commision :
																						${chargeMap.PDCharSFPercentPCC}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.PDCharSFFF == 'true'}">
																					<label class="labelfix">Fixed Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.PDCharSFShipFee == 'true'}">
																					<label class="labelfix">Shipping Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.PDCharSFRevShipFee == 'true'}">
																					<label class="labelfix">Reverse Shipping
																						Fee</label>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.PDCharSFPCC == 'true'}">
																					<label class="labelfix">Payment Collection
																						Charges</label>
																				</c:if>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-4 radio1" id="blk-20">
																		<div class="row">
																			<div class="col-md-12">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.PDCharSFFixedAmt} %</label>

																			</div>
																		</div>
																	</div>

																</div>
																<div class="col-sm-12">
																	<div class="col-sm-4"></div>
																	<div class="col-sm-4"></div>
																	<div class="col-sm-4 radio1" id="blk-22">
																		<div class="row">
																			<div class="col-md-12">
																				<c:if
																					test="${chargeMap.PDCharBRVarFixedAmt != null}">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.PDCharBRVarFixedAmt}</label>
																					<br>
																				</c:if>
																				<c:if test="${chargeMap.PDCharBRPercentSP != null}">
																					<label class="labelfix">% of SP :
																						${chargeMap.PDCharBRPercentSP}</label>
																					<br>
																				</c:if>
																				<c:if test="${chargeMap.PDCharBRPercentPCC != null}">
																					<label class="labelfix">% of Commision :
																						${chargeMap.PDCharBRPercentPCC}</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.PDCharBRFF == 'true'}">
																					<label class="labelfix">Fixed Fee</label>
																					<br>
																				</c:if>
																				<c:if
																					test="${event.nrnReturnConfig.PDCharBRShipFee == 'true'}">
																					<label class="labelfix">Shipping Fee</label>
																					<br>
																				</c:if>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-4 radio1" id="blk-23">
																		<div class="row">
																			<div class="col-md-12">
																				<label class="labelfix">Fixed Amount :
																					${chargeMap.PDCharBRFixedAmt} %</label>

																			</div>
																		</div>
																	</div>

																</div>
															</div>
															<div class="col-sm-12 m-l">
																<hr>
																<div class="col-sm-3">
																	<label
																		style="position: relative; right: 31px; font-size: 12px; font-weight: 600;">REVERSE SHIPPING
																	</label>
																</div>
																<div class="col-sm-5">
																	<c:choose>
																		<c:when	test="${event.nrnReturnConfig.revShippingFeeType == 'revShipFeePCC'}">
																			<label class="labelfix">% of Shipping Fee :	${chargeMap.revShipFeePCC}</label><br>
																		</c:when>
																		<c:when	test="${event.nrnReturnConfig.revShippingFeeType == 'revShipFeeGRT'}">
																			<label class="labelfix"><u>Which Ever Is Higher</u></label><br>
																			<label class="labelfix">Flat Amount :	${chargeMap.revShipFeeFlatAmt}</label><br>
																			<label class="labelfix">% of Market Fee :	${chargeMap.revShipFeePCCMF}</label><br>
																		</c:when>
																		<c:when	test="${event.nrnReturnConfig.revShippingFeeType == 'revShipFeeFF'}">
																			<label class="labelfix">Flat Amount :	${chargeMap.revShipFeeFF}</label><br>
																		</c:when>
																		<c:when	test="${event.nrnReturnConfig.revShippingFeeType == 'revShipFeeShipFee'}">
																			<label class="labelfix">Same as Shipping Fee</label><br>
																		</c:when>
																		<c:when	test="${event.nrnReturnConfig.revShippingFeeType == 'revShipFeeVar'}">
																			<div class="col-sm-6" style="padding: 0px;">
																				<label class="labelfix"><u>Dead Weight</u></label><br>
																					Amount : ${chargeMap.revShipFeeDWAmt}<br>
																					Per Weight : ${chargeMap.revShipFeeDWPW}<br>
																					Min Weight : ${chargeMap.revShipFeeDWMW}<br>
																			</div>
																			<div class="col-sm-6" style="padding: 0px;">
																				<label class="labelfix"><u>Volume Weight</u></label><br>
																					Amount : ${chargeMap.revShipFeeVWAmt}<br>
																					Per Weight : ${chargeMap.revShipFeeVWPW}<br>
																					Min Weight : ${chargeMap.revShipFeeVWMW}
																			</div>
																		</c:when>
																		<c:otherwise>
																			<label class="labelfix">None</label>
																		</c:otherwise>																		
																	</c:choose>
																</div>
																<div class="col-sm-4"></div>
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
			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>
		</div>


	</div>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>
	<script type="text/javascript" src="/O2R/seller/js/jquery.jcarousel.min.js"></script>
	<script type="text/javascript" src="/O2R/seller/js/jcarousel.responsive.js"></script>
	<script src="/O2R/seller/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- iCheck -->
	<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script>
	<!-- Switchery -->
	<script src="/O2R/seller/js/plugins/switchery/switchery.js"></script>
	<script src="/O2R/seller/js/pickList1.js"></script>
	<script src="/O2R/seller/js/inspinia.js"></script>
	
	<script type="text/javascript">

$(document).ready( function() {


   	 $('.carousel').carousel({
		pause: true,
		interval: false
	});
   	$('.slim-scroll-bar').slimScroll({
        height: '100px',
        railOpacity: 0.4
    });

	var clickEvent = false;
	$('#myCarousel').on('click', '.nav a', function() {
        clickEvent = true;
        $('.nav li').removeClass('active');
        $(this).parent().addClass('active');        
	}).on('slid.bs.carousel', function(e) {
    if(!clickEvent) {
        var count = $('.nav').children().length -1;
        var current = $('.nav li.active');
        current.removeClass('active').next().addClass('active');
        var id = parseInt(current.data('slide-to'));
        if(count == id) {
            $('.nav li').first().addClass('active');    
        }        
   	 }
    	clickEvent = false;

	});
	
	
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
	
	
		

	<script type="text/javascript">
        
        function handleRadioEvent(thiss){
     	   var x=thiss.id;

     	   if(!(x>=1 &&  x<=10))
     	            $('.radio1').hide();

     	            $("#blk-"+ x).slideDown();

     	           for(var a=0;a<10;a++){
                       if(a != x){
                           $("#blk-"+a).slideUp();                    
                       }else{
                           $("#blk-"+a).slideDown();
                       }
                    }	    
    	 }        
	    
	 $("[name=toggler]").click(function(){
         var x=this.id;
         if(!(x>=1 &&  x<=29))
             $('.radio1').hide();

         $("#blk-"+ x).slideDown();
         for(var a=0;a<29;a++){
             if(a != x){
                 $("#blk-"+a).slideUp();                    
             }else{
                 $("#blk-"+a).slideDown();
             }
         }
	 
     });
    
</script>
<script>      
         var val = 
         {
            01: {id:01, text: 'Andhra Pradesh'},
            02: {id:02,text:'Andaman and Nicobar Islands'},
            03: {id:03, text: 'Arunachal Pradesh'},
            04: {id:04, text:'Assam'},
            05: {id:05, text:'Chhattisgarh'},
            06: {id:06, text:'Chandigarh'},
            07: {id:07, text:'Dadra and Nagar Haveli'},
            08: {id:08, text:'Daman and Diu'},
            09: {id:09, text:'Delhi'},
            10: {id:10, text:'Goa'},
            11: {id:11, text:'Gujarat'},
            12: {id:12, text:'Haryana'},
            13: {id:13, text:'Himachal Pradesh'},
            14: {id:14, text:'Jammu and Kashmir'},
            15: {id:15, text:'Jharkhand'},
            16: {id:16, text:'Karnataka'},
            17: {id:17, text:'Kerala'},
            18: {id:18, text:'Lakshadweep'},
            19: {id:19, text:'Madhya Pradesh'},
            20: {id:20, text:'Maharashtra'},
            21: {id:21, text:'Manipur'},
            22: {id:22, text:'Meghalaya'},
            23: {id:23, text:'Mizoram'},
            24: {id:24, text:'Nagaland'},
            25: {id:25, text:'Odisha'},
            26: {id:26, text:'Punjab'},
            27: {id:27, text:'Pondicherry'},
            28: {id:28, text:'Rajasthan'},
            29: {id:29, text:'Sikkim'},
            30: {id:30, text:'Tamil Nadu'},
            31: {id:31, text:'Telangana'},
            32: {id:32, text:'Tripura'},
            33: {id:33, text:'Uttar Pradesh'},
            34: {id:34, text:'Uttarakhand'},
            35: {id:35, text:'West Bengal'}
         };
         var pick = $("#pickList").pickList({data: val});
      </script>

</body>
</html>
