package io.slack.network.HandlerMessages.TypeMessagesHandler.Channels;

import io.slack.network.ClientHandler;
import io.slack.network.communication.Message;

import java.util.concurrent.CopyOnWriteArrayList;

public class Subject {

    public void notifyAll(CopyOnWriteArrayList<ClientHandler> clientsToNotify, Message messageNotify) {
        for (ClientHandler clientHandler : clientsToNotify) {
            clientHandler.notify(messageNotify);
        }
    }
}