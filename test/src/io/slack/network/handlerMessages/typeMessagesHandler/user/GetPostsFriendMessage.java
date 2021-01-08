package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.model.Friend;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.model.UserAndUserCredentials;
import io.slack.service.FriendService;
import io.slack.service.PostDirectService;
import io.slack.service.UserService;

public class GetPostsFriendMessage implements ClientMessageHandler<UserAndUserCredentials> {
    @Override
    public Message handle(UserAndUserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling get post friendship...");

        String firstEmail = dataMessage.getFirstUserEmail();
        String secondEmail = dataMessage.getSecondUserEmail();

        FriendService friendService = new FriendService();
        Friend friend = friendService.getFriend(firstEmail,secondEmail);

        PostDirectService postDirectService = new PostDirectService();
        Message message = postDirectService.getAllFromFriend(friend);

        return message;
    }
}
