package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.model.ChannelCredentials;
import io.slack.network.model.UserCredentials;
import io.slack.service.ChannelService;
import io.slack.service.UserService;
import io.slack.utils.Pair;

public class CreateChannelMessage extends Subject implements ClientMessageHandler<ChannelCredentials> {
    @Override
    public Pair handle(ChannelCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling create channel ...");

        String title = dataMessage.getTitle();
        String adminEmail = dataMessage.getAdminEmail();

        UserService us = new UserService();
        User admin = us.getUser(adminEmail);
        ChannelService cs = new ChannelService();
        Message message = cs.create(title, admin);
        Channel channel = cs.getChannel(title);;
        Thread thread = null;
        if (message.getCode() == 200)   {
            Message messageToSend = new MessageAttachment<>(ClientMessageType.CREATECHANNEL.getValue(),
                    channel);
            thread = this.notifyChannelMembers(clientHandler, channel, messageToSend);
        }

        return new Pair(message, thread);
    }
}
