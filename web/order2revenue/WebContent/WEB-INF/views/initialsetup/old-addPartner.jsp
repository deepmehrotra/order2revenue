<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.error {
	color: red;
	font-weight:bold;
}
</style>
</head>
 <body>

<style type="text/css">
span.#error{
  color: red;
  font-weight: bold;
}
</style>

 <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Partner</h5>
                        </div>
                        <div class="ibox-content add-company">
                        <form:form method="POST" action="savePartner.html" id="addpartnerform"
                         role="form" class="form-horizontal" enctype="multipart/form-data">
                           <c:if test="${!empty partner.pcId}">
                        <%--  <form:hidden path="pcId" value="${partner.pcId}"/> --%>
                         <input type="hidden" name="pcId" id="pcId" value="${partner.pcId}"/>
                         </c:if>
                        
                                <div class="col-sm-6">
                                    <div class="form-group"><label class="col-sm-4 control-label">Partner Name</label>

                                    <div class="col-sm-8"><form:input path="pcName" value="${partner.pcName}" class="form-control" id="partnerName" onblur="checkOnBlur()"/>
                                    <span id="partnerNameMessage" style="font-weight: bold;color=red"></span></div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group"><label class="col-sm-4 control-label">Alias Name</label>

                                    <div class="col-sm-8"><form:input path="pcDesc" value="${partner.pcDesc}" class="form-control"/></div>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group"><label class="col-sm-2 control-label">Upload Brand Logo</label>

                                    <div class="col-md-4">
                                        <label title="Upload image file" for="image" class="btn btn-white btn-block">
                                        <i class="fa fa-upload"></i>
                                            <input type="file" accept="image/*" name="image" id="image" class="hide" >
                                            Upload Logo
                                        </label>
                                    </div>
                                    
                                </div>
                                

                                    <div class="hr-line-dashed"></div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="radio"><label><form:radiobutton path="paymentType" value="paymentcycle" id="paymentcycle" name="toggler"/>Subdivided Monthly</label>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="radio"><label><form:radiobutton path="paymentType" value="datewisepay" id="datewisepay" name="toggler"/>Delivery/Shipped Date </label>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="radio"><label><form:radiobutton path="paymentType" value="monthly" id="monthly" name="toggler"/> Monthly</label>
                                    </div>
                                </div>
                            
                                <div class="col-sm-12 radio1" id="blk-paymentcycle">
                                <div class="col-sm-6">
                                   <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Cycle Start date</label>
                                    <div class="col-sm-8"><form:input path="startcycleday" value="${partner.startcycleday}" placeholder="Duration of Payment from Start Date" class="form-control"/></div>
                                    </div>
                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Cycle End date</label>
                                    <div class="col-sm-8"><form:input path="paycycleduration" value="${partner.paycycleduration}" placeholder="Duration of Payment from Start Date" class="form-control"/></div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Payment date</label>
                                    <div class="col-sm-8"><form:input path="paydaysfromstartday"  value="${partner.paydaysfromstartday}" placeholder="Duration of Payment from Start Date" class="form-control"/></div>
                                    </div>

                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Payment From</label>
                                    <div class="col-sm-8">
                                    <form:select path="paycyclefromshipordel" items="${datemap}" class="form-control" name="account" id="paymentField">
                                          </form:select></div>
                                    </div>
                                    </div>
                                    <small class="help-block">(For ex: If your first payment cycle of month is staring from 5th May to 10th May and Payment date for that cycel is 15th May , then you Start Date will have 5 End day will have 10 and Payment day will have 15)</small>
                                </div>
                                <div class="col-sm-12 radio1" id="blk-datewisepay">
                                    <div class="row">
                                        <div class="col-md-6">
                                             <form:select path="isshippeddatecalc" items="${datemap}" class="form-control" id="paymentField1"> 
                                        </form:select>
                                        </div>
                                        
                                        <div class="col-md-6 payment-box" id="true"><form:input path="noofdaysfromshippeddate" id="noofdaysfromshippeddate" value="${partner.noofdaysfromshippeddate}"
                                        placeholder="Payment Days From Shipped Date" class="form-control"/></div>
                                        <div class="col-md-6 payment-box" id="false"><form:input path="noofdaysfromdeliverydate" id="noofdaysfromdeliverydate" value="${partner.noofdaysfromdeliverydate}"
                                        placeholder="Payment Days From Delivery Date" class="form-control"/></div>
                                    </div>
                                </div>
                                <div class="col-sm-12 radio1" id="blk-monthly">
                                     <div class="row">
                                        <div class="col-md-4">
                                           <form:input path="monthlypaydate" value="${partner.monthlypaydate}"  placeholder="Enter Day" class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group"><label class="col-sm-3 control-label">Max RTO Acceptance Period</label>
                                            <div class="col-md-4"><form:input path="maxRTOAcceptance" value="${partner.maxRTOAcceptance}"  placeholder="Enter Value" class="form-control"/></div>
                                            <div class="col-md-4"><select class="form-control" name="account">
                                            <option>Days</option>
                                            </select></div>
                                </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group"><label class="col-sm-3 control-label">Max Return Acceptance</label>
                                            <div class="col-md-4"><form:input path="maxReturnAcceptance" value="${partner.maxReturnAcceptance}"  placeholder="Enter Value" class="form-control"/></div>
                                            <div class="col-md-4"><select class="form-control" name="account">
                                            <option>Days</option>
                                            </select></div>
                                </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group">
                                    <div class="col-md-4"><form:checkbox path="tdsApplicable" id="tdsApplicable"/> TDS Applicable</div>
                                </div>
                                </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                 <!--    <button class="btn btn-primary pull-right" type="button"  onclick="submitform()">Save</button> -->
                                       <input type="button" class="btn btn-primary pull-right" value="Save" onclick="submitForm()"/>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
