package com.bo.models;

import com.bo.db.FCS;
import com.bo.messages.BasicMessage;
import com.bo.messages.NewUser;

public class User {
	private String userDirection;
	private String actualMessage;
	private boolean isNewUser = false;

	// New User
	public User(String phone, String actualMessage) {
		this.userDirection = phone;
		this.actualMessage = actualMessage;
	}
	public User(String phone) {
		this.userDirection = phone;
		this.actualMessage = "1";
		this.isNewUser = true;
	}
	public void setUserLastResponse(String lastResponse) {
		FCS.getInstance().userUpdate("LAST_RESPONSE",lastResponse,userDirection);
		actualMessage = lastResponse;
	}
	public String getUserDirection() {
		return userDirection;
	}
	public boolean isNewUser(){
		return isNewUser;
	}
	@Override
	public String toString() {
		System.out.println(
				"Phone [" + userDirection + "] LastResponse: " + actualMessage);
		return "";
	}
}
