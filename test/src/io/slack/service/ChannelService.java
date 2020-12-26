package io.slack.service;

import io.slack.dao.DAO;
import io.slack.dao.DAOFactory;
import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Add parameters to update OR add update for each Channel attribute
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

	// Channel deletion
	public Message delete(String name) {
		try {
			Channel channel = channelDAO.find(name);
			if (channel == null) {        // Channel does not exist
				return new Message(404);
			}
			channelDAO.delete(name);
			return new MessageAttachment<>(200, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// Update channel (add all parameters or one update per attribute?)
	public Message update(String name) {
		try {
			Channel channel = channelDAO.find(name);
			if (channel == null) {		// Channel does not exist
				return new Message(404);
			}
			channelDAO.update(channel);
			return new MessageAttachment<Channel>(200, channel);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// Finds channel
	public Message get(String name) {
		try {
			Channel channel = channelDAO.find(name);
			if (channel == null) {        // Channel does not exist
				return new Message(404);
			}
			return new MessageAttachment<>(200, channel);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	// Gets all channels
	public Message getAll() {
		try {
			List<Channel> channels = channelDAO.findAll();
			if (channels.isEmpty()) {        // Channels do not exist
				return new Message(404);
			}
			return new MessageAttachment<ArrayList>(200, (ArrayList) channels);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

}
