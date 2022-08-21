package com.bo.models;

import org.openqa.selenium.By;

public class Byy {

	private String xPath;

	public Byy(String xPath) {
		this.xPath = xPath;
	}

	public By get(String arg) {
		return By.xpath(xPath.replace("%", arg));
	}

}
