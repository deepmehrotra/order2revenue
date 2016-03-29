<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
   <link rel="icon" href="O2R/landing/img/favicon.ico">
    <title>O2R-Sign In</title>
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

    <div class="col-lg-7 loginscreen animated fadeInDown center-align">
        <div>
            <div class="text-center">

                <img src="/O2R/seller/img/o2r-register-logo.jpg" alt="" class="login-logo"/>

            </div>
            <form method="post" action="<c:url value='j_spring_security_check'/>"
            class="m-t" role="form" action="index.html"  id="loginForm">
                 <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-6 b-r"><h3 class="m-t-none m-b">Sign in</h3>
                            <p></p>
                                <p>Sign in today for more experience.</p>
                             <c:choose>
                            <c:when test="${error == true}">
								<b class="error">Invalid login or password.</b>
									</c:when>
								<c:when test="${!empty param.registered }">
										<b class="error">Your account has been created. We will reach you soon with login details .</b>
                         		</c:when>
                         		</c:choose>
									</p>
                               
                                    <div class="form-group"><label>Email</label><input type="text" name="j_username" id="j_username" placeholder="Enter email" class="form-control"/></div>
                                    <div class="form-group"><label>Password</label> <input type="password" name="j_password" id="j_password" placeholder="Password" class="form-control"/></div>
                                    <div class="btn-forgot">
                                        <a href="">Forgot Password?</a>
                                        <label> <input type="checkbox" class="i-checks"/> Remember me </label>
                                    </div>
                                    <div class="mar-top-10">
                                        <input type="submit" class="btn btn-primary block full-width m-b" onclick="submitForm()" value="Log in"/>
                                    </div>
                               
                            </div>
                            <div class="col-sm-6"><h4>Not a member?</h4>
                                <p>You can create an account:</p>
                                <p class="text-center">
                                    <a href="register.html"><i class="fa fa-sign-in big-icon"></i></a>
                                </p>
                            </div>
                        </div>
                    </div>
            </form>
        </div>
    </div>

    <!-- Mainly scripts -->
    <script src="/O2R/seller/js/jquery-2.1.1.js"></script>
       <script src="/O2R/seller/js/jquery.validate.js"></script>
    <script src="/O2R/seller/js/bootstrap.min.js"></script>
<script>
function submitForm(){
 var validator = $("#loginForm").validate({
	  rules: {
		  j_username: {
		        required: true,
				 email:true,
		            },
		  j_password: {
		 required: true,
		            }   		            
		  },
     messages: {
    	 j_username: "Login Id  is required",
    	 j_password: "Please enter your password"
     }
 });
}
</script>
</body>

</html>