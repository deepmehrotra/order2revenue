<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
   <link rel="icon" href="O2R/landing/img/favicon.ico">
    <title>O2R-Register New User</title>
    <link href="/O2R/seller/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/O2R/seller/font-awesome/css/font-awesome.css" rel="stylesheet"/>

    <link href="/O2R/seller/css/animate.css" rel="stylesheet"/>
    <link href="/O2R/seller/css/style.css" rel="stylesheet"/>
<style>
.error {
	color: red;
}
</style>
</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div>

                <img src="/O2R/seller/img/o2r-register-logo.jpg"/>

            </div>
             <form:form method="POST" action="saveSeller.html" class="m-t" role="form" id="registerForm">
            <div class="form-group">
                    <form:input path="name" class="form-control" placeholder="User Name" required="" />
                </div>
                <div class="form-group">
                    <form:input path="email" class="form-control" placeholder="Email Id" required="" onblur="checkOnBlur()"/>
                    <span id="emailMessage" style="color:red"></span>
                </div>
                <div class="form-group">
                    <form:password path="password" class="form-control" placeholder="Password" required=""/>
                </div>
                <div class="form-group">
                   <input type="password" name="ConfirmPassword" class="form-control" placeholder="Confirm Password" required=""/>
                </div>
                <div class="form-group">
                        <div class="checkbox i-checks"><label> <input type="checkbox" name="terms"/><i></i> Agree the terms and policy </label></div>
                </div>
                <button type="button" class="btn btn-success block full-width m-b" onclick="submitUser()">Register</button>

                <p class="text-muted text-center"><small>Already have an account?</small></p>
                <a class="btn btn-sm btn-white btn-block" href="login-form.html">Login</a>
            </form:form>
        </div>
    </div>
    <!-- Mainly scripts -->
    <script src="/O2R/seller/js/jquery-2.1.1.js"></script>
     <script src="/O2R/seller/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="/O2R/seller/js/bootstrap.min.js"></script>
<script>
var nameAvailability=true;

function checkOnBlur()
{
	var email=document.getElementById("email").value;
	
	$.ajax({
        url: "checkExistingUser.html?email="+email,
       success : function(res) {
    	 	   if(res=="false")
                	{
        	nameAvailability=false;
                	 $("#emailMessage").html("Email id already exist. Choose another");
                	}
                else
                	{
                	nameAvailability=true;
                	$("#emailMessage").html("Email id available");
                	}
      }
	 });
	}
function submitUser(){
	alert("Validation inside");
 var validator = $("#registerForm").validate({
	  rules: {
		  name: {
		        required: true,
		            },
		        email: {
		        required: true,
		        email:true,
		            }, 
		        password: {
				required: true,
				minlength : 5,
				},   
				ConfirmPassword: {
				required: true,
				minlength : 5,
				equalTo : "#password",
						    },
				terms:
					{
					required: true
					}
		  },
     messages: {
    	 name: "Name is required",
    	 email: "Email is required",
    	 password: "Please enter 6 to 12 digit password",
    	 ConfirmPassword: "Passwords are not matching"
     }
 });
 if(validator.form()&&nameAvailability)
 { // validation perform
	 alert(" Validation confirm");
		  $('form#registerForm').submit();
 }
}
</script>
</body>

</html>