package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.model.UserCredentials;
import io.slack.service.ChannelService;

public class CreateChannelMessage extends Subject implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling create channel ...");

        String title = dataMessage.getTitle();
        User admin = dataMessage.getAdmin();

        ChannelService cs = new ChannelService();
        Message message = cs.create(title, admin);

        Channel channel = dataMessage;//(Channel) ((MessageAttachment) message).getAttachment();

        if (message.getCode() == 200)   {
            this.notifyChannelMembers(clientHandler, channel, message);
        }

        return message;
    }
}
