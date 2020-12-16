package io.slack.network;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException {
        Client client1 = new Client();
        Message message = new SubMessage(1, "Salut c'est un premier message du client");
        client1.sendMessage(message);
    }
}
