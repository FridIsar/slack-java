import java.io.*;
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

                switch (messageReceived.code) {
                    case 1:
                        messageReceived = (SubMessage) messageReceived;
                        break;
                    default:
                        System.out.println("/!\\ Invalid message !");
                        break;
                }

                // Treatment to make:
                // 1 -> idetnify the code of the Message
                // 2 -> make the adequate treatement like update
                //      the databse then notify every client in the vector
                // end of
                System.out.println(messageReceived.toString());
                this.oos.writeObject(new SubMessage(1, "Message bien reçu par le serveur !"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
