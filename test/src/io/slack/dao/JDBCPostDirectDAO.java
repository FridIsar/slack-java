package io.slack.dao;

import io.slack.model.*;
import io.slack.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCPostDirectDAO implements DAO<PostDirect> {

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public PostDirect insert(PostDirect object) throws Exception {
        String query = "insert into directPosts (message, user1_id, user2_id, sending_date) values ( ?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,object.getMessage());
            statement.setInt(2, object.getAuthor().getId());
            if( ((ChannelDirect)object.getChannel()).getFriend().getFirstUser().equals( object.getAuthor() )  )
                statement.setInt(3,((ChannelDirect)object.getChannel()).getFriend().getSecUser().getId());
            else
                statement.setInt(3,((ChannelDirect)object.getChannel()).getFriend().getFirstUser().getId());
            statement.setDate(4,object.getSendingDate());
            statement.executeUpdate();
            String querySelect = "select * from directPosts;";
            try(PreparedStatement statementSelect = connection.prepareStatement(querySelect)){
                try(ResultSet resultSetSelect=statementSelect.executeQuery()){
                    resultSetSelect.last();
                    object.setId(resultSetSelect.getInt(1));
                    return object;
                }
            }
        }
    }


    @Override
    public PostDirect update(PostDirect object) throws Exception {
        return null;
    }
    @Override
    public void delete(String key) throws Exception {
    }
    @Override
    public PostDirect find(String key) throws Exception {
        return null;
    }

    public List<PostDirect> findAllFromFriend(Friend friend) throws Exception {
        List<PostDirect> posts = new ArrayList<>();
        String query = "select * from directPosts where user1_id = ? AND user2_id = ?; ";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,friend.getFirstUser().getId());
            statement.setInt(2,friend.getSecUser().getId());
            try(ResultSet resultSet = statement.executeQuery()){
                UserService userService = new UserService();
                while (resultSet.next()){
                    String message = resultSet.getString(2);
                    PostDirect postDirect = new PostDirect(friend.getFirstUser() , message, friend );
                    postDirect.setId( resultSet.getInt(1) );
                    postDirect.setSendingDate( resultSet.getDate(5) );
                    posts.add(postDirect);
                }
            }
        }
        return posts;

    }


    @Override
    public List<PostDirect> findAll() throws Exception {
        List<PostDirect> posts = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from directPosts ;")){
                UserService userService = new UserService();
                while (resultSet.next()){
                    User author =DAOFactory.getUser().find(userService.getEmail( resultSet.getInt(3) ) );
                    User user =DAOFactory.getUser().find(userService.getEmail( resultSet.getInt(4) ) );
                    Friend friend = new Friend(author, user);
                    String message = resultSet.getString(2);
                    PostDirect postDirect = new PostDirect(author , message, friend );
                    postDirect.setId( resultSet.getInt(1) );
                    postDirect.setSendingDate( resultSet.getDate(5) );
                    posts.add(postDirect);
                }
            }
        }
        return posts;
    }
}
