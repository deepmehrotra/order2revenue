<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
  <link rel="icon" href="img/favicon.ico">

  <title>Order to Revenue-Support</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
   <link href="/landing/css/bootstrap.min.css" rel="stylesheet">
   <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
   <link rel="stylesheet" type="text/css" href="/landing/css/support.css">
  <link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="/landing/js/bootstrap.min.js"></script>
  <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

  <style type="text/css">
  	.navbar-nav>li>a:hover
  	{
  		color: #009EEF  !important;
  	}
  	.questions
  	{
  		text-decoration: none !important;
  	}
  	.questions:hover
  	{
  		text-decoration: none !important;
  		color: #1ab394;
  	}
  	.anchor
  	{
  		text-decoration: none !important;
  		margin: 16px 0px 0px -18px;
  		background-color:#1ab394;
  		color:#fff;
  		font-weight:800;
  		width: 52%;
		font-size: 11px;
		height: 29px;
		  		
  	}

  </style>


  <script>
         $(function() {
            var availableWords = [
               "Amazon",
               "Basic Enventory Manager",
               "Cloud Based",
               "Contact Us",
               "Delivery Reports",
               "Enventory Forms",
            ];
            $( "#automplete-1" ).autocomplete({
               source: availableWords
            });
            $( "#automplete-2" ).autocomplete({
               source: availableWords
            });
         });
      </script>
      <script>
$(document).ready(function(){
	
	var url="#"+document.getElementById("set").value;
	window.location.href=url;
	
  $('body').scrollspy({target: ".navbar", offset: 50});   
  $("#myNavbar a").on('click', function(event) {
    event.preventDefault();
    var hash = this.hash;
    $('html, body').animate({
      scrollTop: $(hash).offset().top
    }, 800, function(){
      window.location.hash = hash;
  });
});
});
  
</script>
</head>
<body>
<input type="hidden" id="set" value="${setSection} ">
<nav class="navbar navbar-inverse navbar-fixed-top" style="background-color:#fff;">
  <div class="container-fluid">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>                        
      </button>
     	<a class="navbar-brand" href="/landing/home.html" style="margin-top: -15px;"><img src="/landing/img/bluelogo.png" class="img-responsive" alt="logo" width="75%"></a>
    	<a href="orderindex.html" class=" btn anchor">Dashboard</a>
    </div>
    <div>
      <div class="collapse navbar-collapse" id="myNavbar">
        <ul class="nav navbar-nav" style="float: right;">
          <li><a href="#section1">Common Terms</a></li>
          <li><a href="#section2">FAQ'S</a></li>
          <li><a href="#section3">Guidelines</a></li>
          <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Training Center<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="#section41">Initial Setup</a></li>
              <li><a href="#section42">Daily Activities</a></li>
              <li><a href="#section43">Reports</a></li>
              <li><a href="#section44">Miscellaneous</a></li>
              <li><a href="#section45">Others</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>
</nav>    

<div id="section1" class="container-fluid">
    <h1 class="heading1">Common Terms</h1>
    <hr class="headingline">

  <div class="row">
    <div class="col-lg-12">
      <div class="col-lg-3">
        <h3 class="heading2">Order Related</h3>
        <div class="col-md-12">
            <div class="column">
              <p>
              <a href="#" title="Are orders from Sales Channels that work on Marketplace/Dropship/Oneship Models. In this model the sellers are required to keep the goods with themselves and ship directly to end customers as and when an order arrives">
              MP Orders</a>
              </p>
              <p>
              <a href="#" title="Are orders from Sales Channels that work on PO Model. In this model the sellers are required to keep the goods with themselves and ship to the Channel when an order arrives.Then the channel takes the responsibility of shipping to the end customer.">
              PO Orders</a>
              </p>
               <p> <a href="#" title="is the unique reference ID associated with an Order or Purchase Order. Mostly Order ID is used to identify or search for an Order. 
                Plz Note that it may or may not be unique depending on the sales channel. For example Snapdeal’s unique order attribute is “Sub-Order ID”, Flipkart’s unique attribute is “Flipkart unique item ID” and Paytm’s unique attribute is ”Paytm unique item ID”. This does not mean that Snapdeal, Flipkart and Paytm does not have Order ID’s, they do but the same are not unique. Hence during order input the sellers are required to use the following 
                • For Snapdeal: Sub-order ID in place of Order ID
                • For Flipkart: Item ID in place of Order ID
                • For Paytm: Item ID in place of Order ID
                ">Order ID/PO ID</a></p>
                <p><a href="#" title="is the return reference ID associated with Sale Returns of MP Orders and is mostly provided by the Sales Channel. In case the same is not provided. The seller can input their own desired unique value to further identify the Order.">Return RTO/ID</a></p>
                <p><a href="#" title="is the unique reference ID associated with Sale Returns of PO Channels such as Myntra and jabong.">Gate pass Id</a></p>
                <p><a href="#" title="is Snapdeal’s Unique Order Attribute for identifying an Order."></a>Sub Order ID </p>
                <p><a href="#" title="is Unique Order Attribute for identifying an Order for Flipkart and Paytm"></a>Unique Item ID </p>
                <p><a href="#" title="is Snapdeal’s another reference Order Attribute for identifying an Order.">PI Reference No </a></p>
                <p><a href="#" title="Invoice ID is the unique ID provided by the seller against every Sale, Transaction or Order."></a>Invoice ID </p>
                <p><a href="#" title="is the Airway Bill No of a shipment and is used to track the status of a shipment. The same may or may not be unique depending on Sales Channel."></a>AWB </p>
                <p><a href="#" title="">AWB</p> 
               
               <p>PI Reference Number</p> 
               <p><a href="#" title="">Flipkart Unique Item ID</p> 
                
               <p><a href="#" title="or Shipping Partner is the carrier which is being used to transport or deliver the shipment. Generally, every sales channel uses multiple Logistic Partners to deliver its shipments.">Logistic Partner</a></p> 
               <p><a href="#" title=":is the date on which an order is shipped by the seller.">Order Shipped Dtae</a></p> 
               <p><a href="#" title="is the date on which an order was received by the seller.">Order Received Dtae</a></p> 
               <p><a href="#" title="is the date on which an order is expected to be delivered to the customer.">Estimated Delivery Dtae</a></p> 
               <p><a href="#" title="">Additional Shipping Charges</p> 
               <p><a href="#" title="">Customer Discount</p> 
               <p><a href="#" title="">EOSS Discount</p> 
               <p><a href="#" title="is the date on which a Return/RTO order is received by the seller irrespective of the date on which the order was picked from the customer">Return/Rto Date</a></p> 
               <p><a href="#" title="">Return Charges</a></p> 
               <p><a href="#" title="is the quantity of an order that has been returned to the seller for any reason.">Return Qty</a></p> 
               <p><a href="#" title="">Return Reason</a></p> 
               <p><a href="#" title="">Return/RTO Acceptance Period</a></p>
               <p><a href="#" title="is the amount that the seller is due to be paid, for selling a product via a sales channel, irrespective of the quantity of the order. In other words net N/R is 
                Gross N/R * Qty.">Net N/R(Net Receivable)</a></p>
            </div>
        </div>
      </div>
      <div class="col-lg-3">
        <h3 class="heading">Payment Related</h3>
        <div class="column">
          <p><a href="#" title="is a categorized, Sales Channel agreed, timelines that will be used to record and reconcile transactions between a specific period. For example, the transaction that will happen between 1st Apr 2015 – 30th April 2015 will be paid for on 15th May 2015.">Payment Cycle</a></p>
          <p><a href="#" title="is the date on which the payment of an order is expected to physically arrive at the seller.">Estimated Date of Payment</a></p> 
          <p><a href="#" title="is the date on which the payment of an order has physically arrived at the Seller.">Actual Date of Payment</a></p> 
          <p><a href="#" title="describes how a customer has paid for an Order. Typically, there are 2 kinds of Payment Type- Prepaid or COD. Prepaid is when a customer has made the payment and the product is shipped post the payment has been received by the Sales Channel. Whereas in COD Orders, the customer pays for the product at the time of delivery of the product.">Payment Type</a></p> 
          <p><a href="#" title="">Payment Differnce Amount</a></p> 
          <p><a href="#" title="">A/R,A/R1,A/R2</a></p> 
          <p><a href="#" title="">Net Payment Received</a></p> 
          <p><a href="#" title="">Gross P/R</a></p> 
          <p><a href="#" title="">NET P/R</a></p> 
          <p><a href="#" title="is the maximum retail price on which a product can be sold.">MRP</a></p> 
          <p><a href="#" title="is the selling price of a product. This is the amount that a customer has paid to purchase the product from the Sales Channel. The SP of a product may or may not be equal to MRP. In case the seller has provided the customer with a discount, SP and MRP would differ. Plz Note that SP of a product includes the following
          a.  Additional Shipping Charges taken from Customers
          b.  Giftwrap Charges
          c.  Installation Charges
          d.  Any Other Charges taken from customer at the point of sale
          Plz Note in any case the seller should always input total SP of an order which is included of the above stated charges.
          ">SP</a></p> 
          <p><a href="#" title="is the amount that the seller is due to be paid, for selling 1 quantity of a product via any sales channel.">GROSS N/R</a></p> 
          <p><a href="#" title="">NET N/R</a></p> 
          <p><a href="#" title=": is the final quantity of an order that been sold. It might be “0” in certain cases for example, if an order consisted of, Gross Qty = 1, and the same was returned to the seller, the Net Sale Qty will be = 0.">Net Sale Qty</a></p> 
          <p><a href="#" title="is the quantity of a product that has been sold in an order. This may or may not be same as “Net Sale Qty”. In case a sold order is returned to the seller for any reason, the Net Sale Qty will differ from Gross Sale Qty. ">Gross Sale Qty</a></p> 
          <p><a href="#" title=": is the net sales of the business on which tax liability stands due. For MP Channels, the sales channels only show themselves as an intermediary hence the liability of depositing sales tax on Selling price of the product stays with the Seller.
          For example if a product is sold to customer for Rs 500/-, and the seller is receiving only Rs 250 /- from the sales channel after deducting commission. then taxable sale would be Rs 500/-
          ">Net Taxable Sale </a></p> 
          <p><a href="#" title="is the actual sales of the business. These are the figures that should ideally be received by the seller against sales of his/her products via any sales channels. 
          For example, if a product is sold to customer for INR 500/-, and the seller is receiving only INR 250 /- from the sales channel after deducting commission. then actual sale would be INR 250/-. 
          ">Net Actual Sale</a></p>
          <p><a href="#" title="is the total sale value of the business in the concerned time period. Gross Sale is taken into account on the basis of SP’s for sales channels who have TDS as applicable, and on the basis of PO Price for sales channels without having TDS as applicable. TDS applicable settings can be structured during Sales Channel configuration. Plz note Sale Return is not deducted from the Sale Return.">Gross Sale</a></p> 
           <p><a href="#" title="is the total net sale value of the business in the concerned time period. Net Sale is the taxable sale value of the business after deducting Sale Return Values from the Gross Sale Values. Plz note that tax to be deposited by the seller is calculated on the basis of Net Sale."></a>Net sale</p>
           <p><a href="#" title="is a sale order which has been returned by the customer with or without any reason. There are 2 kinds of Sale Return – “Return” and “RTO”. Return is an order which has been returned by the customer after the product was delivered to the customer and the customer was for some reason did not want the product. Whereas RTO is an order which was returned as it could not reach the customer for any reason like pin-code not available, customer not present, fake order etc.">Net Return</a></p>
           <p><a href="#" title="is the total representation of net profits from the sale of products during the concerned period."> Income</a></p>
           <p><a href="#" title="">Net Income</a></p>
          <p><a href="#" title="">Net Income</a></p>  
          <p><a href="#" title="">Gross Profit </a></p>  
          <p><a href="#" title=":is the price that the seller will be paid by the Sales Channel against any transaction. TP may or may not be equal to Gross or Net N/R of an order depending on the conditions. For TP Channels the N/R Switch needs to be turned off. And the seller is required to input TP value of the order during order input">Transfer Price(TP) </a></p>
          <p><a href="#" title="is the amount that the seller is due to be paid, for selling a product via a sales channel, irrespective of the quantity of the order. In other words net N/R is 
