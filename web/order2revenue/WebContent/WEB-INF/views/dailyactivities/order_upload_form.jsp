<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<link rel='stylesheet' href='/O2R/seller/css/nprogress.css' />
<script src='/O2R/seller/js/nprogress.js'></script>
<!-- <script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->
<script src="/O2R/seller/js/jquery.ui.widget.js"></script>
<script src="/O2R/seller/js/jquery-2.1.1.js"></script>
<script src="/O2R/seller/js/jquery.iframe-transport.js"></script>
<script src="/O2R/seller/js/jquery.fileupload.js"></script>
<style type="text/css">
        .para
        {
            text-align: justify;
        }
        .left
        {
            float: left;
            width: 24%;
            margin: 0px;
            position: relative;
            left: 24%;
            top: 8px;
            color:#fff;
            border: 1px dashed #fff;
        }
        .right
        {
            float: right;
            width: 24%;
            margin: 0px;
            position: relative;
            right: 24%;
            top: 8px;
            color:#fff;
            border: 1px dashed #fff;
        }
        .heading
        {
            position: relative;
            top: -7px;
        }
        .hide {
   			 display: none;
        }
    </style>
<script type="text/javascript">
	$(function() {
		var downloadValue = '${downloadValue}';
		var uploadValue = '${uploadValue}';
		if (uploadValue != null && uploadValue.length != 0)
			$("#sheetValue").val(uploadValue);
		else
			$("#downloadreporttype").val(downloadValue);
	});
	$('#fileupload1,#fileupload2').submit(
			function() {
				$.ajax({
					xhr : function() {
						$('#dropdown').dropdown("toggle");
						NProgress.configure({
							parent : "#bar1"
						});
						NProgress.start();
						var xhr = new window.XMLHttpRequest();
						
						//Upload progress
						xhr.upload.addEventListener("progress", function(evt) {
							$("#bar1").show();
							if (evt.lengthComputable) {
								var percentComplete = evt.loaded / evt.total;
								var progress = parseInt(evt.loaded / evt.total
										* 100, 10);
								NProgress.set(progress / 100);
							}
						}, false);
						
						//Download progress
						xhr.addEventListener("progress", function(evt) {
							NProgress.start();
							if (evt.lengthComputable) {
								var percentComplete = evt.loaded / evt.total;
								var progress = parseInt(evt.loaded / evt.total
										* 100, 10);
								NProgress.set(progress / 100);
								alert(progress);
							}
						}, false);
						return xhr;
					},
					url : 'saveSheet.html',
					data : $('#fileupload1,#fileupload2').serialize(),
					processData : false,
					contentType : false,
					type : 'POST',
					success : function(data) {
						$("#bar1").hide();
					}
				});
			});
</script>
<script>
	$(document).ready(function() {
		$('.div-toggle').trigger('change');
		var downloadValue = '${downloadValue}';
		var uploadValue = '${uploadValue}';
		if (uploadValue != null && uploadValue.length != 0)			
			$('#sheetValue').val(uploadValue);			
		else
			$("#downloadreporttype").val(downloadValue);
	});
	$('#download').click(function() {
		var value = $('#downloadreporttype').val();
		if(value == ''){
			window.location.reload();
		}else{
			window.location = 'download/xls.html?sheetvalue=' + value;
		}
	});
	$('#download1').click(function() {
		var value = $('#downloadreporttype').val();
		var url="/O2R/XLSFiles/"+value+".xls";		
		if(value == ''){
			window.location.reload();
		}else{			
			document.getElementById('my_iframe').src = url;			
		}
	});
</script>

