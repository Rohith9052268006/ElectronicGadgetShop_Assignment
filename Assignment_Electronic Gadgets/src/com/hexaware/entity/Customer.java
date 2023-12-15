package com.hexaware.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private List<Orders> orders;
    
    

	public Customer(int customerID, String firstName, String lastName, String email, String phone, String address) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Customer() {
		
	}

	public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

    public void CalculateTotalOrders() {
        System.out.println(firstName+" "+lastName+" placed "+orders.size()+" orders");
    }

    public void GetCustomerDetails() {
        System.out.println("CustomerID : "+customerID);
        System.out.println("FirstName : "+firstName);
        System.out.println("Last Name : "+lastName);
        System.out.println("Phone : "+phone);
        System.out.println("Address : "+address);
    }

    public void UpdateCustomerInfo() throws NumberFormatException, IOException {
        System.out.println("1.Update email\n2.Update PhoneNo\n3.Update Address");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int choice =Integer.parseInt(br.readLine());
        switch(choice) {
	        case 1:
	        	System.out.println("Enter new Email");
	        	String newEmail=br.readLine();
	        	this.email=newEmail;
	        	break;
	        case 2:
	        	System.out.println("Enter new PhoneNo");
	        	String newPhone=br.readLine();
	        	this.phone=newPhone;
	        	break;
	        case 3:
	        	System.out.println("Enter new Address");
	        	String newAddress=br.readLine();
	        	this.address=newAddress;
	        	break;
	        default:
	        	System.out.println("Please enter correct choice");
        }
    }
}
