package com.bo.models;

import com.bo.messages.BasicMessage;

public class MessageResponse {
	
	private boolean isNext;
	private BasicMessage nextMsg;
	
	public MessageResponse(boolean isNext, BasicMessage nextMsg) {
		super();
		this.isNext = isNext;
		this.nextMsg = nextMsg;
	}
	
	public boolean isNext() {
		return isNext;
	}
	public void setNext(boolean isNext) {
		this.isNext = isNext;
	}
	public BasicMessage getNextMsg() {
		return nextMsg;
	}
	public void setNextMsg(BasicMessage nextMsg) {
		this.nextMsg = nextMsg;
	}
	
	

}
