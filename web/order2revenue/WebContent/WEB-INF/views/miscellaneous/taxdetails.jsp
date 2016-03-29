<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="/O2R/seller/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css">
    <link href="/O2R/seller/css/jquery-ui.css" rel="stylesheet">
<link href="/O2R/seller/css/jtable/jtable.css" rel="stylesheet" type="text/css" />
</head>
<body>

<script src="/O2R/seller/js/jquery-ui-1.10.4.min.js" type="text/javascript"></script>
<!-- Data picker -->
<script src="/O2R/seller/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="/O2R/seller/js/jquery.jtable.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function () {

        //Prepare jtable plugin
        $('#TaxDetailContainer').jtable({
            title: 'Tax',
            paging: true,
            sorting: true,
            messagesNew: {
                editRecord: 'Update Status'
            },
            selecting: true, //Enable selecting
            multiselect: true, //Allow multiple selecting
            selectingCheckboxes: true, //Show checkboxes on first column
            //selectOnRowClick: false, //Enable this to only select using checkboxes
            actions: {
                listAction: 'listTaxDetailsJson.html',
               // deleteAction: 'deleteTaxDetailsJson.html',
                updateAction: 'savePaidTaxDetailJson.html'
               // createAction: 'saveTaxDetailsJson.html'
            },
            fields: {
            	taxId : {
					title : '',
					width : '0%',
					key : true,
					list: false,
					type :'hidden',
					create :true,
					edit:true
				},
				label: {
                	title: '',
                    width: '20%',
                    list : false,
					edit : true,
					create : false,
					input: function (data) {
			            return '<h2>Update tax status</h2>';
			        } 
                },
				description: {
                	title: 'Description',
                    width: '23%',
                    list : true,
					edit : false,
					create : false,
                    inputClass: 'validate[required]'
                },
                particular: {
                    title: 'Tax Category',
                    width: '13%',
                    list : true,
					edit : false,
					create : false,				
					inputClass: 'validate[required]'
                },
                taxortdsCycle: {
                    title: 'Tax Cycle',
                    list : true,
					edit : false,
					create : false,				
                    inputClass: 'validate[required]'
                },
                balanceRemaining: {
                    title: 'Amount to deposit',
                    width: '12%',
                    list : true,
					edit : false,
					create : false				
                   },
                   
               paidAmount: {
                       title: 'Paid Amount',
                       width: '12%',
                       list : true,
   					edit : true,
   					create : false				
                       
                   },
                
               uploadDate: {
                    title: 'Amount Till',
                    list: true,
                    edit : false,
					create : false
                    },
              
               
               dateOfPayment: {
                    title: 'Payment Date',
                    width: '12%',
                    type: 'date',
                    displayFormat: 'yy-mm-dd',
                    list : true,
   					edit : false,
   					create : false				
                 },
                 status: {
                    title: 'Status',
                    width: '15%',
                    list : true,
					edit : true,
					create : false,
					options: {'Due' : 'Due','Paid': 'Paid'}
                }
            },
            //Register to selectionChanged event to hanlde events                                     
            recordAdded: function(event, data){
                //after record insertion, reload the records
                $('#TaxDetailContainer').jtable('load');
            },
        });

        //Load product list from server
        $('#TaxDetailContainer').jtable('load');
    });
 </script>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>Tax Details</h5>
				</div>
				<div class="ibox-content overflow-h">
					<div class="col-lg-12">

						<div id="TaxDetailContainer"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>