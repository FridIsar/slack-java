package io.slack.utils;

public class Pair<Message,Thread> {
    public final Message message;
    public final Thread thread;

    public Pair(Message message, Thread thread) {
        this.message = message;
        this.thread = thread;
    }

    public Message getMessage() {
        return message;
    }

    public Thread getThread() {
        return thread;
    }

};