package io.slack.network.HandlerMessages;

import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;

public class SigninCompletionHandler implements CompletionHandler<Integer, AsynchronousSocketChannel> {

    @Override
    public void completed(Integer integer, AsynchronousSocketChannel asynchronousSocketChannel) {
        InputStream connectionInputStream = Channels.newInputStream(asynchronousSocketChannel);
        //start to read message from the client
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(connectionInputStream);
            Message message = (Message) ois.readObject();
            System.out.println("Signin: " + message.getCode());

            // Now retrieve all the Channels
            Message messageToSend = new Message(ClientMessageType.GETCHANNELS.getValue());
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bytesOut);
            oos.writeObject(messageToSend);
            asynchronousSocketChannel.write(ByteBuffer.wrap(bytesOut.toByteArray()), asynchronousSocketChannel, new GetChannelsCompletionHandler());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connexion closed by the server");
            return;
        }
    }

    @Override
    public void failed(Throwable throwable, AsynchronousSocketChannel asynchronousSocketChannel) {

    }
}
