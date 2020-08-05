package com.microcred.composite.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Class to hold transfer request data
 * @author Ambalal Patil
 */
public class TransferDataRequest {

	private String fromAcctId;
	private String toAcctId;
	private BigDecimal amount;
	private OffsetDateTime transferDate;
	private String transferNote;
	
	public String getFromAcctId() {
		return fromAcctId;
	}
	public void setFromAcctId(String fromAcctId) {
		this.fromAcctId = fromAcctId;
	}
	public String getToAcctId() {
		return toAcctId;
	}
	public void setToAcctId(String toAcctId) {
		this.toAcctId = toAcctId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public OffsetDateTime getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(OffsetDateTime transferDate) {
		this.transferDate = transferDate;
	}
	public String getTransferNote() {
		return transferNote;
	}
	public void setTransferNote(String transferNote) {
		this.transferNote = transferNote;
	}
	
}
