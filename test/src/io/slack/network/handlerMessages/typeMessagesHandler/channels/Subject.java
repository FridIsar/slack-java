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

    public void notifyChannelMembers(ClientHandler clientHandler, Channel channel, Message messageToNotify)  {

        MemberService ms = new MemberService();
        Message membersMessage = ms.getAllFromChannel(channel);

        ArrayList<User> members = (ArrayList<User>) ((MessageAttachment) membersMessage).getAttachment();

        CopyOnWriteArrayList<ClientHandler> connectedUsers = clientHandler.getActivatedClient();
        ConcurrentHashMap<Socket, String> concurrentUserAuthenticated = clientHandler.getConcurrentUserAuthenticated();
        CopyOnWriteArrayList<ClientHandler> connectedMembers = new CopyOnWriteArrayList<ClientHandler>();

        for (ClientHandler connectedUser : connectedUsers)    {
            for (User memberUser : members) {
                String userEmail = concurrentUserAuthenticated.get(connectedUser.getSocket());
                if (userEmail.equals(memberUser.getEmail()))    {
                    connectedMembers.add(connectedUser);
                }
            }
        }

        if (messageToNotify.getCode() == 200)   {
            Thread threadNotify = new Thread(() -> {
                Message message = messageToNotify;
                this.notifyAll(connectedMembers, message);
            });
            threadNotify.start();
        }
    }
}
