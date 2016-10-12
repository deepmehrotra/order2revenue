
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*"%>
<%
	Date date = new java.util.Date();
	pageContext.setAttribute("currentDate", date);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="/O2R/seller/css/summernote.css" rel="stylesheet">
<link href="/O2R/seller/css/summernote-bs3.css" rel="stylesheet">




<jsp:include page="../globalcsslinks.jsp"></jsp:include>

<style type="text/css">
.buton {
	padding: 0px 6px 0px 6px;
	border: 2px solid #fff;
	border-radius: 50%;
	position: relative;
	top: -6px;
	background: magenta;
	left: 95%;
	color: #fff;
	z-index: 99999999999999999999999;
	position: absolute;
	cursor: pointer;
}
</style>
</head>
<body>

	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>


			<div class="wrapper wrapper-content  animated fadeInRight">
				<div class="row">
					<div class="col-sm-8">
						<div class="ibox">
							<div class="ibox-content">
								<form role="search" class="navbar-form-new" method="post"
									action="#" style="margin: 0px;">
									<div class="form-group top-search-100">
										<div class="top-search-100">
											<p>
												<input type="text" id="search"
													placeholder="Enter keyword to search" class="form-control" />
											</p>
										</div>
									</div>
								</form>
								<div class="clients-list">
									<ul class="nav nav-tabs">
										<li class="active"><a data-toggle="tab" href="#tab-1"><i
												class="fa fa-user"></i> Sellers (${sellers.size()})</a></li>
									</ul>
									<div class="tab-content"
										style="overflow-x: hidden; ">
										<div id="tab-1" class="tab-pane active">
											<div class="full-height-scroll">
												<div class="table-responsive">
													<table class="table table-striped table-hover target"
														id="tblData" bgcolor="#ACAAFC">
														<tbody>
															<c:if test="${!empty sellers}">
																<c:forEach items="${sellers}" var="seller">
																	<tr>
																		<td><input type="checkbox"
																			value="${seller.email}" id="${seller.id}"></td>
																		<c:if test="${seller.logoUrl != null}">
																			<td class="client-avatar"><a data-toggle="tab"
																				href="#contact-${seller.id}" class="client-link"><img
																					alt="image" src="${seller.logoUrl}"></a></td>
																		</c:if>
																		<c:if test="${seller.logoUrl == null}">
																			<td class="client-avatar"><a data-toggle="tab"
																				href="#contact-${seller.id}" class="client-link"><img
																					alt="image"
																					src="/O2R/sellerimages/defaultSeller.jpg"></a></td>
																		</c:if>
																		<td><a data-toggle="tab"
																			href="#contact-${seller.id}" class="client-link">${seller.email} (${seller.id})</a></td>
																		<td class="contact-type"><i class="fa fa-phone">
																		</i></td>
																		<td>${seller.contactNo}</td>
																		<td><fmt:formatDate
																				value="${seller.sellerAccount.lastLogin}"
																				pattern="MMM dd ,YY HH:mm:ss" /></td>
																		<td class="client-status"><span
																			class="label label-primary">Active</span></td>
																		<td><a class="client-link"><i
																				class="fa fa-edit text-navy" data-toggle="modal"
																				data-target="#edit-${seller.id}"
																				data-original-title="Edit"></i></a></td>
																	</tr>
																</c:forEach>
															</c:if>
														</tbody>
													</table>

													<c:forEach items="${sellers}" var="seller">
														<div class="modal inmodal fade" id="edit-${seller.id}"
															tabindex="-1" role="dialog" aria-hidden="true">
															<div class="modal-dialog modal-lg">
																<div class="modal-content animated bounceInTop"
																	style="left: 29%; width: 45%; top: 79px;">
																	<div class="modal-header" style="padding: 10px 0px;">
																		<h4>EDIT ORDER BUCKET</h4>
																	</div>
																	<div class="modal-body">
																		<div class="row">
																			<div class="col-sm-12">
																				<div class="form-group">
																					<form action="updateSellerAccount.html"
																						id="saveOrderBucket" method="POST">
																						<div class="col-sm-12">
																							<div class="mar-btm-20-oh">
																								<label class="col-sm-6 control-label">Orders
																									In Bucket</label>
																								<div class="col-sm-6">
																									<input
																										value="${seller.sellerAccount.orderBucket}"
																										name="orderBucket" id="orderBucket"
																										style="width: 100%;" />
																								</div>
																								<input type="hidden" name="sellerAccId"
																									value="${seller.sellerAccount.selaccId}">
																							</div>
																						</div>
																						<button type="submit"
																							class=" btn btn-primary form-control pull-left"
																							style="background: #1ab394; width: 50%; color: #fff;">Update</button>
																						<button type="button"
																							class=" btn btn-primary form-control pull-right"
																							data-dismiss="modal"
																							style="background: #1ab394; width: 50%; color: #fff;">Cancel</button>
																					</form>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</c:forEach>

												</div>
											</div>
										</div>
									</div>
									<hr>
									<div style="align: center">
										<button class="btn btn-primary btn-sm btn-block pull-right"
											style="width: 48%; margin-top: -14px; margin-right: 25%;"
											data-toggle="modal" data-target="#sendMail-all">
											<i class="fa fa-envelope "></i>SEND 2 SELECTED
										</button>
									</div>

									<div class="modal inmodal fade" id="sendMail-all" tabindex="-1"
										role="dialog" aria-hidden="true">
										<div class="modal-dialog modal-lg">
											<div class="modal-content animated bounceInTop"
												style="width: 100%; top: -9px;">
												<div class="modal-header" style="padding: 10px 0px;">
													<img alt="image" class="img-circle pull-right"
														src="/O2R/sellerimages/group.jpg" style="width: 45px;">
													<img alt="image" class="img-circle pull-left"
														data-dismiss="modal" src="/O2R/landing/img/logoo2r.png"
														style="width: 40px;">
													<h4>SEND MAIL</h4>
													<i class="fa fa-share"></i>
												</div>
												<div class="modal-body">
													<div class="row">
														<div class="col-sm-12">
															<div class="form-group">
																<form action="userquery.html" id="contactusform"
																	method="POST">
																	<%-- <label class="pull-left" style="width: 20%;">To</label><input type="text" value="${seller.email}" class="form-control pull-right" style="color: darkgreen; width: 80%;" required>
																 <br>
																 <label class="pull-left" style="width: 20%;">Subject</label><input type="text" class="form-control pull-right" style="color: darkgreen; width: 80%;" required>
																 <br>
																 <center><label>BODY</label></center>
																 <textarea rows="5" cols="10" class="form-control"></textarea> --%>
																	<div class="mail-box">
																		<div class="mail-body">
																			<div class="form-group">
																				<label class="col-sm-2">To:</label> <input
																					type="text" class="form-control" id="toSelected"
																					name="email"> <label class="col-sm-2">Subject:</label>
																				<input type="text" class="form-control"
																					name="subject">
																			</div>
																		</div>
																		<div class="mail-text h-200">
																			<div class="summernote"></div>
																		</div>
																	</div>
																	<br>
																	<button type="submit"
																		class=" btn btn-primary form-control pull-left"
																		style="background: #1ab394; width: 50%; color: #fff;">SEND</button>
																	<button type="button"
																		class=" btn btn-primary form-control pull-right"
																		data-dismiss="modal"
																		style="background: #1ab394; width: 50%; color: #fff;">CANCEL
																	</button>
																</form>
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
					<div class="col-sm-4">
						<div class="ibox ">
							<div class="ibox-content">
								<div class="tab-content">
									<c:if test="${!empty sellers}">
										<c:forEach items="${sellers}" var="seller">
											<div id="contact-${seller.id}" class="tab-pane">
												<div class="row m-b-lg">
													<div class="col-lg-12">
														<c:if test="${seller.logoUrl != null}">
															<center>
																<img alt="image" class="img-circle"
																	src="${seller.logoUrl}" style="width: 62px;">
															</center>
														</c:if>
														<c:if test="${seller.logoUrl == null}">
															<center>
																<img alt="image" class="img-circle"
																	src="/O2R/sellerimages/defaultSeller.jpg"
																	style="width: 62px">
															</center>
														</c:if>
														<br>
														<button type="button"
															class="btn btn-primary btn-sm btn-block">
															<b><font color="#fffffff" size="3">${seller.name}</font></b>
														</button>
														<br>
														<div class="full-height-scroll">
															<ul class="list-group clear-list">
																<li class="list-group-item fist-item"><span
																	class="pull-right"> ${seller.name} </span> Seller Name
																</li>
																<li class="list-group-item"><span
																	class="pull-right"> ${seller.companyName} </span>
																	Company Name</li>
																<li class="list-group-item"><span
																	class="pull-right"> ${seller.contactNo} </span> Contact
																	No</li>
																<li class="list-group-item"><span
																	class="pull-right"> ${seller.email} </span> Email</li>
																<li class="list-group-item"><span
																	class="pull-right"> ${seller.address} </span> Address</li>
																<li class="list-group-item"><span
																	class="pull-right"> <fmt:formatDate
																			value="${seller.sellerAccount.ativationDate}"
																			pattern="MMM dd,YY" />
																</span> Activation Date</li>
																<li class="list-group-item"><span
																	class="pull-right">
																		${seller.sellerAccount.orderBucket} </span> Order Count</li>
																<li class="list-group-item"><span
																	class="pull-right">
																		${seller.sellerAccount.lastLogin} </span> Last Login</li>
																<li class="list-group-item"><span
																	class="pull-right"> <c:choose>
																			<c:when
																				test="${seller.sellerAccount.accountStatus == true}">
																				<font color="green">Active</font>
																			</c:when>
																			<c:otherwise>
																				<font color="red">Inactive</font>
																			</c:otherwise>
																		</c:choose>
																</span> Account Status</li>
																<li class="list-group-item"><span
																	class="pull-right">
																		${seller.sellerAccount.totalOrderProcessed} </span> Total
																	Order Processed</li>
																<li class="list-group-item"><span
																	class="pull-right">
																		${seller.sellerAccount.totalAmountPaidToO2r} </span> Total
																	Amount Paid</li>
																<li class="list-group-item"><span
																	class="pull-right"> ${seller.plan.planName} </span>
																	Plan Name</li>
															</ul>
														</div>
														<!-- <button type="button" class="btn btn-primary btn-sm btn-block" data-toggle="modal" data-target="#sendMail" ><i
	                                                class="fa fa-envelope "></i> <b>Send Mail</b>
	                                            </button> -->
														<a href="#">
															<button type="button"
																class="btn btn-primary btn-sm btn-block"
																data-toggle="modal" data-target="#sendMail-${seller.id}">
																<i class="fa fa-envelope "></i> <b>Send Mail</b>
															</button>
														</a>
													</div>
												</div>
											</div>
										</c:forEach>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:forEach items="${sellers}" var="seller">
				<div class="modal inmodal fade" id="sendMail-${seller.id}"
					tabindex="-1" role="dialog" aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content animated bounceInTop"
							style="width: 100%; top: -9px;">
							<div class="modal-header" style="padding: 10px 0px;">
								<c:if test="${seller.logoUrl != null}">
									<img alt="image" class="img-circle pull-right"
										src="${seller.logoUrl}" style="width: 38px;">
								</c:if>
								<c:if test="${seller.logoUrl == null}">
									<img alt="image" class="img-circle pull-right"
										src="/O2R/sellerimages/defaultSeller.jpg" style="width: 38px;">
								</c:if>
								<img alt="image" class="img-circle pull-left"
									src="/O2R/landing/img/logoo2r.png" style="width: 40px;">
								<h4>SEND MAIL</h4>
								<i class="fa fa-share"></i>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<form action="userquery.html" id="contactusform"
												method="POST">
												<%-- <label class="pull-left" style="width: 20%;">To</label><input type="text" value="${seller.email}" class="form-control pull-right" style="color: darkgreen; width: 80%;" required>
												 <br>
												 <label class="pull-left" style="width: 20%;">Subject</label><input type="text" class="form-control pull-right" style="color: darkgreen; width: 80%;" required>
												 <br>
												 <center><label>BODY</label></center>
												 <textarea rows="5" cols="10" class="form-control"></textarea> --%>
												<div class="mail-box">
													<div class="mail-body">
														<div class="form-group">
															<label class="col-sm-2">To:</label> <input type="text"
																class="form-control" value="${seller.email}"
																name="email"> <label class="col-sm-2">Subject:</label>
															<input type="text" class="form-control" name="subject">
														</div>
													</div>
													<div class="mail-text h-200">
														<div class="summernote"></div>
													</div>
												</div>
												<br>
												<button type="submit"
													class=" btn btn-primary form-control pull-left"
													style="background: #1ab394; width: 50%; color: #fff;">SEND</button>
												<button type="button"
													class=" btn btn-primary form-control pull-right"
													data-dismiss="modal"
													style="background: #1ab394; width: 50%; color: #fff;">CANCEL
												</button>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>

			<jsp:include page="../globalfooter.jsp"></jsp:include>

			<jsp:include page="../globaljslinks.jsp"></jsp:include>
			<script src="/O2R/seller/js/summernote.min.js"></script>
			<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script>
			<script>
				$(document).ready(function() {

					$checks = $(":checkbox");
					$checks.on('click', function() {
						var string = $checks.filter(":checked").map(function() {
							return this.value;
						}).get().join(",");
						$('#toSelected').val(string);
					});

					$('#search').keyup(function() {
						searchTable($(this).val());
					});

					$('.i-checks').iCheck({
						checkboxClass : 'icheckbox_square-green',
						radioClass : 'iradio_square-green',
					});
					$('.summernote').summernote();
					var edit = function() {
						$('.click2edit').summernote({
							focus : true
						});
					};
					var save = function() {
						var aHTML = $('.click2edit').code();
						$('.click2edit').destroy();
					};
				});

				function searchTable(inputVal) {
					var table = $('#tblData');
					table.find('tr').each(function(index, row) {
						var allCells = $(row).find('td');
						if (allCells.length > 0) {
							var found = false;
							allCells.each(function(index, td) {
								var regExp = new RegExp(inputVal, 'i');
								if (regExp.test($(td).text())) {
									found = true;
									return false;
								}
							});
							if (found == true)
								$(row).show();
							else
								$(row).hide();
						}
					});
				}
			</script>


		</div>
	</div>
</body>
</html>
