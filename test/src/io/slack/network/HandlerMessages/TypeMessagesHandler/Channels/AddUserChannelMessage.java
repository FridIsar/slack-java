package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserAndChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.UserService;

public class AddUserChannelMessage extends Subject implements ClientMessageHandler<UserAndChannelCredentials> {
    @Override
    public Message handle(UserAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling add user to channel ...");
        String channelTitle = dataMessage.getChannelTitle();
        String userEmail = dataMessage.getUserEmail();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle);

        UserService us = new UserService();
        User user = us.getUser(userEmail);

        MemberService ms = new MemberService();
        Message message = ms.create(channel, user);

        if (message.getCode() == 200)   {
            this.notifyChannelMembers(clientHandler, channel, message);
        }

        return message;
    }
}
