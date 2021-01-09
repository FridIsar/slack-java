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

	private String title;
	private User admin;
	private Date createdAt;
	private transient Image icon;
	private int id;
	private List<User> users = new ArrayList<>();
	private List<Post> posts = new ArrayList<>();

	public Channel() {}

	public Channel(String title, User admin) {
		this.title = title;
		this.admin=admin;
		this.createdAt=new Date(Instant.now().toEpochMilli());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<Post> getMessages() {
		return posts;
	}

	public void addPost(Post post){this.posts.add((post));
		System.out.println("addpost");}

	public void removePost(Post post){this.posts.remove((post)); }

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Channel channel = (Channel) o;
		return title.equals(channel.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}

	@Override
	public String toString() {
		return "Channel{" +
				"title='" + title +'}';
	}

	@Override
	public int compareTo(Channel o) {
		return title.compareTo(o.title);
	}
}
