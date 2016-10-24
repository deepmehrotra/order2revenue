
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
                        <label class="btn btn-sm btn-white" onclick="grossMarginCall('Today');"> <input type="radio" > Today </label>
                        <label class="btn btn-sm btn-white active" onclick="grossMarginCall('Monthly');"> <input type="radio" > Monthly </label>
                        <label class="btn btn-sm btn-white" onclick="grossMarginCall('Quaterly');"> <input type="radio" > Quaterly </label>
                        <label class="btn btn-sm btn-white" onclick="grossMarginCall('Yearly');"> <input type="radio"> Annually </label>
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
		                    	<span>Gross Margin</span>
		                        <h2 class="font-bold" id="grossMarginPeriod"></h2>		                        
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
		                            <h2 class="font-bold" id="GrossMarginGV"></h2>
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
		                            <h2 class="font-bold" id="GrossMarginRV"></h2>
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
		                            <h2 class="font-bold" id="GrossMarginNV"></h2>
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
		                            <span> Bad Quantity Value </span>
		                            <h2 class="font-bold" id="BadQuantityValue"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
	         </div>
            <div class="col-lg-12 m-b" style="background: currentColor;padding: 16px;">
                <div class="col-lg-6">
                    <div data-toggle="buttons" class="btn-group">
                        <label class="btn btn-sm btn-white" onclick="ActualSale('Today');"> <input type="radio" > Today </label>
                        <label class="btn btn-sm btn-white" onclick="ActualSale('Monthly');"> <input type="radio" > Monthly </label>
                        <label class="btn btn-sm btn-white" onclick="ActualSale('Quaterly');"> <input type="radio" > Quaterly </label>
                        <label class="btn btn-sm btn-white" onclick="ActualSale('Yearly');"> <input type="radio"> Annually </label>
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
		                        <span>Actual Sale</span>
		                        <h2 class="font-bold" id="ActualSalePeriod"></h2>
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
		                            <h2 class="font-bold" id="ActualSaleGV"></h2>
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
		                            <h2 class="font-bold" id="ActualSaleRV"></h2>
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
		                            <h2 class="font-bold" id="ActualSaleNV"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		        </div>
	        </div>
	        <div class="col-lg-12 m-b" style="background: currentColor;padding: 16px;">
                <div class="col-lg-6">
                    <div data-toggle="buttons" class="btn-group">
                        <label class="btn btn-sm btn-white" onclick="taxFreeSale('Today');"> <input type="radio" > Today </label>
                        <label class="btn btn-sm btn-white" onclick="taxFreeSale('Monthly');"> <input type="radio" > Monthly </label>
                        <label class="btn btn-sm btn-white" onclick="taxFreeSale('Quaterly');"> <input type="radio" > Quaterly </label>
                        <label class="btn btn-sm btn-white" onclick="taxFreeSale('Yearly');"> <input type="radio"> Annually </label>
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
		                        <span>Tax Free Sale</span>
		                        <h2 class="font-bold" id="taxFreeSalePeriod"></h2>
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
		                            <h2 class="font-bold" id="taxFreeSaleGV"></h2>
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
		                            <h2 class="font-bold" id="taxFreeSaleRV"></h2>
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
		                            <h2 class="font-bold" id="taxFreeSaleNV"></h2>
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
		                    Taxable Sale
		                </div>
		                <div class="col-lg-9">
		                    <div data-toggle="buttons" class="btn-group">
				                <label class="btn btn-sm btn-white" onclick="taxableSale('Today');"> <input type="radio" > Today </label>
		                        <label class="btn btn-sm btn-white" onclick="taxableSale('Monthly');"> <input type="radio" > Monthly </label>
		                        <label class="btn btn-sm btn-white" onclick="taxableSale('Quaterly');"> <input type="radio" > Quaterly </label>
		                        <label class="btn btn-sm btn-white" onclick="taxableSale('Yearly');"> <input type="radio"> Annually </label>
		                    </div>
		                </div>
			            <div class="col-lg-3">
			                <div class="widget style1">
	                            <div class="col-lg-12 col-xs-12" style="color: #fff;">
	                                <span></span>
	                                <h2 class="font-bold" id="taxableSalePeriod"></h2>
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
			                            <h5 class="font-bold" id="taxableSaleGV"></h5>
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
			                            <h5 class="font-bold" id="taxableSaleRV"></h5>
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
			                            <h5 class="font-bold" id="taxableSaleNV"></h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
	        		</div>
	        		<div class="col-sm-6" style="padding: 16px;">
		                <div class="col-lg-3">
		                    Sale Quantity
		                </div>
		                <div class="col-lg-9">
		                    <div data-toggle="buttons" class="btn-group">
		                        <label class="btn btn-sm btn-white" onclick="saleQuantity('Today')"> <input type="radio"> Today </label>
		                        <label class="btn btn-sm btn-white" onclick="saleQuantity('Monthly')"> <input type="radio"> Monthly </label>
		                        <label class="btn btn-sm btn-white" onclick="saleQuantity('Quaterly')"> <input type="radio"> Quaterly </label>
		                        <label class="btn btn-sm btn-white" onclick="saleQuantity('Yearly')"> <input type="radio"> Annually </label>
		                    </div>
		                </div>
			            <div class="col-lg-3">
			                <div class="widget style1">
			                    
		                            <div class="col-lg-12 col-xs-12">
		                                <span>Value For</span>
		                                <h2 class="font-bold" id="saleQuantityPeriod"></h2>
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
			                            <h5 class="font-bold" id="saleQuantityGV"></h5>
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
			                            <h5 class="font-bold" id="saleQuantityRV"></h5>
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
			                            <h5 class="font-bold" id="saleQuantityNV"></h5>
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
						<div class="col-lg-3">
		                    Top Selling SKU
		                </div>
		                <div class="col-lg-9">
		                    <div data-toggle="buttons" class="btn-group">
		                        <label class="btn btn-sm btn-white" onclick="topSellingSKU('Today')"> <input type="radio"> Today </label>
		                        <label class="btn btn-sm btn-white" onclick="topSellingSKU('Monthly')"> <input type="radio"> Monthly </label>
		                        <label class="btn btn-sm btn-white" onclick="topSellingSKU('Quaterly')"> <input type="radio"> Quaterly </label>
		                        <label class="btn btn-sm btn-white" onclick="topSellingSKU('Yearly')"> <input type="radio"> Annually </label>
		                    </div>
		                </div>
			            <div class="col-lg-3">
			                <div class="widget style1">
			                    
		                            <div class="col-lg-12 col-xs-12">
		                                <span id="topSellingSKUName"></span>
		                                <h2 class="font-bold" id="topSellingSKUPeriod"></h2>
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
			                            <h5 class="font-bold" id="topSellingSKUGV"></h5>
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
			                            <h5 class="font-bold" id="topSellingSKURV"></h5>
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
			                            <h5 class="font-bold" id="topSellingSKUNV"></h5>
			                        </div>
			                    </div>
			                </div>
			            </div>                        
                    </div>
                    <div class="col-sm-6" style="padding: 16px;background: cyan;">
                        <div class="col-lg-3">
		                    Top Selling Reg
		                </div>
		                <div class="col-lg-9">
		                    <div data-toggle="buttons" class="btn-group">
		                        <label class="btn btn-sm btn-white" onclick="topSellingRegion('Today')"> <input type="radio"> Today </label>
		                        <label class="btn btn-sm btn-white" onclick="topSellingRegion('Monthly')"> <input type="radio"> Monthly </label>
		                        <label class="btn btn-sm btn-white" onclick="topSellingRegion('Quaterly')"> <input type="radio"> Quaterly </label>
		                        <label class="btn btn-sm btn-white" onclick="topSellingRegion('Yearly')"> <input type="radio"> Annually </label>
		                    </div>
		                </div>
			            <div class="col-lg-3">
			                <div class="widget style1">
			                     <div class="col-lg-12 col-xs-12">
		                            <span id="topSellingRegionName"></span>
		                            <h2 class="font-bold" id="topSellingRegionPeriod"></h2>
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
			                            <h5 class="font-bold" id="topSellingRegionGV"></h5>
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
			                            <h5 class="font-bold" id="topSellingRegionRV"></h5>
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
			                            <h5 class="font-bold" id="topSellingRegionNV"></h5>
			                        </div>
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
			                        <span>Outstanding Payments</span>
			                        <h2 class="font-bold" id="Period"></h2>
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
			                            <h2 class="font-bold" id="GV"></h2>
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
			                            <h2 class="font-bold" id="RV"></h2>
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
			                            <h2 class="font-bold" id="NV"></h2>
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
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({								
		url : "getGrossMargin.html?status=ALL",
		dataType : "json",
		success : function(data) {			
			document.getElementById("grossMarginPeriod").innerHTML = "ALL";
			document.getElementById("GrossMarginGV").innerHTML = data.grossMargin;
			document.getElementById("GrossMarginRV").innerHTML = data.returnGrossProfit;
			document.getElementById("GrossMarginNV").innerHTML = data.netGrossProfit;
			document.getElementById("BadQuantityValue").innerHTML = data.badQuantityValue;
		}							
	});
	
	$.ajax({								
		url : "getActualSale.html?status=ALL",
		dataType : "json",
		success : function(data) {			
			document.getElementById("ActualSalePeriod").innerHTML = "ALL";
			document.getElementById("ActualSaleGV").innerHTML = data.grossNR;
			document.getElementById("ActualSaleRV").innerHTML = data.returnNR;
			document.getElementById("ActualSaleNV").innerHTML = data.netNR;
			//document.getElementById("").innerHTML = data.addCharges;
		}							
	});
	
	$.ajax({								
		url : "getSaleQuantity.html?status=ALL",
		dataType : "json",
		success : function(data) {			
			document.getElementById("saleQuantityPeriod").innerHTML = "ALL";
			document.getElementById("saleQuantityGV").innerHTML = data.grossSaleQty;
			document.getElementById("saleQuantityRV").innerHTML = data.returnSaleQty;
			document.getElementById("saleQuantityNV").innerHTML = data.netSaleQty;			
		}							
	});
	
	$.ajax({								
		url : "getTaxableSale.html?status=ALL",
		dataType : "json",
		success : function(data) {			
			document.getElementById("taxableSalePeriod").innerHTML = "ALL";
			document.getElementById("taxableSaleGV").innerHTML = data.grossSP;
			document.getElementById("taxableSaleRV").innerHTML = data.returnSP;
			document.getElementById("taxableSaleNV").innerHTML = data.netSP;			
		}							
	});
	
	$.ajax({								
		url : "getTaxFreeSale.html?status=ALL",
		dataType : "json",
		success : function(data) {			
			document.getElementById("taxFreeSalePeriod").innerHTML = "ALL";
			document.getElementById("taxFreeSaleGV").innerHTML = data.grossPR;
			document.getElementById("taxFreeSaleRV").innerHTML = data.returnPR;
			document.getElementById("taxFreeSaleNV").innerHTML = data.netPR;
			//document.getElementById("").innerHTML = data.addCharges;
		}							
	});
	
	$.ajax({								
		url : "getTopSellingSKU.html?status=ALL",
		dataType : "json",
		success : function(data) {			
			document.getElementById("topSellingSKUPeriod").innerHTML = "ALL";
			document.getElementById("topSellingSKUGV").innerHTML = data.saleQ;
			document.getElementById("topSellingSKURV").innerHTML = data.returnQ;
			document.getElementById("topSellingSKUNV").innerHTML = data.netQ;
			document.getElementById("topSellingSKUName").innerHTML = data.sku;		
		}							
	});
	
	$.ajax({								
		url : "getTopSellingRegion.html?status=ALL",
		dataType : "json",
		success : function(data) {			
			document.getElementById("topSellingRegionPeriod").innerHTML = "ALL";
			document.getElementById("topSellingRegionGV").innerHTML = data.saleQ;
			document.getElementById("topSellingRegionRV").innerHTML = data.returnQ;
			document.getElementById("topSellingRegionNV").innerHTML = data.netQ;
			document.getElementById("topSellingRegionName").innerHTML = data.name;		
		}							
	});
});


