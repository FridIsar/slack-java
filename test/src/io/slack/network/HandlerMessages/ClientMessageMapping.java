package io.slack.network.HandlerMessages;

import java.util.HashMap;

public class ClientMessageMapping {

    public static HashMap<Integer, ClientMessageHandler> handlers =
            new HashMap<Integer, ClientMessageHandler>() {
        {
            put(ClientMessageType.SIGNIN.getValue(), new SignInMessage());
            put(ClientMessageType.GETCHANNELS.getValue(), new GetChannelsMessage());
        }
    };
}
