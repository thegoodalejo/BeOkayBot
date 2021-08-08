package com.bo.messages;

import com.bo.queue.ChatQueue;

public class InfoFactura extends BasicMessage {

	private String phone;
	private int responseCount;
	private boolean isErr;

	private final String[][] messages = {
			{ "🤖: La informacion que se te pedira \n es para la 🧾facturacion electronica de Be Okay 🍃. ",
					"0⃣ Entendido", "1️⃣ Regresar" },
			{ "", "", "", "", "" } };

	public InfoFactura(String phone) {
		super();
		this.phone = phone;
		this.responseCount = 0;
		isErr = false;
		System.out.println("InfoFactura: " + responseCount);
	}

	public InfoFactura(String phone, int responseCount) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		isErr = false;
		System.out.println("InfoFactura: " + responseCount);
	}

	public InfoFactura(String phone, int responseCount, int isErr) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		if (isErr == 0) {
			this.isErr = true;
			System.out.println("Pregunta: ERR");
		} else {
			System.out.println("No responde aun el usuers");
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
		for (String strings : messages[responseCount]) {
			this.sendMessage(strings);
		}
	}

	@Override
	public BasicMessage messageResponse(int index) {
		BasicMessage msgResponse = null;
		String response = this.getLastMessage(phone);
		System.out.println("messageResponse " + response);
		if (response != null && !response.isEmpty()) {
			switch (responseCount) {
			// ***********//
			case 0:
				switch (response) {
				case "0":
					System.out.println("Registra tus datos");
					msgResponse = new InfoFactura(phone, ++responseCount);
					ChatQueue.setPending(index, true);
					break;
				case "1":
					System.out.println("Continuar sin registrarme");
					break;
				case "X":
					System.out.println("Respuesta enigmatica");
					msgResponse = new InfoFactura(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
					System.out.println("Respuesta erronea");
					msgResponse = new InfoFactura(phone, responseCount, 0);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// ***********//
			case 1:
				switch (response) {
				case "0":
					System.out.println("0️⃣ Menos de 24 años");
					msgResponse = new InfoFactura(phone, ++responseCount);
					break;
				case "1":
					System.out.println("1️⃣ 25-35 años");
					msgResponse = new InfoFactura(phone, ++responseCount);
					break;
				case "2":
					System.out.println("2️⃣ 36-50 años");
					msgResponse = new InfoFactura(phone, ++responseCount);
					break;
				case "3":
					System.out.println("3️⃣ Más de 50 años");
					msgResponse = new InfoFactura(phone, ++responseCount);
					break;
				case "X":
					System.out.println("3️⃣ Más de 50 años");
					msgResponse = new InfoFactura(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
					System.out.println("Respuesta erronea");
					msgResponse = new InfoFactura(phone, responseCount, 0);
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
