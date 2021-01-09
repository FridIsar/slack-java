package io.slack.dao;

import io.slack.model.*;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.service.PostDirectService;
import io.slack.service.UserService;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCFriendsDAO implements DAO<Friend> {

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Friend insert(Friend object) throws Exception {
        String query = "insert into friends values (?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, object.getFirstUser().getId() );
            statement.setInt(2, object.getSecUser().getId() );
            statement.executeUpdate();
            return object;
        }
    }

    @Override
    public Friend update(Friend object) throws Exception {
        return null;
    }


    @Override
    public void delete(String key) throws Exception {}
    public void delete(String key1, String key2) throws Exception {
        String query = "delete from friends where usr1_id = ? and usr2_id = ?;";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            UserService userService = new UserService();
            statement.setInt(1,userService.getID(key1));
            statement.setInt(2,userService.getID(key2));
            statement.executeUpdate();
        }
    }

    @Override
    public Friend find(String key) throws Exception {return null;}
    public Friend find(String key1, String key2) throws Exception{
        String query = "SELECT * from friends where usr1_id = ? and usr2_id = ?;";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            UserService userService = new UserService();
            statement.setInt(1, userService.getID(key1));
            statement.setInt(2, userService.getID(key2));
            try(ResultSet resultSet=statement.executeQuery()){
                if(resultSet.next()){
                    User user1 = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(1)) );
                    User user2 = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(2)) );
                    Friend friend = new Friend(user1,user2);
                    friend.setChannelDirect(new ChannelDirect(friend));
                    PostDirectService postDirectService = new PostDirectService();
                    Message message = postDirectService.getAllFromFriend(friend);
                    if(message.hasAttachment()){
                        friend.getChannelDirect().setPosts( (List<Post>) ((MessageAttachment)message).getAttachment());
                    }
                    return  friend;
                }
            }
        }
        return null;
    }

    public List<Friend> findAllFromUser(String key) throws Exception {
        List<Friend> friends = new ArrayList<>();
        String query = "select * from friends where usr1_id = ? OR usr2_id = ?; ";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            UserService userService = new UserService();
            User user = userService.getUser(key);
            int id = userService.getID(key);
            statement.setInt(1, id);
            statement.setInt(2, id);
            try(ResultSet resultSet = statement.executeQuery()){
                PostDirectService postDirectService = new PostDirectService();
                while(resultSet.next()){
                    User user1 = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(1)) );
                    User user2 = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(2)) );
                    Friend friend;
                    if(! user1.equals(user))
                        friend = new Friend(user2, user1 );
                    else
                        friend = new Friend(user1, user2);
                    friend.setChannelDirect(new ChannelDirect(friend));
                    Message message = postDirectService.getAllFromFriend(friend);
                    if(message.hasAttachment()){
                        friend.getChannelDirect().setPosts( (List<Post>) ((MessageAttachment)message).getAttachment());
                    }
                    friends.add(friend);
                }
            }
        }
        return friends;
    }

    @Override
    public List<Friend> findAll() throws Exception {
        List<Friend> friends = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from friends")){
                UserService userService = new UserService();
                PostDirectService postDirectService = new PostDirectService();
                while(resultSet.next()){
                    User user1 = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(1)) );
                    User user2 = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(2)) );
                    Friend friend = new Friend(user1,user2);
                    friend.setChannelDirect(new ChannelDirect(friend));
                    Message message = postDirectService.getAllFromFriend(friend);
                    if(message.hasAttachment()){
                        friend.getChannelDirect().setPosts( (List<Post>) ((MessageAttachment)message).getAttachment());
                    }
                    friends.add(friend);
                }
            }
        }
        return friends;
    }
}
