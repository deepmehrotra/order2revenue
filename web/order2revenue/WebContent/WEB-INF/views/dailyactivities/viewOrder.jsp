<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Order : ${order.channelOrderID}</h5>
				</div>
				<div class="ibox-content view-order">
					<div class="time-line-wrp">
						<ul>
							<c:if test="${!empty order.orderTimeline}">
								<c:forEach items="${order.orderTimeline}" var="timeline"
									varStatus="loop">
									<li class="active"><i class="fa fa-check"></i> <span><fmt:formatDate
												value="${timeline.eventDate}" pattern="MMM dd,YY" /></span>
										<p>${timeline.event}</p></li>

								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
			<div class="ibox float-e-margins">
				<div class="ibox-title ">
					<h5>Order Info</h5>
				</div>

				<div class="ibox-content add-company view-order">
					<table class="table table-bordered custom-table">
						<thead>
							<tr>
								<th>Partner</th>
								<th>Invoice Id</th>
								<th>PI Reference No</th>
								<th>Sub Order Id</th>
								<th>Order Date</th>
								<th>Status</th>
								<th>Final Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${order.pcName}</td>
								<td>${order.invoiceID}</td>
								<td>${order.PIreferenceNo}</td>
								<td>${order.subOrderID}</td>
								<td><fmt:formatDate value="${order.orderDate}"
										pattern="MMM-dd-YYYY" /></td>
								<td>${order.status}</td>
								<td>${order.finalStatus}</td>
								<td>
									<form  class="form-inline" action="markOrderStatus.html" method="get">
										<input type="hidden" name="orderId" value="${order.orderId}">
										<select class="btn btn-primary btn-xs" id="" name="selectStatus" required="required" style="width: 75%;color: #676a6c !important;">
											<option value="">Select To Mark</option>										
											<option value="Settled" id="Settled">Settled</option>
											<option value="" disabled>Payment Recieved</option>
											<option value="" disabled>Return Recieved</option>											
										</select>
										<button class="btn btn-primary btn-xs" type="submit">Mark</button>
									</form>					
								</td>
							</tr>
						</tbody>
					</table>

				</div>


			</div>
			<div class="col-lg-12 m-b-sm">
				<a href="#" class="btn btn-default openall">Expand All</a> <a href="#" class="btn btn-default closeall">Collapse All</a>			
			</div>
			<div class="col-lg-12 order-info-block">
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsecust">Sale Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapsecust" class="panel-collapse collapse in">
                            <div class="panel-body" style="overflow-x: hidden;overflow-y: scroll;height: 436px;">
                              <div class="ibox-content add-company view-order">                                
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Gross Sale Quantity</td>
                                        <td>${order.quantity}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>Order MRP</td>
                                        <td>${order.orderMRP}</td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Customer Discount</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.discount}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Order SP</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderSP}" /></td>  
                                    </tr>                                    
                                    <tr>
                                        <td>TDS</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderTax.tdsToDeduct}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Gross N/R</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.grossNetRate * order.quantity}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Tax</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderTax.tax}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Product Cost</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.quantity * productCost}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>P/R</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.pr}" /></td> 
                                    </tr>
                                    <tr>
                                        <td>Gross Profit</td>
                                        <td>
                                        	<fmt:formatNumber type="number" maxFractionDigits="2" value="${order.pr - (productCost * order.quantity)}" />                                       
                                        </td>  
                                    </tr>
                                    </tbody>
                                </table>
                              </div>
                            </div>
                        </div>
                    </div>
                    </div>
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsevents">Return Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapsevents" class="panel-collapse collapse in">
                            <div class="panel-body" style="overflow-x: hidden;overflow-y: scroll;height: 436px;">
                              <div class="ibox-content add-company view-order">        
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                    	<td>Return Id</td>
                                        <td>${order.orderReturnOrRTO.returnOrRTOId}</td>                                      
                                    </tr>
                                    <tr>
                                        <td>Return Quantity</td>
                                        <td>${order.orderReturnOrRTO.returnorrtoQty}</td>                                       
                                    </tr>
                                    <tr>
                                        <td>Return Date</td>
                                        <td><fmt:formatDate	value="${order.orderReturnOrRTO.returnDate}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Return Type</td>
                                        <c:choose>
                                        	<c:when test="${order.orderReturnOrRTO.type == 'returnCharges'}">
                                        		<td>Return Charges</td>
                                        	</c:when>
                                        	<c:when test="${order.orderReturnOrRTO.type == 'RTOCharges'}">
                                        		<td>RTO Charges</td>
                                        	</c:when>
                                        	<c:when test="${order.orderReturnOrRTO.type == 'replacementCharges'}">
                                        		<td>Replacement Charges</td>
                                        	</c:when>
                                        	<c:when test="${order.orderReturnOrRTO.type == 'partialDeliveryCharges'}">
                                        		<td>Partial Delivery Charges</td>
                                        	</c:when>
                                        	<c:when test="${order.orderReturnOrRTO.type == 'cancellationCharges'}">
                                        		<td>Cancellation Charges</td>
                                        	</c:when>                                    
                                        </c:choose>                                      
                                    </tr>
                                    <tr>
                                        <td>Fault Type</td>
                                        <c:choose>
                                        	<c:when test="${order.orderReturnOrRTO.returnCategory == 'buyerReturn'}">
                                        		<td>Buyer Return</td>
                                        	</c:when>
                                        	<c:when test="${order.orderReturnOrRTO.returnCategory == 'sellerFault'}">
                                        		<c:choose>
	                                        		<c:when test="${order.orderReturnOrRTO.cancelType == 'beforeRTD'}">
	                                        			<td>Seller Fault : Before RTD</td>
	                                        		</c:when>
	                                        		<c:when test="${order.orderReturnOrRTO.cancelType == 'afterRTD'}">
	                                        			<td>Seller Fault : After RTD</td>
	                                        		</c:when>
	                                        		<c:otherwise>
	                                        			<td>Seller Fault</td>
	                                        		</c:otherwise>
                                        		</c:choose>    
                                        	</c:when>                                        	                                       	                                
                                        </c:choose>                                          
                                    </tr>
                                    <tr>
                                        <td>Return Charges</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderReturnOrRTO.returnOrRTOChargestoBeDeducted}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Return Product Cost</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderReturnOrRTO.returnorrtoQty * productCost}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Return Date Limit</td>
                                        <td><fmt:formatDate	value="${order.returnLimitCrossed}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Return Reason</td>
                                        <td>${order.orderReturnOrRTO.returnOrRTOreason}</td>  
                                    </tr>
                                    <tr>
                                        <td>Return Limit Status</td>
                                        <c:if test="${order.orderReturnOrRTO.returnDate > order.returnLimitCrossed}">
                                        	<td>Limit Crossed</td>
                                        </c:if>  
                                    </tr>                                    
                                    </tbody>
                                </table>
                              </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="float-e-margins col-lg-4">
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsenotes">Net Transaction Info<i class="glyphicon glyphicon-minus" style="float: right;"></i> </a>
                          </h4>
                        </div>
                        <div id="collapsenotes" class="panel-collapse collapse in">
                          <div class="panel-body" style="overflow-x: hidden;overflow-y: scroll;height: 436px;" >
                            <div class="ibox-content add-company view-order">      
                              <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Net Sale Qty</td>
                                        <td>${order.quantity - order.orderReturnOrRTO.returnorrtoQty}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>Net Order MRP</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.orderMRP / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Net Customer Discount</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.discount / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Order SP</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.orderSP / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>                                                                      
                                    <tr>
                                        <td>Net TDS</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.orderTax.tdsToDeduct / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net N/R</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.grossNetRate * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Tax</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.orderTax.tax / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net P/R</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.pr / order.quantity) * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Product Cost</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${productCost * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Gross Profit</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.grossProfit}" /></td>  
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-lg-12 order-info-block">
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseSaleCom">Sale Commission <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapseSaleCom" class="panel-collapse collapse in">
                            <div class="panel-body">
                              <div class="ibox-content add-company view-order">                                
                                <table class="table table table-striped">
                                    <tbody>                                    
                                    <tr>
                                        <td>Gross Commission</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.partnerCommission * order.quantity}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Gross PCC</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.pccAmount * order.quantity}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Gross Shipping Charges</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.shippingCharges * order.quantity}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Gross Fixed Fee</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${(order.fixedfee) * (order.quantity)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Service Tax</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${serviceTax * order.quantity}" /></td>  
                                    </tr>                                    
                                    </tbody>
                                </table>
                              </div>
                            </div>
                        </div>
                    </div>
                    </div>
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseReturnCom">Return Commission<i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapseReturnCom" class="panel-collapse collapse in">
                            <div class="panel-body">
                              <div class="ibox-content add-company view-order">        
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Return Commission</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.partnerCommission * order.orderReturnOrRTO.returnorrtoQty}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Return PCC</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.pccAmount * order.orderReturnOrRTO.returnorrtoQty}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Return Shipping Charges</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.shippingCharges * order.orderReturnOrRTO.returnorrtoQty}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Return Fixed Fee</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.fixedfee * order.orderReturnOrRTO.returnorrtoQty}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Return Service Tax</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${serviceTax * order.orderReturnOrRTO.returnorrtoQty}" /></td>  
                                    </tr>
                                    </tbody>
                                </table>
                              </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="float-e-margins col-lg-4">
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseNetCom">Net Commission<i class="glyphicon glyphicon-minus" style="float: right;"></i> </a>
                          </h4>
                        </div>
                        <div id="collapseNetCom" class="panel-collapse collapse in">
                          <div class="panel-body">
                            <div class="ibox-content add-company view-order">      
                              <table class="table table table-striped">
                                    <tbody>                                    
                                    <tr>
                                        <td>Net Commission</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.partnerCommission * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net PCC</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.pccAmount * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Shipping Charges</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.shippingCharges * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Fixed Fee</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.fixedfee * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Service Tax</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${serviceTax * (order.quantity - order.orderReturnOrRTO.returnorrtoQty)}" /></td>  
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-lg-12 order-info-block">
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsecustomer">Event Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                          </div>
                          <div id="collapsecustomer" class="panel-collapse collapse in">
                            <div class="panel-body" style="overflow-x: hidden;overflow-y: scroll;height: 345px;">
                              <div class="ibox-content add-company view-order">                                
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Event Name</td>
                                        <td>${event.eventName}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>Event Start Date</td>
                                        <td><fmt:formatDate	value="${event.startDate}" pattern="MMM dd,YY" /></td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Event End Date</td>
                                        <td><fmt:formatDate	value="${event.endDate}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    </tbody>
                                </table>

                                </div>
                              </div>
                            </div>
                        </div>
                    
                    </div>
                    <div class="float-e-margins col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsepayment">Payment Info  
                             <i class="glyphicon glyphicon-minus" style="float: right;"></i> 
                             <a class="btn btn-small btn-info pull-right" href="#" data-toggle="modal"data-target="#modaltable" style="margin:-2px 10px 0px 0px;padding: 0px 6px 0px 6px;color: #000;background-color:#dccdb8;" title="Channel Properties">
                                <i class="fa fa-eye" aria-hidden="true"></i>
                            </a></h4>

                          </div>
			<div class="modal inmodal fade" id="modaltable" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight" style="left: 22%;width:50%;">
                              <div class="modal-header" style="padding: 5px 6px 9px 14px;background: #c1c1c1;color: #fff;font-weight: bold;letter-spacing: 1px;">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h1>Channel Properties</h1>
                               </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12" style="overflow-x: scroll;overflow-y: scroll;height: 228px;">
                                    <table class="table table-bordered" >
                                        <thead>
                                            <tr>
                                                <th>S.NO</th>
                                                <th>Property</th>
                                                <th>Value</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${!empty order.orderPayment.paymentVar}">
											<c:forEach items="${order.orderPayment.paymentVar}" var="properties"
													varStatus="loop">
                                            <tr>
                                                <td>
                                                   ${loop.index+1}
                                                </td>
                                                <td>
                                                   ${properties.payKey}
                                                <td>
                                                   ${properties.payValue}
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
                          </div>
                        </div>
                          <div id="collapsepayment" class="panel-collapse collapse in">
                            <div class="panel-body" style="overflow-x: hidden;overflow-y: scroll;height: 345px;">
                              <div class="ibox-content add-company view-order">        
                                <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>Payment Type</td>
                                        <td>${order.paymentType}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>Payment Due Date</td>
                                        <td><fmt:formatDate	value="${order.paymentDueDate}" pattern="MMM dd,YY" /></td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Last Payment Date</td>
                                        <td><fmt:formatDate	value="${order.orderPayment.dateofPayment}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Total Negative Amount</td>
                                        <td>
                                        	<c:if test="${order.orderPayment.negativeAmount gt 0}">
												-${order.orderPayment.negativeAmount}
											</c:if>
										</td>  
                                    </tr>
                                    <tr>
                                        <td>Total Positive Amount</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderPayment.positiveAmount}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Net Payment Result</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderPayment.netPaymentResult}" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Payment Difference</td>
                                        <td><fmt:formatNumber
												type="number" maxFractionDigits="2"
												value="${order.orderPayment.paymentDifference}" /></td>  
                                    </tr>

                                    </tbody>
                                </table>
                              </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="float-e-margins col-lg-4">
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseshipping">Shipping Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></span> </h4>
                        </div>
                        <div id="collapseshipping" class="panel-collapse collapse in">
                          <div class="panel-body" style="overflow-x: hidden;overflow-y: scroll;height: 345px;">
                            <div class="ibox-content add-company view-order">      
                              <table class="table table table-striped">
                                    <tbody>
                                    <tr>
                                        <td>AWB Number</td>
                                        <td>${order.awbNum}</td>
                                        
                                    </tr>
                                    <tr>
                                        <td>SKU CODE</td>
                                        <td>${order.productSkuCode}</td>
                                       
                                    </tr>
                                    <tr>
                                        <td>Logistic Partner</td>
                                        <td>${order.logisticPartner}</td>  
                                    </tr>
                                    <tr>
                                        <td>Shipped Date</td>
                                        <td><fmt:formatDate	value="${order.shippedDate}" pattern="MMM dd,YY" /></td>  
                                    </tr>
                                    <tr>
                                        <td>Delivery Date</td>
                                        <td><fmt:formatDate	value="${order.deliveryDate}" pattern="MMM dd,YY" /></td>  
                                    </tr> 
                                    <tr>
                                        <td>Shipping Charges</td>
                                        <td>${order.shippingCharges}</td>  
                                    </tr> 
                                    <tr>
                                        <td>Shipping Zone</td>
                                        <td>${order.volShippingString}</td>  
                                    </tr>
                                    <tr>
                                        <td>Weight Calculation</td>
                                        <td>${order.dwShippingString}</td>  
                                    </tr> 
                                   </tbody>
                                </table>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-lg-12 order-info-block">
                        <div class="float-e-margins col-lg-6">
                            <div class="panel panel-default">
                              <div class="panel-heading">
                                <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseeventsinfo">Customer Info <i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></span></h4>
                              </div>
                              <div id="collapseeventsinfo" class="panel-collapse collapse in">
                                <div class="panel-body" style="height: 331px;">
                                  <div class="ibox-content add-company view-order">                                
                                    <table class="table table table-striped">
                                        <tbody>
                                        <tr>
                                            <td>Name</td>
                                            <td>${order.customer.customerName}</td>        
                                        </tr>
                                        <tr>
                                            <td>Contact</td>
                                            <td>${order.customer.customerPhnNo}</td>
                                           
                                        </tr>
                                        <tr>
                                            <td>Email</td>
                                            <td>${order.customer.customerEmail}</td>  
                                        </tr>
                                        <tr>
                                            <td>City</td>
                                            <td>${order.customer.customerCity}</td>  
                                        </tr>
                                        <tr>
                                            <td>Address</td>
                                            <td>${order.customer.customerAddress}</td>  
                                        </tr>
                                        <tr>
                                            <td>Zipcode</td>
                                            <td>${order.customer.zipcode}</td>  
                                        </tr>
                                        </tbody>
                                    </table>
                                    </div>
                                  </div>
                                </div>
                            </div>
                        </div>
                        <div class="float-e-margins col-lg-6">
                            <div class="panel panel-default">
                              <div class="panel-heading">
                                <h4 class="panel-title ibox-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsesellernotes">Other Info<i class="glyphicon glyphicon-minus" style="float: right;"></i> </a></h4>
                              </div>
                              <div id="collapsesellernotes" class="panel-collapse collapse in">
                                <div class="panel-body">
                                  <div class="ibox-content add-company view-order">        
                                    <table class="table table table-striped">
                                        <tbody>
                                        	<tr>
	                                            <td>Seller Note : </td>
	                                            <td>${order.sellerNote}</td>
	                                        </tr>
	                                        <tr>	                                        
	                                            <td>Order File : </td>
	                                            <td><a href="getUploadReportList.html">${order.orderFileName}</a></td>
	                                        </tr>
	                                        <tr>
	                                            <td>Payment File : </td>
	                                            <td><a href="getUploadReportList.html">${order.orderPayment.paymentFileName}</a></td>
	                                        </tr>
	                                        <tr>
	                                            <td>Return File : </td>
	                                            <td><a href="getUploadReportList.html">${order.orderReturnOrRTO.returnFileName}</a></td>        
                                        	</tr>
                                        	<tr>
	                                            <td>Order Type : </td>
	                                            <c:choose>
	                                            	<c:when test="${order.typeIdentifier != null}">
	                                            		<td>${order.typeIdentifier}</td>
	                                            	</c:when>
	                                            	<c:otherwise>
	                                            		<td>Normal</td>
	                                            	</c:otherwise>
	                                            </c:choose>	                                                    
                                        	</tr>
                                        </tbody>
                                    </table>
                                  </div>
                              </div>
                            </div>
                          </div>
                        </div>
                    </div>

			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Comments</h5>
				</div>
				<div class="ibox-content add-company view-order">

					<blockquote>
						<p>${order.sellerNote}</p>
						<%--  <small><strong>Author name</strong> in <cite title="" data-original-title="">June 26, 2015</cite></small> --%>
					</blockquote>
					<!--  <textarea class="form-control" placeholder="Type your comments here..."></textarea> -->

				</div>
			</div>
		
		</div>
		<jsp:include page="../globalfooter.jsp"></jsp:include>
	</div>
	
	<script>
		$(document).ready(function() {			
			$("#form").validate({
				rules : {
					number : {
						required : true,
						number : true
					}
				}
			});
		});	
		
	</script>
	<script type="text/javascript">


