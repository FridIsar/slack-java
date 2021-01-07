package io.slack.network.model;

import java.io.Serializable;

public class UserAndChannelCredentials implements Serializable {
    private String userEmail;
    private String channelTitle;

    public UserAndChannelCredentials()  {}

    public UserAndChannelCredentials(String userEmail, String channelTitle) {
        this.userEmail = userEmail;
        this.channelTitle = channelTitle;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getChannelTitle() {
        return channelTitle;
    }
}
