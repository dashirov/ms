package org.maj.sm.model;

import java.util.Date;

public class PriceChangeLog {
	
	private Date priceChangeDate;
	private double newPrice;
	
	public PriceChangeLog() {
		super();
	}
	public PriceChangeLog(Date priceChangeDate, double newPrice) {
		super();
		this.priceChangeDate = priceChangeDate;
		this.newPrice = newPrice;
	}
	public Date getPriceChangeDate() {
		return priceChangeDate;
	}
	public void setPriceChangeDate(Date priceChangeDate) {
		this.priceChangeDate = priceChangeDate;
	}
	public double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}
	
	

}
