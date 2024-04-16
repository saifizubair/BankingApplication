package com.nagarro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.nagarro.Exception.CustomerNotFoundException;
import com.nagarro.entity.Customer;
import com.nagarro.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    private static final String ACCOUNT_SERVICE_BASE_URL = "http://localhost:8080/account/deleteByCustomerId/";

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        // Add customer 
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        // Get all customers 
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(int customerId) {
        // Get customer by ID 
        return customerRepository.findById(customerId)
                                 .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
    }

    @Override
    public Customer updateCustomer(int customerId, Customer customer) {
        // Update the customer
        customer.setCustomerId(customerId);
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int customerId) {
        // Delete customer 
        Customer customerToDelete = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        
        // Fetch the account details by using RestTemplate
        try {
            restTemplate.delete(ACCOUNT_SERVICE_BASE_URL + customerId);
        } catch (HttpServerErrorException ex) {
            // If internal server error occurred in account service, handle it accordingly
            throw new RuntimeException("Error occurred while deleting account for customer with id: " + customerId, ex);
        } catch (Exception ex) {
            // Handle other exceptions if necessary
            throw new RuntimeException("Error occurred while deleting account for customer with id: " + customerId, ex);
        }

        // After successfully deleting the account, proceed to delete the customer
        customerRepository.delete(customerToDelete);
    }

}
