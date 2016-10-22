package com.o2r.amazonservices.mws.test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.model.GetReportRequest;
import com.amazonaws.mws.model.GetReportRequestListRequest;
import com.amazonaws.mws.model.GetReportRequestListResponse;
import com.amazonaws.mws.model.GetReportRequestListResult;
import com.amazonaws.mws.model.GetReportResponse;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.ReportRequestInfo;
import com.amazonaws.mws.model.RequestReportRequest;
import com.amazonaws.mws.model.RequestReportResponse;
import com.amazonaws.mws.model.RequestReportResult;
import com.amazonaws.mws.model.ResponseMetadata;
import com.amazonservices.mws.orders._2013_09_01.constants.MWSConstants;

public class ReportMain {

	final String accessKeyId = MWSConstants.ACCESSKEY;
	final String secretAccessKey = MWSConstants.SECRETKEY;

	final String appName = MWSConstants.APPNAME;
	final String appVersion = MWSConstants.APPVERSION;
	final String merchantId = MWSConstants.SELLERID;
	final String sellerDevAuthToken = MWSConstants.MWSAUTHTOKEN;
	// marketplaces from which data should be included in the report; look at
	// the
	// API reference document on the MWS website to see which marketplaces are
	// included if you do not specify the list yourself
	final IdList marketplaces = new IdList(Arrays.asList("A21TJRUUN4KGV"));

