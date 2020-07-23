package com.microcred.composite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microcred.composite.model.TransferDataRequest;

/**
 * Facade service class for all transfer operations
 * @author Ambalal Patil
 */
@Service
public class CompositeTransferService {

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Method to perform create transfer operation
	 * @param transferDataRequest - an instance of {@link TransferDataRequest}}
	 * @return transfer status - on success - SUCCESS else FAILED
	 */
	public String createTransfer(TransferDataRequest transferDataRequest) {
		
		// 1. Validate transfer data using validate-transfer micro-service
		Boolean isValidTransfer = restTemplate.postForObject("http://validate-transfer-service/validate",
													transferDataRequest, Boolean.class);
		String transferStatus = "FAILED";
		// 2. On successful validation, perform transfer using execute-transfer micro-service
		if(isValidTransfer) {
			transferStatus = restTemplate.postForObject("http://execute-transfer-service/createtransfer",
					transferDataRequest, String.class);
		}
		return transferStatus;
	}
}
