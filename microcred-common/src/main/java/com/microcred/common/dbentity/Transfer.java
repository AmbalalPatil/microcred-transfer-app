package com.microcred.common.dbentity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity class for TRANSFER table. 
 * @author Ambalal Patil
 */
@Table(name = "TRANSFER")
@Entity
public class Transfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRANSFER_ID")
	private Long transferId;
	
	@JsonManagedReference
	@NotNull
	@ManyToOne
	@JoinColumn(name = "FROM_ACCOUNT_ID")
	private Account fromAccount;
	
	@JsonManagedReference
	@NotNull
	@ManyToOne
	@JoinColumn(name = "TO_ACCOUNT_ID")
	private Account toAccount;
	
	@NotNull
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Size(max = 50)
	@Column(name = "TRANSFER_NOTE")
	private String transferNote;
	
	@NotNull
	@Column(name = "TRANSFER_DATE")
	private OffsetDateTime transferDate;
	
	@NotNull
	@Column(name = "CREATE_DATE")
	private OffsetDateTime createDate;
	
	@Column(name = "UPDATE_DATE")
	private OffsetDateTime updateDate;

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransferNote() {
		return transferNote;
	}

	public void setTransferNote(String transferNote) {
		this.transferNote = transferNote;
	}

	public OffsetDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(OffsetDateTime createDate) {
		this.createDate = createDate;
	}

	public OffsetDateTime getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(OffsetDateTime transferDate) {
		this.transferDate = transferDate;
	}

	public OffsetDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(OffsetDateTime updateDate) {
		this.updateDate = updateDate;
	}
	
}
