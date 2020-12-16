package io.slack.dao;

import io.slack.model.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class MemoryChannelDAO implements DAO<Channel> {

	private Map<String, Channel> channels = new ConcurrentHashMap<String,Channel>();

	@Override
	public Channel insert(Channel object) {
		channels.put(object.getName(), object);
		return object;
	}

	@Override
	public Channel update(Channel object) {
		channels.put(object.getName(), object);
		return object;
	}

	@Override
	public void delete(String key) { channels.remove((key));}

	@Override
	public Channel find(String key) {
		return channels.get(key);
	}

	@Override
	public List<Channel> findAll() {
		return new ArrayList<Channel>(channels.values());
	}
}
