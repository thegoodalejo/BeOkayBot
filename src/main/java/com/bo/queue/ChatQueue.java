package com.bo.queue;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bo.actions.BotActions;
import com.bo.db.FCS;
import com.bo.models.MessageOptions;
import com.bo.models.MsgAction;
import com.bo.models.User;
import com.bo.singleton.MyDriver;
import com.bo.ui.Ui;
import com.bo.utils.BotWait;
import com.bo.utils.MessageLoader;

public class ChatQueue {

	private static List<User> listNewUsers = new ArrayList<>();
	private static List<User> listToSendMsg = new ArrayList<>();
	private static List<User> listToReadMsg = new ArrayList<>();
	public static List<User> removeList = new ArrayList<>();

	public static void addToNewUserQueue(User user) {
		System.out.println("Adding [" + user.getUserDirection() + "] to readMsgQueue");
		listNewUsers.add(user);
		FCS.getInstance().userUpdate("DATE_TIME",""+ ZonedDateTime.now().toInstant().toEpochMilli(),user.getUserDirection());
		FCS.getInstance().userUpdate("BOT_LISTENING","true",user.getUserDirection());
	}

	public static void addToSend(User user) {
		System.out.println("Adding [" + user.getUserDirection() + "] to sendMsgQueue");
		listToReadMsg.add(user);
		FCS.getInstance().userUpdate("DATE_TIME",""+ ZonedDateTime.now().toInstant().toEpochMilli(),user.getUserDirection());
		FCS.getInstance().userUpdate("BOT_LISTENING","true",user.getUserDirection());
	}

	public static void initQueueFromLastSession() {
		listToReadMsg = FCS.getInstance().getLastSession();
	}

	public static void processList(){
		processNewUsers();
		processUsersToRead();
		MyDriver.instance().refresh();
		BotWait.forElementLong(Ui.sidePanel);
	}

	private static void processUsersToRead() {
		if (listToReadMsg.isEmpty()) return;
		System.out.println("Procesing queued users ¡!");
		for (User user: listToReadMsg) {
			BotWait.seconds(2);
			String response = BotActions.getLastResponse(user.getUserDirection());
			switch (response){
				case "null":
					System.out.println(user.getUserDirection() + " aun no responde...");
				break;
				default:
					System.out.println("Last msg was -> " + response);
					Map<String, MessageOptions> optionsMap;
					try {
						optionsMap = FCS.getInstance().userLastResponseOptions(user.getUserDirection());
					}catch (NullPointerException e){
						removeList.add(user);
						BotActions.sendCommonMsgToUser(user.getUserDirection(),MessageLoader.endSessionMsg);
						break;
					}

					MessageOptions messageOption;
					try {
						messageOption = optionsMap.get(response);
						for (MsgAction action : messageOption.actionsList) {
							action.excecute(user);
						}
						user.setUserLastResponse(messageOption.hookId);
						BotActions.sendMsgToUser(user.getUserDirection(),messageOption.hookId);
					}catch (NullPointerException e){
						BotActions.sendCommonMsgToUser(user.getUserDirection(),MessageLoader.defaultMsg);
						System.out.println("Null respuesta no valida -> " + MessageLoader.defaultMsg);//*/
						System.out.println("q pdo");
					}
				break;
			}
		}
		removeUsersFromList(listToReadMsg);
	}
	private static void processNewUsers() {
		if (listNewUsers.isEmpty()) return;
		System.out.println("Processing new Users ¡¡¡¡");
		for (User user: listNewUsers) {
			BotWait.seconds(2);
			BotActions.sendMsgToUser(user.getUserDirection());
			listToReadMsg.add(user);
		}
		listNewUsers.clear();
	}

	public static List<User> getRequestList() {
		return listToSendMsg;
	}
	public static void removeUsersFromList(List<User> targetList){
		for (User user : removeList) {
			targetList.remove(user);
		}
		removeList.clear();
	}
}

