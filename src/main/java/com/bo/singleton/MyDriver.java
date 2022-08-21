package com.bo.singleton;

import com.bo.utils.BotWait;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyDriver {

	private static MyDriver instance;

	private WebDriver driver;

	private MyDriver() throws IOException, InterruptedException {
		System.out.println("Setting properties");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Alejo\\IdeaProjects\\BeOkayBot\\lib\\src\\test\\resources\\chromedriver_104.exe");
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(false);
		driver = new ChromeDriver(options);
		driver.get("https://web.whatsapp.com/");
	}

	public static MyDriver instance() {
		if (instance == null) {
			try {
				instance = new MyDriver();
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	public WebDriver get() {
		return driver;
	}
	public void refresh(){
		BotWait.seconds(1);
		for (int i = 0; i < 100; i++) {
			try {
				MyDriver.instance().get().navigate().refresh();
				break;
			}catch (UnhandledAlertException e){
				System.out.println("Pending MSG ...");
				BotWait.halfSecond();
			}
		}
	}

	public void close() {
		driver.quit();
	}

}
