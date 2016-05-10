package com.o2r.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="gatepass")
public class GatePass {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int gatepassId;
	@Column
	private String poID;
	@Column
	private String invoiceID;
	@Column
	private String productSkuCode;
	@Column
	private int quantity;
	@Column
	private double returnpr;
	@Column
	private float serviceTax;
	@Column
	private double landedPrice;
	@Column
	private double netReturn;
	@ManyToOne(cascade=CascadeType.ALL)
	private OrderRTOorReturn consolidatedReturn;
	
	/*@Override
	public String toString() {
		return "OrderRTOorReturn [returnId=" + returnId
				+ ", returnOrRTOreason=" + returnOrRTOreason + ", returnDate="
				+ returnDate + ", returnOrRTOCharges=" + returnOrRTOCharges
				+ ", returnorrtoQty=" + returnorrtoQty + ", returnOrRTOId="
				+ returnOrRTOId + ", returnUploadDate=" + returnUploadDate
				+ "]";
	}*/

	public int getGatepassId() {
		return gatepassId;
	}

	public void setGatepassId(int gatepassId) {
		this.gatepassId = gatepassId;
	}

	public String getPoID() {
		return poID;
	}

	public void setPoID(String poID) {
		this.poID = poID;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public String getProductSkuCode() {
		return productSkuCode;
	}

	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getReturnpr() {
		return returnpr;
	}

	public void setReturnpr(double returnpr) {
		this.returnpr = returnpr;
	}

	public float getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(float serviceTax) {
		this.serviceTax = serviceTax;
	}

	public double getLandedPrice() {
		return landedPrice;
	}

	public void setLandedPrice(double landedPrice) {
		this.landedPrice = landedPrice;
	}

	public double getNetReturn() {
		return netReturn;
	}

	public void setNetReturn(double netReturn) {
		this.netReturn = netReturn;
	}
}
