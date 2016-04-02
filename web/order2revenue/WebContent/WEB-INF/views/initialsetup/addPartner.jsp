<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
.error {
	color: red;
	font-weight:bold;
}
</style>
<style type="text/css">
span.#error{
  color: red;
  font-weight: bold;
}

.pickListButtons {
  padding: 10px;
  text-align: center;
}



.pickListSelect {
  height: 200px !important;
}
.pickList1Select {
  height: 200px !important;
}

</style>
<link href="/O2R/seller/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="/O2R/seller/css/plugins/switchery/switchery.css" rel="stylesheet">
</head>
 <body>


  <form:form method="POST" action="savePartner.html" id="addpartnerform"
                         role="form" class="form-horizontal" enctype="multipart/form-data">
 <div class="row">

   <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Partner</h5>
                        </div>
                        <div class="ibox-content add-company">
                       
                           <c:if test="${!empty partner.pcId}">
                        <%--  <form:hidden path="pcId" value="${partner.pcId}"/> --%>
                         <input type="hidden" name="pcId" id="pcId" value="${partner.pcId}"/>
                         </c:if>
                        
                                <div class="col-sm-6">
                                    <div class="form-group"><label class="col-sm-4 control-label">Partner Name</label>

                                    <div class="col-sm-8"><form:input path="pcName" value="${partner.pcName}" class="form-control" id="partnerName" onblur="checkOnBlur()"/>
                                    <span id="partnerNameMessage" style="font-weight: bold;color=red"></span></div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group"><label class="col-sm-4 control-label">Alias Name</label>

                                    <div class="col-sm-8"><form:input path="pcDesc" value="${partner.pcDesc}" class="form-control"/></div>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group"><label class="col-sm-2 control-label">Upload Brand Logo</label>

                                    <div class="col-md-4">
                                        <label title="Upload image file" for="image" class="btn btn-white btn-block">
                                        <i class="fa fa-upload"></i>
                                            <input type="file" accept="image/*" name="image" id="image" class="hide" >
                                            Upload Logo
                                        </label>
                                    </div>
                                    
                                </div>
                                

                                    <div class="hr-line-dashed"></div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="radio"><label><form:radiobutton path="paymentType" value="paymentcycle" id="paymentcycle" name="toggler"/>Subdivided Monthly</label>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="radio"><label><form:radiobutton path="paymentType" value="datewisepay" id="datewisepay" name="toggler"/>Delivery/Shipped Date </label>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="radio"><label><form:radiobutton path="paymentType" value="monthly" id="monthly" name="toggler"/> Monthly</label>
                                    </div>
                                </div>
                            
                                <div class="col-sm-12 radio1" id="blk-paymentcycle">
                                <div class="col-sm-6">
                                   <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Cycle Start date</label>
                                    <div class="col-sm-8"><form:input path="startcycleday" value="${partner.startcycleday}" placeholder="Duration of Payment from Start Date" class="form-control"/></div>
                                    </div>
                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Cycle End date</label>
                                    <div class="col-sm-8"><form:input path="paycycleduration" value="${partner.paycycleduration}" placeholder="Duration of Payment from Start Date" class="form-control"/></div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Payment date</label>
                                    <div class="col-sm-8"><form:input path="paydaysfromstartday"  value="${partner.paydaysfromstartday}" placeholder="Duration of Payment from Start Date" class="form-control"/></div>
                                    </div>

                                    <div class="mar-btm-20-oh"><label class="col-sm-4 control-label">Payment From</label>
                                    <div class="col-sm-8">
                                    <form:select path="paycyclefromshipordel" items="${datemap}" class="form-control" name="account" id="paymentField">
                                          </form:select></div>
                                    </div>
                                    </div>
                                    <small class="help-block">(For ex: If your first payment cycle of month is staring from 5th May to 10th May and Payment date for that cycel is 15th May , then you Start Date will have 5 End day will have 10 and Payment day will have 15)</small>
                                </div>
                                <div class="col-sm-12 radio1" id="blk-datewisepay">
                                    <div class="row">
                                        <div class="col-md-6">
                                             <form:select path="isshippeddatecalc" items="${datemap}" class="form-control" id="paymentField1"> 
                                        </form:select>
                                        </div>
                                        
                                        <div class="col-md-6 payment-box" id="true"><form:input path="noofdaysfromshippeddate" id="noofdaysfromshippeddate" value="${partner.noofdaysfromshippeddate}"
                                        placeholder="Payment Days From Shipped Date" class="form-control"/></div>
                                        <div class="col-md-6 payment-box" id="false"><form:input path="noofdaysfromdeliverydate" id="noofdaysfromdeliverydate" value="${partner.noofdaysfromdeliverydate}"
                                        placeholder="Payment Days From Delivery Date" class="form-control"/></div>
                                    </div>
                                </div>
                                <div class="col-sm-12 radio1" id="blk-monthly">
                                     <div class="row">
                                        <div class="col-md-4">
                                           <form:input path="monthlypaydate" value="${partner.monthlypaydate}"  placeholder="Enter Day" class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="hr-line-dashed"></div>
                                    <div class="form-group"><label class="col-sm-3 control-label">Max RTO Acceptance Period</label>
                                            <div class="col-md-4"><form:input path="maxRTOAcceptance" value="${partner.maxRTOAcceptance}"  placeholder="Enter Value" class="form-control"/></div>
                                            <div class="col-md-4"><select class="form-control" name="account">
                                            <option>Days</option>
                                            </select></div>
                                </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group"><label class="col-sm-3 control-label">Max Return Acceptance</label>
                                            <div class="col-md-4"><form:input path="maxReturnAcceptance" value="${partner.maxReturnAcceptance}"  placeholder="Enter Value" class="form-control"/></div>
                                            <div class="col-md-4"><select class="form-control" name="account">
                                            <option>Days</option>
                                            </select></div>
                                </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group">
                                    <div class="col-md-4"><form:checkbox path="tdsApplicable" id="tdsApplicable"/> TDS Applicable</div>
                                </div>
                                </div>
                                <div class="col-sm-12">
                                <div class="hr-line-dashed"></div>
                                 <!--    <button class="btn btn-primary pull-right" type="button"  onclick="submitform()">Save</button> -->
                                       <input type="button" class="btn btn-primary pull-right" value="Save" onclick="submitForm()"/>
                                </div>
                         <%--    </form:form> --%>
                        </div>
                    </div>
                </div>

