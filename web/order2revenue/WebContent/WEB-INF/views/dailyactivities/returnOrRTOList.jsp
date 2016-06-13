<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <!-- Data picker -->
   <link href="/O2R/seller/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css">
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script>


<script type="text/javascript">
$(document).ready(function () {

    //Radio toggle
    $("[name=toggler]").click(function(){
    	 $('.radio1').hide();
        $("#blk-"+$(this).val()).slideDown();
    });

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
    
   
	
	
$('#LoadOnSubmit').click(function (e) {
    e.preventDefault();
    $.ajax({
        url: $("#findOrderForm").attr("action"),
        context: document.body,
        type: 'post',
        data:$("#findOrderForm").serialize(),
        success : function(res) {
             $("#RTOTableContainer").html(res);
     }
 });

});
});


</script>
<script type="text/javascript">
    function onclickReturn(value,id) {
    	var targeturl="";
    	switch(value)
    	{
    	case "uploadReturn" :
    		targeturl="uploadOrderDA.html?value=ordersummary";
    	break;
    	case "downloadReturn" :
    		targeturl="downloadOrderDA.html?value=ordersummary";
    	break;    		
    	}
        $.ajax({
            url : targeturl,
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
  
</script>
 </head>
 <body>
  <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Returns</h5>
                        </div>
                        <div class="ibox-content overflow-h">
                        <form method="POST" action="searchReturnOrder.html?action=list" id="findOrderForm" class="form-horizontal">
                        <div class="col-sm-12">
                            <div class="col-sm-4">
                                <div class="radio"><label>
                                 <input type="radio" value="searchByProperty" id="searchType" name="toggler">Search by Attribute</label>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="radio"><label>
                                 <input type="radio" value="searchByDate" id="searchType" name="toggler"> Search by Date </label>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 radio1" id="blk-searchByProperty">
                             <div class="row">
                                <div class="col-md-4">
                                    <select class="form-control" id="searchCriteria" name="searchCriteria">
                                   	<option value="channelOrderID">Order ID/PO No</option>
							    	<option value="awbNum">AWB</option>
							    	<option value="invoiceID">Invoice ID</option>
							    	<option value="subOrderID">Sub Order ID</option>
							    	<option value="PIreferenceNo">PI Reference No</option>
							    	<option value="uniqueItemId">Unique Item ID</option>
							    	<option value="returnOrRTOId">Sale Return ID/ Debit Note No</option>
							    	<option value="pcName">Partner</option> 
							    	<option value="productSkuCode">Product SKU</option>
							    	<option value="customerName">Customer Name</option> 
							    	<option value="customerCity">Customer City</option>
							    	<option value="customerEmail">Customer mail</option>
							    	<option value="customerPhnNo">Customer Phone No</option>    	
							    	<option value="status"> Order Status</option>
							    	<option value="sellerNote">Seller Notes</option> 
                                    </select>
                                </div>
                                <div class="col-md-5">
                                    <input type="text" id="searchString" name="searchString" class="form-control" placeholder="Search String">
                                </div>
                               
                            </div>
                        </div>
                        <div class="col-sm-12 radio1" id="blk-searchByDate">
                             <div class="form-group">
                                        <div class="col-md-4">
                                            <select class="form-control" id="searchDateCriteria" name="searchDateCriteria">
                                            <option value="orderDate">Order Received Date</option>
									    	<option value="shippedDate">Order Shipped Date</option>
									    	<option value="deliveryDate">Order Delivery Expected Date</option> 
									    	<option value="paymentDueDate">Payment Due Date</option>
									    	  	<option value="dateofPayment">Actual Date of Payment</option>
									    	
                                            </select>
                                        </div>
                                        <div class="col-md-3" id="data_1">
                                            <div class="input-group date">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" name="startDate" id="startDate" class="form-control" value="Start Date">
                                            </div>
                                        </div>
                                        <div class="col-md-3" id="data_2">
                                            <div class="input-group date">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text"  name="endDate" id="endDate" class="form-control" value="End Date">
                                            </div>
                                        </div>
                                       
                                     </div>
                        </div>
	                         <div class="col-md-2">
	                                    <button class="btn btn-success " type="button"  id="LoadOnSubmit"><i class="fa fa-search"></i>&nbsp;&nbsp;<span class="bold">Search</span></button>
	                         </div>
	                         <div class="col-sm-12">
										<div class="hr-line-dashed"></div>
										<a href="#" onclick="onclickReturn('uploadReturn',0)"
											class="btn btn-success btn-xs">Bulk Upload RTO/Return</a>&nbsp;&nbsp;
										<a href="#"
											onclick="onclickReturn('downloadReturn',0)"
											class="btn btn-success btn-xs">Download RTO/Return Format</a>
							</div>
                        </form>
                        <div class="col-lg-12">
                                <div class="hr-line-dashed"></div>	
                                <div id="RTOTableContainer"></div>					
                        </div>
						</div>
					</div>
				</div>
			</div>
			
			

 </body>
</html>