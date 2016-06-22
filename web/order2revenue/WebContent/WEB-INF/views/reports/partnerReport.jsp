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

                        <input type="hidden" name="reportName" id="reportName" value="${reportName}"/>
						<c:if test="${reportName ne 'debtorsReport'}">
	                        <div class="col-sm-12 mar-btm-20-oh">
	                             <div class="form-group">
	                             	<label class="col-sm-4 control-label label-text-mrg">Select Period</label>
	                                <div class="col-sm-8">
	                                    <div class="row">
	                                        <div class="col-md-4" id="data_1">
	                                        <div class="input-group date">
	                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" name="startdate" class="form-control" value="Start Date">
	                                        </div>
	                                    </div>
	                                    <div class="col-md-4" id="data_2">
	                                        <div class="input-group date">
	                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input  type="text" name="enddate" class="form-control" value="End Date">
	                                        </div>
	                                    </div>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
                        </c:if>

          

                        <div class="col-sm-12">

                        <div class="hr-line-dashed"></div>

                            <h2><small>Select Headers</small></h2>

	                        	<c:if test="${reportName eq 'partnerBusinessReport'}">
		                            <div class="col-md-3">
		     	                        <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="SelectAll"  id="selectall" name="headers">Select All</label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getInvoiceID" name="headers"> Invoice Id </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getChannelOrderID" name="headers"> Order Id</label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnId" name="headers"> Return Id</label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPcName" name="headers"> Partner </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getOrderDate" name="headers"> Received Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getShippedDate" name="headers"> Shipped Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPaymentDueDate" name="headers"> Expected Date of Payment </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getDateofPayment" name="headers"> Actual Payment Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnDate" name="headers"> Return Date </label></div>
		                            </div>
		
		                            <div class="col-md-3">
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getProductCategory" name="headers"> Category </label></div>
			                            <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossSaleQuantity" name="headers"> Gross Sale Qty </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnQuantity" name="headers"> Return/RTO Qty </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetSaleQuantity" name="headers"> Net Sale Qty </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetSP" name="headers"> Total SP </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossNetRate" name="headers"> Net N/R </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetReturnCharges" name="headers"> Net Return Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTotalReturnCharges" name="headers"> Total Return Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetActualSale" name="headers"> Net Actual Sale </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPaymentResult" name="headers"> Net Payment Result </label></div>
		                            </div>
		                            
		                            <div class="col-md-3">
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPaymentDifference" name="headers"> Payment Diff Amt </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossPartnerCommission" name="headers"> Gross Commission(Selling Fee) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPccAmount" name="headers"> PCC </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getFixedfee" name="headers"> Fixed Fee </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getShippingCharges" name="headers"> Shipping Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getServiceTax" name="headers"> Service Tax </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTaxSP" name="headers"> Tax(SP) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTaxPOPrice" name="headers"> Tax(PO Price) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossCommissionQty" name="headers"> Gross Commission to be paid </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnCommision" name="headers"> Return Commision </label></div>
		                            </div>
		                            
		                            <div class="col-md-3">
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getAdditionalReturnCharges" name="headers"> Additional return Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPartnerCommissionPaid" name="headers"> Net Parnet Commission paid </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTdsToBeDeducted10" name="headers"> TDS to be deducted @ 10% </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTdsToBeDeducted2" name="headers"> TDS to be deducted @ 2% </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetTaxToBePaid" name="headers"> Net Tax to be paid </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetEossValue" name="headers"> Net EOSS Value </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPr" name="headers"> Net P/R </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossProfit" name="headers"> Gross Profit </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getFinalStatus" name="headers"> Final Status </label></div>
		                            </div>
								</c:if>
	                        	<c:if test="${reportName eq 'partnerCommissionReport'}">
									<div class="col-md-3">
		     	                        <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="SelectAll"  id="selectall" name="headers">Select All</label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getInvoiceID" name="headers"> Invoice Id </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getChannelOrderID" name="headers"> Order Id</label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPcName" name="headers"> Partner </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getOrderDate" name="headers"> Received Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getShippedDate" name="headers"> Shipped Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPaymentDueDate" name="headers"> Expected Date of Payment </label></div>
		                            </div>
		
		                            <div class="col-md-3">
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getDateofPayment" name="headers"> Actual Payment Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnDate" name="headers"> Return Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnId" name="headers"> ReturnID/GP-ID </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getProductCategory" name="headers"> Category </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetSaleQuantity" name="headers"> Net Sale Qty </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossPartnerCommission" name="headers"> Gross Commission(Selling Fee) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPccAmount" name="headers"> PCC </label></div>
		                            </div>
		                            
		                            <div class="col-md-3">
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getFixedfee" name="headers"> Fixed Fee </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getShippingCharges" name="headers"> Shipping Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getServiceTax" name="headers"> Service Tax </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTaxSP" name="headers"> Tax(SP) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTaxPOPrice" name="headers"> Tax(PO Price) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossCommissionQty" name="headers"> Gross Commission to be paid </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnCommision" name="headers"> Return Commision </label></div>
		                            </div>
		                            
		                            <div class="col-md-3">
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getAdditionalReturnCharges" name="headers"> Additional return Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPartnerCommissionPaid" name="headers"> Net Parnet Commission paid </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnChargesDesciption" name="headers"> Return Charges Desciption </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTdsToBeDeposited" name="headers"> TDS to be deducted </label></div>
										<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPaymentDifference" name="headers"> Payment Diff Amt </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getFinalStatus" name="headers"> Final Status </label></div>
		                            </div>
								</c:if>
								<c:if test="${reportName eq 'debtorsReport'}">
		                            <div class="col-md-3">
		     	                        <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="SelectAll"  id="selectall" name="headers">Select All</label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getChannelOrderID" name="headers"> Order Id</label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnId" name="headers"> Return Id </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getAwb" name="headers"> AWB </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getInvoiceID" name="headers"> Invoice Id </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getSubOrderId" name="headers"> Sub Order Id </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPiRefNumber" name="headers"> PI Reference No </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPcName" name="headers"> Partner </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getLogisticPartner" name="headers"> Logistics Partner </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getOrderDate" name="headers"> Received Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getShippedDate" name="headers"> Shipped Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getDeliveryDate" name="headers"> Expected Date of Delivery </label></div>
		                            </div>
		
		                            <div class="col-md-3">
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPaymentDueDate" name="headers"> Expected Date of Payment </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnDate" name="headers"> Return Date </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getCustomerName" name="headers"> Customer Name </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getCustomerEmail" name="headers"> Customer Email </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getCustomerPhone" name="headers"> Customer Phone </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getCustomerZip" name="headers"> Customer Zip Code </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getCustomerCity" name="headers"> Customer City </label></div>
			                            <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossSaleQuantity" name="headers"> Gross Sale Qty </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnQuantity" name="headers"> Return/RTO Qty </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetSaleQuantity" name="headers"> Net Sale Qty </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getOrderSP" name="headers"> Gross SP </label></div>
		                            </div>
		                            
		                            <div class="col-md-3">
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnSP" name="headers"> Return SP </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetSP" name="headers"> Net SP </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetRate" name="headers"> Net N/R </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getAdditionalReturnCharges" name="headers"> Additional return Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossReturnChargesReversed" name="headers"> Gross Return Charges Reversed </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTotalReturnCharges" name="headers"> Total Return Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetActualSale" name="headers"> Net Actual Sale </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossPartnerCommission" name="headers"> Gross Commission(Selling Fee) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPccAmount" name="headers"> PCC </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getFixedfee" name="headers"> Fixed Fee </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getShippingCharges" name="headers"> Shipping Charges </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getServiceTax" name="headers"> Service Tax </label></div>
		                            </div>
		                            
		                            <div class="col-md-3">
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTaxSP" name="headers"> Tax(SP) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTaxPOPrice" name="headers"> Tax(PO Price) </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossCommissionQty" name="headers"> Gross Commission to be paid </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnCommision" name="headers"> Return Commision </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPartnerCommissionPaid" name="headers"> Net Parnet Commission paid </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossTds" name="headers"> Total Gross TDS </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getReturnTds" name="headers"> Total Return TDS </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTdsToBeDeposited" name="headers"> TDS to be Deposited </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetTaxToBePaid" name="headers"> Net Tax To Be Paid </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPaymentResult" name="headers"> Net Payment Result </label></div>
		                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPaymentDifference" name="headers"> Payment Diff Amt </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getFinalStatus" name="headers"> Final Status </label></div>
		                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getProductCategory" name="headers"> Category </label></div>
		                            </div>
								</c:if>

                        </div>

						<input type="hidden" name="requestType" id="requestType" value=""/>

                        <div class="col-sm-12">

                        <div class="hr-line-dashed"></div>

                        <button class="btn btn-primary pull-right mar-left-20" type="button" onclick="submitReport('download')" >Download Report</button>

                        <button class="btn btn-success pull-right" type="button" onclick="submitReport('view')">View Complete Report</button>

                        </div>

                          </form>     

                        </div>

                    </div>

                </div>

            </div>

