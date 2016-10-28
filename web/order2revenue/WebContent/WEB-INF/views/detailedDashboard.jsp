
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
	<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/eggplant/jquery-ui.min.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.12.1.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	
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
	font-size: 11px;
}
.netValue{
	background: #2a9c2e;
	color : #fff;
}
</style>
<script type="text/javascript">
    $(function() {
      $("#startDateList").datepicker({
        dateFormat: 'mm-dd-yy'
      });
      $("#endDateList").datepicker({
        dateFormat: 'dd-mm-yy'
      });
    });
 </script>
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
		                    <div class="col-xs-8 text-center" style="color: #fff;">
		                    	<span>Gross Margin</span>
		                        <h3 class="font-bold" id="grossMarginPeriod"></h3>		                        
		                    </div>    
		                </div>
		            </div>
		            <div class="col-lg-3">
		                <div class="widget style1 navy-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/gross.png" alt="gross">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Gross Value</span>
		                            <h2 class="font-bold" id="GrossMarginGV"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 lazur-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/return.png" alt="return">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Return Value</span>
		                            <h2 class="font-bold" id="GrossMarginRV"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 yellow-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/net.png" alt="net">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Bad Quantity</span>
		                            <h2 class="font-bold" id="BadQuantityValue"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 netValue">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/net.png" alt="net">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Net Value </span>
		                            <h2 class="font-bold" id="GrossMarginNV"></h2>
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
		                    <div class="col-xs-8 text-center" style="color:#fff;">
		                        <span>Actual Sale</span>
		                        <h3 class="font-bold" id="ActualSalePeriod"></h3>
		                    </div>    
		                </div>
		            </div>
		            <div class="col-lg-3">
		                <div class="widget style1 navy-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/gross.png" alt="gross">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Gross Value</span>
		                            <h2 class="font-bold" id="ActualSaleGV"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 lazur-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/return.png" alt="return">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Return Value</span>
		                            <h2 class="font-bold" id="ActualSaleRV"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 yellow-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/net.png" alt="net">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Additional Charges </span>
		                            <h2 class="font-bold" id="ActualSaleAD"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 netValue">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/net.png" alt="net">
		                        </div>
		                        <div class="col-xs-8 text-center">
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
		                    <div class="col-xs-8 text-center" style="color:#fff;">
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
		                        <div class="col-xs-8 text-center">
		                            <span> Gross Value</span>
		                            <h2 class="font-bold" id="taxFreeSaleGV"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 lazur-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/return.png" alt="return">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Return Value</span>
		                            <h2 class="font-bold" id="taxFreeSaleRV"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 yellow-bg">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/net.png" alt="net">
		                        </div>
		                        <div class="col-xs-8 text-center">
		                            <span> Additional Charges </span>
		                            <h2 class="font-bold" id="taxFreeSaleAD"></h2>
		                        </div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-2">
		                <div class="widget style1 netValue">
		                    <div class="row">
		                        <div class="col-xs-4">
		                            <img src="/O2R/seller/img/net.png" alt="net">
		                        </div>
		                        <div class="col-xs-8 text-center">
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
			                <div class="widget style1 netValue">
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
			                <div class="widget style1 netValue">
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
                    <div class="col-sm-6" style="padding: 16px;">
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
			                <div class="widget style1 netValue">
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
                    <div class="col-sm-6" style="padding: 16px;background: #a9a9a9;">
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
			                <div class="widget style1 netValue">
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
             <div class="row">          
            	<div class="col-lg-12 m-b" style="padding: 16px;">
	            	<div class="col-sm-6" style="background: #a9a9a9;padding: 16px;">
		                <div class="col-lg-12">
		                    Outstanding Payment
		                </div>
			            <div class="col-lg-4">
			                <div class="widget style1">
	                            <div class="col-lg-12 col-xs-12">
	                                <span>TOTAL</span>	                                
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
			                            <span>Gross Value</span>
			                            <h5 class="font-bold" id="outPayGV"></h5>
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
			                            <span>Discounted</span>
			                            <h5 class="font-bold">###</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
	        		</div>
	        		<div class="col-sm-6" style="background:cyan ;padding: 16px;">
		                <div class="col-lg-12" style="color: #fff;">
		                    Upcoming Payment
		                </div>
		                
			            <div class="col-lg-4">
			                <div class="widget style1">
	                            <div class="col-lg-12 col-xs-12" style="color: #fff;">
	                                <span>TOTAL</span>	                                
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
			                            <h5 class="font-bold" id="upcomPayGV"></h5>
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
			                            <span>Deducted</span>
			                            <h5 class="font-bold">###</h5>
			                        </div>
			                    </div>
			                </div>
			            </div>
	        		</div>
	        	</div>
	         </div>
				<div class="ibox-content" style="background: cadetblue;">
					<div class="row">
						<div class="col-sm-3 m-b-xs">
							<p>
								<label id="criteria"></label> <small id="wiseList"></small>
							</p>
						</div>
						<div class="col-sm-3">							
						</div>
						<form action="getDashListOnCriteria.html" method="get">
							<div class="col-sm-3">
								<fmt:formatDate value="${startDate}" var="startDate" type="date" pattern="MM/dd/yyyy" />
								<input type="text" id="startDateList" name="startDateList" value="${startDate}" placeholder="From" class="btn btn-primary btn-xs"
									style="background: dimgray; width: 48%; float: left; margin-right: 5px;" />
								<fmt:formatDate value="${endDate}" var="endDate" type="date" pattern="MM/dd/yyyy" />
								<input type="text" id="endDateList" name="endDateList" value="${endDate}" placeholder="To" class="btn btn-primary btn-xs"
									style="background: dimgray; width: 48%; float: left;" />
							</div>
							<div class="col-sm-3" style="text-align: right;">
								<select class="btn btn-primary btn-xs" id="selectCriteriaList" name="selectCriteriaList" style="width: 70%; color: #676a6c !important;">
									<option value="Gross Margin" id="Gross Margin">Gross Margin</option>
									<option value="Actual Sale" id="Actual Sale">Actual Sale</option>
									<option value="Tax Free Sale" id="Tax Free Sale">Tax Free Sale</option>
									<option value="Taxable Sale" id="Taxable Sale">Taxable Sale</option>
									<option value="Sale Quantity" id="Sale Quantity">Sale Quantity</option>
									<option value="Top Selleing SKUs" id="Top Selleing SKUs">Top Selleing SKUs</option>
									<option value="Top Selling Regions" id="Top Selling Regions">Top Selling Regions</option>
									<option value="Outstanding Payment" id="Outstanding Payment">Outstanding Payment</option>
									<option value="Upcoming Payment" id="Upcoming Payment">Upcoming Payment</option>
								</select>
								<button class="btn btn-primary btn-xs" type="submit" style="background: dimgray;">GET</button>
							</div>
						</form>

					</div>
					<div class="table-responsive">
					<div class="slim-scroll-bar">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th>Month</th>
									<th>Channel</th>
									<th>City</th>
									<th>SKU</th>
									<th>Gross</th>
									<th>Return</th>
									<th>Net</th>
									<th>Additional</th>
									<th>BadQtyCharge</th>
									<th>Deducted</th>
									
								</tr>
							</thead>							
							<tbody >
								<c:if test="${criteria eq 'Outstanding Payment' or criteria eq 'Upcoming Payment'}">
									<c:if test="${!empty DDBeans}">
										<c:forEach items="${DDBeans}" var="DDBean" varStatus="loop">
											<tr>
												<td>${loop.index+1}</td>
												<td></td>
												<td>${DDBean.channel}</td>
												<td></td>
												<td></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.grossValue}" /></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>									
											</tr>
										</c:forEach>
									</c:if>
								</c:if>
								<c:if test="${criteria eq 'Actual Sale' or criteria eq 'Tax Free Sale'}">
									<c:if test="${!empty DDBeans}">
										<c:forEach items="${DDBeans}" var="DDBean" varStatus="loop">
											<tr>
												<td>${loop.index+1}</td>
												<td>${DDBean.month}</td>
												<td></td>
												<td></td>
												<td></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.grossValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.returnValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.netValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.addCharges}" /></td>
												<td></td>
												<td></td>									
											</tr>
										</c:forEach>
									</c:if>
								</c:if>	
								<c:if test="${criteria eq 'Taxable Sale'}">
									<c:if test="${!empty DDBeans}">
										<c:forEach items="${DDBeans}" var="DDBean" varStatus="loop">
											<tr>
												<td>${loop.index+1}</td>
												<td>${DDBean.month}</td>
												<td></td>
												<td></td>
												<td></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.grossValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.returnValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.netValue}" /></td>
												<td></td>
												<td></td>	
												<td></td>								
											</tr>
										</c:forEach>
									</c:if>
								</c:if>
								<c:if test="${criteria eq 'Gross Margin'}">
									<c:if test="${!empty DDBeans}">
										<c:forEach items="${DDBeans}" var="DDBean" varStatus="loop">
											<tr>
												<td>${loop.index+1}</td>
												<td>${DDBean.month}</td>
												<td></td>
												<td></td>
												<td></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.grossValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.returnValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.netValue}" /></td>
												<td></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.grossValue}" /></td>
												<td></td>									
											</tr>
										</c:forEach>
									</c:if>
								</c:if>	
								<c:if test="${criteria eq 'Top Selling Regions'}">
									<c:if test="${!empty DDBeans}">
										<c:forEach items="${DDBeans}" var="DDBean" varStatus="loop">
											<tr>
												<td>${loop.index+1}</td>
												<td></td>
												<td></td>
												<td>${DDBean.city}</td>
												<td></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.grossValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.returnValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.netValue}" /></td>
												<td></td>
												<td></td>
												<td></td>									
											</tr>
										</c:forEach>
									</c:if>
								</c:if>	
								<c:if test="${criteria eq 'Sale Quantity'}">
									<c:if test="${!empty DDBeans}">
										<c:forEach items="${DDBeans}" var="DDBean" varStatus="loop">
											<tr>
												<td>${loop.index+1}</td>
												<td>${DDBean.month}</td>
												<td></td>
												<td></td>
												<td></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.grossValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.returnValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.netValue}" /></td>
												<td></td>
												<td></td>	
												<td></td>								
											</tr>
										</c:forEach>
									</c:if>
								</c:if>	
								<c:if test="${criteria eq 'Top Selleing SKUs'}">
									<c:if test="${!empty DDBeans}">
										<c:forEach items="${DDBeans}" var="DDBean" varStatus="loop">
											<tr>
												<td>${loop.index+1}</td>
												<td></td>
												<td></td>
												<td></td>
												<td>${DDBean.sku}</td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.grossValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.returnValue}" /></td>
												<td><fmt:formatNumber type="number"	maxFractionDigits="2" value="${DDBean.netValue}" /></td>
												<td></td>
												<td></td>	
												<td></td>								
											</tr>
										</c:forEach>
									</c:if>
								</c:if>																											
							</tbody>
						</table>
						</div>
					</div>
				</div>
				<jsp:include page="globalfooter.jsp"></jsp:include>
    </div>
