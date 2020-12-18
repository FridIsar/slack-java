package io.slack.network;

import io.slack.model.Credentials;
import io.slack.network.HandlerMessages.ClientMessageType;
import io.slack.network.HandlerMessages.MessageCompletionHandler;
import io.slack.network.HandlerMessages.SigninCompletionHandler;
import io.slack.network.communication.Message;
import io.slack.network.communication.MessageAttachment;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ConcurrentHashMap;

public class AsyncServer {


    public static void main(String[] args) throws IOException {
         ConcurrentHashMap<Integer, String> concurrentUserAuthenticated;
        concurrentUserAuthenticated = new ConcurrentHashMap<>();
        InetSocketAddress sockAddr = new InetSocketAddress("localhost", 50_500);

        //create a socket channel and bind to local bind address
        AsynchronousServerSocketChannel serverSock =  AsynchronousServerSocketChannel.open().bind(sockAddr);

        //start to accept the connection from client
        serverSock.accept(serverSock, new MessageCompletionHandler(concurrentUserAuthenticated));
        System.in.read();
    }
}
