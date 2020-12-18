package io.slack.network.HandlerMessages;

import io.slack.model.Credentials;
import io.slack.network.ClientTreatment;
import io.slack.network.communication.Message;

import java.io.Serializable;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ConcurrentHashMap;

public class SignInMessage implements ClientMessageHandler<Credentials>{

    @Override
    public Message handle(Credentials dataMessage, ClientTreatment clientTreatment) {
        System.out.println("Handling Signin ...");
        clientTreatment.getConcurrentUserAuthenticated().put(clientTreatment.getSocket().hashCode(), "@EMAIL");
        return new Message(200);
    }

    @Override
    public Message asyncHandle(Credentials dataMessage, ConcurrentHashMap<Integer, String> cc, AsynchronousSocketChannel sock) {
        // some treatment
        System.out.println("Handling Signin ...");
        cc.put(sock.hashCode(), "@EMAIL");
        return new Message(200);
    }
}
