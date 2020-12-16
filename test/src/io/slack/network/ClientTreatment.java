package io.slack.network;

import io.slack.network.communication.Message;
import io.slack.network.communication.SubMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ClientTreatment implements Callable {
    final ObjectInputStream ois;
    final ObjectOutputStream oos;
    private Socket socket;

    public ClientTreatment(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        System.out.println("in client treatment");
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public Integer call() throws Exception {
        while(true) {
            try {
                Message messageReceived = (Message) this.ois.readObject();

                switch (messageReceived.getCode()) {
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
                this.oos.writeObject(new SubMessage(1, "io.slack.network.io.slack.front.Message bien reçu par le serveur !"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
