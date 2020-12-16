package io.slack.dao;

import io.slack.model.Channel;
import io.slack.model.User;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class DAOFactory {

	private static final boolean useJdbcUser = false;
	private static final boolean useJdbcChannel = false;

	public static DAO<User> getUser() {
		if (useJdbcUser) {
			return new JDBCUserDAO();
		}
		return new MemoryUserDAO();
	}

	public static DAO<Channel> getChannel() {
		if(useJdbcChannel)
			 return new JDBCChannelDAO();

		return new MemoryChannelDAO();
	}


	private DAOFactory() {}

}
