package com.kiy.cloud.notice;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.*;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public final class Mail {

	private Session session;
	private Transport transport;

	public Mail() {
	}

	/**
	 * 根据邮件地址获取服务MX记录
	 * 
	 * @param address
	 * @return
	 * @throws TextParseException
	 */
	public final static String[] getMXHosts(String address) throws TextParseException {
		String host = null;
		String[] temp = address.split("@");
		if (temp.length == 2) {
			host = temp[1];
		}
		if (host != null && host.length() > 1) {
			Lookup lookup = new Lookup(host, Type.MX);
			lookup.run();
			if (lookup.getResult() == Lookup.SUCCESSFUL) {
				Record[] results = lookup.getAnswers();
				String[] mxHosts = new String[results.length];
				for (int index = 0; index < results.length; index++) {
					Record record = results[index];
					mxHosts[index] = record.getAdditionalName().toString();
				}
				return mxHosts;
			}
		}
		return null;
	}

	/**
	 * 直接连接邮件地址指向的服务器投递邮件
	 * 
	 * @param form
	 * @param to
	 * @param subject
	 * @param content
	 * @throws TextParseException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public final static void sendMX(String form, String form_name, String to, String to_name, String subject, String content) throws TextParseException, MessagingException, UnsupportedEncodingException {
		String[] hosts = getMXHosts(to);
		if (hosts != null && hosts.length > 0) {
			Properties properties = new Properties();
			properties.put("mail.smtp.host", hosts[0]);

			if (form_name != null && form_name.length() > 0)
				form_name = MimeUtility.encodeText(form_name, "utf-8", "B");
			if (to_name != null && to_name.length() > 0)
				to_name = MimeUtility.encodeText(to_name, "utf-8", "B");

			Session session = Session.getInstance(properties, null);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(form, form_name, "utf-8"));
			message.setRecipient(RecipientType.TO, new InternetAddress(to, to_name, "utf-8"));
			message.setSubject(subject, "utf-8");
			message.setContent(content, "text/html;charset=utf-8");

			Transport transport = session.getTransport("smtp");
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
	}

	/**
	 * 登录到SMTP服务器
	 * 
	 * @param username
	 * @param password
	 * @param host
	 * @param port
	 * @throws Exception
	 */
	public final void login(String username, String password, String host, String port) throws Exception {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		session = Session.getInstance(properties, new MailAuthenticator(username, password));
		transport = session.getTransport();
		transport.connect();
	}

	/**
	 * 发送邮件到多个目标地址
	 * 
	 * @param form
	 * @param to
	 * @param subject
	 * @param content
	 * @throws Exception
	 */
	public final void send(String form, String[] to, String subject, String content) throws Exception {
		MimeMessage message = new MimeMessage(session);
		Address[] address = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			address[i] = new InternetAddress(to[i]);
		}
		// 发信地址
		message.setFrom(new InternetAddress(form));
		// 收信地址
		message.setRecipients(RecipientType.TO, address);
		// 邮件主题
		message.setSubject(subject, "utf-8");
		// 邮件内容
		message.setContent(content, "text/html;charset=utf-8");
		// 发送邮件
		transport.sendMessage(message, address);
	}

	/**
	 * 服务送邮件到单个目标地址
	 * 
	 * @param form
	 * @param to
	 * @param subject
	 * @param content
	 * @throws Exception
	 */
	public final void send(String form, String to, String subject, String content) throws Exception {
		MimeMessage message = new MimeMessage(session);
		// 发信地址
		message.setFrom(new InternetAddress(form));
		// 收信地址
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		// 邮件主题
		message.setSubject(subject, "utf-8");
		// 邮件内容
		message.setContent(content, "text/html;charset=utf-8");
		// 发送邮件
		transport.sendMessage(message, new Address[] { new InternetAddress(to) });
	}

	/**
	 * 注销SMTP服务器登录状态
	 * 
	 * @throws Exception
	 */
	public final void logout() throws Exception {
		if (transport != null) {
			transport.close();
		}
		transport = null;
		session = null;
	}

	private final class MailAuthenticator extends Authenticator {
		private String username;
		private String password;

		public MailAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		protected final PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	/**
	 * 测试
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Mail.sendMX("service@teyua.net", "田园宠物", "teyua@qq.com", null, "TEST测试", "TEST测试");
	}
}
