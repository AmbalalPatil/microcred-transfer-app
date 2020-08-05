package com.microcred.composite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.exception.TransferException;
import com.microcred.common.response.MicroCredResponse;
import com.microcred.composite.model.TransferDataRequest;
import com.microcred.composite.service.CompositeTransferService;

/**
 * Controller to handle transfer operation requests.
 * @author Ambalal Patil
 */
@RestController
public class CompositeTransferController {

	@Autowired
	private CompositeTransferService compositeTransferService;
	
	@PostMapping(value="/performtransfer")
	public ResponseEntity<?> createTransfer(@RequestBody TransferDataRequest transferDataRequest) {
		return compositeTransferService.createTransfer(transferDataRequest);
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
