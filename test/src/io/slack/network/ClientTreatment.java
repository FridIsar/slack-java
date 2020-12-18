package io.slack.network;

import io.slack.network.HandlerMessages.ClientMessageMapping;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.communication.SubMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientTreatment implements Callable {
    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private CopyOnWriteArrayList<ClientTreatment> activatedClient;

    public ConcurrentHashMap<Integer, String> getConcurrentUserAuthenticated() {
        return concurrentUserAuthenticated;
    }

    private ConcurrentHashMap<Integer, String> concurrentUserAuthenticated;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientTreatment(Socket socket,
                           CopyOnWriteArrayList<ClientTreatment> activatedClient,
                           ConcurrentHashMap<Integer, String>concurrentUserAuthenticated) throws IOException {
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
                Message messageReceived = (Message) this.ois.readObject();
                Message messageToSend = ClientMessageMapping.handlers.get(messageReceived.getCode()).handle(
                        messageReceived.hasAttachment() ? ((MessageAttachment)messageReceived).getAttachment() : null,
                        this);
                this.oos.writeObject(messageToSend);
            } catch (IOException e) {
                System.out.println("CLIENT CLOSED !");
                this.socket.close();
                return 1;
            }
        }
    }

}
