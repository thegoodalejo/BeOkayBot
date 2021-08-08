package com.bo.models;

import com.bo.messages.BasicMessage;
import com.bo.messages.Lobby;
import com.bo.messages.NewUser;

public class User {

	public BasicMessage msg;

	private String phone;
	private String name;
	private boolean isQueued;
	private boolean isPending;
	private int counter = 0;
	private boolean isEnding = false;

	// New User
	public User(String phone, boolean isQueued) {
		this.phone = phone;
		this.name = "New User";
		this.isQueued = isQueued;
		this.msg = new NewUser(phone);
		this.isPending = true;
	}

	// Arleady registred user
	public User(String phone, String name, boolean isQueued, boolean isPending) {
		super();
		this.phone = phone;
		this.name = name;
		this.isQueued = isQueued;
		if (name.equals("Registred User")) {
			this.msg = new NewUser(phone);
		} else {
			this.msg = new Lobby(phone);
		}
		this.isPending = isPending;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isQueued() {
		return isQueued;
	}

	public void setQueued(boolean isQueued) {
		this.isQueued = isQueued;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	public BasicMessage getMsg() {
		return msg;
	}

	public void setMsg(BasicMessage msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		System.out.println(
				"Phone [" + phone + "] Name: " + name + " isQueued -> " + isQueued + " isPendingMsg -> " + isPending);
		return "";
	}

	public boolean increaseAtempts() {
		System.out.println((counter+1) + " of " + 5);
		return ++counter > 5;
	}
	public void resetAtempts() {
		counter = 0;
	}
	
	public void setEnding() {
		isEnding = true;
	}
	public boolean isEnding() {
		return isEnding;
	}
}
