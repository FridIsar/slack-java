package io.slack.network;

import io.slack.network.communication.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.*;

public class Client {
    final Socket socket;
    final InetAddress ip;
    // test local
    final int serverPort = 50_500;

    // port Serveur Ubuntu :
    //final int serverPort = 40_000;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Message response;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public Client() throws IOException {
        // test localhost :
        this.ip = InetAddress.getByName("127.0.0.1");

        // adresse Serveur Ubuntu :

        //this.ip = InetAddress.getByName("20.39.243.239");
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
            System.out.println("writing sendMessage...");
            oos.writeObject(sendMessage);
        } catch (IOException e) {
            System.out.println("Writing error sendMessage");
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
        System.out.println(response.getCode());
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
                    System.out.println(messageReceived.getCode());
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
