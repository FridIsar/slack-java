package io.slack.service;

import io.slack.dao.DAO;
import io.slack.dao.DAOFactory;
import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.utils.EmailUtils;

import java.util.ArrayList;

/**
 * TODO change class of messages from String to ChatMessage
 */

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class ChannelService {

	private final DAO<Channel> channelDAO = DAOFactory.getChannel();

	// Channel creation
	public Message create(String name, User admin) {
		try {
			Channel channel = channelDAO.find(name);
			if (channel != null) {		// Channel already exists
				return new Message(403);
			}
			/*if (!name.isValid()) {	// Invalid channel name
				return new Message(403);
			}*/
			channel = new Channel(name, admin);
			channelDAO.insert(channel);
			return new MessageAttachment<Channel>(200, channel);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// User is entering a channel
	public Message addUser(String channelName, User user)	{
		try {
			Channel channel = channelDAO.find(channelName);
			if (channel == null) { 				// Channel not found
				return new Message(404);
			}
			if (channel.getUsers().contains(user)) { 	// User already in channel
				return new Message(400);
			}
			channel.addUser(user);
			channelDAO.update(channel);
			return new MessageAttachment<Channel>(200, channel);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// User has published a message into a channel
	public Message publishMessage(String channelName, String message, User user)	{
		try {
			Channel channel = channelDAO.find(channelName);
			if (channel == null) { 				// Channel not found
				return new Message(404);
			}
			if (!channel.getUsers().contains(user)) {	// User not in channel
				return new Message(403);
			}
			// channel.addMessage(message);
			channelDAO.update(channel);
			return new MessageAttachment<Channel>(200, channel);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// User wants to retrieve all messages from a channel
	public Message getMessages(String channelName, User user)	{
		try {
			Channel channel = channelDAO.find(channelName);
			if (channel == null) { 				// Channel not found
				return new Message(404);
			}
			if (!channel.getUsers().contains(user)) { 	// User not in channel
				return new Message(403);
			}
			ArrayList<String> messages = new ArrayList<String>();
			// messages = channel.getMessages();
			return new MessageAttachment<ArrayList>(200, messages);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// User quits a channel
	public Message removeUser(String channelName, User user)	{
		try {
			Channel channel = channelDAO.find(channelName);
			if (channel == null) { 				// Channel not found
				return new Message(404);
			}
			if (!channel.getUsers().contains(user)) { 	// User not in channel
				return new Message(403);
			}
			channel.removeUser(user);
			channelDAO.update(channel);
			return new MessageAttachment<Channel>(200, channel);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

}
