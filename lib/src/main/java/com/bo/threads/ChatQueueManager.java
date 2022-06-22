package com.bo.threads;

import java.util.concurrent.TimeUnit;

import com.bo.controller.Relay;

public class ChatQueueManager extends Thread {

	public void run() {
		System.out.println("ChatQueueManager is running");
		while (true) {

			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Relay.isChating = false;
			System.out.println("Relay.isChating -> " + Relay.isChating);
		}
	}
}
