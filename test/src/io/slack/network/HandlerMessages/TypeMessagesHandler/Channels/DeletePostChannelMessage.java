package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.PostAndChannelCredentials;

public class DeletePostChannelMessage implements ClientMessageHandler<PostAndChannelCredentials> {
    @Override
    public Message handle(PostAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        return null;
    }
}
