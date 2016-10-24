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

<style type="text/css">
.clear {
	clear: both;
}
#main-content {
	width: 540px;
	float: right;
}

#page-wrap {
	width: 1000px;
	margin: 0 auto;
	position: relative;
	/*background: rgba(0,0,0,0.7);*/
	/*overflow: hidden;*/
	padding: 30px 154px;
}

.inmodal .modal-header {
	text-align: center;
	background: #1ab394;
	padding: 3px 0px 15px 0px;
}

#contactusform h3, p {
	color: #222;
	font-weight: 600;
	font-style: cursive;
}

.buton {
	padding: 0px 6px 0px 6px;
	border: 2px solid #fff;
	border-radius: 50%;
	position: relative;
	top: -6px;
	background: magenta;
	left: 95%;
	color: #fff;
	z-index: 99999999999999999999999;
	position: absolute;
	cursor: pointer;
}

::-webkit-input-placeholder {
	color: #222 !important;
}

:-moz-placeholder {
	color: #222;
}

::-moz-placeholder {
	color: #222;
}

:-ms-input-placeholder {
	color: #222;
}

.underline {
	width: 100%;
	overflow: auto;
	color: #000;
}

.underline li, #contactusform p {
	text-align: justify;
	font-weight: 600;
}

.heading {
	color: #000;
}

