<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
 <jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script type="text/javascript">
function getmappings()
{
	
	var channelName=document.getElementById('channelName').value;
	var fileName=document.getElementById('fileName').value;
	alert("Inside upload");
	alert("fileName="+fileName+"&channelName="+channelName);
	window.location='uploadmappings.html?fileName='+fileName+'&channelName='+channelName;
	
	
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
                <div class="col-lg-12 text-center">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5 class="text-center">Channel Mapping</h5>
                        </div>
                        <div class="ibox-content report-links">
							<div class="col-lg-12 text-center">
								<h1>Map Channel Upload Files Directly </h1>
							</div>
							<div class="col-lg-12">
								<div class="col-lg-3">
								</div>
								<div class="col-lg-3">
									<select class="form-control" name="selectchannelName" id="channelName">
                                             <c:forEach items="${partnerNames}" var="channelName">
										   
										         <option value="${channelName}">${channelName}</option>
										    </c:forEach>
                                            </select>
								</div>
								<div class="col-lg-3" style="padding: 0px 16px 0px 0px;">
									<select class="form-control" name="selectfileName" id="fileName">
                                             <c:forEach items="${fileNames}" var="fileName" >
										   
										         <option value="${fileName}">${fileName}</option>
										    </c:forEach>
                                            </select>
								</div>
								<div class="col-lg-3" style="margin: 0px;padding: 0px;">
                                      <a href="#" 
                                      onclick="getmappings()" class="btn btn-white pull-left" ><i class="fa fa-search"></i></a>
                                </div>
							</div>
							<div class="col-lg-12 m-t-xs">
									<div class="col-lg-3">
									</div>
									<form:form method="POST" action="savemappingdetails.html"	id="savemappingdetailsForm" 
									 modelAttribute="mapping" role="form" class="form-horizontal">
										<input type="hidden" name="channelName" value="${channelName}"/>
                                        <input type="hidden" name="fileName" value="${fileName}"/>
									<div class="col-lg-6">
										<table class="" border="1" style="border: 1px solid #ccc;width:100%;">
										<tbody>
										<c:choose>
										<c:when test="${mapping != null}">
										
                                        <input type="hidden" name="mapId" value="${mapping.mapId}"/>
										<c:forEach items="${mapping.columMap}" var="columMap" varStatus="status">
											<tr>
												<td style="width:70%;padding: 4px;">
												<input name="${columMap.o2rColumName}" value="${columMap.o2rColumName}"
												 readonly="readonly"/>
							
												</td>
												<td style="width:70%;padding: 4px;">
												<input name="map-${columMap.o2rColumName}" value="${columMap.channelColumName}"/>
												</td>
											</tr>
											</c:forEach>
											</c:when>
											<c:when test="${o2rheaders != null}">
											<c:forEach items="${o2rheaders}" var="o2rheaders" varStatus="status">
										
											<tr>
												<td style="width:70%;padding: 4px;">
												<input name="${o2rheaders}" value="${o2rheaders}"
												 readonly="readonly"/>
							
												</td>
												<td style="width:70%;padding: 4px;">
												<input name="map-${o2rheaders}"/>
												</td>
											</tr>
											</c:forEach>
											</c:when>
											</c:choose>
										</tbody>
									</table>
									</div>
									<div class="col-lg-3">
									<button class="btn btn-primary pull-right" type="submit"
								>Save</button>
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
</div>

<jsp:include page="../globaljslinks.jsp"></jsp:include>

<script type="text/javascript">
var nameAvailability=true;
$(document).ready(function() {
	
	$("#fileName").val('${fileName}');
	$("#channelName").val('${channelName}');
	$("#selectfileName").val('${fileName}');
	$("#selectchannelName").val('${channelName}');
	
	
});
</script>
</html>