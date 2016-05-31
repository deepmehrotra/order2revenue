<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

.labelfix {
	font-size: 12px;
}
</style>
<link href="/O2R/seller/css/bootstrap.min.css" rel="stylesheet">
<link href="/O2R/seller/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="/O2R/seller/css/animate.css" rel="stylesheet">
<link href="/O2R/seller/css/style.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/O2R/seller/css/jcarousel.responsive.css">
<link href="/O2R/seller/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="/O2R/seller/css/plugins/switchery/switchery.css"
	rel="stylesheet">
<link href="/O2R/seller/css/plugins/iCheck/custom.css" rel="stylesheet">
<!-- <script src="/O2R/seller/js/inspinia.js"></script> -->
<script type="text/javascript">
	function relodPage(id) {
	    window.location.href = 'viewPartner.html?pcId=' + id;
	    //location.reload();	
	}
</script>
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
											<c:forEach items="${partnerList}" var="each" varStatus="loop">
												<c:choose>
													<c:when test="${each.pcId == partner.pcId}">
														<li data-target="#myCarousel" data-slide-to="0"
															class="active"><a href=""> <c:if
																	test="${each.pcLogoUrl == null}">
																	<img src="/O2R/partnerimages/5Yamaha.jpg"
																		id="${each.pcId}" onclick="relodPage(this.id);">
																</c:if> <c:if test="${each.pcLogoUrl != null}">
																	<img src="${each.pcLogoUrl}" id="${each.pcId}"
																		onclick="relodPage(this.id);">
																</c:if>
														</a></li>
													</c:when>
													<c:otherwise>
														<li data-target="#myCarousel" data-slide-to="0" class="">
															<a href=""> <c:if test="${each.pcLogoUrl == null}">
																	<img src="/O2R/partnerimages/5Yamaha.jpg"
																		id="${each.pcId}" onclick="relodPage(this.id);">
																</c:if> <c:if test="${each.pcLogoUrl != null}">
																	<img src="${each.pcLogoUrl}" id="${each.pcId}"
																		onclick="relodPage(this.id);">
																</c:if>
														</a>
														</li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
									</ul>
								</div>
								<a href="#" class="jcarousel-control-prev">&lsaquo;</a> <a
									href="#" class="jcarousel-control-next">&rsaquo;</a>
							</div>
							<div class="carousel-inner">
								<div class="item active">
									<div class="col-lg-12">
										<div class="float-e-margins col-lg-6">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title ibox-title">
														<a data-toggle="collapse" data-parent="#accordion"
															href="#collapsebasic">Basic Settings</a>
													</h4>
												</div>
												<div id="collapsebasic" class="panel-collapse collapse in">
													<div class="panel-body">
														<div class="ibox-content add-company view-order">
															<table class="table table hover"
																style="border-spacing: 0 5px;">
																<tbody>
																	<tr>
																		<td style="width: 50%;"><label>ALIAS NAME
																		</label></td>
																		<td><font color="green" size="2"> <input
																				type="text" class="form-control"
																				placeholder="${partner.pcDesc}" disabled>
																		</font></td>
																	</tr>
																	<tr>
																		<td style="width: 50%;"><label>PARTNER
																				NAME </label></td>
																		<td><font color="green" size="2"> <input
																				type="text" class="form-control"
																				placeholder="${partner.pcName}" disabled>
																		</font></td>
																	</tr>
																</tbody>
															</table>
															<div>
																<h4 style="text-align: center;">PAYMENT TYPE</h4>
															</div>
															<div class="col-lg-12" style="text-align: center;">
																<div class="input-group1">
																	<c:choose>
																		<c:when
																			test="${partner.paymentType == 'paymentcycle'}">
																			<font color="green" size="2"> <input
																				type="text" class="form-control"
																				placeholder="Payment Cycle"
																				style="text-align: center;" disabled>
																			</font>
																		</c:when>
																		<c:when test="${partner.paymentType == 'datewisepay'}">
																			<font color="green" size="2"> <input
																				type="text" class="form-control"
																				placeholder="Day Wise" style="text-align: center;"
																				disabled>
																			</font>
																		</c:when>
																		<c:otherwise>
																			<font color="green" size="2"> <input
																				type="text" class="form-control"
																				placeholder="Monthly" style="text-align: center;"
																				disabled>
																			</font>
																		</c:otherwise>
																	</c:choose>
																</div>
															</div>
															<div>
																<br> <br>
																<h4 style="text-align: center;">PAYMENT CYCLE</h4>
															</div>
															<div class="col-lg-12" style="text-align: center;">
																<c:choose>
																	<c:when test="${partner.paymentType == 'paymentcycle'}">
																		<font color="green" size="2"> <input
																			type="text" class="form-control"
																			placeholder="${partner.startcycleday} to ${partner.paycycleduration} ,Payment on ${partner.paydaysfromstartday}"
																			style="text-align: center;" disabled>
																		</font>
																	</c:when>
																	<c:when test="${partner.paymentType == 'datewisepay'}">
																		<c:choose>
																			<c:when test="${partner.isshippeddatecalc}">
																				<font color="green" size="2"> <input
																					type="text" class="form-control"
																					placeholder="${partner.noofdaysfromshippeddate} Days From Shipped Date"
																					style="text-align: center;" disabled>
																				</font>
																			</c:when>
																			<c:otherwise>
																				<font color="green" size="2"> <input
																					type="text" class="form-control"
																					placeholder="${partner.noofdaysfromshippeddate} Days From Delivery Date"
																					style="text-align: center;" disabled>
																				</font>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<font color="green" size="2"> <input
																			type="text" class="form-control"
																			placeholder="${partner.monthlypaydate} of Every Month"
																			style="text-align: center;" disabled>
																		</font>
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="float-e-margins col-lg-6">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title ibox-title">
														<a data-toggle="collapse" data-parent="#accordion"
															href="#collapseship">Commission/Tax Config</a>
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
																		<td style="width: 50%;"><label>COMMISSION</label>
																		</td>
																		<td>
																			<div>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'fixed' }">
																					<button class="btn btn-grey" id="button1"
																						style="width: 100%;">
																						<font size="2" color="green">Fixed</font><span
																							class="caret"></span>
																					</button>
																				</c:if>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<button class="btn btn-grey" id="button1"
																						style="width: 100%;">
																						<font size="2" color="green">Category Wise</font><span
																							class="caret"></span>
																					</button>
																				</c:if>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="10">
																			<div id="accordion" class="collapse">
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'fixed' }">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.fixedCommissionPercent} %</label>
																				</c:if>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<c:choose>
																						<c:when test="${!empty categoryMap}">
																							<c:forEach items="${categoryMap}" var="cat"
																								varStatus="loop">
																								<div class="form-group col-md-12">
																									<label class="labelfix">${cat.key} :
																										${cat.value} </label>
																								</div>
																							</c:forEach>
																						</c:when>
																					</c:choose>
																				</c:if>
																			</div>
																		</td>
																	</tr>
																	<tr data-toggle="collapse" data-target="#accordion"
																		class="clickable">
																		<td style="width: 50%;"><label>Tax SP</label></td>
																		<td>
																			<div>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'fixed' }">
																					<button class="btn btn-grey" id="button1"
																						style="width: 100%;">
																						<font size="2" color="green">Fixed</font><span
																							class="caret"></span>
																					</button>
																				</c:if>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<button class="btn btn-grey" id="button1"
																						style="width: 100%;">
																						<font size="2" color="green">Category Wise</font><span
																							class="caret"></span>
																					</button>
																				</c:if>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="10">
																			<div id="accordion" class="collapse">
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'fixed' }">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.fixedCommissionPercent} %</label>
																				</c:if>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<c:choose>
																						<c:when test="${!empty categoryMap}">
																							<c:forEach items="${categoryMap}" var="cat"
																								varStatus="loop">
																								<div class="form-group col-md-12">
																									<label class="labelfix">${cat.key} :
																										${cat.value} </label>
																								</div>
																							</c:forEach>
																						</c:when>
																					</c:choose>
																				</c:if>
																			</div>
																		</td>
																	</tr>
																	<tr data-toggle="collapse" data-target="#accordion"
																		class="clickable">
																		<td style="width: 50%;"><label>Tax PO</label></td>
																		<td>
																			<div>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'fixed' }">
																					<button class="btn btn-grey" id="button1"
																						style="width: 100%;">
																						<font size="2" color="green">Fixed</font><span
																							class="caret"></span>
																					</button>
																				</c:if>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<button class="btn btn-grey" id="button1"
																						style="width: 100%;">
																						<font size="2" color="green">Category Wise</font><span
																							class="caret"></span>
																					</button>
																				</c:if>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td colspan="10">
																			<div id="accordion" class="collapse">
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'fixed' }">
																					<label class="labelfix">Fixed Amount :
																						${chargeMap.fixedCommissionPercent} %</label>
																				</c:if>
																				<c:if
																					test="${partner.nrnReturnConfig.commissionType == 'categoryWise' }">
																					<c:choose>
																						<c:when test="${!empty categoryMap}">
																							<c:forEach items="${categoryMap}" var="cat"
																								varStatus="loop">
																								<div class="form-group col-md-12">
																									<label class="labelfix">${cat.key} :
																										${cat.value} </label>
																								</div>
																							</c:forEach>
																						</c:when>
																					</c:choose>
																				</c:if>
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
	<script type="text/javascript"
		src="/O2R/seller/js/jquery.jcarousel.min.js"></script>
	<script type="text/javascript"
		src="/O2R/seller/js/jcarousel.responsive.js"></script>
	<!-- iCheck -->
	<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script>
	<!-- Switchery -->
	<script src="/O2R/seller/js/plugins/switchery/switchery.js"></script>
	<script src="/O2R/seller/js/pickList1.js"></script>
	<!-- <script src="/O2R/seller/js/pickList.js"></script> -->
	<script type="text/javascript">
                $(document).ready(function() {


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
                        if (!clickEvent) {
                            var count = $('.nav').children().length - 1;
                            var current = $('.nav li.active');
                            current.removeClass('active').next().addClass('active');
                            var id = parseInt(current.data('slide-to'));
                            if (count == id) {
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
                    var switchery = new Switchery(elem, {
                        color: '#1AB394'
                    });

                    var elem_2 = document.querySelector('.js-switch_2');
                    var switchery_2 = new Switchery(elem_2, {
                        color: '#ED5565'
                    });

                    var elem_3 = document.querySelector('.js-switch_3');
                    var switchery_3 = new Switchery(elem_3, {
                        color: '#1AB394'
                    });


                });
                </script>
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

                $("[name=toggler]").click(function() {
                    var x = this.id;
                    if (!(x >= 1 && x <= 29))
                        $('.radio1').hide();

                    $("#blk-" + x).slideDown();
                    for (var a = 0; a < 29; a++) {
                        if (a != x) {
                            $("#blk-" + a).slideUp();
                        } else {
                            $("#blk-" + a).slideDown();
                        }
                    }

                });
                </script>
	<script>
                var val = {
                    01: {
                        id: 01,
                        text: 'Andhra Pradesh'
                    },
                    02: {
                        id: 02,
                        text: 'Andaman and Nicobar Islands'
                    },
                    03: {
                        id: 03,
                        text: 'Arunachal Pradesh'
                    },
                    04: {
                        id: 04,
                        text: 'Assam'
                    },
                    05: {
                        id: 05,
                        text: 'Chhattisgarh'
                    },
                    06: {
                        id: 06,
                        text: 'Chandigarh'
                    },
                    07: {
                        id: 07,
                        text: 'Dadra and Nagar Haveli'
                    },
                    08: {
                        id: 08,
                        text: 'Daman and Diu'
                    },
                    09: {
                        id: 09,
                        text: 'Delhi'
                    },
                    10: {
                        id: 10,
                        text: 'Goa'
                    },
                    11: {
                        id: 11,
                        text: 'Gujarat'
                    },
                    12: {
                        id: 12,
                        text: 'Haryana'
                    },
                    13: {
                        id: 13,
                        text: 'Himachal Pradesh'
                    },
                    14: {
                        id: 14,
                        text: 'Jammu and Kashmir'
                    },
                    15: {
                        id: 15,
                        text: 'Jharkhand'
                    },
                    16: {
                        id: 16,
                        text: 'Karnataka'
                    },
                    17: {
                        id: 17,
                        text: 'Kerala'
                    },
                    18: {
                        id: 18,
                        text: 'Lakshadweep'
                    },
                    19: {
                        id: 19,
                        text: 'Madhya Pradesh'
                    },
                    20: {
                        id: 20,
                        text: 'Maharashtra'
                    },
                    21: {
                        id: 21,
                        text: 'Manipur'
                    },
                    22: {
                        id: 22,
                        text: 'Meghalaya'
                    },
                    23: {
                        id: 23,
                        text: 'Mizoram'
                    },
                    24: {
                        id: 24,
                        text: 'Nagaland'
                    },
                    25: {
                        id: 25,
                        text: 'Odisha'
                    },
                    26: {
                        id: 26,
                        text: 'Punjab'
                    },
                    27: {
                        id: 27,
                        text: 'Pondicherry'
                    },
                    28: {
                        id: 28,
                        text: 'Rajasthan'
                    },
                    29: {
                        id: 29,
                        text: 'Sikkim'
                    },
                    30: {
                        id: 30,
                        text: 'Tamil Nadu'
                    },
                    31: {
                        id: 31,
                        text: 'Telangana'
                    },
                    32: {
                        id: 32,
                        text: 'Tripura'
                    },
                    33: {
                        id: 33,
                        text: 'Uttar Pradesh'
                    },
                    34: {
                        id: 34,
                        text: 'Uttarakhand'
                    },
                    35: {
                        id: 35,
                        text: 'West Bengal'
                    }
                };
                var pick = $("#pickList").pickList({
                    data: val
                });
                </script>
</body>
</html>