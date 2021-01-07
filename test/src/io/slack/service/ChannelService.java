package io.slack.service;

import io.slack.dao.DAO;
import io.slack.dao.DAOFactory;
import io.slack.dao.JDBCChannelDAO;
import io.slack.dao.JDBCUserDAO;
import io.slack.model.Channel;
import io.slack.model.Member;
import io.slack.model.Post;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.util.ArrayList;
import java.util.List;

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
			channel = channelDAO.insert(channel);
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
			PostService ps = new PostService();
			Message message = ps.getAllFromChannel(channel);
			if (message.hasAttachment())	{
				ArrayList<Post> posts = (ArrayList) ((MessageAttachment) message).getAttachment();
				for (Post post : posts)	{
					ps.delete(post);
				}
			}
			MemberService ms = new MemberService();
			Message message2 = ms.getAllFromChannel(channel);
			if (message.hasAttachment())	{
				ArrayList<User> users = (ArrayList) ((MessageAttachment) message2).getAttachment();
				for (User user : users)	{
					ms.delete(name, user.getEmail());
				}
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
			return new MessageAttachment<Channel>(200, channel);
		} catch (Exception e) {
			e.printStackTrace();
			return new Message(500);
		}
	}

	public int getID(String name){
		try{
			if(channelDAO instanceof JDBCChannelDAO){
				return  ((JDBCChannelDAO) channelDAO).getID(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	public String getName(int id){
		try{
			if(channelDAO instanceof JDBCChannelDAO){
				return ((JDBCChannelDAO) channelDAO).getName(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Channel getChannel(String name){
		try{
			if(channelDAO instanceof JDBCChannelDAO){
				return ((JDBCChannelDAO) channelDAO).find(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	// Gets all channels
	public List getFromAdmin(User admin) {
		try {
			JDBCChannelDAO jdbcChannel = new JDBCChannelDAO();
			List<Channel> channels = jdbcChannel.findFromAdmin(admin.getId());
			return channels;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
