package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserAndChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.UserService;

public class AddUserChannelMessage implements ClientMessageHandler<UserAndChannelCredentials> {
    @Override
    public Message handle(UserAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        String channelTitle = dataMessage.getChannelTitle();
        String userEmail = dataMessage.getUserEmail();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle);

        UserService us = new UserService();
        User user = us.getUser(userEmail);

        //TODO CREATE SERVICE AND DAO FOR CHANNEL (ADD USER)
        return null;
    }
}
