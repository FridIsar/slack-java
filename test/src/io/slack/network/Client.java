package io.slack.network;

import io.slack.network.communication.Message;
import io.slack.network.communication.SubMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


/**
 * TODO :
 * Logique                  Concrete action code
 * user open the app        Syste
 *
 */
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

    // TODO :
    //  split synchronous messages at the first time
    //      -> when the user want's to connect
    //  split asynchronous messages at the second time
    //      -> when the user want's to chat (both receive and send messages)
    public void readMessage() {
        Thread threadReadMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Message messageReceived = (Message) ois.readObject();
                        System.out.println(messageReceived.getCode());



                        } catch (IOException | ClassNotFoundException e) {
                        System.out.println("SERVER CLOSED !");
                        break;
                    }
                }
            }
        });
        threadReadMessage.start();
    }
}
