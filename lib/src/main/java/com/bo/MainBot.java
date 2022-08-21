package com.bo;

import com.bo.actions.BotActions;
import com.bo.db.FCS;
import com.bo.models.User;
import com.bo.tasks.QRPop;
import com.bo.utils.MessageLoader;

import com.bo.queue.ChatQueue;
import com.bo.singleton.MyDriver;

public class MainBot {
	public static final boolean online = true;

	public enum DEBUG_LVL {LOW,MEDIUM,FULL}
	public static void main(String[] args){

        int a = 2;

		if(true) {
			/*Load msg structure*/
			MessageLoader.load();
			/*Driver Init*/
			MyDriver.instance().get();
			/*QE pop for WhatsApp*/
			QRPop.openFrame();
			/*Queue pending msg in case unexpected shotdown*/
			ChatQueue.initQueueFromLastSession();
			/*Start to read new msg's and proces them*/
			while(online){
				BotActions.queuePendingMessagesToRead();
				ChatQueue.processList();
			}
		}

	}
	public void initBot() throws InterruptedException {

	}

	public void end(){
		MyDriver.instance().get().close();
	}

}
