package io.slack.dao;

import io.slack.model.Friend;
import io.slack.model.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCFriendsDAO implements DAO<Friend> {

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Friend insert(Friend object) throws Exception {
        String query = "insert into friends values (?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, object.getFirstUser().getEmail());
            statement.setString(2, object.getSecUser().getEmail());

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
        String query = "delete from friends where usr1_email = ? and usr2_email = ?;";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,key1);
            statement.setString(2,key2);
            try(ResultSet resultSet=statement.executeQuery()){}
        }
    }

    @Override
    public Friend find(String key) throws Exception {return null;}
    public Friend find(String key1, String key2) throws Exception{
        String query = "SELECT * from friends where usr1_email = ? and usr2_email = ?;";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,key1);
            statement.setString(2,key2);
            try(ResultSet resultSet=statement.executeQuery()){
                if(resultSet.next()){
                    User user1 = DAOFactory.getUser().find(resultSet.getString(1));
                    User user2 = DAOFactory.getUser().find(resultSet.getString(2));
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
                while(resultSet.next()){
                    User user1 = DAOFactory.getUser().find(resultSet.getString(1));
                    User user2 = DAOFactory.getUser().find(resultSet.getString(2));
                    friends.add( new Friend(user1,user2) );
                }
            }
        }
        return friends;
    }
}
