package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.service.ChannelService;

public class CreateChannelMessage  implements ClientMessageHandler<Channel> {
    @Override
    public Message handle(Channel dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling create channel ...");

        String title = dataMessage.getTitle();
        User admin = dataMessage.getAdmin();

        ChannelService cs = new ChannelService();
        Message message = cs.create(title, admin);

        return message;
    }
}
