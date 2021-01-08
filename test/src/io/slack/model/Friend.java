package io.slack.model;

import java.io.Serializable;

public class Friend implements Serializable {
    private User firstUser;
    private User secUser;
    private ChannelDirect channelDirect;

    public Friend(){}

    public Friend(User firstUser, User secUser) {
        this.firstUser = firstUser;
        this.secUser = secUser;
    }

    public ChannelDirect getChannelDirect() {
        return channelDirect;
    }

    public void setChannelDirect(ChannelDirect channelDirect) {
        this.channelDirect = channelDirect;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public User getSecUser() {
        return secUser;
    }
}
