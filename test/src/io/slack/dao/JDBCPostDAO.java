package io.slack.dao;

import io.slack.model.*;
import io.slack.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCPostDAO implements DAO<Post> {

    private final Connection connection = DatabaseConnection.getConnection();


    @Override
    public Post insert(Post object) throws Exception {
        String query = "insert into posts (message, channel_id, user_id, sending_date, with_attachment) values ( ?, ?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,object.getMessage());
            statement.setInt(2,object.getChannel().getId());
            statement.setInt(3,object.getAuthor().getId());
            statement.setDate(4,object.getSendingDate());
            statement.setBoolean(5,object instanceof PostImage || object instanceof PostPdf);
            statement.executeUpdate();
            String querySelect = "select * from posts;";
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
    public Post update(Post object) throws Exception {
        String query = "update posts set message = ?, modification_date = ? where channel_id = ? and id = ? ; ";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, object.getMessage());
            statement.setDate(2,object.getModificationDate());
            statement.setInt(3,object.getChannel().getId());
            statement.setInt(4, object.getId());

            try(ResultSet resultSet=statement.executeQuery()){
                return object;
            }
        }
    }

    @Override
    public void delete(String key) throws Exception { }
    public void delete(Post post) throws Exception{
        String query = "delete from posts where channel_id = ? and id = ? ;";
        try(PreparedStatement statement= connection.prepareStatement(query)){
            statement.setInt(1,post.getChannel().getId());
            statement.setInt(2,post.getId());
            statement.executeUpdate();
        }
    }


    @Override
    public Post find(String key) throws Exception {

        return null;
    }

    public List<Post> findAllFromChannel(Channel channel) throws Exception {
        List<Post> posts = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from posts where channel_id = '"+channel.getId() +"';")){
                UserService userService = new UserService();
                while (resultSet.next()){
                    User user =DAOFactory.getUser().find(userService.getEmail( resultSet.getInt(4) ) );
                    String message = resultSet.getString(2);
                    Post post = new Post(user , message, channel );
                    post.setId( resultSet.getInt(1) );
                    post.setSendingDate( resultSet.getDate(5) );
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    @Override
    public List<Post> findAll() throws Exception {
        List<Post> posts = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from posts;")){
                UserService userService = new UserService();
                while (resultSet.next()){
                    User user =DAOFactory.getUser().find(userService.getEmail( resultSet.getInt(4) ) );
                    Channel channel= DAOFactory.getChannel().find( resultSet.getString(3) );
                    String message = resultSet.getString(2);
                    Post post = new Post(user , message, channel );
                    post.setId( resultSet.getInt(1) );
                    post.setSendingDate( resultSet.getDate(5) );
                    posts.add(post);
                }
            }
        }
        return posts;
    }
}
