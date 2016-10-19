
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.Date"%>
<%@ page language="java" import="java.util.*" %> 
<%@ page import = "org.springframework.core.io.*" %>
<%@ page import = "org.springframework.core.io.support.PropertiesLoaderUtils" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="globalcsslinks.jsp"></jsp:include>
<style type="text/css">
.widget {
	margin-bottom: 20px;
	margin-top: 20px;
}

.btn-white {
	font-size: 10px;
	padding: 5px;
}

.widget.style1 h2 {
	font-size: 18px;
}
</style>
</head>
<body>
<!-- Time Taken: ${timeTaken} ms %> ... -->
<div id="wrapper">
<jsp:include page="sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="globalheader.jsp"></jsp:include> 
      <div class="wrapper wrapper-content animated fadeInRight white-bg">
      		<div class="col-sm-12"><a href="orderindex.html" 
      			class="btn-primary btn-xs" style="margin: 0px 0px 0px -15px;position: relative;top: -6px;background-color: #293846;">NORMAL VIEW</a></div>
            <div class="col-lg-12 m-b" style="background: darkgray;padding: 16px;">
                <div class="col-lg-6">
                    <div data-toggle="buttons" class="btn-group">
                        <label class="btn btn-sm btn-white"> <input type="radio" id="option1" name="options" onclick="myFunction();"> Today </label>
                        <label class="btn btn-sm btn-white active"> <input type="radio" id="option2" name="options"> Monthly </label>
                        <label class="btn btn-sm btn-white"> <input type="radio" id="option3" name="options"> Quaterly </label>
                        <label class="btn btn-sm btn-white"> <input type="radio" id="option4" name="options"> Annually </label>
                    </div>
                </div>
                <div class="col-lg-6">
                </div>
                <div class="col-lg-12">
		            <div class="col-lg-3">
		                <div class="widget style1">
		                    <div class="col-xs-4 text-center">
		                       <!--  <i class="fa fa-shopping-cart" aria-hidden="false"></i> -->
		                    </div>
		                    <div class="col-xs-8 text-right" style="color: #fff;">
		                        <span> Sale Quantity</span>
		                        <h2 class="font-bold">4,232</h2>
		                    </div>    
		                </div>
		            </div>
		            <div class="col-lg-3">
		                <div class="widget style1 navy-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/gross.png" alt="gross">
		                        </div>
		                        <div class="col-xs-8 text-right">
		                            <span> Gross Value</span>
		                            <h2 class="font-bold">2600</h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-3">
		                <div class="widget style1 lazur-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/return.png" alt="return">
		                        </div>
		                        <div class="col-xs-8 text-right">
		                            <span> Return Value</span>
		                            <h2 class="font-bold">260</h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-3">
		                <div class="widget style1 yellow-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/net.png" alt="net">
		                        </div>
		                        <div class="col-xs-8 text-right">
		                            <span> Net Value </span>
		                            <h2 class="font-bold">120909090989</h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
	         </div>
            <div class="col-lg-12 m-b" style="background: currentColor;padding: 16px;">
                <div class="col-lg-6">
                    <div data-toggle="buttons" class="btn-group">
                        <label class="btn btn-sm btn-white"> <input type="radio" id="option1" name="options"> Today </label>
                        <label class="btn btn-sm btn-white active"> <input type="radio" id="option2" name="options"> Monthly </label>
                        <label class="btn btn-sm btn-white"> <input type="radio" id="option3" name="options"> Quaterly </label>
                        <label class="btn btn-sm btn-white"> <input type="radio" id="option4" name="options"> Annually </label>
                    </div>
                </div>
                <div class="col-lg-6">
                </div>
                <div class="col-lg-12">
		            <div class="col-lg-3">
		                <div class="widget style1">
		                    <div class="col-xs-4 text-center">
		                       <!--  <i class="fa fa-shopping-cart" aria-hidden="false"></i> -->
		                    </div>
		                    <div class="col-xs-8 text-right" style="color:#fff;">
		                        <span>Gross Margin</span>
		                        <h2 class="font-bold">4,232</h2>
		                    </div>    
		                </div>
		            </div>
		            <div class="col-lg-3">
		                <div class="widget style1 navy-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/gross.png" alt="gross">
		                        </div>
		                        <div class="col-xs-8 text-right">
		                            <span> Gross Value</span>
		                            <h2 class="font-bold">2600</h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-3">
		                <div class="widget style1 lazur-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/return.png" alt="return">
		                        </div>
		                        <div class="col-xs-8 text-right">
		                            <span> Return Value</span>
		                            <h2 class="font-bold">260</h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-3">
		                <div class="widget style1 yellow-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/net.png" alt="net">
		                        </div>
		                        <div class="col-xs-8 text-right">
		                            <span> Net Value </span>
		                            <h2 class="font-bold">12</h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
	        </div>
	        <div class="row">
	            <div class="col-lg-12 m-b">
	            	<div class="col-sm-6" style="background: #a9a9a9;padding: 16px;">
		                <div class="col-lg-3" style="color: #fff;">
		                    Actual Sale
		                </div>
		                <div class="col-lg-9">
		                    <div data-toggle="buttons" class="btn-group">
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option1" name="options"> Today </label>
		                        <label class="btn btn-sm btn-white active"> <input type="radio" id="option2" name="options"> Monthly </label>
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option3" name="options"> Quaterly </label>
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option4" name="options"> Annually </label>
		                    </div>
		                </div>
			            <div class="col-lg-3">
			                <div class="widget style1">
	                            <div class="col-lg-12 col-xs-12" style="color: #fff;">
	                                <span> Gross Margin</span>
	                                <h2 class="font-bold">4,232</h2>
	                            </div>
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 navy-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/gross.png" alt="gross">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Gross Value</span>
			                            <h5 class="font-bold">260000</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 lazur-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/return.png" alt="return">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Return Value</span>
			                            <h5 class="font-bold">260</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 yellow-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/net.png" alt="net">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Net Value </span>
			                            <h5 class="font-bold">12</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
	        		</div>
	        		<div class="col-sm-6" style="padding: 16px;">
		                <div class="col-lg-3">
		                    Taxable Sale
		                </div>
		                <div class="col-lg-9">
		                    <div data-toggle="buttons" class="btn-group">
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option1" name="options"> Today </label>
		                        <label class="btn btn-sm btn-white active"> <input type="radio" id="option2" name="options"> Monthly </label>
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option3" name="options"> Quaterly </label>
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option4" name="options"> Annually </label>
		                    </div>
		                </div>
			            <div class="col-lg-3">
			                <div class="widget style1">
			                    
		                            <div class="col-lg-12 col-xs-12">
		                                <span> Gross Margin</span>
		                                <h2 class="font-bold">4,232</h2>
		                            </div>
		                        
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 navy-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/gross.png" alt="gross">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Gross Value</span>
			                            <h5 class="font-bold">2600</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 lazur-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/return.png" alt="return">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Return Value</span>
			                            <h5 class="font-bold">260</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 yellow-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/net.png" alt="net">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Net Value </span>
			                            <h5 class="font-bold">12</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
	        		</div>
	        	</div>
	        	<div class="col-lg-12 m-b" style="padding: 16px;">
	            	<div class="col-sm-6">
		                <div class="col-lg-3">
		                    Tax Free Sale
		                </div>
		                <div class="col-lg-9">
		                    <div data-toggle="buttons" class="btn-group">
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option1" name="options"> Today </label>
		                        <label class="btn btn-sm btn-white active"> <input type="radio" id="option2" name="options"> Monthly </label>
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option3" name="options"> Quaterly </label>
		                        <label class="btn btn-sm btn-white"> <input type="radio" id="option4" name="options"> Annually </label>
		                    </div>
		                </div>
			            <div class="col-lg-3">
			                <div class="widget style1">
			                    
		                            <div class="col-lg-12 col-xs-12">
		                                <span> Gross Margin</span>
		                                <h2 class="font-bold">4,232</h2>
		                            </div>
		                        
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 navy-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/gross.png" alt="gross">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Gross Value</span>
			                            <h5 class="font-bold">2600</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 lazur-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/return.png" alt="return">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Return Value</span>
			                            <h5 class="font-bold">260</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div class="col-lg-3">
			                <div class="widget style1 yellow-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/net.png" alt="net">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Net Value </span>
			                            <h5 class="font-bold">12</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
	        		</div>
	        		<div class="col-sm-6" style="background: #a9a9a9;padding: 16px;">
		                <div class="col-lg-12" style="color: #fff;">
		                    Outstanding Payment
		                </div>
		                
			            <div class="col-lg-4">
			                <div class="widget style1">
	                            <div class="col-lg-12 col-xs-12" style="color: #fff;">
	                                <span>Order Count</span>
	                                <h2 class="font-bold">4,232</h2>
	                            </div>
			                </div>
			            </div>
			            <div class="col-lg-4">
			                <div class="widget style1 navy-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/gross.png" alt="gross">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Gross Value</span>
			                            <h5 class="font-bold">260000000000</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div class="col-lg-4">
			                <div class="widget style1 lazur-bg">
			                    <div class="row">
			                        <div class="col-xs-12 text-center">
			                            <img src="/O2R/seller/img/return.png" alt="return">
			                        </div>
			                        <div class="col-xs-12 text-center">
			                            <span> Return Value</span>
			                            <h5 class="font-bold">260</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
	        		</div>
	        	</div>
	        </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="col-sm-6" style="background: #a9a9a9;padding: 16px;">
							<div class="col-lg-12">
								<div class="col-lg-6" style="color: #fff;">Top Selling SKU
								</div>
								<div class="col-lg-6 pull-right text-right">
									<div data-toggle="buttons" class="btn-group">
										<label class="btn btn-sm btn-white"> <input
											type="radio" id="option1" name="options"> Today
										</label> <label class="btn btn-sm btn-white active"> <input
											type="radio" id="option2" name="options"> Monthly
										</label> <label class="btn btn-sm btn-white"> <input
											type="radio" id="option3" name="options"> Quaterly
										</label> <label class="btn btn-sm btn-white"> <input
											type="radio" id="option4" name="options"> Annually
										</label>
									</div>
								</div>
							</div>
							<div class="col-lg-6">
                            <div class="widget style1 lazur-bg">
                                <div class="row">
                                    <div class="col-xs-4">
                                        <img src="/O2R/seller/img/gross.png" alt="gross">
                                    </div>
                                    <div class="col-xs-8 text-right">
                                        <span>Sale Quantity details</span>
                                        <h2 class="font-bold">260000000</h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="widget style1 navy-bg">
                                <div class="row">
                                    <div class="col-xs-2">
                                        <img src="/O2R/seller/img/gross.png" alt="gross">
                                    </div>
                                    <div class="col-xs-10 text-right">
                                        <span> Gross Profit earned details</span>
                                        <h2 class="font-bold">2600090</h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6" style="padding: 16px;background: cyan;">
                        <div class="col-lg-12">
                            <div class="col-lg-6" style="color: #fff;">
                                Top Selling Regions
                            </div>
                            <div class="col-lg-6 pull-right text-right" style="padding: 0px;">
                                <div data-toggle="buttons" class="btn-group">
                                    <label class="btn btn-sm btn-white"> <input type="radio" id="option1" name="options"> Today </label>
                                    <label class="btn btn-sm btn-white active"> <input type="radio" id="option2" name="options"> Monthly </label>
                                    <label class="btn btn-sm btn-white"> <input type="radio" id="option3" name="options"> Quaterly </label>
                                    <label class="btn btn-sm btn-white"> <input type="radio" id="option4" name="options"> Annually </label>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="widget style1 lazur-bg">
                                <div class="row">
                                    <div class="col-xs-4">
                                        <img src="/O2R/seller/img/gross.png" alt="gross">
                                    </div>
                                    <div class="col-xs-8 text-right">
                                        <span>Sale Quantity details</span>
                                        <h2 class="font-bold">260000000</h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="widget style1 yellow-bg">
                                <div class="row">
                                    <div class="col-xs-2">
                                        <img src="/O2R/seller/img/net.png" alt="net">
                                    </div>
                                    <div class="col-xs-10 text-right">
                                        <span>Gross Profit earned details</span>
                                        <h2 class="font-bold">120000000000</h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <div class="col-sm-9 m-b-xs">
                        <p>Upcoming Payments <small>Channelwise List</small></p>
                    </div>
                    <div class="col-sm-3">
                        <!-- <div class="input-group"><input type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                            <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div> -->
                    </div>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Sl No</th>
                            <th>Payment </th>
                            <th>Name </th>
                            <th>Phone </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>Payment <small>This is Payment don by abc</small></td>
                            <td>Patrick Smith</td>
                            <td>0800 051213</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>Alpha Payment</td>
                            <td>Alice Jackson</td>
                            <td>0500 780909</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>            
        </div>
      <jsp:include page="globalfooter.jsp"></jsp:include>
    </div>
</div>	


<jsp:include page="globaljslinks.jsp"></jsp:include>
</body>
</html>