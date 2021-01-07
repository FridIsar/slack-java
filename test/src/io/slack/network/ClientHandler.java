package io.slack.network;

import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.handlerMessages.ClientMessageMapping;
import io.slack.network.handlerMessages.typeMessagesHandler.channels.Observer;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Callable, Observer {
    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private CopyOnWriteArrayList<ClientHandler> activatedClient;
    private ConcurrentHashMap<Socket, String> concurrentUserAuthenticated;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientHandler(Socket socket,
                         CopyOnWriteArrayList<ClientHandler> activatedClient,
                         ConcurrentHashMap<Socket, String>concurrentUserAuthenticated) throws IOException {
        this.socket = socket;
        this.activatedClient = activatedClient;
        this.concurrentUserAuthenticated = concurrentUserAuthenticated;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public Integer call() throws Exception {
        while(true) {
            try {
                // on lit le Message
                System.out.println("waiting message");
                Message messageReceived = null;
                try {
                    messageReceived = (MessageAttachment) this.ois.readObject();
                    System.out.println("message recevied at code "+messageReceived.getCode());
                } catch (IOException e) {
                    System.out.println("erreur received");
                    e.printStackTrace();
                }

                int code = (messageReceived.getCode());
                ClientMessageHandler cmh = ClientMessageMapping.getMapping().get(code);
                System.out.println("handler class is "+cmh.getClass()+messageReceived.hasAttachment());
                Message messageToSend = cmh.handle(messageReceived.hasAttachment() ? ((MessageAttachment) messageReceived).getAttachment() : null,this);

                System.out.println("Server is calling ...");
                this.oos.writeObject(messageToSend);
                System.out.println("Server called ...");

            } catch (IOException e) {
                System.out.println("CLIENT CLOSED !");
                this.socket.close();
                return 1;
            }
        }
    }

    @Override
    public void notify(Message messageNotify) {
        try {
            System.out.println("Server is notifying...");
            System.out.println("notifying object "+ messageNotify);
            this.oos.writeObject(messageNotify);
            System.out.println("Server notified...");
        } catch (IOException e) {
            System.out.println("notify An error occurred");
            e.printStackTrace();
        }
    }

    public ConcurrentHashMap<Socket, String> getConcurrentUserAuthenticated() {
        return concurrentUserAuthenticated;
    }

    public CopyOnWriteArrayList<ClientHandler> getActivatedClient() {
        return activatedClient;
    }
}
