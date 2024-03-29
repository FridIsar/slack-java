package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.model.UserCredentials;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.UserService;
import io.slack.utils.Pair;

public class GetChannelsUserMessage implements ClientMessageHandler<User> {

    @Override
    public Pair handle(User dataMessage, ClientHandler clientHandler) {

        System.out.println("Handling channels user...");


        MemberService ms = new MemberService();
        Message message = ms.getAllFromUser(dataMessage);

        Thread thread = null;
        return new Pair(message, thread);
    }
}
