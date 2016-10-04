<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.util.*" %>
<%@ page import="java.security.*" %>

<%!
public boolean empty(String s)
	{
		if(s== null || s.trim().equals(""))
			return true;
		else
			return false;
	}
%>
<%!
	public String hashCal(String type,String str){
		byte[] hashseq=str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try{
		MessageDigest algorithm = MessageDigest.getInstance(type);
		algorithm.reset();
		algorithm.update(hashseq);
		byte messageDigest[] = algorithm.digest();		

		for (int i=0;i<messageDigest.length;i++) {
			String hex=Integer.toHexString(0xFF & messageDigest[i]);
			if(hex.length()==1) hexString.append("0");
			hexString.append(hex);
		}
			
		}catch(NoSuchAlgorithmException nsae){ }		
		return hexString.toString();
	}
%>
<% 	
	/* String merchant_key="rKhu3pp4";
	String salt="r28k5UA4t1";
	String action1 ="";
	String base_url="https://test.payu.in"; */
	String merchant_key=request.getAttribute("payuMerchantKey").toString();
	String salt=request.getAttribute("payuSalt").toString();
	String action1 ="";
	String base_url=request.getAttribute("payubaseurl").toString();
	int error=0;
	String hashString="";
	
	Enumeration paramNames = request.getParameterNames();
	Map<String,String> params= new HashMap<String,String>();
    	while(paramNames.hasMoreElements()) 
	{
      		String paramName = (String)paramNames.nextElement();
      
      		String paramValue = request.getParameter(paramName);
		params.put(paramName,paramValue);
	}
	String txnid ="";
	String udf2 ="";
	if(empty(params.get("txnid"))){
		Random rand = new Random();
		String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
		txnid=hashCal("SHA-256",rndm).substring(0,20);
	}
	else
		txnid=params.get("txnid");
    udf2 = txnid;
	String txn="abcd";
	String hash="";
	String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
	if(empty(params.get("hash")) && params.size()>0)
	{
		if( empty(params.get("key"))
			|| empty(params.get("txnid"))
			|| empty(params.get("amount"))
			|| empty(params.get("firstname"))
			|| empty(params.get("email"))
			|| empty(params.get("phone"))
			|| empty(params.get("productinfo"))
			|| empty(params.get("surl"))
			|| empty(params.get("furl"))
			|| empty(params.get("service_provider"))
	)
			
			error=1;
		else{
			String[] hashVarSeq=hashSequence.split("\\|");
			
			for(String part : hashVarSeq)
			{
				hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
				hashString=hashString.concat("|");
			}
			hashString=hashString.concat(salt);
			

			 hash=hashCal("SHA-512",hashString);
			action1=base_url.concat("/_payment");
		}
	}
	else if(!empty(params.get("hash")))
	{
		hash=params.get("hash");
		action1=base_url.concat("/_payment");
	}
		

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>
  <script type="text/javascript"
    src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function onclickviewExpCat(id) {
       $.ajax({
            url : 'viewExpenseGroup.html?expcategoryId='+id,
            success : function(data) {
            	if($(data).find('#j_username').length > 0){
            		window.location.href = "orderindex.html";
            	}else{
                	$('#centerpane').html(data);
            	}
            }
        });
    }
    function onclickAddTaxCategory() {
    	  $.ajax({
            url : 'addTaxCategory.html',
            success : function(data) {
            	if($(data).find('#j_username').length > 0){
            		window.location.href = "orderindex.html";
            	}else{
                	$('#centerpane').html(data);
            	}
            }
        });
    }
    
    function select(obj) {		
		$(".selectPlanField").val($(obj).val()).trigger('change');	
	}   
    
	function updateFields(obj){		
		if($(obj).val() && "notSelected" != $(obj).val()){
			/* var serviceTax = parseFloat('${serviceTax}'); */
			var serviceTax =14;
			var kktax =.5;
			var educationcess =.5;
			var orderCount = parseInt($(obj).find(':selected').data('ordercount'));			
			var planPrice = $(obj).find(':selected').data('planprice');			
			var minAmount = parseInt(Math.ceil(orderCount*planPrice));			
			var taxAmount = parseFloat(parseFloat(minAmount*serviceTax/100).toFixed(2));
			var kktaxAmount = parseFloat(parseFloat(minAmount*kktax/100).toFixed(2));
			var educessAmount = parseFloat(parseFloat(minAmount*educationcess/100).toFixed(2));
			var totalAmount = minAmount + taxAmount+kktaxAmount+educessAmount;
			$(".currPlanPriceTxt").html(planPrice);			
			$(".currTotalAmount").val(totalAmount);
			$(".currOrderCount").val(orderCount);
			localStorage.setItem("totalAmount", totalAmount);
			localStorage.setItem("orderCount", orderCount);
			localStorage.setItem("txnid", '<%= txnid %>');
			localStorage.setItem("pid", $(".selectPlanField").val());
			$(".currAmountText").html(minAmount);
			$(".currTaxAmountText").html(taxAmount);
			$(".currTotalAmountText").html(totalAmount);
			$(".payuAmount").val(totalAmount);
			$(".kkTaxAmountText").html(kktaxAmount);
			$(".educessTaxAmountText").html(educessAmount);
			$(".checkoutButton").removeAttr('disabled');
		}else{
			$(".currPlanPriceTxt").html(0);
			$(".currAmount").val(0);
			$(".currTotalAmount").val(0);
			$(".payuAmount").val(0);
			$(".currOrderCount").val(0);
			$(".currAmountText").html(0);
			$(".currTaxAmountText").html(0);
			$(".currTotalAmountText").html(0);
			$(".checkoutButton").prop('disabled', true);
			localStorage.clear();
		}
	} 
	function updatePrices(obj){
		var currObjVal = $(obj).val();
		if(currObjVal && "" != currObjVal){
			var orderCount = parseInt($(".selectPlanField").find(':selected').data('ordercount'));
			var currOrder = parseInt(currObjVal);			
			if(currOrder >= orderCount){
				//var serviceTax = parseFloat('${serviceTax}');
				var serviceTax =14;
				var kktax =.5;
				var educationcess =.5;
				$(".checkoutButton").removeAttr('disabled');
				var planPrice = $(".selectPlanField").find(':selected').data('planprice');
				var minAmount = parseInt(Math.ceil(currOrder*planPrice));
				var taxAmount = parseFloat(parseFloat(minAmount*serviceTax/100).toFixed(2));
				var kktaxAmount = parseFloat(parseFloat(minAmount*kktax/100).toFixed(2));
				var educessAmount = parseFloat(parseFloat(minAmount*educationcess/100).toFixed(2));
				var totalAmount = minAmount + taxAmount+kktaxAmount+educessAmount;
				$(".currTotalAmount").val(totalAmount);
				$(".currOrderCount").val(currOrder);
				$(".currAmountText").html(minAmount);
				$(".currTaxAmountText").html(taxAmount);
				$(".currTotalAmountText").html(totalAmount);
				$(".kkTaxAmountText").html(kktaxAmount);
				$(".educessTaxAmountText").html(educessAmount);
				$(".payuAmount").val(totalAmount);
				localStorage.setItem("totalAmount", totalAmount);
				localStorage.setItem("orderCount", orderCount);
				localStorage.setItem("pid", $(".selectPlanField").val());	
				localStorage.setItem("txnid", '<%= txnid %>');
			} else{
				$(".currTotalAmount").val(0);				
				$(".currAmountText").html(0);
				$(".currTaxAmountText").html(0);
				$(".currTotalAmountText").html(0);
				$(".kkTaxAmountText").html(0);
				$(".educessTaxAmountText").html(0);
				$(".checkoutButton").prop('disabled', true);
				localStorage.clear();
			}
		} else{
			$(".currTotalAmount").val(0);			
			$(".currAmountText").html(0);
			$(".currTaxAmountText").html(0);
			$(".currTotalAmountText").html(0);
			$(".kkTaxAmountText").html(0);
			$(".educessTaxAmountText").html(0);
			$(".checkoutButton").prop('disabled', true);
			localStorage.clear();
		}			
	}
	
	function submitPayUForm(){
		var payuForm = document.forms.payuForm;
	   	payuForm.submit();
	}
