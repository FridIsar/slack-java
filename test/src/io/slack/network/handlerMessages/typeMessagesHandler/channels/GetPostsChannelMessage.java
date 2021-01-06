package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.service.ChannelService;
import io.slack.service.PostService;

public class GetPostsChannelMessage implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {

        String channelTitle = dataMessage.getTitle();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle);

        PostService ps = new PostService();
        Message message = ps.getAllFromChannel(channel);

        return message;
    }
}