</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Export Import Files</h5>
				</div>
				<div class="ibox-content overflow-h">
					<div class="col-lg-12">
						<div class="hr-line-dashed"></div>
						<div class="col-md-4">
			
							<iframe id="my_iframe" style="display:none;"></iframe>
							<select class="form-control" id="downloadreporttype"
								name="downloadreporttype" required	autocomplete="off" >
								<option value="">Select file to download</option>
								<option value="MP_Order_Upload">MP Order Upload</option>
								<option value="PO_Order_Upload">PO Order Upload</option>
								<option value="MP_Payment_Upload">MP Payment Upload</option>
								<option value="Po_Payment_Upload">PO Payment Upload</option>
								<option value="MP_Return_Upload">MP Return Upload</option>
								<option value="PO_Gatepass_Uplooad">PO GatePass Upload</option>								
								<option value="Create_Parent_Product">Create Parent Product</option>
								<option value="Product_Edit">Edit Product Upload</option>
								<option value="MP_SKU_Mapping">MP SKU Mapping</option>
								<option value="PO_Product_Config">PO Product Config</option>
								<option value="inventorysummary">Inventory Upload</option>
								<option value="Expense_Upload">Expense Upload</option>	
								<option value="Event_SKU_Upload">Event SKU Upload</option>															
							</select>
						</div>						
						<div class="col-md-4">
							<button class="btn btn-success " type="button" id="download1">
								<i class="fa fa-download"></i>&nbsp;&nbsp;<span class="bold">Download</span>
							</button>
						</div>
						<div class="col-md-2">
						</div>
						</div>
						

						<div class="col-lg-12">
							<div class="form-group">

								<form:form id="fileupload1" method="post"
									action="saveSheet.html" modelAttribute="uploadForm"
									enctype="multipart/form-data" class="form-horizontal">

									<!-- 	<input id="addFile" type="button" value="Add File" /> -->
									<div class="col-md-4">
										<select class="form-control" id="sheetValue" name="sheetValue" required	autocomplete="off" >
											<option value="">Select file to upload</option>
											<option value="ordersummary">MP Order Upload</option>
											<option value="orderPoSummary">PO Order Upload</option>
											<option value="paymentSummary">MP Payment Upload</option>
											<option value="returnSummary">MP Return Upload</option>
											<option value="gatepassSummary">PO GatePass Upload</option>											
											<option value="productSummary">Create Parent Product</option>
											<option value="editProductSummary">Edit Product Upload</option>
											<option value="skuMapping">MP SKU Mapping</option>
											<option value="productConfigSummary">PO Product Config</option>
											<option value="inventorySummary">Inventory Upload</option>
											<option value="poPaymentSummary">PO Payment Upload</option>
											<option value="expenseSummary">Expense Upload</option>	
											<option value="EventSKUSummary">Event SKU Upload</option>										
										</select>
									</div>
									<div class="col-md-4">															
										<input name="files[0]" type="file" id="file1" class="form-control" required accept="application/vnd.ms-excel" onchange="checkfile1(this);"/>											
									</div>
									<div class="col-md-2">
										
										<button class="btn btn-success " type="submit" id="upload">
											<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">Upload</span>
										</button>
									</div>
								</form:form>
							</div>
						</div>						
					</div>
				</div>
			</div>
						
		            	<div class="col-lg-12">
		            		<h3 class="text-center heading">
			            		<div class="hr-line left">      
			                    </div>
			                    OR
			                    <div class="hr-line right">
		                    </div>
            				</h3>
		            	</div>	
		            	
			            <div class="col-lg-12">
			                <div class="ibox float-e-margins">
			                    <div class="ibox-title">
			                        <h5>Channel Upload</h5>
			                    </div>
			                    <div class="ibox-content overflow-h">
			                        <div class="form-group">
		                                <form:form id="fileupload2" method="post"
											action="saveSheet.html" modelAttribute="uploadForm"
												enctype="multipart/form-data" class="form-horizontal">
		                                    <div class="col-md-4">
		                                        <select class="form-control div-toggle" data-target=".my-info" id="sheetValue" name="sheetValue" required autocomplete="off" 
		                                        	style="width: 97%;margin-left: 14px">
		                                            <option value="" data-show=".upload">Upload</option>
		                                            <option value="Snapdeal_Order" data-show=".snapdealorderupload">Snapdeal Order Upload</option>
		                                            <option value="Snapdeal_Payment" data-show=".snapdealpaymentupload">Snapdeal Payment</option>
													<option value="Flipkart_Order" data-show=".flipkartorderupload">Flipkart Order Upload</option>
													<option value="Flipkart_Payment" data-show=".flipkartpaymentupload">Flipkart Payment</option>
													<option value="PayTM_Order" data-show=".paytmorderupload">PayTM Order Upload</option>
													<option value="PayTM_Payment" data-show=".paytmpaymentupload">PayTM Payment Upload</option>
													<option value="Amazon_Order" data-show=".amazonorderupload">Amazon Order Upload</option>											
													<option value="Amazon_Payment" data-show=".amazonepaymentupload">Amazon Payment Upload</option>
													<option value="Limeroad_Order" data-show=".limeroadorderupload">Limeroad Order Upload</option>
													<option value="Limeroad_Payment" data-show=".limeroadpaymentupload">Limeroad Payment Upload</option>
		                                        </select>
		                                    </div>
		                                    <div class="col-md-4">
		                                        <input name="files[0]" type="file" id="file" class="form-control" required accept="application/vnd.ms-excel" onchange="checkfile(this);"/>	
		                                    </div>
		                                    <div class="col-md-2">
		                                        <button class="btn btn-success " type="submit" id="upload4Channel">
		                                            <i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">Upload</span>
		                                        </button>
		                                    </div>
		                                </form:form>
		                            </div>
		                            <!-- <div class="col-lg-12">
		                                <p class="m-t-sm">
		                                <b>Note : </b><font color="green">Upload Channel files with 1st row as headers</font>
		                            </p>
		                            </div> -->
		                      <div class="col-lg-12">
                                <div class="my-info">
                                    <div class="upload">
                                        <ul class="m-t-sm">
                                            <li >
                                                <b style="color: #000;">Note : </b><font color="green">Upload Channel files with 1st row as headers</font>
                                            </li>   
                                        </ul>
                                    </div>
                                    <div class="amazonorderupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                        <ol>
                                            <li>
                                                 Upload the order sheet downloaded form Amazon seller panel but <b>only in .xls format</b>. 
                                            </li>
                                            <li>
                                                Row with the column headers is the first row of sheet. Make sure to keep the spelling of all column header according to instructions.
                                            </li>
                                            <li>
                                                Do not change the formatting of columns.
                                            </li>
                                            <li>
                                                Excel sheet should have these columns with exact same heading as downloaded form panel : 
                                                Mandatory Columns : order-id,purchase-date,sku,quantity-purchased,item-price,shipping-price,ship-postal-code,payment-method,
                                                Non mandatory Columns:order-item-id,buyer-email,buyer-name,buyer-phone-number,ship-address-1,ship-address-2,ship-address-3,ship-city,fulfilled-by
                                            </li>
                                            <li>
                                                Other mandatory columns to be added manually with respective values for each transactions : 
                                                Sales Channel : Channel name for which file is being upload. It should be same(case-sensitive) as the name of sales channel configured.
                                            </li>
                                            <li>
                                                InvoiceID : Mention invoice id for each order.
                                            </li>
                                            <li>
                                                Order Shipped Date :Mention shipped date in mm-DD-yyyy format.
                                            </li>
                                            <li>
                                                Some non mandatory columns to be added : 
                                            </li>
                                            <li>
                                                Net Rate:
                                                Mandatory when NR switch for particular channel is off or Event is implemented on that channel and SKU with fixed TP.
                                            </li>
                                            <li>
                                                Seller Notes : Any remarks about order.
                                            </li>
                                            <li>
                                                AWB No.:Airway bill number. It is used to track order during return and to track order delivery status.
                                            </li>
                                            <li>
                                                PIreferenceNo: Another Identifier to find order.
                                            </li>
                                            <li>
                                                Order MRP: Used to find discount values on order, enter MRP per quantity.
                                            </li>
                                        </ol>

                                    </div>
                                    <div class="flipkartorderupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                    <ol>
                                        <li>
                                            Upload the order sheet downloaded form Flipkart seller panel but only in xls format
                                        </li>
                                        <li>
                                            Row with the column headers is the first row of sheet. Make sure to keep the spelling of all column header according to instructions.
                                        </li>
                                        <li>
                                            Do not change the formatting of columns.
                                        </li>
                                        <li>
                                            Excel sheet should have these columns with exact same heading as downloaded form panel or mention here : 
                                            Mandatory columns : ORDER ITEM ID,SKU Code,Quantity,Invoice No.,Invoice Amount,PIN Code,Ready to Ship by date
                                            Non mandatory columns:Order Id,Ship to name,Address Line 1,Address Line 2,City,Phone No,
                                        </li>
                                        <li>
                                            Other mandatory columns to be added manually with respective values for each transactions : 
                                            Sales Channel : Channel name for which file is being upload. It should be same(case-sensitive) as the name of sales channel configured.
                                            Payment Type: Its value should be Prepaid or COD.
                                        </li>
                                        <li>
                                            Some non mandatory columns to be added :
                                        </li>
                                        <li>
                                           Net Rate: Mandatory when NR switch for particular channel is off or Event is implemented on that channel and SKU with fixed TP. 
                                        </li>
                                        <li>
                                            Seller Notes : Any remarks about order.
                                        </li>
                                        <li>
                                            AWB No.:Airway bill number. It is used to track order during return and to track order delivery status.
                                        </li>
                                        <li>
                                            PIreferenceNo: Another Identifier to find order.
                                        </li>
                                        <li>
                                            Order MRP: Used to find discount values on order , enter MRP per quantity.
                                        </li>
                                        <li>
                                           Logistic Partner:It makes O2R easy to track deliveries. 
                                        </li>
                                        <li>
                                            Customer Email: It helps to create customer database.
                                        </li>
                                    </ol>
                                    </div>
                                    <div class="snapdealorderupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                    <ol>
                                        <li>
                                           Upload the order sheet downloaded form Snapdeal seller panel but only in xls format.  
                                        </li>
                                        <li>
                                           Row with the column headers is the first row of sheet. Make sure to keep the spelling of all column header according to instructions. 
                                        </li>
                                        <li>
                                            Do not change the formatting of columns.
                                        </li>
                                        <li>
                                            Excel sheet should have these columns with exact same heading as downloaded form panel : 
                                            Mandatory columns : Suborder Id,SKU Code,Order Created Date,Pin Code,Selling Price Per Item,Invoice Code,Payment Mode.
                                            Non mandatory columns:Courier,Reference Code,AWB Number,Customer Name,City,MRP,Mobile No.,Email ID,Handover Date,Address.
                                        </li>
                                        <li>
                                            Other mandatory columns to be added manually with respective values for each transactions : 
                                        </li>
                                        <li>
                                           Sales Channel : Channel name for which file is being upload. It should be same(case-sensitive) as the name of sales channel configured. 
                                        </li>
                                        <li>
                                            Quantity: Number of pieces of each order.
                                        </li>
                                        <li>
                                            Some non mandatory columns to be added : 
                                        </li>
                                        <li>
                                            Net Rate: Mandatory when NR switch for particular channel is off or Event is implemented on that channel and SKU with fixed TP.
                                        </li>
                                        <li>
                                            Seller Notes : Any remarks about order.
                                        </li>
                                        <li>
                                            Secondary OrderID: Another identifier to search for the order.
                                        </li>

                                    </ol>
                                    </div>
                                    <div class="paytmorderupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                        <ol>
                                            <li>
                                                Upload the order sheet downloaded form Paytm seller panel but only in xls format.
                                            </li>
                                            <li>
                                                Row with the column headers is the first row of sheet. Make sure to keep the spelling of all column header according to instructions.
                                            </li>
                                            <li>
                                                Do not change the formatting of columns. 
                                            </li>
                                            <li>
                                                Excel sheet should have these columns with exact same heading as downloaded form panel : 
                                                Mandatory columns : creation date,pincode,item_id,item.sku,item_price,qty,shipping_amount,invoice_id
                                                Non mandatory columns:order_id,customer_firstname,customer_lastname,customer_email,phone,address,city,item_mrp,awb,shipper
                                            </li>
                                            </ol>
                                            <h3 class="text-center">
                                                Other mandatory columns to be added manually with respective values for each transactions : 
                                            </h3>
                                            <ol>
                                                <li>
                                                    Sales Channel : Channel name for which file is being upload.It should be same(case-sensistive) as the name of sales channel configured.
                                                </li>
                                                <li>
                                                    Quantity: Number of pieces of each order.
                                                </li>
                                                <li>
                                                    Payment Type: Its value should be Prepaid or COD.
                                                    Order Shipped Date :Mention shipped date in mm-dd-yyyy format.
                                                </li>
                                                <li>
                                                    Some non mandatory columns to be added : 
                                                    Net Rate: Manadatory when NR switch for particular channel is off or Event is implemented on that channel and SKU with fixed TP.
                                                </li>
                                                <li>
                                                    Seller Notes : Any remarks about order.
                                                    PIreferenceNo : Another identifier to search for the order.
                                                </li>

                                        </ol>
                                    </div>
                                    <div class="limeroadorderupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                        <ol>
                                            <li>
                                                Upload the order sheet downloaded form Limeroad seller panel but only in xls format. 
                                            </li>
                                            <li>
                                                Row with the column headers is the first row of sheet. Make sure to keep the spelling of all column header according to instructions.
                                            </li>
                                            <li>
                                                Do not change the formatting of columns.
                                            </li>
                                            <li>
                                                Excel sheet should have these columns with exact same heading as downloaded form panel : 
                                                Mandatory columns : Order Id,Date Time,Vendor Code,Qty,Order Type
                                                Non mandatory columns:Customer Name,Number,Address,AWB,
                                            </li>
                                        </ol>
                                        <h3 class="text-center">
                                            <b>Other mandatory columns to be added manually with respective values for each transactions : </b>
                                        </h3>
                                        <ol>
                                            <li>
                                                Sales Channel : Channel name for which file is being upload.It should be same(case-sensistive) as the name of sales channel configured.
                                            </li>
                                            <li>
                                                Pincode : Get Zipcode value from address colum in file.
                                            </li>
                                            <li>
                                                Quantity: Number of pieces of each order.
                                            </li>
                                            <li>
                                                InvoiceID: Input invoice number for each order transaction.
                                            </li>
                                            <li>
                                                Order Shipped Date :Mention shipped date in mm-dd-yyyy format.
                                            </li>
                                            <li>
                                                Order SP : Enter SP per quantity . If any extra charges like shipping charges add in SP value.
                                            </li>
                                            </ol>
                                            <h3 class="text-center"><b>Some non mandatory columns to be added :</b></h3>
                                            <ol>
                                                <li>
                                                    Net Rate: Manadatory when NR switch for particular channel is off or Event is implemented on that channel and SKU with fixed TP
                                                </li>
                                                <li>
                                                    Seller Notes : Any remarks about order.
                                                </li>    
                                                <li>
                                                    PIreferenceNo : Another identifier to search for the order.
                                                </li>  
                                                <li>
                                                    Order MRP: Used to find discount values on order , enter MRP per quantity.
                                                </li>
                                                <li>
                                                    Secondary OrderID : Identifier to search order during return and payment.
                                                </li>
                                                <li>
                                                    Logistic Partner : It is used to track order during return and to track order delivery status.
                                                </li> 
                                                <li>
                                                    Customer Email: It helps to create customer database.
                                                </li>  
                                                <li>
                                                    Customer City : It helps to create customer database.
                                                </li>   
                                        </ol>
                                    </div>
                                    <div class="amazonpaymentupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                        <ol>
                                            <li>
                                                Upload the payment sheet from Amazon but <b>only in xls format</b>.  
                                            </li>
                                            <li>
                                                Row with the colum headers is the first row of sheet.Make sure to keep the spelling of all colum header according to instructions.
                                            </li>
                                            <li>
                                                Do not change the formatting of colums.
                                            </li>
                                            <li>
                                                Excel sheet should have these colums with exact same heading as downloaded form panel : 
                                                Mandatory Colums : Date,Order ID,Transaction type,Payment Detail,Amount,SKU
												Non mandatory Colums:
                                            </li>
                                            <li>
                                                Other mandatory colums to be added manually with respective values for each transactions : 
                                                InvoiceId : Put invoice number for order search.
                                            </li>
                                            <li>
                                                No non mandatory colums to be added.
                                            </li>                                            
                                        </ol>

                                    </div>
                                    <div class="flipkartpaymentupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                        <ol>
                                            <li>
                                                Upload the payment sheet from Flipkart but <b>only in .xls format</b>. 
                                            </li>
                                            <li>
                                                Row with the colum headers is the first row of sheet.Make sure to keep the spelling of all colum header according to instructions.
                                            </li>
                                            <li>
                                                Do not change the formatting of columns.
                                            </li>
                                            <li>
                                                Excel sheet should have these colums with exact same heading as downloaded form panel : 
													Mandatory Colums : Order Type,Fulfilment Type,Order item ID,Order item ID,Settlement Date,Settlement Value (Rs.),Seller SKU
													Non mandatory Colums:Order ID/FSN
                                            </li>
                                            <li>
                                                Other mandatory colums to be added manually with respective values for each transactions : 
												InvoiceId : Put invoice number for order search.
                                            </li>
                                            <li>
                                                No non mandatory colums to be added.
                                            </li>                                            
                                        </ol>

                                    </div>
                                    <div class="paytmpaymentupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                        <ol>
                                            <li>
                                                 Upload the payment sheet from Paytm but <b>only in .xls format</b>. 
                                            </li>
                                            <li>
                                                Row with the colum headers is the first row of sheet.Make sure to keep the spelling of all colum header according to instructions.
                                            </li>
                                            <li>
                                                Do not change the formatting of columns.
                                            </li>
                                            <li>
                                                Excel sheet should have these colums with exact same heading as downloaded form panel : 
													Mandatory Colums : Order ID,Order Item ID,Payable Amount
													Non mandatory Colums:Order ID/FSN
											</li>
                                            <li>
                                                Other mandatory colums to be added manually with respective values for each transactions : 
													Payment Date : Date of Payment. Enter value in mm-DD-yyyy format.
                                            </li>
                                            <li>
                                                No non mandatory colums to be added.
                                            </li>                                           
                                        </ol>

                                    </div>
                                    <div class="snapdealpaymentupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                        <ol>
                                            <li>
                                                 Upload the payment sheet from Paytm but <b>only in .xls format</b>. 
                                            </li>
                                            <li>
                                                Row with the colum headers is the first row of sheet.Make sure to keep the spelling of all colum header according to instructions.
                                            </li>
                                            <li>
                                                Do not change the formatting of columns.
                                            </li>
                                            <li>
                                                 Excel sheet should have these colums with exact same heading as downloaded form panel : 
													Mandatory Colums : Type,Transaction ID,Reason,SKU,Invoice Number,Net  Payable,Payment Date,
													Non mandatory Colums:
                                            </li>
                                            <li>
                                                No mandatory colums to be added.
                                            </li>
                                            <li>
                                                No non mandatory colums to be added.
                                            </li>
                                         </ol>

                                    </div>
                                    <div class="limeroadpaymentupload">
                                        <h2 class="text-center">
                                            <b>Follow These Instructions</b>
                                        </h2>
                                        <ol>
                                            <li>
                                                Upload the payment sheet from Paytm but <b>only in .xls format</b>. 
                                            </li>
                                            <li>
                                                Row with the colum headers is the first row of sheet.Make sure to keep the spelling of all colum header according to instructions.
                                            </li>
                                            <li>
                                                Do not change the formatting of columns.
                                            </li>
                                            <li>
                                                Excel sheet should have these colums with exact same heading as downloaded form panel : 
													Mandatory Colums : Sales / Sales Return,Order ID,Vendor Invoice Number,Outstanding Payable to Vendors (Final)
													Non mandatory Colums:
                                            </li>
                                            <li>
                                                Other mandatory colums to be added manually with respective values for each transactions : 
													Payment Date : Date of Payment. Enter value in mm-DD-yyyy format.
                                            </li>
                                            <li>
                                                No non mandatory colums to be added.
                                            </li>
                                        </ol>

                                    </div>
                                </div>
                            </div>
				                </div>
				            </div>
				        </div>
						

		
		
		
	</div>
<script type="text/javascript" language="javascript">
function checkfile(sender) {
	var validExts = new Array(".xls");
    var fileExt = sender.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
      alert("Invalid file selected, valid files are of " +
               validExts.toString() + " types.");
      file.form.reset();
      return false;
    }
    else return true;
}
function checkfile1(sender) {
	var validExts = new Array(".xls");
    var fileExt = sender.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
      alert("Invalid file selected, valid files are of " +
               validExts.toString() + " types.");
      file1.form.reset();
      return false;
    }
    else return true;
}
</script>
<script type="text/javascript">
      $(document).on('change', '.div-toggle', function() {
		  var target = $(this).data('target');
		  var show = $("option:selected", this).data('show');
		  $(target).children().addClass('hide');
		  $(show).removeClass('hide');
	  });
</script>

<!-- <script type="text/javascript">
	function extCheck(){
		var elem= document.getElementById("file");
		var re_text = /\.xls/i;
		if (elem.value.search(re_text) == -1){
			alert ("Incorrect File extension\n Should be xls ");       
			file.form.reset();
	        return false;
	    } else
	    	return true;
	}
</script> -->

</body>
</html>
