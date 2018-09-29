package com.epam.lab.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.lab.pageElements.Button;
import com.epam.lab.pageElements.TextInput;
import com.epam.lab.utils.CustomFieldDecorator;

public class GmailLoginPage {
	@FindBy(xpath = "//input[@type = 'email']")
	private TextInput emailInput;
	@FindBy(xpath = "//div[@id = 'identifierNext']//span")
	private Button emailSubmit;
	@FindBy(name = "password")
	private TextInput passwordInput;
	@FindBy(css = "#passwordNext")
	private Button passwordSubmit;
	private WebDriver driver;
	private int pageUpdateTimeOut;

	public GmailLoginPage(WebDriver driver, int pageUpdateTimeOut) {
		this.pageUpdateTimeOut = pageUpdateTimeOut;
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
		this.driver = driver;
	}

	public void typeEmailAndSubmit(String login) {
		emailInput.type(login);
		emailSubmit.click(driver, pageUpdateTimeOut);
	}

	public GmailHomePage typePasswordAndSubmit(String password) {
		boolean isPasswordField = true;
		boolean shouldClickWithJS = true;
		passwordInput.type(password, isPasswordField);
		passwordSubmit.click(driver, pageUpdateTimeOut, shouldClickWithJS);
		return new GmailHomePage(driver, pageUpdateTimeOut);
	}

}
