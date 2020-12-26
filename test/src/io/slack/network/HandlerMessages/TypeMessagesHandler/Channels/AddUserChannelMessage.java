package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserAndChannelCredentials;

public class AddUserChannelMessage implements ClientMessageHandler<UserAndChannelCredentials> {
    @Override
    public Message handle(UserAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        return null;
    }
}
