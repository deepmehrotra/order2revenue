<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
	function onclickQuaterlyButton() {
		document.getElementById("month").style.display = "none";
		document.getElementById("quarter").style.display = "block";
		document.getElementById("year").style.display = "none";
	}

	function onclickMonthlyButton() {
		document.getElementById("month").style.display = "block";
		document.getElementById("quarter").style.display = "none";
		document.getElementById("year").style.display = "none";
	}

	function onclickTodayButton() {
		document.getElementById("month").style.display = "none";
		document.getElementById("quarter").style.display = "none";
		$.ajax({
			url : 'viewDetails.html',
			success : function(data) {
				if ($(data).find('#j_username').length > 0) {
					alert('ok1');
					window.location.href = "viewDetails.html";
				} else {
					window.location.href = "viewDetails.html";
					$('#centerpane').html(data);
				}
			}
		});

	}

	function onclickAnnualyButton() {
		document.getElementById("month").style.display = "none";
		document.getElementById("quarter").style.display = "none";
		document.getElementById("year").style.display = "block";
	}

	function hidebtn() {
		document.getElementById("month").style.display = "none";
		document.getElementById("quarter").style.display = "none";
		document.getElementById("year").style.display = "none";
		document.getElementById("todayGraphButton").style.display = "none";

	}
</script>

</head>
<body onload="hidebtn()">

	<div class="col-sm-12">
		<div class="hr-line-dashed"></div>
		<h2>
			<small>Filtering records</small>
		</h2>

		<div class="ibox-title">
			<h5>Order V/s Payment</h5>
			<div class="pull-right">
				<div class="btn-group">
					<button type="button" class="btn btn-xs btn-white"
						id="todayGraphButton" onclick="onclickTodayButton()">Today</button>
					<button type="button" class="btn btn-xs btn-white active"
						id="quaterlyGraphButton" onclick="onclickQuaterlyButton()">Quaterly</button>
					<button type="button" class="btn btn-xs btn-white"
						id="monthlyGraphButton" onclick="onclickMonthlyButton()">Monthly</button>
					<button type="button" onclick="onclickAnnualyButton()"
						class="btn btn-xs btn-white" id="yearGraphButton">Annualy</button>
				</div>
			</div>
		</div>
		<div>
			<form id=month method="POST" action="monthindex.html">
				<label id="hello">Choose your Month</label> <select id="month"
					name="month">
					<option value="dropdown">Pls select one
					<option value="Jan">Jan
					<option value="Feb">Feb
					<option value="Mar">Mar
					<option value="Apr">Apr
					<option value="May">May
					<option value="Jun">Jun
					<option value="Jul">Jul
					<option value="Aug">Aug
					<option value="Sep">Sep
					<option value="Oct">Oct
					<option value="Nov">Nov
					<option value="Dec">Dec
				</select> <input type="submit" class="btn btn-primary block full-width m-b"
					value="Submit" onclick="onclickMSubmitButton()" />
			</form>
		</div>


		<div>
			<form id=quarter method="POST" action="quaterlyindex.html">
				<label id="hello">Choose your Quarter</label> <select id="Quarter"
					name="Quarter">
					<option value="dropdown">Pls select one
					<option value="First">First
					<option value="Second">Second
					<option value="Third">Third
					<option value="Fourth">Fourth
				</select> <input type="submit" class="btn btn-primary block full-width m-b"
					value="Submit" onclick="onclickQSubmitButton()" />

			</form>
		</div>
		<div>
			<form id=year method="POST" action="yearlyindex.html">
				<label id="hello">Choose your Quarter</label> <select id="year"
					name="year">
					<option value="dropdown">Pls select one
					<option value="2016">2016
					<option value="2015">2015
					<option value="2014">2014
					<option value="2013">2013
				</select> <input type="submit" class="btn btn-primary block full-width m-b"
					value="Submit" />
			</form>
		</div>


		<h2>
			<small>Select Headers</small>
		</h2>

		<table border="1" cellpading="5"
			class="table table-bordered custom-table">
			<thead>
				<tr>
					<th>SKU Code</th>
					<th>Gross Sale Qty</th>
					<th>Return Sale</th>
					<th>NetSale (G-R)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${productList}" var="viewDetailsmp1">
					<tr>
						<td><c:out value="${viewDetailsmp1.skucode}" /></td>
						<td><c:out value="${viewDetailsmp1.netSaleQty}" /></td>
						<td><c:out value="${viewDetailsmp1.returnSaleQty}" /></td>
						<td><c:out value="${viewDetailsmp1.grossSaleQty}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>
