package io.slack.network.communication;

import java.io.Serializable;

public class Message implements Serializable {

    protected final int code;

    public Message(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean hasAttachment() {
        return false;
    }

    @Override
    public String toString() {
        return "io.slack.network.io.slack.front.Message{" +
                "code=" + code +
                '}';
    }
}
