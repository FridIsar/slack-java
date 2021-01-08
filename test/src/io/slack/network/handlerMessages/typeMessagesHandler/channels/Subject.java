package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.PostService;
import io.slack.service.UserService;
import io.slack.utils.Utils;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Subject {

    public void notifyAll(CopyOnWriteArrayList<ClientHandler> clientsToNotify, Message messageNotify) {
        for (ClientHandler clientHandler : clientsToNotify) {
            clientHandler.notify(messageNotify);
        }
    }

    public Thread notifyChannelMembers(ClientHandler clientHandler, Channel channel, Message messageToNotify)  {

        MemberService ms = new MemberService();
        Message membersMessage = ms.getAllFromChannel(channel);


        ArrayList<User> members = (ArrayList<User>) ((MessageAttachment) membersMessage).getAttachment();

        CopyOnWriteArrayList<ClientHandler> connectedUsers = clientHandler.getActivatedClient();
        ConcurrentHashMap<Socket, String> concurrentUserAuthenticated = clientHandler.getConcurrentUserAuthenticated();
        CopyOnWriteArrayList<ClientHandler> connectedMembers = new CopyOnWriteArrayList<ClientHandler>();

        for (ClientHandler connectedUser : connectedUsers)    {
            for (User memberUser : members) {
                String userEmail = concurrentUserAuthenticated.get(connectedUser.getSocket());
                if (userEmail != null && userEmail.equals(memberUser.getEmail()))    {
                    connectedMembers.add(connectedUser);
                }
            }
        }
        Thread threadNotify = new Thread(() -> {
            //Utils.wait(3);
            Message message = messageToNotify;
            //System.out.println("notifying..."+((MessageAttachment) message).getAttachment()+"code "+message.getCode());
            this.notifyAll(connectedMembers, message);
            //System.out.println("notified");
        });
        //threadNotify.start();
        return threadNotify;
    }
}
