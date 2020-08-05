package com.microcred.validate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.exception.TransferException;
import com.microcred.common.request.TransferRequestTO;
import com.microcred.common.response.MicroCredResponse;
import com.microcred.validate.service.ValidateTransferService;

/**
 * Controller to handle transfer validation requests
 * @author Ambalal Patil
 */
@RestController
public class ValidateTransferController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateTransferController.class);
	
	@Autowired
	private ValidateTransferService validateTransferService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/validate")
	public ResponseEntity validateTransfer(@RequestBody TransferRequestTO rquestTO) throws Exception {
		
		validateTransferService.validateTransfer(rquestTO);
		MicroCredResponse response = new MicroCredResponse(ResponseCodeEnum.INFORMATION.name(),
				ResponseCodeEnum.VALIDATE_TRANSFER_SUCCESSFUL.name(),
				"validate transfer successfully completed",
				null);
		LOGGER.debug("Response :", new ObjectMapper().writeValueAsString(response));
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(TransferException.class)
	public ResponseEntity handleTransferException(TransferException transferException) {
		MicroCredResponse response = new MicroCredResponse(ResponseCodeEnum.ERROR.name(),
				transferException.getErrorCode().name(),
				transferException.getMessage(),
				null);
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleGenericException(Exception exception) {
		MicroCredResponse response = new MicroCredResponse(ResponseCodeEnum.ERROR.name(),
				ResponseCodeEnum.GENERIC_APP_FAILURE.name(),
				exception.getMessage(),
				null);
		return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
