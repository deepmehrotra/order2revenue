package com.o2r.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class ShopcluesOrderAPI {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int shopcluesOrderId;
	@Column
	private int order_id;	
	@Column
	private String is_parent_order;
	@Column
	private int exempt_from_billing;
	@Column
	private int parent_order_id;
	@Column
	private int company_id;
	@Column
	private Date timestamp;
	@Column
	private String status;
	@Column
	private double total;
	@Column
	private double subtotal;
	@Column
	private String details;
	@Column
	private int payment_id;
	@Column
	private String s_city;
	@Column
	private String s_state;
	@Column
	private int s_zipcode;
	@Column
	private String label_printed;
	@Column
	private String gift_it;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@Column
	private double credit_used;	
	@Column
	private String product_name;
	@Column
	private int product_id;
	@Column
	private int quantity;
	@Column
	private double selling_price;
	@Column
	private String image_path;
	@Column
	private String payment_type;
	@Column
	private String errorMsg;
	@Column
	private String orderStatus;
	@Column
	private int sellerId;
	
	
	
	
	
	
	
}