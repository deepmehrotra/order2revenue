<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <style type="text/css">
span.#error{
  color: red;
  font-weight: bold;
}
</style>
<link href="/O2R/seller/css/plugins/chosen/chosen.css" rel="stylesheet">
<script type="text/javascript">
    function submitExpense(){
    	     $.ajax({
                    url: $("#addManualPaymentForm").attr("action"),
                    context: document.body,
                    type: 'post',
                    data:$("#addManualPaymentForm").serialize(),
                    success : function(res) {
                                  
                        $("#centerpane").html(res);
                   
                }
             });
      
    };
</script>      

 </head>
 <body>
  <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Create Payments</h5>
                        </div>
                        <div class="ibox-content overflow-h">
                            <form:form method="POST" action="saveManualPayment.html" id="addManualPaymentForm"
                             role="form" class="form-horizontal">
 							
                            <div class="col-sm-12">
                                <div class="col-sm-6">
                        
                                     <div class="mar-btm-20-dt"><label class="col-sm-5 control-label">Select OrderId</label>
                                    <div class="col-sm-8 input-group pad-lr">
                                        <form:select path="orderId"  class="chosen-select" style="width:80%;" tabindex="2">
                                      		<c:forEach var="chorderId" items="${orderIdmap}">
                                      		
											<option value="${chorderId.key}">${chorderId.value}</option>
											</c:forEach>
                                          
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="mar-btm-20-dt"><label class="col-sm-5 control-label">Date of Payment</label>
                                    <div class="col-md-7" id="data_1">
                                            <div class="input-group date">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i>
                                            </span><form:input id="my" path="orderPayment.dateofPayment" value="${order.orderPayment.dateofPayment}" 
                                    class="form-control"/>
                                            </div>
                                        </div>
                                    </div>
                                   
                                         <div class="mar-btm-20-dt"><label class="col-sm-5 control-label">Negative Amount</label>
                                    <div class="col-sm-7"><form:input path="orderPayment.negativeAmount" value="${order.orderPayment.negativeAmount}" 
                                    class="form-control"/></div>
                                    </div>
                                <div class="mar-btm-20-dt"><label class="col-sm-4 control-label">Positive Amount</label>
                                    <div class="col-sm-8"><form:input path="orderPayment.positiveAmount" value="${order.orderPayment.positiveAmount}" 
                                    class="form-control" /></div>
                                    </div>
                                  
                                </div>
                             
                            </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                    <button class="btn btn-primary pull-right" type="submit" onclick="submitOrder()">Save</button>
                                </div>
                            </form:form>
                        </div>
                </div>
            </div>
        </div>
        <!-- Chosen -->
    <script src="/O2R/seller/js/plugins/chosen/chosen.jquery.js"></script>
        <script>
    $(document).ready(function(){
        $('#data_1 .input-group.date').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
            });
        var config = {
                '.chosen-select'           : {},
                '.chosen-select-deselect'  : {allow_single_deselect:true},
                '.chosen-select-no-single' : {disable_search_threshold:10},
                '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
                '.chosen-select-width'     : {width:"95%"}
            }
            for (var selector in config) {
                $(selector).chosen(config[selector]);
            }
    });
</script>
<script>
function submitOrder(){
 var validator = $("#addManualPaymentForm").validate({
	  rules: {
		  
		  pcName: {
				required: true,
				    }
 		  
		  },
     messages: {
    	 pcName: "Please select partner"
     }
 });
 
}
</script>

 </body>
</html>