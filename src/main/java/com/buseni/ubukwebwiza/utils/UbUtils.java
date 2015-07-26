package com.buseni.ubukwebwiza.utils;

import java.text.Normalizer;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

public class UbUtils {
	
	
	public static final Integer MAX_PHOTO = 8;
	
	 /**
     * 
     * @param name
     * @return
     */
    public static String normalizeName(String name){
        //name = name.replaceAll("\u002F", "-");
        name = name.replaceAll("[^\\p{L}\\p{N}]", "-");
        StringBuilder nameBuilder = new StringBuilder();
        StringTokenizer token = new StringTokenizer(name," '-");
        String str;
        while (token.hasMoreTokens()){
            str = token.nextToken();
            nameBuilder.append(str);
            if(token.hasMoreElements()){
                nameBuilder.append("-");
            }
        }
        String normalizeName = Normalizer.normalize(nameBuilder.toString(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
         
        return normalizeName.toLowerCase();
    }
    
    public static String createUrlName(String name){
    	 StringBuilder sb =  new StringBuilder();
    	 sb.append(name).append("-").append(generatePIN());
    	 return normalizeName(sb.toString());
    }
    
    
    public static String normalizeFileName(String name){
       if(StringUtils.isNotEmpty(name)){
    	  return System.currentTimeMillis()+name.trim().replaceAll("\\s+","_").toLowerCase();           
       }
     return "";
    }
    
    public static String generatePIN() {
        //generate a 4 digit integer 1000 <10000
        int randomPIN = (int)(Math.random()*9000)+1000;

        //Store integer in a string
       return String.valueOf(randomPIN);

    }

    

}
