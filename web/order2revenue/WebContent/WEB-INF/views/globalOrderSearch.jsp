<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="globalcsslinks.jsp"></jsp:include>
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
    
    $("#saveOrderPaymentForm").validate();
    $("#returnOrRTOId").rules("add", {
        required:true,
        messages: {
               required: "Please Enter  Payment Date."
        }
     });
  
});

function setOrderID(orderid,channelorderid,value)
{
	if(value=='return')
		{
	setVisibility('register', 'inline');
	setVisibility('payment', 'none');
	document.getElementById("returnorderId").value=orderid;
	document.getElementById("returnchannelOrderID").value=channelorderid;
}
	else
		{
		setVisibility('payment', 'inline');
		document.getElementById("paymentOrderId").value=orderid;
		document.getElementById("paymentChannelOrderID").value=channelorderid;
		}

}

function setVisibility(id, visibility) {
	document.getElementById(id).style.display = visibility;
	}
	
function onclickNavigateOrder(id)
{
	   $.ajax({
           url :"viewOrderDA.html?orderId="+id,
           success : function(data) {
               $('#centerpane').html(data);
           }
       });
	}

</script>
 </head>
 <body>
  <div id="wrapper">
<jsp:include page="sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="globalheader.jsp"></jsp:include>  
      <div class="wrapper wrapper-content animated fadeInRight" id="centerpane"> 
  <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Search Results</h5>
                        </div>
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
                                    <th>Status</th>
                                    <th colspan="2">Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${!empty searchOrderList}">
                                <c:forEach items="${searchOrderList}" var="searchOrder" varStatus="loop">
                                <tr>
                                    <td>${loop.index+1}</td>
                                   <td>${searchOrder.pcName}</td>
                                    <td><a href="#" onclick="onclickNavigateOrder(${searchOrder.orderId})">${searchOrder.channelOrderID}</a></td>
                                    <td>${searchOrder.status}</td>
                                    <td><fmt:formatDate value="${searchOrder.returnLimitCrossed}" pattern="MMM dd ,YY"/></td>
                                     <td>${searchOrder.orderPayment.netPaymentResult}</td>
                                      <td>${searchOrder.status}</td>
                                       <c:if test="${empty searchOrder.orderReturnOrRTO.returnDate}">
                                     <td><label onclick="setOrderID(${searchOrder.orderId},'${searchOrder.channelOrderID}','return')" style='cursor: pointer;'>Mark Return</label> 
                                     </td>
                                     </c:if>
                                       <td><label onclick="setOrderID(${searchOrder.orderId},'${searchOrder.channelOrderID}','payment')" style='cursor: pointer;'>Mark Paid</label> 
                                     </td>
                                       </tr>
                                </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            
    <!--  Code for Payment and Return Popup boxes -->
    
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
<div id="payment"   >
  <div class="row" >
                <div class="col-lg-12"  >
                    <div class="ibox float-e-margins" style="border:solid">
                        <div class="ibox-title">
                            <h5>Add Order Payment</h5>
                        </div>
 			<div class="ibox-content add-company">
                        <form:form method="POST" action="saveManualPayment.html" 
                             id="saveOrderPaymentForm" role="form" class="form-horizontal">
                            
                        <input  type="hidden" name="orderId" id="paymentOrderId"  value=""  />
                           <input  type="hidden" name="channelOrderID" id="paymentChannelOrderID"  value=""  />
                       
  							<div class="col-sm-12">
                                    <div class="form-group"><label class="col-sm-5 control-label">Positive Payment</label>

                                    <div class="col-sm-7"><form:input path="orderPayment.positiveAmount" 
                                      class="form-control"  id="positiveAmount"  width='200'/><span id="positiveAmount" style="color:red"></span>
                                  </div>
                                    </div>
                                     <div class="form-group"><label class="col-sm-5 control-label">Negative Payment</label>
								     <div class="col-sm-7"><form:input path="orderPayment.negativeAmount"    class="form-control"  
								     id="negativeAmount" width='200'/>
                                  </div>
                                    </div>                      
                                <div class="form-group"><label class="col-sm-5 control-label">Payment Date</label>
                                    <div class="col-md-7" id="data_1">
                                            <div class="input-group date">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <form:input class="form-control"  id="dateofPayment" path="orderPayment.dateofPayment"   width='200'/>
                                            </div>
                                        </div>
                                    </div>
                               </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                   &nbsp; &nbsp; &nbsp; <button class="btn btn-primary pull-right" type="submit"  style="padding-left:10px">Save</button>   &nbsp; &nbsp; &nbsp;
                                    &nbsp;   <button class="btn btn-primary pull-right" type="button"  onclick="setVisibility('payment', 'none');">Cancel</button>&nbsp; &nbsp; &nbsp;
                                </div>
                            </form:form>
                        </div>
                        </div>
                </div>
            </div>
</div>
            
     </div>
 <jsp:include page="globalfooter.jsp"></jsp:include>

    </div>
</div>

<jsp:include page="globaljslinks.jsp"></jsp:include>
</html>