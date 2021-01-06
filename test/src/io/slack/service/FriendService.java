package io.slack.service;

import io.slack.dao.DAO;
import io.slack.dao.JDBCFriendsDAO;
import io.slack.dao.MemoryChannelDAO;
import io.slack.model.Friend;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.util.ArrayList;
import java.util.List;

public class FriendService {

    private final JDBCFriendsDAO friendDAO = new JDBCFriendsDAO();

    public Message create(User user1, User user2){
        try {
            Friend friend = friendDAO.find(user1.getEmail(), user2.getEmail());
            Friend friend2 = friendDAO.find(user2.getEmail(), user1.getEmail());
            if(friend !=null || friend2!=null){ //friend already exists
                return new Message(403);
            }
            friend = new Friend(user1,user2);
            friendDAO.insert(friend);
            return new MessageAttachment<Friend>(200, friend);

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message delete(String email1, String email2){
        try {
            Friend friend=friendDAO.find(email1,email2);
            Friend friend2=friendDAO.find(email2,email1);
            if(friend==null && friend2==null){ //friend does not exist
                return new Message(404);
            }
            if (friend != null)
                friendDAO.delete(email1,email2);
            else
                friendDAO.delete(email2,email1);
            return new MessageAttachment<>(200, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message get(String email1, String email2){
        try{
            Friend friend=friendDAO.find(email1, email2);
            Friend friend2=friendDAO.find(email2,email1);
            if(friend==null && friend2==null){ //friend does not exist
                return new Message(404);
            }
            if (friend != null)
                return new MessageAttachment<Friend>(200,friend);
            else
                return new MessageAttachment<Friend>(200,friend2);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message getAll(){
        try{
            List<Friend> friends = friendDAO.findAll();
            if(friends.isEmpty()){
                return new Message(404);
            }
            return new MessageAttachment<ArrayList>(200, (ArrayList)friends);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }
}
