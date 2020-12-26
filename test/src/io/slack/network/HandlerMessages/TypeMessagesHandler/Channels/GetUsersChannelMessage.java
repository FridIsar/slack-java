package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;

public class GetUsersChannelMessage implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        return null;
    }
}
