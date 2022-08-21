package com.bo.ui;

import org.openqa.selenium.By;

public class Ui {

	// Left Panel
	public static final By sidePanel = By.xpath("//div[@id='side']");//div[@data-testid and .//span[@aria-label]]
	public static final By unreadMessageContainer = By.xpath("//div[@id='side']//div[@data-testid='cell-frame-container' and .//span[@aria-label]]");
	public static final By unreadMessageTitle = By.xpath("/div[2]/div[1]//span[@title]");
	//public static final By searchNumber = By.xpath("//label[@class='_1Jn3C']//div[contains(@class,'copyable-text')]"); (antes funcionaba con el bot de BO, no se si fue por que era busines)
	public static final By searchNumber = By.xpath("//div[contains(@class,'copyable-text')]");
	public static final By backSearchNumber = By.xpath("//span[@data-icon='x-alt']/..");
	//public static final By pickNumberResult = By.xpath("(//div[@data-testid])[1]");(antes funcionaba)
	//public static final By pickNumberResult = By.xpath("//div[@data-testid='cell-frame-container']//span[contains(text(),{0})]");
	//public static Byy searchNumberResult = new Byy("(//span[@title='%'])[1]");

	// ChatArea
	//public static final By emptyChatArea = By.xpath("//div[@data-asset-intro-image-dark]"); (antes funcionaba)
	//public static final By emptyChatArea = By.xpath("//div[@id='app']/div/div/div[1]");(antes funcionaba)
	public static final By emptyChatArea = By.xpath("//span[@data-testid='intro-md-beta-logo-dark']");
	// **//
	public static final By txtChat = By.xpath("//*[@id='main']/footer//p");
	public static final By txtChatContainer = By.xpath("//*[@id='main']/footer/div[1]");
	public static final By downMsg = By.xpath("//span[@data-testid = 'down']");
	public static final By btnSendMsg = By.xpath("//*[@id='main']/footer/div[1]/div/span[2]/div/div[2]/div[2]/button");
	public static final By ctrLastMsg = By.xpath("//div[@data-id][last()]");
	public static final By txtLastMsg = By.xpath("//div[@data-id][last()]//span[not(@*) and text()]");

	// Shopping Car
	public static final By openShopingCar = By.xpath("//div[@data-id][last()]//div[@title]");
	public static final By shopingCarContainer = By.xpath("//div[contains(@class,'1bLj8')]");
	public static final By shopingCarItemList = By.xpath("//div[contains(@class,'1bLj8')]//div[@class='_3m_Xw']");
	public static final By itemListTitle = By.xpath("//div[contains(@class,'1bLj8')]//span[@title]");
	public static final By itemListAmount = By.xpath("//div[contains(@class,'1bLj8')]//span[contains(text(),'Cant')]");

}
