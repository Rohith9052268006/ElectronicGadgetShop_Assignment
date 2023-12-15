package com.hexaware.controller;


import com.hexaware.dao.CustomerDao;
import com.hexaware.dao.InventoryDao;
import com.hexaware.dao.OrderDao;
import com.hexaware.dao.OrderDetailDao;
import com.hexaware.dao.ProductDao;
import com.hexaware.entity.Inventory;
import com.hexaware.entity.OrderDetail;
import com.hexaware.entity.Orders;
import com.hexaware.entity.Product;
import com.hexaware.entity.Sale;
import com.hexaware.exceptions.InsufficientStockException;
import com.hexaware.exceptions.InvalidOrderException;
import com.hexaware.exceptions.InvalidProductException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.*;

public class OrderController implements OrderControllerInterface {
	public static int orderDetailId=0;
    private OrderDao orderDao ;
    InventoryDao inventoryDao = new InventoryDao();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public OrderController() {
    	
    }
    public OrderController(OrderDao orderDao){
    	this.orderDao=orderDao;
    }
    public void addOrder() throws IOException, SQLException, InvalidOrderException, InvalidProductException {
        Orders orders = new Orders();
        OrderDao orderDao = new OrderDao();
        CustomerDao customerDao = new CustomerDao();
        ProductDao productDao = new ProductDao();
        SalesController salesController=new SalesController();
        Sale sale=null;
    
        System.out.println("Enter orderId:");
        int orderid=Integer.parseInt(br.readLine());
        orders.setOrderID(orderid);
        
        System.out.println("Enter customerid");
        orders.setCustomer(customerDao.getCustomerById(Integer.parseInt(br.readLine())));
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String orderDate = currentDateTime.format(formatter);
        orders.setOrderDate(orderDate);
        orders.setStatus("ordered");

        System.out.println("Enter product id");
        int productId = Integer.parseInt(br.readLine());
        Inventory productInventory = inventoryDao.getInventoryById(productId);
        if (productInventory.getQuantityInStock() > 0) {       	
        	System.out.println("Enter quantity");
            int quantity = Integer.parseInt(br.readLine());
            if (quantity <= productInventory.getQuantityInStock()) {
                OrderDetail orderDetail = new OrderDetail(orderid, orders, productDao.getProductById(productId), quantity);
                OrderDetailController orderDetailController = new OrderDetailController();
                
                Product product=productDao.getProductById(productId);
                double totalAmount=product.getPrice()*quantity;
                orders.setTotalAmount(totalAmount);
                System.out.println("Total Amount : "+totalAmount);
                System.out.println("Do you want to continue : Y/N");
                String choice=br.readLine().toLowerCase();
                if(choice.equals("y")) {
                	orderDao.addOrder(orders);
	                orderDetailController.addOrderDetail(orderDetail);
	                inventoryDao.updateStockQuantityOrdered(productId, quantity);
	                sale=new Sale(orderid,productId,totalAmount,orderDate);
	                salesController.addToSalesReport(sale);
	                System.out.println("Order placed successfully!");
                }
                else {
                	return;
                }
            } else {
                throw new InsufficientStockException("Insufficient stock for the requested quantity.");
            }
        } else {
            throw new InvalidProductException("Product not found in stock.");
        }
    }


    public void updateOrderStatus() throws IOException{
        try {
        	System.out.println("Enter Order Id");
        	int orderId=Integer.parseInt(br.readLine());
        
        	System.out.println("Enter status");
        	String newStatus=br.readLine();
            orderDao.updateOrderStatus(orderId, newStatus);
        } catch (SQLException | InvalidOrderException e) {
            e.printStackTrace(); 
        }
    }

    public void cancelOrder(int orderId) throws InvalidProductException {
        try {
            OrderDetailController orderDetailController = new OrderDetailController();
            OrderDetailDao orderDetailDao = new OrderDetailDao();

            int orderDetailId = orderDetailDao.getOrderDetailsByOrderId(orderId);
            if (orderDetailId > 0) {
                OrderDetail orderDetail = orderDetailDao.getOrderDetailById(orderDetailId);

                if (orderDetail != null) {
                    inventoryDao.updateStockQuantityCancelled(orderDetail.getProduct().getProductID(), orderDetail.getQuantity());
                    orderDetailController.removeOrderDetail(orderDetailId);
                    orderDao.cancelOrder(orderId);
                    System.out.println("Order cancelled successfully!");
                } else {
                    System.out.println("Order details not found for ID: " + orderDetailId);
                }
            } else {
                System.out.println("No order details found for order ID: " + orderId);
            }
        } catch (SQLException | InvalidOrderException e) {
            e.printStackTrace();
        }
    }



    public List<Orders> getOrdersByDateRange(String startDate, String endDate) {
        try {
            return orderDao.getOrdersByDateRange(startDate, endDate);
        } catch (SQLException e) {
            e.printStackTrace(); 
            return null;
        }
    }
    public void getOrdersForCustomer(int customerId) {
        try {
            orderDao.getOrdersForCustomer(customerId);
        } catch (SQLException e) {
            e.printStackTrace(); 
            
        }
    }
	
	
}
