package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.PostAndChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.PostService;
import io.slack.service.UserService;

import java.awt.*;

public class AddPostChannelMessage extends Subject implements ClientMessageHandler<PostAndChannelCredentials> {
    @Override
    public Message handle(PostAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        // 1 - some processing on channels
        String authorEmail = dataMessage.getAuthorEmail();
        String textMessage = dataMessage.getTextMessage();
        String channelTitle = dataMessage.getChannelTitle();
        Image attached = dataMessage.getAttached();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle); //((MessageAttachment) channelMsg).getAttachment();

        UserService us = new UserService();
        User user = us.getUser(authorEmail);

        // 2 - Retrieve list of users to notify - Channel.getUsers -> into list of emails
        java.util.List usersToNotify = channel.getUsers();

        PostService ps = new PostService();
        Message message = ps.create(user, textMessage, channel);


        // 3 - call notifyAll(Message messageNotify)

        if (message.getCode() == 200)   {
            Thread threadNotify = new Thread(() -> {
                // TODO :
                // ServerMessageType -> code test = 1000 = UPDATE CHANNEL
                Message messageNotify = message;
                this.notifyAll(clientHandler.getActivatedClient(), messageNotify);
            });
            threadNotify.start();
        }
        return message;
    }


}