<script type="text/javascript">
$(document).ready(function(){
    $("[name=paymentType]").click(function(){
    	 $('.radio1').hide();
        $("#blk-"+$(this).val()).slideDown();
    });
    $('#paymentField').change(function () {
        $('.payment-box').hide();
        $('#'+$(this).val()).fadeIn();
    });
    $('#paymentField1').change(function () {
    	$('.payment-box').hide();
        $('#'+$(this).val()).fadeIn();
    });
    $('#data_1 .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
   
   if('${partner.paymentType}'=='paymentcycle')
	   $("#paymentcycle").prop("checked", true).trigger("click");
   else if('${partner.paymentType}'=='datewisepay')
	   {
	   $("#datewisepay").prop("checked", true).trigger("click");
	   $('#paymentField1').trigger('change');
	  if('${partner.isshippeddatecalc}'!=true)
		   {
		  $("#noofdaysfromdeliverydate").val('${partner.noofdaysfromshippeddate}');
		   }
	   }
   else if('${partner.paymentType}'=='monthly')
	   $('#monthly').prop("checked", true).trigger("click");
	 
  if('${partner.tdsApplicable}'=='true')
		   $("#tdsApplicable").prop("checked", true);

});
var nameAvailability=true;

function checkOnBlur()
{
	var partner=document.getElementById("partnerName").value;
	$.ajax({
        url: "ajaxPartnerCheck.html?partner="+partner,
       success : function(res) {
    	 	   if(res=="false")
                	{
        	nameAvailability=false;
                	 $("#partnerNameMessage").html("Partner Name  already exist");
                	}
                else
                	{
                	nameAvailability=true;
                	$("#partnerNameMessage").html("Partner Name   available");
                	}
      }
	 });
	}
function submitForm(){
 var validator = $("#addpartnerform").validate({
  rules: {                   
	  pcName: "required",
     maxReturnAcceptance: {
			required: true,
	        min: 1,
	        max:100,
			number:true,
		},
	maxRTOAcceptance:{
		required:true,
		min:1,
		max:100,
		number:true,
	}	,
	toggler:{
		required:true
	},
	startcycleday:{
		 required: function(element){
			 return (getRole() == 'paymentcycle');
		   },
		number: true,
		min:1,
		max:31,
		
	},
	paycycleduration:{
		 required: function(element){
			 return (getRole() == 'paymentcycle');
		   },
		number: true,
		min:1,
		max:31,
		
	},
	paydaysfromstartday:{
		 required: function(element){
			 return (getRole() == 'paymentcycle');
		   },
		number: true,
		min:1,
		max:31,
		
	},
	monthlypaydate:{
		 required: function(element){
			 return (getRole() == 'monthly');
		   },
		number: true,
		min:1,
		max:31,
		
	}
  	},	
     errorElement: "span",                              
     messages: {
    	 pcName: " Enter partner name",
    	 maxReturnAcceptance:"Max return required between 1 and 100",
    	 maxRTOAcceptance:"RTO acceptance required between 1 and 100",
    	 toggler:"Please select any Payment Cycle"
     }
 });
 if(validator.form()&&nameAvailability){ // validation perform
	 $('form#addpartnerform').submit();
	 }
}
function getRole() {
    return $("#addpartnerform").find("input[type=radio]:checked").val();
}
</script>                 
 </body>
</html>