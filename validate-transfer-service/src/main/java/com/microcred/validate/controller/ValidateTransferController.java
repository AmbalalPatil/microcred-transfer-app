package com.microcred.validate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microcred.validate.model.TransferDataRequest;
import com.microcred.validate.service.ValidateTransferService;

/**
 * Controller to handle transfer validation requests
 * @author Ambalal Patil
 */
@RestController
public class ValidateTransferController {

	@Autowired
	private ValidateTransferService validateTransferService;
	
	@PostMapping("/validate")
	public Boolean validateTransfer(@RequestBody TransferDataRequest transferDataRequest) {
		return validateTransferService.validateTransfer(transferDataRequest);
	}
}
