package com.buseni.ubukwebwiza.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.buseni.ubukwebwiza.account.beans.SignupForm;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {   
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {       
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){   
        SignupForm user = (SignupForm) obj;
      //  return user.getPassword().equals(user.getMatchingPassword());    
        return user.getPassword().equals(user.getPassword()); 
    }     
}