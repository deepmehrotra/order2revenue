<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="row">
	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title" style='min-height: 90px;'>
				<div>
					<h5>PO Orders</h5>
					<div class="table-menu-links">
						<a href="orderList.html" id="Orders">MP Orders</a> <a href="#"
							id="returnOrders">Return</a> <a href="gatepasslist.html"
							id="gatepass">GatePass</a> <a href="#" id="paymentOrders">Payment</a>
						<a href="#" id="actionableOrders">Actionable</a>
					</div>
				</div>
				<div class="col-sm-12">
					<hr style='margin-top: 0.4em; margin-bottom: 0.7em;' />
				</div>
				<div class="col-sm-12">
					<div class="col-sm-5">
						<label>Period:</label> &nbsp;&nbsp;&nbsp;<label> ${period}
						</label>
						<button class="btn btn-xs btn-grey"
							onclick="location.href = 'poOrderList.html?value=All';">View
							All</button>
						<!-- <input name="startDate" id="monthYearPicker" /> -->
						<%-- <input value="${period}" id="ChromeMonthPicker" type="month" 
											class="form-control input-sm" style="width:70%;"/>  --%>
					</div>
					<div class="col-sm-3">
						<label> Change Period: </label>
						<button class="btn btn-xs btn-darkgrey"
							onclick="location.href = 'poOrderList.html?value=';">Monthly</button>
						<button class="btn btn-xs btn-grey active"
							onclick="onclickNavigateOrder('viewPOOrderDetails','0')">Annually</button>
					</div>
					<div class="ibox-tools">
						<button class="btn btn-white table-menu-search search-dd">
							<i class="fa fa-search"></i>
						</button>
						<div class="search-more-wrp">
							<form role="search" class="form-inline" method="post"
								action="searchOrder.html">
								<div class="form-group">
									<select class="form-control" name="searchOrder"
										id="searchOrders">
										<option id="1" value="channelOrderID">Channel
											OrderId/PO ID</option>
										<option id="2" value="invoiceID">Invoice ID</option>
										<option id="3" value="subOrderID">Sub Order ID</option>
										<option id="4" value="pcName">Partner</option>
										<option id="5" value="customerName">Customer Name</option>
										<option id="6" value="status">Order Status</option>
										<option id="7" value="orderDate">Order Date</option>
										<option id="8" value="shippedDate">Order Shipped Date</option>
									</select>
								</div>
								<div class="form-group TopSearch-box001 OrderSearch-box "
									id="searchchannelOrderID">
									<input type="text" placeholder="Enter Channel OrderID"
										class="form-control" name="channelOrderID">
								</div>
								<div class="form-group TopSearch-box002 OrderSearch-box"
									id="SearchorderDate" style="display: none">
									<div class="input-group date">
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span><input type="text"
											class="form-control" placeholder="" name="startDate">
									</div>
									<div class="input-group date">
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span><input type="text"
											class="form-control" placeholder="" name="endDate">
									</div>
								</div>
								<div class="form-group">
									<button class="btn btn-primary btn-block" type="submit">Search</button>
								</div>
							</form>
						</div>
						<span>Last</span>
						<button type="button" id="LoadFirst500"
							class="btn btn-xs btn-white active">500</button>
						<button type="button" class="btn btn-xs btn-white">1000</button>
						<button type="button" id="LoadMoreOrder"
							class="btn btn-xs btn-white">More</button>
					</div>
				</div>
			</div>
			<div class="bs-example">

				<div class="ibox-content overflow-h cus-table-filters">
					<div class="scroll-y">
						<table
							class="table table-striped table-bordered table-hover dataTables-example">
							<thead>
								<tr>
									<th rowspan="2">PERIOD</th>
									<th rowspan="2">DEBITS</th>
									<th colspan="4" style="text-align: center;">CREDITS</th>
									<th rowspan="2">CLOSING BALANCE</th>
								</tr>
								<tr>
									<th>GATEPASS</th>
									<th>PAYMENTS</th>
									<th>MANUAL CHARGES</th>
									<th>EOSS</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${!empty poPaymentListMonthly}">
									<c:forEach items="${poPaymentListMonthly}" var="poPayment"
										varStatus="loop">
										<tr>
											<td><a href="#"
												onclick="onclickNavigateOrder('poOrderList','${poPayment.paymentDetail}')">${poPayment.paymentDetail}</a></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.debits}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.gatepass}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.payments}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.manualCharges}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.eoss}" /></td>
											<td><fmt:formatNumber type="number"
													maxFractionDigits="2" value="${poPayment.closingBal}" /></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
					<div class="col-sm-12">
						<div class="hr-line-dashed"></div>
						<a href="#" onclick="onclickNavigateOrder('uploadPO',0)"
							class="btn btn-success btn-xs">Bulk Upload PO</a>&nbsp;&nbsp; <a
							href="#" onclick="onclickNavigateOrder('downloadPO',0)"
							class="btn btn-success btn-xs">Download PO Format</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>