	public ReportMain() {
		try {
			//invokeRequestReport();
			//invokeReportRequestList();
			invokeReportUsingReportId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void invokeRequestReport() throws Exception {
		try {
			MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
			MarketplaceWebService service = new MarketplaceWebServiceClient(accessKeyId, secretAccessKey, appName, appVersion, config);
			RequestReportRequest request = new RequestReportRequest()
					.withMerchant(merchantId)
					.withMarketplaceIdList(marketplaces)
					.withReportType("_GET_FBA_FULFILLMENT_CUSTOMER_RETURNS_DATA_")
					.withReportOptions("ShowSalesChannel=true");
			DatatypeFactory df = null;
			try {
				df = DatatypeFactory.newInstance();
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			XMLGregorianCalendar startDate = df.newXMLGregorianCalendar(new GregorianCalendar(2011, 1, 1));
			request.setStartDate(startDate);
			requestMwsReport(service, request);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}

	public void requestMwsReport(MarketplaceWebService service, RequestReportRequest request) throws Exception {
		try {
RequestReportResponse response = service.requestReport(request);

            
            System.out.println ("RequestReport Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    RequestReportResponse");
            System.out.println();
            if (response.isSetRequestReportResult()) {
                System.out.print("        RequestReportResult");
                System.out.println();
                RequestReportResult  requestReportResult = response.getRequestReportResult();
                if (requestReportResult.isSetReportRequestInfo()) {
                    System.out.print("            ReportRequestInfo");
                    System.out.println();
                    ReportRequestInfo  reportRequestInfo = requestReportResult.getReportRequestInfo();
                    if (reportRequestInfo.isSetReportRequestId()) {
                        System.out.print("                ReportRequestId");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getReportRequestId());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetReportType()) {
                        System.out.print("                ReportType");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getReportType());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetStartDate()) {
                        System.out.print("                StartDate");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getStartDate());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetEndDate()) {
                        System.out.print("                EndDate");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getEndDate());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getSubmittedDate());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetReportProcessingStatus()) {
                        System.out.print("                ReportProcessingStatus");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getReportProcessingStatus());
                        System.out.println();
                    }
                } 
            } 
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                System.out.println();
                ResponseMetadata  responseMetadata = response.getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    System.out.println();
                    System.out.print("                " + responseMetadata.getRequestId());
                    System.out.println();
                }
            } 
            System.out.println();
            System.out.println(response.getResponseHeaderMetadata());
            System.out.println();
			
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}

	public void invokeReportRequestList() throws Exception {
		try {
			MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
			MarketplaceWebService service = new MarketplaceWebServiceClient(accessKeyId, secretAccessKey, appName, appVersion, config);
			GetReportRequestListRequest request = new GetReportRequestListRequest();
	        request.setMerchant( merchantId );
	        reportRequestList(service, request);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public void reportRequestList(MarketplaceWebService service, GetReportRequestListRequest request) throws Exception {
		try {
			GetReportRequestListResponse response = service.getReportRequestList(request);
			System.out.println ("GetReportRequestList Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetReportRequestListResponse");
            System.out.println();
            if (response.isSetGetReportRequestListResult()) {
                System.out.print("        GetReportRequestListResult");
                System.out.println();
                GetReportRequestListResult  getReportRequestListResult = response.getGetReportRequestListResult();
                if (getReportRequestListResult.isSetNextToken()) {
                    System.out.print("            NextToken");
                    System.out.println();
                    System.out.print("                " + getReportRequestListResult.getNextToken());
                    System.out.println();
                }
                if (getReportRequestListResult.isSetHasNext()) {
                    System.out.print("            HasNext");
                    System.out.println();
                    System.out.print("                " + getReportRequestListResult.isHasNext());
                    System.out.println();
                }
                java.util.List<ReportRequestInfo> reportRequestInfoList = getReportRequestListResult.getReportRequestInfoList();
                for (ReportRequestInfo reportRequestInfo : reportRequestInfoList) {
                    System.out.print("            ReportRequestInfo");
                    System.out.println();
                    if (reportRequestInfo.isSetReportRequestId()) {
                        System.out.print("                ReportRequestId");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getReportRequestId());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetReportType()) {
                        System.out.print("                ReportType");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getReportType());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetStartDate()) {
                        System.out.print("                StartDate");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getStartDate());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetEndDate()) {
                        System.out.print("                EndDate");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getEndDate());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetSubmittedDate()) {
                        System.out.print("                SubmittedDate");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getSubmittedDate());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetCompletedDate()) {
                        System.out.print("                CompletedDate");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getCompletedDate());
                        System.out.println();
                    }                    
                    if (reportRequestInfo.isSetReportProcessingStatus()) {
                        System.out.print("                ReportProcessingStatus");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getReportProcessingStatus());
                        System.out.println();
                    }
                    if (reportRequestInfo.isSetGeneratedReportId()) {
                        System.out.print("                ReportId");
                        System.out.println();
                        System.out.print("                    " + reportRequestInfo.getGeneratedReportId());
                        System.out.println();
                    }
                }
            } 
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                System.out.println();
                ResponseMetadata  responseMetadata = response.getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    System.out.println();
                    System.out.print("                " + responseMetadata.getRequestId());
                    System.out.println();
                }
            } 
            System.out.println();
            System.out.println(response.getResponseHeaderMetadata());
            System.out.println();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public void invokeReportUsingReportId() throws Exception {
		try {
			MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
			MarketplaceWebService service = new MarketplaceWebServiceClient(accessKeyId, secretAccessKey, appName, appVersion, config);
			GetReportRequest request = new GetReportRequest();
	        request.setMerchant( merchantId );
	        request.setMWSAuthToken(sellerDevAuthToken);
	        request.setReportId( "3756184186017083" );
	        OutputStream report = new FileOutputStream( "report.xml" );
	        request.setReportOutputStream( report );
			reportUsingReportId(service, request);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public void reportUsingReportId(MarketplaceWebService service, GetReportRequest request) throws Exception {
		try {
			GetReportResponse response = service.getReport(request);


            System.out.println ("GetReport Action Response");
            System.out.println ("=============================================================================");
            System.out.println ();

            System.out.print("    GetReportResponse");
            System.out.println();
            System.out.print("    GetReportResult");
            System.out.println();
            System.out.print("            MD5Checksum");
            System.out.println();
            System.out.print("                " + response.getGetReportResult().getMD5Checksum());
            System.out.println();
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                System.out.println();
                ResponseMetadata  responseMetadata = response.getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    System.out.println();
                    System.out.print("                " + responseMetadata.getRequestId());
                    System.out.println();
                }
            } 
            System.out.println();

            System.out.println("Report");
            System.out.println ("=============================================================================");
            System.out.println();
            System.out.println( request.getReportOutputStream().toString() );
            System.out.println();

            System.out.println(response.getResponseHeaderMetadata());
            System.out.println();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ReportMain();
	}

}
