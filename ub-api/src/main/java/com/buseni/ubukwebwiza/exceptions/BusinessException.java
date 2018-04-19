package com.buseni.ubukwebwiza.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CustomError> errors = new ArrayList<>();

	public List<CustomError> getErrors() {
		return errors;
	}

	public void setErrors(List<CustomError> errors) {
		this.errors = errors;
	}
	
	public BusinessException(CustomError error){
		errors.add(error);
	}
	
	public void addError(CustomError error){
		errors.add(error);
	}
}