Gross N/R * Qty. 
">Net N/R(Net Receivable) </a></p>
          <p><a href="#" title="is the direct commission paid to Sales Channel for selling a product. Plz note that sales channels always charge commissions on total SP">Gross Selling Commission </a></p>
          <p><a href="#" title="is paid to Sales Channels for selling a product is directly dependent on selling price of a product. Plz note that sales channels always charge commissions on total SP.">Fixed Fee</a></p>
          <p><a href="#" title="">Net Profit</a></p> 

        </div>
      </div>
      <div class="col-lg-3">
        <h3 class="heading">Product Related</h3>
         <div class="subcolumn">
          <p><a href="#" title=": is created to categorize products and generate informative category wise reports to assess and compare the performance of each product category.">Product Category</a></a></p> 
            <p><a href="#" title="is created to further sub categorize products and generate informative group wise reports to assess and compare the performance of each inventory group within a Product Category.">Inventory Group</a></a></p> 
            <p><a href="#" title="">SKU </a></p>
            <p><a href="#" title="is the direct landed cost of a product to the seller. Generally, this is the amount which the seller has invested to buy or manufacture a product.">Cost Of Product </a></p>
            <p><a href="#" title="">Opening Stock Valuation </a></p>
            <p><a href="#" title="are taken from customer for collecting payments on seller’s behalf.  ">Payment Collection Charges(PCC) </a></p>
             <p><a href="#" title="">Closing Stock Valuation </a></p>
              <p><a href="#" title="">Inventory Thresold Limit</a></p>
         </div>
         <h3 class="heading">Channel Related</h3>
          <div class="subcolumn">
            <p><a href="#" title="or Selling Partners are the online Webstores on which a seller is selling their Products. For example, Snapdeal, Amazon, Flipkart are all Sales Channels">Sales Channel</a></p>
            <p><a >Gross Partner Commission Paid</a></p>
            <p><a href="#" title="">Partner Commission Reversed</a></p>
            <p><a href="#" title="">Channel Mapping names</a></p>
         </div>
      </div>
      <div class="col-lg-3">
        <h3 class="heading">General</h3>
          <div class="subcolumn">
          <p><a href="#" title="">Product Category</a></p> 
            <p><a href="#" title="">Inventory Group</a></p> 
            <p><a href="#" title="is a unique Product that the seller has to offer. SKU is unique product ID created by the seller to identify their Offerings.">SKU </a></p>
            <p><a>Cost Of Product </a></p>
            <p><a href="#" title="">Opening Stock Valuation </a></p>
            <p><a href="#" title="">Closing Stock Valuation </a></p>
            <p><a href="#" title="">Closing Stock Valuation </a></p>
            <p><a href="#" title="">Inventory Thresold Limit</a></p>
         </div>
         <h3 class="heading">TAX/TDS Related</h3>
         <div class="subcolumn">
          <p><a href="#" title="">Tax Category</a></p> 
            <p><a href="#" title="">Tax amount to be deposited</a></p> 
            <p><a href="#" title="">Tax amount Reversed </a></p>
            <p><a href="#" title="">Net Partner Commission Paid </a></p>
            <p><a href="#" title="">TDS To Be Deposited</a></p>
            <p><a href="#" title="">Net Partner Commission Paid </a></p>
            <p><a href="#" title="">TDS Reversed</a></p> 
         </div>
      </div>     
    </div>
  </div>
