package io.slack.service;

import io.slack.dao.JDBCPostDAO;
import io.slack.model.Channel;
import io.slack.model.Post;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.util.ArrayList;
import java.util.List;

public class PostService {

    private final JDBCPostDAO postDAO = new JDBCPostDAO();

    public Message create(User user, String textMessage, Channel channel){
        try{
            Post post = new Post(user, textMessage, channel);
            postDAO.insert(post);

            return new MessageAttachment<Post>(200, post);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    //todo after the find method in jdbc
    public Message get(){return null;}

    public Message delete(Post post){
        try{
            postDAO.delete(post);
            return new MessageAttachment<Post>(200, post);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message update(Post post){
        try{
            postDAO.update(post);
            return new MessageAttachment<Post>(200,post);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message getAllFromChannel(Channel channel){
        try{
            List<Post> posts = postDAO.findAllFromChannel(channel);
            if(posts.isEmpty()){
                return new Message(404);
            }
            return new MessageAttachment<ArrayList>(200, (ArrayList)posts);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

}
