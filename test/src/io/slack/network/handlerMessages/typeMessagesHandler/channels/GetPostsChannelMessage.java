package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.service.ChannelService;
import io.slack.service.PostService;
import io.slack.utils.Pair;

public class GetPostsChannelMessage implements ClientMessageHandler<Channel> {
    @Override
    public Pair handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling get posts by channel ...");
        String channelTitle = dataMessage.getTitle();

        Channel channel = dataMessage;

        PostService ps = new PostService();
        Message message = ps.getAllFromChannel(channel);

        Thread thread = null;
        return new Pair(message, thread);
    }
}
