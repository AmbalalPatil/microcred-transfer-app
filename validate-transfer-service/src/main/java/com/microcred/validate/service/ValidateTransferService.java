package com.microcred.validate.service;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.exception.TransferException;
import com.microcred.common.request.TransferRequestTO;

/**
 * Service class to validate transfer data
 * @author Ambalal Patil
 */
@Service
public class ValidateTransferService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTransferService.class);
	
	/**
	 * Method to validate transfer request data
	 * @param requestTO - an instance of {@link TransferRequestTO}
	 * @return True if transfer data is valid else false
	 */
	public void validateTransfer(TransferRequestTO requestTO) throws TransferException {
		LOGGER.debug("Validating transfer request : ", requestTO);
		
		// 1. Check if request obejct is non-null
		if(Objects.isNull(requestTO)) {
			LOGGER.error("Transfer Request is null.");
			throw new TransferException(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_REQUEST_IS_NULL, 
					"Request cannot be null.");
		}
		
		// 2. Check if all mandatory data is present
		if(Objects.isNull(requestTO.getFromAcctId())
				|| Objects.isNull(requestTO.getToAcctId())
				|| Objects.isNull(requestTO.getAmount())
				|| Objects.isNull(requestTO.getTransferDate())) {
			LOGGER.error("Mandatory parameters are missing in the transfer request", requestTO);
			throw new TransferException(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_MANDATORY_PARAM_MISSING, 
					"Mandatory params are missing.");
		}
		
		// 3. Check if amount is greater than zero
		if(!(requestTO.getAmount().doubleValue() > 0)) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Transfer amount must be greater than zero. Amount :");
			stringBuilder.append(requestTO.getAmount().doubleValue());
			LOGGER.error(stringBuilder.toString());
			throw new TransferException(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_INVALID_TRANSFER_AMOUNT, 
					stringBuilder.toString());
		}
		
		// 4. Check if transfer date is a past date
		if(requestTO.getTransferDate().toLocalDate().isBefore(LocalDate.now())) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Transfer date should not be a past date. TransferDate : ");
			stringBuilder.append(requestTO.getTransferDate());
			LOGGER.error(stringBuilder.toString());
			throw new TransferException(ResponseCodeEnum.VALIDATE_TRANSFER_FAILURE_INVALID_TRANSFER_DATE, 
			stringBuilder.toString());
		}
		LOGGER.info("Transfer request successfully validated.");
	}
}