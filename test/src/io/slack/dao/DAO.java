package io.slack.dao;

import io.slack.model.User;

import java.util.List;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */

public interface DAO<T> {

	T insert(T object) throws Exception;

	T update(T object) throws Exception;

	void delete(String key) throws Exception;

	T find(String key) throws Exception;

	List<T> findAll() throws Exception;
}
