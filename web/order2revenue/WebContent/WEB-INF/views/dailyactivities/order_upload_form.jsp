<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<link rel='stylesheet' href='/O2R/seller/css/nprogress.css' />
<script src='/O2R/seller/js/nprogress.js'></script>
<!-- <script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->
<script src="/O2R/seller/js/jquery.ui.widget.js"></script>
<script src="/O2R/seller/js/jquery.iframe-transport.js"></script>
<script src="/O2R/seller/js/jquery.fileupload.js"></script>
<script type="text/javascript">
	$(function() {
		var downloadValue = '${downloadValue}';
		var uploadValue = '${uploadValue}';
		if (uploadValue != null && uploadValue.length != 0)
			$("#sheetValue").val(uploadValue);
		else
			$("#downloadreporttype").val(downloadValue);
	});
	$("#fileupload1").submit(
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
					data : $("#fileupload1").serialize(),
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
		var downloadValue = '${downloadValue}';
		var uploadValue = '${uploadValue}';
		if (uploadValue != null && uploadValue.length != 0)
			$("#sheetValue").val(uploadValue);
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
							<!-- <select class="form-control" id="downloadreporttype"
								name="downloadreporttype">
								<option value="">Select file to download</option>
								<option value="ordersummary">Order Summary</option>
								<option value="orderposummary">Order PO Summary</option>
								<option value="paymentsummary">Payment Summary</option>
								<option value="popaymentsummary">PO Payment Summary</option>
								<option value="returnsummary">Return Summary</option>
								<option value="gatepasssummary">GatePass Summary</option>
								<option value="debitnotesummary">Debit Note</option>
								<option value="productsummary">Product Summary</option>
								<option value="editproductsummary">Edit Product Summary</option>
								<option value="skumappingsummary">MP Channels SKU Mapping</option>
								<option value="productconfigsummary">Product Config
									Summary</option>
								<option value="inventorysummary">Inventory Summary</option>
								<option value="expensesummary">Expense Summary</option>
							</select> -->
							<iframe id="my_iframe" style="display:none;"></iframe>
							<select class="form-control" id="downloadreporttype"
								name="downloadreporttype">
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
											<option value="">Select file to download</option>
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
										</select>
									</div>
									<div class="col-md-4">															
										<input name="files[0]" type="file"	class="form-control" required/>											
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
	</div>
</body>
</html>