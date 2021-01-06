package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserAndChannelCredentials;

public class DeleteUserChannelMessage implements ClientMessageHandler<UserAndChannelCredentials> {
    @Override
    public Message handle(UserAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        return null;
    }
}
