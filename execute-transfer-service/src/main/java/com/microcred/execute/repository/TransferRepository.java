package com.microcred.execute.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microcred.common.dbentity.Transfer;

/**
 * Performs CRUD operations on TRANSFER table. 
 * @author Ambalal Patil
 */
public interface TransferRepository extends JpaRepository<Transfer, Long>{
	
	@Query(value= "SELECT * FROM TRANSFER WHERE (FROM_ACCOUNT_ID IN (:accountIdList) or TO_ACCOUNT_ID in (:accountIdList)) AND TRANSFER_DATE > :transferDate", nativeQuery=true)
	public List<Transfer> findByFromAndToAccountId(@Param("accountIdList") List<Long> accountIdList, @Param("transferDate") OffsetDateTime offsetDateTime);
}