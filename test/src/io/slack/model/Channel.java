package io.slack.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class Channel implements Serializable, Comparable<Channel> {

	private String name;
	private List<User> users = new ArrayList<>();

	public Channel() {}

	public Channel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
