package io.slack.network.HandlerMessages;

import io.slack.model.Channel;
import io.slack.model.Credentials;
import io.slack.network.ClientTreatment;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

public class GetChannelsMessage implements ClientMessageHandler<Channel>{
    @Override
    public Message handle(Channel dataMessage, ClientTreatment clientTreatment) {
        // some treatment
        if (! clientTreatment.getConcurrentUserAuthenticated().containsKey(clientTreatment.getSocket().hashCode())) {
            return new Message(403);
        }
        System.out.println("Handling GetChannels");
        return new MessageAttachment<Channel>(200, null);
    }
}
