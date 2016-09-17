<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel='stylesheet' href='/O2R/seller/css/nprogress.css' />
<script src='/O2R/seller/js/nprogress.js'></script>
<!-- <script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->
<script src="/O2R/seller/js/jquery-2.1.1.js"></script>
<script src="/O2R/seller/js/jquery.ui.widget.js"></script>
<script src="/O2R/seller/js/jquery.iframe-transport.js"></script>
<script src="/O2R/seller/js/jquery.fileupload.js"></script>
<style type="text/css">
.para {
	text-align: justify;
}

.left {
	float: left;
	width: 24%;
	margin: 0px;
	position: relative;
	left: 24%;
	top: 8px;
	color: #fff;
	border: 1px dashed #fff;
}

.right {
	float: right;
	width: 24%;
	margin: 0px;
	position: relative;
	right: 24%;
	top: 8px;
	color: #fff;
	border: 1px dashed #fff;
}

.heading {
	position: relative;
	top: -7px;
}

.hide {
	display: none;
}
</style>
<!-- <script type="text/javascript">
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
				
				$('#progressSpiner').show();
				
				$.ajax({
					xhr : function() {
						$('#uploadReport').show();
						$('#progrssDropdown').dropdown("toggle");
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
</script> -->
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
				
				$("#isProgress").val("true");
				$("#isProgress").trigger("change");
				setProgress1();
				$('#progressSpiner').attr('class', 'fa fa-refresh fa-spin');
				$('#progressSpiner').attr('style', 'color:#1ab394;');
				
				$('#uploadReport').show();
				NProgress.configure({
					parent : "#bar1"
				});
				NProgress.start();
				
				//var progressBar = document.getElementById("progress"),
			    xhr = new XMLHttpRequest();
				xhr.open("POST", "saveSheet.html", true);
				xhr.upload.onprogress = function (evt) {
					$("#bar1").show();
					if (evt.lengthComputable) {
						var percentComplete = evt.loaded / evt.total;
						var progress = parseInt(evt.loaded / evt.total
								* 100, 10);
						NProgress.set(progress / 100);
					}
				    /* if (e.lengthComputable) {
				        progressBar.max = e.total;
				        progressBar.value = e.loaded;
				    } */
				}
				xhr.upload.onloadstart = function (e) {
				    //progressBar.value = 0;
				    NProgress.set(0);
				}
				xhr.upload.onloadend = function (e) {
				    //progressBar.value = e.loaded;
				    NProgress.set(e.loaded);
				    //$('#uploadReport').hide();
				    //$("#bar1").hide();
				}
				xhr.send(new FormData());
				
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
<body >		
	<c:if test="${seller.sellerAccount.orderBucket > 0}">
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

							<iframe id="my_iframe" style="display: none;"></iframe>
							<select class="form-control" id="downloadreporttype"
								name="downloadreporttype" required autocomplete="off">
								<option value="">Select file to download</option>
								<option value="MP_Order_Upload">MP Order Upload</option>
								<option value="PO_Order_Upload">PO Order Upload</option>
								<option value="MP_Payment_Upload">MP Payment Upload</option>
								<option value="Po_Payment_Upload">PO Payment Upload</option>
								<option value="MP_Return_Upload">MP Return Upload</option>
								<option value="PO_Gatepass_Uplooad">PO GatePass Upload</option>
								<option value="Create_Parent_Product">Create Parent
									Product</option>
								<option value="Product_Edit">Edit Product Upload</option>
								<option value="MP_Vendor_SKU_Mapping">Vendor SKU
									Mapping</option>
								<option value="PO_Product_Config">PO Product Config</option>
								<option value="inventorysummary">Inventory Upload</option>
								<option value="Expense_Upload">Expense Upload</option>
								<option value="Event_SKU">Event SKU Upload</option>
								<option value="Create_Inventory_Groups">Create
									Inventory Groups</option>
								<option value="Create_Product_Category">Create Product
									Category</option>
								<option value="ProdCat_Comm_Mapping">Category wise
									Commission Upload</option>
								<option value="Tax_Category_Mapping">Tax Category Upload</option>
							</select>
						</div>
						<div class="col-md-4">
							<button class="btn btn-success " type="button" id="download1">
								<i class="fa fa-download"></i>&nbsp;&nbsp;<span class="bold">Download</span>
							</button>
						</div>
						<div class="col-md-2"></div>
					</div>


					<div class="col-lg-12">
						<div class="form-group">

							<form:form id="fileupload1" method="post" action="saveSheet.html"
								modelAttribute="uploadForm" enctype="multipart/form-data"
								class="form-horizontal">

								<!-- 	<input id="addFile" type="button" value="Add File" /> -->
								<div class="col-md-4">
									<select class="form-control" id="sheetValue" name="sheetValue"
										required autocomplete="off">
										<option value="">Select file to upload</option>
										<option value="ordersummary">MP Order Upload</option>
										<option value="orderPoSummary">PO Order Upload</option>
										<option value="paymentSummary">MP Payment Upload</option>
										<option value="returnSummary">MP Return Upload</option>
										<option value="gatepassSummary">PO GatePass Upload</option>
										<option value="productSummary">Create Parent Product</option>
										<option value="editProductSummary">Edit Product
											Upload</option>
										<option value="vendorSKUMapping">Vendor SKU Mapping</option>
										<option value="productConfigSummary">PO Product
											Config</option>
										<option value="inventorySummary">Inventory Upload</option>
										<option value="poPaymentSummary">PO Payment Upload</option>
										<option value="expenseSummary">Expense Upload</option>
										<option value="EventSKUSummary">Event SKU Upload</option>
										<option value="CreateInventoryGroups">Create
											Inventory Groups</option>
										<option value="CreateProCat">Create Product Category</option>
										<option value="prodCat_Comm_Mapping">Category wise Commission Upload</option>
										<option value="product_Tax_Mapping">Tax Category Upload</option>
									</select>
								</div>
								<div class="col-md-4">
									<input name="files[0]" type="file" id="file1"
										class="form-control" required
										accept="application/vnd.ms-excel" onchange="checkfile1(this);" />
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
			<div class="hr-line left"></div>
			OR
			<div class="hr-line right"></div>
		</h3>
	</div>

	<div class="col-lg-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>Channel Upload</h5>
			</div>
			<div class="ibox-content overflow-h">
				<div class="form-group">
					<form:form id="fileupload2" method="post" action="saveSheet.html"
						modelAttribute="uploadForm" enctype="multipart/form-data"
						class="form-horizontal">
						<div class="col-md-4">
							<select class="form-control div-toggle" data-target=".my-info"
								id="sheetValue" name="sheetValue" required autocomplete="off"
								style="width: 97%; margin-left: 14px">
								<option value="" data-show=".upload">Upload</option>
								<option value="Unicommerce_Order"
									data-show=".unicommerceorderupload">Unicommerce Order
									Upload</option>
								<option value="Snapdeal_Order" data-show=".snapdealorderupload">Snapdeal
									Order Upload</option>
								<option value="Snapdeal_Payment"
									data-show=".snapdealpaymentupload">Snapdeal Payment</option>
								<option value="Flipkart_Order" data-show=".flipkartorderupload">Flipkart
									Order Upload</option>
								<option value="Flipkart_Payment"
									data-show=".flipkartpaymentupload">Flipkart Payment</option>
								<option value="PayTM_Order" data-show=".paytmorderupload">PayTM
									Order Upload</option>
								<option value="PayTM_Payment" data-show=".paytmpaymentupload">PayTM
									Payment Upload</option>
								<option value="Amazon_Order" data-show=".amazonorderupload">Amazon
									Order Upload</option>
								<option value="Amazon_Payment" data-show=".amazonepaymentupload">Amazon
									Payment Upload</option>
								<option value="Limeroad_Order" data-show=".limeroadorderupload">Limeroad
									Order Upload</option>
								<option value="Limeroad_Payment"
									data-show=".limeroadpaymentupload">Limeroad Payment
									Upload</option>
							</select>
						</div>
						<div class="col-md-4">
							<input name="files[0]" type="file" id="file" class="form-control"
								required accept="application/vnd.ms-excel"
								onchange="checkfile(this);" />
						</div>
						<div class="col-md-2">
							<button class="btn btn-success " type="submit"
								id="upload4Channel">
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
								<li><b style="color: #000;">Note : </b><font color="green">Upload
										Channel files with 1st row as headers</font></li>
							</ul>
						</div>
						<div class="amazonorderupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
							<ol>
                        <li>
                            Bulk Download pending orders from Amazon Seller Panel,by
                            <ol class="d">
                                <li>
                                    Visiting Orders->Order Reports Tab
                                    </li>
                                    <li>
                                        Selecting No of Days for which orders need to be downloaded
                                    </li>

                                    <li>
                                        Click on Request Report
                                    </li>
                                    <li>
                                        The requested report will be displayed in the below list.
                                    </li>
                                    
                                </ol>
                            </li>
                            <li>
                               Open the .txt downloaded file
                            </li>
                             <li>
                                Copy and paste all contents to a new Excel File
                            </li>
                            <li>
                                Ensure that the 1st row of file is the “headers”
                            </li>
                            <li>
                                Do not change the formatting of columns or any headers.
                            </li>
                            <li>
                                Add the following "Case Sensitive" headers (without “ ”) at the end of 1st row of the file and input its corresponding value against every row 
                                <ol class="d">
                                    <li>
                                        <b>Mandatory Values (to be added manually by seller)</b>
                                        <ol class="c">
                                            <li>
                                               <b class="color">Sales Channel</b> -
                                               Channel name for which file is being uploaded.It should be same(case-sensitive) as the name of sales channel configured.
                                            </li>
                                            <li>
                                                <b class="color">InvoiceID</b> -
                                               Mention invoice id for each order
                                            </li>
                                            <li>
                                                <b class="color">Order Shipped Date</b> – Mention shipped date in "MM-DD-YYYY" format.
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                        <b>Non-Mandatory (scenario specific or good to have attributes)</b>
                                        <ol class="c">
                                            <li>
                                                <b class="color">Net Rate</b> - Mandatory when N/R switch is off,or an Event is configured for all or any particular SKU’s at fixed Transfer Price.
                                            </li>
                                            <li>
                                                <b class="color">Seller Notes</b> - "Any customized remarks about order."
                                            </li>
                                            <li>
                                                <b class="color">AWB No</b> - "Airway bill number. It is used to track order during return and to track order delivery status."
                                            </li>
                                            <li>
                                                <b class="color">Order MRP</b> - "Used to find discount values on order,enter MRP per quantity."
                                            </li>
                                        </ol>
                                    </li>
                                </ol>
                            </li>
                            <li>
                                Save the file in ".xls" (97-2003) format
                            </li>
                            <li>
                                Import the file by selecting "Amazon Order Upload" from the dropdown list
                            </li>
                            <li>
                                Make sure to keep the spelling & spacing of all column headers exactly as stated in the instructions.   
                            </li>
                            <li>
                                The file to be imported should have the following columns by default
                                order-id,purchase-date, sku,quantity-purchased, item-price,shipping-price,ship-postal-code,payment-method
                            </li>
                            <li>
                                Watch Tutorial
                            </li>
                        </li>
                    </ol>

						</div>
						<div class="flipkartorderupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
							<ol>
                        <li>
                            Bulk Download pending orders from Flipkart Seller Panel,before generating shipping label 
                        </li>
                        <li>
                            Open the downloaded file
                        </li>
                        <li>
                            Ensure that the 1st row of file is the "headers"
                        </li>
                        <li>
                            Do not change the formatting of columns or any headers.
                        </li>
                        <li>
                                Add the following "Case Sensitive" headers (without “ ”) at the end of 1st row of the file and input its corresponding value against every row 
                                <ol class="d">
                                    <li>
                                        <b>Mandatory Values (to be added manually by seller)</b>
                                        <ol class="c">
                                            <li>
                                               <b class="color">Sales Channel</b> -
                                               Channel name for which file is being uploaded.It should be same(case-sensitive) as the name of sales channel configured.
                                            </li>
                                            <li>
                                                <b class="color">Payment Type</b> -
                                               Valid values PREPAID,COD or OTHERS
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                        <b>Non-Mandatory (scenario specific or good to have attributes)</b>
                                        <ol class="c">
                                            <li>
                                                <b class="color">Net Rate</b> - Mandatory when N/R switch is off,or an Event is configured for all or any particular SKU’s at fixed Transfer Price.
                                            </li>
                                            <li>
                                                <b class="color">Seller Notes</b> - Any customized remarks about order.
                                            </li>
                                            <li>
                                                <b class="color">AWB No</b> - Airway bill number. It is used to track order during return and to track order delivery status.
                                            </li>
                                            <li>
                                                <b class="color">Order MRP</b> - Used to find discount values on order,enter MRP per quantity.
                                            </li>
                                            <li>
                                                <b class="color">Logistic Partner</b> - Shipping Provider
                                            </li>
                                            <li>
                                                <b class="color">Customer Email</b> - If provided, adds to the customer database.
                                            </li>
                                        </ol>
                                    </li>
                                </ol>
                            </li>
                            <li>
                                Save the file in ".xls" (97-2003) format.
                            </li>   
                            <li>
                                Import the file by selecting "Flipkart Order Upload" from the dropdown list
                            </li> 
                            <li>
                                Make sure to keep the spelling & spacing of all column headers exactly as stated in the instructions
                            </li>
                            <li>
                                The file to be imported should have the following columns by default ORDER ITEM ID,SKU Code,Quantity,Invoice No.,Invoice Amount,PIN Code,Ready to Ship by date.
                            </li>
                            <li>
                                Watch Tutorial.
                            </li>
                        </ol>
						</div>
						<div class="snapdealorderupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
						  <ol>
                            <li>
                                Bulk Download pending orders from snapdeal Seller Panel, before generating shipping label 
                            </li>
                            <li>
                                Open the downloaded file
                            </li>
                            <li>
                                Ensure that the 1st row of file is the “headers”
                            </li>
                            <li>
                                Do not change the formatting of columns or any headers.
                            </li>
                            <li>
                                Add the following "Case Sensitive" headers (without “ ”) at the end of 1st row of the file and input its corresponding value against every row 
                                <ol class="d">
                                    <li>
                                        <b>Mandatory Values (to be added manually by seller)</b>
                                        <ol class="c">
                                            <li>
                                               <b class="color">Sales Channel</b> -
                                               Channel name for which file is being uploaded.It should be same(case-sensitive) as the name of sales channel configured.
                                            </li>
                                            <li>
                                                <b class="color">Quantity</b> -
                                               Quantity of orders.In most cases it is 1.
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                        <b>Non-Mandatory (scenario specific or good to have attributes)</b>
                                            <ol class="c">
                                                <li>
                                                    <b class="color">Net Rate</b> - Mandatory when N/R switch is off,or an Event is configured for all or any particular SKU’s at fixed Transfer Price.
                                                </li>
                                                <li>
                                                    <b class="color">Seller Notes</b> - Any customized remarks about order.
                                                </li>
                                                <li>
                                                    <b class="color">AWB No</b> - Airway bill number.It is used to track order during return and to track order delivery status.
                                                </li>
                                                <li>
                                                    <b class="color">Secondary Order ID </b> - any secondary order reference ID.
                                                </li>
                                            </ol>
                                        </li>
                                    </ol>
                                </li>
                                <li>
                                    Save the file in ".xls" (97-2003) format
                                </li>
                                <li>
                                    Import the file by selecting "Snapdeal Order Upload" from the dropdown list
                                </li>
                                <li>
                                    Make sure to keep the spelling & spacing of all column headers exactly as stated in the instructions.
                                </li>
                                <li>
                                    The file to be imported should have the following columns by default
                                    Suborder Id,SKU Code,Order Created Date,Pin Code,Selling Price Per Item,Invoice Code,Payment Mode
                                </li>
                                <li>
                                    Watch Tutorial
                                </li>
                            </ol>
						</div>
						<div class="paytmorderupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
							  <ol>
                            <li>
                                Bulk Download pending orders from paytm Seller Panel,before generating shipping label 
                            </li>
                            <li>
                                Open the downloaded file
                            </li>
                            <li>
                                Ensure that the 1st row of file is the "headers"
                            </li>
                            <li>
                                Do not change the formatting of columns or any headers.
                            </li>
                            <li>
                                Add the following "Case Sensitive" headers (without “ ”) at the end of 1st row of the file and input its corresponding value against every row 
                                <ol class="d">
                                    <li>
                                        <b>Mandatory Values (to be added manually by seller)</b>
                                        <ol class="c">
                                            <li>
                                               <b class="color">Sales Channel</b> -
                                               Channel name for which file is being uploaded.It should be same(case-sensitive) as the name of sales channel configured.
                                            </li>
                                            <li>
                                                <b class="color">Payment Type</b>Valid Values, PREPAID,COD or OTHERS
                                            </li>
                                            <li>
                                                <b class="color">Order Shipped Date</b> -
                                               Mention shipped date in “MM-DD-YYYY” format.
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                        <b>Non-Mandatory (scenario specific or good to have attributes)</b>
                                            <ol class="c">
                                                <li>
                                                    <b class="color">Net Rate</b> - Mandatory when N/R switch is off,or an Event is configured for all or any particular SKU’s at fixed Transfer Price.
                                                </li>
                                                <li>
                                                    <b class="color">Seller Notes</b> - Any customized remarks about order.
                                                </li>
                                            </ol>
                                        </li>
                                    </ol>
                                </li>
                                <li>
                                    Save the file in ".xls" (97-2003) format
                                </li>
                                <li>
                                    Import the file by selecting "Paytm Order Upload" from the dropdown list
                                </li>
                                <li>
                                    Make sure to keep the spelling & spacing of all column headers exactly as stated in the instructions.
                                </li>
                                <li>
                                    The file to be imported should have the following columns by default
                                    creation date,pincode,item_id,item.sku,item_price,qty, shipping_amount,invoice_id
                                </li>
                                <li>
                                    Watch Tutorial
                                </li>
                            </ol>
						</div>
						<div class="unicommerceorderupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
							<ol>
                                <li>
                                    Bulk Download Sale Orders report from Unicommerce Panel for all shipped orders
                                </li>
                                <li>
                                    Open the downloaded file
                                </li>
                                <li>
                                    Ensure that the 1st row of file is the "headers"
                                </li>
                                <li>
                                    Do not change the formatting of columns or any headers.
                                </li>
                                <li>
                                    Add the following "Case Sensitive" headers (without “ ”) at the end of 1st row of the file and input its corresponding value against every row 
                                    <ol class="d">
                                        <li>
                                            <b>Mandatory Values (to be added manually by seller)</b>
                                            <ol class="c">
                                                <li>
                                                   <b class="color">O2R Channel</b> -
                                                   Channel name for which orders are uploaded. It should be exactly the same(case-sensitive) as the name of sales channel configured with Order2Revenue. 
                                                </li>
                                            </ol>
                                        </li>
                                        <li>
                                            <b>Non-Mandatory (scenario specific or good to have attributes)</b>
                                                <ol class="c">
                                                    <li>
                                                        <b class="color">Net Rate</b> - Mandatory when N/R switch is off,or an Event is configured for all or any particular SKU’s at fixed Transfer Price.
                                                    </li>
                                                    <li>
                                                        <b class="color">Seller Notes</b> - Any customized remarks about order.
                                                    </li>
                                                    <li>
                                                        <b class="color">Order MRP </b> - 
                                                        Used to find discount values on order, enter MRP per quantity
                                                    </li>
                                                    <li>
                                                        <b class="color">PIreferenceNo</b> - 
                                                        Additional identifier for order search
                                                    </li>
                                                </ol>
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                        Save the file in ".xls" (97-2003) format
                                    </li>
                                    <li>
                                        Import the file by selecting "Unicommerce Order Upload" from the dropdown list
                                    </li>
                                    <li>
                                        Make sure to keep the spelling & spacing of all column headers exactly as stated in the instructions.
                                    </li>
                                    <li>
                                        The file to be imported should have the following columns by default
                                        Display Order Code, COD, Invoice Code, Shipping Address Pincode, Item SKU Code, Channel Name, Total Price, Packet Number, Order Date as dd/mm/yyyy hh:MM:ss, Dispatch Date
                                    </li>
                                    <li>
                                        Watch Tutorial
                                    </li>
                                </ol>
						</div>
						<div class="limeroadorderupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
							 <ol>
                                <li>
                                    Bulk Download pending orders from limeroad Seller Panel, before generating shipping label 
                                </li>
                                <li>
                                    Open the downloaded file
                                </li>
                                <li>
                                    Ensure that the 1st row of file is the "headers"
                                </li>
                                <li>
                                    Do not change the formatting of columns or any headers.
                                </li>
                                <li>
                                    Add the following "Case Sensitive" headers (without “ ”) at the end of 1st row of the file and input its corresponding value against every row 
                                    <ol class="d">
                                        <li>
                                            <b>Mandatory Values (to be added manually by seller)</b>
                                            <ol class="c">
                                                <li>
                                                   <b class="color">Sales Channel</b> -
                                                   Channel name for which file is being upoaded.It should be same(case-sensitive) as the name of sales channel configured.
                                                </li>
                                                <li>
                                                    <b class="color">InvoiceID</b>Input invoice ID for each order transaction.
                                                </li>
                                                <li>
                                                    <b class="color">Order Shipped Date</b> -
                                                   Mention shipped date in “MM-DD-YYYY” format.
                                                </li>
                                                <li>
                                                    <b class="color">Pincode</b>Shipping Pin Code.The same is available in address column.The seller can easily use the RIGHT formula to extract pin code values into this column. 
                                                </li>
                                            </ol>
                                        </li>
                                        <li>
                                            <b>Non-Mandatory (scenario specific or good to have attributes)</b>
                                                <ol class="c">
                                                    <li>
                                                        <b class="color">Net Rate</b> - Mandatory when N/R switch is off,or an Event is configured for all or any particular SKU’s at fixed Transfer Price.
                                                    </li>
                                                    <li>
                                                        <b class="color">Seller Notes</b> - Any customized remarks about order.
                                                    </li>
                                                    <li>
                                                        <b class="color">Secondary OrderID </b> - Additional Order Search Identifier
                                                    </li>
                                                    <li>
                                                        <b class="color">Order MRP </b> - Used to find discount values on order,enter MRP per quantity.
                                                    </li>
                                                    <li>
                                                        <b class="color">Logistic Partner </b> - Shipping Provider
                                                    </li>
                                                    <li>
                                                        <b class="color"> Customer Email</b> - If provided, adds to the customer database.
                                                    </li>
                                                    <li>
                                                        <b class="color">Customer City</b> - City where the order is shipped
                                                    </li>
                                                </ol>
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                        Save the file in ".xls" (97-2003) format
                                    </li>
                                    <li>
                                        Import the file by selecting "limeroad Order Upload" from the dropdown list
                                    </li>
                                    <li>
                                        Make sure to keep the spelling & spacing of all column headers exactly as stated in the instructions.
                                    </li>
                                    <li>
                                        The file to be imported should have the following columns by default
                                        Order Id, Date Time, Vendor Code,Price,Qty,Customer Name, Number,Address,Order Type,AWB 
                                    </li>
                                    <li>
                                        Watch Tutorial
                                    </li>
                                </ol>
						</div>
						<div class="amazonepaymentupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
						  <ol>
                                    <li>
                                        Download Payment Detail File from Amazon Seller Panel.
                                    </li>
                                    <li>
                                        Ensure that the 1st row of file is the "headers"
                                    </li>
                                    <li>
                                        Do not change the formatting of columns or any headers.
                                    </li>
                                    <li>
                                        The downloaded excel file will by default have the following columns with exact same heading. Ensure      the same are present.<br>
                                        •   Mandatory Column (Already present in downloaded file)
                                        <ol class="d">
                                            <li>
                                                  Date
                                            </li>
                                            <li>
                                                Order ID
                                            </li>
                                            <li>
                                                SKU
                                            </li>
                                            <li>
                                                Transaction type
                                            </li>
                                            <li>
                                                Payment Detail
                                            </li>
                                            <li>
                                                Amount
                                            </li>
                                            
                                        </ol>
                                    </li>
                                    <li>
                                         Save the file in <b>.xls(97-2003)</b> format. 
                                    </li>
                                    <li>
                                        Simply Upload the file after selecting <b>"Amazon Payment Upload"</b> from the dropdown list on the Import Page.
                                    </li>
                                    <li>
                                        Watch Tutorial
                                    </li>
                                </ol>

						</div>
						<div class="flipkartpaymentupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
							 <ol>
                                    <li>
                                        Download Payment Detail File from Flipkart Seller Panel.
                                    </li>
                                    <li>
                                        Ensure that the 1st row of file is the "headers"
                                    </li>
                                    <li>
                                        Do not change the formatting of columns or any headers.
                                    </li>
                                    <li>
                                        The downloaded excel file will by default have the following columns with exact same heading. Ensure      the same are present.<br>
                                        •   Mandatory Column (Already present in downloaded file)
                                        <ol class="d">
                                            <li>
                                                Order Type
                                            </li>
                                            <li>
                                                Fulfilment Type
                                            </li>
                                            <li>
                                                Seller SKU 
                                            </li>
                                            <li>
                                                 Order ID/FSN
                                            </li>
                                            <li>
                                                Order item ID
                                            </li>
                                            <li>    
                                               Settlement Date
                                            </li>
                                            <li>
                                             Settlement Value (Rs.)
                                            </li>
                                            
                                        </ol>
                                    </li>
                                    <li>
                                         Save the file in <b>.xls(97-2003)</b> format. 
                                    </li>
                                    <li>
                                        Simply Upload the file after selecting <b>"Amazon Payment Upload"</b> from the dropdown list on the Import Page.
                                    </li>
                                    <li>
                                        Watch Tutorial
                                    </li>
                                </ol>

						</div>
						<div class="paytmpaymentupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
						 <ol>
                                    <li>
                                        Download Payment Detail File from Flipkart Seller Panel.
                                    </li>
                                    <li>
                                        Ensure that the 1st row of file is the "headers"
                                    </li>
                                    <li>
                                        Do not change the formatting of columns or any headers.
                                    </li>
                                    <li>
                                        The downloaded excel file will by default have the following columns with exact same heading. Ensure      the same are present.<br>
                                        •   Mandatory Column (Already present in downloaded file)
                                        <ol class="d">
                                            <li>
                                                Order ID
                                            </li>
                                            <li>
                                                Order Item ID
                                            </li>
                                            <li>
                                                Merchant SKU
                                            </li>
                                            <li>
                                                Payment Date
                                            </li>
                                            <li>
                                               Payable Amount
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                         Save the file in <b>.xls(97-2003) </b>format. 
                                    </li>
                                    <li>
                                        Simply Upload the file after selecting <b>"Paytm Payment Upload"</b> from the dropdown list on the Import Page.
                                    </li>
                                    <li>
                                        Watch Tutorial
                                    </li>
                                </ol>

						</div>
						<div class="snapdealpaymentupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
							 <ol>
                                    <li>
                                        Download Payment Detail File from Snapdeal Seller Panel.
                                    </li>
                                    <li>
                                        Ensure that the 1st row of file is the "headers"
                                    </li>
                                    <li>
                                        Do not change the formatting of columns or any headers.
                                    </li>
                                    <li>
                                        The downloaded excel file will by default have the following columns with exact same heading. Ensure      the same are present.<br>
                                        •   Mandatory Column (Already present in downloaded file)
                                        <ol class="d">
                                            <li>
                                                Type
                                            </li>
                                            <li>
                                                Transaction ID
                                            </li>
                                            <li>
                                                Reason
                                            </li>
                                            <li>
                                                SKU
                                            </li>
                                            <li>
                                               Invoice Number
                                            </li>
                                            <li>
                                                Net  Payable
                                            </li>
                                            <li>
                                                Payment Date
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                         Save the file in<b> .xls(97-2003) </b>format. 
                                    </li>
                                    <li>
                                        Simply Upload the file after selecting <b>"Snapdeal Payment Upload" </b>from the dropdown list on the Import Page.
                                    </li>
                                    <li>
                                        Watch Tutorial
                                    </li>
                                </ol>

						</div>
						<div class="limeroadpaymentupload">
							<h2 class="text-center">
								<b>Follow These Instructions</b>
							</h2>
							 <ol>
                                    <li>
                                        Open the file received via mail from Limeroad for the payments received
                                    </li>
                                    <li>
                                        Ensure that the 1st row of file is the "headers"
                                    </li>
                                    <li>
                                        Do not change the formatting of columns or any headers.
                                    </li>
                                    <li>
                                        The downloaded excel file will by default have the following columns with exact same heading. Ensure      the same are present.<br>
                                        •   Mandatory Column (Already present in downloaded file)
                                        <ol class="d">
                                            <li>
                                                Sales / Sales Return
                                            </li>
                                            <li>
                                                Order ID
                                            </li>
                                            <li>
                                                Vendor Invoice Number
                                            </li>
                                            <li>
                                                Style Code
                                            </li>
                                            <li>
                                               Outstanding Payable to Vendors (Final)
                                            </li>
                                        </ol>
                                    </li>
                                    <li>
                                        At the end of the 1st row.Add the following: 
                                        "Payment Date": Date of Payment received.Ensure to enter the value as <b>"MM-DD-YYYY"</b> format

                                    </li>
                                    <li>
                                        Save the file in <b>.xls(97-2003)</b> format
                                    </li>
                                    <li>
                                        Simply Upload the file after selecting <b> "Limeroad Payment Upload" </b>from the dropdown list on the Import Page.
                                    </li>
                                    <li>
                                        Watch Tutorial
                                    </li>
                                </ol>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>





	</div>
	</c:if>
	<c:if test="${seller.sellerAccount.orderBucket < 1}">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title text-center">
						<h3 style="color: red;" >Access Restricted !</h3>
					</div>
					<div class="ibox-content overflow-h">
						<div class="col-lg-12 text-center">
							<img alt="" src="/O2R/seller/img/AccessDenied.jpg">
						</div>							
					</div>
				</div>
			</div>
		</div>
	</c:if>
		
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
