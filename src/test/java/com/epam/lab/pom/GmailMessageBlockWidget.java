package com.epam.lab.pom;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.lab.model.Message;
import com.epam.lab.pageElements.Button;
import com.epam.lab.pageElements.Input;
import com.epam.lab.pageElements.DivTextInput;
import com.epam.lab.utils.CustomFieldDecorator;

public class GmailMessageBlockWidget {
	private WebDriver driver;
	@FindBy(css = "*[name='to']")
	private DivTextInput receiverInputField;
	@FindBy(xpath = "//*[@name='to']//following-sibling::div//*[@role='link'][1]")
	private Button copyReceiverButton;
	@FindBy(css = "*[name='cc']")
	private DivTextInput copyReceiverInputField;
	@FindBy(xpath = "//*[@name='cc']//following-sibling::div//*[@role='link'][2]")
	private Button hiddenCopyReceiverButton;
	@FindBy(css = "*[name='bcc']")
	private DivTextInput hiddenCopyReceiverInputField;
	@FindBy(css = "*[name='subjectbox']")
	private DivTextInput subjectInputField;
	@FindBy(xpath = "//*[@role='textbox']")
	private DivTextInput messageInput;
	@FindBy(xpath = "//*[@role='dialog']//img[3]")
	private Button saveAndCloseButton;
	@FindBy(xpath = "//input[@name = 'to']")
	private Input receiverInput;
	@FindBy(xpath = "//input[@name = 'cc']")
	private Input ccInput;
	@FindBy(xpath = "//input[@name = 'bcc']")
	private Input bccInput;
	@FindBy(xpath = "//input[@name = 'subject']")
	private Input subjectInput;

	public GmailMessageBlockWidget(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
	}

	public void typeReceiver(String receiver) {
		receiverInputField.type(receiver);
	}

	public void typeCopyReceiver(String receiver, int timeOut) {
		copyReceiverButton.click(driver, timeOut);
		copyReceiverInputField.type(receiver);
	}

	public void typeHiddenCopyReceiver(String receiver, int timeOut) {
		hiddenCopyReceiverButton.click(driver, timeOut);
		hiddenCopyReceiverInputField.type(receiver);
	}

	public void typeSubject(String subject) {
		subjectInputField.type(subject);
	}

	public void typeMessage(String message) {
		messageInput.type(message);
	}

	public boolean checkComposeFields(Message message) {
		return receiverInput.getValue().equals(message.getTo()) || ccInput.getValue().equals(message.getCc())
				|| bccInput.getValue().equals(message.getBcc()) || subjectInput.getValue().equals(message.getSubject())
				|| messageInput.getText().equals(message.getText()) ? true : false;
	}

	public void saveAndClose(int timeOut) {
		saveAndCloseButton.click(driver, timeOut);
	}

	public void clickSendButton() {
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		messageInput.type(keysPressed);
	}
}
