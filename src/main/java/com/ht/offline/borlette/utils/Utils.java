/*
 * Utils.java
 *
 * Created on Septembre 17 2018, 11:25 AM
 */

package com.ht.offline.borlette.utils;

/* 
 * @auteur : Vertus Hector
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.security.MessageDigest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import biz.isman.util.ConvertUtils;

public class Utils implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

    //getting the default locale by giving the preferred language
    public static Locale getLocale(String language) {
       Locale locale = null;

       if (language.equalsIgnoreCase("fr") || language.equalsIgnoreCase("fr_FR")) {
           locale = new Locale("fr");
       }

       if (language.equalsIgnoreCase("en") || language.equalsIgnoreCase("en_US")) {
           locale = new Locale("en");
       }

       if (language.equalsIgnoreCase("ht") || language.equalsIgnoreCase("ht_HT")) {
           locale = new Locale("ht");
       }

       // Set the default locale to custom locale
        Locale.setDefault(locale);

       return Locale.getDefault();
    }

    //methode permetant de verifier une valeur dans une intervalle
    public static boolean intRange(int min,int max,int value) {
        return (value >= min) && (value <= max);
    } // fin de la methode intRange

    public static boolean intRangeRight(int min,int max,int value) {
        return (value >= min) && (value < max);
    // fin de la methode intRange
    }

    public static boolean longRange(Long min, Long max, Long value) {
        return (value >= min) && (value <= max);
    } // fin de la methode intRange
    
    public static boolean doubleRange(double min,double max,double value) {
        return (value >= min) && (value <= max);
    } // fin de la methode intRange  
    
    public static boolean doubleRangeLeft(double min,double max,double value) {
        return (value > min) && (value <= max);
    } // fin de la methode intRange  
    
    public static boolean doubleRangeRight(double min,double max,double value) {
        return (value >= min) && (value < max);
    } // fin de la methode intRange      

    //Arrondir des valeurs numeriques
    //round(1.6666666,2); =1.67   round(1.6666666,3); = 1.667   round(1.6666666,0);  =2.0 
    public double round(double value, int decimal) {
        return (double)( (int)(value * Math.pow(10,decimal) + .5) ) / Math.pow(10,decimal);        
    } 
    
    /*method to format a number
     *@number : number to format
     *@separator : char to separate the values
     *Example : formatNumber(20.1,'.') ; will return 20.10 or 2.0 = 02.00 
     */    
	public static String formatNumber(double number, char separator) {
		
		int value = Integer.parseInt(firstIndexOfString(String.valueOf(number), '.'));
		
		String fValue = "";
		
		if(intRange(0,9,value)) fValue = "0"+value;
		else if(value>=10) fValue = String.valueOf(value);
		
		int decimal = Integer.parseInt(lastIndexOfString(String.valueOf(number), '.'));
		
		String fDecimal = "";
		
		if(intRange(0,9,decimal)) fDecimal = decimal+"0";
		else if(decimal>=10) fDecimal = String.valueOf(decimal);
		
		return fValue+separator+fDecimal;
	} 
	
    /*method to format decimal of number
     *@number : number to format the decimal
     *@decimal : number of decimal to add to the number
     *Example : formatDecimal(20.1,2) ; will return 20.10 or formatDecimal(0.2,3) will return 0.200
     */    
	public static String formatDecimal(double number, int decimal) {
		
		int value = Integer.parseInt(firstIndexOfString(String.valueOf(number), '.'));
		
		int decima = Integer.parseInt(lastIndexOfString(String.valueOf(number), '.'));
		
		String fDecimal = "";
		
		if(intRange(0,9,decima)) fDecimal = decima+addZeros(decimal-1);
		else if(decima>=10) fDecimal = String.valueOf(decima);
		
		return value+"."+fDecimal;
	}
	
	private static String addZeros(int number) {
		StringBuilder buff = new StringBuilder();
		
		if(number>0) {
			for(int i=0; i<number; i++) {
				buff.append("0");
			}
		} else return null;
		
		return buff.toString();
	}

	//String encryptor=================================================================================
    /**
     * Encod? une chaine de caract?re
     *
     * string : chaine ? encoder
     * @return chaine encod?
     * @throws NoSuchAlgorithmException
     */
    public static String encriptString(String string) { 
        
      StringBuffer hexString = new StringBuffer();
      
      try {  
          if (ConvertUtils.isNotBlank(string)) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(string.getBytes());
    
                byte[] hash = md.digest();
    
                for (int i = 0; i < hash.length; i++) {
                    if((0xff & hash[i]) < 0x10)
                        hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                    else
                        hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
          }
          else System.out.println(">----------- Unable to encript a blink string ....."); //log.info(">----------- Unable to encript a blink string .....");
        
      }
      catch (NoSuchAlgorithmException nosae) {
    	  System.out.println(">-------------------- Encript String Error "+nosae);
      }
      
      return (!string.equals(null) || string.length()>0) ? hexString.toString() : "";    
    }
    
    //function to check if a string is a valid md5 format
    public static boolean isValidMD5(String string) {
        return string.matches("^[a-fA-F0-9]{32}$");
    }

    //function to encrypt string using base64
	public static String encrypt(String data){
		if(!isBlank(data))			
			return Base64.getEncoder().encodeToString(data.getBytes()); //return new String(Base64.getEncoder().encode(data.getBytes()));
		return null;
	}
    
	//function to decrypt string using base64
	public static String decrypt(String data){		
		if(!isBlank(data))
			return new String(Base64.getDecoder().decode(data)); 
		return null;
	}

    //End tring encryptor=======================================================================================
        
    public static String encodeNumber(int number) {
        String value = null ;
        
        if (number >=0 & number <=9)
            value = "00".concat(String.valueOf(number)) ;
        else {
            if (number >=10 & number <100)
                value = "0".concat(String.valueOf(number)) ;
            else {
                if (number >=100)
                    value = String.valueOf(number) ;
                
            }                
        }
                    
        return value;
    }

    //return true if is an integer or a numeric value otherwise false
    public static boolean isNumeric(String stringValue) {
        return stringValue.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
    } 
    
    // Returns true if all are digits numbers, else false.
    public static boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)) == false) {
                return false;
            }
        }

        return true;
    }    

   //check if an object is null, return true if the given object is null false otherwise
   public static boolean isNull(Object obj) {
       return obj == null;
   }
   
   //check if an object is not null, return true if the given object is null false otherwise
   public static boolean isNotNull(Object obj) {
       return obj != null;
   }   


   //check if an object is empty or null
   public static boolean isBlank(String string) {
       return ConvertUtils.isBlank(string);
   } 
   
    //read the method combineString below
    public String coeffCode(String svalue1,int index1,String svalue2,int index2) {
        return combineString(svalue1,index1,svalue2,index2).toUpperCase();
    }
    
    
    /*method to extract a substring
     *@string_value : string to be extracted
     *@index : number of the extraction
     *example : prefix("Hector",3) will return Hec
     *          prefix("Dalton",2) will return Da
     */
    
    public static String prefix(String stringValue,int index) {
        return stringValue.substring(0, index);
    }
            
    /*method to combine string
     *@svalue1 : extracting the first string value
     *@index1 : number of the first extraction
     *@svalue2 : extracting the second string value
     *@index2 : number of the second extraction
     *example : combineString("Hector",3,"Vertus",2) will return HecVe     
     */    
    public String combineString(String svalue1,int index1,String svalue2,int index2) {
        return prefix(svalue1, index1).concat(prefix(svalue2, index2));
    }

    //Methode to compare two strings without case sensitive
    public boolean compareStringWithoutCase(String string_a,String string_b) {
        boolean result = false;

        result = string_a.equalsIgnoreCase(string_b) == true;
                
        return result;        
    }
    
    //Methode to compare two strings with case sensitive
    /**
     * 
     * @param string_a 
     * @param string_b 
     * @return 
     */
    public boolean compareStringWithCase(String string_a,String string_b) {
        boolean result = false;

        result = string_a.equals(string_b);
                
        return result;        
    }
    
    /*method to convert a double to integer and round it to upper
     *@dblvalue : the double to round and convert
     *@example : dblvalue=10.22 will return 11, 56.9 will return 57
     */     
    public int roundToUpper(double dblvalue) {              
        return (int)Math.ceil(dblvalue);
    }
    
    /*method to round a number to two decimals     
     *@example : number=10.2 will return 10.20
     */     
    public static double round(double number) {
        double num = (int)Math.round(number * 100)/(double)100;
		DecimalFormat df = new  DecimalFormat("##.00");
		
        return Double.valueOf(df.format(num));
    }
    
    //return two exact decimal of a number
    public static double exactDecimal(double number) {
    	DecimalFormat df = new  DecimalFormat("##.00");
    	
    	String numString = String.valueOf(number);
    	
    	String first = firstIndexOfString(numString,'.');
    	
    	String last = lastIndexOfString(numString,'.');
    	
    	if(last.length()<=1) last = last.concat("0");
    	else last = prefix(last, 2);
    	
    	String numberString = first.concat(".".concat(last));
    	
    	double numformat = Double.valueOf(numberString);
		
    	return Double.valueOf(df.format(numformat));
    } 
    
    //format any number to two decimal. Example : formatNumber(1000) returns 1,000.00
    public static String formatNumber(double number) {
    	DecimalFormat df = new DecimalFormat("#,###,##0.00");	
    	return df.format(number);
    }
    
    /*method to capitalize parameter @string. 
     *Example : capitalizeString("the lord of the rings by malval jhon-doe")
     *returns : The Lord Of The Rings By Malval Jhon-Doe
     *Very important to format name of person.  
     */
    public static String capitalizeString(String string) {
    	
    	String formattedString = null;
    	
    	if(ConvertUtils.isNotBlank(string)) {
    		
    		//variable to store the rest in subString variable
    		//String subString = null;
    		
	    	String[] strings = null;
	    	
	    	if(containsWhitespace(string)) {
	    		strings = string.split(" "); //Tools.extractSubstring(string, ' ');
	    		
	    		for(int i=0; i<strings.length; i++) {
	    			
	    			if(string.contains("-")) {
	    				strings[i] = dashedString(strings[i]);	    				
	    			} else {	    			
		    			/*//extract the first character from string, then store the rest in subString variable
		    			subString = strings[i].substring(1, strings[i].length());
		    			
		    			//capitalize the first letter
		    			String letter = String.valueOf(strings[i].charAt(0)).toUpperCase();
		    			
		    			//concatenate capitalize letter to subString
		    			strings[i] = letter.concat(subString);*/
	    				strings[i] = capitalizeStringf(strings[i]);
	    			}
	    		}
	    		
	    		
	    		formattedString = isNotNull(strings) ? arrayToString(strings, " ").trim() : null;
	    		//System.out.println("String to format 1 :: "+formattedString);
	    	} else if(string.contains("-")) {
	    		formattedString = dashedString(string);
	    	} else {
    			/*//extract the first character from string, then store the rest in subString variable
    			subString = string.substring(1, string.length());
    			
    			//capitalize the first letter
    			String letter = String.valueOf(string.charAt(0)).toUpperCase();
    			
    			formattedString = letter.concat(subString);
    			//System.out.println("String to format 2:: "+formattedString);*/	    		
	    		formattedString = capitalizeStringf(string);
	    	}
    	}
    	
    	//System.out.println("String to format returned :: "+formattedString);
    	
    	return formattedString;
    }
    
    /*method to capitalize the first letter only of the parameter @string. 
     *Example : capitalizeStringf("boulevard de la liberte")
     *returns : Boulevard de la liberte 
     */
    public static String capitalizeStringf(String string) {
    	
    	String formattedString = null;
    	
    	if(ConvertUtils.isNotBlank(string)) {    	
			/*//extract the first character from string, then store the rest in subString variable
	    	String subString = string.substring(1, string.length());
			
			//capitalize the first letter
			String letter = String.valueOf(string.charAt(0)).toUpperCase();
			
			formattedString = letter.concat(subString);
			//System.out.println("String to format :: "+formattedString); */
    		
    		formattedString = string.substring(0, 1).toUpperCase() + string.substring(1);    		
    	}
		
		return formattedString;
    }
    
    //this method is useful especially for method capitalizeString above
    private static String dashedString(String string) {
    	
    	String dashedString = null;
    	
    	if(ConvertUtils.isNotBlank(string)) {
    		
    		//variable to store the rest in subString variable
    		//String subString = null;
    		
	    	String[] strings = null;
	    	
	    	if(string.contains("-")) {
	    		strings = string.split("-");//Tools.extractSubstring(string, '-');
	    		
	    		for(int i=0; i<strings.length; i++) {
	    			//extract the first character from string, then store the rest in subString variable
	    			/*subString = strings[i].substring(1, strings[i].length());
	    			
	    			//capitalize the first letter
	    			String letter = String.valueOf(strings[i].charAt(0)).toUpperCase();
	    			
	    			strings[i] = letter.concat(subString);*/
	    			strings[i] = capitalizeStringf(strings[i]);
	    		}
	    		
	    		dashedString = arrayToString(strings, "-").trim();
	    	}    	
    	}
    	
    	return dashedString;
    }
    
    //method to check if @string contains white space
    public static boolean containsWhitespace(String string) {
		boolean found = false;
		
	    if(string != null){
			for(int i = 0; i < string.length(); i++){
			    if(Character.isWhitespace(string.charAt(i))){
			    	found = true;
			    } 
			}
	    } 
	    
	    return found;
    }
    
    /*Function to format a string
     *@string : The string to format
     *@mask : the mask
     *Example :: maskString("50937063321", "###-#-###-####")
     *Return :: 509-3-706-3321   */
    public static String maskString(String string, String mask) {
    	
    	String masked = null;
    	
		try {	
			 javax.swing.text.MaskFormatter maskFormatter = new javax.swing.text.MaskFormatter(mask);			 
			 maskFormatter.setValueContainsLiteralCharacters(false);
			 masked = maskFormatter.valueToString(string);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return masked;
    }
        
    /*indexed a string and get the first index
     *@string : the string to be indexed
     *@separator : char witch separe the strings
     *@example : firstIndexOfString("Hector, Vertus",',') will return Hector
     */    
    public static String firstIndexOfString(String string,char separator) {
    	String indexedString = null;
    	if(ConvertUtils.isNotBlank(string)) {
	        int index = string.indexOf(separator);
	        if (index == -1)
	        	indexedString = string;
	        else
	        	indexedString = string.substring(0, index);
    	}
    	return indexedString;
    }

    /*indexed a string and get the first index
     *@string : the string to be indexed
     *@separator : char witch separe the strings
     *@example : lastIndexOfString("Hector, Vertus",',') will return Vertus
     */
    public static String lastIndexOfString(String string,char separator) {
        return string.substring(string.lastIndexOf(separator)+1).trim();
    }
    
    /* Convert an array of strings to one string.
     * Put the 'separator' string between each element.
     * Don't put any empty string in the first element of the array
     */
    public static String arrayToString(String[] array, String separator) {
    	return String.join(separator, array);
    }

    public String appendString(String string, char separator) {
        StringBuffer stringbuff = new StringBuffer();
        if(!isBlank(string)) {
           stringbuff.append(string);
           stringbuff.append(separator);
        }

        return stringbuff.toString();
    }

    public static boolean isLongrangeIn(Long[] longrangeParent, Long[] longrangeChild) { //check if a date range is inside another
        return (longrangeChild[0] >= longrangeParent[0]) && (longrangeChild[1] <= longrangeParent[1]);
    }
        
    //method to compare string elements each other from an array of string
    public static boolean isFound(String[] elements){
    	boolean found = false;
    	String[] cloneElements = elements;
    	int length = elements.length;
       	if(length >0){       	      		  
       	   for(int i=0;i<length;i++){
       		   int element = 0;
        	   for(int j=0;j<cloneElements.length;j++){
        		   if(ConvertUtils.isSame(elements[i],cloneElements[j])) {
        			  element++;					  
        		   }				  
        	   }
        	   //System.out.println("Element counter :: " + element);	   
   			   if(element>=2) {
   				 //System.out.println(elements[i] + " :: is found...");
   				 found = true;
    			 break;				  
        	   }
    		   
       	   }
       	}    	
    	
    	return found;
    }
    
    //method to drop duplicate elements from an array, example :{"Jo", "Sara", "Jo" , "Pator", "Sara"} will return {"Sara", "Jo" , "Pator"}
    public static String[] dropDuplicates(String[] elements) {
    	
    	if(isNull(elements)) return elements;
    	
	 	for(int i=0;i<elements.length;i++){
	 		int element = 0;
	    	for(int j=0;j<elements.length;j++){
	    		if(ConvertUtils.isSame(elements[i], elements[j])) {
	    		  element++;					  
	    		}				  
	    	}
	    	
	    	//System.out.println("Element counter :: " + element);	   
			if(element>=2) {
				//System.out.println(elements[i] + " :: is found...");
				elements[i] = null;    			 				  
	    	}    		   
	   	}    	
    	
    	return cleanArray(elements);
    }
    
    //same as above
    public static long[] dropDuplicates(long[] elements) {
    	if(isNull(elements)) {
    		return elements;
    	}
    	
    	String[] el = new String[elements.length];
    	
    	for(int i=0; i<elements.length; i++) {
    		el[i]=String.valueOf(elements[i]);
    	}
    	
    	el = dropDuplicates(el);
    	
    	elements = new long[el.length];
    	
    	for(int i=0; i<el.length; i++) {
    		elements[i]=Long.valueOf(el[i]);
    	}    	
    	
    	return elements;
    }
   
    //method to drop duplicate elements from an array, example :{"Jo", "Sara", "Jo" , "Pator", "Sara"} will return {"Sara", "Jo" , "Pator"}
    public static Object[] dropDuplicates(Object[] elements) {
    	
    	if(isNull(elements)) return elements;
    	
	 	for(int i=0;i<elements.length;i++){
	 		int element = 0;
	    	for(int j=0;j<elements.length;j++){
	    		if(elements[i]== elements[j]) {
	    		  element++;					  
	    		}				  
	    	}
 
			if(element>=2) {
				elements[i] = null;    			 				  
	    	}    		   
	   	}    	
    	
    	return cleanArray(elements);
    }
    
    //method to remove empty string elements from the array
    public static String[] cleanArray(String[] elements){
    	String[] cleanup =null;
    	
    	int blank=0;
    	for(int i=0;i<elements.length;i++){
    		if(ConvertUtils.isBlank(elements[i])) blank++;    	
        }
    	
    	cleanup = new String[elements.length-blank];
    	int j =0;
    	for(int i=0;i<elements.length;i++){
    		if(ConvertUtils.isNotBlank(elements[i])) {
    			cleanup[j] = elements[i];
    			j++;
    		}
        }    	
    	
    	return blank>0?cleanup:elements;
    }
    
    public static String[][] cleanArray(String[][] elements){
    	String[][] cleanup =null;
    	
    	int blank=0;//, size = 0;
    	for(int i=0;i<elements.length;i++){
    		//if(size==0) size=elements[i].length;    			
    		if(isNull(elements[i])) blank++;    	
        }
    	
    	cleanup = new String[elements.length-blank][];
    	int j =0;
    	for(int i=0;i<elements.length;i++){
    		if(isNotNull(elements[i])) {
    			cleanup[j] = elements[i];
    			j++;
    		}
        }    	
    	
    	return blank>0?cleanup:elements;
    }
    
    //same as above
    public static Object[] cleanArray(Object[] elements){
    	Object[] cleanup =null;
    	
    	int blank=0;
    	if(isNotNull(elements)) {	    	
	    	for(int i=0;i<elements.length;i++){
	    		if(isNull(elements[i])) blank++;    	
	        }
	    	
	    	cleanup = new Object[elements.length-blank];
	    	int j =0;
	    	for(int i=0;i<elements.length;i++){
	    		if(isNotNull(elements[i])) {
	    			cleanup[j] = elements[i];
	    			j++;
	    		}
	        }  
    	}
    	
    	return blank > 0 ? cleanup:elements;
    }    
    
    
	/*Difference between two integer arrays.
	 *Make sure that arrayA has more elements than arrayB.Otherwise arrayB will become arrayA
	 *Example : int[] arrayA = {1,2,3,4,5}
	 * 			int[] arrayB = {2,5}
	 *Result	Utils.diff(arrayA,arrayB)={1,3,4} 	vice versa			 
	 * */
	public static int[] diff(int[] arrayA, int[] arrayB) {
		
		if(isNull(arrayA)) {
			arrayA = new int[0];
		}
		
		if(isNull(arrayB)) {
			arrayB = new int[0];
		}
		
		if(arrayA.length==0) {
			return arrayB;
		}
		
		if(arrayB.length==0) {
			return arrayA;
		}
		
		int[] resultset = new int[0]; //resutlset=arrayA-arrayB		
		int count=0;
		if(arrayA.length>arrayB.length){
			for (int i = 0; i < arrayA.length; i++) {
				//count=0;
				for (int j = 0; j < arrayB.length; j++){
					if (arrayA[i]==arrayB[j]){
						arrayA[i]=-1; //replace the value by -1
						count++;
					}
				}
			}
        
			//System.out.println("<>--------- Count value = " + count);
			resultset = new int[arrayA.length-count];
			int n=0,k=0;
			while(n<arrayA.length){
				if(arrayA[n]!=-1) {
					resultset[k] = arrayA[n];        		
					k++;
				}        	
				n++;
			}
		} else 	{
			for (int i = 0; i < arrayB.length; i++) {
				//count=0;
				for (int j = 0; j < arrayA.length; j++){
					if (arrayB[i]==arrayA[j]){
						arrayB[i]=-1; //replace the value by -1
						count++;
					}
				}
			}
        
			//System.out.println("<>--------- Count value = " + count);
			resultset = new int[arrayB.length-count];
			int n=0,k=0;
			while(n<arrayB.length){
				if(arrayB[n]!=-1) {
					resultset[k] = arrayB[n];        		
					k++;
				}        	
				n++;
			}
		}
		
		
		//else System.out.println("<>--------- Array B has more elements than array A");

		return resultset;
	} 
	
	//method to sort an array of string
	public static String[] sortArray(String[] array) {
        int size = array.length;
        for(int i = 0; i<size-1; i++) {
           for (int j = i+1; j< array.length; j++) {
              if(array[i].compareTo(array[j])>0) {
                 String temp = array[i];
                 array[i] = array[j];
                 array[j] = temp;
              }
           }
        } 
        
        return array;
    }	
	
	/*Difference between two integer arrays.
	 *Make sure that arrayA has more elements than arrayB.Otherwise arrayB will become arrayA
	 *Example : long[] arrayA = {1,2,3,4,5}
	 * 			long[] arrayB = {2,5}
	 *Result	Utils.diff(arrayA,arrayB)={1,3,4} 	vice versa			 
	 * */
	public static long[] diff(long[] arrayA, long[] arrayB) {

		if(isNull(arrayA)) {
			arrayA = new long[0];
		}
		
		if(isNull(arrayB)) {
			arrayB = new long[0];
		}	
		
		if(arrayA.length==0) {
			return arrayB;
		}
		
		if(arrayB.length==0) {
			return arrayA;
		}
		
		long[] resultset; //resutlset=arrayA-arrayB		
		int count=0;
		if(arrayA.length>arrayB.length){
			for (int i = 0; i < arrayA.length; i++) {
				//count=0;
				for (int j = 0; j < arrayB.length; j++){
					if (arrayA[i]==arrayB[j]){
						arrayA[i]=-1; //replace the value by -1
						count++;
					}
				}
			}
        
			//System.out.println("<>--------- Count value = " + count);
			resultset = new long[arrayA.length-count];
			int n=0,k=0;
			while(n<arrayA.length){
				if(arrayA[n]!=-1) {
					resultset[k] = arrayA[n];        		
					k++;
				}        	
				n++;
			}
		} else 	{
			for (int i = 0; i < arrayB.length; i++) {
				//count=0;
				for (int j = 0; j < arrayA.length; j++){
					if (arrayB[i]==arrayA[j]){
						arrayB[i]=-1; //replace the value by -1
						count++;
					}
				}
			}
        
			//System.out.println("<>--------- Count value = " + count);
			resultset = new long[arrayB.length-count];
			int n=0,k=0;
			while(n<arrayB.length){
				if(arrayB[n]!=-1) {
					resultset[k] = arrayB[n];        		
					k++;
				}        	
				n++;
			}
		}
		
		
		//else System.out.println("<>--------- Array B has more elements than array A");

		return resultset;
	}	
	
	//method to cast string array to integer array
	public static int[] cast(String[] array) {
		int[] elements = new int[array.length];	 
		if(array.length>0){		   
		   for(int i=0;i<array.length;i++){
			   elements[i] = Integer.parseInt(array[i]);
		   }
		}
		
		return elements;
	}
	
    //cast an array into string
    public static String arrayToString(String[] array, char separator) {
        if(isNull(array)) return null;

        StringBuffer buff = new StringBuffer();
        for(int i=0; i < array.length; i++) {
            buff.append(array[i]);
            if(array.length-1!=i) {
                buff.append(separator);
            }
        }

        return buff.toString();
    }	
	
	//method to add a string into an array of string.
	public static String[] addToArray(String[] arg0, String arg1) {

		if(isNull(arg0)) {
			arg0 = new String[]{};
		}
		
	    //define the new array
	    String[] newArray = new String[arg0.length + 1];

	    if(arg0.length == 0) {
	    	newArray[0] = arg1; //set arg1 into the first element
	    } else {	    
		    //copy values into new array
		    for(int i=0;i < arg0.length;i++) {
		        newArray[i] = arg0[i];
		    }
		    
		    //add new value to the new array
		    newArray[newArray.length-1] = arg1;		    
	    }

	    //copy the address to the old reference 
	    //the old array values will be deleted by the Garbage Collector
	    arg0 = newArray;
	    
	    return arg0;
	}
	
	//method to add an object into an array of object.
	public static Object[] addToArray(Object[] arg0, Object arg1) {

		if(isNull(arg0)) {
			arg0 = new Object[]{};
		}
		
	    //define the new array
	    Object[] newArray = new Object[arg0.length + 1];

	    if(arg0.length == 0) {
	    	newArray[0] = arg1; //set arg1 into the first element
	    } else {	    
		    //copy values into new array
		    for(int i=0;i < arg0.length;i++) {
		        newArray[i] = arg0[i];
		    }
		    
		    //add new value to the new array
		    newArray[newArray.length-1] = arg1;		    
	    }

	    //copy the address to the old reference 
	    //the old array values will be deleted by the Garbage Collector
	    arg0 = newArray;
	    
	    return arg0;
	}	
	
	/*Method to add a string into an array of string. Usage :: define an array variable 
	String[] array = null; // you can use your existing array as well.
	array = Utils.addToStringArray(array, string);
	*/
	public static String[] addToStringArray(String[] arg0, String arg1) {
		if(isNull(arg0)) {
			arg0 = new String[]{};
		}
		
	    String[] newArray = Arrays.copyOf(arg0, arg0.length+1);
	    newArray[arg0.length] = arg1;
	    return newArray;
	}
	
	public static String[][] addToStringArray(String[][] arg0, String[] arg1) {
		if(isNull(arg0)) {
			arg0 = new String[][]{};
		}
		
	    String[][] newArray = Arrays.copyOf(arg0, arg0.length+1);
	    newArray[arg0.length] = arg1;
	    return newArray;
	}	
	
    // Function to remove the element 
    public static String[] removeFromArray(String[] array, String element) {
        // return the original array 
        if (array == null
            || element==null) { 
            return array; 
        } 
  
        // Create another array of size one less 
        String[] anotherArray = new String[array.length - 1]; 
  
        // Copy the elements except the index 
        // from original array to the other array 
        for (int i = 0, k = 0; i < array.length; i++) { 
			if(!element.equals(array[i])) {
            // the removal element index 
               anotherArray[k++] = array[i];
		    } 
        } 
  
        // return the resultant array 
        return anotherArray; 
    } 	
	
	/*Method to add a an object into an array of object.
 	Usage :: define an array variable 
	Object[] array = null; // you can use your existing array as well.
	array = Utils.addToObjectArray(array, object);
	*/	
	public static Object[] addToObjectArray(Object[] arg0, Object arg1) {
		if(isNull(arg0)) {
			arg0 = new Object[]{};
		}
		
	    Object[] newArray = Arrays.copyOf(arg0, arg0.length+1);
	    newArray[arg0.length] = arg1;
	    return newArray;
	}
	
	public static Object[][] addToObjectArray(Object[][] arg0, Object[] arg1) {
		if(isNull(arg0)) {
			arg0 = new Object[][]{};
		}
		
	    Object[][] newArray = Arrays.copyOf(arg0, arg0.length+1);
	    newArray[arg0.length] = arg1;
	    return newArray;
	}	
	
	/*Method to add a long into an array of long. Usage :: define an array variable 
	long[] array = null; // you can use your existing array as well.
	array = Utils.addToArray(array, long);
	*/
	public static long[] addToArray(long[] arg0, long arg1) {
		if(isNull(arg0)) {
			arg0 = new long[]{};
		}
		
	    long[] newArray = Arrays.copyOf(arg0, arg0.length+1);
	    newArray[arg0.length] = arg1;
	    return newArray;
	}	
	
	public static int[] addToArray(int[] arg0, int arg1) {
		if(isNull(arg0)) {
			arg0 = new int[]{};
		}
		
	    int[] newArray = Arrays.copyOf(arg0, arg0.length+1);
	    newArray[arg0.length] = arg1;
	    return newArray;
	}	
	
	public static String[] mergeArrays(String[] firstArray, String[] secondArray)  { 
		if(isNull(firstArray)) {
			firstArray = new String[]{};
		}
		
		if(isNull(secondArray)) {
			secondArray = new String[]{};
		}
		
		int fal = firstArray.length;        //determines length of firstArray  
		int sal = secondArray.length;   //determines length of secondArray  
		String[] result = new String[fal + sal];  //resultant array of size first array and second array  
		System.arraycopy(firstArray, 0, result, 0, fal);  
		System.arraycopy(secondArray, 0, result, fal, sal);  
		
		return dropDuplicates(result);	
	} 	
	
	public static long[] mergeArrays(long[] firstArray, long[] secondArray)  { 
		if(isNull(firstArray)) {
			firstArray = new long[]{};
		}
		
		if(isNull(secondArray)) {
			secondArray = new long[]{};
		}		
		
		int fal = firstArray.length;        //determines length of firstArray  
		int sal = secondArray.length;   //determines length of secondArray  
		long[] result = new long[fal + sal];  //resultant array of size first array and second array  
		System.arraycopy(firstArray, 0, result, 0, fal);  
		System.arraycopy(secondArray, 0, result, fal, sal);  
		
		return result;	//leave duplications.
	} 	

	//return a list of object without duplicate values, cast your own objects to a list of objects
	public static List<Object> getCleanList(List<Object> objectsList) {
		
		Map<String, Object> objectsMap = new HashMap<String, Object>();
		
		for(Object object : objectsList) {
			objectsMap.put(object.toString(), object);
		}
		
		List<Object> objects = new ArrayList<Object>();
		
		for(Object object : objectsMap.values()) {
			objects.add(object);
		}		
		return objects;
	}
	
	//get the greatest value from an array
    public static int greatestNumber(int[] nums){
        int greatest = 0;

        for(int n:nums){
            if(greatest < n){
            	greatest =n;
            } 
        }
       //System.out.println("Max Number: "+greatest);
        return greatest;
    }
 
    //get two greatest numbers from an array
    public static int[] twoGreatestNumbers(int[] nums){
    	int[] greatestNumbers = new int[2];
        int numberOne = 0;
        int numberTwo = 0;
        for(int n:nums){
            if(numberOne < n){
            	numberTwo = numberOne;
                numberOne =n;
            } else if(numberTwo < n){
            	numberTwo = n;
            }
        }
        
        greatestNumbers[0] = numberOne; //first element is the greatest number
        greatestNumbers[1] = numberTwo; 
        //System.out.println("First Max Number: "+numberOne);
        //System.out.println("Second Max Number: "+numberTwo);
        return greatestNumbers;
    }
    
    public static boolean stringContains(String string, char ch) {
        int count = 0; 
        for(int i=0;i<string.length();i++) {
            if(string.charAt(i)==ch){
                count++;
            }
        }
        
        return count != 0;
    } 
    
    /*defining a standard regular expression
     *@value    : value to check if it's a good pattern
     *@pattern  : enter the key bundle of the given properties file for the pattern
     *Example   : regEx("003-268-001-2","(\\d{3}-)?\\d{3}-\\d{3}-\\d{1}")== true ? "all is okay" : "all is bad";
     */
    public static boolean regEx(String value,String pattern) {                
		boolean matches = false ;
		Pattern patrn = Pattern.compile(pattern);
                Matcher fit = patrn.matcher(value);
                
        if (fit.matches()) {
           matches = true ;
	    }

		return matches;
    }
    
    /*this method is defined to extract a string between some special character
     *for example : [HELLO] , HELLO is between [ and ] (return HELLO)
     *              "My computer", My computer is between " and " (return My Computer)
     *@charOpen : character to open
     *@charClose: character to close
     *@string   : the string to extract
     **/
    public String stringBetween(String string, char charOpen,char charClose) {
        String ext_str = null;
        int index = string.indexOf(charClose);
        if (index == -1)
            ext_str = string;
        else
            ext_str = string.substring(0, index);  		     
        
        return ext_str.substring(ext_str.lastIndexOf(charOpen)+1).trim();        
    }

    /*this method is defined to extract a string between some special string and character
     *for example : //localhost/ , localhost is between // and / (return localhost)
     *@stringOpen : String to open
     *@charClose: character to close
     *@string   : the string to extract
     **/
    public String stringBetween(String stringOpen,char charClose,String string) {
        String ext_str = null;
        int index = string.indexOf(charClose);
        if (index == -1)
            ext_str = string;
        else
            ext_str = string.substring(0, index);

        return ext_str.substring(ext_str.lastIndexOf(stringOpen)+1).trim();
    }

    /*this method is defined to extract a string between some special character and string
     *for example : "http:// ,http: is between " and // (return http:)
     *@charOpen : character to open
     *@stringClose: String to close
     *@string   : the string to extract
     **/
    public String stringBetween(char charOpen,String stringClose,String string) {
        String ext_str = null;
        int index = string.indexOf(stringClose);
        if (index == -1)
            ext_str = string;
        else
            ext_str = string.substring(0, index);

        return ext_str.substring(ext_str.lastIndexOf(charOpen)+1).trim();
    }

    /*method to extract substring in string by giving the separator
     *@text : text to be extracted (for example : "to be or not to be")
     *@separator : separator wanted to extract the text (for example :' ' for one space,'\t' for tab,etc ...)
     *
     */
    public static String[] split(String text,char separator) {
      int count = 0;
      //determine the number of the substring
      int index = 0;

      do {
           ++count;
           ++index;

           index = text.indexOf(separator,index);
      } while (index != -1);

      //extract the substring into an array
      String[] subStr = new String[count];
      index = 0;
      int endIndex = 0;

      for (int i=0; i<count; i++) {
         endIndex = text.indexOf(separator,index);

         if (endIndex==-1)
             subStr[i] = text.substring(index).trim();
         else
             subStr[i] = text.substring(index,endIndex).trim();

         index = endIndex + 1;
      }

      return subStr; //return the substring values
    } // end of method   

    /*Method to check if a single character appears in a string
     *@string : string to to find the specific character
     *@search : character to find
     * */
    public static boolean containsChar(String string, char search) {
        if (string.length() == 0) return false;
        else return string.charAt(0) == search || containsChar(string.substring(1), search);
    }    
    
    //method to count the number of a character in a string
    public static int countChar(String string, char tocount) {    	
    	int counter = 0;
    	for( int i=0; i<string.length(); i++ ) {
    		if(string.charAt(i) == tocount) {
    			counter++;
    		} 
    	}
    	return counter;   
    } 
    
    /*Method to remove all white spaces appear in a string
     *@string : string contains white space
     *Example : To be or not to be, will return Tobeornottobe
     * */
    public static String removeBlanks(String string) {
    	return string.replaceAll("\\s","");
    }

    //function to get the operating system
    public static String operatingSystem() {
        String operatingSystem = null;
        String OS = System.getProperty("os.name").toLowerCase();

        if (OS.contains("win")) operatingSystem = "windows";
        else if (OS.contains("mac")) operatingSystem = "macos";
        else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) operatingSystem = "linux";
        else if (OS.contains("sunos")) operatingSystem = "solaris";

        return operatingSystem;
    }
    
    //function to create a default temp path into home directory
    public static String defaultTempPath() {
    	String homeDirectory = System.getProperty("user.home");
    	Path path = Paths.get(homeDirectory.concat("/tmp"));
    	//create the directory
    	
    	
    	//create an hidden director for the temp path
    	if(ConvertUtils.isSame(operatingSystem(), "windows")) {
    		try {
    			IoData.createDirectory(path.toString());
    	       //link file to DosFileAttributes
    	      // DosFileAttributes dos = Files.readAttributes(path, DosFileAttributes.class);

    	       //hide the Log file
    	       Files.setAttribute(path, "dos:hidden", true);  //create hidden directory
    		} catch(IOException ioe) {    			
    		}
    	} else if(ConvertUtils.isSame(operatingSystem(), "linux")) {
    		path = Paths.get(homeDirectory.concat("/.tmp")); //create hidden directory
    		IoData.createDirectory(path.toString());
    	}

    	return path.toString();
    } 
    
    //function to shuffle a string
    public static String shuffle(String shuffle) {
        //create an array with each char of @shuffle
        char[] array = shuffle.toCharArray();

        Random rgen = new Random();  // Random number generator

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            char temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return String.copyValueOf(array);
    }
    
    // Iterative function to generate all permutations of a String
    public static String[] permute(String string) {
        // create an empty ArrayList to store (partial) permutations
        List<String> list = new ArrayList<>();

        // initialize the list with the first character of the string
        list.add(String.valueOf(string.charAt(0)));

        // do for every character of the specified string
        for (int i = 1; i < string.length(); i++) {
            // consider previously constructed partial permutation one by one

            // (iterate backwards to avoid ConcurrentModificationException)
            for (int j = list.size() - 1; j >= 0 ; j--) {
                // remove current partial permutation from the ArrayList
                String str = list.remove(j);

                // Insert next character of the specified string in all
                // possible positions of current partial permutation. Then
                // insert each of these newly constructed string in the list

                for (int k = 0; k <= str.length(); k++) {
                    // Advice: use StringBuilder for concatenation
                    list.add(str.substring(0, k) + string.charAt(i) +
                            str.substring(k));
                }
            }
        }

        String[] result = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result; //do not drop duplicate here, leave the result as is, if you need so, do it in your codes outside here.
    }    
    
    public static byte[] readBytesFromFile(String filePath) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;
    }     
    
} //end of the class
