package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentials;

public class CreateChannelMessage  implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling create channel ...");

        return new Message(200);
    }
}