<div class="col-lg-12">
  <div class="ibox float-e-margins">
    <div class="ibox-title">
      <h5>State Selection</h5>
    </div>
    <div class="ibox-content add-company">
      <div class="row">
        <div class="col-sm-2">Select State</div>
        <div class="col-sm-2">Local</div>
        <div class="col-sm-2">Zonal</div>
        <div class="col-sm-2">National</div>
        <div class="col-sm-2">Metro</div>
      

      </div>
      <div class="row">
        <div class="col-lg-12 m-l-n">
          <div class="panel panel-default">        
            <div class="panel-body">
               <div id="pickList">
               
               </div>
               <br><br>
            </div>
         </div>
        </div>

        
        </div>

      </div>
    </div>
  </div>
</div>
<div class="col-lg-12">
  <div class="ibox float-e-margins">
    <div class="ibox-title">
      <h5>Dropship</h5>
    </div>
    <div class="ibox-content add-company">
      <div class="col-lg-12">
      <!--   <input type="checkbox" class="js-switch_2" id="nr-switch" /> -->
        <form:checkbox path="nrnReturnConfig.nrCalculator"   class="js-switch_2" id="nr-switch" />
        <label>NR Switch</label>
        <div class="col-sm-12 radio5" id="nr-switch-sec"> 
          <!--<div class="col-sm-12 radio1" id="blk-200">-->
          <h3>NR Calculator</h3>
          <div class="panel-body">
            <div class="panel-group" id="accordion">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne1">Commission</a> </h5>
                </div>
                <div id="collapseOne1" class="panel-collapse collapse in">
                  <div class="panel-body">
                    <div class="col-sm-12">
                      <div class="col-sm-6">
                        <div class="radio">
                          <label>
                          <form:radiobutton path="nrnReturnConfig.commissionType" value="fixed" id="optionsRadios1" name="toggler"/>
                      <!--       <input type="radio" value="4" id="optionsRadios1" name="toggler"> -->
                            Fixed</label>
                        </div>
                      </div>
                      <div class="col-sm-6">
                        <div class="radio">
                          <label>
                           <form:radiobutton path="nrnReturnConfig.commissionType" value="categoryWise" id="optionsRadios1" name="toggler"/>
                           <!--  <input type="radio" value="5" id="optionsRadios1" name="toggler"> -->
                            Category Wise </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 radio1" id="blk-4">
                      <div class="col-sm-12">
                        <div class="form-group">
                          <div class="input-group m-b col-md-4">
                            <input type="text" class="form-control" name="fixedCommissionPercent">
                            <span class="input-group-addon">%</span></div>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 radio1" id="blk-5">
                     <c:if test="${!empty categoryList}">
           <c:forEach items="${categoryList}" var="category" varStatus="loop">
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label">${category}</label>
                        <div class="input-group m-b col-md-4">
                          <input type="text" class="form-control" name='"prodcat"+${category}'>
                        <!--   <span class="input-group-addon">%</span> --></div>
                      </div>
                    </c:forEach>
                    </c:if>
                    </div>
                  </div>
                </div>
              </div>
             
                <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo1">Fixed Fee</a> </h4>
                </div>
                <div id="collapseTwo1" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="col-sm-4" style="border-right:1px dotted #ccc;">
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label content-rgt">&lt;250</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeelt250">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-12 control-label">&gt;250&&&lt;500</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeegt250lt500">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label">&gt;500</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeegt500">
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-4" style="border-right:1px dotted #ccc;">
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label content-rgt">&lt;500</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeelt500Big">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-12 control-label">&gt;500&&&lt;1000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeegt500lt1000">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label">&gt;1000&&&lt;10000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeegt1000lt10000">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label">&gt;10000</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeegt10000">
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <div class="form-group col-md-12">
                        <label class="col-md-4 control-label content-rgt">&gt;500</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeelt500">
                        </div>
                      </div>
                      <div class="form-group col-md-12">
                        <label class="col-md-12 control-label">&gt;500</label>
                        <div class="col-md-12 content-rgt">
                          <input type="text" placeholder="" class="form-control" style="width:50%;" name="fixedfeegt500">
                        </div>
                      </div>
                    </div>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree1">Payment Collection Charges</a> </h4>
                </div>
                <div id="collapseThree1" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="col-sm-12">
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                            <form:checkbox path="nrnReturnConfig.whicheverGreaterPCC" id="whicheverGreaterPCC"/>
                              <i></i> Which Ever Is Greater </label>
                          </div>
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                              <input type="checkbox" value="" name="ispercentSPPCC">
                              <i></i> Percentage of SP </label>
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="input-group m-b">
                            <input type="text" class="form-control" name="percentSPPCC">
                            <span class="input-group-addon">%</span></div>
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="col-sm-6">
                          <div class="checkbox i-checks">
                            <label>
                              <input type="checkbox" value="" name="isfixedAmountPCC">
                              <i></i> Fixed Amount </label>
                          </div>
                        </div>
                        <div class="col-sm-6">
                          <div class="input-group m-b">
                            <input type="text" class="form-control" name="fixedAmtPCC">
                            <span class="input-group-addon">%</span></div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsefour1">Shipping Fee</a> </h4>
                </div>
                <div id="collapsefour1" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="col-sm-12">
                      <div class="col-sm-6">
                        <div class="radio">
                          <label>
                            <!-- <input type="radio" value="6" id="optionsRadios1" name="toggler"> -->
                            <form:radiobutton path="nrnReturnConfig.shippingFeeType" value="variable" id="variable" name="toggler"/>
                            Variable Shipping Charges  </label>
                        </div>
                      </div>
                      <div class="col-sm-6">
                        <div class="radio">
                          <label>
                         <!--    <input type="radio" value="7" id="optionsRadios1" name="toggler"> -->
                              <form:radiobutton path="nrnReturnConfig.shippingFeeType" value="fixed" id="fixed" name="toggler"/>
                         
                            Fixed Shipping Charges </label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 radio1" id="blk-variable">
                      <div class="col-sm-12 center-align text-center font-bold">
                        <h4 class="text-info">Which Ever is Higher</h4>
                      </div>
                      <div class="col-sm-6">
                        <h4>Volume calculation= (lxbxh)(cm)/5000</h4>
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>Volume Weight Slab(gms) </th>
                              <th>Local </th>
                              <th>Zonal </th>
                              <th>National </th>
                              <th>Metro </th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td><label>&lt; 500</label></td>
                              <td><div class="form-group ">
                                  <div class=" content-rgt">
                                    <input type="text" placeholder="" class="form-control" name="localvwlt500">
                                  </div>
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="zonalvwlt500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="nationalvwlt500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="metrovwlt500">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>500 &gt; 1000</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="localvwgt500lt1000">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="zonalvwgt500lt1000"> 
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="nationalvwgt500lt1000">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="metrovwgt500lt1000">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>1000 &gt; 1500</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="localvwgt1000lt1500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="zonalvwgt1000lt1500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="nationalvwgt1000lt1500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="metrovwgt1000lt1500">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>1500 &gt; 5000</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="localvwgt1500lt5000">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="zonalvwgt1500lt5000">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="nationalvwgt1500lt5000">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="metrovwgt1500lt5000">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>add 1 kg</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="localvwgt5000">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="zonalvwgt5000">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="nationalvwgt5000">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="metrovwgt5000">
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div class="col-sm-6">
                        <h4>Weight calculation</h4>
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>Dead Weight Slab(gms) </th>
                              <th>Local </th>
                              <th>Zonal </th>
                              <th>National </th>
                              <th>Metro </th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td><label>&lt; 500</label></td>
                              <td><div class="form-group ">
                                  <div class=" content-rgt">
                                    <input type="text" placeholder="" class="form-control"  name="localdwlt500">
                                  </div>
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="zonaldwlt500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="nationaldwlt500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="metrodwlt500">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>additional &gt; 500</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="localdwgt500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="zonaldwgt500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="nationaldwgt500">
                                </div></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="metrodwgt500">
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                    <div class="col-sm-12 radio1" id="blk-fixed">
                      <div class="col-sm-12 center-align text-center font-bold">
                        <h4 class="text-info">Which Ever is Higher</h4>
                      </div>
                      <div class="col-sm-6">
                        <h4>Weight calculation </h4>
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>Dead Weight Slab(gms) </th>
                              <th>Price </th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td><label>&lt; 500</label></td>
                              <td><div class="form-group ">
                                  <div class=" content-rgt">
                                    <input type="text" placeholder="" class="form-control" name="fixeddwlt500">
                                  </div>
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>additional &gt; 500</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="fixeddwgt500">
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div class="col-sm-6">
                        <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                        <table class="table table-bordered">
                          <thead>
                            <tr>
                              <th>Volume Weight Slab(gms) </th>
                              <th>Price </th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td><label>&lt; 500</label></td>
                              <td><div class="form-group ">
                                  <div class=" content-rgt">
                                    <input type="text" placeholder="" class="form-control"  name="fixedvwlt500">
                                  </div>
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>500 &gt; 1000</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="fixedvwgt500lt1000">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>1000 &gt; 1500</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control"  name="fixedvwgt1000lt1500">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>1500 &gt; 5000</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="fixedvwgt1500lt5000">
                                </div></td>
                            </tr>
                            <tr>
                              <td><label>add 1 kg</label></td>
                              <td><div class=" content-rgt">
                                  <input type="text" placeholder="" class="form-control" name="fixedvwgt5000">
                                </div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsefive1">Service Tax</a> </h4>
                </div>
                <div id="collapsefive1" class="panel-collapse collapse">
                  <div class="panel-body">
                    <div class="form-group col-md-12">
                      <div class="col-md-4 content-rgt">
                        <input type="text" placeholder="Enter Service Tax %" class="form-control"  name="serviceTax">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="ibox float-e-margins">
    <div class="ibox-title">
      <h5>Return Calculator</h5>
    </div>
    <div class="ibox-content add-company">
      <div class="panel-body">
        <div class="panel-group" id="accordion">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h5 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Return Charges</a> </h5>
            </div>
            <div id="collapseOne" class="panel-collapse collapse in">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <form:radiobutton path="nrnReturnConfig.retCharType" value="fixed" id="retrun-sf-fix" name="toggler"/>
                     <!--    <input type="radio" value="8" id="optionsRadios1" name="toggler"> -->
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                           <form:radiobutton path="nrnReturnConfig.retCharType" value="variable" id="retrun-sf-variable" name="toggler"/>
                     <!--    <input type="radio" value="9" id="optionsRadios1" name="toggler"> -->
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-retrun-sf-fix">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Enter Fix Charges</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control" name="retCharSFFixedAmt">
                        </div>
                      </div>
				
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-retrun-sf-variable">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">% of SP</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="Enter %" class="form-control" name="retCharSFPercentSP">
                      </div>
						<label class="col-sm-2 control-label">Fix Amount</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="Enter Amount" class="form-control" name="retCharSFVarFixedAmt">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                        <!--   <input type="checkbox" value=""> -->
                            <form:checkbox path="nrnReturnConfig.retCharSFFF" id="retCharSFFF"/>
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                       <!--    <input type="checkbox" value=""> -->
                               <form:checkbox path="nrnReturnConfig.retCharSFFF" id="retCharSFFF"/>
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                       <!--    <input type="checkbox" value=""> -->
                          <form:checkbox path="nrnReturnConfig.retCharSFShipFee" id="retCharSFShipFee"/>
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                           <form:radiobutton path="nrnReturnConfig.revshippingFeeType"  value="variable" id="revsf-variable" name="toggler"/>
                                         <!--  <input type="radio" value="201" id="optionsRadios1" name="toggler"> -->
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                            <form:radiobutton path="nrnReturnConfig.revshippingFeeType"  value="fixed" id="revsf-fixed" name="toggler"/>
                                         <!--  <input type="radio" value="202" id="optionsRadios1" name="toggler"> -->
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-revsf-variable">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-revsf-fixed">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Return</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                      <form:radiobutton path="nrnReturnConfig.retCharType" value="variable" id="retrun-sf-variable" name="toggler"/>
                       <!--  <input type="radio" value="10" id="optionsRadios1" name="toggler"> -->
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="11" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-10">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-11">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="203" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="204" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-203">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-204">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">RTO Charges</a> </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="12" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="13" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-12">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-13">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="205" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="206" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-205">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-206">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Return</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="14" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="15" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-14">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-15">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="207" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="208" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-207">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-208">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">Replacement Charges</a> </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="16" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="17" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-16">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-17">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="209" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="210" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-209">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-210">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Return</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="18" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="19" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-18">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-19">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="211" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="212" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-211">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-212">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsefive">Partial Delivery Charges</a> </h4>
            </div>
            <div id="collapsefive" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="20" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="21" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-20">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-21">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="213" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="214" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-213">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-214">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Return</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="22" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="23" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-22">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-23">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="215" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="216" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-215">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-216">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title"> <a data-toggle="collapse" data-parent="#accordion" href="#collapsesix">Cancellation Charges</a> </h4>
            </div>
            <div id="collapsesix" class="panel-collapse collapse">
              <div class="panel-body">
                <h4>Seller Fault </h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <h5>Before RTD</h5>
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="24" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="25" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-24">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-25">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="217" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="218" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-217">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-218">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <h5>After RTD</h5>
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="124" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="125" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-124">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-125">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="219" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="220" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-219">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-220">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <h4>Buyer Cancellation</h4>
                <div class="col-sm-12">
                  <div class="hr-line-dashed"></div>
                </div>
                <div class="col-sm-12">
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="26" id="optionsRadios1" name="toggler">
                        Fixed Amount</label>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="radio">
                      <label>
                        <input type="radio" value="27" id="optionsRadios1" name="toggler">
                        Variable </label>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12 radio1 m-b" id="blk-26">
                    <div class="col-sm-12">
                      <div class="form-group">
                        <div class="col-md-2 content-rgt">
                          <label>Lable</label>
                        </div>
                        <div class="col-md-3 content-rgt">
                          <input type="text" placeholder="" class="form-control">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 radio1" id="blk-27">
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">lable</label>
                      <div class="col-sm-3">
                        <input type="text" placeholder="" class="form-control">
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> % of SP or commission </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Fixed Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Shipping Fee </label>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="checkbox i-checks">
                        <label>
                          <input type="checkbox" value="">
                          <i></i> Service Tax </label>
                      </div>
                    </div>
                    <div class="form-group col-md-12">
                      <label class="col-sm-2 control-label">Reverse Shipping Fee</label>
                      <div class="col-sm-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal"> View </button>
                        <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                          <div class="modal-dialog modal-lg">
                            <div class="modal-content animated bounceInRight">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span> </button>
                                <h4 class="modal-title">Reverse Shipping Charges </h4>
                              </div>
                              <div class="modal-body">
                                <div class="row">
                                  <div class="col-sm-12">
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="221" id="optionsRadios1" name="toggler">
                                          Variable</label>
                                      </div>
                                    </div>
                                    <div class="col-sm-6">
                                      <div class="radio">
                                        <label>
                                          <input type="radio" value="222" id="optionsRadios1" name="toggler">
                                          Fixed </label>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-221">
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                    <div class="row">
                                      <div class="col-sm-2">
                                        <div class="form-group">
                                          <label>Label</label>
                                        </div>
                                      </div>
                                      <div class="col-sm-3">
                                        <div class="form-group">
                                          <input type="text" placeholder="" class="form-control">
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="col-sm-12 radio1" id="blk-222">
                                    <div class="col-sm-12 center-align text-center font-bold">
                                      <h4 class="text-info">Which Ever is Higher</h4>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Volume calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 500 &gt; 1000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1000 &gt; 1500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> 1500 &gt; 5000</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td> add 1 kg</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                    <div class="col-sm-6">
                                      <h4>Weight calculation= (lxbxh)(cm)/5000 </h4>
                                      <table class="table table-bordered">
                                        <thead>
                                          <tr>
                                            <th>Volume Weight Slab(gms) </th>
                                            <th>Local </th>
                                            <th>Zonal</th>
                                            <th>National</th>
                                            <th>Metro</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                          <tr>
                                            <td>&lt;500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                          <tr>
                                            <td>&gt; 500</td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                            <td><input type="text" placeholder="" class="form-control"></td>
                                          </tr>
                                        </tbody>
                                      </table>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="modal-footer">
                                <button type="button" class="btn btn-white" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                              </div>
                            </div>
                          </div>
                          
                          <div class="footer">
                            <div class="pull-right"> <img alt="image"  src="img/go-easy-logo.jpg" /> </div>
                            <div> <a href="">Support</a> | <a href="">Contact Us</a> </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
