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
	private final String MESSAGE_BLOCK_XPATH = "//div[contains(@class, 'nH') and contains(@class ,'Hd')]";
	private WebDriver driver;
	private GmailMessageBlockWidget gmailMessageBlockWidget;
	private int pageUpdateTimeOut;
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

	public GmailHomePage(WebDriver driver, int pageUpdateTimeOut) {
		this.pageUpdateTimeOut = pageUpdateTimeOut;
		this.driver = driver;
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
		gmailMessageBlockWidget = new GmailMessageBlockWidget(driver);
	}

	public void composeClick() {
		composeButton.click(driver, pageUpdateTimeOut);
	}

	public void draftClick() {
		draftButton.click(driver, pageUpdateTimeOut);
	}

	public void lastMessageClick() {
		isURLCorrect("drafts");
		lastMessage.click();
	}

	public void typeReceiver(String receiver) {
		gmailMessageBlockWidget.typeReceiver(receiver);
	}

	public void typeCopyReceiver(String receiver) {
		gmailMessageBlockWidget.typeCopyReceiver(receiver, pageUpdateTimeOut);
	}

	public void typeHiddenCopyReceiver(String receiver) {
		gmailMessageBlockWidget.typeHiddenCopyReceiver(receiver, pageUpdateTimeOut);
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

	public void saveAndClose() {
		gmailMessageBlockWidget.saveAndClose(pageUpdateTimeOut);
	}

	public void clickSendButton() {
		gmailMessageBlockWidget.clickSendButton();
	}

	public boolean isMessageBlockPresent(boolean isOpened) {
		boolean isBlockClosed = isOpened;
		try {
			if (isOpened) {
				(new WebDriverWait(driver, pageUpdateTimeOut)).until(new Function<WebDriver, Boolean>() {
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
				(new WebDriverWait(driver, pageUpdateTimeOut)).until(ExpectedConditions.visibilityOf(messageBlock));
			}
		} catch (Exception ex) {
			isBlockClosed = !isBlockClosed;
		}
		return !isBlockClosed;
	}

	public boolean isURLCorrect(String expectedURL) {
		boolean isComparisionFailed = false;
		try {
			(new WebDriverWait(driver, pageUpdateTimeOut)).until((dr) -> dr.getCurrentUrl().contains(expectedURL));
		} catch (Exception ex) {
			isComparisionFailed = true;
		}
		return !isComparisionFailed;
	}

	public boolean isMessageSent() {
		boolean isFailed = false;
		try {
			(new WebDriverWait(driver, pageUpdateTimeOut)).until(ExpectedConditions.visibilityOf(viewSentMessage));
		} catch (Exception ex) {
			isFailed = true;
		}
		return !isFailed;
	}
}
