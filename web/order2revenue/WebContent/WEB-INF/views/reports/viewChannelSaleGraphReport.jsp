<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <jsp:include page="../globalcsslinks.jsp"></jsp:include>

    <!-- orris -->
    <link href="/O2R/seller/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">

    <!-- Data Tables -->
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet">
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet">

</head>

<body>

 <div id="wrapper">
<jsp:include page="../sidenavigation.jsp"></jsp:include>
    <div id="page-wrapper" class="gray-bg">
     <jsp:include page="../globalheader.jsp"></jsp:include>  
      <div class="wrapper wrapper-content animated fadeInRight" id="centerpane"> 
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Reports</h5>
                        </div>
                        <div class="ibox-content overflow-h">
                            
                            <div class="panel-options">

                                <ul class="nav nav-tabs">
                                    <li class="active"><a data-toggle="tab" href="#tab-1">View Graph</a></li>
                                    <li class=""><a data-toggle="tab" href="#tab-2">View Report</a></li>
                                </ul>
                            </div>
                            <div class="tab-content">
                            <div id="tab-1" class="tab-pane active col-sm-12 chart-even">
							
                                <div class="row">
                                
                                
                                                                <div class="col-lg-6">
                                         <table class="table table-bordered custom-table">
                                            <thead>
				                                            <tr>
				                                                <th>Partner</th>
				                                                <th color="green">SP</th>
				                                                <th>NR</th>
				                                                <th>AR</th>
				                                            </tr>
                                            </thead>
                                            <tbody>
                                             <c:if test="${!empty ttsolist}">
                                 			 <c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
                                 			 <c:if test="${!empty ttso.groupByName}">
                                            <tr>
                                                <td>${ttso.pcName}</td>
                                                <td>${ttso.netRate}</td>
                                                <td>${ttso.orderSP}</td>
                                                <td>${ttso.orderSP/2}</td>
                                            </tr>
                                            </c:if>
                                            </c:forEach>
                                            </c:if>
                                            </tbody>
                                        </table>
                                </div>
                                
                                
                                
								                <div class="col-lg-6">
								                    <div class="ibox float-e-margins">
								                        <div class="ibox-title">
								                            <h5>Line Chart
								                                <small>With custom colors.</small>
								                            </h5>
								                            <div ibox-tools></div>
								                        </div>
								                        <div class="ibox-content">
								                            <div>
								                                <canvas id="flot-chart-content" height="140"></canvas>
								                            </div>
								                        </div>
								                    </div>
								                </div>

                                
                                </div>

                        <div class="row">
                                    <div class="col-lg-6">
                                    <div class="float-e-margins graph-brd">
                                    <div class="ibox-content">
                                         <table class="table table-bordered custom-table">
                                            <thead>
                                            <tr>
                                                <th>Partner</th>
                                                <th>Gross Sale Qty</th>
                                                <th>Sale Return</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                             <c:if test="${!empty ttsolist}">
                                 			 <c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
                                 			 <c:if test="${empty ttso.groupByName}">
                                            <tr>
                                                <td>${ttso.pcName}</td>
                                                <td>${ttso.quantity}</td>
                                                <td>${ttso.returnorrtoQty}</td>
                                            </tr>
                                            </c:if>
                                            </c:forEach>
                                            </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                    </div>
                                </div>
										                <div class="col-lg-6">
										                    <div class="ibox float-e-margins">
										                        <div class="ibox-title">
										                            <h5>Bar Chart</h5>
										                            <div ibox-tools></div>
										                        </div>
										                        <div class="ibox-content">
										                            <div>
										                                <canvas id="barChart" height="140"></canvas>
										                            </div>
										                        </div>
										                    </div>
										                </div>
                                </div>
                        <div class="row">
                                    <div class="col-lg-6">
                                    <div class="float-e-margins graph-brd">
                                    <div class="ibox-content">
                                         <table class="table table-bordered custom-table">
                                            <thead>
                                            <tr>
                                                <th>Partner</th>
                                                <th>Gross Sale</th>
                                                <th>Return Amount</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                             <c:if test="${!empty ttsolist}">
                                 			 <c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
                                 			 <c:if test="${empty ttso.groupByName}">
                                            <tr>
                                                <td>${ttso.pcName}</td>
                                                <td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ttso.grossProfit}" /></td>
                                                <td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ttso.grossProfit/2}" /></td>
                                            </tr>
                                            </c:if>
                                            </c:forEach>
                                            </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                    </div>
                                </div>
					                <div class="col-lg-6">
					                    <div class="ibox float-e-margins">
					                        <div class="ibox-title">
					                            <h5>Line Chart
					                                <small>With custom colors.</small>
					                            </h5>
					                            <div ibox-tools></div>
					                        </div>
					                        <div class="ibox-content">
					                            <div>
					                                <canvas id="lineChart" height="140"></canvas>
					                            </div>
					                        </div>
					                    </div>
					                </div>
                                </div>
                        <div class="row">
                                    <div class="col-lg-6">
                                    <div class="float-e-margins graph-brd">
                                    <div class="ibox-content">
                                         <table class="table table-bordered custom-table">
                                            <thead>
                                            <tr>
                                                <th>Partner</th>
                                                <th>Net AR</th>
                                                <th>Net Due</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                             <c:if test="${!empty ttsolist}">
                                 			 <c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
                                 			 <c:if test="${empty ttso.groupByName}">
                                            <tr>
                                                <td>${ttso.pcName}</td>
                                                <td>${ttso.quantity}</td>
                                                <td>${ttso.returnorrtoQty}</td>
                                            </tr>
                                            </c:if>
                                            </c:forEach>
                                            </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                    </div>
                                </div>
								                <div class="col-lg-6">
								                    <div class="ibox float-e-margins">
								                        <div class="ibox-title">
								                            <h5>Bar Chart
								                                <small>With custom colors.</small>
								                            </h5>
								                            <div ibox-tools></div>
								                        </div>
								                        <div class="ibox-content">
								                            <div>
								                                <canvas id="barSecondChart" height="140"></canvas>
								                            </div>
								                        </div>
								                    </div>
								                </div>
                                </div>

                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="float-e-margins graph-brd">
                                        <div >
                                      </div>
                                            
                                            <div class="ibox-content">
                                                <div id="morris-line-chart"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                    <button class="btn btn-primary pull-right" type="submit">Print</button>
                                </div>
                            </div>
                            
                            <div id="tab-2" class="tab-pane col-sm-12">
                            <form role="form" class="form-horizontal">
                            <div class="col-sm-12"  style="overflow-x: scroll;">
                            <div id="tbdata1">
                            <p id="selectTriggerFilter"><label><b>Filter:</b></label><br></p>
                                <table id="filterTable" style="width:50%" class="table table-striped table-bordered table-hover dataTables-example" >
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Date Range</th>
                                    <th>Partner Name</th>
                                    
                                    <th colspan="3" style="color:blue">N/R Amount, Gross SP, Gross Qty</th>
                                    
                                    <th  colspan="3" style="color:blue">N/R Amount,Rtn SP,Rtn Qty</th>

                                    <th>Return Vs Gross</th>
                                    
                                    <th   colspan="3" style="color:blue">N/R Amount,Net SP,Net Qty</th>
                                    
                                    <th>Tax Category </th>
                                    <th>Net Tax Liability On SP</th>
                                    
                                    <th  colspan="3" style="color:blue">N/R Amount,Net Pure Sale,Qty</th>
                                     <th>Net A/R</th>
                                     <th>Net Due</th>
                                    </tr>
                                </thead>
                                <tbody>
                                 <c:if test="${!empty ttsolist}">
                                 
                               <c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
                              
                              <c:if test="${!empty ttso.groupByName}">
                                <tr>
                                    <td>${loop.index+1}</td>
                                    <td>${ttso.startDate} ${ttso.endDate}</td>
                                    <td>${ttso.pcName}</td>
                                    
                                    <td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ttso.grossNetRate}" /></td>
									<td>${ttso.orderSP}</td>
									<td>${ttso.quantity}</td>
									
									<td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ttso.netRate}" /></td>
									<td>${ttso.returnOrRTOChargestoBeDeducted}</td>
									<td>${ttso.returnorrtoQty}</td>
									
									<td>${ttso.returnorrtoQty * 100/ttso.quantity}</td>
									
									<td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ttso.netRate}" /></td>
									<td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ttso.orderSP - ttso.returnOrRTOChargestoBeDeducted}" /></td>
									<td>${ttso.quantity - ttso.returnorrtoQty}</td>
									
									<td>${ttso.taxCategtory}</td>
									<td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ttso.grossNetRate - ttso.grossNetRate * 100/105}" /></td>

									<td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${ttso.netRate}" /></td>
									<td><fmt:formatNumber type="number"  maxFractionDigits="2"  value="${(ttso.orderSP - ttso.returnOrRTOChargestoBeDeducted)-(ttso.grossNetRate - ttso.grossNetRate * 100/105)}" /></td>
									<td>${ttso.quantity - ttso.returnorrtoQty}</td>
									<td>${ttso.positiveAmount + ttso.negativeAmount}</td>
									<td>TBD</td>
                                  </tr>
                                </c:if>
                                </c:forEach>
                                </c:if>
                                </tbody>
                                </table></div>
                            </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                    <button class="btn btn-primary pull-right" type="submit">Save</button>
                                </div>

                            </form>
                            </div>
                            </div>
                        </div>
                </div>
            </div>
        </div>
         <jsp:include page="../globalfooter.jsp"></jsp:include>

    </div>
