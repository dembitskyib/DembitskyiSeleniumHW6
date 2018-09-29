package com.epam.lab.businessObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.epam.lab.pom.GmailLoginPage;

public class LoginBO {
	private final String VALUE_ATTACHED_MESSAGE = "%s: Attaching value '%s' to the '%s' field";
	private GmailLoginPage gmailLoginPage;
	private String threadName;
	private static final Logger logger = LogManager.getLogger(LoginBO.class);

	public LoginBO(WebDriver driver, int pageUpdateTimeOut) {
		gmailLoginPage = new GmailLoginPage(driver, pageUpdateTimeOut);
		threadName = Thread.currentThread().getName();
	}

	public void logIn(String email, String password) {
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, threadName, email, "email"));
		gmailLoginPage.typeEmailAndSubmit(email);
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, threadName, password, "password"));
		gmailLoginPage.typePasswordAndSubmit(password);
	}
}
