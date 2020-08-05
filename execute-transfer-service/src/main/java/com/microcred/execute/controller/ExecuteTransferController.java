package com.microcred.execute.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microcred.common.dbentity.Transfer;
import com.microcred.common.exception.ResponseCodeEnum;
import com.microcred.common.exception.TransferException;
import com.microcred.common.request.TransferRequestTO;
import com.microcred.common.request.ViewTransferRequestTO;
import com.microcred.common.response.MicroCredResponse;
import com.microcred.execute.service.ExecuteTransferService;

/**
 * Controller to handle transfer CRUD operation requests
 * @author Ambalal Patil
 */
@RestController
public class ExecuteTransferController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteTransferService.class);
	
	@Autowired
	public ExecuteTransferService executeTransferService;
	
	@PostMapping("/performtransfer")
	public ResponseEntity<MicroCredResponse<Transfer>> performTransfer(@RequestBody TransferRequestTO transferDataRequest) throws Exception {
		
		Transfer transfer = executeTransferService.performTransfer(transferDataRequest);
		MicroCredResponse<Transfer> response = new MicroCredResponse<Transfer>(ResponseCodeEnum.INFORMATION.name(),
				ResponseCodeEnum.CREATE_TRANSFER_SUCCESSFUL.name(),
				"Transfer successfully created",
				transfer);
		LOGGER.debug("Response :", new ObjectMapper().writeValueAsString(response));
		return new ResponseEntity<MicroCredResponse<Transfer>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/transfer/{transferid}")
	public ResponseEntity<MicroCredResponse<Transfer>> updateTransfer(@PathVariable("transferid") Long transferId, 
			@RequestBody TransferRequestTO transferDataRequest) throws Exception {
		
		Transfer transfer = executeTransferService.updateTransfer(transferId, transferDataRequest);
		MicroCredResponse<Transfer> response = new MicroCredResponse<Transfer>(ResponseCodeEnum.INFORMATION.name(),
				ResponseCodeEnum.UPDATE_TRANSFER_SUCCESSFUL.name(),
				"Transfer successfully updated",
				transfer);
		LOGGER.debug("Response :", new ObjectMapper().writeValueAsString(response));
		return new ResponseEntity<MicroCredResponse<Transfer>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/transfer/{transferid}")
	public ResponseEntity<MicroCredResponse<Transfer>> deleteTransfer(@PathVariable("transferid") Long transferId) throws Exception {
		
		executeTransferService.deleteTransfer(transferId);
		MicroCredResponse<Transfer> response = new MicroCredResponse<Transfer>(ResponseCodeEnum.INFORMATION.name(),
				ResponseCodeEnum.DELETE_TRANSFER_SUCCESSFUL.name(),
				"Transfer successfully deleted",
				null);
		LOGGER.debug("Response :", new ObjectMapper().writeValueAsString(response));
		return new ResponseEntity<MicroCredResponse<Transfer>>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/transfer")
	public ResponseEntity<MicroCredResponse<List<Transfer>>> getAccountTransfers(@RequestBody ViewTransferRequestTO request) throws Exception {
		
		List<Transfer> accountTransfers = executeTransferService.findAccountTransfersByAccountIds(request.getAccountIdList());
		MicroCredResponse<List<Transfer>> response = new MicroCredResponse<List<Transfer>>(ResponseCodeEnum.INFORMATION.name(),
				ResponseCodeEnum.GET_ACCOUNT_TRANSFERS_SUCCESSFUL.name(),
				"Account transfers successfully retrieved",
				accountTransfers);
		LOGGER.debug("Response :", new ObjectMapper().writeValueAsString(response));
		return new ResponseEntity<MicroCredResponse<List<Transfer>>>(response, HttpStatus.OK);
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
