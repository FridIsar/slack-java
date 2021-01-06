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

public class GetChannelsUserMessage implements ClientMessageHandler<User> {

    @Override
    public Message handle(User dataMessage, ClientHandler clientHandler) {

        System.out.println("Handling channels user...");

        System.out.println("1 ..."+dataMessage);

        MemberService ms = new MemberService();
        Message message = ms.getAllFromUser(dataMessage);

        System.out.println("2 ..."+message.getCode());
        return message;
    }
}
