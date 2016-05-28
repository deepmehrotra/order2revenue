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
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="/O2R/seller/js/jquery-2.1.1.js"></script>
	<script src="/O2R/seller/js/bootstrap.min.js"></script>
</head>
	<body>
		<div class="col-lg-12">
        	<a href="home.html">
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
	          <form method="POST" action="saveSeller.html" id="registerForm">
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
	              		<input type="email" name="email" required autocomplete="off"/ placeholder="Email id">
	              	</div>
	            </div>
	            <div class="field-wrap">
	            	<div class="icon">
	            		<img src="/O2R/landing/img/icons/password.png" alt="user">
	            	</div>
	            	<div class="inputtext">
	            		<input type="password" name="password" required autocomplete="off"/ placeholder="Password">
	            	</div>
	          	</div>
	          	<div class="field-wrap">
	          		<div class="icon">
	          			<img src="/O2R/landing/img/icons/password.png" alt="user">
	          		</div>
	          		<div class="inputtext">
	           	 		<input type="password" required autocomplete="off"/ placeholder="Confirm Password">
	           	 	</div>
	          	</div>
	          	<div class="field-wrap" style="display:flex;">
	          		<div class="check">
	          			<input type="checkbox" style="width: 10%;height: 15px;margin-top: 9px;">
	          		</div> 
	          		<div class="accept">
	          			<p style="color: #a0b3b0;font-weight: 600;margin-top: 6px;">
	          			<a href="#">Accept terms & conditions</a></p>
	          		</div>
	          	</div>
	          </div>
	          <br>
	          <button type="submit" class="button button-block">Register</button> 
	          </form>
	        </div>
	        
	      </div>
	</div>
	<script type="text/javascript">
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
	</script>
	</body>
</html>