//Period Wise Data Call


function grossMarginCall(status){	
	$.ajax({								
		url : "getGrossMargin.html?status="+status,
		dataType : "json",
		success : function(data) {			
			document.getElementById("grossMarginPeriod").innerHTML = status;
			document.getElementById("GrossMarginGV").innerHTML = data.grossMargin;
			document.getElementById("GrossMarginRV").innerHTML = data.returnGrossProfit;
			document.getElementById("GrossMarginNV").innerHTML = data.netGrossProfit;
			document.getElementById("BadQuantityValue").innerHTML = data.badQuantityValue;
		}							
	});
}
function saleQuantity(status){	
	$.ajax({								
		url : "getSaleQuantity.html?status="+status,
		dataType : "json",
		success : function(data) {			
			document.getElementById("saleQuantityPeriod").innerHTML = status;
			document.getElementById("saleQuantityGV").innerHTML = data.grossSaleQty;
			document.getElementById("saleQuantityRV").innerHTML = data.returnSaleQty;
			document.getElementById("saleQuantityNV").innerHTML = data.netSaleQty;			
		}							
	});
}
function ActualSale(status){	
	$.ajax({								
		url : "getActualSale.html?status="+status,
		dataType : "json",
		success : function(data) {			
			document.getElementById("ActualSalePeriod").innerHTML = status;
			document.getElementById("ActualSaleGV").innerHTML = data.grossNR;
			document.getElementById("ActualSaleRV").innerHTML = data.returnNR;
			document.getElementById("ActualSaleNV").innerHTML = data.netNR;
			document.getElementById("").innerHTML = data.addCharges;
		}							
	});
}
function taxableSale(status){	
	$.ajax({								
		url : "getTaxableSale.html?status="+status,
		dataType : "json",
		success : function(data) {			
			document.getElementById("taxableSalePeriod").innerHTML = status;
			document.getElementById("taxableSaleGV").innerHTML = data.grossSP;
			document.getElementById("taxableSaleRV").innerHTML = data.returnSP;
			document.getElementById("taxableSaleNV").innerHTML = data.netSP;			
		}							
	});
}

