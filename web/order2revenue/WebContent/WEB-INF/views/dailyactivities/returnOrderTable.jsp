<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
#register {
	position: fixed;
	left: 15%;
	top: 20%;
	width: 400px;
	padding: 10px;
	display: none;
}
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
	function setOrderID(orderid,channelorderid,value)
	{
		if	(value=='return')
		{
			alert(orderid);
			alert(channelorderid);
			document.getElementById("returnorderId").value=orderid;
			document.getElementById("returnchannelOrderID").value=channelorderid;
		}
	}
	function show1(){

		var option = document.getElementById("category").value;
        var option1 = document.getElementById("category1").value;
        
        if(option == "cancellationCharges" && option1 == "sellerFault")
        {
          document.getElementById("showit").style.display="block";
        }   
      }
      function mydate() {
    	  d = new Date(document.getElementById("datefield").value);
    	  dt = d.getDate();
    	  mn = d.getMonth();
    	  mn++;
    	  yy = d.getFullYear();
    	  date = mn + "/" + dt + "/" + yy;
    	  document.getElementById("datevalue").value = date;
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
				<c:forEach items="${searchOrderList}" var="searchOrder"
					varStatus="loop">
					<tr>
						<td>${loop.index+1}</td>
						<td>${searchOrder.pcName}</td>
						<td>${searchOrder.channelOrderID}</td>
						<td>${searchOrder.status}</td>
						<td><fmt:formatDate value="${searchOrder.returnLimitCrossed}"
								pattern="MMM dd ,YY" /></td>
						<td>${searchOrder.orderPayment.netPaymentResult}</td>
						<td><fmt:formatDate
								value="${searchOrder.orderReturnOrRTO.returnDate}"
								pattern="MMM dd ,YY" /></td>
						<c:if test="${empty searchOrder.orderReturnOrRTO.returnDate}">
							<td><label data-toggle="modal" data-target="#myModal22"
								onclick="setOrderID(${searchOrder.orderId},'${searchOrder.channelOrderID}','return')"
								style='cursor: pointer;'>Mark Return</label></td>
						</c:if>
						<%-- <td><label
							onclick="setOrderID(${searchOrder.orderId},'${searchOrder.channelOrderID}','payment')"
							style='cursor: pointer;'>Mark Paid</label></td> --%>
						<!-- <td class="tooltip-demo">
                                    <a href="#"><i class="fa fa-edit text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Delete Empty Category"></i></a></td>
                     -->
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>
<!-- Code for Mark Return Popup -->

<div class="col-sm-3">
	<!-- <button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#myModal22">View</button> -->
	<form:form method="POST" action="saveReturnorRTO.html">
		<div class="modal inmodal fade" id="myModal22" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content animated bounceInRight"
					style="left: 22%; width: 50%;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Return Details</h4>
					</div>
					<div class="modal-body" style="padding: 20px 30px 50px 30px;">
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group">
									<form:input path="orderId"
												id="returnorderId" class="form-control" type="hidden"/>
									<form:input path="channelOrderID"
												id="returnchannelOrderID" class="form-control" type="hidden"/>
									<label class="col-sm-4 control-label">Return Id</label>
									<div class="col-sm-8">
										<form:input path="orderReturnOrRTO.returnOrRTOId"
											placeholder="Return ID" class="form-control" />
									</div>
								</div>
							</div>
							<br> <br> <br>
							<div class="col-sm-12">
								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">Return Date</label>
									<div class="col-sm-8">
										<div class="input-group date">
											<form:input path=""
												type="date" id="datefield" onchange="mydate();"
												class="form-control" />
											<form:input path="orderReturnOrRTO.returnDate"
												id="datevalue" class="form-control" type="hidden"/>
										</div>
									</div>
								</div>
							</div>
							<br> <br> <br>
							<div class="col-sm-12">
								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">Return Reason</label>
									<div class="col-sm-8">
										<form:input path="orderReturnOrRTO.returnOrRTOreason"
											class="form-control" />
									</div>
								</div>
							</div>
							<br> <br> <br>
							<div class="col-sm-12">
								<div class="mar-btm-20-oh">
									<label class="col-sm-4 control-label">Return Quantity</label>
									<div class="col-sm-8">
										<form:input path="orderReturnOrRTO.returnorrtoQty"
											class="form-control" />
									</div>
								</div>
							</div>
							<br> <br>
							<div class="col-sm-12">
								<div class="form-group">
									<label class="col-sm-4 control-label">Return Type</label>
									<div class="col-sm-8">
										<form:select path="orderReturnOrRTO.type" class="form-control"
											id="category">
											<form:option value="" disabled="true" selected="true">Return Type</form:option>
											<form:option value="returnCharges">Return Charges</form:option>
											<form:option value="RTOCharges">RTO Charges</form:option>
											<form:option value="replacementCharges">Replacement
												Charges</form:option>
											<form:option value="partialDeliveryCharges">Partial
												Delivery Charges</form:option>
											<form:option value="cancellationCharges" id="">
												Cancellation Charges</form:option>
										</form:select>
									</div>
								</div>
							</div>
							<br> <br> <br> <br>
							<div class="col-sm-12">
								<div class="form-group">
									<label class="col-sm-4 control-label">Fault Type</label>
									<div class="col-sm-8">
										<form:select path="orderReturnOrRTO.returnCategory"
											class="form-control" onchange="show1()" id="category1">
											<form:option value="" disabled="true" selected="true">Fault Type</form:option>
											<form:option value="buyerReturn">Buyer Return</form:option>
											<form:option value="sellerFault">Seller Fault</form:option>
										</form:select>
									</div>
								</div>
							</div>
							<br> <br> <br>
							<div class="col-sm-12">
								<div class="form-group" id="showit" style="visibility: visible; display: none;">
									<label class="col-sm-4 control-label">Stages</label>
									<div class="col-sm-8">
										<form:select path="orderReturnOrRTO.cancelType"
											class="form-control" style="visibility: visible;" id="showit">
											<form:option value="beforeRTD">Before RTD</form:option>
											<form:option value="afterRTD">After RTD</form:option>
										</form:select>
									</div>

								</div>
							</div>
							<br> <br>
							<div class="col-lg-12">
								<div class="modal-footer" style="border: none;">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal">Close</button>
									<button type="submit" class="btn btn-primary">Save
										changes</button>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</div>
