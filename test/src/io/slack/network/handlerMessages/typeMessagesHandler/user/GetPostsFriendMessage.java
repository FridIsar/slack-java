package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.model.UserAndUserCredentials;

public class GetPostsFriendMessage implements ClientMessageHandler<UserAndUserCredentials> {
    @Override
    public Message handle(UserAndUserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling get post friendship...");

        return null;
    }
}
