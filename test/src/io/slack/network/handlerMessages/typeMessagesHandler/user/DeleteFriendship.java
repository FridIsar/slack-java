package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.model.UserAndUserCredentials;
import io.slack.service.FriendService;
import io.slack.service.UserService;
import io.slack.utils.Pair;

public class DeleteFriendship implements ClientMessageHandler<UserAndUserCredentials> {
    @Override
    public Pair handle(UserAndUserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling delete friendship");

        String firstEmail = dataMessage.getFirstUserEmail();
        String secondEmail = dataMessage.getSecondUserEmail();

        FriendService friendService = new FriendService();
        Message message = friendService.delete(firstEmail, secondEmail);

        Thread thread = null;
        return new Pair(message, thread);
    }
}
