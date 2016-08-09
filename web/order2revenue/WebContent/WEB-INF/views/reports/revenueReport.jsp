<%@ page language="java" contentType="text/html; charset=ISO-8859-1"

    pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<body>

  <div class="row">

                <div class="col-lg-12">

                    <div class="ibox float-e-margins">

                        <div class="ibox-title">

                            <h5>${reportNameStr}</h5>

                          <!--  <h5>Report</h5> -->

                        </div>

                        <div class="ibox-content report-links">

                          <form method="POST" action="getReport.html" id="selectReportForm"

                        class="form-horizontal" name="selectReportForm">

                        <input type="hidden" name="reportName" id="reportName" value="${reportName}"/>
						<c:if test="${reportName ne 'debtorsReport'}">
	                        <div class="col-sm-12 mar-btm-20-oh">
	                             <div class="form-group">
	                             	<label class="col-sm-4 control-label label-text-mrg">Select Financial Year</label>
	                                <div class="col-sm-8">
	                                	<select name="selectedYear">
	                                		<option value="2014">2014-15</option>
	                                		<option value="2015">2015-16</option>
	                                		<option value="2016">2016-17</option>
	                                		<option value="2017">2017-18</option>
	                                		<option value="2018">2018-19</option>
	                                		<option value="2019">2019-20</option>
	                                		<option value="2020">2020-21</option>
	                                		<option value="2021">2021-22</option>
	                                	</select>
	                                </div>
	                            </div>
	                        </div>
                        </c:if>

						<input type="hidden" name="requestType" id="requestType" value=""/>

                        <div class="col-sm-12">

                        <div class="hr-line-dashed"></div>

                        <button class="btn btn-primary pull-right mar-left-20" type="button" onclick="submitReport('download')" >Download Report</button>

                        <button class="btn btn-success pull-right" type="button" onclick="submitReport('view')">View Complete Report</button>

                        </div>

                          </form>     

                        </div>

                    </div>

                </div>

            </div>

<script>

    $(document).ready(function(){

        $('#data_1 .input-group.date').datepicker({

                todayBtn: "linked",

                keyboardNavigation: false,

                forceParse: false,

                calendarWeeks: true,

                autoclose: true

            });

        $('#data_2 .input-group.date').datepicker({

                todayBtn: "linked",

                keyboardNavigation: false,

                forceParse: false,

                calendarWeeks: true,

                autoclose: true

            });

 

        $("[name=toggler]").click(function(){
        	alert("TOGGLER CLICK");

            $('.radio2').hide();

            $("#blk-"+$(this).val()).slideDown();

        });
        
    
            $('#selectall').click(function(event) {  //on click 
            	alert(" Select all ");
                if(this.checked) { // check select status
                    $('.checkbox1').each(function() { //loop through each checkbox
                        this.checked = true;  //select all checkboxes with class "checkbox1"               
                    });
                }else{
                    $('.checkbox1').each(function() { //loop through each checkbox
                        this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                    });         
                }
            });
            
        

    });

    function submitReport(value){

    	if(value=='download'){
			document.getElementById('selectReportForm').action = "downloadRevenueReport.html";
		} else {
    	   	document.getElementById('selectReportForm').action = "getRevenueReport.html";
   	   	}
       	$("#selectReportForm").submit();

        $.ajax({
			url:$("#selectReportForm").attr("action"),
            context: document.body,
            type: 'post',
			data:$("#selectReportForm").serialize(),
			success : function(res) {
				if($(res).find('#j_username').length > 0){
	        		window.location.href = "orderindex.html";
	        	}else{
	            	$('#centerpane').html(res);
	        	}
           	}
		});
	}

</script>

</body>

</html>