function plotChartsLine(labels,gpData ,expenseData)
{
	  var lineData = {
		        labels: labels,
		        datasets: [
		            {
		                label: "Gross Profit",
		                fillColor: "rgba(220,220,220,0.5)",
		                strokeColor: "rgba(220,220,220,1)",
		                pointColor: "rgba(220,220,220,1)",
		                pointStrokeColor: "#fff",
		                pointHighlightFill: "#fff",
		                pointHighlightStroke: "rgba(220,220,220,1)",
		                data: gpData
		            },
		            {
		                label: "Expense",
		                fillColor: "rgba(26,179,148,0.5)",
		                strokeColor: "rgba(26,179,148,0.7)",
		                pointColor: "rgba(26,179,148,1)",
		                pointStrokeColor: "#fff",
		                pointHighlightFill: "#fff",
		                pointHighlightStroke: "rgba(26,179,148,1)",
		                data: expenseData
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
		        multiTooltipTemplate: "<%= datasetLabel %> - <%= value %>",
		    };


		    var ctx = document.getElementById("lineChart").getContext("2d");
		    var myNewChart = new Chart(ctx).Line(lineData, lineOptions);
	}

function plotBarGraph(axislabels,label1,label2,dataset1,dataset2,elementId)
{
	 var barData = {
		       // labels: ["January", "February", "March", "April", "May", "June", "July"],
			 labels:axislabels,
		        datasets: [
		            {
		                label: label1,
		                fillColor: "rgba(220,220,220,0.5)",
		                strokeColor: "rgba(220,220,220,0.8)",
		                highlightFill: "rgba(220,220,220,0.75)",
		                highlightStroke: "rgba(220,220,220,1)",
		                data: dataset1
		            },
		            {
		                label: label2,
		                /*fillColor: "rgba(26,179,148,0.5)",
		                strokeColor: "rgba(26,179,148,0.8)",
		                highlightFill: "rgba(26,179,148,0.75)",
		                highlightStroke: "rgba(26,179,148,1)",*/
		                
						
						fillColor: "rgba(151,187,205,0.5)",
						strokeColor: "rgba(151,187,205,0.8)",
						highlightFill: "rgba(151,187,205,0.75)",
						highlightStroke: "rgba(151,187,205,1)",
						data: dataset2
		            }
		        ]
		    };

		    var barOptions = {
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
		        multiTooltipTemplate: "<%= datasetLabel %> - <%= value %>"
		    }


		    var ctx = document.getElementById(elementId).getContext("2d"); 
		    var myNewChart = new Chart(ctx).Bar(barData, barOptions);
		    /*var ctx2 = document.getElementById("quantityBarChart").getContext("2d");
		    var myNewChart = new Chart(ctx).Bar(barData, barOptions);
		    var myNewChart = new Chart(ctx2).Bar(barData, barOptions);*/
		    
}

function plotOrderPayment(ordercount,paymentcount, interval,barwidth)
{
	  var dataset = [
                   {
                       label: "Number of orders",
                       data: ordercount,
                       color: "#1ab394",
                       bars: {
                           show: true,
                           align: "center",
                           //barWidth: 24 * 60 * 60 * 600,
                           barWidth: barwidth,
                           lineWidth:0
                       }
                   }, {
                       label: "Payments",
                       data: paymentcount,
                       yaxis: 2,
                       color: "#464f88",
                       lines: {
                           lineWidth:1,
                               show: true,
                               fill: true,
                           fillColor: {
                               colors: [{
                                   opacity: 0.2
                               }, {
                                   opacity: 0.2
                               }]
                           }
                       },
                       splines: {
                           show: false,
                           tension: 0.6,
                           lineWidth: 1,
                           fill: 0.1
                       },
                   }
               ];
               var options = {
                   xaxis: {
                       mode: "time",
                       tickSize: [1, interval],
                       tickLength: 0,
                       axisLabel: "Date",
                       axisLabelUseCanvas: true,
                       axisLabelFontSizePixels: 12,
                       axisLabelFontFamily: 'Arial',
                       axisLabelPadding: 10,
                       color: "#d5d5d5"
                   },
                   yaxes: [{
                       position: "left",
                      // max: 500,
                       color: "#d5d5d5",
                       axisLabelUseCanvas: true,
                       axisLabelFontSizePixels: 12,
                       axisLabelFontFamily: 'Arial',
                       axisLabelPadding: 3
                   }, {
                       position: "right",
                       clolor: "#d5d5d5",
                       axisLabelUseCanvas: true,
                       axisLabelFontSizePixels: 12,
                       axisLabelFontFamily: ' Arial',
                       axisLabelPadding: 67
                   }
                   ],
                   legend: {
                       noColumns: 1,
                       labelBoxBorderColor: "#000000",
                       position: "nw"
                   },
                   grid: {
                       hoverable: false,
                       borderWidth: 0
                   }
               };
               $.plot($("#flot-dashboard-chart"), dataset, options);
}

function gd(year, month, day) {
    return new Date(year, month - 1, day).getTime();
}
//$(function () {
//
//    var lineData = {
//        labels: ["January", "February", "March", "April", "May", "June", "July"],
//        datasets: [
//            {
//                label: "Example dataset",
//                fillColor: "rgba(220,220,220,0.5)",
//                strokeColor: "rgba(220,220,220,1)",
//                pointColor: "rgba(220,220,220,1)",
//                pointStrokeColor: "#fff",
//                pointHighlightFill: "#fff",
//                pointHighlightStroke: "rgba(220,220,220,1)",
//                data: [65, 59, 80, 81, 56, 55, 40]
//            },
//            {
//                label: "Example dataset",
//                fillColor: "rgba(26,179,148,0.5)",
//                strokeColor: "rgba(26,179,148,0.7)",
//                pointColor: "rgba(26,179,148,1)",
//                pointStrokeColor: "#fff",
//                pointHighlightFill: "#fff",
//                pointHighlightStroke: "rgba(26,179,148,1)",
//                data: [28, 48, 40, 19, 86, 27, 90]
//            }
//        ]
//    };
//
//    var lineOptions = {
//        scaleShowGridLines: true,
//        scaleGridLineColor: "rgba(0,0,0,.05)",
//        scaleGridLineWidth: 1,
//        bezierCurve: true,
//        bezierCurveTension: 0.4,
//        pointDot: true,
//        pointDotRadius: 4,
//        pointDotStrokeWidth: 1,
//        pointHitDetectionRadius: 20,
//        datasetStroke: true,
//        datasetStrokeWidth: 2,
//        datasetFill: true,
//        responsive: true,
//        multiTooltipTemplate: "<%= datasetLabel %> - <%= value %>",
//    };
//
//
//    var ctx = document.getElementById("lineChart1").getContext("2d");
//    var myNewChart = new Chart(ctx).Line(lineData, lineOptions);
//
//    var barData = {
//        labels: ["January", "February", "March", "April", "May", "June", "July"],
//        datasets: [
//            {
//                label: "My First dataset",
//                fillColor: "rgba(220,220,220,0.5)",
//                strokeColor: "rgba(220,220,220,0.8)",
//                highlightFill: "rgba(220,220,220,0.75)",
//                highlightStroke: "rgba(220,220,220,1)",
//                data: [65, 59, 80, 81, 56, 55, 40]
//            },
//            {
//                label: "My Second dataset",
//                fillColor: "rgba(26,179,148,0.5)",
//                strokeColor: "rgba(26,179,148,0.8)",
//                highlightFill: "rgba(26,179,148,0.75)",
//                highlightStroke: "rgba(26,179,148,1)",
//                data: [28, 48, 40, 19, 86, 27, 90]
//            }
//        ]
//    };
//
//    var barOptions = {
//        scaleBeginAtZero: true,
//        scaleShowGridLines: true,
//        scaleGridLineColor: "rgba(0,0,0,.05)",
//        scaleGridLineWidth: 1,
//        barShowStroke: true,
//        barStrokeWidth: 2,
//        barValueSpacing: 5,
//        barDatasetSpacing: 1,
//        responsive: true,
//    }
//
//
//    var ctx = document.getElementById("barChart").getContext("2d"); 
//    ctx2 = document.getElementById("barChart2").getContext("2d");
//    var myNewChart = new Chart(ctx).Bar(barData, barOptions);
//    var myNewChart = new Chart(ctx2).Bar(barData, barOptions);
//
//    // var polarData = [
//    //     {
//    //         value: 300,
//    //         color: "#a3e1d4",
//    //         highlight: "#1ab394",
//    //         label: "App"
//    //     },
//    //     {
//    //         value: 140,
//    //         color: "#dedede",
//    //         highlight: "#1ab394",
//    //         label: "Software"
//    //     },
//    //     {
//    //         value: 200,
//    //         color: "#b5b8cf",
//    //         highlight: "#1ab394",
//    //         label: "Laptop"
//    //     }
//    // ];
//
//    // var polarOptions = {
//    //     scaleShowLabelBackdrop: true,
//    //     scaleBackdropColor: "rgba(255,255,255,0.75)",
//    //     scaleBeginAtZero: true,
//    //     scaleBackdropPaddingY: 1,
//    //     scaleBackdropPaddingX: 1,
//    //     scaleShowLine: true,
//    //     segmentShowStroke: true,
//    //     segmentStrokeColor: "#fff",
//    //     segmentStrokeWidth: 2,
//    //     animationSteps: 100,
//    //     animationEasing: "easeOutBounce",
//    //     animateRotate: true,
//    //     animateScale: false,
//    //     responsive: true,
//
//    // };
//
//    // var ctx = document.getElementById("polarChart").getContext("2d");
//    // var myNewChart = new Chart(ctx).PolarArea(polarData, polarOptions);
//
//    // var doughnutData = [
//    //     {
//    //         value: 300,
//    //         color: "#a3e1d4",
//    //         highlight: "#1ab394",
//    //         label: "App"
//    //     },
//    //     {
//    //         value: 50,
//    //         color: "#dedede",
//    //         highlight: "#1ab394",
//    //         label: "Software"
//    //     },
//    //     {
//    //         value: 100,
//    //         color: "#b5b8cf",
//    //         highlight: "#1ab394",
//    //         label: "Laptop"
//    //     }
//    // ];
//
//    // var doughnutOptions = {
//    //     segmentShowStroke: true,
//    //     segmentStrokeColor: "#fff",
//    //     segmentStrokeWidth: 2,
//    //     percentageInnerCutout: 45, // This is 0 for Pie charts
//    //     animationSteps: 100,
//    //     animationEasing: "easeOutBounce",
//    //     animateRotate: true,
//    //     animateScale: false,
//    //     responsive: true,
//    // };
//
//
//    // var ctx = document.getElementById("doughnutChart").getContext("2d");
//    // var myNewChart = new Chart(ctx).Doughnut(doughnutData, doughnutOptions);
//
//
//    // var radarData = {
//    //     labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],
//    //     datasets: [
//    //         {
//    //             label: "My First dataset",
//    //             fillColor: "rgba(220,220,220,0.2)",
//    //             strokeColor: "rgba(220,220,220,1)",
//    //             pointColor: "rgba(220,220,220,1)",
//    //             pointStrokeColor: "#fff",
//    //             pointHighlightFill: "#fff",
//    //             pointHighlightStroke: "rgba(220,220,220,1)",
//    //             data: [65, 59, 90, 81, 56, 55, 40]
//    //         },
//    //         {
//    //             label: "My Second dataset",
//    //             fillColor: "rgba(26,179,148,0.2)",
//    //             strokeColor: "rgba(26,179,148,1)",
//    //             pointColor: "rgba(26,179,148,1)",
//    //             pointStrokeColor: "#fff",
//    //             pointHighlightFill: "#fff",
//    //             pointHighlightStroke: "rgba(151,187,205,1)",
//    //             data: [28, 48, 40, 19, 96, 27, 100]
//    //         }
//    //     ]
//    // };
//
//    // var radarOptions = {
//    //     scaleShowLine: true,
//    //     angleShowLineOut: true,
//    //     scaleShowLabels: false,
//    //     scaleBeginAtZero: true,
//    //     angleLineColor: "rgba(0,0,0,.1)",
//    //     angleLineWidth: 1,
//    //     pointLabelFontFamily: "'Arial'",
//    //     pointLabelFontStyle: "normal",
//    //     pointLabelFontSize: 10,
//    //     pointLabelFontColor: "#666",
//    //     pointDot: true,
//    //     pointDotRadius: 3,
//    //     pointDotStrokeWidth: 1,
//    //     pointHitDetectionRadius: 20,
//    //     datasetStroke: true,
//    //     datasetStrokeWidth: 2,
//    //     datasetFill: true,
//    //     responsive: true,
//    // }
//
//    // var ctx = document.getElementById("radarChart").getContext("2d");
//    // var myNewChart = new Chart(ctx).Radar(radarData, radarOptions);
//
//});