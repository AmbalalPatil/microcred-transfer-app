package com.microcred.composite.model;

import java.math.BigDecimal;

/**
 * Class to hold transfer request data
 * @author Ambalal Patil
 */
public class TransferDataRequest {

	private Long transferId;
	private String fromAcctId;
	private String toAcctId;
	private BigDecimal amount;
	private String transferNote;
	public Long getTransferId() {
		return transferId;
	}
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
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
	public String getTransferNote() {
		return transferNote;
	}
	public void setTransferNote(String transferNote) {
		this.transferNote = transferNote;
	}
	
}
