package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.Member;
import io.slack.model.Post;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.model.UserAndChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.UserService;
import io.slack.utils.Pair;

public class AddUserChannelMessage extends Subject implements ClientMessageHandler<UserAndChannelCredentials> {
    @Override
    public Pair handle(UserAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling add user to channel ...");
        String channelTitle = dataMessage.getChannelTitle();
        String userEmail = dataMessage.getUserEmail();

        ChannelService cs = new ChannelService();
        Channel channel = cs.getChannel(channelTitle);

        UserService us = new UserService();
        User user = us.getUser(userEmail);

        MemberService ms = new MemberService();
        Message message = ms.create(channel, user);
        Thread thread = null;
        if (message.getCode() == 200)   {
            Message messageToSend = new MessageAttachment<Member>(ClientMessageType.ADDUSERCHANNEL.getValue(),
                    (Member) ((MessageAttachment) message).getAttachment());
            thread = this.notifyChannelMembers(clientHandler, channel, messageToSend);
        }

        return new Pair(message, thread);
    }
}
