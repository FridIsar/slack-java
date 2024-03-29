package io.slack.network.handlerMessages.typeMessagesHandler.channels;

import io.slack.model.Channel;
import io.slack.model.Post;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.model.PostAndChannelCredentials;
import io.slack.service.ChannelService;
import io.slack.service.MemberService;
import io.slack.service.PostService;
import io.slack.utils.Pair;

public class DeletePostChannelMessage extends Subject implements ClientMessageHandler<PostAndChannelCredentials> {
    @Override
    public Pair handle(PostAndChannelCredentials dataMessage, ClientHandler clientHandler) {

        System.out.println("Handling delete post from channel ...");
        String channelTitle = dataMessage.getChannelTitle();
        Post post = null; // todo replace by dataMessage.getPost();

        ChannelService cs = new ChannelService();
        Channel channel = (Channel) ((MessageAttachment) cs.get(channelTitle)).getAttachment();

        PostService ps = new PostService();
        Message message = ps.delete(post);

        Thread thread = null;
        if (message.getCode() == 200)   {
            thread = this.notifyChannelMembers(clientHandler, channel, message);
        }

        return new Pair(message, thread);
    }
}
