package io.slack.service;

import io.slack.dao.DAO;
import io.slack.dao.DAOFactory;
import io.slack.model.Channel;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class ChannelService {

	private final DAO<Channel> channelDAO = DAOFactory.getChannel();

	// User is entering a channel
	public void addUser() {}

	// User has published a message into a channel
	public void publishMessage(){}

	// User wants to retrieve all messages from a channel
	public void getMessages() {}

	// User quits a channel
	public void removeUser() {}

}
