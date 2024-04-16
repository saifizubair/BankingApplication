package com.nagarro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {

	@Id
    private int accountId;

    private String ifscCode;
    private String bankName;
    private int customerId;
    private double balance;
    
	public Account() {
		
	}

	public Account(int accountId, String ifscCode, String bankName, int customerId, double balance) {
		super();
		this.accountId = accountId;
		this.ifscCode = ifscCode;
		this.bankName = bankName;
		this.customerId = customerId;
		this.balance = balance;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
    
    
}