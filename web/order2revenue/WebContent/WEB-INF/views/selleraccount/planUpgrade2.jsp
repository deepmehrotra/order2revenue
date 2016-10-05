<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>
  <script type="text/javascript"
    src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
 

<style type="text/css">
	.column
	{
		background-color: #f2f2f2;
		height: 400px;
		width: 100%;
		margin-top: 20px;
	}
	table td
    {
        border: 1px solid #fff;
        height: 40px;
        font-weight: 800;
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
					<div class="col-lg-6 text-center" style="margin-top: 50px;">						
						<c:choose>
							<c:when test='${status =="true"}'>
							<img src="/O2R/seller/img/success_transaction.jpg" alt="check" style="float: left;">
							<h1>Payment Successful</h1>
							</c:when>
							<c:otherwise>
							<img src="/O2R/seller/img/fail_transaction.jpg" alt="check" style="float: left;">
							<h1>Sorry! Your payment failed.</h1>
							</c:otherwise>
						</c:choose>
						
						<div>
							<table cellpadding="0" cellspacing="0" width="100%" style="position: relative;top: 23px;background-color: #f2f2f2;">
								<tbody>
									<tr>
										<td>
											Invoice ID:
										</td>
										<td>
											${accountTransaction.invoiceId}
										</td>
									</tr>
									<tr>
										<td>
											Status:
										</td>
										<td>
											<c:out value="${accountTransaction.status}"/>
										</td>
									</tr>
									<tr>
										<td>
											Transaction Details:
										</td>
										<td>
											<c:out value="${accountTransaction.transactionId}"/>
										</td>
									</tr>
									<tr>
										<td>
											Transaction Date:
										</td>
										<td>
											<fmt:formatDate type="date" value="${accountTransaction.transactionDate}"/>
										</td>
									</tr>
									<tr>
										<td>
											Total Orders :
										</td>
										<td>
											<c:out value="${accountTransaction.currentOrderCount}"/>
										</td>
									</tr>
									<tr>
										<td>
											Transaction Amount :
										</td>
										<td>
											&#8377; <c:out value="${accountTransaction.transactionAmount}"/> /-
										</td>
									</tr>
								</tbody>
								
							</table>
						</div>
						<br><br>
						<button class="btn btn-primary" style="background-color: #1ab394;color: #fff;margin-left: -2%;">Print Record</button>
					</div>
					<div class="col-lg-6">
						<div class="column text-center">
							<br>
							<img src="/O2R/seller/img/pdf.png" alt="pdf">
							<figcaption>Download Invoice</figcaption>
							<c:choose>
							<c:when test='${status =="true"}'>
							<h1>
							
								Thank You 
							</h1>
							<h3>
								For Your Purchase
							</h3>
							<p>
								O2R has received your payment.In case of any issues,you will be required to present <br>
								record of this transaction which can be downloaded by clicking on <br>
								"Print Record" Button.<br>
								For any other query feel free to contact us @ Customer Care.
							</p>
					</c:when>
					<c:otherwise>
					<h1>
							
								Sorry for the inconvenience!
							</h1>
							<h3>
								Contact admin and know status about your payment!
							</h3>
							</c:otherwise>
					</c:choose>
						</div>
					</div>
                </div>
            </div>
		</div>
 <jsp:include page="../globalfooter.jsp"></jsp:include>

    </div>
</div>

<jsp:include page="../globaljslinks.jsp"></jsp:include>
</html>