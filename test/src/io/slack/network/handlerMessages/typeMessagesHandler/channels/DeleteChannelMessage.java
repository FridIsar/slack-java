package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.service.ChannelService;

public class DeleteChannelMessage extends Subject implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling delete channel ...");

        String title = dataMessage.getTitle();

        ChannelService cs = new ChannelService();

        Channel channel = dataMessage;//Channel channel = (Channel) ((MessageAttachment) cs.get(title)).getAttachment();
        //keeping channel object in memory to notify the ex-channel's members of its deletion

        Message message = cs.delete(title);

        if (message.getCode() == 200)   {
            this.notifyChannelMembers(clientHandler, channel, message);
        }

        return message;
    }
}