</div>	
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						
						var id = '${criteria}';
						if(id != ""){
							$('#selectCriteriaList').val(id);
						}
						if(id == 'Gross Margin' || id == 'Actual Sale' || id == 'Tax Free Sale' || id == 'Taxable Sale' || id == 'Sale Quantity'){
							document.getElementById("criteria").innerHTML = id;
							document.getElementById("wiseList").innerHTML = "Monthly Wise List";
						} else if(id == 'Top Selleing SKUs'){
							document.getElementById("criteria").innerHTML = id;
							document.getElementById("wiseList").innerHTML = "SKU Wise List";
						} else if(id == 'Top Selling Regions'){
							document.getElementById("criteria").innerHTML = id;
							document.getElementById("wiseList").innerHTML = "City Wise List";
						} else {
							document.getElementById("criteria").innerHTML = id;
							document.getElementById("wiseList").innerHTML = "Channel Wise List";
						}
						$('.slim-scroll-bar').slimScroll({
					        height: '250px',
					        railOpacity: 0.4
					    });
						
						$("#endDateList").change(
								function() {
									var startDate = document
											.getElementById("startDateList").value;
									var endDate = document
											.getElementById("endDateList").value;

									if ((Date.parse(startDate) > Date
											.parse(endDate))) {
										alert("End Date Should Be Greater Than Start Date !");
										document.getElementById("endDateList").value = "";
									}
								});
						
						
						
						
						$
								.ajax({
									url : "getGrossMargin.html?status=ALL",
									dataType : "json",
									success : function(data) {
										document
												.getElementById("grossMarginPeriod").innerHTML = "ALL";
										document
												.getElementById("GrossMarginGV").innerHTML = data.grossMargin;
										document
												.getElementById("GrossMarginRV").innerHTML = data.returnGrossProfit;
										document
												.getElementById("GrossMarginNV").innerHTML = data.netGrossProfit;
										document
												.getElementById("BadQuantityValue").innerHTML = data.badQuantityValue;
									}
								});

						$
								.ajax({
									url : "getActualSale.html?status=ALL",
									dataType : "json",
									success : function(data) {
										document
												.getElementById("ActualSalePeriod").innerHTML = "ALL";
										document.getElementById("ActualSaleGV").innerHTML = data.grossNR;
										document.getElementById("ActualSaleRV").innerHTML = data.returnNR;
										document.getElementById("ActualSaleNV").innerHTML = data.netNR;
										document.getElementById("ActualSaleAD").innerHTML = data.addCharges;
									}
								});

						$
								.ajax({
									url : "getSaleQuantity.html?status=ALL",
									dataType : "json",
									success : function(data) {
										document
												.getElementById("saleQuantityPeriod").innerHTML = "ALL";
										document
												.getElementById("saleQuantityGV").innerHTML = data.grossSaleQty;
										document
												.getElementById("saleQuantityRV").innerHTML = data.returnSaleQty;
										document
												.getElementById("saleQuantityNV").innerHTML = data.netSaleQty;
									}
								});

						$
								.ajax({
									url : "getTaxableSale.html?status=ALL",
									dataType : "json",
									success : function(data) {
										document
												.getElementById("taxableSalePeriod").innerHTML = "ALL";
										document
												.getElementById("taxableSaleGV").innerHTML = data.grossSP;
										document
												.getElementById("taxableSaleRV").innerHTML = data.returnSP;
										document
												.getElementById("taxableSaleNV").innerHTML = data.netSP;
									}
								});

						$
								.ajax({
									url : "getTaxFreeSale.html?status=ALL",
									dataType : "json",
									success : function(data) {
										document
												.getElementById("taxFreeSalePeriod").innerHTML = "ALL";
										document
												.getElementById("taxFreeSaleGV").innerHTML = data.grossPR;
										document
												.getElementById("taxFreeSaleRV").innerHTML = data.returnPR;
										document
												.getElementById("taxFreeSaleNV").innerHTML = data.netPR;
										document.getElementById("taxFreeSaleAD").innerHTML = data.addCharges;
									}
								});

						$
								.ajax({
									url : "getTopSellingSKU.html?status=ALL",
									dataType : "json",
									success : function(data) {
										document
												.getElementById("topSellingSKUPeriod").innerHTML = "ALL";
										document
												.getElementById("topSellingSKUGV").innerHTML = data.saleQ;
										document
												.getElementById("topSellingSKURV").innerHTML = data.returnQ;
										document
												.getElementById("topSellingSKUNV").innerHTML = data.netQ;
										document
												.getElementById("topSellingSKUName").innerHTML = data.sku;
									}
								});

						$
								.ajax({
									url : "getTopSellingRegion.html?status=ALL",
									dataType : "json",
									success : function(data) {
										document
												.getElementById("topSellingRegionPeriod").innerHTML = "ALL";
										document
												.getElementById("topSellingRegionGV").innerHTML = data.grossSale;
										document
												.getElementById("topSellingRegionRV").innerHTML = data.returnSale;
										document
												.getElementById("topSellingRegionNV").innerHTML = data.netSale;
										document
												.getElementById("topSellingRegionName").innerHTML = data.Region;
									}
								});

						$
								.ajax({
									url : "getOutstandingPayment.html",
									dataType : "json",
									success : function(data) {
										document.getElementById("outPayGV").innerHTML = data.Total;
									}
								});

						$
								.ajax({
									url : "getUpcomingPayment.html",
									dataType : "json",
									success : function(data) {
										document.getElementById("upcomPayGV").innerHTML = data.Total;
									}
								});
					});

	//Period Wise Data Call

	function grossMarginCall(status) {
		$
				.ajax({
					url : "getGrossMargin.html?status=" + status,
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
	function saleQuantity(status) {
		$
				.ajax({
					url : "getSaleQuantity.html?status=" + status,
					dataType : "json",
					success : function(data) {
						document.getElementById("saleQuantityPeriod").innerHTML = status;
						document.getElementById("saleQuantityGV").innerHTML = data.grossSaleQty;
						document.getElementById("saleQuantityRV").innerHTML = data.returnSaleQty;
						document.getElementById("saleQuantityNV").innerHTML = data.netSaleQty;
					}
				});
	}
	function ActualSale(status) {
		$
				.ajax({
					url : "getActualSale.html?status=" + status,
					dataType : "json",
					success : function(data) {
						document.getElementById("ActualSalePeriod").innerHTML = status;
						document.getElementById("ActualSaleGV").innerHTML = data.grossNR;
						document.getElementById("ActualSaleRV").innerHTML = data.returnNR;
						document.getElementById("ActualSaleNV").innerHTML = data.netNR;
						document.getElementById("ActualSaleAD").innerHTML = data.addCharges;
					}
				});
	}
	function taxableSale(status) {
		$
				.ajax({
					url : "getTaxableSale.html?status=" + status,
					dataType : "json",
					success : function(data) {
						document.getElementById("taxableSalePeriod").innerHTML = status;
						document.getElementById("taxableSaleGV").innerHTML = data.grossSP;
						document.getElementById("taxableSaleRV").innerHTML = data.returnSP;
						document.getElementById("taxableSaleNV").innerHTML = data.netSP;
					}
				});
	}

	function taxFreeSale(status) {
		$
				.ajax({
					url : "getTaxFreeSale.html?status=" + status,
					dataType : "json",
					success : function(data) {
						document.getElementById("taxFreeSalePeriod").innerHTML = status;
						document.getElementById("taxFreeSaleGV").innerHTML = data.grossPR;
						document.getElementById("taxFreeSaleRV").innerHTML = data.returnPR;
						document.getElementById("taxFreeSaleNV").innerHTML = data.netPR;
						document.getElementById("taxFreeSaleAD").innerHTML = data.addCharges;
					}
				});
	}

	function topSellingSKU(status) {
		$
				.ajax({
					url : "getTopSellingSKU.html?status=" + status,
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

	function topSellingRegion(status) {
		$
				.ajax({
					url : "getTopSellingRegion.html?status=" + status,
					dataType : "json",
					success : function(data) {
						document.getElementById("topSellingRegionPeriod").innerHTML = status;
						document.getElementById("topSellingRegionGV").innerHTML = data.grossSale;
						document.getElementById("topSellingRegionRV").innerHTML = data.returnSale;
						document.getElementById("topSellingRegionNV").innerHTML = data.netSale;
						document.getElementById("topSellingRegionName").innerHTML = data.Region;
					}
				});
	}	
	
</script>
<jsp:include page="globaljslinks.jsp"></jsp:include>
<script src="/O2R/seller/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
</body>
</html>