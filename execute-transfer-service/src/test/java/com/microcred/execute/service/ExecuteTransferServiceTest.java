package com.microcred.execute.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import com.microcred.common.dbentity.Account;
import com.microcred.common.dbentity.Transfer;
import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.exception.TransferException;
import com.microcred.common.request.TransferRequestTO;
import com.microcred.execute.repository.AccountRepository;
import com.microcred.execute.repository.TransferRepository;

public class ExecuteTransferServiceTest {

	private static final long TRANSFER_ID = 10L;
	private static final String TRANSFER_NOTE = "Test transfer";
	private static final String ACCOUNT_TYPE = "CHECKING";
	private static final long TO_ACCOUNT_NUMBER = 987654L;
	private static final long TO_ACCOUNT_ID = 102L;
	private static final long FROM_ACCOUNT_NUMBER = 123456L;
	private static final long FROM_ACCOUNT_ID = 101L;
	
	@InjectMocks
	private ExecuteTransferService service;
	@Mock
	private TransferRepository transferRepository;
	@Mock
	private AccountRepository accountRepository;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testPerformTransfer_happyPath() {
		try {
			when(accountRepository.findById(FROM_ACCOUNT_ID)).thenReturn(Optional.of(getMockAccount(FROM_ACCOUNT_ID, FROM_ACCOUNT_NUMBER)));
			when(accountRepository.findById(TO_ACCOUNT_ID)).thenReturn(Optional.of(getMockAccount(TO_ACCOUNT_ID, TO_ACCOUNT_NUMBER)));
			when(transferRepository.save(any(Transfer.class))).thenReturn(getMockTransfer());
			
			Transfer savedTransfer = service.performTransfer(getMockTransferRequest());
			
			assertNotNull(savedTransfer);
			assertEquals(TRANSFER_ID, savedTransfer.getTransferId().longValue());
			assertEquals(BigDecimal.TEN, savedTransfer.getAmount());
			assertEquals(TRANSFER_NOTE, savedTransfer.getTransferNote());
			assertNotNull(savedTransfer.getTransferDate());
			assertNotNull(savedTransfer.getCreateDate());
			
			Account fromAccount = savedTransfer.getFromAccount();
			assertNotNull(fromAccount);
			assertEquals(FROM_ACCOUNT_ID, fromAccount.getAccountId().longValue());
			assertEquals(FROM_ACCOUNT_NUMBER, fromAccount.getAccountNumber().longValue());
			assertEquals(100L, fromAccount.getBalance().longValue());
			assertEquals(ACCOUNT_TYPE, fromAccount.getAccountType());
			
			Account toAccount = savedTransfer.getToAccount();
			assertNotNull(toAccount);
			assertEquals(TO_ACCOUNT_ID,  toAccount.getAccountId().longValue());
			assertEquals(TO_ACCOUNT_NUMBER, toAccount.getAccountNumber().longValue());
			assertEquals(100L, toAccount.getBalance().longValue());
			assertEquals(ACCOUNT_TYPE, toAccount.getAccountType());
			
		} catch(TransferException transferException) {
			fail("Happy path test case, Exception is not expected.", transferException);
		}
	}
	
