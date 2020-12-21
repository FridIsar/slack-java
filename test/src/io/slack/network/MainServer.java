package io.slack.network;

import io.slack.network.communication.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainServer {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ClassNotFoundException {
        Server server = new Server();
    }
}
