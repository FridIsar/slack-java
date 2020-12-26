package io.slack.network.HandlerMessages.TypeMessagesHandler.User;

import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentials;

public class DisconnectMessage implements ClientMessageHandler<UserCredentials> {
    @Override
    public Message handle(UserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling disconnect ...");
        clientHandler.getConcurrentUserAuthenticated().remove(clientHandler.getSocket(), "@EMAIL");
        return new Message(200);
    }
}
