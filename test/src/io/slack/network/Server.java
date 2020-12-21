package io.slack.network;

import io.slack.network.communication.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.*;

public class Server {
    // user tcp connected
    static CopyOnWriteArrayList<ClientHandler> activatedClient;
    // user authenticated <Socket,  email>
    private ConcurrentHashMap<Socket, String> concurrentUserAuthenticated;
    private ExecutorService exectutorService;
    private CompletionService<Integer> completionService;
    private ServerSocket serverSocket;

    public Server() throws IOException, ExecutionException, InterruptedException, ClassNotFoundException {
        activatedClient = new CopyOnWriteArrayList<>();
        concurrentUserAuthenticated = new ConcurrentHashMap<>();

        this.exectutorService = Executors.newFixedThreadPool(5);
        this.completionService = new ExecutorCompletionService<>(this.exectutorService);
        this.serverSocket = new ServerSocket(50_500);

        Thread threadReadMessage = new Thread(() -> this.runServer());
        threadReadMessage.start();

    }

    /**
     * Method to run the Server
     */
    public void runServer() {

        while (true) {
            try {
                Socket socketClient = this.serverSocket.accept();

                // lance un thread
                ClientHandler clientHandler = new ClientHandler(socketClient, activatedClient, concurrentUserAuthenticated);
                activatedClient.add(clientHandler);
                this.completionService.submit(clientHandler);

            } catch (IOException e) {
                System.out.println("runServer : error IOEXception");
                e.printStackTrace();
            }
        }
    }


    public void shutdown() {
        this.exectutorService.shutdown();
    }

}
