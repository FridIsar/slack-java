package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.Post;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.service.ChannelService;

public class DeleteChannelMessage extends Subject implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling delete channel ...");

        String title = dataMessage.getTitle();

        ChannelService cs = new ChannelService();

        Message message = cs.delete(title);

        if (message.getCode() == 200)   {
            Message messageToSend = new MessageAttachment<>(ClientMessageType.DELETECHANNEL.getValue(),
                    null);
            this.notifyChannelMembers(clientHandler, dataMessage, messageToSend);
        }

        return message;
    }
}
