package io.slack.network.handlerMessages;

import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;

import java.io.Serializable;

public interface ClientMessageHandler<T extends Serializable> {

    Message handle(T dataMessage, ClientHandler clientHandler);
}
