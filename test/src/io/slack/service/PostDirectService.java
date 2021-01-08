package io.slack.service;

import io.slack.dao.JDBCPostDirectDAO;
import io.slack.model.ChannelDirect;
import io.slack.model.Friend;
import io.slack.model.PostDirect;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.util.ArrayList;
import java.util.List;

public class PostDirectService {

    private final JDBCPostDirectDAO postDirectDAO = new JDBCPostDirectDAO();

    public Message create(User author, String textMessage, User user){
        try {
            PostDirect postDirect = new PostDirect(author, textMessage, new ChannelDirect(new Friend(author, user)));
            postDirect = postDirectDAO.insert(postDirect);

            return new MessageAttachment<PostDirect>(200, postDirect);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message getAllFromFriend(Friend friend){
        try {
            List<PostDirect> postDirects=postDirectDAO.findAllFromFriend(friend);
            if(postDirects.isEmpty()){
                return new Message(404);
            }
            return new MessageAttachment<ArrayList>(200, (ArrayList)postDirects);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }
}
