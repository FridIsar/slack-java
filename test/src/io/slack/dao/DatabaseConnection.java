package io.slack.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class DatabaseConnection {

	private static final DatabaseConnection INSTANCE = new DatabaseConnection();

	public static Connection getConnection() {
		return INSTANCE.connection;
	}

	private final Connection connection;

	public DatabaseConnection() {
		connection = init();
	}

	private Connection init() {
		String dbUrl = "jdbc:mysql://db4free.net:3306/javaslack?autoReconnect=true&useSSL=false";
		// prev "jdbc:mysql://localhost:3306/slack";
		String pwd = "password";
		// prev "@Kaizoku2";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dbUrl, "slackroot", pwd); // prev "root"
		} catch (SQLException | ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}

}
