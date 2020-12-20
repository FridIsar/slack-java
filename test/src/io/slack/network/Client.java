package io.slack.network;

import io.slack.network.communication.Message;
import io.slack.network.communication.SubMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.*;

public class Client {
    final Socket socket;
    final InetAddress ip;
    final int serverPort = 50_500;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Message response;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public Client() throws IOException {
        this.ip = InetAddress.getByName("localhost");
        this.socket = new Socket(ip, serverPort);
        this.runClient();
    }

    /**
     * Method to run the Client
     * @throws IOException
     */
    public void runClient() throws IOException {
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        Thread threadReadMessage = new Thread(() -> this.readContinuouslyMessages());
        threadReadMessage.start();
    }

    /**
     * Method to send a Message to the server
     * @param sendMessage
     * @return
     * @throws InterruptedException
     */
    public Message sendMessage(Message sendMessage) throws InterruptedException {
        Message response = null;

        System.out.println("sendMessage");
        try {
            oos.writeObject(sendMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Waiting until response variable is set
        this.lock.lock();
        this.condition.await();
        System.out.println("sendMessage into lock");
        if (this.response == null) {
            System.out.println("sendMessage An error occurred : response null");
        } else {
            response = new Message(this.response);
        }
        this.response = null;
        this.lock.unlock();
        return response;
    }

    /**
     * Method to read continuously entrering Message (s) coming from the server
     */
    public void readContinuouslyMessages() {
        while (true) {
            try {
                Message messageReceived = (Message) ois.readObject();
                System.out.println(messageReceived.getCode());

                if (messageReceived.getCode() >= 200 && messageReceived.getCode() <= 500) {
                    // Notify with signalAll that response has been set

                    this.lock.lock();
                    System.out.println("readContinuouslyMessages into lock");
                    this.response = messageReceived;
                    this.condition.signalAll();
                    this.lock.unlock();
                } else {
                    // TODO :
                    //  - message from server
                    //  - notify ControllerClient of the reception of the messsage using Oberver Pattern
                    //  - (example : call method setChannels to update channels attribut)

                    System.out.println("Message coming from server , maybe update channel");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("SERVER CLOSED !");
                break;
            }
        }
    }
}
