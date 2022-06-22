package com.bo.messages;

import com.bo.queue.ChatQueue;

public class Lobby extends BasicMessage {

	private String phone;
	private int responseCount;
	private boolean isErr;

	private final String[] messages = {
			"Soy Happy 🤖 el asistente virtual de Be Okay 🍃..."
					+ "\ue008\ue007\ue008¿Como te puedo ayudar?, recuerda que no interpreto emojis ni notas de voz"
					+ "\ue008\ue007\ue0081️⃣ Comprar" + "\ue008\ue007\ue0082️⃣ Información de nuestros productos"
					+ "\ue008\ue007\ue0080️⃣ Hablar con una persona real 💁🏻‍♀️",
			"1️⃣ Comprar" + "\ue008\ue007\ue0082️⃣ Información de nuestros productos"
					+ "\ue008\ue007\ue0080️⃣ Hablar con una persona real",
			"En Be Okay 🌱☘️producimos alimentos saludables 😁👌🏼, somos un emprendimiento que nació con la idea de promover una cultura de alimentación equilibrada ⚖️. "
					+ "\ue008\ue007\ue008Queremos ofrecer una alternativa saludable que sea beneficioso para tu cuerpo💃🏾"
					+ "\ue008\ue007\ue008🤖Pronto regresaremos a nuestro menu principal...",
			"1️⃣ Proteínas vegetales" + "\ue008\ue007\ue0082️⃣ Aderezos",
			"1️⃣ Definición general" + "\ue008\ue007\ue0082️⃣ Nutrientes" + "\ue008\ue007\ue0083️⃣ Modo de preparación",
			"1️⃣ Definición general" + "\ue008\ue007\ue0082️⃣ Nutrientes" + "\ue008\ue007\ue0083️⃣ Modo de uso",
			"Nuestras proteínas son completamente naturales 🌿, es un producto vegano 🌱 y sin gluten añadido."
					+ "\ue008\ue007\ue008Tenemos dos opciones 🍴de lenteja y de garbanzo ambas por 550 gramos, sin conservantes ni aditivos 👏🏼"
					+ "\ue008\ue007\ue008🤖Pronto regresaremos a nuestro menu principal...",
			"Estas leguminosas son buena fuente de proteína 💪🏼, fibra, vitaminas, minerales 🧬 y no elevan el nivel de colesterol ❤️"
					+ "\ue008\ue007\ue008🤖Pronto regresaremos a nuestro menu principal...",
			"En nuestro Instagram encontrarás el paso a paso para preparar las proteínas 🤗 "
					+ "\ue008\ue007\ue008https://www.instagram.com/p/CSxIP_JJyGB/ "
					+ "\ue008\ue007\ue008🤖Pronto regresaremos a nuestro menu principal...",
			"Nuestros aderezos son veganos 🌱, sin gluten añadido, hemos reducido la cantidad de aceite y de sal 🧂que normalmente tienen este tipo de productos 😲, todo con el fin de cuidarte 😏 y solo usamos aceite de oliva extra virgen 🥳. "
					+ "\ue008\ue007\ue008Contamos con 2 sabores 👀, aderezo de ajonjolí y de semillas de calabaza, ambos por 170 gramos ⬅️"
					+ "\ue008\ue007\ue008🤖Pronto regresaremos a nuestro menu principal...",
			"El ajonjolí es rico en Omega 3 ❤️, fibra, potasio 🏃🏻‍♂️, calcio 🦷. "
					+ "\ue008\ue007\ue008Las semillas de calabaza son buena fuente de proteína 💪🏼, Omega 3 💕, fibra y otros minerales 👌🏼"
					+ "\ue008\ue007\ue008🤖Pronto regresaremos a nuestro menu principal...",
			"Nuestros aderezos los puedes untar en pan 🍞, tostadas, galletas, verduras 🥦, sobre pollo 🐔🤯 si así lo quieres 😉 entre otros cosas....."
					+ "\ue008\ue007\ue008🤖Pronto regresaremos a nuestro menu principal...",
			"Comprar" };

