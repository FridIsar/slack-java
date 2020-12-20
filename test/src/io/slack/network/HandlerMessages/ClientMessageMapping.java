package io.slack.network.HandlerMessages;

import io.slack.network.HandlerMessages.TypeMessagesHandler.Channels.GetChannelsMessage;
import io.slack.network.HandlerMessages.TypeMessagesHandler.Channels.AddMessageChannelMessage;
import io.slack.network.HandlerMessages.TypeMessagesHandler.User.SignInMessage;

import java.util.HashMap;
import java.util.Map;

public class ClientMessageMapping {
    // à transférer dans ClientMessageType
    private static final Map<Integer, ClientMessageHandler> handlers =
            new HashMap<Integer, ClientMessageHandler>() {
        {
            put(ClientMessageType.SIGNIN.getValue(), new SignInMessage());
            put(ClientMessageType.GETCHANNELS.getValue(), new GetChannelsMessage());
            put(ClientMessageType.ADDMESSAGECHANNEL.getValue(), new AddMessageChannelMessage());
        }
    };

    public static Map<Integer, ClientMessageHandler> getMapping() {
        return new HashMap<>(handlers);
    }
}
