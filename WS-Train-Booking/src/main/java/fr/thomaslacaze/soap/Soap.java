package fr.thomaslacaze.soap;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.thomaslacaze.soap.db.SQLiteConnection;

public class Soap {
	protected static String baseURL = "http://localhost:8081";
	protected static SQLiteConnection db = new SQLiteConnection();

	protected String getUser(String userName, String password) {
		ResultSet rs = db.performQuery(
				"SELECT * FROM User WHERE userName = '" + userName + "' AND password = '" + password + "'");
		String userId = null;
		int numberUser = 0;
		try {
			while (rs.next()) { //Check if the combination userName & password are unique
				numberUser += 1; 
				userId = rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberUser == 1 ? userId : "-2"; //If numberUser contains more than 1 users there is a problem
	}
}