
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*"%>
 <%Date date = new java.util.Date();
               pageContext.setAttribute("currentDate", date);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 
 <style type="text/css">
    body.DTTT_Print {
        background: #fff;

    }
    .DTTT_Print #page-wrapper {
        margin: 0;
        background:#fff;
    }

    button.DTTT_button, div.DTTT_button, a.DTTT_button {
        border: 1px solid #e7eaec;
        background: #fff;
        color: #676a6c;
        box-shadow: none;
        padding: 6px 8px;
    }
    button.DTTT_button:hover, div.DTTT_button:hover, a.DTTT_button:hover {
        border: 1px solid #d2d2d2;
        background: #fff;
        color: #676a6c;
        box-shadow: none;
        padding: 6px 8px;
    }

    .dataTables_filter label {
        margin-right: 5px;

    }
</style>
 
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>
  <!-- Data Tables -->
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet">
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet">

</head>
<body>
	
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
	
	
  	<div class="wrapper wrapper-content animated fadeInRight"
		id="centerpane">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title text-center">
						<h3 style="color: green;">Update Area Zipcode</h3>
					</div>
					<div class="ibox-content overflow-h">
						<div class="col-lg-12">
							<div class="col-sm-4 text-center">
								<label>Zipcode *</label>
								<input type="text" class="" name="zip" id="zip" onmouseout="checkZip();" style="width: 150px;">
								<label id="zipStatus"></label>								
							</div>
							<div class="col-sm-4 text-center">
								<label>Area *</label>
								<input type="text" class="" name="zip" id="area">
							</div>	
							<div class="col-sm-4 text-center">
								<label>City *</label>								
								<select class="btn btn-primary btn-xs" id="cityList" name="cityList" style="color: black; width: 60%;">
									<c:forEach items="${cityList}" var="city" varStatus="loop">
										<option value="${city}">${city}</option>
									</c:forEach>	
								</select>
							</div>													
						</div>
						<div class="col-lg-12 text-center" >
							<a><img src="/O2R/seller/img/database_add.png" onclick="addZipcode();"></a>							
						</div>
						<div class="col-lg-12 text-center" >							
							<label id="result"></label>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../globalfooter.jsp"></jsp:include>

	<jsp:include page="../globaljslinks.jsp"></jsp:include>

<!-- Scripts and Table -->



<!-- Data Tables -->
<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>

<script type="text/javascript">
	
	var checkedValue = null;
	var zipStatus = false;
	function checkZip(){
		var zip = $('#zip').val();
		document.getElementById("result").innerHTML = "";
		$.ajax({
			url : 'checkZipcode.html?zipcode='+zip,
			success : function(data) {
				if(data != 'true'){
					document.getElementById("zipStatus").style = "color: green !important;";
					document.getElementById("zipStatus").innerHTML = "Valid";
					zipStatus = true;
	        	}else{
	        		document.getElementById("zipStatus").style = "color: red !important;";
	        		document.getElementById("zipStatus").innerHTML = "Invalid";
	        		zipStatus = false;
	        	}
			}
		});	
	}	
	function addZipcode(){
		var zip = $('#zip').val();
		var city = $('#cityList').val();
		var area = $('#area').val();
		if(zipStatus != false){
			window.location = "addZipcode.html?zip="+zip+"&city="+city+"&area="+area;
		}
	}	
	
	$(document).ready(function() {
		
		var isSave = '${result}';
		if(isSave == 'true'){
			document.getElementById("result").style = "color: green !important;";
    		document.getElementById("result").innerHTML = "Zipcode Saved Successfully !";
		} else {
			document.getElementById("result").style = "color: red !important;";
    		document.getElementById("result").innerHTML = "Zipcode Must be Numeric & 6 Digit !";
		}
		$('.dataTables-example').dataTable({
			responsive : true,
			"dom" : 'T<"clear">lfrtip',
			"tableTools" : {
				"sSwfPath" : "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
			}
		});

		$('#searchProduct').change(function() {
			$('.ProductSearch-box').hide();
			$('#' + $(this).val()).fadeIn();
		});
		$('.search-dd').on('click', function(e) {
			e.stopPropagation();
			$('.search-more-wrp').slideToggle();
		});
		$('.input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,
			calendarWeeks : true,
			autoclose : true
		});

		$('#download').click(function() {
			$.ajax({
				url : 'downloadOrderDA.html?value=productSummary',
				success : function(data) {
					if($(data).find('#j_username').length > 0){
		        		window.location.href = "orderindex.html";
		        	}else{
		            	$('#centerpane').html(data);
		        	}
				}
			});
		});
		$('#upload').click(function() {
			$.ajax({
				url : 'uploadOrderDA.html?value=productSummary',
				success : function(data) {
					if($(data).find('#j_username').length > 0){
		        		window.location.href = "orderindex.html";
		        	}else{
		            	$('#centerpane').html(data);
		        	}
				},
				error : function(data) {
					alert(" Failing to get the data");
				}
			});
		});

		$('#LoadMoreProduct').click(function(e) {
			e.preventDefault();
			pageno = parseInt(getQueryVariable("page"));
			alert(" Page number in loadmore : " + pageno);
			pageno = pageno + 1;
			window.location = "Product.html?page=" + pageno;

		});
		$('#LoadFirst500').click(function(e) {
			window.location = "Product.html?page=" + 0;

		});
	});

	function getQueryVariable(variable) {
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i = 0; i < vars.length; i++) {
			var pair = vars[i].split("=");
			if (pair[0] == variable) {
				return pair[1];
			}
		}
		return 0;
	}
	function onclickAddProduct() {
		$.ajax({
			url : 'addProduct.html',
			success : function(data) {
				if($(data).find('#j_username').length > 0){
	        		window.location.href = "orderindex.html";
	        	}else{
	            	$('#centerpane').html(data);
	        	}
			}
		});
	}
	function onclickEditProduct(id) {
		$.ajax({
			url : 'editProduct.html?id=' + id,
			success : function(data) {
				if($(data).find('#j_username').length > 0){
	        		window.location.href = "orderindex.html";
	        	}else{
	            	$('#centerpane').html(data);
	        	}
			}
		});
	}

	function onclickNavigatePayment(value) {
		// window.open("updateInventory.html?id="+value, 'liveMatches', 'width=720,height=800,toolbar=0,location=0, directories=0, status=0,location=no,menubar=0');
		window
				.open(
						"updateInventory.html?id=" + value,
						null,
						"location=no,height=400,width=1200,top=100,left=50,status=yes,resizable=no,titlebar=no,toolbar=no,menubar=no,scrollbars=no,location=no");
	}
</script>
	</div>
</div>
 </body>
</html>
