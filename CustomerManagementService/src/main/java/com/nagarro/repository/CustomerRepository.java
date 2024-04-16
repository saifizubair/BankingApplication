package com.nagarro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
}
