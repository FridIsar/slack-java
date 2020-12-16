package io.slack.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    final Socket socket;
    final InetAddress ip;
    final int serverPort = 50_500;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Client() throws IOException {
        this.ip = InetAddress.getByName("localhost");
        this.socket = new Socket(ip, serverPort);
        this.runClient();
    }

    public void runClient() throws IOException {
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        this.readMessage();
    }

    public void sendMessage(Message sendMessage) {
        try {
            oos.writeObject(sendMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() {
        Thread threadReadMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message messageReceived = (Message) ois.readObject();

                        switch (messageReceived.code) {
                            case 1:
                                messageReceived = (SubMessage) messageReceived;
                                break;
                            default:
                                System.out.println("/!\\ Invalid message !");
                                break;
                        }

                        // Treatment to make:
                        // 1 -> idetnify the code of the io.slack.network.io.slack.front.Message
                        // 2 -> make the adequate treatement like update
                        //      the databse then notify every client in the vector
                        // end of
                        System.out.println(messageReceived.toString());
                        Thread.sleep(2000);
                        oos.writeObject(new SubMessage(1, "io.slack.network.io.slack.front.Message bien reçu par le client !"));
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadReadMessage.start();
    }
}
