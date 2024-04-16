package com.nagarro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nagarro.Exception.AccountNotFoundException;
import com.nagarro.Exception.CustomerNotFoundException;
import com.nagarro.Exception.InsufficientBalanceException;
import com.nagarro.entity.Account;
import com.nagarro.service.AccountService;
@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    
    @PostMapping("/addAccount")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        try {
            Account updatedaccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedaccount);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    
    
    @PostMapping("/addMoney/{customerId}/{amount}")
    public ResponseEntity<?> addMoneyToAccount(@PathVariable int customerId, @PathVariable double amount) {
        try {
            Account updatedAccount = accountService.addMoneyToAccount(customerId, amount);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedAccount);
        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add money to account: " + ex.getMessage());
        }
    }

    @PostMapping("/withdrawMoney/{customerId}/{amount}")
    public ResponseEntity<?> withdrawMoneyFromAccount(@PathVariable int customerId, @PathVariable double amount) {
        try {
            Account updatedAccount = accountService.withdrawMoneyFromAccount(customerId, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InsufficientBalanceException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to withdraw money from account: " + ex.getMessage());
        }
    }

    @GetMapping("/getAccountDetails/{accountId}")
    public ResponseEntity<?> getAccountDetails(@PathVariable int accountId) {
        Account account = accountService.getAccountDetails(accountId);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found with ID: " + accountId);
        }
    }

    @DeleteMapping("/deleteAccount/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable int accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("Account deleted successfully!");
    }
    
    @DeleteMapping("/deleteByCustomerId/{customerId}")
    public ResponseEntity<String> deleteAccountByCustomerId(@PathVariable int customerId) {
        accountService.deleteAccountByCustomerId(customerId);
        return ResponseEntity.ok("Account deleted...");
    }
    
}
