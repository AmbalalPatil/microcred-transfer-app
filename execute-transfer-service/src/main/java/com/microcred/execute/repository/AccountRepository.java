package com.microcred.execute.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microcred.common.dbentity.Account;

/**
 * Performs CRUD operations on ACCOUNT table. 
 * @author Ambalal Patil
 */
public interface AccountRepository extends JpaRepository<Account, Long>{

}
