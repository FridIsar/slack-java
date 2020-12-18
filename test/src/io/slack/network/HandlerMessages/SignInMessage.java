package io.slack.network.HandlerMessages;

import io.slack.model.Credentials;
import io.slack.network.ClientTreatment;
import io.slack.network.communication.Message;

import java.io.Serializable;

public class SignInMessage implements ClientMessageHandler<Credentials>{

    @Override
    public Message handle(Credentials dataMessage, ClientTreatment clientTreatment) {
        // some treatment
        System.out.println("Handling Signin ...");
        clientTreatment.getConcurrentUserAuthenticated().put(clientTreatment.getSocket().hashCode(), "@EMAIL");
        return new Message(200);
    }
}
