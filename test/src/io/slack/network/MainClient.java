package io.slack.network;

import io.slack.network.model.UserCredentials;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client1 = new Client();
        Message message = new MessageAttachment<UserCredentials>(ClientMessageType.SIGNIN.getValue(), new UserCredentials("root@slack.com","password"));
        client1.sendMessage(message);
        message = new Message(ClientMessageType.GETCHANNELS.getValue());
        client1.sendMessage(message);
        message = new Message(ClientMessageType.ADDPOSTCHANNEL.getValue());
        client1.sendMessage(message);
    }
}
