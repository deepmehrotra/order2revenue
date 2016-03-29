/*
 * @Author Kapil Kumar
 */

package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Plan")
public class Plan {

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private int pid;
		
		@Column()
		private String planId;
		@Column		
		private long orderCount;
		@Column		
		private String description;
		@Column		
		private String planName;
		@Column	
		private boolean isActive;
		@Column		
		private double planPrice;
		public int getPid() {
			return pid;
		}
		public void setPid(int pid) {
			this.pid = pid;
		}
		public String getPlanId() {
			return planId;
		}
		public void setPlanId(String planId) {
			this.planId = planId;
		}
		public long getOrderCount() {
			return orderCount;
		}
		public void setOrderCount(long orderCount) {
			this.orderCount = orderCount;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getPlanName() {
			return planName;
		}
		public void setPlanName(String planName) {
			this.planName = planName;
		}
		public boolean isActive() {
			return isActive;
		}
		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
		public double getPlanPrice() {
			return planPrice;
		}
		public void setPlanPrice(double planPrice) {
			this.planPrice = planPrice;
		}
		
		
}
