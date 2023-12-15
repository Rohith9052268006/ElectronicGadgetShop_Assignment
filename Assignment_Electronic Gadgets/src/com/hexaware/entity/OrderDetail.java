package com.hexaware.entity;

import java.io.*;

public class OrderDetail {
    private int orderDetailID;
    private Orders orders;
    private Product product;
    private int quantity;

    public OrderDetail(int orderDetailID, Orders orders, Product product, int quantity) {
        this.orderDetailID = orderDetailID;
        this.orders = orders;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderDetail() {
		// TODO Auto-generated constructor stub
	}

	public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Orders getOrder() {
        return orders;
    }

    public void setOrder(Orders orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double calculateSubtotal() {
    	return quantity * product.getPrice();
    }

    public void getOrderDetailInfo() {
    	System.out.println("Order Detail ID: " + orderDetailID);
        System.out.println("Product: " + product.getProductName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Subtotal: " + calculateSubtotal());
    }

    public void updateQuantity() throws IOException{
    	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    	int newQuantity = Integer.parseInt(br.readLine());
    	if (newQuantity > 0) {
            this.quantity = newQuantity;
            System.out.println("Quantity for OrderDetailID " + orderDetailID + " updated to " + newQuantity);
        } else {
            System.out.println("Invalid quantity. Please provide a positive quantity.");
        }
    }

    public void addDiscount() {
    	int discountPercentage=20;
    	product.setPrice(product.getPrice()*0.8);
    	if (discountPercentage < 0 || discountPercentage > 100) {
            System.out.println("Invalid discount percentage. Discount not applied.");
            return;
        }
    	double discountAmount = calculateSubtotal() * (discountPercentage / 100);
    	double discountedSubtotal = calculateSubtotal() - discountAmount;
    	System.out.println("Discount applied: " + discountAmount);
        System.out.println("Discounted Subtotal: " + discountedSubtotal);
    }
}
