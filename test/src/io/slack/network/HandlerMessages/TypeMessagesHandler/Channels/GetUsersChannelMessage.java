package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.service.ChannelService;
import io.slack.service.PostService;
import io.slack.service.UserService;

public class GetUsersChannelMessage implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {

        String channelTitle = dataMessage.getTitle();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle);

        UserService us = new UserService();
        Message message = us.getAllFromChannel(channel);

        return message;
    }
}
