package com.epam.lab;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.lab.businessObjects.GmailMessageBO;
import com.epam.lab.businessObjects.LoginBO;
import com.epam.lab.model.User;
import com.epam.lab.model.Users;
import com.epam.lab.parsers.JAXBParser;
import com.epam.lab.parsers.PropertyParser;
import com.epam.lab.parsers.XMLParser;

public class GmailSendTest {
	private final String PROPERTIES_PATH = "src/test/resources/config.properties";
	private Users users;
	private XMLParser xmlParser;
	private PropertyParser propertyParser;
	private String receiver;
	private String copyReceiver;
	private String hiddenCopyReceiver;
	private String subject;
	private String messageText;
	private int elementWaitTimeOut;

	@BeforeClass
	public void parametersSetup() {
		propertyParser = new PropertyParser(PROPERTIES_PATH);
		xmlParser = new XMLParser(propertyParser.getProperty("xmlPath"));
		System.setProperty("webdriver.chrome.driver", propertyParser.getProperty("chromeDriverPath"));
		users = JAXBParser.getUsers(propertyParser.getProperty("usersDataPath"));
		receiver = xmlParser.getProperty("to");
		copyReceiver = xmlParser.getProperty("cc");
		hiddenCopyReceiver = xmlParser.getProperty("bcc");
		subject = xmlParser.getProperty("subject");
		messageText = xmlParser.getProperty("text");
		elementWaitTimeOut = Integer.parseInt(propertyParser.getProperty("pageElementChangeTimeOut"));
	}

	@DataProvider(name = "user-data", parallel = true)
	public Object[] provide() throws Exception {
		Object[] userList = users.getUsers().toArray();
		return userList;
	}

	public WebDriver createDriver() {
		WebDriver chromeDriver = new ChromeDriver();
		chromeDriver.manage().timeouts().implicitlyWait(Integer.parseInt(propertyParser.getProperty("implicitlyWait")),
				TimeUnit.SECONDS);
		return chromeDriver;
	}

	@Test(dataProvider = "user-data", threadPoolSize = 3)
	public void gmailSaveAndSendTest(User user) {
		WebDriver chromeDriver = createDriver();
		chromeDriver.get(xmlParser.getProperty("homePageURL"));
		LoginBO loginBO = new LoginBO(chromeDriver);
		loginBO.logIn(user.getEmail(), user.getPassword(), elementWaitTimeOut);
		GmailMessageBO gmailMessageBO = new GmailMessageBO(chromeDriver);
		gmailMessageBO.writeEmailAndSave(receiver, copyReceiver, hiddenCopyReceiver, subject, messageText,
				elementWaitTimeOut);
		gmailMessageBO.openDraftAndSend(receiver, copyReceiver, hiddenCopyReceiver, subject, messageText,
				xmlParser.getProperty("draftLettersURL"), elementWaitTimeOut,
				Integer.parseInt(propertyParser.getProperty("pageUpdateTimeOut")));
		Assert.assertTrue(gmailMessageBO
				.isEmailSendingSuccessful(Integer.parseInt(propertyParser.getProperty("pageUpdateTimeOut"))));
		chromeDriver.quit();
	}

}
