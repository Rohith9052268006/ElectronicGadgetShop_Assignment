package com.hexaware.dao;

import com.hexaware.entity.Customer;
import com.hexaware.entity.Orders;
import com.hexaware.exceptions.InvalidDataException;
import com.hexaware.exceptions.InvalidOrderException;
import com.hexaware.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderDao {
    public void addOrder(Orders orders) throws SQLException, InvalidOrderException {
        try (Connection connection = DBConnUtil.getMyDBConnection()) {
            String insertOrderQuery = "INSERT INTO Orders (orderID, Customerid, orderdate,TotalAmount, Status) VALUES (?,?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrderQuery)) {
                
                preparedStatement.setInt(1, orders.getOrderID());
            	preparedStatement.setInt(2, orders.getCustomer().getCustomerID());
                preparedStatement.setString(3, orders.getOrderDate());
                preparedStatement.setDouble(4, orders.getTotalAmount());
                preparedStatement.setString(5, orders.getStatus());
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Failed to add order, no rows affected.");
                }
            }
        }
    }

    public void updateOrderStatus(int orderId, String newStatus) throws SQLException, InvalidOrderException {
        try (Connection connection = DBConnUtil.getMyDBConnection()) {
            String updateOrderStatusQuery = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateOrderStatusQuery)) {
                
                preparedStatement.setString(1, newStatus);
                preparedStatement.setInt(2, orderId);

                
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 0) {
                    throw new InvalidOrderException("No order found with ID: " + orderId);
                }
            }
        }
    }

    public void cancelOrder(int orderId) throws SQLException, InvalidOrderException {
        try (Connection connection = DBConnUtil.getMyDBConnection()) {
            String cancelOrderQuery = "DELETE FROM Orders WHERE OrderID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(cancelOrderQuery)) {
                
                preparedStatement.setInt(1, orderId);

                
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 0) {
                    throw new InvalidOrderException("No order found with ID: " + orderId);
                }
            }
        }
    }
    public Customer getCustomerById(int customerId) throws SQLException {
        try (Connection connection = DBConnUtil.getMyDBConnection()) {
            String getCustomerByIdQuery = "SELECT * FROM Customer WHERE CustomerID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(getCustomerByIdQuery)) {
                preparedStatement.setInt(1, customerId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Customer customer = new Customer();
                        
                        customer.setCustomerID(resultSet.getInt("CustomerID"));
                        customer.setFirstName(resultSet.getString("FirstName"));
                        customer.setLastName(resultSet.getString("LastName"));
                        customer.setEmail(resultSet.getString("Email"));
                        customer.setPhone(resultSet.getString("Phone"));
                        customer.setAddress(resultSet.getString("Address"));
                        return customer;
                    }
                }
            }
        }
        return null; 
    }
    public List<Orders> getOrdersByDateRange(String startDate, String endDate) throws SQLException {
        List<Orders> orders = new ArrayList<>();

        try {
        	Connection connection = DBConnUtil.getMyDBConnection() ;
            String getOrdersByDateRangeQuery = "SELECT * FROM Orders WHERE OrderDate BETWEEN ? AND ?";
            try {
            		
            	PreparedStatement preparedStatement = connection.prepareStatement(getOrdersByDateRangeQuery);
                
                preparedStatement.setDate(1, Date.valueOf(startDate));
                preparedStatement.setDate(2, Date.valueOf(endDate));

              
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                	while (resultSet.next()) {
                	    Orders order = new Orders();
                	   
                	    order.setOrderID(resultSet.getInt("OrderID"));
                	    order.setCustomer(getCustomerById(resultSet.getInt("CustomerID")));
                	    order.setOrderDate(resultSet.getString("OrderDate"));
                	    order.setTotalAmount(resultSet.getDouble("TotalAmount"));
                	    order.setStatus(resultSet.getString("Status"));

                	    orders.add(order);
                	    Collections.sort(orders, new Comparator<Orders>() {
                            public int compare(Orders s1, Orders s2) {
                                return s1.getOrderDate().compareTo(s2.getOrderDate());
                            }
                        });;
                	}

                }catch(Exception e) {
                	System.out.println(e.getMessage());
                }
                
            }catch(Exception e) {
            	System.out.println(e.getMessage());
            }
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        return orders;
        
    }
    public void getOrdersForCustomer(int customerId) throws SQLException {
        List<Orders> orders = new ArrayList<>();

        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders WHERE CustomerID = ?")) {

            preparedStatement.setInt(1, customerId);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Orders order = new Orders();
                    order.setOrderID(resultSet.getInt("OrderID"));
                    order.setCustomer(getCustomerById(customerId));
                    order.setOrderDate(resultSet.getString("orderdate"));
                    order.setStatus(resultSet.getString("status"));
                    order.setTotalAmount(resultSet.getDouble("totalamount"));

                    orders.add(order);
                }
            }

        }

        for(int i=0;i<orders.size();i++) {
        	orders.get(i).GetOrderDetails();
        }
    }
    public Orders getOrderById(int orderId) {
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders WHERE OrderID = ?")) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Orders orders = new Orders();
                    orders.setOrderID(resultSet.getInt("OrderID"));
                    orders.setOrderDate(resultSet.getString("OrderDate"));
                    orders.setTotalAmount(resultSet.getDouble("TotalAmount"));
                    orders.setStatus(resultSet.getString("Status"));               
                    int customerId = resultSet.getInt("CustomerID");
                    CustomerDao customerDao=new CustomerDao();
                    Customer customer = customerDao.getCustomerById(customerId);
                    orders.setCustomer(customer);

                    return orders;
                }
            }

        } catch (SQLException e) {
            throw new InvalidDataException("Order not found");
        }

        return null;
    }
    
}