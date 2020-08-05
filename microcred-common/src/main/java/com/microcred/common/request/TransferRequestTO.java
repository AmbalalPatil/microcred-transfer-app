package com.microcred.common.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Class to hold transfer request data
 * @author Ambalal Patil
 */
public class TransferRequestTO {

	private Long fromAcctId;
	private Long toAcctId;
	private BigDecimal amount;
	private String transferNote;
	private OffsetDateTime transferDate;
	
	public Long getFromAcctId() {
		return fromAcctId;
	}
	public void setFromAcctId(Long fromAcctId) {
		this.fromAcctId = fromAcctId;
	}
	public Long getToAcctId() {
		return toAcctId;
	}
	public void setToAcctId(Long toAcctId) {
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
	public OffsetDateTime getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(OffsetDateTime transferDate) {
		this.transferDate = transferDate;
	}
	@Override
	public String toString() {
		return "TransferRequestTO [fromAcctId=" + fromAcctId + ", toAcctId=" + toAcctId + ", amount=" + amount
				+ ", transferNote=" + transferNote + ", transferDate=" + transferDate + "]";
	}
}