var selectIds = $('#collapsecust');
$(function ($) {
    selectIds.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});

var selectIds1 = $('#collapsevents');
$(function ($) {
    selectIds1.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds2 = $('#collapsenotes');
$(function ($) {
    selectIds2.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds3 = $('#collapsecustomer');
$(function ($) {
    selectIds3.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds4 = $('#collapsepayment');
$(function ($) {
    selectIds4.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds5 = $('#collapseshipping');
$(function ($) {
    selectIds5.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds6 = $('#collapseeventsinfo');
$(function ($) {
    selectIds6.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds7 = $('#collapsesellernotes');
$(function ($) {
    selectIds7.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds8 = $('#collapseSaleCom');
$(function ($) {
    selectIds8.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds9 = $('#collapseReturnCom');
$(function ($) {
    selectIds9.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});
var selectIds10 = $('#collapseNetCom');
$(function ($) {
    selectIds10.on('show.bs.collapse hidden.bs.collapse', function () {
        $(this).prev().find('.glyphicon').toggleClass('glyphicon-plus glyphicon-minus');
    })
});


	$('.closeall').click(function(){
	  $('.panel-collapse.in')
	    .collapse('hide');
	});
	$('.openall').click(function(){
	  $('.panel-collapse:not(".in")')
	    .collapse('show');
	});


	
</script>
</body>
</html>