package io.slack.network.HandlerMessages.TypeMessagesHandler.User;

import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentials;
import io.slack.network.model.UserCredentialsOptions;

public class UpdateUserMessage implements ClientMessageHandler<UserCredentialsOptions> {
    @Override
    public Message handle(UserCredentialsOptions dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling update user ...");

        return new Message(200);
    }
}
