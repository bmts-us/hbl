/*
 * KeyGen.java
 *
 * Created on 2018-09-17 @ 08:30am
 */
package com.ht.offline.borlette.utils;
/**
*
* @author Hector Vertus
*/
import java.util.Random;
import java.util.UUID;

public class KeyGen {

	private static final int keylength   = 20;//the default key length is 12, for example : Gt9ju(Cib1ZyAVdf65*=
	private static final Random rnd = new Random();

	//method to generate a random string, it's important to auto-generate primary keys of string
    public static String keyGen() {        
    	String key = null;
        StringBuilder sb = new StringBuilder(keylength);
        Random random = new Random();

        for(int j=0;j<keylength;j++) {
            sb.append(Tokens.RANDOM_STRING.charAt(random.nextInt(Tokens.RANDOM_STRING.length())));
        }
                
        key = sb.toString();
        
        sb.delete(0,keylength);
        
        
        return key;
    }
    
    //same as above
    public static String keyGen(int length) {        
    	String key = null;
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for(int j=0;j<length;j++) {
            sb.append(Tokens.RANDOM_STRING.charAt(random.nextInt(Tokens.RANDOM_STRING.length())));
        }
                
        key = sb.toString();
        
        sb.delete(0,length);

        return key;
    }    
    
    //create a unique identifier, will return a string looks like this :: 3aae7d1a-8799-4a6f-8863-cde6b1782e7b
    public static String uniqueID() {
    	return UUID.randomUUID().toString();
    }
    
    //method to generate a random integer, it's important to generate primary or any integer keys, the number to return is static
    public static String integerKeyGen(){        
        //note a single Random object is reused here
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(Tokens.RANDOM_INTEGER);
        return String.valueOf(randomInt);
    }
        
    //same as integerKeyGen defined above, the unique difference is to specify the number of integer to return
    public static String integerKeyGen(int digCount) {
        StringBuilder sb = new StringBuilder(digCount);
        for(int i=0; i < digCount; i++)
            sb.append((char)('0' + rnd.nextInt(10)));
        return sb.toString();
    }    
    
    //generate an ID, the default pattern is xxx-xxx-xxxx you can modify it 
    public static String gennerateId() {
    	return integerKeyGen(3).concat("-".concat(integerKeyGen(3).concat("-".concat(integerKeyGen(4)))));
    }
    
    //@len is the length of the of the key to generate.
    public static int intKeyGen(int len) {
        if (len > 18)
            throw new IllegalStateException("To many digits");
        
        long tLen = (long) Math.pow(10, len - 1) * 9;

        long number = (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;

        String tVal = number + "";
        if (tVal.length() != len) {
            throw new IllegalStateException("The random number '" + tVal + "' is not '" + len + "' digits");
        }
        
        return Integer.valueOf(tVal);
    }   
    
}
