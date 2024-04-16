package com.nagarro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	

	Account findByCustomerId(int customerId);
    
}
	