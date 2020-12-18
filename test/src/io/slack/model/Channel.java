package io.slack.model;

import java.awt.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class Channel implements Serializable, Comparable<Channel> {

	private String name;
	private User admin;
	private Date createdAt;
	private Image icon;
	private List<User> users = new ArrayList<>();
	private List<Message> messages = new ArrayList<>();

	public Channel() {}

	public Channel(String name, User admin) {
		this.name = name;
		this.admin=admin;
		this.createdAt=new Date(Instant.now().toEpochMilli());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getAdmin() {
		return admin;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public void removeUser(User user) {
		this.users.remove(user);
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void addMessage(Message message){this.messages.add((message)); }

	public void removeMessage(Message message){this.messages.remove((message)); }

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Channel channel = (Channel) o;
		return name.equals(channel.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Channel o) {
		return name.compareTo(o.name);
	}
}
