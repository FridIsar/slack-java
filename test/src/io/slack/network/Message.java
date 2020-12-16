package io.slack.network;

import java.io.Serializable;

public class Message implements Serializable {
    protected int code;

    public Message(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "io.slack.network.io.slack.front.Message{" +
                "code=" + code +
                '}';
    }
}