</div>
<div id="section2" class="container-fluid" style="background-image: url(/landing/img/background_faq.png);">
   <div class="col-lg-9">
    <h1 class="heading1">FAQ'S</h1>
  	<hr class="headingline">
  	<br>
   	<br>
  	<div class="panel-body">
		<div class="panel-group" id="accordion">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne1" class="questions">
						Order Related
					</a> 
					</h5>
				</div>
				<div id="collapseOne1" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="panel-group" id="accordion1">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-a" class="questions">
										How do I upload Orders?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-a" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                Orders can be uploaded manually or in bulk.<br>
												Manual Order Upload <br>
												1.	Visit “Orders” in ”Daily Activites” Icon on the tool bar. <br>
												2.	Click ”Create Order”. <br>
												3.	Input Details, Click ”Save” button.<br>
												Bulk Order upload<br>
												1.	Visit ”Daily Activites” Icon on the tool bar.<br>
												2.	Click “Bulk Upload Orders” button.<br>
												3.	Download upload format by selecting Order Summary from the dropdown and clicking on download.<br>
												4.	Input details in the excel sheet. Upload the sheet by clicking on Upload Button.<br>
							                </a></p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-b" class="questions">
										How do I upload Purchase Orders?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-b" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Purchase Orders can be uploaded only by using bulk upload method via PO Orders Summary. The process remains the same as in normal bulk upload. Just mention the same PO ID against every SKU.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-c" class="questions">
										How do I search/lookup/check/edit an Order information?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-c" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                	Order Information can be pulled from either of the 2 below stated ways.<br>
												1.	By using Universal Search located at the top of the screen. Simply select and input any property of an order (including Order ID, PO ID, Invoice ID, AWB, PI reference no, Sub-Order ID, Flipkart Unique item ID, Customer Name, SKU) in the search bar situated at the center top of the screen and click on search. <br>
												2.	By visiting Daily Activities Page, click on “Orders” tab. Input any property of the order in the search bar and the order information will be pulled up.<br>
												<b>Plz Note</b> An order cannot be edited at any point of time. In case of errors the seller can manually close that very order. And re-upload the order with different details.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-d" class="questions">
										What is expected delivery date?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-d" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Expected Delivery date is the date on which the order is expected to be delivered to the customer. Plz note that this is only an estimation and not the actual delivery date. The seller is required to provide an estimated delivery time against every state. The same is helpful for accounting purposes for sales channels, who make payment on the basis on delivery of an order. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-e" class="questions">
										Should the additional shipping charges charged from customers be inserted separately over the SP(Selling Price)?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-e" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	No. Plz note that any additional charges including shipping, Giftwrap or any other charged from the customers, over and above the selling price of a product needs to inserted along with SP. That is the seller is required to always state the Total SP of an order. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-f" class="questions">
										Is the N/R inserted against an order calculated considering additional shipping charges or not?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-f" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Yes. Plz Note that the Sales Channels always calculate commissions on total SP of an order.	
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-g" class="questions">
										What is the difference between Customer Discount and EOSS discount?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-g" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Customer Discount is the discount offered by the seller to the customer against the MRP of the product, the SP of an order is derived after deducting customer discount value from the MRP of a product. 
												Whereas EOSS discount is charged only in cases of Myntra. If a seller is selling at Myntra and is offering any customer discounts, at the end of the month during reconciliation, he is deducted with EOSS Discount Value from his payment. How Myntra handles it is, even if a seller is selling with customer discounts, Myntra still raises its PO on the MRP itself and then later deducts the difference in the amount from the payments by terming the same as EOSS discount. <br>
												For example, A seller is selling product X @ Myntra @ 50% commission with the following details-<br>
												MRP= Rs 200, SP= Rs 100, Hence Customer Discount = Rs 100 or 50%. <br>
												Whereas in the same example, <br>
												Myntra would raise the PO at Rs 100. But as per the agreed terms, actually seller is supposed to receive only Rs 50. Myntra would adjust this difference in amount at the end of the month by terming the same as EOSS Discount.

							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-h" class="questions">
										At what stage should an order be punched into O2R?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-h" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	On daily basis whatever orders are shipped by the seller during the entire day, should be inserted into O2R. hence their status should be shipped. The order can also be punched in before shipping. But such cases leave the scope of an order actually not being shipped. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-i" class="questions">
										Where can I view or download my shipped/disputed/actionable orders data for a selective period?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-i" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Shipped Orders data can be downloaded/viewed by visiting “Reports-> Order Related Report”.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-j" class="questions">
										Where can I view or download my paid and unpaid orders? Difference between Actionable and Settled Orders?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-j" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	In simple terms the seller can identify between paid and unpaid orders by checking its status. If the status of an order is “Settled” that means full payment has been received against that order and no further action is required, whereas if the status of and order is “Actionable” that means some or full payment against that order is still to be received and hence needs seller attention.
												These orders can be viewed or downloaded from the “Reports->Order Related Reports” section.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-k" class="questions">
										What do I need to do when a disputed order has been accepted and paid for by the sales channel?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-k" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	In such a case, no special action is required from the Seller. Seller just need to perform their daily activities as described and O2R will do the job for the seller. At any point of time O2R will update and reflect the status of an order with its disputed amount.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-l" class="questions">
										How do I know what orders need attention? What are Actionable Orders? Where to find them?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-l" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	At any point of time when the seller wants to view/know/download order details that require attention. The seller needs to visit reports and download Actionable Orders data. If an order has been paid for incorrectly or is still unpaid, the same will be downloaded using this report. Plz note O2R never suppresses any orders until any action from the seller. For example if an order was paid incorrectly in January 2015, and the seller is checking actionable orders in the month of December 2015, the data will still show the order from January 2015. But if an order has an amount that is to be deducted from the seller. Post 90 days such orders will be settled. In case any further transactions against such orders occur. O2R will reopen such orders and treat them accordingly.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-m" class="questions">
										Do I have to change the status of an order manually (marking order as delivered or paid)?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-m" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	No manual status change is not required from the seller. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-n" class="questions">
										What is order Timeline? Understanding Order Timeline?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-n" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Order timeline describes the life of an order. It can be used to understand what all transactions have happened with an order during its lifetime.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-o" class="questions">
										What to do if I have uploaded a wrong order?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-o" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	In case the seller has identified a mistake or mutually settled an order with the sales channel on whatever agreed terms. In such instances the seller can visit “Manual” tab in the toolbar to manually close an order.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-p" class="questions">
										Is it possible to edit an order?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-p" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	No it is not possible to edit an order. Contact O2R to provide last days backup in such scenarios or manually close an order and recreate with appropriate details.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-q" class="questions">
										What if I punch in wrong details against an order?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-q" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller should contact O2R to provide last days’ backup in such scenarios or manually close an order and recreate with appropriate details.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-r" class="questions">
										How to delete an order?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-r" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Deleting an order is not an option for seller. The seller should contact O2R to provide last days’ backup in such scenarios or manually close an order and recreate with appropriate details.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-s" class="questions">
										Why did my order fail to upload? Where can I see failed results?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-s" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	There could be several reason for this to happen. Download Failed Report available in Progress bar on the top right corner of the screen. Follow the report to know the errors. Reupload with correct details and the same will be uploaded.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-t" class="questions">
										Shipping Incentives and disincentives?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-t" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 Shipping incentives and disincentives are to be added manually via manual charges tab. Incentives should be fed with “–“(minus) Negative value/sign/symbol. And Disincentives to be punched with as positive values.	
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-u" class="questions">
										Can I upload old orders?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-u" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Yes. The sellers can upload old orders using the normal process, if they have the information collected to reconcile all old orders. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1-v" class="questions">
										Difference between Order Summary Report and PO Orders Summary Report?
									</a> 
									</h5>
								</div>
								<div id="collapseOne1-v" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Order Summary Report is used to upload MP Orders and PO Orders Summary Report is used to upload PO Orders.
							                </p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="questions">Channel Related</a> </h5>
				</div>
				<div id="collapseTwo" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="panel-group" id="accordion2">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-a" class="questions">
										How do I add a sales channel which is not in the list of preconfigured channels?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-a" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Visit Initial Setup->Channels-><b>”Add Sales Channel”</b> button. Insert Properties and “Save” the same.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-b" class="questions">
										What are payment cycles?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-b" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Payment Cycles are developed to define and understand how and when the payment of orders will be received by the seller. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-c" class="questions">
										How do I know what is my payment cycle with a sales channel?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-c" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller needs to check with Sales Channels for better understanding of their respective payment cycles. Generally there are 3 kind of Payment Cycles as described.<br>
												1.	<b>Sub divided Monthly</b><br>
												This is a payment cycle in which a month is sub-divided into cycles. And payment for all the transactions in the defined period will be provided on the defined date. For example (1-10 of the month on 15), (11-20 of the month on 25) and (21-31 of the month on 5 of next month).<br>
												2.	<b>Monthly</b><br>
												This is a payment cycle in which all the transactions between a month will be paid for on a defined date. For example (1-31 of the month on 15 of next month)<br>
												3.	<b>Delivery/Shipped Date</b><br>
												This is a payment cycle in which payments are made against every individual order and not on the basis of a collective time period. The payments are made either on the basis of ship date or delivery date of an order. For example Payment on the basis of (Order Ship Date + 15 days), Payment on the basis of (Delivery Date + 10 days)	 
							         </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-d" class="questions">
										What is TDS applicable?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-d" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	TDS Applicable is a property of Sales Channels, which if enabled defines whether TDS is applicable to the sales channel or not. If TDS property is enabled that means that the seller needs to deduct TDS before paying commission to sales channels as defined by the government of India.
							                 	<br>
												This property can be set by visiting <b>“Initial Setup->Channels”</b>.	 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-e" class="questions">
										What is Return/RTO limit?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-e" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Return/RTO limit or Sale Return Limit is the threshold limit mutually agreed with sales channel for accepting sales returns. For example lets assume that the seller has agreed with Flipkart to accept sale returns within 30 days.This means that if a sale return comes back to the seller within 30 days it will be accepted and accounted for as agreed,whereas if the return comes post the agreed period of 30 days, the same will be accepted but will also be highlighted and will have its final action as <b>“Actionable”</b>, hence will be available in list of actionable orders.The sellers are required to take such cases to respective Sales Channels for resolution.
												A seller can configure Sale Return Acceptable threshold limit while configuring sales channels.To edit this property Visit Initial Setup->Sales Channels.		 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-f" class="questions">
										Where can I check the commissions paid to sales channels?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-f" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Commission paid to sales channels can be checked via Reports section(for a specific desired period. In case the seller wishes to check the commission paid against a particular order, he can visit the Order information page for the information
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-g" class="questions">
										How can I see the TDS to be deposited liability for sales channels? What is my TDS amount due to be reimbursed from the sales channels?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-g" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	TDS to be deposited liability can be check by visiting Miscellaneous->TDS, or by downloading Reports. TDS to be reimbursed amount can be checked via Miscellaneous ->TDS, or by downloading Reports.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-h" class="questions">
										How can I update sales channel commissions rates when the same has been changed or updated? How to Edit Sales Channel?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-h" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Sales Channels commission rates for MP Channels can be edited by visiting Initial Setup->Sales channels->Edit Channel button, making the changes and then saving. For temporary changes in MP Channels, the seller should create an Event by visiting Miscellaneous->Events and the configurations will override original configurations. Whereas for changes in PO Channels, the seller should make simply update values in “Product mapping” tab by visiting Initial Setup->Product Config->Product Mapping.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-i" class="questions">
										Do I need to update sales channel commissions during or before a sale event or not?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-i" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Yes, the seller should create Events for Sale events/promotions for MP Channels.For such temporary arrangements the seller should create an Event by visiting Miscellaneous->Events and the configurations will override original configurations. Whereas for PO Channels the seller can simply update values in <b>“Product mapping”</b>tab by visiting Initial Setup->Product Config->Product Mapping
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-j" class="questions">
										How do I see the pending payment amounts to be recovered or received from sales channels? How to get the details of such orders?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-j" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The pending payment amounts can be checked by downloading reports of Actionable orders. All orders that have their final action as “actionable” have partial or full amounts and RLC orders that are still to be recovered from the sales channels. To download such reports, visit Reports->Order Related Reports
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-k" class="questions">
										How do I see revenues from sales channels?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-k" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Gross Profits can be checked at order level.In case the seller wishes to know Gross Profits or any other revenue related information for a specific period,he needs to visit Reports->Revenue reports.Net Profit for the current financial year can be viewed @ Dashboard.To know the net profitability for a specific period the seller can download Net Business Profitability Report by visiting Reports.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-l" class="questions">
										How do I see and compare revenues for different sales channels?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-l" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The sellers can view and download Orderwise GP Report to analyze revenues from different Sales Channels by visiting Reports Tab.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-m" class="questions">
										How do I check ideal TOI to be paid value?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-m" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 The seller can visit Initial->Sales Channels-> View Channel Config button to review details of configured Sales Channels.	
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-n" class="questions">
										How to review details of already configured sales channels?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-n" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller can visit Initial->Sales Channels-> View Channel Config button to review details of configured Sales Channels.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-o" class="questions">
									What is Suggested PO Price? How is Suggested PO Price Different from Net PO Price? What if Sales Channel has given wrong rates in PO? How to identify?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-o" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							              		Suggested PO Price is the ideal PO Price that should have been ideally received in a new Purchase order from the Sales Channel.Net PO Price is actual price that the Sales Channel has provided for the discussed Purchase Order.In case there is a difference between the 2.The PO will not upload @ O2R.The seller will be informed of such incidences via Upload failure Report.The seller will need to take this forward to the respective Sales Channels,Get a new PO issued and then re-upload the same with updated prices. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-p" class="questions">
									What is Suggested Deduction?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-p" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							              		Suggested Deduction is the ideal Deduction Price that should have been ideally received in a new Return Gatepass from the Sales Channel.In case there is a mismatch between the Gatepass Amount received and Suggested Deduction,such entries will be highlighted and will have their action as Actionable and will be available in actionable orders.Plz note Suggested Deduction figure will only appear if the seller is able to provide invoice ID or PO ID against a Gatepass.Otherwise the given rates will be accepted by O2R. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion2" href="#collapseOne2-q" class="questions">
									How do I check or know return conditions?
									</a> 
									</h5>
								</div>
								<div id="collapseOne2-q" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							              		The seller needs to check with Sales Channels for better understanding of their respective return conditions.<br> Generally, the Seller needs to check for the following conditions:<br>
												4.	<b>”Normal Return Conditions”</b> for both <b>”Seller Faults”</b> and <b>”Buyer Return”</b> Orders.<br>
												5.	<b>”RTO Conditions”</b> for both <b>”Seller Faults”</b> and <b>”Buyer Return”</b> Orders.<br>
												6.	<b>“Replacement Conditions”</b> for both <b>”Seller Faults”</b> and <b>”Buyer Return”</b> Orders.<br>
												7.	<b>“Partial Delivery Conditions”</b> for both<b>”Seller Faults” </b>and <b>”Buyer Return”</b> Orders.<br>
												8.	<b>”Cancellation Conditions”</b> for both <b>“After Ready to Dispatch”</b>(After RTD) and <b>“Before Ready to dipatch”</b> (After RTD), for both <b>”Seller Faults”</b> and <b>”Buyer Return”</b> Orders.<br>
												9.	<b>“Reverse Ship Fee”</b> Rates separately 
							                </p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="questions">Return Related</a> </h5>
				</div>
				<div id="collapseThree" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="panel-group" id="accordion3">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-a" class="questions">
										How do I upload returns? Is it possible to upload returns in bulk?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-a" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Returns can be uploaded by visiting Daily Activities->Returns.<br> 
												1.<b>Manual Return Upload for MP Orders :</b> <br>
												In order to upload any Sale Return the seller needs to search for an order first by providing any order related information like Order ID, Invoice ID, Customer Name, AWB no, PI reference No, Sub-Order ID, Flipkart unique Item ID etc. After pulling the order information the seller needs to mark the order as a return and provide seller or channel generated Return ID for the same.<br>
												2.<b>Bulk Return Upload for MP and PO Orders :</b><br>
												The sellers can also upload returns in bulk for both MP and PO Orders. For uploading MP Returns, the seller will use Return Summary File whereas for uploading PO Returns, the seller will use Gatepass Summary File.
												The seller can scan barcodes in an excel file and then punch those details in bulk upload files to upload returns.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-b" class="questions">
										Some returns or RTO’s may come which are not uploaded as shipped orders. What will be done in this case?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-b" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	In such incidences, the return upload will fail.As a resolution,the seller should upload such orders via Upload Orders Process and then re-upload returns only of such orders.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-c" class="questions">
										Is it possible to upload returns that have not been added as shipped orders?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-c" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	No,it is not possible to upload returns that have not been added as shipped orders.In case such instances comes up, the seller is required to upload the order as shipped and then mark the same order as a return.It is best advised to mention correct order related information while uploading such orders.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-d" class="questions">
										What if I receive a return which has not been shipped by me?What if a wrong product(by a different seller) is received as return?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-d" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 In such cases,the seller is expected not to upload such sale returns and raise SPF claims or escalate such instances to respective Sales channels directly in order to handle such disputes.Plz note that Sales Channels would deduct the money for such returns as while receiving the return the seller has signed the receiving copy and the same will be presented in case of any disputes. Hence it is best advised to resolve such issues with Sales Channels directly.As per the discussed resolution with Sales Channel,the seller should or should not upload return.
											In case the seller decides not to raise such issues with Sales Channels,he will be advised to upload the Sale return by mentioning the ideal SKU as return.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-e" class="questions">
										What If I receive a partial return,how should that be inserted in O2R? how to handle that with O2R?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-e" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	In cases of partial returns,the seller should punch appropriate Return quantity that have been physically received by the seller. Such instances should immediately be reported or escalated to sales channels and resolved directly with Channels.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-f" class="questions">
										How do I identify/differentiate between a Return, RTO, Cancellation, Replacement or Partial Delivery??
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-f" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	<b>Return</b> is an order that has been delivered to the customer,and the customer has requested a sale return after looking at a product.Usually a return packaging is torned or changed at the time when the shipment reaches the seller. Return is a sale return which does not fall under any other classification. For example,Returns due to size,color,product quality,customer remorse.<br>
												<b>RTO</b> is an order that could not be delivered to the customer due to any reason like customer not present,wrong address, cancelled after shipping, pin code unserviceable. Usually when a RTO packet arrives @ the seller,it packaging is intact as it was when the order was shipped.<br>
												<b>Cancellation Return</b> Any order which has been cancelled by the buyer or seller for any reason.The cancellation may be before or After RTD.<br>
												<b>Replacement Return</b> are any sale returns for which the seller is required to ship a replacement.<br>
												<b>Partial Delivery Return</b> Any order which was partly delivered to the buyer. For example,a customer ordered <b>“1 Bean Bag Cover with Beans”</b>but only received the Bean Bag Cover and returned the product.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-g" class="questions">
										How do I account for Return related claims that have been approved and I will be paid for in future?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-g" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 Accounting such Return related claims is not required with O2R. The seller only needs to sincerely upload all payment summaries and O2R will automatically tag respective payments debits and credits to their orders.	
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-h" class="questions">
										What if I do not upload all sale returns?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-h" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 In any case O2R’s efficiency and accuracy is completely dependent on the data provided by the seller.O2R merely reflects statistics from the data that has been provided by the seller to O2R. <br>
											Hence in case a return is not uploaded by the seller,that order will be accounted differently than in it should have been ideally. Sale Return Deductions for such orders should be done via Manual Charges.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-i" class="questions">
										What are Reversed Return Charges and additional return charges.How are return charges determined?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-i" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Return Charges is the amount that will be deducted by the Sales Channels on account of a Sale Return after considering whether any payments have been received by the seller or not against such orders.Reversed Return Charges is the amount that is reversed by O2R(since the goods have been sent back hence there will be no payment against it).Whereas Additional Return Charges are the charges that will be levied on to the seller over and above the reversed amount.<br>
												Return Charges are calculated by O2R automatically on the basis of agreed terms between the seller and sales channel. It is best advised to consult with Sales Channels in cases of any required clarifications about the different return charges for different return conditions.<br>
												For example,a Snapdeal order was shipped with Transfer Price of Rs 100/-.The same is returned by the customer. The agreed terms with Snapdeal is that if a return comes due to no fault of seller,no charges will be deducted for the order. In this case the seller should mark the return as Return->Seller Fault,when uploading the return @ O2R.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-j" class="questions">
										Difference between Gatepass Summary Report and Return Summary Report?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-j" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Gatepass Summary Report is used to upload PO Returns in Bulk whereas Return Summary Report is used to upload MP returns in bulk.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-k" class="questions">
										How do I view/download return orders for a specific period?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-k" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Return Orders data for a specific period can be fetched via Reports->Order Related Reports->
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-l" class="questions">
										How do I identify/see/view return orders that have crossed Acceptance limits?									
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-l" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	This data for a specific period can be fetched in bulk via Reports->Order Related Reports->. 
												In case Acceptance Limit needs to be checked for a particular order,the same can be viewed @ Order detail page.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-m" class="questions">
										How do I differentiate between Acceptable and Non Acceptable Returns/RTO’s?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-m" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Acceptable Returns are those that have not crossed their return acceptance limit @ the time of arrival @ seller’s and vice versa. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion3" href="#collapseOne3-n" class="questions">
										How do I know where/how much inappropriate return charges have been debited by the sales channels?How do I recover wrong return charges deducted by sales channels?
									</a> 
									</h5>
								</div>
								<div id="collapseOne3-n" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller can download all orders with their final Action as <b>“Actionable”</b> via Reports->Order Related Reports-> to get the data of disputed orders of all kinds. This data would also contain details of such inappropriate return deductions by the Sales Channel.
							                </p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour" class="questions">Payment Related</a> </h5>
				</div>
				<div id="collapseFour" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="panel-group" id="accordion4">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-a" class="questions">
										How do I upload payments? How do I upload payments in bulk?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-a" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Payments can be uploaded manually via Daily Activities->Payment.<br>
												For Bulk Uploads click on Bulk Upload payment button @ Daily Activities->Payment.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-b" class="questions">
										What are negative amounts and positive amounts in a payment summary? 
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-b" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Negative amounts are every deduction that the Sales Channel made, on account of any return orders in that specific payment cycle. 
												Whereas Positive amounts are every credit value that the Sales Channel paid, on account of orders shipped in that specific payment cycle. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-c" class="questions">
										Difference between Payment Summary Report and PO Payment Summary Report?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-c" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Payment Summary Report is used to upload MP Orders Payments in bulk. Whereas PO Payment Summary Report is used to upload PO Payments in bulk.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-d" class="questions">
										How do I identify or segregate Manual Charges from a Payment Summary?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-d" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Manual Charges(both positive and negative) consist of all charges that are not directly associated with an original order.The below mentioned charges need to be separated from a payment summary manually by the seller.</p><br>
							                 	<div class="col-lg-6">
													<p class="heading"><b>1.Negative Manual Charges</b></p>
													<ol style="color: #009EEF;">
														<li>Packaging charges</li>
														<li>Additional Commissions</li>
														<li>Promotional Fee</li>
														<li>Disincentives</li>
														<li>Stock-Out commission</li>
														<li>EOSS discount</li>
														<li>TOI deductions</li>
														<li>Penalties</li>
														<li>Centralized Bill</li>
														<li>Invoice Mismatch</li>
													</ol>
												</div>
												<div class="col-lg-6">
													<p class="heading">
													<b>2.Positive Manual Charges</b></p>
													<ol style="color: #009EEF;">
														<li>Shipping Incentives</li>
														<li>SPF claims reversal</li>
														<li>TDS reimburse reversal</li>
													</ol>
												</div> 	
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-e" class="questions">
										Downloading total payments received data in a specific period?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-e" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller can download the total payments received data via Reports.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-f" class="questions">
										How can I know my expected pending payments that are yet to be received?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-f" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller can view such upcoming payments @ Dashboard under the upcoming payments section.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-g" class="questions">
										How can I know my disputed payment amounts?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-g" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller can check the disputed payment amounts via Reports->Order Related Reports.All orders that have their final action as Actionable form the disputed payment amount.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-h" class="questions">
										Is it necessary to link manual charges with payment summaries? What if I do not input manual charges? 
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-h" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Yes,it is necessary to link manual charges to payment summaries.In case the seller fails to do the the physical amount received will differ from the payments amounts being displayed @ O2R.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-i" class="questions">
										How do I link Manual Charges to a payment summary?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-i" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	In order to link manual charges,the seller needs to make a note of the payment ID of the respective uploaded payment summary. This payment ID should be mentioned when creating the concerned manual charges to associate it to the payment summary.Payment ID can be found @ the payments tab
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-j" class="questions">
										How do I view/download orders that have been paid for correctly?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-j" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller can download Settled Orders Reports via Reports->Order Related Report.Every order that has its final action as settled has been paid for correctly. 
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-k" class="questions">
										How do I view/download orders that have been paid for incorrectly? How do I view/download orders that have not been paid for, or are due to be paid for?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-k" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 The seller can download Actionable Orders Reports via Reports->Order Related Report. Every order that has its final action as actionable has been paid for incorrectly.	
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-l" class="questions">
										How do I recover my pending payments?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-l" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	The seller should download all actionable orders report at the end of every payment cycle.The seller should consult with the Sales Channels directly in order to recover such payments. The Payment Difference amount is the always the disputed amount of an order.At any point of time payment difference amount is calculated after considering all the debits and credits of an order life.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-m" class="questions">
										How are expected payments calculated?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-m" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Expected Payments are calculated on the basis of the payment cycles described by the seller for each Sales Channel.O2R accounts the transactions as per the properties described by the seller.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-n" class="questions">
										While uploading Payment Summary there could be orders that are not present in O2R Database?What to do?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-n" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	On such an incidence,the seller should upload such orders via Upload Orders Process and then re-upload payment summary only of such orders.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-o" class="questions">
										How do I add/edit manual charges?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-o" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Manual Charges can be added or edited via Manual Charges icon @ the toolbar.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion4" href="#collapseOne4-p" class="questions">
										When to add any manual charges?
									</a> 
									</h5>
								</div>
								<div id="collapseOne4-p" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Manual Charges are added in the following condition,<b>“Payment Summary received by the seller”</b>
												When a seller receives a payment summary from any Sales Channel. The seller needs to upload the same to O2R via Daily Activities->Payment.At this time, the seller needs to identify,segregate and input manual charges separately from the core payment summary.The separated manual charges are to be added via Manual Charges icon @ the toolbar.
							                </p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive" class="questions">Reports Related</a> </h5>
				</div>
				<div id="collapseFive" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="panel-group" id="accordion5">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne5-a" class="questions">
										Downloading my revenue related reports(including Gross Profit and Net profit)?
									</a> 
									</h5>
								</div>
								<div id="collapseOne5-a" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Revenue Related Reports can be downloaded/viewed via Reports->Revenue Related Reports.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne5-b" class="questions">
										Downloading my best selling products or regions report?
									</a> 
									</h5>
								</div>
								<div id="collapseOne5-b" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 Best selling products(SKU’s) or regions report can be downloaded or viewed via Reports->General Reports.	
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne5-c" class="questions">
										Where can I view or download my total turnover or taxable sales report?
									</a> 
									</h5>
								</div>
								<div id="collapseOne5-c" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Total turnover report can be accessed via Reports->Revenue Related Reports.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne5-d" class="questions">
										Where can I view or download my total shipped orders,settled orders, actionable orders,disputed orders,payment difference orders,sales return orders,return limit crossed orders?
									</a> 
									</h5>
								</div>
								<div id="collapseOne5-d" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	All order related reports can be accessed via Reports->Order Related Reports.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne5-e" class="questions">
										What is customer database?
									</a> 
									</h5>
								</div>
								<div id="collapseOne5-e" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Customer Database is list of information of customers that have purchased seller’s product. The seller can access his customer database via Customers Tab.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne5-f" class="questions">
										What is List of Invoices report?
									</a> 
									</h5>
								</div>
								<div id="collapseOne5-f" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	List of Invoices report provides the seller with detailed invoice centric information of the selected period of all sales channels.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne5-g" class="questions">
										What is TDS to be deposited report?
									</a> 
									</h5>
								</div>
								<div id="collapseOne5-g" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 TDS to be deposited report provides information of TDS related transactions of the selected period.This report could be used to understand how has the TDS amount to be deposited,has been derived.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion5" href="#collapseOne5-h" class="questions">
										What is Sales tax liability report?
									</a> 
									</h5>
								</div>
								<div id="collapseOne5-h" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Sales Tax to be deposited report provides information of Sales Tax related transactions of the selected period.This report could be used to understand how has the Sales Tax amount to be deposited,has been derived.
							                </p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseSix" class="questions">Dashboard Related</a> </h5>
				</div>
				<div id="collapseSix" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="panel-group" id="accordion6">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion6" href="#collapseOne6-a" class="questions">
										
									</a> 
									</h5>
								</div>
								<div id="collapseOne6-a" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para"class="heading">
							                 	
							                </p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseSeven" class="questions">Tax/TDS Related</a> </h5>
				</div>
				<div id="collapseSeven" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="panel-group" id="accordion7">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-a" class="questions">
										Understanding Tax/TDS Liabilities.What is TDS reimbursed?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-a" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Tax Liability is the tax amount to be deposited by the seller at the end of a tax cycle.The tax rates and cycles depends on the seller’s local government authorities trade policies and regulations.At the end of a tax cycle the seller needs to pay tax to the government for his business transactions during a tax cycle.In some states the tax cycles could be monthly, quarterly or any period. The tax rates are product oriented. Tax history and transactions can be viewed via Manual Charges->TAX.<br>
												<b>TDS</b>(Tax Deducted at source) Liability is the TDS amount to be deposited by the seller at the end of a TDS cycle.The TDS rates and cycles depends on the seller’s local government authorities trade policies and regulations. At the end of a TDS cycle the seller needs to pay TDS to the government for his business transactions during a TDS cycle. In some states the TDS cycles could be monthly, quarterly or any period.<br>
												<b>Plz Note</b> TDS is a refundable tax that a seller has deposited on behalf of Sales Channels and needs to reimburse the same from respective Sales Channels. After depositing the TDS,at the end of a TDS Cycle,a TDS certificate is collected from local government authorities and is shared with Sales Channels for reimbursing the TDS amounts.Once the TDS amount is credited to the sales channels via a payment.The seller needs to separate it from the payment summary and mark the TDS status as Reimbursed to keep updates of TDS to be reimbursed.TDS status could be triggered via Manual Charges->TDS.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-b" class="questions">
										Creating Tax categories?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-b" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Tax Categories are created as on local tax authorities requirements and are decisive of product nature.For example a seller in New Delhi dealing into Apparels is liable to pay 5% of transaction value as state VAT.Hence he will create a tax category of local sale @ 5%.In case he is also dealing in electronics he must also create another Tax category of local sale @ 10%.	
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-c" class="questions">
										When to create a new TAX category?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-c" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para"class="heading">
							                 	Tax categories are created during the process of initial setup. In case the local tax authority amends any new regulations or policies, new tax categories suiting the new policies should be created by the seller.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-d" class="questions">
									Where do I find/view/download my tax dues/liabilities?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-d" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Tax/TDS dues can be viewed via Manual Charges->TAX and Manual Charges->TDS.
												In order to view transaction details constructing the TAX and TDS amounts,the seller needs to visit Reports->Tax Related reports.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-e" class="questions">
										Why do I need to Mark Taxes/TDS as deposited?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-e" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	<b>Plz Note</b> TDS is a refundable tax that a seller has deposited on behalf of Sales Channels and needs to reimburse the same from respective Sales Channels. After depositing the TDS,at the end of a TDS Cycle,a TDS certificate is collected from local government authorities and is shared with Sales Channels for reimbursing the TDS amounts.Once the TDS amount is credited to the sales channels via a payment.The seller needs to separate it from the payment summary and mark the TDS status as Reimbursed to keep updates of TDS to be reimbursed. TDS status could be triggered via Manual Charges->TDS.
												Taxes are required to marked as deposited to keep track and records of the pending tax dues of the seller.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-f" class="questions">
										Marking TDS/TAX as deposited/reimbursed?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-f" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Tax/TDS dues can be marked as deposited or reimbursed via Manual Charges->TAX and Manual Charges->TDS.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-g" class="questions">
									How accurate are the TAX/TDS calculations.Do I need to check before depositing?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-g" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	TAX/TDS calculations are vulnerably dependent on sellers data input for its accuracy of data.O2R will only calculate and reflect from the data that has been provided to it.Tax/TDS properties are also defined by the seller itself.O2R recommends the sellers to ensure regular updates of daily activities into O2R for most accurate results.<br>
												O2R suggests sellers to download tax related transaction data via Reports->Tax relate reports.And check the same before depositing TAX/TDS dues.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-h" class="questions">
										Procedure for depositing TDS?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-h" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	TDS needs to be deposited to the TDS authorities via online or offline modes of payments.Plz consult local Chartered Accountants or local TDS Authorities to understand the complete process.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-i" class="questions">
										Procedure for depositing Sales TAX?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-i" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	TAX needs to be deposited to the TAX authorities via online or offline modes of payments.Plz consult local Chartered Accountants or local TAX Authorities to understand the complete process.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-j" class="questions">
										Procedure for recovering TDS amounts?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-j" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	After depositing the TDS,at the end of a TDS Cycle,a TDS certificate is collected from local government authorities and is shared with Sales Channels for reimbursing the TDS amounts. Once the TDS amount is credited to the sales channels via a payment.The seller needs to separate it from the payment summary and mark the TDS status as Reimbursed to keep updates of TDS to be reimbursed.TDS status could be triggered via Manual Charges->TDS.
							                </p>
										</div>
									</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion7" href="#collapseOne7-k" class="questions">
										How is taxable sale value calculated?
									</a> 
									</h5>
								</div>
								<div id="collapseOne7-k" class="panel-collapse collapse">
									<div class="panel-body">
										<div class="col-sm-12 wow fadeInRight animated">
							                <p id="para" class="heading">
							                 	Taxable sale value consists of 2 components. 
												First component consists of dropship transactions wherein the seller is directly selling to the end customer via a sales channel.And the sales channel is merely acting as a platform for the seller to sell his offerings.In such cases the seller pays taxes on the SP(Selling Price) of a product rather than the Net/Receivable.<br>
												Second Component consists of PO(Purchase Order) transactions wherein the seller is dealing and selling its goods to a Sales Channels.And the Sales channel is acting as a distributor and further selling to end customers. In such cases the seller pays taxes on the PO price of a transaction rather than the SP.(Selling price).<br>
												Taxable sale is derived by considering Gross Sale and Sale Return and is the Net Sale of the seller’s business.
							                </p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</div>
			<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseEight" class="questions">Expenses Related</a> </h5>
					</div>
					<div id="collapseEight" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="panel-group" id="accordion8">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion8" href="#collapseOne8-a" class="questions">
											What are expense groups?
										</a> 
										</h5>
									</div>
									<div id="collapseOne8-a" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Expense Groups are seller created expense categories and are further used to analyze different business expenditures over a period of time. 
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion8" href="#collapseOne8-b" class="questions">
											What to think before creating expense groups?
										</a> 
										</h5>
									</div>
									<div id="collapseOne8-b" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller should think about his business and then plan the different branches of expenses that his business requires.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion8" href="#collapseOne8-c" class="questions">
											Creating expense group?
										</a> 
										</h5>
									</div>
									<div id="collapseOne8-c" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller can create expense groups at any point of time by visiting Initial Setup->Expense Groups->Create Expense Group
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion8" href="#collapseOne8-d" class="questions">
											What all,kind of expenses should be punched into O2R?
										</a> 
										</h5>
									</div>
									<div id="collapseOne8-d" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	All expenses of any nature should be punched into O2R.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion8" href="#collapseOne8-e" class="questions">
											Are any expenses shared with the government of India?
										</a> 
										</h5>
									</div>
									<div id="collapseOne8-e" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	No.<b>Plz note</b> that all seller related business information is completely private and confidential and will not be shared with the government.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion8" href="#collapseOne8-f" class="questions">
											How do I view/download expense data for a period?
										</a> 
										</h5>
									</div>
									<div id="collapseOne8-f" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller can visit Reports->Expense Related Reports to view this data.
								                </p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>					
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseNine" class="questions">Pricing Related</a> </h5>
					</div>
					<div id="collapseNine" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="panel-group" id="accordion9">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-a" class="questions">
											What is the cost of using O2R?What are different pricing options/plans available with O2R?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-a" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	O2R offers a Pay per Use Model to its clients. Based on the monthly business volumes the seller should opt for the best suited plan. The different pricing plans can be viewed by visiting My Account->Upgrade plans or by visiting O2R Webpage.
													<b>Plz note</b>that there are no fixed monthly charges that O2R demands.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-b" class="questions">
											What do I pay O2R charges/Fee/commission?What is CPO?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-b" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	O2R deducts the CPO rate only when an order is uploaded. O2R does not charge for uploading returns, payments, manual charges etc. <br>
													CPO is Cost per Order,it is the rate that O2R will charge for its offered services.It is dependent on the seller’s selected plan.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-c" class="questions">
											How do I pay O2R?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-c" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para"class="heading">
								                 	The seller is required to recharge his Order Bucket as and when the orders are on the verge of depletion.The seller can pay online by Credit/Debit Cards and Net Banking facilities.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-d" class="questions">
											How do I view/check order consumption history?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-d" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller can view order consumption history by visiting My Account tab.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-e" class="questions">
											Where do I view/check my payment history?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-e" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller can view order payment history by visiting My Account tab.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-f" class="questions">
											Am I charged on uploading a sale return order as a return?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-f" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para"class="heading">
								                 	No,O2R only charges on uploading an order.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-g" class="questions">
											How do I know my best suited plan?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-g" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para"class="heading">
								                 	The best way to determine this is to know the average monthly sales volumes.Based on that the seller can estimate his monthly consumption and chose the best plan.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-h" class="questions">
											How do I recharge my order bucket? How do I purchase orders?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-h" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para"class="heading">
								                 	The seller can recharge by visiting My Account->Upgrade plan->Check Out->Enter Payment Details->Confirming Payment.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-i" class="questions">
											When do I receive alerts of order recharge?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-i" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller will be reminded when there are < 200 orders remaining in the order bucket.	
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-j" class="questions">
											What if my order bucket I empty?Will O2R still be operational for me?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-j" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	<b>Plz Note</b>that in case the order bucket is empty,the seller will not be able to login to O2R.He will be required to pay before logging in and using any O2R related services.In case the seller does not wish to use O2R any further,the seller can make a request for data backup service by contacting O2R.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion9" href="#collapseOne9-k" class="questions">
											How do I cancel/delete my O2R account?
										</a> 
										</h5>
									</div>
									<div id="collapseOne9-k" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	In case the seller has no order balance in order bucket and does not recharge for a period of 90 days.We will delete his/her O2R account.
								                </p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>					
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseTen" class="questions">Product Related</a> </h5>
					</div>
					<div id="collapseTen" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="panel-group" id="accordion10">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-a" class="questions">
											How do I create/upload products(SKU’s)?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-a" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller can add products manually or in bulk by visiting Initial Setup->Product Info->SKU’s.<br>
													<b>Plz note</b>before uploading products the seller needs to create Product Categories and inventory Groups.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-b" class="questions">
											How do I create/upload products(SKU’s) in bulk?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-b" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller can visit Initial Setup->Product Info->SKU’s->Bulk Upload->Download Product Summary File->Input Details->Upload File.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-c" class="questions">
											When do I create new products?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-c" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Whenever the seller brings a new product to be sold via any of the configured sales channels,he should punch that into O2R.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-d" class="questions">
											What are product categories?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-d" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Product Categories are created to group product SKU’s.The created categories are helpful in analyzing business insights	
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-e" class="questions">
											How to create/edit product categories?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-e" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller can visit Initial Setup->Product Info->Product Categories->Add Category.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-f" class="questions">
											When to create/edit product category?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-f" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller is advised to create product categories during initial setup.Also the seller can create a new category at any point of time whenever he is starting with a new category.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-g" class="questions">
											Can I delete any products or SKUs?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-g" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 No.Deleting products is not an option with O2R.	
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-h" class="questions">
											How do I link products(SKU’s) to product categories?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-h" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Products are linked to product categories during the SKU upload process by mentioning the category against every SKU. 
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-i" class="questions">
											Is mapping products with its sales channels names mandatory?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-i" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	No mapping products to their Channel SKU names is non mandatory.But in case the seller choses to do so,it will be very helpful during return upload process.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-j" class="questions">
											How do I upload/edit inventories manually or in bulk?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-j" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The product inventories can be added or subtracted at any point of time via Initial Setup->Product Info->SKU. <br>
													For manual update,simple search SKU and click on Edit Inventory Button.<br>
													For bulk upload,simply use Inventory summary file to update details. 
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-k" class="questions">
											What if my O2R inventory for a SKU is 0 and an order is uploaded for the same product.The order would not reflect in O2R.What do I do?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-k" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Before uploading such orders,the seller needs to update the product inventory.Only then will he/she be able to upload those orders.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-l" class="questions">
											What are product costs?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-l" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Product cost is landed or direct from vendor cost of a product before any transportation.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-m" class="questions">
											Where to add/edit product costs?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-m" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Product Cost are punched at the time of product upload.As of now updating product cost is not an option.But soon this will be possible. 
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-n" class="questions">
											What if I wish to change my product cost at any point of time?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-n" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	As of now updating product cost is not an option.But soon this will be possible. 
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-o" class="questions">
											How to check my closing stock and opening stock data and valuation?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-o" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Opening Stock and Closing Stock Valuations can be viewed in Reports->Net Reports->Net Profitability Reports.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion10" href="#collapseOne10-p" class="questions">
											What is closing/opening stock valuation?
										</a> 
										</h5>
									</div>
									<div id="collapseOne10-p" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Opening and Closing Stock valuation is the valuation of the total inventory let in warehouse at the beginning and end of a month. 
								                </p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>					
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseEleven" class="questions">Others</a> </h5>
					</div>
					<div id="collapseEleven" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="panel-group" id="accordion11">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-a" class="questions">
											How reliable is O2R data and calculations?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-a" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	O2R data is a mere reflection of statistics derived from the inputs provided by the Seller.Based on the input strings O2R provides 100% accurate information.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-b" class="questions">
											What if I start using O2R in the middle of a month or financial year?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-b" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	You can very well choose to start O2R at any point of time. The only limitation is for that very financial year O2R will reflect the data from the point the seller has started using.In case the seller has recorded old orders data in excel.They can also input the same for complete information.O2R will treat old orders as they are present orders only and will reflect complete results if complete information is punched.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-c" class="questions">
											How to initial setup/begin/initiate/start with O2R(InitialSetup)? Things to do to before I start using O2R?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-c" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Kindly visit Training Center to learn about initial setup.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-d" class="questions">
											What are my daily activities?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-d" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Kindly visit Training Center to learn about Daily Activities.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-e" class="questions">
											How to Use Reports?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-e" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Kindly visit Training Center to learn about Reports.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-f" class="questions">
											Does O2R syncs with Sales Channels?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-f" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para"class="heading">
								                 No,as on date O2R does not sync or collaborate with any Sales Channel for any information.O2R takes all the information from the seller and reflects statistics without any influence from the Sales Channels.We @ O2R feel that if a seller,chooses the convenience of automatically fetching data from channels then he really doesn’t need an accounting software.The fetched data will always be Channel influenced.The seller can view the same statistics @ their respective Sales Channels also.	
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-g" class="questions">
											Does O2R share data with Sales Channels?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-g" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	No,O2R does not hsare any data with Sales Channels.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-h" class="questions">
											What is my total turnover?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-h" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para"class="heading">
								                 	The seller can view his total turnover details in Reports->Net Reports-> Channelwise Sales Report.
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-i" class="questions">
											How is Gross Profit calculated by O2R?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-i" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	Gross Profit is calculated after comparing Pure Sale(taxfree Sale) with direct from vendor or direct landing product costs. 
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-j" class="questions">
											How is Net Profit calculated by O2R?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-j" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 Net Profits are calculated after considering opening and closing stock valuations,business expenses and actual sales receipts.	
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-k" class="questions">
											How do I check my Gross/Net Profit for a period?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-k" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 For Gross Profit visit Reports->Net reports->Orderwise Gross profit Report. For Net Profit visit Reports->Net reports->Net Profitability Report.	
								                </p>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion11" href="#collapseOne11-l" class="questions">
											What value is used to calculate total turnover(SP or N/R)?
										</a> 
										</h5>
									</div>
									<div id="collapseOne11-l" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para" class="heading">
								                 	The seller receives both Taxable and Actual Turnover information from O2R.The taxable Turnover is derived after considering SP and PO Price.Whereas Actual Turnover is derived after considering N/R.
								                </p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>					
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwelve" class="questions">Manual Charges  Related</a> </h5>
					</div>
					<div id="collapseTwelve" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="panel-group" id="accordion12">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h5 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion6" href="#collapseOne12-a" class="questions">
											
										</a> 
										</h5>
									</div>
									<div id="collapseOne12-a" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="col-sm-12 wow fadeInRight animated">
								                <p id="para"class="heading">
								                 	
								                </p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>					
					</div>
				</div>
			</div>
    	</div>
	</div>
	<div class="col-lg-3" style="margin-top: 50px;">
	  <form role="search" class="form-inline navbar-form-new pull-right" method="POST" style="position: relative;top: 16px;">
		<div class="ui-widget">
			<input type="text" placeholder="Search" class="form-control" name="top-search" id="automplete-1">
			<button class="btn btn-white" type="submit"><i class="fa fa-search"></i></button>
		</div>
	   </form> 
	   <br><br>
		<img src="/landing/img/faq.png" alt="faq" width="30%" style="float: right;margin-top: 58%;">
	  
	</div>
	