</div>
  	</div>
	 </form:form>
	<!-- iCheck --> 
<script src="/O2R/seller/js/plugins/iCheck/icheck.min.js"></script> 
<!-- Switchery --> 
<script src="/O2R/seller/js/plugins/switchery/switchery.js"></script> 
<script src="js/pickList.js"></script>
<script type="text/javascript">
$(document).ready(function () {
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
	var elem = document.querySelector('.js-switch');
var switchery = new Switchery(elem, { color: '#1AB394' });

var elem_2 = document.querySelector('.js-switch_2');
var switchery_2 = new Switchery(elem_2, { color: '#ED5565' });

var elem_3 = document.querySelector('.js-switch_3');
var switchery_3 = new Switchery(elem_3, { color: '#1AB394' });

$("[name=toggler]").click(function(){
	$('.radio1').hide();
	$("#blk-"+$(this).val()).slideDown();
});


$("#nr-switch").change(function() {
if(this.checked) {

$('.radio5').hide();
$("#nr-switch-sec").slideDown();
}
else
{
$("#nr-switch-sec").slideUp();

}
});


$("[name=paymentType]").click(function(){
	 $('.radio1').hide();
   $("#blk-"+$(this).val()).slideDown();
});
$('#paymentField').change(function () {
   $('.payment-box').hide();
   $('#'+$(this).val()).fadeIn();
});
$('#paymentField1').change(function () {
	$('.payment-box').hide();
   $('#'+$(this).val()).fadeIn();
});
$('#data_1 .input-group.date').datepicker({
       todayBtn: "linked",
       keyboardNavigation: false,
       forceParse: false,
       calendarWeeks: true,
       autoclose: true
   });

if('${partner.paymentType}'=='paymentcycle')
  $("#paymentcycle").prop("checked", true).trigger("click");
else if('${partner.paymentType}'=='datewisepay')
  {
  $("#datewisepay").prop("checked", true).trigger("click");
  $('#paymentField1').trigger('change');
 if('${partner.isshippeddatecalc}'!=true)
	   {
	  $("#noofdaysfromdeliverydate").val('${partner.noofdaysfromshippeddate}');
	   }
  }
else if('${partner.paymentType}'=='monthly')
  $('#monthly').prop("checked", true).trigger("click");

if('${partner.tdsApplicable}'=='true')
	   $("#tdsApplicable").prop("checked", true);
});

