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
        String query = "insert into posts (message, channel_name, user_id, sending_date, with_attachment) values ( ?, ?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,object.getMessage());
            statement.setString(2,object.getChannel().getTitle());
            statement.setInt(3,object.getId());
            statement.setDate(4,object.getSendingDate());
            statement.setBoolean(5,object instanceof PostImage || object instanceof PostPdf);

            try(ResultSet resultSet=statement.executeQuery()){
                resultSet.last();
                object.setId(resultSet.getInt(1));
                return object;
            }
        }
    }

    //todo do it correctly
    @Override
    public Post update(Post object) throws Exception {
        return null;
    }

    @Override
    public void delete(String key) throws Exception { }
    public void delete(Post post) throws Exception{
        String query = "delete from posts where channel_name = ? and id = ? ;";
        try(PreparedStatement statement= connection.prepareStatement(query)){
            statement.setString(1,post.getChannel().getTitle());
            statement.setInt(2,post.getId());
            try(ResultSet resultSet = statement.executeQuery()){

            }
        }
    }

    //todo if we can find a list of posts from a channel's name and words contained in the textMessage
    @Override
    public Post find(String key) throws Exception {
        return null;
    }

    public List<Post> findAllFromChannel(Channel channel) throws Exception {
        List<Post> posts = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from posts where channel_name = '"+channel.getTitle() +"';")){
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
            try(ResultSet resultSet = statement.executeQuery("select * from posts")){
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
