<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <script type="text/javascript">
var nameAvailability=true;

function checkOnBlur()
{
	var name=document.getElementById("categoryName").value;
	
	$.ajax({
        url: "checkTaxCategory.html?name="+name,
       success : function(res) {
    	 	   if(res=="false")
                	{
        	nameAvailability=false;
                	 $("#catNameMessage").html("Tax category already exist");
                	}
                else
                	{
                	nameAvailability=true;
                	$("#catNameMessage").html("Tax category  available");
                	}
      }
	 });
	}
function submitForm(){
	 var validator = $("#addTaxCatForm").validate({
		  rules: {
			  taxCatName: {
			        required: true,
			            }, 
			  taxPercent: {
					required: true,
			        min: 1,
			        max: 100,
					number:true,
					            }    		            
			  },
	     messages: {
	    	 taxCatName: "Tax Category Name Required",
	    	taxPercent: "Tax percentage is required from 1% to 100%"
	     }
	 });
	 if(validator.form()&&nameAvailability)
	 { // validation perform
			  $('form#addTaxCatForm').submit();
	 }

	}
</script>
 </head>
 <body>
  <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Tax Category</h5>
                        </div>
                        <div class="ibox-content add-company">
                        <form:form method="POST" action="saveTaxCategory.html" 
                             id="addTaxCatForm" role="form" class="form-horizontal">
                              <c:if test="${!empty taxCategory.taxCatId}">
                        <input type="hidden" name="taxCatId" id="taxCatId" value="${taxCategory.taxCatId}"/>
                         </c:if>
  							<div class="col-sm-6">
                                    <div class="form-group"><label class="col-sm-5 control-label">Tax Category Name</label>

                                    <div class="col-sm-7"><form:input path="taxCatName" value="${taxCategory.taxCatName}"
                                    class="form-control"  id="categoryName" onblur="checkOnBlur()"/><span id="catNameMessage" style="color:red"></span>
                                  </div>
                                    </div>
                                     <div class="form-group"><label class="col-sm-5 control-label">Description</label>

                                    <div class="col-sm-7"><form:input path="taxCatDescription" value="${taxCategory.taxCatDescription}"
                                    class="form-control"/>
                                  </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group"><label class="col-sm-4 control-label">Tax Percent</label>

                                    <div class="col-sm-8"><form:input path="taxPercent" value="${taxCategory.taxPercent}"
                                    class="form-control"/></div>
                                    </div>
                                </div>
                              
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                    <button class="btn btn-primary pull-right" type="button"  onclick="submitForm()">Save</button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
                    
 </body>
</html>