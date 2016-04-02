<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<style type="text/css">
span .#error {
	color: red;
	font-weight: bold;
}
</style>
<script type="text/javascript">
	function submitSeller() {
		$.ajax({
			url : $("#addSellerForm").attr("action"),
			context : document.body,
			type : 'post',
			data : $("#addSellerForm").serialize(),
			success : function(res) {
				$("#centerpane").html(res);
			}
		});
	};
	reqObj = null;
	function varify() {
		document.getElementById("res").innerHTML = "Checking";
		if (window.XMLHttpRequest) {
			reqObj = new XMLHttpRequest();
		} else {
			reqObj = new ActiveXObject("Microsoft.XMLHTTP");
		}
		reqObj.onreadystatechange = process;
		reqObj.open("GET", "checkSeller.html?id="
				+ document.getElementById("test").value, true);
		reqObj.send(null);
	}
	function process() {
		if (reqObj.readyState == 4) {
			document.getElementById("res").innerHTML = reqObj.responseText;
		}
	}
	function blurFunction() {
		document.getElementById("myInput").style.background = "red";
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
								<h5>Seller Info</h5>
							</div>
							<div class="ibox-content add-company">
								<form:form method="POST" action="saveSeller.html" role="form"
									class="form-horizontal" id="addSellerForm">
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label">Seller Name</label>
											<c:if test="${!empty seller.id}">
												<form:input type="hidden" class="form-control"
													value="${seller.id}" path="id" />
												<form:input type="hidden" class="form-control"
													value="${seller.password}" path="password" />
												<form:input type="hidden" class="form-control"
													value="${seller.role.id}" path="role.id" />
												<form:input type="hidden" class="form-control"
													value="${seller.role.role}" path="role.role" />
											</c:if>
											<div class="col-sm-8">
												<form:input type="text" class="form-control"
													value="${seller.name}" path="name" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">Company Name</label>

											<div class="col-sm-8">
												<form:input type="text" class="form-control"
													value="${seller.companyName}" path="companyName" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label required">Phone
												No. *</label>

											<div class="col-sm-8">
												<form:input type="text" class="form-control"
													value="${seller.contactNo}" path="contactNo" />
											</div>
										</div>
										<div class="hr-line-dashed"></div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label">TIN No</label>

											<div class="col-sm-8">
												<form:input type="text" class="form-control"
													value="${seller.tinNumber}" path="tinNumber" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label">TAN No</label>

											<div class="col-sm-8">
												<form:input type="text" class="form-control"
													value="${seller.tanNumber}" path="tanNumber" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-sm-4 control-label required">Email
												*</label>

											<div class="col-sm-8">
												<form:input type="email" class="form-control"
													value="${seller.email}" path="email" required="true" />
											</div>
										</div>
										<div class="hr-line-dashed"></div>
									</div>

									<h4>Seller Brand</h4>
									<br>
									<div>
										<div class="col-md-4">
											<form:input type="text" class="form-control"
												value="${seller.brandName}" path="brandName" />
											<div class="hr-line-dashed"></div>
										</div>
										<div class="col-md-8">
											<label title="Upload image file" for="inputImage"
												class="btn btn-white btn-block"> <i
												class="fa fa-upload"></i> <input type="file"
												accept="image/*" name="file" id="inputImage" class="hide">
												Upload Logo
											</label>
											<div class="hr-line-dashed"></div>
										</div>
									</div>
									<!-- <div class="col-md-2">
										<button class="btn btn-success" type="submit">Add
											Brand</button>
									</div> -->

									<h4>Expected Delivery Time</h4>
									<p>
										Plz Note: <br> <span class="span1">The time
											defined here will derive here the expected delivery time of
											products to the customers and the expected payments to be
											received in cases where payment is directly related to
											delivery of the order. </span>
									</p>
									<div class="col-lg-6 m-l-n"
										style="overflow: scroll; overflow-x: hidden; height: 400px;">
										<div class="col-lg-12 m-l-n">
											<div class="ibox float-e-margins">
												<div class="form-group">
													<label class="col-sm-4 control-label"></label>

													<div class="col-sm-8">
														<label class="col-sm-8 control-label">SELLER
															TIME(DAYS)</label>
													</div>
												</div>
												<%-- <c:forEach var="state" items="${statemap}">
													<div class="form-group">
														<form:input type="hidden"
															path="stateDeliveryTime.state.id" />
														<form:label class="col-sm-4 control-label" value="${state.value}"
															path="stateDeliveryTime.state.stateName" />
														<div class="col-sm-8">
															<form:input type="text" class="form-control"
																path="stateDeliveryTime.deliveryTime" />
														</div>
													</div>
												</c:forEach> --%>
												<c:forEach var="stateDeliveryTime"
													items="${seller.stateDeliveryTime}" varStatus="status">
													<div class="form-group">
														<input type="hidden" class="form-control"
															name="stateDeliveryTime[${status.index}].seller.id"
															value="${seller.id}" /> <input type="hidden"
															class="form-control"
															name="stateDeliveryTime[${status.index}].state.id"
															value="${stateDeliveryTime.state.id}" /> <input
															type="hidden" class="form-control"
															name="stateDeliveryTime[${status.index}].state.stateName"
															value="${stateDeliveryTime.state.stateName}" /> <label
															class="col-sm-4 control-label">${stateDeliveryTime.state.stateName}
														</label>
														<div class="col-sm-8">
															<input type="text" class="form-control"
																name="stateDeliveryTime[${status.index}].deliveryTime" 
																value="${stateDeliveryTime.deliveryTime}" />
														</div>
													</div>
												</c:forEach>
											</div>
											<div class="col-lg-4 m-l-n"></div>
										</div>
									</div>
									<div class="col-lg-6 m-l-n">
										<img src="/O2R/seller/img/india_map.jpg" alt="india_map"
											style="width: 100%;">
									</div>

									<div class="col-sm-12">
										<div class="hr-line-dashed"></div>
										<button class="btn btn-primary pull-right" type="submit">Save</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>

				</div>
			</div>
			<div class="footer">
				<div class="pull-right">
					<img alt="image" src="/O2R/seller/img/go-easy-logo.jpg" />
				</div>
				<div>
					<a href="">Support</a> | <a href="">Contact Us</a>
				</div>
			</div>

		</div>
	</div>

	<!-- Mainly scripts -->
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/custom.js"></script>
	<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

	<!-- Custom and plugin javascript -->
	<script src="js/inspinia.js"></script>
	<script src="js/plugins/pace/pace.min.js"></script>

	<!-- Jquery Validate -->
	<script src="js/plugins/validate/jquery.validate.min.js"></script>
	<script>
		$(document).ready(function() {
			$("#form").validate({
				rules : {
					number : {
						required : true,
						number : true
					}
				}
			});
		});
	</script>
</body>

</html>