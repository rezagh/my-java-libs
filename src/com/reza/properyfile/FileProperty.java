package com.reza.properyfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileProperty {
	//private static ReadProperty instance;
	private Properties prop = new Properties();
	//private static String FILENAME = "project.property";
	private InputStream is;
	private Log log = LogFactory.getLog(FileProperty.class);
	
	public FileProperty(String filename){
		log.debug("Reading property file: " + filename);
		is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		//is = FileProperty.class.getResourceAsStream("/"+filename);
		log.debug("InputStream value: " + is);
		try {
			prop.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String args[]) {
		//System.out.println(ReadProperty.getProperty("cache_file"));
		
		
	}
	public Properties getProperties(){
		return prop;
	}
	
//	public String getProperty(String key){
//		if (instance == null){
//			//InputStream is = ReadProperty.class.getResourceAsStream("/"+FILENAME);  
//			//Properties prop = new Properties();
//			try {
//				prop.load(is);
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//String directory = prop.getProperty("cache_file");
//			//System.out.println(directory);
//			try {
//				is.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return prop.getProperty(key);
//			//instance = new ReadProperty(url);
//		}

//		return prop.getProperty(key);
//	}
}
