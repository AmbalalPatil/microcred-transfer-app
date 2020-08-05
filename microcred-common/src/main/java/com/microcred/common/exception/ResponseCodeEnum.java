package com.microcred.common.exception;

/**
 * 
 * @author Ambalal Patil
 */
public enum ResponseCodeEnum {

	// Response types
	INFORMATION,
	ERROR,
	
	// Success response codes
	CREATE_TRANSFER_SUCCESSFUL,
	UPDATE_TRANSFER_SUCCESSFUL,
	DELETE_TRANSFER_SUCCESSFUL,
	GET_ACCOUNT_TRANSFERS_SUCCESSFUL,
	VALIDATE_TRANSFER_SUCCESSFUL,
	
	// Error response codes
	GENERIC_APP_FAILURE,
	AUTH_SERVER_FAILURE_USER_IS_UNAUTHORIZED,
	API_GATEWAY_FAILURE_AUTH_TOKEN_INVALID,
	EXECUTE_TRANSFER_FAILURE_FROM_ACCOUNT_NOT_FOUND,
	EXECUTE_TRANSFER_FAILURE_TO_ACCOUNT_NOT_FOUND,
	EXECUTE_TRANSFER_FAILURE_INSUFFICIENT_FROM_ACCOUNT_BALANCE,
	EXECUTE_TRANSFER_FAILURE_DB_INSERT_FAILED,
	EXECUTE_TRANSFER_FAILURE_TRANSFER_NOT_FOUND,
	EXECUTE_TRANSFER_FAILURE_INVALID_TRANSFER_DATE,
	EXECUTE_TRANSFER_FAILURE_INVALID_TRANSFER_AMOUNT,
	EXECUTE_TRANSFER_FAILURE_DB_UPDATE_FAILED,
	EXECUTE_TRANSFER_FAILURE_ACCOUNT_TRANSFERS_NOT_FOUND,
	VALIDATE_TRANSFER_FAILURE_REQUEST_IS_NULL,
	VALIDATE_TRANSFER_FAILURE_MANDATORY_PARAM_MISSING,
	VALIDATE_TRANSFER_FAILURE_INVALID_TRANSFER_AMOUNT,
	VALIDATE_TRANSFER_FAILURE_INVALID_TRANSFER_DATE;
}
