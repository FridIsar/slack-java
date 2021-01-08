package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.model.UserAndUserCredentials;
import io.slack.service.FriendService;
import io.slack.service.UserService;

public class AddFriendshipMessage implements ClientMessageHandler<UserAndUserCredentials> {
    @Override
    public Message handle(UserAndUserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling add friendship");

        String firstEmail = dataMessage.getFirstUserEmail();
        String secondEmail = dataMessage.getSecondUserEmail();

        UserService userService = new UserService();

        User firstUser = userService.getUser(firstEmail);
        User secondUser = userService.getUser(secondEmail);

        FriendService friendService = new FriendService();
        Message message = friendService.create(firstUser, secondUser);

        return message;
    }
}
