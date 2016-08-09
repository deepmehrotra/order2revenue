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
													<option value="">Select Status</option>
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
										value="invoiceId" name="headers"> Invoice Id
									</label>
								</div>

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnCharges" name="headers"> Return Charges
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="PIreferenceNo" name="headers"> PI Reference No
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="logicalPartner" name="headers"> Logistic Partner
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="productCategory" name="headers"> Product Category
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="totalMRP" name="headers"> Total MRP
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossNR" name="headers"> Gross N/R
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netNR" name="headers"> Net N/R
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossPR" name="headers"> Gross P/R
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnPR" name="headers"> Return P/R
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netPCC" name="headers"> Net PCC
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netFixedFee" name="headers"> Net Fixed Fee
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnTDS" name="headers"> Return TDS
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netTDS" name="headers"> Net TDS To Deposited
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="customerCity" name="headers"> Customer City
									</label>
								</div>

							</div>

							<div class="col-md-3">

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="Partner" name="headers"> Partner
									</label>
								</div>

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="recievedDate" name="headers"> Received Date
									</label>
								</div>

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="status" name="headers"> Status
									</label>
								</div>

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netRate" name="headers"> Net Receivable
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnId" name="headers"> Return ID
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="deliveryDate" name="headers"> Delivery Date
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="quantity" name="headers">Sale Quantity
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossSp" name="headers"> Total Gross SP
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="addReturnCharges" name="headers"> Additional Return Charges
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnChargesReversed" name="headers"> Gross Return Charges Reversed
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="totalReturnCharges" name="headers"> Total Return Charges
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netPR" name="headers"> Net P/R
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="taxCategory" name="headers"> Tax Category
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netShippingCharges" name="headers"> Net Shipping Charges
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="serviceTax" name="headers"> Service Tax
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossCostProduct" name="headers"> Gross Cost Of Product
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnCostProduct" name="headers"> Return Cost Of Product
									</label>
								</div>
							</div>

							<div class="col-md-3">

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="shippedDate" name="headers"> Shipped Date
									</label>
								</div>

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="payCycle" name="headers"> Payment Cycle
									</label>
								</div>

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="finalStatus" name="headers"> Final Status
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="awbNo" name="headers"> AWB
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="paymentType" name="headers"> Payment Type
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="dateofPayment" name="headers"> Actual Payment Date
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnQuantity" name="headers"> Return Quantity
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnSp" name="headers"> Return SP
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="paymentDifference" name="headers"> Payment Difference Amount
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="eossDiscount" name="headers"> Total Gross/EOSS Discount
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossTax" name="headers"> Gross Tax
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnTax" name="headers"> Return Tax
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossCommission" name="headers"> Gross Commission
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnCommission" name="headers"> Return Commission Reversed
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netCostProduct" name="headers"> Net Cost Of Product
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossProfit" name="headers"> Gross Profit
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="sellerNote" name="headers"> Seller Note
									</label>
								</div>

							</div>

							<div class="col-md-3">

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="payDate" name="headers"> Payment Date
									</label>
								</div>

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netPayment" name="headers"> Net Payment
									</label>
								</div>

								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="payDiff" name="headers"> Payment Difference
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="subOrderId" name="headers"> Sub Order ID
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="sku" name="headers"> SKU
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnDate" name="headers"> Return Date
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netSaleQuantity" name="headers"> Net Sale Quantity
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netSp" name="headers"> Net SP
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="totalReturnEossDiscount" name="headers"> Total Return/EOSS Discount
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netReturnEossDiscount" name="headers"> Total Net Return/EOSS Discount
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netTax" name="headers"> Net Tax
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netSellingFee" name="headers"> Net Selling Fee
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="netCommission" name="headers"> Net Commission
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="grossTDS" name="headers"> Gross TDS
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="returnReason" name="headers"> Return Reason
									</label>
								</div>
								<div class="checkbox">
									<label> <input type="checkbox" class="checkbox1"
										value="customerName" name="headers"> Customer Name
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
			if (diffDays > 90) {
				alert("Download Report cannnot be generated for more than 90 days! Kindly change your date selection!");
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

		$.ajax({
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
		});
	}
</script>

</body>

</html>