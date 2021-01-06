package io.slack.model;

import java.io.Serializable;

public class Member implements Serializable {
    private Channel channel;
    private User user;

    public Member(){}

    public Member(Channel channel, User secUser) {
        this.channel = channel;
        this.user = secUser;
    }

    public Channel getChannel() {
        return channel;
    }

    public User getUser() {
        return user;
    }
}
