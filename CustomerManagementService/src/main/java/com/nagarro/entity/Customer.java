package com.nagarro.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;

@Entity
public class Customer {

     @Id
     private int customerId;
	 private String name;
     private String email;
     private long phoneNumber;
      
	public Customer() {
	
	}
	
	
	public Customer(int customerId, String name, String email, long phoneNumber) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}


	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
     
     
     

}	
