package io.slack.dao;

import io.slack.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JDBCUserDAO implements DAO<User> {

	private final Connection connection = DatabaseConnection.getConnection();

	@Override
	public User insert(User object) throws SQLException {
		String query = "insert into user values ( ?, ?,  ?, ? );";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setString(1, object.getEmail());
			statement.setString(2, object.getPassword());
			statement.setString(3, object.getPseudo());
			statement.setDate(4, object.getCreatedAt());

			try(ResultSet resultSet = statement.executeQuery()){
				return object;
			}
		}
	}

	@Override
	public User update(User object) throws SQLException {
		delete( object.getEmail() );
		return insert( object);

	}

	@Override
	public void delete(String key) throws SQLException {
		String query = "delete from user where email = ? ;";
		try(PreparedStatement statement =connection.prepareStatement(query)){
			statement.setString(1, key);
			try(ResultSet resultSet = statement.executeQuery()){

			}
		}
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
