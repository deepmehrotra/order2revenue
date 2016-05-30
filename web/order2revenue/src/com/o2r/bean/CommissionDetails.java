package com.o2r.bean;

import java.util.Comparator;

public class CommissionDetails {
	private String partner;
	private String categoryName;
	private double netPartnerCommissionPaid;
	private double grossPartnerCommissionPaid;
	private double netSaleQty;
	private double netTDSToBeDeposited;
	private double additionalReturnCharges;
	private double netReturnCommission;
	private double netChannelCommissionToBePaid;
	private double netSrCommisison;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public double getNetPartnerCommissionPaid() {
		return netPartnerCommissionPaid;
	}

	public void setNetPartnerCommissionPaid(double netPartnerCommissionPaid) {
		this.netPartnerCommissionPaid = netPartnerCommissionPaid;
	}

	public double getGrossPartnerCommissionPaid() {
		return grossPartnerCommissionPaid;
	}

	public void setGrossPartnerCommissionPaid(double grossPartnerCommissionPaid) {
		this.grossPartnerCommissionPaid = grossPartnerCommissionPaid;
	}

	public double getNetSaleQty() {
		return netSaleQty;
	}

	public void setNetSaleQty(double netSaleQty) {
		this.netSaleQty = netSaleQty;
	}

	public double getNetTDSToBeDeposited() {
		return netTDSToBeDeposited;
	}

	public void setNetTDSToBeDeposited(double netTDSToBeDeposited) {
		this.netTDSToBeDeposited = netTDSToBeDeposited;
	}

	public double getAdditionalReturnCharges() {
		return additionalReturnCharges;
	}

	public void setAdditionalReturnCharges(double additionalReturnCharges) {
		this.additionalReturnCharges = additionalReturnCharges;
	}

	public double getNetReturnCommission() {
		return netReturnCommission;
	}

	public void setNetReturnCommission(double netReturnCommission) {
		this.netReturnCommission = netReturnCommission;
	}

	public double getNetChannelCommissionToBePaid() {
		return netChannelCommissionToBePaid;
	}

	public void setNetChannelCommissionToBePaid(
			double netChannelCommissionToBePaid) {
		this.netChannelCommissionToBePaid = netChannelCommissionToBePaid;
	}

	public double getNetSrCommisison() {
		return netSrCommisison;
	}

	public void setNetSrCommisison(double netSrCommisison) {
		this.netSrCommisison = netSrCommisison;
	}

	public static class OrderByGrossCommission implements
			Comparator<CommissionDetails> {
		@Override
		public int compare(CommissionDetails graph1,
				CommissionDetails graph2) {
			return graph1.grossPartnerCommissionPaid < graph2.grossPartnerCommissionPaid ? 1
					: (graph1.grossPartnerCommissionPaid > graph2.grossPartnerCommissionPaid ? -1
							: 0);
		}
	}

	public static class OrderByNetChannelCommission implements
			Comparator<CommissionDetails> {
		@Override
		public int compare(CommissionDetails graph1,
				CommissionDetails graph2) {
			return graph1.netChannelCommissionToBePaid < graph2.netChannelCommissionToBePaid ? 1
					: (graph1.netChannelCommissionToBePaid > graph2.netChannelCommissionToBePaid ? -1
							: 0);
		}
	}
}
