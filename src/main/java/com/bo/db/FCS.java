package com.bo.db;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bo.models.MessageOptions;
import com.bo.models.User;
import com.bo.queue.ChatQueue;
import com.bo.utils.MessageLoader;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class FCS {

	private static final String PATH = "lib/src/test/resources/com/bo/data/DB.xlsx";
	private static FCS instance;

	private FCS() {
		//System.out.println("FilloConnectionSingleton Init");
	}

	public static FCS getInstance() {
		if (instance == null) {
			instance = new FCS();
		}
		return instance;
	}

	public List<User> getLastSession() {
		List<User> response = new ArrayList<>();
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e1) {
			System.out.println("DB Err, Check DB Path");
		}
		String strQueryUser = "select * from CONTACTOS where CHAT_TO_USER = 'true' AND BOT_LISTENING = 'true'";
		Recordset recordset;
		try {
			recordset = connection.executeQuery(strQueryUser);
			System.out.println("Pendientes de sesion anterior => " + recordset.getCount());
			while (recordset.next()) {
				String userDir = recordset.getField("USER_DIR");
				String lastResponse = recordset.getField("LAST_RESPONSE");
				if(!lastResponse.equals("0")){
					User user = new User(
							recordset.getField(userDir),
							recordset.getField(lastResponse));
					response.add(user);
				}else {
					System.out.println("Adding existing user as new ");
					ChatQueue.addToNewUserQueue(new User(userDir));
				}
			}
			recordset.close();
		} catch (FilloException e) {
			if (e.getMessage().equals("No records found")) {
				System.out.println("No hay Usuarios pendientes por encolar");
			}
		}
		connection.close();
		return response;
	}

	public User userInfo(String userDir) {
		User response = null;
		System.out.println("Looking for UserInfo -> "+ userDir + " in our DB");
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e1) {
			e1.printStackTrace();
		}
		String strQueryUser = "select * from CONTACTOS where USER_DIR = '" + userDir + "'";
		Recordset recordset;
		try {
			recordset = connection.executeQuery(strQueryUser);
			while (recordset.next()) {
				boolean isUserToChat = Boolean.parseBoolean(recordset.getField("CHAT_TO_USER"));
				boolean isUserToQueue = Boolean.parseBoolean(recordset.getField("BOT_LISTENING"));

				if(isUserToChat && !isUserToQueue){
					System.out.println("Arleadyyyyy Registred user " + userDir);
					response = new User(recordset.getField("USER_DIR"),recordset.getField("LAST_RESPONSE"));
				}else {
					System.out.println("Existing user doesnt aply to queue");
					System.out.println("isUserToChat=> " + isUserToChat + " || isUserToQueue=> " + isUserToQueue);
				}
			}
			recordset.close();
		} catch (FilloException e) {
			System.out.println("No records found, New user created with: " + userDir);
			response = new User(userDir);
		}
		connection.close();
		return response;
	}

	public Map<String, MessageOptions> userLastResponseOptions(String userDir) {
		Fillo fillo = new Fillo();
		Connection connection = null;
		String lastResponse = "1";
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e1) {
			e1.printStackTrace();
		}
		String strQueryUser = "select LAST_RESPONSE from CONTACTOS where USER_DIR = '" + userDir + "'";
		Recordset recordset;
		try {
			recordset = connection.executeQuery(strQueryUser);
			while (recordset.next()) {
				lastResponse = recordset.getField("LAST_RESPONSE");
			}
			recordset.close();
		} catch (FilloException e) {
			if (e.getMessage().equals("No records found")) {
				System.out.println("No records found, LAST_RESPONSE of user ->" + userDir);
			}
		}
		connection.close();
		return MessageLoader.messageMap.get(lastResponse).getOptions();
	}

	public void addNewUser(String userDir) {
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e) {
			e.printStackTrace();
		}
		String strAddUser = "INSERT INTO CONTACTOS(USER_DIR,CHAT_TO_USER,BOT_LISTENING,LAST_RESPONSE, DATE_TIME) " +
				"VALUES('" + userDir + "','true','true','1','"+ ZonedDateTime.now().toInstant().toEpochMilli() +"')";
		try {
			connection.executeUpdate(strAddUser);
		} catch (FilloException e) {
			e.printStackTrace();
		}
		connection.close();
	}

	public void userUpdate(String field, String value, String phone) {
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e) {
			e.printStackTrace();
		}
		String strAddUser = "UPDATE CONTACTOS SET " + field + " = '" + value + "' WHERE USER_DIR = '" + phone + "'";
		try {
			connection.executeUpdate(strAddUser);
			System.out.println("Db Update => " + strAddUser);
		} catch (FilloException e) {
			e.printStackTrace();
		}
		connection.close();
	}
}
