/*
 * SpringUtils.java
 *
 * Created on May 13, 2013, 12:24 AM
 */
package com.ht.offline.borlette.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import biz.isman.util.ConvertUtils;

public class SpringUtils {
	
	//protected final Log log = LogFactory.getLog(getClass());
	private static final Log log = LogFactory.getLog(SpringUtils.class);
	
	public static String imagePath = "";//Resources.getKey(Tokens.MAIN_RESSOURCE_BUNDLE_NAME,"webapp.home").concat("/".concat(Resources.getKey(Tokens.MAIN_RESSOURCE_BUNDLE_NAME,"image.temp.path"))); //Get Image from the default path
	
	//method to upload an image
	public static boolean uploadImage(MultipartFile imagefile, String imagepath) {
		log.info("<>----------- Entering insite method :: upload(...)");
		boolean uploaded = false;
		try {
			if(Utils.isNotNull(imagefile)) {			
								
				imagePath= Utils.isNotNull(imagepath) ? imagepath : imagePath;
				
				//log.info("<>----------- Image path to upload :: "+imagePath);
			
				//log.info("<>-------------------<> Image file size :: "+imagefile.getSize());			
				InputStream inputStream = null;
				OutputStream outputStream = null;
				if (imagefile.getSize() > 0) {
					inputStream = imagefile.getInputStream();				
					outputStream = new FileOutputStream(imagePath.concat(imagefile.getOriginalFilename()));
					//log.info("<>-------------------<> Object ouputsStream was created properly.");				
					//log.info("<>-------------------<> File name uploaded is :: "+imagefile.getOriginalFilename());
					int readBytes = 0;
					byte[] buffer = new byte[8192];
					while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {					
						outputStream.write(buffer, 0, readBytes);
					}
					outputStream.close();
					inputStream.close();
					uploaded = true;							
				} 			
			}// else log.info("<>----------- No image to upload.");
		} catch (Exception ex) {
			log.error("??????????? Unable to upload the image : uploadImage(MultipartFile imagefile)", ex);
		}		
		log.info("<>----------- Exit from SpringUtils.uploadImage(...) ");
		return uploaded;
	}
	
	public static boolean uploadImage(MultipartFile imagefile) {
		return uploadImage(imagefile, null);
	}	
		
	
	//Cookie ===================================================================================================================================================	
	public static void createCookie(String cookieName, String cookieValue, HttpServletResponse servletResponse) {
		//log.info("<>------------ inside Create Cookie : createCookie(HttpServletResponse servletResponse)");
		Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(86400); //set expire time to 86400 seconds equal to 24 hours.
        servletResponse.addCookie(cookie);	
        //log.info("<>------------ Cookie ["+cookieName+"] has been created successfully.");
	}
	
	public static String getCookie(String cookieName, HttpServletRequest request) {
		if(ConvertUtils.isBlank(cookieName) || Utils.isNull(request)) {
			return null;
		}
		
		Cookie cookie = null;
		
	    //Get an array of Cookies associated with this domain
	    Cookie[] cookies = request.getCookies();
	    
	    //Get the cookieName Cookie if it exists
	    if (Utils.isNotNull(cookies)){
	    	if(cookies.length > 0) {
		        for (int i = 0; i < cookies.length; i++){
		        	//log.info("<>------------ Checking cookie :: "+cookies[i].getName()+ ", Value :: " + cookies[i].getValue().trim());
		            if (ConvertUtils.isSame(cookies[i].getName(), cookieName)){
		              cookie = cookies[i];
		              break;
		              //log.info("<>------------ Cookie found :: "+cookie.getValue().trim());
		            } 
		        }//end for
	    	}
	    }//end if
	   
	    if(Utils.isNotNull(cookie)) {
		    //log.info("<>------------ Cookie to return :: "+cookie.getValue().trim());		    
		    return  cookie.getValue().trim();
	    } else return null;
	}
	
	public static void removeCookie(String cookieName, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		
	    if (cookies != null) {
	        for (int i = 0; i < cookies.length; i++) {
	        	if(ConvertUtils.isSame(cookieName, cookies[i].getName())) {
		            cookies[i].setValue("");
		            cookies[i].setPath("/");
		            cookies[i].setMaxAge(0);
		            response.addCookie(cookies[i]);
	        	}
	        }
	    }		
	}
	
	public static void eraseCookie(HttpServletRequest request, HttpServletResponse response) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (int i = 0; i < cookies.length; i++) {
	            cookies[i].setValue("");
	            cookies[i].setPath("/");
	            cookies[i].setMaxAge(0);
	            response.addCookie(cookies[i]);
	        }
	    }
	}	  
  
}
