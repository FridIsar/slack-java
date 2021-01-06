package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.PostService;
import io.slack.service.UserService;

public class GetUsersChannelMessage implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling get users by channel ...");
        String channelTitle = dataMessage.getTitle();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle);

        MemberService ms = new MemberService();
        Message message = ms.getAllFromChannel(channel);

        return message;
    }
}
