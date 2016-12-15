package com.o2r.model;
// Generated Oct 23, 2016 4:48:17 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * PartnerSellerAuthInfo generated by hbm2java
 */
@Entity
@Table(name="partner_seller_auth_info"
    ,catalog="o2rschema"
)
public class PartnerSellerAuthInfo  {


     private Integer id;
     private String sellerid;
     private String partnerSellerAuthInfocol;
     private String mwsauthtoken;
     private String accesskey;
     private String secretkey;
     private String marketplaceid;
     private String serviceurl;
     private Integer status;
     private String pcName;
     private Partner partner;
     private String amazonSellerId;
     

   
	public PartnerSellerAuthInfo() {
    }

    public PartnerSellerAuthInfo(String sellerid, String partnerSellerAuthInfocol, String mwsauthtoken, String accesskey, String secretkey, String marketplaceid, String serviceurl, Integer status, String pcName, String amazonSellerId) {
       this.sellerid = sellerid;
       this.partnerSellerAuthInfocol = partnerSellerAuthInfocol;
       this.mwsauthtoken = mwsauthtoken;
       this.accesskey = accesskey;
       this.secretkey = secretkey;
       this.marketplaceid = marketplaceid;
       this.serviceurl = serviceurl;
       this.status = status;
       this.pcName = pcName;
       this.amazonSellerId=amazonSellerId;
    }
   
    @Id
  	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="SELLERID", length=1000)
    public String getSellerid() {
        return this.sellerid;
    }
    
    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    
    @Column(name="PARTNER_SELLER_AUTH_INFOcol", length=1000)
    public String getPartnerSellerAuthInfocol() {
        return this.partnerSellerAuthInfocol;
    }
    
    public void setPartnerSellerAuthInfocol(String partnerSellerAuthInfocol) {
        this.partnerSellerAuthInfocol = partnerSellerAuthInfocol;
    }

    
    @Column(name="MWSAUTHTOKEN", length=1000)
    public String getMwsauthtoken() {
        return this.mwsauthtoken;
    }
    
    public void setMwsauthtoken(String mwsauthtoken) {
        this.mwsauthtoken = mwsauthtoken;
    }

    
    @Column(name="ACCESSKEY", length=1000)
    public String getAccesskey() {
        return this.accesskey;
    }
    
    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    
    @Column(name="SECRETKEY", length=1000)
    public String getSecretkey() {
        return this.secretkey;
    }
    
    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    
    @Column(name="MARKETPLACEID", length=1000)
    public String getMarketplaceid() {
        return this.marketplaceid;
    }
    
    public void setMarketplaceid(String marketplaceid) {
        this.marketplaceid = marketplaceid;
    }

    
    @Column(name="SERVICEURL", length=1000)
    public String getServiceurl() {
        return this.serviceurl;
    }
    
    public void setServiceurl(String serviceurl) {
        this.serviceurl = serviceurl;
    }

    
    @Column(name="STATUS")
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    @Column(name="PCNAME")
    public String getPcName() {
        return this.pcName;
    }
    
    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    @OneToOne(cascade = CascadeType.ALL)
	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	@Column(name="amazonSellerId")
	public String getAmazonSellerId() {
			return amazonSellerId;
	}

	public void setAmazonSellerId(String amazonSellerId) {
			this.amazonSellerId = amazonSellerId;
	}

    
    


}


