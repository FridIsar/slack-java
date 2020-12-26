package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.model.Channel;
import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.PostAndChannelCredentials;

public class AddPostChannelMessage extends Subject implements ClientMessageHandler<PostAndChannelCredentials> {
    @Override
    public Message handle(PostAndChannelCredentials dataMessage, ClientHandler clientHandler) {
        // 1 - some processing on channels
        //
        // 2 - Retrieve list of users to notify - Channel.getUsers -> into list of emails
        // 3 - call notifyAll(Message messageNotify)

        Thread threadNotify = new Thread(() -> {
            // TODO :
            // ServerMessageType -> code test = 1000 = UPDATE CHANNEL
            Message messageNotify = new Message(1000);
           this.notifyAll(clientHandler.getActivatedClient(), messageNotify);
        });
        threadNotify.start();
        return new Message(200);
    }


}
