package com.buseni.ubukwebwiza.exceptions;


import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<ExceptionMessage> exceptionMessages;
	
	public BusinessException() {
		super();
		exceptionMessages = new ArrayList<ExceptionMessage>();
	}
	
	public BusinessException(String key, String message){
		super(message);
		exceptionMessages = new ArrayList<ExceptionMessage>();
		exceptionMessages.add(new ExceptionMessage(key, message));
	}


	
	public void add(ExceptionMessage validationMessage) {
		exceptionMessages.add(validationMessage);
	}

	public List<ExceptionMessage> getExceptionMessages() {
		return exceptionMessages;
	}

}
