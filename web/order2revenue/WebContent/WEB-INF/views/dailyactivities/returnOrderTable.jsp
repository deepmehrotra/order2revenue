
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
#register {
		position: fixed;
		left: 15%;
		top: 20%;
		width: 400px;
		padding: 10px;
		display: none;
       }
  </style>
  <style>
     #payment {
position: fixed;
left: 15%;
top: 20%;
width: 400px;
padding: 10px;
display: none;
     }
  </style>         


<script type="text/javascript">
$(document).ready(function(){
    $('#data_1 .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
    
     $("#saveReturnorRTOForm").validate();
    $("#returnOrRTOId").rules("add", {
        required:true,
        messages: {
               required: "Please Enter  Return Id."
        }
     });
    $("#returnCharges").rules("add", {
        required:true,
        min: 1,
        messages: {
               required: "Return Charges  are mandatory"
        }
     });
    $("#returnDate").rules("add", {
        required:true,
        messages: {
               required: "Return Date is mandatory"
        }
     });
  
});


function setVisibility(id, visibility) {
document.getElementById(id).style.display = visibility;
}

/* function submitForm(){
	 var validator = $("#saveReturnorRTOForm").validate({
		  rules: {
			  taxCatName: {
			        required: true,
			            }, 
			  taxPercent: {
					required: true,
			        min: 1,
			        max: 100,
					number:true,
					            }    		            
			  },
	     messages: {
	    	 taxCatName: "Tax Category Name Required",
	    	taxPercent: "Tax percentage is required from 1% to 100%"
	     }
	 });
	 if(validator.form())
	 { // validation perform
			  $('form#saveReturnorRTOForm').submit();
	 } 

	}*/
	
	function setOrderID(orderid,channelorderid,value)
	{
		if(value=='return')
			{
		setVisibility('register', 'inline');
		document.getElementById("returnorderId").value=orderid;
		document.getElementById("returnchannelOrderID").value=channelorderid;
	}
		
	
	}
</script>
<div class="ibox-content">
                         <table class="table table-bordered custom-table">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Partner</th>
                                    <th>Channel Order Id</th>
                                    <th>Status</th>
                                    <th>Return Limit</th>
                                    <th>Payment Result</th>
                                    <th>Return Date</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${!empty searchOrderList}">
                                <c:forEach items="${searchOrderList}" var="searchOrder" varStatus="loop">
                                <tr>
                                    <td>${loop.index+1}</td>
                                    <td>${searchOrder.pcName}</td>
                                    <td>${searchOrder.channelOrderID}</td>
                                    <td>${searchOrder.status}</td>
                                    <td><fmt:formatDate value="${searchOrder.returnLimitCrossed}" pattern="MMM dd ,YY"/></td>
                                     <td>${searchOrder.orderPayment.netPaymentResult}</td>
                                     <td><fmt:formatDate value="${searchOrder.orderReturnOrRTO.returnDate}" pattern="MMM dd ,YY"/>
                                     </td>
                                       <c:if test="${empty searchOrder.orderReturnOrRTO.returnDate}">
                                     <td><label onclick="setOrderID(${searchOrder.orderId},'${searchOrder.channelOrderID}','return')" style='cursor: pointer;'>Mark Return</label> 
                                     </td>
                                     </c:if>
                                       <td><label onclick="setOrderID(${searchOrder.orderId},'${searchOrder.channelOrderID}','payment')" style='cursor: pointer;'>Mark Paid</label> 
                                     </td>
                                    <!-- <td class="tooltip-demo">
                                    <a href="#"><i class="fa fa-edit text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Delete Empty Category"></i></a></td>
                     -->            </tr>
                                </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
<!-- Code for Mark Return Popup -->

<div id="register"   >
  <div class="row" >
                <div class="col-lg-12"  >
                    <div class="ibox float-e-margins" style="border:solid">
                        <div class="ibox-title">
                            <h5>Mark Return Order</h5>
                        </div>
 			<div class="ibox-content add-company">
                        <form:form method="POST" action="saveReturnorRTO.html" 
                             id="saveReturnorRTOForm" role="form" class="form-horizontal">
                            
                        <input  type="hidden" name="orderId" id="returnorderId"  value=""  />
                           <input  type="hidden" name="channelOrderID" id="returnchannelOrderID"  value=""  />
                       
  							<div class="col-sm-12">
                                    <div class="form-group"><label class="col-sm-5 control-label">Return orRTO Id</label>

                                    <div class="col-sm-7"><form:input path="orderReturnOrRTO.returnOrRTOId" 
                                      class="form-control"  id="returnOrRTOId"  width='200'/><span id="returnOrRTOId" style="color:red"></span>
                                  </div>
                                    </div>
                                     <div class="form-group"><label class="col-sm-5 control-label">Return charges</label>
								     <div class="col-sm-7"><form:input path="orderReturnOrRTO.returnOrRTOChargestoBeDeducted"    class="form-control"  
								     id="returnCharges" width='200'/>
                                  </div>
                                    </div>
                                     <div class="form-group"><label class="col-sm-5 control-label">Return reason</label>
								     <div class="col-sm-7"><form:input path="orderReturnOrRTO.returnOrRTOreason"    class="form-control"  width='200'/>
                                  </div>
                                    </div>  
                                     <div class="form-group"><label class="col-sm-5 control-label">Return or RTO</label>
								     <div class="col-sm-7">
								     	<form:select path="orderReturnOrRTO.returnOrRTOstatus"  class="form-control" >
									      <form:option value="return" label="Return"/>
									         <form:option value="RTO" label="RTO"/>									      
									 </form:select>
                                    </div>        
                                    </div>                        
                                <div class="form-group"><label class="col-sm-5 control-label">Return Date</label>
                                    <div class="col-md-7" id="data_1">
                                            <div class="input-group date">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <form:input class="form-control"  id="returnDate" path="orderReturnOrRTO.returnDate"   width='200'/>
                                            </div>
                                        </div>
                                    </div>
                               </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                   &nbsp; &nbsp; &nbsp; <button class="btn btn-primary pull-right" type="submit"  style="padding-left:10px">Save</button>   &nbsp; &nbsp; &nbsp;
                                    &nbsp;   <button class="btn btn-primary pull-right" type="button"  onclick="setVisibility('register', 'none');">Cancel</button>&nbsp; &nbsp; &nbsp;
                                </div>
                            </form:form>
                        </div>
                        </div>
                </div>
            </div>
</div>
