package com.epam.lab.businessObjects;

import org.openqa.selenium.WebDriver;

import com.epam.lab.model.Message;
import com.epam.lab.pom.GmailHomePage;

public class GmailMessageBO {
	private GmailHomePage gmailHomePage;

	public GmailMessageBO(WebDriver driver) {
		gmailHomePage = new GmailHomePage(driver);
	}

	public void writeEmailAndSave(Message message, int elementWaitTimeOut) {
		gmailHomePage.composeClick(elementWaitTimeOut);
		boolean isBlockOpened = false;
		isBlockOpened = gmailHomePage.isMessageBlockPresent(elementWaitTimeOut, isBlockOpened);
		gmailHomePage.typeReceiver(message.getTo());
		gmailHomePage.typeCopyReceiver(message.getCc(), elementWaitTimeOut);
		gmailHomePage.typeHiddenCopyReceiver(message.getBcc(), elementWaitTimeOut);
		gmailHomePage.typeSubject(message.getSubject());
		gmailHomePage.typeMessage(message.getText());
		gmailHomePage.saveAndClose(elementWaitTimeOut);
		gmailHomePage.isMessageBlockPresent(elementWaitTimeOut, isBlockOpened);
	}

	public void openDraftAndSend(Message message, String draftLettersURL, int elementWaitTimeOut,
			int pageUpdateTimeOut) {
		gmailHomePage.draftClick(pageUpdateTimeOut);
		gmailHomePage.lastMessageClick(draftLettersURL, pageUpdateTimeOut);
		if (gmailHomePage.checkComposeFields(message.getTo(), message.getCc(), message.getBcc(), message.getSubject(),
				message.getText())) {
			gmailHomePage.clickSendButton();
		}
	}

	public boolean isEmailSendingSuccessful(int timeOut) {
		return gmailHomePage.isMessageSent(timeOut);
	}

}
