package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.network.model.UserCredentials;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.service.UserService;

public class SignInMessage implements ClientMessageHandler<UserCredentials> {

    @Override
    public Message handle(UserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling Signin ...");

        String email = dataMessage.getEmail();
        String password = dataMessage.getPassword();


        System.out.println("haut");
        UserService userService = new UserService();
        Message message = userService.authenticate(email, password);
        System.out.println("bas");

        if (message.getCode() == 200) {
            clientHandler.getConcurrentUserAuthenticated().put(clientHandler.getSocket(), email);
        }
        return message;
    }
}
