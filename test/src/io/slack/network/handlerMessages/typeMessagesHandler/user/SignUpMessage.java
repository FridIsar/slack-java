package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentialsOptions;
import io.slack.service.UserService;
import io.slack.utils.Pair;

public class SignUpMessage implements ClientMessageHandler<UserCredentialsOptions> {

    @Override
    public Pair handle(UserCredentialsOptions dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling signup ...");

        String email = dataMessage.getEmail();
        String password = dataMessage.getPassword();
        String pseudo = dataMessage.getPseudo();

        UserService userService = new UserService();
        Message message = userService.create(email, password, pseudo);

        if (message.getCode() == 200)
            clientHandler.getConcurrentUserAuthenticated().put(clientHandler.getSocket(), email);
        Thread thread = null;
        return new Pair(message, thread);
    }
}
