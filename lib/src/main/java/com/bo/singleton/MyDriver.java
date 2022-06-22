package com.bo.singleton;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MyDriver {

	private static MyDriver instance;

	private WebDriver driver;

	private MyDriver() {
		System.setProperty("webdriver.gecko.driver", "src//main//resources//com//bo//driver//geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("https://web.whatsapp.com/");
	}

	public static MyDriver instance() {
		if (instance == null) {
			instance = new MyDriver();
		}
		return instance;
	}

	public WebDriver get() {
		return driver;
	}

}
