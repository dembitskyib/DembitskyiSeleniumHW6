package com.epam.lab.pageElements;

import org.openqa.selenium.WebElement;

public class Input extends Element {

	public Input(WebElement webElement) {
		super(webElement);
	}

	public String getValue() {
		return super.getAttribute("value");
	}

	public void sendKeys(CharSequence... arg0) {
		throw new UnsupportedOperationException(UNSUPPORTED_OPERATION_MESSAGE);
	}
}
