package com.reza.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class ComponentRegistry {
	private static ComponentRegistry instance; 
	private static Log log = LogFactory.getLog(ComponentRegistry.class);
	BeanFactory factory= null;
	
	private ComponentRegistry(){
		loadXML();
	}
	
	public static synchronized ComponentRegistry getInstance()	  {
	    if (instance == null)    {
	      if (log.isDebugEnabled()) log.debug("Creating the first instance of ComponentRegistry");
	      instance = new ComponentRegistry();
	    }   else    {
	    	if (log.isDebugEnabled()) log.debug("Returning the instance of ComponentRegistry");
	    }
	    return instance;
	  }	

	public Object getBean(String beanName)	  {
		return this.factory.getBean(beanName);
//		Object bean = this.OTHER_BEANS.get(beanName);
//	    if (bean != null) return bean;
//	    try
//	    {
//	      bean = this.applicationContext.getBean(beanName);
//	    }
//	    catch (Exception e) {
//	      throw new ComponentRegistryFailureException("Could not find bean [" + beanName + "]", e);
//	    }

	    //return bean;
	  }	
	private void loadXML(){
		Resource xmlResource = new FileSystemResource("WEB-INF/spring-config.xml");
        factory = new XmlBeanFactory(xmlResource);
	}
}
