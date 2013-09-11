package com.reza.properyfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProjectPropertyFile {
	private static Properties prop = new Properties();
	private InputStream is;
	private Log log = LogFactory.getLog(ProjectPropertyFile.class);
	private String filename = "project.property";
	private static ProjectPropertyFile projectProperty;
	
	private ProjectPropertyFile() {
		
		this.log.debug("Reading property file: " + filename);
		this.is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);

		try {
			prop.load(this.is);
			this.is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		if (projectProperty == null){
			projectProperty = new ProjectPropertyFile();
		}
		return (String)prop.get(key);
	}
	public static void main (String args[]) {
		System.out.println(ProjectPropertyFile.getValue("IMAGE_LOCATION"));
	}

}
