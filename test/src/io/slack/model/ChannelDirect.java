package io.slack.model;

public class ChannelDirect extends Channel {

    private Friend friend;

    public ChannelDirect() {}

    public ChannelDirect(Friend friend) {
        this.friend = friend;
    }

    public Friend getFriend() {
        return friend;
    }
}
