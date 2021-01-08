package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentials;
import io.slack.service.UserService;
import io.slack.utils.Pair;

public class DisconnectMessage implements ClientMessageHandler<UserCredentials> {
    @Override
    public Pair handle(UserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling disconnect ...");

        String email = dataMessage.getEmail();  // utiliser aussi getPassword() ?

        UserService userService = new UserService();
        Message message = userService.get(email);

        if (message.getCode() == 200)
            clientHandler.getConcurrentUserAuthenticated().remove(clientHandler.getSocket(), email);
        Thread thread = null;
        return new Pair(message, thread);
    }
}