</script>
<style type="text/css">
    .box{
        
        display: none;
		}
	.table ,th,td{
		font-weight: 800;
		border:none !important;
		font-size: 10px;
		border-collapse: collapse;
	}
	.table>tbody>tr>td{
		padding: 7px 7px 3px 10px;
	}
	.tabltd
	{
		border-top: 1px solid #ccc !important;
	}
	table td + td{
	font-weight:800;
	font-size: 10px;
	}
	.column
	{
		background-color: #ededed;
		height: 424px;
		width: 100%;
	}
	.column p
	{
		font-size: 14px;
	}
	.column h3
	{
		padding-top: 32px;
		color: #55c2ac;
	}
	.column button
	{
		margin:61px 0px 3px 26px;
    	width: 80%;
    	background-color: #1ab394;
    	color:#fff;
	}
	.column1
	{
		background-color: #ededed;
		height: 150px;
		width: 100%;
		margin-top: 20px;
	}
	.column1 p
	{
		font-size: 14px;
	}
	.column1 h3
	{
		padding-top: 32px;
		color: #55c2ac;
	}
	.column1 button
	{
    	width: 100%;
    	background-color: #1ab394;
    	color:#fff;
	}
	.span
	{
		color: #1ab394;
	}
	.sp{
		color: #1ab394;
		font-weight: 700;
	}
	#check
	{
		width: 33%;
	    margin-top: -36px;
	    margin-right: 8px;
	    box-shadow: 2px solid;
	    outline: none;
	}
    </style>
 </head>
 <body>
  <div id="wrapper">
	<jsp:include page="../sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="../globalheader.jsp"></jsp:include>  
		<div class="wrapper wrapper-content animated fadeInRight" id="centerpane" style="background: #fff;"> 			
			<div class="row">
                <div class="col-lg-12">
					<div class="col-lg-9">
						<div class="col-lg-12 text-center">
							<div class ="navy-line" style="border:1px solid #1ab395;width: 6%;margin-left: 47%;">
							</div>
							<h1>UPGRADE PLAN</h1>
						</div>
						<%-- <div class="col-md-4">
							<div class="column text-center">
								<h3>Current Plan</h3>
								<c:choose>
									<c:when test="${myAccount.plan == null}">
										No Plan subscribed yet !										
									</c:when>
									<c:otherwise>
										<button class="btn btn-block"><c:out value="${myAccount.plan.planName}"/></button>
										<br>
										<div align="center">
											<p>								
												<img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${myAccount.plan.planPrice}"/> per Order
											</p>
											<p>
												MIN ORDER COUNT = <fmt:formatNumber type="number" maxFractionDigits="0" value="${myAccount.plan.orderCount}" />
											</p>
											<p>
												MIN AMOUNT = <span><img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${myAccount.plan.orderCount*myAccount.plan.planPrice}"/>/-</span>
											</p>
											
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div> --%>
						<c:forEach items="${upgrade}" var="up">
							<c:if test="${up.planName != myAccount.plan.planName && up.planName != 'Admin_Demo'}">
								<div class="col-md-4">
									<div class="column1 text-center">
										<button class="btn btn-block" onclick="select(this)" value="${up.pid}">${up.planName}</button>
										<br>
										<div align="center">
											<p>								
												<img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${up.planPrice}"/> per Order
											</p>									
											<p>
												ORDER COUNT =  <c:out value="${up.orderCount}"/>
											</p>
											<p>
												MIN AMOUNT =<span><img src="/O2R/seller/img/rupee.png" alt="rupee"> <fmt:formatNumber type="number" maxFractionDigits="0" value="${up.orderCount*up.planPrice}" />/-</span>
											</p>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${up.planName == myAccount.plan.planName}">
								<div class="col-md-4">
									<div class="column1 text-center">
										<button class="btn btn-block" onclick="select(this)" style="outline: none;background-color: darkslategrey" value="${up.pid}">${up.planName}</button>
										<br>
										<div align="center">
											<p>								
												<img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${up.planPrice}"/> per Order
											</p>									
											<p>
												ORDER COUNT =  <c:out value="${up.orderCount}"/>
											</p>
											<p>
												MIN AMOUNT =<span><img src="/O2R/seller/img/rupee.png" alt="rupee"> <fmt:formatNumber type="number" maxFractionDigits="0" value="${up.orderCount*up.planPrice}" />/-</span>
											</p>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${up.planName == 'Admin_Demo'}">
								<c:if test='<%= SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals("[ROLE_ADMIN, ROLE_MODERATOR]") %>'>
									<div class="col-md-4">
										<div class="column1 text-center">
											<button class="btn btn-block" onclick="select(this)" value="${up.pid}">${up.planName}</button>
											<br>
											<div align="center">
												<p>								
													<img src="/O2R/seller/img/rupee.png" alt="rupee"> <c:out value="${up.planPrice}"/> per Order
												</p>									
												<p>
													ORDER COUNT =  <c:out value="${up.orderCount}"/>
												</p>
												<p>
													MIN AMOUNT =<span><img src="/O2R/seller/img/rupee.png" alt="rupee"> <fmt:formatNumber type="number" maxFractionDigits="0" value="${up.orderCount*up.planPrice}" />/-</span>
												</p>
											</div>
										</div>
									</div>
								</c:if>								
							</c:if>
						</c:forEach>
					</div>
					<div class="col-lg-3">
						<div class="column text-center" style="margin-left: -21px;width: 111%;margin-top: -11px;">
							<form id="selectPlan" action="thankyou.html" method="post" >
							<input type="hidden" id='returntxnid' name="returntxnid" value="" />
							<input type="hidden" id='payusuccessful' name="payusuccessful" value="" />
							<table class="table"cellspacing="0"cellpadding="0" border="0">
								<tbody>
									<tr>
										<td>
											Desired Plan
										</td>
										<td>
											<select class="form-control selectPlanField" name="pid" onchange="updateFields(this)" style="font-size: 12px;color: #1ab394;">
												<option value="notSelected">
													Select Plan
												</option>
												<c:forEach items="${upgrade}" var="up">
													<option value='<c:out value="${up.pid}"/>' 
														data-planprice='<c:out value="${up.planPrice}"/>'
														data-ordercount='<c:out value="${up.orderCount}"/>'>
														<c:out value="${up.planName}"/>
													</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td>
											Desired Order	
										</td>
										<td>
											<input type="number" class="form-control currOrderCount" name="orderCount" oninput="updatePrices(this)" style="font-size: 12px;color: #1ab394;">
											<small>Minimum Order</small>
										</td>

									</tr>
									<tr>
										<td>
											Price/Order<br>
											<small>As Per Current Plan</small>
										</td>
										<td>
											<img src="/O2R/seller/img/rupee.png" alt="rupee"> <span class="currPlanPriceTxt"></span> per Order
										</td>
									</tr>
									<!-- <tr>
										<td>
											Order Count<br>											
										</td>
										<td>
											<input type="text" class="form-control " name="" style="width: 60%; float: left;" readonly="true">Orders
										</td>
									</tr> -->
									<tr style="border-top: 2px solid #ccc;">
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td style="text-align: right;">
											Amount : 
										</td>
										<td style="text-align: left;">
											&#8377; <span class="span currAmountText">0</span>/-
										</td>

									</tr>
									<tr>
										<td style="text-align: right;font-size: 10px;">
											Service Tax :
										</td>
										<td style="text-align: left;">
											&#8377; <span class="span currTaxAmountText">0</span>/-
										</td>
									</tr>
									<tr>
										<td style="text-align: right;font-size: 10px;">
											Krishi Kalyan Cess :
										</td>
										<td style="text-align: left;">
											&#8377; <span class="span kkTaxAmountText">0</span>/-
										</td>
									</tr>
									<tr>
										<td style="text-align: right;font-size: 10px;">
											Swachh bharat Cess :
										</td>
										<td style="text-align: left;">
											&#8377; <span class="span educessTaxAmountText">0</span>/-
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="tabltd">
											
										</td>
									</tr>
									<tr>
										<td style="text-align: right;">
											Total :
										</td>
										<td style="text-align: left;">
											<input type="hidden" class="currTotalAmount" name="totalAmount" >
											&#8377; <span class="span currTotalAmountText">0</span>/-
										</td>
									</tr>
								</tbody>
							</table>
							</form>
							
							<button type="button" disabled="true" id="check" class="btn btn-gray pull-right checkoutButton" 
								style="width: 50%;margin-top: -14px;margin-right: 8px;box-shadow: 2px solid;box-shadow: 4px 11px 10px #ccc;" onclick="submitPayUForm();">Checkout</button>
						</div>
						<div>
							<form action="<%= action1 %>" method="post" name="payuForm">
								<input type="hidden" name="key" value="<%= merchant_key %>" />
								<input type="hidden" name="hash" value="<%= hash %>"/>
							    <input type="hidden" name="txnid" value="<%= txnid %>" />
							    <input type="hidden" name="udf2" value="<%= txnid %>" />
								<input type="hidden" name="service_provider" value="payu_paisa" />
							    <input type="hidden" name="amount" class="payuAmount" value="<%= (empty(params.get("amount"))) ? "" : params.get("amount") %>" />
							    <input type="hidden" name="firstname" id="firstname" value="${myAccount.name}" />
							    <input type="hidden" name="email" id="email" value="${myAccount.email}" />
								<input type="hidden" name="phone" value="${myAccount.contactNo}" />
								<input type="hidden" name="productinfo" value="${myAccount.email}"/>
								<input type="hidden" name="surl" class="successUrl" value="${successurl}"/>
								<input type="hidden" name="furl" class="failureUrl" value="${failurl}" />
					         	<% if(empty(hash)){ %>
					            	<input type="submit" style="display:none;" value="Submit" />
					          	<% } %>
						    </form>
						</div>
					</div>	
                </div>
				<div class="col-lg-12 text-center">
					<p style="color: #000;position: relative;top: 6px;">
						<span class="sp">Plz note</span> the above mentioned charges are subjected to additional service tax @ 15% over and above the stated charges.No additional or hidden charges<br>
						<span class="sp">Order Count</span> is the number of orders purchased by the seller.The seller can choose to recharge his account with any amount equal to greater than the stated minimum amounts.<br>
						<span class="sp">Purchased Orders</span> have unlimited time validity.They can used as per the consumption levels without any fixed monthly.liability.<br>
						The seller will receive reminders to recharge his account when only 10% of purchased order count are left in the order bucket.
					</p>
				</div>
            </div>
		</div>
 		<jsp:include page="../globalfooter.jsp"></jsp:include>
    </div>
</div>
<jsp:include page="../globaljslinks.jsp"></jsp:include>
<script type="text/javascript">
	var hash='<%= hash %>';
	var payUParam = window.location.search.split("?");
	if(payUParam.length > 1){
		var payUSuccess = payUParam[1].split("=")[1];
		
			var totalAmount = localStorage.getItem("totalAmount");
			var orderCount = localStorage.getItem("orderCount");
			var pid = localStorage.getItem("pid");
			var txnid = localStorage.getItem("txnid");
			//alert("txnid : "+txnid);
			$(".currTotalAmount").val(totalAmount);
			$(".currOrderCount").val(orderCount);
			$(".selectPlanField").val(pid);
			$("#returntxnid").val(txnid);
			$("#payusuccessful").val(payUSuccess);
			$("#selectPlan").submit();
			localStorage.clear();
		
	} else{
		if (hash == '')
			console.log("Initial Call");
		else{
		   	var payuForm = document.forms.payuForm;
		   	payuForm.submit();
		}
	}
</script>
</html>