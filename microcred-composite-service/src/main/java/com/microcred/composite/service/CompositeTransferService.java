package com.microcred.composite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.response.MicroCredResponse;
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
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public ResponseEntity createTransfer(TransferDataRequest transferDataRequest) {
		
		// 1. Validate transfer data using validate-transfer micro-service
		MicroCredResponse response = restTemplate.postForObject("http://validate-transfer-service/validate",
													transferDataRequest, MicroCredResponse.class);
		
		// 2. On successful validation, perform transfer using execute-transfer micro-service
		if(ResponseCodeEnum.VALIDATE_TRANSFER_SUCCESSFUL.name().equals(response.getCode())) {
			response = restTemplate.postForObject("http://execute-transfer-service/performtransfer",
					transferDataRequest, MicroCredResponse.class);
		}
		return new ResponseEntity(response, HttpStatus.OK);
	}
}
