package io.slack.dao;

import io.slack.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class MemoryUserDAO implements DAO<User> {

	private final Map<String, User> users = new ConcurrentHashMap<>();

	@Override
	public User insert(User object) {
		users.put(object.getEmail(), object);
		return object;
	}

	@Override
	public User update(User object) {
		users.put(object.getEmail(), object);
		return object;
	}

	@Override
	public void delete(String key) {
		users.remove(key);
	}

	@Override
	public User find(String key) {
		return users.get(key);
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<>(users.values());
	}
}
