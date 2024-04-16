package com.nagarro.service;

import java.util.List;

import com.nagarro.entity.Customer;

public interface CustomerService {

    Customer addCustomer(Customer customer);    // Add customer

    List<Customer> getAllCustomers();   // Get all Customers

    Customer getCustomerById(int customerId);    // Get single Customer Details

    Customer updateCustomer(int customerId, Customer customer);   //Update Customer Details

	void deleteCustomer(int customerId);     //Delete Customer
}


