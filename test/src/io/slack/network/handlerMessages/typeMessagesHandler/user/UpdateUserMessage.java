package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.model.UserCredentials;
import io.slack.network.model.UserCredentialsOptions;
import io.slack.service.UserService;
import io.slack.utils.Pair;

public class UpdateUserMessage implements ClientMessageHandler<UserCredentialsOptions> {
    @Override
    public Pair handle(UserCredentialsOptions dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling update user ...");

        String email = dataMessage.getEmail();
        String newPassword = dataMessage.getPassword();
        String pseudo = dataMessage.getPseudo();

        UserService userService = new UserService();
        int id = userService.getID(email);
        Message message = userService.update(id, email, newPassword, pseudo);

        if (message.getCode() == 200)   {
            // in case email is updated
            String newEmail = ((User) ((MessageAttachment) message).getAttachment()).getEmail();
            clientHandler.getConcurrentUserAuthenticated().replace(clientHandler.getSocket(), email, newEmail);
        }

        Thread thread = null;
        return new Pair(message, thread);
    }
}
