package io.slack.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    // change vector
    // copy and onwrite array list
    static Vector<ClientTreatment> activatedClient;
    private ExecutorService exectutorService;
    private CompletionService<Integer> completionService;
    private ServerSocket serverSocket;

    public Server() throws IOException, ExecutionException, InterruptedException, ClassNotFoundException {
        activatedClient = new Vector<>();
        this.exectutorService = Executors.newFixedThreadPool(20);
        this.completionService = new ExecutorCompletionService<>(this.exectutorService);

        // server is listening on port 50 500
        this.serverSocket = new ServerSocket(50_500);

        this.runServer();
    }

    public void runServer() throws IOException, InterruptedException, ExecutionException, ClassNotFoundException {
        Socket socket;

        while (true) {
            socket = this.serverSocket.accept();

            System.out.println("New client request received :" + socket);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            ClientTreatment clientTreatment = new ClientTreatment(socket, ois, oos);
            activatedClient.add(clientTreatment);
            this.completionService.submit(clientTreatment);

        }
    }

    public void shutdown() {
        this.exectutorService.shutdown();
    }

}
