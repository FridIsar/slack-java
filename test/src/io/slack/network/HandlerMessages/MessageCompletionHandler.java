package io.slack.network.HandlerMessages;

import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ConcurrentHashMap;

public class MessageCompletionHandler implements
        CompletionHandler<AsynchronousSocketChannel,AsynchronousServerSocketChannel> {
    private ConcurrentHashMap<Integer, String> cc;

    public MessageCompletionHandler(ConcurrentHashMap<Integer, String> cc) {
        this.cc = cc;
    }

    @Override
    public void completed(AsynchronousSocketChannel asynchronousSocketChannel,
                          AsynchronousServerSocketChannel asynchronousServerSocketChannel) {
        // new thread for future client
        asynchronousServerSocketChannel.accept( asynchronousServerSocketChannel, new MessageCompletionHandler(cc) );

        // new connection => read the message (need to be use in a thread)
        InputStream connectionInputStream = Channels.newInputStream(asynchronousSocketChannel);
        //start to read message from the client
        ObjectInputStream ois = null;
        try {
            while(true) {
                ois = new ObjectInputStream(connectionInputStream);
                Message messageReceived = (Message) ois.readObject();
                Message messageToSend = ClientMessageMapping.handlers.get(messageReceived.getCode()).asyncHandle(
                        messageReceived.hasAttachment() ? ((MessageAttachment) messageReceived).getAttachment() : null,
                        this.cc, asynchronousSocketChannel);
                ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
                oos.writeObject(messageToSend);
                asynchronousSocketChannel.write(ByteBuffer.wrap(bytesOut.toByteArray()));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connexion closed by the client");
            return;
        }
    }

    @Override
    public void failed(Throwable throwable, AsynchronousServerSocketChannel asynchronousServerSocketChannel) {

    }
}
