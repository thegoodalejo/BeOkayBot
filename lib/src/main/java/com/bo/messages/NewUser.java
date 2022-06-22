package com.bo.messages;

import com.bo.db.FCS;
import com.bo.queue.ChatQueue;

public class NewUser extends BasicMessage {

	private String phone;
	private int responseCount;
	private boolean isErr;

	private final String[] messages = { 
			"Hola, bienvenido a Be Okay, Soy 🤖Happy, el asistente virtual de Be Okay. \ue008\ue007\ue008Queremos saber un poco mas de ti, con qué tipo de alimentación te identificas?"
			+ "\ue008\ue007\ue0080️⃣ Vegano 🌱"
			+ "\ue008\ue007\ue0081️⃣ Vegetariano 🥦"
			+ "\ue008\ue007\ue0082⃣  Ninguno de los anteriores" };

	public NewUser(String phone) {
		super();
		this.phone = phone;
		this.responseCount = 0;
		isErr = false;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	public NewUser(String phone, int responseCount) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		isErr = false;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	public NewUser(String phone, int responseCount, int isErr) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		if (isErr == 0) {
			this.isErr = true;
		}
	}

	@Override
	public void messageRequest() {
		try {
			this.goToPhone(phone);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isErr) {
			this.sendMessage(this.errorMsg);
			isErr = false;
		}
		this.sendMessage(messages[responseCount]);
	}

	@Override
	public BasicMessage messageResponse(int index) {
		BasicMessage msgResponse = null;
		String response = this.getLastMessage(phone);
		System.out.println("messageResponse " + response);
		if (response != null && !response.isEmpty()) {
			switch (responseCount) {
			// Saber Mas de ti....//
			case 0:
				switch (response) {
				case "0":
					msgResponse = new Lobby(phone, 0);
					ChatQueue.setPending(index, true);
					FCS.getInstance().userUpdate("FOOD", "Vegano", phone);
					FCS.getInstance().userUpdate("STATUS", "BO User", phone);
					break;
				case "1":
					msgResponse = new Lobby(phone, 0);
					ChatQueue.setPending(index, true);
					FCS.getInstance().userUpdate("FOOD", "Vegetariano", phone);
					FCS.getInstance().userUpdate("STATUS", "BO User", phone);
					break;
				case "2":
					msgResponse = new Lobby(phone, 0);
					ChatQueue.setPending(index, true);
					FCS.getInstance().userUpdate("FOOD", "NO", phone);
					FCS.getInstance().userUpdate("STATUS", "BO User", phone);
					break;
				case "X":
					msgResponse = new NewUser(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
					msgResponse = new NewUser(phone, responseCount, 0);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// ***********//
			default:
				break;
			}
		}
		return msgResponse;
	}
}
