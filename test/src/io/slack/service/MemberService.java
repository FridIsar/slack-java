package io.slack.service;

import io.slack.dao.JDBCMemberDAO;
import io.slack.model.Channel;
import io.slack.model.Friend;
import io.slack.model.Member;
import io.slack.model.User;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.util.ArrayList;
import java.util.List;

public class MemberService {

    private final JDBCMemberDAO memberDAO = new JDBCMemberDAO();

    public Message create(Channel channel, User user){
        try {
            Member member = memberDAO.find(channel.getTitle(), user.getEmail());
            if(member !=null){ //member already exists
                return new Message(403);
            }
            member = new Member(channel,user);
            memberDAO.insert(member);
            return new MessageAttachment<Member>(200, member);

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message delete(String title, String email){
        try {
            Member member= memberDAO.find(title,email);
            if(member==null){ //member does not exist
                return new Message(404);
            }
            memberDAO.delete(title,email);
            return new MessageAttachment<>(200, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message get(String title, String email){
        try{
            Member member= memberDAO.find(title, email);
            if(member==null){ //member does not exist
                return new Message(404);
            }
            return new MessageAttachment<Member>(200,member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message getAllFromChannel(Channel channel){
        try{
            List<User> users = memberDAO.findAllFromChannel(channel);
            users.add(channel.getAdmin());
            if(users.isEmpty()){
                return new Message(404);
            }
            return new MessageAttachment<ArrayList>(200, (ArrayList)users);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message getAllFromUser(User user){
        try{
            List<Channel> channels = memberDAO.findAllFromUser(user);
            ChannelService cs = new ChannelService();
            channels.addAll(cs.getFromAdmin(user));
            if(channels.isEmpty()){
                return new Message(404);
            }
            return new MessageAttachment<ArrayList>(200, (ArrayList)channels);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }

    public Message getAll(){
        try{
            List<Member> members = memberDAO.findAll();
            if(members.isEmpty()){
                return new Message(404);
            }
            return new MessageAttachment<ArrayList>(200, (ArrayList)members);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500);
        }
    }
}
