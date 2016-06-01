<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Registration O2R</title>
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="/O2R/seller/css/registration.css">	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	<!-- <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script> -->
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.8.1/jquery.validate.min.js"></script>
	<script src="http://jquery.bassistance.de/validate/additional-methods.js"></script>
	
	
</head>
	<body>
		<div class="col-lg-12">
        	<a href="landing/home.html">
        	<img src="/O2R/landing/img/logo02r.png" alt="logo" class="img-responsive" style="float:right;margin:20px 80px 0px 0px;">
        	</a>
    	</div>
		<div class="form">
	      <ul class="tab-group">
	        <li class="tab active"><a href="#login">Log In</a></li>
	        <li class="tab"><a href="#signup">Sign Up</a></li>
	      </ul>
	      <div class="tab-content">
	      	<div id="login">   
	          <h1>Welcome Back!</h1>
	          
	          <form action="<c:url value='j_spring_security_check'/>"  class="m-t" role="form" action="index.html"  id="loginForm" method="post">
	            <div class="field-wrap">
	            	<div class="icon">
	              		<img src="/O2R/landing/img/icons/email.png" alt="user">
	              	</div>
	              	<div class="inputtext">
	              		<input type="email" name="j_username" id="j_username" required placeholder="Email id" />
	              	</div>	
	          	</div>
	          	<br><br>
	          <div class="field-wrap">
	            <div class="icon">
	            	<img src="/O2R/landing/img/icons/password.png" alt="user">
	            </div>
	            <div class="inputtext">
	            	<input type="password" name="j_password" id="j_password" required placeholder="Password" />
	            </div>
	          </div>
	          <br>
	          <p class="forgot"><a href="#">Forgot Password?</a></p>
	          <button class="button button-block">Log In</button>
	          </form>
	        </div> 
	        <div id="signup">   
	          <h1>Sign Up for Free</h1>
	          <form method="POST" action="saveSeller.html" id="registerForm" >
	          <div class="top-row">
	            <div class="field-wrap">
	            	<div class="icon">
	            		<img src="/O2R/landing/img/icons/user.png" alt="user">
	            	</div>
	            	<div class="inputtext">
	              		<input type="text" name="name" required autocomplete="off" placeholder="User Name"/>
	              	</div>
	            </div>
	            <div class="field-wrap">
	            	<div class="icon">
	              		<img src="/O2R/landing/img/icons/email.png" alt="user">
	              	</div>
	              	<div class="inputtext">
	              		<input type="email" id="email" name="email" required autocomplete="off"/ placeholder="Email id" onblur="onBlur()">
	              		<span id="emailMessage" style="color:red"></span>
	              	</div>
	            </div>
	            <div class="field-wrap">
	            	<div class="icon">
	            		<img src="/O2R/landing/img/icons/password.png" alt="user">
	            	</div>
	            	<div class="inputtext">
	            		<input type="password" id="password" name="password" required autocomplete="off"/ placeholder="Password">
	            	</div>
	          	</div>
	          	<div class="field-wrap">
	          		<div class="icon">
	          			<img src="/O2R/landing/img/icons/password.png" alt="user">
	          		</div>
	          		<div class="inputtext">
	           	 		<input type="password" name="ConfirmPassword" id="cpass" required autocomplete="off"/ placeholder="Confirm Password" onblur="Cpass();" />
	           	 		<span id="cpassMessage" style="color:red"></span>
	           	 	</div>
	          	</div>
	          	<div class="field-wrap" style="display:flex;">
	          		<div class="check">
	          			<input type="checkbox" name="terms" required autocomplete="off"/ style="width: 10%;height: 15px;margin-top: 9px; float: right;">
	          		</div> 
	          		<div class="accept">
	          			<p style="color: #a0b3b0;font-weight: 600;margin-top: 6px;">
	          			<a href="#">Accept terms & conditions</a></p>
	          		</div>
	          	</div>
	          </div>
	          <br>
	          <button type="submit" class="button button-block" onclick="return onSubmit();">Register</button> 
	          </form>
	        </div>
	        
	      </div>
	</div>
<script>

$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active highlight');
        } else {
          label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active highlight'); 
			} else {
		    label.removeClass('highlight');   
			}   
    } else if (e.type === 'focus') {
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }
});
$('.tab a').on('click', function (e) {
  e.preventDefault();
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  target = $(this).attr('href');
  $('.tab-content > div').not(target).hide();
  $(target).fadeIn(600);
});


var passCon=true;
var nameAvailability=true;
function onBlur(){
	
	var email=document.getElementById("email").value;
	$.ajax({		
	    url: "checkExistingUser.html?email="+email,
	   success : function(res) 
				{

					if (res == "false") {
						nameAvailability = false;
						$("#emailMessage").html(
								"Email id already exist. Choose another !").show();
					}else{
						nameAvailability = true;
						$("#emailMessage").html(
						"Email id already exist. Choose another !").hide();
					}
				}

			});
		}

function Cpass(){
	
	var password=document.getElementById("password").value;
	var confirm=document.getElementById("cpass").value;
		if (password != confirm) {
						passCon = false;
						$("#cpassMessage").html(
								"Password Not Matching !").show();
					}else{
						passCon = true;
						$("#cpassMessage").html(
						"Password Not Matching !").hide();
					}
	}	

function onSubmit()
{	
	if(nameAvailability && passCon)
	 {
		 	
			//$('#registerForm').submit();
			return true;
	 }else{
		 	
		 	return false;
	 }
	 
}


	</script>
	</body>
</html>