</div>
<div id="section3" class="container-fluid">
  <div class="col-lg-9">
    <h1 class="heading1">Guidelines</h1>
    <hr class="headingline">
    
    <div style="float: left;overflow-x: hidden;overflow-y: scroll;height: 538px;">
      <p class="paragraph">
        α TDS will only be applicable on Partners having TDS Applicable Checked as a property in the Partner Setup section.
      </p>
		<p class="paragraph">
			α All Orders to be accounted in the period, events related to them happen.For example if a Return comes in Period Y and the order was due in Period
			   then it should be calculated in Period Y only and not in Period X.It will be used in Period X only in the net due amount.</p>
		<p class="paragraph">
			α Any Return Order is considered in a period dependent on its date of payment(deduction).
		</p>
		<p class="paragraph">
			α Orders are considered in the period in which they are shipped.
		</p>
		<p class="paragraph">
			α Sales Tax is calculated on Selling Price of a product for channels who have TDS Applicable as their property. Whereas for Channels who do not have
		   applicable, Sales Tax is calculated on N/R.
		</p>
		<p class="paragraph">
			α Never Insert Negative Value in a Payment Manual or bulk Upload with “–“(minus).
		</p>
		<p class="paragraph">
			α Gross Profitability to use only orders that are settled in that very period.
		</p>
		<p class="paragraph">
			α Never Insert Negative Value in a Payment Manual or bulk Upload with “–“(minus).
		</p>
		<p class="paragraph">
			α Gross Profitability to use only orders that are settled in that very period.
		</p>
		<p class="paragraph">
			α Never Insert Negative Value in a Payment Manual or bulk Upload with “–“(minus).
		</p>
      </div>
  </div>
  <div class="col-lg-3">
    <form role="search" class="form-inline navbar-form-new pull-right" method="POST" action="" id="" style="position: relative;top:16px;">
    <br>
    <div class="ui-widget">
        <input type="text" placeholder="Search" class="form-control" name="top-search" id="automplete-2">
        <button class="btn btn-white" type="submit"><i class="fa fa-search"></i></button>
    </div>
   </form> 
    <img src="/landing/img/guide.png" alt="faq" width="30%" style="float: right;margin-top: 20%;">
  </div>


  
