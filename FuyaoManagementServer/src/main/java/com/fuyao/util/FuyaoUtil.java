package com.fuyao.util;

import java.util.UUID;

import javax.servlet.http.Part;

public class FuyaoUtil {
	public static final String IMAGE_PATH = "E:\\nginx-1.12.1\\nginx-1.12.1\\html\\ImageResource\\";
	
	public static String getImageType(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				if (content.toLowerCase().contains(".jpg")) {
					return ".jpg";
				} else if (content.toLowerCase().contains(".png")) {
					return ".png";
				} else if (content.toLowerCase().contains(".gif")) {
					return ".gif";
				}
			}
		}
		return null;
	}
	
	public static String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	public static String getImageUUID() {
		String s = UUID.randomUUID().toString().toLowerCase();
		s.replace("-","");
		return s;
	}
}
