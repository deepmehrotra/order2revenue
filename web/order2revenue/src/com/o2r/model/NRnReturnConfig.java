package com.o2r.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class NRnReturnConfig {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int configId;
	@Column
	private boolean nrCalculator;
	@Column
	private String commissionType;   // - fixed or categoryWise
	@Column
	private String categoryWiseCommsion;
	@Column
	private boolean whicheverGreaterPCC;
	@Column
	private String shippingFeeType ; // - variable  or fixed
	@Column
	private String revshippingFeeType ;

	@Column
	private 	String retCharType;
	@Column
	private boolean retCharSFFF;
	@Column
	private boolean retCharSFShipFee;
	@Column
	private boolean retCharSFSerTax;
	@Column
	private boolean retCharSFPCC;
	@Column
	private boolean retCharBRFF;
	@Column
	private boolean retCharBRShipFee;
	@Column
	private boolean retCharBRSerTax;
	@Column
	private boolean retCharBRPCC;
	@Column
	private String RTOCharType;

	@Column
	private boolean RTOCharSFFF;
	@Column
	private boolean RTOCharSFShipFee;
	@Column
	private boolean RTOCharSFSerTax;
	@Column
	private boolean RTOCharSFPCC;
	@Column
	private boolean RTOCharBRFF;
	@Column
	private boolean RTOCharBRShipFee;
	@Column
	private boolean RTOCharBRSerTax;
	@Column
	private boolean RTOCharBRPCC;
	@Column
	private 	String repCharType;
	@Column
	private boolean repCharSFFF;
	@Column
	private boolean repCharSFShipFee;
	@Column
	private boolean repCharSFSerTax;
	@Column
	private boolean repCharSFPCC;
	@Column
	private boolean repCharBRFF;
	@Column
	private boolean repCharBRShipFee;
	@Column
	private boolean repCharBRSerTax;
	@Column
	private boolean repCharBRPCC;
	@Column
	private String PDCharType;
	//@Column
	//private float PDCharSFFixAmt;
	//@Column
	//private float PDCharSFVarPerSP;
	@Column
	private boolean PDCharSFFF;
	@Column
	private boolean PDCharSFShipFee;
	@Column
	private boolean PDCharSFSerTax;
	@Column
	private boolean PDCharSFPCC;
	//@Column
	//private 	float PDCharBRFixAmt;
	//@Column
	//private float PDCharBRVarPerSP;
	@Column
	private boolean PDCharBRFF;
	@Column
	private boolean PDCharBRShipFee;
	@Column
	private boolean PDCharBRSerTax;
	@Column
	private boolean PDCharBRPCC;
	@Column
	private String canCharType;
	@Column
	private String canCharSFType;
	//@Column
	//private float canCharSFFixAmt;
	//@Column
	//private float canCharSFVarPerSP;
	@Column
	private boolean canCharSFFF;
	@Column
	private boolean canCharSFShipFee;
	@Column
	private boolean canCharSFSerTax;
	@Column
	private boolean canCharSFPCC;

	@Column
	private boolean canCharSFBRTDFF;
	@Column
	private boolean canCharSFBRTDShipFee;
	@Column
	private boolean canCharSFBRTDSerTax;
	@Column
	private boolean canCharSFBRTDPCC;
	//@Column
	//private 	float canCharBRFixAmt;
	//@Column
	//private float canCharBRVarPerSP;
	@Column
	private boolean canCharBRFF;
	@Column
	private boolean canCharBRShipFee;
	@Column
	private boolean canCharBRSerTax;
	@Column
	private boolean canCharBRPCC;
	@Column
	private String nationalList;
	@Column
	private String zonalList;
	@Column
	private String metroList;
	@Column
	private String localList;
	@Column
	private String revShippingFeeType ; // - variable  or fixed
	@OneToMany(cascade=CascadeType.ALL)
	private List<NRnReturnCharges> charges=new ArrayList<>();
	public int getConfigId() {
		return configId;
	}
	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
	public String getShippingFeeType() {
		return shippingFeeType;
	}
	public void setShippingFeeType(String shippingFeeType) {
		this.shippingFeeType = shippingFeeType;
	}
	public String getRetCharType() {
		return retCharType;
	}
	public void setRetCharType(String retCharType) {
		this.retCharType = retCharType;
	}
	public boolean isRetCharBRFF() {
		return retCharBRFF;
	}
	public void setRetCharBRFF(boolean retCharBRFF) {
		this.retCharBRFF = retCharBRFF;
	}
	public boolean isRetCharBRShipFee() {
		return retCharBRShipFee;
	}
	public void setRetCharBRShipFee(boolean retCharBRShipFee) {
		this.retCharBRShipFee = retCharBRShipFee;
	}
	public boolean isRetCharBRSerTax() {
		return retCharBRSerTax;
	}
	public void setRetCharBRSerTax(boolean retCharBRSerTax) {
		this.retCharBRSerTax = retCharBRSerTax;
	}
	public String getRTOCharType() {
		return RTOCharType;
	}
	public void setRTOCharType(String rTOCharType) {
		RTOCharType = rTOCharType;
	}
	public boolean isRTOCharSFFF() {
		return RTOCharSFFF;
	}
	public void setRTOCharSFFF(boolean rTOCharSFFF) {
		RTOCharSFFF = rTOCharSFFF;
	}
	public boolean isRTOCharSFShipFee() {
		return RTOCharSFShipFee;
	}
	public void setRTOCharSFShipFee(boolean rTOCharSFShipFee) {
		RTOCharSFShipFee = rTOCharSFShipFee;
	}
	public boolean isRTOCharSFSerTax() {
		return RTOCharSFSerTax;
	}
	public void setRTOCharSFSerTax(boolean rTOCharSFSerTax) {
		RTOCharSFSerTax = rTOCharSFSerTax;
	}
	public boolean isRTOCharBRFF() {
		return RTOCharBRFF;
	}
	public void setRTOCharBRFF(boolean rTOCharBRFF) {
		RTOCharBRFF = rTOCharBRFF;
	}
	public boolean isRTOCharBRShipFee() {
		return RTOCharBRShipFee;
	}
	public void setRTOCharBRShipFee(boolean rTOCharBRShipFee) {
		RTOCharBRShipFee = rTOCharBRShipFee;
	}
	public boolean isRTOCharBRSerTax() {
		return RTOCharBRSerTax;
	}
	public void setRTOCharBRSerTax(boolean rTOCharBRSerTax) {
		RTOCharBRSerTax = rTOCharBRSerTax;
	}
	public String getRepCharType() {
		return repCharType;
	}
	public void setRepCharType(String repCharType) {
		this.repCharType = repCharType;
	}
	public boolean isRepCharSFFF() {
		return repCharSFFF;
	}
	public void setRepCharSFFF(boolean repCharSFFF) {
		this.repCharSFFF = repCharSFFF;
	}
	public boolean isRepCharSFShipFee() {
		return repCharSFShipFee;
	}
	public void setRepCharSFShipFee(boolean repCharSFShipFee) {
		this.repCharSFShipFee = repCharSFShipFee;
	}
	public boolean isRepCharSFSerTax() {
		return repCharSFSerTax;
	}
	public void setRepCharSFSerTax(boolean repCharSFSerTax) {
		this.repCharSFSerTax = repCharSFSerTax;
	}
	public boolean isRepCharBRFF() {
		return repCharBRFF;
	}
	public void setRepCharBRFF(boolean repCharBRFF) {
		this.repCharBRFF = repCharBRFF;
	}
	public boolean isRepCharBRShipFee() {
		return repCharBRShipFee;
	}
	public void setRepCharBRShipFee(boolean repCharBRShipFee) {
		this.repCharBRShipFee = repCharBRShipFee;
	}
	public boolean isRepCharBRSerTax() {
		return repCharBRSerTax;
	}
	public void setRepCharBRSerTax(boolean repCharBRSerTax) {
		this.repCharBRSerTax = repCharBRSerTax;
	}
	public String getPDCharType() {
		return PDCharType;
	}
	public void setPDCharType(String pDCharType) {
		PDCharType = pDCharType;
	}
	public boolean isPDCharSFFF() {
		return PDCharSFFF;
	}
	public void setPDCharSFFF(boolean pDCharSFFF) {
		PDCharSFFF = pDCharSFFF;
	}
	public boolean isPDCharSFShipFee() {
		return PDCharSFShipFee;
	}
	public void setPDCharSFShipFee(boolean pDCharSFShipFee) {
		PDCharSFShipFee = pDCharSFShipFee;
	}
	public boolean isPDCharSFSerTax() {
		return PDCharSFSerTax;
	}
	public void setPDCharSFSerTax(boolean pDCharSFSerTax) {
		PDCharSFSerTax = pDCharSFSerTax;
	}
	public boolean isPDCharBRFF() {
		return PDCharBRFF;
	}
	public void setPDCharBRFF(boolean pDCharBRFF) {
		PDCharBRFF = pDCharBRFF;
	}
	public boolean isPDCharBRShipFee() {
		return PDCharBRShipFee;
	}
	public void setPDCharBRShipFee(boolean pDCharBRShipFee) {
		PDCharBRShipFee = pDCharBRShipFee;
	}
	public boolean isPDCharBRSerTax() {
		return PDCharBRSerTax;
	}
	public void setPDCharBRSerTax(boolean pDCharBRSerTax) {
		PDCharBRSerTax = pDCharBRSerTax;
	}
	public String getCanCharType() {
		return canCharType;
	}
	public void setCanCharType(String canCharType) {
		this.canCharType = canCharType;
	}
	public boolean isCanCharSFFF() {
		return canCharSFFF;
	}
	public void setCanCharSFFF(boolean canCharSFFF) {
		this.canCharSFFF = canCharSFFF;
	}
	public boolean isCanCharSFShipFee() {
		return canCharSFShipFee;
	}
	public void setCanCharSFShipFee(boolean canCharSFShipFee) {
		this.canCharSFShipFee = canCharSFShipFee;
	}
	public boolean isCanCharSFSerTax() {
		return canCharSFSerTax;
	}
	public void setCanCharSFSerTax(boolean canCharSFSerTax) {
		this.canCharSFSerTax = canCharSFSerTax;
	}

	public boolean isCanCharBRFF() {
		return canCharBRFF;
	}
	public void setCanCharBRFF(boolean canCharBRFF) {
		this.canCharBRFF = canCharBRFF;
	}
	public boolean isCanCharBRShipFee() {
		return canCharBRShipFee;
	}
	public void setCanCharBRShipFee(boolean canCharBRShipFee) {
		this.canCharBRShipFee = canCharBRShipFee;
	}
	public boolean isCanCharBRSerTax() {
		return canCharBRSerTax;
	}
	public void setCanCharBRSerTax(boolean canCharBRSerTax) {
		this.canCharBRSerTax = canCharBRSerTax;
	}
	public String getNationalList() {
		return nationalList;
	}
	public void setNationalList(String nationalList) {
		this.nationalList = nationalList;
	}
	public String getZonalList() {
		return zonalList;
	}
	public void setZonalList(String zonalList) {
		this.zonalList = zonalList;
	}
	public String getMetroList() {
		return metroList;
	}
	public void setMetroList(String metroList) {
		this.metroList = metroList;
	}
	public String getLocalList() {
		return localList;
	}
	public void setLocalList(String localList) {
		this.localList = localList;
	}
	public String getRevShippingFeeType() {
		return revShippingFeeType;
	}
	public void setRevShippingFeeType(String revShippingFeeType) {
		this.revShippingFeeType = revShippingFeeType;
	}
	public List<NRnReturnCharges> getCharges() {
		return charges;
	}
	public void setCharges(List<NRnReturnCharges> charges) {
		this.charges = charges;
	}
	public String getCategoryWiseCommsion() {
		return categoryWiseCommsion;
	}
	public void setCategoryWiseCommsion(String categoryWiseCommsion) {
		this.categoryWiseCommsion = categoryWiseCommsion;
	}
	public boolean isWhicheverGreaterPCC() {
		return whicheverGreaterPCC;
	}
	public void setWhicheverGreaterPCC(boolean whicheverGreaterPCC) {
		this.whicheverGreaterPCC = whicheverGreaterPCC;
	}
	public boolean isRetCharSFFF() {
		return retCharSFFF;
	}
	public void setRetCharSFFF(boolean retCharSFFF) {
		this.retCharSFFF = retCharSFFF;
	}
	public boolean isRetCharSFShipFee() {
		return retCharSFShipFee;
	}
	public void setRetCharSFShipFee(boolean retCharSFShipFee) {
		this.retCharSFShipFee = retCharSFShipFee;
	}
	public boolean isRetCharSFSerTax() {
		return retCharSFSerTax;
	}
	public void setRetCharSFSerTax(boolean retCharSFSerTax) {
		this.retCharSFSerTax = retCharSFSerTax;
	}
	public boolean isRetCharSFPCC() {
		return retCharSFPCC;
	}
	public void setRetCharSFPCC(boolean retCharSFPCC) {
		this.retCharSFPCC = retCharSFPCC;
	}
	public boolean isRetCharBRPCC() {
		return retCharBRPCC;
	}
	public void setRetCharBRPCC(boolean retCharBRPCC) {
		this.retCharBRPCC = retCharBRPCC;
	}
	public boolean isRTOCharSFPCC() {
		return RTOCharSFPCC;
	}
	public void setRTOCharSFPCC(boolean rTOCharSFPCC) {
		RTOCharSFPCC = rTOCharSFPCC;
	}
	public boolean isRTOCharBRPCC() {
		return RTOCharBRPCC;
	}
	public void setRTOCharBRPCC(boolean rTOCharBRPCC) {
		RTOCharBRPCC = rTOCharBRPCC;
	}
	public boolean isRepCharSFPCC() {
		return repCharSFPCC;
	}
	public void setRepCharSFPCC(boolean repCharSFPCC) {
		this.repCharSFPCC = repCharSFPCC;
	}
	public boolean isRepCharBRPCC() {
		return repCharBRPCC;
	}
	public void setRepCharBRPCC(boolean repCharBRPCC) {
		this.repCharBRPCC = repCharBRPCC;
	}
	public boolean isPDCharSFPCC() {
		return PDCharSFPCC;
	}
	public void setPDCharSFPCC(boolean pDCharSFPCC) {
		PDCharSFPCC = pDCharSFPCC;
	}
	public boolean isPDCharBRPCC() {
		return PDCharBRPCC;
	}
	public void setPDCharBRPCC(boolean pDCharBRPCC) {
		PDCharBRPCC = pDCharBRPCC;
	}
	public String getCanCharSFType() {
		return canCharSFType;
	}
	public void setCanCharSFType(String canCharSFType) {
		this.canCharSFType = canCharSFType;
	}
	public boolean isCanCharSFPCC() {
		return canCharSFPCC;
	}
	public void setCanCharSFPCC(boolean canCharSFPCC) {
		this.canCharSFPCC = canCharSFPCC;
	}
	public boolean isCanCharBRPCC() {
		return canCharBRPCC;
	}
	public void setCanCharBRPCC(boolean canCharBRPCC) {
		this.canCharBRPCC = canCharBRPCC;
	}
	public boolean isCanCharSFBRTDFF() {
		return canCharSFBRTDFF;
	}
	public void setCanCharSFBRTDFF(boolean canCharSFBRTDFF) {
		this.canCharSFBRTDFF = canCharSFBRTDFF;
	}
	public boolean isCanCharSFBRTDShipFee() {
		return canCharSFBRTDShipFee;
	}
	public void setCanCharSFBRTDShipFee(boolean canCharSFBRTDShipFee) {
		this.canCharSFBRTDShipFee = canCharSFBRTDShipFee;
	}
	public boolean isCanCharSFBRTDSerTax() {
		return canCharSFBRTDSerTax;
	}
	public void setCanCharSFBRTDSerTax(boolean canCharSFBRTDSerTax) {
		this.canCharSFBRTDSerTax = canCharSFBRTDSerTax;
	}
	public boolean isCanCharSFBRTDPCC() {
		return canCharSFBRTDPCC;
	}
	public void setCanCharSFBRTDPCC(boolean canCharSFBRTDPCC) {
		this.canCharSFBRTDPCC = canCharSFBRTDPCC;
	}
	public String getRevshippingFeeType() {
		return revshippingFeeType;
	}
	public void setRevshippingFeeType(String revshippingFeeType) {
		this.revshippingFeeType = revshippingFeeType;
	}
	public boolean isNrCalculator() {
		return nrCalculator;
	}
	public void setNrCalculator(boolean nrCalculator) {
		this.nrCalculator = nrCalculator;
	}


}

