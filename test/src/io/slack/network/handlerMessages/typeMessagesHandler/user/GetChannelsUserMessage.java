package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.UserService;

public class GetChannelsUserMessage implements ClientMessageHandler<User> {

    @Override
    public Message handle(User dataMessage, ClientHandler clientHandler) {

        String userEmail = dataMessage.getEmail();

        UserService userService = new UserService();
        User user = userService.getUser(userEmail);

        MemberService ms = new MemberService();
        Message message = ms.getAllFromUser(user);

        return message;
    }
}
