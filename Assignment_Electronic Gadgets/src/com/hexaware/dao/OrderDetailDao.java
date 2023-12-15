package com.hexaware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.entity.OrderDetail;
import com.hexaware.exceptions.InvalidProductException;
import com.hexaware.util.DBConnUtil;

public class OrderDetailDao {
    

    public void addOrderDetail(OrderDetail orderDetail) throws SQLException {
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO OrderDetail (orderDetailID, orderID, productID, quantity) VALUES (?, ?, ?, ?)")) {

            statement.setInt(1, orderDetail.getOrderDetailID());
            statement.setInt(2, orderDetail.getOrder().getOrderID());
            statement.setInt(3, orderDetail.getProduct().getProductID());
            statement.setInt(4, orderDetail.getQuantity());

            statement.executeUpdate();
        }
    }

    public void updateOrderDetail(OrderDetail orderDetail) throws SQLException {
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE OrderDetail SET orderID=?, productID=?, quantity=? WHERE orderDetailID=?")) {

            statement.setInt(1, orderDetail.getOrder().getOrderID());
            statement.setInt(2, orderDetail.getProduct().getProductID());
            statement.setInt(3, orderDetail.getQuantity());
            statement.setInt(4, orderDetail.getOrderDetailID());

            statement.executeUpdate();
        }
    }

    public void removeOrderDetail(int orderDetailID) throws SQLException {
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM OrderDetail WHERE orderDetailID=?")) {

            statement.setInt(1, orderDetailID);
            statement.executeUpdate();
        }
    }

    public List<OrderDetail> getAllOrderDetails() throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();

        try (Connection connection = DBConnUtil.getMyDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM OrderDetail")) {

            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailID(resultSet.getInt("orderDetailID"));
                orderDetail.setQuantity(resultSet.getInt("quantity"));

                orderDetails.add(orderDetail);
            }
        }

        return orderDetails;
    }
    public int getOrderDetailsByOrderId(int orderId) throws SQLException {
        int orderDetailId=0;
        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM OrderDetail WHERE OrderID = ?")) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderDetailId=resultSet.getInt("OrderDetailID");
                    
                }
            }
        }

        return orderDetailId;
    }

    public OrderDetail getOrderDetailById(int orderDetailId) throws SQLException, InvalidProductException {
        OrderDetail orderDetail = null;

        try (Connection connection = DBConnUtil.getMyDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM OrderDetail WHERE orderDetailID = ?")) {

            preparedStatement.setInt(1, orderDetailId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderDetail = new OrderDetail();
                    orderDetail.setOrderDetailID(resultSet.getInt("orderDetailID"));
                    orderDetail.setOrder(new OrderDao().getOrderById(resultSet.getInt("orderID")));
                    orderDetail.setProduct(new ProductDao().getProductById(resultSet.getInt("productID")));
                    orderDetail.setQuantity(resultSet.getInt("quantity"));
                }
            }
        }

        return orderDetail;
    }

    

}