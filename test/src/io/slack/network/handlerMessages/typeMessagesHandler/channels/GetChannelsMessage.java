package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.model.ChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.PostService;

public class GetChannelsMessage implements ClientMessageHandler<ChannelCredentials> {

    @Override
    public Message handle(ChannelCredentials dataMessage, ClientHandler clientHandler) {
        if (! clientHandler.getConcurrentUserAuthenticated().containsKey(clientHandler.getSocket())) {
            return new Message(403); //?
        }
        System.out.println("Handling GetChannels");
        String channelTitle = dataMessage.getTitle();

        ChannelService cs = new ChannelService();
        Message message = cs.get(channelTitle);

        return message;
    }
}
