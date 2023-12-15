package com.hexaware.controller;

import java.sql.SQLException;
import java.util.List;

import com.hexaware.dao.OrderDetailDao;
import com.hexaware.entity.OrderDetail;

public class OrderDetailController implements OrderDetailControllerInterface {
    private OrderDetailDao orderDetailDao;

    public OrderDetailController() {
        this.orderDetailDao = new OrderDetailDao();
    }

    public void addOrderDetail(OrderDetail orderDetail) throws SQLException {
        orderDetailDao.addOrderDetail(orderDetail);
    }

    public void updateOrderDetail(OrderDetail orderDetail) throws SQLException {
        orderDetailDao.updateOrderDetail(orderDetail);
    }

    public void removeOrderDetail(int orderDetailID) throws SQLException {
        orderDetailDao.removeOrderDetail(orderDetailID);
    }

    public List<OrderDetail> getAllOrderDetails() throws SQLException {
        return orderDetailDao.getAllOrderDetails();
    }

}