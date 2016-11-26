<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<body>

  <div class="row">

                <div class="col-lg-12">

                    <div class="ibox float-e-margins">

                        <div class="ibox-title">

                            <h5>${reportNameStr}</h5>

                          <!--  <h5>Report</h5> -->

                        </div>

                        <div class="ibox-content report-links">

					<form method="POST" action="getReport.html" id="selectReportForm"
						class="form-horizontal" name="selectReportForm">

						<input type="hidden" name="reportName" id="reportName"
							value="${reportName}" />

						<div class="col-sm-12">

							<div class="form-group">
							
								<div class="col-lg-12">
									<label class="col-sm-4 control-label label-text-mrg">Select	Period</label>
									<div class="col-sm-8">	
										<div class="row">
	
											<div class="col-md-4" id="data_1">
	
												<div class="input-group date">
	
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span><input type="text"
														name="startdate" class="form-control" value="Start Date">
	
												</div>
	
											</div>
	
											<div class="col-md-4" id="data_2">
	
												<div class="input-group date">
	
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span><input type="text"
														name="enddate" class="form-control" value="End Date">
	
												</div>
	
											</div>
											<div class="col-md-4">
											</div>
										</div>
									</div>
								</div>
								
								<div class="col-lg-12" style="margin-top: 15px;">
									<label class="col-sm-4 control-label label-text-mrg">Select	Status</label>
									<div class="col-sm-8">
										
										<div class="col-md-8">
											<div class="row">
												<select class="form-control" name="statusList" id="statusList" class="chosen-select" style="width: 97.5%;">
													<option value="">All</option>
					                            	<option value="Actionable">Actionable</option>
					                            	<option value="Settled">Settled</option>
					                            	<option value="In Process">In Process</option>
					                            	<option value="Payment Recieved">Payment Received</option>
					                            	<option value="Payment Deducted">Payment Deducted</option>
					                            	<option value="Return Recieved">Return Received</option>
					                            	<option value="Return Limit Crossed">Return Limit Crossed</option>			                            				                            	
					                            </select>										
											</div>
										</div>
										<div class="col-md-4">
										</div>
									</div>
								</div>
								
							</div>
						</div>



						<div class="col-sm-12">

							<div class="hr-line-dashed"></div>

							<h2>
								<small>Select Headers</small>
							</h2>

							<div class="col-md-3">
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="SelectAll" id="selectall" name="headers">Select
										All
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="orderId" name="headers"> Order Id
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="Partner" name="headers"> Channel
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="subOrderId" name="headers"> Secondary OrderId
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnId" name="headers"> ReturnId
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="recievedDate" name="headers"> Order RecievedDate
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="shippedDate" name="headers"> Order shipped Date
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="deliveryDate" name="headers"> Expected Delivery Date
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnDate" name="headers"> Return Received Date
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="dateofPayment" name="headers"> Expected date of Payment
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="payDate" name="headers"> Actual payment Date
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="invoiceId" name="headers"> InvoiceId
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="PIreferenceNo" name="headers"> PIreferenceNo
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="productCategory" name="headers"> Product Category
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="sku" name="headers"> Vendor  SKU
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="quantity" name="headers"> Gross Sale Quantity
									</label>
								</div>	
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnQuantity" name="headers"> Return Quantity
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netSaleQuantity" name="headers"> Net Sale Quantity
									</label>
								</div>								

							</div>

							<div class="col-md-3">									
								
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="totalMRP" name="headers"> MRP
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossSp" name="headers"> Gross Taxable Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnSp" name="headers"> Return Taxable Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netSp" name="headers"> Net Taxable Sale
									</label>
								</div>								
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossNR" name="headers"> Gross Actual Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnChargesReversed" name="headers"> Reversed Actual Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="addReturnCharges" name="headers"> Add. Return Charges
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="totalReturnCharges" name="headers"> Total Return Actual Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netNR" name="headers"> Net Actual Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netPayment" name="headers"> Net Payment Result
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="payDiff" name="headers"> Net Payment Diff.
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossPR" name="headers"> Gross Tax-free Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnPR" name="headers"> Return Tax free Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netPR" name="headers"> Net Tax Free Sale
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="taxCategory" name="headers"> Tax Category
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossTax" name="headers"> Gross Tax Dues
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnTax" name="headers"> Reversed Tax Dues
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netTax" name="headers"> Net Tax Dues
									</label>
								</div>
								
							</div>

							<div class="col-md-3">								
								
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossTDS" name="headers"> Gross TDS Dues
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnTDS" name="headers"> Reversed TDS Dues
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netTDS" name="headers"> Net TDS Dues
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossCommission" name="headers"> Gross Commission Paid
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnCommission" name="headers"> Reversed Commission
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netCommission" name="headers"> Net Commission Paid
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netSellingFee" name="headers"> Net Selling Fee
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netPCC" name="headers"> Net PCC Paid
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netFixedFee" name="headers"> Net Fixed Fee Paid
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netShippingCharges" name="headers"> Net Shipping Charges Paid
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="serviceTax" name="headers"> Net Service Tax Paid
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="eossDiscount" name="headers"> Gross EOSS Disc
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="totalReturnEossDiscount" name="headers"> Reversed ROSS Disc
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netReturnEossDiscount" name="headers"> Net EOSS Disc
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossCostProduct" name="headers"> Gross Product Cost
									</label>
								</div>	
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnCostProduct" name="headers"> Return Product Cost
									</label>
								</div>						

							</div>

							<div class="col-md-3">								
								
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netCostProduct" name="headers"> Net Product Cost
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossProfit" name="headers"> Gross Margin
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="paymentType" name="headers"> Payment Type
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="customerName" name="headers"> Customer Name
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="customerCity" name="headers"> Customer City
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="logicalPartner" name="headers"> Logistic Partner
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="awbNo" name="headers"> AWB no
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnType" name="headers"> Return Type
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="faultType" name="headers"> Fault Type
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnStage" name="headers"> Return Stage
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="shippingZone" name="headers"> Shipping Zone
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnReason" name="headers"> Return Reason
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="sellerNote" name="headers"> Seller Notes
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="status" name="headers"> Last Updated Status
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="finalStatus" name="headers"> Action
									</label>
								</div>

							</div>

						</div>

						<input type="hidden" name="requestType" id="requestType" value="" />

						<div class="col-sm-12">

							<div class="hr-line-dashed"></div>

							<button class="btn btn-primary pull-right mar-left-20"
								type="button" onclick="submitReport('download')">Download
								Report</button>

							<button class="btn btn-success pull-right" type="button"
								onclick="submitReport('view')">View Complete Report</button>

						</div>

					</form>

				</div>

                    </div>

                </div>

            </div>

