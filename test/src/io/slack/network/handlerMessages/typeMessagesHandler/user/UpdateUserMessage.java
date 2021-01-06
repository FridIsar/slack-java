package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.model.UserCredentialsOptions;
import io.slack.service.UserService;

public class UpdateUserMessage implements ClientMessageHandler<UserCredentialsOptions> {
    @Override
    public Message handle(UserCredentialsOptions dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling update user ...");

        String email = dataMessage.getEmail();
        String newPassword = dataMessage.getPassword();
        String pseudo = dataMessage.getPseudo();

        UserService userService = new UserService();
        int id = userService.getID(email);
        Message message = userService.update(id, email, newPassword, pseudo);
        return message;
    }
}
