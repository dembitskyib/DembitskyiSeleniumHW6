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

public class GmailSendTest {
	private final String PROPERTIES_PATH = "src/test/resources/config.properties";
	private final String INITIAL_PAGE = "https://www.google.com/gmail/";
	private Users users;
	private PropertyParser propertyParser;
	private int pageUpdateTimeOut;
	private int implicitlyWait;
	private DriverPool driverPool;

	@BeforeClass
	public void parametersSetup() {
		propertyParser = new PropertyParser(PROPERTIES_PATH);
		System.setProperty("webdriver.chrome.driver", propertyParser.getProperty("chromeDriverPath"));
		users = JAXBParser.getUsers(propertyParser.getProperty("usersDataPath"));
		pageUpdateTimeOut = propertyParser.getIntProperty("pageUpdateTimeOut");
		implicitlyWait = propertyParser.getIntProperty("implicitlyWait");
		driverPool = DriverPool.getInstance(propertyParser.getIntProperty("maxDriverConnections"));
	}

	@Test(dataProvider = "user-data")
	public void gmailSaveAndSendTest(User user) {
		WebDriver chromeDriver = driverPool.getDriver(implicitlyWait);
		chromeDriver.get(INITIAL_PAGE);
		LoginBO loginBO = new LoginBO(chromeDriver, pageUpdateTimeOut);
		loginBO.logIn(user.getEmail(), user.getPassword());
		GmailMessageBO gmailMessageBO = new GmailMessageBO(chromeDriver, pageUpdateTimeOut);
		gmailMessageBO.writeEmailAndSave(users.getMessage());
		gmailMessageBO.openDraftAndSend(users.getMessage());
		Assert.assertTrue(gmailMessageBO.isEmailSendingSuccessful());
		chromeDriver.quit();
	}

	@AfterClass
	public void driversQuit() {
		driverPool.quitAll();
	}

	@DataProvider(name = "user-data", parallel = true)
	public Object[] provide() throws Exception {
		return users.getUsers().toArray();
	}

}
