package com.reza.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.reza.properyfile.FileProperty;

public class MailSender implements Runnable{

	private Session session;
	//private static MailManager instance;
	
//	private static String DEFAULT_FROM_EMAIL;
//	private static String DEFAULT_FROM_EMAIL_PASS;
//	private static String DEFAULT_TO_EMAIL;
	
	private String subject;
	private String content;
	private String toEmail;
	
	private String fromEmail;
	private String fromEmailPass;
	private String attachFilename;
	
	
		
	
	private static Log log = LogFactory.getLog(MailManager.class);
	
//	public static MailManager getInstance(){
//		if (instance == null) instance = new MailManager();
//		return instance;
//	}
	
	private void startup()  {
		FileProperty mailProperties = new FileProperty("../mailer.properties");//out of classes in WEB-INF
		fromEmail = (String) mailProperties.getProperties().get("DEFAULT_FROM_EMAIL");
		fromEmailPass = (String) mailProperties.getProperties().get("DEFAULT_FROM_EMAIL_PASS");
		toEmail = (String) mailProperties.getProperties().get("DEFAULT_TO_EMAIL");

	    Properties props = new Properties();
	    //props.put("mail.host", mailProperties.getProperties().get("HOST"));
	    props.put("mail.smtp.host",mailProperties.getProperties().get("HOST"));
	    props.put("mail.debug",mailProperties.getProperties().get("DEBUG"));
	    props.put("mail.smtp.auth",mailProperties.getProperties().get("AUTH"));
	    //props.put("mail.store.protocol",mailProperties.getProperties().get("STORE_PROTOCOL"));
	    props.put("mail.transport.protocol",mailProperties.getProperties().get("TRANSPORT_PROTOCOL"));
	    props.put("mail.smtp.port",mailProperties.getProperties().get("SMTP_PORT"));
	    props.put("mail.smtp.socketFactory.port",mailProperties.getProperties().get("SMTP_PORT"));
	    props.put("mail.smtp.socketFactory.class",mailProperties.getProperties().get("SSL_FACTORY"));
	    //props.put("mail.smtp.socketFactory.fallback",mailProperties.getProperties().get("FALLBACK"));
		
	    session = Session.getInstance(props,
	    		new javax.mail.Authenticator() {
		    		protected PasswordAuthentication getPasswordAuthentication() {
		    			return new PasswordAuthentication(fromEmail, fromEmailPass);
		    		}
	    		});
	}

	public void run() {
		try {
	    	sendEmail(fromEmail, fromEmailPass,toEmail, subject, content, attachFilename);
	    }
	    catch(Exception ex)
	    {
	        log.fatal("Could not send e-mail [" + ex + "]", ex);                                          
	    }
	}
	
	/**
	 * Sends email to the specified recipient using the default "from" email identified in mailer.properties
	 * @param toEmail
	 * @param subject
	 * @param content
	 */
	public MailSender(final String toEmail, final String subject, final String content){
		startup();//this should be the first line
		this.subject = subject;
		this.content = content;
		this.toEmail = toEmail;
	}

	/**
	 * Sends email to the default recipient identified in mailer.properties
	 * @param subject
	 * @param content
	 */
	public MailSender(final String subject, final String content){
		startup();//this should be the first line
		this.subject = subject;
		this.content = content;
	}

	/**
	 * Sends email to the specified email with attachment
	 * @param to
	 * @param subject
	 * @param content
	 * @param filename for attachment
	 */
	public MailSender(final String to, final String subject, final String content, final String filename){
		startup();//this should be the first line
		this.subject = subject;
		this.content = content;
		this.toEmail = to;
		this.attachFilename = filename;
	}

	//TODO handle exceptions - send something to user

	/**
	 * Sends email 
	 * @param fromEmail
	 * @param fromEmailPassword
	 * @param toEmail
	 * @param subject
	 * @param content
	 * @param attachFilename for attachment
	 */
	private void sendEmail(String fromEmail, String fromEmailPassword, String toEmail, String subject, String content,String attachFilename){
		log.debug("SENDING message from " + fromEmail + " to " + toEmail);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addRecipients(Message.RecipientType.TO, toEmail);
			msg.setSubject(MimeUtility.encodeText(subject,"UTF-8","Q"));
			
			
			if(!StringUtils.isBlank(attachFilename)){
				log.debug("attaching file: " + attachFilename);
				
				Multipart mp = new MimeMultipart();
				
				
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setText(content,"UTF-8");
				
				MimeBodyPart mbp2 = new MimeBodyPart();
			    
				FileDataSource fds = new FileDataSource(new File(attachFilename));
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());
				
				
				mp.addBodyPart(mbp1);
				mp.addBodyPart(mbp2);
				
				msg.setContent(mp);
			}else{
				msg.setText(content,"UTF-8");	
			}
			
			msg.setSender(new InternetAddress(fromEmailPassword));
			Transport t = session.getTransport();
		    t.connect(fromEmailPassword, fromEmailPassword);
		    t.sendMessage(msg, msg.getAllRecipients());
		}catch(Exception e) {
			log.error(e);
		}
	}
}