function taxFreeSale(status){	
	$.ajax({								
		url : "getTaxFreeSale.html?status="+status,
		dataType : "json",
		success : function(data) {			
			document.getElementById("taxFreeSalePeriod").innerHTML = status;
			document.getElementById("taxFreeSaleGV").innerHTML = data.grossPR;
			document.getElementById("taxFreeSaleRV").innerHTML = data.returnPR;
			document.getElementById("taxFreeSaleNV").innerHTML = data.netPR;
			//document.getElementById("").innerHTML = data.addCharges;
		}							
	});
}

function topSellingSKU(status){	
	$.ajax({								
		url : "getTopSellingSKU.html?status="+status,
		dataType : "json",
		success : function(data) {			
			document.getElementById("topSellingSKUPeriod").innerHTML = status;
			document.getElementById("topSellingSKUGV").innerHTML = data.saleQ;
			document.getElementById("topSellingSKURV").innerHTML = data.returnQ;
			document.getElementById("topSellingSKUNV").innerHTML = data.netQ;
			document.getElementById("topSellingSKUName").innerHTML = data.sku;
		}							
	});
}

function topSellingRegion(status){	
	$.ajax({								
		url : "getTopSellingRegion.html?status="+status,
		dataType : "json",
		success : function(data) {			
			document.getElementById("topSellingRegionPeriod").innerHTML = status;
			document.getElementById("topSellingRegionGV").innerHTML = data.saleQ;
			document.getElementById("topSellingRegionRV").innerHTML = data.returnQ;
			document.getElementById("topSellingRegionNV").innerHTML = data.netQ;
			document.getElementById("topSellingRegionName").innerHTML = data.name;		
		}							
	});
}

</script>

<jsp:include page="globaljslinks.jsp"></jsp:include>
</body>
</html>