</div>
<div id="section41" class="container-fluid">
  <h1 class="heading1">Initial Setup</h1>
  <hr class="headingline">

  <div class="col-lg-12">
    <div class="col-lg-3">
      <img src="/landing/img/setting.png" alt="setting" width="100%">
    </div>
    <div class="col-lg-9">
      <div class="col-md-12">
        <div class="col-md-3">
            <img src="/landing/img/curve.png" alt="curve" width="40%" style="width: 47%;position: relative;left: 156px;top: -19px;z-index: 9;">
        </div>
        <div class="col-md-9">

          <button class="btn btn-red btn-block" id="butonn" onclick="location.href='#';">Configure Product Categories</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn1" onclick="location.href='#';">Configure Inventory Groups</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn2" onclick="location.href='#';">Configure SKU's</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn3" onclick="location.href='#';">Configure Tax Categories</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn4" onclick="location.href='#';">Configure Expense Groups</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn5" onclick="location.href='#';">Configure Sales Channels</button>


        </div>

      </div>

    </div>
    
  </div>
  
</div>
<div id="section42" class="container-fluid" style="background-color: #ffffff;">
  <h1 class="heading1">Daily Activities</h1>
  <hr class="headingline">
  <div class="col-lg-12">
    <div class="col-lg-3">
        <img src="/landing/img/dailyact.png" alt="dailyactivities" width="100%">
    </div>
    <div class="col-lg-9">
      <div class="col-md-12">
        <div class="col-md-3">
            <img src="/landing/img/curve.png" alt="curve" width="40%" style="width: 47%;position: relative;
            left: 157px;top: -19px;z-index: 9;">
        </div>
        <div class="col-md-9">
          <button class="btn btn-red btn-block" id="butonninput" onclick="location.href='inputorder.html';"> Input Orders</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn1" onclick="location.href='#';">Input Sale Returns</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn2" onclick="location.href='#';">Input Payments</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn3" onclick="location.href='#';">Input Manual Charges</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn4" onclick="location.href='#';">Input Inventories</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn5" onclick="location.href='#';">Input Expenses</button>
        </div>
      </div>
    </div>
  </div>
