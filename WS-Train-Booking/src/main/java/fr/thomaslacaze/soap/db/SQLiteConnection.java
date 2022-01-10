package fr.thomaslacaze.soap.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnection {

	private String url;
	private static Connection conn;

	public SQLiteConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		this.url = "jdbc:sqlite:" + System.getenv("DATABASE_PATH");
		try {
			SQLiteConnection.conn = DriverManager.getConnection(this.url);
			PreparedStatement statement = conn
					.prepareStatement(
							"CREATE TABLE IF NOT EXISTS User (" + "    userName STRING," + "    password STRING,"
									+ "    id       INTEGER PRIMARY KEY AUTOINCREMENT" + ");",
							Statement.RETURN_GENERATED_KEYS);
			statement.execute(); //Init database if table User are not present
			statement.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public ResultSet selectRows(String sql) {
		Statement statement = null;
		try {
			statement = SQLiteConnection.conn.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet insertData(String sql) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.execute();
			return statement.getGeneratedKeys();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet performQuery(String sql) {
		Statement statement = null;
		try {
			statement = SQLiteConnection.conn.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}