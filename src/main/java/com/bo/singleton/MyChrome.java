package com.bo.singleton;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MyChrome {

	private static MyChrome instance;

	private WebDriver driver;

	private MyChrome() {
		System.setProperty("webdriver.gecko.driver", "src//test//resources//com//bo//driver//geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("https://web.whatsapp.com/");
	}

	public static MyChrome instance() {
		if (instance == null) {
			instance = new MyChrome();
		}
		return instance;
	}

	public WebDriver get() {
		return driver;
	}

}
