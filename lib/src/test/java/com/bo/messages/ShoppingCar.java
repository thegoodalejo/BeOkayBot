package com.bo.messages;

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
			"0️⃣ Confirmar" + "\ue008\ue007\ue0081️⃣ Descartar", "🤖 Pedido confirmado" };

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
			case "0": // Confirmar
				msgResponse = new ShoppingCar(phone, 2);
				ChatQueue.setPending(index, true);
				break;
			case "1": // Cancelar
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
			msgResponse = new Lobby(phone, 1);
			ChatQueue.setPending(index, true);
			break;
		// ***********//
		default:
			break;
		}

		return msgResponse;
	}

}