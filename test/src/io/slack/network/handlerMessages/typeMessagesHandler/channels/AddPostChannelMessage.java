package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.Post;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.handlerMessages.typeMessagesHandler.channels.Subject;
import io.slack.network.model.PostAndChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.PostService;
import io.slack.service.UserService;

import java.awt.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddPostChannelMessage extends Subject implements ClientMessageHandler<PostAndChannelCredentials> {
    @Override
    public Message handle(PostAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling add post to channel ...");

        String authorEmail = dataMessage.getAuthorEmail();
        String textMessage = dataMessage.getTextMessage();
        String channelTitle = dataMessage.getChannelTitle();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle);

        UserService us = new UserService();
        User user = us.getUser(authorEmail);

        PostService ps = new PostService();
        Message message = ps.create(user, textMessage, channel);

        if (message.getCode() == 200)   {
            Message messageToSend = new MessageAttachment<Post>(ClientMessageType.ADDPOSTCHANNEL.getValue(),
                    (Post) ((MessageAttachment) message).getAttachment());
            this.notifyChannelMembers(clientHandler, channel, messageToSend);
        }

        return message;
    }


}
