package com.bo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bo.messages.BasicMessage;
import com.bo.models.User;
import com.bo.queue.ChatQueue;
import com.bo.singleton.MyDriver;
import com.bo.ui.Ui;

public class MainBot {

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = MyDriver.instance().get();
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Ui.sidePanel));

		ChatQueue.init();

		while (true) {
			// READ INC MSG
			try {
				if (!driver.findElements(Ui.unreadMessage).isEmpty()) {
					System.out.println("Looking unrread msg [" + driver.findElements(Ui.unreadMessage).size() + "]");
					List<WebElement> unreadMsg = driver.findElements(Ui.unreadMessage);
					for (WebElement element : unreadMsg) {
						String phoneNumber = element.getText().substring(0, 15);
						ChatQueue.add(phoneNumber);
					}
				}
			} catch (org.openqa.selenium.ElementClickInterceptedException e) {
				System.out.println("Overload ******");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
//			ChatQueue.popList();
//			System.out.println("*****************");
			TimeUnit.MILLISECONDS.sleep(500);
			// ACTIONS FOR LIST
			if (!ChatQueue.isRequestEmpty()) {
				List<User> temporalUsers = ChatQueue.getRequestList();
				for (int i = 0; i < temporalUsers.size(); i++) {
					if (temporalUsers.get(i).isPending()) {
						System.out.println("messageRequest atemp To [" + temporalUsers.get(i).getPhone() + "]");
						temporalUsers.get(i).msg.messageRequest();
						ChatQueue.setPending(i, false);
					} else {
						System.out.println("messageResponse atemp To [" + temporalUsers.get(i).getPhone() + "]");
						BasicMessage tempMsg = temporalUsers.get(i).msg.messageResponse(i);
						ChatQueue.updateMsg(i, tempMsg);
					}
				}
			} else {
				try {
					MyDriver.instance().get().findElement(Ui.emptyChatArea);
				} catch (NoSuchElementException e) {
					MyDriver.instance().get().navigate().refresh();
					wait.until(ExpectedConditions.visibilityOfElementLocated(Ui.sidePanel));
				}
			}
//			TimeUnit.MILLISECONDS.sleep(1500);
		}
	}
}
