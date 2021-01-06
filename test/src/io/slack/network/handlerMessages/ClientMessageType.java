package io.slack.network.HandlerMessages;

public enum  ClientMessageType {
    //user
    SIGNIN(100),
    SIGNUP(101),
    DELETEUSER(102),
    DISCONNECT(103),

    CHECKEMAIL(104), //todo send a string or an object ?
    UPDATEUSER(105),

    //channel
    CREATECHANNEL(200),
    DELETECHANNEL(201),

    GETCHANNELS(202),

    ADDUSERCHANNEL(203),
    GETUSERSCHANNEL(204),
    DELETEUSERCHANNEL(205),

    ADDPOSTCHANNEL(206),
    DELETEPOSTCHANNEL(207),
    GETPOSTSCHANNEL(208);


    private int value;
    private ClientMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
