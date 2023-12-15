package com.hexaware.dao;

import com.hexaware.entity.Customer;
import com.hexaware.entity.Orders;
import com.hexaware.exceptions.InvalidDataException;
import com.hexaware.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
	Connection connection = DBConnUtil.getMyDBConnection();
	
	public void addCustomer(Customer customer) throws InvalidDataException {
	    try {
	    	PreparedStatement preparedStatement = connection.prepareStatement
	    			("INSERT INTO Customer (customerid, FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?, ?)");

	        preparedStatement.setInt(1, customer.getCustomerID());
	        preparedStatement.setString(2, customer.getFirstName());
	        preparedStatement.setString(3, customer.getLastName());
	        preparedStatement.setString(4, customer.getEmail());
	        preparedStatement.setString(5, customer.getPhone());
	        preparedStatement.setString(6, customer.getAddress());

	        
	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected == 0) {
	            throw new SQLException("Customer insertion failed, no rows affected.");
	        }

	    } catch (SQLException e) {
	        throw new InvalidDataException("Error adding customer to the database.");
	    }
	}


    public void updateCustomer(Customer customer) throws InvalidDataException {
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Customer SET FirstName=?, LastName=?, Email=?, Phone=?, Address=? WHERE CustomerID=?")) {

           
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.setInt(6, customer.getCustomerID());

            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Customer update failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new InvalidDataException("Error updating customer details in the database.");
        }
    }

    public void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnUtil.getMyDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer")) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("CustomerID"));
                customer.setFirstName(resultSet.getString("FirstName"));
                customer.setLastName(resultSet.getString("LastName"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setAddress(resultSet.getString("Address"));

                customers.add(customer);
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        for (int i = 0; i < customers.size(); i++) {
            System.out.println("Customer id : " + customers.get(i).getCustomerID());
            System.out.println("First name : " + customers.get(i).getFirstName());
            System.out.println("Last name : " + customers.get(i).getLastName());
            System.out.println("Phone no : " + customers.get(i).getPhone());
            System.out.println("Address : " + customers.get(i).getAddress());
            System.out.println();
        }

    }
    public List<Customer> getAllCustomersWithOrders() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnUtil.getMyDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer")) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("CustomerID"));
                customer.setFirstName(resultSet.getString("FirstName"));
                customer.setLastName(resultSet.getString("LastName"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setAddress(resultSet.getString("Address"));

                
                List<Orders> order = getOrdersForCustomer(customer.getCustomerID());
                customer.setOrders(order);

                customers.add(customer);
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        return customers;
    }
    private List<Orders> getOrdersForCustomer(int customerId) {
        List<Orders> orders = new ArrayList<>();

        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders WHERE customer_id = ?")) {

            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
            	while (resultSet.next()) {
            	    Orders order = new Orders();
            	    order.setOrderID(resultSet.getInt("OrderID"));
            	    order.setOrderDate(resultSet.getString("OrderDate"));
            	    order.setTotalAmount(resultSet.getDouble("TotalAmount"));
            	    order.setStatus(resultSet.getString("Status"));
            	    int customerId1 = resultSet.getInt("CustomerID");
            	    CustomerDao customerDao=new CustomerDao();
            	    Customer customer = customerDao.getCustomerById(customerId1);
            	    order.setCustomer(customer);
            	    orders.add(order);
            	}

            }

        } catch (SQLException e) {
           
            e.printStackTrace();
        }

        return orders;
    }
    public Customer getCustomerById(int customerId) {
	    Customer customer = null;
	
	    try (Connection connection = DBConnUtil.getMyDBConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE CustomerID = ?")) {
	
	        preparedStatement.setInt(1, customerId);
	
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                customer = new Customer();
	                customer.setCustomerID(resultSet.getInt("CustomerID"));
	                customer.setFirstName(resultSet.getString("FirstName"));
	                customer.setLastName(resultSet.getString("LastName"));
	                customer.setEmail(resultSet.getString("Email"));
	                customer.setPhone(resultSet.getString("Phone"));
	                customer.setAddress(resultSet.getString("Address"));
	            }
	        }
	
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	    }
	
	    return customer;
	}
}

