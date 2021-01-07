package io.slack.dao;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCChannelDAO implements DAO<Channel> {

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Channel insert(Channel object) throws Exception {
        String query = "insert into channels (name, admin_id, creation_date ) value ( ?, ?, ? );";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, object.getTitle());
            statement.setInt(2, object.getAdmin().getId());
            statement.setDate(3, object.getCreatedAt());
            statement.executeUpdate();
            Channel channel = this.find(object.getTitle());

            return channel;

        } catch (Exception e)   {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Channel update(Channel object) throws Exception {
        String query = "update channels set name = ? where id = ?;";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,object.getId());
            try(ResultSet resultSet=statement.executeQuery()){
                return object;
            }
        }
    }
//TODO FAIRE TOUS LES DELETE
    @Override
    public void delete(String key) throws Exception {
        String query = "delete from channels where name = ? ;";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, key);
            statement.executeUpdate();
        }
    }

    @Override
    public Channel find(String key) throws Exception {
        String query = "SELECT * FROM channels WHERE name = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, key);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = DAOFactory.getUser().find( resultSet.getString(3) );
                    Channel channel = new Channel(resultSet.getString(2), user );
                    channel.setId(resultSet.getInt(1));
                    return channel;
                }
            }
        }
        return null;
    }


    public List<Channel> findFromAdmin(int key) throws Exception {
        List<Channel> channels = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM channels WHERE admin_id = "+key+";")) {
                while (resultSet.next()) {
                    User user = DAOFactory.getUser().find( resultSet.getString(3) );
                    Channel channel = new Channel( resultSet.getString(2) , user);
                    channel.setId(resultSet.getInt(1));
                    channels.add(channel);
                }
            }
        }
        return channels;
    }

    public int getID(String key) throws Exception{
        String query = "SELECT id FROM channels WHERE name = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, key);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return -1;
    }

    public String getName(int id) throws Exception{
        String query = "SELECT * FROM channels WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(2);
                }
            }
        }
        return null;
    }

    @Override
    public List<Channel> findAll() throws Exception {
        List<Channel> channels = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM channels")) {
                while (resultSet.next()) {
                    User user = DAOFactory.getUser().find( resultSet.getString(3) );
                    Channel channel = new Channel( resultSet.getString(2) , user);
                    channel.setId(resultSet.getInt(1));
                    channels.add(channel);
                }
            }
        }
        return channels;
    }
}
