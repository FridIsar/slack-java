package io.slack.dao;

import io.slack.model.Channel;

import java.util.List;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public class MemoryChannelDAO implements DAO<Channel> {

	@Override
	public Channel insert(Channel object) throws Exception {
		return null;
	}

	@Override
	public Channel update(Channel object) throws Exception {
		return null;
	}

	@Override
	public void delete(String key) throws Exception {

	}

	@Override
	public Channel find(String key) throws Exception {
		return null;
	}

	@Override
	public List<Channel> findAll() throws Exception {
		return null;
	}
}
