<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script type="text/javascript">

    $(document).ready(function() {
    	
    	var catvalue='${expense.expenseCatName}';
    	if(catvalue!=null&&uploadValue.length!=0)
    		$("#expenseCatName").val(catvalue);
    });
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
                            <h5>Create Expense</h5>
                        </div>
                        <div class="ibox-content overflow-h">
                            <form:form method="POST" action="saveExpense.html" id="addExpenseForm"
                             role="form" class="form-horizontal">
 							
 							 <c:if test="${!empty expense.expenseId}">
                        <input type="hidden" name="expenseId" id="expenseId" value="${expense.expenseId}"/>
                         </c:if>
                            <div class="col-sm-12">
                                <div class="col-sm-6">
                                 <div class="mar-btm-20-oh"><label class="col-sm-5 control-label">Title</label>
                                    <div class="col-sm-7"><form:input path="expenseName" value="${expense.expenseName}"
                                     class="form-control"/>
                                     </div>
                                    </div>
                                    <div class="mar-btm-20-oh"><label class="col-sm-5 control-label">Date of Expense</label>
                                    <div class="col-md-7" id="data_1">
                                            <div class="input-group date">
                                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                            <form:input type="text" class="form-control"  path="expenseDate" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="mar-btm-20-oh"><label class="col-sm-5 control-label">Notes</label>
                                    <div class="col-sm-7"><form:input path="expenseDescription" value="${expense.expenseDescription}"
                                     class="form-control"/></div>
                                    </div>
                                    <div><label class="col-sm-5 control-label">Paid to</label>
                                    <div class="col-sm-7"><form:input path="paidTo" value="${expense.paidTo}"
                                     class="form-control"/></div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                
                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Expense Category </label>
                                    <div class="col-sm-8">
                                     <c:choose>
                                     <c:when test="${empty expense.expenseCatName}">
                                    <form:select path="expenseCatName" items="${catmap}" class="form-control"
                                    id="expenseCatName">  
       								 </form:select>
       								 </c:when>
       								 <c:otherwise>
       								 <form:input path="expenseCatName" value="${expense.expenseCatName}"
                                     class="form-control" readonly="true"/>
       								 </c:otherwise>
       								 </c:choose>
       								 </div>
                                    </div>
                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Amount</label>
                                    <div class="col-sm-8"><form:input path="amount" value="${expense.amount}"
                                     class="form-control"/></div>
                                    </div>
                                    <div ><label class="col-sm-4 control-label">Expenditure by person</label>
                                    <div class="col-sm-8"><form:input path="expenditureByperson" value="${expense.expenditureByperson}"
                                     class="form-control"/></div>
                                    </div>
                                </div>
                            </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                    <button class="btn btn-primary pull-right" type="submit" onclick="submitExpense()">Save</button>
                                </div>
                            </form:form>
                        </div>
                </div>
            </div>
        </div>
        
        </div>
 <jsp:include page="../globalfooter.jsp"></jsp:include>

    </div>
</div>

<jsp:include page="../globaljslinks.jsp"></jsp:include>
<script>
function submitExpense(){
 var validator = $("#addExpenseForm").validate({
	  rules: {
			  expenseName: {
		        required: true,
		            },
		       dateOfExpense : {
		       required : true,
		       date : true,
		            },
		        amount: {
				required: true,
		        min: 1,
				number:true,
				    }   
		  },
     messages: {
    	 expenseName: "Expense Name Required",
    	 amount: "Expense Amount required",
    	 quantity: "Date Of Expense is required"
     }
 });
 if(validator.form()){ // validation perform
  $('form#addExpenseForm').submit();
 }
}

    $(document).ready(function(){
        $('#data_1 .input-group.date').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
            });
    });
</script>
 </body>
</html>