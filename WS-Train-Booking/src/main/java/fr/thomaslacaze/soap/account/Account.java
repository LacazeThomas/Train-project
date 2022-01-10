package fr.thomaslacaze.soap.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import fr.thomaslacaze.soap.Soap;

public class Account extends Soap {

	public String register(String userName, String password) {
		int userID = this.addUser(userName, password);
		switch (userID) {
		case -2:
			return "Can't create your account";
		case -1:
			return "Username already exist";
		default:
			return "Account created, user id: " + userID;
		}
	}

	public String getID(String userName, String password) {
		String userID = this.getUser(userName, password);
		if (userID.isEmpty()) {
			return "Wrong user or password";
		}
		return userID;
	}

	private int addUser(String userName, String password) {
		if (userName.isEmpty() || password.isEmpty()) {
			return -2;
		}

		ResultSet rsExist = Soap.db.selectRows("SELECT userName FROM User WHERE userName = '" + userName + "';");
		try {
			if (rsExist.next())
				return -1;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return -1;
		}

		ResultSet rs = db
				.insertData("INSERT INTO User (userName, password) VALUES  ('" + userName + "', '" + password + "');");
		int userID = -2;
		try {
			if (rs.next())
				userID = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userID;

	}
}