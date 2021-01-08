package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.model.Friend;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.model.UserCredentials;
import io.slack.service.FriendService;

public class GetFriendsMessage implements ClientMessageHandler<UserCredentials> {
    @Override
    public Message handle(UserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling get friends...");

        FriendService friendService = new FriendService();
        Message message = friendService.getAllFromUser(dataMessage.getEmail());

        return message;
    }
}
