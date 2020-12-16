package io.slack.dao;

import io.slack.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *  * TODO à faire
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class JDBCUserDAO implements DAO<User> {

	private final Connection connection = DatabaseConnection.getConnection();

	@Override
	public User insert(User object) {
		return null;
	}

	@Override
	public User update(User object) {
		return null;
	}

	@Override
	public void delete(String key) {
	}

	@Override
	public User find(String key) throws SQLException {
		String query = "SELECT * FROM user WHERE email = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, key);
			try(ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
				}
			}
		}
		return null;
	}

	@Override
	public List<User> findAll() throws SQLException {
		List<User> users = new ArrayList<>();
		try(Statement statement = connection.createStatement()) {
			try(ResultSet resultSet = statement.executeQuery("SELECT * FROM user")) {
				while (resultSet.next()) {
					User user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
					users.add(user);
				}
			}
		}
		return users;
	}
}
