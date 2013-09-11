package com.reza.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlUtil {

	/**
	 * Reads a url content to a string.
	 * @param url
	 * @return The url content or empty string in case of error.
	 */
	public static String readUrlToString(String url){
    	String out = "";
    	BufferedReader in = null;
    	
    	try { 
            URL theUrl = new URL(url); 
            in = new BufferedReader(new InputStreamReader(theUrl.openStream())); 
            String inputLine; 
            
            while ((inputLine = in.readLine()) != null) {  
                out = out + inputLine; 
             }
        } catch (Exception e) { 
            e.printStackTrace();
            
        }finally{
        	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return out;
	}

}
