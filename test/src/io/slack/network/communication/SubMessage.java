package io.slack.network.communication;


public class SubMessage extends Message {
    private String specificSubMessage;

    public SubMessage(int code, String specificSubMessage) {
        super(code);
        this.specificSubMessage = specificSubMessage;
    }

    @Override
    public String toString() {
        return "SubMessage{" +
                "specificSubMessage='" + specificSubMessage + '\'' +
                ", code=" + code +
                '}';
    }
}
