package com.buseni.ubukwebwiza.api.exceptions;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;

import com.buseni.ubukwebwiza.exceptions.CustomError;

public class ErrorsHelper {
	/**
	 * 
	 * @param result
	 * @param errors
	 */
	public static void rejectErrors(final BindingResult result, final List<CustomError> errors) {
	   
	        for (final CustomError error : errors) {
	        	if(StringUtils.isNotEmpty(error.getField())){
	        		result.rejectValue(error.getField(), error.getErrorCode(), error.getErrorArgs(), error.getDefaultMessage());
	        	}else{
	        		result.reject(error.getErrorCode(), error.getErrorArgs(), error.getDefaultMessage());
	        	}
	            
	        }
	    
	}
}
