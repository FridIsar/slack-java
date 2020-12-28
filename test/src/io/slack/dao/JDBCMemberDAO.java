package io.slack.dao;

import io.slack.model.Channel;
import io.slack.model.Member;
import io.slack.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCMemberDAO implements DAO<Member>{

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public Member insert(Member object) throws Exception {
        String query = "insert into members values (?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, object.getChannel().getTitle());
            statement.setString(2, object.getUser().getEmail());

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
        String query = "delete from members where channel_name = ? and user_email = ?;";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,key1);
            statement.setString(2,key2);
            try(ResultSet resultSet=statement.executeQuery()){}
        }
    }

    @Override
    public Member find(String key) throws Exception {return null;}
    public Member find(String key1, String key2) throws Exception{
        String query = "SELECT * from members where channel_name = ? and user_email = ?;";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1,key1);
            statement.setString(2,key2);
            try(ResultSet resultSet=statement.executeQuery()){
                if(resultSet.next()){
                    Channel channel = DAOFactory.getChannel().find(resultSet.getString(1));
                    User user = DAOFactory.getUser().find(resultSet.getString(2));
                    return  new Member(channel,user);
                }
            }
        }
        return null;
    }

    @Override
    public List<Member> findAll() throws Exception {
        List<Member> members = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try(ResultSet resultSet = statement.executeQuery("select * from members")){
                while(resultSet.next()){
                    Channel channel = DAOFactory.getChannel().find(resultSet.getString(1));
                    User user = DAOFactory.getUser().find(resultSet.getString(2));
                    members.add( new Member(channel,user) );
                }
            }
        }
        return members;
    }
}
