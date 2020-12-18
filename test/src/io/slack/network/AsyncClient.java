package io.slack.network;

import io.slack.model.Credentials;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.HandlerMessages.SigninCompletionHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncClient {

    public static void main(String[] args) {
        try {
            AsynchronousSocketChannel socket = AsynchronousSocketChannel.open();
            socket.connect(new InetSocketAddress("localhost", 50_500)).get();

            // Send the Signin Message and wait
            Message message = new MessageAttachment<Credentials>(ClientMessageType.SIGNIN.getValue(), new Credentials());
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
            oos.writeObject(message);
            socket.write(ByteBuffer.wrap(bytesOut.toByteArray()), socket, new SigninCompletionHandler());
            System.in.read();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