</div>
<div id="section43" class="container-fluid" style="background-color: #fff;">
  <h1 class="heading1">Reports</h1>
  <hr class="headingline">
  <div class="col-lg-12">
    <div class="col-lg-3">
        <img src="/landing/img/report.png" alt="report" width="100%">
    </div>
    <div class="col-lg-9">
      <div class="col-md-12">
        <div class="col-md-3">
            <img src="/landing/img/curve1.png" alt="curve1" width="40%" style="width: 53%;position: relative;left: 157px;top: -17px;z-index: 9;">
        </div>
        <div class="col-md-9">
          <button class="btn btn-red btn-block" id="butonn" onclick="location.href='#';">Order Related Report</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn1" onclick="location.href='#';">Taxation Reports</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn2" onclick="location.href='#';">Channel Related Reports</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn7" onclick="location.href='#';">Expense Related Reports</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn8" onclick="location.href='#';">Inventory Related Reports</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn9" onclick="location.href='#';">General Reports</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn6" onclick="location.href='#';">Revenue Related Reports</button>

        </div>
      </div>
    </div>
  </div>

  
</div>
<div id="section44" class="container-fluid" style="background-color: #fff;">
  <h1 class="heading1">Miscellaneous</h1>
  <hr class="headingline">
  <div class="col-lg-12">
    <div class="col-lg-3">
        <img src="/landing/img/misscellaneous.png" alt="misscellaneous" width="100%">
    </div>
    <div class="col-lg-9">
      <div class="col-md-12">
        <div class="col-md-3">
            <img src="/landing/img/curve.png" alt="curve" width="40%" style="width: 47%;position: relative;left: 158px;top: -19px;z-index:9;">
        </div>
        <div class="col-md-9">
          <button class="btn btn-red btn-block" id="butonn" onclick="location.href='#';">Understanding Tax/TDS</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn1" onclick="location.href='#';">Configure Events</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn2" onclick="location.href='#';">Configure Expected Delivery Time</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn3" onclick="location.href='#';">Undestanding Customer Database</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn4" onclick="location.href='#';">Using Universal Search</button>
          <br>
          <button class="btn btn-red btn-block" id="butonn5" onclick="location.href='#';">Understanding Dashboard</button>
        </div>
      </div>
    </div>
  </div>
</div>
<div id="section45" class="container-fluid" style="background-color: #fff;">
  <h1 class="heading1">Others</h1>
  <hr class="headingline">
</div>
</body>
</html>