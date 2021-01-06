package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.model.UserAndChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.UserService;

public class DeleteUserChannelMessage extends Subject implements ClientMessageHandler<UserAndChannelCredentials> {
    @Override
    public Message handle(UserAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling delete user from channel ...");
        String channelTitle = dataMessage.getChannelTitle();
        String userEmail = dataMessage.getUserEmail();


        ChannelService cs = new ChannelService();
        Channel channel = (Channel) ((MessageAttachment) cs.get(channelTitle)).getAttachment();

        MemberService ms = new MemberService();
        Message message = ms.delete(channelTitle, userEmail);

        if (message.getCode() == 200)   {
            this.notifyChannelMembers(clientHandler, channel, message);
        }

        return message;
    }
}
