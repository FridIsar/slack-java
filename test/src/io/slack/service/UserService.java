package io.slack.service;

import io.slack.dao.DAO;
import io.slack.dao.DAOFactory;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.utils.EmailUtils;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class UserService {

	private final DAO<User> userDAO = DAOFactory.getUser();

	public Message create(String email, String password, String pseudo) {
		try {
			User user = userDAO.find(email);
			if (user != null) {
				return new Message(403);
			}
			if (!EmailUtils.isEmail(email) || !EmailUtils.isPassword(password)) {
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
