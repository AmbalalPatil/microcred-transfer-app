package com.microcred.execute.service;

import org.springframework.stereotype.Service;

import com.microcred.execute.model.TransferDataRequest;

/**
 * Service class to handle transfer CRUD operations
 * @author Ambalal Patil
 */
@Service
public class ExecuteTransferService {

	public String createTransfer(TransferDataRequest transferDataRequest) {
		return "SUCCESS";
	}
}
