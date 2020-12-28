package io.slack.network.model;

import java.io.Serializable;

public class ChannelCredentials implements Serializable {
    private String title;
    private String adminEmail;

    public ChannelCredentials(){}

    public ChannelCredentials(String title, String adminEmail) {
        this.title = title;
        this.adminEmail = adminEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getAdminEmail() {
        return adminEmail;
    }
}
