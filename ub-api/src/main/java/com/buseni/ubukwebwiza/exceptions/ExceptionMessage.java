package com.buseni.ubukwebwiza.exceptions;

import javax.validation.ConstraintViolation;


/**
 * Validation Message
 * @author fhabumug
 *
 */
public class ExceptionMessage {

	private String key;
	private String message;
	
	/**
	 * Default constructor
	 */
	public ExceptionMessage(){
	}
	
	/**
	 * Constructor with parameters
	 * @param key : key of the validationmessage
	 * @param message : message of the validationmessage
	 */
	public ExceptionMessage(String key, String message){
		this.key = key;
		this.message = message;
	}
	
	/**
	 * Constructor with contraint violation
	 * This constructor is used by Hibernate Validator method validate
	 * When there is a constraint violation
	 * @param cons
	 */
	public ExceptionMessage(ConstraintViolation<?> cons) {
		this.key = cons.getRootBeanClass().getSimpleName().toLowerCase()+"."+cons.getPropertyPath().toString();
		this.message = cons.getMessage();
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
