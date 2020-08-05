package com.microcred.validate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.exception.TransferException;
import com.microcred.common.request.TransferRequestTO;

public class ValidateTransferServiceTest {

	private static final long TO_ACCOUNT_ID = 102L;
	private static final long FROM_ACCOUNT_ID = 101L;
	private static final String TRANSFER_NOTE = "Test transfer";
	
	private ValidateTransferService service = new ValidateTransferService();
	
	@Test
	public void testValidateTransfer_happyPath() {
		try {
			service.validateTransfer(getMockTransferRequest());
		} catch(TransferException transferException) {
			fail("Happy path test case, Exception is not expected.", transferException);
		}
	}
	
	@Test
	public void testValidateTransfer_requestIsNull() {
		try {
			service.validateTransfer(null);
			fail("Exception is expected for null transfer amount.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_REQUEST_IS_NULL, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testValidateTransfer_fromAccountIdIsNull() {
		try {
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setFromAcctId(null);
			service.validateTransfer(requestTO);
			fail("Exception is expected for missing from account id.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_MANDATORY_PARAM_MISSING, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testValidateTransfer_toAccountIdIsNull() {
		try {
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setToAcctId(null);
			service.validateTransfer(requestTO);
			fail("Exception is expected for missing to account id.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_MANDATORY_PARAM_MISSING, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testValidateTransfer_amountIsNull() {
		try {
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setAmount(null);
			service.validateTransfer(requestTO);
			fail("Exception is expected for null amount.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_MANDATORY_PARAM_MISSING, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testValidateTransfer_transferDateIsNull() {
		try {
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setTransferDate(null);
			service.validateTransfer(requestTO);
			fail("Exception is expected for null transfer date.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_MANDATORY_PARAM_MISSING, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testValidateTransfer_amountIsZero() {
		try {
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setAmount(BigDecimal.ZERO);
			service.validateTransfer(requestTO);
			fail("Exception is expected for transfer amount zero.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_INVALID_TRANSFER_AMOUNT, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testValidateTransfer_transferDateIsOld() {
		try {
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setTransferDate(OffsetDateTime.now().minusDays(1));
			service.validateTransfer(requestTO);
			fail("Exception is expected for old transfer date.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_INVALID_TRANSFER_DATE, transferException.getErrorCode());
		}
	}
	
		
	private TransferRequestTO getMockTransferRequest() {
		TransferRequestTO requestTO = new TransferRequestTO();
		requestTO.setFromAcctId(FROM_ACCOUNT_ID);
	
		requestTO.setToAcctId(TO_ACCOUNT_ID);
		requestTO.setAmount(BigDecimal.TEN);
		requestTO.setTransferDate(OffsetDateTime.now());
		requestTO.setTransferNote(TRANSFER_NOTE);
		return requestTO;
	}

}
