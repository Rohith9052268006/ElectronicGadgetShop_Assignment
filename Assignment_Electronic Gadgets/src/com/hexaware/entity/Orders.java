package com.hexaware.entity;

import java.io.IOException;
import com.hexaware.controller.OrderController;
import com.hexaware.exceptions.InvalidProductException;

public class Orders {
    private int orderID;
    private Customer customer;
    private String orderDate;
    private String status;
	private double totalAmount;
	OrderController orderController = new OrderController();
	
    public Orders(int orderID, Customer customer, String orderDate,String status, double totalAmount) {
        this.orderID = orderID;
        this.customer = customer;
        this.orderDate = orderDate;
        this.status=status;
        this.totalAmount = totalAmount;
    }

    public Orders() {
		
	}

	public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void CalculateTotalAmount() {
        System.out.println("Total amount for orderId : "+orderID +" is "+getTotalAmount());
    }
    
    public void GetOrderDetails() {
    	System.out.println("Order ID: " + orderID);
        System.out.println("Customer: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("Order Date : "+orderDate);
        System.out.println("Order Status: " + status);
        

        
    }

    public void UpdateOrderStatus(String status) throws IOException{
    	orderController.updateOrderStatus();
    }

    public void CancelOrder() throws InvalidProductException {
    	orderController.cancelOrder(this.orderID);
    }
}
