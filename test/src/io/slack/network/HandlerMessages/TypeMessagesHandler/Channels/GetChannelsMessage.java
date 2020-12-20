package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

public class GetChannelsMessage implements ClientMessageHandler<Channel> {

    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        if (! clientHandler.getConcurrentUserAuthenticated().containsKey(clientHandler.getSocket())) {
            return new Message(403);
        }

        System.out.println("Handling GetChannels");
        return new MessageAttachment<Channel>(200, null);
    }
}
