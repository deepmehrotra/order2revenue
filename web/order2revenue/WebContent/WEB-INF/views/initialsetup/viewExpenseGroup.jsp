<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>
  <!-- Data Tables -->
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet">
    <link href="/O2R/seller/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet">
<script type="text/javascript">

    function onclickAddExpense() {
    	   $.ajax({
            url : 'addExpense.html',
            success : function(data) {
                $('#centerpane').html(data);
            }
        });
    }
    function onclickAddExpenseGroup() {
        $.ajax({
            url : 'downloadOrderDA.html?value=expenseSummary',
            success : function(data) {
            	alert(data);
                $('#centerpane').html("Got response from backend");
            }
        });
    }
   
   
 
</script>
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
                            <h5>All Expenses</h5>
                            <div class="ibox-tools">
                             <button class="btn btn-white table-menu-search search-dd">
                                <i class="fa fa-search"></i>
                           </button>
                                <div class="search-more-wrp">
                                    <form role="search" class="form-inline" method="post" action="searchExpense.html">
                                        <div class="form-group">
                                            <select class="form-control" name="searchExpense"  id="searchExpense">
                                                <option value="expenseCategory">Expense Group</option>
                                                <option value="expenseDate">Expense Date</option>
                                            </select>
                                        </div>
                                        <div class="form-group ProductSearch-box" id="expenseCategory">
                                           <input type="text" placeholder="Expense Group" class="form-control" name="expenseCategory">
                                        </div>
                                        <div class="form-group ProductSearch-box" id="expenseDate" style="display:none">
                                            <div class="input-group date">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" class="form-control"  placeholder="Start Date"
                                                name="startDate">
                                            </div>
                                            <div class="input-group date">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" class="form-control" placeholder="End Date"
                                                 name="endDate">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button class="btn btn-primary btn-block" type="submit">Search</button>
                                        </div>
                                    </form>
                                </div>
                                <span>Last</span>
                                <button type="button" class="btn btn-xs btn-white active">500</button>
                                <button type="button" class="btn btn-xs btn-white">1000</button>
                                <button type="button" class="btn btn-xs btn-white">More</button>
                                <a href="addExpense.html"  class="btn btn-primary btn-xs" >Add Expenses</a>
                             </div>
                        </div>
                        <div class="ibox-content overflow-h cus-table-filters">
                        <div class="scroll-y">
                             <table class="table table-striped table-bordered table-hover dataTables-example" >
                                <thead>
                                <tr>
                                    <th>SL No.</th>
                                    <th>Name</th>
                                    <th>Date of Expense</th>
                                    <th>Expense Category</th>
                                    <th>Notes</th>
                                    <th>Amount</th>
                                    <th>Paid To</th>
                                    <th>Expenditure</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${!empty expenses}">
                                <c:forEach items="${expenses}" var="expense" varStatus="loop">
                                <tr>
                                    <td>${loop.index+1}</td>
                                    <td>${expense.expenseName}</td>
                                    <td><fmt:formatDate value="${expense.expenseDate}" pattern="MMM dd ,YY"/></td>
                                    <td>${expense.expenseCatName}</td>
                                    <td>${expense.expenseDescription}</td>
                                    <td>${expense.amount}</td>
                                     <td>${expense.paidTo}</td>
                                      <td>${expense.expenditureByperson}</td>
                                    <td class="tooltip-demo">
                                  <%--   <a href="editExpense.html?expenseId=${expense.expenseId}"><i class="fa fa-edit text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Edit"></i></a>
                                   --%>  <a href="deleteExpense.html?expenseId=${expense.expenseId}"><i class="fa fa-edit text-navy" data-toggle="tooltip" data-placement="top" data-original-title="Delete"></i></a></td>
                                
                                </tr>
                                </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                            </div>
                            <div class="col-sm-12">
                            <div class="hr-line-dashed"></div>
                                <a href="#" id="bulkupload"  class="btn btn-success btn-xs">Bulk Upload Expenses</a>  
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
<script src="/O2R/seller/js/plugins/dataTables/dataTables.responsive.js"></script>
<script src="/O2R/seller/js/plugins/dataTables/dataTables.tableTools.min.js"></script>

<script>
    $(document).ready(function(){
        $('.dataTables-example').dataTable({
                responsive: true,
                "dom": 'T<"clear">lfrtip',
                "tableTools": {
                    "sSwfPath": "js/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
                }
        });
        
        $('#searchExpense').change(function () {
            $('.ProductSearch-box').hide();
            $('#'+$(this).val()).fadeIn();
        });
        $('.search-dd').on('click', function(e){
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
        
        
        $('#bulkupload').click(function(){
    		alert(" Bulk upload expense");
    		 $.ajax({
    	            url : 'downloadOrderDA.html?value=expenseSummary',
    	            success : function(data) {
    	            	 $('#centerpane').html(data);
    	            }
    	        });
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
</style>
 </body>
</html>
</body>
</html>