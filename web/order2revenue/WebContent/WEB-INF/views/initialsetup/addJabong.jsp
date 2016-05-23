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
				<div class="ibox-title">
					<img src="/O2R/partnerimages/Jabong.jpg" alt="logo">
				</div>
				<div class="ibox-content add-company">
					<form:form method="POST" action="saveJabong.html"
						id="addpartnerform" name="addpartnerform" role="form"
						class="form-horizontal" enctype="multipart/form-data">

						<div class="col-sm-4">
							<div class="radio">
								<label> <form:radiobutton path="paymentType"
										value="paymentcycle" id="1" name="toggler"
										onChange="handleRadioEvent(this);" />Subdivided Monthly
								</label>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="radio">
								<label> <form:radiobutton path="paymentType"
										value="datewisepay" id="2" name="toggler"
										onChange="handleRadioEvent(this);" />Delivery/ Shipped Date
								</label>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="radio">
								<label> <form:radiobutton path="paymentType"
										value="monthly" id="3" name="toggler"
										onChange="handleRadioEvent(this);" /> Monthly
								</label>
							</div>
						</div>
						<div class="col-sm-12 radio1" id="blk-1">
							<div class="col-sm-6">
								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">Start Date</label>
									<div class="col-sm-8">
										<form:input path="startcycleday"
											value="${partner.startcycleday}"
											placeholder="Duration of Payment from Start Date"
											class="form-control" />
									</div>
								</div>

								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">End Date</label>
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
									<label class="col-sm-4 control-label">Payment Date</label>
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
										<form:select path="paycyclefromshipordel" items="${datemap}"
											class="form-control" name="account" id="paymentField">
										</form:select>
									</div>
								</div>
							</div>
							<small class="help-block">(For ex: If your Payment cycle
								is staring from 5th May to 10th May and Payment date for that
								cycel is 15th May , then you Start Date will have 5 Duration
								will have 5 and Payment from SD will have 10)</small>
						</div>
						<div class="col-sm-12 radio1" id="blk-2">
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
						<div class="col-sm-12 radio1" id="blk-3">
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
							<label> ALIAS NAME &nbsp; &nbsp; &nbsp; <form:input
									path="pcDesc" value="${partner.pcDesc}" class="form-control" />
							</label>

							<div class="col-sm-12">
								<div class="hr-line-dashed"></div>
								<div class="col-sm-4">
									<div class="radio">
										<label>NET COMMISSION</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="radio">
										<label> <form:radiobutton
												path="nrnReturnConfig.commissionType" value="fixed" id="4"
												name="toggler" onChange="handleRadioEvent(this);"
												class="commissionType" />FIXED
										</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="radio">
										<label> <form:radiobutton
												path="nrnReturnConfig.commissionType" value="categoryWise"
												id="5" class="commissionType" name="toggler"
												onChange="handleRadioEvent(this);" /> CATEGORY WISE
										</label>
									</div>
								</div>
								<div class="col-sm-12 radio1" id="blk-4">
									<div class="row">
										<div class="input-group m-b col-md-4">
											<input type="text" class="form-control"
												name="nr-fixedCommissionPercent"
												value="${fixedCommissionPercent}"> <span
												class="input-group-addon">%</span>
										</div>
									</div>
								</div>
								<div class="col-sm-12 radio1" id="blk-5">
									<div class="row">
										<div class="col-md-4">
											<c:if test="${!empty categoryList}">
												<c:forEach items="${categoryList}" var="category"
													varStatus="loop">
													<div class="form-group col-md-12">
														<label class="col-md-4 control-label">${category}</label>
														<div class="input-group m-b col-md-4">
															<input type="text" class="form-control"
																name='nr-comm-${category}'>
														</div>
													</div>
												</c:forEach>
											</c:if>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="hr-line-dashed"></div>
								<div class="col-sm-4">
									<div class="radio">
										<label>TAX (ON SP)</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="radio">
										<label> <form:radiobutton
												path="nrnReturnConfig.taxSpType" value="fixed" id="6"
												name="toggler" onChange="handleRadioEvent(this);"
												class="taxSpType" />FIXED
										</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="radio">
										<label> <form:radiobutton
												path="nrnReturnConfig.taxSpType" value="categoryWise" id="7"
												class="taxSpType" name="toggler"
												onChange="handleRadioEvent(this);" /> CATEGORY WISE
										</label>
									</div>
								</div>

								<div class="col-sm-12 radio1" id="blk-6">
									<div class="row">
										<div class="input-group m-b col-md-4">
											<input type="text" class="form-control"
												name="nr-fixedTaxSpPercent"
												value="${fixedCommissionPercent}"> <span
												class="input-group-addon">%</span>
										</div>
									</div>
								</div>
								<div class="col-sm-12 radio1" id="blk-7">
									<div class="row">
										<div class="col-md-4">
											<c:if test="${!empty categoryList}">
												<c:forEach items="${categoryList}" var="category"
													varStatus="loop">
													<div class="form-group col-md-12">
														<label class="col-md-4 control-label">${category}</label>
														<div class="input-group m-b col-md-4">
															<input type="text" class="form-control"
																name='nr-taxSp-${category}'>
														</div>
													</div>
												</c:forEach>
											</c:if>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="hr-line-dashed"></div>
								<div class="col-sm-4">
									<div class="radio">
										<label>TAX (ON PO PRICE)</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="radio">
										<label> <form:radiobutton
												path="nrnReturnConfig.taxPoType" value="fixed" id="8"
												name="toggler" onChange="handleRadioEvent(this);"
												class="taxPoType" />FIXED
										</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="radio">
										<label><form:radiobutton
												path="nrnReturnConfig.taxPoType" value="categoryWise" id="9"
												class="taxPoType" name="toggler"
												onChange="handleRadioEvent(this);" /> CATEGORY WISE</label>
									</div>
								</div>

								<div class="col-sm-12 radio1" id="blk-8">
									<div class="row">
										<div class="input-group m-b col-md-4">
											<input type="text" class="form-control"
												name="nr-fixedTaxPoPercent"
												value="${fixedCommissionPercent}"> <span
												class="input-group-addon">%</span>
										</div>
									</div>
								</div>
								<div class="col-sm-12 radio1" id="blk-9">
									<div class="row">
										<div class="col-md-4">
											<c:if test="${!empty categoryList}">
												<c:forEach items="${categoryList}" var="category"
													varStatus="loop">
													<div class="form-group col-md-12">
														<label class="col-md-4 control-label">${category}</label>
														<div class="input-group m-b col-md-4">
															<input type="text" class="form-control"
																name='nr-taxPo-${category}'>
														</div>
													</div>
												</c:forEach>
											</c:if>
										</div>
									</div>
								</div>
								<div class="ibox float-e-margins">

									<input class="btn btn-primary pull-right" id="submitButton"
										type="submit" value="Save">

								</div>
							</div>
						</div>
					</form:form>
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

	<script>
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