var nameAvailability=true;

function checkOnBlur()
{
	var partner=document.getElementById("partnerName").value;
	$.ajax({
        url: "ajaxPartnerCheck.html?partner="+partner,
       success : function(res) {
    	 	   if(res=="false")
                	{
        	nameAvailability=false;
                	 $("#partnerNameMessage").html("Partner Name  already exist");
                	}
                else
                	{
                	nameAvailability=true;
                	$("#partnerNameMessage").html("Partner Name   available");
                	}
      }
	 });
	}
function submitForm(){
 var validator = $("#addpartnerform").validate({
  rules: {                   
	  pcName: "required",
     maxReturnAcceptance: {
			required: true,
	        min: 1,
	        max:100,
			number:true,
		},
	maxRTOAcceptance:{
		required:true,
		min:1,
		max:100,
		number:true,
	}	,
	toggler:{
		required:true
	},
	startcycleday:{
		 required: function(element){
			 return (getRole() == 'paymentcycle');
		   },
		number: true,
		min:1,
		max:31,
		
	},
	paycycleduration:{
		 required: function(element){
			 return (getRole() == 'paymentcycle');
		   },
		number: true,
		min:1,
		max:31,
		
	},
	paydaysfromstartday:{
		 required: function(element){
			 return (getRole() == 'paymentcycle');
		   },
		number: true,
		min:1,
		max:31,
		
	},
	monthlypaydate:{
		 required: function(element){
			 return (getRole() == 'monthly');
		   },
		number: true,
		min:1,
		max:31,
		
	}
  	},	
     errorElement: "span",                              
     messages: {
    	 pcName: " Enter partner name",
    	 maxReturnAcceptance:"Max return required between 1 and 100",
    	 maxRTOAcceptance:"RTO acceptance required between 1 and 100",
    	 toggler:"Please select any Payment Cycle"
     }
 });
 if(validator.form()&&nameAvailability){ // validation perform
	 $('form#addpartnerform').submit();
	 }
}
function getRole() {
    return $("#addpartnerform").find("input[type=radio]:checked").val();
}

