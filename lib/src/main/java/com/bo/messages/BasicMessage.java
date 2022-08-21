package com.bo.messages;

import java.util.concurrent.TimeUnit;

import com.bo.utils.BotWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bo.singleton.MyDriver;
import com.bo.ui.Ui;

public abstract class BasicMessage {

	private WebDriver driver;
	private static final String LINE = "\ue008\ue007\ue008";
	protected final String errorMsg = "Intenta con una de las opciones anteriores";
	public boolean isLast = false;
	public boolean isResponse = true;

	private final static int TIMEOUT = 1100;
	private final static int HALF_TIMEOUT = TIMEOUT/2;

	protected BasicMessage() {
		this.driver = MyDriver.instance().get();
	}

	public void goToPhone(String phone) {
		BotWait.forElementShort(Ui.searchNumber);
		driver.findElement(Ui.searchNumber).click();
		driver.findElement(Ui.searchNumber).sendKeys(phone);
		By pickNumberResult = By.xpath("//div[@data-testid='cell-frame-container']//span[contains(text(),'"+phone+"')]");
		BotWait.forElementLong(pickNumberResult);
		try {
			driver.findElement(pickNumberResult).click();
		}catch (StaleElementReferenceException e){
			System.out.println("Arleady in last msg element is not attached to the page document");
		}catch (ElementClickInterceptedException e){
			MyDriver.instance().get().navigate().refresh();
		}

		BotWait.forElementShort(Ui.backSearchNumber);
		driver.findElement(Ui.backSearchNumber).click();
		try {
			driver.findElement(Ui.downMsg).click();
			System.out.println("Down to last msg");
		} catch (NoSuchElementException e) {
			System.out.println("Arleady in last msg");
		} catch (StaleElementReferenceException e){
			System.out.println("Arleady in last msg element is not attached to the page document");
		}
		BotWait.forElementShort(Ui.txtChatContainer);
		driver.findElement(Ui.txtChatContainer).click();
	}

	protected void sendMessage(String msg) {
		System.out.println("Sending msg -> " + msg);
		driver.findElement(Ui.txtChat).sendKeys(msg);
		driver.findElement(Ui.btnSendMsg).click();
		System.out.println("Sended...");
	}
	
	protected void muteAlerts() {
		driver.findElement(Ui.optionsMenu).click();
		driver.findElement(Ui.muteNotifications).click();
		driver.findElement(Ui.confirmMute).click();
		try {
			TimeUnit.MILLISECONDS.sleep(TIMEOUT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected String getLastMessage(String phone) {
		goToPhone(phone);
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
		goToPhone(phone);
		String msgType = driver.findElement(Ui.ctrLastMsg).getAttribute("data-id");
		if (msgType.contains("false")) {
			try {
				driver.findElement(Ui.openShopingCar);
				return "0";
			} catch (Exception e) {
				return "N";
			}
			
		} else {
			return "X";
		}
	}

	protected String getLastAddres(String phone) {
		try {
			goToPhone(phone);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msgType = driver.findElement(Ui.ctrLastMsg).getAttribute("data-id");
		if (msgType.contains("false")) {
			try {
				if (driver.findElement(Ui.txtLastAddres).getText().contains("maps")) {
					return "0";
				}
				return "1";// respuesta no es un enlace de maps
			} catch (Exception e) {
				return "1"; // respuesta un href //a
			}
		} else {
			return "X"; // no responde
		}
	}

	protected String getOrderDetail() {
		driver.findElement(Ui.openShopingCar).click();
		try {
			TimeUnit.MILLISECONDS.sleep(HALF_TIMEOUT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Ui.shopingCarContainer));
		int a = driver.findElements(Ui.shopingCarItemList).size();
		StringBuilder str = new StringBuilder();
//		System.out.println(a + " Elementos");
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
