<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <div class="row border-bottom">
            <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-new" method="post" action="findGlobalOrders.html">
                        <div class="form-group  top-search-100">
                        <div class="top-search-30 f-left">
                            <select class="form-control" name="searchCriteria"  id="searchCriteria">
                             <option value="">Select Criteria</option>
                                <option id="" value="channelOrderID">Channel Order Id</option>
                                <option id="" value="awbNum">AWB</option>
							    <option id="" value="invoiceID">Invoice ID</option>
							    <option id="" value="subOrderID">Sub Order ID</option>
							    <option id="" value="PIreferenceNo">PI Reference No</option>
							    <option id="" value="uniqueItemId">Unique Item ID</option>
							    <option id="" value="returnOrRTOId">Sale Return ID/ Debit Note No</option>
							    <option id="" value="pcName">Partner</option> 
							    <option id="" value="productSkuCode">Product SKU</option>
							    <option id="" value="customerName">Customer Name</option> 
							    <option id="" value="customerCity">Customer City</option>
							    <option id="" value="customerEmail">Customer mail</option>
							    <option id="" value="customerPhnNo">Customer Phone No</option>    	
							    <option id="" value="status"> Order Status</option>
							    <option id="" value="sellerNote">Seller Notes</option>
                                <option id="" value="orderDate">Order Date</option>
                                <option id="" value="orderDate">Order Received Date</option>
								<option id="" value="shippedDate">Order Shipped Date</option>
								<option id="" value="deliveryDate">Order Delivery Expected Date</option> 
								<option id="" value="paymentDueDate">Payment Due Date</option>
								<option id="" value="dateofPayment">Actual Date of Payment</option>
                            </select>
                            </div>
                          <div class="top-search-60 f-left TopSearch-box" id="channelOrderID">
                            <input type="text" placeholder="Enter Search String" class="form-control"  id="searchstring" name="searchstring">
                        </div>
                        <div class="top-search-60 f-left TopSearch-box" id="orderDate" style="display:none">
                            <div class="input-group date f-left top-search-50">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text"  name="startDate"  id="startDate"   class="form-control" >
                            </div>
                            <div class="input-group date f-left top-search-50">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text"   name="endDate"  id="endDate"    class="form-control" >
                            </div>
                        </div>
                            <button class="btn btn-white" type="submit"><i class="fa fa-search"></i></button>
                        </div>
                    </form>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                    <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                        <i class="fa fa-exchange"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-messages animated fadeInRight">
                        <li>
                            <div class="dropdown-messages-box">
                                <a href="profile.html" class="pull-left">
                                    <i class="fa fa-level-up"></i>
                                </a>
                                <div class="media-body">
                                    <small class="pull-right">46h ago</small>
                                    <strong>Exported</strong> by John Deo.<br>
                                    <small class="text-muted">3 days ago at 7:58 pm - 10.06.2014</small>
                                </div>
                            </div>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <div class="dropdown-messages-box">
                                <a href="profile.html" class="pull-left">
                                    <i class="fa fa-level-down"></i>
                                </a>
                                <div class="media-body ">
                                    <small class="pull-right">23h ago</small>
                                    <strong>Imported</strong> by John Deo. <br>
                                    <small class="text-muted">2 days ago at 2:30 am - 11.06.2014</small>
                                </div>
                            </div>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <div class="text-center link-block">
                                <a href="mailbox.html">
                                    <i class="fa fa-envelope"></i> <strong>Read All Updates</strong>
                                </a>
                            </div>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle count-info" data-toggle="dropdown">
                        <i class="fa fa-info"></i>
                    </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="#">Lorem Ipsum</a></li>
                            <li><a href="#">Lorem Ipsum</a></li>
                        </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle count-info" data-toggle="dropdown">
                        <i class="fa fa-gear"></i>
                    </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="addSeller.html">Seller Info</a></li>
                            <li><a href="#">Goeasy Account</a></li>
                            <li><a href="upgrade.html">Plan Upgrade</a></li>
                            <li><a href="#">Summary</a></li>
                        </ul>
                </li>
                <li>
                    <a href="/j_spring_security_logout">
                        <i class="fa fa-sign-out"></i> Log out
                    </a>
                </li>
                </ul>

            </nav>
        </div>

</body>
</html>