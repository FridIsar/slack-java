package io.slack.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Vector;
import java.util.concurrent.*;

public class Server {
    // user tcp connected
    static CopyOnWriteArrayList<ClientTreatment> activatedClient;
    // user authenticated
    private ConcurrentHashMap<Integer, String> concurrentUserAuthenticated;
    private ExecutorService exectutorService;
    private CompletionService<Integer> completionService;
    private ServerSocket serverSocket;

    public Server() throws IOException, ExecutionException, InterruptedException, ClassNotFoundException {
        activatedClient = new CopyOnWriteArrayList<>();
        concurrentUserAuthenticated = new ConcurrentHashMap<>();

        this.exectutorService = Executors.newFixedThreadPool(5);
        this.completionService = new ExecutorCompletionService<>(this.exectutorService);
        this.serverSocket = new ServerSocket(50_500);

        this.runServer();

    }

    public void runServer() throws IOException, InterruptedException, ExecutionException, ClassNotFoundException {

        while (true) {
            Socket socketClient = this.serverSocket.accept();

            // lance un thread
            ClientTreatment clientTreatment = new ClientTreatment(socketClient, activatedClient, concurrentUserAuthenticated);
            activatedClient.add(clientTreatment);
            this.completionService.submit(clientTreatment);

        }
    }

    public void shutdown() {
        this.exectutorService.shutdown();
    }

}
