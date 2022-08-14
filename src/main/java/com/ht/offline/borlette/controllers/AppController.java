/**
 * AppController.java
 * Created on 2022-08-07
 * Author: Hector Vertus
 */
package com.ht.offline.borlette.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/app")
public class AppController {
	public static final Logger log = LogManager.getLogger(AppController.class);
	
    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        log.info(">------------- Inside download() -------------<");
        try {
        	File fileToDownload = new File("/SoftDev/mobileAPI/hbl/microservices/hbl/app/release/app-release.apk");
	        // Tell the browser the file type to download	         
	        response.setContentType("application/octet-stream");	
	        //Show the download dialog
	        response.setHeader("Content-Disposition","attachment; filename="+fileToDownload.getName());	
	        //This should send the file to the device
	        ServletOutputStream outputStream = response.getOutputStream();
	        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileToDownload));
	        byte[] buffer = new byte[8192]; //8kb buffer
	        int bytesReads = -1;
	        while ((bytesReads = inputStream.read(buffer)) != -1){
	        	outputStream.write(buffer, 0, bytesReads);
	        }
	        
	        inputStream.close();
	        outputStream.flush();		        
        }
        catch(IOException ioe) {
        	log.error(">------------< Unable to download the file:: "+ioe);
        }
        
        log.info(">-----------< Exit from download(HttpServletResponse response)"); 
    }

}
