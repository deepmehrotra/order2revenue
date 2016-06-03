
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="globalcsslinks.jsp"></jsp:include>

</head>
<body>
<div id="wrapper">
<jsp:include page="sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="globalheader.jsp"></jsp:include> 
      <div class="wrapper wrapper-content animated fadeInRight" id="centerpane">
         <!--  <div class="wrapper wrapper-content"> -->
       <div class="row">
                    <div class="col-lg-3">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <span class="label label-primary pull-right">Annual</span>
                                <h5>Net Profit</h5>
                            </div>
                            <div class="ibox-content">
                                <h1 class="no-margins">
                                <fmt:formatNumber type="number"  maxFractionDigits="2"  value="${dashboardValue.profitThisYear}" /> </h1>
                                <div class="stat-percent font-bold text-navy">
                                 <fmt:formatNumber type="number"  maxFractionDigits="2"  value="${dashboardValue.percentChangeInProfit}" />% <i class="fa fa-level-up"></i></div>
                                <small><%= new Date().getYear()+1900%></small>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <span class="label label-info pull-right">Annual</span>
                                <h5>Sale Quantity</h5>
                            </div>
                            <div class="ibox-content">
                                <h1 class="no-margins">${dashboardValue.saleQuantityThisYear}</h1>
                                <div class="stat-percent font-bold text-info">
                                 <fmt:formatNumber type="number"  maxFractionDigits="2"  value="${dashboardValue.percentChangeInSQ}" />% <i class="fa fa-level-up"></i></div>
                                <small>Till <fmt:formatDate value="<%= new Date()%>" pattern="MMM dd ,YYYY"/></small>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <span class="label label-warning pull-right">Current</span>
                                <h5>Customers</h5>
                            </div>
                            <div class="ibox-content">
                                <h1 class="no-margins">${dashboardValue.totalCustomers} </h1>
                                <div class="stat-percent font-bold text-warning"> </div>
                                <small>Till <fmt:formatDate value="<%= new Date()%>" pattern="MMM dd ,YYYY"/> </small>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <span class="label label-primary pull-right">Current</span>
                                <h5>Available Stock</h5>
                            </div>
                            <div class="ibox-content">
                                <h1 class="no-margins">${dashboardValue.totalStock}</h1>
                                <div class="stat-percent font-bold text-navy"></div>
                                <small>Till <fmt:formatDate value="<%= new Date()%>" pattern="MMM dd ,YYYY"/> </small>
                            </div>
                        </div>
                    </div>
                </div>
                 <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>Order V/s Payment</h5>
                                <div class="pull-right">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-xs btn-white active"  id="todayGraphButton">Today</button>
                                        <button type="button" class="btn btn-xs btn-white" id="monthlyGraphButton">Monthly</button>
                                        <button type="button" class="btn btn-xs btn-white" id="yearGraphButton">Annual</button>
                                    </div>
                                </div>
                            </div>
                            <div class="ibox-content">
                                <div class="row">
                                <div class="col-lg-9">
                                    <div class="flot-chart">
                                        <div class="flot-chart-content" id="flot-dashboard-chart"></div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <ul class="stat-list">
                                        <li>
                                            <h2 class="no-margins"  id="totalOrderCount">${dashboardValue.todaysOrderCount}</h2>
                                            <small>Total orders in period</small>
                                            <div class="stat-percent">48% <i class="fa fa-level-up text-navy"></i></div>
                                            <div class="progress progress-mini">
                                                <div style="width: 48%;" class="progress-bar"></div>
                                            </div>
                                        </li>
                                        <li>
                                            <h2 class="no-margins "  id="totalGrossProfit">
                                             <fmt:formatNumber type="number"  maxFractionDigits="2"  value="${dashboardValue.todaysGrossProfit}" /></h2>
                                            <small>Gross Profit</small>
                                            <div class="stat-percent">60% <i class="fa fa-level-down text-navy"></i></div>
                                            <div class="progress progress-mini">
                                                <div style="width: 60%;" class="progress-bar"></div>
                                            </div>
                                        </li>
                                        <li>
                                            <h2 class="no-margins "  id="totalPaymentCount">${dashboardValue.todaysPaymentCount}</h2>
                                            <small>Payment Count</small>
                                            <div class="stat-percent">22% <i class="fa fa-bolt text-navy"></i></div>
                                            <div class="progress progress-mini">
                                                <div style="width: 22%;" class="progress-bar"></div>
                                            </div>
                                        </li>
                                        </ul>
                                    </div>
                                </div>
                                </div>
                            </div>
                        </div>
                    </div>
        <div class="row">
                    <div class="col-lg-3">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>Upcoming Payments</h5>
                            </div>
                            <div class="ibox-content" style="height:195px;">
                             <div class="slim-scroll-bar">
                            <table class="table">
                            <thead>
                            <tr>
                               <th>#</th>
                                <th>Date</th>
                                <th>Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                              <c:if test="${!empty dashboardValue.totalUpcomingPayments}">
                             <c:forEach items="${dashboardValue.totalUpcomingPayments}" var="ucPayment"  varStatus="loop">
                            <tr>
                                <td>${loop.index+1}</td>
                                <td><fmt:formatDate value="${ucPayment.key}" pattern="MMM dd ,YYYY"/></td>
                                <td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ucPayment.value}" /></td>
                            </tr>
                            </c:forEach>
                            </c:if>
                        
                           </tbody>
                        </table>
                        </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>Outstanding Payments</h5>
                            </div>
                            <div class="ibox-content">
                             <div class="slim-scroll-bar">
                            <table class="table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Sales Channel</th>
                                <th>Amount</th>
                            </tr>
                            </thead>
                            <tbody>
                              <c:if test="${!empty dashboardValue.totalOutstandingPayments}">
                             <c:forEach items="${dashboardValue.totalOutstandingPayments}" var="osPayment"  varStatus="loop">
                            <tr>
                                <td>${loop.index+1}</td>
                                <td>${osPayment.key}</td>
                                <td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${osPayment.value}" /></td>
                            </tr>
                            </c:forEach>
                            </c:if>
                           </tbody>
                        </table>
                        </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>Top Selling SKU</h5>
                            </div>
                            <div class="ibox-content">
                             <div class="slim-scroll-bar">
                            <table class="table">
                            <thead>
                            <tr>
                            
                                <th>SKU</th>
                                <th>Qty</th>
                            </tr>
                            </thead>
                            <tbody>
                              <c:if test="${!empty dashboardValue.topSellingSKU}">
                             <c:forEach items="${dashboardValue.topSellingSKU}" var="topSKU"  varStatus="loop">
                            <tr>
                             
                                <td>${topSKU.key}</td>
                                <td>${topSKU.value}</td>
                            </tr>
                            </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                        </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>Top Selling Regions(6 months)</h5>
                            </div>
                            <div class="ibox-content">
                             <div class="slim-scroll-bar">
                               <table class="table">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>City</th>
                                <th>Sold Qty</th>
                            </tr>
                            </thead>
                            <tbody>
                             <c:if test="${!empty dashboardValue.topSellingRegion}">
                             <c:forEach items="${dashboardValue.topSellingRegion}" var="topSR"  varStatus="loop">
                            <tr>
                                <td>${loop.index+1}</td>
                                 <td>${topSR.key}</td>
                                <td>${topSR.value}</td>
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
                 <div class="row">
                    <div class="col-lg-4">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>Expenditure</h5>
                            </div>
                            <div class="ibox-content">
                                <div id="morris-donut-chart" ></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>Gross Profit Vs Expenses(Last 6 Month)</h5>
                            </div>
                            <div class="ibox-content">
                            <div>
                                <canvas id="lineChart" height="153"></canvas>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                <div class="col-lg-9">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>TAX Alert</h5>
                                </div>
                                <div class="ibox-content">
                                 <div class="slim-scroll-bar">
                                <table class="table">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Category</th>
                                    <th>Duration</th>
                                    <th>Amount</th>
                                     <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                   <c:if test="${!empty dashboardValue.taxAlerts}">
                             <c:forEach items="${dashboardValue.taxAlerts}" var="taxAlerts"  varStatus="loop">
                                    <tr>
                                        <td>${loop.index+1 }</td>
                                        <td><span class="pie">${taxAlerts.particular}</span></td>
                                        <td>${taxAlerts.taxortdsCycle}</td>
                                        <td class="text-navy"><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${taxAlerts.balanceRemaining}" />  </td>
                                         <td>${taxAlerts.status}</td>
                                    </tr>
                                   </c:forEach>
                                   </c:if>
                                    </tbody>
                            </table>
                            </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>TDS Alert</h5>
                                </div>
                                <div class="ibox-content"  style="height:195px;">
                                 <div class="slim-scroll-bar">
                                <table class="table">
                               <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Duration </th>
                                    <th>Amount</th>
                                    <th>Due Date</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody>
                              <c:if test="${!empty dashboardValue.tdsAlerts}">
                             <c:forEach items="${dashboardValue.tdsAlerts}" var="tdsAlerts"  varStatus="loop">
                                    <tr>
                                        <td>${loop.index+1 }</td>
                                        <td><span class="pie">${tdsAlerts.taxortdsCycle}</span></td>
                                        <td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${tdsAlerts.balanceRemaining}" /> </td>
                                        <td class="text-navy"> ${tdsAlerts.dateOfPayment} </td>
                                         <td>${tdsAlerts.status}</td>
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
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>Sale Vs Return Amount</h5>
                                </div>
                                <div class="ibox-content">
                                 <div>
                                    <canvas id="amountBarChart" height="140"></canvas>
                                </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>Sale Vs Return Quantity</h5>
                                </div>
                                <div class="ibox-content">
                                    <div>
                                    <canvas id="quantityBarChart" height="140"></canvas>
                                </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Notifications</h5>
                        </div>
                        <div class="ibox-content" style="height:465px;overflow-y:auto;">
                                <div class="feed-activity-list" >
                                    <div class="feed-element">
                                        <div>
                                            <small class="pull-right text-navy">1m ago</small>
                                            <strong>Monica Smith</strong>
                                            <div>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum</div>
                                            <small class="text-muted">Today 5:60 pm - 12.06.2014</small>
                                        </div>
                                    </div>
                                    <div class="feed-element">
                                        <div>
                                            <small class="pull-right">2m ago</small>
                                            <strong>Jogn Angel</strong>
                                            <div>There are many variations of passages of Lorem Ipsum available</div>
                                            <small class="text-muted">Today 2:23 pm - 11.06.2014</small>
                                        </div>
                                    </div>
                                    <div class="feed-element">
                                        <div>
                                            <small class="pull-right">5m ago</small>
                                            <strong>Jesica Ocean</strong>
                                            <div>Contrary to popular belief, Lorem Ipsum</div>
                                            <small class="text-muted">Today 1:00 pm - 08.06.2014</small>
                                        </div>
                                    </div>
                                    <div class="feed-element">
                                        <div>
                                            <small class="pull-right">5m ago</small>
                                            <strong>Monica Jackson</strong>
                                            <div>The generated Lorem Ipsum is therefore </div>
                                            <small class="text-muted">Yesterday 8:48 pm - 10.06.2014</small>
                                        </div>
                                    </div>
                                    <div class="feed-element">
                                        <div>
                                            <small class="pull-right">5m ago</small>
                                            <strong>Anna Legend</strong>
                                            <div>All the Lorem Ipsum generators on the Internet tend to repeat </div>
                                            <small class="text-muted">Yesterday 8:48 pm - 10.06.2014</small>
                                        </div>
                                    </div>
                                    <div class="feed-element">
                                        <div>
                                            <small class="pull-right">5m ago</small>
                                            <strong>Damian Nowak</strong>
                                            <div>The standard chunk of Lorem Ipsum used </div>
                                            <small class="text-muted">Yesterday 8:48 pm - 10.06.2014</small>
                                        </div>
                                    </div>
                                    <div class="feed-element">
                                        <div>
                                            <small class="pull-right">5m ago</small>
                                            <strong>Gary Smith</strong>
                                            <div>200 Latin words, combined with a handful</div>
                                            <small class="text-muted">Yesterday 8:48 pm - 10.06.2014</small>
                                        </div>
                                   </div>
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
<!-- Flot -->
<script src="/O2R/seller/js/plugins/flot/jquery.flot.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.spline.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.pie.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.symbol.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.time.js"></script>
<!-- Morris -->
<script src="/O2R/seller/js/plugins/morris/raphael-2.1.0.min.js"></script>
<script src="/O2R/seller/js/plugins/morris/morris.js"></script>
<!-- Morris demo data-->
<script src="/O2R/seller/js/demo/morris-o2r.js"></script>

