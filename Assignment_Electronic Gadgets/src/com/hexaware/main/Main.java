package com.hexaware.main;
import java.util.Scanner;
import com.hexaware.controller.*;
import com.hexaware.dao.*;
import com.hexaware.exceptions.*;

public class Main {

    public static void main(String[] args) throws InvalidProductException {
    	Scanner scanner = null;
        try {
	    	scanner = new Scanner(System.in);
	
	        CustomerDao customerDao = new CustomerDao();
	        InventoryDao inventoryDao = new InventoryDao();
	        OrderDao orderDao = new OrderDao();
	        
	        CustomerControllerInterface customerController = new CustomerController(customerDao);
	        InventoryControllerInterface inventoryController = new InventoryController(inventoryDao);
	        OrderControllerInterface orderController = new OrderController(orderDao);
	        SalesControllerInterface salesController=new SalesController();
	        ProductControllerInterface productController=new ProductController();
	        
	        System.out.println("1.Customer\n2.Developer");        
	        int sw=scanner.nextInt();
	        if(sw==1) {
	        	while (true) {
	                System.out.println("Choose an option:");
	                System.out.println("1. Register Customer");
	                System.out.println("2. Update Customer Details");
	                System.out.println("3. Tracking Order Status");
	                System.out.println("4. List All Products");
	                System.out.println("5. Place Order");
	                System.out.println("6. Cancel Order");
	                System.out.println("7. List Orders for a Customer");
	                System.out.println("0. Exit");
	
	                int choice = scanner.nextInt();
	
	                switch (choice) {
	                    case 1:
	                        // Register a new customer
	                        try {
	                            customerController.registerCustomer();
	                            System.out.println("Customer registered successfully!");
	                        } catch (InvalidDataException e) {
	                            System.out.println("Error: " + e.getMessage());
	                        }
	                        break;
	
	                    case 2:
	                        // Update customer details
	                        try {
	                            customerController.updateCustomerDetails();
	                            System.out.println("Customer details updated successfully!");
	                        } catch (InvalidDataException e) {
	                            System.out.println("Error: " + e.getMessage());
	                        }
	                        break;
	
	                    case 3:
	                        // Tracking Order Status
	                        System.out.println("Enter Order ID:");
	                        int orderId = scanner.nextInt();
	                        String status = orderDao.getOrderById(orderId).getStatus();
	                        System.out.println("Order Status: " + status);
	                        break;

	
	                    case 4:
	                    	System.out.println("All Products: ");
	                    	inventoryController.getAllInventory();
	                        break;
	
	                    case 5:
	                        // Place a new order
	                        try {
	                            orderController.addOrder();
	                            System.out.println("Order placed successfully!");
	                        } catch (InvalidDataException e) {
	                            System.out.println("Error: " + e.getMessage());
	                        }
	                        break;
	                    case 6:
	                    	//cancel order
	                    	System.out.println("Enter orderId");
	                    	int orderId1=scanner.nextInt();
	                    	orderController.cancelOrder(orderId1);
	                    	break;
	                    case 7:
	                        // Get orders for a specific customer
	                        System.out.println("Enter Customer ID:");
	                        int customerId = scanner.nextInt();
	                        System.out.println("Customer Orders: ");
	                        orderController.getOrdersForCustomer(customerId);
	                        
	                        break;
	
	                    case 0:
	                        // Exit the program
	                        System.out.println("Exiting the application. Goodbye!");
	                        System.exit(0);
	                        break;
	
	                    default:
	                        System.out.println("Invalid choice. Please choose a valid option.");
	                        break;
	                }
	            }
            }
	        else if(sw==2) {
	        	String username="";
	        	String password="";
	        	System.out.println("Enter User name");
	        	username=scanner.next();
	        	System.out.println("Enter password");
	        	password=scanner.next();	
	        	if(!username.equals("Rohith")||!password.equals("Rohith@02")) {
	        		throw new AuthorizationException("Username and password does not match!!!");
	        	}
	        	System.out.println("Login Success!!");
	        	while (true) {
	                System.out.println("Choose an option:");
	                System.out.println("1. List All Customers");
	                System.out.println("2. Product Catalog management");
	                System.out.println("3. Inventory Management");
	                System.out.println("4. Update Order Status");
	                System.out.println("5. Sales Reporting");                
	                System.out.println("0. Exit");
	
	                int choice = scanner.nextInt();
	
	                switch (choice) {
	                    case 1:
	                    	System.out.println("All Customers: " );
	                    	customerController.getAllCustomers();     
	                        break;
	
	                    case 2:
	                        // Product Catalog Management
	                        try {
	                            System.out.println("Choose a product management option:");
	                            System.out.println("1. Add Product");
	                            System.out.println("2. Remove Product");
	                            System.out.println("3. Update Product");
	                            int productOption = scanner.nextInt();
	                            switch (productOption) {
	                                case 1:
	                                    productController.addProduct();
	                                    System.out.println("Product added successfully!");
	                                    break;

	                                case 2:
	                                    productController.removeProduct();
	                                    System.out.println("Product removed successfully!");
	                                    break;

	                                case 3:
	                                    productController.updateProduct();
	                                    System.out.println("Product updated successfully!");
	                                    break;

	                                default:
	                                    System.out.println("Invalid product management option. Please choose a valid option.");
	                                    break;
	                            }
	                        } catch (InvalidDataException e) {
	                            System.out.println("Error: " + e.getMessage());
	                        }
	                        break;
	
	                    case 3:
	                        // Inventory Management
	                        try {
	                        	// Update Product Quantity
	                            System.out.println("2. Update Product Quantity");
                                inventoryController.updateProductQuantity();
                                System.out.println("Product quantity updated successfully!");
                                break;
	                                
	                            
	                        } catch (InvalidDataException e) {
	                            System.out.println("Error: " + e.getMessage());
	                        }
	                        break;
	                        
	                    case 4:
	                    	orderController.updateOrderStatus();
	                    	break;
	                    	
	                    case 5:	                    	
	                    	salesController.getReport();
	                        break;
	                        
	                    case 0:
	                        // Exit the program
	                        System.out.println("Exiting the application. Goodbye!");
	                        System.exit(0);
	                        break;
	
	                    default:
	                        System.out.println("Invalid choice. Please choose a valid option.");
	                        break;
	                }
	            }
	        }else {
	        	System.out.println("invalid choice please choose a valid option");
	        }
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        }finally {
        	scanner.close();
        }
        
        
    }
}


