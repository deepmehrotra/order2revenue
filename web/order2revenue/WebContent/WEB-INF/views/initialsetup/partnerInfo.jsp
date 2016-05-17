<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
  
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script type="text/javascript">
    function onclickaddpartner(value,id) {
    	var urltogo="";
    	if(value=="add")
    		{
    	if(id!=0)
    		{
    		urltogo='addPartner.html?pid='+id;
    		location.href=urltogo;
    		}
    	else
    		{
    		urltogo='addPartner.html';
    		location.href=urltogo;
    		}
    		
    	}
    	else if(value=="partnerDetails")
		{
    		urltogo='listPartners.html';
		}
    	else
  			{
  			urltogo='editPartner.html?pcId='+id;
  			}
        $.ajax({
        	
            url : urltogo,
            success : function(data) {
            $('#centerpane').html(data);
            }
        });
    }
    function onclickConfigurepartner(value) {
    	var urltogo="";
    	if(value.localeCompare("Jabong") != 0){
    		if(value.localeCompare("Myntra") != 0){
    			urltogo='addPartner.html?partnerName='+value;
    		}else{
    			urltogo='addMyntra.html?partnerName='+value;
    		}
    	}else{
    		urltogo='addJabong.html?partnerName='+value;
    	}    	
    	location.href=urltogo;
    	
       /*  $.ajax({
        	
            url : urltogo,
            success : function(data) {
            $('#centerpane').html(data);
            }
        }); */
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
                            <h5>Active Sales Channels</h5>
                            <div class="ibox-tools">
                                <a href="#"  onclick="onclickaddpartner('add',0)" class="btn btn-primary btn-xs" >Add Sales Channel</a>&nbsp;&nbsp;
                                 <a href="#"  onclick="onclickaddpartner('partnerDetails',0)" class="btn btn-primary btn-xs" >Channel Details</a>
                            </div>
                        </div>
                        <div class="ibox-content add-company">
                         <c:if test="${!empty partners}">
                         <c:forEach items="${partners}" var="partner">
                    <div class="col-lg-3">
                        <div class="panel panel-default add-logo-page">
                            <div class="panel-body text-center" style="height: 100px; width: 200px;">
                            	<c:choose>
                            		<c:when test="${partner.pcLogoUrl != null}">
                            			<img alt="image"  src="${partner.pcLogoUrl}"  title="${partner.pcName}">
                            		</c:when>
                            		<c:otherwise>
                            			<img alt="image"  src="/O2R/partnerimages/5Yamaha.jpg"  title="${partner.pcName}">
                            		</c:otherwise>                            	
                            	</c:choose>                            	
                                <div><a href="editPartner.html?pcId=${partner.pcId}"  style="width:49%;z-index: 9999;" onclick=""><i class="fa fa-pencil"></i></a>
								<a href="viewPartner.html?pcId=${partner.pcId}"><i class="fa fa-eye" style="position: relative;left: 54px;"></i></a></div>	
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                    </c:if>
                   
                </div>
            </div>
        </div>

        <div class="col-lg-12">
                <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Choose Sales Channels</h5>
                        </div>
                        <div class="ibox-content add-company">
                         <c:if test="${!empty partnertoadd}">
                         <c:forEach items="${partnertoadd}" var="addpartner" varStatus="loop">
                    <div class="col-lg-3">
                        <div class="panel panel-default add-logo-page">
                            <div class="panel-body text-center">
                                <img alt="image"  src="${addpartner.pcLogoUrl}"  title="${addpartner.pcName}">
                                <a href="#"  onclick="onclickConfigurepartner('${addpartner.pcName}')"><i class="fa fa-plus"></i></a>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                    </c:if>
                   
                </div>
            </div>
        </div>

</div>

        </div>
 <jsp:include page="../globalfooter.jsp"></jsp:include>

    </div>
</div>

<jsp:include page="../globaljslinks.jsp"></jsp:include>
</body>
</html>