package com.bo.messages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bo.singleton.MyDriver;
import com.bo.ui.Ui;

public abstract class BasicMessage {

	private WebDriver driver;
	private static final String LINE = "\ue008\ue007\ue008";
	protected final String errorMsg = "🤖: Ups... Respuesta incorrecta";
	public boolean isLast = false;
	public boolean isResponse = true;

	private final static int TIMEOUT = 1000;

	protected BasicMessage() {
		this.driver = MyDriver.instance().get();
	}

	public void goToPhone(String phone) throws InterruptedException {
		driver.findElement(Ui.searchNumber).click();
		driver.findElement(Ui.searchNumber).sendKeys(phone);
		TimeUnit.MILLISECONDS.sleep(TIMEOUT);
		driver.findElement(Ui.pickNumberResult).click();
		driver.findElement(Ui.backSearchNumber).click();
		TimeUnit.MILLISECONDS.sleep(TIMEOUT);
		try {
			driver.findElement(Ui.downMsg).click();
		} catch (NoSuchElementException e) {
			TimeUnit.MILLISECONDS.sleep(TIMEOUT);
		}
		driver.findElement(Ui.txtChat).click();
	}

	protected void sendMessage(String msg) {
		driver.findElement(Ui.txtChat).sendKeys(msg);
		driver.findElement(Ui.btnSendMsg).click();
	}

	protected String getLastMessage(String phone) {
		try {
			goToPhone(phone);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msgType = driver.findElement(Ui.ctrLastMsg).getAttribute("data-id");
		if (msgType.contains("false")) {
			try {
				return driver.findElement(Ui.txtLastMsg).getText();
			} catch (NoSuchElementException e) {
				System.out.println("Si hay una respuesta pero no contiene text..");
				return "X";
			}
		} else {
			return "X";
		}
	}

	protected String getLastOrder(String phone) {
		try {
			goToPhone(phone);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msgType = driver.findElement(Ui.ctrLastMsg).getAttribute("data-id");
		if (msgType.contains("false") && driver.findElement(Ui.openShopingCar).isDisplayed()) {
			return "0";
		} else {
			return "X";
		}
	}

	protected String getOrderDetail() {
		driver.findElement(Ui.openShopingCar).click();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Ui.shopingCarContainer));
		int a = driver.findElements(Ui.shopingCarItemList).size();
		StringBuilder str = new StringBuilder();
		System.out.println(a + " Elementos");
		for (int i = 0; i < a; i++) {
			str.append(driver.findElements(Ui.itemListTitle).get(i).getText());
			str.append(" - ");
			str.append(driver.findElements(Ui.itemListAmount).get(i).getText());
			str.append(LINE);
		}
		return str.toString();
	}

	public abstract void messageRequest();

	public abstract BasicMessage messageResponse(int index);

}
