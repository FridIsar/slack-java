package io.slack.network.HandlerMessages.TypeMessagesHandler.User;

import io.slack.network.ClientHandler;
import io.slack.network.HandlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentials;
import io.slack.service.UserService;

public class DeleteUserMessage implements ClientMessageHandler<UserCredentials> {
    @Override
    public Message handle(UserCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling delete user ...");

        String email = dataMessage.getEmail();

        UserService userService = new UserService();
        Message message = userService.delete(email);

        if (message.getCode() == 200)
            clientHandler.getConcurrentUserAuthenticated().remove(clientHandler.getSocket(), email); //todo see with merieme
        return message;

    }
}
