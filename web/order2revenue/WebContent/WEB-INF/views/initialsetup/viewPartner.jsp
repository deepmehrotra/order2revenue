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

	<link href="/O2R/seller/css/bootstrap.min.css" rel="stylesheet">
    <link href="/O2R/seller/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="/O2R/seller/css/animate.css" rel="stylesheet">
    <link href="/O2R/seller/css/style.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/O2R/seller/css/jcarousel.responsive.css">


	<link href="/O2R/seller/css/plugins/datapicker/datepicker3.css"	rel="stylesheet">
	<link href="/O2R/seller/css/plugins/switchery/switchery.css" rel="stylesheet">
	<link href="/O2R/seller/css/plugins/iCheck/custom.css" rel="stylesheet">

	<!-- <script src="/O2R/seller/js/inspinia.js"></script> -->
    


</head>
<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight">
			
				<div class="row">
                    <div class="col-lg-12">
                        <div id="myCarousel" class="carousel slide" data-ride="carousel">
                             <div class="jcarousel-wrapper">
                                <div class="jcarousel">
                                     <ul class="nav nav-pills nav-justified">
                                     	<c:if test="${!empty partnerList }">
                                     		<c:forEach items="${partnerList}" var="partner" varStatus="loop">
												<li data-target="#myCarousel" data-slide-to="0" class="active">
                                            		<a href="viewPartner.html?pcId=${partner.pcId}">
                                                		<img src="${partner.pcLogoUrl}" alt="Image 1">
                                            		</a>
                                        		</li>
											</c:forEach>
                                     	</c:if>
                                        <!-- <li data-target="#myCarousel" data-slide-to="0" class="active">
                                            <a href="#about">
                                                <img src="img/Snapdeal.jpg" alt="Image 1">
                                            </a>
                                        </li>
                                        <li data-target="#myCarousel" data-slide-to="1">
                                            <a href="#">
                                                <img src="img/Flipkart.jpg" alt="Image 2">
                                            </a>
                                        </li>
                                        <li data-target="#myCarousel" data-slide-to="2">
                                            <a href="#">
                                                <img src="img/Snapdeal.jpg" alt="Image 3">
                                            </a>
                                        </li>
                                        <li data-target="#myCarousel" data-slide-to="3">
                                            <a href="#">
                                                <img src="img/Snapdeal.jpg" alt="Image 4">
                                            </a>
                                        </li>
                                        <li data-target="#myCarousel" data-slide-to="4">
                                            <a href="#">
                                                <img src="img/Snapdeal.jpg" alt="Image 5">
                                            </a>
                                        </li>
                                        <li data-target="#myCarousel" data-slide-to="5">
                                            <a href="#">
                                                <img src="img/Flipkart.jpg" alt="Image 6">
                                            </a>
                                        </li>
                                        <li data-target="#myCarousel" data-slide-to="6">
                                            <a href="#">
                                                <img src="img/Flipkart.jpg" alt="Image 7">
                                            </a>
                                        </li>
                                        <li data-target="#myCarousel" data-slide-to="7">
                                            <a href="#">
                                                <img src="img/Flipkart.jpg" alt="Image 8">
                                            </a>
                                        </li> -->
                                    </ul>
                                </div>
                                <a href="#" class="jcarousel-control-prev">&lsaquo;</a>
                                <a href="#" class="jcarousel-control-next">&rsaquo;</a>
                            </div>
                            <div class="carousel-inner">
                                <div class="item active">
                                    <div class="col-lg-12">  
                                        <div class="float-e-margins col-lg-4">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsebasic">Basic Settings</a> </h4>
                                                </div>
                                                <div id="collapsebasic" class="panel-collapse collapse in">
                                                    <div class="panel-body">
                                                        <div class="ibox-content add-company view-order">
                                                            <table style="border-spacing: 0 5px;">
                                                                <tbody>
                                                                    <tr>
                                                                        <td><label>ALIAS NAME </label></td>
                                                                        <td><label class="labelfix">: ${partner.pcDesc}</label></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td><label>TDS APPLICABLE</label></td>
                                                                        <td><label class="labelfix">: ${partner.tdsApplicable}</label></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td><label>RETURN LIMIT</label></td>
                                                                        <td><label class="labelfix">: ${partner.maxReturnAcceptance}</label></td><%-- <input type="text" value="${partner.maxReturnAcceptance}" class="form-control" disabled> --%>
                                                                    </tr>
                                                                    <tr>
                                                                    	<td><label>PAYMENT TYPE</label></td>
                                                                    	<td><c:choose>
									    										<c:when test="${partner.paymentType == 'paymentcycle'}">
									    											<label class="labelfix">: Payment Cycle</label>
									    										</c:when>
									   											<c:when test="${partner.paymentType == 'datewisepay'}">
										    									   	<label class="labelfix">: Day Wise</label>
										    									</c:when>
									    										<c:otherwise>
									     											<label class="labelfix">: Monthly</label>
									        									</c:otherwise>
																			</c:choose>
                                                                    	</td>	                                                                    							    										
                                                                    </tr>
                                                                    <tr>
                                                                        <td><label>PAYMENT CYCLE</label></td>
                                                                        <td>
                                                                        <c:choose>
									    										<c:when test="${partner.paymentType == 'paymentcycle'}">
																					<label class="labelfix">: ${partner.startcycleday} to ${partner.paycycleduration} ,payment on ${partner.paydaysfromstartday}</label>
									    										</c:when>
									   											<c:when test="${partner.paymentType == 'datewisepay'}">
										    									<c:choose>
										    										<c:when test="${partner.isshippeddatecalc}">
										    											<label class="labelfix">: ${partner.noofdaysfromshippeddate} days from shipped date</label>
										    									    	<%-- <input type="text" value="${partner.noofdaysfromshippeddate} days from shipped date" class="form-control" disabled> --%>
										    										</c:when>
										    	 									<c:otherwise>
										    	 										<label class="labelfix">: ${partner.noofdaysfromshippeddate} days from delivery date</label>
										    	 										<%-- <input type="text" value="${partner.noofdaysfromshippeddate} days from delivery date" class="form-control" disabled> --%>
										    	 									</c:otherwise>
										    									</c:choose>									       
									   	 										</c:when>
									    										<c:otherwise>
									     											<input type="text" value="${partner.monthlypaydate} of every month" class="form-control" disabled>
									        									</c:otherwise>
																		</c:choose>
																		</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                            <h4 style="text-align: center;">STATE DISTRIBUTION </h4>
                                                            <div class="row">
                                                            <div class="col-lg-12">
                                                            
                                                               <div id="pickList" form="form2">

                                                               </div>
	                                                            <input type="hidden" id="hidlocalList" value='${partner.nrnReturnConfig.localList}'/>
                                                    			<input type="hidden" id="hidzonalList" value='${partner.nrnReturnConfig.zonalList}'/>
                                                    			<input type="hidden" id="hidmetroList" value='${partner.nrnReturnConfig.metroList}'/>
                                                    			<input type="hidden" id="hidnationalList" value='${partner.nrnReturnConfig.nationalList}'/>
                                                       		</div>
                                                   		</div>

                                               </div>
                                           </div>
                                       </div>
                                   </div>
                                   </div>
										<div class="float-e-margins col-lg-4">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title ibox-title">
														<a data-toggle="collapse" data-parent="#accordion" href="#collapseship">N/R Config</a>
													</h4>
												</div>
												<div id="collapseship" class="panel-collapse collapse in">
													<div class="panel-body">
														<div class="ibox-content add-company view-order">
															<table class="table table hover"
																style="border-spacing: 0 5px;">
																<tbody>
																	<tr data-toggle="collapse" data-target="#accordion"
																		class="clickable">
																		<td style="width: 50%;"><label>COMMISSION</label></td>
																		<td>
																			<div>
																				<c:if test="${partner.nrnReturnConfig.commissionType == 'fixed' }">
																					<button class="btn btn-grey" id="button1">Fixed</button>
																				</c:if>
																				<c:if test="${partner.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<button class="btn btn-grey" id="button1">Category Wise</button>
																				</c:if>																				
																			</div>
																		</td>
																	</tr>

																	<tr>
																		<td colspan="10">
																			<div id="accordion" class="collapse">
																				<c:if test="${partner.nrnReturnConfig.commissionType == 'fixed' }">
																					<label class="labelfix">Fixed Amount : ${chargeMap.fixedCommissionPercent} %</label>
																				</c:if>
																				<c:if test="${partner.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<c:choose>
																						<c:when test="${!empty categoryMap}">
																							<c:forEach items="${categoryMap}" var="cat" varStatus="loop">
																								<div class="form-group col-md-12">
																									<label class="labelfix">${cat.key} : ${cat.value}</label>																									
																								</div>
																							</c:forEach>
																						</c:when>																						
																					</c:choose>
																				</c:if>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td style="width: 50%;"><label>FIXED FEE</label></td>
																		<td>
																			<c:if test="${chargeMap.fixedfeelt250 != null }">
																				<div><label class="labelfix">&lt;250 : ${chargeMap.fixedfeelt250}</label></div>
																				<div><label class="labelfix">&gt;250&&&lt;500 : ${chargeMap.fixedfeegt250lt500}</label></div>
																				<div><label class="labelfix">&gt;500 : ${chargeMap.fixedfeegt500}</label></div>																			
																			</c:if>
																			<c:if test="${chargeMap.fixedfeelt500Big != null }">
																				<div><label class="labelfix">&lt;500 : ${chargeMap.fixedfeelt500Big}</label></div>
																				<div><label class="labelfix">&gt;500 && &lt;1000 : ${chargeMap.fixedfeegt500lt1000}</label></div>
																				<div><label class="labelfix">&gt;1000 && &lt;10000 : ${chargeMap.fixedfeegt1000lt10000}</label></div>
																				<div><label class="labelfix">&gt;10000 : ${chargeMap.fixedfeegt10000}</label></div>																			
																			</c:if>
																			<c:if test="${chargeMap.fixedfeelt500 != null }">
																				<div><label class="labelfix">&lt;500 : ${chargeMap.fixedfeelt500}</label></div>
																				<div><label class="labelfix">&gt;500 : ${chargeMap.fixedfeegt500Big}</label></div>																			
																			</c:if>
																		</td>
																	</tr>
																	<tr>
																		<td style="width: 50%;"><label>PCC</label></td>
																		<td>
																			<c:if test="${partner.nrnReturnConfig.whicheverGreaterPCC == 'true'}">
																				<div><label class="labelfix">Which Ever Is Greater</label></div>
																			</c:if>
																			<c:if test="${chargeMap.percentSPPCC != null}">
																				<div><label class="labelfix">% of SP : ${chargeMap.percentSPPCC}</label></div>
																			</c:if>
																			<c:if test="${chargeMap.fixedAmtPCC != null}">
																				<div><label class="labelfix">Fixed AMT : ${chargeMap.fixedAmtPCC}</label></div>
																			</c:if>																			
																		</td>
																	</tr>
																	<tr data-toggle="collapse" data-target="#accordion2"
																		class="clickable">
																		<td style="width: 50%;"><label>Shipping	Fee</label></td>
																		<td>
																			<c:if test="${partner.nrnReturnConfig.shippingFeeType == 'fixed'}">
																				<button class="btn btn-grey" id="button3">Fixed Shipping Charges</button>
																			</c:if>
																			<c:if test="${partner.nrnReturnConfig.shippingFeeType == 'variable'}">
																				<button class="btn btn-grey" id="button3">Variable Shipping Charges</button>
																			</c:if>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="10">
																			<div id="accordion2" class="collapse">
																				<c:if test="${partner.nrnReturnConfig.shippingFeeType == 'variable'}">
																					<h5>Volume calculation= (lxbxh)(cm)/5</h5>
																					<table class="table-bordered" style="border-spacing: 0 5px;">
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
																								<td>&lt;500</td>
																								<td><label class="labelfix">${chargeMap.localvwlt500}</label></td>
																								<td><label class="labelfix">${chargeMap.zonalvwlt500}</label></td>
																								<td><label class="labelfix">${chargeMap.nationalvwlt500}</label></td>
																								<td><label class="labelfix">${chargeMap.metrovwlt500}</label></td>
																							</tr>
																							<tr>
																								<td>500 &gt; 1000</td>
																								<td><label class="labelfix">${chargeMap.localvwgt500lt1000}</label></td>
																								<td><label class="labelfix">${chargeMap.zonalvwgt500lt1000}</label></td>
																								<td><label class="labelfix">${chargeMap.nationalvwgt500lt1000}</label></td>
																								<td><label class="labelfix">${chargeMap.metrovwgt500lt1000}</label></td>
																							</tr>
																							<tr>
																								<td>1000 &gt; 1500</td>
																								<td><label class="labelfix">${chargeMap.localvwgt1000lt1500}</label></td>
																								<td><label class="labelfix">${chargeMap.zonalvwgt1000lt1500}</label></td>
																								<td><label class="labelfix">${chargeMap.nationalvwgt1000lt1500}</label></td>
																								<td><label class="labelfix">${chargeMap.metrovwgt1000lt1500}</label></td>
																							</tr>
																							<tr>
																								<td>1500 &gt; 5000</td>
																								<td><label class="labelfix">${chargeMap.localvwgt1500lt5000}</label></td>
																								<td><label class="labelfix">${chargeMap.zonalvwgt1500lt5000}</label></td>
																								<td><label class="labelfix">${chargeMap.nationalvwgt1500lt5000}</label></td>
																								<td><label class="labelfix">${chargeMap.metrovwgt1500lt5000}</label></td>
																							</tr>
																							<tr>
																								<td>add 1 kg</td>
																								<td><label class="labelfix">${chargeMap.localvwgt5000}</label></td>
																								<td><label class="labelfix">${chargeMap.zonalvwgt5000}</label></td>
																								<td><label class="labelfix">${chargeMap.nationalvwgt5000}</label></td>
																								<td><label class="labelfix">${chargeMap.metrovwgt5000}</label></td>
																							</tr>
																						</tbody>
																					</table>
																					<br>
																					<h5>Weight calculation</h5>
																					<table class="table-bordered"
																						style="border-spacing: 0 5px;">
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
																								<td>&lt;500</td>
																								<td><label class="labelfix">${chargeMap.localdwlt500}</label></td>
																								<td><label class="labelfix">${chargeMap.zonaldwlt500}</label></td>
																								<td><label class="labelfix">${chargeMap.nationaldwlt500}</label></td>
																								<td><label class="labelfix">${chargeMap.metrodwlt500}</label></td>
																							</tr>
																							<tr>
																								<td>&gt; 500</td>
																								<td><label class="labelfix">${chargeMap.localdwgt500}</label></td>
																								<td><label class="labelfix">${chargeMap.zonaldwgt500}</label></td>
																								<td><label class="labelfix">${chargeMap.nationaldwgt500}</label></td>
																								<td><label class="labelfix">${chargeMap.metrodwgt500}</label></td>
																							</tr>
																						</tbody>
																					</table>
																				</c:if>
																				<c:if test="${partner.nrnReturnConfig.shippingFeeType == 'fixed'}">
																					<h5>Weight calculation</h5>
																					<table class="table-bordered"
																						style="border-spacing: 0 5px;">
																						<thead>
																							<tr>
																								<th>Weight Weight Slab(gms)</th>
																								<th>Price</th>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td>&lt; 500</td>
																								<td><label class="labelfix">${chargeMap.fixeddwlt500}</label></td>
																							</tr>
																							<tr>
																								<td>&gt; 500</td>
																								<td><label class="labelfix">${chargeMap.fixeddwgt500}</label></td>
																							</tr>
																						</tbody>
																					</table>


																					<h5>Volume calculation= (lxbxh)(cm)/5000</h5>
																					<table class="table-bordered"
																						style="border-spacing: 0 5px;">
																						<thead>
																							<tr>
																								<th>Volume Weight Slab(gms)</th>
																								<th>Price</th>
																							</tr>
																						</thead>
																						<tbody>
																							<tr>
																								<td>&lt; 500</td>
																								<td><label class="labelfix">${chargeMap.fixedvwlt500}</label></td>
																							</tr>
																							<tr>
																								<td>500 &gt; 1000</td>
																								<td><label class="labelfix">${chargeMap.fixedvwgt500lt1000}</label></td>
																							</tr>
																							<tr>
																								<td>1000 &gt; 1500</td>
																								<td><label class="labelfix">${chargeMap.fixedvwgt1000lt1500}</label></td>
																							</tr>
																							<tr>
																								<td>1500 &gt; 5000</td>
																								<td><label class="labelfix">${chargeMap.fixedvwgt1500lt5000}</label></td>
																							</tr>
																							<tr>
																								<td>add 1 kg</td>
																								<td><label class="labelfix">${chargeMap.fixedvwgt5000}</label></td>
																							</tr>
																						</tbody>
																					</table>
																				</c:if>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td style="width: 50%;"><label>Service Tax</label></td>
																		<td>
																			<div>
																				<label class="labelfix">: ${chargeMap.serviceTax} %</label>
																			</div>
																		</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>
										
										
		<div class="float-e-margins col-lg-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title ibox-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapseconfig">RETURN CONFIG</a> </h4>
                </div>
                  <div id="collapseconfig" class="panel-collapse collapse in">
                      <div class="panel-body" style="margin-top: -18px;">
                        <div class="add-company"> 
                            <hr>
                            <div class="col-sm-12">
                                <div class="col-sm-4">
                                    
                                </div>
                                <div class="col-sm-4">
                                    <label>SELLER FAULT</label>
                                </div>
                                <div class="col-sm-4">
                                    <label>BUYER RETURN</label>
                                </div>
                            </div>

                            <div class="col-sm-12">
                                <div class="col-sm-4">
                                    <div class="radio"><label><strong>RETURN</strong></label>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                	<c:choose>
									    <c:when test="${partner.nrnReturnConfig.retCharSFType == 'fixed'}">									    	                                     
									        <div class="radio"><label class="labelfix"><input type="radio" value="" id="1" name="toggler">Fixed</label></div>
									    </c:when>
									    <c:when test="${partner.nrnReturnConfig.retCharSFType == 'variable'}">
										    <div class="radio"><label class="labelfix"><input type="radio" value="" id="2" name="toggler">Variable</label></div>								       
									    </c:when>
									    <c:otherwise>
									     	<div class="radio"><label class="labelfix"><input type="radio" value="" id="3" name="toggler">No-Charges </label></div>
									    </c:otherwise>
									</c:choose>									
                                </div>
                                <div class="col-sm-4">
                                	<c:choose>
									    <c:when test="${partner.nrnReturnConfig.retCharBRType == 'fixed'}">									    	                                     
									        <div class="radio"><label class="labelfix"><input type="radio" value="" id="5" name="toggler">Fixed</label></div>
									    </c:when>
									    <c:when test="${partner.nrnReturnConfig.retCharBRType == 'variable'}">
										    <div class="radio"><label class="labelfix"><input type="radio" value="" id="6" name="toggler">Variable</label></div>								       
									    </c:when>
									    <c:otherwise>
									     	<div class="radio"><label class="labelfix"><input type="radio" value="" id="4" name="toggler">No-Charges </label></div>
									    </c:otherwise>
									</c:choose>																		
                                </div>
                                <div class="col-sm-12 radio1" id="blk-1">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <input type="text" value="${chargeMap.retCharSFFixedAmt} %" class="form-control" disabled>
                                        </div>                                        
                                    </div>
                                </div>
                                <div class="col-sm-12 radio1" id="blk-2">
									<div class="row">
										<div class="col-md-12">
											<c:if test="${chargeMap.retCharSFVarFixedAmt != null}">
												<label class="labelfix">Fixed Amount : ${chargeMap.retCharSFVarFixedAmt}</label><br>
											</c:if>
											<c:if test="${chargeMap.retCharSFPercentSP != null}">
												<label class="labelfix">% of SP : ${chargeMap.retCharSFPercentSP}</label><br>
											</c:if>
											<c:if test="${chargeMap.retCharSFPercentPCC != null}">
												<label class="labelfix">% of Commision : ${chargeMap.retCharSFPercentPCC}</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.retCharSFFF == 'true'}">
												<label class="labelfix">Fixed Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.retCharSFShipFee == 'true'}">
												<label class="labelfix">Shipping Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.retCharSFRevShipFee == 'true'}">
												<label class="labelfix">Reverse Shipping Fee</label>
											</c:if>											
										</div>
									</div>
								</div>			
								
								<div class="col-sm-12 radio1" id="blk-5">
									<div class="row">
										<div class="col-md-12">
                                            <input type="text" value="${chargeMap.retCharBRFixedAmt} %" class="form-control" disabled>
                                        </div>
									</div>
								</div>
								<div class="col-sm-12 radio1" id="blk-6">
									<div class="row">
										<div class="col-md-12">
											<c:if test="${chargeMap.retCharBRVarFixedAmt != null}">
												<label class="labelfix">Fixed Amount : ${chargeMap.retCharBRVarFixedAmt}</label><br>
											</c:if>
											<c:if test="${chargeMap.retCharBRPercentSP != null}">
												<label class="labelfix">% of SP : ${chargeMap.retCharBRPercentSP}</label><br>
											</c:if>
											<c:if test="${chargeMap.retCharBRPercentPCC != null}">
												<label class="labelfix">% of Commision : ${chargeMap.retCharBRPercentPCC}</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.retCharBRFF == 'true'}">
												<label class="labelfix">Fixed Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.retCharBRShipFee == 'true'}">
												<label class="labelfix">Shipping Fee</label><br>
											</c:if>
										</div>
									</div>
								</div>
							</div>
                        <div class="col-sm-12">
                            <div class="hr-line-dashed"></div>
                            <div class="col-sm-4">
                                <div class="radio"><label><strong>RTO</strong></label>
                                </div>
                            </div>
                            <div class="col-sm-4">
								<c:choose>
									    <c:when test="${partner.nrnReturnConfig.RTOCharSFType == 'fixed'}">									    	                                     
									        <div class="radio"><label class="labelfix"><input type="radio" value="" id="7" name="toggler">Fixed</label></div>
									    </c:when>
									    <c:when test="${partner.nrnReturnConfig.RTOCharSFType == 'variable'}">
										    <div class="radio"><label class="labelfix"><input type="radio" value="" id="8" name="toggler">Variable</label></div>								       
									    </c:when>
									    <c:otherwise>
									     	<div class="radio"><label class="labelfix"><input type="radio" value="" id="9" name="toggler">No-Charges </label></div>
									    </c:otherwise>
								</c:choose>
                            </div>
                            <div class="col-sm-4">
                                <c:choose>
									    <c:when test="${partner.nrnReturnConfig.RTOCharBRType == 'fixed'}">									    	                                     
									        <div class="radio"><label class="labelfix"><input type="radio" value="" id="11" name="toggler">Fixed</label></div>
									    </c:when>
									    <c:when test="${partner.nrnReturnConfig.RTOCharBRType == 'variable'}">
										    <div class="radio"><label class="labelfix"><input type="radio" value="" id="12" name="toggler">Variable</label></div>								       
									    </c:when>
									    <c:otherwise>
									     	<div class="radio"><label class="labelfix"><input type="radio" value="" id="10" name="toggler">No-Charges </label></div>
									    </c:otherwise>
								</c:choose>
                            </div>
                            <div class="col-sm-12 radio1" id="blk-7">
                    			<div class="form-group col-md-12">                      				
                      				<div class="col-sm-12">
                       				 	<input type="text" value="${chargeMap.RTOCharSFFixedAmt} %" class="form-control" disabled>
                      				</div>
                   				</div>                    		
                  			</div>
							<div class="col-sm-12 radio1" id="blk-8">
                                <div class="row">
                                    <div class="col-md-12">
                                     		<c:if test="${chargeMap.RTOCharSFFixedAmt != null}">
												<label class="labelfix">Fixed Amount : ${chargeMap.RTOCharSFFixedAmt}</label><br>
											</c:if>
											<c:if test="${chargeMap.RTOCharSFPercentSP != null}">
												<label class="labelfix">% of SP : ${chargeMap.RTOCharSFPercentSP}</label><br>
											</c:if>
											<c:if test="${chargeMap.RTOCharSFPercentPCC != null}">
												<label class="labelfix">% of Commision : ${chargeMap.RTOCharSFPercentPCC}</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.RTOCharSFFF == 'true'}">
												<label class="labelfix">Fixed Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.RTOCharSFShipFee == 'true'}">
												<label class="labelfix">Shipping Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.RTOCharSFRevShipFee == 'true'}">
												<label class="labelfix">Reverse Shipping Fee</label>
											</c:if>	   
                                    </div>                                    
                                </div>
                            </div>
                            							
						<div class="col-sm-12 radio1" id="blk-11">
                               <div class="row">
                                <div class="col-md-12">
                                    <input type="text" value="${chargeMap.RTOCharBRFixedAmt} %" class="form-control" disabled>
                                </div>
                            </div>
                        </div>
						<div class="col-sm-12 radio1" id="blk-12">
                               <div class="row">
                                <div class="col-md-12">
                                	<c:if test="${chargeMap.RTOCharBRVarFixedAmt != null}">
										<label class="labelfix">Fixed Amount : ${chargeMap.RTOCharBRVarFixedAmt}</label><br>
									</c:if>
									<c:if test="${chargeMap.RTOCharBRPercentSP != null}">
										<label class="labelfix">% of SP : ${chargeMap.RTOCharBRPercentSP}</label><br>
									</c:if>
									<c:if test="${chargeMap.RTOCharBRPercentPCC != null}">
										<label class="labelfix">% of Commision : ${chargeMap.RTOCharBRPercentPCC}</label><br>
									</c:if>
									<c:if test="${partner.nrnReturnConfig.RTOCharBRFF == 'true'}">
										<label class="labelfix">Fixed Fee</label><br>
									</c:if>
									<c:if test="${partner.nrnReturnConfig.RTOCharBRShipFee == 'true'}">
										<label class="labelfix">Shipping Fee</label><br>
									</c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="hr-line-dashed"></div>
                        <div class="col-sm-4">
                            <div class="radio"><label><strong>REPLACE <br>MENT</strong></label>
                            </div>
                        </div>
                        <div class="col-sm-4">
                        	<c:choose>
								<c:when test="${partner.nrnReturnConfig.repCharSFType == 'fixed'}">									    	                                     
									<div class="radio"><label class="labelfix"><input type="radio" value="" id="15" name="toggler">Fixed</label></div>
								</c:when>
								<c:when test="${partner.nrnReturnConfig.repCharSFType == 'variable'}">
									<div class="radio"><label class="labelfix"><input type="radio" value="" id="14" name="toggler">Variable</label></div>								       
								</c:when>
								<c:otherwise>
								   	<div class="radio"><label class="labelfix"><input type="radio" value="" id="13" name="toggler">No-Charges </label></div>
								</c:otherwise>
							</c:choose>							
                        </div>
                        <div class="col-sm-4">
                        	<c:choose>
								<c:when test="${partner.nrnReturnConfig.repCharBRType == 'fixed'}">									    	                                     
									<div class="radio"><label class="labelfix"><input type="radio" value="" id="18" name="toggler">Fixed</label></div>
								</c:when>
								<c:when test="${partner.nrnReturnConfig.repCharBRType == 'variable'}">
								    <div class="radio"><label class="labelfix"><input type="radio" value="" id="17" name="toggler">Variable</label></div>								       
								</c:when>
								<c:otherwise>
								   	<div class="radio"><label class="labelfix"><input type="radio" value="" id="16" name="toggler">No-Charges </label></div>
								</c:otherwise>
							</c:choose>							
                        </div>                        
						<div class="col-sm-12 radio1" id="blk-14">
                            <div class="row">
                                <div class="col-md-12">
                                     		<c:if test="${chargeMap.repCharSFVarFixedAmt != null}">
												<label class="labelfix">Fixed Amount : ${chargeMap.repCharSFVarFixedAmt}</label><br>
											</c:if>
											<c:if test="${chargeMap.repCharSFPercentSP != null}">
												<label class="labelfix">% of SP : ${chargeMap.repCharSFPercentSP}</label><br>
											</c:if>
											<c:if test="${chargeMap.repCharSFPercentPCC != null}">
												<label class="labelfix">% of Commision : ${chargeMap.repCharSFPercentPCC}</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.repCharSFFF == 'true'}">
												<label class="labelfix">Fixed Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.repCharSFShipFee == 'true'}">
												<label class="labelfix">Shipping Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.repCharSFRevShipFee == 'true'}">
												<label class="labelfix">Reverse Shipping Fee</label>
											</c:if>	   
                                </div>
                            </div>
                        </div>
						<div class="col-sm-12 radio1" id="blk-15">
                            <div class="row">
                                <div class="col-md-12">
                                    <input type="text" value="${chargeMap.repCharSFFixedAmt} %" class="form-control" disabled>
                                </div>
                            </div>
                        </div>
                        
						<div class="col-sm-12 radio1" id="blk-17">
	                        <div class="row">
	                            <div class="col-md-12">
                                	<c:if test="${chargeMap.repCharBRFixedAmt != null}">
										<label class="labelfix">Fixed Amount : ${chargeMap.repCharBRFixedAmt}</label><br>
									</c:if>
									<c:if test="${chargeMaprepCharBRPercentSP != null}">
										<label class="labelfix">% of SP : ${chargeMap.repCharBRPercentSP}</label><br>
									</c:if>
									<c:if test="${chargeMap.repCharBRPercentPCC != null}">
										<label class="labelfix">% of Commision : ${chargeMap.repCharBRPercentPCC}</label><br>
									</c:if>
									<c:if test="${partner.nrnReturnConfig.repCharBRFF == 'true'}">
										<label class="labelfix">Fixed Fee</label><br>
									</c:if>
									<c:if test="${partner.nrnReturnConfig.repCharBRShipFee == 'true'}">
										<label class="labelfix">Shipping Fee</label><br>
									</c:if>
                                </div>
	                        </div>
	                    </div>
						<div class="col-sm-12 radio1" id="blk-18">
	                        <div class="row">
	                            <div class="col-md-12">
	                                <input type="text" value="${chargeMap.repCharBRFixedAmt} %" class="form-control" disabled>
	                            </div>
	                        </div>
	                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="hr-line-dashed"></div>
                    <div class="col-sm-4">
                        <div class="radio"><label><strong>PARTIAL DELIVERY</strong></label>
                        </div>
                    </div>
                    <div class="col-sm-4">
                    	<c:choose>
								<c:when test="${partner.nrnReturnConfig.PDCharSFType == 'fixed'}">									    	                                     
									<div class="radio"><label class="labelfix"><input type="radio" value="" id="20" name="toggler">Fixed</label></div>
								</c:when>
								<c:when test="${partner.nrnReturnConfig.PDCharSFType == 'variable'}">
									<div class="radio"><label class="labelfix"><input type="radio" value="" id="19" name="toggler">Variable</label></div>								       
								</c:when>
								<c:otherwise>
								   	<div class="radio"><label class="labelfix"><input type="radio" value="" id="21" name="toggler">No-Charges </label></div>
								</c:otherwise>
						</c:choose>						
                    </div>
					<div class="col-sm-4">
						<c:choose>
								<c:when test="${partner.nrnReturnConfig.PDCharBRType == 'fixed'}">									    	                                     
									<div class="radio"><label class="labelfix"><input type="radio" value="" id="23" name="toggler">Fixed</label></div>
								</c:when>
								<c:when test="${partner.nrnReturnConfig.PDCharBRType == 'variable'}">
									<div class="radio"><label class="labelfix"><input type="radio" value="" id="22" name="toggler">Variable</label></div>								       
								</c:when>
								<c:otherwise>
								   	<div class="radio"><label class="labelfix"><input type="radio" value="" id="24" name="toggler">No-Charges </label></div>
								</c:otherwise>
						</c:choose>						
					</div>
					<div class="col-sm-12 radio1" id="blk-19">
                    	<div class="row">
                            <div class="col-md-12">
                                     		<c:if test="${chargeMap.PDCharSFVarFixedAmt != null}">
												<label class="labelfix">Fixed Amount : ${chargeMap.PDCharSFVarFixedAmt}</label><br>
											</c:if>
											<c:if test="${chargeMap.PDCharSFPercentSP != null}">
												<label class="labelfix">% of SP : ${chargeMap.PDCharSFPercentSP}</label><br>
											</c:if>
											<c:if test="${chargeMap.PDCharSFPercentPCC != null}">
												<label class="labelfix">% of Commision : ${chargeMap.PDCharSFPercentPCC}</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.PDCharSFFF == 'true'}">
												<label class="labelfix">Fixed Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.PDCharSFShipFee == 'true'}">
												<label class="labelfix">Shipping Fee</label><br>
											</c:if>
											<c:if test="${partner.nrnReturnConfig.PDCharSFRevShipFee == 'true'}">
												<label class="labelfix">Reverse Shipping Fee</label>
											</c:if>	   
                                </div>
						</div>
                 	</div>
					<div class="col-sm-12 radio1" id="blk-20">
                        <div class="row">
                            <div class="col-md-12">
                            	<input type="text" value="${chargeMap.PDCharSFFixedAmt} %" class="form-control" disabled>
							</div>
						</div>
					</div>
					
					<div class="col-sm-12 radio1" id="blk-22">
                    	<div class="row">
                            <div class="col-md-12">
                                	<c:if test="${chargeMap.PDCharBRVarFixedAmt != null}">
										<label class="labelfix">Fixed Amount : ${chargeMap.PDCharBRVarFixedAmt}</label><br>
									</c:if>
									<c:if test="${chargeMap.PDCharBRPercentSP != null}">
										<label class="labelfix">% of SP : ${chargeMap.PDCharBRPercentSP}</label><br>
									</c:if>
									<c:if test="${chargeMap.PDCharBRPercentPCC != null}">
										<label class="labelfix">% of Commision : ${chargeMap.PDCharBRPercentPCC}</label><br>
									</c:if>
									<c:if test="${partner.nrnReturnConfig.PDCharBRFF == 'true'}">
										<label class="labelfix">Fixed Fee</label><br>
									</c:if>
									<c:if test="${partner.nrnReturnConfig.PDCharBRShipFee == 'true'}">
										<label class="labelfix">Shipping Fee</label><br>
									</c:if>
                                </div>
						</div>             
                  	</div>
					<div class="col-sm-12 radio1" id="blk-23">
                        <div class="row">
                            <div class="col-md-12">
                            	<input type="text" value="${chargeMap.PDCharBRFixedAmt} %" class="form-control" disabled>
							</div>
						</div>
					</div>
					
                </div>
                
                
                
                <div class="col-sm-12">
                    <div class="hr-line-dashed"></div>
                    <div class="col-sm-4">
                        <div class="radio"><label><strong>CANCELLA<br>TION</strong></label>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="col-md-12">
                            <div class="radio"><label class="labelfix"> <input type="radio" value="" id="25" name="toggler">BEFORE RTD </label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="radio"><label class="labelfix"> <input type="radio" value="" id="26" name="toggler">AFTER RTD </label>
                            </div>
                        </div> 
                    </div>
                    <div class="col-sm-4">
                        <div class="col-md-12">
                            <div class="radio"><label class="labelfix"> <input type="radio" value="" id="27" name="toggler">BEFORE RTD </label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="radio"><label class="labelfix"> <input type="radio" value="" id="28" name="toggler">AFTER RTD </label>
                            </div>
                        </div> 
                    </div>
					<div class="col-sm-12 radio1" id="blk-25">
						  <div class="col-sm-12">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="29" name="toggler">
								Fixed Amount</label>
							</div>
						  </div>
						  <div class="col-sm-12" style="display: none;">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="30" name="toggler">
								Variable </label>
							</div>
						  </div>
						  <div class="col-sm-12" style="display: none;">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="31" name="toggler">
								No Charges </label>
							</div>
						  </div>
						  
							<div class="col-sm-12 radio1" id="blk-29">
								<div class="col-sm-12">
								  <div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								  </div>
								</div>
							</div>
							<div class="col-sm-12 radio1" id="blk-30">
								<div class="col-sm-12">
								  <div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								  </div>
								</div>
							</div>
							<div class="col-sm-12 radio1" id="blk-31">
								<div class="col-sm-12">
								  <div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								  </div>
								</div>
							</div>
					</div>
					<div class="col-sm-12 radio1" id="blk-26">
						  <div class="col-sm-12">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="32" name="toggler">
								Fixed Amount</label>
							</div>
						  </div>
						  <div class="col-sm-12" style="display: none;">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="33" name="toggler">
								Variable </label>
							</div>
						  </div>
						  <div class="col-sm-12" style="display: none;">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="34" name="toggler">
								No Charges </label>
							</div>
						  </div>
						  <div class="col-sm-12 radio1" id="blk-32">
								<div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								  </div>
							</div>
							<div class="col-sm-12 radio1" id="blk-33">
								  <div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								  </div>
							</div>
							<div class="col-sm-12 radio1" id="blk-34">
								  <div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								  </div>
							</div>
					</div>
					<div class="col-sm-12 radio1" id="blk-27">
						<div class="col-sm-12">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="35" name="toggler">
								Fixed Amount</label>
							</div>
						</div>
						<div class="col-sm-12" style="display: none;">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="36" name="toggler">
								Variable </label>
							</div>
						</div>
						<div class="col-sm-12" style="display: none;">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="37" name="toggler">
								No Charges </label>
							</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-35">
								<div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-36">
								<div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-37">
								<div class="form-group">
									<div class="col-md-2 content-rgt">
									  <label>Lable</label>
									</div>
									<div class="col-md-3 content-rgt">
									  <input type="text" placeholder="" class="form-control">
									</div>
								 </div>
						</div>
					</div>
					<div class="col-sm-12 radio1" id="blk-28">
						<div class="col-sm-12">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="38" name="toggler">
								Fixed Amount</label>
							</div>
						</div>
						<div class="col-sm-12" style="display: none;">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="39" name="toggler">
								Variable </label>
							</div>
						</div>
						<div class="col-sm-12" style="display: none;">
							<div class="radio">
							  <label>
								<input type="radio" value="" id="40" name="toggler">
								No Charges </label>
							</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-38">
							<div class="form-group">
								<div class="col-md-2 content-rgt">
									
								</div>
								<div class="col-md-3 content-rgt">
									<input type="text" placeholder="" class="form-control">
								</div>
							</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-39">
							<div class="form-group">
								<div class="col-md-2 content-rgt">
									<label>Lable</label>
								</div>
								<div class="col-md-3 content-rgt">
									<input type="text" placeholder="" class="form-control">
								</div>
							</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-40">
							<div class="form-group">
								<div class="col-md-2 content-rgt">
									<label>Lable</label>
								</div>
								<div class="col-md-3 content-rgt">
									<input type="text" placeholder="" class="form-control">
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
                                <div class="item">
                                    
                                </div>            
                                <div class="item">

                                </div>
                                <div class="item">

                                </div>
                                <div class="item">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                    consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                                    cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                                    proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                                    </p>
                                </div>
                                <div class="item">
                                    
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
	<!-- iCheck -->
	<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script>
	<!-- Switchery -->
	<script src="/O2R/seller/js/plugins/switchery/switchery.js"></script>
	<script src="/O2R/seller/js/pickList1.js"></script>
	<!-- <script src="/O2R/seller/js/pickList.js"></script> -->	
	
	<script type="text/javascript">

$(document).ready( function() {


   	 $('.carousel').carousel({
		pause: true,
		interval: false
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