<script>

    $(document).ready(function(){

        $('#data_1 .input-group.date').datepicker({

                todayBtn: "linked",

                keyboardNavigation: false,

                forceParse: false,

                calendarWeeks: true,

                autoclose: true

            });

        $('#data_2 .input-group.date').datepicker({

                todayBtn: "linked",

                keyboardNavigation: false,

                forceParse: false,

                calendarWeeks: true,

                autoclose: true

            });

 

        $("[name=toggler]").click(function(){
        	alert("TOGGLER CLICK");

            $('.radio2').hide();

            $("#blk-"+$(this).val()).slideDown();

        });
        
    
            $('#selectall').click(function(event) {  //on click 
            	alert(" Select all ");
                if(this.checked) { // check select status
                    $('.checkbox1').each(function() { //loop through each checkbox
                        this.checked = true;  //select all checkboxes with class "checkbox1"               
                    });
                }else{
                    $('.checkbox1').each(function() { //loop through each checkbox
                        this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                    });         
                }
            });
            
        

    });

    function submitReport(value){
    	
    	var reportName = "${reportName}";
    	
    	if(!(reportName == "debtorsReport")){
        	var startDate = new Date($("input[name='startdate']").val());
        	var endDate = new Date($("input[name='enddate']").val());
        	if(startDate == "Invalid Date" || endDate == "Invalid Date"){
        		alert("Please select both the dates to proceed!");
        		return;
        	}

        	if(startDate < endDate){
        		alert("'Start Date' cannot be before 'End Date'! Kindly correct the dates to proceed!");
        		return;
        	}
    	}

    	if(value=='download'){
    		if(!(reportName == "debtorsReport")){
	    	   	var timeDiff = Math.abs(new Date(startDate).getTime() - new Date(endDate).getTime());
	    	   	var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	    	   	if(diffDays > 90){
	        	   	alert("Download Report cannnot be generated for more than 90 days! Kindly change your date selection!");
	        	   	return;
	    	   	}
    		}
    	   
    	   	var isCheckboxSelected = false;
    	   	$('.checkbox1').each(function() {
   	   			if(this.checked && this.value!="SelectAll"){
    	   			isCheckboxSelected = true;
    	   		}               
           	});
    	   
    	   	if(!isCheckboxSelected){
    		   	alert("Please select atleast one checkbox to proceed");
    		   	return;
    	   	}
			document.getElementById('selectReportForm').action = "downloadPartnerReport.html";
		} else {
    	   	document.getElementById('selectReportForm').action = "getPartnerReport.html";
   	   	}
       	$("#selectReportForm").submit();

        $.ajax({
			url:$("#selectReportForm").attr("action"),
            context: document.body,
            type: 'post',
			data:$("#selectReportForm").serialize(),
			success : function(res) {
				$("#centerpane").html(res);
           	}
		});
	}

</script>

</body>

</html>