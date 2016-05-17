package com.o2r.helper;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile; 

public class ValidateUpload {
   public static void validateOfficeData(MultipartFile file){
	   System.out.println("Inside validating"+file.getContentType());
        if(!file.getContentType().equals("application/vnd.ms-excel"))
        {
        	System.out.println(" File type is not excel ");
            throw new MultipartException("Only excel files accepted!");
        }
    }
} 