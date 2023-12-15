package com.hexaware.entity;

public class Sale {
	private int SaleId;
	private int productId;
	private double amount;
	private String date;
	
	
	public Sale(int saleId, int productId, double amount, String date) {
		super();
		SaleId = saleId;
		this.productId = productId;
		this.amount = amount;
		this.date = date;
	}
	
	public int getSaleId() {
		return SaleId;
	}

	public void setSaleId(int saleId) {
		SaleId = saleId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
	
}
