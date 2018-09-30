package com.epam.lab.pageElements;

import org.openqa.selenium.WebElement;

public class TextInput extends Element {

	public TextInput(WebElement webElement) {
		super(webElement);
	}

	public void type(String text) {
		super.clear();
		super.sendKeys(text);
	}
}
