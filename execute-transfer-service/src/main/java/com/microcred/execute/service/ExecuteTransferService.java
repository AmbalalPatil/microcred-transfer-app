package com.microcred.execute.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microcred.common.dbentity.Account;
import com.microcred.common.dbentity.Transfer;
import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.exception.TransferException;
import com.microcred.common.request.TransferRequestTO;
import com.microcred.execute.repository.AccountRepository;
import com.microcred.execute.repository.TransferRepository;

/**
 * Service class to handle transfer CRUD operations
 * @author Ambalal Patil
 */
@Service
@Transactional
public class ExecuteTransferService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteTransferService.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransferRepository transferRepository;

	/**
	 * Method to save transfer
	 * @param request - an instance of {@link TransferRequestTO}
	 * @return - an instance of {@link Transfer}
	 * @throws TransferException
	 */
	public Transfer performTransfer(TransferRequestTO request) throws TransferException {
		LOGGER.debug("Peforming transfer for request: {}", request);
		
		// 1. Get FROM account
		Account fromAccount = accountRepository.findById(request.getFromAcctId())
				.orElseThrow(() -> {
					String errMsg = "From account not found in DB for account Id : " + request.getFromAcctId();
					LOGGER.error(errMsg);
					return new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_FROM_ACCOUNT_NOT_FOUND, errMsg);
				});
	
		// 2. Get TO account
		Account toAccount = accountRepository.findById(request.getToAcctId())
				.orElseThrow(() -> {
					String errMsg = "To account not found in DB for account Id : " + request.getToAcctId();
					LOGGER.error(errMsg);
					return new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_TO_ACCOUNT_NOT_FOUND, errMsg);
				});
		
		// 3. Validate if from account has sufficient balance to perform requested transfer
		checkIfFromAccountHasSufficientBalance(request, fromAccount);
		
		// 4. Update account balances
		fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
		toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
		
		// 5. Create Transfer
		Transfer transfer = new Transfer();
		transfer.setFromAccount(fromAccount);
		transfer.setToAccount(toAccount);
		transfer.setAmount(request.getAmount());
		transfer.setTransferNote(request.getTransferNote());
		transfer.setTransferDate(request.getTransferDate());
		transfer.setCreateDate(OffsetDateTime.now());
		
		// 6. Save Transfer in DB
		Transfer savedTransfer = transferRepository.save(transfer);
		
		// 7. Validate if transfer successfully saved in DB
		if(Objects.isNull(savedTransfer) || savedTransfer.getTransferId() == 0) {
			LOGGER.error("Error occurred while saving transfer in to DB");
			throw new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_DB_INSERT_FAILED, 
					"Error while saving transfer");
		}
		LOGGER.info("Successfully peformed transfer.");
		
		// 8. Return Saved transfer details
		return savedTransfer;
	}

	/**
	 * Method to update transfer for given transferId
	 * @param transferId - Id of transfer to be updated
	 * @param request - an instance of {@link TransferRequestTO}
	 * @return - an instance of {@link Transfer}
	 * @throws TransferException
	 */
	public Transfer updateTransfer(Long transferId, TransferRequestTO request) throws TransferException {
		LOGGER.debug("Updating transfer for request: [{}] and transferId: [{}]", request, transferId);
		
		// 1. Check if valid amount provided in the request
		if(Objects.isNull(request.getAmount()) || !(request.getAmount().doubleValue() > 0)){
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Transfer amount should not be null or less than 0. TransferAmount : ");
			stringBuilder.append(request.getAmount());
			LOGGER.error(stringBuilder.toString());		
			throw new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_INVALID_TRANSFER_AMOUNT, 
					stringBuilder.toString());
		}
		
		// 2. Check if transfer date is not past date
		if(Objects.isNull(request.getTransferDate()) || request.getTransferDate().toLocalDate().isBefore(LocalDate.now())){
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Transfer date should not be null or past date. TransferDate : ");
			stringBuilder.append(request.getTransferDate());
			LOGGER.error(stringBuilder.toString());
			throw new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_INVALID_TRANSFER_DATE, 
					stringBuilder.toString());
		}
		
		// 3. Get existing transfer record for given transferId
		Transfer transfer = findTransferById(transferId);
		
		// 4. Validate if from account has sufficient balance to perform requested transfer
		checkIfFromAccountHasSufficientBalance(request, transfer.getFromAccount());
		
		// 5. Update account balances
		transfer.getFromAccount().setBalance(transfer.getFromAccount().getBalance().subtract(request.getAmount()));
		transfer.getToAccount().setBalance(transfer.getToAccount().getBalance().add(request.getAmount()));
		
		transfer.setAmount(request.getAmount());
		transfer.setTransferDate(request.getTransferDate());
		transfer.setUpdateDate(OffsetDateTime.now());
		
		// 6. Update transfer details into DB
		Transfer savedTransfer = transferRepository.save(transfer);
		
		// 7. Validate if transfer is successfully updated
		if(Objects.isNull(savedTransfer) || savedTransfer.getTransferId() == 0) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Error while updating transfer for transferId : ");
			stringBuilder.append(transferId);
			LOGGER.error(stringBuilder.toString());
			throw new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_DB_UPDATE_FAILED, 
					stringBuilder.toString());
		}
		LOGGER.debug("Transfer successfully updated for transferId : [{}]", savedTransfer.getTransferId());
		
		// 8. Return updated transfer details
		return savedTransfer;
	}
	
	/**
	 * Method to delete transfer for given transactionId
	 * @param transferId - Id of transfer to be deleted
	 * @throws TransferException
	 */
	public void deleteTransfer(Long transferId) throws TransferException {
		LOGGER.debug("Deleting transfer for transferId: [{}]", transferId);
		
		// 1. Get transfer for given transferId
		Transfer findTransferById = findTransferById(transferId);
		
		// 2. Delete transfer from DB
		transferRepository.delete(findTransferById);
		LOGGER.debug("Transfer successfully deleted for transferId : [{}]", transferId);	
	}
	
	/**
	 * Method to fetch accounts and transfers for given accountIds
	 * @param accountIdList - List of accountIds
	 * @return List of {@link Account}
	 * @throws TransferException
	 */
	public List<Transfer> findAccountTransfersByAccountIds(List<Long> accountIdList) throws TransferException {
		LOGGER.debug("Fetching account transfers for accountIds :", accountIdList);
		
		// 1. Get account transfer list for given accountIds
		OffsetDateTime _30DaysOldDate = OffsetDateTime.now().minusDays(30);
		List<Transfer> accountTransferList = transferRepository.findByFromAndToAccountId(accountIdList, _30DaysOldDate);
		
		// 2. Check if account transfers found
		if(CollectionUtils.isEmpty(accountTransferList)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Account transfers not found for accountIds : ");
			stringBuilder.append(accountIdList);
			LOGGER.error(stringBuilder.toString());;
			throw new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_ACCOUNT_TRANSFERS_NOT_FOUND, 
					stringBuilder.toString());
		}
		LOGGER.debug("Account transfer successfully retrieved for accountIds : ", accountIdList);
		
		//3. Return account transfers
		return accountTransferList;
	}
	
	private Transfer findTransferById(Long transferId) throws TransferException {
		return transferRepository.findById(transferId)
					.orElseThrow(() -> new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_TRANSFER_NOT_FOUND, 
							"Transfer record not found in DB for transfer Id : " + transferId));
	}
	
	private void checkIfFromAccountHasSufficientBalance(TransferRequestTO request, Account fromAccount)
			throws TransferException {
		if(fromAccount.getBalance().compareTo(request.getAmount()) == -1) {
			StringBuilder sb = new StringBuilder("From account balance :")
					.append(fromAccount.getBalance())
					.append(" is less than transfer amount :")
					.append(request.getAmount());
			LOGGER.error(sb.toString());
			throw new TransferException(ResponseCodeEnum.EXECUTE_TRANSFER_FAILURE_INSUFFICIENT_FROM_ACCOUNT_BALANCE, sb.toString());
		}
	}
}