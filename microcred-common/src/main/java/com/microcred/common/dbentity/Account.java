package com.microcred.common.dbentity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity class for ACCOUNT table. 
 * @author Ambalal Patil
 */
@Table(name = "ACCOUNT")
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
	@Column(name = "ACCOUNT_NUMBER")
	private Long accountNumber;
	
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;
	
	@Column(name = "BALANCE")
	private BigDecimal balance;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.DETACH,
			mappedBy = "fromAccount")
	private List<Transfer> debitTransactions;
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.DETACH,
			mappedBy = "toAccount")
	private List<Transfer> creditTransactions;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transfer> getDebitTransactions() {
		return debitTransactions;
	}

	public void setDebitTransactions(List<Transfer> debitTransactions) {
		this.debitTransactions = debitTransactions;
	}

	public List<Transfer> getCreditTransactions() {
		return creditTransactions;
	}

	public void setCreditTransactions(List<Transfer> creditTransactions) {
		this.creditTransactions = creditTransactions;
	}

}
