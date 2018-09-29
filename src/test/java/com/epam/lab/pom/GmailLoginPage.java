package com.epam.lab.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.lab.pageElements.Button;
import com.epam.lab.pageElements.TextInput;
import com.epam.lab.utils.CustomFieldDecorator;

public class GmailLoginPage {
	private WebDriver driver;
	@FindBy(xpath = "//input[@type = 'email']")
	private TextInput emailInput;
	@FindBy(xpath = "//div[@id = 'identifierNext']//span")
	private Button emailSubmit;
	@FindBy(name = "password")
	private TextInput passwordInput;
	@FindBy(css = "#passwordNext")
	private Button passwordSubmit;

	public GmailLoginPage(WebDriver driver) {
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
		this.driver = driver;
	}

	public void typeEmailAndSubmit(String login, int timeOut) {
		emailInput.type(login);
		emailSubmit.click(driver, timeOut);
	}

	public GmailHomePage typePasswordAndSubmit(String password, int timeOut) {
		boolean isPasswordField = true;
		boolean shouldClickWithJS = true;
		passwordInput.type(password, isPasswordField);
		passwordSubmit.click(driver, timeOut, shouldClickWithJS);
		return new GmailHomePage(driver);
	}

}
