package com.bo.messages;

public class EndSesion extends BasicMessage {

	private String phone;
	private boolean isTimeout;

	private final String[] messages = {
			"🤖 Happy se despide, No olvides seguirnos en nuestras redes sociales.\ue008\ue007\ue008https://www.instagram.com/beokay.shop/?hl=es ",
			"Pronto una persona real te contactara" };

	public EndSesion(String phone, boolean isTimeout) {
		super();
		this.phone = phone;
		this.isTimeout = isTimeout;
		System.out.println(this.getClass().getSimpleName());
	}

	@Override
	public void messageRequest() {
		try {
			this.goToPhone(phone);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isTimeout) {
			this.sendMessage(messages[0]);
		} else {
			this.sendMessage(messages[1]);
			this.muteAlerts();
		}
	}

	@Override
	public BasicMessage messageResponse(int index) {
		return null;
	}
}
