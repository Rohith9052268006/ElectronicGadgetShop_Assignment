package com.hexaware.controller;

import java.sql.SQLException;
import java.util.List;

import com.hexaware.entity.OrderDetail;

public interface OrderDetailControllerInterface {
	public void addOrderDetail(OrderDetail orderDetail) throws SQLException;
	public void updateOrderDetail(OrderDetail orderDetail) throws SQLException;
	public void removeOrderDetail(int orderDetailID) throws SQLException ;
    public List<OrderDetail> getAllOrderDetails() throws SQLException;
}
