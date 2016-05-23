<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="globalcsslinks.jsp"></jsp:include>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
<style type="text/css">
span .#error {
	color: red;
	font-weight: bold;
}

.pickListButtons {
	padding: 10px;
	text-align: center;
}

.pickListSelect {
	height: 200px !important;
}

.pickList1Select {
	height: 200px !important;
}
</style>
<script type="text/javascript">
	
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="globalheader.jsp"></jsp:include>
			<div class="col-sm-12">
				<div class="text-center animated fadeInDown col-sm-6"
					style="padding-top: 50px">
					<h1 style="color: #e10000; font-size: 150px; line-height: 120px">500</h1>
					<h3 style="color: #e10000; font-size: 30px;">Internal Server Error</h3>

					<div class="error-desc"	style="color: #18a689; font-size: 16px; margin-top: 30px;">
						<table>
							<tr>
								<td align="left">Error Message  </td><td align="left"><font color="red" style="Algerian">:${errorMessage}</font></td>								
							</tr>	
							<tr>
								<td align="left">Error Time  </td><td align="left"><font color="red" style="Algerian">:${errorTime}</font></td>							
							</tr>
							<tr>
								<td align="left">Error Code  </td><td align="left"><font color="red" style="Algerian">:${errorCode}</font></td>								
							</tr>
						</table>
					</div>
				</div>
				<div class="col-sm-6 text-center" style="padding-top: 100px">
					<img src="/O2R/landing/img/404.jpg" alt="">
				</div>
			</div>
			<jsp:include page="globalfooter.jsp"></jsp:include>
		</div>


	</div>

	<jsp:include page="globaljslinks.jsp"></jsp:include>
</body>
</html>