package com.epam.lab.pom;

import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.lab.model.Message;
import com.epam.lab.pageElements.Button;
import com.epam.lab.utils.CustomFieldDecorator;

public class GmailHomePage {
	private WebDriver driver;
	private GmailMessageBlockWidget gmailMessageBlockWidget;
	private final String MESSAGE_BLOCK_XPATH = "//div[contains(@class, 'nH') and contains(@class ,'Hd')]";
	@FindBy(xpath = "//div[contains(@class, 'T-I') and contains(@class ,'J-J5-Ji') and contains(@class ,'T-I-KE') and contains(@class ,'L3')]")
	private Button composeButton;
	@FindBy(xpath = "//div[contains(@class, 'nH') and contains(@class ,'Hd')]")
	private WebElement composeTable;
	@FindBy(css = "*[href*='#drafts'")
	private Button draftButton;
	@FindBy(xpath = "//*[@role='main']//tr[contains(@class, 'zA') and contains(@class ,'yO')][1]")
	private WebElement lastMessage;
	@FindBy(xpath = MESSAGE_BLOCK_XPATH)
	private WebElement messageBlock;
	@FindBy(id = "link_vsm")
	private WebElement viewSentMessage;

	public GmailHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
		gmailMessageBlockWidget = new GmailMessageBlockWidget(driver);
	}

	public void composeClick(int timeOut) {
		composeButton.click(driver, timeOut);
	}

	public void draftClick(int timeOut) {
		draftButton.click(driver, timeOut);
	}

	public void lastMessageClick(String draftLettersURL, int timeOut) {
		isURLCorrect(draftLettersURL, timeOut);
		lastMessage.click();
	}

	public void typeReceiver(String receiver) {
		gmailMessageBlockWidget.typeReceiver(receiver);
	}

	public void typeCopyReceiver(String receiver, int timeOut) {
		gmailMessageBlockWidget.typeCopyReceiver(receiver, timeOut);
	}

	public void typeHiddenCopyReceiver(String receiver, int timeOut) {
		gmailMessageBlockWidget.typeHiddenCopyReceiver(receiver, timeOut);
	}

	public void typeSubject(String subject) {
		gmailMessageBlockWidget.typeSubject(subject);
	}

	public void typeMessage(String message) {
		gmailMessageBlockWidget.typeMessage(message);
	}

	public boolean checkComposeFields(Message message) {
		return gmailMessageBlockWidget.checkComposeFields(message);
	}

	public void saveAndClose(int timeOut) {
		gmailMessageBlockWidget.saveAndClose(timeOut);
	}

	public void clickSendButton() {
		gmailMessageBlockWidget.clickSendButton();
	}

	public boolean isMessageBlockPresent(int timeOut, boolean isOpened) {
		boolean isBlockClosed = isOpened;
		try {
			if (isOpened) {
				(new WebDriverWait(driver, timeOut)).until(new Function<WebDriver, Boolean>() {
					public Boolean apply(WebDriver driver) {
						boolean isPresent = true;
						try {
							driver.findElement(By.xpath(MESSAGE_BLOCK_XPATH));
						} catch (Exception ex) {
							isPresent = false;
						}
						return isPresent;
					}
				});
			} else {
				(new WebDriverWait(driver, timeOut)).until(ExpectedConditions.visibilityOf(messageBlock));
			}
		} catch (Exception ex) {
			isBlockClosed = !isBlockClosed;
		}
		return !isBlockClosed;
	}

	public boolean isURLCorrect(String expectedURL, int timeOut) {
		boolean isComparisionFailed = false;
		try {
			(new WebDriverWait(driver, timeOut)).until((dr) -> dr.getCurrentUrl().equals(expectedURL));
		} catch (Exception ex) {
			isComparisionFailed = true;
		}
		return !isComparisionFailed;
	}

	public boolean isMessageSent(int timeOut) {
		boolean isFailed = false;
		try {
			(new WebDriverWait(driver, timeOut)).until(ExpectedConditions.visibilityOf(viewSentMessage));
		} catch (Exception ex) {
			isFailed = true;
		}
		return !isFailed;
	}
}
