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
            System.out.println("dans le for");
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
            System.out.println("premier  for "+connectedUsers.size());
            for (User memberUser : members) {
                System.out.println("deuxieme for "+memberUser.getEmail());
                String userEmail = concurrentUserAuthenticated.get(connectedUser.getSocket());
                System.out.println("tropisieme for "+userEmail);
                if (userEmail != null && userEmail.equals(memberUser.getEmail()))    {
                    connectedMembers.add(connectedUser);
                    System.out.println("in member");
                }
                System.out.println("fin 2eme b");
            }
            System.out.println("fin 1ere b");
        }
        Thread threadNotify = new Thread(() -> {
            Message message = messageToNotify;
            System.out.println("notifying..."+((MessageAttachment) message).getAttachment()+"code "+message.getCode());
            System.out.println(connectedMembers);
            this.notifyAll(connectedMembers, message);
            System.out.println("notified");
        });
        threadNotify.start();
    }
}
