package io.slack.network.handlerMessages.typeMessagesHandler.user;

import io.slack.model.PostDirect;
import io.slack.model.User;
import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.model.PostAndFriendCredentials;
import io.slack.service.PostDirectService;
import io.slack.service.UserService;
import io.slack.utils.Pair;

public class AddPostFriendMessage implements ClientMessageHandler<PostAndFriendCredentials> {
    @Override
    public Pair handle(PostAndFriendCredentials dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling direct post ...");

        String authorEmail = dataMessage.getAuthorEmail();
        String textMessage = dataMessage.getTextMessage();
        String otherUserEmail = dataMessage.getOtherUserEmail();

        UserService userService = new UserService();
        User author = userService.getUser(authorEmail);
        User otherUser = userService.getUser(otherUserEmail);

        PostDirectService postDirectService = new PostDirectService();
        Message message = postDirectService.create(author,textMessage,otherUser);

        if(message.getCode() == 200){
            Message messageToSend = new MessageAttachment<PostDirect>(ClientMessageType.ADDPOSTFRIEND.getValue(),
                    (PostDirect) ((MessageAttachment)message).getAttachment() );
            //todo les deux user du friend
        }

        Thread thread = null;
        return new Pair(message, thread);
    }
}
