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

                            <h5>${reportName}</h5>

                          <!--  <h5>Report</h5> -->

                        </div>

                        <div class="ibox-content report-links">

                          <form method="POST" action="getReport.html" id="selectReportForm"

                        class="form-horizontal" name="selectReportForm">

                        <input type="hidden" name="reportName" id="reportName" value="${reportName}"/>

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

          

                        <div class="col-sm-12">

                        <div class="hr-line-dashed"></div>

                            <h2><small>Select Headers</small></h2>

                            <div class="col-md-3">
     	                        <div class="checkbox" ><label> <input type="checkbox" class="checkbox1" value="SelectAll"  id="selectall" name="headers">Select All</label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="orderId" name="headers"> Order Id</label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="invoiceId" name="headers"> Invoice Id </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="Partner" name="headers"> Partner </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="Category" name="headers"> Category </label></div>
                                <div class="checkbox"><label> <input type="checkbox"  class="checkbox1" value="recievedDate" name="headers"> Received Date </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="shippedDate" name="headers"> Shipped Date </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="dateOfPayment" name="headers"> Expected Date of Payment </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="actualPaymentDate" name="headers"> Actual Payment Date </label></div>
                            </div>

                            <div class="col-md-3">
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="returnDate" name="headers"> Return Date </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netSaleQty" name="headers"> Net Sale Qty </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="returnQty" name="headers"> Return/RTO Qty </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="grossSaleQty" name="headers"> Gross Sale Qty </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="totalSP" name="headers"> Total SP </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netNR" name="headers"> Net N/R </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netReturnCharges" name="headers"> Net Return Charges </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netPaymentResult" name="headers"> Net Payment Result </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="paymentDiff" name="headers"> Payment Diff Amt </label></div>
                            </div>
                            
                            <div class="col-md-3">
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="grossCommSell" name="headers"> Gross Commission(Selling Fee) </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="pcc" name="headers"> PCC </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="fixedFee" name="headers"> Fixed Fee </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="shippingCharges" name="headers"> Shipping Charges </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="paymentDiff" name="headers"> Payment Diff Amt </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="servceTax" name="headers"> Service Tax </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="taxSP" name="headers"> Tax(SP) </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="taxPO" name="headers"> Tax(PO Price) </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="grossComm" name="headers"> Gross Commission to be paid </label></div>
                            </div>
                            
                            <div class="col-md-3">
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="returnComm" name="headers"> Return Commision </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="additonalCharges" name="headers"> Additional return Charges </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netComm" name="headers"> Net Parnet Commission paid </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="tds10" name="headers"> TDS to be deducted @ 10% </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="tds2" name="headers"> TDS to be deducted @ 2% </label></div>
                            	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netTaxToPay" name="headers"> Net Tax to be paid </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="finalStatus" name="headers"> Final Status </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netEoss" name="headers"> Net EOSS Value </label></div>
                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="netPR" name="headers"> Net P/R </label></div>
                            </div>
                            
                        </div>

						<input type="hidden" name="requestType" id="requestType" value=""/>

                        <div class="col-sm-12">

                        <div class="hr-line-dashed"></div>

                        <button class="btn btn-primary pull-right mar-left-20" type="submit" onclick="submitReport('download')" >Download Report</button>

                        <button class="btn btn-success pull-right" type="submit" onclick="submitReport('view')">View Complete Report</button>

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

       if(value=='download')

              {

              //document.getElementById("requestType").value = "download";

              document.getElementById('selectReportForm').action = "downloadOrderReport.html";

              }
       else
    	   {
    	   document.getElementById('selectReportForm').action = "getPartnerReport.html";
    	   }

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