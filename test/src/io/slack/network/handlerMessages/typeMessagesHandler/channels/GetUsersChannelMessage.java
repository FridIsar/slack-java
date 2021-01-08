package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.utils.Pair;

public class GetUsersChannelMessage implements ClientMessageHandler<Channel> {
    @Override
    public Pair handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling get users by channel ...");

        Channel channel = dataMessage;

        MemberService ms = new MemberService();
        Message message = ms.getAllFromChannel(channel);

        Thread thread = null;
        return new Pair(message, thread);
    }
}
