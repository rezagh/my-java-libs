package com.reza.mail;

import org.springframework.core.task.TaskExecutor;

//http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/scheduling.html
public class MailManager{
	private TaskExecutor taskExecutor;

	public MailManager(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	public void sendMail(final String toEmail, final String subject, final String content){
		taskExecutor.execute(new MailSender(toEmail,subject,content));
	}

	public void sendEmailToDefault(final String subject, final String content){
		taskExecutor.execute(new MailSender(subject,content));
	}

	public void sendEmailToDefault( String to, String subject, String content,  String filename){
		taskExecutor.execute(new MailSender(to, subject , content , filename));
	}
}