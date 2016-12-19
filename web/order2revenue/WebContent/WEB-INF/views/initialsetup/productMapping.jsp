<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script src="/O2R/seller/js/jquery-2.1.1.js"></script>

<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
<script
	src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
<!-- Data Tables -->
<link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css"
	rel="stylesheet">
<link
	href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css"
	rel="stylesheet">
<style>
body.DTTT_Print {
	background: #fff;
}

.DTTT_Print #page-wrapper {
	margin: 0;
	background: #fff;
}

button.DTTT_button, div.DTTT_button, a.DTTT_button {
	border: 1px solid #e7eaec;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

button.DTTT_button:hover, div.DTTT_button:hover, a.DTTT_button:hover {
	border: 1px solid #d2d2d2;
	background: #fff;
	color: #676a6c;
	box-shadow: none;
	padding: 6px 8px;
}

.dataTables_filter label {
	margin-right: 5px;
}
</style>
</head>

<body>
	<div id="wrapper">
		<jsp:include page="../sidenavigation.jsp"></jsp:include>
		<div id="page-wrapper" class="gray-bg">
			<jsp:include page="../globalheader.jsp"></jsp:include>
			<div class="wrapper wrapper-content animated fadeInRight"
				id="centerpane">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox float-e-margins">
							<div class="ibox-title">
								<h5>SKU Mappings(${productMappingCount})</h5>
								<c:if test="${productMappingCount > 0 }">
									<c:choose>
										<c:when test="${productMappingCount >= listSize}">
											<label class="m-l-lg">${listSize-499} - ${listSize} of ${productMappingCount}</label>
										</c:when>
										<c:when test="${productMappingCount < listSize}">
											<label class="m-l-lg">${listSize-499} - ${productMappingCount} of ${productMappingCount}</label>
										</c:when>																			
									</c:choose>									
								</c:if>
								<c:if test="${productMappingCount == 0 }">
									<label class="m-l-lg">0 - 0 of 0</label>
								</c:if>
								<div class="ibox-tools">
									<button class="btn btn-white table-menu-search search-dd">
										<i class="fa fa-search"></i>
									</button>
									<div class="search-more-wrp">
										<form role="search" class="form-inline" method="post"
											action="searchProductMapping.html">
											<div class="form-group">
												<select class="form-control" name="searchProduct"
													id="searchProduct">
													<option value="productSkuCode">Product SKU Code</option>
													<option value="vendorSkuRef">Vendor SKU Code</option>
													<option value="channelName">Channel Name</option>
													<option value="channelSkuRef">Channel SKU Ref.</option>													
												</select>
											</div>
											<div class="form-group ProductSearch-box" id="SKU">
												<input type="text" class="form-control" name="value" style="width: 100%" required>
											</div>											
											<div class="form-group">
												<button class="btn btn-primary btn-block" type="submit">Search</button>
											</div>
										</form>
									</div>
									<span>Last</span>
									<button type="button" id="LoadFirst500"
										class="btn btn-xs btn-white active">500</button>
									<button type="button" class="btn btn-xs btn-white">1000</button>
									<button type="button" id="LoadMoreProduct"
										class="btn btn-xs btn-white">More</button>
								</div>
							</div>
							<div class="bs-example">
								<div class="tab-content">
									<div class="ibox-content cus-table-filters">
										<div class="scroll-y">
											<table
												class="table table-striped table-bordered table-hover dataTables-example">
												<thead>
													<tr>
														<th>SL No.</th>
														<th>Product SKU_Code</th>
														<th>Channel Name</th>
														<th>Channel SKU_Ref</th>
														<th>Vendor SKU_Ref</th>
														<th>Commission Percent</th>
														<th>Transfer Price</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${!empty productMappingList}">
														<c:forEach items="${productMappingList}"
															var="productMapping" varStatus="loop">
															<tr>
																<td>${loop.index+1}</td>
																<td>${productMapping.productSkuCode}</td>
																<td>${productMapping.channelName}</td>
																<td>${productMapping.channelSkuRef}</td>
																<td>${productMapping.vendorSkuRef}</td>
																<td>${productMapping.commision}</td>
																<td>${productMapping.grossNR}</td>
																<td><a href="#"
																	onclick="removeSKUMapping('${productMapping.productConfigId}')">
																	Unlink</a></td>
															</tr>
														</c:forEach>
													</c:if>
												</tbody>
											</table>
										</div>
										<div class="col-sm-12">
											<div class="hr-line-dashed"></div>
											<a href="#" id="upload1" class="btn btn-success btn-xs">Bulk
												Upload SKU Mapping</a>&nbsp;&nbsp; <a href="#" id="download1"
												class="btn btn-success btn-xs">Download SKU Mapping
												Summary</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<jsp:include page="../globalfooter.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="../globaljslinks.jsp"></jsp:include>
	<!-- Scripts ro Table -->
	<!-- Data Tables -->
	<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
	<script
		src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>
	<script>                   
                    $(document)
                        .ready(
                            function() {
                                $('.dataTables-example')
                                    .dataTable({
                                        responsive: true,
                                        "dom": 'T<"clear">lfrtip',
                                        "tableTools": {
                                            "sSwfPath": "/O2R/seller/js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                                        }
                                    });

                                $('#searchProduct').change(function() {
                                    $('#SKU'.fadeIn());
                                });
                                $('.search-dd').on('click', function(e) {
                                    e.stopPropagation();
                                    $('.search-more-wrp').slideToggle();
                                });
                                $('.input-group.date').datepicker({
                                    todayBtn: "linked",
                                    keyboardNavigation: false,
                                    forceParse: false,
                                    calendarWeeks: true,
                                    autoclose: true
                                });

                                $('#LoadMoreProduct')
                                    .click(
                                        function(e) {
                                            e.preventDefault();
                                            pageno = parseInt(getQueryVariable("page"));
                                            pageno = pageno + 1;
                                            window.location = "productMapping.html?page=" + pageno;

                                        });
                                $('#LoadFirst500').click(function(e) {
                                    window.location = "productMapping.html?page=" + 0;

                                });
                            });

                    function getQueryVariable(variable) {
                        var query = window.location.search.substring(1);
                        var vars = query.split("&");
                        for (var i = 0; i < vars.length; i++) {
                            var pair = vars[i].split("=");
                            if (pair[0] == variable) {
                                return pair[1];
                            }
                        }
                        return 0;
                    }

                    function onclickAddProduct() {
                        $.ajax({
                            url: 'addProduct.html',
                            success: function(data) {
                                $('#centerpane').html(data);
                            }
                        });
                    }

                    function onclickEditProduct(id) {
                        $.ajax({
                            url: 'editProduct.html?id=' + id,
                            success: function(data) {
                                $('#centerpane').html(data);
                            }
                        });
                    }

                    function show(vale, valf) {
                        var valued = document.getElementById('productName');
                        var valuee = document.getElementById('productSkuCode');

                        valued.value = vale;
                        valuee.value = valf;
                        //editProduct.html?id=${product.productId}
                        var xxx = $('editProduct.html?id' + vale).submit();
                    }
                    
                    function removeSKUMapping(value) {
                        $.post( "removeProductMapping.html", {value:value}, function(data) {
                        	window.location.href = "productMapping.html";
                        });
                    }

                    $('#upload1').click(function() {
                        $.ajax({
                            url: 'uploadOrderDA.html?value=skuMapping',
                            success: function(data) {
                                $('#centerpane').html(data);
                            }
                        });
                    });
                    
                    $('#download1').click(function(){
               		 $.ajax({
               	            url : 'downloadOrderDA.html?value=skuMapping',
               	            success : function(data) {
               	            	 $('#centerpane').html(data);
               	            }
               	        });
               		});
                    
                    $('#LoadFirst500').click(function(e) {
                        window.location = "Product.html?page=" + 0;

                    });
                    </script>
</body>
</html>