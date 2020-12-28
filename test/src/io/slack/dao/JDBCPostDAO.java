package io.slack.dao;

import io.slack.model.Post;
import io.slack.model.PostImage;
import io.slack.model.PostPdf;
import io.slack.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class JDBCPostDAO implements DAO<Post> {

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Post insert(Post object) throws Exception {
        String query = "insert into posts (message, channel_name, user_id, sending_date, with_attachment) values ( ?, ?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,object.getMessage());
            statement.setString(2,object.getChannel().getTitle());
            UserService userService = new UserService();
            statement.setInt(3,userService.getID(object.getAuteur().getEmail()));
            statement.setDate(4,object.getSendingDate());
            statement.setBoolean(5,object instanceof PostImage || object instanceof PostPdf);

            try(ResultSet resultSet=statement.executeQuery()){
                return object;
            }
        }
    }

    @Override
    public Post update(Post object) throws Exception {
        return null;
    }

    @Override
    public void delete(String key) throws Exception {

    }

    @Override
    public Post find(String key) throws Exception {
        return null;
    }

    @Override
    public List<Post> findAll() throws Exception {
        return null;
    }
}
