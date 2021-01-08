package io.slack.network.handlerMessages;

import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;
import io.slack.utils.Pair;

import java.io.Serializable;

public interface ClientMessageHandler<T extends Serializable> {

    Pair handle(T dataMessage, ClientHandler clientHandler);
}
