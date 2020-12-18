package io.slack.network.HandlerMessages;

public enum  ClientMessageType {
    SIGNIN(100),
    SIGNUP(101),
    GETCHANNELS(200);

    private int value;
    private ClientMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
