package com.microcred.execute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microcred.execute.model.TransferDataRequest;
import com.microcred.execute.service.ExecuteTransferService;

/**
 * Controller to handle transfer CRUD operation requests
 * @author Ambalal Patil
 */
@RestController
public class ExecuteTransferController {

	@Autowired
	public ExecuteTransferService executeTransferService;
	
	@PostMapping("/createtransfer")
	public String createTransfer(@RequestBody TransferDataRequest transferDataRequest) {
		return executeTransferService.createTransfer(transferDataRequest);
	}
}
