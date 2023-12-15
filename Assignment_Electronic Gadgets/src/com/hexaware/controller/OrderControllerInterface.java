package com.hexaware.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.hexaware.entity.Orders;
import com.hexaware.exceptions.InvalidOrderException;
import com.hexaware.exceptions.InvalidProductException;

public interface OrderControllerInterface {
    public void addOrder() throws IOException, SQLException, InvalidOrderException, InvalidProductException ;
    public void updateOrderStatus() throws IOException;
    public void cancelOrder(int orderId) throws InvalidProductException;
    public List<Orders> getOrdersByDateRange(String startDate, String endDate);
    public void getOrdersForCustomer(int customerId);
}
