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
                            
                            <c:if test="${reportName eq 'channelSaleReport' || reportName eq 'categoryWiseSaleReport'}">
	                            <div class="col-md-3">
		                            <div class="checkbox" ><label> <input type="checkbox" class="checkbox1" value="SelectAll"  id="selectall" name="headers">Select All</label></div>
									<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getOrderId" name="headers"> Order Id</label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getInvoiceId" name="headers"> Invoice Id </label></div>
	                                <c:if test="${reportName eq 'channelSaleReport'}">
	                                	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPartner" name="headers"> Partner </label></div>
	                                </c:if>
	                                <c:if test="${reportName eq 'categoryWiseSaleReport'}">
	                                	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getCategory" name="headers"> Category </label></div>
	                                	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getProductSku" name="headers"> SKU </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossNrAmount" name="headers"> Gross N/R Amount </label></div>
	                                </c:if>
	                            </div>
	                            <div class="col-md-3">
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossSpAmount" name="headers"> Gross SP Amount </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossQty" name="headers"> Gross Qty</label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getSaleRetNrAmount" name="headers"> Sale Return N/R Amount </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getSaleRetSpAmount" name="headers"> Sale Return SP Amount </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getSaleRetQty" name="headers"> Sale Return Qty</label></div>
    	                        </div>
    	                        <div class="col-md-3">
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getSaleRetVsGrossSale" name="headers"> Sale Return vs Gross Sale </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetNrAmount" name="headers"> Net N/R Amount </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetSpAmount" name="headers"> Net SP Amount </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetQty" name="headers"> Net Qty</label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getTaxCategory" name="headers"> Tax Category </label></div>
    	                        </div>
    	                        <div class="col-md-3">
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetTaxLiability" name="headers"> Net Tax Liability </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPr" name="headers"> Net P/R </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPaymentResult" name="headers"> NPR </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPaymentDifference" name="headers"> Payment Difference </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getFinalStatus" name="headers"> Final Status </label></div>
    	                        </div>
                            </c:if>
							<c:if test="${reportName eq 'paymentsReceievedReport'}">
								TODO
							</c:if>
							<c:if test="${reportName eq 'orderwiseGPReport'}">
								<div class="col-md-3">
		                            <div class="checkbox" ><label> <input type="checkbox" class="checkbox1" value="SelectAll"  id="selectall" name="headers">Select All</label></div>
									<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getOrderId" name="headers"> Order Id</label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getInvoiceId" name="headers"> Invoice Id </label></div>
                                	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getShippedDate" name="headers"> Shipped Date </label></div>
	                            </div>
	                            <div class="col-md-3">
                                	<div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getProductSku" name="headers"> SKU </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossQty" name="headers"> Gross Qty</label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getSaleRetQty" name="headers"> Sale Return Qty</label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetQty" name="headers"> Net Qty</label></div>
    	                        </div>
    	                        <div class="col-md-3">
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getProductCost" name="headers"> Cost of Product</label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetNrAmount" name="headers"> Net N/R </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getPr" name="headers"> Net P/R </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetReturnCharges" name="headers"> Return Charges </label></div>
    	                        </div>
    	                        <div class="col-md-3">
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getNetPaymentResult" name="headers"> Net Payment Result </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGrossProfit" name="headers"> Gross Profit </label></div>
	                                <div class="checkbox"><label> <input type="checkbox" class="checkbox1" value="getGpVsProductCost" name="headers"> %GP Vs ProductCost </label></div>
    	                        </div>
							</c:if>
							
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

              document.getElementById('selectReportForm').action = "downloadChannelReport.html";

              }
       else
    	   {
    	   document.getElementById('selectReportForm').action = "getChannelReport.html";
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