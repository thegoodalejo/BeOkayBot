package com.bo.threads;

import org.openqa.selenium.WebDriver;

import com.bo.controller.Relay;
import com.bo.queue.ChatQueue;
import com.bo.singleton.MyDriver;

public class MessageListener extends Thread {

	WebDriver driver;

	public MessageListener() {
		this.driver = MyDriver.instance().get();
	}

	public void run() {
		System.out.println("MessageListener is running");
		System.out.println(
				"Queue size -> " + ChatQueue.getRequestList().size() + " , Relay.isChating -> " + Relay.isChating);
		while (true) {
			while (true) {

			}
		}
	}
}
