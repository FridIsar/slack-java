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

	private DatabaseConnection() {
		connection = init();
	}

	private Connection init() {
		String dbUrl = "jdbc:mysql://localhost:3306/slack";
		String pwd="@Kaizoku2";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dbUrl, "root", pwd);
		} catch (SQLException | ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}

}
