package io.slack.network.handlerMessages;

public enum  ClientMessageType {
    //user
    SIGNIN(600),
    SIGNUP(601),
    DELETEUSER(602),
    DISCONNECT(603),

    CHECKEMAIL(604),
    UPDATEUSER(605),

    //channel
    CREATECHANNEL(700),
    DELETECHANNEL(701),

    GETCHANNELS(702),

    ADDUSERCHANNEL(703),
    GETUSERSCHANNEL(704),
    GETCHANNELSUSER(710),
    DELETEUSERCHANNEL(705),

    ADDPOSTCHANNEL(706),
    DELETEPOSTCHANNEL(707),
    GETPOSTSCHANNEL(708),

    ADDFRIEND(711),
    DELETEFRIEND(712),
    GETFRIENDS(713);

    private int value;
    private ClientMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
