package io.slack.dao;

import io.slack.model.Channel;
import io.slack.model.Member;
import io.slack.model.User;
import io.slack.service.ChannelService;
import io.slack.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCMemberDAO implements DAO<Member>{

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Member insert(Member object) throws Exception {
        String query = "insert into members ( channel_id, user_id) values (?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, object.getChannel().getId());
            statement.setInt(2, object.getUser().getId());

            try(ResultSet resultSet = statement.executeQuery()){
                return object;
            }
        }
    }

    @Override
    public Member update(Member object) throws Exception {
        return null;
    }


    @Override
    public void delete(String key) throws Exception {}
    public void delete(String key1, String key2) throws Exception {
        String query = "delete from members where channel_id = ? and user_id = ?;";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            UserService userService = new UserService();
            ChannelService channelService = new ChannelService();
            statement.setInt(1,channelService.getID(key1));
            statement.setInt(2,userService.getID(key2));
            try(ResultSet resultSet=statement.executeQuery()){}
        }
    }

    @Override
    public Member find(String key) throws Exception {return null;}
    public Member find(String key1, String key2) throws Exception{
        String query = "SELECT * from members where channel_id = ? and user_id = ?;";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            UserService userService = new UserService();
            ChannelService channelService = new ChannelService();
            statement.setInt(1,channelService.getID(key1));
            statement.setInt(2,userService.getID(key2));
            try(ResultSet resultSet=statement.executeQuery()){
                if(resultSet.next()){
                    Channel channel = DAOFactory.getChannel().find(resultSet.getString(1));
                    User user = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(2)) );
                    return  new Member(channel,user);
                }
            }
        }
        return null;
    }

    public List<User> findAllFromChannel(Channel channel) throws Exception {
        List<User> users = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from members where channel_id = '"+channel.getId() +"';")){
                UserService userService = new UserService();
                while (resultSet.next()){
                    User user = userService.getUser(userService.getEmail( resultSet.getInt(2) ) );
                    users.add(user);
                }
            }
        }
        return users;
    }

    public List<Channel> findAllFromUser(User user) throws SQLException {
        List<Channel> channels = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from members where channel_id = '"+user.getId() +"';")) {
                ChannelService channelService = new ChannelService();
                while (resultSet.next()) {
                    Channel channel = channelService.getChannel(channelService.getName(resultSet.getInt(1)));
                    channels.add(channel);
                }
            }
        }
        return channels;
    }

    @Override
    public List<Member> findAll() throws Exception {
        List<Member> members = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from members")){
                UserService userService = new UserService();
                ChannelService channelService = new ChannelService();
                while(resultSet.next()){
                    Channel channel = DAOFactory.getChannel().find( channelService.getName(resultSet.getInt(1)) );
                    User user = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(2)) );
                    members.add( new Member(channel,user) );
                }
            }
        }
        return members;
    }
}
