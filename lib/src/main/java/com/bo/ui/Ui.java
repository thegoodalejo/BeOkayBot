package com.bo.ui;

import org.openqa.selenium.By;

import com.bo.models.Byy;

public class Ui {

	// Left Panel
	public static final By sidePanel = By.xpath("//div[@id='side']");
	public static final By unreadMessage = By
			.xpath("//div[@data-testid and .//span[@aria-label] and not(.//span[@data-testid = 'muted'])]");
	public static final By searchNumber = By.xpath("//label[@class='_1Jn3C']//div[contains(@class,'copyable-text')]");
	public static final By backSearchNumber = By.xpath("//button[@class='_28-cz']");
	public static final By pickNumberResult = By.xpath("(//div[@data-testid])[1]");
	public static Byy searchNumberResult = new Byy("(//span[@title='%'])[1]");

	// ChatArea
	public static final By emptyChatArea = By.xpath("//div[@data-asset-intro-image-dark]");
	// **//
//	public static final By txtChat = By.xpath("//div[@data-tab='6']"); ojo EN ALGUN momento cambio de 6 a 9
	public static final By txtChat = By.xpath("//div[@data-tab='9']");
	public static final By downMsg = By.xpath("//span[@data-testid = 'down']");
	public static final By btnSendMsg = By.xpath("//button[@class='_4sWnG']");
	public static final By ctrLastMsg = By.xpath("//div[@data-id][last()]");
	public static final By txtLastMsg = By.xpath("//div[@data-id][last()]//span[not(@*) and text()]");
	public static final By txtLastAddres = By.xpath("//div[@data-id][last()]//a[contains(text(),'maps')]");

	// Shopping Car
	public static final By openShopingCar = By.xpath("//div[@data-id][last()]//div[@title]");
	public static final By shopingCarContainer = By.xpath("//div[contains(@class,'1bLj8')]");
	public static final By shopingCarItemList = By.xpath("//div[contains(@class,'1bLj8')]//div[@class='_3m_Xw']");
	public static final By itemListTitle = By.xpath("//div[contains(@class,'1bLj8')]//span[@title]");
	public static final By itemListAmount = By.xpath("//div[contains(@class,'1bLj8')]//span[contains(text(),'Cant')]");

	// Options
	public static final By optionsMenu = By.xpath("//div[@id='main']//span[@data-testid='menu']");
	public static final By muteNotifications = By.xpath("//div[text()='Silenciar notificaciones']");
	public static final By confirmMute = By.xpath("//div[text()='Silenciar notificaciones']");

}
