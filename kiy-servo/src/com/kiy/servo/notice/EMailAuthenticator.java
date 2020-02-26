/**
 * 2017年3月9日
 */
package com.kiy.servo.notice;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class EMailAuthenticator extends Authenticator {

    private String user;
    private String password;

    public EMailAuthenticator(String u, String p) {
	user = u;
	password = p;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
	return new PasswordAuthentication(user, password);
    }
}