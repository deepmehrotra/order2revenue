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
#sellerImage img {
        height: 80px;
        width: 80px;
        object-fit: contain;
        position: relative;
    	top: -25px;
    	left: 50px;
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
								<c:if test="${passwordStatus != null }"><div align="center" style="width: 82%;float: left;">${passwordStatus}</div></c:if>
								<div class="ibox-tools pull-right">
									<a href="#"	class="btn btn-primary btn-xs password">Change Password</a>
								</div>
							</div>
							<div id="showChangeBox" class="col-lg-12" style="padding: 11px 0px 8px 0px;">
								<form:form method="POST" action="changePassword.html" role="form" id="changePassword">
									<div class="col-sm-3" style="display: inline-flex;">							
										<label style="width: 35%;">Old Password</label>
										<input type="password" name="oldPass" class="form-control" style="width: 65%;" required />
									</div>
									<div class="col-sm-3" style="display: inline-flex;">
										<label style="width: 35%;">New Password</label>
										<input type="password" name="newPass" class="form-control" style="width: 65%;" required />
									</div>
									<div class="col-sm-3" style="display: inline-flex;">
										<label style="width: 35%;">Re-Type Password</label>
										<input type="password" class="form-control" style="width: 65%;" required />
									</div>
									<div class="col-sm-3">
										<button type="submit" class="btn btn-primary btn-xs" style="margin-top: 6px;margin-left: 139px;">Save Changes</button>
									</div>
								</form:form>
							</div>
							<div class="ibox-content add-company">
								<form:form method="POST" action="saveSeller.html" role="form"
									class="form-horizontal" id="addSellerForm" enctype="multipart/form-data">
									<div class="col-sm-6">
										<div class="form-group">
											<label class="col-sm-4 control-label">Seller Name</label>
											<c:if test="${!empty seller.id}">
												<form:input type="hidden" class="form-control"
													value="${seller.id}" path="id" />
												<form:input type="hidden" class="form-control" 
													value="${seller.email}" path="email" />
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
												<label class="form-control">${seller.email}</label>																								
											</div>
										</div>
										<div class="hr-line-dashed"></div>
									</div>

									<h4 align="center">Seller Brand</h4>
									<br>
									<div class="col-sm-12">
										<div class="col-md-2" id="sellerImage">
											<c:choose>
												<c:when test="${seller.logoUrl != null}">
	        											<img alt="image" class="img-circle" src="${seller.logoUrl}" />
	    										</c:when>
	    										<c:otherwise>
	        											<img alt="image" class="img-circle" src="/O2R/sellerimages/defaultSeller.jpg" />
	    										</c:otherwise>	
    										</c:choose>
										</div>
										<div class="col-md-4">
										</div>
										<div class="col-md-6" >
											<label title="Upload image file" for="image"
													class="btn btn-white btn-block"> <i
													class="fa fa-upload"></i> <input type="file"
													accept="image/*" name="image" id="image" class="hide">
													Upload Logo
											</label>
											<div class="hr-line-dashed"></div>										
										</div>	
																
									</div>
								

									<h4 style="text-align: center;">Expected Delivery Time</h4>
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
												<c:if test="${!empty stateTimes}">
												<c:forEach var="stateDeliveryTime"
													items="${stateTimes}" varStatus="status">
													<div class="form-group">
														<%-- <input type="hidden" class="form-control"
															name="stateDeliveryTime[${status.index}].seller.id"
															value="${seller.id}" /> <input type="hidden"
															class="form-control"
															name="stateDeliveryTime[${status.index}].state.id"
															value="${stateDeliveryTime.state.id}" /> <input
															type="hidden" class="form-control"
															name="stateDeliveryTime[${status.index}].state.stateName"
															value="${stateDeliveryTime.state.stateName}" /> --%> 
															<label
															class="col-sm-4 control-label">${stateDeliveryTime.key}
														</label>
														<div class="col-sm-8">
															<input type="text" class="form-control"
																name="sdt-${stateDeliveryTime.key}" 
																value="${stateDeliveryTime.value}" />
														</div>
													</div>
												</c:forEach>
												</c:if>
											</div>
											<div class="col-lg-4 m-l-n"></div>
										</div>
									</div>
									<div class="col-lg-6 ">
										<img src="/O2R/seller/img/india_map.jpg" alt="india_map"
											style="width: 71%;margin-left:100px;">
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
			<jsp:include page="../globalfooter.jsp"></jsp:include>
			

		</div>
	</div>

	<!-- Mainly scripts -->
<jsp:include page="../globaljslinks.jsp"></jsp:include>
	<script>
		$(document).ready(function() {
			
			$("#showChangeBox").hide();
			$(".password").click(function(){
		        $("#showChangeBox").toggle(1500);
		    });
			
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