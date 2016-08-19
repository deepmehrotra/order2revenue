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
		                                        <select class="form-control" id="sheetValue" name="sheetValue" required autocomplete="off">
		                                            <option value="">Upload</option>
		                                            <option value="Snapdeal_Order">Snapdeal Order Upload</option>
		                                            <option value="Snapdeal_Payment">Snapdeal Payment</option>
													<option value="Flipkart_Order">Flipkart Order Upload</option>
													<option value="Flipkart_Payment">Flipkart Payment</option>
													<option value="PayTM_Order">PayTM Order Upload</option>
													<option value="PayTM_Payment">PayTM Payment Upload</option>
													<option value="Amazon_Order">Amazon Order Upload</option>											
													<option value="Amazon_Payment">Amazon Payment Upload</option>
													<option value="Limeroad_Order">Limeroad Order Upload</option>
													<option value="Limeroad_Payment">Limeroad Payment Upload</option>
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
		                            <div class="col-lg-12">
		                                <p class="m-t-sm">
		                                <b>Note : </b><font color="green">Upload Channel files with 1st row as headers</font>
		                            </p>
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
