package io.slack.network.HandlerMessages.TypeMessagesHandler.User;

import io.slack.network.model.UserCredentials;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;

public class SignInMessage implements ClientMessageHandler<UserCredentials> {

    @Override
    public Message handle(UserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling Signin ...");
        clientHandler.getConcurrentUserAuthenticated().put(clientHandler.getSocket(), "@EMAIL");
        return new Message(200);
    }
}
