<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/O2R/seller/css/bootstrap.min.css" rel="stylesheet">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Registration O2R</title>
	<link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="/O2R/seller/css/registration.css">	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>	
	<script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.8.1/jquery.validate.min.js"></script>	
	
<script type="text/javascript">
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-82779501-1', 'auto');
  ga('send', 'pageview');

</script>

	
</head>
	<body>
		<div class="col-lg-12">
        	<a href="landing/home.html">
        	<img src="/O2R/landing/img/logoo2r.png" alt="logo" class="img-responsive" style="float:left;position:relative;left: 57px;top: -26px;width: 6%;">
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
	          	<c:if test="${error == true}">
	          		<font color="red" size="4" ><b>Invalid Email Id with Password !</b></font>
	          	</c:if>
	          
	            <div class="field-wrap">
	            	<div class="icon">
	              		<img src="/O2R/landing/img/icons/email.png" alt="user">
	              	</div>
	              	<div class="inputtext">
	              		<input type="email" name="j_username" id="j_username" required placeholder="Email id" style="height: 37px;"/>
	              	</div>	
	          	</div>
	          	<br><br>
	          <div class="field-wrap">
	            <div class="icon">
	            	<img src="/O2R/landing/img/icons/password.png" alt="user">
	            </div>
	            <div class="inputtext">
	            	<input type="password" name="j_password" id="j_password" required placeholder="Password" style="height: 37px;"/>
	            </div>
	          </div>
	          <br>
	          <p class="forgot"><a href="#" data-toggle="modal" data-target="#sendInd" style="text-decoration:none;"><font size="3" color="white" > Forgot password ?</font></a></p>
	          <button class="button button-block">Log In</button>
	          </form>
	        </div>
	        
	        <div class="modal inmodal fade" id="sendInd" tabindex="-1" role="dialog"  aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content animated bounceInTop" style="left: 29%;width: 45%;top: 79px;">
						<div class="modal-header" style="height: 76px;background: currentColor;">
							<h1 style="color:white;"><b>FORGOT PASSWORD</b></h1>
							<button type="button" class="close danger" data-dismiss="modal" style="opacity: 0.8;position: relative;top : -92px;right: -8px;"><span class="buton" aria-hidden="true" data-backdrop="false">&times;</span> </button>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group" style="width: 91%;margin-left: 15px; margin-top: -35px;">
										<form action="mail4get.html"  id="contactusform" method="POST" >
											<h3>Don't worry.We 've all been there.</h3>
											<p>Enter your email id,we will send the instruction on your mail to change your password.</p>
											<input type="email" placeholder="Enter Email id"  name="passwordMail" class="form-control" style="color: darkgreen;" required>
											<br>
											<input type="submit" value="Send Instruction" class=" btn btn-primary form-control" style="background: #1ab394;">
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	        
	        
	         
	        <div id="signup">   
	          <h1>Sign Up for Free</h1>
	          <form method="POST" action="saveSeller.html" id="registerForm" >
	          <div class="top-row">
	            <div class="field-wrap">
	            	<div class="icon1">
	            		<img src="/O2R/landing/img/icons/user.png" alt="user">
	            	</div>
	            	<div class="inputtext">
	              		<input type="text" name="name" required autocomplete="off" placeholder="User Name"/>
	              	</div>
	            </div>
	            <div class="field-wrap">
	            	<div class="icon1">
	              		<img src="/O2R/landing/img/icons/email.png" alt="user">
	              	</div>
	              	<div class="inputtext">
	              		<input type="email" id="email" name="email" required autocomplete="off"/ placeholder="Email id" onblur="onBlur()">
	              		<span id="emailMessage" style="color:red"></span>
	              	</div>
	            </div>
	            <div class="field-wrap">
	            	<div class="icon1">
	            		<img src="/O2R/landing/img/icons/password.png" alt="user">
	            	</div>
	            	<div class="inputtext">
	            		<input type="password" id="password" name="password" required autocomplete="off"/ placeholder="Password">
	            	</div>
	          	</div>
	          	<div class="field-wrap">
	          		<div class="icon1">
	          			<img src="/O2R/landing/img/icons/password.png" alt="user">
	          		</div>
	          		<div class="inputtext">
	           	 		<input type="password" name="ConfirmPassword" id="cpass" required autocomplete="off"/ placeholder="Confirm Password" onblur="Cpass();" />
	           	 		<span id="cpassMessage" style="color:red"></span>
	           	 	</div>
	          	</div>
	          	<div class="field-wrap" style="display:flex;">
	          		<div class="check">
	          			<input type="checkbox" name="terms" required autocomplete="off"/ style="width: 10%;height: 15px;margin-top: 7px; float: right;position: relative;left: 18px;">
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


var passCon=false;
var nameAvailability=false;
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
