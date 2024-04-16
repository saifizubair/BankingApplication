package com.nagarro.service;

import com.nagarro.entity.Account;

public interface AccountService {

    Account withdrawMoneyFromAccount(int customerId, double amount);

    Account getAccountDetails(int accountId);

    void deleteAccount(int accountId);
    void deleteAccountByCustomerId(int customerId);

	Account addMoneyToAccount(int customerId, double amount);

	Account createAccount(Account account);
}