<script>
 
 
 //plotting Sale amount and return amoutn barchart
 
  var saleAmount = [];
    var amountLables=[];
                        <c:if test="${!empty dashboardValue.saleAmount}">
                        <c:forEach items="${dashboardValue.saleAmount}" var="saleAmount" varStatus="loop">
                        saleAmount.push('${saleAmount.value}');
                        amountLables.push('${saleAmount.key}');
                         </c:forEach>
                         </c:if>
                         
    var returnAmount = [];
    var retamountLables=[];
                        <c:if test="${!empty dashboardValue.returnAmount}">
                        <c:forEach items="${dashboardValue.returnAmount}" var="returnAmount" varStatus="loop">
                        returnAmount.push('${returnAmount.value}');
                        retamountLables.push('${returnAmount.key}');
                         </c:forEach>
                         </c:if>
 
//plotting return  quantity  and sale quantity  barchart

var saleQuantity = [];
  var saleQuantityLables=[];
                      <c:if test="${!empty dashboardValue.saleQuantity}">
                      <c:forEach items="${dashboardValue.saleQuantity}" var="saleQuantity" varStatus="loop">
                      saleQuantity.push('${saleQuantity.value}');
                      saleQuantityLables.push('${saleQuantity.key}');
                       </c:forEach>
                       </c:if>
                       
  var returnQuantity = [];
  var returnQuantityLables=[];
                      <c:if test="${!empty dashboardValue.returnQuantity}">
                      <c:forEach items="${dashboardValue.returnQuantity}" var="returnQuantity" varStatus="loop">
                      returnQuantity.push('${returnQuantity.value}');
                      returnQuantityLables.push('${returnQuantity.key}');
                       </c:forEach>
                       </c:if>
                       
                    
 
                       //plotting expense category wise
