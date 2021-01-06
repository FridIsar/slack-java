package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.service.ChannelService;
import io.slack.service.PostService;

import java.awt.*;

public class GetPostsChannelMessage implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling get posts by channel ...");
        String channelTitle = dataMessage.getTitle();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle);

        PostService ps = new PostService();
        Message message = ps.getAllFromChannel(channel);

        return message;
    }
}
