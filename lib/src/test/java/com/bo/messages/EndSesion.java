package com.bo.messages;

public class EndSesion extends BasicMessage {

	private String phone;
	private int responseCount;

	private final String[][] messages = { { "🤖 Happy se despide.\ue008\ue007\ue008Vuelve pronto "} };

	public EndSesion(String phone) {
		super();
		this.phone = phone;
		this.responseCount = 0;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	@Override
	public void messageRequest() {
		try {
			this.goToPhone(phone);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.sendMessage(messages[0][0]);
	}

	@Override
	public BasicMessage messageResponse(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}
