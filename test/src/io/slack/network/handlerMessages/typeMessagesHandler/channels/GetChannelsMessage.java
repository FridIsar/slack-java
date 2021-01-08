package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.model.ChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.PostService;
import io.slack.utils.Pair;

import java.util.ArrayList;

public class GetChannelsMessage implements ClientMessageHandler<ChannelCredentials> {

    @Override
    public Pair handle(ChannelCredentials dataMessage, ClientHandler clientHandler) {
        if (! clientHandler.getConcurrentUserAuthenticated().containsKey(clientHandler.getSocket())) {
            return new Pair(new Message(403), null); //?
        }
        System.out.println("Handling GetChannels");
        String channelTitle = dataMessage.getTitle();

        ChannelService cs = new ChannelService();
        Message message = cs.get(channelTitle);

        if (message.hasAttachment())    {
            Channel channel = (Channel) ((MessageAttachment) message).getAttachment();
            PostService ps = new PostService();
            Message messageP = ps.getAllFromChannel(channel);
            if (messageP.hasAttachment())   {
                channel.setPosts((ArrayList) ((MessageAttachment) messageP).getAttachment());
            }
            MemberService ms = new MemberService();
            Message messageM = ms.getAllFromChannel(channel);
            if (messageP.hasAttachment())   {
                channel.setPosts((ArrayList) ((MessageAttachment) messageP).getAttachment());
            }
            message = new MessageAttachment<Channel>(message.getCode(), channel);
        }


        Thread thread = null;
        return new Pair(message, thread);
    }
}
