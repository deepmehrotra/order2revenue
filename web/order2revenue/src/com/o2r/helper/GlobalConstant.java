package com.o2r.helper;

import java.util.ArrayList;

public class GlobalConstant {

	public final ArrayList<String> orderList = new ArrayList<String>(){
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

			   }};



public final ArrayList<String> productHeaderList = new ArrayList<String>(){
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


public final ArrayList<String> paymentHeaderList = new ArrayList<String>(){
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


public final ArrayList<String> inventoryHeaderList = new ArrayList<String>(){
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

public final ArrayList<String> expenseHeaderList = new ArrayList<String>(){
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


		//Constants for Error messages
public static  final String addCategoryError="Error while saving the new category. Either try again with correct values or contact admin with the error details.";
public static  final String listCategoryError="Error while retrieving Product Category list. Either try again or contact admin with the error details.";
public static  final String listInventoryGroupError="Error while retrieving Inventory Groups. Either try again or contact admin with the error details.";
public static  final String getSKUCountError="Error while retrieving SKU. Either try again or contact admin with the error details.";
public static  final String getCategorywithNameError="Error while retrieving category entered by user. Either try again or contact admin with the error details.";
public static  final String deleteCategoryError="Error while deleting category. Either try again or contact admin with the error details.";

public static  final String addTaxDetailError="Error while saving the tax details. Either try again with correct values or contact admin with the error details.";
public static  final String addTaxDetailErrorCode="#00071";

public static  final String addTaxPaymentDetailError="Error while payment of  the tax details. Either try again with correct values or contact admin with the error details.";
public static  final String addTaxPaymentDetailErrorCode="#00072";

public static  final String addTDSPaymentDetailError="Error while payment of  the TDS details. Either try again with correct values or contact admin with the error details.";
public static  final String addTDSPaymentDetailErrorCode="#00073";

public static  final String addTaxCategoryError="Error while adding Tax Category. Either try again with correct values or contact admin with the error details.";
public static  final String addTaxCategoryErrorCode="#00074";

public static  final String getTaxDetailListError="Error while getting Tax Details. Either try again with correct values or contact admin with the error details.";
public static  final String getTaxDetailListErrorCode="#00077";
public static  final String getTaxDetailListErrorCode2="#00076";

public static  final String getTaxCatListError="Error while getting Tax Category. Either try again with correct values or contact admin with the error details.";
public static  final String getTaxCatListErrorCode="#00075";

public static  final String getTaxCategoryError="Error while getting Tax Category. Either try again  or contact admin with the error details.";
public static  final String getTaxCategoryErrorCode="#00078";

public static  final String deleteTaxCategoryError="Error while deleting Tax Category. Either try again  or contact admin with the error details.";
public static  final String deleteTaxCategoryErrorCode="#00079";

//Error Codes
public static  final String addCategoryErrorCode="#00061";
public static  final String listCategoryErrorCode="#00062";
public static  final String listInventoryGroupErrorCode="#00063";
public static  final String getSKUCountErrorCode="#000564";
public static  final String getCategorywithNameErrorCode="#00065";
public static  final String deleteCategoryErrorCode="#00066";



//NRnReturn Configuration Constants
public static  final String fixedCommissionPercent="fixedCommissionPercent";
public static  final String fixedfeelt250="fixedfeelt250";
public static  final String fixedfeelt500="fixedfeelt500";
public static  final String fixedfeelt500Big="fixedfeelt500Big";
public static  final String fixedfeegt250lt500="fixedfeegt250lt500";
public static  final String fixedfeegt500="fixedfeegt500";
public static  final String fixedfeegt500lt1000="fixedfeegt500lt100";
public static  final String fixedfeegt1000lt10000="fixedfeegt250lt500";
public static  final String fixedfeegt10000="fixedfeegt1000";
public static  final String percentSPPCC="percentSPPCC";
public static  final String fixedAmtPCC="fixedAmtPCC";


//Return Configuration Constansts
public static  final String SellerFaultString="sellerFault";
public static  final String BuyerReturnString="buyerReturn";

public static  final String fixedString="fixed";
public static  final String variableString="variable";


public static  final String ReturnChargesSellerFaultVariablePercentSP="retCharSFPercentSP";
public static  final String ReturnChargesSellerFaultFixedAmount="retCharSFFixedAmt";
//public static  final String ReturnChargesSellerFaultFixedFee="retCharSFFF";
public static  final String ReturnChargesSellerFaultVariableFixedAmt="retCharSFVarFixedAmt";

//Return Chages Buyer Return Configuration Strings
//public static  final String ReturnChargesBuyerReturnFixedFee="retCharBRFF";
public static  final String ReturnChargesBuyerReturnFixedAmount="retCharBRFixedAmt";
public static  final String ReturnChargesBuyerReturnVariableFixedAmt="retCharBRVarFixedAmt";
public static  final String ReturnChargesBuyerReturnVariablePercentSP="retCharBRPercentSP";

//RTO Charges Configuration
public static  final String RTOChargesSellerFaultVariablePercentSP="RTOCharSFPercentSP";
public static  final String RTOChargesSellerFaultFixedAmount="RTOCharSFFixedAmt";
public static  final String RTOChargesSellerFaultVariableFixedAmt="RTOCharSFVarFixedAmt";

//RTO Chages Buyer Return Configuration Strings
public static  final String RTOChargesBuyerReturnFixedAmount="RTOCharBRFixedAmt";
public static  final String RTOChargesBuyerReturnVariableFixedAmt="RTOCharBRVarFixedAmt";
public static  final String RTOChargesBuyerReturnVariablePercentSP="RTOCharBRPercentSP";

//Cancellation Charges Configuration

public static  final String SFCancellationAfterRTDString="afterRTD";
public static  final String SFCancellationBeforeRTDString="beforeRTD";


public static  final String CancellationChargesSellerFaultVariablePercentSP="cancelCharSFPercentSP";
public static  final String CancellationChargesSellerFaultFixedAmount="cancelCharSFFixedAmt";
public static  final String CancellationChargesSellerFaultVariableFixedAmt="cancelCharSFVarFixedAmt";

public static  final String CancellationChargesSellerFaultBRTDVariablePercentSP="cancelCharSFBRTDPercentSP";
public static  final String CancellationChargesSellerFaultBRTDFixedAmount="cancelCharSFBRTDFixedAmt";
public static  final String CancellationChargesSellerFaultBRTDVariableFixedAmt="cancelCharSFVarBRTDFixedAmt";

//Cancellation Chager Buyer Return Configuration Strings
public static  final String CancellationChargesBuyerReturnFixedAmount="cancelCharBRFixedAmt";
public static  final String CancellationChargesBuyerReturnVariableFixedAmt="cancelCharBRVarFixedAmt";
public static  final String CancellationChargesBuyerReturnVariablePercentSP="cancelCharBRPercentSP";

//Replacement Charges Configuration
public static  final String ReplacementChargesSellerFaultVariablePercentSP="replacementCharSFPercentSP";
public static  final String ReplacementChargesSellerFaultFixedAmount="replacementCharSFFixedAmt";
public static  final String ReplacementChargesSellerFaultVariableFixedAmt="replacementCharSFFixedAmt";

//Replacement Chages Buyer Return Configuration Strings
public static  final String ReplacementChargesBuyerReturnFixedAmount="replacementCharBRFixedAmt";
public static  final String ReplacementChargesBuyerReturnVariableFixedAmt="replacementCharBRVarFixedAmt";
public static  final String ReplacementChargesBuyerReturnVariablePercentSP="replacementCharBRPercentSP";

//Partial Delivery Charges Configuration
public static  final String PartialDelChargesSellerFaultVariablePercentSP="partialDelCharSFPercentSP";
public static  final String PartialDelChargesSellerFaultFixedAmount="partialDelCharSFFixedAmt";
public static  final String PartialDelChargesSellerFaultVariableFixedAmt="partialDelCharSFVarFixedAmt";

//PartialDelivery Chager Buyer Return Configuration Strings
public static  final String PartialDelChargesBuyerReturnFixedAmount="partialDelCharBRFixedAmt";
public static  final String PartialDelChargesBuyerReturnVariableFixedAmt="partialDelCharBRVarFixedAmt";
public static  final String PartialDelChargesBuyerReturnVariablePercentSP="partialDelCharBRPercentSP";

/*public static  final String fixedCommissionPercent="fixedCommissionPercent";
public static  final String fixedfeelt250="fixedfeelt250";
public static  final String fixedfeelt500="fixedfeelt500";
public static  final String fixedfeelt500Big="fixedfeelt500Big";*/

}