.butonend {
	padding: 0px 6px 0px 6px;
	border: 2px solid #fff;
	border-radius: 50%;
	background: magenta;
	color: #fff;
	z-index: 99999999999999999999999;
	cursor: pointer;
}
.accept1
{
	width: 98%;
    position: relative;
    top: 10px;
}
</style>
</head>
	<body>
		<div class="col-lg-12">
        	<a href="landing/home.html">
        	<img src="/O2R/landing/img/logoo2r.png" alt="logo" class="img-responsive" style="float:left;position:relative;left: 57px;top: -26px;width: 6%;">
        	</a>
    	</div>
		<div class="form">
	      <ul class="tab-group">
	        <li class="tab active"><a href="#login" onclick="location.href='login-form.html#login'">Log In</a></li>
	        <li class="tab"><a href="#signup" onclick="location.href='login-form.html#signup'">Sign Up</a></li>
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
	          	<div class="field-wrap col-sm-12" style="display:flex;">	          		
	          		<div class="check col-sm-6">
	          			<input type="checkbox" name="terms" required autocomplete="off"/ style="width: 22%;height: 15px;margin-top: 7px; float: right;position: relative;left: 20px;">
	          		</div> 
	          		<div class="accept1 col-sm-6">
	          			<p style="color: #a0b3b0;font-weight: 600;margin-top: 6px;">
	          			<a href="#" data-toggle="modal" data-target="#myModal33">Accept terms & conditions</a></p>
	          		</div>
	          	</div>
	          </div>
	          <br>
	          <button type="submit" class="button button-block" onclick="return onSubmit();">Register</button> 
	          </form>
	        </div>
	        
	      </div>
	      <div class="modal inmodal fade" id="myModal33" tabindex="-1" role="dialog"  aria-hidden="true" style="width: 69%;left: 17%;margin: 0px 0px 31px 3px;">
				<div class="modal-dialog modal-lg" style="margin:0px;">
					<div class="modal-content animated bounceInTop" style="left: 1%;">
						<div class="modal-header">
							<h1>Terms & Conditions</h1>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
									<p>
										This Terms of Use document ("TOS") is a legal agreement between Client(1st USER or its further developed end users) and Bullfrog Business Solutions ("Bullfrog"),the owner of all intellectual property rights in the cloud-based business management platform titled "Bullfrog" ("Platform").The "TOS" describes the terms on which Bullfrog offers access to the Platform and the Services (defined below) to all end users.
									</p>
									<p>
									<b>
										(PLEASE READ THE TOS CAREFULLY.ACCEPTANCE OF THIS TOS AND USE OF THE PLATFORM SHALL ALSO IMLPIES YOUR ACCEPTANCE OF THE TOS,THE PRIVACY POLICY AVAILABLE AT <a href="http://www.order2revenue.com">www.Bullfrog.com </a>privacy-policy,AND THE OTHER WEBSITE POLICIES AS WELL AS YOUR AGREEMENT TO BE LEGALLY BOUND BY THE SAME.)
									</b>	
									</p>
									<p>
										This is a legally binding agreement and is enforceable against Client/User.Bullfrog may modify the terms of the TOS at any time and shall inform the Client/User of the same.It shall be deemed to have accepted the revised TOS if the CLIENT/USER continue to access the Platform after the modifications become effective.If Client/User do not agree to the terms of this TOS,please do not access or use the Platform.
										The following are the terms and conditions on which Bullfrog agrees to permit Client/User to access and use the Platform and the Services.
									</p>
									<h1 class="heading">
										1: Registration
									</h1>
									<ul class="underline">
										<li>
											In order to access and use the Platform, you will be required to register yourself and maintain a Bullfrog account ("Account") which will require you to furnish to Bullfrog,certain information and details, including Your name, e-mail id,and any other information deemed necessary by Bullfrog ("Account Information").You agree to keep this information updated at all times.
											
										</li>
										<li>
											You shall be responsible for maintaining the confidentiality and security of the password and for all activities that occur in and through Your Account. Bullfrog and its affiliates / partners are not liable for any harm caused by,or related to the theft of, Your ID, your disclosure of Your Account Information,or Your authorization to allow another person to access and use the Service using Your Account.However,you may be liable to Bullfrog and its affiliates / partners for the losses caused to them due to such unauthorized use.
										</li>
									</ul>
									<h1 class="heading">
										Account Dos and Don'ts:
									</h1>
									<ul class="underline">
										<li>
											You agree to abide by the following dos and don'ts for registering and maintaining the security of Your Account.
										</li>
										<li>
											You will create the Account on behalf of an entity or firm or in Your individual capacity only if You are a natural person aged 18 years or above and competent to enter into a valid and binding contract.
										</li>
										<li>

										You will not provide any false personal information to Bullfrog.
										</li>
										<li>You shall ensure that the Account Information is complete,accurate and up-to-date at all times.</li>
										<li>You shall not use any other user's Account Information or log into that user's Account.
										</li>
										<li>You will not share your password or do anything that might jeopardize the security of Your Account.</li>
										<li>On completing the registration process,you shall be entitled to access the Platform and avail of the Services.
										</li>
										<li>Your account,ID,and password may not be transferred or sold to another party.
										</li>
										<li>You agree to immediately notify Bullfrog of any unauthorized use of your account or any other breach of security known to you.
										</li>
										<li>In order to ensure that Bullfrog is able to provide high quality services,respond to customer needs, and comply with laws,you hereby consent to let Bullfrog's employees and agents access Your Account and records on a case-to-case basis to investigate complaints or other allegations or suspected abuse.Additionally,you agree to disclose to Bullfrog,and permit Bullfrog to use,your log-in ID,password and such other account details with respect to your account(s) with e-commerce websites/platforms,for the limited purpose of resolving technical problems.
										<li>You also grant Bullfrog the right to disclose to its affiliates / partners / third parties Your Account Information to the extent necessary for the purpose of rendering the Services.
										</li>
									</ul>
									<h1 class="heading">
										2: SCOPE OF SERVICES
									</h1>
									<ul class="underline">
										<li>
											Bullfrog hereby grants you a specific, non-exclusive, non-transferrable and limited license to access and use the Platform (which is public cloud hosted order and/or warehouse management application) via the internet, and avail of the services provided by the Platform with respect to warehouse management ("Services"). Bullfrog reserves the right to make changes to the functionality or the documentation of the Platform and the provision of Services from time to time.
										</li>
										<li>
											Bullfrog retains all rights in the Platform and the Services, and grants you only a right and license to use the Platform / application as stated herein. No other license is intended to be granted to you. Bullfrog reserves all rights in its name, trademarks,copyrights and any other intellectual property.
										</li>
										<li>
											You may use the Platform and the Service to store data, print and display data in the Service. No other use of the Platform and the Service by You shall be permitted.
										</li>
										<li>
											You may access the Platform and the Services using a single user Account via multiple access points. You may allow your employees, agents and independent contractors to access the Platform via Your Account on Your behalf. However, you agree not to provide any access to Your Account to any third party vendors.
										</li>
										<li>
											Bullfrog shall perform all necessary server management and maintenance services with respect to the Platform at no additional cost to you.
										</li>
										<li>
											Bullfrog does not guarantee availability of the Platform at all times. Bullfrog shall use reasonable efforts to make the Services available to you, at all times through the Platform. However, as the Services are provided over the Internet, data and cellular networks, the quality and availability of the same may be affected by factors outside Bullfrog’s control. Therefore, Bullfrog shall not be liable for non-availability of the Services at any time. Bullfrog may try and restore access to the Platform and the Services on a best reasonable and commercially viable basis.
										</li>
									</ul>
									<h1 class="heading">
										3: USE OF SERVICES
									</h1>
									<ul class="underline">
										<li>
											The Platform is a standard off-the-shelf application.We do not provide any customization in the platform.
										</li>
										<li>
											You agree to use the Services solely for the purpose for which the Services are provided,solely to aid your business.You shall not sublicense or resell the Platform or the Services for the use or benefit of any other organization,entity,business or enterprise.
										</li>
										<li>You agree not to submit or upload to the Platform, any material that is illegal, misleading, defamatory, indecent or obscene, threatening, infringing of any third party proprietary rights, invasive of personal privacy or otherwise objectionable (collectively, “Objectionable Matter”). Bullfrog reserves the right to adopt and amend rules for the permissible use of the Platform and the Services at any time, and you shall be required to comply with such rules. You shall also be required to comply with all applicable laws regarding privacy, data storage etc., or any other policy of Bullfrog, as updated from time to time.Bullfrog reserves the right to terminate this Agreement and Your access to the Platform, without notice,if you commit any breach of this clause.
										</li>
										<li>
											As part of the Services, the Platform allows you to upload data / content to it.All user data uploaded or submitted by you to Your Account,shall be your sole property. You retain all rights in the data uploaded by you to the Platform and shall remain liable for the legality,reliability,integrity, accuracy and copyright permissions thereto of such data.Bullfrog will use commercially reasonable security measures to protect the User’s data against unauthorized disclosure or use. However, Bullfrog does not guarantee data security.If your data is damaged or lost, Bullfrog will use commercially reasonable means to recover such data.You agree that you are entering into this agreement in full knowledge of the same.
										</li>
										<li>
											You shall not alter, resell or sublicense the Platform or the Services to any third party. You shall not reverse engineer the Platform or its software or other technology, circumvent, disable or otherwise interfere with security-related features or any digital rights management mechanisms of the Platform. You will not use the Platform or the Service to (i) build a competitive product or service,(ii) make or have a product with similar ideas, features, functions or graphics of the Platform,(iii) make derivative works based on the Platform / Services; or (iv) copy any features, functions or graphics of the Platform/Services.
										</li>
										<li>
											Bullfrog is an intermediary as defined under the Information Technology Act, 2000.Bullfrog does not monitor or control any data or content uploaded by you to the Platform.You agree not to use or encourage, or permit others to store,upload, modify,update or share any information that:
										</li>
										<li>
											belongs to another person and to which you do not have any right.
										</li>
										<li>
											is grossly harmful, misleading, harassing, blasphemous defamatory, indecent, obscene, pornographic, peadophilic, libelous, invasive of another’s privacy, hateful, or racially, ethnically objectionable, disparaging, relating or encouraging money laundering or gambling, invasive of personal privacy or otherwise objectionable or any data / content that is contrary to any applicable local, national, and international laws and regulations;
										</li>
										<li>
											infringes any patent, trademark, copyright or other proprietary rights;
										</li>
										<li>
											violates any law for the time being in force;
										</li>
										<li>
											results in impersonation of any person or entity, or falsely states or otherwise misrepresents your affiliation with a person or entity;
										</li>
										<li>
											is someone’s identification documents or sensitive financial information;
										</li>
										<li>
											contains software viruses or any other computer code, files or programs designed to interrupt, destroy or limit the functionality of any computer resource;
										</li>
										<li>
											threatens the unity, integrity, defense, security or sovereignty of India, friendly relations with foreign states, or public order or causes incitement to the commission of any cognizable offence or prevents investigation of any offence or is insulting any other nation; or
										</li>
										<li>
											Makes available any data / content in contravention of these TOS or applicable policies, or any data / content that you do not have a right to access, store, use or make available to third parties under any law or contractual or fiduciary relationship.
										</li>
										<li>
											Bullfrog reserves the right to suspend or terminate Your access to Your Account if You cause any disruption or harm to the Bullfrog infrastructure or to any third parties, or violate the provisions of the Information Technology Act, 2000, any applicable privacy laws or any of the applicable laws.You hereby consent to let Bullfrog’s employees and agents access Your Account and records on a case-to-case basis to investigate complaints or other allegations or suspected abuse.
										</li>
									</ul>
									<h1 class="heading">
										4: FEES
									</h1>
									<ul class="underline">
										<li>
											This is a paid version of the Platform. The Platform works on a prepaid or recharge model where you may choose from the paid or recharge options ("Fees") by going to the billing section of your Bullfrog account.
										</li>
										<li>
											The Fees shall be exclusive of all applicable taxes. You may choose to pay the Fees by any of the payment options made available by Bullfrog including, credit card, debit card, or net banking. There will be no deduction of TDS in case of use of any of the online methods for payments of Fees. If Bullfrog changes the Fees payable, Bullfrog shall give you advance notice of these changes via a message to the email address associated with Your Account.Bullfrog will bill you through your chosen payment method, from the date you opt for the paid Services option until termination. All payments are final and non-refundable. You will not be entitled to any cancellation or cooling off period after opting for the paid Services.
										</li>
										<li>
											All advance paid is non-refundable and initial upgrade amount paid will have validity of 30 days. If customer is unable to go live and start-processing orders in specified period, his account will be disabled and upgrade fees will be adjusted.
										</li>
										<li>
											Regular Training and support is provided over phone and email only.For Initial Training Phase,personal training can also be provided for a maximum of 4 sessions (maximum 4 hours per training session) if the seller is phasing any difficulties, by booing a training slot in advance with Bullfrog.
										</li>
										<li>
											The Services are provided to You via the internet and data and cellular networks, relevant internet charges and network or data charges, roaming charges, etc.,applicable for Your use of the internet and the data shall apply (over and above the Fees) while accessing the Platform and availing the Services.You accept responsibility for all such charges that may arise due to your use of the Platform and the Services.
										</li>
										<li>
											Bullfrog reserves the right to change the fees charged for any product type at any point of time without prior notice.
										</li>
									</ul>
									<h1 class="heading">
										5: INDEMNIFICATION
									</h1>
									<ul class="underline">
										<li>
											You shall indemnify and hold harmless,Bullfrog,its affiliates,any third party content / networks / infrastructure providers and their respective directors,officers,personnel,contractors and agents, for and against any and all claims,losses, damages, costs and expenses arising out of, or relating to, your use of the Platform and the Services or Your breach of the TOS or any other restrictions or guidelines provided by Bullfrog.This indemnification obligation will survive at all times,including,your use of the Platform and the Services.
										</li>
									</ul>
									<h1 class="heading">
										6: DISCLAIMER OF WARRANTEES
									</h1>
									<ul class="underline">
										<li>
											THE PLATFORM AND THE SERVICES ARE PROVIDED ON AN "AS-IS" AND "WITH ALL FAULTS AND RISKS" BASIS, WITHOUT WARRANTIES OF ANY KIND.BULLFROG DOES NOT WARRANT,EXPRESSLY OR BY IMPLICATION,THE ACCURACY OR RELIABILITY OF THE PLATFORM OR THE SERVICES OR ITS SUSTAINABILITY FOR A PARTICULAR PURPOSE OR THE SAFETY/SECURITY OF THE DATA/CONTENT STORED ON THE PLATFORM BY YOU.BULLFROG DISCLAIMS ALL WARRANTIES WHETHER EXPRESS OR IMPLIED,INCLUDING THOSE OF MERCHANTABILITY,FITNESS FOR A PARTICULAR PURPOSE, NON-INFRINGEMENT,OR THAT USE OF THE PLATFORM OR ANY MATERIAL THEREOF WILL BE UNINTERRUPTED OR ERROR-FREE.WITHOUT LIMITING THE GENERALITY OF THE FOREGOING,BULLFROG DOES NOT REPRESENT OR WARRANT THAT THE PLATFORM AND THE SERVICES WILL RESULT IN COMPLIANCE,FULFILLMENT OR CONFORMITY WITH THE LAWS, REGULATIONS,REQUIREMENTS OR GUIDELINES OF ANY GOVERNMENT OR GOVERNMENTAL AGENCY.
										</li>
										<li>
											Since BULLFROG is taking business finance related inputs from clients and as outputs providing information like Net Profits, Net taxable, actual and tax free sale. We need to communicate that since data is provided by the clients including commercial contract agreements and daily sale/return/payment information, we are merely a reflection of the data provided and hence should not be accountable of accuracy of data from any government’s perspective.
										</li>
										<li>
											We are deriving the possible TAX/TDS dues of the clients,we need to state that we are suggesting the same to the best of our knowledge based on the data provided by the client and the client is advised to consult to a professional CA for accuracy of the data.In no case we will be liable for any government related liabilities.Those remain with the client.
										</li>
										<li>
											In case any government authority/body comes to us, demanding any client’s data,Bullfrog shall first inform the client and then shall act in accordance with the law of the land in force at that time.In this situation,client’s prompt reply / directions shall also decide the further course of action.
										</li>
										<li>
											At any point of time if the CLIENT wants to discontinue using our services. No pending balance with O2R is refundable. But the CLIENT can procure his data from us in suitable formats on contacting us with due notice of 15 days.
										</li>
										<li>
											To the maximum extent permitted by applicable law, Bullfrog provides no warranty on the use of the Platform and the Services, and shall not be liable for the same under any laws applicable to intellectual property rights,libel,privacy, publicity, obscenity or other laws.Bullfrog also disclaims all liability with respect to the misuse, loss,modification or unavailability of the Platform and the Services.
										</li>
									</ul>
									<h1 class="heading">
										7: LIMITATION OF LIABILITY
									</h1>
									<ul class="underline">
										<li>
										YOU ASSUME THE ENTIRE RISK OF USING THE PLATFORM AND THE SERVICES. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, IN NO EVENT SHALL BULLFROG BE LIABLE TO YOU FOR ANY SPECIAL,INCIDENTAL,INDIRECT,PUNITIVE OR CONSEQUENTIAL DAMAGES WHATSOEVER (INCLUDING,WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS,BUSINESS INTERRUPTION,LOSS OF DATA OR INFORMATION,OR ANY OTHER PECUNIARY LOSS) ARISING OUT OF THE USE OF,OR INABILITY TO USE OR ACCESS THE PLATFORM OR THE SERVICES OR FOR ANY SECURITY BREACH OR ANY VIRUS,BUG,UNAUTHORIZED INTERVENTION,DEFECT,OR TECHNICAL MALFUNCTIONING OF THE PLATFORM,WHETHER OR NOT FORESEEABLE AND WHETHER OR NOT BULLFROG HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES,OR BASED ON ANY THEORY OF LIABILITY,INCLUDING BREACH OF CONTRACT OR WARRANTY,NEGLIGENCE OR OTHER TORTIOUS ACTION,OR ANY OTHER CLAIM ARISING OUT OF,OR IN CONNECTION WITH,YOUR USE OF,OR ACCESS TO,THE SOFTWARE OR THE SERVICES.FURTHER,BULLFROG SHALL NOT BE LIABLE TO YOU FOR ANY TEMPORARY DISABLEMENT,PERMANENT DISCONTINUANCE OF THE SERVICES BY BULLFROG,DATA LOSS OR FOR ANY CONSEQUENCES RESULTING FROM SUCH ACTIONS.
									</li>
									<li>
										BULLFROG’S AGGREGATE LIABILITY,IF ANY,(WHETHER UNDER CONTRACT,TORT INCLUDING NEGLIGENCE,WARRANTY OR OTHERWISE) AND THAT OF ITS AFFILIATES SHALL BE LIMITED TO THE TOTAL AMOUNT OF FEES RECEIVED FROM YOU FOR THE ONE (1) MONTH IMMEDIATELY PRECEDING THE DATE THE CLAIM WAS MADE.DAMAGES,IN THE NATURE,AND TO THE AMOUNT, PROVIDED IN THIS CLAUSE,IS THE ONLY RECOURSE THAT YOU MAY HAVE AGAINST BULLFROG FOR BREACH BY BULLFROG OF ANY OF ITS RIGHTS OR OBLIGATIONS HEREUNDER.
									</li>
									</ul>
									<h1 class="heading">
										8: NOTICE AND TAKEDOWN MECHANISM
									</h1>
									<ul class="underline">
										<li>
											The content uploaded by You may be available on the e-commerce websites / marketplaces in India or abroad, as chosen by You In such circumstances, the notice and takedown provisions applicable to the particular e-commerce websites / marketplaces will be applicable to the content uploaded by You. Content which is objectionable or infringing will be accordingly taken down. For all other times, the notice and takedown mechanism provided below will apply.
											If You are the owner of copyright in any content shared or uploaded on the Platform without Your consent, or You believe that any user of the Platform is storing, hosting,uploading or transmitting data / content,then You are required to send a written notice to the Grievance Officer at Bullfrog providing the following information/details:
										</li>
										<li>
											Description of the work with adequate information to identify the data / content;
										</li>
										<li>
											Details establishing that you are the owner or the exclusive licensee of copyright in the content;
										</li>
										<li>
											Details establishing that the copy of the content in question is an infringing copy of the content owned by You and that the allegedly infringing act is not considered as a "non-infringing" act under Section 52 of the Copyright Act and is not any other act that is permitted under the Copyright Act;
										</li>
										<li>
											Details of the location where the content in question is stored (i.e., the URL of the page where such content is stored);
										</li>
										<li>
											Details of the person,if known,who has uploaded the infringing copy of the content;and
										</li>
										<li>
											Undertaking that You shall file an infringement suit in the competent court against such person uploading the infringing copy and provide a copy of the orders so obtained to Bullfrog within twenty-one (21) days from the date of receipt of the notice by Bullfrog.
										</li>
										<li>
											You may also inform the Grievance Officer / Nodal Officer if any content that is in violation of the TOS.
										</li>
										<li>
											The details of the Grievance Officer and the Nodal Officer are as provided below:
										</li>
										<li>
											Name of the Grievance Officer: 
										</li>
										<li>
											Address of the Grievance Officer: 
										</li>
										<li>
											Email Address: 
											Contact Number: 
											Name of the Nodal Officer: 
											Address of the Nodal Officer: 
											Email Address: 
											Contact Number: 
										</li>
										<li>
											If You knowingly misrepresent that any material or activity is infringing,You may be subject to legal liability.Accordingly,if You are not sure whether material available online infringes your copyright, please contact a lawyer.
										</li>
									</ul>
									<h1 class="heading">
										9: SUSPENSION AND TERMINATION
									</h1>
									<ul class="underline">
										<li>
											The Services will remain in effect until suspended or terminated under this TOS.
										</li>
										<li>
											You may terminate Your registration by sending an email to contact@order2revenue.com, if You no longer wish to use the Platform. On termination, you will cease to have access to the Platform or any of the Services.
										</li>
										<li>
											Bullfrog reserves the right to suspend or terminate Your Account or restrict or prohibit You access to the Platform immediately (a) if Bullfrog is unable to verify or authenticate Your registration data, email address or other information provided by You, (b) if Bullfrog believes that Your actions may cause legal liability for You or for Bullfrog, or all or some of Bullfrog’s other users, or (c) if Bullfrog believes You have provided false or misleading registration data or other information, have not updated Your Account Information, have interfered with other users or the administration of the Services, or have violated this TOS or the Privacy Policy. You shall not be entitled to access the Platform or avail the Services if Your Account has been temporarily or indefinitely suspended or terminated by Bullfrog for any reason whatsoever. Bullfrog may, at any time, reinstate any suspended Account, without giving any reason for that. If You have been indefinitely suspended. You shall not register or attempt to register with Bullfrog or its affiliates / partners or use the Services in any manner whatsoever until such time that You are reinstated by Bullfrog.
										</li>
										<li>
											In addition to the above, if you are not able to go live and start processing orders within 30 days of up gradation date, Bullfrog shall be entitled to terminate Your Account irrespective of the balance amount available in Your Account. In such cases, Bullfrog shall not be liable to refund any Fees to You.
										</li>
										<li>
											In addition to the above,if You do not process any order through the Platform for a continuous period of (2) month,Bullfrog shall be entitled to terminate Your Account irrespective of the balance amount available in Your Account. In such cases,Bullfrog shall not be liable to refund any Fees to You.
										</li>
										<li>
											Upon termination of this TOS, your right to access the Platform and use the Services shall immediately cease. Thereafter, you shall have no right,and Bullfrog shall have no obligation thereafter,to execute any of the uncompleted tasks.
										</li>
										<li>
											Bullfrog does not have a refund policy and therefore,no refund of the Fees shall be provided under any circumstance.
										</li>
										<li>
											Once the Services are terminated or suspended, any data that You may have stored on the Platform, may not be retrieved later. Bullfrog shall be under no obligation to return the information or data to you.
										</li>
									</ul>
									<h1 class="heading">
										10: GOVERNING LAW
									</h1>
									<p class="underline">	
										This TOS is governed and construed in accordance with the laws of India.The courts in Delhi alone shall have exclusive jurisdiction to hear disputes arising out of the TOS without any reference to the conflict of law provisions.The Platform is made available to You by Bullfrog from its offices in India.You agree that: (i) the Services shall be deemed solely based in India; and (ii) the use of the Platform and the Services do not give rise to personal jurisdiction over Bullfrog,either specific or general,in jurisdictions other than India.You agree that the laws of India,excluding India’s choice of law rules,will apply to these TOS and to the provision of Services by Bullfrog and Your use of the same.Bullfrog makes no representation that the Platform and the Services are appropriate or available for use at other locations outside India. Access to the Platform from jurisdictions where the Services are illegal is prohibited.Bullfrog reserves the right to block access to the Platform by certain international users.If You access the Platform from a location outside India,You are responsible for compliance with all applicable local laws.
									</p>
									<h1 class="heading">
										11. FORCE MAJEURE
									</h1>
									<p class="underline">	
									Bullfrog shall be under no liability whatsoever in case of occurrence of a Force Majeure event,including in case of non-availability of any portion of the Platform and/or Services occasioned by act of God,war,disease,revolution, riot,civil commotion,strike,lockout,flood,fire,failure of any public utility, man-made disaster,infrastructure failure, technology outages,failure of technology integration of partners or any other cause whatsoever, beyond the control of Bullfrog.Further,in case of a force majeure event,Bullfrog shall not be liable for any breach of security or loss of data uploaded by You to the Platform.
									</p>
									<h1 class="heading">
										12: WAIVER
									</h1>
									<p class="underline">	
										Any failure by Bullfrog to enforce the TOS,for whatever reason,shall not necessarily be construed as a waiver of any right to do so at any time.
									</p>
									<h1 class="heading">
										13: SEVERABILITY
									</h1>
									<p class="underline">
										If any of the provisions of this TOS are deemed invalid, void,or for any reason unenforceable, that part of the TOS will be deemed severable and will not affect the validity and enforceability of any remaining provisions of the TOS.
									</p>
									<h1 class="heading">
										14: ENTIRE AGREEMENT
									</h1>
									<p class="underline">
										The TOS as amended from time to time,along with the Privacy Policy and other related policies made available from time to time, constitutes the entire agreement and supersedes all prior understandings between the parties relating to the subject matter herein.
										If You have questions or concerns about the TOS,please contact Bullfrog at
										<a href="mailto:contact@order2revenue.com">contact@order2revenue.com</a>
									</p>
								</div>
								<button type="button" class="close btn-danger danger" data-dismiss="modal" style="opacity: 0.8;position: relative;top: 35px;left: 23px;"><span class="butonend" aria-hidden="true" data-backdrop="false" style="left: 97%;">&times;</span> </button>
							</div>

						</div>
					</div>
				</div>
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
