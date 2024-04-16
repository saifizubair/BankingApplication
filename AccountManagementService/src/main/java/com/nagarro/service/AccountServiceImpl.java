package com.nagarro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.nagarro.Exception.AccountNotFoundException;
import com.nagarro.Exception.CustomerNotFoundException;
import com.nagarro.Exception.InsufficientBalanceException;
import com.nagarro.entity.Account;
import com.nagarro.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    private static final String CMS_BASE_URL = "http://localhost:8088/customer/getCustomer/";

   
    

    @Override
    public Account addMoneyToAccount(int customerId, double amount) {
        ResponseEntity<Object> response;
        try {
            response = restTemplate.getForEntity(CMS_BASE_URL + customerId, Object.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } catch (HttpServerErrorException.InternalServerError ex) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to fetch customer details", ex);
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            Account account = accountRepository.findByCustomerId(customerId);
            if (account != null) {
                account.setBalance(account.getBalance() + amount);
                return accountRepository.save(account);
            } else {
                throw new AccountNotFoundException("Account not found for customer id: " + customerId);
            }
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } else {
            throw new RuntimeException("Failed to fetch customer details");
        }
    }

    @Override
    public Account withdrawMoneyFromAccount(int customerId, double amount) {
        ResponseEntity<Object> response;
        try {
            response = restTemplate.getForEntity(CMS_BASE_URL + customerId, Object.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } catch (HttpServerErrorException.InternalServerError ex) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to fetch customer details", ex);
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            Account account = accountRepository.findByCustomerId(customerId);
            if (account != null) {
                double newBalance = account.getBalance() - amount;
                if (newBalance >= 0) {
                    account.setBalance(newBalance);
                    return accountRepository.save(account);
                } else {
                    throw new InsufficientBalanceException("Insufficient balance");
                }
            } else {
                throw new AccountNotFoundException("Account not found for customer id: " + customerId);
            }
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } else {
            throw new RuntimeException("Failed to fetch customer details");
        }
    }

    @Override
    public Account getAccountDetails(int accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public void deleteAccount(int accountId) {
        // Implement logic to delete account
        accountRepository.deleteById(accountId);
    }

    @Override
    public void deleteAccountByCustomerId(int customerId) {
        // Implement logic to delete account by customer ID
        Account account = accountRepository.findByCustomerId(customerId);
        if (account != null) {
            accountRepository.delete(account);
        } else {
            throw new AccountNotFoundException("Account not found for customer id: " + customerId);
        }
    }
    
    @Override
    public Account createAccount(Account account) {
        int customerId = account.getCustomerId(); // Assuming customer ID is present in the Account object
        
        // Check if the customer already has an account
        Account existingAccount = accountRepository.findByCustomerId(customerId);
        if (existingAccount != null) {
            throw new RuntimeException("Customer already has an account.");
        }

        // Fetch customer details from CMS
        ResponseEntity<Object> response;
        try {
            response = restTemplate.getForEntity(CMS_BASE_URL + customerId, Object.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } catch (HttpServerErrorException.InternalServerError ex) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to fetch customer details", ex);
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            // Customer exists, create the account
            return accountRepository.save(account);
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            // Customer not found, throw an exception
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        } else {
            // Error fetching customer details, throw a generic exception
            throw new RuntimeException("Failed to fetch customer details");
        }
    }


}
