package com.bo.messages;

import com.bo.db.FCS;
import com.bo.queue.ChatQueue;

public class ShoppingCar extends BasicMessage {

	private String phone;
	private String orderItemsList = "";
	private int responseCount;
	private boolean isErr;
	private boolean isConfirm = false;

	private final String[] messages = { "En este link podras ver nuestro catalogo en linea 🏪"
			+ "\ue008\ue007\ue008Alli podras agregar los productos que quieras comprar al carrito de compras 🛒"
			+ "\ue008\ue007\ue007\ue008https://wa.me/c/573243132255"
			+ "\ue008\ue007\ue007\ue008🤖 Estare esperando hasta que completes tu pedido y lo envies como mensaje",
			"1️⃣ Confirmar" + "\ue008\ue007\ue0080️⃣ Descartar",
			"🤖 Pedido confirmado, Se te pediran datos para envio y facturacion 🧾" };

	public ShoppingCar(String phone) {
		super();
		this.phone = phone;
		this.responseCount = 0;
		isErr = false;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	public ShoppingCar(String phone, int responseCount) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		isErr = false;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	public ShoppingCar(String phone, int responseCount, int isErr) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		if (isErr == 0) {
			this.isErr = true;
			System.out.println("Pregunta: ERR");
		}

	}

	public ShoppingCar(String phone, int responseCount, String orderDetail) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		isConfirm = true;
		this.orderItemsList = orderDetail;
		FCS.getInstance().userUpdate("ORDER_DETAIL", orderItemsList, phone);
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
		if (isErr) {
			this.sendMessage(this.errorMsg);
			isErr = false;
		}
		if (isConfirm) {
			this.sendMessage("🤖: Tu pedido es el siguiente");
			this.sendMessage(orderItemsList);
			isConfirm = false;
		}
		this.sendMessage(messages[responseCount]);
	}

	@Override
	public BasicMessage messageResponse(int index) {
		BasicMessage msgResponse = null;
		switch (responseCount) {
		// Mensaje ver catalogo//
		case 0:
			String response = this.getLastOrder(phone);
			switch (response) {
			case "0": // Tenemos catalogo
				FCS.getInstance().userUpdate("ORDER_STATUS", "Pending", phone);
				msgResponse = new ShoppingCar(phone, 1, this.getOrderDetail());
				ChatQueue.setPending(index, true);
				break;
			case "X":
				msgResponse = new ShoppingCar(phone, responseCount, 1);
				break;
			// *ERROR*//
			default:
				msgResponse = new ShoppingCar(phone, responseCount, 0);
				ChatQueue.setPending(index, true);
				break;
			}
			break;
		// Confirmacion de pedido//
		case 1:
			String response2 = this.getLastMessage(phone);
			switch (response2) {
			case "1": // Confirmar
				msgResponse = new ShoppingCar(phone, 2);
				ChatQueue.setPending(index, true);
				break;
			case "0": // Cancelar
				msgResponse = new Lobby(phone, 1);
				ChatQueue.setPending(index, true);
				break;
			case "X":
				System.out.println("Respuesta enigmatica");
				msgResponse = new ShoppingCar(phone, responseCount, 1);
				break;
			// *ERROR*//
			default:
				System.out.println("Respuesta erronea");
				msgResponse = new ShoppingCar(phone, responseCount, 0);
				ChatQueue.setPending(index, true);
				break;
			}
			break;
		// Pedido Confirmado //
		case 2:
			FCS.getInstance().userUpdate("ORDER_STATUS", "Confirmed", phone);
			msgResponse = new InfoFactura(phone);
			ChatQueue.setPending(index, true);
			break;
		// ***********//
		default:
			msgResponse = new Lobby(phone, 1);
			ChatQueue.setPending(index, true);
			break;
		}

		return msgResponse;
	}

}