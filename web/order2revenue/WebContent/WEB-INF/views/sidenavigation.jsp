<%@page import="java.io.File"%>
<%@page import="com.o2r.helper.HelperClass"%>
<%@page import="com.o2r.bean.SellerBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%		
    if(session.getAttribute("logoUrl") == null){
        if (request.getAttribute("sellerBean") != null) {
            SellerBean bean = (SellerBean) request.getAttribute("sellerBean");
            if (bean.getLogoUrl() != null) {
                session.setAttribute("logoUrl", bean.getLogoUrl());
            } else {
                session.setAttribute("logoUrl",   "/O2R/sellerimages/defaultSeller.jpg");
            }
            session.setAttribute("sellerName", bean.getName());
        }
    }
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
#topSellerImg img {
	height: 80px;
	width: 80px;
	object-fit: contain;
}
</style>
</head>
<body>

	<nav class="navbar-default navbar-static-side" role="navigation">
	<div class="sidebar-collapse">
		<ul class="nav" id="side-menu">
			<li class="nav-header">
				<div class="dropdown profile-element" align="center"
					id="topSellerImg">
					<span> <c:choose>
							<c:when test='<%= session.getAttribute("logoUrl") != null %>'>
								<img alt="image" class="img-circle"
									src='<%= session.getAttribute("logoUrl") %>' />
							</c:when>
							<c:otherwise>
								<img alt="image" class="img-circle"
									src="/O2R/sellerimages/defaultSeller.jpg" />
							</c:otherwise>
						</c:choose>
					</span> <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
						class="clear"> <span class="block m-t-xs"> <strong
								class="font-bold"><%= session.getAttribute("sellerName")%></strong>
						</span> <span class="text-muted text-xs block">Seller<b
								class="caret"></b></span>
					</span>
					</a>
					<ul class="dropdown-menu animated fadeInRight m-t-xs">
						<li><a href="profile.html">Profile</a></li>
						<li><a href="partners.html">Partners</a></li>
						<li class="divider"></li>
						<li><a href="/j_spring_security_logout">Logout</a></li>
					</ul>

				</div>
				<div class="logo-element">O2R</div>
			</li>
			<li class="active"><a href="orderindex.html"><i
					class="fa fa-th-large"></i> <span class="nav-label">Dashboard</span></a>
			</li>
			<li><a href="minor.html"><i class="fa fa-sitemap"></i> <span
					class="nav-label">Initial Setup</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li class="active"><a href="partners.html">Sales Channels</a></li>
					<li><a href="#">Product Info <span class="fa arrow"></span></a>
						<ul class="nav nav-third-level">
							<li><a href="#"
								onclick="onclickSideNavigation('InventoryGroup')">Inventory
									Groups</a></li>
							<li><a href="Product.html">SKUs</a></li>
							<li><a href="ProductMapping.html">Product Mapping</a></li>

						</ul></li>
					<li><a href="expenseCategories.html">Expense Group</a></li>
					<li><a href="listTaxCategories.html">Tax Categories</a></li>
				</ul></li>
			<li><a href="minor.html"><i class="fa fa-laptop"></i> <span
					class="nav-label">Daily Activities</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li class="active"><a href="orderList.html">Order</a></li>
					<li><a href="poOrderList.html?value=">PO Orders</a></li>
					<li><a href="#" onclick="onclickSideNavigation('RTO/Return')">RTO/Return</a></li>
					<li><a href="gatepasslist.html">Gatepasses</a></li>
					<li><a href="paymentUploadList.html">Payment</a></li>
					<li><a href="#" onclick="onclickSideNavigation('Inventory')">Inventory</a></li>
					<li><a href="expenselist.html">Expenses</a></li>

				</ul></li>
			<li><a href="getAllReports.html"><i
					class="fa fa-bar-chart-o"></i> <span class="nav-label">Reports</span>
			</a></li>
			<li><a href="minor.html"><i class="fa fa-wrench"></i> <span
					class="nav-label">Miscellaneous</span> <span class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li class="active"><a href="#"
						onclick="onclickSideNavigation('ManualCharges')">Manual
							Charges</a></li>
					<li><a href="#" onclick="onclickSideNavigation('Tax')">Tax</a></li>
					<li><a href="#" onclick="onclickSideNavigation('TDS')">TDS</a></li>
					<li><a href="eventsList.html">Events</a></li>

				</ul></li>
			<li><a href="#"><i class="fa fa-envelope"></i> <span
					class="nav-label">Orders</span> </a></li>
		</ul>

	</div>
	</nav>

</body>
</html>