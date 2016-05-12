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
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function onclickviewExpCat(id) {
       $.ajax({
            url : 'viewExpenseGroup.html?expcategoryId='+id,
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
    function onclickAddTaxCategory() {
    	  $.ajax({
            url : 'addTaxCategory.html',
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
 
</script>
 </head>
 <body>
  <div id="wrapper">
<jsp:include page="../sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="../globalheader.jsp"></jsp:include>  
		<div class="wrapper wrapper-content animated fadeInRight" id="centerpane"> 
			<div class="row">
                <div class="col-lg-12 text-center">
					<div class ="navy-line" style="border:1px solid #1ab395;width: 6%;margin-left: 47%;">
					</div>
					<h1>Upgrade Plan </h1>
				</div>
            </div>
			<div class="row">
                <div class="col-lg-12">
					<div class="col-lg-6 text-center" style="margin-top: 50px;">
						<img src="/O2R/seller/img/check.png" alt="check" style="float: left; width: 20%;">
						<h1>Payment Successful</h1>
						<div>
							<table cellpadding="0" cellspacing="0" border="1" width="50%" style="position: relative;left: 86px;top: 23px;">
								<tbody>
									<tr>
										<td>
											Transaction Id :
										</td>
										<td>
											ALK1234
										</td>
									</tr>
									<tr>
										<td>
											Transaction Date :
										</td>
										<td>
											21/09/2015
										</td>
									</tr>
									<tr>
										<td>
											Total Orders :
										</td>
										<td>
											<c:out value="${currOrderCount}"/>
										</td>
									</tr>
									<tr>
										<td>
											Transaction Amount :
										</td>
										<td>
											&#8377; <c:out value="${currTotalAmount}"/> /-
										</td>
									</tr>
								</tbody>
								
							</table>
						</div>
						<br><br>
						<button class="btn btn-primary" style="background-color: #1ab394;color: #fff;margin-left: 16%;">Print Record</button>
					</div>
					<div class="col-lg-6">
						<div class="column text-center">
							<br>
							<img src="/O2R/seller/img/pdf.png" alt="pdf">
							<figcaption>Download Invoice</figcaption>
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