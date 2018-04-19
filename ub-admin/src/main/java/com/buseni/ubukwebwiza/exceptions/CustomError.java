package com.buseni.ubukwebwiza.exceptions;

import org.apache.commons.lang3.StringUtils;

public class CustomError {

	
	private String field;
	private String errorCode;
	private Object[] errorArgs;
	private String defaultMessage;
	
	
	public CustomError(String errorCode) {
		this.errorCode = errorCode;
		this.field = StringUtils.EMPTY;
		this.errorArgs = new Object[]{};
		this.defaultMessage = StringUtils.EMPTY;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Object[] getErrorArgs() {
		return errorArgs;
	}
	public void setErrorArgs(Object[] errorArgs) {
		this.errorArgs = errorArgs;
	}
	public String getDefaultMessage() {
		return defaultMessage;
	}
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
}
