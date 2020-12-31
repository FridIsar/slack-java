package io.slack.network.HandlerMessages.TypeMessagesHandler.User;

import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentials;
import io.slack.network.model.UserCredentialsOptions;
import io.slack.service.UserService;

public class SignUpMessage implements ClientMessageHandler<UserCredentialsOptions> {

    @Override
    public Message handle(UserCredentialsOptions dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling signup ...");

        String email = dataMessage.getEmail();
        String password = dataMessage.getPassword();
        String pseudo = dataMessage.getPseudo();

        UserService userService = new UserService();
        Message message = userService.create(email, password, pseudo);

        if (message.getCode() == 200)
            clientHandler.getConcurrentUserAuthenticated().put(clientHandler.getSocket(), email);
        return message;
    }
}
