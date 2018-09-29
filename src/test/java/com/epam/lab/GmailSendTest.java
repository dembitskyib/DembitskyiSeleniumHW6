package com.epam.lab;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.lab.businessObjects.GmailMessageBO;
import com.epam.lab.businessObjects.LoginBO;
import com.epam.lab.model.User;
import com.epam.lab.model.Users;
import com.epam.lab.utils.DriverPool;
import com.epam.lab.utils.parsers.JAXBParser;
import com.epam.lab.utils.parsers.PropertyParser;
import com.epam.lab.utils.parsers.XMLParser;

public class GmailSendTest {
	private final String PROPERTIES_PATH = "src/test/resources/config.properties";

	private Users users;
	private XMLParser xmlParser;
	private PropertyParser propertyParser;
	private int elementWaitTimeOut;
	private int pageUpdateTimeOut;
	private int implicitlyWait;
	private DriverPool driverPool;

	@BeforeClass
	public void parametersSetup() {
		propertyParser = new PropertyParser(PROPERTIES_PATH);
		xmlParser = new XMLParser(propertyParser.getProperty("xmlPath"));
		System.setProperty("webdriver.chrome.driver", propertyParser.getProperty("chromeDriverPath"));
		users = JAXBParser.getUsers(propertyParser.getProperty("usersDataPath"));
		pageUpdateTimeOut = propertyParser.getIntProperty("pageUpdateTimeOut");
		elementWaitTimeOut = propertyParser.getIntProperty("pageElementChangeTimeOut");
		implicitlyWait = propertyParser.getIntProperty("implicitlyWait");
		driverPool = DriverPool.getInstance(propertyParser.getIntProperty("maxDriverConnections"));
	}

	@Test(dataProvider = "user-data")
	public void gmailSaveAndSendTest(User user) {
		WebDriver chromeDriver = driverPool.getDriver(implicitlyWait);
		chromeDriver.get("https://www.google.com/gmail/");
		LoginBO loginBO = new LoginBO(chromeDriver);
		loginBO.logIn(user.getEmail(), user.getPassword(), elementWaitTimeOut);
		GmailMessageBO gmailMessageBO = new GmailMessageBO(chromeDriver);
		gmailMessageBO.writeEmailAndSave(users.getMessage(), elementWaitTimeOut);
		gmailMessageBO.openDraftAndSend(users.getMessage(), xmlParser.getProperty("draftLettersURL"),
				elementWaitTimeOut, pageUpdateTimeOut);
		Assert.assertTrue(gmailMessageBO.isEmailSendingSuccessful(pageUpdateTimeOut));
		chromeDriver.quit();
	}

	@AfterClass
	public void driversQuit() {
		driverPool.quitAll();
	}

	@DataProvider(name = "user-data", parallel = true)
	public Object[] provide() throws Exception {
		Object[] userList = users.getUsers().toArray();
		return userList;
	}

}