var expensedata = [];
var expenseColor=[];
                var piecolor =['#87d6c6','#bababa','#79d2c0','#54cdb4','#1ab394'];
                        <c:if test="${!empty dashboardValue.expenditureThisMonth}">
                       <c:forEach items="${dashboardValue.expenditureThisMonth}" var="expenditureThisMonth" varStatus="loop">
            expensedata.push({label:'${expenditureThisMonth.key}',value:'${expenditureThisMonth.value}'});
            expenseColor.push(piecolor['${loop.index}']);
                        </c:forEach>
                        </c:if>
           
                        //PLotting Expense vs Gross Profit
    var grossProfit = [];
    var profitLables=[];
                        <c:if test="${!empty dashboardValue.grossProfitMonthly}">
                        <c:forEach items="${dashboardValue.grossProfitMonthly}" var="grossProfitMonthly" varStatus="loop">
                        grossProfit.push('${grossProfitMonthly.value}');
             profitLables.push('${grossProfitMonthly.key}');
                         </c:forEach>
                         </c:if>
                         
      var expenseMonthly = [];
        <c:if test="${!empty dashboardValue.expenditureMonthly}">
                          <c:forEach items="${dashboardValue.expenditureMonthly}" var="expenditureMonthly" varStatus="loop">
                          expenseMonthly.push('${expenditureMonthly.value}');
             			  </c:forEach>
                           </c:if>
                         
     
            
        //Plotting order vs payment for last 12 months
          function plotOrderPaymentMonthly()
        {
    	   var orderMonthlyCount=[];
           <c:if test="${!empty dashboardValue.last12MonthsOrderCount}">
           <c:forEach items="${dashboardValue.last12MonthsOrderCount}" var="last12MonthsOrderCount" varStatus="loop">
           var arr1 = ["${last12MonthsOrderCount.key.getTime()}","${last12MonthsOrderCount.value}"];
           orderMonthlyCount.push(arr1);
           </c:forEach>
           </c:if>
           var paymentMonthlycount=[];
           
           <c:choose>
                           <c:when test="${!empty dashboardValue.last12MonthsPaymentCount}">
          <c:forEach items="${dashboardValue.last12MonthsPaymentCount}" var="last12MonthsPaymentCount" varStatus="loop">
           var arr1 = ["${last12MonthsPaymentCount.key.getTime()}","${last12MonthsPaymentCount.value} "];
           paymentMonthlycount.push(arr1);
           </c:forEach>
           </c:when>
           <c:otherwise>
           <c:if test="${!empty dashboardValue.last12MonthsOrderCount}">
           <c:forEach items="${dashboardValue.last12MonthsOrderCount}" var="last12MonthsOrderCount" varStatus="loop">
           var arr1 = ["${last12MonthsOrderCount.key.getTime()}",0];
           paymentMonthlycount.push(arr1);
           </c:forEach>
           </c:if>
           </c:otherwise>
           </c:choose>
           
           plotOrderPayment(orderMonthlyCount,paymentMonthlycount, "month",24*60*60*1000);
           
        }
       
       function plotOrderPaymentToday()
       {
    	   var order30Count=[];
           <c:if test="${!empty dashboardValue.last30daysOrderCount}">
           <c:forEach items="${dashboardValue.last30daysOrderCount}" var="last30daysOrderCount" varStatus="loop">
           var arr1 = ["${last30daysOrderCount.key.getTime()}","${last30daysOrderCount.value}"];
           order30Count.push(arr1);
           </c:forEach>
           </c:if>
           var payment30count=[];
           
           <c:choose>
                           <c:when test="${!empty dashboardValue.last30DaysPaymentCount}">
          <c:forEach items="${dashboardValue.last30DaysPaymentCount}" var="last30DaysPaymentCount" varStatus="loop">
           var arr1 = ["${last30DaysPaymentCount.key.getTime()}","${last30DaysPaymentCount.value} "];
           payment30count.push(arr1);
           </c:forEach>
           </c:when>
           <c:otherwise>
           <c:if test="${!empty dashboardValue.last30daysOrderCount}">
           <c:forEach items="${dashboardValue.last30daysOrderCount}" var="last30daysOrderCount" varStatus="loop">
           var arr1 = ["${last30daysOrderCount.key.getTime()}",0];
           payment30count.push(arr1);
           </c:forEach>
           </c:if>
           </c:otherwise>
           </c:choose>
          
           plotOrderPayment(order30Count,payment30count, "day",24*60*60*1000);
          
       }
            
    $(document).ready(function(){
       $('#paymentField').change(function () {
            $('.TopSearch-box').hide();
            $('#'+$(this).val()).fadeIn();
        });
       $('.slim-scroll-bar').slimScroll({
           height: '159px',
           railOpacity: 0.4
       });
        $('.input-group.date').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
            });
        $('#monthlyGraphButton').click(function () {
        	plotOrderPaymentMonthly();
        	 $('#totalOrderCount').text('${dashboardValue.thisMonthOrderCount}');
        	 $('#totalPaymentCount').text('${dashboardValue.thisMonthPaymentCount}');
        	 $('#totalGrossProfit').text('${dashboardValue.thisMonthGrossProfit}');
        	
        });
        $('#todayGraphButton').click(function () {
        	plotOrderPaymentToday();
        	 $('#totalOrderCount').text('${dashboardValue.todaysOrderCount}');
        	 $('#totalPaymentCount').text('${dashboardValue.todaysPaymentCount}');
        	 $('#totalGrossProfit').text('${dashboardValue.todaysGrossProfit}');
        	
        });
        $('#yearGraphButton').click(function () {
        	plotOrderPaymentMonthly();
        	 $('#totalOrderCount').text('${dashboardValue.thisYearOrderCount}');
        	 $('#totalPaymentCount').text('${dashboardValue.thisYearPaymentCount}');
        	 $('#totalGrossProfit').text('<fmt:formatNumber type="number"  maxFractionDigits="2"  value="${dashboardValue.thisYearGrossProfit}"/>');
        	
        });
         
          
         //   $.plot($("#flot-dashboard-chart"), dataset, options);
      //   plotOrderPayment(order30Count,payment30count, "day",24*60*60*1000);
      plotOrderPaymentToday();
            plotdonut(expensedata,expenseColor);
            plotChartsLine(profitLables,grossProfit,expenseMonthly);
            plotBarGraph(saleQuantityLables,"Sale Quantity","Return Quantity",saleQuantity,returnQuantity,"quantityBarChart");
            plotBarGraph(amountLables,"Sale Amount","Return Amount",saleAmount,returnAmount,"amountBarChart");
            

        });
    </script>
<!-- ChartJS-->
<script src="/O2R/seller/js/plugins/chartJs/Chart.min.js"></script>
<script src="/O2R/seller/js/demo/chartjs-o2r.js"></script>
</body>
</html>