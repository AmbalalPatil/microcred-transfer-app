package com.microcred.validate.service;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.microcred.validate.model.TransferDataRequest;

/**
 * Service class to validate transfer data
 * @author Ambalal Patil
 */
@Service
public class ValidateTransferService {

	/**
	 * Method to validate transfer request data
	 * @param transferDataRequest - an instance of {@link TransferDataRequest}
	 * @return True if transfer data is valid else false
	 */
	public Boolean validateTransfer(TransferDataRequest transferDataRequest) {
		
		Boolean isValid = Boolean.TRUE;
		
		// 1. Check if request obejct is non-null
		if(Objects.isNull(transferDataRequest)) {
			isValid = Boolean.FALSE;
		}
		
		// 2. Check if all mandatory data is present
		if(StringUtils.isBlank(transferDataRequest.getFromAcctId())
				|| StringUtils.isBlank(transferDataRequest.getFromAcctId())
				|| Objects.isNull(transferDataRequest.getAmount())) {
			isValid = Boolean.FALSE;
		}
		
		// 3. Check if amount is greater than zero
		if(transferDataRequest.getAmount().doubleValue() <= 0) {
			isValid = Boolean.FALSE;
		}
		return isValid;
	}
}