package com.hexaware.controller;
import com.hexaware.dao.CustomerDao;
import com.hexaware.entity.Customer;
import com.hexaware.exceptions.InvalidDataException;
import java.util.Scanner;
import java.io.*;
public class CustomerController implements CustomerControllerInterface {
    private CustomerDao customerDao;
    Scanner scanner=null;
    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void registerCustomer() throws InvalidDataException, NumberFormatException, IOException {
        Customer customer=new Customer();
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter first name");
        String firstName=br.readLine();
        System.out.println("Enter last name");
        String lastName=br.readLine();
        System.out.println("Enter the email");
        String email =br.readLine();
        System.out.println("phone");
        String phoneNo=br.readLine();
        System.out.println("Enter the address");
        String address=br.readLine();
    
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phoneNo);
        customer.setEmail(email);
        customer.setAddress(address);
        
    	validateCustomerData(customer);
        customerDao.addCustomer(customer);
    }

    public void updateCustomerDetails() {
        try {
        	scanner=new Scanner(System.in);
            System.out.println("Enter Customer ID:");
            int customerID = scanner.nextInt();
            Customer existingCustomer = customerDao.getCustomerById(customerID);

            if (existingCustomer != null) {
                System.out.println("Current Customer Details:");
                existingCustomer.GetCustomerDetails();
                System.out.println("Choose the detail to update:");
                System.out.println("1. First Name");
                System.out.println("2. Last Name");
                System.out.println("3. Email");
                System.out.println("4. Phone");
                System.out.println("5. Address");

                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.println("Enter new First Name:");
                        String newFirstName = scanner.nextLine();
                        existingCustomer.setFirstName(newFirstName);
                        break;

                    case 2:
                        System.out.println("Enter new Last Name:");
                        String newLastName = scanner.nextLine();
                        existingCustomer.setLastName(newLastName);
                        break;

                    case 3:
                        System.out.println("Enter new Email:");
                        String newEmail = scanner.nextLine();
                        existingCustomer.setEmail(newEmail);
                        break;

                    case 4:
                        System.out.println("Enter new Phone:");
                        String newPhone = scanner.nextLine();
                        existingCustomer.setPhone(newPhone);
                        break;

                    case 5:
                        System.out.println("Enter new Address:");
                        String newAddress = scanner.nextLine();
                        existingCustomer.setAddress(newAddress);
                        break;

                    default:
                        System.out.println("Invalid choice. No changes made.");
                        return;
                }
                customerDao.updateCustomer(existingCustomer);
                System.out.println("Customer details updated successfully!");
            } else {
                System.out.println("Customer with ID " + customerID + " not found.");
            }
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
        	scanner.close();
        }
    }
    public void getAllCustomers() {
        customerDao.getAllCustomers();
    }

    private void validateCustomerData(Customer customer) throws InvalidDataException {
        if (customer.getEmail() == null || !customer.getEmail().matches("^[\\w.-]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidDataException("Invalid email address.");
        }
    }
}
