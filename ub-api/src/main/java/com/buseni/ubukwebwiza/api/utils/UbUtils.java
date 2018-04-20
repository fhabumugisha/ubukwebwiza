package com.buseni.ubukwebwiza.api.utils;

import java.text.Normalizer;
import java.util.Random;
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
    
    public static String createUrlName(String name, boolean withPin){
    	 StringBuilder sb =  new StringBuilder();
    	 sb.append(name);
    	 if(withPin){
    		 sb.append("-").append(generatePIN()); 
    	 }
    	
    	 return normalizeName(sb.toString());
    }
    
    
    public static String normalizeFileName(String name){
       if(StringUtils.isNotEmpty(name)){
    	  return System.currentTimeMillis()+name.trim().replaceAll("\\s+","_").toLowerCase();           
       }
     return "";
    }
    
    public static String generatePassword(int len) {
        final char[] alphanumeric = alphanumeric();
        StringBuffer out = new StringBuffer();
        final Random rand = new Random();
        while(out.length() < len){
            int idx = Math.abs(( rand.nextInt() % alphanumeric.length ));
            out.append(alphanumeric[idx]);
        }
        return out.toString();
    }
    
    
    /**
     *  create alphanumeric char array
     * @return
     */
    private static char[] alphanumeric(){
        StringBuffer buf=new StringBuffer(128);
        for(int i=48; i<= 57;i++)buf.append((char)i); // 0-9
        for(int i=65; i<= 90;i++)buf.append((char)i); // A-Z
        for(int i=97; i<=122;i++)buf.append((char)i); // a-z
        return buf.toString().toCharArray();
    }
    
    public static String generatePIN() {
        //generate a 4 digit integer 1000 <10000
        int randomPIN = (int)(Math.random()*9000)+1000;

        //Store integer in a string
       return String.valueOf(randomPIN);

    }

    

}
