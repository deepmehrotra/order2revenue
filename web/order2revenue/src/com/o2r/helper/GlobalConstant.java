package com.o2r.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GlobalConstant {

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

	public final ArrayList<String> paymentHeaderList = new ArrayList<String>() {
		/**
	 *
	 */
		private static final long serialVersionUID = 1L;

		{
			add("ChannelOrderId");
			add("InvoiceId");
			add("SkUCode");
			add("Recieved Amount");
			add("Negative Charges");
			add("Payment Date");

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
	

	public static final HashMap<String, String> preDefinedExpenseCategoryMap = new HashMap<String, String>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("Petty","Petty");
		put("Assets","Not included in Net Profit");
		put("Salaries","Salaries");
		put("Overheads","Overheads");
		put("Taxable Purchases","Taxable Purchases");
	    put("Tax/TDS","Tax/TDS");
	    put("Manual Charges","Manual Charges");
	}};

	/* Null Area */
	public static final String nullException = "NullException !";
	public static final String nullValuesError = "Field Should Not Be Empty!";
	public static final String nullValuesErrorCode = "#00999";

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

	// Error Codes
	public static final String addCategoryErrorCode = "#00061";
	public static final String listCategoryErrorCode = "#00062";
	public static final String listInventoryGroupErrorCode = "#00063";
	public static final String getSKUCountErrorCode = "#000564";
	public static final String getCategorywithNameErrorCode = "#00065";
	public static final String deleteCategoryErrorCode = "#00066";

	// NRnReturn Configuration Constants
	public static final String fixedCommissionPercent = "fixedCommissionPercent";
	public static final String fixedfeelt250 = "fixedfeelt250";
	public static final String fixedfeelt500 = "fixedfeelt500";
	public static final String fixedfeelt500Big = "fixedfeelt500Big";
	public static final String fixedfeegt250lt500 = "fixedfeegt250lt500";
	public static final String fixedfeegt500 = "fixedfeegt500";
	public static final String fixedfeegt500Big = "fixedfeegt500Big";
	public static final String fixedfeegt500lt1000 = "fixedfeegt500lt1000";
	public static final String fixedfeegt1000lt10000 = "fixedfeegt1000lt10000";
	public static final String fixedfeegt10000 = "fixedfeegt10000";
	public static final String percentSPPCC = "percentSPPCC";
	public static final String fixedAmtPCC = "fixedAmtPCC";

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
	public static final String PCJABONG = "jabong";

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
			put("getGpVsProductCost", "%GP vs Product Cost");
			
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
			put("getGrossReturnChargesReversed", "Gross Return Charges Reversed");
			put("getTotalReturnCharges", "Total Return Charges");
			put("getGrossTds", "Total Gross TDS");
			put("getReturnTds", "Total Return TDS");
			put("getNetActualSale", "Net Actual Sale");
			put("getPaymentId", "Payment Id");
			put("getPaymentType", "Payment Type");
			
			// For Total Payments Received
			put("getPaymentDate", "Expected Date of Payment");
			put("getReceivedDate", "Order Received Date");
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
	
	public static final List<Long> times = Arrays.asList(
	        TimeUnit.DAYS.toMillis(365),
	        TimeUnit.DAYS.toMillis(30),
	        TimeUnit.DAYS.toMillis(1),
	        TimeUnit.HOURS.toMillis(1),
	        TimeUnit.MINUTES.toMillis(1),
	        TimeUnit.SECONDS.toMillis(1) );
	public static final List<String> timesString = Arrays.asList("year","month","day","hour","minute","second");

	public static String toDuration(long duration) {

	    StringBuffer res = new StringBuffer();
	    for(int i=0;i< times.size(); i++) {
	        Long current = times.get(i);
	        long temp = duration/current;
	        if(temp>0) {
	            res.append(temp).append(" ").append( timesString.get(i) ).append(temp > 1 ? "s" : "").append(" ago");
	            break;
	        }
	    }
	    if("".equals(res.toString()))
	        return "0 second ago";
	    else
	        return res.toString();
	}
}
