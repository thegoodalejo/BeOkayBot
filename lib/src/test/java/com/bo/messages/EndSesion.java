package com.bo.messages;

public class EndSesion extends BasicMessage {

	private String phone;
	private int responseCount;

	private final String[] messages = { "¡Gracias por escribirnos! \n" +
			"Te bendecimos. \n" +
			"Visítanos (dirección iglesia) y experimenta el poder de Dios."};

	public EndSesion(String phone) {
		super();
		this.phone = phone;
		this.responseCount = 0;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	@Override
	public void messageRequest() {
		this.goToPhone(phone);
		this.sendMessage(messages[0]);
	}

	@Override
	public BasicMessage messageResponse(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}
