<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="org.springframework.core.io.*"%>
<%@ page
	import="org.springframework.core.io.support.PropertiesLoaderUtils"%>
<%@ page language="java" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<style type="text/css">
.lable {
	margin-top: -55px;
	font-size: 17px;
	text-align: center;
	z-index: 999999999999;
	color: #080e08;
	font-weight: 800;
	font-style: normal;
	border-radius: 10px;
}

.partnerImg {
	height: 91px;
	object-fit: contain;
}
</style>
<jsp:include page="../globalcsslinks.jsp"></jsp:include>
<script type="text/javascript">
                        function onclickaddpartner(value, id) {
                            var urltogo = "";
                            if (value == "add") {
                                if (id != 0) {
                                    urltogo = 'addPartner.html?pid=' + id;
                                    location.href = urltogo;
                                } else {
                                    urltogo = 'addPartner.html';
                                    location.href = urltogo;
                                }

                            } else if (value == "partnerDetails") {
                                urltogo = 'listPartners.html';
                            } else {
                                urltogo = 'editPartner.html?pcId=' + id;
                            }
                            $.ajax({

                                url: urltogo,
                                success: function(data) {
                                	if($(data).find('#j_username').length > 0){
                                		window.location.href = "orderindex.html";
                                	}else{
                                    	$('#centerpane').html(data);
                                	}
                                }
                            });
                        }

                        function onclickConfigurepartner(value) {
                            var urltogo = "";
                            /* if (value.localeCompare("jabong") != 0) { */
                                if (value.localeCompare("myntra") != 0) {
                                    urltogo = 'addPartner.html?partnerName=' + value;
                                } else {
                                    urltogo = 'addMyntra.html?partnerName=' + value;
                                }
                            /* } else {
                                urltogo = 'addJabong.html?partnerName=' + value;
                            } */
                            location.href = urltogo;
                        }

                        function onclickEditpartner(id, name) {
                            var urltogo = "";
                            /* if (name.localeCompare("jabong") != 0) { */
                                if (name.localeCompare("myntra") != 0) {
                                    urltogo = 'editPartner.html?pcId=' + id;
                                } else {
                                    urltogo = 'editMyntra.html?pcId=' + id;
                                }
                            /* } else {
                                urltogo = 'editJabong.html?pcId=' + id;
                            } */
                            location.href = urltogo;
                        }
                        
                        function onclickDuplicatepartner(id, name) {
                            var urltogo = "";
                            /* if (name.localeCompare("jabong") != 0) { */
                                if (name.localeCompare("myntra") != 0) {
                                    urltogo = 'editPartner.html?pcId=' + id + "&isDuplicate=true";
                                } else {
                                    urltogo = 'editMyntra.html?pcId=' + id + "&isDuplicate=true";
                                }
                            /* } else {
                                urltogo = 'editJabong.html?pcId=' + id;
                            } */
                            location.href = urltogo;
                        }
                        </script>
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
								<h5>Active Sales Channels</h5>
								<div class="ibox-tools">
									<a href="listPartners.html" class="btn btn-primary btn-xs">View Channel Details</a>
									<a href="#" onclick="onclickaddpartner('add',0)"
										class="btn btn-primary btn-xs">Add Sales Channel</a>
								</div>
							</div>
							<div class="ibox-content add-company">
								<c:if test="${!empty partners}">
									<c:forEach items="${partners}" var="partner">
										<div class="col-lg-3">
											<div class="panel panel-default add-logo-page">
												<div class="panel-body text-center">
													<div class="partnerImg">
														<%
															org.springframework.core.io.Resource resource = new ClassPathResource(
																			"database.properties");
																	Properties props = PropertiesLoaderUtils
																			.loadProperties(resource);
														%>
														<c:choose>
															<c:when test="${partner.pcLogoUrl != null}">
																<img alt="image" src="${partner.pcLogoUrl}"
																	title="${partner.pcName}"
																	style="width: 100%; height: 100%;">
															</c:when>
															<c:otherwise>
																<img alt="image"
																	src="<%=props.getProperty(" defaultpartnerimage.view ") %>"
																	title="${partner.pcName}" style="width: 100%;">
																<b><label class="lable">${partner.pcName}</label></b>
															</c:otherwise>
														</c:choose>
													</div>
													<%-- <div class="panel panel-default add-logo-page" style="border-top: 0px solid #ccc;">
                                    <div class="panel-body col-lg-4 text-center">
                                        <a href="#" title="Edit" style="background: #2f4050;"
                                        	onclick="onclickEditpartner('${partner.pcId}','${partner.pcName}')"><i class="fa fa-edit"></i></a>
                                    </div>
									 <div class="panel-body col-lg-4 text-center">
                                        <a href="#" title="Duplicate" style="background:#1ab394;"><span class="glyphicon glyphicon-duplicate"></span></a>
										
                                    </div>
									 <div class="panel-body col-lg-4 text-center">
                                        <a href="viewPartner.html?pcId=${partner.pcId}" 
                                        	title="View" style="background:#2f4050;"><i class="fa fa-eye"></i></a>
                                    </div>
									
                                </div> --%>
													<div>
														<a title="Edit" href="#" style="width: 33%; z-index: 9999;"
															onclick="onclickEditpartner('${partner.pcId}','${partner.pcName}')"><i
															class="fa fa-pencil"></i></a> 
														<a title="Duplicate" href="#" style="width: 66%; z-index: 9998;"
															onclick="onclickDuplicatepartner('${partner.pcId}','${partner.pcName}')"><i
															class="fa fa-files-o" style="position: relative; left: 35px;"></i></a>
														<a title="View" href="viewPartner.html?pcId=${partner.pcId}"><i
															class="fa fa-eye" style="position: relative; left: 72px;"></i></a>
													</div>
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
									<c:forEach items="${partnertoadd}" var="addpartner"
										varStatus="loop">
										<div class="col-lg-3">
											<div class="panel panel-default add-logo-page">
												<div class="panel-body text-center">
													<img alt="image" src="${addpartner.pcLogoUrl}"
														title="${addpartner.pcName}" style="width: 100%;"> <a
														href="#"
														onclick="onclickConfigurepartner('${addpartner.pcName}')"><i
														class="fa fa-plus"></i></a>
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
