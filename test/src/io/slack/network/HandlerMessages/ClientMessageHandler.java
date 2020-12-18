package io.slack.network.HandlerMessages;

import io.slack.network.AsyncServer;
import io.slack.network.ClientTreatment;
import io.slack.network.communication.Message;

import java.io.Serializable;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ConcurrentHashMap;

public interface ClientMessageHandler<T extends Serializable> {

    Message handle(T dataMessage, ClientTreatment clientTreatment);
    Message asyncHandle(T dataMessage, ConcurrentHashMap<Integer, String> cc, AsynchronousSocketChannel socket);
}
