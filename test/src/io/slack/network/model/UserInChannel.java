package io.slack.network.model;

import io.slack.model.Channel;
import io.slack.model.User;

import java.io.Serializable;

public class UserInChannel implements Comparable<UserInChannel>, Serializable {

    private User user;
    private Channel channel;

    public UserInChannel(User user, Channel channel) {
        this.user = user;
        this.channel = channel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public int compareTo(UserInChannel userInChannel) {
        return 0;
    }
}
