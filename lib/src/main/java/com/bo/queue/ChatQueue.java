package com.bo.queue;

import java.util.ArrayList;
import java.util.List;

import com.bo.db.FCS;
import com.bo.messages.BasicMessage;
import com.bo.messages.EndSesion;
import com.bo.models.User;

public class ChatQueue {

	private static List<User> listRequest = new ArrayList<>();

	public static void add(String phone) {
		User user;
		user = FCS.getInstance().userInfo(phone);
		if (user.getStatus().equals("New User")) {
			user.setStatus("Registred User");
			FCS.getInstance().addNewUser(user);
		}
		if (!user.isQueued()) {
			user.setQueued(true);
			FCS.getInstance().userUpdate("IS_QUEUED", "true", user.getPhone());
			listRequest.add(user);
		}
		user.toString();
	}

	public static void setPending(int index, boolean isPending) {
		listRequest.get(index).setPending(isPending);
		listRequest.get(index).resetAtempts();
	}

	public static void init() {
		listRequest = FCS.getInstance().getRequest();
	}

	public static boolean isRequestEmpty() {
		List<User> tempListRequest = new ArrayList<>();
		tempListRequest.addAll(listRequest);
		for (User user : tempListRequest) {
			if(user.isEnding()) {
				listRequest.remove(user);
			}
		}
		return listRequest.size() < 1;
	}

	public static List<User> getRequestList() {
		return listRequest;
	}

	public static void clearRequest() {
		listRequest.clear();
	}
	
	public static void updateMsg(int index, BasicMessage newMsg) {
		if (listRequest.get(index).increaseAtempts() || newMsg == null) {
			System.out.println("EndSesion");
			FCS.getInstance().userUpdate("IS_QUEUED", "false", listRequest.get(index).getPhone());
			new  EndSesion(listRequest.get(index).getPhone(),true).messageRequest();
			listRequest.get(index).setEnding();
		}else {
//			System.out.println(listRequest.get(index).getPhone() + " Updated");
			listRequest.get(index).setMsg(newMsg);
		}
	}
	
	public static void popList() {
		for (User user : listRequest) {
			user.toString();
		}
	}
}
