package com.o2r.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GlobalConstant {

	public static final String orderUniqueSymbol = "$";

	public final ArrayList<String> orderList = new ArrayList<String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			add("ChannelOrderID");
			add("OrderRecievedDate");
			add("SkUCode");
			add("Partner");
			add("Customer Name");
			add("Payment Type");
			add("AWB No.");
			add("InvoiceID");
			add("subOrderID");
			add("PIreferenceNo");
			add("Logistic Partner");
			add("Order MRP");
			add("Order SP");
			add("Shipping Charges");
			add("Shipped Date");
			add("Delivery Date");
			add("Quantity");
			add("Net Rate");
			add("Customer Email");
			add("Customer Phone No");
			add("Customer City");
			add("Customer Address");
			add("Tax Category");
			add("Seller Notes");

		}
	};
	public static final ArrayList<String> channelMappingList = new ArrayList<String>() {
		/**
 *
 */
		private static final long serialVersionUID = 1L;

		{
			add("Amazon");
			add("Flipkart");
			add("Snapdeal");
			add("PayTM");
			add("Limeroad");
			add("Unicommerce");
			add("Jabong");
		}
	};
	public static final ArrayList<String> filesMappingList = new ArrayList<String>() {
		/**
 *
 */
		private static final long serialVersionUID = 1L;

		{
			add("payment");
			add("order");
		}
	};

	public final ArrayList<String> productHeaderList = new ArrayList<String>() {
		/**
 *
 */
		private static final long serialVersionUID = 1L;

		{
			add("ProductName");
			add("SkUCode");
			add("Category");
			add("ProductPrice");
			add("Quantity");
			add("Threshold Limit");
			add("ChanelSKU(Separated by ;)");

		}
	};

	public static final ArrayList<String> paymentHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("ChannelOrderId");
			add("InvoiceId");
			add("Recieved Amount");
			add("Fulfilment Type");
			add("Payment Date");
			add("Sales Channel");
			add("Seller SKU");
			add("Particulars");

		}
	};

	public static final ArrayList<String> snapdealpaymentHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("ChannelOrderId");
			add("Recieved Amount");
			add("Particular");
			add("Payment Date");
			add("Seller SKU");
			add("Sales Channel");

		}
	};
	public static final ArrayList<String> amazonPaymentHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("ChannelOrderId");
			add("Recieved Amount");
			add("Payment Detail");
			add("Payment Date");
			add("Seller SKU");
			add("Sales Channel");

		}
	};

	public static final ArrayList<String> limeroadPaymentHeaderList = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("ChannelOrderId");
			add("SKU");
			add("Invoice ID");
			add("Recieved Amount");
			add("Payment Date");
			add("Particular");
		}
	};

	public static final ArrayList<String> UnicommercePaymentHeaderList = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("ChannelOrderId");
			add("SKU");
			add("Invoice ID");
			add("Recieved Amount");
			add("Payment Date");
			add("Particular");
		}
	};

	public static final ArrayList<String> JabongPaymentHeaderList = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Payment Date");
			add("ChannelOrderId");
			add("Recieved Amount");
			add("Invoice ID");
			add("SKU");
			add("Particular");
		}
	};

	public static final ArrayList<String> flipkartOrderHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("Order Recieved Date");
			add("Secondary OrderID");
			add("Channel Order ID");
			add("SkUCode");
			add("Quantity");
			add("InvoiceID");
			add("Order SP");
			add("Shipping PinCode");
			add("Order Shipped Date");
			add("Sales Channel");
			add("Payment Type");
			add("AWB No");
			add("PIreferenceNo");
			add("Logistic Partner");
			add("Order MRP");
			add("Net Rate");
			add("Customer Name");
			add("Customer Address");
			add("Customer City");
			add("Customer Phone No");
			add("Customer Email");
			add("Seller Note");

		}
	};

	public static final ArrayList<String> amazonOrderHeaderList = new ArrayList<String>() {

		private static final long serialVersionUID = 1L;

		{
			add("Channel Order ID");
			add("Secondary OrderID");
			add("Order Recieved Date");
			add("Customer Email");
			add("Customer Name");
			add("Customer Phone No");
			add("SkUCode");
			add("Quantity");
			add("OrderSP Component A");
			add("OrderSP Component B");
			add("Customer Address 1");
			add("Customer Address 2");
			add("Customer Address 3");
			add("Customer City");
			add("Shipping PinCode");
			add("Payment Type");
			add("Logistic Partner");
			add("Sales Channel");
			add("InvoiceID");
			add("Order Shipped Date");
			add("Net Rate");
			add("Seller Note");
			add("AWB No");
			add("PIreferenceNo");
			add("Order MRP");
		}
	};

	public static final ArrayList<String> snapdealOrderHeaderList = new ArrayList<String>() {

		private static final long serialVersionUID = 1L;
		{
			add("Logistic Partner");
			add("PIreferenceNo");
			add("Channel Order ID");
			add("SkUCode");
			add("AWB No");
			add("Order Recieved Date");
			add("Customer Name");
			add("Customer City");
			add("Shipping PinCode");
			add("Order SP");
			add("Order MRP");
			add("InvoiceID");
			add("Customer Phone No");
			add("Customer Email");
			add("Payment Type");
			add("Order Shipped Date");
			add("Customer Address");
			add("Sales Channel");
			add("Quantity");
			add("Net Rate");
			add("Seller Note");
			add("Secondary OrderID");
		}
	};

	public static final ArrayList<String> PayTMOrderHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("Order Recieved Date");
			add("Secondary OrderID");
			add("Channel Order ID");
			add("SkUCode");
			add("Quantity");
			add("InvoiceID");
			add("OrderSP Component A");
			add("OrderSP Component B");
			add("Shipping PinCode");
			add("Order Shipped Date");
			add("Sales Channel");
			add("Payment Type");
			add("AWB No");
			add("PIreferenceNo");
			add("Logistic Partner");
			add("Order MRP");
			add("Net Rate");
			add("Customer_Fname");
			add("Customer_Lname");
			add("Customer Address");
			add("Customer City");
			add("Customer Phone No");
			add("Customer Email");
			add("Seller Note");

		}
	};

	public static final ArrayList<String> LimeroadOrderHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("Channel Order ID");
			add("SkUCode");
			add("Order Recieved Date");
			add("Secondary OrderID");
			add("Quantity");
			add("InvoiceID");
			add("Order SP");
			add("Shipping PinCode");
			add("Order Shipped Date");
			add("Sales Channel");
			add("Payment Type");
			add("AWB No");
			add("PIreferenceNo");
			add("Logistic Partner");
			add("Order MRP");
			add("Net Rate");
			add("Customer Name");
			add("Customer Address");
			add("Customer City");
			add("Customer Phone No");
			add("Customer Email");
			add("Seller Note");

		}
	};

	public static final ArrayList<String> UnicommerceOrderHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("Channel Order ID");
			add("SkUCode");
			add("Order Recieved Date");
			add("Secondary OrderID");
			add("Quantity");
			add("InvoiceID");
			add("Order SP");
			add("Shipping PinCode");
			add("Order Shipped Date");
			add("Sales Channel");
			add("O2R Channel");
			add("Payment Type");
			add("AWB No");
			add("PIreferenceNo");
			add("Logistic Partner");
			add("Order MRP");
			add("Net Rate");
			add("Customer Name");
			add("Customer Address A");
			add("Customer Address B");
			add("Customer City");
			add("Customer Phone No");
			add("Customer Email");
			add("Seller Note");

		}
	};

	public static final ArrayList<String> JabongOrderHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("Secondary OrderID");
			add("SkUCode");
			add("Order Recieved Date");
			add("Channel Order ID");
			add("Customer Email");
			add("Customer Name");
			add("Customer Address");
			add("Customer Phone No");
			add("Customer City");
			add("Shipping PinCode");
			add("Payment Type");
			add("Order SP");
			add("Order MRP");
			add("Logistic Partner");
			add("AWB No");
			add("Sales Channel");
			add("InvoiceID");
			add("Order Shipped Date");
			add("Quantity");
			add("PIreferenceNo");
			add("Net Rate");
			add("Seller Note");

		}
	};

	public final ArrayList<String> inventoryHeaderList = new ArrayList<String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			add("ProductName");
			add("SkUCode");
			add("CurrentQuantity");
			add("Quantity to Add");
			add("Quantity to Substract");

		}
	};

	public final ArrayList<String> expenseHeaderList = new ArrayList<String>() {
		/**
			 *
			 */
		private static final long serialVersionUID = 1L;

		{
			add("Name");
			add("Description");
			add("Expense Category");
			add("Expense Amount");
			add("Expenditure By");
			add("Paid To");

		}
	};

	public static final HashMap<String, String> preDefinedExpenseCategoryMap = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("Petty", "Petty");
			put("Assets", "Not included in Net Profit");
			put("Salaries", "Salaries");
			put("Overheads", "Overheads");
			put("Taxable Purchases", "Taxable Purchases");
			put("Tax/TDS", "Tax/TDS");
			put("Manual Charges", "Manual Charges");
		}
	};

	public final ArrayList<String> amazonpaymentvariablesList = new ArrayList<String>() {
		/**
			 *
			 */
		private static final long serialVersionUID = 1L;

		{
			add("Amazon Easy Ship Fees");
			add("Commission");
			add("Fixed closing fee");
			add("FBA Pick & Pack Fee");
			add("FBA Weight Handling Fee");
			add("Delivery Services Fee");
			add("Amazon Easy Ship Fees");
			add("Refund commission");
			add("Product Tax");
			add("Previous Reserve Amount Balance");
			add("Current Reserve Amount");
			add("Shipping");
			add("Shipping holdback");
			add("Shipping Chargeback");
			add("Shipping tax");
			add("Tax Withholding Reimbursement");

		}
	};
	
	public static final ArrayList<String> flipkartpaymentvariablesList = new ArrayList<String>() {
		/**
			 *
			 */
		private static final long serialVersionUID = 1L;

		{
			add("Total Marketplace Fee (Rs.)");
			add("Service Tax (Rs.)");
			add("Sb Cess Tax(Rs.)");
			add("KK Cess Tax(Rs.)");
			add("Commission Rate");
			add("Commission (Rs.)");
			add("Payment Fee/collection fee");
			add("Fee Discount (Rs.)");
			add("Cancellation Fee (Rs.)");
			add("Fixed Fee  (Rs.)");
			add("Admonetaisation Fee (Rs.)");
			add("Dead Weight (In Kgs)");
			add("Chargeable Wt. Slab (In Kgs)");
			add("Chargeable Weight Type");
			add("Shipping Fee (Rs.)");
			add("Reverse Shipping  Fee (Rs.)");
			add("Shipping Zone");
			add("Token Of Apology");
			add("Pick And Pack Fee");
			add("Storage Fee");
			add("Removal Fee");
			add("Service Cancellation Fee(Rs.)");
			add("Total Offer Amount");
			add("My Offer Share");
			add("Flipkart Offer Share");
			add("NDD Amount(next day delivery or same day)");
			add("Ndd Fee");
			add("SDD Amount");
			add("Sdd Fee");
			add("Sellable Regular Storage Fee");
			add("Unsellable Regular Storage Fee");
			add("Sellable Longterm 1 Storage Fee");
			add("Unsellable Longterm 1 Storage Fee");
			add("Sellable Longterm 2 Storage Fee");
			add("Unsellable Longterm 2 Storage Fee");
			add("Is Replacement");
			

		}
	};
	
	
	public static final ArrayList<String> paytmpaymentvariablesList = new ArrayList<String>() {
		/**
				 */
		private static final long serialVersionUID = 1L;

		{
			add("Marketplace Commission");
			add("Logistics Charges");
			add("PG Commission");
			add("Adjustment Amount");
			add("Adjustment Taxes");
			add("Net Adjustments");
			add("Service Tax");
			add("Payable Amount");
			add("Payout - Wallet");
			add("Payout - PG");
			add("Payout - COD");

		}
	};
	
	public static final ArrayList<String> snapdealpaymentvariablesList = new ArrayList<String>() {
		/**
				 */
		private static final long serialVersionUID = 1L;

		{
			add("Seller Funded Cashback");
			add("Benefit Passed on to Buyer");
			add("EMI");
			add("Fulfillment Fee");
			add("Fulfillment Fee Waiver");
			add("Processing Charges");
			add("Packaging Charges");
			add("Special Packaging Charges");
			add("Marketing Fee");
			add("Payment Collection Fee");
			add("Courier Fee");
			add("Fulfilment fees for Returns");
			add("Processing fees for Returns");
			add("Packaging fees for Returns");
			add("Special packaging fees for Returns");
			add("Payment collection fees for Returns");
			add("Logistics fees for Returns");
			add("Reverse logistics fees for Returns");
			add("Service Tax");
			add("Swachh Bharat Cess");
			add("Krishi Kalyan Cess");
			add("Commission");

		}
	};
	
	
	public static final ArrayList<String> limeroadpaymentvariablesList = new ArrayList<String>() {
		/**
				 */
		private static final long serialVersionUID = 1L;

		{
			add("Total Cart Discount");
			add("Cart Disc By Vendor (Final)");
			add("Cart Disc By Limeroad (final)");
			add("Vendor NSP");
			add("Net Selling Price");
			add("Taxable Amount");
			add("Tax 2 Rate");
			add("Tax 2 Amount");
			add("Shipping Charge paid by Customer");
			add("COD Charge paid by Customer");
			add("Entry Tax");
			add("LBT/Octroi");
			add("LR Margin % inclusive service tax");
			add("Transporter Cost Recovered from Vendor (Final)");
			add("Cod Cost Recovered from Vendor (Final)");
			add("LR Margin Amount after Discount borne by Limeroad (Final)");
			add("Logistics fees for Returns");
			add("Transporter Cost Recovered from Vendor on Return (Final)");
			add("Vendor Payment Due (after TDS addition) (Final)");
			add("Margin Reversed on Returns");
			

		}
	};
	
	public static final ArrayList<String> jabongpaymentvariablesList = new ArrayList<String>() {
		/**
				 */
		private static final long serialVersionUID = 1L;

		{
			add("Jabong Product Discount");
			add("Seller Product Discount");
			add("Taxable Amount");
			add("Tax 2 Amount");
			add("Tax%");
			add("Return Amount");
			add("TYPE OF TAX");
			add("Tax Amount");
			add("Final Commission %");
			add("Commission Amount");
		}
	};
	
	// Headers For Detailed Dashboard
	
	public static final ArrayList<String> grossHeader = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("#");
			add("Month");
			add("Gross");
			add("Return");
			add("Net");
			add("BadQtyCharge");
		}
	};
	public static final ArrayList<String> NR_PR_Header = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("#");
			add("Month");
			add("Gross");
			add("Return");
			add("Net");
			add("Additional");
		}
	};
	public static final ArrayList<String> SP_SaleQty_Header = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("#");
			add("Month");
			add("Gross");
			add("Return");
			add("Net");
		}
	};
	public static final ArrayList<String> Up_Out_Header = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("#");
			add("Channel");
			add("Gross");			
		}
	};
	public static final ArrayList<String> SKUHeader = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("#");
			add("SKU");
			add("Gross");
			add("Return");
			add("Net");
		}
	};
	public static final ArrayList<String> RegionsHeader = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("#");
			add("City");
			add("Gross");
			add("Return");
			add("Net");
		}
	};
	
	
	
	/* Null Area */
	public static final String nullException = "NullException !";
	public static final String nullValuesError = "Field Should Not Be Empty!";
	public static final String nullValuesErrorCode = "#00999";

	public static final String volShipping = "Volumetric Shipping";
	public static final String dwShipping = "Deadweight Shipping";

	// Alerts Message
	public static final String InventoryMsg = "Product Quantity is Under Limit !";
	public static final String OrderMsg = "Order Bucket Is Low !";
	public static final String CustomerMsg = "Customer Is Blacklisted with Contact No";

	/*
	 * log.error("searchExpense exception : " + GlobalConstant.nullException);
	 * model.put("errorMessage", GlobalConstant.nullValuesError);
	 * model.put("errorTime", new Date()); model.put("errorCode",
	 * GlobalConstant.nullValuesErrorCode); return new
	 * ModelAndView("globalErorPage", model);
	 */
	/* Null Area */

	/** Error Codes **/
	/*
	 * log.error("addOrderDA exception : " + ce.toString()); model.put("error",
	 * ce.getLocalMessage()); String errors = gson.toJson(model); return errors;
	 */

	/** Error Codes **/

	/** paymentUpload Area **/

	// Constants for Error messages
	public static final String addPaymentUploadError = "Error while upload the payment. Either try again with correct format or contact admin with the error details.";
	public static final String listPaymentUploadError = "Error while upload the payment. Either try again with correct format or contact admin with the error details.";
	public static final String getPaymentUploadError = "Error while retriving the payment. Either try again with correct payment ID or contact admin with the error details.";
	public static final String deletePaymentUploadError = "Error while deleting the payment. Either try again with correct seller ID or contact admin with the error details.";
	public static final String getManualPaymentError = "Error while retriving the payment. Either try again with correct seller ID or contact admin with the error details.";

	// Error codes
	public static final String addPaymentUploadErrorCode = "#00011";
	public static final String listPaymentUploadErrorCode = "#00012";
	public static final String getPaymentUploadErrorCode = "#00013";
	public static final String deletePaymentUploadErrorCode = "#00014";
	public static final String getManualPaymentErrorCode = "#00015";

	/** paymentUpload Area **/	
	
	public static int MonthNo(String month){
		switch (month) {
		case "January":
			return 1;
		case "February":
			return 2;
		case "March":
			return 3;
		case "April":
			return 4;
		case "May":
			return 5;
		case "June":
			return 6;
		case "July":
			return 7;
		case "August":
			return 8;
		case "September":
			return 9;
		case "October":
			return 10;
		case "November":
			return 11;
		case "December":
			return 12;
		default:
			return 0;
		}		
	}
	
	/** AdminModule Area **/

	// Constants for Error messages
	public static final String addEmployeeError = "Error while adding Employee. Either try again with correct data or contact admin with the error details.";
	public static final String listEmployeeError = "Error while getting Employees. Either try again with correct data or contact admin with the error details.";
	public static final String getEmployeeError = "Error while retriving the Employee. Either try again with correct Employee ID or contact admin with the error details.";
	public static final String deleteEmployeeError = "Error while deleting the Employee. Either try again with correct Employee ID or contact admin with the error details.";
	public static final String addQueryError = "Error while adding Query. Either try again with correct data or contact admin with the error details.";
	public static final String listQueriesError = "Error while getting queries. Either try again or contact admin with the error details.";

	// Error codes
	public static final String addEmployeeErrorCode = "#00051";
	public static final String listEmployeeErrorCode = "#00052";
	public static final String getEmployeeErrorCode = "#00053";
	public static final String deleteEmployeeErrorCode = "#00054";
	public static final String addQueryErrorCode = "#00055";
	public static final String listQueriesErrorCode = "#00056";

	/** AdminModule Area **/

	/** Seller Area **/

	// Constants for Error messages
	public static final String addSellerError = "Error while adding the seller. Either try again with correct data or contact admin with the error details.";
	public static final String listSellerError = "Error while retriving the sellers. Either try again or contact admin with the error details.";
	public static final String getSellerByIdError = "Error while retriving the seller by ID. Either try again with correct seller ID or contact admin with the error details.";
	public static final String getSellerByEmailError = "Error while retriving the seller by Email. Either try again with correct seller ID or contact admin with the error details.";
	public static final String deleteSellerError = "Error while deleting the seller. Either try again with correct seller ID or contact admin with the error details.";
	public static final String planUpgradeError = "Error while upgrading te seller. Either try again or contact admin with the error details.";
	public static final String updateProcessedOrdersCountError = "Error while upgrading te seller. Either try again or contact admin with the error details.";

	// Error codes
	public static final String addSellerErrorCode = "#00021";
	public static final String listSellerErrorCode = "#00022";
	public static final String getSellerByIdErrorCode = "#00023";
	public static final String getSellerByEmailErrorCode = "#00024";
	public static final String deleteSellerErrorCode = "#00025";
	public static final String planUpgradeErrorCode = "#00026";
	public static final String updateProcessedOrdersCountErrorCode = "#00027";

	/** Seller Area **/

	/** Order Area **/

	// Constants for Error messages
	public static final String addOrderError = "Error while adding the Order. Either try again with correct data or contact admin with the error details.";
	public static final String listOrderError = "Error while retriving the Order. Either try again or contact admin with the error details.";
	public static final String getOrderError = "Error while retriving the Order by ID. Either try again with correct ID or contact admin with the error details.";
	public static final String deleteOrderError = "Error while deleting the Order. Either try again with correct Order ID or contact admin with the error details.";
	public static final String addReturnOrderError = "Error while adding return order. Either try again with correct Data or contact admin with the error details.";
	public static final String deleteReturnInfoError = "Error while deleting the return Info. Either try again or contact admin with the error details.";
	public static final String findOrdersError = "Error while find orders. Either try with correct ID or contact admin with the error details.";
	public static final String findOrdersbyDateError = "Error while find orders by Date. Either try with correct Date or contact admin with the error details.";
	public static final String findOrdersbyReturnDateError = "Error while find orders by return Date. Either try with correct Date or contact admin with the error details.";
	public static final String findOrdersbyPaymentDateError = "Error while find orders by payment Date. Either try with correct Date or contact admin with the error details.";
	public static final String findOrdersbyCustomerDetailsError = "Error while find orders by Customer Details. Either try with correct ID or contact admin with the error details.";
	public static final String addOrderPaymentError = "Error while adding order payment. Either try with correct Data or contact admin with the error details.";
	public static final String addDebitNoteError = "Error while adding debit notes. Either try with correct notes or contact admin with the error details.";
	public static final String addPOPaymentError = "Error while adding payments. Either try with correct Dataor contact admin with the error details.";
	public static final String listOrdersError = "Error while retriving orders. Either try with correct Data or contact admin with the error details.";

	// Error codes
	public static final String addOrderErrorCode = "#00101";
	public static final String listOrderErrorCode = "#00102";
	public static final String getOrderErrorCode = "#00103";
	public static final String deleteOrderErrorCode = "#00104";
	public static final String addReturnOrderErrorCode = "#00105";
	public static final String deleteReturnInfoErrorCode = "#00106";
	public static final String findOrdersErrorcode = "#00107";
	public static final String findOrdersbyDateErrorCode = "#00108";
	public static final String findOrdersbyReturnDateErrorCode = "#00109";
	public static final String findOrdersbyPaymentDateErrorCode = "#00110";
	public static final String findOrdersbyCustomerDetailsErrorCode = "#00111";
	public static final String addOrderPaymentErrorCode = "#00112";
	public static final String addDebitNoteErrorCode = "#00113";
	public static final String addPOPaymentErrorCode = "#00114";
	public static final String listOrdersErrorCode = "#00115";

	/** Order Area **/

	/** Product Area **/

	// Constants for Error messages
	public static final String addProductError = "Error while Adding the Product. Either try again with correct data or contact admin with the error details.";
	public static final String listProductsError = "Error while Getting the products. Either try again with correct data or contact admin with the error details.";
	public static final String getProductError = "Error while retriving the product. Either try again with correct ID or contact admin with the error details.";
	public static final String deleteProductError = "Error while deleting the product. Either try again with correct ID or contact admin with the error details.";
	public static final String updateInventoryError = "Error while updating the Inventory. Either try again with correct Data or contact admin with the error details.";
	public static final String getProductwithCreatedDateError = "Error while retriving the Product. Either try again with correct date or contact admin with the error details.";

	// Error codes
	public static final String addProductErrorCode = "#00031";
	public static final String listProductsErrorCode = "#00032";
	public static final String getProductErrorCode = "#00033";
	public static final String deleteProductErrorCode = "#00034";
	public static final String updateInventoryErrorCode = "#00035";
	public static final String getProductwithCreatedDateErrorCode = "#00036";

	/** Product Area **/

	/** Expense Area **/

	// Constants for Error messages
	public static final String addExpenseError = "Error while Adding the Expense. Either try again with correct data or contact admin with the error details.";
	public static final String addExpenseCategoryError = "Error while Adding the Expense Category. Either try again with correct data or contact admin with the error details.";
	public static final String addStateDeliveryTimeError = "Error while Adding the State Delivery Time. Either try again with correct data or contact admin with the error details.";
	public static final String listExpensesError = "Error while Getting the Expenses. Either try again with correct data or contact admin with the error details.";
	public static final String listExpensesCategoriesError = "Error while Getting the Expense Catagories. Either try again with correct data or contact admin with the error details.";
	public static final String getExpenseError = "Error while retriving the Expense. Either try again with correct ID or contact admin with the error details.";
	public static final String getExpenseCategoryError = "Error while retriving the Expense Category. Either try again with correct ID or contact admin with the error details.";
	public static final String deleteExpenseError = "Error while deleting the Expense. Either try again with correct ID or contact admin with the error details.";
	public static final String deleteExpenseCategoryError = "Error while deleting the Expense Category. Either try again with correct ID or contact admin with the error details.";
	public static final String getMonthlyAmountError = "Error while retriving the Monthly Amount. Either try again with correct data or contact admin with the error details.";
	public static final String getExpenseByCategoryError = "Error while retriving Expenses By Category. Either try again with correct Data or contact admin with the error details.";
	public static final String getExpenseByDateError = "Error while retriving Expenses By Date. Either try again with correct Data or contact admin with the error details.";

	// Error codes
	public static final String addExpenseErrorCode = "#00080";
	public static final String addExpenseCategoryErrorCode = "#00081";
	public static final String listExpensesErrorCode = "#00082";
	public static final String listExpensesCategoriesErrorCode = "#00083";
	public static final String getExpenseErrorCode = "#00084";
	public static final String getExpenseCategoryErrorCode = "#00085";
	public static final String deleteExpenseErrorCode = "#00086";
	public static final String deleteExpenseCategoryErrorCode = "#00087";
	public static final String getMonthlyAmountErrorCode = "#00088";
	public static final String getExpenseByCategoryErrorCode = "#00089";
	public static final String getExpenseByDateErrorCode = "#00090";

	public static final String addStateDeliveryTimeErrorCode = "#00011";

	/** Expense Area **/

	/** Partner Area **/

	// Constants for Error messages
	public static final String addPartnerError = "Error while Adding the Partner. Either try again with correct data or contact admin with the error details.";
	public static final String listPartnerError = "Error while Getting the partner. Either try again with correct data or contact admin with the error details.";
	public static final String getPartnerError = "Error while retriving the partner. Either try again with correct ID or contact admin with the error details.";
	public static final String deletePartnerError = "Error while deleting the partner. Either try again with correct ID or contact admin with the error details.";

	// Error codes
	public static final String addPartnerErrorCode = "#00041";
	public static final String listPartnerErrorCode = "#00042";
	public static final String getPartnerErrorCode = "#00043";
	public static final String deletePartnerErrorCode = "#00044";

	/** Partner Area **/

	/** Event Area **/

	// Constants for Error messages
	public static String addEventError = "Error while Adding the Event. Either try again with correct data or contact admin with the error details.";
	public static final String listEventError = "Error while Getting Events. Either try again with correct data or contact admin with the error details.";
	public static final String getEventError = "Error while retriving Events. Either try again with correct (ID/Name) or contact admin with the error details.";

	// Error codes
	public static final String addEventErrorCode = "#00111";
	public static final String listEventErrorCode = "#00112";
	public static final String getEventErrorCode = "#00113";

	/** Event Area **/

	/** Plan Area **/

	// Constants for Error messages
	public static final String addPlanError = "Error while upload the plan. Either try again with correct data or contact admin with the error details.";
	public static final String listPlansError = "Error while getting the plans. Either try again with correct data or contact admin with the error details.";
	public static final String getPlanError = "Error while retriving the plan. Either try again with correct plan ID or contact admin with the error details.";
	public static final String deletePlanError = "Error while deleting the plan. Either try again with correct plan or contact admin with the error details.";

	// Error codes
	public static final String addPlanErrorCode = "#00046";
	public static final String listPlansErrorCode = "#00047";
	public static final String getPlanErrorCode = "#00048";
	public static final String deletePlanErrorCode = "#00049";

	/** Plan Area **/

	/** Report Area **/

	// Constants for Error messages
	public static final String getPartnerTSOdetailsError = "Error while getting Partner TSO. Either try again with correct data or contact admin with the error details.";
	public static final String getAllPartnerTSOdetailsError = "Error while getting All Paartner TSO. Either try again with correct data or contact admin with the error details.";

	// Error codes
	public static final String getPartnerTSOdetailsErrorCode = "#00091";
	public static final String getAllPartnerTSOdetailsErrorCode = "#00092";

	/** Report Area **/
	public static final String addReportError = "Error while adding the report. Either try again with correct data or contact admin with the error details.";
	public static final String listReportError = "Error while retriving the reports. Either try again or contact admin with the error details.";

	public static final String addReportErrorCode = "#00201";
	public static final String listReportsErrorCode = "#00202";

	/** ManualCharges Area **/

	// Constants for Error messages
	public static final String addManualChargesError = "Error while Adding the Product. Either try again with correct data or contact admin with the error details.";
	public static final String listManualChargesError = "Error while Getting the products. Either try again with correct data or contact admin with the error details.";
	public static final String getManualChargesError = "Error while retriving the product. Either try again with correct ID or contact admin with the error details.";
	public static final String deleteManualChargesError = "Error while deleting the product. Either try again with correct ID or contact admin with the error details.";
	public static final String getMCforPaymentIDError = "Error while retriving the Product. Either try again with correct date or contact admin with the error details.";

	// Error codes
	public static final String addManualChargesErrorCode = "#00016";
	public static final String listManualChargesErrorCode = "#00017";
	public static final String getManualChargesErrorCode = "#00018";
	public static final String deleteManualChargesErrorCode = "#00019";
	public static final String getMCforPaymentIDErrorCode = "#00020";

	/** ManualCharges Area **/

	// Constants for Error messages
	public static final String addCategoryError = "Error while saving the new category. Either try again with correct values or contact admin with the error details.";
	public static final String listCategoryError = "Error while retrieving Product Category list. Either try again or contact admin with the error details.";
	public static final String listInventoryGroupError = "Error while retrieving Inventory Groups. Either try again or contact admin with the error details.";
	public static final String getSKUCountError = "Error while retrieving SKU. Either try again or contact admin with the error details.";
	public static final String getCategorywithNameError = "Error while retrieving category entered by user. Either try again or contact admin with the error details.";
	public static final String deleteCategoryError = "Error while deleting category. Either try again or contact admin with the error details.";

	public static final String addTaxDetailError = "Error while saving the tax details. Either try again with correct values or contact admin with the error details.";
	public static final String addTaxDetailErrorCode = "#00071";

	public static final String addTaxPaymentDetailError = "Error while payment of  the tax details. Either try again with correct values or contact admin with the error details.";
	public static final String addTaxPaymentDetailErrorCode = "#00072";

	public static final String addTDSPaymentDetailError = "Error while payment of  the TDS details. Either try again with correct values or contact admin with the error details.";
	public static final String addTDSPaymentDetailErrorCode = "#00073";

	public static final String addTaxCategoryError = "Error while adding Tax Category. Either try again with correct values or contact admin with the error details.";
	public static final String addTaxCategoryErrorCode = "#00074";

	public static final String getTaxDetailListError = "Error while getting Tax Details. Either try again with correct values or contact admin with the error details.";
	public static final String getTaxDetailListErrorCode = "#00077";
	public static final String getTaxDetailListErrorCode2 = "#00076";

	public static final String getTaxCatListError = "Error while getting Tax Category. Either try again with correct values or contact admin with the error details.";
	public static final String getTaxCatListErrorCode = "#00075";

	public static final String getTaxCategoryError = "Error while getting Tax Category. Either try again  or contact admin with the error details.";
	public static final String getTaxCategoryErrorCode = "#00078";

	public static final String deleteTaxCategoryError = "Error while deleting Tax Category. Either try again  or contact admin with the error details.";
	public static final String deleteTaxCategoryErrorCode = "#00079";

	public static final String addChannelUploadMappingError = "Error while adding Channel Mapping of  the seller. Either try again with correct values or contact admin with the error details.";
	public static final String addChannelUploadMappingErrorCode = "#00212";

	public static final String getChannelUploadMappingError = "Error while get Channel Mapping of  the seller. Either try again with correct values or contact admin with the error details.";
	public static final String getChannelUploadMappingErrorCode = "#00213";

	// Error Codes
	public static final String addCategoryErrorCode = "#00061";
	public static final String listCategoryErrorCode = "#00062";
	public static final String listInventoryGroupErrorCode = "#00063";
	public static final String getSKUCountErrorCode = "#000564";
	public static final String getCategorywithNameErrorCode = "#00065";
	public static final String deleteCategoryErrorCode = "#00066";

	// NRnReturn Configuration Constants
	public static final String fixedCommissionPercent = "fixedCommissionPercent";

	public static final String percentSPPCCValue = "pccpercentSPValue";
	public static final String percentSPPCCHigher = "pccpercentSPHigher";
	public static final String fixedAmtPCC = "pccfixedAmt";
	public static final String rangePCC = "pccrange";
	public static final String valuePCC = "pccvalue";

	// Return Configuration Constansts
	public static final String SellerFaultString = "sellerFault";
	public static final String BuyerReturnString = "buyerReturn";

	public static final String fixedString = "fixed";
	public static final String variableString = "variable";

	public static final String ReturnChargesSellerFaultVariablePercentSP = "retCharSFPercentSP";
	public static final String ReturnChargesSellerFaultFixedAmount = "retCharSFFixedAmt";
	public static final String ReturnChargesSellerFaultVariableFixedAmt = "retCharSFVarFixedAmt";
	public static final String ReturnChargesSellerFaultVariablePercentPCC = "retCharSFPercentPCC";

	public static final String ReturnChargesBuyerReturnFixedAmount = "retCharBRFixedAmt";
	public static final String ReturnChargesBuyerReturnVariableFixedAmt = "retCharBRVarFixedAmt";
	public static final String ReturnChargesBuyerReturnVariablePercentSP = "retCharBRPercentSP";

	// RTO Charges Configuration
	public static final String RTOChargesSellerFaultVariablePercentSP = "RTOCharSFPercentSP";
	public static final String RTOChargesSellerFaultFixedAmount = "RTOCharSFFixedAmt";
	public static final String RTOChargesSellerFaultVariableFixedAmt = "RTOCharSFVarFixedAmt";
	public static final String RTOChargesSellerFaultVariablePercentPCC = "RTOCharSFPercentPCC";

	// RTO Chages Buyer Return Configuration Strings
	public static final String RTOChargesBuyerReturnFixedAmount = "RTOCharBRFixedAmt";
	public static final String RTOChargesBuyerReturnVariableFixedAmt = "RTOCharBRVarFixedAmt";
	public static final String RTOChargesBuyerReturnVariablePercentSP = "RTOCharBRPercentSP";

	// Cancellation Charges Configuration

	public static final String SFCancellationAfterRTDString = "afterRTD";
	public static final String SFCancellationBeforeRTDString = "beforeRTD";

	public static final String CancellationChargesSellerFaultVariablePercentSP = "canCharSFPercentSP";
	public static final String CancellationChargesSellerFaultFixedAmount = "canCharSFFixedAmt";
	public static final String CancellationChargesSellerFaultVariableFixedAmt = "canCharSFVarFixedAmt";
	public static final String CancellationChargesSellerFaultVariablePercentPCC = "canCharSFPercentPCC";

	public static final String CancellationChargesSellerFaultBRTDVariablePercentSP = "canCharSFBFRTDPercentSP";
	public static final String CancellationChargesSellerFaultBRTDFixedAmount = "canCharSFBFRTDFixedAmt";
	public static final String CancellationChargesSellerFaultBRTDVariableFixedAmt = "canCharSFBFRTDVarFixedAmt";
	public static final String CancellationChargesSellerFaultBRTDVariablePercentPCC = "canCharSFBFRTDPercentPCC";

	// Cancellation Chager Buyer Return Configuration Strings
	public static final String CancellationChargesBuyerReturnFixedAmount = "canCharBRFixedAmt";
	public static final String CancellationChargesBuyerReturnVariableFixedAmt = "canCharBRVarFixedAmt";
	public static final String CancellationChargesBuyerReturnVariablePercentSP = "canCharBRPercentSP";

	// Replacement Charges Configuration
	public static final String ReplacementChargesSellerFaultVariablePercentSP = "repCharSFPercentSP";
	public static final String ReplacementChargesSellerFaultFixedAmount = "repCharSFFixedAmt";
	public static final String ReplacementChargesSellerFaultVariableFixedAmt = "repCharSFVarFixedAmt";
	public static final String ReplacementChargesSellerFaultVariablePercentPCC = "repCharSFPercentPCC";

	// Replacement Chages Buyer Return Configuration Strings
	public static final String ReplacementChargesBuyerReturnFixedAmount = "repCharBRFixedAmt";
	public static final String ReplacementChargesBuyerReturnVariableFixedAmt = "repCharBRVarFixedAmt";
	public static final String ReplacementChargesBuyerReturnVariablePercentSP = "repCharBRPercentSP";

	// Partial Delivery Charges Configuration
	public static final String PartialDelChargesSellerFaultVariablePercentSP = "PDCharSFPercentSP";
	public static final String PartialDelChargesSellerFaultFixedAmount = "PDCharSFFixedAmt";
	public static final String PartialDelChargesSellerFaultVariableFixedAmt = "PDCharSFVarFixedAmt";
	public static final String PartialDelChargesSellerFaultVariablePercentPCC = "PDCharSFPercentPCC";

	// PartialDelivery Chager Buyer Return Configuration Strings
	public static final String PartialDelChargesBuyerReturnFixedAmount = "PDCharBRFixedAmt";
	public static final String PartialDelChargesBuyerReturnVariableFixedAmt = "PDCharBRVarFixedAmt";
	public static final String PartialDelChargesBuyerReturnVariablePercentSP = "PDCharBRPercentSP";

	// Reverse Shipping Fee Configuration
	public static final String ReverseShippingFeePercentShipFee = "revShipFeePCC";
	public static final String ReverseShippingFeeFlatAmt = "revShipFeeFlatAmt";
	public static final String ReverseShippingFeePercentMarketFee = "revShipFeePCCMF";
	public static final String ReverseShippingFeeFixedAmt = "revShipFeeFF";
	public static final String ReverseShippingFeeDeadWeightAmt = "revShipFeeDWAmt";
	public static final String ReverseShippingFeeDeadWeightPerWeight = "revShipFeeDWPW";
	public static final String ReverseShippingFeeDeadWeightMinWeight = "revShipFeeDWMW";
	public static final String ReverseShippingFeeVolumeWeightAmt = "revShipFeeVWAmt";
	public static final String ReverseShippingFeeVolumeWeightPerWeight = "revShipFeeVWPW";
	public static final String ReverseShippingFeeVolumeWeightMinWeight = "revShipFeeVWMW";

	// Product Config Prefix
	public static final String TaxSPPrefix = "taxSp-";
	public static final String TaxPOPrefix = "taxPo-";
	public static final String CommPOPrefix = "comm-";
	public static final String fixedtaxSPPercent = "fixedTaxSpPercent";
	public static final String fixedtaxPOPercent = "fixedTaxPoPercent";

	public static final String TaxCategoryPrefix = "-taxCat-";

	// Channel Name
	public static final String PCMYNTRA = "myntra";
	public static final String PCAMAZON = "amazon";
	public static final String PCJABONG = "jabong";
	public static final String PCFLIPKART = "flipkart";
	public static final String PCPAYTM = "paytm";

	/*
	 * public static final String
	 * fixedCommissionPercent="fixedCommissionPercent"; public static final
	 * String fixedfeelt250="fixedfeelt250"; public static final String
	 * fixedfeelt500="fixedfeelt500"; public static final String
	 * fixedfeelt500Big="fixedfeelt500Big";
	 */

	/**
	 * Map of header names required for reports
	 * 
	 */
	public static final Map<String, String> headerMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			// For Partner Report
			put("getInvoiceID", "Invoice Id");
			put("getInvoiceId", "Invoice Id");
			put("getOrderId", "Order ID/PO No");
			put("getChannelOrderID", "Order ID/PO No");
			put("getPartner", "Partner");
			put("getPcName", "Partner");
			put("getOrderDate", "Order Received Date");
			put("getShippedDate", "Order Shipped Date");
			put("getPaymentDueDate", "Expected Date of Payment");
			put("getDateofPayment", "Actual Date of Payment");
			put("getReturnDate", "Date of Return/RTO");
			put("getProductCategory", "Product Category");
			put("getGrossSaleQuantity", "Gross Sale Qty");
			put("getReturnQuantity", "Return/RTO Qty");
			put("getNetSaleQuantity", "Net Sale Qty");
			put("getOrderSP", "Gross SP");
			put("getNetRate", "Net N/R");
			put("getGrossNetRate", "Net N/R");
			put("getNetReturnCharges", "Net Return Charges");
			put("getNetPaymentResult", "Net Payment Result");
			put("getPaymentDifference", "Net Payment Difference");
			put("getGrossPartnerCommission", "Gross Commission (Selling Fee)");
			put("getPccAmount", "PCC");
			put("getFixedfee", "Fixed Fee");
			put("getShippingCharges", "Shipping Charges");
			put("getServiceTax", "Service Tax");
			put("getTaxSP", "Tax (SP)");
			put("getTaxPOPrice", "Tax (PO Price)");
			put("getGrossCommission", "Gross Commission to be Paid");
			put("getGrossCommissionQty", "Gross Commission to be Paid");
			put("getReturnCommision", "Return Commission");
			put("getAdditionalReturnCharges", "Additional Return Charges");
			put("getNetPartnerCommissionPaid", "Net Partner Commisssion Paid");
			put("getTdsToBeDeducted10", "TDS to be deducted @ 10%");
			put("getTdsToBeDeducted2", "TDS to be deducted @ 2%");
			put("getNetTaxToBePaid", "Net Tax to be Paid");
			put("getNetEossValue", "Net EOSS Value");
			put("getNetPr", "Net P/R");
			put("getFinalStatus", "Final  Status");
			put("getReturnId", "Return Id/ GP ID");
			put("getReturnChargesDesciption", "Return Charges Description");
			put("getTdsToBeDeposited", "TDS to be deposited");

			// For Consolidated Report
			put("orderId", "OrderId");
			put("Partner", "Channel");
			put("subOrderId", "Secondary OrderId");
			put("returnId", "ReturnId");
			put("recievedDate", "Order RecievedDate");
			put("shippedDate", "Order shipped Date");
			put("deliveryDate", "Expected Delivery Date");
			put("returnDate", "Return Received Date");
			put("dateofPayment", "Expected date of Payment");
			put("payDate", "Actual payment Date");
			put("invoiceId", "InvoiceId");
			put("PIreferenceNo", "PIreferenceNo");
			put("productCategory", "ProductCategory");
			put("sku", "Vendor  SKU");
			put("quantity", "Qty");
			put("returnQuantity", "Return Qty");
			put("netSaleQuantity", "Net Sale Qty");
			put("totalMRP", "MRP");
			put("grossSp", "Gross Taxable Sale");
			put("returnSp", "Return Taxable Sale");
			put("netSp", "Net Taxable Sale");
			put("netRate", "netRate");
			put("grossNR", "Gross Actual Sale");
			put("returnChargesReversed", "Reversed Actual Sale");
			put("addReturnCharges", "Add. Return Charges");
			put("totalReturnCharges", "Total Return Actual Sale");
			put("netNR", "Net Actual Sale");
			put("netPayment", "Net Payment Result");
			put("payDiff", "Net Payment Diff.");
			put("grossPR", "Gross Tax-free Sale");
			put("returnPR", "Return Tax free Sale");
			put("netPR", "Net Tax Free Sale");
			put("taxCategory", "Tax Category");
			put("grossTax", "Gross Tax Dues");
			put("returnTax", "Reversed Tax Dues");
			put("netTax", "Net Tax Dues");
			put("grossTDS", "Gross TDS Dues");
			put("returnTDS", "Reversed TDS Dues");
			put("netTDS", "Net TDS Dues");
			put("grossCommission", "Gross Commission Paid");
			put("returnCommission", "Reversed Commission");
			put("netCommission", "Net Commission Paid");
			put("netSellingFee", "Net Selling Fee");
			put("netPCC", "Net PCC Paid");
			put("netFixedFee", "Net Fixed Fee Paid");
			put("netShippingCharges", "Net Shipping Charges Paid");
			put("serviceTax", "Net Service Tax Paid");
			put("eossDiscount", "Gross EOSS Disc");
			put("totalReturnEossDiscount", "Reversed ROSS Disc");
			put("netReturnEossDiscount", "Net EOSS Disc");
			put("grossCostProduct", "Gross Product Cost");
			put("returnCostProduct", "Return Product Cost");
			put("netCostProduct", "Net Product Cost");
			put("grossProfit", "Gross Margin");
			put("paymentType", "Payment Type");
			put("customerName", "Customer Name");
			put("customerCity", "Customer City");
			put("logicalPartner", "Logisitic Partner");
			put("awbNo", "AWB no");
			put("returnType", "Return Type");
			put("faultType", "Fault Type");
			put("returnStage", "Return Stage");
			put("shippingZone", "Shipping Zone");
			put("returnReason", "Return Reason");
			put("sellerNote", "Seller Notes");
			put("status", "Last Updated Status");
			put("finalStatus", "Action");

			// For Channel Report
			put("getCategory", "Product Category");
			put("getProductSku", "Product SKU");
			put("getGrossQty", "Gross Qty");
			put("getGrossNrAmount", "Gross N/R Amount");
			put("getGrossSpAmount", "Gross SP Amount");
			put("getSaleRetQty", "Sale Return Qty");
			put("getSaleRetNrAmount", "Sale Return N/R Amount");
			put("getRetAmountToBeReversed", "Return Charges to be reversed");
			put("getSaleRetSpAmount", "Sale Return SP Amount");
			put("getNetQty", "Net Sale Qty");
			put("getNetNrAmount", "Net Sale N/R Amount");
			put("getNetSpAmount", "Net Sale SP Amount");
			put("getNetPureSaleQty", "Net Pure Sale Qty");
			put("getNetPureSaleNrAmount", "Net Pure Sale N/R Amount");
			put("getNetPureSaleSpAmount", "Net Pure Sale AP Amount");
			put("getSaleRetVsGrossSale", "Sale Return vs Gross Sale");
			put("getTaxCategory", "Tax Category");
			put("getNetTaxLiability", "Net Tax Liability");
			put("getNetAr", "Net A/R");
			put("getNetToBeReceived", "Net to be Received");
			put("getProductCost", "Gross Product Cost");
			put("getReturnProductCost", "Return Product Cost");
			put("getNetProductCost", "Net Product Cost");
			put("getPr", "P/R");
			put("getNetReturnCharges", "Return Charges");
			put("getGrossProfit", "Gross Profit");
			put("getSaleGrossProfit", "Sale Gross Profit");
			put("getReturnGrossProfit", "Return Gross Profit");
			put("getGpVsProductCost", "%GP vs Product Cost");
			put("getNetEOSSValue", "Net EOSS Value");

			// For Debtors Report
			put("getAwb", "AWB");
			put("getSubOrderId", "Sub Order Id");
			put("getPiRefNumber", "PI Reference No");
			put("getLogisticPartner", "Logistic Partner");
			put("getDeliveryDate", "Expected Date of Delivery");
			put("getCustomerName", "Customer Name");
			put("getCustomerEmail", "Customer Email");
			put("getCustomerPhone", "Customer Phone");
			put("getCustomerZip", "Customer Zip Code");
			put("getCustomerCity", "Customer City");
			put("getReturnSP", "Return SP");
			put("getNetSP", "Net SP");
			put("getGrossReturnChargesReversed",
					"Gross Return Charges Reversed");
			put("getTotalReturnCharges", "Total Return Charges");
			put("getGrossTds", "Total Gross TDS");
			put("getReturnTds", "Total Return TDS");
			put("getNetActualSale", "Net Actual Sale");
			put("getPaymentId", "Payment Id");
			put("getPaymentType", "Payment Type");

			// For Total Payments Received
			put("getPaymentDate", "Expected Date of Payment");
			put("getReceivedDate", "Order Received Date");

			// For Expense Report
			put("getExpenseDate", "Expense Date");
			put("getExpenseName", "Expense Name");
			put("getExpenseCatName", "Expense Category");
			put("getAmount", "Amount");

			// For Stock Report
			put("getMonthStr", "Month");
			put("getOpenStock", "Opening Stock");
			put("getOpenStockValuation", "Opening Stock Valuation");
			put("getCloseStock", "Closing Stock");
			put("getCloseStockValuation", "Closing Stock Valuation");
		}
	};

	/**
	 * Map of Report Names for display
	 * 
	 */
	public static final Map<String, String> reportNameMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("totalShippedOrders", "Total Shipped Orders");
			put("paymentDifferenceOrders", "Payment Difference Orders");
			put("returnRTOReasonAnalysis", "Return/RTO reason Analysis");
			put("saleReturnOrders", "Sale return Orders");
			put("actionableOrders", "Actionable Orders");
			put("disputedOrders", "Disputed Orders");
			put("returnRTOLimitCrossed", "Return/RTO limit crossed Orders");
			put("settledOrders", "Settled Orders");
			put("channelSaleReport", "Channel Wise Sales Order");
			put("categoryWiseSaleReport", "Category Wise Sales Order");
			put("paymentsReceievedReport", "Total Payments Receieved");
			put("orderwiseGPReport", "Orderwise GP Report");
			put("productSaleReport", "Product Shipped Orders");
			put("productSaleReport", "Payment Difference Orders");
			put("productSaleReport", "Return/RTO limit crossed Orders");
			put("productSaleReport", "Settled Orders");
			put("partnerCommissionReport", "Partner Commission Paid Report");
			put("partnerBusinessReport", "Partner Business Report");
			put("debtorsReport", "Debtors Report");
			put("expenditureReport", "Expenditure Report");
			put("inventoryUploadHistory", "Inventory Upload History");
			put("inventoryMovementDetails", "Inventory Movement Details");
			put("inventoryThresholdReport", "Inventory Threshold Report");
			put("closingStockReport", "Closing stock report");
			put("customerDatabase", "Customer Database");
			put("bestSellingSKUreport", "Best Selling SKU");
			put("bestSellingRegionReport", "Best Selling Region Report");
			put("netSaleReport", "Net Sale Report");
			put("productSaleReport", "Product Sale Report");
			put("channelSaleReport", "Channel Sale Report");
			put("netDebtors", "Net Debtors");
			put("grossProfitabilityReport", "Gross Profitability Report");
			put("netProfitabilityReport", "Net Profitability Report");
			put("netDebtors", "Net Debtors");
			put("performanceAnalysis", "Performance Analysis");
			put("totalTurnover", "Total Turnover");
			put("totalPaymentsReceived", "Total Payments Received");
		}
	};

	/**
	 * Map of Month Names for display
	 * 
	 */
	public static final Map<String, String> monthNameMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("01", "January");
			put("02", "February");
			put("03", "March");
			put("04", "April");
			put("05", "May");
			put("06", "June");
			put("07", "July");
			put("08", "August");
			put("09", "September");
			put("10", "October");
			put("11", "November");
			put("12", "December");
		}
	};

	/**
	 * Map of WorkSheet Names for Upload Report
	 * 
	 */
	public static final Map<String, String> worksheetNameMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("ordersummary", "MP_Order_Upload");
			put("orderPoSummary", "PO_Order_Upload");
			put("gatepassSummary", "PO_Gatepass_Uplooad");
			put("paymentSummary", "MP_Payment_Upload");
			put("returnSummary", "MP_Return_Upload");
			put("productSummary", "Create_Parent_Product");
			put("editProductSummary", "Product_Edit");
			put("productConfigSummary", "PO_Product_Config");
			put("inventorySummary", "InventoryReport");
			put("poPaymentSummary", "Po_Payment_Upload");
			put("expenseSummary", "Expense_Upload");
			put("EventSKUSummary", "Event_SKU_Upload");
			put("product_Tax_Mapping", "product_Tax_Mapping");
			put("Dlink_SKU_Mapping", "Dlink_SKU_Mapping");
			put("vendorSKUMapping", "MP_Vendor_SKU_Mapping");
			put("Unicommerce_Order", "Unicommerce_Order_Upload");
			put("Flipkart_Payment", "Flipkart_Payment");
			put("Flipkart_Order", "Flipkart_Order_Upload");
			put("PayTM_Payment", "PayTM_Payment");
			put("PayTM_Order", "PayTM_Order_Upload");
			put("Amazon_Order", "Amazon_Order_Upload");
			put("Amazon_Payment", "Amazon_Payment");
			put("Limeroad_Payment", "Limeroad_Payment");
			put("Limeroad_Order", "Limeroad_Order_Upload");
			put("Snapdeal_Payment", "Snapdeal_Payment");
			put("Snapdeal_Order", "Snapdeal_Order_Upload");
			put("Jabong_Payment", "Jabong_Payment_Upload");
			put("Jabong_Order", "Jabong_Order_Upload");
			put("CreateInventoryGroups", "Create_Inventory_Group");
			put("CreateProCat", "Create_Product_Category");
			put("prodCat_Comm_Mapping", "ProdCat_Comm_Mapping");
		}
	};

	public static final List<Long> times = Arrays.asList(
			TimeUnit.DAYS.toMillis(365), TimeUnit.DAYS.toMillis(30),
			TimeUnit.DAYS.toMillis(1), TimeUnit.HOURS.toMillis(1),
			TimeUnit.MINUTES.toMillis(1), TimeUnit.SECONDS.toMillis(1));
	public static final List<String> timesString = Arrays.asList("year",
			"month", "day", "hour", "minute", "second");

	public static String toDuration(long duration) {

		StringBuffer res = new StringBuffer();
		for (int i = 0; i < times.size(); i++) {
			Long current = times.get(i);
			long temp = duration / current;
			if (temp > 0) {
				res.append(temp).append(" ").append(timesString.get(i))
						.append(temp > 1 ? "s" : "").append(" ago");
				break;
			}
		}
		if ("".equals(res.toString()))
			return "0 second ago";
		else
			return res.toString();
	}
}
