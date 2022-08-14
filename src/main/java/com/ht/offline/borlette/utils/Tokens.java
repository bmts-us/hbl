/*
 * Tokens.java
 *
 * Created on September 22, 2011, 03:24 PM
 */
package com.ht.offline.borlette.utils;
/**
*
* @author  hvertus
*/
public final class Tokens {
	  	
	  //New Once
	  public static final String BACKUP_PATH	    		= "/opt/web/appdata/isale/";

	  //Default ressource name
	  public static final String RESSOURCE_BUNDLE_NAME	    = "com.ht.inventory.management.i18n.ImsysResource";
	  
	  public static final String MAIN_RESSOURCE_BUNDLE_NAME	= "com.ht.inventory.management.i18n.MainResource";
	  

	 //Webapp engine values-------------------------------------------------------------------------------
	  
	  public static final String CACHE_KEYS_SEQUENCE	    = "keysSequence";
	  
	  public static final String KEY_GENERATED			    = "KeyGen";
	  
	  public static final String SEQUENCE_KEY			    = "SequenceKey";
	  
	  public static final String UNIQUE_ID_GENERATED	    = "UniqueIDGen";

	  public static final String CHOICE_ANY				    = "ANY";
	  
	  public static final String CONNECTED_USER			    = "connectedUser";
	  
	  public static final String CHECKED_MARK_GIF			= "yes.gif";
	  
	  public static final String UNCHECKED_MARK_GIF			= "no.gif";

	  //Report Tokens ------------------------------------------------------------------------------- 	  
	  public static final String REPORT_CONTENT_TYPE    	= "application/pdf";
	  
	  public static final String JASPER_COMPILED_EXT        = ".jasper";
	  
	  public static final String JASPER_COMPILED_EXTENSION  = "jasper";
	  
	  public static final String JASPER_SOURCE_EXT          = ".jrxml";
	  
	  public static final String JASPER_SOURCE_EXTENSION    = "jrxml";	  
	  
	  public static final String PDF_FILE_EXTENSION    		= ".pdf";	  
	  
	  //-------------------------------------------------------------------------------	 
	  
	  public static final String SUCCESS				= "success";
	  
	  public static final String ERROR					= "error";
	  
	  public static final String FAIL				 	= "fail";
	  
	  public static final String WARNING				= "warning";
	    
	  //-------------------------------------------------------------------------------
	  //Mime type for image
	  /*    .jpg  | image/jpeg
	        .gif  | image/gif
	        .png  | image/png
	        .wbmp | image/vnd.wap.wbmp
	        .txt  | text/plain */
	  public static final String JPEG_MIME          = "image/jpeg";
	  
	  public static final String GIF_MIME           = "image/gif";
	  
	  public static final String PNG_MIME           = "image/png";
	  
	  public static final String WBMP_MIME          = "image/vnd.wap.wbmpg";
	  
	  public static final String TXT_MIME           = "text/plain";
	  
	  public static final String URL_DATA_PNG_BASE64_PREFIX = "data:image/png;base64,"; //mostly use by primefaces to convert signature into png.

	  //For image ------------------------------------------------------------------

	  public static final String PICTURE_PREFIX     = "pic";
	  
	  public static final String JPEG_FILE_EXTENSION= ".jpg";
	  //-----------------------------------------------------------------------------
	  //chars to generate unique keys
	  //public static final String RANDOM_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()<>[]{}/";
	  public static final String RANDOM_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	  
	  public static final int    RANDOM_INTEGER= 100000; //random integer will generate with 100000 differents values but u can increase it if wanted
	  

}
	  

