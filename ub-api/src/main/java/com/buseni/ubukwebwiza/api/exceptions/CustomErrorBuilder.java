package com.buseni.ubukwebwiza.api.exceptions;

import com.buseni.ubukwebwiza.exceptions.CustomError;

public class CustomErrorBuilder {

	
	private CustomError customError;
	
	public CustomErrorBuilder(String errorCode) {
		customError =  new CustomError(errorCode);
	}
	
	
	public CustomErrorBuilder field(String field) {
		customError.setField(field);
		return this;
	}
	public CustomErrorBuilder errorCode(String errorCode) {
		customError.setErrorCode(errorCode);
		return this;
	}
	
	public CustomErrorBuilder errorArgs(Object[] errorArgs) {
		customError.setErrorArgs(errorArgs);
		return this;
	}
	
	public CustomErrorBuilder defaultMessage(String defaultMessage) {
		customError.setDefaultMessage(defaultMessage);
		return  this;
	}
	public CustomError buid() {
		return customError;
	}
}
