package io.slack.dao;

import io.slack.model.Channel;
import io.slack.model.Post;
import io.slack.model.User;
import io.slack.service.UserService;

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
		String query = "insert into users (email, password, username, creation_date ) values ( ?, ?,  ?, ? );";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setString(1, object.getEmail());
			statement.setString(2, object.getPassword());
			statement.setString(3, object.getPseudo());
			statement.setDate(4, object.getCreatedAt());
			statement.executeUpdate();
			User user = this.find(object.getEmail());


			/*try(statement.executeUpdate()){
				//resultSet.last();
				//object.setId(resultSet.getInt(1));
				//return object;
			}*/
			return user;
		}
	}

	@Override
	public User update(User object) throws SQLException {
		String query = "update users set email = ? , password = ? ,  username = ? where id = ?;";
		try(PreparedStatement statement= connection.prepareStatement(query)){
			statement.setString(1,object.getEmail());
			statement.setString(2,object.getPassword());
			statement.setString(3,object.getPseudo());
			statement.setInt(4,object.getId());

			try(ResultSet resultSet = statement.executeQuery()){
				return object;
			}
		}
	}

	@Override
	public void delete(String key) throws SQLException {
		String query = "delete from users where email = ? ;";
		try(PreparedStatement statement =connection.prepareStatement(query)){
			statement.setString(1, key);
			try(ResultSet resultSet = statement.executeQuery()){

			}
		}
	}

	@Override
	public User find(String key) throws SQLException {
		String query = "SELECT * FROM users WHERE email = ? UNION SELECT * FROM users WHERE  id = ?;";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, key);
			statement.setString(2, key);
			try(ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					User user= new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
					user.setCreatedAt(resultSet.getDate(5));
					user.setId(resultSet.getInt(1));
					return user;
				}
			}
		}
		return null;
	}

	public int getID(String key) throws Exception{
		String query = "SELECT id FROM users WHERE email = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, key);
			try(ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
			}
		}
		return -1;
	}

	public String getEmail(int id) throws Exception{
		String query = "SELECT * FROM users WHERE id = ?";
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString(2);
				}
			}
		}
		return null;
	}

	@Override
	public List<User> findAll() throws SQLException {
		List<User> users = new ArrayList<>();
		try(Statement statement = connection.createStatement()) {
			try(ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
				while (resultSet.next()) {
					User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
					user.setCreatedAt(resultSet.getDate(5));
					user.setId(resultSet.getInt(1));
					users.add(user);
				}
			}
		}
		return users;
	}
}
