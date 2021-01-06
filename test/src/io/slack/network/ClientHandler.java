package io.slack.network;

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
                } catch (IOException e) {
                    System.out.println("erreur received");
                    e.printStackTrace();
                }

                System.out.println("message received "+messageReceived.getCode());
                Message messageToSend = ClientMessageMapping.getMapping().get(messageReceived.getCode()).handle(
                        messageReceived.hasAttachment() ? ((MessageAttachment) messageReceived).getAttachment() : null,
                        this);
                this.oos.writeObject(messageToSend);

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
            this.oos.writeObject(messageNotify);
        } catch (IOException e) {
            System.out.println("notify An error occurred");
        }
    }

    public ConcurrentHashMap<Socket, String> getConcurrentUserAuthenticated() {
        return concurrentUserAuthenticated;
    }

    public CopyOnWriteArrayList<ClientHandler> getActivatedClient() {
        return activatedClient;
    }
}