//Code for state selection module 
  var val = 
         {
            01: {id:01, text: 'Andhra Pradesh'},
            02: {id:02,text:'Andaman and Nicobar Islands'},
            03: {id:03, text: 'Arunachal Pradesh'},
            04: {id:04, text:'Assam'},
            05: {id:05, text:'Chhattisgarh'},
            06: {id:06, text:'Chandigarh'},
            07: {id:07, text:'Dadra and Nagar Haveli'},
            08: {id:08, text:'Daman and Diu'},
            09: {id:09, text:'Delhi'},
            10: {id:10, text:'Goa'},
            11: {id:11, text:'Gujarat'},
            12: {id:12, text:'Haryana'},
            13: {id:13, text:'Himachal Pradesh'},
            14: {id:14, text:'Jammu and Kashmir'},
            15: {id:15, text:'Jharkhand'},
            16: {id:16, text:'Karnataka'},
            17: {id:17, text:'Kerala'},
            18: {id:18, text:'Lakshadweep'},
            19: {id:19, text:'Madhya Pradesh'},
            20: {id:20, text:'Maharashtra'},
            21: {id:21, text:'Manipur'},
            22: {id:22, text:'Meghalaya'},
            23: {id:23, text:'Mizoram'},
            24: {id:24, text:'Nagaland'},
            25: {id:25, text:'Odisha'},
            26: {id:26, text:'Punjab'},
            27: {id:27, text:'Pondicherry'},
            28: {id:28, text:'Rajasthan'},
            29: {id:29, text:'Sikkim'},
            30: {id:30, text:'Tamil Nadu'},
            31: {id:31, text:'Telangana'},
            32: {id:32, text:'Tripura'},
            33: {id:33, text:'Uttar Pradesh'},
            34: {id:34, text:'Uttarakhand'},
            35: {id:35, text:'West Bengal'}
         };

         var pick = $("#pickList").pickList({data: val});


         $("#getSelected").click(function () {
            console.log(pick.getValues());
         });
      
</script>                 
 </body>
</html>