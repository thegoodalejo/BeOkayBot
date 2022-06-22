package com.bo.messages;

import com.bo.db.FCS;
import com.bo.queue.ChatQueue;

public class InfoFactura extends BasicMessage {

	private String phone;
	private int responseCount;
	private boolean isErr;

	private final String[] messages = {
			"🤖: Envio y Facturacion 🧾. " + "\ue008\ue007\ue008Nombre Completo"
					+ "\ue008\ue007\ue008Cedula (sin puntos ni comas)" + "\ue008\ue007\ue008Correo electronico"
					+ "\ue008\ue007\ue008Direccion" + "\ue008\ue007\ue008Celular",
			"🤖: Generando tu pedido..." };

	public InfoFactura(String phone) {
		super();
		this.phone = phone;
		this.responseCount = 0;
		isErr = false;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	public InfoFactura(String phone, int responseCount) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		isErr = false;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	public InfoFactura(String phone, int responseCount, int isErr) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		if (isErr == 0) {
			this.isErr = true;
			System.out.println("Pregunta: ERR");
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
			// ***********//
			case 0:
				switch (response) {
				case "X":
					System.out.println("Respuesta enigmatica");
					msgResponse = new InfoFactura(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
					FCS.getInstance().userUpdate("INFO_FACTURA", response, phone);
					msgResponse = new InfoFactura(phone, 1);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// ***********//
			case 1:
				msgResponse = new EndSesion(phone,false);
				ChatQueue.setPending(index, true);
				break;
			// ***********//
			default:
				break;
			}
		}
		return msgResponse;
	}

}
