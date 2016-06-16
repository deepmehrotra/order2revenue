package com.o2r.dao;

import java.util.Date;
import java.util.List;

import com.o2r.bean.ChannelCatNPR;
import com.o2r.bean.ChannelGP;
import com.o2r.bean.ChannelMC;
import com.o2r.bean.ChannelMCNPR;
import com.o2r.bean.ChannelNPR;
import com.o2r.bean.ChannelNR;
import com.o2r.bean.ChannelNetQty;
import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.CommissionDetails;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;
import com.o2r.model.UploadReport;

/**
 * @author Deep Mehrotra
 *
 */
public interface ReportsGeneratorDao {

	public TotalShippedOrder getPartnerTSOdetails(String pcName,
			Date startDate, Date endDate, int sellerId) throws CustomException;

	public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate,
			Date endDate, int sellerId) throws CustomException;

	public List<PartnerReportDetails> getPartnerReportDetails(Date startDate, Date endDate,
			int sellerId) throws CustomException;

	public List<ChannelReportDetails> getChannelReportDetails(Date startDate, Date endDate, int sellerId) throws CustomException;

	public List<PartnerReportDetails> getDebtorsReportDetails(Date startDate, Date endDate, int sellerId) throws CustomException;

	public List<CommissionDetails> fetchPC(int sellerId, Date startDate, Date endDate, String criteria);

	public UploadReport addUploadReport(UploadReport uploadReport, int sellerId)
			throws CustomException;

	public List<UploadReport> listUploadReport(int sellerId) throws CustomException;

	public UploadReport getUploadLog(int id, int sellerId) throws CustomException;

	public List<ChannelNPR> fetchChannelNPR(int sellerId, Date startDate, Date endDate, String criteria);

	public List<ChannelCatNPR> fetchChannelCatNPR(int sellerId, Date startDate, Date endDate, String criteria);

	public List<ChannelMC> fetchChannelMC(int sellerId, Date startDate, Date endDate, String criteria);

	public List<ChannelMCNPR> fetchChannelMCNPR(int sellerId, Date startDate, Date endDate, String criteria);

	public List<ChannelNR> fetchChannelNR(int sellerId, Date startDate, Date endDate, String criteria);

	public List<ChannelNetQty> fetchChannelNetQty(int sellerId, Date startDate, Date endDate, String criteria);

	public List<ChannelGP> fetchChannelGP(int sellerId, Date startDate, Date endDate, String criteria);

	//public void addUploadReport(UploadReport uploadReport) throws CustomException;

	/*public List<UploadReport> listUploadReport() throws CustomException;

	public UploadReport addUploadReport(UploadReport uploadReport, int sellerId)
			throws CustomException;*/

}
