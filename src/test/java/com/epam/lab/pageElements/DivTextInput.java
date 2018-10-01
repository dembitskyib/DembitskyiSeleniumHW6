package com.epam.lab.pageElements;

import org.openqa.selenium.WebElement;

public class DivTextInput extends Element {

	public DivTextInput(WebElement webElement) {
		super(webElement);
	}

	public void type(String text) {
		super.clear();
		super.sendKeys(text);
	}
}
