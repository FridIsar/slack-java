package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.PostAndChannelCredentials;

public class DeletePostChannelMessage implements ClientMessageHandler<PostAndChannelCredentials> {
    @Override
    public Message handle(PostAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        return null;
    }
}
