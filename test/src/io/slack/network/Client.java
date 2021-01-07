package io.slack.network;

import io.slack.controller.ControllerClient;
import io.slack.model.Member;
import io.slack.model.Post;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;
import io.slack.network.handlerMessages.ClientMessageType;
import io.slack.network.model.UserInChannel;

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
    final int serverPort = 50_600;

    // port Serveur Ubuntu :
    // final int serverPort = 40_000;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Message response;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private ControllerClient controllerClient;

    public Client() throws IOException {
        // test localhost :
        this.ip = InetAddress.getByName("127.0.0.1");

        // adresse Serveur Ubuntu :

        //this.ip = InetAddress.getByName("20.39.243.239");
        this.socket = new Socket(ip, serverPort);
        this.runClient();
    }

    public Client(ControllerClient controllerClient) throws IOException {
        this.controllerClient = controllerClient;

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
    private void runClient() throws IOException {
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

        System.out.println("sendMessage "+sendMessage.getCode());
        try {
            System.out.println("writing sendMessage...");
            oos.writeObject(sendMessage);
        } catch (IOException e) {
            System.out.println("Writing error sendMessage");
            e.printStackTrace();
        }
        System.out.println("waiting for lock");
        // Waiting until response variable is set
        this.lock.lock();
        this.condition.await();
        System.out.println("sendMessage into lock");
        if (this.response == null) {
            System.out.println("sendMessage An error occurred : response null");
        } else {
            if (this.response.hasAttachment())   {
                response = new MessageAttachment((MessageAttachment) this.response);
            }
            else  {
                response = new Message(this.response);
            }
        }
        this.response = null;
        this.lock.unlock();
        return response;
    }

    /**
     * Method to read continuously entrering Message (s) coming from the server
     */
    private void readContinuouslyMessages() {
        while (true) {
            try {


                Message messageReceived = (Message) ois.readObject();
                System.out.println(messageReceived.getCode() + "is message received client" + ((MessageAttachment) messageReceived).getAttachment().getClass());

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
                    handlerMessageSentByServer((MessageAttachment) messageReceived);

                    System.out.println("Message coming from server , maybe update channel");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("SERVER CLOSED !");
                e.printStackTrace();

                // SERVER CLOSES ->
                this.controllerClient.receiveErrorServer();
                break;
            }
        }
    }

    private void handlerMessageSentByServer(MessageAttachment messageReceived) {
        // TODO :
        //  /!\ remplacer le new par this.controllerClient

        switch (messageReceived.getCode()) {
            case  706:
                // ADDPOSTCHANNEL -> un User a enovyé un Post dans un Channel dont fait partie Client
                // désencapsuler le MessageAttachement et envoyer la donnée Post
                this.controllerClient.receivePost( (Post) messageReceived.getAttachment());
                break;

            case 707 :
                // DELETEPOSTCHANNEL -> un User a supprimé un Post dans un Channel dont fait partie Client
                // désencapsuler le MessageAttachement et envoyer la donnée Post
                this.controllerClient.receiveDeletePost( (Post) messageReceived.getAttachment());
                break;

            case 703 :
                System.out.println("case 703");
                Member member =  (Member) ((MessageAttachment) messageReceived).getAttachment();
                this.controllerClient.receiveAddUserInChannel(member.getUser(), member.getChannel());
                break;

            case 705 :
                // DELETEUSERCHANNEL -> un User admin d'un Channel a retiré un membre User du channel dont fait partie Client
                // TODO :
                //  Passer en paramètre un model de network UserInChannel
                UserInChannel userInChannel = (UserInChannel) messageReceived.getAttachment();
                this.controllerClient.receiveRemoveUserInChannel( userInChannel.getUser(), userInChannel.getChannel());
                break;

            default:
                break;
        }
    }
}
