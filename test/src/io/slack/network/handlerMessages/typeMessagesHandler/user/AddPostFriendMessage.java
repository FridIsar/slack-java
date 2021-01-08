package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.model.PostAndFriendCredentials;

public class AddPostFriendMessage implements ClientMessageHandler<PostAndFriendCredentials> {
    @Override
    public Message handle(PostAndFriendCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling direct post ...");



        return null;
    }
}