</div>
</div>

<jsp:include page="../globaljslinks.jsp"></jsp:include>

<!-- Flot -->
<script src="/O2R/seller/js/plugins/flot/jquery.flot.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.pie.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.time.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.axislabels.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.symbol.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.spline.js"></script>
<script src="/O2R/seller/js/plugins/flot/jquery.flot.resize.js"></script>

<!-- Morris -->
<script src="/O2R/seller/js/plugins/morris/raphael-2.1.0.min.js"></script>
<script src="/O2R/seller/js/plugins/morris/morris.js"></script>

<!-- Morris demo data-->
<script src="/O2R/seller/js/demo/morris-demo.js"></script>

<!-- ChartJS-->
<script src="/O2R/seller/js/plugins/chartJs/Chart.min.js"></script>
<script src="/O2R/seller/js/demo/flot-demo-1.js"></script>
<script   language="javascript">
   function changeTable(svalue){
	   document.getElementById("tbdata1").innerHTML="";
   }
</script>

<!-- Data Tables -->
<script src="/O2R/seller/js/plugins/dataTables/jquery.dataTables.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>







<script>

$(window).load(function() {
	 	
	 	var labelsdata = [];
	 	var adata = [];
	 	var bdata = [];
	 	var cdata = [];
 		 	
	 	<c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
	 	<c:if test="${empty ttso.groupByName}">
			labelsdata.push('${ttso.pcName}');
	 		adata.push('${ttso.orderSP}');
	 		bdata.push('${ttso.orderSP - 6000}');
	 		cdata.push('${ttso.orderSP - 9000}');
	 	</c:if>
		</c:forEach>
	 	
	    var lineData = {
	            labels: labelsdata,
	            datasets: [
	                {
	                    label: "Example dataset",
	                    fillColor: "rgba(220,220,220,0.5)",
	                    strokeColor: "red",
	                    pointColor: "green",
	                    pointStrokeColor: "#fff",
	                    pointHighlightFill: "#fff",
	                    pointHighlightStroke: "rgba(220,220,220,1)",
	                    data: adata
	                },
	                {
	                    label: "Example dataset",
	                    fillColor: "rgba(120,120,220,0.5)",
	                    strokeColor: "green",
	                    pointColor: "red",
	                    pointStrokeColor: "#fff",
	                    pointHighlightFill: "#fff",
	                    pointHighlightStroke: "rgba(26,179,148,1)",
	                    data: bdata
	                },
	                {
	                    label: "Example dataset",
	                    fillColor: "rgba(220,220,220,0.5)",
	                    strokeColor: "blue",
	                    pointColor: "blue",
	                    pointStrokeColor: "#fff",
	                    pointHighlightFill: "#fff",
	                    pointHighlightStroke: "rgba(55,100,118,1)",
	                    data: cdata
	                }
	            ]
	        };

	        var lineOptions = {
	            scaleShowGridLines: true,
	            scaleGridLineColor: "rgba(0,0,0,.05)",
	            scaleGridLineWidth: 1,
	            bezierCurve: true,
	            bezierCurveTension: 0.4,
	            pointDot: true,
	            pointDotRadius: 4,
	            pointDotStrokeWidth: 1,
	            pointHitDetectionRadius: 20,
	            datasetStroke: true,
	            datasetStrokeWidth: 2,
	            datasetFill: true,
	            responsive: true,
	        };


	        var ctx = document.getElementById("flot-chart-content").getContext("2d");
	        var myNewChart = new Chart(ctx).Line(lineData, lineOptions);
	        
	
	var axislabels=[];
	var dataset1 = [];
	var dataset2 = [];
	
	
	 var barData = {
		       
			 labels:axislabels,
		        datasets: [
		            {
		                label: "label1",
		                fillColor: "rgba(26,179,148,1)",
		                strokeColor: "rgba(220,220,220,0.8)",
		                highlightFill: "rgba(220,220,220,0.75)",
		                highlightStroke: "rgba(220,220,220,1)",
		                data: dataset1
		            },
		            {
		                label: "label2",
		                fillColor: "rgba(226,109,148,1)",
		                strokeColor: "rgba(26,179,148,0.8)",
		                highlightFill: "rgba(26,179,148,0.75)",
		                highlightStroke: "rgba(26,179,148,1)",
		                data:dataset2
		            }
		        ]
		    };

		    var barOptions1 = {
		        scaleBeginAtZero: true,
		        scaleShowGridLines: true,
		        scaleGridLineColor: "rgba(0,0,0,.05)",
		        scaleGridLineWidth: 1,
		        barShowStroke: true,
		        barStrokeWidth: 2,
		        barValueSpacing: 5,
		        showTooltips : true,
		        barDatasetSpacing: 1,
		        responsive: true,
		        multiTooltipTemplate: ""
		    }
		 	<c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
		 	<c:if test="${empty ttso.groupByName}">
		 	axislabels.push('${ttso.pcName}');
		 		dataset1.push('${ttso.quantity}');
		 		dataset2.push('${ttso.quantity/2}');
		 	</c:if>
			</c:forEach>
        	
		    var ctx = document.getElementById("barChart").getContext("2d"); 
		    var myNewChart = new Chart(ctx).Bar(barData, barOptions1);
	      //  var ctx = document.getElementById("flot-chart-content_1").getContext("2d");
	      //  var myNewChart = new Chart(ctx).Line(lineData1, lineOptions);
	      
	      var labelsx=[];
	      var data1x=[];
	      var data2x=[];
	      
	      
	  var lineDatax = {
		        labels: labelsx,
		        datasets: [
		            {
		                label: "Gross Profit",
		                fillColor: "rgba(120,120,120,0.5)",
		                strokeColor: "black",
		                pointColor: "rgba(220,220,220,1)",
		                pointStrokeColor: "#fff",
		                pointHighlightFill: "#fff",
		                pointHighlightStroke: "rgba(220,220,220,1)",
		                data: data1x
		            },
		            {
		                label: "Expense",
		                fillColor: "rgba(26,179,148,0.5)",
		                strokeColor: "rgba(26,179,148,0.7)",
		                pointColor: "rgba(26,179,148,1)",
		                pointStrokeColor: "#fff",
		                pointHighlightFill: "#fff",
		                pointHighlightStroke: "rgba(26,179,148,1)",
		                data: data2x
		            }
		        ]
		    };
	  
	  var lineOptionsx = {
		        scaleShowGridLines: true,
		        scaleGridLineColor: "rgba(0,0,0,.05)",
		        scaleGridLineWidth: 1,
		        bezierCurve: true,
		        bezierCurveTension: 0.4,
		        pointDot: true,
		        pointDotRadius: 4,
		        pointDotStrokeWidth: 1,
		        pointHitDetectionRadius: 20,
		        datasetStroke: true,
		        datasetStrokeWidth: 2,
		        datasetFill: true,
		        responsive: true,
		        multiTooltipTemplate: "",
		    };
	  
		<c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
	 	<c:if test="${empty ttso.groupByName}">
			labelsx.push('${ttso.pcName}');
			data1x.push('${ttso.grossProfit}');
			data2x.push('${ttso.grossProfit/2}');
	 	</c:if>
		</c:forEach>


		    var ctx = document.getElementById("lineChart").getContext("2d");
		    var myNewChart = new Chart(ctx).Line(lineDatax, lineOptionsx);


			var axislabelsx=[];
			var dataset1x = [];
			var dataset2x = [];
			
			
			 var barDatax = {
				       
					 labels:axislabelsx,
				        datasets: [
				            {
				                label: "label1",
				                fillColor: "rgba(220,220,220,0.8)",
				                strokeColor: "rgba(220,220,220,0.8)",
				                highlightFill: "rgba(220,220,220,0.75)",
				                highlightStroke: "rgba(220,220,220,1)",
				                data: dataset1x
				            },
				            {
				                label: "label2",
				                fillColor: "rgba(26,179,148,0.8)",
				                strokeColor: "rgba(26,179,148,0.8)",
				                highlightFill: "rgba(26,179,148,0.75)",
				                highlightStroke: "rgba(26,179,148,1)",
				                data:dataset2x
				            }
				        ]
				    };

				    var barOptions1x = {
				        scaleBeginAtZero: true,
				        scaleShowGridLines: true,
				        scaleGridLineColor: "rgba(0,0,0,.05)",
				        scaleGridLineWidth: 1,
				        barShowStroke: true,
				        barStrokeWidth: 2,
				        barValueSpacing: 5,
				        showTooltips : true,
				        barDatasetSpacing: 1,
				        responsive: true,
				        multiTooltipTemplate: ""
				    }
				 	<c:forEach items="${ttsolist}" var="ttso" varStatus="loop">
				 	<c:if test="${empty ttso.groupByName}">
				 	axislabelsx.push('${ttso.pcName}');
				 		dataset1x.push('${ttso.quantity}');
				 		dataset2x.push('${ttso.quantity/2}');
				 	</c:if>
					</c:forEach>
		        	
				    var ctx = document.getElementById("barSecondChart").getContext("2d"); 
				    var myNewChart = new Chart(ctx).Bar(barDatax, barOptions1x);
				    
				    
				    $(document).ready(function() {
				    	   $('#filterTable').DataTable({
				    	        "lengthMenu": [
				    	            [10, 25, 50, 100, -1],
				    	            [10, 25, 50, 100, "All"]
				    	        ],
				    	        "scrollY": "200px",
				    	        "dom": 'rtipS',
				    	        // searching: false,
				    	        "deferRender": true,
				    	        initComplete: function () {
				    	           var column = this.api().column(0);
				    	           var select = $('<select class="filter"><option value=""></option></select>')
				    	               .appendTo('#selectTriggerFilter')
				    	               .on('change', function () {
				    	                  var val = $(this).val();
				    	                  column.search(val ? '^' + $(this).val() + '$' : val, true, false).draw();
				    	               });

				    	           column.data().unique().sort().each(function (d, j) {
				    	               select.append('<option value="' + d + '">' + d + '</option>');
				    	           });
				    	        }
				    	    });
				    	});

    $('.dataTables-example').dataTable({
	            responsive: true,
	            "dom": 'T<"clear">lfrtip',
	            "tableTools": {
	                "sSwfPath": "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
	            }
	    });
	});
	
 	
</script>
<style>
    body.DTTT_Print {
        background: #fff;

    }
    .DTTT_Print #page-wrapper {
        margin: 0;
        background:#fff;
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
    div.dataTables_length select{
        padding: 0 10px;
    }
</style>
</body>

</html>
