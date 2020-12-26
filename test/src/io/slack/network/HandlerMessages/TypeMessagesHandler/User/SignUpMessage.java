package io.slack.network.HandlerMessages.TypeMessagesHandler.User;

import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentials;
import io.slack.network.model.UserCredentialsOptions;

public class SignUpMessage implements ClientMessageHandler<UserCredentialsOptions> {

    @Override
    public Message handle(UserCredentialsOptions dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling signup ...");
        clientHandler.getConcurrentUserAuthenticated().put(clientHandler.getSocket(), "@EMAIL");
        return new Message(200);
    }
}
