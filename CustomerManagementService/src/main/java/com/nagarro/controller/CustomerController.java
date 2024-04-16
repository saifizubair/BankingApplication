package com.nagarro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nagarro.entity.Customer;
import com.nagarro.service.CustomerService;

@RestController
@RequestMapping("customer")
public class CustomerController {

	
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    
    @GetMapping("/getCustomer/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/updateCustomer/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int customerId, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Deleted successfully!");
        
    }
    
}
        		

