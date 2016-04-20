<!-- Mainly scripts -->
<script src="/O2R/seller/js/jquery-2.1.1.js"></script>
<script src="/O2R/seller/js/bootstrap.min.js"></script>
<script src="/O2R/seller/js/custom.js"></script>
<script src="/O2R/seller/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="/O2R/seller/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
 <!-- Jquery Validate -->
    <script src="/O2R/seller/js/plugins/validate/jquery.validate.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="/O2R/seller/js/inspinia.js"></script>
<script src="/O2R/seller/js/plugins/pace/pace.min.js"></script>

<!-- Data picker -->
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script>

<script type="text/javascript">
function onclickSideNavigation(value) {
	var targeturl="";
	switch(value)
	{
	case "Partner" :
		targeturl="partners.html";
	break;
	case "ManualCharges" :
		targeturl="manualCharges.html";
	break;
	case "Payment" :
		targeturl="paymentUploadList.html";
	break;
	case "InventoryGroup" :
		targeturl="inventoryGroups.html";
	break;
	case "Product" :
		targeturl="Product.html";
	break;
	case "Expenses" :
		targeturl="expenseCategories.html";
	break;
	case "OrderDA" :
		targeturl="orderList.html";
	break;
	case "RTO/Return" :
		targeturl="returnOrderList.html";
	break;
	case "Inventory" :
		targeturl="inventoryList.html";
	break;
	case "ExpensDA" :
		targeturl="expenselist.html";
	break;
	case "Tax" :
		targeturl="taxDetailList.html";
	break;
	case "TDS" :
		targeturl="tdsDetailList.html";
	break;
	case "Events" :
		targeturl="eventsList.html";
	break;
	case "Reports" :
		targeturl="getAllReports.html";
	break;
	
	
	
	}
    $.ajax({
        url : targeturl,
        success : function(data) {
            $('#centerpane').html(data);
        }
    });
}
</script>
<script>
$(document).ready(function(){
    $('#searchCriteria').change(function () {
         $('.TopSearch-box').hide();
         $('#'+$(this).val()).fadeIn();
     });
     $('.input-group.date').datepicker({
             todayBtn: "linked",
             keyboardNavigation: false,
             forceParse: false,
             calendarWeeks: true,
             autoclose: true
         });
 });
</script>