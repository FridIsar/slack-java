package io.slack.network.HandlerMessages;

public enum  ClientMessageType {
    SIGNIN(100),
    SIGNUP(101),
    GETCHANNELS(200),
    ADDMESSAGECHANNEL(201),
    DELETECHANNEL(202),
    DELETEUSERCHANNEL(203),
    CREATECHANNEL(204);

    private int value;
    private ClientMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
