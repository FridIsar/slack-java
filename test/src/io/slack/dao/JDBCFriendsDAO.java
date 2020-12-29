package io.slack.dao;

import io.slack.model.Friend;
import io.slack.model.User;
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

            try(ResultSet resultSet = statement.executeQuery()){
                return object;
            }
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
            try(ResultSet resultSet=statement.executeQuery()){}
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
                    return  new Friend(user1,user2);
                }
            }
        }
        return null;
    }

    @Override
    public List<Friend> findAll() throws Exception {
        List<Friend> friends = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from friends")){
                UserService userService = new UserService();
                while(resultSet.next()){
                    User user1 = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(1)) );
                    User user2 = DAOFactory.getUser().find( userService.getEmail(resultSet.getInt(2)) );
                    friends.add( new Friend(user1,user2) );
                }
            }
        }
        return friends;
    }
}
