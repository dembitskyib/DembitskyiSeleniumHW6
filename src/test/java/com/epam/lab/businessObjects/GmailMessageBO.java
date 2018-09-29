package com.epam.lab.businessObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.epam.lab.model.Message;
import com.epam.lab.pom.GmailHomePage;

public class GmailMessageBO {
	private final String VALUE_ATTACHED_MESSAGE = "%s: Attaching value '%s' to the '%s' field";
	private String threadName;
	private GmailHomePage gmailHomePage;
	private static final Logger logger = LogManager.getLogger(GmailMessageBO.class);

	public GmailMessageBO(WebDriver driver, int pageUpdateTimeOut) {
		gmailHomePage = new GmailHomePage(driver, pageUpdateTimeOut);
		threadName = Thread.currentThread().getName();
	}

	public void writeEmailAndSave(Message message) {
		logger.info(String.format("%s: Clicking compose button", threadName));
		gmailHomePage.composeClick();
		boolean isBlockOpened = false;
		isBlockOpened = gmailHomePage.isMessageBlockPresent(isBlockOpened);
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, threadName, message.getTo(), "to"));
		gmailHomePage.typeReceiver(message.getTo());
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, threadName, message.getCc(), "cc"));
		gmailHomePage.typeCopyReceiver(message.getCc());
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, threadName, message.getBcc(), "bcc"));
		gmailHomePage.typeHiddenCopyReceiver(message.getBcc());
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, threadName, message.getSubject(), "subject"));
		gmailHomePage.typeSubject(message.getSubject());
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, threadName, message.getText(), "text"));
		gmailHomePage.typeMessage(message.getText());
		logger.info(String.format("%s: Closing message block", threadName));
		gmailHomePage.saveAndClose();
		gmailHomePage.isMessageBlockPresent(isBlockOpened);
	}

	public void openDraftAndSend(Message message) {
		logger.info(String.format("%s: Navigating to draft page", threadName));
		gmailHomePage.draftClick();
		logger.info(String.format("%s: Clicking on last message", threadName));
		gmailHomePage.lastMessageClick();
		logger.info(String.format("%s: Checking saved message fields", threadName));
		if (gmailHomePage.checkComposeFields(message)) {
			gmailHomePage.clickSendButton();
		}
	}

	public boolean isEmailSendingSuccessful() {
		return gmailHomePage.isMessageSent();
	}

}
