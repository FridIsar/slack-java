package io.slack.dao;

import io.slack.model.Channel;
import io.slack.model.User;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class DAOFactory {

	private static final boolean useJdbc = false;

	public static DAO<User> getUser() {
		if (useJdbc) {
			return new JDBCUserDAO();
		}
		return new MemoryUserDAO();
	}

	public static DAO<Channel> getChannel() {
		return new MemoryChannelDAO();
	}


	private DAOFactory() {}

}
