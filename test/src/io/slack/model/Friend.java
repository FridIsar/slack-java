package io.slack.model;

import java.io.Serializable;
import java.util.Objects;

public class Friend implements Serializable {
    private User firstUser;
    private User secUser;
    private ChannelDirect channelDirect;

    public Friend(){}

    public Friend(User firstUser, User secUser) {
        this.firstUser = firstUser;
        this.secUser = secUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return (Objects.equals(firstUser, friend.firstUser) &&
                Objects.equals(secUser, friend.secUser)) ||
                (Objects.equals(firstUser, friend.secUser) &&
                Objects.equals(secUser, friend.firstUser));
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
