package com.worksap.stm2016.utils;

import java.io.File;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.worksap.stm2016.model.Role;

//import *.SystemUtil;

public class EmailUtils{

	private static JavaMailSenderImpl mailSender;
	private static String from;
	
	static public void InitEmailUtils() {
		mailSender = new JavaMailSenderImpl();
		//mailSender.setHost(SystemUtil.getAppConfig("mail.host"));	//eg. smtp.qq.com
		mailSender.setHost("smtp.163.com");	//eg. smtp.qq.com
		//mailSender.setUsername(SystemUtil.getAppConfig("mail.username"));
		mailSender.setUsername("ascorpior@163.com");
		//mailSender.setPassword(SystemUtil.getAppConfig("mail.password"));
		mailSender.setPassword("aim@future2020");
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true/*SystemUtil.getAppConfig("mail.smtp.auth")*/);	//eg. true
		javaMailProperties.put("mail.smtp.timeout", 25000);
		mailSender.setJavaMailProperties(javaMailProperties);
		//from = SystemUtil.getAppConfig("mail.from");
		//from = "195625741@qq.com";
		from = "ascorpior@163.com";
	}
	
	
	static public Boolean sendNotificationToAdmin(String subject, String content) {
		try {
			/*MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelp = new MimeMessageHelper(message,
					true, "UTF-8");
			messageHelp.setFrom(from,"SEMS");
			int totalAdm = 10;//Integer.parseInt(SystemUtil.getAppConfig("mail.total"));
			for (int i = 0; i < totalAdm; ++i) {
				String curToKey = "mail.to"+i;
				String curTo = "";//SystemUtil.getAppConfig(curToKey);
				messageHelp.setTo(curTo);
				messageHelp.setSubject(subject);
				StringBuffer body = new StringBuffer();
				body.append(content);
				messageHelp.setText(body.toString(), true);
				mailSender.send(message);
			}*/
			return true;
		} catch (Exception e) {
			new Exception("[ERROR]邮件发送时出现未知错误", e).printStackTrace();
			return false;
		}
	}
		
	/**
	 * @throws Exception 
	 * 
	 * @Title: sendMail
	 * @Description: 发送邮件，只发送内容
	 * @param @param to
	 * @param @param subject
	 * @param @param content
	 * @param @throws MessagingException
	 * @param @throws SendFailedException
	 * @param @throws UnknownHostException
	 * @return Boolean 是否成功
	 * @throws
	 */
	static public Boolean sendMail(String to, String subject, String content) {
		try {
			System.out.println("@sending!");
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelp = new MimeMessageHelper(message,
					true, "UTF-8");
			messageHelp.setFrom(from,"STEMS");
			messageHelp.setTo(to);
			messageHelp.setSubject(subject);
			StringBuffer body = new StringBuffer();
			body.append(content);
			messageHelp.setText(body.toString(), true);
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			new Exception("[ERROR]邮件发送时出现未知错误", e).printStackTrace();
			return false;
		}
	}

	/**
	 * @throws Exception 
	 * 
	 * @Title: sendMail
	 * @Description: 发送邮件，携带附件
	 * @param @param to
	 * @param @param subject
	 * @param @param content
	 * @param @param file
	 * @param @throws MessagingException
	 * @param @throws SendFailedException
	 * @param @throws UnknownHostException
	 * @return void
	 * @throws
	 */
	static public void sendMailWithAttach(String to, String subject, String content, File[] fileList){
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true, "UTF-8");
			messageHelper.setFrom(from,"blabla");
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			StringBuffer body = new StringBuffer();
			body.append(content);
			
			messageHelper.setText(body.toString(), true);
			if (fileList != null && fileList.length > 0) {
				for(File file : fileList){
					FileSystemResource fsr = new FileSystemResource(file);
					messageHelper.addInline(MimeUtility.encodeWord(file.getName()), fsr);
				}
			}
			mailSender.send(message);
		} catch (Exception e) {
			new Exception("[ERROR]邮件发送时出现未知错误", e).printStackTrace();
		}
	}
	
	static public boolean notifyAddingEmployeeByEmail(String username, String password, String email, Role role) {
		StringBuffer sb = new StringBuffer();
		String title = "Appointment from ETEMS";
		sb.append("You have been apppointed as a ");
		sb.append(role.getRoleName());
		sb.append(" in the ETEMS.<br>");
		sb.append("This is your account information in ETEMS:<br>");
		sb.append("<strong>Username: ");
		sb.append(username);
		sb.append("</strong><br>");
		sb.append("<strong>Password: ");
		sb.append(password);
		sb.append("</strong><br><br>ETEMS<br>");
		return sendMail(email, title, sb.toString());
		//return true;
	}

//	public static void main(String[] args) {
//		MailUtil mailUtil = new MailUtil();
//		logger.info(mailUtil);
//		try {
//			mailUtil.sendMail("receiveEmailHere", "123", "456");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