	public Lobby(String phone) {
		super();
		this.phone = phone;
		this.responseCount = 0;
		isErr = false;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	public Lobby(String phone, int responseCount) {
		super();
		this.phone = phone;
		this.responseCount = responseCount;
		isErr = false;
		System.out.println(this.getClass().getSimpleName() + " " + responseCount);
	}

	public Lobby(String phone, int responseCount, int isErr) {
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
//		System.out.println("messageResponse " + response);
		if (response != null && !response.isEmpty()) {
			switch (responseCount) {
			// Menu con Saludo//
			case 0:
				switch (response) {
				case "1": // Comprar
					msgResponse = new ShoppingCar(phone);
					ChatQueue.setPending(index, true);
					break;
				case "2": // Informacion productos
					msgResponse = new Lobby(phone, 3);
					ChatQueue.setPending(index, true);
					break;
				case "0": // Persona real
					msgResponse = new EndSesion(phone,false);
					ChatQueue.setPending(index, true);
					break;
				case "X":
					msgResponse = new Lobby(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
					msgResponse = new Lobby(phone, responseCount, 0);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// Menu sin saludo//
			case 1:
				switch (response) {
				case "1": // Comprar
					msgResponse = new ShoppingCar(phone);
					ChatQueue.setPending(index, true);
					break;
				case "2": // Informacion productos
					msgResponse = new Lobby(phone, 3);
					ChatQueue.setPending(index, true);
					break;
				case "0": // Persona real
					msgResponse = new EndSesion(phone,false);
					ChatQueue.setPending(index, true);
					break;
				case "X":
//					System.out.println("Respuesta enigmatica");
					msgResponse = new Lobby(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
//					System.out.println("Respuesta erronea");
					msgResponse = new Lobby(phone, responseCount, 0);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// Quienes somos //
			case 2:
				switch (response) {
				case "0": // Regresar
					msgResponse = new Lobby(phone, 1);
					ChatQueue.setPending(index, true);
					break;
				case "X": // Regresar
					msgResponse = new Lobby(phone, 1);
					ChatQueue.setPending(index, true);
					break;
				// *ERROR*//
				default: // Regresar
					msgResponse = new Lobby(phone, 1);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// Informacion Productos//
			case 3:
				switch (response) {
				case "1": // Proteinas
					msgResponse = new Lobby(phone, 4);
					ChatQueue.setPending(index, true);
					break;
				case "2": // Aderezos
					msgResponse = new Lobby(phone, 5);
					ChatQueue.setPending(index, true);
					break;
				case "X":
					msgResponse = new Lobby(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
					msgResponse = new Lobby(phone, responseCount, 0);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// Proteinas vegetales //
			case 4:
				switch (response) {
				case "1": // Definicion general
					msgResponse = new Lobby(phone, 6);
					ChatQueue.setPending(index, true);
					break;
				case "2": // Nutrientes
					msgResponse = new Lobby(phone, 7);
					ChatQueue.setPending(index, true);
					break;
				case "3": // Modo de preparacion
					msgResponse = new Lobby(phone, 8);
					ChatQueue.setPending(index, true);
					break;
				case "X":
					msgResponse = new Lobby(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
					msgResponse = new Lobby(phone, responseCount, 0);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// Aderezos //
			case 5:
				switch (response) {
				case "1": // Definicion general
					msgResponse = new Lobby(phone, 9);
					ChatQueue.setPending(index, true);
					break;
				case "2": // Nutrientes
					msgResponse = new Lobby(phone, 10);
					ChatQueue.setPending(index, true);
					break;
				case "3": // Modo de Uso
					msgResponse = new Lobby(phone, 11);
					ChatQueue.setPending(index, true);
					break;
				case "X":
					msgResponse = new Lobby(phone, responseCount, 1);
					break;
				// *ERROR*//
				default:
					msgResponse = new Lobby(phone, responseCount, 0);
					ChatQueue.setPending(index, true);
					break;
				}
				break;
			// ***********//
			default:
				msgResponse = new Lobby(phone, 1);
				ChatQueue.setPending(index, true);
				break;
			}
		}
		return msgResponse;
	}

}
