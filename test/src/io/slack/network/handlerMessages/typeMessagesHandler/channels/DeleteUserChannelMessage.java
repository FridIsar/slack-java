package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.Member;
import io.slack.model.Post;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.model.UserAndChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.UserService;
import io.slack.utils.Pair;

import java.util.ArrayList;

public class DeleteUserChannelMessage extends Subject implements ClientMessageHandler<UserAndChannelCredentials> {
    @Override
    public Pair handle(UserAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling delete user from channel ...");
        String channelTitle = dataMessage.getChannelTitle();
        String userEmail = dataMessage.getUserEmail();


        ChannelService cs = new ChannelService();
        Channel channel = (Channel) ((MessageAttachment) cs.get(channelTitle)).getAttachment();

        MemberService ms = new MemberService();
        Message message = ms.delete(channelTitle, userEmail);
        Thread thread = null;

/*        Message usersMessage = ms.getAllFromChannel(channel);
        if (usersMessage.hasAttachment())   {
            channel.getUsers().addAll((ArrayList<User>) ((MessageAttachment) usersMessage).getAttachment());
        }*/

        if (message.getCode() == 200)   {
            Message messageToSend = new MessageAttachment<Member>(ClientMessageType.DELETEUSERCHANNEL.getValue(),
                    (Member) ((MessageAttachment) message).getAttachment());
            thread = this.notifyChannelMembers(clientHandler, channel, messageToSend);
        }

        return new Pair(message, thread);
    }
}
