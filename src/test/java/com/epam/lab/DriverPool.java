package com.epam.lab;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverPool {
	private static int maxConnections;
	private static DriverPool instance;
	private static WebDriver[] drivers;
	private static int counter;

	private DriverPool() {

	}

	public static DriverPool getInstance(int maxCon) {
		if (Objects.isNull(instance)) {
			synchronized (DriverPool.class) {
				instance = new DriverPool();
				maxConnections = maxCon;
				drivers = new WebDriver[maxConnections];
				counter = 0;
			}
		}
		return instance;
	}

	private WebDriver initDriver(int implicitlyWait) {
		WebDriver webDriver = new ChromeDriver();
		webDriver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
		return webDriver;
	}

	public WebDriver getDriver(int implicitlyWait) {
		synchronized (this) {
			drivers[counter % maxConnections] = initDriver(implicitlyWait);
		}
		counter++;
		return drivers[(counter - 1) % maxConnections];
	}

	public void quitAll() {
		for (int i = 0; i < drivers.length; i++) {
			if (Objects.isNull(drivers[i])) {
				drivers[i].quit();
			}
		}
	}
}
