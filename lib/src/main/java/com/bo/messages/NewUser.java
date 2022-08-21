package com.bo.messages;

import com.bo.db.FCS;
import com.bo.queue.ChatQueue;

public class NewUser extends BasicMessage {

	private String phone;
	private int responseCount;
	private boolean isErr;

	private final String[] messages = { 
			"¡Hola! Si estas aqui, es porque Dios quiere hacer un milagro en tu vida."
			+ "\ue008\ue007\ue008Si continuas las conversaciones estas aceptando nuestra política de  (link) \ue008\ue007\ue008"
			+ "\ue008\ue007\ue008Cuentanos, ¿cual es tu necesidad?"
			+ "\ue008\ue007\ue0081 Familiar"
			+ "\ue008\ue007\ue0082 Financiera"
			+ "\ue008\ue007\ue0083 Emocional" ,
			"A continuación, vamos a enviar una oración vía audio." +
			"\ue008\ue007\ue008Si Dios hace un milagro, cuéntanos.\ue008\ue007\ue008\ue008\ue007\ue008"
			+ "Si te quieres seguir comunicado con nosotros, marca."
			+ "\ue008\ue007\ue0081. Sí"
			+ "\ue008\ue007\ue0082. No",
			"¡Gracias por escribirnos!"
			+ "\ue008\ue007\ue008Te bendecimos.\ue008\ue007\ue008"
			+"\ue008\ue007\ue008Visítanos (dirección iglesia) y experimenta el poder de Dios."};

	public NewUser(String phone) {
		super();
		this.phone = phone;
		this.responseCount = 0;
		isErr = false;
		System.out.println("Message pack class -> " + this.getClass().getSimpleName() + " responseCount-> " + responseCount);
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
		this.goToPhone(phone);
		if (isErr) {
			this.sendMessage(this.errorMsg);
			isErr = false;
		}
		this.sendMessage(messages[responseCount]);
	}

	@Override
	public BasicMessage messageResponse(int index) {

		return null;
	}
}
