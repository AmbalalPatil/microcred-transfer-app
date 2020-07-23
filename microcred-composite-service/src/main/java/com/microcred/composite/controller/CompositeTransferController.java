package com.microcred.composite.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping(value="/transfer")
	public String createTransfer(@RequestBody TransferDataRequest transferDataRequest) {
		return compositeTransferService.createTransfer(transferDataRequest);
	}
	
	@PutMapping(value="/transfer")
	public String updateTransfer(@RequestBody TransferDataRequest transferDataRequest) {
		return "Transfer updated";
	}
	
	@DeleteMapping(value="/transfer/{transferid}")
	public String deleteTransfer(@PathVariable("transferid") Long transferId) {
		return "Transfer deleted";
	}
	
	@GetMapping(value="/transfer")
	public List<String> getTransfers() {
		return Arrays.asList("transfer1", "transfer2");
	}
}
