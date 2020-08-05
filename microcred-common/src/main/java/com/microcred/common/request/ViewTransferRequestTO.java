package com.microcred.common.request;

import java.util.List;

/**
 * 
 * @author Ambalal Patil
 */
public class ViewTransferRequestTO {

	private List<Long> accountIdList;

	public List<Long> getAccountIdList() {
		return accountIdList;
	}

	public void setAccountIdList(List<Long> accountIdList) {
		this.accountIdList = accountIdList;
	}
	
	
}
