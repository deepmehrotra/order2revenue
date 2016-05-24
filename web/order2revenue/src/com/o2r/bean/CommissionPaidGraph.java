package com.o2r.bean;

import java.util.Comparator;

public class CommissionPaidGraph {
	private double netPartnerCommissionPaid;
	private double grossPartnerCommissionPaid;
	private double netSaleQty;
	private double netTDSToBeDeposited;
	private double additionalReturnCharges;
	private double netReturnCommission;
	private double netChannelCommissionToBePaid;
	private double netSrCommisison;

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
			Comparator<CommissionPaidGraph> {
		@Override
		public int compare(CommissionPaidGraph graph1,
				CommissionPaidGraph graph2) {
			return graph1.grossPartnerCommissionPaid < graph2.grossPartnerCommissionPaid ? 1
					: (graph1.grossPartnerCommissionPaid > graph2.grossPartnerCommissionPaid ? -1
							: 0);
		}
	}

	public static class OrderByNetChannelCommission implements
			Comparator<CommissionPaidGraph> {
		@Override
		public int compare(CommissionPaidGraph graph1,
				CommissionPaidGraph graph2) {
			return graph1.netChannelCommissionToBePaid < graph2.netChannelCommissionToBePaid ? 1
					: (graph1.netChannelCommissionToBePaid > graph2.netChannelCommissionToBePaid ? -1
							: 0);
		}
	}
}