<script>
	$(document).ready(function() {

		$('#data_1 .input-group.date').datepicker({

			todayBtn : "linked",

			keyboardNavigation : false,

			forceParse : false,

			calendarWeeks : true,

			autoclose : true

		});

		$('#data_2 .input-group.date').datepicker({

			todayBtn : "linked",

			keyboardNavigation : false,

			forceParse : false,

			calendarWeeks : true,

			autoclose : true

		});

		$("[name=toggler]").click(function() {

			$('.radio2').hide();

			$("#blk-" + $(this).val()).slideDown();

		});

		$('#selectall').click(function(event) { //on click 
			alert(" Select all ");
			if (this.checked) { // check select status
				$('.checkbox1').each(function() { //loop through each checkbox
					this.checked = true; //select all checkboxes with class "checkbox1"               
				});
			} else {
				$('.checkbox1').each(function() { //loop through each checkbox
					this.checked = false; //deselect all checkboxes with class "checkbox1"                       
				});
			}
		});

	});

	function submitReport(value) {

		var startDate = new Date($("input[name='startdate']").val());
		var endDate = new Date($("input[name='enddate']").val());
		if (startDate == "Invalid Date" || endDate == "Invalid Date") {
			alert("Please select both the dates to proceed!");
			return;
		}

		if (startDate > endDate) {
			alert("'Start Date' cannot be after 'End Date'! Kindly correct the dates to proceed!");
			return;
		}

		if (value == 'download') {
			var timeDiff = Math.abs(new Date(startDate).getTime()
					- new Date(endDate).getTime());
			var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
			if (diffDays > 93) {
				alert("Download Report cannnot be generated for more than 3 months! Kindly change your date selection!");
				return;
			}

			var isCheckboxSelected = false;
			$('.checkbox1').each(function() {
				if (this.checked && this.value != "SelectAll") {
					isCheckboxSelected = true;
				}
			});

			if (!isCheckboxSelected) {
				alert("Please select atleast one checkbox to proceed");
				return;
			}
			document.getElementById('selectReportForm').action = "downloadreport.html";
		} else {
			document.getElementById('selectReportForm').action = "getReport.html";
		}
		$("#selectReportForm").submit();

	/* 	$.ajax({
			url : $("#selectReportForm").attr("action"),
			context : document.body,
			type : 'post',
			data : $("#selectReportForm").serialize(),
			success : function(res) {
				if($(res).find('#j_username').length > 0){
	        		window.location.href = "orderindex.html";
	        	}else{
	            	$('#centerpane').html(res);
	        	}
			}
		}); */
	}
</script>

</body>

</html>