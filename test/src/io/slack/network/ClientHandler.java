package io.slack.network;

import io.slack.network.handlerMessages.ClientMessageHandler;
import io.slack.network.handlerMessages.ClientMessageMapping;
import io.slack.network.handlerMessages.typeMessagesHandler.channels.Observer;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.utils.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler implements Callable, Observer {
    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private CopyOnWriteArrayList<ClientHandler> activatedClient;
    private ConcurrentHashMap<Socket, String> concurrentUserAuthenticated;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Lock lock = new ReentrantLock();

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
                System.out.println("(method call) Server waiting message...");
                Message messageReceived = null;
                try {
                    messageReceived = (MessageAttachment) this.ois.readObject();
                    System.out.println("(method call) Server received message "+messageReceived);
                } catch (IOException e) {
                    System.out.println("(method call) erreur received");
                    e.printStackTrace();
                }

                int code = (messageReceived.getCode());
                ClientMessageHandler cmh = ClientMessageMapping.getMapping().get(code);

                Pair<Message, Thread> pair = cmh.handle(messageReceived.hasAttachment() ? ((MessageAttachment) messageReceived).getAttachment() : null,this);
                Message messageToSend = pair.message;
                Thread notifyThread = pair.thread;

                System.out.println("(method call) Server is writing ..." + messageToSend);
                this.oos.writeObject(messageToSend);
                System.out.println("(method call) Server writing ended");

                if (notifyThread != null)   {
                    notifyThread.setDaemon(true);
                    notifyThread.start();
                    //todo stop notifythread
                }

                //System.out.println("(method call) is unlocking...");
                //this.lock.unlock();
                //System.out.println("(method call) is unlocked");

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
            //System.out.println("(method notify) is locked ... ");
            //this.lock.lock();
            System.out.println("(method notify) Server is writing ... "+messageNotify);
            this.oos.writeObject(messageNotify);
            System.out.println("(method notify) Server writing ended");
        } catch (IOException e) {
            System.out.println("(method notify) notify An error occurred");
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
