package com.bo.db;

import java.util.ArrayList;
import java.util.List;

import com.bo.models.User;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class FCS {

	private static final String PATH = "src/test/resources/com/bo/data/DB.xlsx";
	private static FCS instance;

	private FCS() {
		System.out.println("FilloConnectionSingleton Init");
	}

	public static FCS getInstance() {
		if (instance == null) {
			instance = new FCS();
		}
		return instance;
	}

	public User userInfo(String phone) {
		User response = null;
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e1) {
			e1.printStackTrace();
		}
		String strQueryUser = "select * from CONTACTOS where PHONE = '" + phone + "'";
		Recordset recordset;
		try {
			recordset = connection.executeQuery(strQueryUser);
			while (recordset.next()) {
				System.out.println("Arleady Registred");
				response = new User(recordset.getField("PHONE"), recordset.getField("NAME"),
						Boolean.parseBoolean(recordset.getField("IS_QUEUED")), true);
			}
			recordset.close();
		} catch (FilloException e) {
			if (e.getMessage().equals("No records found")) {
				response = new User(phone, false);
				System.out.println("NewUser " + phone);
			}
		}
		connection.close();
		return response;
	}

	public void addNewUser(User user) {
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e) {
			e.printStackTrace();
		}
		String strAddUser = "INSERT INTO CONTACTOS(PHONE,NAME,IS_QUEUED) VALUES('" + user.getPhone() + "','"
				+ user.getName() + "','true')";
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
		String strAddUser = "UPDATE CONTACTOS SET " + field + " = '" + value + "'WHERE PHONE = '" + phone + "'";
		try {
			connection.executeUpdate(strAddUser);
		} catch (FilloException e) {
			e.printStackTrace();
		}
		connection.close();
	}

	public List<User> getRequest() {
		List<User> response = new ArrayList<>();
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e1) {
			e1.printStackTrace();
		}
		String strQueryUser = "select * from CONTACTOS where IS_QUEUED = 'true'";
		Recordset recordset;
		try {
			recordset = connection.executeQuery(strQueryUser);
			System.out.println("Pendientes en fila: " + recordset.getCount());
			while (recordset.next()) {
				System.out.println("NAME " + recordset.getField("NAME") + " PHONE " + recordset.getField("PHONE"));
				User user = new User(recordset.getField("PHONE"), recordset.getField("NAME"),
						Boolean.parseBoolean(recordset.getField("IS_QUEUED")), true);
				response.add(user);
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

	public List<User> getResponse() {
		List<User> response = new ArrayList<>();
		Fillo fillo = new Fillo();
		Connection connection = null;
		try {
			connection = fillo.getConnection(PATH);
		} catch (FilloException e1) {
			e1.printStackTrace();
		}
		String strQueryUser = "select * from CONTACTOS where IS_QUEUED = 'true'";
		Recordset recordset;
		try {
			recordset = connection.executeQuery(strQueryUser);
			System.out.println("Pendientes en fila: " + recordset.getCount());
			while (recordset.next()) {
				System.out.println("NAME " + recordset.getField("NAME") + " PHONE " + recordset.getField("PHONE"));
				User user = new User(recordset.getField("PHONE"), recordset.getField("NAME"),
						Boolean.parseBoolean(recordset.getField("IS_QUEUED")), true);
				response.add(user);
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
}
