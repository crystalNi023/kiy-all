/**
 * 2017年2月16日
 */
package com.kiy.servo.notice;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.kiy.common.Tool;
import com.kiy.servo.Log;

/**
 * 邮件发送
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class EMail {

    private static final String CHARSET = "UTF-8";
    private static final String MAIL_MINE_TYPE = "text/html;charset=UTF-8";

    private Session session;
    private EMailAuthenticator authenticator;

    public EMail(String host, int port, String user, String pwd) {
	Properties props = new Properties();
	props.setProperty("mail.debug", "true");
	props.setProperty("mail.transport.protocol", "smtp");
	props.setProperty("mail.smtp.host", host);
	props.setProperty("mail.smtp.port", Integer.toString(port));

	if (!Tool.isEmpty(user) && !Tool.isEmpty(pwd)) {
	    props.setProperty("mail.smtp.auth", "true");
	    authenticator = new EMailAuthenticator(user, pwd);
	}

	session = Session.getDefaultInstance(props, authenticator);
    }

    public void send(String subject, String content) {
	MimeMessage message = new MimeMessage(session);
	try {
	    // FROM 发件人(地址,名称,编码)
	    message.setFrom(new InternetAddress("aa@send.com", "USER_AA", CHARSET));
	    // TO 收件人(地址,名称,编码)
	    message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("cc@receive.com", "USER_CC", CHARSET));
	    // TO 增加收件人(地址,名称,编码)
	    message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com", "USER_DD", CHARSET));
	    // CC 抄送(地址,名称,编码)
	    message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress("ee@receive.com", "USER_EE", CHARSET));
	    // BCC 密送(地址,名称,编码)
	    message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", CHARSET));
	    // SUBJECT 邮件标题
	    message.setSubject(subject, CHARSET);
	    // CONTENT 邮件内容
	    message.setContent(content, MAIL_MINE_TYPE);
	    // SENT_DATE 发件时间
	    message.setSentDate(new Date());
	    message.saveChanges();

	    Transport.send(message);
	} catch (UnsupportedEncodingException | MessagingException ex) {
	    Log.error(ex);
	}
    }
}