package io.slack.service;

import io.slack.dao.DAO;
import io.slack.dao.DAOFactory;
import io.slack.dao.JDBCUserDAO;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.utils.EmailUtils;

import java.util.ArrayList;
import java.util.List;

public class UserService {

	private final DAO<User> userDAO = DAOFactory.getUser();

	// User creation
	public Message create(String email, String password, String pseudo) {
		try {
			User user = userDAO.find(email);
			if (user != null) {			// User already exists
				return new Message(403);
			}
			if (!EmailUtils.isEmail(email) || !EmailUtils.isPassword(password)) {	// Invalid email or password
				return new Message(403);
			}
			user = new User(email, password, pseudo);
			userDAO.insert(user);
			return new MessageAttachment<>(200, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// User deletion
	public Message delete(String email) {
		try {
			User user = userDAO.find(email);
			if (user == null) {				// User does not exist
				return new Message(404);
			}
			if (!EmailUtils.isEmail(email)) {	// Invalid email
				return new Message(403);
			}
			userDAO.delete(email);
			return new MessageAttachment<>(200, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// User update
	public Message update(String email) {
		try {
			User user = userDAO.find(email);
			if (user == null) {			// User not found
				return new Message(404);
			}
			if (!EmailUtils.isEmail(email)) {	// Invalid email
				return new Message(403);
			}
			userDAO.update(user);
			return new MessageAttachment<>(200, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// User update with email change
	public Message update(String email, String newEmail) {
		try {
			User user = userDAO.find(email);
			if (user == null) {			// User not found
				return new Message(404);
			}
			if (!EmailUtils.isEmail(email) || !EmailUtils.isEmail(newEmail)) {	// Invalid email
				return new Message(403);
			}
			((JDBCUserDAO)userDAO).update(newEmail, user);
			return new MessageAttachment<>(200, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// Finds User
	public Message get(String email) {
		try {
			User user = userDAO.find(email);
			if (user == null) {			// User not found
				return new Message(404);
			}
			if (!EmailUtils.isEmail(email)) {	// Invalid email
				return new Message(403);
			}
			return new MessageAttachment<>(200, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// Gets all Users
	public Message getAll() {
		try {
			List<User> users = userDAO.findAll();
			if (users.isEmpty()) {			// No Users
				return new Message(404);
			}
			return new MessageAttachment<>(200, (ArrayList) users);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// Connects User
	public Message authenticate(String email, String password) {
		try {
			User user = userDAO.find(email);
			if (user == null) {
				return new Message(404);
			}
			if (!user.getPassword().equals(password)) {
				return new Message(403);
			}
			return new MessageAttachment<>(200, user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

}