	@Test
	public void testPerformTransfer_fromAccountNotFound() {
		try {
			when(accountRepository.findById(FROM_ACCOUNT_ID)).thenReturn(Optional.empty());
			service.performTransfer(getMockTransferRequest());
			
			fail("Exception is expected for from account not found case.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_FROM_ACCOUNT_NOT_FOUND, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testPerformTransfer_toAccountNotFound() {
		try {
			when(accountRepository.findById(FROM_ACCOUNT_ID)).thenReturn(Optional.of(getMockAccount(FROM_ACCOUNT_ID, FROM_ACCOUNT_NUMBER)));
			when(accountRepository.findById(TO_ACCOUNT_ID)).thenReturn(Optional.empty());
			service.performTransfer(getMockTransferRequest());
			
			fail("Exception is expected for to account not found case.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_TO_ACCOUNT_NOT_FOUND, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testPerformTransfer_insufficientFromAccountBalance() {
		try {
			when(accountRepository.findById(FROM_ACCOUNT_ID)).thenReturn(Optional.of(getMockAccount(FROM_ACCOUNT_ID, FROM_ACCOUNT_NUMBER)));
			when(accountRepository.findById(TO_ACCOUNT_ID)).thenReturn(Optional.of(getMockAccount(TO_ACCOUNT_ID, TO_ACCOUNT_NUMBER)));
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setAmount(new BigDecimal(1000L));
			service.performTransfer(requestTO);
			
			fail("Exception is expected for insufficient balance.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_INSUFFICIENT_FROM_ACCOUNT_BALANCE, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testPerformTransfer_DBInsertFailed() {
		try {
			when(accountRepository.findById(FROM_ACCOUNT_ID)).thenReturn(Optional.of(getMockAccount(FROM_ACCOUNT_ID, FROM_ACCOUNT_NUMBER)));
			when(accountRepository.findById(TO_ACCOUNT_ID)).thenReturn(Optional.of(getMockAccount(TO_ACCOUNT_ID, TO_ACCOUNT_NUMBER)));
			service.performTransfer(getMockTransferRequest());
			
			fail("Exception is expected for DB insert failure");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_DB_INSERT_FAILED, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testUpdateTransfer_happyPath() {
		try {
			when(transferRepository.findById(any())).thenReturn(Optional.of(getMockTransfer()));
			when(transferRepository.save(any(Transfer.class))).thenReturn(getMockTransfer());
			
			Transfer savedTransfer = service.updateTransfer(TRANSFER_ID, getMockTransferRequest());
			
			assertNotNull(savedTransfer);
			assertEquals(TRANSFER_ID, savedTransfer.getTransferId().longValue());
			
		} catch(TransferException transferException) {
			fail("Happy path test case, Exception is not expected.", transferException);
		}
	}
	
	@Test
	public void testUpdateTransfer_transferAmountIsNull() {
		try {
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setAmount(null);
			service.updateTransfer(TRANSFER_ID, requestTO);
			
			fail("Exception is expected for null transfer amount.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_INVALID_TRANSFER_AMOUNT, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testUpdateTransfer_transferAmountIsZero() {
		try {
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setAmount(BigDecimal.ZERO);
			service.updateTransfer(TRANSFER_ID, requestTO);
			
			fail("Exception is expected for zero transfer amount.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_INVALID_TRANSFER_AMOUNT, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testUpdateTransfer_transferNotFound() {
		try {
			when(transferRepository.findById(any())).thenReturn(Optional.empty());
			service.updateTransfer(TRANSFER_ID, getMockTransferRequest());
			
			fail("Exception is expected for transfer not found case.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_TRANSFER_NOT_FOUND, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testUpdateTransfer_insufficientFromAccountBalance() {
		try {
			when(transferRepository.findById(any())).thenReturn(Optional.of(getMockTransfer()));
			TransferRequestTO requestTO = getMockTransferRequest();
			requestTO.setAmount(new BigDecimal(1000L));
			service.updateTransfer(TRANSFER_ID, requestTO);
			
			fail("Exception is expected for insufficient balance.");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_INSUFFICIENT_FROM_ACCOUNT_BALANCE, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testUpdateTransfer_DBUpdateFailed() {
		try {
			when(transferRepository.findById(any())).thenReturn(Optional.of(getMockTransfer()));
			service.updateTransfer(TRANSFER_ID, getMockTransferRequest());
			
			fail("Exception is expected for DB insert failure");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_DB_UPDATE_FAILED, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testDeleteTransfer_happyPath() {
		try {
			when(transferRepository.findById(any())).thenReturn(Optional.of(getMockTransfer()));
			doNothing().when(transferRepository).delete(any(Transfer.class));
			
			service.deleteTransfer(TRANSFER_ID);
		} catch(TransferException transferException) {
			fail("Happy path test case, Exception is not expected.", transferException);
		}
	}
	
	@Test
	public void testDeleteTransfer_DBDeleteFailed() {
		try {
			when(transferRepository.findById(any())).thenReturn(Optional.empty());
			
			service.deleteTransfer(TRANSFER_ID);
			fail("Exception is expected for fetch transfer failure");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_TRANSFER_NOT_FOUND, transferException.getErrorCode());
		}
	}
	
	@Test
	public void testFindAccountTransfersByAccountIds_happyPath() {
		try {
			when(transferRepository.findByFromAndToAccountId(any(), any())).thenReturn(Arrays.asList(getMockTransfer()));
			
			List<Transfer> accountTransfers = service.findAccountTransfersByAccountIds(Arrays.asList(101L, 102L));
			assertFalse(CollectionUtils.isEmpty(accountTransfers));
			assertEquals(1, accountTransfers.size());
		} catch(TransferException transferException) {
			fail("Happy path test case, Exception is not expected.", transferException);
		}
	}
	
	@Test
	public void testFindAccountTransfersByAccountIds_DBFetchFailed() {
		try {
			when(transferRepository.findByFromAndToAccountId(any(), any())).thenReturn(new ArrayList<>());
			
			service.findAccountTransfersByAccountIds(Arrays.asList(101L, 102L));
			fail("Exception is expected for fetch account transfers failure");
		} catch(TransferException transferException) {
			assertNotNull(transferException);
			assertEquals(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_ACCOUNT_TRANSFERS_NOT_FOUND, transferException.getErrorCode());
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
	
	private Account getMockAccount(Long accountId, Long accountNumber) {
		Account account = new Account();
		account.setAccountId(accountId);
		account.setAccountNumber(accountNumber);
		account.setAccountType(ACCOUNT_TYPE);
		account.setBalance(new BigDecimal(100));
		return account;
	}
	
	private Transfer getMockTransfer() {
		Transfer transfer = new Transfer();
		transfer.setTransferId(TRANSFER_ID);
		transfer.setFromAccount(getMockAccount(FROM_ACCOUNT_ID, FROM_ACCOUNT_NUMBER));
		transfer.setToAccount(getMockAccount(TO_ACCOUNT_ID, TO_ACCOUNT_NUMBER));
		transfer.setAmount(BigDecimal.TEN);
		transfer.setTransferDate(OffsetDateTime.now());
		transfer.setCreateDate(OffsetDateTime.now());
		transfer.setTransferNote(TRANSFER_NOTE);
		return transfer;
	}

}
