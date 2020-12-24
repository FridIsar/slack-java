package io.slack.network;

import io.slack.network.model.Credentials;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client1 = new Client();
        Message message = new MessageAttachment<Credentials>(ClientMessageType.SIGNIN.getValue(), new Credentials());
        client1.sendMessage(message);
        message = new Message(ClientMessageType.GETCHANNELS.getValue());
        client1.sendMessage(message);
        message = new Message(ClientMessageType.ADDMESSAGECHANNEL.getValue());
        client1.sendMessage(message);
    }
}
